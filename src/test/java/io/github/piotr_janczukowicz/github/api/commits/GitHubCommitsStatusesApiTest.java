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
class GitHubCommitsStatusesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String REF = "6dcb09b5b57875f334f61aebed695e2e4193db5e";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCommitsStatusesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void createCommitStatus_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("commit-status.json")));
        final GitHubCommitStatus status =
                api().createCommitStatus(OWNER, REPO, REF, CommitsCatalogFixtures.createCommitStatusPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(status).isEqualTo(CommitsCatalogFixtures.commitStatus());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/statuses/" + REF);
        });
    }

    @Test
    void listCommitStatusesForRef_shouldReturnStatuses_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("commit-status.json") + "]"));

        final GitHubPage<GitHubCommitStatus> page = api().listCommitStatusesForRef(OWNER, REPO, REF);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(CommitsCatalogFixtures.commitStatus());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/commits/" + REF + "/statuses?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void getCombinedStatusForRef_shouldReturnStatus_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("combined-commit-status.json")));

        final Optional<GitHubCombinedCommitStatus> status = api().getCombinedStatusForRef(OWNER, REPO, REF);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(status).contains(CommitsCatalogFixtures.combinedCommitStatus());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/commits/" + REF + "/status?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void getCombinedStatusForRef_shouldPercentEncodeSlashInOwner_whenOwnerContainsSlash() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("combined-commit-status.json")));

        api().getCombinedStatusForRef("a/b", REPO, REF);

        assertThat(captureSingleRequest().uri().getRawPath())
                .isEqualTo("/repos/a%2Fb/Hello-World/commits/" + REF + "/status");
    }

    @Test
    void getCombinedStatusForRef_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubCombinedCommitStatus> status = api().getCombinedStatusForRef(OWNER, REPO, REF);

        assertThat(status).isEmpty();
    }

    private GitHubCommitsStatusesApi api() {
        return new GitHubCommitsStatusesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCommitsStatusesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
