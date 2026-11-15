package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubRepositoriesPlanningStatusesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoriesPlanningStatusesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listPlanningStatuses_shouldReturnPlanningStatuses_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("planning-status.json") + "]"));

        final List<GitHubPlanningStatus> statuses = api().listPlanningStatuses(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(statuses).containsExactly(RepositoriesCatalogFixtures.planningStatus());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/planning-statuses");
        });
    }

    @Test
    void listPlanningStatuses_shouldThrow_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));
        final GitHubRepositoriesPlanningStatusesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listPlanningStatuses(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
    }

    private GitHubRepositoriesPlanningStatusesApi api() {
        return new GitHubRepositoriesPlanningStatusesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubRepositoriesPlanningStatusesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
