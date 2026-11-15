package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueType;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueTypeRequest;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.search.SearchCatalogFixtures;
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
class GitHubUsersIssueTypesApiTest {

    private static final long ISSUE_TYPE_ID = 1L;
    private static final String ISSUE_TYPES_URI = "https://api.github.com/user/issue-types";
    private static final String ISSUE_TYPE_URI = "https://api.github.com/user/issue-types/1";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUsersIssueTypesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void list_shouldReturnIssueTypes_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + issueTypeJson() + "]"));

        final List<GitHubIssueType> issueTypes = callApi(GitHubUsersIssueTypesApi::list);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(issueTypes).containsExactly(SearchCatalogFixtures.issueType());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(ISSUE_TYPES_URI);
        });
    }

    @Test
    void list_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> callApi(GitHubUsersIssueTypesApi::list));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void create_shouldPostPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueTypeJson()));
        final GitHubIssueTypeRequest payload = OrganizationsCatalogFixtures.issueTypeRequest();

        final GitHubIssueType created = callApi(api -> api.create(payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(created).isEqualTo(SearchCatalogFixtures.issueType());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString(ISSUE_TYPES_URI);
            softly.assertThat(bodyOf(request)).isEqualTo(encoded(payload));
        });
    }

    @Test
    void create_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> callApi(api -> api.create(null)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void update_shouldPutPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueTypeJson()));
        final GitHubIssueTypeRequest payload = OrganizationsCatalogFixtures.issueTypeRequest();

        final GitHubIssueType updated = callApi(api -> api.update(ISSUE_TYPE_ID, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(updated).isEqualTo(SearchCatalogFixtures.issueType());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString(ISSUE_TYPE_URI);
            softly.assertThat(bodyOf(request)).isEqualTo(encoded(payload));
        });
    }

    @Test
    void update_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> callApi(api -> api.update(ISSUE_TYPE_ID, null)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void delete_shouldDeleteIssueType_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApi(api -> {
            api.delete(ISSUE_TYPE_ID);
            return null;
        });
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString(ISSUE_TYPE_URI);
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

        assertThat(request.uri()).hasToString("https://api.github.com/user/issue-types/3000000000");
    }

    @FunctionalInterface
    private interface IssueTypesApiAction<T> {
        T apply(GitHubUsersIssueTypesApi api) throws Exception;
    }

    private <T> T callApi(final IssueTypesApiAction<T> action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubUsersIssueTypesApi(TestClients.caller(client)));
        }
    }

    private static String issueTypeJson() {
        return FileUtils.readString(SearchCatalogFixtures.class, "issue-type.json");
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
