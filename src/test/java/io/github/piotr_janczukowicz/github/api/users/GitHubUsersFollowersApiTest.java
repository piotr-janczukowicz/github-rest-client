package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
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
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubUsersFollowersApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUsersFollowersApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listFollowers_shouldReturnUsers_whenAuthenticatedUser() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("users-one.json")));

        final GitHubPage<GitHubSimpleUser> page =
                new GitHubUsersFollowersApi(TestClients.caller(transport)).listFollowers();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.usersOne());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/user/followers?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listFollowing_shouldReturnUsers_whenAuthenticatedUser() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("users-one.json")));

        final GitHubPage<GitHubSimpleUser> page =
                new GitHubUsersFollowersApi(TestClients.caller(transport)).listFollowing();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.usersOne());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/user/following?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void isFollowing_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final boolean following = new GitHubUsersFollowersApi(TestClients.caller(transport)).isFollowing("octocat");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(following).isTrue();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/following/octocat");
        });
    }

    @Test
    void isFollowing_shouldReturnFalse_whenUserPairNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final boolean following =
                new GitHubUsersFollowersApi(TestClients.caller(transport)).isFollowing("octocat", "hubot");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(following).isFalse();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/users/octocat/following/hubot");
        });
    }

    @Test
    void follow_shouldSendPutWithAbsentBody_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubUsersFollowersApi(TestClients.caller(transport)).follow("octocat");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/following/octocat");
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void unfollow_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubUsersFollowersApi(TestClients.caller(transport)).unfollow("octocat");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/following/octocat");
        });
    }

    @Test
    void listFollowers_shouldThrowNotFound_whenUsernameMissing() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));
        final GitHubUsersFollowersApi usersApi = new GitHubUsersFollowersApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.listFollowers("missing"));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void listFollowers_shouldReturnUsers_whenUsernameProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("users-one.json")));

        final GitHubPage<GitHubSimpleUser> page =
                new GitHubUsersFollowersApi(TestClients.caller(transport)).listFollowers("octocat");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.usersOne());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/followers?page=1&" + TestClients.perPageQuery());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
        });
        verify(transport, times(1)).execute(any());
    }

    @Test
    void listFollowing_shouldReturnUsers_whenUsernameProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("users-one.json")));

        final GitHubPage<GitHubSimpleUser> page =
                new GitHubUsersFollowersApi(TestClients.caller(transport)).listFollowing("octocat");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.usersOne());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/following?page=1&" + TestClients.perPageQuery());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
        });
        verify(transport, times(1)).execute(any());
    }

    @Test
    void isFollowing_shouldThrow_whenAuthenticatedCheckUnsuccessful() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.UNAUTHORIZED.code(), json("bad-credentials.json")));
        final GitHubUsersFollowersApi usersApi = new GitHubUsersFollowersApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.isFollowing("octocat"));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void isFollowing_shouldReturnTrue_whenAuthenticatedCheckReturnsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), null));

        final boolean following = new GitHubUsersFollowersApi(TestClients.caller(transport)).isFollowing("octocat");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(following).isTrue();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/following/octocat");
        });
    }

    @Test
    void isFollowing_shouldThrow_whenUserPairCheckUnsuccessful() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.UNAUTHORIZED.code(), json("bad-credentials.json")));
        final GitHubUsersFollowersApi usersApi = new GitHubUsersFollowersApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.isFollowing("octocat", "hubot"));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @ParameterizedTest
    @MethodSource("unsupportedListFollowersPaginationRequests")
    void listFollowers_shouldThrow_whenUnsupportedPagination(final UsersCall call, final String messageFragment) {
        final GitHubUsersFollowersApi usersApi = new GitHubUsersFollowersApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(usersApi));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("unsupportedListFollowingPageRequests")
    void listFollowing_shouldThrow_whenUnsupportedPagination(
            final GitHubPageRequest pageRequest, final String messageFragment) {
        final GitHubUsersFollowersApi usersApi = new GitHubUsersFollowersApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.listFollowing(pageRequest));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("unsupportedListFollowingPageRequests")
    void listFollowing_shouldThrow_whenUnsupportedPaginationForUsername(
            final GitHubPageRequest pageRequest, final String messageFragment) {
        final GitHubUsersFollowersApi usersApi = new GitHubUsersFollowersApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.listFollowing("octocat", pageRequest));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private static Stream<Arguments> unsupportedListFollowersPaginationRequests() {
        return Stream.of(
                Arguments.of(
                        (UsersCall) api -> api.listFollowers(GitHubPageRequest.byCursor()),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (UsersCall) api -> api.listFollowers(GitHubPageRequest.bySince()),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (UsersCall) api -> api.listFollowers("octocat", GitHubPageRequest.byCursor()),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (UsersCall) api -> api.listFollowers("octocat", GitHubPageRequest.bySince()),
                        "unsupported pagination request: since"));
    }

    private static Stream<Arguments> unsupportedListFollowingPageRequests() {
        return Stream.of(
                Arguments.of(GitHubPageRequest.byCursor(), "unsupported pagination request: cursor"),
                Arguments.of(GitHubPageRequest.bySince(), "unsupported pagination request: since"));
    }

    @FunctionalInterface
    interface UsersCall {
        void execute(GitHubUsersFollowersApi api) throws Exception;
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubUsersFollowersApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
