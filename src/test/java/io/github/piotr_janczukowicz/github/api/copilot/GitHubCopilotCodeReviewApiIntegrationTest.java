package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.copilotApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCopilotCodeReviewApiIntegrationTest {

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getEnterprisePolicy_shouldCallEndpoint_whenStubbed() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/copilot/policies/code-review",
                okJson(jsonBytes(
                        GitHubCopilotCodeReviewApiIntegrationTest.class,
                        "enterprise-copilot-code-review-policy.json")));

        final GitHubEnterpriseCopilotCodeReviewPolicy result;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            result = copilot.api().codeReview().getEnterprisePolicy("octo-enterprise");
        }

        assertThat(result).isEqualTo(CopilotCatalogFixtures.enterpriseCopilotCodeReviewPolicy());
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/copilot/policies/code-review");
    }

    @Test
    void setEnterprisePolicy_shouldCallEndpoint_whenStubbed() throws Exception {
        HTTP.stub(PUT, "/enterprises/octo-enterprise/copilot/policies/code-review", noContent());
        final GitHubSetEnterpriseCodeReviewPolicyPayload payload =
                CopilotCatalogFixtures.setEnterpriseCodeReviewPolicyPayload();

        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            copilot.api().codeReview().setEnterprisePolicy("octo-enterprise", payload);
        }

        HTTP.verifyRequested(PUT, "/enterprises/octo-enterprise/copilot/policies/code-review");
        HTTP.verifyBody(
                PUT,
                "/enterprises/octo-enterprise/copilot/policies/code-review",
                new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getOrganizationRunner_shouldCallEndpoint_whenStubbed() throws Exception {
        HTTP.stub(
                GET,
                "/orgs/octo-org/copilot/code-review/runner",
                okJson(jsonBytes(
                        GitHubCopilotCodeReviewApiIntegrationTest.class,
                        "organization-copilot-code-review-runner.json")));

        final GitHubOrganizationCopilotCodeReviewRunner result;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            result = copilot.api().codeReview().getOrganizationRunner("octo-org");
        }

        assertThat(result).isEqualTo(CopilotCatalogFixtures.organizationCopilotCodeReviewRunner());
        HTTP.verifyRequested(GET, "/orgs/octo-org/copilot/code-review/runner");
    }

    @Test
    void setOrganizationRunner_shouldCallEndpoint_whenStubbed() throws Exception {
        HTTP.stub(
                PUT,
                "/orgs/octo-org/copilot/code-review/runner",
                okJson(jsonBytes(
                        GitHubCopilotCodeReviewApiIntegrationTest.class,
                        "organization-copilot-code-review-runner.json")));
        final GitHubSetOrganizationCodeReviewRunnerPayload payload =
                CopilotCatalogFixtures.setOrganizationCodeReviewRunnerPayload();

        final GitHubOrganizationCopilotCodeReviewRunner result;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            result = copilot.api().codeReview().setOrganizationRunner("octo-org", payload);
        }

        assertThat(result).isEqualTo(CopilotCatalogFixtures.organizationCopilotCodeReviewRunner());
        HTTP.verifyRequested(PUT, "/orgs/octo-org/copilot/code-review/runner");
        HTTP.verifyBody(
                PUT,
                "/orgs/octo-org/copilot/code-review/runner",
                new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getOrganizationSettings_shouldCallEndpoint_whenStubbed() throws Exception {
        HTTP.stub(
                GET,
                "/orgs/octo-org/copilot/code-review/settings",
                okJson(jsonBytes(
                        GitHubCopilotCodeReviewApiIntegrationTest.class,
                        "organization-copilot-code-review-settings.json")));

        final GitHubOrganizationCopilotCodeReviewSettings result;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            result = copilot.api().codeReview().getOrganizationSettings("octo-org");
        }

        assertThat(result).isEqualTo(CopilotCatalogFixtures.organizationCopilotCodeReviewSettings());
        HTTP.verifyRequested(GET, "/orgs/octo-org/copilot/code-review/settings");
    }

    @Test
    void updateOrganizationSettings_shouldCallEndpoint_whenStubbed() throws Exception {
        HTTP.stub(
                PATCH,
                "/orgs/octo-org/copilot/code-review/settings",
                okJson(jsonBytes(
                        GitHubCopilotCodeReviewApiIntegrationTest.class,
                        "organization-copilot-code-review-settings.json")));
        final GitHubUpdateOrganizationCodeReviewSettingsPayload payload =
                CopilotCatalogFixtures.updateOrganizationCodeReviewSettingsPayload();

        final GitHubOrganizationCopilotCodeReviewSettings result;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            result = copilot.api().codeReview().updateOrganizationSettings("octo-org", payload);
        }

        assertThat(result).isEqualTo(CopilotCatalogFixtures.organizationCopilotCodeReviewSettings());
        HTTP.verifyRequested(PATCH, "/orgs/octo-org/copilot/code-review/settings");
        HTTP.verifyBody(
                PATCH,
                "/orgs/octo-org/copilot/code-review/settings",
                new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getOrganizationPolicy_shouldCallEndpoint_whenStubbed() throws Exception {
        HTTP.stub(
                GET,
                "/orgs/octo-org/copilot/policies/code-review",
                okJson(jsonBytes(
                        GitHubCopilotCodeReviewApiIntegrationTest.class,
                        "organization-copilot-code-review-policy.json")));

        final GitHubOrganizationCopilotCodeReviewPolicy result;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            result = copilot.api().codeReview().getOrganizationPolicy("octo-org");
        }

        assertThat(result).isEqualTo(CopilotCatalogFixtures.organizationCopilotCodeReviewPolicy());
        HTTP.verifyRequested(GET, "/orgs/octo-org/copilot/policies/code-review");
    }

    @Test
    void setOrganizationPolicy_shouldCallEndpoint_whenStubbed() throws Exception {
        HTTP.stub(PUT, "/orgs/octo-org/copilot/policies/code-review", noContent());
        final GitHubSetOrganizationCodeReviewPolicyPayload payload =
                CopilotCatalogFixtures.setOrganizationCodeReviewPolicyPayload();

        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            copilot.api().codeReview().setOrganizationPolicy("octo-org", payload);
        }

        HTTP.verifyRequested(PUT, "/orgs/octo-org/copilot/policies/code-review");
        HTTP.verifyBody(
                PUT,
                "/orgs/octo-org/copilot/policies/code-review",
                new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getRepositorySettings_shouldCallEndpoint_whenStubbed() throws Exception {
        HTTP.stub(
                GET,
                "/repos/octocat/hello-world/copilot/code-review/settings",
                okJson(jsonBytes(
                        GitHubCopilotCodeReviewApiIntegrationTest.class,
                        "repository-copilot-code-review-settings.json")));

        final GitHubRepositoryCopilotCodeReviewSettings result;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            result = copilot.api().codeReview().getRepositorySettings("octocat", "hello-world");
        }

        assertThat(result).isEqualTo(CopilotCatalogFixtures.repositoryCopilotCodeReviewSettings());
        HTTP.verifyRequested(GET, "/repos/octocat/hello-world/copilot/code-review/settings");
    }

    @Test
    void updateRepositorySettings_shouldCallEndpoint_whenStubbed() throws Exception {
        HTTP.stub(
                PATCH,
                "/repos/octocat/hello-world/copilot/code-review/settings",
                okJson(jsonBytes(
                        GitHubCopilotCodeReviewApiIntegrationTest.class,
                        "repository-copilot-code-review-settings.json")));
        final GitHubUpdateRepositoryCodeReviewSettingsPayload payload =
                CopilotCatalogFixtures.updateRepositoryCodeReviewSettingsPayload();

        final GitHubRepositoryCopilotCodeReviewSettings result;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            result = copilot.api().codeReview().updateRepositorySettings("octocat", "hello-world", payload);
        }

        assertThat(result).isEqualTo(CopilotCatalogFixtures.repositoryCopilotCodeReviewSettings());
        HTTP.verifyRequested(PATCH, "/repos/octocat/hello-world/copilot/code-review/settings");
        HTTP.verifyBody(
                PATCH,
                "/repos/octocat/hello-world/copilot/code-review/settings",
                new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }
}
