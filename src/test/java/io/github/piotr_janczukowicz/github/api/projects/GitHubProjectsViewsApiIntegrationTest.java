package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.projectsApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubProjectsViewsApiIntegrationTest {

    private static final String ORG = "octocat";
    private static final String USER_ID = "2";
    private static final long PROJECT_NUMBER = 1L;
    private static final String ORG_PATH = "/orgs/octocat/projectsV2/1/views";
    private static final String USER_PATH = "/users/2/projectsV2/1/views";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void createForOrg_shouldReturnView_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, ORG_PATH, createdJson(jsonBytes(GitHubProjectsViewsApiIntegrationTest.class, "view.json")));
        final GitHubCreateProjectViewPayload payload = ProjectsCatalogFixtures.createProjectViewPayload();

        final GitHubProjectView view;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            view = api.api().views().createForOrg(ORG, PROJECT_NUMBER, payload);
        }

        assertThat(view).isEqualTo(expectedView());
        HTTP.verifyRequested(POST, ORG_PATH);
        HTTP.verifyHeader(POST, ORG_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, ORG_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void createForUser_shouldReturnView_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, USER_PATH, createdJson(jsonBytes(GitHubProjectsViewsApiIntegrationTest.class, "view.json")));
        final GitHubCreateProjectViewPayload payload = ProjectsCatalogFixtures.createProjectViewPayload();

        final GitHubProjectView view;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            view = api.api().views().createForUser(USER_ID, PROJECT_NUMBER, payload);
        }

        assertThat(view).isEqualTo(expectedView());
        HTTP.verifyRequested(POST, USER_PATH);
        HTTP.verifyBody(POST, USER_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    private static GitHubProjectView expectedView() {
        return CODEC.decode(
                jsonBytes(GitHubProjectsViewsApiIntegrationTest.class, "view.json"), GitHubProjectView.class);
    }
}
