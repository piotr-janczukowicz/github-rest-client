package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubPushRuleBypassRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubOrganizationsBypassRequestsApiTest {

    private static final String ORG = "github";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsBypassRequestsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listPushBypassRequests_shouldReturnFirstPage_whenFilterOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        final GitHubPage<GitHubPushRuleBypassRequest> page = api().listPushBypassRequests(ORG);

        assertThat(page.items()).isEmpty();
        assertThat(captureSingleRequest().uri().getPath()).isEqualTo("/orgs/github/bypass-requests/push-rules");
    }

    @Test
    void listPushBypassRequests_shouldSendFilterAndPageNumber_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listPushBypassRequests(
                        ORG,
                        OrganizationsCatalogFixtures.listPushBypassRequestsFilter(),
                        GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri())
                .hasParameter("repository_name", "octo-repo")
                .hasParameter("request_status", "open")
                .hasParameter("page", "2");
    }

    @Test
    void listPushBypassRequests_shouldThrow_whenFilterNull() {
        final GitHubOrganizationsBypassRequestsApi api = api();
        final GitHubPageRequest page = GitHubPageRequest.byPage();

        final Throwable thrown = catchThrowable(() -> api.listPushBypassRequests(ORG, null, page));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    private GitHubOrganizationsBypassRequestsApi api() {
        return new GitHubOrganizationsBypassRequestsApi(TestClients.caller(transport));
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
