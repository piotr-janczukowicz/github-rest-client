package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.teams.TeamCatalogFixtures;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubSinceRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubRepositoryApiTest {

    private static final String ORG = "github";
    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String USERNAME = "octocat";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listForOrg_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("minimal-repository.json") + "]"));

        final GitHubPage<GitHubMinimalRepository> page = api().listForOrg(
                        ORG,
                        GitHubListForOrgFilterBuilder.builder()
                                .type(GitHubOrgReposListType.PUBLIC)
                                .build(),
                        GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.helloWorldMinimalRepository());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri).hasParameter("type", "public");
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/github/repos?type=public&page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void createInOrg_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("full-repository.json")));
        final GitHubCreateInOrgPayload payload = RepositoriesCatalogFixtures.createOrgRepositoryPayload();

        final GitHubFullRepository repository = api().createInOrg(ORG, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(repository).isEqualTo(RepositoriesCatalogFixtures.fullRepository());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/repos");
        });
    }

    @Test
    void get_shouldReturnRepository_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("full-repository.json")));

        final Optional<GitHubFullRepository> repository = api().get(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(repository).contains(RepositoriesCatalogFixtures.fullRepository());
            softly.assertThat(requestUri).hasToString("https://api.github.com/repos/octocat/Hello-World");
        });
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubFullRepository> repository = api().get(OWNER, REPO);

        assertThat(repository).isEmpty();
    }

    @Test
    void update_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("full-repository.json")));
        final GitHubUpdatePayload payload = RepositoriesCatalogFixtures.updateRepositoryPayload();

        api().update(OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World");
        });
    }

    @Test
    void delete_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().delete(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World");
        });
    }

    @Test
    void listForAuthenticatedUser_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("repository.json") + "]"));

        final GitHubPage<GitHubRepository> page = api().listForAuthenticatedUser();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.teamEnvironmentRepository());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/user/repos?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void createForAuthenticatedUser_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("full-repository.json")));
        final GitHubCreateForAuthenticatedUserPayload payload =
                RepositoriesCatalogFixtures.createAuthenticatedUserRepositoryPayload();

        api().createForAuthenticatedUser(payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/user/repos");
        });
    }

    @Test
    void listForUser_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("minimal-repository.json") + "]"));

        final GitHubPage<GitHubMinimalRepository> page = api().listForUser(USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.helloWorldMinimalRepository());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/users/octocat/repos?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listPublic_shouldSendSinceQuery_whenGitHubSinceRequest() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("minimal-repository.json") + "]"));

        api().listPublic(GitHubSinceRequest.of(42L));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("since", "42").hasNoParameter("per_page");
    }

    @Test
    void listPublic_shouldSendVisibility_whenFilterSet() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("minimal-repository.json") + "]"));

        api().listPublic(new GitHubListPublicFilter(GitHubPublicReposVisibility.ALL), GitHubSinceRequest.of(42L));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasParameter("visibility", "all")
                .hasParameter("since", "42")
                .hasNoParameter("per_page");
    }

    @Test
    void listPublic_shouldThrow_whenFilterNull() {
        final GitHubRepositoryApi api = api();
        final GitHubPageRequest request = GitHubPageRequest.none();

        final Throwable thrown = catchThrowable(() -> api.listPublic(null, request));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void listPublic_shouldThrow_whenExplicitPerPage() {
        final Throwable thrown = catchThrowable(() -> api().listPublic(GitHubSinceRequest.of(1L, 500)));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("unsupported pagination request: explicit per_page")
                .hasMessageContaining("allowed: none, since");
    }

    @Test
    void createUsingTemplate_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("full-repository.json")));
        final GitHubCreateUsingTemplatePayload payload =
                RepositoriesCatalogFixtures.createRepositoryFromTemplatePayload();

        api().createUsingTemplate(OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/generate");
    }

    @Test
    void transfer_shouldPostPayload_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("minimal-repository.json")));
        final GitHubTransferPayload payload = RepositoriesCatalogFixtures.transferRepositoryPayload();

        api().transfer(OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/transfer");
    }

    @Test
    void getAllTopics_shouldReturnTopics_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repository-topics.json")));

        final GitHubRepositoryTopics topics = api().getAllTopics(OWNER, REPO);

        assertThat(topics).isEqualTo(RepositoriesCatalogFixtures.repositoryTopics());
    }

    @Test
    void getAllTopics_shouldSendPageQuery_whenCustomGitHubPageNumberRequest() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repository-topics.json")));

        api().getAllTopics(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "10");
    }

    @Test
    void replaceAllTopics_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repository-topics.json")));
        final GitHubReplaceAllTopicsPayload payload = RepositoriesCatalogFixtures.replaceAllTopicsPayload();

        api().replaceAllTopics(OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void listLanguages_shouldReturnLanguages_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("languages.json")));

        final GitHubRepositoryLanguages languages = api().listLanguages(OWNER, REPO);

        assertThat(languages).isEqualTo(RepositoriesCatalogFixtures.repositoryLanguages());
    }

    @Test
    void createDispatchEvent_shouldPostPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubCreateDispatchEventPayload payload = RepositoriesCatalogFixtures.createDispatchEventPayload();

        api().createDispatchEvent(OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/dispatches");
    }

    @Test
    void listContributors_shouldReturnContributors_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("contributor.json") + "]"));

        final GitHubPage<GitHubContributor> page = api().listContributors(OWNER, REPO);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.contributorOctocat());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listContributors_shouldReturnEmptyPage_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final GitHubPage<GitHubContributor> page = api().listContributors(OWNER, REPO);

        final Optional<GitHubPage<GitHubContributor>> next = page.next();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).isEmpty();
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(next).isEmpty();
        });
    }

    @Test
    void listTags_shouldReturnTags_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-tag.json") + "]"));

        final GitHubPage<GitHubRepositoryTag> page = api().listTags(OWNER, REPO);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.repositoryTag());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listTeams_shouldReturnTeams_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("team.json", GitHubTeam.class) + "]"));

        final GitHubPage<GitHubTeam> page = api().listTeams(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(TeamCatalogFixtures.team());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri.getPath()).endsWith("/teams");
        });
    }

    @Test
    void listActivities_shouldUseCursorPagination_whenCalled() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-activity.json") + "]"));

        api().listActivities(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains(TestClients.perPageQuery());
    }

    @Test
    void checkAutomatedSecurityFixes_shouldReturnStatus_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("automated-security-fixes.json")));

        final GitHubAutomatedSecurityFixesStatus status = api().checkAutomatedSecurityFixes(OWNER, REPO);

        assertThat(status).isEqualTo(RepositoriesCatalogFixtures.automatedSecurityFixesStatus());
    }

    @Test
    void checkAutomatedSecurityFixes_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().checkAutomatedSecurityFixes(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void enableAutomatedSecurityFixes_shouldSendPut_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().enableAutomatedSecurityFixes(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void disableAutomatedSecurityFixes_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().disableAutomatedSecurityFixes(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void checkVulnerabilityAlerts_shouldReturnTrue_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final boolean enabled = api().checkVulnerabilityAlerts(OWNER, REPO);

        assertThat(enabled).isTrue();
    }

    @Test
    void checkVulnerabilityAlerts_shouldReturnFalse_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final boolean enabled = api().checkVulnerabilityAlerts(OWNER, REPO);

        assertThat(enabled).isFalse();
    }

    @Test
    void checkPrivateVulnerabilityReporting_shouldReturnStatus_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("private-vulnerability-reporting.json")));

        final GitHubPrivateVulnerabilityReportingStatus status = api().checkPrivateVulnerabilityReporting(OWNER, REPO);

        assertThat(status).isEqualTo(RepositoriesCatalogFixtures.privateVulnerabilityReportingEnabled());
    }

    @Test
    void checkImmutableReleases_shouldReturnStatus_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("immutable-releases.json")));

        final GitHubImmutableReleasesStatus status = api().checkImmutableReleases(OWNER, REPO);

        assertThat(status).isEqualTo(RepositoriesCatalogFixtures.immutableReleasesStatus());
    }

    @Test
    void codeownersErrors_shouldReturnErrors_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codeowners-errors.json")));

        final Optional<GitHubCodeownersErrors> errors = api().codeownersErrors(
                        OWNER,
                        REPO,
                        GitHubCodeownersErrorsFilterBuilder.builder()
                                .ref("main")
                                .build());

        assertThat(errors).contains(RepositoriesCatalogFixtures.codeownersErrors());
    }

    @Test
    void getHashAlgorithm_shouldReturnAlgorithm_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("repository-hash-algorithm.json")));

        final GitHubRepositoryHashAlgorithmResponse response = api().getHashAlgorithm(OWNER, REPO);

        assertThat(response).isEqualTo(RepositoriesCatalogFixtures.repositoryHashAlgorithmResponse());
    }

    @Test
    void getHashAlgorithm_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getHashAlgorithm(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void listForOrg_shouldUseDefaultFilter_whenSingleArgOverload() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("minimal-repository.json") + "]"));

        api().listForOrg(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/orgs/github/repos?page=1&" + TestClients.perPageQuery());
    }

    @Test
    void listForOrg_shouldUseDefaultFilter_whenRequestOverload() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("minimal-repository.json") + "]"));

        api().listForOrg(ORG, GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "10");
    }

    @Test
    void listForAuthenticatedUser_shouldUseDefaultFilter_whenRequestOverload() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("repository.json") + "]"));

        api().listForAuthenticatedUser(GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "10");
    }

    @Test
    void listForAuthenticatedUser_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("repository.json") + "]"));

        api().listForAuthenticatedUser(
                        GitHubListForAuthenticatedUserFilterBuilder.builder()
                                .type(GitHubAuthenticatedUserReposType.OWNER)
                                .build(),
                        GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("type", "owner");
    }

    @Test
    void listForUser_shouldUseDefaultFilter_whenSingleArgOverload() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("minimal-repository.json") + "]"));

        api().listForUser(USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/users/octocat/repos?page=1&" + TestClients.perPageQuery());
    }

    @Test
    void listForUser_shouldUseDefaultFilter_whenRequestOverload() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("minimal-repository.json") + "]"));

        api().listForUser(USERNAME, GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "10");
    }

    @Test
    void listPublic_shouldOmitSince_whenGitHubNoneRequest() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("minimal-repository.json") + "]"));

        api().listPublic(GitHubPageRequest.none());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasToString("https://api.github.com/repositories");
    }

    @Test
    void listPublic_shouldOmitSince_whenNoRequest() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("minimal-repository.json") + "]"));

        api().listPublic();
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasToString("https://api.github.com/repositories");
    }

    @Test
    void listContributors_shouldUseDefaultFilter_whenRequestOverload() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("contributor.json") + "]"));

        api().listContributors(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "10");
    }

    @Test
    void listContributors_shouldSendAnonQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("contributor.json") + "]"));

        api().listContributors(
                        OWNER,
                        REPO,
                        GitHubListContributorsFilterBuilder.builder().anon(true).build(),
                        GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("anon", "1");
    }

    @Test
    void listTags_shouldUseDefaultPagination_whenSingleArgOverload() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-tag.json") + "]"));

        api().listTags(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString(
                        "https://api.github.com/repos/octocat/Hello-World/tags?page=1&" + TestClients.perPageQuery());
    }

    @Test
    void listTeams_shouldUseDefaultPagination_whenSingleArgOverload() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("team.json", GitHubTeam.class) + "]"));

        api().listTeams(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString(
                        "https://api.github.com/repos/octocat/Hello-World/teams?page=1&" + TestClients.perPageQuery());
    }

    @Test
    void listActivities_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-activity.json") + "]"));

        api().listActivities(
                        OWNER,
                        REPO,
                        GitHubListActivitiesFilterBuilder.builder()
                                .activityType(GitHubRepositoryActivityType.PUSH)
                                .build(),
                        GitHubPageRequest.byCursor());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("activity_type", "push");
    }

    @Test
    void codeownersErrors_shouldReturnErrors_whenRefOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codeowners-errors.json")));

        final Optional<GitHubCodeownersErrors> errors = api().codeownersErrors(OWNER, REPO);

        assertThat(errors).contains(RepositoriesCatalogFixtures.codeownersErrors());
    }

    @Test
    void enableVulnerabilityAlerts_shouldSendPut_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().enableVulnerabilityAlerts(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void disableVulnerabilityAlerts_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().disableVulnerabilityAlerts(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void enablePrivateVulnerabilityReporting_shouldSendPut_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().enablePrivateVulnerabilityReporting(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void disablePrivateVulnerabilityReporting_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().disablePrivateVulnerabilityReporting(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void enableImmutableReleases_shouldSendPut_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().enableImmutableReleases(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void disableImmutableReleases_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().disableImmutableReleases(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void checkImmutableReleases_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().checkImmutableReleases(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    private GitHubRepositoryApi api() {
        return new GitHubRepositoryApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubRepositoryApiTest.class, fileName);
    }

    private static String json(final String fileName, final Class<?> anchor) {
        return FileUtils.readString(anchor, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    @Test
    void listCacheInfo_shouldReturnReplicas_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("list-cache-info-item.json") + "]"));

        final GitHubPage<GitHubListCacheInfoItem> page = api().listCacheInfo(OWNER, REPO);

        assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.listCacheInfoItem());
    }

    @Test
    void listCacheInfo_shouldUseDefaultPagination_whenSingleArgOverload() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("list-cache-info-item.json") + "]"));

        api().listCacheInfo(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/repos/octocat/Hello-World/replicas/caches?page=1&"
                        + TestClients.perPageQuery());
    }

    @Test
    void listCacheInfo_shouldThrow_whenRequestNull() {
        final GitHubRepositoryApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listCacheInfo(OWNER, REPO, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }
}
