package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
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
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubOrganizationsBlockingApiIntegrationTest {

    private static final JsonType<List<GitHubSimpleUser>> USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);

    private static final String ORG = "github";
    private static final String BLOCKS_PATH = "/orgs/github/blocks";
    private static final String BLOCK_OCTOCAT_PATH = "/orgs/github/blocks/octocat";
    private static final String BLOCK_MISSING_PATH = "/orgs/github/blocks/missing";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listBlocked_shouldReturnUsers_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, BLOCKS_PATH, okJson(usersJson("users-one.json")));

        final GitHubPage<GitHubSimpleUser> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().blocking().listBlocked(ORG);
        }

        final List<GitHubSimpleUser> expected = CODEC.decode(usersJson("users-one.json"), USERS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, BLOCKS_PATH);
    }

    @Test
    void block_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, BLOCK_OCTOCAT_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().blocking().block(ORG, "octocat");
        }

        HTTP.verifyRequested(PUT, BLOCK_OCTOCAT_PATH);
    }

    @Test
    void unblock_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, BLOCK_OCTOCAT_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().blocking().unblock(ORG, "octocat");
        }

        HTTP.verifyRequested(DELETE, BLOCK_OCTOCAT_PATH);
    }

    @Test
    void isBlocked_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, BLOCK_OCTOCAT_PATH, noContent());

        final boolean blocked;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            blocked = organizations.api().blocking().isBlocked(ORG, "octocat");
        }

        assertThat(blocked).isTrue();
        HTTP.verifyRequested(GET, BLOCK_OCTOCAT_PATH);
    }

    @Test
    void isBlocked_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, BLOCK_MISSING_PATH, notFound());

        final boolean blocked;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            blocked = organizations.api().blocking().isBlocked(ORG, "missing");
        }

        assertThat(blocked).isFalse();
        HTTP.verifyRequested(GET, BLOCK_MISSING_PATH);
    }

    private static byte[] usersJson(final String fileName) {
        return jsonBytes(GitHubSimpleUser.class, fileName);
    }
}
