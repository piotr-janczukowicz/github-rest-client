package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.accepted;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.acceptedJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.securityAdvisoriesApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubFullRepository;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubSecurityAdvisoriesApiIntegrationTest {

    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final String GLOBAL_GHSA_ID = "GHSA-jfh8-c2jp-5v3q";
    private static final String REPOSITORY_GHSA_ID = "GHSA-49wp-qq6x-g2rf";

    private static final String ADVISORIES_PATH = "/advisories";
    private static final String GLOBAL_ADVISORY_PATH = "/advisories/" + GLOBAL_GHSA_ID;
    private static final String ORG_ADVISORIES_PATH = "/orgs/octo-org/security-advisories";
    private static final String REPO_ADVISORIES_PATH = "/repos/octocat/hello-world/security-advisories";
    private static final String REPO_REPORTS_PATH = "/repos/octocat/hello-world/security-advisories/reports";
    private static final String REPO_ADVISORY_PATH =
            "/repos/octocat/hello-world/security-advisories/" + REPOSITORY_GHSA_ID;
    private static final String REPO_ADVISORY_CVE_PATH = REPO_ADVISORY_PATH + "/cve";
    private static final String REPO_ADVISORY_FORKS_PATH = REPO_ADVISORY_PATH + "/forks";

    private static final JsonType<List<GitHubGlobalAdvisory>> GLOBAL_ADVISORIES_TYPE =
            JsonTypes.listOf(GitHubGlobalAdvisory.class);
    private static final JsonType<List<GitHubRepositoryAdvisory>> REPOSITORY_ADVISORIES_TYPE =
            JsonTypes.listOf(GitHubRepositoryAdvisory.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listGlobalAdvisories_shouldReturnAdvisories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ADVISORIES_PATH,
                okJson(listJsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "global-advisory.json")));

        final GitHubPage<GitHubGlobalAdvisory> page;
        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            page = advisories.api().globalAdvisories().listGlobalAdvisories();
        }

        final List<GitHubGlobalAdvisory> expected = CODEC.decode(
                listJsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "global-advisory.json"),
                GLOBAL_ADVISORIES_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, ADVISORIES_PATH);
        HTTP.verifyQueryParam(GET, ADVISORIES_PATH, "per_page", "100");
    }

    @Test
    void listGlobalAdvisories_shouldFollowLinkNext_whenAfterCursorPresent() throws Exception {
        HTTP.stub(
                GET,
                ADVISORIES_PATH,
                okJson(listJsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "global-advisory.json"))
                        .header(
                                LINK,
                                linkNext(HTTP.uri(
                                        ADVISORIES_PATH + "?after=cursor-after-token&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "global-advisory.json")));

        final GitHubPage<GitHubGlobalAdvisory> first;
        final Optional<GitHubPage<GitHubGlobalAdvisory>> second;
        final Optional<GitHubPage<GitHubGlobalAdvisory>> third;
        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            first = advisories.api().globalAdvisories().listGlobalAdvisories();
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        final List<GitHubGlobalAdvisory> expected = expectedGlobalAdvisories();

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expected);
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expected);
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, ADVISORIES_PATH, 2);
    }

    @Test
    void getGlobalAdvisory_shouldReturnAdvisory_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                GLOBAL_ADVISORY_PATH,
                okJson(jsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "global-advisory.json")));

        final GitHubGlobalAdvisory advisory;
        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            advisory = advisories
                    .api()
                    .globalAdvisories()
                    .getGlobalAdvisory(GLOBAL_GHSA_ID)
                    .orElseThrow();
        }

        final GitHubGlobalAdvisory expected = CODEC.decode(
                jsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "global-advisory.json"),
                GitHubGlobalAdvisory.class);

        assertThat(advisory).isEqualTo(expected);
        HTTP.verifyRequested(GET, GLOBAL_ADVISORY_PATH);
    }

    @Test
    void getGlobalAdvisory_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, GLOBAL_ADVISORY_PATH, notFound());

        final Optional<GitHubGlobalAdvisory> advisory;
        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            advisory = advisories.api().globalAdvisories().getGlobalAdvisory(GLOBAL_GHSA_ID);
        }

        assertThat(advisory).isEmpty();
        HTTP.verifyRequested(GET, GLOBAL_ADVISORY_PATH);
    }

    @Test
    void listGlobalAdvisories_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                ADVISORIES_PATH,
                okJson(listJsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "global-advisory.json")));
        final GitHubListGlobalAdvisoriesFilter filter = GitHubListGlobalAdvisoriesFilterBuilder.builder()
                .type(GitHubGlobalAdvisoryType.REVIEWED)
                .ecosystem(GitHubSecurityAdvisoryEcosystem.NPM)
                .severity(GitHubSecurityAdvisorySeverity.HIGH)
                .direction(GitHubDirection.DESC)
                .sort(GitHubGlobalAdvisoriesSort.UPDATED)
                .build();

        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            advisories.api().globalAdvisories().listGlobalAdvisories(filter);
        }

        HTTP.verifyRequested(GET, ADVISORIES_PATH);
        HTTP.verifyQueryParam(GET, ADVISORIES_PATH, "type", "reviewed");
        HTTP.verifyQueryParam(GET, ADVISORIES_PATH, "ecosystem", "npm");
        HTTP.verifyQueryParam(GET, ADVISORIES_PATH, "severity", "high");
        HTTP.verifyQueryParam(GET, ADVISORIES_PATH, "sort", "updated");
        HTTP.verifyQueryParam(GET, ADVISORIES_PATH, "direction", "desc");
    }

    @Test
    void listOrgRepositoryAdvisories_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                ORG_ADVISORIES_PATH,
                okJson(listJsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "repository-advisory.json")));
        final GitHubListRepositoryAdvisoriesFilter filter = GitHubListRepositoryAdvisoriesFilterBuilder.builder()
                .direction(GitHubDirection.ASC)
                .sort(GitHubRepositoryAdvisoriesSort.CREATED)
                .state(GitHubRepositoryAdvisoriesFilterState.DRAFT)
                .build();

        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            advisories.api().repositoryAdvisories().listOrgRepositoryAdvisories(ORG, filter);
        }

        HTTP.verifyRequested(GET, ORG_ADVISORIES_PATH);
        HTTP.verifyQueryParam(GET, ORG_ADVISORIES_PATH, "state", "draft");
        HTTP.verifyQueryParam(GET, ORG_ADVISORIES_PATH, "sort", "created");
        HTTP.verifyQueryParam(GET, ORG_ADVISORIES_PATH, "direction", "asc");
    }

    @Test
    void getRepositoryAdvisory_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REPO_ADVISORY_PATH, notFound());

        final Optional<GitHubRepositoryAdvisory> advisory;
        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            advisory = advisories.api().repositoryAdvisories().getRepositoryAdvisory(OWNER, REPO, REPOSITORY_GHSA_ID);
        }

        assertThat(advisory).isEmpty();
        HTTP.verifyRequested(GET, REPO_ADVISORY_PATH);
    }

    @Test
    void listOrgRepositoryAdvisories_shouldReturnAdvisories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_ADVISORIES_PATH,
                okJson(listJsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "repository-advisory.json")));

        final GitHubPage<GitHubRepositoryAdvisory> page;
        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            page = advisories.api().repositoryAdvisories().listOrgRepositoryAdvisories(ORG);
        }

        final List<GitHubRepositoryAdvisory> expected = CODEC.decode(
                listJsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "repository-advisory.json"),
                REPOSITORY_ADVISORIES_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, ORG_ADVISORIES_PATH);
    }

    @Test
    void listRepositoryAdvisories_shouldReturnAdvisories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_ADVISORIES_PATH,
                okJson(listJsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "repository-advisory.json")));

        final GitHubPage<GitHubRepositoryAdvisory> page;
        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            page = advisories.api().repositoryAdvisories().listRepositoryAdvisories(OWNER, REPO);
        }

        final List<GitHubRepositoryAdvisory> expected = CODEC.decode(
                listJsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "repository-advisory.json"),
                REPOSITORY_ADVISORIES_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, REPO_ADVISORIES_PATH);
    }

    @Test
    void listRepositoryAdvisories_shouldReturnAdvisories_whenStateAndSortProvided() throws Exception {
        HTTP.stub(
                GET,
                REPO_ADVISORIES_PATH,
                okJson(listJsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "repository-advisory.json")));

        final GitHubListRepositoryAdvisoriesFilter filter = GitHubListRepositoryAdvisoriesFilterBuilder.builder()
                .direction(GitHubDirection.DESC)
                .sort(GitHubRepositoryAdvisoriesSort.UPDATED)
                .state(GitHubRepositoryAdvisoriesFilterState.PUBLISHED)
                .build();

        final GitHubPage<GitHubRepositoryAdvisory> page;
        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            page = advisories.api().repositoryAdvisories().listRepositoryAdvisories(OWNER, REPO, filter);
        }

        final List<GitHubRepositoryAdvisory> expected = CODEC.decode(
                listJsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "repository-advisory.json"),
                REPOSITORY_ADVISORIES_TYPE);

        assertThat(page.items()).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, REPO_ADVISORIES_PATH);
        HTTP.verifyQueryParam(GET, REPO_ADVISORIES_PATH, "state", "published");
        HTTP.verifyQueryParam(GET, REPO_ADVISORIES_PATH, "sort", "updated");
        HTTP.verifyQueryParam(GET, REPO_ADVISORIES_PATH, "direction", "desc");
    }

    @Test
    void getRepositoryAdvisory_shouldReturnAdvisory_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_ADVISORY_PATH,
                okJson(jsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "repository-advisory.json")));

        final GitHubRepositoryAdvisory advisory;
        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            advisory = advisories
                    .api()
                    .repositoryAdvisories()
                    .getRepositoryAdvisory(OWNER, REPO, REPOSITORY_GHSA_ID)
                    .orElseThrow();
        }

        final GitHubRepositoryAdvisory expected = CODEC.decode(
                jsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "repository-advisory.json"),
                GitHubRepositoryAdvisory.class);

        assertThat(advisory).isEqualTo(expected);
        HTTP.verifyRequested(GET, REPO_ADVISORY_PATH);
    }

    @Test
    void createRepositoryAdvisory_shouldReturnAdvisory_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                REPO_ADVISORIES_PATH,
                createdJson(jsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "repository-advisory.json")));
        final GitHubCreateRepositoryAdvisoryPayload payload =
                SecurityAdvisoriesCatalogFixtures.createRepositoryAdvisoryPayload();

        final GitHubRepositoryAdvisory advisory;
        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            advisory = advisories.api().repositoryAdvisories().createRepositoryAdvisory(OWNER, REPO, payload);
        }

        final GitHubRepositoryAdvisory expected = CODEC.decode(
                jsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "repository-advisory.json"),
                GitHubRepositoryAdvisory.class);

        assertThat(advisory).isEqualTo(expected);
        HTTP.verifyRequested(POST, REPO_ADVISORIES_PATH);
        HTTP.verifyHeader(POST, REPO_ADVISORIES_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void createPrivateVulnerabilityReport_shouldReturnAdvisory_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                REPO_REPORTS_PATH,
                createdJson(jsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "repository-advisory.json")));
        final GitHubCreatePrivateVulnerabilityReportPayload payload =
                SecurityAdvisoriesCatalogFixtures.createPrivateVulnerabilityReportPayload();

        final GitHubRepositoryAdvisory advisory;
        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            advisory = advisories.api().repositoryAdvisories().createPrivateVulnerabilityReport(OWNER, REPO, payload);
        }

        final GitHubRepositoryAdvisory expected = CODEC.decode(
                jsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "repository-advisory.json"),
                GitHubRepositoryAdvisory.class);

        assertThat(advisory).isEqualTo(expected);
        HTTP.verifyRequested(POST, REPO_REPORTS_PATH);
        HTTP.verifyHeader(POST, REPO_REPORTS_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void updateRepositoryAdvisory_shouldReturnAdvisory_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                REPO_ADVISORY_PATH,
                okJson(jsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "repository-advisory.json")));
        final GitHubUpdateRepositoryAdvisoryPayload payload =
                SecurityAdvisoriesCatalogFixtures.updateRepositoryAdvisoryPayload();

        final GitHubRepositoryAdvisory advisory;
        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            advisory = advisories
                    .api()
                    .repositoryAdvisories()
                    .updateRepositoryAdvisory(OWNER, REPO, REPOSITORY_GHSA_ID, payload);
        }

        final GitHubRepositoryAdvisory expected = CODEC.decode(
                jsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "repository-advisory.json"),
                GitHubRepositoryAdvisory.class);

        assertThat(advisory).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, REPO_ADVISORY_PATH);
        HTTP.verifyHeader(PATCH, REPO_ADVISORY_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void createRepositoryAdvisoryCveRequest_shouldComplete_whenResponseIsAccepted() throws Exception {
        HTTP.stub(POST, REPO_ADVISORY_CVE_PATH, accepted());

        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            advisories.api().repositoryAdvisories().createRepositoryAdvisoryCveRequest(OWNER, REPO, REPOSITORY_GHSA_ID);
        }

        HTTP.verifyRequested(POST, REPO_ADVISORY_CVE_PATH);
    }

    @Test
    void createFork_shouldReturnRepository_whenResponseIsAccepted() throws Exception {
        HTTP.stub(
                POST,
                REPO_ADVISORY_FORKS_PATH,
                acceptedJson(jsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "full-repository.json")));

        final GitHubFullRepository repository;
        try (final GitHubApiWireMockSupport.SecurityAdvisoriesApiClient advisories = securityAdvisoriesApi(HTTP)) {
            repository = advisories.api().repositoryAdvisories().createFork(OWNER, REPO, REPOSITORY_GHSA_ID);
        }

        final GitHubFullRepository expected = CODEC.decode(
                jsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "full-repository.json"),
                GitHubFullRepository.class);

        assertThat(repository).isEqualTo(expected);
        HTTP.verifyRequested(POST, REPO_ADVISORY_FORKS_PATH);
    }

    private static List<GitHubGlobalAdvisory> expectedGlobalAdvisories() {
        return CODEC.decode(
                listJsonBytes(GitHubSecurityAdvisoriesApiIntegrationTest.class, "global-advisory.json"),
                GLOBAL_ADVISORIES_TYPE);
    }
}
