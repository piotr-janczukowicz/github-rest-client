package io.github.piotr_janczukowicz.github.api.releases;

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
class GitHubReleaseApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long RELEASE_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubReleaseApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listReleases_shouldReturnReleases_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("release.json") + "]"));

        final GitHubPage<GitHubRelease> page = api().listReleases(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ReleasesCatalogFixtures.release());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/releases?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void getRelease_shouldReturnRelease_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("release.json")));

        final Optional<GitHubRelease> release = api().getRelease(OWNER, REPO, RELEASE_ID);

        assertThat(release).contains(ReleasesCatalogFixtures.release());
    }

    @Test
    void getRelease_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubRelease> release = api().getRelease(OWNER, REPO, RELEASE_ID);

        assertThat(release).isEmpty();
    }

    @Test
    void getLatestRelease_shouldReturnRelease_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("release.json")));

        final Optional<GitHubRelease> release = api().getLatestRelease(OWNER, REPO);

        assertThat(release).contains(ReleasesCatalogFixtures.release());
    }

    @Test
    void getReleaseByTag_shouldReturnRelease_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("release.json")));

        final Optional<GitHubRelease> release = api().getReleaseByTag(OWNER, REPO, "v1.0.0");

        assertThat(release).contains(ReleasesCatalogFixtures.release());
    }

    @Test
    void getReleaseByTag_shouldPercentEncodeSlashInOwner_whenOwnerContainsSlash() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("release.json")));

        api().getReleaseByTag("a/b", REPO, "v1.0.0");

        assertThat(captureSingleRequest().uri().getRawPath())
                .isEqualTo("/repos/a%2Fb/Hello-World/releases/tags/v1.0.0");
    }

    @Test
    void createRelease_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("release.json")));
        final GitHubCreateReleasePayload payload = ReleasesCatalogFixtures.createReleasePayload();

        final GitHubRelease release = api().createRelease(OWNER, REPO, payload);

        assertThat(release).isEqualTo(ReleasesCatalogFixtures.release());
    }

    @Test
    void updateRelease_shouldPatchWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("release.json")));

        api().updateRelease(OWNER, REPO, RELEASE_ID, null);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
    }

    @Test
    void deleteRelease_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteRelease(OWNER, REPO, RELEASE_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/releases/1");
    }

    @Test
    void generateReleaseNotes_shouldPostPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("release-notes-content.json")));
        final GitHubGenerateReleaseNotesPayload payload = ReleasesCatalogFixtures.generateReleaseNotesPayload();

        final GitHubReleaseNotesContent notes = api().generateReleaseNotes(OWNER, REPO, payload);

        assertThat(notes).isEqualTo(ReleasesCatalogFixtures.releaseNotesContent());
    }

    @Test
    void updateRelease_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("release.json")));
        final GitHubUpdateReleasePayload payload = ReleasesCatalogFixtures.updateReleasePayload();

        final GitHubRelease release = api().updateRelease(OWNER, REPO, RELEASE_ID, payload);

        assertThat(release).isEqualTo(ReleasesCatalogFixtures.release());
    }

    private GitHubReleaseApi api() {
        return new GitHubReleaseApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubReleaseApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
