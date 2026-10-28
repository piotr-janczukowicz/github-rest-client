package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpStatus.OK;
import static io.github.piotr_janczukowicz.test.http.StubHttpServer.status;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.organizationsApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.users.GitHubAttestation;
import io.github.piotr_janczukowicz.github.api.users.GitHubAttestations;
import io.github.piotr_janczukowicz.github.api.users.GitHubAttestationsBulk;
import io.github.piotr_janczukowicz.github.api.users.GitHubDeleteAttestationsBulkPayload;
import io.github.piotr_janczukowicz.github.api.users.GitHubDeleteAttestationsBulkPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubListAttestationsBulkPayload;
import io.github.piotr_janczukowicz.github.api.users.GitHubListAttestationsFilterBuilder;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubOrganizationsAttestationsApiIntegrationTest {

    private static final String ORG = "github";
    private static final String SUBJECT_DIGEST = "sha256:abc";
    private static final String LIST_ATTESTATIONS_PATH = "/orgs/github/attestations/sha256:abc";
    private static final String LIST_ATTESTATIONS_BULK_PATH = "/orgs/github/attestations/bulk-list";
    private static final String DELETE_ATTESTATIONS_BULK_PATH = "/orgs/github/attestations/delete-request";
    private static final String DELETE_BY_DIGEST_PATH = "/orgs/github/attestations/digest/sha256:abc";
    private static final String LIST_REPOSITORIES_PATH = "/orgs/github/attestations/repositories";
    private static final String DELETE_BY_ID_PATH = "/orgs/github/attestations/1";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listAttestations_shouldReturnAttestations_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, LIST_ATTESTATIONS_PATH, okJson(usersJson("list-attestations.json")));

        final GitHubPage<GitHubAttestation> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().attestations().listAttestations(ORG, SUBJECT_DIGEST);
        }

        assertThat(page.items()).isEqualTo(expectedAttestations());
        HTTP.verifyRequested(GET, LIST_ATTESTATIONS_PATH);
    }

    @Test
    void listAttestations_shouldReturnAttestations_whenPredicateTypeProvided() throws Exception {
        HTTP.stub(GET, LIST_ATTESTATIONS_PATH, okJson(usersJson("list-attestations.json")));

        final GitHubPage<GitHubAttestation> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations
                    .api()
                    .attestations()
                    .listAttestations(
                            ORG,
                            SUBJECT_DIGEST,
                            GitHubListAttestationsFilterBuilder.builder()
                                    .predicateType("provenance")
                                    .build(),
                            GitHubPageRequest.byCursor());
        }

        assertThat(page.items()).isEqualTo(expectedAttestations());
        HTTP.verifyRequested(GET, LIST_ATTESTATIONS_PATH);
        HTTP.verifyQueryParam(GET, LIST_ATTESTATIONS_PATH, "predicate_type", "provenance");
    }

    @Test
    void listAttestationsBulk_shouldReturnBulk_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, LIST_ATTESTATIONS_BULK_PATH, okJson(usersJson("list-attestations-bulk.json")));
        final GitHubListAttestationsBulkPayload payload =
                CODEC.decode(usersJson("list-attestations-bulk-request.json"), GitHubListAttestationsBulkPayload.class);

        final GitHubAttestationsBulk bulk;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            bulk = organizations.api().attestations().listAttestationsBulk(ORG, payload);
        }

        assertThat(bulk).isEqualTo(expectedAttestationsBulk());
        HTTP.verifyHeader(POST, LIST_ATTESTATIONS_BULK_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyRequested(POST, LIST_ATTESTATIONS_BULK_PATH);
    }

    @Test
    void deleteAttestationsBulk_shouldSucceed_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, DELETE_ATTESTATIONS_BULK_PATH, status(OK));
        final GitHubDeleteAttestationsBulkPayload payload = GitHubDeleteAttestationsBulkPayloadBuilder.builder()
                .subjectDigests(List.of(SUBJECT_DIGEST))
                .build();

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().attestations().deleteAttestationsBulk(ORG, payload);
        }

        HTTP.verifyHeader(POST, DELETE_ATTESTATIONS_BULK_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyRequested(POST, DELETE_ATTESTATIONS_BULK_PATH);
    }

    @Test
    void deleteAttestationsBySubjectDigest_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, DELETE_BY_DIGEST_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().attestations().deleteAttestationsBySubjectDigest(ORG, SUBJECT_DIGEST);
        }

        HTTP.verifyRequested(DELETE, DELETE_BY_DIGEST_PATH);
    }

    @Test
    void listAttestationRepositories_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, LIST_REPOSITORIES_PATH, okJson(orgJson("list-attestation-repositories.json")));

        final GitHubPage<GitHubAttestationRepository> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().attestations().listAttestationRepositories(ORG);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedRepositories());
        HTTP.verifyRequested(GET, LIST_REPOSITORIES_PATH);
    }

    @Test
    void listAttestationRepositories_shouldSendPredicateType_whenProvided() throws Exception {
        HTTP.stub(GET, LIST_REPOSITORIES_PATH, okJson(orgJson("list-attestation-repositories.json")));

        final GitHubPage<GitHubAttestationRepository> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations
                    .api()
                    .attestations()
                    .listAttestationRepositories(
                            ORG,
                            GitHubListAttestationsFilterBuilder.builder()
                                    .predicateType("provenance")
                                    .build(),
                            GitHubPageRequest.byCursor());
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedRepositories());
        HTTP.verifyRequested(GET, LIST_REPOSITORIES_PATH);
        HTTP.verifyQueryParam(GET, LIST_REPOSITORIES_PATH, "predicate_type", "provenance");
    }

    @Test
    void deleteAttestationsById_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, DELETE_BY_ID_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().attestations().deleteAttestationsById(ORG, 1L);
        }

        HTTP.verifyRequested(DELETE, DELETE_BY_ID_PATH);
    }

    private static byte[] usersJson(final String fileName) {
        return jsonBytes(GitHubAttestation.class, fileName);
    }

    private static byte[] orgJson(final String fileName) {
        return jsonBytes(GitHubOrganizationsAttestationsApiIntegrationTest.class, fileName);
    }

    private static List<GitHubAttestation> expectedAttestations() {
        return CODEC.decode(usersJson("list-attestations.json"), GitHubAttestations.class)
                .attestations();
    }

    private static GitHubAttestationsBulk expectedAttestationsBulk() {
        return CODEC.decode(usersJson("list-attestations-bulk.json"), GitHubAttestationsBulk.class);
    }

    private static List<GitHubAttestationRepository> expectedRepositories() {
        return Arrays.asList(
                CODEC.decode(orgJson("list-attestation-repositories.json"), GitHubAttestationRepository[].class));
    }
}
