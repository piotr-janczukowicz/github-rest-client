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
class GitHubGistApiTest {

    private static final String GIST_ID = "aa5a315d61ae9438b18d";
    private static final String USERNAME = "octocat";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubGistApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listGists_shouldReturnGists_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        final GitHubPage<GitHubBaseGist> page = api().listGists();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(GistsCatalogFixtures.baseGist());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/gists?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listGists_shouldSendSince_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        api().listGists(GistsCatalogFixtures.listFilter());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasParameter("since", "2022-09-20T12:11:58Z");
    }

    @Test
    void listGists_shouldSendPageAndPerPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        api().listGists(GitHubPageNumberRequest.of(2, 10));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.uri()).hasParameter("page", "2");
            softly.assertThat(request.uri()).hasParameter("per_page", "10");
        });
    }

    @Test
    void listGists_shouldSendFilterAndPageRequest_whenBothProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        api().listGists(GistsCatalogFixtures.listFilter(), GitHubPageNumberRequest.of(3, 20));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.uri()).hasParameter("since", "2022-09-20T12:11:58Z");
            softly.assertThat(request.uri()).hasParameter("page", "3");
            softly.assertThat(request.uri()).hasParameter("per_page", "20");
        });
    }

    @Test
    void createGist_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("gist-simple.json")));

        final GitHubGistSimple gist = api().createGist(GistsCatalogFixtures.createPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(gist).isEqualTo(GistsCatalogFixtures.gistSimple());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/gists");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("Example Ruby script");
        });
    }

    @Test
    void createGist_shouldThrow_whenPayloadNull() {
        final GitHubGistApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createGist(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void listPublicGists_shouldReturnGists_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        final GitHubPage<GitHubBaseGist> page = api().listPublicGists();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(GistsCatalogFixtures.baseGist());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/gists/public?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listPublicGists_shouldSendSince_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        api().listPublicGists(GistsCatalogFixtures.listPublicFilter());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasParameter("since", "2022-09-20T12:11:58Z");
    }

    @Test
    void listPublicGists_shouldSendPageAndPerPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        api().listPublicGists(GitHubPageNumberRequest.of(2, 10));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.uri()).hasParameter("page", "2");
            softly.assertThat(request.uri()).hasParameter("per_page", "10");
        });
    }

    @Test
    void listPublicGists_shouldSendFilterAndPageRequest_whenBothProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        api().listPublicGists(GistsCatalogFixtures.listPublicFilter(), GitHubPageNumberRequest.of(3, 20));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasParameter("since", "2022-09-20T12:11:58Z").hasParameter("page", "3");
    }

    @Test
    void listStarredGists_shouldReturnGists_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        final GitHubPage<GitHubBaseGist> page = api().listStarredGists();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(GistsCatalogFixtures.baseGist());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/gists/starred?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listStarredGists_shouldSendSince_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        api().listStarredGists(GistsCatalogFixtures.listStarredFilter());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasParameter("since", "2022-09-20T12:11:58Z");
    }

    @Test
    void listStarredGists_shouldSendPageAndPerPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        api().listStarredGists(GitHubPageNumberRequest.of(2, 10));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.uri()).hasParameter("page", "2");
            softly.assertThat(request.uri()).hasParameter("per_page", "10");
        });
    }

    @Test
    void listStarredGists_shouldSendFilterAndPageRequest_whenBothProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        api().listStarredGists(GistsCatalogFixtures.listStarredFilter(), GitHubPageNumberRequest.of(3, 20));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasParameter("since", "2022-09-20T12:11:58Z").hasParameter("page", "3");
    }

    @Test
    void getGist_shouldReturnGist_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("gist-simple.json")));

        final Optional<GitHubGistSimple> gist = api().getGist(GIST_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(gist).contains(GistsCatalogFixtures.gistSimple());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/gists/aa5a315d61ae9438b18d");
        });
    }

    @Test
    void getGist_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubGistSimple> gist = api().getGist(GIST_ID);

        assertThat(gist).isEmpty();
    }

    @Test
    void getGist_shouldThrow_whenGistIdBlank() {
        final GitHubGistApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getGist(" "));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("gist_id");
    }

    @Test
    void updateGist_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("gist-simple.json")));

        final GitHubGistSimple gist = api().updateGist(GIST_ID, GistsCatalogFixtures.updatePayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(gist).isEqualTo(GistsCatalogFixtures.gistSimple());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/gists/aa5a315d61ae9438b18d");
            softly.assertThat(request.body().isAbsent()).isFalse();
        });
    }

    @Test
    void updateGist_shouldThrow_whenPayloadNull() {
        final GitHubGistApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updateGist(GIST_ID, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteGist_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteGist(GIST_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/gists/aa5a315d61ae9438b18d");
        });
    }

    @Test
    void listGistCommits_shouldReturnItems_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("gist-commit.json") + "]"));

        final GitHubPage<GitHubGistCommit> page = api().listGistCommits(GIST_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(GistsCatalogFixtures.gistCommit());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/gists/aa5a315d61ae9438b18d/commits?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listGistCommits_shouldSendPageAndPerPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("gist-commit.json") + "]"));

        api().listGistCommits(GIST_ID, GitHubPageNumberRequest.of(2, 10));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.uri()).hasParameter("page", "2");
            softly.assertThat(request.uri()).hasParameter("per_page", "10");
        });
    }

    @Test
    void listGistForks_shouldReturnItems_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("gist-simple.json") + "]"));

        final GitHubPage<GitHubGistSimple> page = api().listGistForks(GIST_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(GistsCatalogFixtures.gistSimple());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/gists/aa5a315d61ae9438b18d/forks?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listGistForks_shouldSendPageAndPerPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("gist-simple.json") + "]"));

        api().listGistForks(GIST_ID, GitHubPageNumberRequest.of(2, 10));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.uri()).hasParameter("page", "2");
            softly.assertThat(request.uri()).hasParameter("per_page", "10");
        });
    }

    @Test
    void forkGist_shouldPostAndReturnFork_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("base-gist.json")));

        final GitHubBaseGist fork = api().forkGist(GIST_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(fork).isEqualTo(GistsCatalogFixtures.baseGist());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/gists/aa5a315d61ae9438b18d/forks");
        });
    }

    @Test
    void isGistStarred_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final boolean starred = api().isGistStarred(GIST_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(starred).isTrue();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/gists/aa5a315d61ae9438b18d/star");
        });
    }

    @Test
    void isGistStarred_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final boolean starred = api().isGistStarred(GIST_ID);

        assertThat(starred).isFalse();
    }

    @Test
    void starGist_shouldSendPutWithAbsentBody_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().starGist(GIST_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/gists/aa5a315d61ae9438b18d/star");
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void unstarGist_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().unstarGist(GIST_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/gists/aa5a315d61ae9438b18d/star");
        });
    }

    @Test
    void getGistRevision_shouldReturnGist_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("gist-simple.json")));

        final Optional<GitHubGistSimple> gist =
                api().getGistRevision(GIST_ID, "57a7f021a713b1c5a6a199b54cc514735d2d462f");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(gist).contains(GistsCatalogFixtures.gistSimple());
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/gists/aa5a315d61ae9438b18d/57a7f021a713b1c5a6a199b54cc514735d2d462f");
        });
    }

    @Test
    void getGistRevision_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubGistSimple> gist = api().getGistRevision(GIST_ID, "missing");

        assertThat(gist).isEmpty();
    }

    @Test
    void listGistsForUser_shouldReturnGists_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        final GitHubPage<GitHubBaseGist> page = api().listGistsForUser(USERNAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(GistsCatalogFixtures.baseGist());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/gists?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listGistsForUser_shouldSendSince_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        api().listGistsForUser(USERNAME, GistsCatalogFixtures.listForUserFilter());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasParameter("since", "2022-09-20T12:11:58Z");
    }

    @Test
    void listGistsForUser_shouldSendPageAndPerPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        api().listGistsForUser(USERNAME, GitHubPageNumberRequest.of(2, 10));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.uri()).hasParameter("page", "2");
            softly.assertThat(request.uri()).hasParameter("per_page", "10");
        });
    }

    @Test
    void listGistsForUser_shouldSendFilterAndPageRequest_whenBothProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("base-gist.json") + "]"));

        api().listGistsForUser(USERNAME, GistsCatalogFixtures.listForUserFilter(), GitHubPageNumberRequest.of(3, 20));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasParameter("since", "2022-09-20T12:11:58Z").hasParameter("page", "3");
    }

    private GitHubGistApi api() {
        return new GitHubGistApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubGistApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
