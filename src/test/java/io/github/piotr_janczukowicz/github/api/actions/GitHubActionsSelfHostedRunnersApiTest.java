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
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubActionsSelfHostedRunnersApiTest {

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
        final Throwable thrown = catchThrowable(() -> new GitHubActionsSelfHostedRunnersApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listSelfHostedRunnersForOrg_shouldReturnRunners_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runners.json")));

        final GitHubPage<GitHubRunner> page = api().listSelfHostedRunnersForOrg(ORG);

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.runners().runners());
    }

    @Test
    void listSelfHostedRunnersForOrg_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runners.json")));

        api().listSelfHostedRunnersForOrg(ORG, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void listSelfHostedRunnersForOrg_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runners.json")));

        api().listSelfHostedRunnersForOrg(
                        ORG,
                        GitHubListSelfHostedRunnersForOrgFilterBuilder.builder()
                                .name("linux")
                                .build(),
                        GitHubPageNumberRequest.of(1, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("name", "linux");
    }

    @Test
    void listRunnerApplicationsForOrg_shouldReturnApplications_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("runner-application.json") + "]"));

        final List<GitHubRunnerApplication> applications = api().listRunnerApplicationsForOrg(ORG);

        assertThat(applications).containsExactly(ActionsCatalogFixtures.runnerApplication());
    }

    @Test
    void generateRunnerJitconfigForOrg_shouldReturnConfig_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("runner-jitconfig.json")));

        final GitHubRunnerJitConfig config =
                api().generateRunnerJitconfigForOrg(ORG, ActionsCatalogFixtures.generateRunnerJitconfigForOrgPayload());

        assertThat(config).isEqualTo(ActionsCatalogFixtures.runnerJitConfig());
    }

    @Test
    void createRegistrationTokenForOrg_shouldReturnToken_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("authentication-token.json")));

        final GitHubAuthenticationToken token = api().createRegistrationTokenForOrg(ORG);

        assertThat(token).isEqualTo(ActionsCatalogFixtures.authenticationToken());
    }

    @Test
    void createRemoveTokenForOrg_shouldReturnToken_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("authentication-token.json")));

        final GitHubAuthenticationToken token = api().createRemoveTokenForOrg(ORG);

        assertThat(token).isEqualTo(ActionsCatalogFixtures.authenticationToken());
    }

    @Test
    void getSelfHostedRunnerForOrg_shouldReturnRunner_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner.json")));

        final GitHubRunner runner = api().getSelfHostedRunnerForOrg(ORG, 1L).orElseThrow();

        assertThat(runner).isEqualTo(ActionsCatalogFixtures.runner());
    }

    @Test
    void getSelfHostedRunnerForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubRunner> result = api().getSelfHostedRunnerForOrg(ORG, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void deleteSelfHostedRunnerFromOrg_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteSelfHostedRunnerFromOrg(ORG, 1L);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE " + BASE + "orgs/github/actions/runners/1");
    }

    @Test
    void listLabelsForSelfHostedRunnerForOrg_shouldReturnLabels_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-labels.json")));

        final Optional<GitHubRunnerLabels> labels = api().listLabelsForSelfHostedRunnerForOrg(ORG, 1L);

        assertThat(labels).contains(ActionsCatalogFixtures.runnerLabels());
    }

    @Test
    void addCustomLabelsToSelfHostedRunnerForOrg_shouldReturnLabels_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-labels.json")));

        final GitHubRunnerLabels labels = api().addCustomLabelsToSelfHostedRunnerForOrg(
                        ORG, 1L, ActionsCatalogFixtures.addCustomLabelsToSelfHostedRunnerForOrgPayload());

        assertThat(labels).isEqualTo(ActionsCatalogFixtures.runnerLabels());
    }

    @Test
    void replaceCustomLabelsForSelfHostedRunnerForOrg_shouldReturnLabels_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-labels.json")));

        final GitHubRunnerLabels labels = api().replaceCustomLabelsForSelfHostedRunnerForOrg(
                        ORG, 1L, ActionsCatalogFixtures.replaceCustomLabelsForSelfHostedRunnerForOrgPayload());

        assertThat(labels).isEqualTo(ActionsCatalogFixtures.runnerLabels());
    }

    @Test
    void removeAllCustomLabelsFromSelfHostedRunnerForOrg_shouldReturnLabels_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-labels.json")));

        final GitHubRunnerLabels labels = api().removeAllCustomLabelsFromSelfHostedRunnerForOrg(ORG, 1L);

        assertThat(labels).isEqualTo(ActionsCatalogFixtures.runnerLabels());
    }

    @Test
    void removeCustomLabelFromSelfHostedRunnerForOrg_shouldReturnLabels_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-labels.json")));

        final GitHubRunnerLabels labels = api().removeCustomLabelFromSelfHostedRunnerForOrg(ORG, 1L, "linux");

        assertThat(labels).isEqualTo(ActionsCatalogFixtures.runnerLabels());
    }

    @Test
    void listSelfHostedRunnersForRepo_shouldReturnRunners_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runners.json")));

        final GitHubPage<GitHubRunner> page = api().listSelfHostedRunnersForRepo(OWNER, REPO);

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.runners().runners());
    }

    @Test
    void listSelfHostedRunnersForRepo_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runners.json")));

        api().listSelfHostedRunnersForRepo(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void listSelfHostedRunnersForRepo_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runners.json")));

        api().listSelfHostedRunnersForRepo(
                        OWNER,
                        REPO,
                        GitHubListSelfHostedRunnersForRepoFilterBuilder.builder()
                                .name("linux")
                                .build(),
                        GitHubPageNumberRequest.of(1, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("name", "linux");
    }

    @Test
    void listRunnerApplicationsForRepo_shouldReturnApplications_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("runner-application.json") + "]"));

        final List<GitHubRunnerApplication> applications = api().listRunnerApplicationsForRepo(OWNER, REPO);

        assertThat(applications).containsExactly(ActionsCatalogFixtures.runnerApplication());
    }

    @Test
    void generateRunnerJitconfigForRepo_shouldReturnConfig_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("runner-jitconfig.json")));

        final GitHubRunnerJitConfig config = api().generateRunnerJitconfigForRepo(
                        OWNER, REPO, ActionsCatalogFixtures.generateRunnerJitconfigForRepoPayload());

        assertThat(config).isEqualTo(ActionsCatalogFixtures.runnerJitConfig());
    }

    @Test
    void createRegistrationTokenForRepo_shouldReturnToken_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("authentication-token.json")));

        final GitHubAuthenticationToken token = api().createRegistrationTokenForRepo(OWNER, REPO);

        assertThat(token).isEqualTo(ActionsCatalogFixtures.authenticationToken());
    }

    @Test
    void createRemoveTokenForRepo_shouldReturnToken_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("authentication-token.json")));

        final GitHubAuthenticationToken token = api().createRemoveTokenForRepo(OWNER, REPO);

        assertThat(token).isEqualTo(ActionsCatalogFixtures.authenticationToken());
    }

    @Test
    void getSelfHostedRunnerForRepo_shouldReturnRunner_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner.json")));

        final GitHubRunner runner =
                api().getSelfHostedRunnerForRepo(OWNER, REPO, 1L).orElseThrow();

        assertThat(runner).isEqualTo(ActionsCatalogFixtures.runner());
    }

    @Test
    void getSelfHostedRunnerForRepo_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubRunner> result = api().getSelfHostedRunnerForRepo(OWNER, REPO, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void deleteSelfHostedRunnerFromRepo_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteSelfHostedRunnerFromRepo(OWNER, REPO, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE " + BASE + "repos/octocat/Hello-World/actions/runners/1");
    }

    @Test
    void listLabelsForSelfHostedRunnerForRepo_shouldReturnLabels_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-labels.json")));

        final Optional<GitHubRunnerLabels> labels = api().listLabelsForSelfHostedRunnerForRepo(OWNER, REPO, 1L);

        assertThat(labels).contains(ActionsCatalogFixtures.runnerLabels());
    }

    @Test
    void addCustomLabelsToSelfHostedRunnerForRepo_shouldReturnLabels_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-labels.json")));

        final GitHubRunnerLabels labels = api().addCustomLabelsToSelfHostedRunnerForRepo(
                        OWNER, REPO, 1L, ActionsCatalogFixtures.addCustomLabelsToSelfHostedRunnerForRepoPayload());

        assertThat(labels).isEqualTo(ActionsCatalogFixtures.runnerLabels());
    }

    @Test
    void replaceCustomLabelsForSelfHostedRunnerForRepo_shouldReturnLabels_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-labels.json")));

        final GitHubRunnerLabels labels = api().replaceCustomLabelsForSelfHostedRunnerForRepo(
                        OWNER, REPO, 1L, ActionsCatalogFixtures.replaceCustomLabelsForSelfHostedRunnerForRepoPayload());

        assertThat(labels).isEqualTo(ActionsCatalogFixtures.runnerLabels());
    }

    @Test
    void removeAllCustomLabelsFromSelfHostedRunnerForRepo_shouldReturnLabels_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-labels.json")));

        final GitHubRunnerLabels labels = api().removeAllCustomLabelsFromSelfHostedRunnerForRepo(OWNER, REPO, 1L);

        assertThat(labels).isEqualTo(ActionsCatalogFixtures.runnerLabels());
    }

    @Test
    void removeCustomLabelFromSelfHostedRunnerForRepo_shouldReturnLabels_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner-labels.json")));

        final GitHubRunnerLabels labels = api().removeCustomLabelFromSelfHostedRunnerForRepo(OWNER, REPO, 1L, "linux");

        assertThat(labels).isEqualTo(ActionsCatalogFixtures.runnerLabels());
    }

    @Test
    void getRunnerVersionDeprecationForOrg_shouldReturnDeprecation_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-version-deprecation.json")));

        final GitHubRunnerVersionDeprecation deprecation =
                api().getRunnerVersionDeprecationForOrg(ORG, "2.300.0").orElseThrow();

        assertThat(deprecation).isEqualTo(ActionsCatalogFixtures.runnerVersionDeprecation());
    }

    @Test
    void getRunnerVersionDeprecationForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubRunnerVersionDeprecation> result = api().getRunnerVersionDeprecationForOrg(ORG, "2.300.0");

        assertThat(result).isEmpty();
    }

    @Test
    void getRunnerVersionDeprecationForOrg_shouldRequestVersionPath_whenCalled() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-version-deprecation.json")));

        api().getRunnerVersionDeprecationForOrg(ORG, "2.300.0");

        assertThat(line(captureSingleRequest()))
                .isEqualTo("GET " + BASE + "orgs/github/actions/runners/deprecations/2.300.0");
    }

    @Test
    void getRunnerVersionDeprecationForRepo_shouldReturnDeprecation_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-version-deprecation.json")));

        final GitHubRunnerVersionDeprecation deprecation =
                api().getRunnerVersionDeprecationForRepo(OWNER, REPO, "2.300.0").orElseThrow();

        assertThat(deprecation).isEqualTo(ActionsCatalogFixtures.runnerVersionDeprecation());
    }

    @Test
    void getRunnerVersionDeprecationForRepo_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubRunnerVersionDeprecation> result =
                api().getRunnerVersionDeprecationForRepo(OWNER, REPO, "2.300.0");

        assertThat(result).isEmpty();
    }

    @Test
    void getRunnerVersionDeprecationForRepo_shouldRequestVersionPath_whenCalled() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-version-deprecation.json")));

        api().getRunnerVersionDeprecationForRepo(OWNER, REPO, "2.300.0");

        assertThat(line(captureSingleRequest()))
                .isEqualTo("GET " + BASE + "repos/octocat/Hello-World/actions/runners/deprecations/2.300.0");
    }

    @Test
    void listSelfHostedRunnersForEnterprise_shouldReturnRunners_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runners-for-enterprise.json")));

        final GitHubPage<GitHubRunner> page = api().listSelfHostedRunnersForEnterprise(ENTERPRISE);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.runnersForEnterprise().runners());
    }

    @Test
    void listSelfHostedRunnersForEnterprise_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runners-for-enterprise.json")));

        api().listSelfHostedRunnersForEnterprise(ENTERPRISE, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void listSelfHostedRunnersForEnterprise_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runners-for-enterprise.json")));

        api().listSelfHostedRunnersForEnterprise(
                        ENTERPRISE,
                        ActionsCatalogFixtures.listSelfHostedRunnersForEnterpriseFilter(),
                        GitHubPageNumberRequest.of(1, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("name", "name-sample");
    }

    @Test
    void listSelfHostedRunnersForEnterprise_shouldRequestEnterprisePath_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runners-for-enterprise.json")));

        api().listSelfHostedRunnersForEnterprise(ENTERPRISE, GitHubPageNumberRequest.of(1, 10));

        assertThat(line(captureSingleRequest()))
                .startsWith("GET " + BASE + "enterprises/octo-enterprise/actions/runners");
    }

    @Test
    void listRunnerApplicationsForEnterprise_shouldReturnApplications_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("runner-application.json") + "]"));

        final List<GitHubRunnerApplication> applications = api().listRunnerApplicationsForEnterprise(ENTERPRISE);

        assertThat(applications).containsExactly(ActionsCatalogFixtures.runnerApplication());
    }

    @Test
    void generateRunnerJitconfigForEnterprise_shouldReturnConfig_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("runner-jitconfig-for-enterprise.json")));

        final GitHubRunnerJitConfigForEnterprise config = api().generateRunnerJitconfigForEnterprise(
                        ENTERPRISE, ActionsCatalogFixtures.generateRunnerJitconfigForEnterprisePayload());

        assertThat(config).isEqualTo(ActionsCatalogFixtures.runnerJitConfigForEnterprise());
    }

    @Test
    void generateRunnerJitconfigForEnterprise_shouldPost_whenPayloadProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("runner-jitconfig-for-enterprise.json")));

        api().generateRunnerJitconfigForEnterprise(
                        ENTERPRISE, ActionsCatalogFixtures.generateRunnerJitconfigForEnterprisePayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST " + BASE + "enterprises/octo-enterprise/actions/runners/generate-jitconfig");
    }

    @Test
    void createRegistrationTokenForEnterprise_shouldReturnToken_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("authentication-token.json")));

        final GitHubAuthenticationToken token = api().createRegistrationTokenForEnterprise(ENTERPRISE);

        assertThat(token).isEqualTo(ActionsCatalogFixtures.authenticationToken());
    }

    @Test
    void createRemoveTokenForEnterprise_shouldReturnToken_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("authentication-token.json")));

        final GitHubAuthenticationToken token = api().createRemoveTokenForEnterprise(ENTERPRISE);

        assertThat(token).isEqualTo(ActionsCatalogFixtures.authenticationToken());
    }

    @Test
    void getSelfHostedRunnerForEnterprise_shouldReturnRunner_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("runner.json")));

        final GitHubRunner runner =
                api().getSelfHostedRunnerForEnterprise(ENTERPRISE, 1L).orElseThrow();

        assertThat(runner).isEqualTo(ActionsCatalogFixtures.runner());
    }

    @Test
    void getSelfHostedRunnerForEnterprise_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubRunner> result = api().getSelfHostedRunnerForEnterprise(ENTERPRISE, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void deleteSelfHostedRunnerFromEnterprise_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteSelfHostedRunnerFromEnterprise(ENTERPRISE, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE " + BASE + "enterprises/octo-enterprise/actions/runners/1");
    }

    @Test
    void getRunnerVersionDeprecationForEnterprise_shouldReturnDeprecation_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-version-deprecation-for-enterprise.json")));

        final GitHubRunnerVersionDeprecationForEnterprise deprecation = api().getRunnerVersionDeprecationForEnterprise(
                        ENTERPRISE, "2.300.0")
                .orElseThrow();

        assertThat(deprecation).isEqualTo(ActionsCatalogFixtures.runnerVersionDeprecationForEnterprise());
    }

    @Test
    void getRunnerVersionDeprecationForEnterprise_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubRunnerVersionDeprecationForEnterprise> result =
                api().getRunnerVersionDeprecationForEnterprise(ENTERPRISE, "2.300.0");

        assertThat(result).isEmpty();
    }

    @Test
    void getRunnerVersionDeprecationForEnterprise_shouldRequestVersionPath_whenCalled() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-version-deprecation-for-enterprise.json")));

        api().getRunnerVersionDeprecationForEnterprise(ENTERPRISE, "2.300.0");

        assertThat(line(captureSingleRequest()))
                .isEqualTo("GET " + BASE + "enterprises/octo-enterprise/actions/runners/deprecations/2.300.0");
    }

    @Test
    void listLabelsForSelfHostedRunnerForEnterprise_shouldReturnLabels_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-labels-for-enterprise.json")));

        final Optional<GitHubRunnerLabelsForEnterprise> labels =
                api().listLabelsForSelfHostedRunnerForEnterprise(ENTERPRISE, 1L);

        assertThat(labels).contains(ActionsCatalogFixtures.runnerLabelsForEnterprise());
    }

    @Test
    void listLabelsForSelfHostedRunnerForEnterprise_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubRunnerLabelsForEnterprise> labels =
                api().listLabelsForSelfHostedRunnerForEnterprise(ENTERPRISE, 1L);

        assertThat(labels).isEmpty();
    }

    @Test
    void addCustomLabelsToSelfHostedRunnerForEnterprise_shouldReturnLabels_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-labels-for-enterprise.json")));

        final GitHubRunnerLabelsForEnterprise labels = api().addCustomLabelsToSelfHostedRunnerForEnterprise(
                        ENTERPRISE, 1L, ActionsCatalogFixtures.addCustomLabelsToSelfHostedRunnerForEnterprisePayload());

        assertThat(labels).isEqualTo(ActionsCatalogFixtures.runnerLabelsForEnterprise());
    }

    @Test
    void replaceCustomLabelsForSelfHostedRunnerForEnterprise_shouldSendPut_whenPayloadProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-labels-for-enterprise.json")));

        final GitHubRunnerLabelsForEnterprise labels = api().replaceCustomLabelsForSelfHostedRunnerForEnterprise(
                        ENTERPRISE,
                        1L,
                        ActionsCatalogFixtures.setCustomLabelsForSelfHostedRunnerForEnterprisePayload());

        assertThat(labels).isEqualTo(ActionsCatalogFixtures.runnerLabelsForEnterprise());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "enterprises/octo-enterprise/actions/runners/1/labels");
    }

    @Test
    void removeAllCustomLabelsFromSelfHostedRunnerForEnterprise_shouldReturnLabels_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-labels-for-enterprise.json")));

        final GitHubRunnerLabelsForEnterprise labels =
                api().removeAllCustomLabelsFromSelfHostedRunnerForEnterprise(ENTERPRISE, 1L);

        assertThat(labels).isEqualTo(ActionsCatalogFixtures.runnerLabelsForEnterprise());
    }

    @Test
    void removeCustomLabelFromSelfHostedRunnerForEnterprise_shouldRequestLabelPath_whenCalled() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("runner-labels-for-enterprise.json")));

        final GitHubRunnerLabelsForEnterprise labels =
                api().removeCustomLabelFromSelfHostedRunnerForEnterprise(ENTERPRISE, 1L, "gpu");

        assertThat(labels).isEqualTo(ActionsCatalogFixtures.runnerLabelsForEnterprise());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE " + BASE + "enterprises/octo-enterprise/actions/runners/1/labels/gpu");
    }

    private GitHubActionsSelfHostedRunnersApi api() {
        return new GitHubActionsSelfHostedRunnersApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActionsSelfHostedRunnersApiTest.class, fileName);
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
