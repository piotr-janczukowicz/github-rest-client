package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeploymentsStatusesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long DEPLOYMENT_ID = 42L;
    private static final long STATUS_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDeploymentsStatusesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listDeploymentStatuses_shouldReturnStatuses_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("deployment-status.json") + "]"));

        final GitHubPage<GitHubDeploymentStatus> page = api().listDeploymentStatuses(OWNER, REPO, DEPLOYMENT_ID);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(DeploymentsCatalogFixtures.deploymentStatus());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void getDeploymentStatus_shouldReturnStatus_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("deployment-status.json")));

        final Optional<GitHubDeploymentStatus> status =
                api().getDeploymentStatus(OWNER, REPO, DEPLOYMENT_ID, STATUS_ID);

        assertThat(status).contains(DeploymentsCatalogFixtures.deploymentStatus());
    }

    @Test
    void createDeploymentStatus_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("deployment-status.json")));

        final GitHubDeploymentStatus status = api().createDeploymentStatus(
                        OWNER, REPO, DEPLOYMENT_ID, DeploymentsCatalogFixtures.createDeploymentStatusPayload());

        assertThat(status).isEqualTo(DeploymentsCatalogFixtures.deploymentStatus());
    }

    private GitHubDeploymentsStatusesApi api() {
        return new GitHubDeploymentsStatusesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubDeploymentsStatusesApiTest.class, fileName);
    }
}
