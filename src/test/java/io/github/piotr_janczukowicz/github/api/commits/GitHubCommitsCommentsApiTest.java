package io.github.piotr_janczukowicz.github.api.commits;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.http.HttpMethod;
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
class GitHubCommitsCommentsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String SHA = "6dcb09b5b57875f334f61aebed695e2e4193db5e";
    private static final long COMMENT_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCommitsCommentsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listCommentsForCommit_shouldReturnComments_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("commit-comment.json") + "]"));

        final GitHubPage<GitHubCommitComment> page = api().listCommentsForCommit(OWNER, REPO, SHA);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(CommitsCatalogFixtures.commitComment());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/commits/" + SHA + "/comments?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listCommentsForCommit_shouldPercentEncodeSlashInOwner_whenOwnerContainsSlash() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("commit-comment.json") + "]"));

        api().listCommentsForCommit("a/b", REPO, SHA);

        assertThat(captureSingleRequest().uri().getRawPath())
                .isEqualTo("/repos/a%2Fb/Hello-World/commits/" + SHA + "/comments");
    }

    @Test
    void listCommitCommentsForRepo_shouldReturnComments_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("commit-comment.json") + "]"));

        final GitHubPage<GitHubCommitComment> page = api().listCommitCommentsForRepo(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(CommitsCatalogFixtures.commitComment());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/comments?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void createCommitComment_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("commit-comment.json")));
        final GitHubCommitComment comment =
                api().createCommitComment(OWNER, REPO, SHA, CommitsCatalogFixtures.createCommitCommentPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(comment).isEqualTo(CommitsCatalogFixtures.commitComment());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/commits/" + SHA + "/comments");
        });
    }

    @Test
    void getCommitComment_shouldReturnComment_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("commit-comment.json")));

        final Optional<GitHubCommitComment> comment = api().getCommitComment(OWNER, REPO, COMMENT_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(comment).contains(CommitsCatalogFixtures.commitComment());
            softly.assertThat(requestUri).hasToString("https://api.github.com/repos/octocat/Hello-World/comments/1");
        });
    }

    @Test
    void updateCommitComment_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("commit-comment.json")));
        final GitHubCommitComment comment =
                api().updateCommitComment(OWNER, REPO, COMMENT_ID, CommitsCatalogFixtures.updateCommitCommentPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(comment).isEqualTo(CommitsCatalogFixtures.commitComment());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/comments/1");
        });
    }

    @Test
    void deleteCommitComment_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteCommitComment(OWNER, REPO, COMMENT_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/comments/1");
        });
    }

    private GitHubCommitsCommentsApi api() {
        return new GitHubCommitsCommentsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCommitsCommentsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
