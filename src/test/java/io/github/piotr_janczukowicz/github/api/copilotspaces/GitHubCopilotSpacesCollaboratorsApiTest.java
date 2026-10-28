package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
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
import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCopilotSpacesCollaboratorsApiTest {

    private static final String ORG = "octo-org";
    private static final String USERNAME = "octocat";
    private static final long SPACE_NUMBER = 1L;
    private static final String ACTOR = "octocat";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotSpacesCollaboratorsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listCollaboratorsForOrg_shouldReturnCollaborators_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("copilot-space-collaborators-page.json")));

        final GitHubCopilotSpaceCollaboratorsPage page = api().listCollaboratorsForOrg(ORG, SPACE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceCollaboratorsPage());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/copilot-spaces/1/collaborators");
        });
    }

    @Test
    void addCollaboratorForOrg_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("copilot-space-user-collaborator.json")));

        final GitHubCopilotSpaceCollaborator collaborator = api().addCollaboratorForOrg(
                        ORG, SPACE_NUMBER, CopilotSpacesCatalogFixtures.addCollaboratorForOrgPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(collaborator).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/copilot-spaces/1/collaborators");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void updateCollaboratorForOrg_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("copilot-space-team-collaborator.json")));

        final Optional<GitHubCopilotSpaceCollaborator> collaborator = api().updateCollaboratorForOrg(
                        ORG,
                        SPACE_NUMBER,
                        GitHubCopilotSpaceActorType.USER,
                        ACTOR,
                        CopilotSpacesCatalogFixtures.updateCollaboratorPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(collaborator).contains(CopilotSpacesCatalogFixtures.copilotSpaceTeamCollaborator());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/copilot-spaces/1/collaborators/User/octocat");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void updateCollaboratorForOrg_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final Optional<GitHubCopilotSpaceCollaborator> collaborator = api().updateCollaboratorForOrg(
                        ORG,
                        SPACE_NUMBER,
                        GitHubCopilotSpaceActorType.TEAM,
                        ACTOR,
                        CopilotSpacesCatalogFixtures.updateCollaboratorPayload());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(collaborator).isEmpty();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octo-org/copilot-spaces/1/collaborators/Team/octocat");
        });
    }

    @Test
    void removeCollaboratorForOrg_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeCollaboratorForOrg(ORG, SPACE_NUMBER, GitHubCopilotSpaceActorType.USER, ACTOR);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/copilot-spaces/1/collaborators/User/octocat");
        });
    }

    @Test
    void listCollaboratorsForUser_shouldReturnCollaborators_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("copilot-space-collaborators-page.json")));

        final GitHubCopilotSpaceCollaboratorsPage page = api().listCollaboratorsForUser(USERNAME, SPACE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceCollaboratorsPage());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/copilot-spaces/1/collaborators");
        });
    }

    @Test
    void addCollaboratorForUser_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("copilot-space-user-collaborator.json")));

        final GitHubCopilotSpaceCollaborator collaborator = api().addCollaboratorForUser(
                        USERNAME, SPACE_NUMBER, CopilotSpacesCatalogFixtures.addCollaboratorForUserPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(collaborator).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceUserCollaborator());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/copilot-spaces/1/collaborators");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void updateCollaboratorForUser_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("copilot-space-team-collaborator.json")));

        final Optional<GitHubCopilotSpaceCollaborator> collaborator = api().updateCollaboratorForUser(
                        USERNAME,
                        SPACE_NUMBER,
                        GitHubCopilotSpaceActorType.USER,
                        ACTOR,
                        CopilotSpacesCatalogFixtures.updateCollaboratorPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(collaborator).contains(CopilotSpacesCatalogFixtures.copilotSpaceTeamCollaborator());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/copilot-spaces/1/collaborators/User/octocat");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void updateCollaboratorForUser_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final Optional<GitHubCopilotSpaceCollaborator> collaborator = api().updateCollaboratorForUser(
                        USERNAME,
                        SPACE_NUMBER,
                        GitHubCopilotSpaceActorType.TEAM,
                        ACTOR,
                        CopilotSpacesCatalogFixtures.updateCollaboratorPayload());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(collaborator).isEmpty();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/users/octocat/copilot-spaces/1/collaborators/Team/octocat");
        });
    }

    @Test
    void removeCollaboratorForUser_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeCollaboratorForUser(USERNAME, SPACE_NUMBER, GitHubCopilotSpaceActorType.USER, ACTOR);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/copilot-spaces/1/collaborators/User/octocat");
        });
    }

    @ParameterizedTest
    @MethodSource("nullActorTypeCalls")
    void validation_shouldThrow_whenActorTypeNull(final ApiCall call) {
        final GitHubCopilotSpacesCollaboratorsApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("actor_type");
    }

    @ParameterizedTest
    @MethodSource("nullPayloadCalls")
    void validation_shouldThrow_whenPayloadNull(final ApiCall call) {
        final GitHubCopilotSpacesCollaboratorsApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @ParameterizedTest
    @MethodSource("invalidCalls")
    void call_shouldThrowIllegalArgument_whenInvalid(final ApiCall call, final String messageFragment) {
        final GitHubCopilotSpacesCollaboratorsApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private GitHubCopilotSpacesCollaboratorsApi api() {
        return new GitHubCopilotSpacesCollaboratorsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCopilotSpacesCollaboratorsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<ApiCall> nullActorTypeCalls() {
        return Stream.of(
                api -> api.updateCollaboratorForOrg(
                        ORG, SPACE_NUMBER, null, ACTOR, CopilotSpacesCatalogFixtures.updateCollaboratorPayload()),
                api -> api.removeCollaboratorForOrg(ORG, SPACE_NUMBER, null, ACTOR),
                api -> api.updateCollaboratorForUser(
                        USERNAME, SPACE_NUMBER, null, ACTOR, CopilotSpacesCatalogFixtures.updateCollaboratorPayload()),
                api -> api.removeCollaboratorForUser(USERNAME, SPACE_NUMBER, null, ACTOR));
    }

    private static Stream<ApiCall> nullPayloadCalls() {
        return Stream.of(
                api -> api.addCollaboratorForOrg(ORG, SPACE_NUMBER, null),
                api -> api.updateCollaboratorForOrg(ORG, SPACE_NUMBER, GitHubCopilotSpaceActorType.USER, ACTOR, null),
                api -> api.addCollaboratorForUser(USERNAME, SPACE_NUMBER, null),
                api -> api.updateCollaboratorForUser(
                        USERNAME, SPACE_NUMBER, GitHubCopilotSpaceActorType.USER, ACTOR, null));
    }

    private static Stream<Arguments> invalidCalls() {
        return Stream.of(
                Arguments.of((ApiCall) api -> api.listCollaboratorsForOrg(" ", SPACE_NUMBER), "org"),
                Arguments.of(
                        (ApiCall) api -> api.addCollaboratorForOrg(
                                " ", SPACE_NUMBER, CopilotSpacesCatalogFixtures.addCollaboratorForOrgPayload()),
                        "org"),
                Arguments.of(
                        (ApiCall) api -> api.updateCollaboratorForOrg(
                                " ",
                                SPACE_NUMBER,
                                GitHubCopilotSpaceActorType.USER,
                                ACTOR,
                                CopilotSpacesCatalogFixtures.updateCollaboratorPayload()),
                        "org"),
                Arguments.of(
                        (ApiCall) api -> api.updateCollaboratorForOrg(
                                ORG,
                                SPACE_NUMBER,
                                GitHubCopilotSpaceActorType.USER,
                                " ",
                                CopilotSpacesCatalogFixtures.updateCollaboratorPayload()),
                        "actor_identifier"),
                Arguments.of(
                        (ApiCall) api -> api.removeCollaboratorForOrg(
                                " ", SPACE_NUMBER, GitHubCopilotSpaceActorType.USER, ACTOR),
                        "org"),
                Arguments.of(
                        (ApiCall) api ->
                                api.removeCollaboratorForOrg(ORG, SPACE_NUMBER, GitHubCopilotSpaceActorType.USER, " "),
                        "actor_identifier"),
                Arguments.of((ApiCall) api -> api.listCollaboratorsForUser(" ", SPACE_NUMBER), "username"),
                Arguments.of(
                        (ApiCall) api -> api.addCollaboratorForUser(
                                " ", SPACE_NUMBER, CopilotSpacesCatalogFixtures.addCollaboratorForUserPayload()),
                        "username"),
                Arguments.of(
                        (ApiCall) api -> api.updateCollaboratorForUser(
                                " ",
                                SPACE_NUMBER,
                                GitHubCopilotSpaceActorType.USER,
                                ACTOR,
                                CopilotSpacesCatalogFixtures.updateCollaboratorPayload()),
                        "username"),
                Arguments.of(
                        (ApiCall) api -> api.updateCollaboratorForUser(
                                USERNAME,
                                SPACE_NUMBER,
                                GitHubCopilotSpaceActorType.USER,
                                " ",
                                CopilotSpacesCatalogFixtures.updateCollaboratorPayload()),
                        "actor_identifier"),
                Arguments.of(
                        (ApiCall) api -> api.removeCollaboratorForUser(
                                " ", SPACE_NUMBER, GitHubCopilotSpaceActorType.USER, ACTOR),
                        "username"),
                Arguments.of(
                        (ApiCall) api -> api.removeCollaboratorForUser(
                                USERNAME, SPACE_NUMBER, GitHubCopilotSpaceActorType.USER, " "),
                        "actor_identifier"));
    }

    @FunctionalInterface
    interface ApiCall {
        void execute(GitHubCopilotSpacesCollaboratorsApi api) throws Exception;
    }
}
