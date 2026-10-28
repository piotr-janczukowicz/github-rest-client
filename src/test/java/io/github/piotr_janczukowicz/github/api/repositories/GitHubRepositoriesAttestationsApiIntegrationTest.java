package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.reposAttestationsApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.users.GitHubAttestation;
import io.github.piotr_janczukowicz.github.api.users.GitHubAttestations;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubRepositoriesAttestationsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String SUBJECT_DIGEST = "sha256:abcd";
    private static final String LIST_PATH = "/repos/octocat/Hello-World/attestations/sha256:abcd";
    private static final String CREATE_PATH = "/repos/octocat/Hello-World/attestations";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listAttestations_shouldReturnAttestations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                LIST_PATH,
                okJson(jsonBytes(GitHubRepositoriesAttestationsApiIntegrationTest.class, "repo-attestations.json")));

        final GitHubPage<GitHubAttestation> page;
        try (final GitHubApiWireMockSupport.ReposAttestationsApiClient attestations = reposAttestationsApi(HTTP)) {
            page = attestations.api().listAttestations(OWNER, REPO, SUBJECT_DIGEST);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedAttestations());
        HTTP.verifyRequested(GET, LIST_PATH);
    }

    @Test
    void listAttestations_shouldSendPredicateType_whenProvided() throws Exception {
        HTTP.stub(
                GET,
                LIST_PATH,
                okJson(jsonBytes(GitHubRepositoriesAttestationsApiIntegrationTest.class, "repo-attestations.json")));

        final GitHubPage<GitHubAttestation> page;
        try (final GitHubApiWireMockSupport.ReposAttestationsApiClient attestations = reposAttestationsApi(HTTP)) {
            final GitHubListAttestationsFilter filter = GitHubListAttestationsFilterBuilder.builder()
                    .predicateType("provenance")
                    .build();
            page = attestations
                    .api()
                    .listAttestations(OWNER, REPO, SUBJECT_DIGEST, filter, GitHubPageRequest.byCursor());
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedAttestations());
        HTTP.verifyRequested(GET, LIST_PATH);
        HTTP.verifyQueryParam(GET, LIST_PATH, "predicate_type", "provenance");
    }

    @Test
    void listAttestations_shouldOmitPredicateType_whenBlank() throws Exception {
        HTTP.stub(
                GET,
                LIST_PATH,
                okJson(jsonBytes(GitHubRepositoriesAttestationsApiIntegrationTest.class, "repo-attestations.json")));

        try (final GitHubApiWireMockSupport.ReposAttestationsApiClient attestations = reposAttestationsApi(HTTP)) {
            final GitHubListAttestationsFilter filter = GitHubListAttestationsFilterBuilder.builder()
                    .predicateType("   ")
                    .build();
            attestations.api().listAttestations(OWNER, REPO, SUBJECT_DIGEST, filter, GitHubPageRequest.byCursor());
        }

        HTTP.verifyRequested(GET, LIST_PATH);
        HTTP.verifyNoQueryParam(GET, LIST_PATH, "predicate_type");
    }

    @Test
    void listAttestations_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                LIST_PATH,
                okJson(jsonBytes(GitHubRepositoriesAttestationsApiIntegrationTest.class, "repo-attestations.json"))
                        .header(LINK, linkNext(HTTP.uri(LIST_PATH + "?after=cursor&" + TestClients.perPageQuery()))),
                okJson(jsonBytes(GitHubRepositoriesAttestationsApiIntegrationTest.class, "repo-attestations.json")));

        final GitHubPage<GitHubAttestation> first;
        final Optional<GitHubPage<GitHubAttestation>> second;
        final Optional<GitHubPage<GitHubAttestation>> third;
        try (final GitHubApiWireMockSupport.ReposAttestationsApiClient attestations = reposAttestationsApi(HTTP)) {
            first = attestations.api().listAttestations(OWNER, REPO, SUBJECT_DIGEST);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedAttestations());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedAttestations());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, LIST_PATH, 2);
    }

    @Test
    void createAttestation_shouldReturnCreated_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                CREATE_PATH,
                createdJson(
                        jsonBytes(GitHubRepositoriesAttestationsApiIntegrationTest.class, "created-attestation.json")));
        final GitHubCreateAttestationPayload payload = RepositoriesCatalogFixtures.createAttestationPayload();

        final GitHubCreatedAttestation created;
        try (final GitHubApiWireMockSupport.ReposAttestationsApiClient attestations = reposAttestationsApi(HTTP)) {
            created = attestations.api().createAttestation(OWNER, REPO, payload);
        }

        final GitHubCreatedAttestation expected = CODEC.decode(
                jsonBytes(GitHubRepositoriesAttestationsApiIntegrationTest.class, "created-attestation.json"),
                GitHubCreatedAttestation.class);

        assertThat(created).isEqualTo(expected);
        HTTP.verifyRequested(POST, CREATE_PATH);
        HTTP.verifyHeader(POST, CREATE_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, CREATE_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    private static List<GitHubAttestation> expectedAttestations() {
        return CODEC.decode(
                        jsonBytes(GitHubRepositoriesAttestationsApiIntegrationTest.class, "repo-attestations.json"),
                        GitHubAttestations.class)
                .attestations();
    }
}
