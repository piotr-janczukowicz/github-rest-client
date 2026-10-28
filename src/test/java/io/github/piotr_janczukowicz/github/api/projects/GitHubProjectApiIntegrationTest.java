package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.projectsApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
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
class GitHubProjectApiIntegrationTest {

    private static final String ORG = "octocat";
    private static final String USERNAME = "monalisa";
    private static final long PROJECT_NUMBER = 1L;
    private static final String ORG_COLLECTION_PATH = "/orgs/octocat/projectsV2";
    private static final String ORG_ITEM_PATH = "/orgs/octocat/projectsV2/1";
    private static final String USER_COLLECTION_PATH = "/users/monalisa/projectsV2";
    private static final String USER_ITEM_PATH = "/users/monalisa/projectsV2/1";

    private static final JsonType<List<GitHubProject>> PROJECTS_TYPE = JsonTypes.listOf(GitHubProject.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listForOrg_shouldReturnProjects_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, ORG_COLLECTION_PATH, okJson(listJsonBytes(GitHubProjectApiIntegrationTest.class, "project.json")));

        final GitHubPage<GitHubProject> page;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            page = api.api().project().listForOrg(ORG);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedProjects());
        HTTP.verifyRequested(GET, ORG_COLLECTION_PATH);
        HTTP.verifyQueryParam(GET, ORG_COLLECTION_PATH, "per_page", "100");
        HTTP.verifyNoQueryParam(GET, ORG_COLLECTION_PATH, "page");
    }

    @Test
    void listForOrg_shouldSendFilterAndCursor_whenProvided() throws Exception {
        HTTP.stub(
                GET, ORG_COLLECTION_PATH, okJson(listJsonBytes(GitHubProjectApiIntegrationTest.class, "project.json")));
        final GitHubListProjectsFilter filter =
                GitHubListProjectsFilterBuilder.builder().q("open").build();

        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            api.api().project().listForOrg(ORG, filter, GitHubCursorRequest.after("cursor", 10));
        }

        HTTP.verifyQueryParam(GET, ORG_COLLECTION_PATH, "q", "open");
        HTTP.verifyQueryParam(GET, ORG_COLLECTION_PATH, "after", "cursor");
        HTTP.verifyQueryParam(GET, ORG_COLLECTION_PATH, "per_page", "10");
        HTTP.verifyNoQueryParam(GET, ORG_COLLECTION_PATH, "page");
    }

    @Test
    void listForOrg_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                ORG_COLLECTION_PATH,
                okJson(listJsonBytes(GitHubProjectApiIntegrationTest.class, "project.json"))
                        .header(
                                LINK,
                                linkNext(
                                        HTTP.uri(ORG_COLLECTION_PATH + "?after=cursor&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubProjectApiIntegrationTest.class, "project.json")));

        final GitHubPage<GitHubProject> first;
        final Optional<GitHubPage<GitHubProject>> second;
        final Optional<GitHubPage<GitHubProject>> third;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            first = api.api().project().listForOrg(ORG);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedProjects());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedProjects());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, ORG_COLLECTION_PATH, 2);
    }

    @Test
    void listForOrg_shouldOmitPaginationQuery_whenNoneRequest() throws Exception {
        HTTP.stub(
                GET, ORG_COLLECTION_PATH, okJson(listJsonBytes(GitHubProjectApiIntegrationTest.class, "project.json")));

        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            api.api().project().listForOrg(ORG, GitHubPageRequest.none());
        }

        HTTP.verifyNoQueryParam(GET, ORG_COLLECTION_PATH, "page");
        HTTP.verifyNoQueryParam(GET, ORG_COLLECTION_PATH, "per_page");
    }

    @Test
    void getForOrg_shouldReturnProject_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ORG_ITEM_PATH, okJson(jsonBytes(GitHubProjectApiIntegrationTest.class, "project.json")));

        final Optional<GitHubProject> project;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            project = api.api().project().getForOrg(ORG, PROJECT_NUMBER);
        }

        assertThat(project).contains(expectedProject());
        HTTP.verifyRequested(GET, ORG_ITEM_PATH);
    }

    @Test
    void getForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_ITEM_PATH, notFound());

        final Optional<GitHubProject> project;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            project = api.api().project().getForOrg(ORG, PROJECT_NUMBER);
        }

        assertThat(project).isEmpty();
        HTTP.verifyRequested(GET, ORG_ITEM_PATH);
    }

    @Test
    void listForUser_shouldReturnProjects_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_COLLECTION_PATH,
                okJson(listJsonBytes(GitHubProjectApiIntegrationTest.class, "project.json")));

        final GitHubPage<GitHubProject> page;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            page = api.api().project().listForUser(USERNAME);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedProjects());
        HTTP.verifyRequested(GET, USER_COLLECTION_PATH);
        HTTP.verifyQueryParam(GET, USER_COLLECTION_PATH, "per_page", "100");
        HTTP.verifyNoQueryParam(GET, USER_COLLECTION_PATH, "page");
    }

    @Test
    void getForUser_shouldReturnProject_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, USER_ITEM_PATH, okJson(jsonBytes(GitHubProjectApiIntegrationTest.class, "project.json")));

        final Optional<GitHubProject> project;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            project = api.api().project().getForUser(USERNAME, PROJECT_NUMBER);
        }

        assertThat(project).contains(expectedProject());
        HTTP.verifyRequested(GET, USER_ITEM_PATH);
    }

    private static List<GitHubProject> expectedProjects() {
        return CODEC.decode(listJsonBytes(GitHubProjectApiIntegrationTest.class, "project.json"), PROJECTS_TYPE);
    }

    private static GitHubProject expectedProject() {
        return CODEC.decode(jsonBytes(GitHubProjectApiIntegrationTest.class, "project.json"), GitHubProject.class);
    }
}
