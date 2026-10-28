package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.billingBillingApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubBillingBillingApiIntegrationTest {

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getAdvancedSecurityBilling_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/orgs/octo-org/settings/billing/advanced-security",
                okJson(jsonBytes(
                        GitHubBillingBillingApiIntegrationTest.class, "advanced-security-active-committers.json")));

        final GitHubAdvancedSecurityActiveCommitters result;
        try (final GitHubApiWireMockSupport.BillingBillingApiClient api = billingBillingApi(HTTP)) {
            result = api.api().getAdvancedSecurityBilling("octo-org");
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(
                                GitHubBillingBillingApiIntegrationTest.class,
                                "advanced-security-active-committers.json"),
                        GitHubAdvancedSecurityActiveCommitters.class));
        HTTP.verifyRequested(GET, "/orgs/octo-org/settings/billing/advanced-security");
    }

    @Test
    void getAdvancedSecurityBillingWithFilter_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/orgs/octo-org/settings/billing/advanced-security",
                okJson(jsonBytes(
                        GitHubBillingBillingApiIntegrationTest.class, "advanced-security-active-committers.json")));

        final GitHubAdvancedSecurityActiveCommitters result;
        try (final GitHubApiWireMockSupport.BillingBillingApiClient api = billingBillingApi(HTTP)) {
            result = api.api()
                    .getAdvancedSecurityBilling(
                            "octo-org",
                            BillingCatalogFixtures.getGithubAdvancedSecurityBillingOrgFilter(),
                            GitHubPageRequest.byPage());
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(
                                GitHubBillingBillingApiIntegrationTest.class,
                                "advanced-security-active-committers.json"),
                        GitHubAdvancedSecurityActiveCommitters.class));
        HTTP.verifyRequested(GET, "/orgs/octo-org/settings/billing/advanced-security");
        HTTP.verifyQueryParam(
                GET, "/orgs/octo-org/settings/billing/advanced-security", "advanced_security_product", "code_security");
    }
}
