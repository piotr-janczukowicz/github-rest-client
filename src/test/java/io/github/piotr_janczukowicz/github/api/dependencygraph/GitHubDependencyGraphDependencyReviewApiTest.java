package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.http.HttpMethod;
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
class GitHubDependencyGraphDependencyReviewApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String BASEHEAD = "main...feature";
    private static final String COMPARE_URI =
            "https://api.github.com/repos/octocat/Hello-World/dependency-graph/compare/main...feature";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDependencyGraphDependencyReviewApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void diffRange_shouldReturnEntries_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("dependency-graph-diff-entry.json") + "]"));

        final List<GitHubDependencyGraphDiffEntry> entries = api().diffRange(OWNER, REPO, BASEHEAD);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(entries).containsExactly(DependencyGraphCatalogFixtures.dependencyGraphDiffEntry());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(COMPARE_URI);
        });
    }

    @Test
    void diffRange_shouldSendNameQuery_whenFilterHasName() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        final List<GitHubDependencyGraphDiffEntry> entries =
                api().diffRange(OWNER, REPO, BASEHEAD, DependencyGraphCatalogFixtures.diffRangeFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(entries).isEmpty();
            softly.assertThat(requestUri).hasToString(COMPARE_URI + "?name=package.json");
        });
    }

    @Test
    void diffRange_shouldEncodeSlashInBasehead_whenBranchNameHasSlash() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().diffRange(OWNER, REPO, "release/1...feature/x");
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getRawPath()).endsWith("/dependency-graph/compare/release%2F1...feature%2Fx");
    }

    @Test
    void diffRange_shouldThrow_whenFilterNull() {
        final GitHubDependencyGraphDependencyReviewApi api = api();

        final Throwable thrown = catchThrowable(() -> api.diffRange(OWNER, REPO, BASEHEAD, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    private GitHubDependencyGraphDependencyReviewApi api() {
        return new GitHubDependencyGraphDependencyReviewApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubDependencyGraphDependencyReviewApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
