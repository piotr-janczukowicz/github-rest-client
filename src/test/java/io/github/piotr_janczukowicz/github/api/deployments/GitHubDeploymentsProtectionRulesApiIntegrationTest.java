package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.deploymentsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubDeploymentsProtectionRulesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ENVIRONMENT = "staging";
    private static final long PROTECTION_RULE_ID = 3L;

    private static final String PROTECTION_RULES_PATH =
            "/repos/octocat/Hello-World/environments/staging/deployment_protection_rules";
    private static final String PROTECTION_RULE_PATH =
            "/repos/octocat/Hello-World/environments/staging/deployment_protection_rules/3";
    private static final String PROTECTION_APPS_PATH =
            "/repos/octocat/Hello-World/environments/staging/deployment_protection_rules/apps";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getAllDeploymentProtectionRules_shouldReturnRules_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PROTECTION_RULES_PATH,
                okJson(wrappedPage("custom_deployment_protection_rules", "deployment-protection-rule.json")));

        final GitHubPage<GitHubDeploymentProtectionRule> page;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            page = api.api().protectionRules().getAllDeploymentProtectionRules(OWNER, REPO, ENVIRONMENT);
        }

        assertThat(page.items()).isEqualTo(expectedProtectionRulesPage().customDeploymentProtectionRules());
        assertThat(page.totalCount()).isEqualTo(1);
        HTTP.verifyRequested(GET, PROTECTION_RULES_PATH);
        HTTP.verifyNoQueryParam(GET, PROTECTION_RULES_PATH, "page");
        HTTP.verifyNoQueryParam(GET, PROTECTION_RULES_PATH, "per_page");
    }

    @Test
    void getCustomDeploymentProtectionRule_shouldReturnRule_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PROTECTION_RULE_PATH,
                okJson(jsonBytes(
                        GitHubDeploymentsProtectionRulesApiIntegrationTest.class, "deployment-protection-rule.json")));

        final Optional<GitHubDeploymentProtectionRule> rule;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            rule = api.api()
                    .protectionRules()
                    .getCustomDeploymentProtectionRule(OWNER, REPO, ENVIRONMENT, PROTECTION_RULE_ID);
        }

        assertThat(rule).contains(expectedProtectionRule());
        HTTP.verifyRequested(GET, PROTECTION_RULE_PATH);
    }

    @Test
    void getCustomDeploymentProtectionRule_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, PROTECTION_RULE_PATH, notFound());

        final Optional<GitHubDeploymentProtectionRule> rule;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            rule = api.api()
                    .protectionRules()
                    .getCustomDeploymentProtectionRule(OWNER, REPO, ENVIRONMENT, PROTECTION_RULE_ID);
        }

        assertThat(rule).isEmpty();
        HTTP.verifyRequested(GET, PROTECTION_RULE_PATH);
    }

    @Test
    void createDeploymentProtectionRule_shouldReturnRule_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                PROTECTION_RULES_PATH,
                createdJson(jsonBytes(
                        GitHubDeploymentsProtectionRulesApiIntegrationTest.class, "deployment-protection-rule.json")));
        final GitHubCreateDeploymentProtectionRulePayload payload =
                DeploymentsCatalogFixtures.createDeploymentProtectionRulePayload();

        final GitHubDeploymentProtectionRule rule;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            rule = api.api().protectionRules().createDeploymentProtectionRule(OWNER, REPO, ENVIRONMENT, payload);
        }

        assertThat(rule).isEqualTo(expectedProtectionRule());
        HTTP.verifyRequested(POST, PROTECTION_RULES_PATH);
        HTTP.verifyHeader(POST, PROTECTION_RULES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, PROTECTION_RULES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void disableDeploymentProtectionRule_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, PROTECTION_RULE_PATH, noContent());

        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            api.api().protectionRules().disableDeploymentProtectionRule(OWNER, REPO, ENVIRONMENT, PROTECTION_RULE_ID);
        }

        HTTP.verifyRequested(DELETE, PROTECTION_RULE_PATH);
    }

    @Test
    void listCustomDeploymentRuleIntegrations_shouldReturnIntegrations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PROTECTION_APPS_PATH,
                okJson(wrappedPage(
                        "available_custom_deployment_protection_rule_integrations",
                        "custom-deployment-rule-app.json")));

        final GitHubPage<GitHubCustomDeploymentRuleApp> page;
        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            page = api.api().protectionRules().listCustomDeploymentRuleIntegrations(OWNER, REPO, ENVIRONMENT);
        }

        assertThat(page.items()).isEqualTo(expectedIntegrationsPage().availableCustomDeploymentRuleIntegrations());
        assertThat(page.totalCount()).isEqualTo(1);
        HTTP.verifyRequested(GET, PROTECTION_APPS_PATH);
        HTTP.verifyQueryParam(GET, PROTECTION_APPS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, PROTECTION_APPS_PATH, "per_page", "100");
    }

    @Test
    void listCustomDeploymentRuleIntegrations_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest()
            throws Exception {
        HTTP.stub(
                GET,
                PROTECTION_APPS_PATH,
                okJson(wrappedPage(
                        "available_custom_deployment_protection_rule_integrations",
                        "custom-deployment-rule-app.json")));

        try (final GitHubApiWireMockSupport.DeploymentsApiClient api = deploymentsApi(HTTP)) {
            api.api()
                    .protectionRules()
                    .listCustomDeploymentRuleIntegrations(OWNER, REPO, ENVIRONMENT, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, PROTECTION_APPS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, PROTECTION_APPS_PATH, "per_page", "10");
    }

    private static byte[] wrappedPage(final String arrayField, final String fixture) {
        final String item = new String(
                jsonBytes(GitHubDeploymentsProtectionRulesApiIntegrationTest.class, fixture), StandardCharsets.UTF_8);
        return ("{\"total_count\":1,\"" + arrayField + "\":[" + item + "]}").getBytes(StandardCharsets.UTF_8);
    }

    private static GitHubDeploymentProtectionRulesPage expectedProtectionRulesPage() {
        return CODEC.decode(
                wrappedPage("custom_deployment_protection_rules", "deployment-protection-rule.json"),
                GitHubDeploymentProtectionRulesPage.class);
    }

    private static GitHubDeploymentProtectionRule expectedProtectionRule() {
        return CODEC.decode(
                jsonBytes(GitHubDeploymentsProtectionRulesApiIntegrationTest.class, "deployment-protection-rule.json"),
                GitHubDeploymentProtectionRule.class);
    }

    private static GitHubCustomDeploymentRuleIntegrationsPage expectedIntegrationsPage() {
        return CODEC.decode(
                wrappedPage(
                        "available_custom_deployment_protection_rule_integrations", "custom-deployment-rule-app.json"),
                GitHubCustomDeploymentRuleIntegrationsPage.class);
    }
}
