package io.github.piotr_janczukowicz.github.api.gists;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubGistsCommentsApiTest {

    private static final String GIST_ID = "aa5a315d61ae9438b18d";
    private static final long COMMENT_ID = 1L;
    private static final String COMMENT_URI = "https://api.github.com/gists/aa5a315d61ae9438b18d/comments/1";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubGistsCommentsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listComments_shouldReturnItems_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("gist-comment.json") + "]"));

        final GitHubPage<GitHubGistComment> page = api().listComments(GIST_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(GistsCatalogFixtures.gistComment());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/gists/aa5a315d61ae9438b18d/comments?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listComments_shouldSendPageAndPerPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("gist-comment.json") + "]"));

        api().listComments(GIST_ID, GitHubPageNumberRequest.of(2, 10));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.uri()).hasParameter("page", "2");
            softly.assertThat(request.uri()).hasParameter("per_page", "10");
        });
    }

    @Test
    void createComment_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("gist-comment.json")));

        final GitHubGistComment comment = api().createComment(GIST_ID, GistsCatalogFixtures.createCommentPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(comment).isEqualTo(GistsCatalogFixtures.gistComment());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/gists/aa5a315d61ae9438b18d/comments");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("This is a comment to a gist");
        });
    }

    @Test
    void createComment_shouldThrow_whenPayloadNull() {
        final GitHubGistsCommentsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createComment(GIST_ID, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getComment_shouldReturnComment_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("gist-comment.json")));

        final Optional<GitHubGistComment> comment = api().getComment(GIST_ID, COMMENT_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(comment).contains(GistsCatalogFixtures.gistComment());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(COMMENT_URI);
        });
    }

    @Test
    void getComment_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubGistComment> comment = api().getComment(GIST_ID, COMMENT_ID);

        assertThat(comment).isEmpty();
    }

    @Test
    void updateComment_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("gist-comment.json")));

        final GitHubGistComment comment =
                api().updateComment(GIST_ID, COMMENT_ID, GistsCatalogFixtures.updateCommentPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(comment).isEqualTo(GistsCatalogFixtures.gistComment());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString(COMMENT_URI);
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("This is an update to a comment in a gist");
        });
    }

    @Test
    void updateComment_shouldThrow_whenPayloadNull() {
        final GitHubGistsCommentsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updateComment(GIST_ID, COMMENT_ID, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteComment_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteComment(GIST_ID, COMMENT_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString(COMMENT_URI);
        });
    }

    private GitHubGistsCommentsApi api() {
        return new GitHubGistsCommentsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubGistsCommentsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
