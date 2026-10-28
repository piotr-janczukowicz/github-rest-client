package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.interactionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubInteractionsReposApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String LIMITS_PATH = "/repos/octocat/Hello-World/interaction-limits";
    private static final String BYPASS_PATH = "/repos/octocat/Hello-World/interaction-limits/pulls/bypass-list";
    private static final String CREATION_CAP_PATH = "/repos/octocat/Hello-World/interaction-limits/pulls/creation-cap";

    private static final JsonType<List<GitHubSimpleUser>> USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getRestrictionsForRepo_shouldReturnRestrictions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                LIMITS_PATH,
                okJson(jsonBytes(GitHubInteractionsReposApiIntegrationTest.class, "interaction-limit-response.json")));

        final Optional<GitHubInteractionLimitResponse> restrictions;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            restrictions = api.api().repos().getRestrictionsForRepo(OWNER, REPO);
        }

        final GitHubInteractionLimitResponse expected = CODEC.decode(
                jsonBytes(GitHubInteractionsReposApiIntegrationTest.class, "interaction-limit-response.json"),
                GitHubInteractionLimitResponse.class);

        assertThat(restrictions).contains(expected);
        HTTP.verifyRequested(GET, LIMITS_PATH);
    }

    @Test
    void getRestrictionsForRepo_shouldReturnEmpty_whenBodyIsEmptyObject() throws Exception {
        HTTP.stub(GET, LIMITS_PATH, okJson("{}".getBytes(StandardCharsets.UTF_8)));

        final Optional<GitHubInteractionLimitResponse> restrictions;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            restrictions = api.api().repos().getRestrictionsForRepo(OWNER, REPO);
        }

        assertThat(restrictions).isEmpty();
        HTTP.verifyRequested(GET, LIMITS_PATH);
    }

    @Test
    void setRestrictionsForRepo_shouldReturnRestrictions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                LIMITS_PATH,
                okJson(jsonBytes(GitHubInteractionsReposApiIntegrationTest.class, "interaction-limit-response.json")));
        final GitHubInteractionLimit payload = InteractionsCatalogFixtures.interactionLimit();

        final GitHubInteractionLimitResponse restrictions;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            restrictions = api.api().repos().setRestrictionsForRepo(OWNER, REPO, payload);
        }

        final GitHubInteractionLimitResponse expected = CODEC.decode(
                jsonBytes(GitHubInteractionsReposApiIntegrationTest.class, "interaction-limit-response.json"),
                GitHubInteractionLimitResponse.class);

        assertThat(restrictions).isEqualTo(expected);
        HTTP.verifyRequested(PUT, LIMITS_PATH);
        HTTP.verifyHeader(PUT, LIMITS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, LIMITS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void removeRestrictionsForRepo_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, LIMITS_PATH, noContent());

        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            api.api().repos().removeRestrictionsForRepo(OWNER, REPO);
        }

        HTTP.verifyRequested(DELETE, LIMITS_PATH);
    }

    @Test
    void getPullRequestBypassListForRepo_shouldReturnUsers_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, BYPASS_PATH, okJson(listJsonBytes(GitHubSimpleUser.class, "simple-user-octocat.json")));

        final List<GitHubSimpleUser> users;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            users = api.api().repos().getPullRequestBypassListForRepo(OWNER, REPO);
        }

        final List<GitHubSimpleUser> expected =
                CODEC.decode(listJsonBytes(GitHubSimpleUser.class, "simple-user-octocat.json"), USERS_TYPE);

        assertThat(users).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, BYPASS_PATH);
    }

    @Test
    void setPullRequestBypassListForRepo_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, BYPASS_PATH, noContent());
        final GitHubInteractionLimitPullRequestBypassList payload =
                InteractionsCatalogFixtures.interactionLimitPullRequestBypassList();

        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            api.api().repos().setPullRequestBypassListForRepo(OWNER, REPO, payload);
        }

        HTTP.verifyRequested(PUT, BYPASS_PATH);
        HTTP.verifyHeader(PUT, BYPASS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, BYPASS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void removePullRequestBypassListForRepo_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, BYPASS_PATH, noContent());
        final GitHubInteractionLimitPullRequestBypassList payload =
                InteractionsCatalogFixtures.interactionLimitPullRequestBypassList();

        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            api.api().repos().removePullRequestBypassListForRepo(OWNER, REPO, payload);
        }

        HTTP.verifyRequested(DELETE, BYPASS_PATH);
        HTTP.verifyHeader(DELETE, BYPASS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(DELETE, BYPASS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getPullRequestCreationCapForRepo_shouldReturnCap_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                CREATION_CAP_PATH,
                okJson(jsonBytes(GitHubInteractionsReposApiIntegrationTest.class, "pull-request-creation-cap.json")));

        final GitHubPullRequestCreationCap cap;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            cap = api.api().repos().getPullRequestCreationCapForRepo(OWNER, REPO);
        }

        final GitHubPullRequestCreationCap expected = CODEC.decode(
                jsonBytes(GitHubInteractionsReposApiIntegrationTest.class, "pull-request-creation-cap.json"),
                GitHubPullRequestCreationCap.class);

        assertThat(cap).isEqualTo(expected);
        HTTP.verifyRequested(GET, CREATION_CAP_PATH);
    }

    @Test
    void getPullRequestCreationCapForRepo_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, CREATION_CAP_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            final GitHubInteractionsReposApi repos = api.api().repos();

            thrown = catchThrowable(() -> repos.getPullRequestCreationCapForRepo(OWNER, REPO));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, CREATION_CAP_PATH);
    }

    @Test
    void updatePullRequestCreationCapForRepo_shouldReturnCap_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                CREATION_CAP_PATH,
                okJson(jsonBytes(GitHubInteractionsReposApiIntegrationTest.class, "pull-request-creation-cap.json")));
        final GitHubUpdatePullRequestCreationCapForOrgPayload payload =
                InteractionsCatalogFixtures.updatePullRequestCreationCapForOrgPayload();

        final GitHubPullRequestCreationCap cap;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            cap = api.api().repos().updatePullRequestCreationCapForRepo(OWNER, REPO, payload);
        }

        final GitHubPullRequestCreationCap expected = CODEC.decode(
                jsonBytes(GitHubInteractionsReposApiIntegrationTest.class, "pull-request-creation-cap.json"),
                GitHubPullRequestCreationCap.class);

        assertThat(cap).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, CREATION_CAP_PATH);
        HTTP.verifyHeader(PATCH, CREATION_CAP_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, CREATION_CAP_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }
}
