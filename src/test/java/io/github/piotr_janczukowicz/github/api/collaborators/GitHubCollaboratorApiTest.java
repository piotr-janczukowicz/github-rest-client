package io.github.piotr_janczukowicz.github.api.collaborators;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCollaboratorApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String USERNAME = "hubot";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCollaboratorApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listCollaborators_shouldReturnCollaborators_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("collaborator.json") + "]"));

        final GitHubPage<GitHubCollaborator> page = api().listCollaborators(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(CollaboratorsCatalogFixtures.collaborator());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/collaborators?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listCollaborators_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("collaborator.json") + "]"));

        final GitHubPage<GitHubCollaborator> page = api().listCollaborators(
                        OWNER,
                        REPO,
                        GitHubListCollaboratorsFilterBuilder.builder()
                                .affiliation(GitHubCollaboratorAffiliation.DIRECT)
                                .permission(GitHubCollaboratorPermission.PUSH)
                                .build(),
                        GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(CollaboratorsCatalogFixtures.collaborator());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri).hasParameter("affiliation", "direct");
            softly.assertThat(requestUri).hasParameter("permission", "push");
            softly.assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/Hello-World/collaborators");
        });
    }

    @Test
    void checkCollaborator_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final boolean isCollaborator = api().checkCollaborator(OWNER, REPO, USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(isCollaborator).isTrue();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/collaborators/hubot");
        });
    }

    @Test
    void checkCollaborator_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final boolean isCollaborator = api().checkCollaborator(OWNER, REPO, USERNAME);

        assertThat(isCollaborator).isFalse();
    }

    @Test
    void addCollaborator_shouldReturnInvitation_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("repository-invitation.json")));

        final Optional<GitHubRepositoryInvitation> invitation =
                api().addCollaborator(OWNER, REPO, USERNAME, CollaboratorsCatalogFixtures.addCollaboratorPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(invitation).contains(CollaboratorsCatalogFixtures.repositoryInvitation());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/collaborators/hubot");
        });
    }

    @Test
    void addCollaborator_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final Optional<GitHubRepositoryInvitation> invitation = api().addCollaborator(OWNER, REPO, USERNAME);

        assertThat(invitation).isEmpty();
    }

    @Test
    void removeCollaborator_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeCollaborator(OWNER, REPO, USERNAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/collaborators/hubot");
        });
    }

    @Test
    void getCollaboratorPermissionLevel_shouldReturnPermission_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("repository-collaborator-permission.json")));

        final Optional<GitHubRepositoryCollaboratorPermission> permission =
                api().getCollaboratorPermissionLevel(OWNER, REPO, USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(permission).contains(CollaboratorsCatalogFixtures.repositoryCollaboratorPermission());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/collaborators/hubot/permission");
        });
    }

    @Test
    void getCollaboratorPermissionLevel_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubRepositoryCollaboratorPermission> permission =
                api().getCollaboratorPermissionLevel(OWNER, REPO, USERNAME);

        assertThat(permission).isEmpty();
    }

    private GitHubCollaboratorApi api() {
        return new GitHubCollaboratorApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCollaboratorApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
