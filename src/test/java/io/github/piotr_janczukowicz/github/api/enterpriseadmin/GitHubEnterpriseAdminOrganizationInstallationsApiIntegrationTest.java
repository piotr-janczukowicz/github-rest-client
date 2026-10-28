package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminOrganizationInstallationsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubInstallation;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminOrganizationInstallationsApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final String ORG = "octo-org";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminOrganizationInstallationsApiIntegrationTest.class;
    private static final String INSTALLABLE_PATH = "/enterprises/octo-enterprise/apps/installable_organizations";
    private static final String INSTALLABLE_REPOSITORIES_PATH = INSTALLABLE_PATH + "/octo-org/accessible_repositories";
    private static final String INSTALLATIONS_PATH =
            "/enterprises/octo-enterprise/apps/organizations/octo-org/installations";
    private static final String INSTALLATION_PATH = INSTALLATIONS_PATH + "/1";
    private static final String REPOSITORIES_PATH = INSTALLATION_PATH + "/repositories";
    private static final String REPOSITORIES_ADD_PATH = REPOSITORIES_PATH + "/add";
    private static final String REPOSITORIES_REMOVE_PATH = REPOSITORIES_PATH + "/remove";
    private static final String INSTALLABLE_JSON = "installable-organization-list.json";
    private static final String ACCESSIBLE_JSON = "accessible-repository-list.json";
    private static final String INSTALLATIONS_JSON = "enterprise-organization-installation-list.json";
    private static final String INSTALLATION_JSON = "installation.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listInstallableOrganizations_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, INSTALLABLE_PATH, okJson(jsonBytes(RESOURCES, INSTALLABLE_JSON)));

        final GitHubPage<GitHubInstallableOrganization> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminOrganizationInstallationsApiClient api =
                enterpriseAdminOrganizationInstallationsApi(HTTP)) {
            page = api.api().listInstallableOrganizations(ENTERPRISE);
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.installableOrganization());
        HTTP.verifyRequested(GET, INSTALLABLE_PATH);
    }

    @Test
    void listInstallableOrganizationsWithRequest_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, INSTALLABLE_PATH, okJson(jsonBytes(RESOURCES, INSTALLABLE_JSON)));

        final GitHubPage<GitHubInstallableOrganization> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminOrganizationInstallationsApiClient api =
                enterpriseAdminOrganizationInstallationsApi(HTTP)) {
            page = api.api().listInstallableOrganizations(ENTERPRISE, GitHubPageRequest.byPage());
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.installableOrganization());
        HTTP.verifyRequested(GET, INSTALLABLE_PATH);
    }

    @Test
    void listInstallableOrganizationAccessibleRepositories_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, INSTALLABLE_REPOSITORIES_PATH, okJson(jsonBytes(RESOURCES, ACCESSIBLE_JSON)));

        final GitHubPage<GitHubAccessibleRepository> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminOrganizationInstallationsApiClient api =
                enterpriseAdminOrganizationInstallationsApi(HTTP)) {
            page = api.api().listInstallableOrganizationAccessibleRepositories(ENTERPRISE, ORG);
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.accessibleRepository());
        HTTP.verifyRequested(GET, INSTALLABLE_REPOSITORIES_PATH);
    }

    @Test
    void listInstallableOrganizationAccessibleRepositoriesWithRequest_shouldReturnPage_whenResponseIsOk()
            throws Exception {
        HTTP.stub(GET, INSTALLABLE_REPOSITORIES_PATH, okJson(jsonBytes(RESOURCES, ACCESSIBLE_JSON)));

        final GitHubPage<GitHubAccessibleRepository> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminOrganizationInstallationsApiClient api =
                enterpriseAdminOrganizationInstallationsApi(HTTP)) {
            page = api.api()
                    .listInstallableOrganizationAccessibleRepositories(ENTERPRISE, ORG, GitHubPageRequest.byPage());
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.accessibleRepository());
        HTTP.verifyRequested(GET, INSTALLABLE_REPOSITORIES_PATH);
    }

    @Test
    void listOrganizationInstallations_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, INSTALLATIONS_PATH, okJson(jsonBytes(RESOURCES, INSTALLATIONS_JSON)));

        final GitHubPage<GitHubEnterpriseOrganizationInstallation> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminOrganizationInstallationsApiClient api =
                enterpriseAdminOrganizationInstallationsApi(HTTP)) {
            page = api.api().listOrganizationInstallations(ENTERPRISE, ORG);
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.enterpriseOrganizationInstallation());
        HTTP.verifyRequested(GET, INSTALLATIONS_PATH);
    }

    @Test
    void listOrganizationInstallationsWithRequest_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, INSTALLATIONS_PATH, okJson(jsonBytes(RESOURCES, INSTALLATIONS_JSON)));

        final GitHubPage<GitHubEnterpriseOrganizationInstallation> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminOrganizationInstallationsApiClient api =
                enterpriseAdminOrganizationInstallationsApi(HTTP)) {
            page = api.api().listOrganizationInstallations(ENTERPRISE, ORG, GitHubPageRequest.byPage());
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.enterpriseOrganizationInstallation());
        HTTP.verifyRequested(GET, INSTALLATIONS_PATH);
    }

    @Test
    void createInstallation_shouldReturnInstallation_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, INSTALLATIONS_PATH, okJson(jsonBytes(RESOURCES, INSTALLATION_JSON)));

        final GitHubInstallation installation;
        try (final GitHubApiWireMockSupport.EnterpriseAdminOrganizationInstallationsApiClient api =
                enterpriseAdminOrganizationInstallationsApi(HTTP)) {
            installation = api.api()
                    .createInstallation(ENTERPRISE, ORG, EnterpriseAdminCatalogFixtures.createInstallationPayload());
        }

        assertThat(installation).isEqualTo(OrganizationsCatalogFixtures.installation());
        HTTP.verifyRequested(POST, INSTALLATIONS_PATH);
    }

    @Test
    void deleteInstallation_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, INSTALLATION_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminOrganizationInstallationsApiClient api =
                enterpriseAdminOrganizationInstallationsApi(HTTP)) {
            api.api().deleteInstallation(ENTERPRISE, ORG, 1L);
        }

        HTTP.verifyRequested(DELETE, INSTALLATION_PATH);
    }

    @Test
    void listInstallationRepositories_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, REPOSITORIES_PATH, okJson(jsonBytes(RESOURCES, ACCESSIBLE_JSON)));

        final GitHubPage<GitHubAccessibleRepository> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminOrganizationInstallationsApiClient api =
                enterpriseAdminOrganizationInstallationsApi(HTTP)) {
            page = api.api().listInstallationRepositories(ENTERPRISE, ORG, 1L);
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.accessibleRepository());
        HTTP.verifyRequested(GET, REPOSITORIES_PATH);
    }

    @Test
    void listInstallationRepositoriesWithRequest_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, REPOSITORIES_PATH, okJson(jsonBytes(RESOURCES, ACCESSIBLE_JSON)));

        final GitHubPage<GitHubAccessibleRepository> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminOrganizationInstallationsApiClient api =
                enterpriseAdminOrganizationInstallationsApi(HTTP)) {
            page = api.api().listInstallationRepositories(ENTERPRISE, ORG, 1L, GitHubPageRequest.byPage());
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.accessibleRepository());
        HTTP.verifyRequested(GET, REPOSITORIES_PATH);
    }

    @Test
    void changeInstallationRepositoryAccessSelection_shouldReturnInstallation_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, REPOSITORIES_PATH, okJson(jsonBytes(RESOURCES, INSTALLATION_JSON)));

        final GitHubInstallation installation;
        try (final GitHubApiWireMockSupport.EnterpriseAdminOrganizationInstallationsApiClient api =
                enterpriseAdminOrganizationInstallationsApi(HTTP)) {
            installation = api.api()
                    .changeInstallationRepositoryAccessSelection(
                            ENTERPRISE,
                            ORG,
                            1L,
                            EnterpriseAdminCatalogFixtures.changeRepositoryAccessSelectionPayload());
        }

        assertThat(installation).isEqualTo(OrganizationsCatalogFixtures.installation());
        HTTP.verifyRequested(PATCH, REPOSITORIES_PATH);
    }

    @Test
    void grantRepositoryAccessToInstallation_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, REPOSITORIES_ADD_PATH, okJson(jsonBytes(RESOURCES, ACCESSIBLE_JSON)));

        final List<GitHubAccessibleRepository> repositories;
        try (final GitHubApiWireMockSupport.EnterpriseAdminOrganizationInstallationsApiClient api =
                enterpriseAdminOrganizationInstallationsApi(HTTP)) {
            repositories = api.api()
                    .grantRepositoryAccessToInstallation(
                            ENTERPRISE, ORG, 1L, EnterpriseAdminCatalogFixtures.grantRepositoryAccessPayload());
        }

        assertThat(repositories).containsExactly(EnterpriseAdminCatalogFixtures.accessibleRepository());
        HTTP.verifyRequested(PATCH, REPOSITORIES_ADD_PATH);
    }

    @Test
    void removeRepositoryAccessFromInstallation_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, REPOSITORIES_REMOVE_PATH, okJson(jsonBytes(RESOURCES, ACCESSIBLE_JSON)));

        final List<GitHubAccessibleRepository> repositories;
        try (final GitHubApiWireMockSupport.EnterpriseAdminOrganizationInstallationsApiClient api =
                enterpriseAdminOrganizationInstallationsApi(HTTP)) {
            repositories = api.api()
                    .removeRepositoryAccessFromInstallation(
                            ENTERPRISE, ORG, 1L, EnterpriseAdminCatalogFixtures.removeRepositoryAccessPayload());
        }

        assertThat(repositories).containsExactly(EnterpriseAdminCatalogFixtures.accessibleRepository());
        HTTP.verifyRequested(PATCH, REPOSITORIES_REMOVE_PATH);
    }
}
