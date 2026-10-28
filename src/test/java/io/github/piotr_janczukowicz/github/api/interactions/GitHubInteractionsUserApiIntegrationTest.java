package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.interactionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubInteractionsUserApiIntegrationTest {

    private static final String LIMITS_PATH = "/user/interaction-limits";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getRestrictionsForAuthenticatedUser_shouldReturnRestrictions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                LIMITS_PATH,
                okJson(jsonBytes(GitHubInteractionsUserApiIntegrationTest.class, "interaction-limit-response.json")));

        final Optional<GitHubInteractionLimitResponse> restrictions;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            restrictions = api.api().user().getRestrictionsForAuthenticatedUser();
        }

        final GitHubInteractionLimitResponse expected = CODEC.decode(
                jsonBytes(GitHubInteractionsUserApiIntegrationTest.class, "interaction-limit-response.json"),
                GitHubInteractionLimitResponse.class);

        assertThat(restrictions).contains(expected);
        HTTP.verifyRequested(GET, LIMITS_PATH);
    }

    @Test
    void getRestrictionsForAuthenticatedUser_shouldReturnEmpty_whenBodyIsEmptyObject() throws Exception {
        HTTP.stub(GET, LIMITS_PATH, okJson("  {}\n".getBytes(StandardCharsets.UTF_8)));

        final Optional<GitHubInteractionLimitResponse> restrictions;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            restrictions = api.api().user().getRestrictionsForAuthenticatedUser();
        }

        assertThat(restrictions).isEmpty();
        HTTP.verifyRequested(GET, LIMITS_PATH);
    }

    @Test
    void getRestrictionsForAuthenticatedUser_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, LIMITS_PATH, noContent());

        final Optional<GitHubInteractionLimitResponse> restrictions;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            restrictions = api.api().user().getRestrictionsForAuthenticatedUser();
        }

        assertThat(restrictions).isEmpty();
        HTTP.verifyRequested(GET, LIMITS_PATH);
    }

    @Test
    void setRestrictionsForAuthenticatedUser_shouldReturnRestrictions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                LIMITS_PATH,
                okJson(jsonBytes(GitHubInteractionsUserApiIntegrationTest.class, "interaction-limit-response.json")));
        final GitHubInteractionLimit payload = InteractionsCatalogFixtures.interactionLimit();

        final GitHubInteractionLimitResponse restrictions;
        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            restrictions = api.api().user().setRestrictionsForAuthenticatedUser(payload);
        }

        final GitHubInteractionLimitResponse expected = CODEC.decode(
                jsonBytes(GitHubInteractionsUserApiIntegrationTest.class, "interaction-limit-response.json"),
                GitHubInteractionLimitResponse.class);

        assertThat(restrictions).isEqualTo(expected);
        HTTP.verifyRequested(PUT, LIMITS_PATH);
        HTTP.verifyHeader(PUT, LIMITS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, LIMITS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void removeRestrictionsForAuthenticatedUser_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, LIMITS_PATH, noContent());

        try (final GitHubApiWireMockSupport.InteractionsApiClient api = interactionsApi(HTTP)) {
            api.api().user().removeRestrictionsForAuthenticatedUser();
        }

        HTTP.verifyRequested(DELETE, LIMITS_PATH);
    }
}
