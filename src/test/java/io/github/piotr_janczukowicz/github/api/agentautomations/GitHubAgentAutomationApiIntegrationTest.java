package io.github.piotr_janczukowicz.github.api.agentautomations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.agentAutomationsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubAgentAutomationApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String AUTOMATION_ID = "automation-1";
    private static final String AUTOMATIONS_PATH = "/agents/automations";
    private static final String AVAILABLE_TOOLS_PATH = "/agents/automations/available-tools";
    private static final String AVAILABLE_TRIGGERS_PATH = "/agents/automations/available-triggers";
    private static final String AUTOMATION_PATH = "/agents/automations/automation-1";
    private static final String AUTOMATION_TASKS_PATH = "/agents/automations/automation-1/tasks";
    private static final String REPO_AUTOMATIONS_PATH = "/agents/repos/octocat/Hello-World/automations";
    private static final String REPO_AUTOMATION_PATH = "/agents/repos/octocat/Hello-World/automations/automation-1";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listAutomations_shouldReturnAutomations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                AUTOMATIONS_PATH,
                okJson(listJsonBytes(GitHubAgentAutomationApiIntegrationTest.class, "list-automations-item.json")));

        final GitHubPage<GitHubListAutomationsItem> page;
        try (final GitHubApiWireMockSupport.AgentAutomationsApiClient automations = agentAutomationsApi(HTTP)) {
            page = automations.api().listAutomations();
        }

        assertThat(page.items()).containsExactly(AgentAutomationsCatalogFixtures.listAutomationsItem());
        HTTP.verifyRequested(GET, AUTOMATIONS_PATH);
        HTTP.verifyQueryParam(GET, AUTOMATIONS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, AUTOMATIONS_PATH, "per_page", "100");
    }

    @Test
    void listAutomations_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                AUTOMATIONS_PATH,
                okJson(listJsonBytes(GitHubAgentAutomationApiIntegrationTest.class, "list-automations-item.json"))
                        .header(LINK, linkNext(HTTP.uri(AUTOMATIONS_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubAgentAutomationApiIntegrationTest.class, "list-automations-item.json")));

        final GitHubPage<GitHubListAutomationsItem> first;
        final Optional<GitHubPage<GitHubListAutomationsItem>> second;
        try (final GitHubApiWireMockSupport.AgentAutomationsApiClient automations = agentAutomationsApi(HTTP)) {
            first = automations.api().listAutomations();
            second = first.next();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactly(AgentAutomationsCatalogFixtures.listAutomationsItem());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items())
                    .containsExactly(AgentAutomationsCatalogFixtures.listAutomationsItem());
        });
        HTTP.verifyRequested(GET, AUTOMATIONS_PATH, 2);
    }

    @Test
    void listAutomations_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                AUTOMATIONS_PATH,
                okJson(listJsonBytes(GitHubAgentAutomationApiIntegrationTest.class, "list-automations-item.json")));

        try (final GitHubApiWireMockSupport.AgentAutomationsApiClient automations = agentAutomationsApi(HTTP)) {
            automations.api().listAutomations(GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, AUTOMATIONS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, AUTOMATIONS_PATH, "per_page", "10");
    }

    @Test
    void listAvailableAutomationTools_shouldReturnTools_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                AVAILABLE_TOOLS_PATH,
                okJson(listJsonBytes(
                        GitHubAgentAutomationApiIntegrationTest.class, "list-available-automation-tools-item.json")));

        final List<GitHubListAvailableAutomationToolsItem> tools;
        try (final GitHubApiWireMockSupport.AgentAutomationsApiClient automations = agentAutomationsApi(HTTP)) {
            tools = automations.api().listAvailableAutomationTools();
        }

        assertThat(tools).containsExactly(AgentAutomationsCatalogFixtures.listAvailableAutomationToolsItem());
        HTTP.verifyRequested(GET, AVAILABLE_TOOLS_PATH);
    }

    @Test
    void listAvailableAutomationTriggers_shouldReturnTriggers_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                AVAILABLE_TRIGGERS_PATH,
                okJson(listJsonBytes(
                        GitHubAgentAutomationApiIntegrationTest.class,
                        "list-available-automation-triggers-item.json")));

        final List<GitHubListAvailableAutomationTriggersItem> triggers;
        try (final GitHubApiWireMockSupport.AgentAutomationsApiClient automations = agentAutomationsApi(HTTP)) {
            triggers = automations.api().listAvailableAutomationTriggers();
        }

        assertThat(triggers).containsExactly(AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItem());
        HTTP.verifyRequested(GET, AVAILABLE_TRIGGERS_PATH);
    }

    @Test
    void getAutomation_shouldReturnAutomation_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                AUTOMATION_PATH,
                okJson(jsonBytes(GitHubAgentAutomationApiIntegrationTest.class, "get-automation-response.json")));

        final Optional<GitHubGetAutomationResponse> automation;
        try (final GitHubApiWireMockSupport.AgentAutomationsApiClient automations = agentAutomationsApi(HTTP)) {
            automation = automations.api().getAutomation(AUTOMATION_ID);
        }

        assertThat(automation).contains(AgentAutomationsCatalogFixtures.getAutomationResponse());
        HTTP.verifyRequested(GET, AUTOMATION_PATH);
    }

    @Test
    void getAutomation_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, AUTOMATION_PATH, notFound());

        final Optional<GitHubGetAutomationResponse> automation;
        try (final GitHubApiWireMockSupport.AgentAutomationsApiClient automations = agentAutomationsApi(HTTP)) {
            automation = automations.api().getAutomation(AUTOMATION_ID);
        }

        assertThat(automation).isEmpty();
    }

    @Test
    void listAutomationTasks_shouldReturnTasks_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                AUTOMATION_TASKS_PATH,
                okJson(jsonBytes(
                        GitHubAgentAutomationApiIntegrationTest.class, "list-automation-tasks-response.json")));

        final GitHubPage<GitHubListAutomationTasksTask> page;
        try (final GitHubApiWireMockSupport.AgentAutomationsApiClient automations = agentAutomationsApi(HTTP)) {
            page = automations.api().listAutomationTasks(AUTOMATION_ID);
        }

        assertThat(page.items())
                .containsExactlyElementsOf(AgentAutomationsCatalogFixtures.listAutomationTasksResponse()
                        .tasks());
        HTTP.verifyRequested(GET, AUTOMATION_TASKS_PATH);
        HTTP.verifyQueryParam(GET, AUTOMATION_TASKS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, AUTOMATION_TASKS_PATH, "per_page", "100");
    }

    @Test
    void listRepoAutomations_shouldReturnAutomations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_AUTOMATIONS_PATH,
                okJson(jsonBytes(
                        GitHubAgentAutomationApiIntegrationTest.class, "list-repo-automations-response.json")));

        final GitHubPage<GitHubListRepoAutomationsAutomation> page;
        try (final GitHubApiWireMockSupport.AgentAutomationsApiClient automations = agentAutomationsApi(HTTP)) {
            page = automations.api().listRepoAutomations(OWNER, REPO);
        }

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(AgentAutomationsCatalogFixtures.listRepoAutomationsResponse()
                            .automations());
            softly.assertThat(page.totalCount()).isEqualTo(1);
        });
        HTTP.verifyRequested(GET, REPO_AUTOMATIONS_PATH);
        HTTP.verifyQueryParam(GET, REPO_AUTOMATIONS_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, REPO_AUTOMATIONS_PATH, "per_page", "100");
    }

    @Test
    void createRepoAutomation_shouldReturnAutomation_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                REPO_AUTOMATIONS_PATH,
                createdJson(jsonBytes(
                        GitHubAgentAutomationApiIntegrationTest.class, "create-repo-automation-response.json")));

        final GitHubCreateRepoAutomationResponse automation;
        try (final GitHubApiWireMockSupport.AgentAutomationsApiClient automations = agentAutomationsApi(HTTP)) {
            automation = automations
                    .api()
                    .createRepoAutomation(OWNER, REPO, AgentAutomationsCatalogFixtures.createRepoAutomationPayload());
        }

        assertThat(automation).isEqualTo(AgentAutomationsCatalogFixtures.createRepoAutomationResponse());
        HTTP.verifyRequested(POST, REPO_AUTOMATIONS_PATH);
    }

    @Test
    void getRepoAutomation_shouldReturnAutomation_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_AUTOMATION_PATH,
                okJson(jsonBytes(GitHubAgentAutomationApiIntegrationTest.class, "get-repo-automation-response.json")));

        final Optional<GitHubGetRepoAutomationResponse> automation;
        try (final GitHubApiWireMockSupport.AgentAutomationsApiClient automations = agentAutomationsApi(HTTP)) {
            automation = automations.api().getRepoAutomation(OWNER, REPO, AUTOMATION_ID);
        }

        assertThat(automation).contains(AgentAutomationsCatalogFixtures.getRepoAutomationResponse());
        HTTP.verifyRequested(GET, REPO_AUTOMATION_PATH);
    }

    @Test
    void getRepoAutomation_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REPO_AUTOMATION_PATH, notFound());

        final Optional<GitHubGetRepoAutomationResponse> automation;
        try (final GitHubApiWireMockSupport.AgentAutomationsApiClient automations = agentAutomationsApi(HTTP)) {
            automation = automations.api().getRepoAutomation(OWNER, REPO, AUTOMATION_ID);
        }

        assertThat(automation).isEmpty();
    }

    @Test
    void editRepoAutomation_shouldReturnAutomation_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                REPO_AUTOMATION_PATH,
                okJson(jsonBytes(GitHubAgentAutomationApiIntegrationTest.class, "edit-repo-automation-response.json")));

        final GitHubEditRepoAutomationResponse automation;
        try (final GitHubApiWireMockSupport.AgentAutomationsApiClient automations = agentAutomationsApi(HTTP)) {
            automation = automations
                    .api()
                    .editRepoAutomation(
                            OWNER, REPO, AUTOMATION_ID, AgentAutomationsCatalogFixtures.editRepoAutomationPayload());
        }

        assertThat(automation).isEqualTo(AgentAutomationsCatalogFixtures.editRepoAutomationResponse());
        HTTP.verifyRequested(PATCH, REPO_AUTOMATION_PATH);
    }

    @Test
    void deleteRepoAutomation_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, REPO_AUTOMATION_PATH, noContent());

        try (final GitHubApiWireMockSupport.AgentAutomationsApiClient automations = agentAutomationsApi(HTTP)) {
            automations.api().deleteRepoAutomation(OWNER, REPO, AUTOMATION_ID);
        }

        HTTP.verifyRequested(DELETE, REPO_AUTOMATION_PATH);
    }
}
