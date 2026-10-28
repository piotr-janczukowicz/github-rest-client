package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubDeploymentsProtectionRulesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ENVIRONMENT = "staging";
    private static final long PROTECTION_RULE_ID = 3L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDeploymentsProtectionRulesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getAllDeploymentProtectionRules_shouldReturnRules_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(),
                        "{\"total_count\":1,\"custom_deployment_protection_rules\":["
                                + json("deployment-protection-rule.json") + "]}"));

        final GitHubPage<GitHubDeploymentProtectionRule> page =
                api().getAllDeploymentProtectionRules(OWNER, REPO, ENVIRONMENT);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(DeploymentsCatalogFixtures.deploymentProtectionRule());
            softly.assertThat(page.totalCount()).isEqualTo(1);
        });
    }

    @Test
    void getCustomDeploymentProtectionRule_shouldReturnRule_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("deployment-protection-rule.json")));

        final Optional<GitHubDeploymentProtectionRule> rule =
                api().getCustomDeploymentProtectionRule(OWNER, REPO, ENVIRONMENT, PROTECTION_RULE_ID);

        assertThat(rule).contains(DeploymentsCatalogFixtures.deploymentProtectionRule());
    }

    @Test
    void createDeploymentProtectionRule_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("deployment-protection-rule.json")));
        final GitHubDeploymentProtectionRule rule = api().createDeploymentProtectionRule(
                        OWNER, REPO, ENVIRONMENT, DeploymentsCatalogFixtures.createDeploymentProtectionRulePayload());

        assertThat(rule).isEqualTo(DeploymentsCatalogFixtures.deploymentProtectionRule());
    }

    @Test
    void disableDeploymentProtectionRule_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().disableDeploymentProtectionRule(OWNER, REPO, ENVIRONMENT, PROTECTION_RULE_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void listCustomDeploymentRuleIntegrations_shouldReturnIntegrations_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(),
                        "{\"total_count\":1,\"available_custom_deployment_protection_rule_integrations\":["
                                + json("custom-deployment-rule-app.json") + "]}"));

        final GitHubPage<GitHubCustomDeploymentRuleApp> page =
                api().listCustomDeploymentRuleIntegrations(OWNER, REPO, ENVIRONMENT);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(DeploymentsCatalogFixtures.customDeploymentRuleApp());
            softly.assertThat(page.totalCount()).isEqualTo(1);
        });
    }

    private GitHubDeploymentsProtectionRulesApi api() {
        return new GitHubDeploymentsProtectionRulesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubDeploymentsProtectionRulesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
