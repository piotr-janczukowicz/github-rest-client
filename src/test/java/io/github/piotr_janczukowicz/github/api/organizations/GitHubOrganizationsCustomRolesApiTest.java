package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubOrganizationsCustomRolesApiTest {

    private static final String ORG = "github";
    private static final long ROLE_ID = 8030L;
    private static final String BASE = "https://api.github.com";
    private static final String REPO_ROLES = BASE + "/orgs/github/custom-repository-roles";
    private static final String REPO_ROLE = REPO_ROLES + "/8030";
    private static final String CUSTOM_ROLES = BASE + "/orgs/github/custom_roles";
    private static final String CUSTOM_ROLE = CUSTOM_ROLES + "/8030";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsCustomRolesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listCustomRoles_shouldThrowUnsupported_withoutHttpCall() {
        final GitHubOrganizationsCustomRolesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listCustomRoles("1"));

        assertThat(thrown).isInstanceOf(UnsupportedOperationException.class);
        verifyNoInteractions(transport);
    }

    @Test
    void listCustomRepoRoles_shouldReturnRoles_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-custom-repo-roles-response.json")));

        final GitHubListCustomRepoRolesResponse response = api().listCustomRepoRoles(ORG);

        assertThat(response).isEqualTo(OrganizationsCatalogFixtures.listCustomRepoRolesResponse());
        assertThat(line(captureSingleRequest())).isEqualTo("GET " + REPO_ROLES);
    }

    @Test
    void createCustomRepoRole_shouldReturnRole_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("organization-custom-repository-role.json")));

        final GitHubOrganizationCustomRepositoryRole role =
                api().createCustomRepoRole(ORG, OrganizationsCatalogFixtures.createCustomRepoRolePayload());

        assertThat(role).isEqualTo(OrganizationsCatalogFixtures.organizationCustomRepositoryRole());
        assertThat(line(captureSingleRequest())).isEqualTo("POST " + REPO_ROLES);
    }

    @Test
    void getCustomRepoRole_shouldReturnRole_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-custom-repository-role.json")));

        final Optional<GitHubOrganizationCustomRepositoryRole> role = api().getCustomRepoRole(ORG, ROLE_ID);

        assertThat(role).contains(OrganizationsCatalogFixtures.organizationCustomRepositoryRole());
        assertThat(line(captureSingleRequest())).isEqualTo("GET " + REPO_ROLE);
    }

    @Test
    void getCustomRepoRole_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubOrganizationCustomRepositoryRole> role = api().getCustomRepoRole(ORG, ROLE_ID);

        assertThat(role).isEmpty();
    }

    @Test
    void updateCustomRepoRole_shouldReturnRole_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-custom-repository-role.json")));

        final GitHubOrganizationCustomRepositoryRole role =
                api().updateCustomRepoRole(ORG, ROLE_ID, OrganizationsCatalogFixtures.updateCustomRepoRolePayload());

        assertThat(role).isEqualTo(OrganizationsCatalogFixtures.organizationCustomRepositoryRole());
        assertThat(line(captureSingleRequest())).isEqualTo("PATCH " + REPO_ROLE);
    }

    @Test
    void deleteCustomRepoRole_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteCustomRepoRole(ORG, ROLE_ID);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE " + REPO_ROLE);
    }

    @Test
    void createCustomRole_shouldReturnRole_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("organization-custom-repository-role.json")));

        final GitHubOrganizationCustomRepositoryRole role =
                api().createCustomRole(ORG, OrganizationsCatalogFixtures.createCustomRolePayload());

        assertThat(role).isEqualTo(OrganizationsCatalogFixtures.organizationCustomRepositoryRole());
        assertThat(line(captureSingleRequest())).isEqualTo("POST " + CUSTOM_ROLES);
    }

    @Test
    void getCustomRole_shouldReturnRole_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-custom-repository-role.json")));

        final Optional<GitHubOrganizationCustomRepositoryRole> role = api().getCustomRole(ORG, ROLE_ID);

        assertThat(role).contains(OrganizationsCatalogFixtures.organizationCustomRepositoryRole());
        assertThat(line(captureSingleRequest())).isEqualTo("GET " + CUSTOM_ROLE);
    }

    @Test
    void getCustomRole_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubOrganizationCustomRepositoryRole> role = api().getCustomRole(ORG, ROLE_ID);

        assertThat(role).isEmpty();
    }

    @Test
    void updateCustomRole_shouldReturnRole_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-custom-repository-role.json")));

        final GitHubOrganizationCustomRepositoryRole role =
                api().updateCustomRole(ORG, ROLE_ID, OrganizationsCatalogFixtures.updateCustomRolePayload());

        assertThat(role).isEqualTo(OrganizationsCatalogFixtures.organizationCustomRepositoryRole());
        assertThat(line(captureSingleRequest())).isEqualTo("PATCH " + CUSTOM_ROLE);
    }

    @Test
    void deleteCustomRole_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteCustomRole(ORG, ROLE_ID);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE " + CUSTOM_ROLE);
    }

    @Test
    void listFineGrainedPermissions_shouldReturnPermissions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), "[" + json("repository-fine-grained-permission.json") + "]"));

        final List<GitHubRepositoryFineGrainedPermission> permissions = api().listFineGrainedPermissions(ORG);

        assertThat(permissions).containsExactly(OrganizationsCatalogFixtures.repositoryFineGrainedPermission());
        assertThat(line(captureSingleRequest())).isEqualTo("GET " + BASE + "/orgs/github/fine_grained_permissions");
    }

    @Test
    void listRepoFineGrainedPermissions_shouldReturnPermissions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), "[" + json("repository-fine-grained-permission.json") + "]"));

        final List<GitHubRepositoryFineGrainedPermission> permissions = api().listRepoFineGrainedPermissions(ORG);

        assertThat(permissions).containsExactly(OrganizationsCatalogFixtures.repositoryFineGrainedPermission());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("GET " + BASE + "/orgs/github/repository-fine-grained-permissions");
    }

    private GitHubOrganizationsCustomRolesApi api() {
        return new GitHubOrganizationsCustomRolesApi(TestClients.caller(transport));
    }

    private static String line(final HttpRequest request) {
        return request.method() + " " + request.uri();
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubOrganizationsCustomRolesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
