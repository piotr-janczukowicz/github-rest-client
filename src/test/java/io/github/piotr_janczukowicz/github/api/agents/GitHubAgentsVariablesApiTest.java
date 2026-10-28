package io.github.piotr_janczukowicz.github.api.agents;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationActionsVariable;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.exception.GitHubValidationException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
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
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubAgentsVariablesApiTest {

    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final String NAME = "USERNAME";
    private static final long REPOSITORY_ID = 64_780_797L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubAgentsVariablesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listOrgVariables_shouldReturnVariables_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-actions-variables-page.json")));

        final GitHubPage<GitHubOrganizationActionsVariable> page = api().listOrgVariables(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            AgentsCatalogFixtures.orgActionsVariablesPage().variables());
            softly.assertThat(page.totalCount())
                    .isEqualTo(AgentsCatalogFixtures.orgActionsVariablesPage().totalCount());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octo-org/agents/variables?page=1&per_page=30");
        });
    }

    @Test
    void listOrgVariables_shouldCapPerPageAtThirty_whenDefaultRequested() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-actions-variables-page.json")));

        api().listOrgVariables(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("per_page", "30");
    }

    @Test
    void listOrgVariables_shouldSendPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-actions-variables-page.json")));

        api().listOrgVariables(ORG, GitHubPageNumberRequest.of(2, 30));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "30");
    }

    @Test
    void createOrgVariable_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), "{}"));

        api().createOrgVariable(ORG, AgentsCatalogFixtures.createOrgVariablePayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/octo-org/agents/variables");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void getOrgVariable_shouldReturnVariable_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-actions-variable.json")));

        final Optional<GitHubOrganizationActionsVariable> variable = api().getOrgVariable(ORG, NAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(variable).contains(OrganizationsCatalogFixtures.organizationActionsVariable());
            softly.assertThat(requestUri).hasToString("https://api.github.com/orgs/octo-org/agents/variables/USERNAME");
        });
    }

    @Test
    void getOrgVariable_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubOrganizationActionsVariable> result = api().getOrgVariable(ORG, NAME);

        assertThat(result).isEmpty();
    }

    @Test
    void updateOrgVariable_shouldPatchPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().updateOrgVariable(ORG, NAME, AgentsCatalogFixtures.updateOrgVariablePayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/agents/variables/USERNAME");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void deleteOrgVariable_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteOrgVariable(ORG, NAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/agents/variables/USERNAME");
        });
    }

    @Test
    void listSelectedReposForOrgVariable_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-variable-selected-repositories-page.json")));

        final GitHubPage<GitHubMinimalRepository> page = api().listSelectedReposForOrgVariable(ORG, NAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(AgentsCatalogFixtures.orgVariableSelectedRepositoriesPage()
                            .repositories());
            softly.assertThat(page.totalCount())
                    .isEqualTo(AgentsCatalogFixtures.orgVariableSelectedRepositoriesPage()
                            .totalCount());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octo-org/agents/variables/USERNAME/repositories?"
                            + "page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listSelectedReposForOrgVariable_shouldSendPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-variable-selected-repositories-page.json")));

        api().listSelectedReposForOrgVariable(ORG, NAME, GitHubPageNumberRequest.of(2, 30));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "30");
    }

    @Test
    void setSelectedReposForOrgVariable_shouldPutPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setSelectedReposForOrgVariable(ORG, NAME, AgentsCatalogFixtures.setSelectedReposForOrgVariablePayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/agents/variables/USERNAME/repositories");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void addSelectedRepoToOrgVariable_shouldPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().addSelectedRepoToOrgVariable(ORG, NAME, REPOSITORY_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/orgs/octo-org/agents/variables/USERNAME/repositories/64780797");
        });
    }

    @Test
    void removeSelectedRepoFromOrgVariable_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeSelectedRepoFromOrgVariable(ORG, NAME, REPOSITORY_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/orgs/octo-org/agents/variables/USERNAME/repositories/64780797");
        });
    }

    @Test
    void listRepoOrganizationVariables_shouldReturnVariables_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("repo-organization-variables-page.json")));

        final GitHubPage<GitHubActionsVariable> page = api().listRepoOrganizationVariables(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(AgentsCatalogFixtures.repoOrganizationVariablesPage()
                            .variables());
            softly.assertThat(page.totalCount())
                    .isEqualTo(AgentsCatalogFixtures.repoOrganizationVariablesPage()
                            .totalCount());
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/repos/octocat/hello-world/agents/organization-variables?page=1&per_page=30");
        });
    }

    @Test
    void listRepoVariables_shouldReturnVariables_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-variables-page.json")));

        final GitHubPage<GitHubActionsVariable> page = api().listRepoVariables(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            AgentsCatalogFixtures.actionsVariablesPage().variables());
            softly.assertThat(page.totalCount())
                    .isEqualTo(AgentsCatalogFixtures.actionsVariablesPage().totalCount());
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/repos/octocat/hello-world/agents/variables?page=1&per_page=30");
        });
    }

    @Test
    void listRepoVariables_shouldSendPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-variables-page.json")));

        api().listRepoVariables(OWNER, REPO, GitHubPageNumberRequest.of(2, 30));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "30");
    }

    @Test
    void createRepoVariable_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), "{}"));

        api().createRepoVariable(OWNER, REPO, AgentsCatalogFixtures.createRepoVariablePayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/agents/variables");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void getRepoVariable_shouldReturnVariable_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-variable.json")));

        final Optional<GitHubActionsVariable> variable = api().getRepoVariable(OWNER, REPO, NAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(variable).contains(AgentsCatalogFixtures.actionsVariable());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/agents/variables/USERNAME");
        });
    }

    @Test
    void getRepoVariable_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubActionsVariable> result = api().getRepoVariable(OWNER, REPO, NAME);

        assertThat(result).isEmpty();
    }

    @Test
    void updateRepoVariable_shouldPatchPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().updateRepoVariable(OWNER, REPO, NAME, AgentsCatalogFixtures.updateRepoVariablePayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/agents/variables/USERNAME");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void deleteRepoVariable_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteRepoVariable(OWNER, REPO, NAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/agents/variables/USERNAME");
        });
    }

    @ParameterizedTest
    @MethodSource("nullRequestCalls")
    void validation_shouldThrow_whenRequestNull(final VariablesCall call) {
        final GitHubAgentsVariablesApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @ParameterizedTest
    @MethodSource("nullPayloadCalls")
    void validation_shouldThrow_whenPayloadNull(final VariablesCall call) {
        final GitHubAgentsVariablesApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @ParameterizedTest
    @MethodSource("invalidCalls")
    void call_shouldThrowIllegalArgument_whenInvalid(final VariablesCall call, final String messageFragment) {
        final GitHubAgentsVariablesApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @Test
    void listOrgVariables_shouldThrow_whenPerPageExceedsMaximum() {
        final GitHubAgentsVariablesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listOrgVariables(ORG, GitHubPageNumberRequest.of(1, 31)));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void listSelectedReposForOrgVariable_shouldThrowApiException_whenResponseIsConflict() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CONFLICT.code(), null));
        final GitHubAgentsVariablesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listSelectedReposForOrgVariable(ORG, NAME));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
        assertThat(((GitHubApiException) thrown).statusCode()).isEqualTo(HttpStatus.CONFLICT.code());
    }

    @Test
    void setSelectedReposForOrgVariable_shouldThrowApiException_whenResponseIsConflict() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CONFLICT.code(), null));
        final GitHubAgentsVariablesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setSelectedReposForOrgVariable(
                ORG, NAME, AgentsCatalogFixtures.setSelectedReposForOrgVariablePayload()));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
        assertThat(((GitHubApiException) thrown).statusCode()).isEqualTo(HttpStatus.CONFLICT.code());
    }

    @Test
    void addSelectedRepoToOrgVariable_shouldThrowApiException_whenResponseIsConflict() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CONFLICT.code(), null));
        final GitHubAgentsVariablesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.addSelectedRepoToOrgVariable(ORG, NAME, REPOSITORY_ID));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
        assertThat(((GitHubApiException) thrown).statusCode()).isEqualTo(HttpStatus.CONFLICT.code());
    }

    @Test
    void removeSelectedRepoFromOrgVariable_shouldThrowApiException_whenResponseIsConflict() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CONFLICT.code(), null));
        final GitHubAgentsVariablesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.removeSelectedRepoFromOrgVariable(ORG, NAME, REPOSITORY_ID));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
        assertThat(((GitHubApiException) thrown).statusCode()).isEqualTo(HttpStatus.CONFLICT.code());
    }

    @Test
    void createRepoVariable_shouldThrowValidationException_whenResponseIsUnprocessable() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.UNPROCESSABLE_ENTITY.code(), "{\"message\":\"bad\"}"));
        final GitHubAgentsVariablesApi api = api();

        final Throwable thrown = catchThrowable(
                () -> api.createRepoVariable(OWNER, REPO, AgentsCatalogFixtures.createRepoVariablePayload()));

        assertThat(thrown).isInstanceOf(GitHubValidationException.class);
    }

    private GitHubAgentsVariablesApi api() {
        return new GitHubAgentsVariablesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubAgentsVariablesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<VariablesCall> nullRequestCalls() {
        return Stream.of(
                api -> api.listOrgVariables(ORG, null),
                api -> api.listSelectedReposForOrgVariable(ORG, NAME, null),
                api -> api.listRepoOrganizationVariables(OWNER, REPO, null),
                api -> api.listRepoVariables(OWNER, REPO, null));
    }

    private static Stream<VariablesCall> nullPayloadCalls() {
        return Stream.of(
                api -> api.createOrgVariable(ORG, null),
                api -> api.updateOrgVariable(ORG, NAME, null),
                api -> api.setSelectedReposForOrgVariable(ORG, NAME, null),
                api -> api.createRepoVariable(OWNER, REPO, null),
                api -> api.updateRepoVariable(OWNER, REPO, NAME, null));
    }

    private static Stream<Arguments> invalidCalls() {
        return Stream.of(
                Arguments.of((VariablesCall) api -> api.listOrgVariables(" "), "org"),
                Arguments.of((VariablesCall) api -> api.getOrgVariable(" ", NAME), "org"),
                Arguments.of((VariablesCall) api -> api.getOrgVariable(ORG, " "), "name"),
                Arguments.of((VariablesCall) api -> api.deleteOrgVariable(ORG, " "), "name"),
                Arguments.of((VariablesCall) api -> api.listSelectedReposForOrgVariable(ORG, " "), "name"),
                Arguments.of((VariablesCall) api -> api.addSelectedRepoToOrgVariable(" ", NAME, REPOSITORY_ID), "org"),
                Arguments.of(
                        (VariablesCall) api -> api.removeSelectedRepoFromOrgVariable(ORG, " ", REPOSITORY_ID), "name"),
                Arguments.of((VariablesCall) api -> api.listRepoVariables(" ", REPO), "owner"),
                Arguments.of((VariablesCall) api -> api.listRepoOrganizationVariables(OWNER, " "), "repo"),
                Arguments.of((VariablesCall) api -> api.getRepoVariable(OWNER, REPO, " "), "name"),
                Arguments.of((VariablesCall) api -> api.deleteRepoVariable(" ", REPO, NAME), "owner"),
                Arguments.of(
                        (VariablesCall) api -> api.listOrgVariables(ORG, GitHubPageRequest.byCursor()),
                        "unsupported pagination request: cursor"));
    }

    @FunctionalInterface
    interface VariablesCall {
        void execute(GitHubAgentsVariablesApi api) throws Exception;
    }
}
