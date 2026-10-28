package io.github.piotr_janczukowicz.github.api.gitignore;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.gitignoreApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubGitignoreApiIntegrationTest {

    private static final String TEMPLATE_NAME = "C";
    private static final String TEMPLATES_PATH = "/gitignore/templates";
    private static final String TEMPLATE_PATH = "/gitignore/templates/C";
    private static final String TEMPLATES_JSON = "[\"Actionscript\",\"Android\",\"C\"]";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void templates_shouldReturnNames_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, TEMPLATES_PATH, okJson(TEMPLATES_JSON.getBytes(StandardCharsets.UTF_8)));

        final List<String> templates;
        try (final GitHubApiWireMockSupport.GitignoreApiClient gitignore = gitignoreApi(HTTP)) {
            templates = gitignore.api().templates();
        }

        assertThat(templates).containsExactly("Actionscript", "Android", "C");
        HTTP.verifyRequested(GET, TEMPLATES_PATH);
    }

    @Test
    void templates_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, TEMPLATES_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.GitignoreApiClient gitignore = gitignoreApi(HTTP)) {
            final GitHubGitignoreApi facade = gitignore.api();

            thrown = catchThrowable(facade::templates);
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, TEMPLATES_PATH);
    }

    @Test
    void get_shouldReturnTemplate_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                TEMPLATE_PATH,
                okJson(jsonBytes(GitHubGitignoreApiIntegrationTest.class, "gitignore-template.json")));

        final Optional<GitHubGitignoreTemplate> template;
        try (final GitHubApiWireMockSupport.GitignoreApiClient gitignore = gitignoreApi(HTTP)) {
            template = gitignore.api().get(TEMPLATE_NAME);
        }

        final GitHubGitignoreTemplate expected = CODEC.decode(
                jsonBytes(GitHubGitignoreApiIntegrationTest.class, "gitignore-template.json"),
                GitHubGitignoreTemplate.class);

        assertThat(template).contains(expected);
        HTTP.verifyRequested(GET, TEMPLATE_PATH);
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, TEMPLATE_PATH, notFound());

        final Optional<GitHubGitignoreTemplate> template;
        try (final GitHubApiWireMockSupport.GitignoreApiClient gitignore = gitignoreApi(HTTP)) {
            template = gitignore.api().get(TEMPLATE_NAME);
        }

        assertThat(template).isEmpty();
    }
}
