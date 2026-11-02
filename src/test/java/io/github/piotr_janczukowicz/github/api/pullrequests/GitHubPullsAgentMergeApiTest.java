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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubPullsAgentMergeApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int PULL_NUMBER = 7;
    private static final String AGENT_MERGE_URI =
            "https://api.github.com/agents/repos/octocat/Hello-World/pulls/7/agent-merge";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubPullsAgentMergeApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getConfiguration_shouldReturnConfiguration_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("get-agent-merge-enrollment-response.json")));

        final Optional<GitHubGetAgentMergeEnrollmentResponse> configuration =
                api().getConfiguration(OWNER, REPO, PULL_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(configuration).contains(PullrequestsCatalogFixtures.getAgentMergeEnrollmentResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(AGENT_MERGE_URI);
        });
    }

    @Test
    void getConfiguration_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubGetAgentMergeEnrollmentResponse> configuration =
                api().getConfiguration(OWNER, REPO, PULL_NUMBER);

        assertThat(configuration).isEmpty();
    }

    @Test
    void putConfiguration_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("put-agent-merge-enrollment-response.json")));
        final GitHubPutAgentMergeEnrollmentPayload payload =
                PullrequestsCatalogFixtures.putAgentMergeEnrollmentPayload();

        final GitHubPutAgentMergeEnrollmentResponse configuration =
                api().putConfiguration(OWNER, REPO, PULL_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(configuration).isEqualTo(PullrequestsCatalogFixtures.putAgentMergeEnrollmentResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString(AGENT_MERGE_URI);
        });
    }

    @Test
    void putConfiguration_shouldReturnConfiguration_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("put-agent-merge-enrollment-response.json")));
        final GitHubPutAgentMergeEnrollmentPayload payload =
                PullrequestsCatalogFixtures.putAgentMergeEnrollmentPayload();

        final GitHubPutAgentMergeEnrollmentResponse configuration =
                api().putConfiguration(OWNER, REPO, PULL_NUMBER, payload);

        assertThat(configuration).isEqualTo(PullrequestsCatalogFixtures.putAgentMergeEnrollmentResponse());
    }

    @Test
    void putConfiguration_shouldThrow_whenPayloadNull() {
        final GitHubPullsAgentMergeApi api = api();

        final Throwable thrown = catchThrowable(() -> api.putConfiguration(OWNER, REPO, PULL_NUMBER, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteConfiguration_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteConfiguration(OWNER, REPO, PULL_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString(AGENT_MERGE_URI);
        });
    }

    private GitHubPullsAgentMergeApi api() {
        return new GitHubPullsAgentMergeApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubPullsAgentMergeApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
