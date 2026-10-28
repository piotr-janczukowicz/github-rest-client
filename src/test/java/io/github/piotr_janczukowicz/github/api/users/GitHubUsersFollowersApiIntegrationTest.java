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
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubUsersFollowersApiIntegrationTest {

    private static final String USER_FOLLOWERS_PATH = "/user/followers";
    private static final String USER_FOLLOWING_PATH = "/user/following";
    private static final String OCTOCAT_FOLLOWERS_PATH = "/users/octocat/followers";
    private static final String OCTOCAT_FOLLOWING_PATH = "/users/octocat/following";
    private static final String AUTH_FOLLOWING_OCTOCAT_PATH = "/user/following/octocat";
    private static final String OCTOCAT_FOLLOWING_HUBOT_PATH = "/users/octocat/following/hubot";
    private static final String TARGET_USER = "hubot";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listFollowers_shouldReturnFollowers_whenAuthenticatedUser() throws Exception {
        HTTP.stub(
                GET,
                USER_FOLLOWERS_PATH,
                okJson(jsonBytes(GitHubUsersFollowersApiIntegrationTest.class, "users-one.json")));

        final GitHubPage<GitHubSimpleUser> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().followers().listFollowers();
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedUsersOne());
    }

    @Test
    void listFollowing_shouldReturnFollowing_whenAuthenticatedUser() throws Exception {
        HTTP.stub(
                GET,
                USER_FOLLOWING_PATH,
                okJson(jsonBytes(GitHubUsersFollowersApiIntegrationTest.class, "users-one.json")));

        final GitHubPage<GitHubSimpleUser> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().followers().listFollowing();
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedUsersOne());
    }

    @Test
    void listFollowers_shouldReturnFollowers_whenUsernameProvided() throws Exception {
        HTTP.stub(
                GET,
                OCTOCAT_FOLLOWERS_PATH,
                okJson(jsonBytes(GitHubUsersFollowersApiIntegrationTest.class, "users-one.json")));

        final GitHubPage<GitHubSimpleUser> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().followers().listFollowers(octocat().login());
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedUsersOne());
    }

    @Test
    void listFollowing_shouldReturnFollowing_whenUsernameProvided() throws Exception {
        HTTP.stub(
                GET,
                OCTOCAT_FOLLOWING_PATH,
                okJson(jsonBytes(GitHubUsersFollowersApiIntegrationTest.class, "users-one.json")));

        final GitHubPage<GitHubSimpleUser> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().followers().listFollowing(octocat().login());
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedUsersOne());
    }

    @Test
    void follow_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, AUTH_FOLLOWING_OCTOCAT_PATH, noContent());

        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            users.api().followers().follow(octocat().login());
        }

        HTTP.verifyRequested(PUT, AUTH_FOLLOWING_OCTOCAT_PATH);
    }

    @Test
    void unfollow_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, AUTH_FOLLOWING_OCTOCAT_PATH, noContent());

        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            users.api().followers().unfollow(octocat().login());
        }

        HTTP.verifyRequested(DELETE, AUTH_FOLLOWING_OCTOCAT_PATH);
    }

    @Test
    void isFollowing_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, AUTH_FOLLOWING_OCTOCAT_PATH, noContent());

        final boolean following;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            following = users.api().followers().isFollowing(octocat().login());
        }

        assertThat(following).isTrue();
    }

    @Test
    void isFollowing_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, AUTH_FOLLOWING_OCTOCAT_PATH, notFound());

        final boolean following;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            following = users.api().followers().isFollowing(octocat().login());
        }

        assertThat(following).isFalse();
    }

    @Test
    void isFollowing_shouldReturnTrue_whenUserPairExists() throws Exception {
        HTTP.stub(GET, OCTOCAT_FOLLOWING_HUBOT_PATH, noContent());

        final boolean following;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            following = users.api().followers().isFollowing(octocat().login(), TARGET_USER);
        }

        assertThat(following).isTrue();
    }

    @Test
    void isFollowing_shouldReturnFalse_whenUserPairNotFound() throws Exception {
        HTTP.stub(GET, OCTOCAT_FOLLOWING_HUBOT_PATH, notFound());

        final boolean following;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            following = users.api().followers().isFollowing(octocat().login(), TARGET_USER);
        }

        assertThat(following).isFalse();
    }

    private static GitHubSimpleUser octocat() {
        return CODEC.decode(
                jsonBytes(GitHubUsersFollowersApiIntegrationTest.class, "simple-user-octocat.json"),
                GitHubSimpleUser.class);
    }

    private static List<GitHubSimpleUser> expectedUsersOne() {
        return CODEC.decode(
                jsonBytes(GitHubUsersFollowersApiIntegrationTest.class, "users-one.json"),
                JsonTypes.listOf(GitHubSimpleUser.class));
    }
}
