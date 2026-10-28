package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
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
class GitHubDeploymentApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final long DEPLOYMENT_ID = 42L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDeploymentApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listDeployments_shouldReturnDeployments_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("deployment.json") + "]"));

        final GitHubPage<GitHubDeployment> page = api().listDeployments(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(DeploymentsCatalogFixtures.deployment());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/deployments?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listDeployments_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("deployment.json") + "]"));

        api().listDeployments(
                        OWNER,
                        REPO,
                        GitHubListDeploymentsFilterBuilder.builder()
                                .environment("production")
                                .ref("main")
                                .build(),
                        GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("environment", "production");
            softly.assertThat(requestUri).hasParameter("ref", "main");
        });
    }

    @Test
    void getDeployment_shouldReturnDeployment_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("deployment.json")));

        final Optional<GitHubDeployment> deployment = api().getDeployment(OWNER, REPO, DEPLOYMENT_ID);

        assertThat(deployment).contains(DeploymentsCatalogFixtures.deployment());
    }

    @Test
    void getDeployment_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubDeployment> deployment = api().getDeployment(OWNER, REPO, DEPLOYMENT_ID);

        assertThat(deployment).isEmpty();
    }

    @Test
    void createDeployment_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("deployment.json")));
        final GitHubCreateDeploymentResult result =
                api().createDeployment(OWNER, REPO, DeploymentsCatalogFixtures.createDeploymentPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result)
                    .isEqualTo(GitHubCreateDeploymentResult.ofCreated(DeploymentsCatalogFixtures.deployment()));
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/deployments");
        });
    }

    @Test
    void createDeployment_shouldReturnMergedBranch_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("deployment-merged-branch.json")));
        final GitHubCreateDeploymentResult result =
                api().createDeployment(OWNER, REPO, DeploymentsCatalogFixtures.createDeploymentPayload());

        assertThat(result)
                .isEqualTo(GitHubCreateDeploymentResult.ofMergedBranch(
                        DeploymentsCatalogFixtures.deploymentMergedBranch()));
    }

    @Test
    void deleteDeployment_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteDeployment(OWNER, REPO, DEPLOYMENT_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/deployments/42");
        });
    }

    private GitHubDeploymentApi api() {
        return new GitHubDeploymentApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubDeploymentApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
