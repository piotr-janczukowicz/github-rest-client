package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.client;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubAppsWebhooksApiIntegrationTest {

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getWebhookConfigForApp_shouldThrowNotFound_whenResponseIsNotFound() {
        HTTP.stub(GET, "/app/hook/config", notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.WireMockClient wireMock = client(HTTP)) {
            thrown = catchThrowable(() -> new GitHubAppsWebhooksApi(wireMock.caller()).getWebhookConfigForApp());
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, "/app/hook/config");
    }

    @Test
    void redeliverWebhookDelivery_shouldPost_whenAccepted() throws Exception {
        HTTP.stub(POST, "/app/hook/deliveries/1/attempts", noContent());

        try (final GitHubApiWireMockSupport.WireMockClient wireMock = client(HTTP)) {
            new GitHubAppsWebhooksApi(wireMock.caller()).redeliverWebhookDelivery(1L);
        }

        HTTP.verifyRequested(POST, "/app/hook/deliveries/1/attempts");
    }
}
