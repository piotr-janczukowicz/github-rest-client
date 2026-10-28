package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.usersApi;
import static org.assertj.core.api.Assertions.assertThat;

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
class GitHubUsersBlockingApiIntegrationTest {

    private static final JsonType<List<GitHubSimpleUser>> USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);

    private static final String BLOCKS_PATH = "/user/blocks";
    private static final String BLOCK_OCTOCAT_PATH = "/user/blocks/octocat";
    private static final String BLOCK_MISSING_PATH = "/user/blocks/missing";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listBlocked_shouldReturnUsers_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, BLOCKS_PATH, okJson(jsonBytes(GitHubUsersBlockingApiIntegrationTest.class, "users-one.json")));

        final GitHubPage<GitHubSimpleUser> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().blocking().listBlocked();
        }

        final List<GitHubSimpleUser> expected =
                CODEC.decode(jsonBytes(GitHubUsersBlockingApiIntegrationTest.class, "users-one.json"), USERS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, BLOCKS_PATH);
    }

    @Test
    void block_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, BLOCK_OCTOCAT_PATH, noContent());

        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            users.api().blocking().block("octocat");
        }

        HTTP.verifyRequested(PUT, BLOCK_OCTOCAT_PATH);
    }

    @Test
    void unblock_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, BLOCK_OCTOCAT_PATH, noContent());

        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            users.api().blocking().unblock("octocat");
        }

        HTTP.verifyRequested(DELETE, BLOCK_OCTOCAT_PATH);
    }

    @Test
    void isBlocked_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, BLOCK_OCTOCAT_PATH, noContent());

        final boolean blocked;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            blocked = users.api().blocking().isBlocked("octocat");
        }

        assertThat(blocked).isTrue();
        HTTP.verifyRequested(GET, BLOCK_OCTOCAT_PATH);
    }

    @Test
    void isBlocked_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, BLOCK_MISSING_PATH, notFound());

        final boolean blocked;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            blocked = users.api().blocking().isBlocked("missing");
        }

        assertThat(blocked).isFalse();
        HTTP.verifyRequested(GET, BLOCK_MISSING_PATH);
    }
}
