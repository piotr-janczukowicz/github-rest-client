package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleset;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRulesetVersion;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRulesetVersionWithState;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubOrganizationsRulesApiTest {

    private static final String ORG = "my-org";
    private static final long RULESET_ID = 42L;
    private static final long VERSION_ID = 1L;
    private static final String ORG_RULESETS_PATH = "/orgs/my-org/rulesets";
    private static final String ORG_RULESET_PATH = "/orgs/my-org/rulesets/42";
    private static final String ORG_RULESET_HISTORY_PATH = "/orgs/my-org/rulesets/42/history";
    private static final String ORG_RULESET_VERSION_PATH = "/orgs/my-org/rulesets/42/history/1";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsRulesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getRulesets_shouldReturnRulesets_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-ruleset.json") + "]"));

        final GitHubPage<GitHubRepositoryRuleset> page = callApi(api -> api.getRulesets(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).isEqualTo(List.of(RepositoriesCatalogFixtures.repositoryRuleset()));
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com" + ORG_RULESETS_PATH + "?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void getRulesets_shouldSendFilterAndPageQuery_whenFilterAndRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-ruleset.json") + "]"));
        final GitHubListOrgRulesetsFilter filter = GitHubListOrgRulesetsFilterBuilder.builder()
                .targets("branch,tag")
                .build();

        callApi(api -> api.getRulesets(ORG, filter, GitHubPageNumberRequest.of(2, 10)));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com" + ORG_RULESETS_PATH + "?targets=branch%2Ctag&page=2&per_page=10");
        });
    }

    @Test
    void getRulesets_shouldThrow_whenOrgBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRulesApi api = new GitHubOrganizationsRulesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.getRulesets(" "));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
        }
    }

    @Test
    void getRulesets_shouldThrow_whenFilterNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRulesApi api = new GitHubOrganizationsRulesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.getRulesets(ORG, null, GitHubPageRequest.byPage()));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
        }
    }

    @Test
    void getRulesets_shouldThrow_whenRequestNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRulesApi api = new GitHubOrganizationsRulesApi(TestClients.caller(client));

            final Throwable thrown =
                    catchThrowable(() -> api.getRulesets(ORG, GitHubListOrgRulesetsFilter.empty(), null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
        }
    }

    @Test
    void createRuleset_shouldReturnRuleset_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("repository-ruleset.json")));
        final GitHubCreateOrgRulesetPayload payload = OrganizationsCatalogFixtures.createOrgRulesetPayload();

        final GitHubRepositoryRuleset ruleset = callApi(api -> api.createRuleset(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(ruleset).isEqualTo(RepositoriesCatalogFixtures.repositoryRuleset());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ORG_RULESETS_PATH);
        });
    }

    @Test
    void createRuleset_shouldThrow_whenOrgBlank() {
        final GitHubCreateOrgRulesetPayload payload = OrganizationsCatalogFixtures.createOrgRulesetPayload();

        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRulesApi api = new GitHubOrganizationsRulesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.createRuleset(" ", payload));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
        }
    }

    @Test
    void createRuleset_shouldThrow_whenPayloadNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRulesApi api = new GitHubOrganizationsRulesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.createRuleset(ORG, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    @Test
    void getRuleset_shouldReturnRuleset_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repository-ruleset.json")));

        final Optional<GitHubRepositoryRuleset> ruleset = callApi(api -> api.getRuleset(ORG, RULESET_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(ruleset).contains(RepositoriesCatalogFixtures.repositoryRuleset());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ORG_RULESET_PATH);
        });
    }

    @Test
    void getRuleset_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubRepositoryRuleset> ruleset = callApi(api -> api.getRuleset(ORG, RULESET_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(ruleset).isEmpty();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ORG_RULESET_PATH);
        });
    }

    @Test
    void getRuleset_shouldThrow_whenOrgBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRulesApi api = new GitHubOrganizationsRulesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.getRuleset(" ", RULESET_ID));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
        }
    }

    @Test
    void updateRuleset_shouldReturnRuleset_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repository-ruleset.json")));
        final GitHubUpdateOrgRulesetPayload payload = OrganizationsCatalogFixtures.updateOrgRulesetPayload();

        final GitHubRepositoryRuleset ruleset = callApi(api -> api.updateRuleset(ORG, RULESET_ID, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(ruleset).isEqualTo(RepositoriesCatalogFixtures.repositoryRuleset());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ORG_RULESET_PATH);
        });
    }

    @Test
    void updateRuleset_shouldPutWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repository-ruleset.json")));

        callApi(api -> api.updateRuleset(ORG, RULESET_ID, null));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ORG_RULESET_PATH);
        });
    }

    @Test
    void updateRuleset_shouldThrow_whenOrgBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRulesApi api = new GitHubOrganizationsRulesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.updateRuleset(" ", RULESET_ID, null));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
        }
    }

    @Test
    void deleteRuleset_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        try (GitHubClient client = TestClients.client(transport)) {
            new GitHubOrganizationsRulesApi(TestClients.caller(client)).deleteRuleset(ORG, RULESET_ID);
        }
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ORG_RULESET_PATH);
        });
    }

    @Test
    void deleteRuleset_shouldThrow_whenOrgBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRulesApi api = new GitHubOrganizationsRulesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.deleteRuleset(" ", RULESET_ID));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
        }
    }

    @Test
    void getRulesetHistory_shouldReturnVersions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("ruleset-version.json") + "]"));

        final GitHubPage<GitHubRulesetVersion> page =
                callApi(api -> api.getRulesetHistory(ORG, RULESET_ID, GitHubPageRequest.byPage()));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).isEqualTo(List.of(RepositoriesCatalogFixtures.rulesetVersion()));
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com" + ORG_RULESET_HISTORY_PATH + "?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void getRulesetHistory_shouldReturnVersions_whenSingleArgOverloadCalled() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("ruleset-version.json") + "]"));

        final GitHubPage<GitHubRulesetVersion> page = callApi(api -> api.getRulesetHistory(ORG, RULESET_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).isEqualTo(List.of(RepositoriesCatalogFixtures.rulesetVersion()));
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com" + ORG_RULESET_HISTORY_PATH + "?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void getRulesetHistory_shouldThrow_whenOrgBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRulesApi api = new GitHubOrganizationsRulesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.getRulesetHistory(" ", RULESET_ID));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
        }
    }

    @Test
    void getRulesetHistory_shouldThrow_whenRequestNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRulesApi api = new GitHubOrganizationsRulesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.getRulesetHistory(ORG, RULESET_ID, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
        }
    }

    @Test
    void getRulesetVersion_shouldReturnVersion_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("ruleset-version-with-state.json")));

        final Optional<GitHubRulesetVersionWithState> version =
                callApi(api -> api.getRulesetVersion(ORG, RULESET_ID, VERSION_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(version).contains(RepositoriesCatalogFixtures.rulesetVersionWithState());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ORG_RULESET_VERSION_PATH);
        });
    }

    @Test
    void getRulesetVersion_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubRulesetVersionWithState> version =
                callApi(api -> api.getRulesetVersion(ORG, RULESET_ID, VERSION_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(version).isEmpty();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ORG_RULESET_VERSION_PATH);
        });
    }

    @Test
    void getRulesetVersion_shouldThrow_whenOrgBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRulesApi api = new GitHubOrganizationsRulesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.getRulesetVersion(" ", RULESET_ID, VERSION_ID));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
        }
    }

    @FunctionalInterface
    private interface RulesApiAction<T> {
        T apply(GitHubOrganizationsRulesApi api) throws Exception;
    }

    private <T> T callApi(final RulesApiAction<T> action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationsRulesApi(TestClients.caller(client)));
        }
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubRepositoryRuleset.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
