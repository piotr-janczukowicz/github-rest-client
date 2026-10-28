package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.interactionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubInteractionsOrgsApiIntegrationTest {

    private static final String ORG = "github";
    private static final String LIMITS_PATH = "/orgs/github/interaction-limits";
    private static final String CREATION_CAP_PATH = "/orgs/github/interaction-limits/pulls/creation-cap";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getRestrictionsForOrg_shouldReturnRestrictions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                LIMITS_PATH,
                okJson(jsonBytes(GitHubInteractionsOrgsApiIntegrationTest.class, "interaction-limit-response.json")));

        final Optional<GitHubInteractionLimitResponse> restrictions;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            restrictions = api.api().orgs().getRestrictionsForOrg(ORG);
        }

        final GitHubInteractionLimitResponse expected = CODEC.decode(
                jsonBytes(GitHubInteractionsOrgsApiIntegrationTest.class, "interaction-limit-response.json"),
                GitHubInteractionLimitResponse.class);

        assertThat(restrictions).contains(expected);
        HTTP.verifyRequested(GET, LIMITS_PATH);
    }

    @Test
    void getRestrictionsForOrg_shouldReturnEmpty_whenBodyIsEmptyObject() throws Exception {
        HTTP.stub(GET, LIMITS_PATH, okJson("{}".getBytes(StandardCharsets.UTF_8)));

        final Optional<GitHubInteractionLimitResponse> restrictions;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            restrictions = api.api().orgs().getRestrictionsForOrg(ORG);
        }

        assertThat(restrictions).isEmpty();
        HTTP.verifyRequested(GET, LIMITS_PATH);
    }

    @Test
    void setRestrictionsForOrg_shouldReturnRestrictions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                LIMITS_PATH,
                okJson(jsonBytes(GitHubInteractionsOrgsApiIntegrationTest.class, "interaction-limit-response.json")));
        final GitHubInteractionLimit payload = InteractionsCatalogFixtures.interactionLimit();

        final GitHubInteractionLimitResponse restrictions;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            restrictions = api.api().orgs().setRestrictionsForOrg(ORG, payload);
        }

        final GitHubInteractionLimitResponse expected = CODEC.decode(
                jsonBytes(GitHubInteractionsOrgsApiIntegrationTest.class, "interaction-limit-response.json"),
                GitHubInteractionLimitResponse.class);

        assertThat(restrictions).isEqualTo(expected);
        HTTP.verifyRequested(PUT, LIMITS_PATH);
        HTTP.verifyHeader(PUT, LIMITS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, LIMITS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void removeRestrictionsForOrg_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, LIMITS_PATH, noContent());

        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            api.api().orgs().removeRestrictionsForOrg(ORG);
        }

        HTTP.verifyRequested(DELETE, LIMITS_PATH);
    }

    @Test
    void getPullRequestCreationCapForOrg_shouldReturnCap_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                CREATION_CAP_PATH,
                okJson(jsonBytes(GitHubInteractionsOrgsApiIntegrationTest.class, "pull-request-creation-cap.json")));

        final GitHubPullRequestCreationCap cap;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            cap = api.api().orgs().getPullRequestCreationCapForOrg(ORG);
        }

        final GitHubPullRequestCreationCap expected = CODEC.decode(
                jsonBytes(GitHubInteractionsOrgsApiIntegrationTest.class, "pull-request-creation-cap.json"),
                GitHubPullRequestCreationCap.class);

        assertThat(cap).isEqualTo(expected);
        HTTP.verifyRequested(GET, CREATION_CAP_PATH);
    }

    @Test
    void getPullRequestCreationCapForOrg_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, CREATION_CAP_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            final GitHubInteractionsOrgsApi orgs = api.api().orgs();

            thrown = catchThrowable(() -> orgs.getPullRequestCreationCapForOrg(ORG));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, CREATION_CAP_PATH);
    }

    @Test
    void updatePullRequestCreationCapForOrg_shouldReturnCap_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                CREATION_CAP_PATH,
                okJson(jsonBytes(GitHubInteractionsOrgsApiIntegrationTest.class, "pull-request-creation-cap.json")));
        final GitHubUpdatePullRequestCreationCapForOrgPayload payload =
                InteractionsCatalogFixtures.updatePullRequestCreationCapForOrgPayload();

        final GitHubPullRequestCreationCap cap;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            cap = api.api().orgs().updatePullRequestCreationCapForOrg(ORG, payload);
        }

        final GitHubPullRequestCreationCap expected = CODEC.decode(
                jsonBytes(GitHubInteractionsOrgsApiIntegrationTest.class, "pull-request-creation-cap.json"),
                GitHubPullRequestCreationCap.class);

        assertThat(cap).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, CREATION_CAP_PATH);
        HTTP.verifyHeader(PATCH, CREATION_CAP_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, CREATION_CAP_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }
}
