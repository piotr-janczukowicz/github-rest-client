package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubUsersIssueFieldsApiTest {

    private static final long ISSUE_FIELD_ID = 1L;
    private static final String ISSUE_FIELDS_URI = "https://api.github.com/user/issue-fields";
    private static final String ISSUE_FIELD_URI = "https://api.github.com/user/issue-fields/1";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUsersIssueFieldsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void list_shouldReturnIssueFields_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + issueFieldJson() + "]"));

        final List<GitHubUserIssueField> issueFields = callApi(GitHubUsersIssueFieldsApi::list);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(issueFields).containsExactly(UsersCatalogFixtures.userIssueField());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(ISSUE_FIELDS_URI);
        });
    }

    @Test
    void list_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> callApi(GitHubUsersIssueFieldsApi::list));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void create_shouldPostPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueFieldJson()));
        final GitHubCreateUserIssueFieldPayload payload = UsersCatalogFixtures.createUserIssueFieldPayload();

        final GitHubUserIssueField created = callApi(api -> api.create(payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(created).isEqualTo(UsersCatalogFixtures.userIssueField());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString(ISSUE_FIELDS_URI);
            softly.assertThat(bodyOf(request)).isEqualTo(encoded(payload));
        });
    }

    @Test
    void create_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> callApi(api -> api.create(null)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void update_shouldPatchPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueFieldJson()));
        final GitHubUpdateUserIssueFieldPayload payload = UsersCatalogFixtures.updateUserIssueFieldPayload();

        final GitHubUserIssueField updated = callApi(api -> api.update(ISSUE_FIELD_ID, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(updated).isEqualTo(UsersCatalogFixtures.userIssueField());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString(ISSUE_FIELD_URI);
            softly.assertThat(bodyOf(request)).isEqualTo(encoded(payload));
        });
    }

    @Test
    void update_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> callApi(api -> api.update(ISSUE_FIELD_ID, null)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void delete_shouldDeleteIssueField_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApi(api -> {
            api.delete(ISSUE_FIELD_ID);
            return null;
        });
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString(ISSUE_FIELD_URI);
        });
    }

    @Test
    void delete_shouldPutLongIdInPath_whenIdExceedsIntRange() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApi(api -> {
            api.delete(3_000_000_000L);
            return null;
        });
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/user/issue-fields/3000000000");
    }

    @FunctionalInterface
    private interface IssueFieldsApiAction<T> {
        T apply(GitHubUsersIssueFieldsApi api) throws Exception;
    }

    private <T> T callApi(final IssueFieldsApiAction<T> action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubUsersIssueFieldsApi(TestClients.caller(client)));
        }
    }

    private static String issueFieldJson() {
        return FileUtils.readString(GitHubUsersIssueFieldsApiTest.class, "user-issue-field.json");
    }

    private static String bodyOf(final HttpRequest request) {
        return new String(request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8);
    }

    private static String encoded(final Object payload) {
        return new String(GitHubJsonCodecs.defaults().encode(payload), StandardCharsets.UTF_8);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
