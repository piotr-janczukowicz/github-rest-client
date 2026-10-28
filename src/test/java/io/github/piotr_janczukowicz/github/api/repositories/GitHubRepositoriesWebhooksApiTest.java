package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
class GitHubRepositoriesWebhooksApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long HOOK_ID = 42L;
    private static final long DELIVERY_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoriesWebhooksApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listWebhooks_shouldReturnHooks_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("hook.json") + "]"));

        final GitHubPage<GitHubHook> page = api().listWebhooks(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.sampleHook());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/hooks?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void getWebhook_shouldReturnHook_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("hook.json")));

        final Optional<GitHubHook> hook = api().getWebhook(OWNER, REPO, HOOK_ID);

        assertThat(hook).contains(RepositoriesCatalogFixtures.sampleHook());
    }

    @Test
    void getWebhook_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubHook> hook = api().getWebhook(OWNER, REPO, HOOK_ID);

        assertThat(hook).isEmpty();
    }

    @Test
    void createWebhook_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("hook.json")));
        final GitHubCreateWebhookPayload payload = RepositoriesCatalogFixtures.createWebhookPayload();

        final GitHubHook hook = api().createWebhook(OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(hook).isEqualTo(RepositoriesCatalogFixtures.sampleHook());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/hooks");
        });
    }

    @Test
    void createWebhook_shouldPostWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("hook.json")));

        api().createWebhook(OWNER, REPO, null);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.POST);
    }

    @Test
    void updateWebhook_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("hook.json")));
        final GitHubUpdateWebhookPayload payload = RepositoriesCatalogFixtures.updateWebhookPayload();

        final GitHubHook hook = api().updateWebhook(OWNER, REPO, HOOK_ID, payload);

        assertThat(hook).isEqualTo(RepositoriesCatalogFixtures.sampleHook());
    }

    @Test
    void deleteWebhook_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteWebhook(OWNER, REPO, HOOK_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/hooks/42");
    }

    @Test
    void getWebhookConfigForRepo_shouldReturnConfig_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("webhook-config.json")));

        final GitHubWebhookConfig config =
                api().getWebhookConfigForRepo(OWNER, REPO, HOOK_ID).orElseThrow();

        assertThat(config).isEqualTo(RepositoriesCatalogFixtures.webhookConfig());
    }

    @Test
    void getWebhookConfigForRepo_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubWebhookConfig> config = api().getWebhookConfigForRepo(OWNER, REPO, HOOK_ID);

        assertThat(config).isEmpty();
    }

    @Test
    void updateWebhookConfigForRepo_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("webhook-config.json")));
        final GitHubUpdateWebhookConfigForRepoPayload payload =
                RepositoriesCatalogFixtures.updateWebhookConfigForRepoPayload();

        final GitHubWebhookConfig config = api().updateWebhookConfigForRepo(OWNER, REPO, HOOK_ID, payload);

        assertThat(config).isEqualTo(RepositoriesCatalogFixtures.webhookConfig());
    }

    @Test
    void listWebhookDeliveries_shouldReturnDeliveries_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("hook-delivery-item.json") + "]"));

        final GitHubPage<GitHubHookDeliveryItem> page = api().listWebhookDeliveries(OWNER, REPO, HOOK_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.hookDeliveryItem());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/hooks/42/deliveries?"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listWebhookDeliveries_shouldSendStatusFilter_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("hook-delivery-item.json") + "]"));

        api().listWebhookDeliveries(
                        OWNER,
                        REPO,
                        HOOK_ID,
                        GitHubListWebhookDeliveriesFilterBuilder.builder()
                                .status(GitHubWebhookDeliveryStatus.SUCCESS)
                                .build(),
                        GitHubPageRequest.byDeliveryCursor());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("status", "success");
    }

    @Test
    void updateWebhookConfigForRepo_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("webhook-config.json")));

        api().updateWebhookConfigForRepo(OWNER, REPO, HOOK_ID, null);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
    }

    @Test
    void getWebhookDelivery_shouldReturnDelivery_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("hook-delivery.json")));

        final GitHubHookDelivery delivery =
                api().getWebhookDelivery(OWNER, REPO, HOOK_ID, DELIVERY_ID).orElseThrow();

        assertThat(delivery).isEqualTo(RepositoriesCatalogFixtures.sampleHookDelivery());
    }

    @Test
    void getWebhookDelivery_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubHookDelivery> delivery = api().getWebhookDelivery(OWNER, REPO, HOOK_ID, DELIVERY_ID);

        assertThat(delivery).isEmpty();
    }

    @Test
    void pingWebhook_shouldPost_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().pingWebhook(OWNER, REPO, HOOK_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/hooks/42/pings");
    }

    @Test
    void testPushWebhook_shouldPost_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().testPushWebhook(OWNER, REPO, HOOK_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/hooks/42/tests");
    }

    @Test
    void redeliverWebhookDelivery_shouldPost_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        api().redeliverWebhookDelivery(OWNER, REPO, HOOK_ID, DELIVERY_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri())
                .hasToString("https://api.github.com/repos/octocat/Hello-World/hooks/42/deliveries/1/attempts");
    }

    private GitHubRepositoriesWebhooksApi api() {
        return new GitHubRepositoriesWebhooksApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubRepositoriesWebhooksApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
