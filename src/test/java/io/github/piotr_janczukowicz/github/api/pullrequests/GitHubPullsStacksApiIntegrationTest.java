package io.github.piotr_janczukowicz.github.api.pullrequests;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubPullsStacksApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int STACK_NUMBER = 42;

    private static final String STACKS_PATH = "/repos/octocat/Hello-World/stacks";
    private static final String STACK_PATH = STACKS_PATH + "/42";
    private static final String ADD_PATH = STACK_PATH + "/add";
    private static final String UNSTACK_PATH = STACK_PATH + "/unstack";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void list_shouldReturnStacks_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                STACKS_PATH,
                okJson(jsonBytes(GitHubPullsStacksApiIntegrationTest.class, "pull-request-stack-minimal-items.json")));

        final GitHubPage<GitHubPullRequestStackMinimal> page;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsStacksApi api = new GitHubPullsStacksApi(client.caller());
            page = api.list(OWNER, REPO);
        }

        assertSoftly(softly -> {
            softly.assertThat(page.items()).hasSize(1);
            HTTP.verifyRequested(GET, STACKS_PATH);
        });
    }

    @Test
    void create_shouldReturnStack_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                STACKS_PATH,
                createdJson(jsonBytes(GitHubPullsStacksApiIntegrationTest.class, "pull-request-stack-detail.json")));
        final GitHubCreatePullRequestStackPayload payload = PullrequestsCatalogFixtures.createPullRequestStackPayload();

        final GitHubPullRequestStackDetail stack;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsStacksApi api = new GitHubPullsStacksApi(client.caller());
            stack = api.create(OWNER, REPO, payload);
        }

        final GitHubPullRequestStackDetail expected = CODEC.decode(
                jsonBytes(GitHubPullsStacksApiIntegrationTest.class, "pull-request-stack-detail.json"),
                GitHubPullRequestStackDetail.class);

        assertSoftly(softly -> {
            softly.assertThat(stack).isEqualTo(expected);
            HTTP.verifyRequested(POST, STACKS_PATH);
            HTTP.verifyBody(POST, STACKS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void get_shouldReturnStack_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                STACK_PATH,
                okJson(jsonBytes(GitHubPullsStacksApiIntegrationTest.class, "pull-request-stack-detail.json")));

        final Optional<GitHubPullRequestStackDetail> stack;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsStacksApi api = new GitHubPullsStacksApi(client.caller());
            stack = api.get(OWNER, REPO, STACK_NUMBER);
        }

        final GitHubPullRequestStackDetail expected = CODEC.decode(
                jsonBytes(GitHubPullsStacksApiIntegrationTest.class, "pull-request-stack-detail.json"),
                GitHubPullRequestStackDetail.class);

        assertSoftly(softly -> {
            softly.assertThat(stack).contains(expected);
            HTTP.verifyRequested(GET, STACK_PATH);
        });
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, STACK_PATH, notFound());

        final Optional<GitHubPullRequestStackDetail> stack;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsStacksApi api = new GitHubPullsStacksApi(client.caller());
            stack = api.get(OWNER, REPO, STACK_NUMBER);
        }

        assertThat(stack).isEmpty();
    }

    @Test
    void add_shouldReturnStack_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                ADD_PATH,
                okJson(jsonBytes(GitHubPullsStacksApiIntegrationTest.class, "pull-request-stack-detail.json")));
        final GitHubAddPullRequestsToStackPayload payload = PullrequestsCatalogFixtures.addPullRequestsToStackPayload();

        final GitHubPullRequestStackDetail stack;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsStacksApi api = new GitHubPullsStacksApi(client.caller());
            stack = api.add(OWNER, REPO, STACK_NUMBER, payload);
        }

        final GitHubPullRequestStackDetail expected = CODEC.decode(
                jsonBytes(GitHubPullsStacksApiIntegrationTest.class, "pull-request-stack-detail.json"),
                GitHubPullRequestStackDetail.class);

        assertSoftly(softly -> {
            softly.assertThat(stack).isEqualTo(expected);
            HTTP.verifyRequested(POST, ADD_PATH);
            HTTP.verifyBody(POST, ADD_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void unstack_shouldReturnStack_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                UNSTACK_PATH,
                okJson(jsonBytes(GitHubPullsStacksApiIntegrationTest.class, "pull-request-stack-detail.json")));

        final Optional<GitHubPullRequestStackDetail> stack;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsStacksApi api = new GitHubPullsStacksApi(client.caller());
            stack = api.unstack(OWNER, REPO, STACK_NUMBER);
        }

        final GitHubPullRequestStackDetail expected = CODEC.decode(
                jsonBytes(GitHubPullsStacksApiIntegrationTest.class, "pull-request-stack-detail.json"),
                GitHubPullRequestStackDetail.class);

        assertSoftly(softly -> {
            softly.assertThat(stack).contains(expected);
            HTTP.verifyRequested(POST, UNSTACK_PATH);
        });
    }

    @Test
    void unstack_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        HTTP.stub(POST, UNSTACK_PATH, noContent());

        final Optional<GitHubPullRequestStackDetail> stack;
        try (final GitHubApiWireMockSupport.WireMockClient client = GitHubApiWireMockSupport.client(HTTP)) {
            final GitHubPullsStacksApi api = new GitHubPullsStacksApi(client.caller());
            stack = api.unstack(OWNER, REPO, STACK_NUMBER);
        }

        assertSoftly(softly -> {
            softly.assertThat(stack).isEmpty();
            HTTP.verifyRequested(POST, UNSTACK_PATH);
        });
    }
}
