package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseTeamOrganizationsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseTeamsEnterpriseTeamOrganizationsApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseTeamsEnterpriseTeamOrganizationsApiIntegrationTest.class;
    private static final String TEAM = "justice-league";
    private static final String ORG = "github";
    private static final String ORGANIZATIONS_PATH = "/enterprises/octo-enterprise/teams/justice-league/organizations";
    private static final String ORGANIZATIONS_ADD_PATH = ORGANIZATIONS_PATH + "/add";
    private static final String ORGANIZATIONS_REMOVE_PATH = ORGANIZATIONS_PATH + "/remove";
    private static final String ORGANIZATION_PATH = ORGANIZATIONS_PATH + "/github";
    private static final String ORGANIZATION_JSON = "organization-simple.json";
    private static final String ORGANIZATION_LIST_JSON = "organization-simple-list.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listAssignments_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ORGANIZATIONS_PATH, okJson(jsonBytes(RESOURCES, ORGANIZATION_LIST_JSON)));

        final GitHubPage<GitHubOrganizationSimple> page;
        try (final GitHubApiWireMockSupport.EnterpriseTeamOrganizationsApiClient api =
                enterpriseTeamOrganizationsApi(HTTP)) {
            page = api.api().listAssignments(ENTERPRISE, TEAM);
        }

        assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.organizationSimple());
        HTTP.verifyRequested(GET, ORGANIZATIONS_PATH);
    }

    @Test
    void bulkAddAssignments_shouldReturnOrganizations_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, ORGANIZATIONS_ADD_PATH, okJson(jsonBytes(RESOURCES, ORGANIZATION_LIST_JSON)));

        final List<GitHubOrganizationSimple> organizations;
        try (final GitHubApiWireMockSupport.EnterpriseTeamOrganizationsApiClient api =
                enterpriseTeamOrganizationsApi(HTTP)) {
            organizations = api.api()
                    .bulkAddAssignments(
                            ENTERPRISE,
                            TEAM,
                            EnterpriseTeamsCatalogFixtures.bulkAddEnterpriseTeamOrganizationsPayload());
        }

        assertThat(organizations).containsExactly(OrganizationsCatalogFixtures.organizationSimple());
        HTTP.verifyRequested(POST, ORGANIZATIONS_ADD_PATH);
    }

    @Test
    void bulkRemoveAssignments_shouldSendPost_whenResponseIsNoContent() throws Exception {
        HTTP.stub(POST, ORGANIZATIONS_REMOVE_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseTeamOrganizationsApiClient api =
                enterpriseTeamOrganizationsApi(HTTP)) {
            api.api()
                    .bulkRemoveAssignments(
                            ENTERPRISE,
                            TEAM,
                            EnterpriseTeamsCatalogFixtures.bulkRemoveEnterpriseTeamOrganizationsPayload());
        }

        HTTP.verifyRequested(POST, ORGANIZATIONS_REMOVE_PATH);
    }

    @Test
    void getAssignment_shouldReturnOrganization_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ORGANIZATION_PATH, okJson(jsonBytes(RESOURCES, ORGANIZATION_JSON)));

        final Optional<GitHubOrganizationSimple> organization;
        try (final GitHubApiWireMockSupport.EnterpriseTeamOrganizationsApiClient api =
                enterpriseTeamOrganizationsApi(HTTP)) {
            organization = api.api().getAssignment(ENTERPRISE, TEAM, ORG);
        }

        assertThat(organization).contains(OrganizationsCatalogFixtures.organizationSimple());
        HTTP.verifyRequested(GET, ORGANIZATION_PATH);
    }

    @Test
    void getAssignment_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORGANIZATION_PATH, notFound());

        final Optional<GitHubOrganizationSimple> organization;
        try (final GitHubApiWireMockSupport.EnterpriseTeamOrganizationsApiClient api =
                enterpriseTeamOrganizationsApi(HTTP)) {
            organization = api.api().getAssignment(ENTERPRISE, TEAM, ORG);
        }

        assertThat(organization).isEmpty();
    }

    @Test
    void addAssignment_shouldReturnOrganization_whenResponseIsCreated() throws Exception {
        HTTP.stub(PUT, ORGANIZATION_PATH, createdJson(jsonBytes(RESOURCES, ORGANIZATION_JSON)));

        final GitHubOrganizationSimple organization;
        try (final GitHubApiWireMockSupport.EnterpriseTeamOrganizationsApiClient api =
                enterpriseTeamOrganizationsApi(HTTP)) {
            organization = api.api().addAssignment(ENTERPRISE, TEAM, ORG);
        }

        assertThat(organization).isEqualTo(OrganizationsCatalogFixtures.organizationSimple());
        HTTP.verifyRequested(PUT, ORGANIZATION_PATH);
    }

    @Test
    void removeAssignment_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORGANIZATION_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseTeamOrganizationsApiClient api =
                enterpriseTeamOrganizationsApi(HTTP)) {
            api.api().removeAssignment(ENTERPRISE, TEAM, ORG);
        }

        HTTP.verifyRequested(DELETE, ORGANIZATION_PATH);
    }
}
