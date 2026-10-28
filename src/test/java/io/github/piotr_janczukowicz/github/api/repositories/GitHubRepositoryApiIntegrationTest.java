package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.acceptedJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.reposRepositoriesApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubSinceRequest;
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
class GitHubRepositoryApiIntegrationTest {

    private static final String ORG = "github";
    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String USERNAME = "octocat";

    private static final String ORG_REPOS_PATH = "/orgs/github/repos";
    private static final String REPO_PATH = "/repos/octocat/Hello-World";
    private static final String CACHE_INFO_PATH = "/repos/octocat/Hello-World/replicas/caches";
    private static final String USER_REPOS_PATH = "/user/repos";
    private static final String USERS_REPOS_PATH = "/users/octocat/repos";
    private static final String PUBLIC_REPOS_PATH = "/repositories";
    private static final String GENERATE_PATH = "/repos/octocat/Hello-World/generate";
    private static final String TRANSFER_PATH = "/repos/octocat/Hello-World/transfer";
    private static final String TOPICS_PATH = "/repos/octocat/Hello-World/topics";
    private static final String LANGUAGES_PATH = "/repos/octocat/Hello-World/languages";
    private static final String DISPATCHES_PATH = "/repos/octocat/Hello-World/dispatches";
    private static final String CONTRIBUTORS_PATH = "/repos/octocat/Hello-World/contributors";
    private static final String TAGS_PATH = "/repos/octocat/Hello-World/tags";
    private static final String TEAMS_PATH = "/repos/octocat/Hello-World/teams";
    private static final String ACTIVITY_PATH = "/repos/octocat/Hello-World/activity";
    private static final String AUTOMATED_SECURITY_FIXES_PATH = "/repos/octocat/Hello-World/automated-security-fixes";
    private static final String VULNERABILITY_ALERTS_PATH = "/repos/octocat/Hello-World/vulnerability-alerts";
    private static final String PRIVATE_VULNERABILITY_REPORTING_PATH =
            "/repos/octocat/Hello-World/private-vulnerability-reporting";
    private static final String IMMUTABLE_RELEASES_PATH = "/repos/octocat/Hello-World/immutable-releases";
    private static final String CODEOWNERS_ERRORS_PATH = "/repos/octocat/Hello-World/codeowners/errors";
    private static final String HASH_ALGORITHM_PATH = "/repos/octocat/Hello-World/hash-algorithm";

