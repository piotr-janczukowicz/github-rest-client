package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCodespacesOrganizationsApiTest {

    private static final String ORG = "octo-org";
    private static final String USERNAME = "johnDoe";
    private static final String CODESPACE_NAME = "johnDoe-octo-abc123";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodespacesOrganizationsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listInOrganization_shouldReturnCodespaces_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespaces-page.json")));

        final GitHubPage<GitHubCodespace> page = api().listInOrganization(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            CodespacesCatalogFixtures.codespacesPage().codespaces());
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/orgs/octo-org/codespaces?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listInOrganization_shouldSendPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespaces-page.json")));

        api().listInOrganization(ORG, GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "1");
    }

    @Test
    void setCodespacesAccess_shouldPutPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setCodespacesAccess(ORG, CodespacesCatalogFixtures.setCodespacesAccessPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/octo-org/codespaces/access");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void setCodespacesAccessUsers_shouldPostPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setCodespacesAccessUsers(ORG, CodespacesCatalogFixtures.setCodespacesAccessUsersPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/octo-org/codespaces/access/selected_users");
        });
    }

    @Test
    void deleteCodespacesAccessUsers_shouldDeletePayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteCodespacesAccessUsers(ORG, CodespacesCatalogFixtures.deleteCodespacesAccessUsersPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/octo-org/codespaces/access/selected_users");
        });
    }

    @Test
    void listForUserInOrganization_shouldReturnCodespaces_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespaces-page.json")));

        final GitHubPage<GitHubCodespace> page = api().listForUserInOrganization(ORG, USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            CodespacesCatalogFixtures.codespacesPage().codespaces());
            softly.assertThat(requestUri.getPath()).isEqualTo("/orgs/octo-org/members/johnDoe/codespaces");
        });
    }

    @Test
    void deleteFromOrganization_shouldDelete_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        api().deleteFromOrganization(ORG, USERNAME, CODESPACE_NAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/orgs/octo-org/members/johnDoe/codespaces/johnDoe-octo-abc123");
        });
    }

    @Test
    void stopInOrganization_shouldPostStop_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespace.json")));

        final GitHubCodespace codespace = api().stopInOrganization(ORG, USERNAME, CODESPACE_NAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(codespace).isEqualTo(CodespacesCatalogFixtures.codespace());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/orgs/octo-org/members/johnDoe/codespaces/johnDoe-octo-abc123/stop");
        });
    }

    private GitHubCodespacesOrganizationsApi api() {
        return new GitHubCodespacesOrganizationsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCodespacesOrganizationsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
