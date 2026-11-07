package io.github.piotr_janczukowicz.github.api.pullrequests;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubPullsCopilotCodeReviewApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int PULL_NUMBER = 1347;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubPullsCopilotCodeReviewApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void request_shouldReturnReviewRequest_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("copilot-code-review-request.json")));
        final GitHubRequestCopilotCodeReviewPayload payload =
                PullrequestsCatalogFixtures.requestCopilotCodeReviewPayload();

        final GitHubCopilotCodeReviewRequest reviewRequest = api().request(OWNER, REPO, PULL_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(reviewRequest).isEqualTo(PullrequestsCatalogFixtures.copilotCodeReviewRequest());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/pulls/1347/copilot-code-review");
        });
    }

    @Test
    void request_shouldSendPost_whenPayloadNull() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("copilot-code-review-request.json")));

        final GitHubCopilotCodeReviewRequest reviewRequest = api().request(OWNER, REPO, PULL_NUMBER, null);

        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(reviewRequest).isEqualTo(PullrequestsCatalogFixtures.copilotCodeReviewRequest());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
        });
    }

    @Test
    void request_shouldSendPost_whenOverloadWithoutPayloadUsed() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("copilot-code-review-request.json")));

        final GitHubCopilotCodeReviewRequest reviewRequest = api().request(OWNER, REPO, PULL_NUMBER);

        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(reviewRequest).isEqualTo(PullrequestsCatalogFixtures.copilotCodeReviewRequest());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
        });
    }

    @Test
    void request_shouldThrowIllegalArgument_whenOwnerBlank() {
        final GitHubPullsCopilotCodeReviewApi api = api();

        final Throwable thrown = catchThrowable(() -> api.request(" ", REPO, PULL_NUMBER));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("owner");
    }

    private GitHubPullsCopilotCodeReviewApi api() {
        return new GitHubPullsCopilotCodeReviewApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubPullsCopilotCodeReviewApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
