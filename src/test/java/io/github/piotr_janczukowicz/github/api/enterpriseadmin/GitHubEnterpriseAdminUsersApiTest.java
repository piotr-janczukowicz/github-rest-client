package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.apps.GitHubAuthorization;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubEnterpriseAdminUsersApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEnterpriseAdminUsersApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listPublicKeys_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("public-key-full.json") + "]"));

        final GitHubPage<GitHubPublicKeyFull> page = api().listPublicKeys();

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.publicKeyFull());
        assertThat(captureSingleRequest().uri().toString()).startsWith("https://api.github.com/admin/keys?");
    }

    @Test
    void listPublicKeys_shouldSendSortAndDirection_whenFilterGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("public-key-full.json") + "]"));

        api().listPublicKeys(EnterpriseAdminCatalogFixtures.listPublicKeysFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("sort", "created").hasParameter("direction", "desc");
    }

    @Test
    void listPublicKeys_shouldThrow_whenRequestNull() {
        final GitHubEnterpriseAdminUsersApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listPublicKeys(GitHubListPublicKeysFilter.empty(), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void deletePublicKey_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deletePublicKey("octocat");

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE https://api.github.com/admin/keys/octocat");
    }

    @Test
    void deletePersonalAccessToken_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deletePersonalAccessToken(1L);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE https://api.github.com/admin/tokens/1");
    }

    @Test
    void createUser_shouldSendPostAndReturnResource_whenResponseIsSuccessful() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("simple-user.json")));

        final GitHubSimpleUser result = api().createUser(EnterpriseAdminCatalogFixtures.createUserPayload());

        assertThat(result).isNotNull();
        assertThat(line(captureSingleRequest())).isEqualTo("POST https://api.github.com/admin/users");
    }

    @Test
    void createUser_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminUsersApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createUser(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void updateUsernameForUser_shouldSendPatchAndReturnResource_whenResponseIsSuccessful() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("update-username-for-user-response.json")));

        final GitHubUpdateUsernameForUserResponse result =
                api().updateUsernameForUser("octocat", EnterpriseAdminCatalogFixtures.updateUsernameForUserPayload());

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.updateUsernameForUserResponse());
        assertThat(line(captureSingleRequest())).isEqualTo("PATCH https://api.github.com/admin/users/octocat");
    }

    @Test
    void updateUsernameForUser_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminUsersApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updateUsernameForUser("octocat", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteUser_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteUser("octocat");

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE https://api.github.com/admin/users/octocat");
    }

    @Test
    void createImpersonationOAuthToken_shouldSendPostAndReturnResource_whenResponseIsSuccessful() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("authorization.json")));

        final GitHubAuthorization result = api().createImpersonationOAuthToken(
                        "octocat", EnterpriseAdminCatalogFixtures.createImpersonationOAuthTokenPayload());

        assertThat(result).isNotNull();
        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/admin/users/octocat/authorizations");
    }

    @Test
    void createImpersonationOAuthToken_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminUsersApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createImpersonationOAuthToken("octocat", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteImpersonationOAuthToken_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteImpersonationOAuthToken("octocat");

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE https://api.github.com/admin/users/octocat/authorizations");
    }

    @Test
    void listPersonalAccessTokens_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("authorization.json") + "]"));

        final GitHubPage<GitHubAuthorization> page = api().listPersonalAccessTokens();

        assertThat(page.items()).hasSize(1);
        assertThat(captureSingleRequest().uri().toString()).startsWith("https://api.github.com/admin/tokens?");
    }

    @Test
    void listPersonalAccessTokens_shouldThrow_whenRequestNull() {
        final GitHubEnterpriseAdminUsersApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listPersonalAccessTokens(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void promoteUserToBeSiteAdministrator_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().promoteUserToBeSiteAdministrator("octocat");

        assertThat(line(captureSingleRequest())).isEqualTo("PUT https://api.github.com/users/octocat/site_admin");
    }

    @Test
    void demoteSiteAdministrator_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().demoteSiteAdministrator("octocat");

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE https://api.github.com/users/octocat/site_admin");
    }

    @Test
    void suspendUser_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().suspendUser("octocat");

        assertThat(line(captureSingleRequest())).isEqualTo("PUT https://api.github.com/users/octocat/suspended");
    }

    @Test
    void suspendUser_shouldSendPayload_whenPayloadGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().suspendUser("octocat", EnterpriseAdminCatalogFixtures.suspendUserPayload());

        assertThat(line(captureSingleRequest())).isEqualTo("PUT https://api.github.com/users/octocat/suspended");
    }

    @Test
    void suspendUser_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminUsersApi api = api();

        final Throwable thrown = catchThrowable(() -> api.suspendUser("octocat", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void unsuspendUser_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().unsuspendUser("octocat");

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE https://api.github.com/users/octocat/suspended");
    }

    @Test
    void unsuspendUser_shouldSendPayload_whenPayloadGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().unsuspendUser("octocat", EnterpriseAdminCatalogFixtures.unsuspendUserPayload());

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE https://api.github.com/users/octocat/suspended");
    }

    @Test
    void unsuspendUser_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminUsersApi api = api();

        final Throwable thrown = catchThrowable(() -> api.unsuspendUser("octocat", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    private GitHubEnterpriseAdminUsersApi api() {
        return new GitHubEnterpriseAdminUsersApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubEnterpriseAdminUsersApiTest.class, fileName);
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
