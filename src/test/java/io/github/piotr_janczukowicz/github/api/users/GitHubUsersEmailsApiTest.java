package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
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
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubUsersEmailsApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUsersEmailsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void setPrimaryEmailVisibility_shouldReturnEmails_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("emails-one.json")));

        final List<GitHubEmail> emails = new GitHubUsersEmailsApi(TestClients.caller(transport))
                .setPrimaryEmailVisibility(GitHubEmailVisibility.PUBLIC);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(emails).isEqualTo(UsersCatalogFixtures.emailsOne());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/email/visibility");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"visibility\":\"public\"");
        });
    }

    @Test
    void setPrimaryEmailVisibility_shouldAcceptPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("emails-one.json")));

        final List<GitHubEmail> emails = new GitHubUsersEmailsApi(TestClients.caller(transport))
                .setPrimaryEmailVisibility(new GitHubSetPrimaryEmailVisibilityPayload(GitHubEmailVisibility.PRIVATE));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(emails).isEqualTo(UsersCatalogFixtures.emailsOne());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/email/visibility");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"visibility\":\"private\"");
        });
    }

    @Test
    void setPrimaryEmailVisibility_shouldThrow_whenPayloadNull() {
        final GitHubUsersEmailsApi emailsApi = new GitHubUsersEmailsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(
                () -> emailsApi.setPrimaryEmailVisibility((GitHubSetPrimaryEmailVisibilityPayload) null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void listEmails_shouldReturnEmails_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("emails-one.json")));

        final GitHubPage<GitHubEmail> page = new GitHubUsersEmailsApi(TestClients.caller(transport)).listEmails();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactlyElementsOf(UsersCatalogFixtures.emailsOne());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/user/emails?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void addEmail_shouldReturnEmails_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("emails-one.json")));

        final List<GitHubEmail> emails =
                new GitHubUsersEmailsApi(TestClients.caller(transport)).addEmail("octocat@github.com");
        final HttpRequest request = captureSingleRequest();

        assertPostedEmails(emails, request);
    }

    @Test
    void addEmails_shouldAcceptPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("emails-one.json")));

        final List<GitHubEmail> emails = new GitHubUsersEmailsApi(TestClients.caller(transport))
                .addEmails(UsersCatalogFixtures.updateEmailsPayload());
        final HttpRequest request = captureSingleRequest();

        assertPostedEmails(emails, request);
    }

    @Test
    void deleteEmail_shouldSendDeleteWithBody_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubUsersEmailsApi(TestClients.caller(transport)).deleteEmail("octocat@github.com");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/emails");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"emails\":[\"octocat@github.com\"]");
        });
    }

    @Test
    void addEmails_shouldReturnEmails_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("emails-one.json")));

        final List<GitHubEmail> emails =
                new GitHubUsersEmailsApi(TestClients.caller(transport)).addEmails(List.of("octocat@github.com"));
        final HttpRequest request = captureSingleRequest();

        assertPostedEmails(emails, request);
    }

    @Test
    void deleteEmails_shouldSendDeleteWithBody_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubUsersEmailsApi(TestClients.caller(transport)).deleteEmails(List.of("octocat@github.com"));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/emails");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"emails\":[\"octocat@github.com\"]");
        });
    }

    @Test
    void listPublicEmails_shouldReturnEmails_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("emails-one.json")));

        final GitHubPage<GitHubEmail> page = new GitHubUsersEmailsApi(TestClients.caller(transport)).listPublicEmails();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactlyElementsOf(UsersCatalogFixtures.emailsOne());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/user/public_emails?page=1&" + TestClients.perPageQuery());
        });
    }

    @ParameterizedTest
    @MethodSource("unsupportedListEmailsPaginationRequests")
    void listEmails_shouldThrow_whenUnsupportedPagination(final UsersCall call, final String messageFragment) {
        final GitHubUsersEmailsApi usersApi = new GitHubUsersEmailsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(usersApi));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("unsupportedListPublicEmailsPageRequests")
    void listPublicEmails_shouldThrow_whenUnsupportedPagination(
            final GitHubPageRequest pageRequest, final String messageFragment) {
        final GitHubUsersEmailsApi usersApi = new GitHubUsersEmailsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.listPublicEmails(pageRequest));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private static Stream<Arguments> unsupportedListEmailsPaginationRequests() {
        return Stream.of(
                Arguments.of(
                        (UsersCall) api -> api.listEmails(GitHubPageRequest.byCursor()),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (UsersCall) api -> api.listEmails(GitHubPageRequest.bySince()),
                        "unsupported pagination request: since"));
    }

    private static Stream<Arguments> unsupportedListPublicEmailsPageRequests() {
        return Stream.of(
                Arguments.of(GitHubPageRequest.byCursor(), "unsupported pagination request: cursor"),
                Arguments.of(GitHubPageRequest.bySince(), "unsupported pagination request: since"));
    }

    @FunctionalInterface
    interface UsersCall {
        void execute(GitHubUsersEmailsApi api) throws Exception;
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubUsersEmailsApiTest.class, fileName);
    }

    private static void assertPostedEmails(final List<GitHubEmail> emails, final HttpRequest request) {
        assertSoftly(softly -> {
            softly.assertThat(emails).isEqualTo(UsersCatalogFixtures.emailsOne());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/emails");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .contains("\"emails\":[\"octocat@github.com\"]");
        });
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
