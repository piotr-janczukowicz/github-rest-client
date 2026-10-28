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
class GitHubUsersSshSigningKeysApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUsersSshSigningKeysApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listSshSigningKeys_shouldReturnKeys_whenAuthenticatedUser() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("ssh-signing-key.json") + "]"));

        final GitHubPage<GitHubSshSigningKey> page =
                new GitHubUsersSshSigningKeysApi(TestClients.caller(transport)).listSshSigningKeys();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(UsersCatalogFixtures.sshSigningKey());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/user/ssh_signing_keys?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void createSshSigningKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("ssh-signing-key.json")));
        final GitHubCreateSshSigningKeyPayload payload = UsersCatalogFixtures.createSshSigningKeyPayload();

        final GitHubSshSigningKey key =
                new GitHubUsersSshSigningKeysApi(TestClients.caller(transport)).createSshSigningKey(payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(key).isEqualTo(UsersCatalogFixtures.sshSigningKey());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/ssh_signing_keys");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"title\":\"title\"")
                    .contains("\"key\":\"ssh-ed25519 AAA\"");
        });
    }

    @Test
    void getSshSigningKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("ssh-signing-key.json")));

        final Optional<GitHubSshSigningKey> key =
                new GitHubUsersSshSigningKeysApi(TestClients.caller(transport)).getSshSigningKey(1L);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(key).contains(UsersCatalogFixtures.sshSigningKey());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/ssh_signing_keys/1");
        });
    }

    @Test
    void getSshSigningKey_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubSshSigningKey> key =
                new GitHubUsersSshSigningKeysApi(TestClients.caller(transport)).getSshSigningKey(1L);

        assertThat(key).isEmpty();
    }

    @Test
    void deleteSshSigningKey_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubUsersSshSigningKeysApi(TestClients.caller(transport)).deleteSshSigningKey(1L);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/ssh_signing_keys/1");
        });
    }

    @Test
    void listSshSigningKeys_shouldReturnKeys_whenUsernameProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("ssh-signing-key.json") + "]"));

        final GitHubPage<GitHubSshSigningKey> page =
                new GitHubUsersSshSigningKeysApi(TestClients.caller(transport)).listSshSigningKeys("octocat");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(UsersCatalogFixtures.sshSigningKey());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/ssh_signing_keys?page=1&"
                            + TestClients.perPageQuery());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
        });
        verify(transport, times(1)).execute(any());
    }

    @ParameterizedTest
    @MethodSource("unsupportedPaginationRequests")
    void listSshSigningKeys_shouldThrow_whenUnsupportedPagination(final UsersCall call, final String messageFragment) {
        final GitHubUsersSshSigningKeysApi usersApi = new GitHubUsersSshSigningKeysApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(usersApi));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubPageRequest unsupportedForPageLists = GitHubPageRequest.bySince();
        final GitHubPageRequest unsupportedCursorForPageLists = GitHubPageRequest.byCursor();
        return Stream.of(
                Arguments.of(
                        (UsersCall) api -> api.listSshSigningKeys(unsupportedCursorForPageLists),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (UsersCall) api -> api.listSshSigningKeys(unsupportedForPageLists),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (UsersCall) api -> api.listSshSigningKeys("octocat", unsupportedCursorForPageLists),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (UsersCall) api -> api.listSshSigningKeys("octocat", unsupportedForPageLists),
                        "unsupported pagination request: since"));
    }

    @FunctionalInterface
    interface UsersCall {
        void execute(GitHubUsersSshSigningKeysApi api) throws Exception;
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubUsersSshSigningKeysApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
