package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.usersApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubSinceRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubUsersApiIntegrationTest {

    @Nested
    class Auth {
        private static final String USER_PATH = "/user";
        private static final String USER_BY_ID_PATH = "/user/1";
        private static final String USER_BY_LOGIN_PATH = "/users/octocat";
        private static final String HOVERCARD_PATH = "/users/octocat/hovercard";

        @RegisterExtension
        final StubHttpServer http = new StubHttpServer();

        @Test
        void getAuthenticatedUser_shouldReturnUser_whenResponseIsOk() throws Exception {
            http.stub(
                    GET,
                    USER_PATH,
                    okJson(jsonBytes(GitHubUsersApiIntegrationTest.Auth.class, "private-user-octocat.json")));

            final GitHubUserView user;
            try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(http)) {
                user = users.api().user().getAuthenticatedUser();
            }

            final GitHubUserView expected = CODEC.decode(
                    jsonBytes(GitHubUsersApiIntegrationTest.Auth.class, "private-user-octocat.json"),
                    GitHubUserView.class);

            assertThat(user).isEqualTo(expected);
        }

        @Test
        void updateAuthenticatedUser_shouldReturnUser_whenResponseIsOk() throws Exception {
            http.stub(
                    PATCH,
                    USER_PATH,
                    okJson(jsonBytes(GitHubUsersApiIntegrationTest.Auth.class, "private-user-octocat.json")));
            final GitHubUpdateAuthenticatedUserPayload update = nameUpdate();

            final GitHubAuthenticatedUser user;
            try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(http)) {
                user = users.api().user().updateAuthenticatedUser(update);
            }

            final GitHubAuthenticatedUser expected = CODEC.decode(
                    jsonBytes(GitHubUsersApiIntegrationTest.Auth.class, "private-user-octocat.json"),
                    GitHubAuthenticatedUser.class);

            assertThat(user).isEqualTo(expected);
            http.verifyHeader(PATCH, USER_PATH, CONTENT_TYPE, "application/json");
        }

        @Test
        void getAccount_shouldReturnUser_whenResponseIsOk() throws Exception {
            http.stub(
                    GET,
                    USER_BY_ID_PATH,
                    okJson(jsonBytes(GitHubUsersApiIntegrationTest.Auth.class, "public-user-octocat.json")));

            final Optional<GitHubUserView> user;
            try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(http)) {
                user = users.api().user().getAccount(1L);
            }

            final GitHubUserView expected = CODEC.decode(
                    jsonBytes(GitHubUsersApiIntegrationTest.Auth.class, "public-user-octocat.json"),
                    GitHubUserView.class);

            assertThat(user).contains(expected);
        }

        @Test
        void getAccount_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
            http.stub(GET, USER_BY_ID_PATH, notFound());

            final Optional<GitHubUserView> user;
            try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(http)) {
                user = users.api().user().getAccount(1L);
            }

            assertThat(user).isEmpty();
        }

        @Test
        void getByLogin_shouldReturnUser_whenResponseIsOk() throws Exception {
            http.stub(
                    GET,
                    USER_BY_LOGIN_PATH,
                    okJson(jsonBytes(GitHubUsersApiIntegrationTest.Auth.class, "public-user-octocat.json")));

            final Optional<GitHubUserView> user;
            try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(http)) {
                user = users.api().user().getByLogin("octocat");
            }

            final GitHubUserView expected = CODEC.decode(
                    jsonBytes(GitHubUsersApiIntegrationTest.Auth.class, "public-user-octocat.json"),
                    GitHubUserView.class);

            assertThat(user).contains(expected);
        }

        @Test
        void getByLogin_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
            http.stub(GET, USER_BY_LOGIN_PATH, notFound());

            final Optional<GitHubUserView> user;
            try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(http)) {
                user = users.api().user().getByLogin("octocat");
            }

            assertThat(user).isEmpty();
        }

        @Test
        void getUserContext_shouldReturnContext_whenResponseIsOk() throws Exception {
            http.stub(
                    GET, HOVERCARD_PATH, okJson(jsonBytes(GitHubUsersApiIntegrationTest.Auth.class, "hovercard.json")));

            final Optional<GitHubUserContext> context;
            try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(http)) {
                context = users.api().user().getUserContext("octocat");
            }

            final GitHubUserContext expected = CODEC.decode(
                    jsonBytes(GitHubUsersApiIntegrationTest.Auth.class, "hovercard.json"), GitHubUserContext.class);

            assertThat(context).contains(expected);
        }

        @Test
        void getUserContext_shouldReturnContext_whenSubjectQueryProvided() throws Exception {
            http.stub(
                    GET, HOVERCARD_PATH, okJson(jsonBytes(GitHubUsersApiIntegrationTest.Auth.class, "hovercard.json")));

            final Optional<GitHubUserContext> context;
            try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(http)) {
                context = users.api()
                        .user()
                        .getUserContext(
                                "octocat", new GitHubGetUserContextFilter(GitHubUserContextSubjectType.ISSUE, "1"));
            }

            final GitHubUserContext expected = CODEC.decode(
                    jsonBytes(GitHubUsersApiIntegrationTest.Auth.class, "hovercard.json"), GitHubUserContext.class);

            assertThat(context).contains(expected);
        }

        @Test
        void getUserContext_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
            http.stub(GET, HOVERCARD_PATH, notFound());

            final Optional<GitHubUserContext> context;
            try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(http)) {
                context = users.api().user().getUserContext("octocat");
            }

            assertThat(context).isEmpty();
        }

        @Test
        void getUserContext_shouldReturnEmpty_whenSubjectQueryNotFound() throws Exception {
            http.stub(GET, HOVERCARD_PATH, notFound());

            final Optional<GitHubUserContext> context;
            try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(http)) {
                context = users.api()
                        .user()
                        .getUserContext(
                                "octocat", new GitHubGetUserContextFilter(GitHubUserContextSubjectType.ISSUE, "1"));
            }

            assertThat(context).isEmpty();
        }

        private static GitHubUpdateAuthenticatedUserPayload nameUpdate() {
            return UsersCatalogFixtures.updateAuthenticatedUserPayload();
        }
    }

    @Nested
    class Pagination {
        private static final JsonType<List<GitHubSimpleUser>> USERS_TYPE = JsonTypes.listOf(GitHubSimpleUser.class);

        private static final String BLOCKS_PATH = "/user/blocks";
        private static final String USERS_PATH = "/users";
        private static final String USERNAME = "octocat";
        private static final String SUBJECT_DIGEST = "sha256:abc";
        private static final String LIST_ATTESTATIONS_PATH = "/users/octocat/attestations/sha256:abc";

        @RegisterExtension
        final StubHttpServer http = new StubHttpServer();

        @Test
        void listBlocked_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
            http.stub(
                    GET,
                    BLOCKS_PATH,
                    okJson(jsonBytes(GitHubUsersApiIntegrationTest.Pagination.class, "users-one.json"))
                            .header(LINK, linkNext(http.uri("/user/blocks?page=2&" + TestClients.perPageQuery()))),
                    okJson(jsonBytes(GitHubUsersApiIntegrationTest.Pagination.class, "users-two.json")));

            final GitHubPage<GitHubSimpleUser> first;
            final Optional<GitHubPage<GitHubSimpleUser>> second;
            final Optional<GitHubPage<GitHubSimpleUser>> third;
            try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(http)) {
                first = users.api().blocking().listBlocked();
                second = first.next();
                third = second.isPresent() ? second.get().next() : Optional.empty();
            }

            assertSoftly(softly -> {
                softly.assertThat(first.items()).containsExactlyElementsOf(expectedUsers("users-one.json"));
                softly.assertThat(second).isPresent();
                softly.assertThat(second.orElseThrow().items())
                        .containsExactlyElementsOf(expectedUsers("users-two.json"));
                softly.assertThat(third).isEmpty();
            });
            http.verifyRequested(GET, BLOCKS_PATH, 2);
        }

        @Test
        void list_shouldReturnEmpty_whenFullPageWithoutLink() throws Exception {
            http.stub(
                    GET,
                    USERS_PATH,
                    okJson(jsonBytes(GitHubUsersApiIntegrationTest.Pagination.class, "users-one.json")));

            final Optional<GitHubPage<GitHubSimpleUser>> second;
            try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(http)) {
                second = users.api().user().list(GitHubSinceRequest.first(1)).next();
            }

            assertThat(second).isEmpty();
            http.verifyRequested(GET, USERS_PATH, 1);
        }

        @Test
        void listAttestations_shouldFollowLinkNext_whenAfterCursorPresent() throws Exception {
            http.stub(
                    GET,
                    LIST_ATTESTATIONS_PATH,
                    okJson(jsonBytes(GitHubUsersApiIntegrationTest.Pagination.class, "list-attestations.json"))
                            .header(
                                    LINK,
                                    linkNext(http.uri(LIST_ATTESTATIONS_PATH + "?after=cursor-after-token&"
                                            + TestClients.perPageQuery()))),
                    okJson(jsonBytes(GitHubUsersApiIntegrationTest.Pagination.class, "list-attestations.json")));

            final GitHubPage<GitHubAttestation> first;
            final Optional<GitHubPage<GitHubAttestation>> second;
            final Optional<GitHubPage<GitHubAttestation>> third;
            try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(http)) {
                first = users.api().attestations().listAttestations(USERNAME, SUBJECT_DIGEST);
                second = first.next();
                third = second.isPresent() ? second.get().next() : Optional.empty();
            }

            assertSoftly(softly -> {
                softly.assertThat(first.items()).isEqualTo(expectedAttestations());
                softly.assertThat(second).isPresent();
                softly.assertThat(second.orElseThrow().items()).isEqualTo(expectedAttestations());
                softly.assertThat(third).isEmpty();
            });
            http.verifyRequested(GET, LIST_ATTESTATIONS_PATH, 2);
        }

        private static List<GitHubSimpleUser> expectedUsers(final String file) {
            return CODEC.decode(jsonBytes(GitHubUsersApiIntegrationTest.Pagination.class, file), USERS_TYPE);
        }

        private static List<GitHubAttestation> expectedAttestations() {
            return CODEC.decode(
                            jsonBytes(GitHubUsersApiIntegrationTest.Pagination.class, "list-attestations.json"),
                            GitHubAttestations.class)
                    .attestations();
        }
    }
}
