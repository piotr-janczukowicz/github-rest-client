package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubPushRuleBypassRequest;
import io.github.piotr_janczukowicz.github.api.secretscanning.SecretScanningCatalogFixtures;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
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
class GitHubRepositoriesBypassRequestsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoriesBypassRequestsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listRepoPushBypassRequests_shouldSendFilterAndPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("push-rule-bypass-request.json") + "]"));

        final GitHubPage<GitHubPushRuleBypassRequest> page = api().listRepoPushBypassRequests(
                        OWNER,
                        REPO,
                        RepositoriesCatalogFixtures.listRepoPushBypassRequestsFilter(),
                        GitHubPageNumberRequest.of(2, 50));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(SecretScanningCatalogFixtures.pushRuleBypassRequest());
            softly.assertThat(requestUri)
                    .hasParameter("time_period", "week")
                    .hasParameter("request_status", "open")
                    .hasParameter("page", "2")
                    .hasParameter("per_page", "50");
        });
    }

    @Test
    void listRepoPushBypassRequests_shouldUseDefaultPage_whenNoRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listRepoPushBypassRequests(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/Hello-World/bypass-requests/push-rules");
    }

    @Test
    void listRepoPushBypassRequests_shouldThrow_whenFilterNull() {
        final Throwable thrown =
                catchThrowable(() -> api().listRepoPushBypassRequests(OWNER, REPO, null, GitHubPageRequest.byPage()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void getRepoPushBypassRequest_shouldReturnRequest_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("push-rule-bypass-request.json")));

        final Optional<GitHubPushRuleBypassRequest> request = api().getRepoPushBypassRequest(OWNER, REPO, 1);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(request).contains(SecretScanningCatalogFixtures.pushRuleBypassRequest());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/bypass-requests/push-rules/1");
        });
    }

    @Test
    void getRepoPushBypassRequest_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubPushRuleBypassRequest> request = api().getRepoPushBypassRequest(OWNER, REPO, 1);

        assertThat(request).isEmpty();
    }

    private GitHubRepositoriesBypassRequestsApi api() {
        return new GitHubRepositoriesBypassRequestsApi(TestClients.caller(transport));
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubRepositoriesBypassRequestsApiTest.class, fileName);
    }
}
