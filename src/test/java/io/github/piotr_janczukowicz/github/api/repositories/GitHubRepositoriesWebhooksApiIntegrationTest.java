package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.accepted;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.reposWebhooksApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubDeliveryCursorRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubRepositoriesWebhooksApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long HOOK_ID = 42L;
    private static final long DELIVERY_ID = 1L;

    private static final String HOOKS_PATH = "/repos/octocat/Hello-World/hooks";
    private static final String HOOK_ITEM_PATH = "/repos/octocat/Hello-World/hooks/42";
    private static final String CONFIG_PATH = "/repos/octocat/Hello-World/hooks/42/config";
    private static final String DELIVERIES_PATH = "/repos/octocat/Hello-World/hooks/42/deliveries";
    private static final String DELIVERY_ITEM_PATH = "/repos/octocat/Hello-World/hooks/42/deliveries/1";
    private static final String PINGS_PATH = "/repos/octocat/Hello-World/hooks/42/pings";
    private static final String TESTS_PATH = "/repos/octocat/Hello-World/hooks/42/tests";
    private static final String ATTEMPTS_PATH = "/repos/octocat/Hello-World/hooks/42/deliveries/1/attempts";

    private static final JsonType<List<GitHubHook>> HOOKS_TYPE = JsonTypes.listOf(GitHubHook.class);
    private static final JsonType<List<GitHubHookDeliveryItem>> DELIVERIES_TYPE =
            JsonTypes.listOf(GitHubHookDeliveryItem.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listWebhooks_shouldReturnHooks_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                HOOKS_PATH,
                okJson(listJsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook.json")));

        final GitHubPage<GitHubHook> page;
        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            page = api.api().listWebhooks(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedHooks());
        HTTP.verifyRequested(GET, HOOKS_PATH);
        HTTP.verifyQueryParam(GET, HOOKS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, HOOKS_PATH, "per_page", "100");
    }

    @Test
    void listWebhooks_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                HOOKS_PATH,
                okJson(listJsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook.json"))
                        .header(LINK, linkNext(HTTP.uri(HOOKS_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook.json")));

        final GitHubPage<GitHubHook> first;
        final Optional<GitHubPage<GitHubHook>> second;
        final Optional<GitHubPage<GitHubHook>> third;
        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            first = api.api().listWebhooks(OWNER, REPO);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedHooks());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedHooks());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, HOOKS_PATH, 2);
    }

    @Test
    void listWebhooks_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                HOOKS_PATH,
                okJson(listJsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook.json")));

        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            api.api().listWebhooks(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, HOOKS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, HOOKS_PATH, "per_page", "10");
    }

    @Test
    void getWebhook_shouldReturnHook_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                HOOK_ITEM_PATH,
                okJson(jsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook.json")));

        final Optional<GitHubHook> hook;
        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            hook = api.api().getWebhook(OWNER, REPO, HOOK_ID);
        }

        final GitHubHook expected = CODEC.decode(
                jsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook.json"), GitHubHook.class);

        assertThat(hook).contains(expected);
        HTTP.verifyRequested(GET, HOOK_ITEM_PATH);
    }

    @Test
    void getWebhook_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, HOOK_ITEM_PATH, notFound());

        final Optional<GitHubHook> hook;
        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            hook = api.api().getWebhook(OWNER, REPO, HOOK_ID);
        }

        assertThat(hook).isEmpty();
        HTTP.verifyRequested(GET, HOOK_ITEM_PATH);
    }

    @Test
    void createWebhook_shouldReturnHook_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                HOOKS_PATH,
                createdJson(jsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook.json")));
        final GitHubCreateWebhookPayload payload = RepositoriesCatalogFixtures.createWebhookPayload();

        final GitHubHook hook;
        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            hook = api.api().createWebhook(OWNER, REPO, payload);
        }

        final GitHubHook expected = CODEC.decode(
                jsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook.json"), GitHubHook.class);

        assertThat(hook).isEqualTo(expected);
        HTTP.verifyRequested(POST, HOOKS_PATH);
        HTTP.verifyHeader(POST, HOOKS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, HOOKS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void createWebhook_shouldPostWithoutBody_whenPayloadNull() throws Exception {
        HTTP.stub(
                POST,
                HOOKS_PATH,
                createdJson(jsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook.json")));

        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            api.api().createWebhook(OWNER, REPO, null);
        }

        HTTP.verifyRequested(POST, HOOKS_PATH);
    }

    @Test
    void updateWebhook_shouldReturnHook_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                HOOK_ITEM_PATH,
                okJson(jsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook.json")));
        final GitHubUpdateWebhookPayload payload = RepositoriesCatalogFixtures.updateWebhookPayload();

        final GitHubHook hook;
        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            hook = api.api().updateWebhook(OWNER, REPO, HOOK_ID, payload);
        }

        final GitHubHook expected = CODEC.decode(
                jsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook.json"), GitHubHook.class);

        assertThat(hook).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, HOOK_ITEM_PATH);
        HTTP.verifyHeader(PATCH, HOOK_ITEM_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, HOOK_ITEM_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteWebhook_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, HOOK_ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            api.api().deleteWebhook(OWNER, REPO, HOOK_ID);
        }

        HTTP.verifyRequested(DELETE, HOOK_ITEM_PATH);
    }

    @Test
    void getWebhookConfigForRepo_shouldReturnConfig_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                CONFIG_PATH,
                okJson(jsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "webhook-config.json")));

        final GitHubWebhookConfig config;
        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            config = api.api().getWebhookConfigForRepo(OWNER, REPO, HOOK_ID).orElseThrow();
        }

        final GitHubWebhookConfig expected = CODEC.decode(
                jsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "webhook-config.json"),
                GitHubWebhookConfig.class);

        assertThat(config).isEqualTo(expected);
        HTTP.verifyRequested(GET, CONFIG_PATH);
    }

    @Test
    void getWebhookConfigForRepo_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, CONFIG_PATH, notFound());

        final Optional<GitHubWebhookConfig> config;
        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            config = api.api().getWebhookConfigForRepo(OWNER, REPO, HOOK_ID);
        }

        assertThat(config).isEmpty();
        HTTP.verifyRequested(GET, CONFIG_PATH);
    }

    @Test
    void updateWebhookConfigForRepo_shouldReturnConfig_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                CONFIG_PATH,
                okJson(jsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "webhook-config.json")));
        final GitHubUpdateWebhookConfigForRepoPayload payload =
                RepositoriesCatalogFixtures.updateWebhookConfigForRepoPayload();

        final GitHubWebhookConfig config;
        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            config = api.api().updateWebhookConfigForRepo(OWNER, REPO, HOOK_ID, payload);
        }

        final GitHubWebhookConfig expected = CODEC.decode(
                jsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "webhook-config.json"),
                GitHubWebhookConfig.class);

        assertThat(config).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, CONFIG_PATH);
        HTTP.verifyHeader(PATCH, CONFIG_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, CONFIG_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updateWebhookConfigForRepo_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        HTTP.stub(
                PATCH,
                CONFIG_PATH,
                okJson(jsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "webhook-config.json")));

        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            api.api().updateWebhookConfigForRepo(OWNER, REPO, HOOK_ID, null);
        }

        HTTP.verifyRequested(PATCH, CONFIG_PATH);
    }

    @Test
    void listWebhookDeliveries_shouldReturnDeliveries_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                DELIVERIES_PATH,
                okJson(listJsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook-delivery-item.json")));

        final GitHubPage<GitHubHookDeliveryItem> page;
        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            page = api.api().listWebhookDeliveries(OWNER, REPO, HOOK_ID);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedDeliveries());
        HTTP.verifyRequested(GET, DELIVERIES_PATH);
        HTTP.verifyQueryParam(GET, DELIVERIES_PATH, "per_page", "100");
        HTTP.verifyNoQueryParam(GET, DELIVERIES_PATH, "page");
    }

    @Test
    void listWebhookDeliveries_shouldSendStatusFilter_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                DELIVERIES_PATH,
                okJson(listJsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook-delivery-item.json")));

        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            api.api()
                    .listWebhookDeliveries(
                            OWNER,
                            REPO,
                            HOOK_ID,
                            GitHubListWebhookDeliveriesFilterBuilder.builder()
                                    .status(GitHubWebhookDeliveryStatus.SUCCESS)
                                    .build(),
                            GitHubPageRequest.byDeliveryCursor());
        }

        HTTP.verifyQueryParam(GET, DELIVERIES_PATH, "status", "success");
        HTTP.verifyQueryParam(GET, DELIVERIES_PATH, "per_page", "100");
    }

    @Test
    void listWebhookDeliveries_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                DELIVERIES_PATH,
                okJson(listJsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook-delivery-item.json"))
                        .header(
                                LINK,
                                linkNext(HTTP.uri(DELIVERIES_PATH + "?after=cursor&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook-delivery-item.json")));

        final GitHubPage<GitHubHookDeliveryItem> first;
        final Optional<GitHubPage<GitHubHookDeliveryItem>> second;
        final Optional<GitHubPage<GitHubHookDeliveryItem>> third;
        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            first = api.api().listWebhookDeliveries(OWNER, REPO, HOOK_ID);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedDeliveries());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedDeliveries());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, DELIVERIES_PATH, 2);
    }

    @Test
    void listWebhookDeliveries_shouldSendPerPage_whenCustomDeliveryCursor() throws Exception {
        HTTP.stub(
                GET,
                DELIVERIES_PATH,
                okJson(listJsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook-delivery-item.json")));

        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            api.api()
                    .listWebhookDeliveries(
                            OWNER,
                            REPO,
                            HOOK_ID,
                            GitHubListWebhookDeliveriesFilter.empty(),
                            GitHubDeliveryCursorRequest.of(10));
        }

        HTTP.verifyQueryParam(GET, DELIVERIES_PATH, "per_page", "10");
        HTTP.verifyNoQueryParam(GET, DELIVERIES_PATH, "page");
    }

    @Test
    void getWebhookDelivery_shouldReturnDelivery_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                DELIVERY_ITEM_PATH,
                okJson(jsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook-delivery.json")));

        final GitHubHookDelivery delivery;
        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            delivery = api.api()
                    .getWebhookDelivery(OWNER, REPO, HOOK_ID, DELIVERY_ID)
                    .orElseThrow();
        }

        final GitHubHookDelivery expected = CODEC.decode(
                jsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook-delivery.json"),
                GitHubHookDelivery.class);

        assertThat(delivery).isEqualTo(expected);
        HTTP.verifyRequested(GET, DELIVERY_ITEM_PATH);
    }

    @Test
    void getWebhookDelivery_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, DELIVERY_ITEM_PATH, notFound());

        final Optional<GitHubHookDelivery> delivery;
        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            delivery = api.api().getWebhookDelivery(OWNER, REPO, HOOK_ID, DELIVERY_ID);
        }

        assertThat(delivery).isEmpty();
        HTTP.verifyRequested(GET, DELIVERY_ITEM_PATH);
    }

    @Test
    void pingWebhook_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(POST, PINGS_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            api.api().pingWebhook(OWNER, REPO, HOOK_ID);
        }

        HTTP.verifyRequested(POST, PINGS_PATH);
    }

    @Test
    void testPushWebhook_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(POST, TESTS_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            api.api().testPushWebhook(OWNER, REPO, HOOK_ID);
        }

        HTTP.verifyRequested(POST, TESTS_PATH);
    }

    @Test
    void redeliverWebhookDelivery_shouldComplete_whenResponseIsAccepted() throws Exception {
        HTTP.stub(POST, ATTEMPTS_PATH, accepted());

        try (final GitHubApiWireMockSupport.ReposWebhooksApiClient api = reposWebhooksApi(HTTP)) {
            api.api().redeliverWebhookDelivery(OWNER, REPO, HOOK_ID, DELIVERY_ID);
        }

        HTTP.verifyRequested(POST, ATTEMPTS_PATH);
    }

    private static List<GitHubHook> expectedHooks() {
        return CODEC.decode(listJsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook.json"), HOOKS_TYPE);
    }

    private static List<GitHubHookDeliveryItem> expectedDeliveries() {
        return CODEC.decode(
                listJsonBytes(GitHubRepositoriesWebhooksApiIntegrationTest.class, "hook-delivery-item.json"),
                DELIVERIES_TYPE);
    }
}
