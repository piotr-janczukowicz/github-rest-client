package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
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
class GitHubSecurityAdvisoriesRepositoryAdvisoriesApiCommentsTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final String GHSA_ID = "GHSA-49wp-qq6x-g2rf";
    private static final long COMMENT_ID = 42L;
    private static final String COMMENTS_URI =
            "https://api.github.com/repos/octocat/hello-world/security-advisories/GHSA-49wp-qq6x-g2rf/comments";
    private static final String COMMENT_URI = COMMENTS_URI + "/42";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void listRepositoryAdvisoryComments_shouldReturnComments_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-advisory-comment.json") + "]"));

        final GitHubPage<GitHubRepositoryAdvisoryComment> page =
                api().listRepositoryAdvisoryComments(OWNER, REPO, GHSA_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(SecurityAdvisoriesCatalogFixtures.repositoryAdvisoryComment());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString()).startsWith(COMMENTS_URI + "?page=1");
        });
    }

    @Test
    void listRepositoryAdvisoryComments_shouldSendSinceQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-advisory-comment.json") + "]"));

        api().listRepositoryAdvisoryComments(
                        OWNER,
                        REPO,
                        GHSA_ID,
                        SecurityAdvisoriesCatalogFixtures.listRepositoryAdvisoryCommentsFilter(),
                        GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri().getQuery()).contains("since=2026-01-01T00:00:00Z");
    }

    @Test
    void createRepositoryAdvisoryComment_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("repository-advisory-comment.json")));
        final GitHubCreateRepositoryAdvisoryCommentPayload payload =
                SecurityAdvisoriesCatalogFixtures.createRepositoryAdvisoryCommentPayload();

        final GitHubRepositoryAdvisoryComment comment =
                api().createRepositoryAdvisoryComment(OWNER, REPO, GHSA_ID, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(comment).isEqualTo(SecurityAdvisoriesCatalogFixtures.repositoryAdvisoryComment());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString(COMMENTS_URI);
        });
    }

    @Test
    void createRepositoryAdvisoryComment_shouldThrow_whenPayloadNull() {
        final GitHubSecurityAdvisoriesRepositoryAdvisoriesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createRepositoryAdvisoryComment(OWNER, REPO, GHSA_ID, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getRepositoryAdvisoryComment_shouldReturnComment_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("repository-advisory-comment.json")));

        final Optional<GitHubRepositoryAdvisoryComment> comment =
                api().getRepositoryAdvisoryComment(OWNER, REPO, GHSA_ID, COMMENT_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(comment).contains(SecurityAdvisoriesCatalogFixtures.repositoryAdvisoryComment());
            softly.assertThat(request.uri()).hasToString(COMMENT_URI);
        });
    }

    @Test
    void getRepositoryAdvisoryComment_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubRepositoryAdvisoryComment> comment =
                api().getRepositoryAdvisoryComment(OWNER, REPO, GHSA_ID, COMMENT_ID);

        assertThat(comment).isEmpty();
    }

    @Test
    void updateRepositoryAdvisoryComment_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("repository-advisory-comment.json")));
        final GitHubUpdateRepositoryAdvisoryCommentPayload payload =
                SecurityAdvisoriesCatalogFixtures.updateRepositoryAdvisoryCommentPayload();

        final GitHubRepositoryAdvisoryComment comment =
                api().updateRepositoryAdvisoryComment(OWNER, REPO, GHSA_ID, COMMENT_ID, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(comment).isEqualTo(SecurityAdvisoriesCatalogFixtures.repositoryAdvisoryComment());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString(COMMENT_URI);
        });
    }

    @Test
    void updateRepositoryAdvisoryComment_shouldThrow_whenPayloadNull() {
        final GitHubSecurityAdvisoriesRepositoryAdvisoriesApi api = api();

        final Throwable thrown =
                catchThrowable(() -> api.updateRepositoryAdvisoryComment(OWNER, REPO, GHSA_ID, COMMENT_ID, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    private GitHubSecurityAdvisoriesRepositoryAdvisoriesApi api() {
        return new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubSecurityAdvisoriesRepositoryAdvisoriesApiCommentsTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
