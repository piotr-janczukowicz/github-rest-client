package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.users.GitHubAttestation;
import io.github.piotr_janczukowicz.github.api.users.GitHubAttestationsBulk;
import io.github.piotr_janczukowicz.github.api.users.GitHubDeleteAttestationsBulkPayload;
import io.github.piotr_janczukowicz.github.api.users.GitHubDeleteAttestationsBulkPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubListAttestationsBulkPayload;
import io.github.piotr_janczukowicz.github.api.users.GitHubListAttestationsFilterBuilder;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
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
import java.net.URI;
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
class GitHubOrganizationsAttestationsApiTest {

    private static final String ORG = "github";
    private static final String SUBJECT_DIGEST = "sha256:abc";
    private static final long ATTESTATION_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsAttestationsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listAttestations_shouldReturnAttestations_whenCursorRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), usersJson("list-attestations.json")));

        final GitHubPage<GitHubAttestation> page = callApi(api -> api.listAttestations(
                ORG,
                SUBJECT_DIGEST,
                GitHubListAttestationsFilterBuilder.builder()
                        .predicateType("provenance")
                        .build(),
                GitHubCursorRequest.after("tok")));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            UsersCatalogFixtures.attestations().attestations());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/orgs/github/attestations/sha256:abc?predicate_type=provenance&after=tok&"
                                    + TestClients.perPageQuery());
        });
    }

    @Test
    void listAttestations_shouldReturnAttestations_whenNoneRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), usersJson("list-attestations.json")));

        final GitHubPage<GitHubAttestation> page =
                callApi(api -> api.listAttestations(ORG, SUBJECT_DIGEST, GitHubPageRequest.none()));

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            UsersCatalogFixtures.attestations().attestations());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listAttestations_shouldReturnAttestations_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), usersJson("list-attestations.json")));

        final GitHubPage<GitHubAttestation> page = callApi(api -> api.listAttestations(ORG, SUBJECT_DIGEST));
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport, atLeastOnce()).execute(captor.capture());

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            UsersCatalogFixtures.attestations().attestations());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(captor.getAllValues().getLast().uri())
                    .hasToString(
                            "https://api.github.com/orgs/github/attestations/sha256:abc?" + TestClients.perPageQuery());
        });
    }

    @Test
    void listAttestations_shouldPercentEncodeSlashInOrg_whenOrgContainsSlash() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), usersJson("list-attestations.json")));

        callApi(api -> api.listAttestations("a/b", SUBJECT_DIGEST));
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport, atLeastOnce()).execute(captor.capture());

        assertThat(captor.getAllValues().getLast().uri().getRawPath()).isEqualTo("/orgs/a%2Fb/attestations/sha256:abc");
    }

    @Test
    void listAttestations_shouldOmitPredicateType_whenBlank() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), usersJson("list-attestations.json")));

        callApi(api -> api.listAttestations(
                ORG,
                SUBJECT_DIGEST,
                GitHubListAttestationsFilterBuilder.builder()
                        .predicateType("  ")
                        .build(),
                GitHubPageRequest.byCursor()));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString(
                        "https://api.github.com/orgs/github/attestations/sha256:abc?" + TestClients.perPageQuery());
    }

    @Test
    void listAttestationsBulk_shouldReturnBulk_whenCursorRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), usersJson("list-attestations-bulk.json")));
        final GitHubListAttestationsBulkPayload payload = UsersCatalogFixtures.listAttestationsBulkPayload();

        final GitHubAttestationsBulk bulk =
                callApi(api -> api.listAttestationsBulk(ORG, payload, GitHubCursorRequest.after("tok")));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(bulk).isEqualTo(UsersCatalogFixtures.attestationsBulk());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/attestations/bulk-list?after=tok&"
                            + TestClients.perPageQuery());
            softly.assertThat(requestBody(request)).contains("\"subject_digests\":[\"sha256:abc\"]");
        });
    }

    @Test
    void listAttestationsBulk_shouldReturnBulk_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), usersJson("list-attestations-bulk.json")));
        final GitHubListAttestationsBulkPayload payload = UsersCatalogFixtures.listAttestationsBulkPayload();

        final GitHubAttestationsBulk bulk = callApi(api -> api.listAttestationsBulk(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(bulk).isEqualTo(UsersCatalogFixtures.attestationsBulk());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/orgs/github/attestations/bulk-list?" + TestClients.perPageQuery());
        });
    }

    @Test
    void listAttestationsBulk_shouldReturnBulk_whenNoneRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), usersJson("list-attestations-bulk.json")));
        final GitHubListAttestationsBulkPayload payload = UsersCatalogFixtures.listAttestationsBulkPayload();

        final GitHubAttestationsBulk bulk =
                callApi(api -> api.listAttestationsBulk(ORG, payload, GitHubPageRequest.none()));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(bulk).isEqualTo(UsersCatalogFixtures.attestationsBulk());
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/attestations/bulk-list");
        });
    }

    @Test
    void deleteAttestationsBulk_shouldSucceed_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), null));
        final GitHubDeleteAttestationsBulkPayload payload = GitHubDeleteAttestationsBulkPayloadBuilder.builder()
                .subjectDigests(List.of(SUBJECT_DIGEST))
                .build();

        callApi(api -> {
            api.deleteAttestationsBulk(ORG, payload);
            return null;
        });
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/attestations/delete-request");
            softly.assertThat(requestBody(request)).contains("\"subject_digests\":[\"sha256:abc\"]");
        });
    }

    @Test
    void deleteAttestationsBySubjectDigest_shouldSucceed_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), null));

        callApi(api -> {
            api.deleteAttestationsBySubjectDigest(ORG, SUBJECT_DIGEST);
            return null;
        });
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/attestations/digest/sha256:abc");
        });
    }

    @Test
    void listAttestationRepositories_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-attestation-repositories.json")));

        final GitHubPage<GitHubAttestationRepository> page = callApi(api -> api.listAttestationRepositories(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(OrganizationsCatalogFixtures.attestationRepositories());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/attestations/repositories?"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listAttestationRepositories_shouldReturnRepositories_whenCursorRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-attestation-repositories.json")));

        final GitHubPage<GitHubAttestationRepository> page =
                callApi(api -> api.listAttestationRepositories(ORG, GitHubCursorRequest.after("tok")));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(OrganizationsCatalogFixtures.attestationRepositories());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/attestations/repositories?after=tok&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listAttestationRepositories_shouldSendPredicateType_whenProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-attestation-repositories.json")));

        callApi(api -> api.listAttestationRepositories(
                ORG,
                GitHubListAttestationsFilterBuilder.builder()
                        .predicateType("provenance")
                        .build(),
                GitHubPageRequest.byCursor()));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/orgs/github/attestations/repositories?predicate_type=provenance&"
                        + TestClients.perPageQuery());
    }

    @Test
    void listAttestationRepositories_shouldOmitPredicateType_whenBlank() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-attestation-repositories.json")));

        callApi(api -> api.listAttestationRepositories(
                ORG,
                GitHubListAttestationsFilterBuilder.builder()
                        .predicateType("  ")
                        .build(),
                GitHubPageRequest.byCursor()));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString(
                        "https://api.github.com/orgs/github/attestations/repositories?" + TestClients.perPageQuery());
    }

    @Test
    void deleteAttestationsById_shouldSucceed_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApi(api -> {
            api.deleteAttestationsById(ORG, ATTESTATION_ID);
            return null;
        });
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/attestations/1");
        });
    }

    @ParameterizedTest
    @MethodSource("unsupportedPaginationRequests")
    void listEndpoint_shouldThrow_whenUnsupportedPagination(
            final AttestationsApiCall call, final String messageFragment) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubListAttestationsBulkPayload bulkPayload = UsersCatalogFixtures.listAttestationsBulkPayload();
        return Stream.of(
                Arguments.of(
                        (AttestationsApiCall)
                                api -> api.listAttestations(ORG, SUBJECT_DIGEST, GitHubPageRequest.bySince()),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (AttestationsApiCall)
                                api -> api.listAttestations(ORG, SUBJECT_DIGEST, GitHubPageRequest.byPage()),
                        "unsupported pagination request: page"),
                Arguments.of(
                        (AttestationsApiCall)
                                api -> api.listAttestationsBulk(ORG, bulkPayload, GitHubPageRequest.bySince()),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (AttestationsApiCall)
                                api -> api.listAttestationsBulk(ORG, bulkPayload, GitHubPageRequest.byPage()),
                        "unsupported pagination request: page"),
                Arguments.of(
                        (AttestationsApiCall) api -> api.listAttestationRepositories(ORG, GitHubPageRequest.bySince()),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (AttestationsApiCall) api -> api.listAttestationRepositories(ORG, GitHubPageRequest.byPage()),
                        "unsupported pagination request: page"));
    }

    @FunctionalInterface
    interface AttestationsApiCall {
        void execute(GitHubOrganizationsAttestationsApi api) throws Exception;
    }

    @FunctionalInterface
    private interface AttestationsApiAction<T> {
        T apply(GitHubOrganizationsAttestationsApi api) throws Exception;
    }

    private void callApiVoid(final AttestationsApiCall action) throws Exception {
        try (final GitHubClient client = TestClients.client(transport)) {
            action.execute(new GitHubOrganizationsAttestationsApi(TestClients.caller(client)));
        }
    }

    private <T> T callApi(final AttestationsApiAction<T> action) throws Exception {
        try (final GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationsAttestationsApi(TestClients.caller(client)));
        }
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubOrganizationsAttestationsApiTest.class, fileName);
    }

    private static String usersJson(final String fileName) {
        return FileUtils.readString(GitHubAttestation.class, fileName);
    }

    private static String requestBody(final HttpRequest request) {
        return new String(request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
