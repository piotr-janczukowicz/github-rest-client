package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubHookDelivery;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubHookDeliveryItem;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubListWebhookDeliveriesFilterBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubUpdateWebhookConfigForRepoPayload;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubWebhookConfig;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubWebhookDeliveryStatus;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubOrganizationsWebhooksApiTest {

    private static final String ORG = "octocat";
    private static final long HOOK_ID = 1L;
    private static final long DELIVERY_ID = 42L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsWebhooksApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listWebhooks_shouldReturnHooks_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + orgHookJson() + "]"));

        final GitHubPage<GitHubOrgHook> page = callApi(api -> api.listWebhooks(ORG));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.orgHook());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octocat/hooks?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void getWebhook_shouldReturnHook_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), orgHookJson()));

        final Optional<GitHubOrgHook> hook = callApi(api -> api.getWebhook(ORG, HOOK_ID));

        assertThat(hook).contains(OrganizationsCatalogFixtures.orgHook());
    }

    @Test
    void getWebhook_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubOrgHook> hook = callApi(api -> api.getWebhook(ORG, HOOK_ID));

        assertThat(hook).isEmpty();
    }

    @Test
    void createWebhook_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), orgHookJson()));
        final GitHubCreateOrgWebhookPayload payload = OrganizationsCatalogFixtures.createOrgWebhookPayload();

        final GitHubOrgHook hook = callApi(api -> api.createWebhook(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(hook).isEqualTo(OrganizationsCatalogFixtures.orgHook());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/octocat/hooks");
        });
    }

    @Test
    void createWebhook_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> callApi(api -> api.createWebhook(ORG, null)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void updateWebhook_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), orgHookJson()));
        final GitHubUpdateOrgWebhookPayload payload = OrganizationsCatalogFixtures.updateOrgWebhookPayload();

        final GitHubOrgHook hook = callApi(api -> api.updateWebhook(ORG, HOOK_ID, payload));

        assertThat(hook).isEqualTo(OrganizationsCatalogFixtures.orgHook());
    }

    @Test
    void updateWebhook_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> callApi(api -> api.updateWebhook(ORG, HOOK_ID, null)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteWebhook_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.deleteWebhook(ORG, HOOK_ID));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/orgs/octocat/hooks/1");
    }

    @Test
    void getWebhookConfigForOrg_shouldReturnConfig_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), webhookConfigJson()));

        final GitHubWebhookConfig config =
                callApi(api -> api.getWebhookConfigForOrg(ORG, HOOK_ID).orElseThrow());

        assertThat(config).isEqualTo(RepositoriesCatalogFixtures.webhookConfig());
    }

    @Test
    void getWebhookConfigForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubWebhookConfig> config = callApi(api -> api.getWebhookConfigForOrg(ORG, HOOK_ID));

        assertThat(config).isEmpty();
    }

    @Test
    void updateWebhookConfigForOrg_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), webhookConfigJson()));
        final GitHubUpdateWebhookConfigForRepoPayload payload =
                RepositoriesCatalogFixtures.updateWebhookConfigForRepoPayload();

        final GitHubWebhookConfig config = callApi(api -> api.updateWebhookConfigForOrg(ORG, HOOK_ID, payload));

        assertThat(config).isEqualTo(RepositoriesCatalogFixtures.webhookConfig());
    }

    @Test
    void updateWebhookConfigForOrg_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), webhookConfigJson()));

        callApiVoid(api -> api.updateWebhookConfigForOrg(ORG, HOOK_ID, null));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
    }

    @Test
    void listWebhookDeliveries_shouldReturnDeliveries_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + hookDeliveryItemJson() + "]"));

        final GitHubPage<GitHubHookDeliveryItem> page = callApi(api -> api.listWebhookDeliveries(ORG, HOOK_ID));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.hookDeliveryItem());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/orgs/octocat/hooks/1/deliveries?" + TestClients.perPageQuery());
        });
    }

    @Test
    void listWebhookDeliveries_shouldSendStatusFilter_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + hookDeliveryItemJson() + "]"));

        callApiVoid(api -> api.listWebhookDeliveries(
                ORG,
                HOOK_ID,
                GitHubListWebhookDeliveriesFilterBuilder.builder()
                        .status(GitHubWebhookDeliveryStatus.SUCCESS)
                        .build(),
                GitHubPageRequest.byDeliveryCursor()));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("status", "success");
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octocat/hooks/1/deliveries?status=success&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void getWebhookDelivery_shouldReturnDelivery_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), hookDeliveryJson()));

        final GitHubHookDelivery delivery =
                callApi(api -> api.getWebhookDelivery(ORG, HOOK_ID, DELIVERY_ID).orElseThrow());

        assertThat(delivery).isEqualTo(RepositoriesCatalogFixtures.sampleHookDelivery());
    }

    @Test
    void getWebhookDelivery_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubHookDelivery> delivery = callApi(api -> api.getWebhookDelivery(ORG, HOOK_ID, DELIVERY_ID));

        assertThat(delivery).isEmpty();
    }

    @Test
    void pingWebhook_shouldPost_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.pingWebhook(ORG, HOOK_ID));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/orgs/octocat/hooks/1/pings");
    }

    @Test
    void redeliverWebhookDelivery_shouldPost_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        callApiVoid(api -> api.redeliverWebhookDelivery(ORG, HOOK_ID, DELIVERY_ID));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/orgs/octocat/hooks/1/deliveries/42/attempts");
    }

    @FunctionalInterface
    private interface WebhooksApiAction<T> {
        T apply(GitHubOrganizationsWebhooksApi api) throws Exception;
    }

    @FunctionalInterface
    private interface WebhooksApiVoidAction {
        void execute(GitHubOrganizationsWebhooksApi api) throws Exception;
    }

    private void callApiVoid(final WebhooksApiVoidAction action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            action.execute(new GitHubOrganizationsWebhooksApi(TestClients.caller(client)));
        }
    }

    private <T> T callApi(final WebhooksApiAction<T> action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationsWebhooksApi(TestClients.caller(client)));
        }
    }

    private static String orgHookJson() {
        return FileUtils.readString(GitHubOrganizationsWebhooksApiTest.class, "org-hook.json");
    }

    private static String webhookConfigJson() {
        return FileUtils.readString(GitHubWebhookConfig.class, "webhook-config.json");
    }

    private static String hookDeliveryItemJson() {
        return FileUtils.readString(GitHubHookDeliveryItem.class, "hook-delivery-item.json");
    }

    private static String hookDeliveryJson() {
        return FileUtils.readString(GitHubHookDelivery.class, "hook-delivery.json");
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
