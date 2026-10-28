package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
class GitHubAppsMarketplaceApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubAppsMarketplaceApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getSubscriptionPlanForAccount_shouldReturnPurchase_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("marketplace-purchase.json")));

        final GitHubMarketplacePurchase purchase =
                api().getSubscriptionPlanForAccount(1L).orElseThrow();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(purchase).isEqualTo(AppsCatalogFixtures.marketplacePurchase());
            softly.assertThat(requestUri).hasToString("https://api.github.com/marketplace_listing/accounts/1");
        });
    }

    @Test
    void getSubscriptionPlanForAccount_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubMarketplacePurchase> result = api().getSubscriptionPlanForAccount(1L);

        assertThat(result).isEmpty();
    }

    @Test
    void getSubscriptionPlanForAccountStubbed_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("marketplace-purchase.json")));

        api().getSubscriptionPlanForAccountStubbed(1L);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void getSubscriptionPlanForAccountStubbed_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubMarketplacePurchase> result = api().getSubscriptionPlanForAccountStubbed(1L);

        assertThat(result).isEmpty();
    }

    @Test
    void listPlans_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("marketplace-listing-plan.json") + "]"));

        api().listPlans(GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void listAccountsForPlan_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("marketplace-purchase.json") + "]"));

        api().listAccountsForPlan(1L, AppsCatalogFixtures.listAccountsForPlanFilter(), GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void listPlansStubbed_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("marketplace-listing-plan.json") + "]"));

        api().listPlansStubbed(GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void listAccountsForPlanStubbed_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("marketplace-purchase.json") + "]"));

        api().listAccountsForPlanStubbed(
                        1L, AppsCatalogFixtures.listAccountsForPlanStubbedFilter(), GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void listSubscriptionsForAuthenticatedUser_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("user-marketplace-purchase.json") + "]"));

        api().listSubscriptionsForAuthenticatedUser(GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.GET);
    }

    @Test
    void listSubscriptionsForAuthenticatedUserStubbed_shouldSendRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("user-marketplace-purchase.json") + "]"));

        api().listSubscriptionsForAuthenticatedUserStubbed(GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.GET);
    }

    private GitHubAppsMarketplaceApi api() {
        return new GitHubAppsMarketplaceApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubAppsMarketplaceApi.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
