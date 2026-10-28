package io.github.piotr_janczukowicz.github.api.meta;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpStatus.OK;
import static io.github.piotr_janczukowicz.test.http.StubHttpServer.status;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.metaApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubMetaApiIntegrationTest {

    private static final String ROOT_PATH = "/";
    private static final String META_PATH = "/meta";
    private static final String OCTOCAT_PATH = "/octocat";
    private static final String VERSIONS_PATH = "/versions";
    private static final String ZEN_PATH = "/zen";

    private static final String OCTOCAT_ASCII =
            "               MMM.           .MMM\n               MMMMMMMMMMMMMMMMMMM";
    private static final String VERSIONS_JSON = "[\"2026-03-10\",\"2022-11-28\"]";
    private static final String ZEN_MESSAGE = "Responsive is better than fast.";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void root_shouldReturnApiRoot_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ROOT_PATH, okJson(jsonBytes(GitHubMetaApiIntegrationTest.class, "api-root.json")));

        final GitHubApiRoot root;
        try (final GitHubApiWireMockSupport.MetaApiClient meta = metaApi(HTTP)) {
            root = meta.api().root();
        }

        final GitHubApiRoot expected =
                CODEC.decode(jsonBytes(GitHubMetaApiIntegrationTest.class, "api-root.json"), GitHubApiRoot.class);

        assertThat(root).isEqualTo(expected);
    }

    @Test
    void get_shouldReturnApiOverview_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, META_PATH, okJson(jsonBytes(GitHubMetaApiIntegrationTest.class, "api-overview.json")));

        final GitHubApiOverview overview;
        try (final GitHubApiWireMockSupport.MetaApiClient meta = metaApi(HTTP)) {
            overview = meta.api().get();
        }

        final GitHubApiOverview expected = CODEC.decode(
                jsonBytes(GitHubMetaApiIntegrationTest.class, "api-overview.json"), GitHubApiOverview.class);

        assertThat(overview).isEqualTo(expected);
    }

    @Test
    void octocat_shouldReturnAsciiArt_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                OCTOCAT_PATH,
                status(OK)
                        .header(CONTENT_TYPE, "application/octocat-stream")
                        .body(OCTOCAT_ASCII.getBytes(StandardCharsets.UTF_8)));

        final String octocat;
        try (final GitHubApiWireMockSupport.MetaApiClient meta = metaApi(HTTP)) {
            octocat = meta.api().octocat();
        }

        assertThat(octocat).isEqualTo(OCTOCAT_ASCII);
        HTTP.verifyHeader(GET, OCTOCAT_PATH, ACCEPT, "application/octocat-stream");
    }

    @Test
    void octocat_shouldSendSpeechBubble_whenFilterHasS() throws Exception {
        HTTP.stub(
                GET,
                OCTOCAT_PATH,
                status(OK)
                        .header(CONTENT_TYPE, "application/octocat-stream")
                        .body(OCTOCAT_ASCII.getBytes(StandardCharsets.UTF_8)));
        final GitHubGetOctocatFilter filter =
                GitHubGetOctocatFilterBuilder.builder().s("Hello").build();

        final String octocat;
        try (final GitHubApiWireMockSupport.MetaApiClient meta = metaApi(HTTP)) {
            octocat = meta.api().octocat(filter);
        }

        assertThat(octocat).isEqualTo(OCTOCAT_ASCII);
        HTTP.verifyQueryParam(GET, OCTOCAT_PATH, "s", "Hello");
        HTTP.verifyHeader(GET, OCTOCAT_PATH, ACCEPT, "application/octocat-stream");
    }

    @Test
    void versions_shouldReturnList_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, VERSIONS_PATH, okJson(VERSIONS_JSON.getBytes(StandardCharsets.UTF_8)));

        final List<String> versions;
        try (final GitHubApiWireMockSupport.MetaApiClient meta = metaApi(HTTP)) {
            versions = meta.api().versions();
        }

        assertThat(versions).containsExactly("2026-03-10", "2022-11-28");
    }

    @Test
    void versions_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, VERSIONS_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.MetaApiClient meta = metaApi(HTTP)) {
            thrown = catchThrowable(() -> meta.api().versions());
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, VERSIONS_PATH);
    }

    @Test
    void zen_shouldReturnSentence_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ZEN_PATH,
                status(OK).header(CONTENT_TYPE, "text/plain").body(ZEN_MESSAGE.getBytes(StandardCharsets.UTF_8)));

        final String zen;
        try (final GitHubApiWireMockSupport.MetaApiClient meta = metaApi(HTTP)) {
            zen = meta.api().zen();
        }

        assertThat(zen).isEqualTo(ZEN_MESSAGE);
        HTTP.verifyHeader(GET, ZEN_PATH, ACCEPT, GitHubClient.DEFAULT_ACCEPT);
    }
}
