package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.client;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubAppsMarketplaceApiIntegrationTest {

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getSubscriptionPlanForAccount_shouldReturnPurchase_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/marketplace_listing/accounts/1",
                okJson(jsonBytes(GitHubAppsMarketplaceApiIntegrationTest.class, "marketplace-purchase.json")));

        final GitHubMarketplacePurchase purchase;
        try (final GitHubApiWireMockSupport.WireMockClient wireMock = client(HTTP)) {
            purchase = new GitHubAppsMarketplaceApi(wireMock.caller())
                    .getSubscriptionPlanForAccount(1L)
                    .orElseThrow();
        }

        assertThat(purchase).isEqualTo(AppsCatalogFixtures.marketplacePurchase());
        HTTP.verifyRequested(GET, "/marketplace_listing/accounts/1");
    }
}
