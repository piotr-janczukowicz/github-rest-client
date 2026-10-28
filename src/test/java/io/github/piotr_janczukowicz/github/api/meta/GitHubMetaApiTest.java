package io.github.piotr_janczukowicz.github.api.meta;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.ACCEPT;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubMetaApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubMetaApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void root_shouldReturnApiRoot_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("api-root.json")));

        final GitHubApiRoot root = api().root();
        final URI requestUri = captureSingleRequest().uri();
        final GitHubApiRoot expected = MetaCatalogFixtures.apiRoot();

        assertSoftly(softly -> {
            softly.assertThat(root).isEqualTo(expected);
            softly.assertThat(requestUri).hasToString("https://api.github.com/");
        });
    }

    @Test
    void get_shouldReturnApiOverview_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("api-overview.json")));

        final GitHubApiOverview overview = api().get();
        final URI requestUri = captureSingleRequest().uri();
        final GitHubApiOverview expected = MetaCatalogFixtures.apiOverview();

        assertSoftly(softly -> {
            softly.assertThat(overview).isEqualTo(expected);
            softly.assertThat(requestUri.toString()).endsWith("/meta");
        });
    }

    @Test
    void octocat_shouldReturnAsciiArt_whenResponseIsOk() throws Exception {
        final String asciiArt = "               MMM.           .MMM\n               MMMMMMMMMMMMMMMMMMM";
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), asciiArt));

        final String octocat = api().octocat();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(octocat).isEqualTo(asciiArt);
            softly.assertThat(request.headers().get(ACCEPT)).contains("application/octocat-stream");
        });
    }

    @Test
    void octocat_shouldSendSpeechBubble_whenFilterHasS() throws Exception {
        final String asciiArt = "               MMM.           .MMM";
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), asciiArt));
        final GitHubGetOctocatFilter filter =
                GitHubGetOctocatFilterBuilder.builder().s("Hello").build();

        api().octocat(filter);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("s", "Hello");
    }

    @Test
    void octocat_shouldThrow_whenFilterNull() {
        final GitHubMetaApi metaApi = api();

        final Throwable thrown = catchThrowable(() -> metaApi.octocat(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void versions_shouldReturnList_whenResponseIsOk() throws Exception {
        final String versionsJson = "[\"2026-03-10\",\"2022-11-28\"]";
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), versionsJson));

        final List<String> versions = api().versions();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(versions).containsExactly("2026-03-10", "2022-11-28");
            softly.assertThat(requestUri).hasToString("https://api.github.com/versions");
        });
    }

    @Test
    void versions_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().versions());

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void zen_shouldReturnSentence_whenResponseIsOk() throws Exception {
        final String zenMessage = "Responsive is better than fast.";
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), zenMessage));

        final String zen = api().zen();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(zen).isEqualTo(zenMessage);
            softly.assertThat(request.headers().get(ACCEPT)).contains(GitHubClient.DEFAULT_ACCEPT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/zen");
        });
    }

    private GitHubMetaApi api() {
        return new GitHubMetaApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubMetaApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
