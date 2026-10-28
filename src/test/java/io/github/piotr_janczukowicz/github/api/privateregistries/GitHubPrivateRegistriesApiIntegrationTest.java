package io.github.piotr_janczukowicz.github.api.privateregistries;

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
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.privateRegistriesApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubPrivateRegistriesApiIntegrationTest {

    private static final String ORG = "github";
    private static final String SECRET_NAME = "MAVEN_REPOSITORY_SECRET";

    private static final String LIST_PATH = "/orgs/github/private-registries";
    private static final String PUBLIC_KEY_PATH = "/orgs/github/private-registries/public-key";
    private static final String ITEM_PATH = "/orgs/github/private-registries/MAVEN_REPOSITORY_SECRET";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listOrgPrivateRegistries_shouldReturnConfigurations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                LIST_PATH,
                okJson(jsonBytes(
                        GitHubPrivateRegistriesApiIntegrationTest.class,
                        "org-private-registry-configurations-page.json")));

        final GitHubPage<GitHubOrgPrivateRegistryConfiguration> page;
        try (final GitHubApiWireMockSupport.PrivateRegistriesApiClient api = privateRegistriesApi(HTTP)) {
            page = api.api().listOrgPrivateRegistries(ORG);
        }

        final GitHubOrgPrivateRegistryConfigurationsPage expected = expectedConfigurationsPage();
        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactlyElementsOf(expected.configurations());
            softly.assertThat(page.totalCount()).isEqualTo(expected.totalCount());
        });
        HTTP.verifyRequested(GET, LIST_PATH);
        HTTP.verifyQueryParam(GET, LIST_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, LIST_PATH, "per_page", "100");
    }

    @Test
    void listOrgPrivateRegistries_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        final byte[] body = jsonBytes(
                GitHubPrivateRegistriesApiIntegrationTest.class, "org-private-registry-configurations-page.json");
        HTTP.stub(
                GET,
                LIST_PATH,
                okJson(body).header(LINK, linkNext(HTTP.uri(LIST_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(body));

        final GitHubPage<GitHubOrgPrivateRegistryConfiguration> first;
        final Optional<GitHubPage<GitHubOrgPrivateRegistryConfiguration>> second;
        final Optional<GitHubPage<GitHubOrgPrivateRegistryConfiguration>> third;
        try (final GitHubApiWireMockSupport.PrivateRegistriesApiClient api = privateRegistriesApi(HTTP)) {
            first = api.api().listOrgPrivateRegistries(ORG);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        final GitHubOrgPrivateRegistryConfigurationsPage expected = expectedConfigurationsPage();
        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expected.configurations());
            softly.assertThat(first.totalCount()).isEqualTo(expected.totalCount());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expected.configurations());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, LIST_PATH, 2);
        HTTP.verifyQueryParam(GET, LIST_PATH, "page", "2");
    }

    @Test
    void listOrgPrivateRegistries_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                LIST_PATH,
                okJson(jsonBytes(
                        GitHubPrivateRegistriesApiIntegrationTest.class,
                        "org-private-registry-configurations-page.json")));

        try (final GitHubApiWireMockSupport.PrivateRegistriesApiClient api = privateRegistriesApi(HTTP)) {
            api.api().listOrgPrivateRegistries(ORG, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, LIST_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, LIST_PATH, "per_page", "10");
    }

    @Test
    void createOrgPrivateRegistry_shouldReturnConfiguration_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                LIST_PATH,
                createdJson(jsonBytes(
                        GitHubPrivateRegistriesApiIntegrationTest.class,
                        "org-private-registry-configuration-with-selected-repositories.json")));
        final GitHubCreateOrgPrivateRegistryPayload payload =
                PrivateRegistriesCatalogFixtures.createOrgPrivateRegistryPayload();

        final GitHubOrgPrivateRegistryConfigurationWithSelectedRepositories configuration;
        try (final GitHubApiWireMockSupport.PrivateRegistriesApiClient api = privateRegistriesApi(HTTP)) {
            configuration = api.api().createOrgPrivateRegistry(ORG, payload);
        }

        final GitHubOrgPrivateRegistryConfigurationWithSelectedRepositories expected = CODEC.decode(
                jsonBytes(
                        GitHubPrivateRegistriesApiIntegrationTest.class,
                        "org-private-registry-configuration-with-selected-repositories.json"),
                GitHubOrgPrivateRegistryConfigurationWithSelectedRepositories.class);

        assertThat(configuration).isEqualTo(expected);
        HTTP.verifyRequested(POST, LIST_PATH);
        HTTP.verifyHeader(POST, LIST_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, LIST_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getOrgPublicKey_shouldReturnPublicKey_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PUBLIC_KEY_PATH,
                okJson(jsonBytes(
                        GitHubPrivateRegistriesApiIntegrationTest.class, "private-registries-public-key.json")));

        final GitHubPrivateRegistriesPublicKey publicKey;
        try (final GitHubApiWireMockSupport.PrivateRegistriesApiClient api = privateRegistriesApi(HTTP)) {
            publicKey = api.api().getOrgPublicKey(ORG);
        }

        final GitHubPrivateRegistriesPublicKey expected = CODEC.decode(
                jsonBytes(GitHubPrivateRegistriesApiIntegrationTest.class, "private-registries-public-key.json"),
                GitHubPrivateRegistriesPublicKey.class);

        assertThat(publicKey).isEqualTo(expected);
        HTTP.verifyRequested(GET, PUBLIC_KEY_PATH);
    }

    @Test
    void getOrgPublicKey_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, PUBLIC_KEY_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.PrivateRegistriesApiClient api = privateRegistriesApi(HTTP)) {
            final GitHubPrivateRegistriesApi facade = api.api();

            thrown = catchThrowable(() -> facade.getOrgPublicKey(ORG));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, PUBLIC_KEY_PATH);
    }

    @Test
    void getOrgPrivateRegistry_shouldReturnConfiguration_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ITEM_PATH,
                okJson(jsonBytes(
                        GitHubPrivateRegistriesApiIntegrationTest.class, "org-private-registry-configuration.json")));

        final Optional<GitHubOrgPrivateRegistryConfiguration> configuration;
        try (final GitHubApiWireMockSupport.PrivateRegistriesApiClient api = privateRegistriesApi(HTTP)) {
            configuration = api.api().getOrgPrivateRegistry(ORG, SECRET_NAME);
        }

        assertThat(configuration).contains(expectedConfiguration());
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void getOrgPrivateRegistry_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ITEM_PATH, notFound());

        final Optional<GitHubOrgPrivateRegistryConfiguration> configuration;
        try (final GitHubApiWireMockSupport.PrivateRegistriesApiClient api = privateRegistriesApi(HTTP)) {
            configuration = api.api().getOrgPrivateRegistry(ORG, SECRET_NAME);
        }

        assertThat(configuration).isEmpty();
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void updateOrgPrivateRegistry_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PATCH, ITEM_PATH, noContent());
        final GitHubUpdateOrgPrivateRegistryPayload payload =
                PrivateRegistriesCatalogFixtures.updateOrgPrivateRegistryPayload();

        try (final GitHubApiWireMockSupport.PrivateRegistriesApiClient api = privateRegistriesApi(HTTP)) {
            api.api().updateOrgPrivateRegistry(ORG, SECRET_NAME, payload);
        }

        HTTP.verifyRequested(PATCH, ITEM_PATH);
        HTTP.verifyHeader(PATCH, ITEM_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, ITEM_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteOrgPrivateRegistry_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.PrivateRegistriesApiClient api = privateRegistriesApi(HTTP)) {
            api.api().deleteOrgPrivateRegistry(ORG, SECRET_NAME);
        }

        HTTP.verifyRequested(DELETE, ITEM_PATH);
    }

    private static GitHubOrgPrivateRegistryConfigurationsPage expectedConfigurationsPage() {
        return CODEC.decode(
                jsonBytes(
                        GitHubPrivateRegistriesApiIntegrationTest.class,
                        "org-private-registry-configurations-page.json"),
                GitHubOrgPrivateRegistryConfigurationsPage.class);
    }

    private static GitHubOrgPrivateRegistryConfiguration expectedConfiguration() {
        return CODEC.decode(
                jsonBytes(GitHubPrivateRegistriesApiIntegrationTest.class, "org-private-registry-configuration.json"),
                GitHubOrgPrivateRegistryConfiguration.class);
    }
}
