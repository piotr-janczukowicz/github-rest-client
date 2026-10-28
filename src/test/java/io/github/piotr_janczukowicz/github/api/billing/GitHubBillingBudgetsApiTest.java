package io.github.piotr_janczukowicz.github.api.billing;

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
class GitHubBillingBudgetsApiTest {

    private static final String ORG = "octo-org";
    private static final String BUDGET_ID = "2066deda-923f-43f9-88d2-62395a28c0cd";
    private static final String BUDGETS_URL = "https://api.github.com/organizations/octo-org/settings/billing/budgets";
    private static final String BUDGET_URL = BUDGETS_URL + "/" + BUDGET_ID;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubBillingBudgetsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listBudgets_shouldReturnBudgets_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("get-all-budgets.json")));

        final GitHubPage<GitHubBudget> page = api().listBudgets(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            BillingCatalogFixtures.getAllBudgets().budgets());
            softly.assertThat(page.totalCount()).isEqualTo(1);
            softly.assertThat(requestUri).hasToString(BUDGETS_URL + "?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listBudgets_shouldUseByPageRequest_whenFilterOnlyPassed() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("get-all-budgets.json")));

        api().listBudgets(ORG, BillingCatalogFixtures.getAllBudgetsOrgFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.toString())
                .startsWith(BUDGETS_URL + "?")
                .contains("scope=enterprise")
                .contains("page=1");
    }

    @Test
    void listBudgets_shouldReturnNullTotalCount_whenResponseOmitsTotalCount() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "{\"budgets\":[]}"));

        final GitHubPage<GitHubBudget> page = api().listBudgets(ORG);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).isEmpty();
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listBudgets_shouldSendFilterQuery_whenFilterSet() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("get-all-budgets.json")));

        api().listBudgets(ORG, BillingCatalogFixtures.getAllBudgetsOrgFilter(), GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.toString())
                .startsWith(BUDGETS_URL + "?")
                .contains("scope=enterprise")
                .contains("user=octocat");
    }

    @Test
    void createOrganizationBudget_shouldPostPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("create-budget.json")));

        final GitHubCreateBudget created =
                api().createOrganizationBudget(ORG, BillingCatalogFixtures.createOrganizationBudgetPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(created).isEqualTo(BillingCatalogFixtures.createBudget());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString(BUDGETS_URL);
        });
    }

    @Test
    void createOrganizationBudget_shouldPostWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("create-budget.json")));

        api().createOrganizationBudget(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString(BUDGETS_URL);
        });
    }

    @Test
    void getBudget_shouldReturnBudget_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("get-budget.json")));

        final GitHubGetBudget budget = api().getBudget(ORG, BUDGET_ID).orElseThrow();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(budget).isEqualTo(BillingCatalogFixtures.getBudget());
            softly.assertThat(requestUri).hasToString(BUDGET_URL);
        });
    }

    @Test
    void getBudget_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubGetBudget> result = api().getBudget(ORG, BUDGET_ID);

        assertThat(result).isEmpty();
    }

    @Test
    void updateBudget_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("update-budget.json")));

        final GitHubUpdateBudget updated =
                api().updateBudget(ORG, BUDGET_ID, BillingCatalogFixtures.updateBudgetOrgPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(updated).isEqualTo(BillingCatalogFixtures.updateBudget());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString(BUDGET_URL);
        });
    }

    @Test
    void deleteBudget_shouldReturnResult_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("delete-budget.json")));

        final GitHubDeleteBudget deleted = api().deleteBudget(ORG, BUDGET_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(deleted).isEqualTo(BillingCatalogFixtures.deleteBudget());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString(BUDGET_URL);
        });
    }

    private GitHubBillingBudgetsApi api() {
        return new GitHubBillingBudgetsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubBillingBudgetsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
