package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.acceptedJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.organizationsApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubOrganizationsPersonalAccessTokensApiIntegrationTest {

    private static final JsonType<List<GitHubOrganizationPatGrantRequest>> PAT_GRANT_REQUESTS_TYPE =
            JsonTypes.listOf(GitHubOrganizationPatGrantRequest.class);
    private static final JsonType<List<GitHubOrganizationPatGrant>> PAT_GRANTS_TYPE =
            JsonTypes.listOf(GitHubOrganizationPatGrant.class);
    private static final JsonType<List<GitHubMinimalRepository>> MINIMAL_REPOSITORIES_TYPE =
            JsonTypes.listOf(GitHubMinimalRepository.class);

    private static final String ORG = "github";
    private static final long PAT_REQUEST_ID = 25_381L;
    private static final long PAT_ID = 25_381L;
    private static final String PAT_GRANT_REQUESTS_PATH = "/orgs/github/personal-access-token-requests";
    private static final String PAT_GRANT_REQUEST_PATH = "/orgs/github/personal-access-token-requests/25381";
    private static final String PAT_GRANT_REQUEST_REPOSITORIES_PATH =
            "/orgs/github/personal-access-token-requests/25381/repositories";
    private static final String PAT_GRANTS_PATH = "/orgs/github/personal-access-tokens";
    private static final String PAT_GRANT_PATH = "/orgs/github/personal-access-tokens/25381";
    private static final String PAT_GRANT_REPOSITORIES_PATH = "/orgs/github/personal-access-tokens/25381/repositories";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listPatGrantRequests_shouldReturnGrantRequests_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, PAT_GRANT_REQUESTS_PATH, okJson(patGrantRequestItemsJson()));

        final GitHubPage<GitHubOrganizationPatGrantRequest> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().personalAccessTokens().listPatGrantRequests(ORG);
        }

        final List<GitHubOrganizationPatGrantRequest> expected =
                CODEC.decode(patGrantRequestItemsJson(), PAT_GRANT_REQUESTS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, PAT_GRANT_REQUESTS_PATH);
    }

    @Test
    void reviewPatGrantRequestsInBulk_shouldSucceed_whenResponseIsAccepted() throws Exception {
        HTTP.stub(POST, PAT_GRANT_REQUESTS_PATH, acceptedJson("{}".getBytes(StandardCharsets.UTF_8)));
        final GitHubReviewPatGrantRequestsInBulkPayload payload =
                OrganizationsCatalogFixtures.reviewPatGrantRequestsInBulkPayload();

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().personalAccessTokens().reviewPatGrantRequestsInBulk(ORG, payload);
        }

        HTTP.verifyBody(POST, PAT_GRANT_REQUESTS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void reviewPatGrantRequest_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(POST, PAT_GRANT_REQUEST_PATH, noContent());
        final GitHubReviewPatGrantRequestPayload payload = OrganizationsCatalogFixtures.reviewPatGrantRequestPayload();

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().personalAccessTokens().reviewPatGrantRequest(ORG, PAT_REQUEST_ID, payload);
        }

        HTTP.verifyBody(POST, PAT_GRANT_REQUEST_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void listPatGrantRequestRepositories_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PAT_GRANT_REQUEST_REPOSITORIES_PATH,
                okJson(listJsonBytes(GitHubMinimalRepository.class, "minimal-repository.json")));

        final GitHubPage<GitHubMinimalRepository> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().personalAccessTokens().listPatGrantRequestRepositories(ORG, PAT_REQUEST_ID);
        }

        final List<GitHubMinimalRepository> expected = CODEC.decode(
                listJsonBytes(GitHubMinimalRepository.class, "minimal-repository.json"), MINIMAL_REPOSITORIES_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, PAT_GRANT_REQUEST_REPOSITORIES_PATH);
    }

    @Test
    void listPatGrants_shouldReturnGrants_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, PAT_GRANTS_PATH, okJson(patGrantItemsJson()));

        final GitHubPage<GitHubOrganizationPatGrant> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().personalAccessTokens().listPatGrants(ORG);
        }

        final List<GitHubOrganizationPatGrant> expected = CODEC.decode(patGrantItemsJson(), PAT_GRANTS_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, PAT_GRANTS_PATH);
    }

    @Test
    void updatePatAccesses_shouldSucceed_whenResponseIsAccepted() throws Exception {
        HTTP.stub(POST, PAT_GRANTS_PATH, acceptedJson("{}".getBytes(StandardCharsets.UTF_8)));
        final GitHubUpdatePatAccessesPayload payload = OrganizationsCatalogFixtures.updatePatAccessesPayload();

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().personalAccessTokens().updatePatAccesses(ORG, payload);
        }

        HTTP.verifyBody(POST, PAT_GRANTS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void updatePatAccess_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(POST, PAT_GRANT_PATH, noContent());
        final GitHubUpdatePatAccessPayload payload = OrganizationsCatalogFixtures.updatePatAccessPayload();

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().personalAccessTokens().updatePatAccess(ORG, PAT_ID, payload);
        }

        HTTP.verifyBody(POST, PAT_GRANT_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void listPatGrantRepositories_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PAT_GRANT_REPOSITORIES_PATH,
                okJson(listJsonBytes(GitHubMinimalRepository.class, "minimal-repository.json")));

        final GitHubPage<GitHubMinimalRepository> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().personalAccessTokens().listPatGrantRepositories(ORG, PAT_ID);
        }

        final List<GitHubMinimalRepository> expected = CODEC.decode(
                listJsonBytes(GitHubMinimalRepository.class, "minimal-repository.json"), MINIMAL_REPOSITORIES_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, PAT_GRANT_REPOSITORIES_PATH);
    }

    private static byte[] patGrantRequestItemsJson() {
        return jsonBytes(
                GitHubOrganizationsPersonalAccessTokensApiIntegrationTest.class, "org-pat-grant-request-items.json");
    }

    private static byte[] patGrantItemsJson() {
        return jsonBytes(GitHubOrganizationsPersonalAccessTokensApiIntegrationTest.class, "org-pat-grant-items.json");
    }
}
