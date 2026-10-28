package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.http.HttpBody;
import io.github.piotr_janczukowicz.http.HttpHeaders;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpResponse;
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
class GitHubDeploymentsBranchPoliciesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ENVIRONMENT = "staging";
    private static final long BRANCH_POLICY_ID = 361_471L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDeploymentsBranchPoliciesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listDeploymentBranchPolicies_shouldReturnPolicies_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(),
                        "{\"total_count\":1,\"branch_policies\":[" + json("deployment-branch-policy.json") + "]}"));

        final GitHubPage<GitHubDeploymentBranchPolicy> page =
                api().listDeploymentBranchPolicies(OWNER, REPO, ENVIRONMENT);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(DeploymentsCatalogFixtures.deploymentBranchPolicy());
            softly.assertThat(page.totalCount()).isEqualTo(1);
        });
    }

    @Test
    void getDeploymentBranchPolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("deployment-branch-policy.json")));

        final Optional<GitHubDeploymentBranchPolicy> policy =
                api().getDeploymentBranchPolicy(OWNER, REPO, ENVIRONMENT, BRANCH_POLICY_ID);

        assertThat(policy).contains(DeploymentsCatalogFixtures.deploymentBranchPolicy());
    }

    @Test
    void createDeploymentBranchPolicy_shouldPostPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("deployment-branch-policy.json")));
        final GitHubDeploymentBranchPolicy policy = api().createDeploymentBranchPolicy(
                        OWNER, REPO, ENVIRONMENT, DeploymentsCatalogFixtures.createDeploymentBranchPolicyPayload());

        assertThat(policy).isEqualTo(DeploymentsCatalogFixtures.deploymentBranchPolicy());
    }

    @Test
    void updateDeploymentBranchPolicy_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("deployment-branch-policy.json")));
        final GitHubDeploymentBranchPolicy policy = api().updateDeploymentBranchPolicy(
                        OWNER,
                        REPO,
                        ENVIRONMENT,
                        BRANCH_POLICY_ID,
                        DeploymentsCatalogFixtures.updateDeploymentBranchPolicyPayload());

        assertThat(policy).isEqualTo(DeploymentsCatalogFixtures.deploymentBranchPolicy());
    }

    @Test
    void deleteDeploymentBranchPolicy_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteDeploymentBranchPolicy(OWNER, REPO, ENVIRONMENT, BRANCH_POLICY_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
    }

    private GitHubDeploymentsBranchPoliciesApi api() {
        return new GitHubDeploymentsBranchPoliciesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubDeploymentsBranchPoliciesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    @Test
    void createDeploymentBranchPolicy_shouldThrowApiExceptionWithoutFollowing_whenResponseIsSeeOther()
            throws Exception {
        final HttpResponse seeOther = new HttpResponse(
                HttpStatus.SEE_OTHER.code(),
                HttpHeaders.of("Location", "https://api.github.com/repos/octocat/Hello-World/policy/1"),
                HttpBody.absent());
        when(transport.execute(any())).thenReturn(seeOther);
        final GitHubDeploymentsBranchPoliciesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createDeploymentBranchPolicy(
                OWNER, REPO, ENVIRONMENT, DeploymentsCatalogFixtures.createDeploymentBranchPolicyPayload()));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
        assertThat(((GitHubApiException) thrown).statusCode()).isEqualTo(HttpStatus.SEE_OTHER.code());
        verify(transport, times(1)).execute(any());
    }
}
