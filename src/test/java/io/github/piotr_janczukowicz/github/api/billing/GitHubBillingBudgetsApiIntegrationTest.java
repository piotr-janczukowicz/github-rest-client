package io.github.piotr_janczukowicz.github.api.billing;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.billingBudgetsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubBillingBudgetsApiIntegrationTest {

    private static final String ORG = "octo-org";
    private static final String BUDGET_ID = "2066deda-923f-43f9-88d2-62395a28c0cd";
    private static final String COLLECTION_PATH = "/organizations/octo-org/settings/billing/budgets";
    private static final String ITEM_PATH = COLLECTION_PATH + "/" + BUDGET_ID;

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listBudgets_shouldReturnBudgets_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-all-budgets.json")));

        final GitHubPage<GitHubBudget> page;
        try (final GitHubApiWireMockSupport.BillingBudgetsApiClient api = billingBudgetsApi(HTTP)) {
            page = api.api().listBudgets(ORG);
        }

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(expectedGetAllBudgets().budgets());
            softly.assertThat(page.totalCount())
                    .isEqualTo(expectedGetAllBudgets().totalCount());
        });
        HTTP.verifyRequested(GET, COLLECTION_PATH);
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "per_page", "100");
    }

    @Test
    void listBudgets_shouldReturnNullTotalCount_whenResponseOmitsTotalCount() throws Exception {
        HTTP.stub(GET, COLLECTION_PATH, okJson("{\"budgets\":[]}".getBytes(StandardCharsets.UTF_8)));

        final GitHubPage<GitHubBudget> page;
        try (final GitHubApiWireMockSupport.BillingBudgetsApiClient api = billingBudgetsApi(HTTP)) {
            page = api.api().listBudgets(ORG);
        }

        assertSoftly(softly -> {
            softly.assertThat(page.items()).isEmpty();
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void createOrganizationBudget_shouldReturnCreatedBudget_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                COLLECTION_PATH,
                okJson(jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "create-budget.json")));

        final GitHubCreateBudget created;
        try (final GitHubApiWireMockSupport.BillingBudgetsApiClient api = billingBudgetsApi(HTTP)) {
            created = api.api().createOrganizationBudget(ORG, BillingCatalogFixtures.createOrganizationBudgetPayload());
        }

        assertThat(created)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "create-budget.json"),
                        GitHubCreateBudget.class));
        HTTP.verifyBody(
                POST,
                COLLECTION_PATH,
                new String(
                        CODEC.encode(BillingCatalogFixtures.createOrganizationBudgetPayload()),
                        StandardCharsets.UTF_8));
    }

    @Test
    void getBudget_shouldReturnBudget_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ITEM_PATH, okJson(jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-budget.json")));

        final GitHubGetBudget budget;
        try (final GitHubApiWireMockSupport.BillingBudgetsApiClient api = billingBudgetsApi(HTTP)) {
            budget = api.api().getBudget(ORG, BUDGET_ID).orElseThrow();
        }

        assertThat(budget)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-budget.json"),
                        GitHubGetBudget.class));
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void updateBudget_shouldReturnUpdatedBudget_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                ITEM_PATH,
                okJson(jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "update-budget.json")));

        final GitHubUpdateBudget updated;
        try (final GitHubApiWireMockSupport.BillingBudgetsApiClient api = billingBudgetsApi(HTTP)) {
            updated = api.api().updateBudget(ORG, BUDGET_ID, BillingCatalogFixtures.updateBudgetOrgPayload());
        }

        assertThat(updated)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "update-budget.json"),
                        GitHubUpdateBudget.class));
        HTTP.verifyBody(
                PATCH,
                ITEM_PATH,
                new String(CODEC.encode(BillingCatalogFixtures.updateBudgetOrgPayload()), StandardCharsets.UTF_8));
    }

    @Test
    void deleteBudget_shouldReturnDeletionResult_whenResponseIsOk() throws Exception {
        HTTP.stub(
                DELETE,
                ITEM_PATH,
                okJson(jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "delete-budget.json")));

        final GitHubDeleteBudget deleted;
        try (final GitHubApiWireMockSupport.BillingBudgetsApiClient api = billingBudgetsApi(HTTP)) {
            deleted = api.api().deleteBudget(ORG, BUDGET_ID);
        }

        assertThat(deleted)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "delete-budget.json"),
                        GitHubDeleteBudget.class));
        HTTP.verifyRequested(DELETE, ITEM_PATH);
    }

    @Test
    void listEnterpriseBudgets_shouldReturnItems_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/budgets",
                okJson(jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-all-budgets.json")));

        final GitHubPage<GitHubBudget> page;
        try (final GitHubApiWireMockSupport.BillingBudgetsApiClient api = billingBudgetsApi(HTTP)) {
            page = api.api()
                    .listEnterpriseBudgets(
                            "octo-enterprise",
                            BillingCatalogFixtures.getAllBudgetsFilter(),
                            GitHubPageRequest.byPage());
        }

        assertThat(page.items())
                .isEqualTo(CODEC.decode(
                                jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-all-budgets.json"),
                                GitHubGetAllBudgets.class)
                        .budgets());
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/budgets");
        HTTP.verifyQueryParam(GET, "/enterprises/octo-enterprise/settings/billing/budgets", "scope", "enterprise");
    }

    @Test
    void createEnterpriseBudget_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                "/enterprises/octo-enterprise/settings/billing/budgets",
                okJson(jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "create-budget.json")));

        final GitHubCreateBudget result;
        try (final GitHubApiWireMockSupport.BillingBudgetsApiClient api = billingBudgetsApi(HTTP)) {
            result = api.api().createEnterpriseBudget("octo-enterprise", BillingCatalogFixtures.createBudgetPayload());
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "create-budget.json"),
                        GitHubCreateBudget.class));
        HTTP.verifyBody(
                POST,
                "/enterprises/octo-enterprise/settings/billing/budgets",
                new String(CODEC.encode(BillingCatalogFixtures.createBudgetPayload()), StandardCharsets.UTF_8));
    }

    @Test
    void getEnterpriseBudget_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/budgets/2066deda-923f-43f9-88d2-62395a28c0cd",
                okJson(jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-budget.json")));

        final GitHubGetBudget result;
        try (final GitHubApiWireMockSupport.BillingBudgetsApiClient api = billingBudgetsApi(HTTP)) {
            result = api.api()
                    .getEnterpriseBudget("octo-enterprise", "2066deda-923f-43f9-88d2-62395a28c0cd")
                    .orElseThrow();
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-budget.json"),
                        GitHubGetBudget.class));
        HTTP.verifyRequested(
                GET, "/enterprises/octo-enterprise/settings/billing/budgets/2066deda-923f-43f9-88d2-62395a28c0cd");
    }

    @Test
    void updateEnterpriseBudget_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                "/enterprises/octo-enterprise/settings/billing/budgets/2066deda-923f-43f9-88d2-62395a28c0cd",
                okJson(jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "update-budget.json")));

        final GitHubUpdateBudget result;
        try (final GitHubApiWireMockSupport.BillingBudgetsApiClient api = billingBudgetsApi(HTTP)) {
            result = api.api()
                    .updateEnterpriseBudget(
                            "octo-enterprise",
                            "2066deda-923f-43f9-88d2-62395a28c0cd",
                            BillingCatalogFixtures.updateBudgetPayload());
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "update-budget.json"),
                        GitHubUpdateBudget.class));
        HTTP.verifyBody(
                PATCH,
                "/enterprises/octo-enterprise/settings/billing/budgets/2066deda-923f-43f9-88d2-62395a28c0cd",
                new String(CODEC.encode(BillingCatalogFixtures.updateBudgetPayload()), StandardCharsets.UTF_8));
    }

    @Test
    void deleteEnterpriseBudget_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                DELETE,
                "/enterprises/octo-enterprise/settings/billing/budgets/2066deda-923f-43f9-88d2-62395a28c0cd",
                okJson(jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "delete-budget.json")));

        final GitHubDeleteBudget result;
        try (final GitHubApiWireMockSupport.BillingBudgetsApiClient api = billingBudgetsApi(HTTP)) {
            result = api.api().deleteEnterpriseBudget("octo-enterprise", "2066deda-923f-43f9-88d2-62395a28c0cd");
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "delete-budget.json"),
                        GitHubDeleteBudget.class));
        HTTP.verifyRequested(
                DELETE, "/enterprises/octo-enterprise/settings/billing/budgets/2066deda-923f-43f9-88d2-62395a28c0cd");
    }

    @Test
    void listEnterpriseBudgetUserStates_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/budgets/2066deda-923f-43f9-88d2-62395a28c0cd/user-states",
                okJson(jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-budget-user-states.json")));

        final GitHubGetBudgetUserStates result;
        try (final GitHubApiWireMockSupport.BillingBudgetsApiClient api = billingBudgetsApi(HTTP)) {
            result = api.api()
                    .listEnterpriseBudgetUserStates(
                            "octo-enterprise",
                            "2066deda-923f-43f9-88d2-62395a28c0cd",
                            BillingCatalogFixtures.getBudgetUserStatesFilter(),
                            GitHubPageRequest.byPage())
                    .orElseThrow();
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-budget-user-states.json"),
                        GitHubGetBudgetUserStates.class));
        HTTP.verifyRequested(
                GET,
                "/enterprises/octo-enterprise/settings/billing/budgets/2066deda-923f-43f9-88d2-62395a28c0cd/user-states");
        HTTP.verifyQueryParam(
                GET,
                "/enterprises/octo-enterprise/settings/billing/budgets/2066deda-923f-43f9-88d2-62395a28c0cd/user-states",
                "sort_order",
                "1");
    }

    @Test
    void listEnterpriseBudgetsWithoutFilter_shouldReturnItems_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/budgets",
                okJson(jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-all-budgets.json")));

        final GitHubPage<GitHubBudget> page;
        try (final GitHubApiWireMockSupport.BillingBudgetsApiClient api = billingBudgetsApi(HTTP)) {
            page = api.api().listEnterpriseBudgets("octo-enterprise");
        }

        assertThat(page.items())
                .isEqualTo(CODEC.decode(
                                jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-all-budgets.json"),
                                GitHubGetAllBudgets.class)
                        .budgets());
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/budgets");
    }

    @Test
    void listEnterpriseBudgetsWithFilter_shouldReturnItems_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/budgets",
                okJson(jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-all-budgets.json")));

        final GitHubPage<GitHubBudget> page;
        try (final GitHubApiWireMockSupport.BillingBudgetsApiClient api = billingBudgetsApi(HTTP)) {
            page = api.api().listEnterpriseBudgets("octo-enterprise", BillingCatalogFixtures.getAllBudgetsFilter());
        }

        assertThat(page.items())
                .isEqualTo(CODEC.decode(
                                jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-all-budgets.json"),
                                GitHubGetAllBudgets.class)
                        .budgets());
        HTTP.verifyRequested(GET, "/enterprises/octo-enterprise/settings/billing/budgets");
        HTTP.verifyQueryParam(GET, "/enterprises/octo-enterprise/settings/billing/budgets", "scope", "enterprise");
    }

    @Test
    void listEnterpriseBudgetUserStatesWithoutFilter_shouldReturnValue_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                "/enterprises/octo-enterprise/settings/billing/budgets/2066deda-923f-43f9-88d2-62395a28c0cd/user-states",
                okJson(jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-budget-user-states.json")));

        final GitHubGetBudgetUserStates result;
        try (final GitHubApiWireMockSupport.BillingBudgetsApiClient api = billingBudgetsApi(HTTP)) {
            result = api.api()
                    .listEnterpriseBudgetUserStates("octo-enterprise", "2066deda-923f-43f9-88d2-62395a28c0cd")
                    .orElseThrow();
        }

        assertThat(result)
                .isEqualTo(CODEC.decode(
                        jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-budget-user-states.json"),
                        GitHubGetBudgetUserStates.class));
        HTTP.verifyRequested(
                GET,
                "/enterprises/octo-enterprise/settings/billing/budgets/2066deda-923f-43f9-88d2-62395a28c0cd/user-states");
    }

    private static GitHubGetAllBudgets expectedGetAllBudgets() {
        return CODEC.decode(
                jsonBytes(GitHubBillingBudgetsApiIntegrationTest.class, "get-all-budgets.json"),
                GitHubGetAllBudgets.class);
    }
}
