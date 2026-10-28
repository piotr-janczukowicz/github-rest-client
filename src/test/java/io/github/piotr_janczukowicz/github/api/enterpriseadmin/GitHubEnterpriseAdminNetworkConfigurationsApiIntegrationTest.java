package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminNetworkConfigurationsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubNetworkConfiguration;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubNetworkSettings;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminNetworkConfigurationsApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminNetworkConfigurationsApiIntegrationTest.class;
    private static final String NETWORK_CONFIGURATIONS_PATH = "/enterprises/octo-enterprise/network-configurations";
    private static final String NETWORK_CONFIGURATION_PATH = NETWORK_CONFIGURATIONS_PATH + "/1";
    private static final String NETWORK_SETTINGS_PATH = "/enterprises/octo-enterprise/network-settings/1";
    private static final String PAGE_JSON = "enterprise-network-configurations-page.json";
    private static final String CONFIGURATION_JSON = "network-configuration.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void list_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, NETWORK_CONFIGURATIONS_PATH, okJson(jsonBytes(RESOURCES, PAGE_JSON)));

        final GitHubPage<GitHubNetworkConfiguration> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminNetworkConfigurationsApiClient api =
                enterpriseAdminNetworkConfigurationsApi(HTTP)) {
            page = api.api().list(ENTERPRISE);
        }

        assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.networkConfiguration());
        HTTP.verifyRequested(GET, NETWORK_CONFIGURATIONS_PATH);
    }

    @Test
    void listWithPageRequest_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, NETWORK_CONFIGURATIONS_PATH, okJson(jsonBytes(RESOURCES, PAGE_JSON)));

        final GitHubPage<GitHubNetworkConfiguration> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminNetworkConfigurationsApiClient api =
                enterpriseAdminNetworkConfigurationsApi(HTTP)) {
            page = api.api().list(ENTERPRISE, GitHubPageRequest.byPage());
        }

        assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.networkConfiguration());
        HTTP.verifyRequested(GET, NETWORK_CONFIGURATIONS_PATH);
    }

    @Test
    void create_shouldReturnConfiguration_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, NETWORK_CONFIGURATIONS_PATH, okJson(jsonBytes(RESOURCES, CONFIGURATION_JSON)));

        final GitHubNetworkConfiguration configuration;
        try (final GitHubApiWireMockSupport.EnterpriseAdminNetworkConfigurationsApiClient api =
                enterpriseAdminNetworkConfigurationsApi(HTTP)) {
            configuration = api.api()
                    .create(
                            ENTERPRISE,
                            EnterpriseAdminCatalogFixtures.createNetworkConfigurationForEnterprisePayload());
        }

        assertThat(configuration).isEqualTo(OrganizationsCatalogFixtures.networkConfiguration());
        HTTP.verifyRequested(POST, NETWORK_CONFIGURATIONS_PATH);
    }

    @Test
    void get_shouldReturnConfiguration_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, NETWORK_CONFIGURATION_PATH, okJson(jsonBytes(RESOURCES, CONFIGURATION_JSON)));

        final Optional<GitHubNetworkConfiguration> configuration;
        try (final GitHubApiWireMockSupport.EnterpriseAdminNetworkConfigurationsApiClient api =
                enterpriseAdminNetworkConfigurationsApi(HTTP)) {
            configuration = api.api().get(ENTERPRISE, "1");
        }

        assertThat(configuration).contains(OrganizationsCatalogFixtures.networkConfiguration());
        HTTP.verifyRequested(GET, NETWORK_CONFIGURATION_PATH);
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, NETWORK_CONFIGURATION_PATH, notFound());

        final Optional<GitHubNetworkConfiguration> configuration;
        try (final GitHubApiWireMockSupport.EnterpriseAdminNetworkConfigurationsApiClient api =
                enterpriseAdminNetworkConfigurationsApi(HTTP)) {
            configuration = api.api().get(ENTERPRISE, "1");
        }

        assertThat(configuration).isEmpty();
        HTTP.verifyRequested(GET, NETWORK_CONFIGURATION_PATH);
    }

    @Test
    void update_shouldReturnConfiguration_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, NETWORK_CONFIGURATION_PATH, okJson(jsonBytes(RESOURCES, CONFIGURATION_JSON)));

        final GitHubNetworkConfiguration configuration;
        try (final GitHubApiWireMockSupport.EnterpriseAdminNetworkConfigurationsApiClient api =
                enterpriseAdminNetworkConfigurationsApi(HTTP)) {
            configuration = api.api()
                    .update(
                            ENTERPRISE,
                            "1",
                            EnterpriseAdminCatalogFixtures.updateNetworkConfigurationForEnterprisePayload());
        }

        assertThat(configuration).isEqualTo(OrganizationsCatalogFixtures.networkConfiguration());
        HTTP.verifyRequested(PATCH, NETWORK_CONFIGURATION_PATH);
    }

    @Test
    void delete_shouldSendRequest_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, NETWORK_CONFIGURATION_PATH, noContent());

        try (final GitHubApiWireMockSupport.EnterpriseAdminNetworkConfigurationsApiClient api =
                enterpriseAdminNetworkConfigurationsApi(HTTP)) {
            api.api().delete(ENTERPRISE, "1");
        }

        HTTP.verifyRequested(DELETE, NETWORK_CONFIGURATION_PATH);
    }

    @Test
    void getNetworkSettings_shouldReturnSettings_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, NETWORK_SETTINGS_PATH, okJson(jsonBytes(RESOURCES, "network-settings.json")));

        final Optional<GitHubNetworkSettings> settings;
        try (final GitHubApiWireMockSupport.EnterpriseAdminNetworkConfigurationsApiClient api =
                enterpriseAdminNetworkConfigurationsApi(HTTP)) {
            settings = api.api().getNetworkSettings(ENTERPRISE, "1");
        }

        assertThat(settings).contains(OrganizationsCatalogFixtures.networkSettings());
        HTTP.verifyRequested(GET, NETWORK_SETTINGS_PATH);
    }
}
