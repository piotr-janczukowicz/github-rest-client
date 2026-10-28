package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
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
class GitHubActionsPoliciesApiTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final String ORG = "github";
    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String BASE = "https://api.github.com/";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsPoliciesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getOrgActionsPolicies_shouldReturnPolicies_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-policies.json")));

        final GitHubPage<GitHubActionsPolicy> page = api().getOrgActionsPolicies(ORG);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsPolicies().policies());
    }

    @Test
    void getOrgActionsPolicies_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-policies.json")));

        api().getOrgActionsPolicies(ORG, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void getOrgActionsPolicies_shouldSendHasParents_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-policies.json")));

        api().getOrgActionsPolicies(
                        ORG, ActionsCatalogFixtures.getOrgActionsPoliciesFilter(), GitHubPageNumberRequest.of(1, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("has_parents", "true");
    }

    @Test
    void createOrgActionsPolicy_shouldReturnPolicy_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("actions-policy.json")));

        final GitHubActionsPolicy policy =
                api().createOrgActionsPolicy(ORG, ActionsCatalogFixtures.createOrgActionsPolicyPayload());

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsPolicy());
    }

    @Test
    void createOrgActionsPolicy_shouldPost_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("actions-policy.json")));

        api().createOrgActionsPolicy(ORG, ActionsCatalogFixtures.createOrgActionsPolicyPayload());

        assertThat(line(captureSingleRequest())).isEqualTo("POST " + BASE + "orgs/github/actions/policies");
    }

    @Test
    void createOrgActionsPolicy_shouldThrow_whenPayloadNull() {
        final GitHubActionsPoliciesApi sut = api();

        final Throwable thrown = catchThrowable(() -> sut.createOrgActionsPolicy(ORG, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getOrgActionsPolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-policy.json")));

        final GitHubActionsPolicy policy = api().getOrgActionsPolicy(ORG, 1L).orElseThrow();

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsPolicy());
    }

    @Test
    void getOrgActionsPolicy_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubActionsPolicy> result = api().getOrgActionsPolicy(ORG, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void updateOrgActionsPolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-policy.json")));

        final GitHubActionsPolicy policy =
                api().updateOrgActionsPolicy(ORG, 1L, ActionsCatalogFixtures.updateOrgActionsPolicyPayload());

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsPolicy());
    }

    @Test
    void updateOrgActionsPolicy_shouldPut_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-policy.json")));

        api().updateOrgActionsPolicy(ORG, 1L, ActionsCatalogFixtures.updateOrgActionsPolicyPayload());

        assertThat(line(captureSingleRequest())).isEqualTo("PUT " + BASE + "orgs/github/actions/policies/1");
    }

    @Test
    void updateOrgActionsPolicy_shouldThrow_whenPayloadNull() {
        final GitHubActionsPoliciesApi sut = api();

        final Throwable thrown = catchThrowable(() -> sut.updateOrgActionsPolicy(ORG, 1L, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteOrgActionsPolicy_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteOrgActionsPolicy(ORG, 1L);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE " + BASE + "orgs/github/actions/policies/1");
    }

    @Test
    void getRepoActionsPolicies_shouldReturnPolicies_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-policies.json")));

        final GitHubPage<GitHubActionsPolicy> page = api().getRepoActionsPolicies(OWNER, REPO);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsPolicies().policies());
    }

    @Test
    void getRepoActionsPolicies_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-policies.json")));

        api().getRepoActionsPolicies(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void getRepoActionsPolicies_shouldSendHasParents_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-policies.json")));

        api().getRepoActionsPolicies(
                        OWNER,
                        REPO,
                        ActionsCatalogFixtures.getRepoActionsPoliciesFilter(),
                        GitHubPageNumberRequest.of(1, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("has_parents", "true");
    }

    @Test
    void createRepoActionsPolicy_shouldReturnPolicy_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("actions-policy.json")));

        final GitHubActionsPolicy policy =
                api().createRepoActionsPolicy(OWNER, REPO, ActionsCatalogFixtures.createRepoActionsPolicyPayload());

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsPolicy());
    }

    @Test
    void createRepoActionsPolicy_shouldPost_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("actions-policy.json")));

        api().createRepoActionsPolicy(OWNER, REPO, ActionsCatalogFixtures.createRepoActionsPolicyPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST " + BASE + "repos/octocat/Hello-World/actions/policies");
    }

    @Test
    void createRepoActionsPolicy_shouldThrow_whenPayloadNull() {
        final GitHubActionsPoliciesApi sut = api();

        final Throwable thrown = catchThrowable(() -> sut.createRepoActionsPolicy(OWNER, REPO, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getRepoActionsPolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-policy.json")));

        final GitHubActionsPolicy policy =
                api().getRepoActionsPolicy(OWNER, REPO, 1L).orElseThrow();

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsPolicy());
    }

    @Test
    void getRepoActionsPolicy_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubActionsPolicy> result = api().getRepoActionsPolicy(OWNER, REPO, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void updateRepoActionsPolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-policy.json")));

        final GitHubActionsPolicy policy =
                api().updateRepoActionsPolicy(OWNER, REPO, 1L, ActionsCatalogFixtures.updateRepoActionsPolicyPayload());

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsPolicy());
    }

    @Test
    void updateRepoActionsPolicy_shouldPut_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-policy.json")));

        api().updateRepoActionsPolicy(OWNER, REPO, 1L, ActionsCatalogFixtures.updateRepoActionsPolicyPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "repos/octocat/Hello-World/actions/policies/1");
    }

    @Test
    void updateRepoActionsPolicy_shouldThrow_whenPayloadNull() {
        final GitHubActionsPoliciesApi sut = api();

        final Throwable thrown = catchThrowable(() -> sut.updateRepoActionsPolicy(OWNER, REPO, 1L, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteRepoActionsPolicy_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteRepoActionsPolicy(OWNER, REPO, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE " + BASE + "repos/octocat/Hello-World/actions/policies/1");
    }

    @Test
    void getEnterpriseActionsPolicies_shouldReturnPolicies_whenRequestOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-policies-for-enterprise.json")));

        final GitHubPage<GitHubActionsPolicy> page = api().getEnterpriseActionsPolicies(ENTERPRISE);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsPoliciesForEnterprise().policies());
    }

    @Test
    void getEnterpriseActionsPolicies_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-policies-for-enterprise.json")));

        api().getEnterpriseActionsPolicies(ENTERPRISE, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void createEnterpriseActionsPolicy_shouldReturnPolicy_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("actions-policy.json")));

        final GitHubActionsPolicy policy = api().createEnterpriseActionsPolicy(
                        ENTERPRISE, ActionsCatalogFixtures.createEnterpriseActionsPolicyPayload());

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsPolicy());
    }

    @Test
    void createEnterpriseActionsPolicy_shouldPost_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("actions-policy.json")));

        api().createEnterpriseActionsPolicy(ENTERPRISE, ActionsCatalogFixtures.createEnterpriseActionsPolicyPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST " + BASE + "enterprises/octo-enterprise/actions/policies");
    }

    @Test
    void getEnterpriseActionsPolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-policy.json")));

        final GitHubActionsPolicy policy =
                api().getEnterpriseActionsPolicy(ENTERPRISE, 1L).orElseThrow();

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsPolicy());
    }

    @Test
    void getEnterpriseActionsPolicy_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubActionsPolicy> result = api().getEnterpriseActionsPolicy(ENTERPRISE, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void updateEnterpriseActionsPolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-policy.json")));

        final GitHubActionsPolicy policy = api().updateEnterpriseActionsPolicy(
                        ENTERPRISE, 1L, ActionsCatalogFixtures.updateEnterpriseActionsPolicyPayload());

        assertThat(policy).isEqualTo(ActionsCatalogFixtures.actionsPolicy());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "enterprises/octo-enterprise/actions/policies/1");
    }

    @Test
    void deleteEnterpriseActionsPolicy_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteEnterpriseActionsPolicy(ENTERPRISE, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE " + BASE + "enterprises/octo-enterprise/actions/policies/1");
    }

    private GitHubActionsPoliciesApi api() {
        return new GitHubActionsPoliciesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActionsPoliciesApiTest.class, fileName);
    }

    private static String line(final HttpRequest request) {
        return request.method() + " " + request.uri();
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
