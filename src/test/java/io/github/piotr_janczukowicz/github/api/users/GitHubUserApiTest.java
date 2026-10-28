package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.github.GitHubHeaders.API_VERSION;
import static io.github.piotr_janczukowicz.github.GitHubHeaders.RATE_LIMIT_REMAINING;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.exception.GitHubForbiddenException;
import io.github.piotr_janczukowicz.github.client.exception.GitHubRateLimitException;
import io.github.piotr_janczukowicz.github.client.exception.GitHubValidationException;
import io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubSinceRequest;
import io.github.piotr_janczukowicz.http.HttpBody;
import io.github.piotr_janczukowicz.http.HttpHeaders;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpResponse;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpResponses;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubUserApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUserApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @ParameterizedTest
    @MethodSource("pagedLists")
    void list_shouldReturnUsers_whenRequestProvided(
            final GitHubPageRequest request, final String file, final GitHubSimpleUser expected, final String uri)
            throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json(file)));

        final GitHubPage<GitHubSimpleUser> page;
        try (final GitHubClient client = TestClients.client(transport)) {
            page = new GitHubUserApi(TestClients.caller(client)).list(request);
        }
        final HttpRequest transportRequest = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(expected);
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(transportRequest.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(transportRequest.uri()).hasToString(uri);
        });
    }

    @ParameterizedTest
    @MethodSource("omittedPaginationLists")
    void list_shouldOmitPaginationQuery_whenNoPaginationToken(final UsersListCall call) throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("users-one.json")));

        final GitHubPage<GitHubSimpleUser> page;
        try (final GitHubClient client = TestClients.client(transport)) {
            page = call.list(new GitHubUserApi(TestClients.caller(client)));
        }
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.usersOne());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/users");
        });
    }

    @Test
    void list_shouldFollowLinkHeader_whenNoRequestAndNextPresent() throws Exception {
        final HttpResponse first = HttpResponses.of(
                HttpStatus.OK.code(),
                Map.of(LINK, List.of("<https://api.github.com/users?since=1>; rel=\"next\"")),
                HttpBody.of(jsonBytes("users-one.json")));
        final HttpResponse second =
                new HttpResponse(HttpStatus.OK.code(), HttpHeaders.empty(), HttpBody.of(jsonBytes("users-two.json")));
        when(transport.execute(any())).thenReturn(first, second);

        final Optional<GitHubPage<GitHubSimpleUser>> next;
        try (final GitHubClient client = TestClients.client(transport)) {
            next = new GitHubUserApi(TestClients.caller(client)).list().next();
        }
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport, atLeastOnce()).execute(captor.capture());

        assertSoftly(softly -> {
            softly.assertThat(next).isPresent();
            softly.assertThat(next.orElseThrow().items())
                    .singleElement()
                    .extracting(GitHubSimpleUser::login)
                    .isEqualTo("two");
            softly.assertThat(captor.getAllValues().getLast().uri())
                    .hasToString("https://api.github.com/users?since=1");
        });
    }

    @Test
    void list_shouldThrow_whenRequestNull() {
        final GitHubUserApi usersApi = new GitHubUserApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.list(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @ParameterizedTest
    @MethodSource("unsuccessfulResponses")
    void list_shouldThrowMappedException_whenUnsuccessful(
            final int statusCode, final String file, final Class<? extends Throwable> type) throws Exception {
        when(transport.execute(any())).thenReturn(response(statusCode, json(file)));
        final GitHubUserApi usersApi = new GitHubUserApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(usersApi::list);

        assertThat(thrown).isInstanceOf(type);
    }

    @Test
    void list_shouldSendVersionHeader_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("empty-array.json")));

        new GitHubUserApi(TestClients.caller(transport)).list(GitHubPageRequest.bySince());

        assertThat(captureSingleRequest().headers().get(API_VERSION)).contains(GitHubClient.API_VERSION);
    }

    @Test
    void list_shouldSendVersionHeader_whenFollowingNextLink() throws Exception {
        final HttpResponse first = HttpResponses.of(
                HttpStatus.OK.code(),
                Map.of(LINK, List.of("<https://api.github.com/users?since=1>; rel=\"next\"")),
                HttpBody.of(jsonBytes("users-one.json")));
        final HttpResponse second =
                new HttpResponse(HttpStatus.OK.code(), HttpHeaders.empty(), HttpBody.of(jsonBytes("users-two.json")));
        when(transport.execute(any())).thenReturn(first, second);

        try (final GitHubClient client = TestClients.client(transport)) {
            new GitHubUserApi(TestClients.caller(client)).list().next();
        }

        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport, atLeastOnce()).execute(captor.capture());

        assertSoftly(softly -> {
            softly.assertThat(captor.getAllValues()).hasSize(2);
            softly.assertThat(captor.getAllValues().get(1).headers().get(API_VERSION))
                    .contains(GitHubClient.API_VERSION);
        });
    }

    @Test
    void getByLogin_shouldReturnUser_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("public-user-octocat.json")));

        final Optional<GitHubUserView> user = new GitHubUserApi(TestClients.caller(transport)).getByLogin("octocat");
        final HttpRequest request = captureSingleRequest();
        final GitHubUserView expected = UsersCatalogFixtures.publicUserOctocat();

        assertSoftly(softly -> {
            softly.assertThat(user).contains(expected);
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/users/octocat");
        });
    }

    @Test
    void getByLogin_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubUserView> user = new GitHubUserApi(TestClients.caller(transport)).getByLogin("missing");

        assertThat(user).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("unsuccessfulResponses")
    void getByLogin_shouldThrowMappedException_whenUnsuccessful(
            final int statusCode, final String file, final Class<? extends Throwable> type) throws Exception {
        when(transport.execute(any())).thenReturn(response(statusCode, json(file)));
        final GitHubUserApi usersApi = new GitHubUserApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.getByLogin("octocat"));

        assertThat(thrown).isInstanceOf(type);
    }

    @Test
    void getByLogin_shouldThrowRateLimitException_whenForbiddenWithRateLimitHeaders() throws Exception {
        final HttpResponse gitHubResponse = HttpResponses.of(
                HttpStatus.FORBIDDEN.code(),
                Map.of(RATE_LIMIT_REMAINING, List.of("0")),
                HttpBody.of(jsonBytes("rate-limit.json")));
        when(transport.execute(any())).thenReturn(gitHubResponse);
        final GitHubUserApi usersApi = new GitHubUserApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.getByLogin("octocat"));

        assertThat(thrown).isInstanceOf(GitHubRateLimitException.class).isNotInstanceOf(GitHubForbiddenException.class);
    }

    @Test
    void getByLogin_shouldThrow_whenLoginIsBlank() {
        final GitHubUserApi usersApi = new GitHubUserApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.getByLogin(" "));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("{username}");
    }

    @Test
    void getAuthenticatedUser_shouldReturnUser_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("private-user-octocat.json")));

        final GitHubUserView user = new GitHubUserApi(TestClients.caller(transport)).getAuthenticatedUser();
        final HttpRequest request = captureSingleRequest();
        final GitHubUserView expected = UsersCatalogFixtures.privateUserOctocat();

        assertSoftly(softly -> {
            softly.assertThat(user).isEqualTo(expected);
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user");
        });
    }

    @Test
    void getAuthenticatedUser_shouldThrowNotFoundException_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));
        final GitHubUserApi usersApi = new GitHubUserApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(usersApi::getAuthenticatedUser);

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @ParameterizedTest
    @MethodSource("unsuccessfulResponses")
    void getAuthenticatedUser_shouldThrowMappedException_whenUnsuccessful(
            final int statusCode, final String file, final Class<? extends Throwable> type) throws Exception {
        when(transport.execute(any())).thenReturn(response(statusCode, json(file)));
        final GitHubUserApi usersApi = new GitHubUserApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(usersApi::getAuthenticatedUser);

        assertThat(thrown).isInstanceOf(type);
    }

    @Test
    void updateAuthenticatedUser_shouldReturnUser_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("private-user-octocat.json")));
        final GitHubUpdateAuthenticatedUserPayload update = nameUpdate();

        final GitHubAuthenticatedUser user =
                new GitHubUserApi(TestClients.caller(transport)).updateAuthenticatedUser(update);
        final HttpRequest request = captureSingleRequest();
        final GitHubAuthenticatedUser expected = UsersCatalogFixtures.privateUserOctocat();

        assertSoftly(softly -> {
            softly.assertThat(user).isEqualTo(expected);
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo("{\"name\":\"mona\"}");
        });
    }

    @Test
    void updateAuthenticatedUser_shouldThrowNotFoundException_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));
        final GitHubUserApi usersApi = new GitHubUserApi(TestClients.caller(transport));
        final GitHubUpdateAuthenticatedUserPayload update = nameUpdate();

        final Throwable thrown = catchThrowable(() -> usersApi.updateAuthenticatedUser(update));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @ParameterizedTest
    @MethodSource("unsuccessfulResponses")
    void updateAuthenticatedUser_shouldThrowMappedException_whenUnsuccessful(
            final int statusCode, final String file, final Class<? extends Throwable> type) throws Exception {
        when(transport.execute(any())).thenReturn(response(statusCode, json(file)));
        final GitHubUserApi usersApi = new GitHubUserApi(TestClients.caller(transport));
        final GitHubUpdateAuthenticatedUserPayload update = nameUpdate();

        final Throwable thrown = catchThrowable(() -> usersApi.updateAuthenticatedUser(update));

        assertThat(thrown).isInstanceOf(type);
    }

    @Test
    void updateAuthenticatedUser_shouldThrow_whenUpdateIsNull() {
        final GitHubUserApi usersApi = new GitHubUserApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.updateAuthenticatedUser(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getAccount_shouldReturnUser_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("public-user-octocat.json")));

        final Optional<GitHubUserView> user = new GitHubUserApi(TestClients.caller(transport)).getAccount(1L);
        final HttpRequest request = captureSingleRequest();
        final GitHubUserView expected = UsersCatalogFixtures.publicUserOctocat();

        assertSoftly(softly -> {
            softly.assertThat(user).contains(expected);
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/1");
        });
    }

    @Test
    void getAccount_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubUserView> user = new GitHubUserApi(TestClients.caller(transport)).getAccount(1L);

        assertThat(user).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("unsuccessfulResponses")
    void getAccount_shouldThrowMappedException_whenUnsuccessful(
            final int statusCode, final String file, final Class<? extends Throwable> type) throws Exception {
        when(transport.execute(any())).thenReturn(response(statusCode, json(file)));
        final GitHubUserApi usersApi = new GitHubUserApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.getAccount(1L));

        assertThat(thrown).isInstanceOf(type);
    }

    @Test
    void getUserContext_shouldReturnContext_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("hovercard.json")));

        final Optional<GitHubUserContext> context =
                new GitHubUserApi(TestClients.caller(transport)).getUserContext("octocat");
        final HttpRequest request = captureSingleRequest();
        final GitHubUserContext expected =
                new GitHubUserContext(List.of(new GitHubUserContextItem("Owns this repository", "repo")));

        assertSoftly(softly -> {
            softly.assertThat(context).contains(expected);
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/users/octocat/hovercard");
        });
    }

    @Test
    void getUserContext_shouldSendSubjectQuery_whenTypeAndIdProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("hovercard.json")));

        new GitHubUserApi(TestClients.caller(transport))
                .getUserContext(
                        "octocat", new GitHubGetUserContextFilter(GitHubUserContextSubjectType.REPOSITORY, "123"));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasParameter("subject_type", "repository");
            softly.assertThat(request.uri()).hasParameter("subject_id", "123");
        });
    }

    @Test
    void getUserContext_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubUserContext> context =
                new GitHubUserApi(TestClients.caller(transport)).getUserContext("missing");

        assertThat(context).isEmpty();
    }

    @Test
    void getUserContext_shouldReturnEmpty_whenSubjectQueryNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubUserContext> context = new GitHubUserApi(TestClients.caller(transport))
                .getUserContext(
                        "missing", new GitHubGetUserContextFilter(GitHubUserContextSubjectType.ORGANIZATION, "1"));

        assertThat(context).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("unsuccessfulResponses")
    void getUserContext_shouldThrowMappedException_whenUnsuccessful(
            final int statusCode, final String file, final Class<? extends Throwable> type) throws Exception {
        when(transport.execute(any())).thenReturn(response(statusCode, json(file)));
        final GitHubUserApi usersApi = new GitHubUserApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.getUserContext("octocat"));

        assertThat(thrown).isInstanceOf(type);
    }

    @Test
    void getUserContext_shouldThrow_whenUsernameIsBlank() {
        final GitHubUserApi usersApi = new GitHubUserApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.getUserContext(" "));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("username");
    }

    @Test
    void getUserContext_shouldThrow_whenUsernameIsBlankOnFilterOverload() {
        final GitHubUserApi usersApi = new GitHubUserApi(TestClients.caller(transport));
        final GitHubGetUserContextFilter filter =
                new GitHubGetUserContextFilter(GitHubUserContextSubjectType.REPOSITORY, "1");

        final Throwable thrown = catchThrowable(() -> usersApi.getUserContext(" ", filter));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("username");
    }

    @Test
    void getAuthenticatedUser_shouldSendVersionHeader_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("private-user-octocat.json")));

        new GitHubUserApi(TestClients.caller(transport)).getAuthenticatedUser();

        assertThat(captureSingleRequest().headers().get(API_VERSION)).contains(GitHubClient.API_VERSION);
    }

    @Test
    void updateAuthenticatedUser_shouldSendVersionHeader_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("private-user-octocat.json")));

        new GitHubUserApi(TestClients.caller(transport)).updateAuthenticatedUser(nameUpdate());

        assertThat(captureSingleRequest().headers().get(API_VERSION)).contains(GitHubClient.API_VERSION);
    }

    @Test
    void getAccount_shouldSendVersionHeader_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("public-user-octocat.json")));

        new GitHubUserApi(TestClients.caller(transport)).getAccount(1L);

        assertThat(captureSingleRequest().headers().get(API_VERSION)).contains(GitHubClient.API_VERSION);
    }

    @Test
    void getByLogin_shouldSendVersionHeader_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("public-user-octocat.json")));

        new GitHubUserApi(TestClients.caller(transport)).getByLogin("octocat");

        assertThat(captureSingleRequest().headers().get(API_VERSION)).contains(GitHubClient.API_VERSION);
    }

    @ParameterizedTest
    @MethodSource("getUserContextVersionCalls")
    void getUserContext_shouldSendVersionHeader_whenCalled(final UsersCall call) throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("hovercard.json")));

        call.execute(new GitHubUserApi(TestClients.caller(transport)));

        assertThat(captureSingleRequest().headers().get(API_VERSION)).contains(GitHubClient.API_VERSION);
    }

    @ParameterizedTest
    @MethodSource("unsupportedPaginationRequests")
    void list_shouldThrow_whenUnsupportedPagination(final UsersCall call, final String messageFragment) {
        final GitHubUserApi usersApi = new GitHubUserApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(usersApi));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        return Stream.of(
                Arguments.of(
                        (UsersCall) api -> api.list(GitHubPageNumberRequest.of(1, 10)),
                        "unsupported pagination request: page"),
                Arguments.of(
                        (UsersCall) api -> api.list(GitHubCursorRequest.after("tok")),
                        "unsupported pagination request: cursor"));
    }

    private static Stream<Arguments> pagedLists() {
        return Stream.of(
                Arguments.of(
                        GitHubSinceRequest.of(100L, 2),
                        "users-one.json",
                        OrganizationsCatalogFixtures.usersOne(),
                        "https://api.github.com/users?since=100&per_page=2"),
                Arguments.of(
                        GitHubPageRequest.bySince(),
                        "users-two.json",
                        UsersCatalogFixtures.usersTwo(),
                        "https://api.github.com/users?" + TestClients.perPageQuery()));
    }

    private static Stream<Arguments> unsuccessfulResponses() {
        return Stream.of(
                Arguments.of(HttpStatus.UNAUTHORIZED.code(), "bad-credentials.json", GitHubApiException.class),
                Arguments.of(HttpStatus.FORBIDDEN.code(), "forbidden.json", GitHubForbiddenException.class),
                Arguments.of(
                        HttpStatus.UNPROCESSABLE_ENTITY.code(),
                        "validation-failed-login.json",
                        GitHubValidationException.class),
                Arguments.of(HttpStatus.INTERNAL_SERVER_ERROR.code(), "server-error.json", GitHubApiException.class));
    }

    private static Stream<UsersCall> getUserContextVersionCalls() {
        return Stream.of(
                api -> api.getUserContext("octocat"),
                api -> api.getUserContext(
                        "octocat", new GitHubGetUserContextFilter(GitHubUserContextSubjectType.ISSUE, "1")));
    }

    private static Stream<UsersListCall> omittedPaginationLists() {
        return Stream.of(GitHubUserApi::list, api -> api.list(GitHubPageRequest.none()));
    }

    private static GitHubUpdateAuthenticatedUserPayload nameUpdate() {
        return UsersCatalogFixtures.updateAuthenticatedUserPayload();
    }

    @FunctionalInterface
    interface UsersListCall {
        GitHubPage<GitHubSimpleUser> list(GitHubUserApi api) throws Exception;
    }

    @FunctionalInterface
    interface UsersCall {
        void execute(GitHubUserApi api) throws Exception;
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubUserApiTest.class, fileName);
    }

    private static byte[] jsonBytes(final String fileName) {
        return FileUtils.readBytes(GitHubUserApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
