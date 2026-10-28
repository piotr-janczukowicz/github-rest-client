package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubRepositoriesRulesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String BRANCH = "main";
    private static final long RULESET_ID = 42L;
    private static final long VERSION_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoriesRulesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getBranchRules_shouldReturnRules_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-rule-detailed.json") + "]"));

        final GitHubPage<GitHubRepositoryRuleDetailed> page = api().getBranchRules(OWNER, REPO, BRANCH);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.creationDetailed());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void getBranchRules_shouldPercentEncodeSlashInOwner_whenOwnerContainsSlash() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-rule-detailed.json") + "]"));

        api().getBranchRules("a/b", REPO, BRANCH);

        assertThat(captureSingleRequest().uri().getRawPath()).isEqualTo("/repos/a%2Fb/Hello-World/rules/branches/main");
    }

    @Test
    void getRepoRulesets_shouldReturnRulesets_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-ruleset.json") + "]"));

        final GitHubPage<GitHubRepositoryRuleset> page = api().getRepoRulesets(OWNER, REPO);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.repositoryRuleset());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void createRepoRuleset_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("repository-ruleset.json")));
        final GitHubCreateRepoRulesetPayload payload = RepositoriesCatalogFixtures.createRepoRulesetPayload();

        final GitHubRepositoryRuleset ruleset = api().createRepoRuleset(OWNER, REPO, payload);

        assertThat(ruleset).isEqualTo(RepositoriesCatalogFixtures.repositoryRuleset());
    }

    @Test
    void getRepoRuleset_shouldReturnRuleset_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repository-ruleset.json")));

        final Optional<GitHubRepositoryRuleset> ruleset = api().getRepoRuleset(OWNER, REPO, RULESET_ID);

        assertThat(ruleset).contains(RepositoriesCatalogFixtures.repositoryRuleset());
    }

    @Test
    void getRepoRuleset_shouldSendIncludesParents_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repository-ruleset.json")));

        api().getRepoRuleset(
                        OWNER,
                        REPO,
                        RULESET_ID,
                        GitHubGetRepoRulesetFilterBuilder.builder()
                                .includesParents(false)
                                .build());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri().getQuery()).contains("includes_parents=false");
    }

    @Test
    void updateRepoRuleset_shouldPutPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repository-ruleset.json")));
        final GitHubUpdateRepoRulesetPayload payload = RepositoriesCatalogFixtures.updateRepoRulesetPayload();

        api().updateRepoRuleset(OWNER, REPO, RULESET_ID, payload);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void updateRepoRuleset_shouldPutWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repository-ruleset.json")));

        api().updateRepoRuleset(OWNER, REPO, RULESET_ID, null);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void deleteRepoRuleset_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteRepoRuleset(OWNER, REPO, RULESET_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/rulesets/42");
    }

    @Test
    void getRepoRulesetHistory_shouldReturnVersions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("ruleset-version.json") + "]"));

        final GitHubPage<GitHubRulesetVersion> page =
                api().getRepoRulesetHistory(OWNER, REPO, RULESET_ID, GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.rulesetVersion());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void getRepoRulesetVersion_shouldReturnVersion_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("ruleset-version-with-state.json")));

        final Optional<GitHubRulesetVersionWithState> version =
                api().getRepoRulesetVersion(OWNER, REPO, RULESET_ID, VERSION_ID);

        assertThat(version).contains(RepositoriesCatalogFixtures.rulesetVersionWithState());
    }

    @Test
    void getRepoRulesets_shouldReturnRulesets_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-ruleset.json") + "]"));

        final GitHubPage<GitHubRepositoryRuleset> page = api().getRepoRulesets(
                        OWNER,
                        REPO,
                        GitHubGetRepoRulesetsFilterBuilder.builder()
                                .includesParents(true)
                                .build(),
                        GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.repositoryRuleset());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void getBranchRules_shouldReturnRules_whenPaginationProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-rule-detailed.json") + "]"));

        final GitHubPage<GitHubRepositoryRuleDetailed> page =
                api().getBranchRules(OWNER, REPO, BRANCH, GitHubPageRequest.byPage());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.creationDetailed());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void getRepoRulesetHistory_shouldReturnVersions_whenSingleArgOverloadCalled() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("ruleset-version.json") + "]"));

        final GitHubPage<GitHubRulesetVersion> page = api().getRepoRulesetHistory(OWNER, REPO, RULESET_ID);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.rulesetVersion());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    private GitHubRepositoriesRulesApi api() {
        return new GitHubRepositoriesRulesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubRepositoriesRulesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
