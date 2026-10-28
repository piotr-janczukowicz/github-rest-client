package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
class GitHubOrganizationsIssueTypesApiTest {

    private static final String ORG = "github";
    private static final int ISSUE_TYPE_ID = 1;
    private static final String ISSUE_TYPES_PATH = "/orgs/github/issue-types";
    private static final String ISSUE_TYPE_PATH = "/orgs/github/issue-types/1";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsIssueTypesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void list_shouldReturnIssueTypes_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + issueTypeJson() + "]"));

        final List<GitHubIssueType> issueTypes = callApi(api -> api.list(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(issueTypes).isEqualTo(List.of(SearchCatalogFixtures.issueType()));
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ISSUE_TYPES_PATH);
        });
    }

    @Test
    void list_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> callApi(api -> api.list(ORG)));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void list_shouldThrow_whenOrgBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsIssueTypesApi api =
                    new GitHubOrganizationsIssueTypesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.list(" "));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
        }
    }

    @Test
    void create_shouldPostPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueTypeJson()));
        final GitHubCreateIssueTypePayload payload = OrganizationsCatalogFixtures.createIssueTypePayload();

        final GitHubIssueType created = callApi(api -> api.create(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(created).isEqualTo(SearchCatalogFixtures.issueType());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ISSUE_TYPES_PATH);
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo(new String(GitHubJsonCodecs.defaults().encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void create_shouldThrow_whenPayloadNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsIssueTypesApi api =
                    new GitHubOrganizationsIssueTypesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.create(ORG, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    @Test
    void update_shouldPutPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), issueTypeJson()));
        final GitHubUpdateIssueTypePayload payload = OrganizationsCatalogFixtures.updateIssueTypePayload();

        final GitHubIssueType updated = callApi(api -> api.update(ORG, ISSUE_TYPE_ID, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(updated).isEqualTo(SearchCatalogFixtures.issueType());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ISSUE_TYPE_PATH);
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo(new String(GitHubJsonCodecs.defaults().encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void update_shouldThrow_whenPayloadNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsIssueTypesApi api =
                    new GitHubOrganizationsIssueTypesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.update(ORG, ISSUE_TYPE_ID, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    @Test
    void delete_shouldPutLongIdInPath_whenIdExceedsIntRange() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.delete(ORG, 3_000_000_000L));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/issue-types/3000000000");
    }

    @Test
    void delete_shouldDeleteIssueType_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.delete(ORG, ISSUE_TYPE_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ISSUE_TYPE_PATH);
        });
    }

    @FunctionalInterface
    private interface IssueTypesApiCall {
        void execute(GitHubOrganizationsIssueTypesApi api) throws Exception;
    }

    @FunctionalInterface
    private interface IssueTypesApiAction<T> {
        T apply(GitHubOrganizationsIssueTypesApi api) throws Exception;
    }

    private void callApiVoid(final IssueTypesApiCall action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            action.execute(new GitHubOrganizationsIssueTypesApi(TestClients.caller(client)));
        }
    }

    private <T> T callApi(final IssueTypesApiAction<T> action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationsIssueTypesApi(TestClients.caller(client)));
        }
    }

    private static String issueTypeJson() {
        return FileUtils.readString(SearchCatalogFixtures.class, "issue-type.json");
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
