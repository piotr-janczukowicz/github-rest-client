package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.acceptedJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.organizationsApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubOrganizationsOutsideCollaboratorsApiIntegrationTest {

    private static final JsonType<List<GitHubSimpleUser>> USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);

    private static final String ORG = "github";
    private static final String OUTSIDE_COLLABORATORS_PATH = "/orgs/github/outside_collaborators";
    private static final String OUTSIDE_COLLABORATOR_OCTOCAT_PATH = "/orgs/github/outside_collaborators/octocat";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listOutsideCollaborators_shouldReturnUsers_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, OUTSIDE_COLLABORATORS_PATH, okJson(usersJson("users-one.json")));

        final GitHubPage<GitHubSimpleUser> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().outsideCollaborators().listOutsideCollaborators(ORG);
        }

        final List<GitHubSimpleUser> expected = CODEC.decode(usersJson("users-one.json"), USERS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, OUTSIDE_COLLABORATORS_PATH);
    }

    @Test
    void convertMemberToOutsideCollaborator_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, OUTSIDE_COLLABORATOR_OCTOCAT_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().outsideCollaborators().convertMemberToOutsideCollaborator(ORG, "octocat");
        }

        HTTP.verifyRequested(PUT, OUTSIDE_COLLABORATOR_OCTOCAT_PATH);
    }

    @Test
    void convertMemberToOutsideCollaborator_shouldSucceed_whenResponseIsAccepted() throws Exception {
        HTTP.stub(PUT, OUTSIDE_COLLABORATOR_OCTOCAT_PATH, acceptedJson("{}".getBytes(StandardCharsets.UTF_8)));
        final GitHubConvertMemberToOutsideCollaboratorPayload payload =
                OrganizationsCatalogFixtures.convertMemberToOutsideCollaboratorPayload();

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().outsideCollaborators().convertMemberToOutsideCollaborator(ORG, "octocat", payload);
        }

        HTTP.verifyBody(
                PUT, OUTSIDE_COLLABORATOR_OCTOCAT_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void removeOutsideCollaborator_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, OUTSIDE_COLLABORATOR_OCTOCAT_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().outsideCollaborators().removeOutsideCollaborator(ORG, "octocat");
        }

        HTTP.verifyRequested(DELETE, OUTSIDE_COLLABORATOR_OCTOCAT_PATH);
    }

    private static byte[] usersJson(final String fileName) {
        return jsonBytes(GitHubSimpleUser.class, fileName);
    }
}
