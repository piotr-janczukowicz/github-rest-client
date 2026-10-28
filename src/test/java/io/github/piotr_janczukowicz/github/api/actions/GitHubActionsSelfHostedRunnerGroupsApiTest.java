package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
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
class GitHubActionsSelfHostedRunnerGroupsApiTest {

    private static final String ORG = "github";
    private static final String BASE = "https://api.github.com/orgs/github/actions/runner-groups";
    private static final String ENTERPRISE = "github";
    private static final String ENTERPRISE_BASE = "https://api.github.com/enterprises/github/actions/runner-groups";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsSelfHostedRunnerGroupsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listSelfHostedRunnerGroupsForOrg_shouldReturnGroups_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-groups.json")));

        final GitHubPage<GitHubRunnerGroupsOrg> page = api().listSelfHostedRunnerGroupsForOrg(ORG);

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.runnerGroups().runnerGroups());
    }

    @Test
    void listSelfHostedRunnerGroupsForOrg_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-groups.json")));

        api().listSelfHostedRunnerGroupsForOrg(ORG, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void listSelfHostedRunnerGroupsForOrg_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-groups.json")));

        api().listSelfHostedRunnerGroupsForOrg(
                        ORG,
                        GitHubListSelfHostedRunnerGroupsForOrgFilterBuilder.builder()
                                .visibleToRepository("octo")
                                .build(),
                        GitHubPageNumberRequest.of(1, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("visible_to_repository", "octo");
    }

    @Test
    void createSelfHostedRunnerGroupForOrg_shouldReturnGroup_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("runner-groups-org.json")));

        final GitHubRunnerGroupsOrg group = api().createSelfHostedRunnerGroupForOrg(
                        ORG, ActionsCatalogFixtures.createSelfHostedRunnerGroupForOrgPayload());

        assertThat(group).isEqualTo(ActionsCatalogFixtures.runnerGroupsOrg());
    }

    @Test
    void getSelfHostedRunnerGroupForOrg_shouldReturnGroup_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-groups-org.json")));

        final GitHubRunnerGroupsOrg group =
                api().getSelfHostedRunnerGroupForOrg(ORG, 1L).orElseThrow();

        assertThat(group).isEqualTo(ActionsCatalogFixtures.runnerGroupsOrg());
    }

    @Test
    void getSelfHostedRunnerGroupForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubRunnerGroupsOrg> result = api().getSelfHostedRunnerGroupForOrg(ORG, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void updateSelfHostedRunnerGroupForOrg_shouldReturnGroup_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-groups-org.json")));

        final GitHubRunnerGroupsOrg group = api().updateSelfHostedRunnerGroupForOrg(
                        ORG, 1L, ActionsCatalogFixtures.updateSelfHostedRunnerGroupForOrgPayload());

        assertThat(group).isEqualTo(ActionsCatalogFixtures.runnerGroupsOrg());
    }

    @Test
    void deleteSelfHostedRunnerGroupFromOrg_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteSelfHostedRunnerGroupFromOrg(ORG, 1L);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE " + BASE + "/1");
    }

    @Test
    void listGitHubHostedRunnersInGroupForOrg_shouldReturnRunners_whenRequestOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-group-hosted-runners.json")));

        final GitHubPage<GitHubActionsHostedRunner> page = api().listGitHubHostedRunnersInGroupForOrg(ORG, 1L);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.runnerGroupHostedRunners().runners());
    }

    @Test
    void listGitHubHostedRunnersInGroupForOrg_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-group-hosted-runners.json")));

        api().listGitHubHostedRunnersInGroupForOrg(ORG, 1L, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void listRepoAccessToSelfHostedRunnerGroupInOrg_shouldReturnRepositories_whenRequestOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-group-repositories.json")));

        final GitHubPage<GitHubMinimalRepository> page = api().listRepoAccessToSelfHostedRunnerGroupInOrg(ORG, 1L);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.runnerGroupRepositories().repositories());
    }

    @Test
    void listRepoAccessToSelfHostedRunnerGroupInOrg_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-group-repositories.json")));

        api().listRepoAccessToSelfHostedRunnerGroupInOrg(ORG, 1L, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void setRepoAccessToSelfHostedRunnerGroupInOrg_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setRepoAccessToSelfHostedRunnerGroupInOrg(
                        ORG, 1L, ActionsCatalogFixtures.setRepoAccessToSelfHostedRunnerGroupInOrgPayload());

        assertThat(line(captureSingleRequest())).isEqualTo("PUT " + BASE + "/1/repositories");
    }

    @Test
    void addRepoAccessToSelfHostedRunnerGroupInOrg_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().addRepoAccessToSelfHostedRunnerGroupInOrg(ORG, 1L, 2L);

        assertThat(line(captureSingleRequest())).isEqualTo("PUT " + BASE + "/1/repositories/2");
    }

    @Test
    void removeRepoAccessToSelfHostedRunnerGroupInOrg_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeRepoAccessToSelfHostedRunnerGroupInOrg(ORG, 1L, 2L);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE " + BASE + "/1/repositories/2");
    }

    @Test
    void listSelfHostedRunnersInGroupForOrg_shouldReturnRunners_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-group-runners.json")));

        final GitHubPage<GitHubRunner> page = api().listSelfHostedRunnersInGroupForOrg(ORG, 1L);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.runnerGroupRunners().runners());
    }

    @Test
    void listSelfHostedRunnersInGroupForOrg_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-group-runners.json")));

        api().listSelfHostedRunnersInGroupForOrg(ORG, 1L, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void setSelfHostedRunnersInGroupForOrg_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setSelfHostedRunnersInGroupForOrg(
                        ORG, 1L, ActionsCatalogFixtures.setSelfHostedRunnersInGroupForOrgPayload());

        assertThat(line(captureSingleRequest())).isEqualTo("PUT " + BASE + "/1/runners");
    }

    @Test
    void addSelfHostedRunnerToGroupForOrg_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().addSelfHostedRunnerToGroupForOrg(ORG, 1L, 2L);

        assertThat(line(captureSingleRequest())).isEqualTo("PUT " + BASE + "/1/runners/2");
    }

    @Test
    void removeSelfHostedRunnerFromGroupForOrg_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeSelfHostedRunnerFromGroupForOrg(ORG, 1L, 2L);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE " + BASE + "/1/runners/2");
    }

    @Test
    void listSelfHostedRunnerGroupsForEnterprise_shouldReturnGroups_whenRequestOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-groups-for-enterprise.json")));

        final GitHubPage<GitHubRunnerGroupsEnterprise> page = api().listSelfHostedRunnerGroupsForEnterprise(ENTERPRISE);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.runnerGroupsForEnterprise().runnerGroups());
    }

    @Test
    void listSelfHostedRunnerGroupsForEnterprise_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-groups-for-enterprise.json")));

        api().listSelfHostedRunnerGroupsForEnterprise(ENTERPRISE, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void listSelfHostedRunnerGroupsForEnterprise_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-groups-for-enterprise.json")));

        api().listSelfHostedRunnerGroupsForEnterprise(
                        ENTERPRISE,
                        GitHubListSelfHostedRunnerGroupsForEnterpriseFilterBuilder.builder()
                                .visibleToOrganization("octo")
                                .build(),
                        GitHubPageNumberRequest.of(1, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("visible_to_organization", "octo");
    }

    @Test
    void createSelfHostedRunnerGroupForEnterprise_shouldReturnGroup_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("runner-groups-enterprise.json")));

        final GitHubRunnerGroupsEnterprise group = api().createSelfHostedRunnerGroupForEnterprise(
                        ENTERPRISE, ActionsCatalogFixtures.createSelfHostedRunnerGroupForEnterprisePayload());

        assertThat(group).isEqualTo(ActionsCatalogFixtures.runnerGroupsEnterprise());
    }

    @Test
    void getSelfHostedRunnerGroupForEnterprise_shouldReturnGroup_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-groups-enterprise.json")));

        final GitHubRunnerGroupsEnterprise group =
                api().getSelfHostedRunnerGroupForEnterprise(ENTERPRISE, 1L).orElseThrow();

        assertThat(group).isEqualTo(ActionsCatalogFixtures.runnerGroupsEnterprise());
    }

    @Test
    void getSelfHostedRunnerGroupForEnterprise_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubRunnerGroupsEnterprise> result =
                api().getSelfHostedRunnerGroupForEnterprise(ENTERPRISE, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void updateSelfHostedRunnerGroupForEnterprise_shouldReturnGroup_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-groups-enterprise.json")));

        final GitHubRunnerGroupsEnterprise group = api().updateSelfHostedRunnerGroupForEnterprise(
                        ENTERPRISE, 1L, ActionsCatalogFixtures.updateSelfHostedRunnerGroupForEnterprisePayload());

        assertThat(group).isEqualTo(ActionsCatalogFixtures.runnerGroupsEnterprise());
    }

    @Test
    void deleteSelfHostedRunnerGroupFromEnterprise_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteSelfHostedRunnerGroupFromEnterprise(ENTERPRISE, 1L);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE " + ENTERPRISE_BASE + "/1");
    }

    @Test
    void listOrgAccessToSelfHostedRunnerGroupInEnterprise_shouldReturnOrganizations_whenRequestOmitted()
            throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-group-organizations-for-enterprise.json")));

        final GitHubPage<GitHubOrganizationSimple> page =
                api().listOrgAccessToSelfHostedRunnerGroupInEnterprise(ENTERPRISE, 1L);

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.runnerGroupOrganizationsForEnterprise()
                        .organizations());
    }

    @Test
    void listOrgAccessToSelfHostedRunnerGroupInEnterprise_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-group-organizations-for-enterprise.json")));

        api().listOrgAccessToSelfHostedRunnerGroupInEnterprise(ENTERPRISE, 1L, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void setOrgAccessToSelfHostedRunnerGroupInEnterprise_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setOrgAccessToSelfHostedRunnerGroupInEnterprise(
                        ENTERPRISE,
                        1L,
                        ActionsCatalogFixtures.setOrgAccessToSelfHostedRunnerGroupInEnterprisePayload());

        assertThat(line(captureSingleRequest())).isEqualTo("PUT " + ENTERPRISE_BASE + "/1/organizations");
    }

    @Test
    void addOrgAccessToSelfHostedRunnerGroupInEnterprise_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().addOrgAccessToSelfHostedRunnerGroupInEnterprise(ENTERPRISE, 1L, 2L);

        assertThat(line(captureSingleRequest())).isEqualTo("PUT " + ENTERPRISE_BASE + "/1/organizations/2");
    }

    @Test
    void removeOrgAccessToSelfHostedRunnerGroupInEnterprise_shouldSendDelete_whenResponseIsNoContent()
            throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeOrgAccessToSelfHostedRunnerGroupInEnterprise(ENTERPRISE, 1L, 2L);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE " + ENTERPRISE_BASE + "/1/organizations/2");
    }

    @Test
    void listSelfHostedRunnersInGroupForEnterprise_shouldReturnRunners_whenRequestOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-group-runners-for-enterprise.json")));

        final GitHubPage<GitHubRunner> page = api().listSelfHostedRunnersInGroupForEnterprise(ENTERPRISE, 1L);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.runnerGroupRunnersForEnterprise().runners());
    }

    @Test
    void listSelfHostedRunnersInGroupForEnterprise_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-group-runners-for-enterprise.json")));

        api().listSelfHostedRunnersInGroupForEnterprise(ENTERPRISE, 1L, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void setSelfHostedRunnersInGroupForEnterprise_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setSelfHostedRunnersInGroupForEnterprise(
                        ENTERPRISE, 1L, ActionsCatalogFixtures.setSelfHostedRunnersInGroupForEnterprisePayload());

        assertThat(line(captureSingleRequest())).isEqualTo("PUT " + ENTERPRISE_BASE + "/1/runners");
    }

    @Test
    void addSelfHostedRunnerToGroupForEnterprise_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().addSelfHostedRunnerToGroupForEnterprise(ENTERPRISE, 1L, 2L);

        assertThat(line(captureSingleRequest())).isEqualTo("PUT " + ENTERPRISE_BASE + "/1/runners/2");
    }

    @Test
    void removeSelfHostedRunnerFromGroupForEnterprise_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeSelfHostedRunnerFromGroupForEnterprise(ENTERPRISE, 1L, 2L);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE " + ENTERPRISE_BASE + "/1/runners/2");
    }

    private GitHubActionsSelfHostedRunnerGroupsApi api() {
        return new GitHubActionsSelfHostedRunnerGroupsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActionsSelfHostedRunnerGroupsApiTest.class, fileName);
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
