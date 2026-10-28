package io.github.piotr_janczukowicz.github.api.collaborators;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.collaboratorsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
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
class GitHubCollaboratorApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String USERNAME = "hubot";

    private static final String COLLECTION_PATH = "/repos/octocat/Hello-World/collaborators";
    private static final String ITEM_PATH = "/repos/octocat/Hello-World/collaborators/hubot";
    private static final String PERMISSION_PATH = "/repos/octocat/Hello-World/collaborators/hubot/permission";

    private static final JsonType<List<GitHubCollaborator>> COLLABORATORS_TYPE =
            JsonTypes.listOf(GitHubCollaborator.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listCollaborators_shouldReturnCollaborators_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(listJsonBytes(GitHubCollaboratorApiIntegrationTest.class, "collaborator.json")));

        final GitHubPage<GitHubCollaborator> page;
        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            page = api.api().collaborator().listCollaborators(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedCollaborators());
        HTTP.verifyRequested(GET, COLLECTION_PATH);
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "per_page", "100");
    }

    @Test
    void listCollaborators_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(listJsonBytes(GitHubCollaboratorApiIntegrationTest.class, "collaborator.json")));

        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            api.api()
                    .collaborator()
                    .listCollaborators(
                            OWNER,
                            REPO,
                            GitHubListCollaboratorsFilterBuilder.builder()
                                    .affiliation(GitHubCollaboratorAffiliation.DIRECT)
                                    .permission(GitHubCollaboratorPermission.PUSH)
                                    .build(),
                            GitHubPageRequest.byPage());
        }

        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "affiliation", "direct");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "permission", "push");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "per_page", "100");
    }

    @Test
    void listCollaborators_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(listJsonBytes(GitHubCollaboratorApiIntegrationTest.class, "collaborator.json"))
                        .header(LINK, linkNext(HTTP.uri(COLLECTION_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubCollaboratorApiIntegrationTest.class, "collaborator.json")));

        final GitHubPage<GitHubCollaborator> first;
        final Optional<GitHubPage<GitHubCollaborator>> second;
        final Optional<GitHubPage<GitHubCollaborator>> third;
        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            first = api.api().collaborator().listCollaborators(OWNER, REPO);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedCollaborators());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedCollaborators());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, COLLECTION_PATH, 2);
    }

    @Test
    void listCollaborators_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(listJsonBytes(GitHubCollaboratorApiIntegrationTest.class, "collaborator.json")));

        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            api.api()
                    .collaborator()
                    .listCollaborators(
                            OWNER, REPO, GitHubListCollaboratorsFilter.empty(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "per_page", "10");
    }

    @Test
    void checkCollaborator_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, ITEM_PATH, noContent());

        final boolean isCollaborator;
        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            isCollaborator = api.api().collaborator().checkCollaborator(OWNER, REPO, USERNAME);
        }

        assertThat(isCollaborator).isTrue();
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void checkCollaborator_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ITEM_PATH, notFound());

        final boolean isCollaborator;
        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            isCollaborator = api.api().collaborator().checkCollaborator(OWNER, REPO, USERNAME);
        }

        assertThat(isCollaborator).isFalse();
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void addCollaborator_shouldReturnInvitation_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                PUT,
                ITEM_PATH,
                createdJson(jsonBytes(GitHubCollaboratorApiIntegrationTest.class, "repository-invitation.json")));
        final GitHubAddCollaboratorPayload payload = CollaboratorsCatalogFixtures.addCollaboratorPayload();

        final Optional<GitHubRepositoryInvitation> invitation;
        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            invitation = api.api().collaborator().addCollaborator(OWNER, REPO, USERNAME, payload);
        }

        final GitHubRepositoryInvitation expected = CODEC.decode(
                jsonBytes(GitHubCollaboratorApiIntegrationTest.class, "repository-invitation.json"),
                GitHubRepositoryInvitation.class);

        assertThat(invitation).contains(expected);
        HTTP.verifyRequested(PUT, ITEM_PATH);
        HTTP.verifyHeader(PUT, ITEM_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, ITEM_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void addCollaborator_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, ITEM_PATH, noContent());

        final Optional<GitHubRepositoryInvitation> invitation;
        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            invitation = api.api().collaborator().addCollaborator(OWNER, REPO, USERNAME);
        }

        assertThat(invitation).isEmpty();
        HTTP.verifyRequested(PUT, ITEM_PATH);
    }

    @Test
    void removeCollaborator_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            api.api().collaborator().removeCollaborator(OWNER, REPO, USERNAME);
        }

        HTTP.verifyRequested(DELETE, ITEM_PATH);
    }

    @Test
    void getCollaboratorPermissionLevel_shouldReturnPermission_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PERMISSION_PATH,
                okJson(jsonBytes(
                        GitHubCollaboratorApiIntegrationTest.class, "repository-collaborator-permission.json")));

        final Optional<GitHubRepositoryCollaboratorPermission> permission;
        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            permission = api.api().collaborator().getCollaboratorPermissionLevel(OWNER, REPO, USERNAME);
        }

        final GitHubRepositoryCollaboratorPermission expected = CODEC.decode(
                jsonBytes(GitHubCollaboratorApiIntegrationTest.class, "repository-collaborator-permission.json"),
                GitHubRepositoryCollaboratorPermission.class);

        assertThat(permission).contains(expected);
        HTTP.verifyRequested(GET, PERMISSION_PATH);
    }

    @Test
    void getCollaboratorPermissionLevel_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, PERMISSION_PATH, notFound());

        final Optional<GitHubRepositoryCollaboratorPermission> permission;
        try (final GitHubApiWireMockSupport.CollaboratorsApiClient api = collaboratorsApi(HTTP)) {
            permission = api.api().collaborator().getCollaboratorPermissionLevel(OWNER, REPO, USERNAME);
        }

        assertThat(permission).isEmpty();
        HTTP.verifyRequested(GET, PERMISSION_PATH);
    }

    private static List<GitHubCollaborator> expectedCollaborators() {
        return CODEC.decode(
                listJsonBytes(GitHubCollaboratorApiIntegrationTest.class, "collaborator.json"), COLLABORATORS_TYPE);
    }
}
