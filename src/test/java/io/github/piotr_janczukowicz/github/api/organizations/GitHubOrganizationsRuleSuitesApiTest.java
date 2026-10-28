package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuite;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteListItem;
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
class GitHubOrganizationsRuleSuitesApiTest {

    private static final String ORG = "my-org";
    private static final long RULE_SUITE_ID = 1L;
    private static final String ORG_RULE_SUITES_PATH = "/orgs/my-org/rulesets/rule-suites";
    private static final String ORG_RULE_SUITE_PATH = "/orgs/my-org/rulesets/rule-suites/1";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsRuleSuitesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void list_shouldReturnSuites_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("git-hub-rule-suite-list-item.json") + "]"));

        final GitHubPage<GitHubRuleSuiteListItem> page = callApi(api -> api.list(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).isEqualTo(List.of(RepositoriesCatalogFixtures.ruleSuiteListItem()));
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com" + ORG_RULE_SUITES_PATH + "?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void list_shouldSendFilterAndPageQuery_whenFilterAndRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("git-hub-rule-suite-list-item.json") + "]"));
        final GitHubListOrgRuleSuitesFilter filter = GitHubListOrgRuleSuitesFilterBuilder.builder()
                .ref("refs/heads/main")
                .build();

        callApi(api -> api.list(ORG, filter, GitHubPageNumberRequest.of(2, 10)));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com" + ORG_RULE_SUITES_PATH
                            + "?ref=refs%2Fheads%2Fmain&page=2&per_page=10");
        });
    }

    @Test
    void list_shouldThrow_whenOrgBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRuleSuitesApi api =
                    new GitHubOrganizationsRuleSuitesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.list(" "));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
        }
    }

    @Test
    void list_shouldThrow_whenFilterNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRuleSuitesApi api =
                    new GitHubOrganizationsRuleSuitesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.list(ORG, null, GitHubPageRequest.byPage()));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
        }
    }

    @Test
    void list_shouldThrow_whenRequestNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRuleSuitesApi api =
                    new GitHubOrganizationsRuleSuitesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.list(ORG, GitHubListOrgRuleSuitesFilter.empty(), null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
        }
    }

    @Test
    void get_shouldReturnSuite_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("rule-suite.json")));

        final Optional<GitHubRuleSuite> suite = callApi(api -> api.get(ORG, RULE_SUITE_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(suite).contains(RepositoriesCatalogFixtures.ruleSuite());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ORG_RULE_SUITE_PATH);
        });
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubRuleSuite> suite = callApi(api -> api.get(ORG, RULE_SUITE_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(suite).isEmpty();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ORG_RULE_SUITE_PATH);
        });
    }

    @Test
    void get_shouldSendIncludeMetadataQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("rule-suite.json")));
        final GitHubGetOrgRuleSuiteFilter filter = OrganizationsCatalogFixtures.getOrgRuleSuiteFilter();

        callApi(api -> api.get(ORG, RULE_SUITE_ID, filter));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com" + ORG_RULE_SUITE_PATH + "?include_metadata=true");
        });
    }

    @Test
    void get_shouldThrow_whenFilterNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRuleSuitesApi api =
                    new GitHubOrganizationsRuleSuitesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.get(ORG, RULE_SUITE_ID, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
        }
    }

    @Test
    void get_shouldThrow_whenOrgBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsRuleSuitesApi api =
                    new GitHubOrganizationsRuleSuitesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.get(" ", RULE_SUITE_ID));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
        }
    }

    @FunctionalInterface
    private interface RuleSuitesApiAction<T> {
        T apply(GitHubOrganizationsRuleSuitesApi api) throws Exception;
    }

    private <T> T callApi(final RuleSuitesApiAction<T> action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationsRuleSuitesApi(TestClients.caller(client)));
        }
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubRuleSuite.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
