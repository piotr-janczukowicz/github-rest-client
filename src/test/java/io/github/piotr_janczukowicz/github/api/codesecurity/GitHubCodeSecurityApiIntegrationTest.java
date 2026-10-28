package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.accepted;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.codeSecurityApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCodeSecurityApiIntegrationTest {

    private static final String ORG = "octo-org";
    private static final String COLLECTION_PATH = "/orgs/octo-org/code-security/configurations";
    private static final String ITEM_PATH = "/orgs/octo-org/code-security/configurations/1";
    private static final String REPO_PATH = "/repos/octocat/Hello-World/code-security-configuration";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listConfigurationsForOrg_shouldSendFilterAndCursor_whenCalled() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(listJsonBytes(GitHubCodeSecurityApiIntegrationTest.class, "code-security-configuration.json")));

        final GitHubPage<GitHubCodeSecurityConfiguration> page;
        try (final GitHubApiWireMockSupport.CodeSecurityApiClient api = codeSecurityApi(HTTP)) {
            page = api.api()
                    .listConfigurationsForOrg(
                            ORG,
                            CodeSecurityCatalogFixtures.getConfigurationsForOrgFilter(),
                            GitHubCursorRequest.after("tok", 50));
        }

        assertThat(page.items()).containsExactly(CodeSecurityCatalogFixtures.codeSecurityConfiguration());
        HTTP.verifyRequested(GET, COLLECTION_PATH);
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "target_type", "global");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "after", "tok");
    }

    @Test
    void getConfiguration_shouldReturnEmpty_whenNotFound() throws Exception {
        HTTP.stub(GET, ITEM_PATH, notFound());

        final Optional<GitHubCodeSecurityConfiguration> configuration;
        try (final GitHubApiWireMockSupport.CodeSecurityApiClient api = codeSecurityApi(HTTP)) {
            configuration = api.api().getConfiguration(ORG, 1);
        }

        assertThat(configuration).isEmpty();
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void createConfiguration_shouldReturnConfiguration_whenCreated() throws Exception {
        HTTP.stub(
                POST,
                COLLECTION_PATH,
                okJson(jsonBytes(GitHubCodeSecurityApiIntegrationTest.class, "code-security-configuration.json")));

        final GitHubCodeSecurityConfiguration configuration;
        try (final GitHubApiWireMockSupport.CodeSecurityApiClient api = codeSecurityApi(HTTP)) {
            configuration =
                    api.api().createConfiguration(ORG, CodeSecurityCatalogFixtures.createConfigurationPayload());
        }

        assertThat(configuration).isEqualTo(CodeSecurityCatalogFixtures.codeSecurityConfiguration());
        HTTP.verifyRequested(POST, COLLECTION_PATH);
    }

    @Test
    void deleteConfiguration_shouldSendDelete_whenNoContent() throws Exception {
        HTTP.stub(DELETE, ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.CodeSecurityApiClient api = codeSecurityApi(HTTP)) {
            api.api().deleteConfiguration(ORG, 1);
        }

        HTTP.verifyRequested(DELETE, ITEM_PATH);
    }

    @Test
    void attachConfiguration_shouldSendPost_whenAccepted() throws Exception {
        HTTP.stub(POST, ITEM_PATH + "/attach", accepted());

        try (final GitHubApiWireMockSupport.CodeSecurityApiClient api = codeSecurityApi(HTTP)) {
            api.api().attachConfiguration(ORG, 1, CodeSecurityCatalogFixtures.attachConfigurationPayload());
        }

        HTTP.verifyRequested(POST, ITEM_PATH + "/attach");
    }

    @Test
    void getDefaultConfigurations_shouldReturnDefaults_whenOk() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH + "/defaults",
                okJson(listJsonBytes(
                        GitHubCodeSecurityApiIntegrationTest.class, "code-security-default-configuration.json")));

        final List<GitHubCodeSecurityDefaultConfiguration> defaults;
        try (final GitHubApiWireMockSupport.CodeSecurityApiClient api = codeSecurityApi(HTTP)) {
            defaults = api.api().getDefaultConfigurations(ORG);
        }

        assertThat(defaults).containsExactly(CodeSecurityCatalogFixtures.codeSecurityDefaultConfiguration());
    }

    @Test
    void getConfigurationForRepository_shouldReturnEmpty_whenNoContent() throws Exception {
        HTTP.stub(GET, REPO_PATH, noContent());

        final Optional<GitHubCodeSecurityConfigurationForRepository> attachment;
        try (final GitHubApiWireMockSupport.CodeSecurityApiClient api = codeSecurityApi(HTTP)) {
            attachment = api.api().getConfigurationForRepository("octocat", "Hello-World");
        }

        assertThat(attachment).isEmpty();
        HTTP.verifyRequested(GET, REPO_PATH);
    }
}
