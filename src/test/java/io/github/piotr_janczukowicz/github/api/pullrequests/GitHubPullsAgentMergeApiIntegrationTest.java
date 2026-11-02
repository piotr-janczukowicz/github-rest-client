package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubPullsAgentMergeApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int PULL_NUMBER = 7;
    private static final String AGENT_MERGE_PATH = "/agents/repos/octocat/Hello-World/pulls/7/agent-merge";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getConfiguration_shouldReturnConfiguration_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                AGENT_MERGE_PATH,
                okJson(jsonBytes(
                        GitHubPullsAgentMergeApiIntegrationTest.class, "get-agent-merge-enrollment-response.json")));

        final Optional<GitHubGetAgentMergeEnrollmentResponse> configuration;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            configuration = new GitHubPullsAgentMergeApi(client.caller()).getConfiguration(OWNER, REPO, PULL_NUMBER);
        }

        assertThat(configuration).contains(PullrequestsCatalogFixtures.getAgentMergeEnrollmentResponse());
        HTTP.verifyRequested(GET, AGENT_MERGE_PATH);
    }

    @Test
    void getConfiguration_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, AGENT_MERGE_PATH, notFound());

        final Optional<GitHubGetAgentMergeEnrollmentResponse> configuration;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            configuration = new GitHubPullsAgentMergeApi(client.caller()).getConfiguration(OWNER, REPO, PULL_NUMBER);
        }

        assertThat(configuration).isEmpty();
    }

    @Test
    void putConfiguration_shouldReturnConfiguration_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                AGENT_MERGE_PATH,
                okJson(jsonBytes(
                        GitHubPullsAgentMergeApiIntegrationTest.class, "put-agent-merge-enrollment-response.json")));

        final GitHubPutAgentMergeEnrollmentResponse configuration;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            configuration = new GitHubPullsAgentMergeApi(client.caller())
                    .putConfiguration(
                            OWNER, REPO, PULL_NUMBER, PullrequestsCatalogFixtures.putAgentMergeEnrollmentPayload());
        }

        assertThat(configuration).isEqualTo(PullrequestsCatalogFixtures.putAgentMergeEnrollmentResponse());
        HTTP.verifyRequested(PUT, AGENT_MERGE_PATH);
    }

    @Test
    void putConfiguration_shouldReturnConfiguration_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                PUT,
                AGENT_MERGE_PATH,
                createdJson(jsonBytes(
                        GitHubPullsAgentMergeApiIntegrationTest.class, "put-agent-merge-enrollment-response.json")));

        final GitHubPutAgentMergeEnrollmentResponse configuration;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            configuration = new GitHubPullsAgentMergeApi(client.caller())
                    .putConfiguration(
                            OWNER, REPO, PULL_NUMBER, PullrequestsCatalogFixtures.putAgentMergeEnrollmentPayload());
        }

        assertThat(configuration).isEqualTo(PullrequestsCatalogFixtures.putAgentMergeEnrollmentResponse());
    }

    @Test
    void deleteConfiguration_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, AGENT_MERGE_PATH, noContent());

        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            new GitHubPullsAgentMergeApi(client.caller()).deleteConfiguration(OWNER, REPO, PULL_NUMBER);
        }

        HTTP.verifyRequested(DELETE, AGENT_MERGE_PATH);
    }
}
