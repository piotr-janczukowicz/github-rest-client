package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest;
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
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubUsersAttestationsApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUsersAttestationsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listAttestations_shouldReturnAttestations_whenCursorRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("list-attestations.json")));

        final GitHubPage<GitHubAttestation> page = new GitHubUsersAttestationsApi(TestClients.caller(transport))
                .listAttestations("octocat", "sha256:abc", GitHubCursorRequest.after("tok"));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            UsersCatalogFixtures.attestations().attestations());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/attestations/sha256:abc?after=tok&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listAttestations_shouldReturnAttestations_whenNoneRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("list-attestations.json")));

        final GitHubPage<GitHubAttestation> page = new GitHubUsersAttestationsApi(TestClients.caller(transport))
                .listAttestations("octocat", "sha256:abc", GitHubPageRequest.none());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            UsersCatalogFixtures.attestations().attestations());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/attestations/sha256:abc");
        });
    }

    @Test
    void listAttestations_shouldReturnAttestations_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("list-attestations.json")));

        final GitHubPage<GitHubAttestation> page =
                new GitHubUsersAttestationsApi(TestClients.caller(transport)).listAttestations("octocat", "sha256:abc");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            UsersCatalogFixtures.attestations().attestations());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/attestations/sha256:abc?"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listAttestations_shouldReturnEmptyPage_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final GitHubPage<GitHubAttestation> page =
                new GitHubUsersAttestationsApi(TestClients.caller(transport)).listAttestations("octocat", "sha256:abc");

        final Optional<GitHubPage<GitHubAttestation>> next = page.next();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).isEmpty();
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(next).isEmpty();
        });
    }

    @Test
    void listAttestations_shouldPercentEncodeSlashInUsername_whenUsernameContainsSlash() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("list-attestations.json")));

        new GitHubUsersAttestationsApi(TestClients.caller(transport)).listAttestations("a/b", "sha256:abc");

        assertThat(captureSingleRequest().uri().getRawPath()).isEqualTo("/users/a%2Fb/attestations/sha256:abc");
    }

    @Test
    void listAttestations_shouldSendPredicateType_whenProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("list-attestations.json")));

        final GitHubListAttestationsFilter filter = GitHubListAttestationsFilterBuilder.builder()
                .predicateType("sbom")
                .build();

        new GitHubUsersAttestationsApi(TestClients.caller(transport))
                .listAttestations("octocat", "sha256:abc", filter, GitHubPageRequest.byCursor());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/attestations/sha256:abc?predicate_type=sbom&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listAttestations_shouldOmitPredicateType_whenBlank() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("list-attestations.json")));

        final GitHubListAttestationsFilter filter = GitHubListAttestationsFilterBuilder.builder()
                .predicateType("  ")
                .build();

        new GitHubUsersAttestationsApi(TestClients.caller(transport))
                .listAttestations("octocat", "sha256:abc", filter, GitHubPageRequest.byCursor());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/attestations/sha256:abc?"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listAttestationsBulk_shouldReturnBulk_whenCursorRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("list-attestations-bulk.json")));
        final GitHubListAttestationsBulkPayload payload = UsersCatalogFixtures.listAttestationsBulkPayload();

        final GitHubAttestationsBulk bulk = new GitHubUsersAttestationsApi(TestClients.caller(transport))
                .listAttestationsBulk("octocat", payload, GitHubCursorRequest.after("tok"));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(bulk).isEqualTo(UsersCatalogFixtures.attestationsBulk());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/attestations/bulk-list?after=tok&"
                            + TestClients.perPageQuery());
            softly.assertThat(requestBody(request)).contains("\"subject_digests\":[\"sha256:abc\"]");
        });
    }

    @Test
    void listAttestationsBulk_shouldReturnBulk_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("list-attestations-bulk.json")));
        final GitHubListAttestationsBulkPayload payload = GitHubListAttestationsBulkPayloadBuilder.builder(
                        UsersCatalogFixtures.listAttestationsBulkPayload())
                .subjectDigests(List.of("sha256:abc123", "sha512:def456"))
                .build();

        final GitHubAttestationsBulk bulk =
                new GitHubUsersAttestationsApi(TestClients.caller(transport)).listAttestationsBulk("octocat", payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(bulk).isEqualTo(UsersCatalogFixtures.attestationsBulk());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/attestations/bulk-list?"
                            + TestClients.perPageQuery());
            softly.assertThat(requestBody(request))
                    .contains("\"subject_digests\":[\"sha256:abc123\",\"sha512:def456\"]");
        });
    }

    @Test
    void listAttestationsBulk_shouldReturnBulk_whenNoneRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("list-attestations-bulk.json")));
        final GitHubListAttestationsBulkPayload payload = UsersCatalogFixtures.listAttestationsBulkPayload();

        final GitHubAttestationsBulk bulk = new GitHubUsersAttestationsApi(TestClients.caller(transport))
                .listAttestationsBulk("octocat", payload, GitHubPageRequest.none());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(bulk).isEqualTo(UsersCatalogFixtures.attestationsBulk());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/users/octocat/attestations/bulk-list");
            softly.assertThat(requestBody(request)).contains("\"subject_digests\":[\"sha256:abc\"]");
        });
    }

    @Test
    void deleteAttestationsBulk_shouldSucceed_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubDeleteAttestationsBulkPayload payload = GitHubDeleteAttestationsBulkPayloadBuilder.builder()
                .subjectDigests(List.of("sha256:abc"))
                .build();

        new GitHubUsersAttestationsApi(TestClients.caller(transport)).deleteAttestationsBulk("octocat", payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/attestations/delete-request");
            softly.assertThat(requestBody(request)).contains("\"subject_digests\":[\"sha256:abc\"]");
        });
    }

    @Test
    void deleteAttestationsBySubjectDigest_shouldSucceed_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), null));

        new GitHubUsersAttestationsApi(TestClients.caller(transport))
                .deleteAttestationsBySubjectDigest("octocat", "sha256:abc");
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/attestations/digest/sha256:abc");
        });
    }

    @Test
    void deleteAttestationsById_shouldSucceed_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        new GitHubUsersAttestationsApi(TestClients.caller(transport)).deleteAttestationsById("octocat", 1L);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/users/octocat/attestations/1");
        });
    }

    @Test
    void listAttestationsBulk_shouldThrowNotFound_whenUsernameMissing() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));
        final GitHubUsersAttestationsApi usersApi = new GitHubUsersAttestationsApi(TestClients.caller(transport));
        final GitHubListAttestationsBulkPayload payload = UsersCatalogFixtures.listAttestationsBulkPayload();

        final Throwable thrown = catchThrowable(() -> usersApi.listAttestationsBulk("missing", payload));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @Test
    void deleteAttestationsById_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));
        final GitHubUsersAttestationsApi usersApi = new GitHubUsersAttestationsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> usersApi.deleteAttestationsById("octocat", 1L));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    @ParameterizedTest
    @MethodSource("unsupportedListAttestationsPaginationRequests")
    void listAttestations_shouldThrow_whenUnsupportedPagination(final UsersCall call, final String messageFragment) {
        final GitHubUsersAttestationsApi usersApi = new GitHubUsersAttestationsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(usersApi));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("unsupportedListAttestationsBulkPageRequests")
    void listAttestationsBulk_shouldThrow_whenUnsupportedPagination(
            final GitHubPageRequest pageRequest, final String messageFragment) {
        final GitHubUsersAttestationsApi usersApi = new GitHubUsersAttestationsApi(TestClients.caller(transport));
        final GitHubListAttestationsBulkPayload payload = UsersCatalogFixtures.listAttestationsBulkPayload();

        final Throwable thrown = catchThrowable(() -> usersApi.listAttestationsBulk("octocat", payload, pageRequest));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private static Stream<Arguments> unsupportedListAttestationsPaginationRequests() {
        return Stream.of(
                Arguments.of(
                        (UsersCall) api -> api.listAttestations("octocat", "sha256:abc", GitHubPageRequest.bySince()),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (UsersCall) api -> api.listAttestations("octocat", "sha256:abc", GitHubPageRequest.byPage()),
                        "unsupported pagination request: page"));
    }

    private static Stream<Arguments> unsupportedListAttestationsBulkPageRequests() {
        return Stream.of(
                Arguments.of(GitHubPageRequest.bySince(), "unsupported pagination request: since"),
                Arguments.of(GitHubPageRequest.byPage(), "unsupported pagination request: page"));
    }

    @FunctionalInterface
    interface UsersCall {
        void execute(GitHubUsersAttestationsApi api) throws Exception;
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubUsersAttestationsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static String requestBody(final HttpRequest request) {
        return new String(request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8);
    }
}
