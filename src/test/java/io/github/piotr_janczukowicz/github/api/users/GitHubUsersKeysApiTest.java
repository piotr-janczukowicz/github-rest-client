package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubUsersKeysApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUsersKeysApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listPublicKeys_shouldReturnKeys_whenAuthenticatedUser() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("key.json") + "]"));

        final GitHubPage<GitHubKey> page = new GitHubUsersKeysApi(TestClients.caller(transport)).listPublicKeys();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(UsersCatalogFixtures.key());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/user/keys?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void createPublicSshKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("key.json")));
        final GitHubCreatePublicSshKeyPayload payload = UsersCatalogFixtures.createPublicSshKeyPayload();

        final GitHubKey key = new GitHubUsersKeysApi(TestClients.caller(transport)).createPublicSshKey(payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(key).isEqualTo(UsersCatalogFixtures.key());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/keys");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"title\":\"title\"")
                    .contains("\"key\":\"ssh-rsa AAA\"");
        });
    }

    @Test
    void getPublicKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("key.json")));

        final Optional<GitHubKey> key = new GitHubUsersKeysApi(TestClients.caller(transport)).getPublicKey(1L);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(key).contains(UsersCatalogFixtures.key());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/keys/1");
        });
    }

    @Test
    void deletePublicKey_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubUsersKeysApi(TestClients.caller(transport)).deletePublicKey(1L);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/keys/1");
        });
    }

    @Test
    void listPublicKeys_shouldReturnSimpleKeys_whenUsernameProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("key-simple.json") + "]"));

        final GitHubPage<GitHubKeySimple> page =
                new GitHubUsersKeysApi(TestClients.caller(transport)).listPublicKeys("octocat");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(UsersCatalogFixtures.keySimple());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/keys?page=1&" + TestClients.perPageQuery());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
        });
        verify(transport, times(1)).execute(any());
    }

    @ParameterizedTest
    @MethodSource("unsupportedPaginationRequests")
    void listPublicKeys_shouldThrow_whenUnsupportedPagination(final UsersCall call, final String messageFragment) {
        final GitHubUsersKeysApi usersApi = new GitHubUsersKeysApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(usersApi));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubPageRequest unsupportedForPageLists = GitHubPageRequest.bySince();
        final GitHubPageRequest unsupportedCursorForPageLists = GitHubPageRequest.byCursor();
        return Stream.of(
                Arguments.of(
                        (UsersCall) api -> api.listPublicKeys(unsupportedCursorForPageLists),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (UsersCall) api -> api.listPublicKeys(unsupportedForPageLists),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (UsersCall) api -> api.listPublicKeys("octocat", unsupportedCursorForPageLists),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (UsersCall) api -> api.listPublicKeys("octocat", unsupportedForPageLists),
                        "unsupported pagination request: since"));
    }

    @FunctionalInterface
    interface UsersCall {
        void execute(GitHubUsersKeysApi api) throws Exception;
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubUsersKeysApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
