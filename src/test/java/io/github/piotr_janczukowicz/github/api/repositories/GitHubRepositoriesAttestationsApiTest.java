package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.users.GitHubAttestation;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubRepositoriesAttestationsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String SUBJECT_DIGEST = "sha256:abcd";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoriesAttestationsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listAttestations_shouldReturnAttestations_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repo-attestations.json")));

        final GitHubPage<GitHubAttestation> page = api().listAttestations(OWNER, REPO, SUBJECT_DIGEST);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.repositoryAttestation());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri.getPath()).isEqualTo("/repos/octocat/Hello-World/attestations/sha256:abcd");
        });
    }

    @Test
    void listAttestations_shouldPercentEncodeSlashInOwner_whenOwnerContainsSlash() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repo-attestations.json")));

        api().listAttestations("a/b", REPO, SUBJECT_DIGEST);

        assertThat(captureSingleRequest().uri().getRawPath())
                .isEqualTo("/repos/a%2Fb/Hello-World/attestations/sha256:abcd");
    }

    @Test
    void createAttestation_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("created-attestation.json")));
        final GitHubCreateAttestationPayload payload = RepositoriesCatalogFixtures.createAttestationPayload();

        final GitHubCreatedAttestation created = api().createAttestation(OWNER, REPO, payload);

        assertThat(created).isEqualTo(RepositoriesCatalogFixtures.createdAttestation());
    }

    @Test
    void listAttestations_shouldSendPredicateType_whenProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repo-attestations.json")));

        final GitHubListAttestationsFilter filter = GitHubListAttestationsFilterBuilder.builder()
                .predicateType("provenance")
                .build();

        api().listAttestations(OWNER, REPO, SUBJECT_DIGEST, filter, GitHubPageRequest.byCursor());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri().getQuery()).contains("predicate_type=provenance");
    }

    @Test
    void listAttestations_shouldOmitPredicateType_whenBlank() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repo-attestations.json")));

        final GitHubListAttestationsFilter filter = GitHubListAttestationsFilterBuilder.builder()
                .predicateType("   ")
                .build();

        api().listAttestations(OWNER, REPO, SUBJECT_DIGEST, filter, GitHubPageRequest.byCursor());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri().getQuery()).doesNotContain("predicate_type");
    }

    private GitHubRepositoriesAttestationsApi api() {
        return new GitHubRepositoriesAttestationsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubRepositoriesAttestationsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
