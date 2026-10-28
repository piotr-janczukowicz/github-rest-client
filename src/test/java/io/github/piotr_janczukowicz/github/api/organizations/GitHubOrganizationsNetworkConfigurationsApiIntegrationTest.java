package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.organizationsApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubOrganizationsNetworkConfigurationsApiIntegrationTest {

    private static final String ORG = "github";
    private static final String NETWORK_CONFIGURATION_ID = "123456789ABCDEF";
    private static final String NETWORK_SETTINGS_ID = "220F78DACB92BBFBC5E6F22DE1CCF52309D";
    private static final String NETWORK_CONFIGURATIONS_PATH = "/orgs/github/settings/network-configurations";
    private static final String NETWORK_CONFIGURATION_PATH =
            "/orgs/github/settings/network-configurations/123456789ABCDEF";
    private static final String NETWORK_SETTINGS_PATH =
            "/orgs/github/settings/network-settings/220F78DACB92BBFBC5E6F22DE1CCF52309D";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void list_shouldReturnNetworkConfigurationsPage_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                NETWORK_CONFIGURATIONS_PATH,
                okJson(jsonBytes(
                        GitHubOrganizationsNetworkConfigurationsApiIntegrationTest.class,
                        "network-configurations-page.json")));

        final GitHubPage<GitHubNetworkConfiguration> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().networkConfigurations().list(ORG);
        }

        final GitHubNetworkConfigurationsPage expected = expectedPage();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactlyElementsOf(expected.networkConfigurations());
            softly.assertThat(page.totalCount()).isEqualTo(expected.totalCount());
        });
        HTTP.verifyRequested(GET, NETWORK_CONFIGURATIONS_PATH);
    }

    @Test
    void list_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        final byte[] body = jsonBytes(
                GitHubOrganizationsNetworkConfigurationsApiIntegrationTest.class, "network-configurations-page.json");
        HTTP.stub(
                GET,
                NETWORK_CONFIGURATIONS_PATH,
                okJson(body)
                        .header(
                                LINK,
                                linkNext(HTTP.uri(
                                        NETWORK_CONFIGURATIONS_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(body));

        final GitHubPage<GitHubNetworkConfiguration> first;
        final Optional<GitHubPage<GitHubNetworkConfiguration>> second;
        final Optional<GitHubPage<GitHubNetworkConfiguration>> third;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            first = organizations.api().networkConfigurations().list(ORG);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        final GitHubNetworkConfigurationsPage expected = expectedPage();
        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expected.networkConfigurations());
            softly.assertThat(first.totalCount()).isEqualTo(expected.totalCount());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expected.networkConfigurations());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, NETWORK_CONFIGURATIONS_PATH, 2);
        HTTP.verifyQueryParam(GET, NETWORK_CONFIGURATIONS_PATH, "page", "2");
    }

    private static GitHubNetworkConfigurationsPage expectedPage() {
        return CODEC.decode(
                jsonBytes(
                        GitHubOrganizationsNetworkConfigurationsApiIntegrationTest.class,
                        "network-configurations-page.json"),
                GitHubNetworkConfigurationsPage.class);
    }

    @Test
    void create_shouldReturnNetworkConfiguration_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                NETWORK_CONFIGURATIONS_PATH,
                createdJson(jsonBytes(
                        GitHubOrganizationsNetworkConfigurationsApiIntegrationTest.class,
                        "network-configuration.json")));
        final GitHubCreateNetworkConfigurationPayload payload =
                OrganizationsCatalogFixtures.createNetworkConfigurationPayload();

        final GitHubNetworkConfiguration networkConfiguration;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            networkConfiguration = organizations.api().networkConfigurations().create(ORG, payload);
        }

        final GitHubNetworkConfiguration expected = CODEC.decode(
                jsonBytes(
                        GitHubOrganizationsNetworkConfigurationsApiIntegrationTest.class, "network-configuration.json"),
                GitHubNetworkConfiguration.class);

        assertThat(networkConfiguration).isEqualTo(expected);
        HTTP.verifyRequested(POST, NETWORK_CONFIGURATIONS_PATH);
        HTTP.verifyHeader(POST, NETWORK_CONFIGURATIONS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, NETWORK_CONFIGURATIONS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void get_shouldReturnNetworkConfiguration_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                NETWORK_CONFIGURATION_PATH,
                okJson(jsonBytes(
                        GitHubOrganizationsNetworkConfigurationsApiIntegrationTest.class,
                        "network-configuration.json")));

        final GitHubNetworkConfiguration networkConfiguration;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            networkConfiguration = organizations
                    .api()
                    .networkConfigurations()
                    .get(ORG, NETWORK_CONFIGURATION_ID)
                    .orElseThrow();
        }

        final GitHubNetworkConfiguration expected = CODEC.decode(
                jsonBytes(
                        GitHubOrganizationsNetworkConfigurationsApiIntegrationTest.class, "network-configuration.json"),
                GitHubNetworkConfiguration.class);

        assertThat(networkConfiguration).isEqualTo(expected);
        HTTP.verifyRequested(GET, NETWORK_CONFIGURATION_PATH);
    }

    @Test
    void update_shouldReturnNetworkConfiguration_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                NETWORK_CONFIGURATION_PATH,
                okJson(jsonBytes(
                        GitHubOrganizationsNetworkConfigurationsApiIntegrationTest.class,
                        "network-configuration.json")));
        final GitHubUpdateNetworkConfigurationPayload payload =
                OrganizationsCatalogFixtures.updateNetworkConfigurationPayload();

        final GitHubNetworkConfiguration networkConfiguration;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            networkConfiguration =
                    organizations.api().networkConfigurations().update(ORG, NETWORK_CONFIGURATION_ID, payload);
        }

        final GitHubNetworkConfiguration expected = CODEC.decode(
                jsonBytes(
                        GitHubOrganizationsNetworkConfigurationsApiIntegrationTest.class, "network-configuration.json"),
                GitHubNetworkConfiguration.class);

        assertThat(networkConfiguration).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, NETWORK_CONFIGURATION_PATH);
        HTTP.verifyHeader(PATCH, NETWORK_CONFIGURATION_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, NETWORK_CONFIGURATION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void delete_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, NETWORK_CONFIGURATION_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().networkConfigurations().delete(ORG, NETWORK_CONFIGURATION_ID);
        }

        HTTP.verifyRequested(DELETE, NETWORK_CONFIGURATION_PATH);
    }

    @Test
    void getNetworkSettings_shouldReturnNetworkSettings_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                NETWORK_SETTINGS_PATH,
                okJson(jsonBytes(
                        GitHubOrganizationsNetworkConfigurationsApiIntegrationTest.class, "network-settings.json")));

        final GitHubNetworkSettings networkSettings;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            networkSettings = organizations
                    .api()
                    .networkConfigurations()
                    .getNetworkSettings(ORG, NETWORK_SETTINGS_ID)
                    .orElseThrow();
        }

        final GitHubNetworkSettings expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsNetworkConfigurationsApiIntegrationTest.class, "network-settings.json"),
                GitHubNetworkSettings.class);

        assertThat(networkSettings).isEqualTo(expected);
        HTTP.verifyRequested(GET, NETWORK_SETTINGS_PATH);
    }
}
