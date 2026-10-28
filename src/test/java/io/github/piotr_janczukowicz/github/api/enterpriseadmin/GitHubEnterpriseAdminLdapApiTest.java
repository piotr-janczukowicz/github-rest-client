package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubEnterpriseAdminLdapApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEnterpriseAdminLdapApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void syncLdapMappingForTeam_shouldPostAndReturnStatus_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("sync-ldap-mapping-for-team.json")));

        final GitHubSyncLdapMappingForTeam result = api().syncLdapMappingForTeam(1L);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.syncLdapMappingForTeam());
        assertThat(line(captureSingleRequest())).isEqualTo("POST https://api.github.com/admin/ldap/teams/1/sync");
    }

    @Test
    void syncLdapMappingForUser_shouldPostAndReturnStatus_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("sync-ldap-mapping-for-user.json")));

        final GitHubSyncLdapMappingForUser result = api().syncLdapMappingForUser("octocat");

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.syncLdapMappingForUser());
        assertThat(line(captureSingleRequest())).isEqualTo("POST https://api.github.com/admin/ldap/users/octocat/sync");
    }

    private GitHubEnterpriseAdminLdapApi api() {
        return new GitHubEnterpriseAdminLdapApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubEnterpriseAdminLdapApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static String line(final HttpRequest request) {
        return request.method() + " " + request.uri();
    }

    @Test
    void updateLdapMappingForTeam_shouldPatchAndReturnTeam_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("ldap-mapping-team.json")));

        final GitHubLdapMappingTeam team =
                api().updateLdapMappingForTeam(1L, EnterpriseAdminCatalogFixtures.updateLdapMappingForTeamPayload());

        assertThat(team).isEqualTo(EnterpriseAdminCatalogFixtures.ldapMappingTeam());
        assertThat(line(captureSingleRequest())).isEqualTo("PATCH https://api.github.com/admin/ldap/teams/1/mapping");
    }

    @Test
    void updateLdapMappingForTeam_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminLdapApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updateLdapMappingForTeam(1L, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void updateLdapMappingForUser_shouldPatchAndReturnUser_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("ldap-mapping-user.json")));

        final GitHubLdapMappingUser user = api().updateLdapMappingForUser(
                        "octocat", EnterpriseAdminCatalogFixtures.updateLdapMappingForUserPayload());

        assertThat(user).isEqualTo(EnterpriseAdminCatalogFixtures.ldapMappingUser());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("PATCH https://api.github.com/admin/ldap/users/octocat/mapping");
    }

    @Test
    void updateLdapMappingForUser_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminLdapApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updateLdapMappingForUser("octocat", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }
}
