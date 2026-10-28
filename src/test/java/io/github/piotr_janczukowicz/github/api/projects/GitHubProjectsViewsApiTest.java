package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubProjectsViewsApiTest {

    private static final String ORG = "octocat";
    private static final String USER_ID = "2";
    private static final long PROJECT_NUMBER = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void createForOrg_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("view.json")));
        final GitHubCreateProjectViewPayload payload = ProjectsCatalogFixtures.createProjectViewPayload();

        final GitHubProjectView view = api().createForOrg(ORG, PROJECT_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(view).isEqualTo(ProjectsCatalogFixtures.projectView());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/octocat/projectsV2/1/views");
        });
    }

    @Test
    void createForUser_shouldUseUsersPath_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("view.json")));
        final GitHubCreateProjectViewPayload payload = ProjectsCatalogFixtures.createProjectViewPayload();

        api().createForUser(USER_ID, PROJECT_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/users/2/projectsV2/1/views");
    }

    @Test
    void createForOrg_shouldThrow_whenOrgBlank() {
        final GitHubCreateProjectViewPayload payload = ProjectsCatalogFixtures.createProjectViewPayload();

        final GitHubProjectsViewsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createForOrg(" ", PROJECT_NUMBER, payload));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
    }

    @Test
    void createForOrg_shouldThrow_whenPayloadNull() {
        final GitHubProjectsViewsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createForOrg(ORG, PROJECT_NUMBER, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    private GitHubProjectsViewsApi api() {
        return new GitHubProjectsViewsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubProjectsViewsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
