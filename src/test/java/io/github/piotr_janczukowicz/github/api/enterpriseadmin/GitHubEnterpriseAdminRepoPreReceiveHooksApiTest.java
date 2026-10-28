package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubEnterpriseAdminRepoPreReceiveHooksApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEnterpriseAdminRepoPreReceiveHooksApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listPreReceiveHooksForRepo_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-pre-receive-hook.json") + "]"));

        final GitHubPage<GitHubRepositoryPreReceiveHook> page = api().listPreReceiveHooksForRepo("octocat", "octocat");

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.repositoryPreReceiveHook());
        assertThat(captureSingleRequest().uri().toString())
                .startsWith("https://api.github.com/repos/octocat/octocat/pre-receive-hooks?");
    }

    @Test
    void listPreReceiveHooksForRepo_shouldSendSortAndDirection_whenFilterGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-pre-receive-hook.json") + "]"));

        api().listPreReceiveHooksForRepo(
                        "octocat", "octocat", EnterpriseAdminCatalogFixtures.listPreReceiveHooksForRepoFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("sort", "created").hasParameter("direction", "desc");
    }

    @Test
    void listPreReceiveHooksForRepo_shouldThrow_whenRequestNull() {
        final GitHubEnterpriseAdminRepoPreReceiveHooksApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listPreReceiveHooksForRepo(
                "octocat", "octocat", GitHubListPreReceiveHooksForRepoFilter.empty(), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void getPreReceiveHookForRepo_shouldReturnResource_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("repository-pre-receive-hook.json")));

        final Optional<GitHubRepositoryPreReceiveHook> result =
                api().getPreReceiveHookForRepo("octocat", "octocat", 1L);

        assertThat(result).contains(EnterpriseAdminCatalogFixtures.repositoryPreReceiveHook());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("GET https://api.github.com/repos/octocat/octocat/pre-receive-hooks/1");
    }

    @Test
    void getPreReceiveHookForRepo_shouldBeEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubRepositoryPreReceiveHook> result =
                api().getPreReceiveHookForRepo("octocat", "octocat", 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void updatePreReceiveHookEnforcementForRepo_shouldSendPatchAndReturnResource_whenResponseIsSuccessful()
            throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("repository-pre-receive-hook.json")));

        final GitHubRepositoryPreReceiveHook result = api().updatePreReceiveHookEnforcementForRepo(
                        "octocat",
                        "octocat",
                        1L,
                        EnterpriseAdminCatalogFixtures.updatePreReceiveHookEnforcementForRepoPayload());

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.repositoryPreReceiveHook());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("PATCH https://api.github.com/repos/octocat/octocat/pre-receive-hooks/1");
    }

    @Test
    void updatePreReceiveHookEnforcementForRepo_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminRepoPreReceiveHooksApi api = api();

        final Throwable thrown =
                catchThrowable(() -> api.updatePreReceiveHookEnforcementForRepo("octocat", "octocat", 1L, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void removePreReceiveHookEnforcementForRepo_shouldSendDeleteAndReturnResource_whenResponseIsSuccessful()
            throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("repository-pre-receive-hook.json")));

        final GitHubRepositoryPreReceiveHook result =
                api().removePreReceiveHookEnforcementForRepo("octocat", "octocat", 1L);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.repositoryPreReceiveHook());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE https://api.github.com/repos/octocat/octocat/pre-receive-hooks/1");
    }

    private GitHubEnterpriseAdminRepoPreReceiveHooksApi api() {
        return new GitHubEnterpriseAdminRepoPreReceiveHooksApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubEnterpriseAdminRepoPreReceiveHooksApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static String line(final HttpRequest request) {
        return request.method() + " " + request.uri();
    }
}
