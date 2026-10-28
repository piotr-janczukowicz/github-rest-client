package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.copilotSpacesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCopilotSpaceApiIntegrationTest {

    private static final String ORG = "octo-org";
    private static final String USERNAME = "octocat";
    private static final long SPACE_NUMBER = 1L;
    private static final String ORG_SPACES_PATH = "/orgs/octo-org/copilot-spaces";
    private static final String ORG_SPACE_PATH = ORG_SPACES_PATH + "/1";
    private static final String USER_SPACES_PATH = "/users/octocat/copilot-spaces";
    private static final String USER_SPACE_PATH = USER_SPACES_PATH + "/1";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listForOrg_shouldReturnSpaces_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_SPACES_PATH,
                okJson(jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-spaces-page.json")));

        final GitHubPage<GitHubCopilotSpace> page;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            page = spaces.api().copilotSpace().listForOrg(ORG);
        }

        final GitHubCopilotSpacesPage expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-spaces-page.json"),
                GitHubCopilotSpacesPage.class);

        assertThat(page.items()).containsExactlyElementsOf(expected.spaces());
        HTTP.verifyRequested(GET, ORG_SPACES_PATH);
        HTTP.verifyQueryParam(GET, ORG_SPACES_PATH, "per_page", "100");
        HTTP.verifyNoQueryParam(GET, ORG_SPACES_PATH, "page");
    }

    @Test
    void listForOrg_shouldSendCursor_whenCursorRequestProvided() throws Exception {
        HTTP.stub(
                GET,
                ORG_SPACES_PATH,
                okJson(jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-spaces-page.json")));

        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            spaces.api().copilotSpace().listForOrg(ORG, GitHubCursorRequest.after("tok", 50));
        }

        HTTP.verifyQueryParam(GET, ORG_SPACES_PATH, "after", "tok");
        HTTP.verifyQueryParam(GET, ORG_SPACES_PATH, "per_page", "50");
    }

    @Test
    void createForOrg_shouldSendPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                ORG_SPACES_PATH,
                createdJson(jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-space.json")));
        final GitHubCreateForOrgPayload payload = CopilotSpacesCatalogFixtures.createForOrgPayload();

        final GitHubCopilotSpace space;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            space = spaces.api().copilotSpace().createForOrg(ORG, payload);
        }

        final GitHubCopilotSpace expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-space.json"), GitHubCopilotSpace.class);

        assertThat(space).isEqualTo(expected);
        HTTP.verifyRequested(POST, ORG_SPACES_PATH);
        HTTP.verifyHeader(POST, ORG_SPACES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, ORG_SPACES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getForOrg_shouldReturnSpace_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_SPACE_PATH,
                okJson(jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-space.json")));

        final GitHubCopilotSpace space;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            space = spaces.api().copilotSpace().getForOrg(ORG, SPACE_NUMBER).orElseThrow();
        }

        final GitHubCopilotSpace expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-space.json"), GitHubCopilotSpace.class);

        assertThat(space).isEqualTo(expected);
        HTTP.verifyRequested(GET, ORG_SPACE_PATH);
    }

    @Test
    void getForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_SPACE_PATH, notFound());

        final Optional<GitHubCopilotSpace> space;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            space = spaces.api().copilotSpace().getForOrg(ORG, SPACE_NUMBER);
        }

        assertThat(space).isEmpty();
    }

    @Test
    void updateForOrg_shouldSendPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                ORG_SPACE_PATH,
                okJson(jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-space.json")));
        final GitHubUpdateForOrgPayload payload = CopilotSpacesCatalogFixtures.updateForOrgPayload();

        final GitHubCopilotSpace space;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            space = spaces.api().copilotSpace().updateForOrg(ORG, SPACE_NUMBER, payload);
        }

        final GitHubCopilotSpace expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-space.json"), GitHubCopilotSpace.class);

        assertThat(space).isEqualTo(expected);
        HTTP.verifyRequested(PUT, ORG_SPACE_PATH);
        HTTP.verifyHeader(PUT, ORG_SPACE_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, ORG_SPACE_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteForOrg_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORG_SPACE_PATH, noContent());

        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            spaces.api().copilotSpace().deleteForOrg(ORG, SPACE_NUMBER);
        }

        HTTP.verifyRequested(DELETE, ORG_SPACE_PATH);
    }

    @Test
    void listForUser_shouldReturnSpaces_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_SPACES_PATH,
                okJson(jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-spaces-page.json")));

        final GitHubPage<GitHubCopilotSpace> page;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            page = spaces.api().copilotSpace().listForUser(USERNAME);
        }

        final GitHubCopilotSpacesPage expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-spaces-page.json"),
                GitHubCopilotSpacesPage.class);

        assertThat(page.items()).containsExactlyElementsOf(expected.spaces());
        HTTP.verifyRequested(GET, USER_SPACES_PATH);
        HTTP.verifyQueryParam(GET, USER_SPACES_PATH, "per_page", "100");
        HTTP.verifyNoQueryParam(GET, USER_SPACES_PATH, "page");
    }

    @Test
    void listForUser_shouldSendCursor_whenCursorRequestProvided() throws Exception {
        HTTP.stub(
                GET,
                USER_SPACES_PATH,
                okJson(jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-spaces-page.json")));

        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            spaces.api().copilotSpace().listForUser(USERNAME, GitHubCursorRequest.after("tok", 50));
        }

        HTTP.verifyQueryParam(GET, USER_SPACES_PATH, "after", "tok");
        HTTP.verifyQueryParam(GET, USER_SPACES_PATH, "per_page", "50");
    }

    @Test
    void createForUser_shouldSendPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                USER_SPACES_PATH,
                createdJson(jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-space.json")));
        final GitHubCreateForUserPayload payload = CopilotSpacesCatalogFixtures.createForUserPayload();

        final GitHubCopilotSpace space;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            space = spaces.api().copilotSpace().createForUser(USERNAME, payload);
        }

        final GitHubCopilotSpace expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-space.json"), GitHubCopilotSpace.class);

        assertThat(space).isEqualTo(expected);
        HTTP.verifyRequested(POST, USER_SPACES_PATH);
        HTTP.verifyHeader(POST, USER_SPACES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, USER_SPACES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getForUser_shouldReturnSpace_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_SPACE_PATH,
                okJson(jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-space.json")));

        final GitHubCopilotSpace space;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            space = spaces.api()
                    .copilotSpace()
                    .getForUser(USERNAME, SPACE_NUMBER)
                    .orElseThrow();
        }

        final GitHubCopilotSpace expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-space.json"), GitHubCopilotSpace.class);

        assertThat(space).isEqualTo(expected);
        HTTP.verifyRequested(GET, USER_SPACE_PATH);
    }

    @Test
    void getForUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, USER_SPACE_PATH, notFound());

        final Optional<GitHubCopilotSpace> space;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            space = spaces.api().copilotSpace().getForUser(USERNAME, SPACE_NUMBER);
        }

        assertThat(space).isEmpty();
    }

    @Test
    void updateForUser_shouldSendPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                USER_SPACE_PATH,
                okJson(jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-space.json")));
        final GitHubUpdateForUserPayload payload = CopilotSpacesCatalogFixtures.updateForUserPayload();

        final GitHubCopilotSpace space;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            space = spaces.api().copilotSpace().updateForUser(USERNAME, SPACE_NUMBER, payload);
        }

        final GitHubCopilotSpace expected = CODEC.decode(
                jsonBytes(GitHubCopilotSpaceApiIntegrationTest.class, "copilot-space.json"), GitHubCopilotSpace.class);

        assertThat(space).isEqualTo(expected);
        HTTP.verifyRequested(PUT, USER_SPACE_PATH);
        HTTP.verifyHeader(PUT, USER_SPACE_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, USER_SPACE_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteForUser_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_SPACE_PATH, noContent());

        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            spaces.api().copilotSpace().deleteForUser(USERNAME, SPACE_NUMBER);
        }

        HTTP.verifyRequested(DELETE, USER_SPACE_PATH);
    }
}
