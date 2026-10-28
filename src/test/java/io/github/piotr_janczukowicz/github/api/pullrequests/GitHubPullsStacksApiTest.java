package io.github.piotr_janczukowicz.github.api.pullrequests;

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
class GitHubPullsStacksApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int STACK_NUMBER = 42;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubPullsStacksApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void list_shouldReturnStacks_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-stack-minimal-items.json")));

        final GitHubPage<GitHubPullRequestStackMinimal> page = api().list(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(PullrequestsCatalogFixtures.pullRequestStackMinimalItems());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString())
                    .startsWith("https://api.github.com/repos/octocat/Hello-World/stacks");
        });
    }

    @Test
    void list_shouldIncludePullRequestFilter_whenFilterSet() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-stack-minimal-items.json")));
        final GitHubListPullRequestStacksFilter filter = GitHubListPullRequestStacksFilterBuilder.builder()
                .pullRequest(101)
                .build();

        api().list(OWNER, REPO, filter, GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri().getQuery()).contains("pull_request=101");
    }

    @Test
    void create_shouldReturnStack_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("pull-request-stack-detail.json")));
        final GitHubCreatePullRequestStackPayload payload = PullrequestsCatalogFixtures.createPullRequestStackPayload();

        final GitHubPullRequestStackDetail stack = api().create(OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(stack).isEqualTo(PullrequestsCatalogFixtures.pullRequestStackDetail());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/stacks");
        });
    }

    @Test
    void create_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> api().create(OWNER, REPO, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void get_shouldReturnStack_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-stack-detail.json")));

        final Optional<GitHubPullRequestStackDetail> stack = api().get(OWNER, REPO, STACK_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(stack).contains(PullrequestsCatalogFixtures.pullRequestStackDetail());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/stacks/42");
        });
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubPullRequestStackDetail> stack = api().get(OWNER, REPO, STACK_NUMBER);

        assertThat(stack).isEmpty();
    }

    @Test
    void add_shouldReturnStack_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-stack-detail.json")));
        final GitHubAddPullRequestsToStackPayload payload = PullrequestsCatalogFixtures.addPullRequestsToStackPayload();

        final GitHubPullRequestStackDetail stack = api().add(OWNER, REPO, STACK_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(stack).isEqualTo(PullrequestsCatalogFixtures.pullRequestStackDetail());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/stacks/42/add");
        });
    }

    @Test
    void add_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> api().add(OWNER, REPO, STACK_NUMBER, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void unstack_shouldReturnStack_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pull-request-stack-detail.json")));

        final Optional<GitHubPullRequestStackDetail> stack = api().unstack(OWNER, REPO, STACK_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(stack).contains(PullrequestsCatalogFixtures.pullRequestStackDetail());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/stacks/42/unstack");
        });
    }

    @Test
    void unstack_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final Optional<GitHubPullRequestStackDetail> stack = api().unstack(OWNER, REPO, STACK_NUMBER);

        assertThat(stack).isEmpty();
    }

    private GitHubPullsStacksApi api() {
        return new GitHubPullsStacksApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubPullsStacksApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
