package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.usersApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubUsersAttestationsApiIntegrationTest {

    private static final String USERNAME = "octocat";
    private static final String SUBJECT_DIGEST = "sha256:abc";
    private static final String LIST_ATTESTATIONS_PATH = "/users/octocat/attestations/sha256:abc";
    private static final String LIST_ATTESTATIONS_BULK_PATH = "/users/octocat/attestations/bulk-list";
    private static final String DELETE_ATTESTATIONS_BULK_PATH = "/users/octocat/attestations/delete-request";
    private static final String DELETE_BY_DIGEST_PATH = "/users/octocat/attestations/digest/sha256:abc";
    private static final String DELETE_BY_ID_PATH = "/users/octocat/attestations/1";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listAttestations_shouldReturnAttestations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                LIST_ATTESTATIONS_PATH,
                okJson(jsonBytes(GitHubUsersAttestationsApiIntegrationTest.class, "list-attestations.json")));

        final GitHubPage<GitHubAttestation> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().attestations().listAttestations(USERNAME, SUBJECT_DIGEST);
        }

        assertThat(page.items()).isEqualTo(expectedAttestations());
        HTTP.verifyRequested(GET, LIST_ATTESTATIONS_PATH);
    }

    @Test
    void listAttestations_shouldReturnAttestations_whenPredicateTypeProvided() throws Exception {
        HTTP.stub(
                GET,
                LIST_ATTESTATIONS_PATH,
                okJson(jsonBytes(GitHubUsersAttestationsApiIntegrationTest.class, "list-attestations.json")));

        final GitHubPage<GitHubAttestation> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            final GitHubListAttestationsFilter filter = GitHubListAttestationsFilterBuilder.builder()
                    .predicateType("provenance")
                    .build();
            page = users.api()
                    .attestations()
                    .listAttestations(USERNAME, SUBJECT_DIGEST, filter, GitHubPageRequest.byCursor());
        }

        assertThat(page.items()).isEqualTo(expectedAttestations());
        HTTP.verifyRequested(GET, LIST_ATTESTATIONS_PATH);
    }

    @Test
    void listAttestationsBulk_shouldReturnBulk_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                LIST_ATTESTATIONS_BULK_PATH,
                okJson(jsonBytes(GitHubUsersAttestationsApiIntegrationTest.class, "list-attestations-bulk.json")));

        final GitHubListAttestationsBulkPayload payload = CODEC.decode(
                jsonBytes(GitHubUsersAttestationsApiIntegrationTest.class, "list-attestations-bulk-request.json"),
                GitHubListAttestationsBulkPayload.class);

        final GitHubAttestationsBulk bulk;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            bulk = users.api().attestations().listAttestationsBulk(USERNAME, payload);
        }

        assertThat(bulk).isEqualTo(expectedAttestationsBulk());
        HTTP.verifyHeader(POST, LIST_ATTESTATIONS_BULK_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyRequested(POST, LIST_ATTESTATIONS_BULK_PATH);
    }

    @Test
    void deleteAttestationsBulk_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(POST, DELETE_ATTESTATIONS_BULK_PATH, noContent());
        final GitHubDeleteAttestationsBulkPayload payload = GitHubDeleteAttestationsBulkPayloadBuilder.builder()
                .subjectDigests(List.of(SUBJECT_DIGEST))
                .build();

        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            users.api().attestations().deleteAttestationsBulk(USERNAME, payload);
        }

        HTTP.verifyHeader(POST, DELETE_ATTESTATIONS_BULK_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyRequested(POST, DELETE_ATTESTATIONS_BULK_PATH);
    }

    @Test
    void deleteAttestationsBySubjectDigest_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, DELETE_BY_DIGEST_PATH, noContent());

        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            users.api().attestations().deleteAttestationsBySubjectDigest(USERNAME, SUBJECT_DIGEST);
        }

        HTTP.verifyRequested(DELETE, DELETE_BY_DIGEST_PATH);
    }

    @Test
    void deleteAttestationsById_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, DELETE_BY_ID_PATH, noContent());

        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            users.api().attestations().deleteAttestationsById(USERNAME, 1L);
        }

        HTTP.verifyRequested(DELETE, DELETE_BY_ID_PATH);
    }

    private static List<GitHubAttestation> expectedAttestations() {
        return CODEC.decode(
                        jsonBytes(GitHubUsersAttestationsApiIntegrationTest.class, "list-attestations.json"),
                        GitHubAttestations.class)
                .attestations();
    }

    private static GitHubAttestationsBulk expectedAttestationsBulk() {
        return CODEC.decode(
                jsonBytes(GitHubUsersAttestationsApiIntegrationTest.class, "list-attestations-bulk.json"),
                GitHubAttestationsBulk.class);
    }
}
