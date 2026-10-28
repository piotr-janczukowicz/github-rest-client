package io.github.piotr_janczukowicz.github.api.collaborators;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.collaboratorsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCollaboratorsInvitationsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long INVITATION_ID = 42L;

    private static final String REPO_COLLECTION_PATH = "/repos/octocat/Hello-World/invitations";
    private static final String REPO_ITEM_PATH = "/repos/octocat/Hello-World/invitations/42";
    private static final String USER_COLLECTION_PATH = "/user/repository_invitations";
    private static final String USER_ITEM_PATH = "/user/repository_invitations/42";

    private static final JsonType<List<GitHubRepositoryInvitation>> INVITATIONS_TYPE =
            JsonTypes.listOf(GitHubRepositoryInvitation.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listInvitations_shouldReturnInvitations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_COLLECTION_PATH,
                okJson(listJsonBytes(
                        GitHubCollaboratorsInvitationsApiIntegrationTest.class, "repository-invitation.json")));

        final GitHubPage<GitHubRepositoryInvitation> page;
        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            page = api.api().invitations().listInvitations(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedInvitations());
        HTTP.verifyRequested(GET, REPO_COLLECTION_PATH);
        HTTP.verifyQueryParam(GET, REPO_COLLECTION_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, REPO_COLLECTION_PATH, "per_page", "100");
    }

    @Test
    void listInvitations_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        assertFollowsLinkNext(REPO_COLLECTION_PATH, api -> api.invitations().listInvitations(OWNER, REPO));
    }

    @Test
    void listInvitations_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                REPO_COLLECTION_PATH,
                okJson(listJsonBytes(
                        GitHubCollaboratorsInvitationsApiIntegrationTest.class, "repository-invitation.json")));

        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            api.api().invitations().listInvitations(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, REPO_COLLECTION_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, REPO_COLLECTION_PATH, "per_page", "10");
    }

    @Test
    void updateInvitation_shouldReturnInvitation_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                REPO_ITEM_PATH,
                okJson(jsonBytes(
                        GitHubCollaboratorsInvitationsApiIntegrationTest.class, "repository-invitation.json")));
        final GitHubUpdateRepositoryInvitationPayload payload =
                CollaboratorsCatalogFixtures.updateRepositoryInvitationPayload();

        final GitHubRepositoryInvitation invitation;
        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            invitation = api.api().invitations().updateInvitation(OWNER, REPO, INVITATION_ID, payload);
        }

        final GitHubRepositoryInvitation expected = CODEC.decode(
                jsonBytes(GitHubCollaboratorsInvitationsApiIntegrationTest.class, "repository-invitation.json"),
                GitHubRepositoryInvitation.class);

        assertThat(invitation).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, REPO_ITEM_PATH);
        HTTP.verifyHeader(PATCH, REPO_ITEM_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, REPO_ITEM_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteInvitation_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, REPO_ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            api.api().invitations().deleteInvitation(OWNER, REPO, INVITATION_ID);
        }

        HTTP.verifyRequested(DELETE, REPO_ITEM_PATH);
    }

    @Test
    void listInvitationsForAuthenticatedUser_shouldReturnInvitations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_COLLECTION_PATH,
                okJson(listJsonBytes(
                        GitHubCollaboratorsInvitationsApiIntegrationTest.class, "repository-invitation.json")));

        final GitHubPage<GitHubRepositoryInvitation> page;
        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            page = api.api().invitations().listInvitationsForAuthenticatedUser();
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedInvitations());
        HTTP.verifyRequested(GET, USER_COLLECTION_PATH);
        HTTP.verifyQueryParam(GET, USER_COLLECTION_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, USER_COLLECTION_PATH, "per_page", "100");
    }

    @Test
    void listInvitationsForAuthenticatedUser_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        assertFollowsLinkNext(USER_COLLECTION_PATH, api -> api.invitations().listInvitationsForAuthenticatedUser());
    }

    @Test
    void listInvitationsForAuthenticatedUser_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest()
            throws Exception {
        HTTP.stub(
                GET,
                USER_COLLECTION_PATH,
                okJson(listJsonBytes(
                        GitHubCollaboratorsInvitationsApiIntegrationTest.class, "repository-invitation.json")));

        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            api.api().invitations().listInvitationsForAuthenticatedUser(GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, USER_COLLECTION_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, USER_COLLECTION_PATH, "per_page", "10");
    }

    @Test
    void acceptInvitationForAuthenticatedUser_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PATCH, USER_ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            api.api().invitations().acceptInvitationForAuthenticatedUser(INVITATION_ID);
        }

        HTTP.verifyRequested(PATCH, USER_ITEM_PATH);
    }

    @Test
    void declineInvitationForAuthenticatedUser_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            api.api().invitations().declineInvitationForAuthenticatedUser(INVITATION_ID);
        }

        HTTP.verifyRequested(DELETE, USER_ITEM_PATH);
    }

    private static List<GitHubRepositoryInvitation> expectedInvitations() {
        return CODEC.decode(
                listJsonBytes(GitHubCollaboratorsInvitationsApiIntegrationTest.class, "repository-invitation.json"),
                INVITATIONS_TYPE);
    }

    private static void assertFollowsLinkNext(final String path, final InvitationsPageLoader loader) throws Exception {
        HTTP.stub(
                GET,
                path,
                okJson(listJsonBytes(
                                GitHubCollaboratorsInvitationsApiIntegrationTest.class, "repository-invitation.json"))
                        .header(LINK, linkNext(HTTP.uri(path + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(
                        GitHubCollaboratorsInvitationsApiIntegrationTest.class, "repository-invitation.json")));

        final GitHubPage<GitHubRepositoryInvitation> first;
        final Optional<GitHubPage<GitHubRepositoryInvitation>> second;
        final Optional<GitHubPage<GitHubRepositoryInvitation>> third;
        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            first = loader.load(api.api());
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedInvitations());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedInvitations());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, path, 2);
    }

    @FunctionalInterface
    private interface InvitationsPageLoader {
        GitHubPage<GitHubRepositoryInvitation> load(GitHubCollaboratorsApi api) throws Exception;
    }
}
