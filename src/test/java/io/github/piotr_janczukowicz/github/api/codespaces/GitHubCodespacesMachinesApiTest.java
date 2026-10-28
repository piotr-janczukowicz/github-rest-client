package io.github.piotr_janczukowicz.github.api.codespaces;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
class GitHubCodespacesMachinesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final String CODESPACE_NAME = "octocat-hello-world-abc123";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodespacesMachinesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listRepoMachines_shouldReturnMachines_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespace-machines-page.json")));

        final GitHubCodespaceMachinesPage page = api().listRepoMachines(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page).isEqualTo(CodespacesCatalogFixtures.codespaceMachinesPage());
            softly.assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/hello-world/codespaces/machines");
        });
    }

    @Test
    void listRepoMachines_shouldSendFilter_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespace-machines-page.json")));

        api().listRepoMachines(OWNER, REPO, CodespacesCatalogFixtures.repoMachinesForAuthenticatedUserFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasParameter("location", "WestUs2")
                .hasParameter("client_ip", "1.2.3.4")
                .hasParameter("ref", "main");
    }

    @Test
    void listCodespaceMachines_shouldReturnMachines_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespace-machines-page.json")));

        final GitHubCodespaceMachinesPage page = api().listCodespaceMachines(CODESPACE_NAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page).isEqualTo(CodespacesCatalogFixtures.codespaceMachinesPage());
            softly.assertThat(requestUri.getPath()).isEqualTo("/user/codespaces/octocat-hello-world-abc123/machines");
        });
    }

    private GitHubCodespacesMachinesApi api() {
        return new GitHubCodespacesMachinesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCodespacesMachinesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
