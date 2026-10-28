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
class GitHubProjectsDraftsApiTest {

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
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("item-simple.json")));
        final GitHubCreateDraftItemPayload payload = ProjectsCatalogFixtures.createDraftItemPayload();

        final GitHubProjectItemSimple item = api().createForOrg(ORG, PROJECT_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(item).isEqualTo(ProjectsCatalogFixtures.itemSimpleDraft());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/octocat/projectsV2/1/drafts");
        });
    }

    @Test
    void createForAuthenticatedUser_shouldUseUserPath_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("item-simple.json")));
        final GitHubCreateDraftItemPayload payload = ProjectsCatalogFixtures.createDraftItemPayload();

        api().createForAuthenticatedUser(USER_ID, PROJECT_NUMBER, payload);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/user/2/projectsV2/1/drafts");
    }

    @Test
    void createForOrg_shouldThrow_whenOrgBlank() {
        final GitHubCreateDraftItemPayload payload = ProjectsCatalogFixtures.createDraftItemPayload();

        final GitHubProjectsDraftsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createForOrg(" ", PROJECT_NUMBER, payload));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
    }

    @Test
    void createForOrg_shouldThrow_whenPayloadNull() {
        final GitHubProjectsDraftsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createForOrg(ORG, PROJECT_NUMBER, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    private GitHubProjectsDraftsApi api() {
        return new GitHubProjectsDraftsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubProjectsDraftsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
