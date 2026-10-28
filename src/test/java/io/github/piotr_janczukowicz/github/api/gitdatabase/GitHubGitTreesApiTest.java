package io.github.piotr_janczukowicz.github.api.gitdatabase;

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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubGitTreesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String TREE_SHA = "9fb037999f264ba9a7fc6274d15fa3ae2ab98312";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubGitTreesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void createTree_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("git-tree.json")));
        final GitHubCreateTreePayload payload = GitDatabaseCatalogFixtures.createTreePayload();

        final GitHubGitTree tree = api().createTree(OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(tree).isEqualTo(GitDatabaseCatalogFixtures.gitTree());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/git/trees");
        });
    }

    @Test
    void createTree_shouldThrow_whenPayloadNull() {
        final GitHubGitTreesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createTree(OWNER, REPO, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getTree_shouldReturnTree_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("git-tree.json")));

        final Optional<GitHubGitTree> tree = api().getTree(OWNER, REPO, TREE_SHA);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(tree).contains(GitDatabaseCatalogFixtures.gitTree());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/git/trees/" + TREE_SHA);
        });
    }

    @Test
    void getTree_shouldSendRecursiveQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("git-tree.json")));

        api().getTree(OWNER, REPO, TREE_SHA, GitDatabaseCatalogFixtures.getTreeFilter());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri())
                .hasToString("https://api.github.com/repos/octocat/Hello-World/git/trees/" + TREE_SHA + "?recursive=1");
    }

    @Test
    void getTree_shouldThrow_whenFilterNull() {
        final GitHubGitTreesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getTree(OWNER, REPO, TREE_SHA, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void getTree_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubGitTree> tree = api().getTree(OWNER, REPO, TREE_SHA);

        assertThat(tree).isEmpty();
    }

    private GitHubGitTreesApi api() {
        return new GitHubGitTreesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubGitTreesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