    private static final JsonType<List<GitHubMinimalRepository>> MINIMAL_REPOSITORIES_TYPE =
            JsonTypes.listOf(GitHubMinimalRepository.class);
    private static final JsonType<List<GitHubRepository>> REPOSITORIES_TYPE = JsonTypes.listOf(GitHubRepository.class);
    private static final JsonType<List<GitHubContributor>> CONTRIBUTORS_TYPE =
            JsonTypes.listOf(GitHubContributor.class);
    private static final JsonType<List<GitHubRepositoryTag>> TAGS_TYPE = JsonTypes.listOf(GitHubRepositoryTag.class);
    private static final JsonType<List<GitHubTeam>> TEAMS_TYPE = JsonTypes.listOf(GitHubTeam.class);
    private static final JsonType<List<GitHubRepositoryActivity>> ACTIVITIES_TYPE =
            JsonTypes.listOf(GitHubRepositoryActivity.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listForOrg_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_REPOS_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "minimal-repository.json")));

        final GitHubPage<GitHubMinimalRepository> page;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            page = api.api().listForOrg(ORG);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedMinimalRepositories());
        HTTP.verifyRequested(GET, ORG_REPOS_PATH);
        HTTP.verifyQueryParam(GET, ORG_REPOS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, ORG_REPOS_PATH, "per_page", "100");
    }

    @Test
    void listForOrg_shouldSendTypeQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                ORG_REPOS_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "minimal-repository.json")));

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api()
                    .listForOrg(
                            ORG,
                            GitHubListForOrgFilterBuilder.builder()
                                    .type(GitHubOrgReposListType.PUBLIC)
                                    .build(),
                            GitHubPageRequest.byPage());
        }

        HTTP.verifyQueryParam(GET, ORG_REPOS_PATH, "type", "public");
        HTTP.verifyQueryParam(GET, ORG_REPOS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, ORG_REPOS_PATH, "per_page", "100");
    }

    @Test
    void listForOrg_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        stubPagedList(ORG_REPOS_PATH, "minimal-repository.json");

        final GitHubPage<GitHubMinimalRepository> first;
        final Optional<GitHubPage<GitHubMinimalRepository>> second;
        final Optional<GitHubPage<GitHubMinimalRepository>> third;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            first = api.api().listForOrg(ORG);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedMinimalRepositories());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedMinimalRepositories());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, ORG_REPOS_PATH, 2);
    }

    @Test
    void listForOrg_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                ORG_REPOS_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "minimal-repository.json")));

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api().listForOrg(ORG, GitHubListForOrgFilter.empty(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, ORG_REPOS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, ORG_REPOS_PATH, "per_page", "10");
    }

    @Test
    void listForAuthenticatedUser_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_REPOS_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository.json")));

        final GitHubPage<GitHubRepository> page;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            page = api.api().listForAuthenticatedUser();
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedRepositories());
        HTTP.verifyRequested(GET, USER_REPOS_PATH);
        HTTP.verifyQueryParam(GET, USER_REPOS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, USER_REPOS_PATH, "per_page", "100");
    }

    @Test
    void listForAuthenticatedUser_shouldSendTypeQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                USER_REPOS_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository.json")));

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api()
                    .listForAuthenticatedUser(
                            GitHubListForAuthenticatedUserFilterBuilder.builder()
                                    .type(GitHubAuthenticatedUserReposType.OWNER)
                                    .build(),
                            GitHubPageRequest.byPage());
        }

        HTTP.verifyQueryParam(GET, USER_REPOS_PATH, "type", "owner");
        HTTP.verifyQueryParam(GET, USER_REPOS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, USER_REPOS_PATH, "per_page", "100");
    }

    @Test
    void listForAuthenticatedUser_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        stubPagedList(USER_REPOS_PATH, "repository.json");

        final GitHubPage<GitHubRepository> first;
        final Optional<GitHubPage<GitHubRepository>> second;
        final Optional<GitHubPage<GitHubRepository>> third;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            first = api.api().listForAuthenticatedUser();
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedRepositories());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedRepositories());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, USER_REPOS_PATH, 2);
    }

    @Test
    void listForAuthenticatedUser_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                USER_REPOS_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository.json")));

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api()
                    .listForAuthenticatedUser(
                            GitHubListForAuthenticatedUserFilter.empty(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, USER_REPOS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, USER_REPOS_PATH, "per_page", "10");
    }

    @Test
    void listForUser_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USERS_REPOS_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "minimal-repository.json")));

        final GitHubPage<GitHubMinimalRepository> page;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            page = api.api().listForUser(USERNAME);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedMinimalRepositories());
        HTTP.verifyRequested(GET, USERS_REPOS_PATH);
        HTTP.verifyQueryParam(GET, USERS_REPOS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, USERS_REPOS_PATH, "per_page", "100");
    }

    @Test
    void listForUser_shouldSendTypeQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                USERS_REPOS_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "minimal-repository.json")));

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api()
                    .listForUser(
                            USERNAME,
                            GitHubListForUserFilterBuilder.builder()
                                    .type(GitHubUserReposListType.OWNER)
                                    .build(),
                            GitHubPageRequest.byPage());
        }

        HTTP.verifyQueryParam(GET, USERS_REPOS_PATH, "type", "owner");
        HTTP.verifyQueryParam(GET, USERS_REPOS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, USERS_REPOS_PATH, "per_page", "100");
    }

    @Test
    void listPublic_shouldOmitSince_whenGitHubNoneRequest() throws Exception {
        HTTP.stub(
                GET,
                PUBLIC_REPOS_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "minimal-repository.json")));

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api().listPublic(GitHubPageRequest.none());
        }

        HTTP.verifyRequested(GET, PUBLIC_REPOS_PATH);
        HTTP.verifyNoQueryParam(GET, PUBLIC_REPOS_PATH, "since");
        HTTP.verifyNoQueryParam(GET, PUBLIC_REPOS_PATH, "per_page");
    }

    @Test
    void listPublic_shouldSendSinceQuery_whenGitHubSinceRequest() throws Exception {
        HTTP.stub(
                GET,
                PUBLIC_REPOS_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "minimal-repository.json")));

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api().listPublic(GitHubSinceRequest.of(42L));
        }

        HTTP.verifyQueryParam(GET, PUBLIC_REPOS_PATH, "since", "42");
        HTTP.verifyNoQueryParam(GET, PUBLIC_REPOS_PATH, "per_page");
    }

    @Test
    void listPublic_shouldFollowSinceNext_whenNextPageRequested() throws Exception {
        HTTP.stub(
                GET,
                PUBLIC_REPOS_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "minimal-repository.json"))
                        .header(LINK, linkNext(HTTP.uri(PUBLIC_REPOS_PATH + "?since=1296269"))),
                okJson("[]".getBytes(StandardCharsets.UTF_8)));

        final GitHubPage<GitHubMinimalRepository> first;
        final Optional<GitHubPage<GitHubMinimalRepository>> second;
        final Optional<GitHubPage<GitHubMinimalRepository>> third;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            first = api.api().listPublic(GitHubSinceRequest.of(0L));
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedMinimalRepositories());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).isEmpty();
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, PUBLIC_REPOS_PATH, 2);
        HTTP.verifyQueryParam(GET, PUBLIC_REPOS_PATH, "since", "0");
        HTTP.verifyQueryParam(GET, PUBLIC_REPOS_PATH, "since", "1296269");
        HTTP.verifyNoQueryParam(GET, PUBLIC_REPOS_PATH, "per_page");
    }

    @Test
    void createInOrg_shouldReturnRepository_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                ORG_REPOS_PATH,
                createdJson(jsonBytes(GitHubRepositoryApiIntegrationTest.class, "full-repository.json")));
        final GitHubCreateInOrgPayload payload = RepositoriesCatalogFixtures.createOrgRepositoryPayload();

        final GitHubFullRepository repository;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            repository = api.api().createInOrg(ORG, payload);
        }

        assertThat(repository).isEqualTo(expectedFullRepository());
        HTTP.verifyRequested(POST, ORG_REPOS_PATH);
        HTTP.verifyHeader(POST, ORG_REPOS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, ORG_REPOS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void get_shouldReturnRepository_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, REPO_PATH, okJson(jsonBytes(GitHubRepositoryApiIntegrationTest.class, "full-repository.json")));

        final Optional<GitHubFullRepository> repository;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            repository = api.api().get(OWNER, REPO);
        }

        assertThat(repository).contains(expectedFullRepository());
        HTTP.verifyRequested(GET, REPO_PATH);
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REPO_PATH, notFound());

        final Optional<GitHubFullRepository> repository;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            repository = api.api().get(OWNER, REPO);
        }

        assertThat(repository).isEmpty();
        HTTP.verifyRequested(GET, REPO_PATH);
    }

    @Test
    void update_shouldReturnRepository_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH, REPO_PATH, okJson(jsonBytes(GitHubRepositoryApiIntegrationTest.class, "full-repository.json")));
        final GitHubUpdatePayload payload = RepositoriesCatalogFixtures.updateRepositoryPayload();

        final GitHubFullRepository repository;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            repository = api.api().update(OWNER, REPO, payload);
        }

        assertThat(repository).isEqualTo(expectedFullRepository());
        HTTP.verifyRequested(PATCH, REPO_PATH);
        HTTP.verifyHeader(PATCH, REPO_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, REPO_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void delete_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, REPO_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api().delete(OWNER, REPO);
        }

        HTTP.verifyRequested(DELETE, REPO_PATH);
    }

    @Test
    void createForAuthenticatedUser_shouldReturnRepository_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                USER_REPOS_PATH,
                createdJson(jsonBytes(GitHubRepositoryApiIntegrationTest.class, "full-repository.json")));
        final GitHubCreateForAuthenticatedUserPayload payload =
                RepositoriesCatalogFixtures.createAuthenticatedUserRepositoryPayload();

        final GitHubFullRepository repository;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            repository = api.api().createForAuthenticatedUser(payload);
        }

        assertThat(repository).isEqualTo(expectedFullRepository());
        HTTP.verifyRequested(POST, USER_REPOS_PATH);
        HTTP.verifyHeader(POST, USER_REPOS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, USER_REPOS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void createUsingTemplate_shouldReturnRepository_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                GENERATE_PATH,
                createdJson(jsonBytes(GitHubRepositoryApiIntegrationTest.class, "full-repository.json")));
        final GitHubCreateUsingTemplatePayload payload =
                RepositoriesCatalogFixtures.createRepositoryFromTemplatePayload();

        final GitHubFullRepository repository;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            repository = api.api().createUsingTemplate(OWNER, REPO, payload);
        }

        assertThat(repository).isEqualTo(expectedFullRepository());
        HTTP.verifyRequested(POST, GENERATE_PATH);
        HTTP.verifyHeader(POST, GENERATE_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, GENERATE_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void transfer_shouldReturnRepository_whenResponseIsAccepted() throws Exception {
        HTTP.stub(
                POST,
                TRANSFER_PATH,
                acceptedJson(jsonBytes(GitHubRepositoryApiIntegrationTest.class, "minimal-repository.json")));
        final GitHubTransferPayload payload = RepositoriesCatalogFixtures.transferRepositoryPayload();

        final GitHubMinimalRepository repository;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            repository = api.api().transfer(OWNER, REPO, payload);
        }

        assertThat(repository).isEqualTo(expectedMinimalRepository());
        HTTP.verifyRequested(POST, TRANSFER_PATH);
        HTTP.verifyHeader(POST, TRANSFER_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, TRANSFER_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getAllTopics_shouldReturnTopics_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                TOPICS_PATH,
                okJson(jsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository-topics.json")));

        final GitHubRepositoryTopics topics;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            topics = api.api().getAllTopics(OWNER, REPO);
        }

        assertThat(topics).isEqualTo(expectedTopics());
        HTTP.verifyRequested(GET, TOPICS_PATH);
    }

    @Test
    void replaceAllTopics_shouldReturnTopics_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                TOPICS_PATH,
                okJson(jsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository-topics.json")));
        final GitHubReplaceAllTopicsPayload payload = RepositoriesCatalogFixtures.replaceAllTopicsPayload();

        final GitHubRepositoryTopics topics;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            topics = api.api().replaceAllTopics(OWNER, REPO, payload);
        }

        assertThat(topics).isEqualTo(expectedTopics());
        HTTP.verifyRequested(PUT, TOPICS_PATH);
        HTTP.verifyHeader(PUT, TOPICS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, TOPICS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void listLanguages_shouldReturnLanguages_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, LANGUAGES_PATH, okJson(jsonBytes(GitHubRepositoryApiIntegrationTest.class, "languages.json")));

        final GitHubRepositoryLanguages languages;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            languages = api.api().listLanguages(OWNER, REPO);
        }

        assertThat(languages).isEqualTo(expectedLanguages());
        HTTP.verifyRequested(GET, LANGUAGES_PATH);
    }

    @Test
    void createDispatchEvent_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(POST, DISPATCHES_PATH, noContent());
        final GitHubCreateDispatchEventPayload payload = RepositoriesCatalogFixtures.createDispatchEventPayload();

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api().createDispatchEvent(OWNER, REPO, payload);
        }

        HTTP.verifyRequested(POST, DISPATCHES_PATH);
        HTTP.verifyHeader(POST, DISPATCHES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, DISPATCHES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void listContributors_shouldReturnContributors_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                CONTRIBUTORS_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "contributor.json")));

        final GitHubPage<GitHubContributor> page;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            page = api.api().listContributors(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedContributors());
        HTTP.verifyRequested(GET, CONTRIBUTORS_PATH);
        HTTP.verifyQueryParam(GET, CONTRIBUTORS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, CONTRIBUTORS_PATH, "per_page", "100");
    }

    @Test
    void listContributors_shouldSendAnonQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                CONTRIBUTORS_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "contributor.json")));

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api()
                    .listContributors(
                            OWNER,
                            REPO,
                            GitHubListContributorsFilterBuilder.builder()
                                    .anon(true)
                                    .build(),
                            GitHubPageRequest.byPage());
        }

        HTTP.verifyQueryParam(GET, CONTRIBUTORS_PATH, "anon", "1");
        HTTP.verifyQueryParam(GET, CONTRIBUTORS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, CONTRIBUTORS_PATH, "per_page", "100");
    }

    @Test
    void listContributors_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        stubPagedList(CONTRIBUTORS_PATH, "contributor.json");

        final GitHubPage<GitHubContributor> first;
        final Optional<GitHubPage<GitHubContributor>> second;
        final Optional<GitHubPage<GitHubContributor>> third;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            first = api.api().listContributors(OWNER, REPO);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedContributors());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedContributors());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, CONTRIBUTORS_PATH, 2);
    }

    @Test
    void listContributors_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                CONTRIBUTORS_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "contributor.json")));

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api()
                    .listContributors(
                            OWNER, REPO, GitHubListContributorsFilter.empty(), GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, CONTRIBUTORS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, CONTRIBUTORS_PATH, "per_page", "10");
    }

    @Test
    void listTags_shouldReturnTags_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, TAGS_PATH, okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository-tag.json")));

        final GitHubPage<GitHubRepositoryTag> page;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            page = api.api().listTags(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedTags());
        HTTP.verifyRequested(GET, TAGS_PATH);
        HTTP.verifyQueryParam(GET, TAGS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, TAGS_PATH, "per_page", "100");
    }

    @Test
    void listTags_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET, TAGS_PATH, okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository-tag.json")));

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api().listTags(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, TAGS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, TAGS_PATH, "per_page", "10");
    }

    @Test
    void listTeams_shouldReturnTeams_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, TEAMS_PATH, okJson(listJsonBytes(GitHubTeam.class, "team.json")));

        final GitHubPage<GitHubTeam> page;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            page = api.api().listTeams(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedTeams());
        HTTP.verifyRequested(GET, TEAMS_PATH);
        HTTP.verifyQueryParam(GET, TEAMS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, TEAMS_PATH, "per_page", "100");
    }

    @Test
    void listTeams_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(GET, TEAMS_PATH, okJson(listJsonBytes(GitHubTeam.class, "team.json")));

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api().listTeams(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, TEAMS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, TEAMS_PATH, "per_page", "10");
    }

    @Test
    void listActivities_shouldReturnActivities_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ACTIVITY_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository-activity.json")));

        final GitHubPage<GitHubRepositoryActivity> page;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            page = api.api().listActivities(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedActivities());
        HTTP.verifyRequested(GET, ACTIVITY_PATH);
        HTTP.verifyQueryParam(GET, ACTIVITY_PATH, "per_page", "100");
    }

    @Test
    void listActivities_shouldSendActivityTypeQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                ACTIVITY_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository-activity.json")));

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api()
                    .listActivities(
                            OWNER,
                            REPO,
                            GitHubListActivitiesFilterBuilder.builder()
                                    .activityType(GitHubRepositoryActivityType.PUSH)
                                    .build(),
                            GitHubPageRequest.byCursor());
        }

        HTTP.verifyQueryParam(GET, ACTIVITY_PATH, "activity_type", "push");
        HTTP.verifyQueryParam(GET, ACTIVITY_PATH, "per_page", "100");
    }

    @Test
    void listActivities_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                ACTIVITY_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository-activity.json"))
                        .header(
                                LINK,
                                linkNext(HTTP.uri(ACTIVITY_PATH + "?after=cursor&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository-activity.json")));

        final GitHubPage<GitHubRepositoryActivity> first;
        final Optional<GitHubPage<GitHubRepositoryActivity>> second;
        final Optional<GitHubPage<GitHubRepositoryActivity>> third;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            first = api.api().listActivities(OWNER, REPO);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedActivities());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedActivities());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, ACTIVITY_PATH, 2);
    }

    @Test
    void checkAutomatedSecurityFixes_shouldReturnStatus_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                AUTOMATED_SECURITY_FIXES_PATH,
                okJson(jsonBytes(GitHubRepositoryApiIntegrationTest.class, "automated-security-fixes.json")));

        final GitHubAutomatedSecurityFixesStatus status;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            status = api.api().checkAutomatedSecurityFixes(OWNER, REPO);
        }

        assertThat(status).isEqualTo(expectedAutomatedSecurityFixesStatus());
        HTTP.verifyRequested(GET, AUTOMATED_SECURITY_FIXES_PATH);
    }

    @Test
    void checkAutomatedSecurityFixes_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, AUTOMATED_SECURITY_FIXES_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            thrown = catchThrowable(() -> api.api().checkAutomatedSecurityFixes(OWNER, REPO));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, AUTOMATED_SECURITY_FIXES_PATH);
    }

    @Test
    void enableAutomatedSecurityFixes_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, AUTOMATED_SECURITY_FIXES_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api().enableAutomatedSecurityFixes(OWNER, REPO);
        }

        HTTP.verifyRequested(PUT, AUTOMATED_SECURITY_FIXES_PATH);
    }

    @Test
    void disableAutomatedSecurityFixes_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, AUTOMATED_SECURITY_FIXES_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api().disableAutomatedSecurityFixes(OWNER, REPO);
        }

        HTTP.verifyRequested(DELETE, AUTOMATED_SECURITY_FIXES_PATH);
    }

    @Test
    void checkVulnerabilityAlerts_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        HTTP.stub(GET, VULNERABILITY_ALERTS_PATH, noContent());

        final boolean enabled;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            enabled = api.api().checkVulnerabilityAlerts(OWNER, REPO);
        }

        assertThat(enabled).isTrue();
        HTTP.verifyRequested(GET, VULNERABILITY_ALERTS_PATH);
    }

    @Test
    void checkVulnerabilityAlerts_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, VULNERABILITY_ALERTS_PATH, notFound());

        final boolean enabled;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            enabled = api.api().checkVulnerabilityAlerts(OWNER, REPO);
        }

        assertThat(enabled).isFalse();
        HTTP.verifyRequested(GET, VULNERABILITY_ALERTS_PATH);
    }

    @Test
    void enableVulnerabilityAlerts_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, VULNERABILITY_ALERTS_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api().enableVulnerabilityAlerts(OWNER, REPO);
        }

        HTTP.verifyRequested(PUT, VULNERABILITY_ALERTS_PATH);
    }

    @Test
    void disableVulnerabilityAlerts_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, VULNERABILITY_ALERTS_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api().disableVulnerabilityAlerts(OWNER, REPO);
        }

        HTTP.verifyRequested(DELETE, VULNERABILITY_ALERTS_PATH);
    }

    @Test
    void checkPrivateVulnerabilityReporting_shouldReturnStatus_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PRIVATE_VULNERABILITY_REPORTING_PATH,
                okJson(jsonBytes(GitHubRepositoryApiIntegrationTest.class, "private-vulnerability-reporting.json")));

        final GitHubPrivateVulnerabilityReportingStatus status;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            status = api.api().checkPrivateVulnerabilityReporting(OWNER, REPO);
        }

        assertThat(status).isEqualTo(expectedPrivateVulnerabilityReportingStatus());
        HTTP.verifyRequested(GET, PRIVATE_VULNERABILITY_REPORTING_PATH);
    }

    @Test
    void enablePrivateVulnerabilityReporting_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, PRIVATE_VULNERABILITY_REPORTING_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api().enablePrivateVulnerabilityReporting(OWNER, REPO);
        }

        HTTP.verifyRequested(PUT, PRIVATE_VULNERABILITY_REPORTING_PATH);
    }

    @Test
    void disablePrivateVulnerabilityReporting_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, PRIVATE_VULNERABILITY_REPORTING_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api().disablePrivateVulnerabilityReporting(OWNER, REPO);
        }

        HTTP.verifyRequested(DELETE, PRIVATE_VULNERABILITY_REPORTING_PATH);
    }

    @Test
    void checkImmutableReleases_shouldReturnStatus_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                IMMUTABLE_RELEASES_PATH,
                okJson(jsonBytes(GitHubRepositoryApiIntegrationTest.class, "immutable-releases.json")));

        final GitHubImmutableReleasesStatus status;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            status = api.api().checkImmutableReleases(OWNER, REPO);
        }

        assertThat(status).isEqualTo(expectedImmutableReleasesStatus());
        HTTP.verifyRequested(GET, IMMUTABLE_RELEASES_PATH);
    }

    @Test
    void checkImmutableReleases_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, IMMUTABLE_RELEASES_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            thrown = catchThrowable(() -> api.api().checkImmutableReleases(OWNER, REPO));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, IMMUTABLE_RELEASES_PATH);
    }

    @Test
    void enableImmutableReleases_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, IMMUTABLE_RELEASES_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api().enableImmutableReleases(OWNER, REPO);
        }

        HTTP.verifyRequested(PUT, IMMUTABLE_RELEASES_PATH);
    }

    @Test
    void disableImmutableReleases_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, IMMUTABLE_RELEASES_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            api.api().disableImmutableReleases(OWNER, REPO);
        }

        HTTP.verifyRequested(DELETE, IMMUTABLE_RELEASES_PATH);
    }

    @Test
    void codeownersErrors_shouldReturnErrors_whenRefProvided() throws Exception {
        HTTP.stub(
                GET,
                CODEOWNERS_ERRORS_PATH,
                okJson(jsonBytes(GitHubRepositoryApiIntegrationTest.class, "codeowners-errors.json")));

        final Optional<GitHubCodeownersErrors> errors;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            errors = api.api()
                    .codeownersErrors(
                            OWNER,
                            REPO,
                            GitHubCodeownersErrorsFilterBuilder.builder()
                                    .ref("main")
                                    .build());
        }

        assertThat(errors).contains(expectedCodeownersErrors());
        HTTP.verifyRequested(GET, CODEOWNERS_ERRORS_PATH);
        HTTP.verifyQueryParam(GET, CODEOWNERS_ERRORS_PATH, "ref", "main");
    }

    @Test
    void codeownersErrors_shouldReturnErrors_whenRefOmitted() throws Exception {
        HTTP.stub(
                GET,
                CODEOWNERS_ERRORS_PATH,
                okJson(jsonBytes(GitHubRepositoryApiIntegrationTest.class, "codeowners-errors.json")));

        final Optional<GitHubCodeownersErrors> errors;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            errors = api.api().codeownersErrors(OWNER, REPO);
        }

        assertThat(errors).contains(expectedCodeownersErrors());
        HTTP.verifyRequested(GET, CODEOWNERS_ERRORS_PATH);
        HTTP.verifyNoQueryParam(GET, CODEOWNERS_ERRORS_PATH, "ref");
    }

    @Test
    void codeownersErrors_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, CODEOWNERS_ERRORS_PATH, notFound());

        final Optional<GitHubCodeownersErrors> errors;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            errors = api.api()
                    .codeownersErrors(
                            OWNER,
                            REPO,
                            GitHubCodeownersErrorsFilterBuilder.builder()
                                    .ref("main")
                                    .build());
        }

        assertThat(errors).isEmpty();
        HTTP.verifyRequested(GET, CODEOWNERS_ERRORS_PATH);
    }

    @Test
    void getHashAlgorithm_shouldReturnAlgorithm_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                HASH_ALGORITHM_PATH,
                okJson(jsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository-hash-algorithm.json")));

        final GitHubRepositoryHashAlgorithmResponse response;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            response = api.api().getHashAlgorithm(OWNER, REPO);
        }

        assertThat(response).isEqualTo(expectedHashAlgorithm());
        HTTP.verifyRequested(GET, HASH_ALGORITHM_PATH);
    }

    @Test
    void getHashAlgorithm_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, HASH_ALGORITHM_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            thrown = catchThrowable(() -> api.api().getHashAlgorithm(OWNER, REPO));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
        HTTP.verifyRequested(GET, HASH_ALGORITHM_PATH);
    }

    private void stubPagedList(final String path, final String fixture) {
        HTTP.stub(
                GET,
                path,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, fixture))
                        .header(LINK, linkNext(HTTP.uri(path + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, fixture)));
    }

    private static List<GitHubMinimalRepository> expectedMinimalRepositories() {
        return CODEC.decode(
                listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "minimal-repository.json"),
                MINIMAL_REPOSITORIES_TYPE);
    }

    private static GitHubMinimalRepository expectedMinimalRepository() {
        return CODEC.decode(
                jsonBytes(GitHubRepositoryApiIntegrationTest.class, "minimal-repository.json"),
                GitHubMinimalRepository.class);
    }

    private static List<GitHubRepository> expectedRepositories() {
        return CODEC.decode(
                listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository.json"), REPOSITORIES_TYPE);
    }

    private static GitHubFullRepository expectedFullRepository() {
        return CODEC.decode(
                jsonBytes(GitHubRepositoryApiIntegrationTest.class, "full-repository.json"),
                GitHubFullRepository.class);
    }

    private static GitHubRepositoryTopics expectedTopics() {
        return CODEC.decode(
                jsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository-topics.json"),
                GitHubRepositoryTopics.class);
    }

    private static GitHubRepositoryLanguages expectedLanguages() {
        return CODEC.decode(
                jsonBytes(GitHubRepositoryApiIntegrationTest.class, "languages.json"), GitHubRepositoryLanguages.class);
    }

    private static List<GitHubContributor> expectedContributors() {
        return CODEC.decode(
                listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "contributor.json"), CONTRIBUTORS_TYPE);
    }

    private static List<GitHubRepositoryTag> expectedTags() {
        return CODEC.decode(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository-tag.json"), TAGS_TYPE);
    }

    private static List<GitHubTeam> expectedTeams() {
        return CODEC.decode(listJsonBytes(GitHubTeam.class, "team.json"), TEAMS_TYPE);
    }

    private static List<GitHubRepositoryActivity> expectedActivities() {
        return CODEC.decode(
                listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository-activity.json"), ACTIVITIES_TYPE);
    }

    private static GitHubAutomatedSecurityFixesStatus expectedAutomatedSecurityFixesStatus() {
        return CODEC.decode(
                jsonBytes(GitHubRepositoryApiIntegrationTest.class, "automated-security-fixes.json"),
                GitHubAutomatedSecurityFixesStatus.class);
    }

    private static GitHubPrivateVulnerabilityReportingStatus expectedPrivateVulnerabilityReportingStatus() {
        return CODEC.decode(
                jsonBytes(GitHubRepositoryApiIntegrationTest.class, "private-vulnerability-reporting.json"),
                GitHubPrivateVulnerabilityReportingStatus.class);
    }

    private static GitHubImmutableReleasesStatus expectedImmutableReleasesStatus() {
        return CODEC.decode(
                jsonBytes(GitHubRepositoryApiIntegrationTest.class, "immutable-releases.json"),
                GitHubImmutableReleasesStatus.class);
    }

    private static GitHubCodeownersErrors expectedCodeownersErrors() {
        return CODEC.decode(
                jsonBytes(GitHubRepositoryApiIntegrationTest.class, "codeowners-errors.json"),
                GitHubCodeownersErrors.class);
    }

    private static GitHubRepositoryHashAlgorithmResponse expectedHashAlgorithm() {
        return CODEC.decode(
                jsonBytes(GitHubRepositoryApiIntegrationTest.class, "repository-hash-algorithm.json"),
                GitHubRepositoryHashAlgorithmResponse.class);
    }

    @Test
    void listCacheInfo_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                CACHE_INFO_PATH,
                okJson(listJsonBytes(GitHubRepositoryApiIntegrationTest.class, "list-cache-info-item.json")));

        final GitHubPage<GitHubListCacheInfoItem> page;
        try (final GitHubApiWireMockSupport.ReposRepositoriesApiClient api = reposRepositoriesApi(HTTP)) {
            page = api.api().listCacheInfo(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        }

        assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.listCacheInfoItem());
        HTTP.verifyQueryParam(GET, CACHE_INFO_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, CACHE_INFO_PATH, "per_page", "10");
    }
}
