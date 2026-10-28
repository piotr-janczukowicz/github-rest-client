package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
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
class GitHubUsersBlockingApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUsersBlockingApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listBlocked_shouldOmitPageQuery_whenNoneRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("users-one.json")));

        new GitHubUsersBlockingApi(TestClients.caller(transport)).listBlocked(GitHubPageRequest.none());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/blocks");
        });
    }

    @Test
    void listBlocked_shouldReturnUsers_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("users-one.json")));

        final GitHubPage<GitHubSimpleUser> page =
                new GitHubUsersBlockingApi(TestClients.caller(transport)).listBlocked();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.usersOne());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/user/blocks?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void isBlocked_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final boolean blocked = new GitHubUsersBlockingApi(TestClients.caller(transport)).isBlocked("octocat");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(blocked).isTrue();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/blocks/octocat");
        });
    }

    @Test
    void isBlocked_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final boolean blocked = new GitHubUsersBlockingApi(TestClients.caller(transport)).isBlocked("missing");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(blocked).isFalse();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/blocks/missing");
        });
    }

    @Test
    void isBlocked_shouldThrow_whenUnsuccessful() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.UNAUTHORIZED.code(), json("bad-credentials.json")));
        final GitHubUsersBlockingApi usersApi = new GitHubUsersBlockingApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.isBlocked("octocat"));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void block_shouldSendPutWithAbsentBody_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubUsersBlockingApi(TestClients.caller(transport)).block("octocat");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/blocks/octocat");
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void unblock_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubUsersBlockingApi(TestClients.caller(transport)).unblock("octocat");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/blocks/octocat");
        });
    }

    @Test
    void isBlocked_shouldReturnTrue_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), null));

        final boolean blocked = new GitHubUsersBlockingApi(TestClients.caller(transport)).isBlocked("octocat");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(blocked).isTrue();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/blocks/octocat");
        });
    }

    @Test
    void block_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));
        final GitHubUsersBlockingApi usersApi = new GitHubUsersBlockingApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.block("missing"));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @ParameterizedTest
    @MethodSource("unsupportedPaginationRequests")
    void listBlocked_shouldThrow_whenUnsupportedPagination(final UsersCall call, final String messageFragment) {
        final GitHubUsersBlockingApi usersApi = new GitHubUsersBlockingApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(usersApi));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubPageRequest unsupportedForPageLists = GitHubPageRequest.bySince();
        final GitHubPageRequest unsupportedCursorForPageLists = GitHubPageRequest.byCursor();
        return Stream.of(
                Arguments.of(
                        (UsersCall) api -> api.listBlocked(unsupportedCursorForPageLists),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (UsersCall) api -> api.listBlocked(unsupportedForPageLists),
                        "unsupported pagination request: since"));
    }

    @FunctionalInterface
    interface UsersCall {
        void execute(GitHubUsersBlockingApi api) throws Exception;
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubUsersBlockingApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
