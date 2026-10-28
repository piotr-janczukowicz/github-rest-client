package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
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
class GitHubProjectApiTest {

    private static final String ORG = "octocat";
    private static final String USERNAME = "monalisa";
    private static final long PROJECT_NUMBER = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void listForOrg_shouldReturnProjects_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("project.json") + "]"));

        final GitHubPage<GitHubProject> page = api().listForOrg(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ProjectsCatalogFixtures.project());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octocat/projectsV2?" + TestClients.perPageQuery());
        });
    }

    @Test
    void listForUser_shouldUseUsersPath_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("project.json") + "]"));

        api().listForUser(USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/users/monalisa/projectsV2?" + TestClients.perPageQuery());
    }

    @Test
    void getForOrg_shouldReturnProject_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("project.json")));

        final Optional<GitHubProject> project = api().getForOrg(ORG, PROJECT_NUMBER);

        assertThat(project).contains(ProjectsCatalogFixtures.project());
    }

    @Test
    void getForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubProject> project = api().getForOrg(ORG, PROJECT_NUMBER);

        assertThat(project).isEmpty();
    }

    @Test
    void listForOrg_shouldThrow_whenOrgBlank() {
        final GitHubProjectApi api = api();

        final Throwable thrown = catchThrowable(
                () -> api.listForOrg(" ", GitHubListProjectsFilter.empty(), GitHubPageRequest.byCursor()));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
    }

    @Test
    void listForOrg_shouldThrow_whenPageRequestInvalid() {
        final GitHubProjectApi api = api();
        final GitHubPageRequest page = GitHubPageRequest.byPage();

        final Throwable thrown = catchThrowable(() -> api.listForOrg(ORG, page));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void listForOrg_shouldSendQuery_whenRequestPresent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("project.json") + "]"));

        api().listForOrg(ORG, GitHubPageRequest.byCursor());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/orgs/octocat/projectsV2?" + TestClients.perPageQuery());
    }

    @Test
    void listForOrg_shouldSendQuery_whenFilterAndRequestPresent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("project.json") + "]"));
        final GitHubListProjectsFilter filter =
                GitHubListProjectsFilterBuilder.builder().q("open").build();

        api().listForOrg(ORG, filter, GitHubPageRequest.byCursor());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("q=open");
    }

    @Test
    void listForUser_shouldSendQuery_whenRequestPresent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("project.json") + "]"));

        api().listForUser(USERNAME, GitHubPageRequest.byCursor());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/users/monalisa/projectsV2?" + TestClients.perPageQuery());
    }

    @Test
    void listForUser_shouldSendQuery_whenFilterAndRequestPresent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("project.json") + "]"));
        final GitHubListProjectsFilter filter =
                GitHubListProjectsFilterBuilder.builder().q("open").build();

        api().listForUser(USERNAME, filter, GitHubPageRequest.byCursor());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("q=open");
    }

    @Test
    void getForUser_shouldReturnProject_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("project.json")));

        final Optional<GitHubProject> project = api().getForUser(USERNAME, PROJECT_NUMBER);

        assertThat(project).contains(ProjectsCatalogFixtures.project());
    }

    private GitHubProjectApi api() {
        return new GitHubProjectApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubProjectApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
