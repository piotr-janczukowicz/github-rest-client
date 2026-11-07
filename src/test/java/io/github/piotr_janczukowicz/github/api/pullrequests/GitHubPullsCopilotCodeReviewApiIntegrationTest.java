package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubPullsCopilotCodeReviewApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int PULL_NUMBER = 1347;
    private static final String PATH = "/repos/octocat/Hello-World/pulls/1347/copilot-code-review";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void request_shouldReturnReviewRequest_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                PATH,
                createdJson(jsonBytes(
                        GitHubPullsCopilotCodeReviewApiIntegrationTest.class, "copilot-code-review-request.json")));
        final GitHubRequestCopilotCodeReviewPayload payload =
                PullrequestsCatalogFixtures.requestCopilotCodeReviewPayload();

        final GitHubCopilotCodeReviewRequest reviewRequest;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsCopilotCodeReviewApi api = new GitHubPullsCopilotCodeReviewApi(client.caller());
            reviewRequest = api.request(OWNER, REPO, PULL_NUMBER, payload);
        }

        final GitHubCopilotCodeReviewRequest expected = CODEC.decode(
                jsonBytes(GitHubPullsCopilotCodeReviewApiIntegrationTest.class, "copilot-code-review-request.json"),
                GitHubCopilotCodeReviewRequest.class);

        assertSoftly(softly -> {
            softly.assertThat(reviewRequest).isEqualTo(expected);
            HTTP.verifyRequested(POST, PATH);
            HTTP.verifyBody(POST, PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
        });
    }
}
