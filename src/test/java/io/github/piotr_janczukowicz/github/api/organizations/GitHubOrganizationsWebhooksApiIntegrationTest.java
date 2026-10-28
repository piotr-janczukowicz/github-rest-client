package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.accepted;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.organizationsApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubHookDelivery;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubHookDeliveryItem;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubUpdateWebhookConfigForRepoPayload;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubWebhookConfig;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubOrganizationsWebhooksApiIntegrationTest {

    private static final String ORG = "octocat";
    private static final long HOOK_ID = 1L;
    private static final long DELIVERY_ID = 42L;

    private static final String HOOKS_PATH = "/orgs/octocat/hooks";
    private static final String HOOK_ITEM_PATH = "/orgs/octocat/hooks/1";
    private static final String CONFIG_PATH = "/orgs/octocat/hooks/1/config";
    private static final String DELIVERIES_PATH = "/orgs/octocat/hooks/1/deliveries";
    private static final String DELIVERY_ITEM_PATH = "/orgs/octocat/hooks/1/deliveries/42";
    private static final String PINGS_PATH = "/orgs/octocat/hooks/1/pings";
    private static final String ATTEMPTS_PATH = "/orgs/octocat/hooks/1/deliveries/42/attempts";

    private static final JsonType<List<GitHubOrgHook>> HOOKS_TYPE = JsonTypes.listOf(GitHubOrgHook.class);
    private static final JsonType<List<GitHubHookDeliveryItem>> DELIVERIES_TYPE =
            JsonTypes.listOf(GitHubHookDeliveryItem.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listWebhooks_shouldReturnHooks_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                HOOKS_PATH,
                okJson(listJsonBytes(GitHubOrganizationsWebhooksApiIntegrationTest.class, "org-hook.json")));

        final GitHubPage<GitHubOrgHook> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().webhooks().listWebhooks(ORG);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedHooks());
        HTTP.verifyRequested(GET, HOOKS_PATH);
        HTTP.verifyQueryParam(GET, HOOKS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, HOOKS_PATH, "per_page", "100");
    }

    @Test
    void createWebhook_shouldReturnHook_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                HOOKS_PATH,
                createdJson(jsonBytes(GitHubOrganizationsWebhooksApiIntegrationTest.class, "org-hook.json")));
        final GitHubCreateOrgWebhookPayload payload = OrganizationsCatalogFixtures.createOrgWebhookPayload();

        final GitHubOrgHook hook;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            hook = organizations.api().webhooks().createWebhook(ORG, payload);
        }

        final GitHubOrgHook expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsWebhooksApiIntegrationTest.class, "org-hook.json"), GitHubOrgHook.class);

        assertThat(hook).isEqualTo(expected);
        HTTP.verifyRequested(POST, HOOKS_PATH);
        HTTP.verifyHeader(POST, HOOKS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, HOOKS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getWebhook_shouldReturnHook_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                HOOK_ITEM_PATH,
                okJson(jsonBytes(GitHubOrganizationsWebhooksApiIntegrationTest.class, "org-hook.json")));

        final Optional<GitHubOrgHook> hook;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            hook = organizations.api().webhooks().getWebhook(ORG, HOOK_ID);
        }

        final GitHubOrgHook expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsWebhooksApiIntegrationTest.class, "org-hook.json"), GitHubOrgHook.class);

        assertThat(hook).contains(expected);
        HTTP.verifyRequested(GET, HOOK_ITEM_PATH);
    }

    @Test
    void updateWebhook_shouldReturnHook_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                HOOK_ITEM_PATH,
                okJson(jsonBytes(GitHubOrganizationsWebhooksApiIntegrationTest.class, "org-hook.json")));
        final GitHubUpdateOrgWebhookPayload payload = OrganizationsCatalogFixtures.updateOrgWebhookPayload();

        final GitHubOrgHook hook;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            hook = organizations.api().webhooks().updateWebhook(ORG, HOOK_ID, payload);
        }

        final GitHubOrgHook expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsWebhooksApiIntegrationTest.class, "org-hook.json"), GitHubOrgHook.class);

        assertThat(hook).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, HOOK_ITEM_PATH);
        HTTP.verifyHeader(PATCH, HOOK_ITEM_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, HOOK_ITEM_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteWebhook_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, HOOK_ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().webhooks().deleteWebhook(ORG, HOOK_ID);
        }

        HTTP.verifyRequested(DELETE, HOOK_ITEM_PATH);
    }

    @Test
    void getWebhookConfigForOrg_shouldReturnConfig_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, CONFIG_PATH, okJson(jsonBytes(GitHubWebhookConfig.class, "webhook-config.json")));

        final GitHubWebhookConfig config;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            config = organizations
                    .api()
                    .webhooks()
                    .getWebhookConfigForOrg(ORG, HOOK_ID)
                    .orElseThrow();
        }

        final GitHubWebhookConfig expected =
                CODEC.decode(jsonBytes(GitHubWebhookConfig.class, "webhook-config.json"), GitHubWebhookConfig.class);

        assertThat(config).isEqualTo(expected);
        HTTP.verifyRequested(GET, CONFIG_PATH);
    }

    @Test
    void updateWebhookConfigForOrg_shouldReturnConfig_whenResponseIsOk() throws Exception {
        HTTP.stub(PATCH, CONFIG_PATH, okJson(jsonBytes(GitHubWebhookConfig.class, "webhook-config.json")));
        final GitHubUpdateWebhookConfigForRepoPayload payload =
                RepositoriesCatalogFixtures.updateWebhookConfigForRepoPayload();

        final GitHubWebhookConfig config;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            config = organizations.api().webhooks().updateWebhookConfigForOrg(ORG, HOOK_ID, payload);
        }

        final GitHubWebhookConfig expected =
                CODEC.decode(jsonBytes(GitHubWebhookConfig.class, "webhook-config.json"), GitHubWebhookConfig.class);

        assertThat(config).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, CONFIG_PATH);
        HTTP.verifyHeader(PATCH, CONFIG_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, CONFIG_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void listWebhookDeliveries_shouldReturnDeliveries_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, DELIVERIES_PATH, okJson(listJsonBytes(GitHubHookDeliveryItem.class, "hook-delivery-item.json")));

        final GitHubPage<GitHubHookDeliveryItem> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().webhooks().listWebhookDeliveries(ORG, HOOK_ID);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedDeliveries());
        HTTP.verifyRequested(GET, DELIVERIES_PATH);
        HTTP.verifyQueryParam(GET, DELIVERIES_PATH, "per_page", "100");
        HTTP.verifyNoQueryParam(GET, DELIVERIES_PATH, "page");
    }

    @Test
    void getWebhookDelivery_shouldReturnDelivery_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, DELIVERY_ITEM_PATH, okJson(jsonBytes(GitHubHookDelivery.class, "hook-delivery.json")));

        final GitHubHookDelivery delivery;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            delivery = organizations
                    .api()
                    .webhooks()
                    .getWebhookDelivery(ORG, HOOK_ID, DELIVERY_ID)
                    .orElseThrow();
        }

        final GitHubHookDelivery expected =
                CODEC.decode(jsonBytes(GitHubHookDelivery.class, "hook-delivery.json"), GitHubHookDelivery.class);

        assertThat(delivery).isEqualTo(expected);
        HTTP.verifyRequested(GET, DELIVERY_ITEM_PATH);
    }

    @Test
    void redeliverWebhookDelivery_shouldComplete_whenResponseIsAccepted() throws Exception {
        HTTP.stub(POST, ATTEMPTS_PATH, accepted());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().webhooks().redeliverWebhookDelivery(ORG, HOOK_ID, DELIVERY_ID);
        }

        HTTP.verifyRequested(POST, ATTEMPTS_PATH);
    }

    @Test
    void pingWebhook_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(POST, PINGS_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().webhooks().pingWebhook(ORG, HOOK_ID);
        }

        HTTP.verifyRequested(POST, PINGS_PATH);
    }

    private static List<GitHubOrgHook> expectedHooks() {
        return CODEC.decode(
                listJsonBytes(GitHubOrganizationsWebhooksApiIntegrationTest.class, "org-hook.json"), HOOKS_TYPE);
    }

    private static List<GitHubHookDeliveryItem> expectedDeliveries() {
        return CODEC.decode(listJsonBytes(GitHubHookDeliveryItem.class, "hook-delivery-item.json"), DELIVERIES_TYPE);
    }
}
