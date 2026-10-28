package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.copilotApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCopilotCopilotCodingAgentManagementApiIntegrationTest {

    private static final String ENTERPRISE_POLICY = "/enterprises/octo-enterprise/copilot/policies/coding_agent";
    private static final String ENTERPRISE_POLICY_ORGS = ENTERPRISE_POLICY + "/organizations";
    private static final String ORG_PERMISSIONS = "/orgs/octo-org/copilot/coding-agent/permissions";
    private static final String ORG_REPOSITORIES = ORG_PERMISSIONS + "/repositories";
    private static final String ORG_REPOSITORY = ORG_REPOSITORIES + "/42";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void setEnterprisePolicy_shouldSendPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ENTERPRISE_POLICY, noContent());
        final GitHubSetEnterpriseCodingAgentPolicyPayload payload =
                CopilotCatalogFixtures.setEnterpriseCodingAgentPolicyPayload();

        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            copilot.api().copilotCodingAgentManagement().setEnterprisePolicy("octo-enterprise", payload);
        }

        HTTP.verifyRequested(PUT, ENTERPRISE_POLICY);
        HTTP.verifyHeader(PUT, ENTERPRISE_POLICY, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, ENTERPRISE_POLICY, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void addOrganizationsToEnterprisePolicy_shouldSendPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(POST, ENTERPRISE_POLICY_ORGS, noContent());
        final GitHubAddOrganizationsToEnterpriseCodingAgentPolicyPayload payload =
                CopilotCatalogFixtures.addOrganizationsToEnterpriseCodingAgentPolicyPayload();

        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            copilot.api().copilotCodingAgentManagement().addOrganizationsToEnterprisePolicy("octo-enterprise", payload);
        }

        HTTP.verifyRequested(POST, ENTERPRISE_POLICY_ORGS);
        HTTP.verifyBody(POST, ENTERPRISE_POLICY_ORGS, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void removeOrganizationsFromEnterprisePolicy_shouldSendPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ENTERPRISE_POLICY_ORGS, noContent());
        final GitHubRemoveOrganizationsFromEnterpriseCodingAgentPolicyPayload payload =
                CopilotCatalogFixtures.removeOrganizationsFromEnterpriseCodingAgentPolicyPayload();

        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            copilot.api()
                    .copilotCodingAgentManagement()
                    .removeOrganizationsFromEnterprisePolicy("octo-enterprise", payload);
        }

        HTTP.verifyRequested(DELETE, ENTERPRISE_POLICY_ORGS);
        HTTP.verifyBody(DELETE, ENTERPRISE_POLICY_ORGS, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getOrganizationPermissions_shouldReturnPermissions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_PERMISSIONS,
                okJson(jsonBytes(
                        GitHubCopilotCopilotCodingAgentManagementApiIntegrationTest.class,
                        "get-copilot-coding-agent-permissions-organization-response.json")));

        final GitHubGetCopilotCodingAgentPermissionsOrganizationResponse permissions;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            permissions = copilot.api().copilotCodingAgentManagement().getOrganizationPermissions("octo-org");
        }

        assertThat(permissions)
                .isEqualTo(CopilotCatalogFixtures.getCopilotCodingAgentPermissionsOrganizationResponse());
        HTTP.verifyRequested(GET, ORG_PERMISSIONS);
    }

    @Test
    void setOrganizationPermissions_shouldSendPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ORG_PERMISSIONS, noContent());
        final GitHubSetCopilotCodingAgentPermissionsOrganizationPayload payload =
                CopilotCatalogFixtures.setCopilotCodingAgentPermissionsOrganizationPayload();

        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            copilot.api().copilotCodingAgentManagement().setOrganizationPermissions("octo-org", payload);
        }

        HTTP.verifyRequested(PUT, ORG_PERMISSIONS);
        HTTP.verifyBody(PUT, ORG_PERMISSIONS, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void listOrganizationSelectedRepositories_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_REPOSITORIES,
                okJson(jsonBytes(
                        GitHubCopilotCopilotCodingAgentManagementApiIntegrationTest.class,
                        "list-copilot-coding-agent-selected-repositories-for-organization-response.json")));

        final GitHubPage<GitHubMinimalRepository> page;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            page = copilot.api().copilotCodingAgentManagement().listOrganizationSelectedRepositories("octo-org");
        }

        final GitHubListCopilotCodingAgentSelectedRepositoriesForOrganizationResponse expected =
                CopilotCatalogFixtures.listCopilotCodingAgentSelectedRepositoriesForOrganizationResponse();
        assertThat(page.items()).isEqualTo(expected.repositories());
        assertThat(page.totalCount()).isEqualTo(expected.totalCount());
        HTTP.verifyRequested(GET, ORG_REPOSITORIES);
    }

    @Test
    void setOrganizationSelectedRepositories_shouldSendPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ORG_REPOSITORIES, noContent());
        final GitHubSetCopilotCodingAgentSelectedRepositoriesForOrganizationPayload payload =
                CopilotCatalogFixtures.setCopilotCodingAgentSelectedRepositoriesForOrganizationPayload();

        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            copilot.api().copilotCodingAgentManagement().setOrganizationSelectedRepositories("octo-org", payload);
        }

        HTTP.verifyRequested(PUT, ORG_REPOSITORIES);
        HTTP.verifyBody(PUT, ORG_REPOSITORIES, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void enableRepositoryForOrganization_shouldPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ORG_REPOSITORY, noContent());

        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            copilot.api().copilotCodingAgentManagement().enableRepositoryForOrganization("octo-org", 42L);
        }

        HTTP.verifyRequested(PUT, ORG_REPOSITORY);
    }

    @Test
    void disableRepositoryForOrganization_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORG_REPOSITORY, noContent());

        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            copilot.api().copilotCodingAgentManagement().disableRepositoryForOrganization("octo-org", 42L);
        }

        HTTP.verifyRequested(DELETE, ORG_REPOSITORY);
    }
}
