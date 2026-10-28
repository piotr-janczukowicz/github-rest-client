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
class GitHubCopilotSpacesCollaboratorsApiIntegrationTest {

    private static final String ORG = "octo-org";
    private static final String USERNAME = "octocat";
    private static final long SPACE_NUMBER = 1L;
    private static final String ACTOR = "octocat";
    private static final String ORG_COLLABORATORS_PATH = "/orgs/octo-org/copilot-spaces/1/collaborators";
    private static final String ORG_COLLABORATOR_PATH = ORG_COLLABORATORS_PATH + "/User/octocat";
    private static final String USER_COLLABORATORS_PATH = "/users/octocat/copilot-spaces/1/collaborators";
    private static final String USER_COLLABORATOR_PATH = USER_COLLABORATORS_PATH + "/User/octocat";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listCollaboratorsForOrg_shouldReturnCollaborators_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_COLLABORATORS_PATH,
                okJson(jsonBytes(
                        GitHubCopilotSpacesCollaboratorsApiIntegrationTest.class,
                        "copilot-space-collaborators-page.json")));

        final GitHubCopilotSpaceCollaboratorsPage page;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            page = spaces.api().collaborators().listCollaboratorsForOrg(ORG, SPACE_NUMBER);
        }

        final GitHubCopilotSpaceCollaboratorsPage expected = CODEC.decode(
                jsonBytes(
                        GitHubCopilotSpacesCollaboratorsApiIntegrationTest.class,
                        "copilot-space-collaborators-page.json"),
                GitHubCopilotSpaceCollaboratorsPage.class);

        assertThat(page).isEqualTo(expected);
        HTTP.verifyRequested(GET, ORG_COLLABORATORS_PATH);
    }

    @Test
    void addCollaboratorForOrg_shouldSendPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                ORG_COLLABORATORS_PATH,
                createdJson(jsonBytes(
                        GitHubCopilotSpacesCollaboratorsApiIntegrationTest.class,
                        "copilot-space-user-collaborator.json")));
        final GitHubAddCollaboratorForOrgPayload payload = CopilotSpacesCatalogFixtures.addCollaboratorForOrgPayload();

        final GitHubCopilotSpaceCollaborator collaborator;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            collaborator = spaces.api().collaborators().addCollaboratorForOrg(ORG, SPACE_NUMBER, payload);
        }

        final GitHubCopilotSpaceCollaborator expected = CODEC.decode(
                jsonBytes(
                        GitHubCopilotSpacesCollaboratorsApiIntegrationTest.class,
                        "copilot-space-user-collaborator.json"),
                GitHubCopilotSpaceCollaborator.class);

        assertThat(collaborator).isEqualTo(expected);
        HTTP.verifyRequested(POST, ORG_COLLABORATORS_PATH);
        HTTP.verifyHeader(POST, ORG_COLLABORATORS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, ORG_COLLABORATORS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updateCollaboratorForOrg_shouldSendPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                ORG_COLLABORATOR_PATH,
                okJson(jsonBytes(
                        GitHubCopilotSpacesCollaboratorsApiIntegrationTest.class,
                        "copilot-space-team-collaborator.json")));
        final GitHubUpdateCollaboratorPayload payload = CopilotSpacesCatalogFixtures.updateCollaboratorPayload();

        final Optional<GitHubCopilotSpaceCollaborator> collaborator;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            collaborator = spaces.api()
                    .collaborators()
                    .updateCollaboratorForOrg(ORG, SPACE_NUMBER, GitHubCopilotSpaceActorType.USER, ACTOR, payload);
        }

        final GitHubCopilotSpaceCollaborator expected = CODEC.decode(
                jsonBytes(
                        GitHubCopilotSpacesCollaboratorsApiIntegrationTest.class,
                        "copilot-space-team-collaborator.json"),
                GitHubCopilotSpaceCollaborator.class);

        assertThat(collaborator).contains(expected);
        HTTP.verifyRequested(PUT, ORG_COLLABORATOR_PATH);
        HTTP.verifyHeader(PUT, ORG_COLLABORATOR_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, ORG_COLLABORATOR_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updateCollaboratorForOrg_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ORG_COLLABORATOR_PATH, noContent());

        final Optional<GitHubCopilotSpaceCollaborator> collaborator;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            collaborator = spaces.api()
                    .collaborators()
                    .updateCollaboratorForOrg(
                            ORG,
                            SPACE_NUMBER,
                            GitHubCopilotSpaceActorType.USER,
                            ACTOR,
                            CopilotSpacesCatalogFixtures.updateCollaboratorPayload());
        }

        assertThat(collaborator).isEmpty();
        HTTP.verifyRequested(PUT, ORG_COLLABORATOR_PATH);
    }

    @Test
    void removeCollaboratorForOrg_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORG_COLLABORATOR_PATH, noContent());

        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            spaces.api()
                    .collaborators()
                    .removeCollaboratorForOrg(ORG, SPACE_NUMBER, GitHubCopilotSpaceActorType.USER, ACTOR);
        }

        HTTP.verifyRequested(DELETE, ORG_COLLABORATOR_PATH);
    }

    @Test
    void listCollaboratorsForUser_shouldReturnCollaborators_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_COLLABORATORS_PATH,
                okJson(jsonBytes(
                        GitHubCopilotSpacesCollaboratorsApiIntegrationTest.class,
                        "copilot-space-collaborators-page.json")));

        final GitHubCopilotSpaceCollaboratorsPage page;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            page = spaces.api().collaborators().listCollaboratorsForUser(USERNAME, SPACE_NUMBER);
        }

        final GitHubCopilotSpaceCollaboratorsPage expected = CODEC.decode(
                jsonBytes(
                        GitHubCopilotSpacesCollaboratorsApiIntegrationTest.class,
                        "copilot-space-collaborators-page.json"),
                GitHubCopilotSpaceCollaboratorsPage.class);

        assertThat(page).isEqualTo(expected);
        HTTP.verifyRequested(GET, USER_COLLABORATORS_PATH);
    }

    @Test
    void addCollaboratorForUser_shouldSendPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                USER_COLLABORATORS_PATH,
                createdJson(jsonBytes(
                        GitHubCopilotSpacesCollaboratorsApiIntegrationTest.class,
                        "copilot-space-user-collaborator.json")));
        final GitHubAddCollaboratorForUserPayload payload =
                CopilotSpacesCatalogFixtures.addCollaboratorForUserPayload();

        final GitHubCopilotSpaceCollaborator collaborator;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            collaborator = spaces.api().collaborators().addCollaboratorForUser(USERNAME, SPACE_NUMBER, payload);
        }

        final GitHubCopilotSpaceCollaborator expected = CODEC.decode(
                jsonBytes(
                        GitHubCopilotSpacesCollaboratorsApiIntegrationTest.class,
                        "copilot-space-user-collaborator.json"),
                GitHubCopilotSpaceCollaborator.class);

        assertThat(collaborator).isEqualTo(expected);
        HTTP.verifyRequested(POST, USER_COLLABORATORS_PATH);
        HTTP.verifyHeader(POST, USER_COLLABORATORS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, USER_COLLABORATORS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updateCollaboratorForUser_shouldSendPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                USER_COLLABORATOR_PATH,
                okJson(jsonBytes(
                        GitHubCopilotSpacesCollaboratorsApiIntegrationTest.class,
                        "copilot-space-team-collaborator.json")));
        final GitHubUpdateCollaboratorPayload payload = CopilotSpacesCatalogFixtures.updateCollaboratorPayload();

        final Optional<GitHubCopilotSpaceCollaborator> collaborator;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            collaborator = spaces.api()
                    .collaborators()
                    .updateCollaboratorForUser(
                            USERNAME, SPACE_NUMBER, GitHubCopilotSpaceActorType.USER, ACTOR, payload);
        }

        final GitHubCopilotSpaceCollaborator expected = CODEC.decode(
                jsonBytes(
                        GitHubCopilotSpacesCollaboratorsApiIntegrationTest.class,
                        "copilot-space-team-collaborator.json"),
                GitHubCopilotSpaceCollaborator.class);

        assertThat(collaborator).contains(expected);
        HTTP.verifyRequested(PUT, USER_COLLABORATOR_PATH);
        HTTP.verifyHeader(PUT, USER_COLLABORATOR_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, USER_COLLABORATOR_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updateCollaboratorForUser_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, USER_COLLABORATOR_PATH, noContent());

        final Optional<GitHubCopilotSpaceCollaborator> collaborator;
        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            collaborator = spaces.api()
                    .collaborators()
                    .updateCollaboratorForUser(
                            USERNAME,
                            SPACE_NUMBER,
                            GitHubCopilotSpaceActorType.USER,
                            ACTOR,
                            CopilotSpacesCatalogFixtures.updateCollaboratorPayload());
        }

        assertThat(collaborator).isEmpty();
        HTTP.verifyRequested(PUT, USER_COLLABORATOR_PATH);
    }

    @Test
    void removeCollaboratorForUser_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_COLLABORATOR_PATH, noContent());

        try (final GitHubApiWireMockSupport.CopilotSpacesApiClient spaces = copilotSpacesApi(HTTP)) {
            spaces.api()
                    .collaborators()
                    .removeCollaboratorForUser(USERNAME, SPACE_NUMBER, GitHubCopilotSpaceActorType.USER, ACTOR);
        }

        HTTP.verifyRequested(DELETE, USER_COLLABORATOR_PATH);
    }
}
