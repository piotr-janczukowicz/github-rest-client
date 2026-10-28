package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.billingCostCentersApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubBillingCostCentersApiIntegrationTest {

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getAllCostCenters_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/cost-centers",
                okJson(jsonBytes(GitHubBillingCostCentersApiIntegrationTest.class, "get-all-cost-centers.json")));

        final GitHubGetAllCostCenters result;
        try (final GitHubApiWireMockSupport.BillingCostCentersApiClient api = billingCostCentersApi(HTTP)) {
            result = api.api().getAllCostCenters("octo-enterprise", BillingCatalogFixtures.getAllCostCentersFilter());
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingCostCentersApiIntegrationTest.class, "get-all-cost-centers.json"),
                        GitHubGetAllCostCenters.class));
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/cost-centers");
        HTTP.verifyQueryParam(GET, "/enterprises/octo-enterprise/settings/billing/cost-centers", "state", "active");
    }

    @Test
    void createCostCenter_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                "/enterprises/octo-enterprise/settings/billing/cost-centers",
                okJson(jsonBytes(
                        GitHubBillingCostCentersApiIntegrationTest.class, "create-cost-center-response.json")));

        final GitHubCreateCostCenterResponse result;
        try (final GitHubApiWireMockSupport.BillingCostCentersApiClient api = billingCostCentersApi(HTTP)) {
            result = api.api().createCostCenter("octo-enterprise", BillingCatalogFixtures.createCostCenterPayload());
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingCostCentersApiIntegrationTest.class, "create-cost-center-response.json"),
                        GitHubCreateCostCenterResponse.class));
        HTTP.verifyBody(
                POST,
                "/enterprises/octo-enterprise/settings/billing/cost-centers",
                new String(CODEC.encode(BillingCatalogFixtures.createCostCenterPayload()), StandardCharsets.UTF_8));
    }

    @Test
    void getCostCenter_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/cost-centers/cc-1a2b3c",
                okJson(jsonBytes(GitHubBillingCostCentersApiIntegrationTest.class, "get-cost-center.json")));

        final GitHubGetCostCenter result;
        try (final GitHubApiWireMockSupport.BillingCostCentersApiClient api = billingCostCentersApi(HTTP)) {
            result = api.api().getCostCenter("octo-enterprise", "cc-1a2b3c").orElseThrow();
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingCostCentersApiIntegrationTest.class, "get-cost-center.json"),
                        GitHubGetCostCenter.class));
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/cost-centers/cc-1a2b3c");
    }

    @Test
    void getCostCenterWithRequest_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/cost-centers/cc-1a2b3c",
                okJson(jsonBytes(GitHubBillingCostCentersApiIntegrationTest.class, "get-cost-center.json")));

        final GitHubGetCostCenter result;
        try (final GitHubApiWireMockSupport.BillingCostCentersApiClient api = billingCostCentersApi(HTTP)) {
            result = api.api()
                    .getCostCenter("octo-enterprise", "cc-1a2b3c", GitHubPageRequest.byPage())
                    .orElseThrow();
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingCostCentersApiIntegrationTest.class, "get-cost-center.json"),
                        GitHubGetCostCenter.class));
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/cost-centers/cc-1a2b3c");
    }

    @Test
    void updateCostCenter_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                "/enterprises/octo-enterprise/settings/billing/cost-centers/cc-1a2b3c",
                okJson(jsonBytes(GitHubBillingCostCentersApiIntegrationTest.class, "update-cost-center.json")));

        final GitHubUpdateCostCenter result;
        try (final GitHubApiWireMockSupport.BillingCostCentersApiClient api = billingCostCentersApi(HTTP)) {
            result = api.api()
                    .updateCostCenter("octo-enterprise", "cc-1a2b3c", BillingCatalogFixtures.updateCostCenterPayload());
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingCostCentersApiIntegrationTest.class, "update-cost-center.json"),
                        GitHubUpdateCostCenter.class));
        HTTP.verifyBody(
                PATCH,
                "/enterprises/octo-enterprise/settings/billing/cost-centers/cc-1a2b3c",
                new String(CODEC.encode(BillingCatalogFixtures.updateCostCenterPayload()), StandardCharsets.UTF_8));
    }

    @Test
    void deleteCostCenter_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                DELETE,
                "/enterprises/octo-enterprise/settings/billing/cost-centers/cc-1a2b3c",
                okJson(jsonBytes(GitHubBillingCostCentersApiIntegrationTest.class, "delete-cost-center.json")));

        final GitHubDeleteCostCenter result;
        try (final GitHubApiWireMockSupport.BillingCostCentersApiClient api = billingCostCentersApi(HTTP)) {
            result = api.api().deleteCostCenter("octo-enterprise", "cc-1a2b3c");
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingCostCentersApiIntegrationTest.class, "delete-cost-center.json"),
                        GitHubDeleteCostCenter.class));
        HTTP.verifyRequested(DELETE, "/enterprises/octo-enterprise/settings/billing/cost-centers/cc-1a2b3c");
    }

    @Test
    void addResourceToCostCenter_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                "/enterprises/octo-enterprise/settings/billing/cost-centers/cc-1a2b3c/resource",
                okJson(jsonBytes(
                        GitHubBillingCostCentersApiIntegrationTest.class,
                        "add-resource-to-cost-center-response.json")));

        final GitHubAddResourceToCostCenterResponse result;
        try (final GitHubApiWireMockSupport.BillingCostCentersApiClient api = billingCostCentersApi(HTTP)) {
            result = api.api()
                    .addResourceToCostCenter(
                            "octo-enterprise", "cc-1a2b3c", BillingCatalogFixtures.addResourceToCostCenterPayload());
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(
                                GitHubBillingCostCentersApiIntegrationTest.class,
                                "add-resource-to-cost-center-response.json"),
                        GitHubAddResourceToCostCenterResponse.class));
        HTTP.verifyBody(
                POST,
                "/enterprises/octo-enterprise/settings/billing/cost-centers/cc-1a2b3c/resource",
                new String(
                        CODEC.encode(BillingCatalogFixtures.addResourceToCostCenterPayload()), StandardCharsets.UTF_8));
    }

    @Test
    void removeResourceFromCostCenter_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                DELETE,
                "/enterprises/octo-enterprise/settings/billing/cost-centers/cc-1a2b3c/resource",
                okJson(jsonBytes(
                        GitHubBillingCostCentersApiIntegrationTest.class,
                        "remove-resource-from-cost-center-response.json")));

        final GitHubRemoveResourceFromCostCenterResponse result;
        try (final GitHubApiWireMockSupport.BillingCostCentersApiClient api = billingCostCentersApi(HTTP)) {
            result = api.api()
                    .removeResourceFromCostCenter(
                            "octo-enterprise",
                            "cc-1a2b3c",
                            BillingCatalogFixtures.removeResourceFromCostCenterPayload());
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(
                                GitHubBillingCostCentersApiIntegrationTest.class,
                                "remove-resource-from-cost-center-response.json"),
                        GitHubRemoveResourceFromCostCenterResponse.class));
        HTTP.verifyBody(
                DELETE,
                "/enterprises/octo-enterprise/settings/billing/cost-centers/cc-1a2b3c/resource",
                new String(
                        CODEC.encode(BillingCatalogFixtures.removeResourceFromCostCenterPayload()),
                        StandardCharsets.UTF_8));
    }

    @Test
    void getAllCostCentersWithoutFilter_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/cost-centers",
                okJson(jsonBytes(GitHubBillingCostCentersApiIntegrationTest.class, "get-all-cost-centers.json")));

        final GitHubGetAllCostCenters result;
        try (final GitHubApiWireMockSupport.BillingCostCentersApiClient api = billingCostCentersApi(HTTP)) {
            result = api.api().getAllCostCenters("octo-enterprise");
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingCostCentersApiIntegrationTest.class, "get-all-cost-centers.json"),
                        GitHubGetAllCostCenters.class));
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/cost-centers");
    }
}
