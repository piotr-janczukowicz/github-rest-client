package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.acceptedJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.reposForksApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
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
class GitHubRepositoriesForksApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String FORKS_PATH = "/repos/octocat/Hello-World/forks";

    private static final JsonType<List<GitHubMinimalRepository>> MINIMAL_REPOSITORIES_TYPE =
            JsonTypes.listOf(GitHubMinimalRepository.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listForks_shouldReturnForks_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                FORKS_PATH,
                okJson(listJsonBytes(GitHubRepositoriesForksApiIntegrationTest.class, "minimal-repository.json")));

        final GitHubPage<GitHubMinimalRepository> page;
        try (final GitHubApiWireMockSupport.ReposForksApiClient api = reposForksApi(HTTP)) {
            page = api.api().listForks(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedMinimalRepositories());
        HTTP.verifyRequested(GET, FORKS_PATH);
        HTTP.verifyQueryParam(GET, FORKS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, FORKS_PATH, "per_page", "100");
    }

    @Test
    void listForks_shouldSendSortQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                FORKS_PATH,
                okJson(listJsonBytes(GitHubRepositoriesForksApiIntegrationTest.class, "minimal-repository.json")));

        try (final GitHubApiWireMockSupport.ReposForksApiClient api = reposForksApi(HTTP)) {
            api.api()
                    .listForks(
                            OWNER,
                            REPO,
                            GitHubListForksFilterBuilder.builder()
                                    .sort(GitHubRepoForksSort.NEWEST)
                                    .build(),
                            GitHubPageRequest.byPage());
        }

        HTTP.verifyQueryParam(GET, FORKS_PATH, "sort", "newest");
        HTTP.verifyQueryParam(GET, FORKS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, FORKS_PATH, "per_page", "100");
    }

    @Test
    void listForks_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        stubPagedList(FORKS_PATH, "minimal-repository.json");

        final GitHubPage<GitHubMinimalRepository> first;
        final Optional<GitHubPage<GitHubMinimalRepository>> second;
        final Optional<GitHubPage<GitHubMinimalRepository>> third;
        try (final GitHubApiWireMockSupport.ReposForksApiClient api = reposForksApi(HTTP)) {
            first = api.api().listForks(OWNER, REPO);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedMinimalRepositories());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedMinimalRepositories());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, FORKS_PATH, 2);
    }

    @Test
    void listForks_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                FORKS_PATH,
                okJson(listJsonBytes(GitHubRepositoriesForksApiIntegrationTest.class, "minimal-repository.json")));

        try (final GitHubApiWireMockSupport.ReposForksApiClient api = reposForksApi(HTTP)) {
            api.api().listForks(OWNER, REPO, GitHubListForksFilter.empty(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, FORKS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, FORKS_PATH, "per_page", "10");
    }

    @Test
    void createFork_shouldReturnRepository_whenResponseIsAccepted() throws Exception {
        HTTP.stub(
                POST,
                FORKS_PATH,
                acceptedJson(jsonBytes(GitHubRepositoriesForksApiIntegrationTest.class, "full-repository.json")));
        final GitHubCreateForkPayload payload = RepositoriesCatalogFixtures.createForkPayload();

        final GitHubFullRepository repository;
        try (final GitHubApiWireMockSupport.ReposForksApiClient api = reposForksApi(HTTP)) {
            repository = api.api().createFork(OWNER, REPO, payload);
        }

        assertThat(repository).isEqualTo(expectedFullRepository());
        HTTP.verifyRequested(POST, FORKS_PATH);
        HTTP.verifyHeader(POST, FORKS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, FORKS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void createFork_shouldReturnRepository_whenPayloadNull() throws Exception {
        HTTP.stub(
                POST,
                FORKS_PATH,
                acceptedJson(jsonBytes(GitHubRepositoriesForksApiIntegrationTest.class, "full-repository.json")));

        final GitHubFullRepository repository;
        try (final GitHubApiWireMockSupport.ReposForksApiClient api = reposForksApi(HTTP)) {
            repository = api.api().createFork(OWNER, REPO, null);
        }

        assertThat(repository).isEqualTo(expectedFullRepository());
        HTTP.verifyRequested(POST, FORKS_PATH);
    }

    private void stubPagedList(final String path, final String fixture) {
        HTTP.stub(
                GET,
                path,
                okJson(listJsonBytes(GitHubRepositoriesForksApiIntegrationTest.class, fixture))
                        .header(LINK, linkNext(HTTP.uri(path + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubRepositoriesForksApiIntegrationTest.class, fixture)));
    }

    private static List<GitHubMinimalRepository> expectedMinimalRepositories() {
        return CODEC.decode(
                listJsonBytes(GitHubRepositoriesForksApiIntegrationTest.class, "minimal-repository.json"),
                MINIMAL_REPOSITORIES_TYPE);
    }

    private static GitHubFullRepository expectedFullRepository() {
        return CODEC.decode(
                jsonBytes(GitHubRepositoriesForksApiIntegrationTest.class, "full-repository.json"),
                GitHubFullRepository.class);
    }
}
