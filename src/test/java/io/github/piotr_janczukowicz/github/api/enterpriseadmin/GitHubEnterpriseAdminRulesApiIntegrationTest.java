package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminRulesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleset;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRulesetVersion;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRulesetVersionWithState;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminRulesApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminRulesApiIntegrationTest.class;
    private static final String RULESETS_PATH = "/enterprises/octo-enterprise/rulesets";
    private static final String RULESET_PATH = RULESETS_PATH + "/1";
    private static final String HISTORY_PATH = RULESET_PATH + "/history";
    private static final String VERSION_PATH = HISTORY_PATH + "/2";
    private static final String RULESET_JSON = "repository-ruleset.json";
    private static final String HISTORY_JSON = "ruleset-version-list.json";
    private static final String VERSION_JSON = "ruleset-version-with-state.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void createEnterpriseRuleset_shouldReturnRuleset_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, RULESETS_PATH, okJson(jsonBytes(RESOURCES, RULESET_JSON)));

        final GitHubRepositoryRuleset ruleset;
        try (final GitHubApiWireMockSupport.EnterpriseAdminRulesApiClient api = enterpriseAdminRulesApi(HTTP)) {
            ruleset = api.api()
                    .createEnterpriseRuleset(
                            ENTERPRISE, EnterpriseAdminCatalogFixtures.createEnterpriseRulesetPayload());
        }

        assertThat(ruleset).isEqualTo(RepositoriesCatalogFixtures.repositoryRuleset());
        HTTP.verifyRequested(POST, RULESETS_PATH);
    }

    @Test
    void getEnterpriseRuleset_shouldReturnRuleset_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, RULESET_PATH, okJson(jsonBytes(RESOURCES, RULESET_JSON)));

        final Optional<GitHubRepositoryRuleset> ruleset;
        try (final GitHubApiWireMockSupport.EnterpriseAdminRulesApiClient api = enterpriseAdminRulesApi(HTTP)) {
            ruleset = api.api().getEnterpriseRuleset(ENTERPRISE, 1L);
        }

        assertThat(ruleset).contains(RepositoriesCatalogFixtures.repositoryRuleset());
        HTTP.verifyRequested(GET, RULESET_PATH);
    }

    @Test
    void getEnterpriseRuleset_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, RULESET_PATH, notFound());

        final Optional<GitHubRepositoryRuleset> ruleset;
        try (final GitHubApiWireMockSupport.EnterpriseAdminRulesApiClient api = enterpriseAdminRulesApi(HTTP)) {
            ruleset = api.api().getEnterpriseRuleset(ENTERPRISE, 1L);
        }

        assertThat(ruleset).isEmpty();
    }

    @Test
    void updateEnterpriseRuleset_shouldReturnRuleset_whenResponseIsOk() throws Exception {
        HTTP.stub(PUT, RULESET_PATH, okJson(jsonBytes(RESOURCES, RULESET_JSON)));

        final GitHubRepositoryRuleset ruleset;
        try (final GitHubApiWireMockSupport.EnterpriseAdminRulesApiClient api = enterpriseAdminRulesApi(HTTP)) {
            ruleset = api.api()
                    .updateEnterpriseRuleset(
                            ENTERPRISE, 1L, EnterpriseAdminCatalogFixtures.updateEnterpriseRulesetPayload());
        }

        assertThat(ruleset).isEqualTo(RepositoriesCatalogFixtures.repositoryRuleset());
        HTTP.verifyRequested(PUT, RULESET_PATH);
    }

    @Test
    void deleteEnterpriseRuleset_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, RULESET_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminRulesApiClient api = enterpriseAdminRulesApi(HTTP)) {
            api.api().deleteEnterpriseRuleset(ENTERPRISE, 1L);
        }

        HTTP.verifyRequested(DELETE, RULESET_PATH);
    }

    @Test
    void getEnterpriseRulesetHistory_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, HISTORY_PATH, okJson(jsonBytes(RESOURCES, HISTORY_JSON)));

        final GitHubPage<GitHubRulesetVersion> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminRulesApiClient api = enterpriseAdminRulesApi(HTTP)) {
            page = api.api().getEnterpriseRulesetHistory(ENTERPRISE, 1L);
        }

        assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.rulesetVersion());
        HTTP.verifyRequested(GET, HISTORY_PATH);
    }

    @Test
    void getEnterpriseRulesetHistoryWithRequest_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, HISTORY_PATH, okJson(jsonBytes(RESOURCES, HISTORY_JSON)));

        final GitHubPage<GitHubRulesetVersion> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminRulesApiClient api = enterpriseAdminRulesApi(HTTP)) {
            page = api.api().getEnterpriseRulesetHistory(ENTERPRISE, 1L, GitHubPageRequest.byPage());
        }

        assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.rulesetVersion());
        HTTP.verifyRequested(GET, HISTORY_PATH);
    }

    @Test
    void getEnterpriseRulesetVersion_shouldReturnVersion_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, VERSION_PATH, okJson(jsonBytes(RESOURCES, VERSION_JSON)));

        final Optional<GitHubRulesetVersionWithState> version;
        try (final GitHubApiWireMockSupport.EnterpriseAdminRulesApiClient api = enterpriseAdminRulesApi(HTTP)) {
            version = api.api().getEnterpriseRulesetVersion(ENTERPRISE, 1L, 2L);
        }

        assertThat(version).contains(RepositoriesCatalogFixtures.rulesetVersionWithState());
        HTTP.verifyRequested(GET, VERSION_PATH);
    }
}
