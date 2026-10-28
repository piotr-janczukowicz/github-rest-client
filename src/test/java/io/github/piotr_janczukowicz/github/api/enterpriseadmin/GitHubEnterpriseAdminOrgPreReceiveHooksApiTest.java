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
class GitHubEnterpriseAdminOrgPreReceiveHooksApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEnterpriseAdminOrgPreReceiveHooksApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listPreReceiveHooksForOrg_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("org-pre-receive-hook.json") + "]"));

        final GitHubPage<GitHubOrgPreReceiveHook> page = api().listPreReceiveHooksForOrg("octocat");

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.orgPreReceiveHook());
        assertThat(captureSingleRequest().uri().toString())
                .startsWith("https://api.github.com/orgs/octocat/pre-receive-hooks?");
    }

    @Test
    void listPreReceiveHooksForOrg_shouldSendSortAndDirection_whenFilterGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("org-pre-receive-hook.json") + "]"));

        api().listPreReceiveHooksForOrg("octocat", EnterpriseAdminCatalogFixtures.listPreReceiveHooksForOrgFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("sort", "created").hasParameter("direction", "desc");
    }

    @Test
    void listPreReceiveHooksForOrg_shouldThrow_whenRequestNull() {
        final GitHubEnterpriseAdminOrgPreReceiveHooksApi api = api();

        final Throwable thrown = catchThrowable(
                () -> api.listPreReceiveHooksForOrg("octocat", GitHubListPreReceiveHooksForOrgFilter.empty(), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void getPreReceiveHookForOrg_shouldReturnResource_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("org-pre-receive-hook.json")));

        final Optional<GitHubOrgPreReceiveHook> result = api().getPreReceiveHookForOrg("octocat", 1L);

        assertThat(result).contains(EnterpriseAdminCatalogFixtures.orgPreReceiveHook());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("GET https://api.github.com/orgs/octocat/pre-receive-hooks/1");
    }

    @Test
    void getPreReceiveHookForOrg_shouldBeEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubOrgPreReceiveHook> result = api().getPreReceiveHookForOrg("octocat", 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void updatePreReceiveHookEnforcementForOrg_shouldSendPatchAndReturnResource_whenResponseIsSuccessful()
            throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("org-pre-receive-hook.json")));

        final GitHubOrgPreReceiveHook result = api().updatePreReceiveHookEnforcementForOrg(
                        "octocat", 1L, EnterpriseAdminCatalogFixtures.updatePreReceiveHookEnforcementForOrgPayload());

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.orgPreReceiveHook());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("PATCH https://api.github.com/orgs/octocat/pre-receive-hooks/1");
    }

    @Test
    void updatePreReceiveHookEnforcementForOrg_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminOrgPreReceiveHooksApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updatePreReceiveHookEnforcementForOrg("octocat", 1L, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void removePreReceiveHookEnforcementForOrg_shouldSendDeleteAndReturnResource_whenResponseIsSuccessful()
            throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("org-pre-receive-hook.json")));

        final GitHubOrgPreReceiveHook result = api().removePreReceiveHookEnforcementForOrg("octocat", 1L);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.orgPreReceiveHook());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE https://api.github.com/orgs/octocat/pre-receive-hooks/1");
    }

    private GitHubEnterpriseAdminOrgPreReceiveHooksApi api() {
        return new GitHubEnterpriseAdminOrgPreReceiveHooksApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubEnterpriseAdminOrgPreReceiveHooksApiTest.class, fileName);
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
