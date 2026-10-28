package io.github.piotr_janczukowicz.github.api.gitignore;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubGitignoreApiTest {

    private static final String TEMPLATE_NAME = "C";
    private static final String TEMPLATES_JSON = "[\"Actionscript\",\"Android\",\"C\"]";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubGitignoreApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void templates_shouldReturnList_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), TEMPLATES_JSON));

        final List<String> templates = api().templates();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(templates).containsExactly("Actionscript", "Android", "C");
            softly.assertThat(requestUri).hasToString("https://api.github.com/gitignore/templates");
        });
    }

    @Test
    void templates_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final GitHubGitignoreApi api = api();

        final Throwable thrown = catchThrowable(api::templates);

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void get_shouldReturnTemplate_whenPresent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("gitignore-template.json")));

        final Optional<GitHubGitignoreTemplate> template = api().get(TEMPLATE_NAME);
        final URI requestUri = captureSingleRequest().uri();
        final GitHubGitignoreTemplate expected = GitignoreCatalogFixtures.gitignoreTemplate();

        assertSoftly(softly -> {
            softly.assertThat(template).contains(expected);
            softly.assertThat(requestUri).hasToString("https://api.github.com/gitignore/templates/C");
        });
    }

    @Test
    void get_shouldEncodeNamePath_whenNameContainsReservedCharacters() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("gitignore-template.json")));

        api().get("a b/c");
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasToString("https://api.github.com/gitignore/templates/a%20b%2Fc");
    }

    @Test
    void get_shouldReturnEmpty_whenNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubGitignoreTemplate> template = api().get(TEMPLATE_NAME);

        assertThat(template).isEmpty();
    }

    @Test
    void get_shouldThrow_whenNameNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubGitignoreApi gitignoreApi = new GitHubGitignoreApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> gitignoreApi.get(null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
        }
    }

    @Test
    void get_shouldThrow_whenNameBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubGitignoreApi gitignoreApi = new GitHubGitignoreApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> gitignoreApi.get(" "));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("name");
        }
    }

    private GitHubGitignoreApi api() {
        return new GitHubGitignoreApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubGitignoreApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
