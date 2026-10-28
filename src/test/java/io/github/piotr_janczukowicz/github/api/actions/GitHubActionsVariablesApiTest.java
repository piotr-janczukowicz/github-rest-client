package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.agents.AgentsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.agents.GitHubActionsVariable;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationActionsVariable;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
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
class GitHubActionsVariablesApiTest {

    private static final String ORG = "github";
    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ENVIRONMENT = "production";
    private static final String NAME = "DEPLOY_ENV";
    private static final String BASE = "https://api.github.com/";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsVariablesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listOrgVariables_shouldReturnVariables_whenRequestOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-actions-variables.json")));

        final GitHubPage<GitHubOrganizationActionsVariable> page = api().listOrgVariables(ORG);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.organizationActionsVariables().variables());
    }

    @Test
    void listOrgVariables_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-actions-variables.json")));

        api().listOrgVariables(ORG, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void createOrgVariable_shouldSendPost_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().createOrgVariable(ORG, ActionsCatalogFixtures.createOrgVariablePayload());

        assertThat(line(captureSingleRequest())).isEqualTo("POST " + BASE + "orgs/github/actions/variables");
    }

    @Test
    void getOrgVariable_shouldReturnVariable_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-actions-variable.json")));

        final GitHubOrganizationActionsVariable variable =
                api().getOrgVariable(ORG, NAME).orElseThrow();

        assertThat(variable).isEqualTo(OrganizationsCatalogFixtures.organizationActionsVariable());
    }

    @Test
    void getOrgVariable_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubOrganizationActionsVariable> result = api().getOrgVariable(ORG, NAME);

        assertThat(result).isEmpty();
    }

    @Test
    void updateOrgVariable_shouldSendPatch_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().updateOrgVariable(ORG, NAME, ActionsCatalogFixtures.updateOrgVariablePayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PATCH " + BASE + "orgs/github/actions/variables/DEPLOY_ENV");
    }

    @Test
    void deleteOrgVariable_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteOrgVariable(ORG, NAME);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE " + BASE + "orgs/github/actions/variables/DEPLOY_ENV");
    }

    @Test
    void listSelectedReposForOrgVariable_shouldReturnRepositories_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("selected-repositories.json")));

        final GitHubPage<GitHubMinimalRepository> page = api().listSelectedReposForOrgVariable(ORG, NAME);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.selectedRepositories().repositories());
    }

    @Test
    void listSelectedReposForOrgVariable_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("selected-repositories.json")));

        api().listSelectedReposForOrgVariable(ORG, NAME, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void setSelectedReposForOrgVariable_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setSelectedReposForOrgVariable(ORG, NAME, ActionsCatalogFixtures.setSelectedReposForOrgVariablePayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "orgs/github/actions/variables/DEPLOY_ENV/repositories");
    }

    @Test
    void addSelectedRepoToOrgVariable_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().addSelectedRepoToOrgVariable(ORG, NAME, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "orgs/github/actions/variables/DEPLOY_ENV/repositories/1");
    }

    @Test
    void removeSelectedRepoFromOrgVariable_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeSelectedRepoFromOrgVariable(ORG, NAME, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE " + BASE + "orgs/github/actions/variables/DEPLOY_ENV/repositories/1");
    }

    @Test
    void listRepoOrganizationVariables_shouldReturnVariables_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-variables.json")));

        final GitHubPage<GitHubActionsVariable> page = api().listRepoOrganizationVariables(OWNER, REPO);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsVariables().variables());
    }

    @Test
    void listRepoOrganizationVariables_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-variables.json")));

        api().listRepoOrganizationVariables(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void listRepoVariables_shouldReturnVariables_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-variables.json")));

        final GitHubPage<GitHubActionsVariable> page = api().listRepoVariables(OWNER, REPO);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsVariables().variables());
    }

    @Test
    void listRepoVariables_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-variables.json")));

        api().listRepoVariables(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void createRepoVariable_shouldSendPost_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().createRepoVariable(OWNER, REPO, ActionsCatalogFixtures.createRepoVariablePayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST " + BASE + "repos/octocat/Hello-World/actions/variables");
    }

    @Test
    void getRepoVariable_shouldReturnVariable_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-variable.json")));

        final GitHubActionsVariable variable =
                api().getRepoVariable(OWNER, REPO, NAME).orElseThrow();

        assertThat(variable).isEqualTo(AgentsCatalogFixtures.actionsVariable());
    }

    @Test
    void getRepoVariable_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubActionsVariable> result = api().getRepoVariable(OWNER, REPO, NAME);

        assertThat(result).isEmpty();
    }

    @Test
    void updateRepoVariable_shouldSendPatch_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().updateRepoVariable(OWNER, REPO, NAME, ActionsCatalogFixtures.updateRepoVariablePayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PATCH " + BASE + "repos/octocat/Hello-World/actions/variables/DEPLOY_ENV");
    }

    @Test
    void deleteRepoVariable_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteRepoVariable(OWNER, REPO, NAME);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE " + BASE + "repos/octocat/Hello-World/actions/variables/DEPLOY_ENV");
    }

    @Test
    void listEnvironmentVariables_shouldReturnVariables_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-variables.json")));

        final GitHubPage<GitHubActionsVariable> page = api().listEnvironmentVariables(OWNER, REPO, ENVIRONMENT);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsVariables().variables());
    }

    @Test
    void listEnvironmentVariables_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-variables.json")));

        api().listEnvironmentVariables(OWNER, REPO, ENVIRONMENT, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void createEnvironmentVariable_shouldSendPost_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().createEnvironmentVariable(
                        OWNER, REPO, ENVIRONMENT, ActionsCatalogFixtures.createEnvironmentVariablePayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST " + BASE + "repos/octocat/Hello-World/environments/production/variables");
    }

    @Test
    void getEnvironmentVariable_shouldReturnVariable_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-variable.json")));

        final GitHubActionsVariable variable =
                api().getEnvironmentVariable(OWNER, REPO, ENVIRONMENT, NAME).orElseThrow();

        assertThat(variable).isEqualTo(AgentsCatalogFixtures.actionsVariable());
    }

    @Test
    void getEnvironmentVariable_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubActionsVariable> result = api().getEnvironmentVariable(OWNER, REPO, ENVIRONMENT, NAME);

        assertThat(result).isEmpty();
    }

    @Test
    void updateEnvironmentVariable_shouldSendPatch_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().updateEnvironmentVariable(
                        OWNER, REPO, ENVIRONMENT, NAME, ActionsCatalogFixtures.updateEnvironmentVariablePayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PATCH " + BASE + "repos/octocat/Hello-World/environments/production/variables/DEPLOY_ENV");
    }

    @Test
    void deleteEnvironmentVariable_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteEnvironmentVariable(OWNER, REPO, ENVIRONMENT, NAME);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE " + BASE + "repos/octocat/Hello-World/environments/production/variables/DEPLOY_ENV");
    }

    private GitHubActionsVariablesApi api() {
        return new GitHubActionsVariablesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActionsVariablesApiTest.class, fileName);
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
