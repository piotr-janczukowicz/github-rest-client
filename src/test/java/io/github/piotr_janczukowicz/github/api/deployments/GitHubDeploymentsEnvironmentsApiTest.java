package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
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
class GitHubDeploymentsEnvironmentsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ENVIRONMENT = "staging";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDeploymentsEnvironmentsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getAllEnvironments_shouldReturnEnvironments_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(),
                        "{\"total_count\":1,\"environments\":[" + json("environment.json") + "]}"));

        final GitHubPage<GitHubEnvironment> page = api().getAllEnvironments(OWNER, REPO);

        assertSoftly(softly -> {
            softly.assertThat(page.totalCount()).isEqualTo(1);
            softly.assertThat(page.items()).containsExactly(DeploymentsCatalogFixtures.environment());
        });
    }

    @Test
    void getEnvironment_shouldReturnEnvironment_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("environment.json")));

        final Optional<GitHubEnvironment> environment = api().getEnvironment(OWNER, REPO, ENVIRONMENT);

        assertThat(environment).contains(DeploymentsCatalogFixtures.environment());
    }

    @Test
    void createOrUpdateEnvironment_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("environment.json")));
        final GitHubEnvironment environment = api().createOrUpdateEnvironment(
                        OWNER, REPO, ENVIRONMENT, DeploymentsCatalogFixtures.createOrUpdateEnvironmentPayload());

        assertThat(environment).isEqualTo(DeploymentsCatalogFixtures.environment());
    }

    @Test
    void createOrUpdateEnvironment_shouldPutWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("environment.json")));

        api().createOrUpdateEnvironment(OWNER, REPO, ENVIRONMENT, null);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void deleteAnEnvironment_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteAnEnvironment(OWNER, REPO, ENVIRONMENT);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/repos/octocat/Hello-World/environments/staging");
    }

    private GitHubDeploymentsEnvironmentsApi api() {
        return new GitHubDeploymentsEnvironmentsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubDeploymentsEnvironmentsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
