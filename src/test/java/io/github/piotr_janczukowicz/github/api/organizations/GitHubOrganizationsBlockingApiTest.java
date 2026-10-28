package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
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
class GitHubOrganizationsBlockingApiTest {

    private static final String ORG = "github";
    private static final String USERNAME = "octocat";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsBlockingApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listBlocked_shouldOmitPageQuery_whenNoneRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("users-one.json")));

        callApi(api -> api.listBlocked(ORG, GitHubPageRequest.none()));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/blocks");
    }

    @Test
    void listBlocked_shouldReturnUsers_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("users-one.json")));

        final GitHubPage<GitHubSimpleUser> page = callApi(api -> api.listBlocked(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.usersOne());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/blocks?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listBlocked_shouldThrow_whenOrgBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsBlockingApi api = new GitHubOrganizationsBlockingApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.listBlocked(" "));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
        }
    }

    @Test
    void isBlocked_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final boolean blocked = callApi(api -> api.isBlocked(ORG, USERNAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(blocked).isTrue();
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/blocks/octocat");
        });
    }

    @Test
    void isBlocked_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final boolean blocked = callApi(api -> api.isBlocked(ORG, "missing"));

        assertThat(blocked).isFalse();
    }

    @Test
    void isBlocked_shouldReturnTrue_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), null));

        final boolean blocked = callApi(api -> api.isBlocked(ORG, USERNAME));

        assertThat(blocked).isTrue();
    }

    @Test
    void isBlocked_shouldThrow_whenUnsuccessful() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.UNAUTHORIZED.code(), json("bad-credentials.json")));
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsBlockingApi api = new GitHubOrganizationsBlockingApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.isBlocked(ORG, USERNAME));

            assertThat(thrown).isInstanceOf(GitHubApiException.class);
        }
    }

    @Test
    void block_shouldSendPutWithAbsentBody_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.block(ORG, USERNAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/blocks/octocat");
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void unblock_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.unblock(ORG, USERNAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/blocks/octocat");
        });
    }

    @Test
    void block_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsBlockingApi api = new GitHubOrganizationsBlockingApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.block(ORG, "missing"));

            assertThat(thrown).isInstanceOf(GitHubApiException.class);
        }
    }

    @ParameterizedTest
    @MethodSource("unsupportedPaginationRequests")
    void listEndpoint_shouldThrow_whenUnsupportedPagination(final BlockingApiCall call, final String messageFragment) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @FunctionalInterface
    interface BlockingApiCall {
        void execute(GitHubOrganizationsBlockingApi api) throws Exception;
    }

    @FunctionalInterface
    private interface BlockingApiAction<T> {
        T apply(GitHubOrganizationsBlockingApi api) throws Exception;
    }

    private void callApiVoid(final BlockingApiCall action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            action.execute(new GitHubOrganizationsBlockingApi(TestClients.caller(client)));
        }
    }

    private <T> T callApi(final BlockingApiAction<T> action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationsBlockingApi(TestClients.caller(client)));
        }
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubPageRequest unsupportedForPageLists = GitHubPageRequest.bySince();
        final GitHubPageRequest unsupportedCursorForPageLists = GitHubPageRequest.byCursor();
        return Stream.of(
                Arguments.of(
                        (BlockingApiCall) api -> api.listBlocked(ORG, unsupportedCursorForPageLists),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (BlockingApiCall) api -> api.listBlocked(ORG, unsupportedForPageLists),
                        "unsupported pagination request: since"));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubSimpleUser.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
