package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
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
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCopilotCopilotCodingAgentManagementApiTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final String ORG = "octo-org";
    private static final long REPOSITORY_ID = 42L;
    private static final String BASE = "https://api.github.com";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotCopilotCodingAgentManagementApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void setEnterprisePolicy_shouldPutPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setEnterprisePolicy(ENTERPRISE, CopilotCatalogFixtures.setEnterpriseCodingAgentPolicyPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/enterprises/octo-enterprise/copilot/policies/coding_agent");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void addOrganizationsToEnterprisePolicy_shouldPostPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().addOrganizationsToEnterprisePolicy(
                        ENTERPRISE, CopilotCatalogFixtures.addOrganizationsToEnterpriseCodingAgentPolicyPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/enterprises/octo-enterprise/copilot/policies/coding_agent/organizations");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void removeOrganizationsFromEnterprisePolicy_shouldDeletePayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeOrganizationsFromEnterprisePolicy(
                        ENTERPRISE, CopilotCatalogFixtures.removeOrganizationsFromEnterpriseCodingAgentPolicyPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/enterprises/octo-enterprise/copilot/policies/coding_agent/organizations");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void getOrganizationPermissions_shouldReturnPermissions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(), json("get-copilot-coding-agent-permissions-organization-response.json")));

        final GitHubGetCopilotCodingAgentPermissionsOrganizationResponse permissions =
                api().getOrganizationPermissions(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(permissions)
                    .isEqualTo(CopilotCatalogFixtures.getCopilotCodingAgentPermissionsOrganizationResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/orgs/octo-org/copilot/coding-agent/permissions");
        });
    }

    @Test
    void setOrganizationPermissions_shouldPutPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setOrganizationPermissions(
                        ORG, CopilotCatalogFixtures.setCopilotCodingAgentPermissionsOrganizationPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/orgs/octo-org/copilot/coding-agent/permissions");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void listOrganizationSelectedRepositories_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(),
                        json("list-copilot-coding-agent-selected-repositories-for-organization-response.json")));

        final GitHubPage<GitHubMinimalRepository> page = api().listOrganizationSelectedRepositories(ORG);
        final HttpRequest request = captureSingleRequest();

        final GitHubListCopilotCodingAgentSelectedRepositoriesForOrganizationResponse expected =
                CopilotCatalogFixtures.listCopilotCodingAgentSelectedRepositoriesForOrganizationResponse();
        assertSoftly(softly -> {
            softly.assertThat(page.items()).isEqualTo(expected.repositories());
            softly.assertThat(page.totalCount()).isEqualTo(expected.totalCount());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/orgs/octo-org/copilot/coding-agent/permissions/repositories");
        });
    }

    @Test
    void listOrganizationSelectedRepositories_shouldThrow_whenRequestNull() {
        final GitHubCopilotCopilotCodingAgentManagementApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listOrganizationSelectedRepositories(ORG, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void listOrganizationSelectedRepositories_shouldUsePageRequest_whenPageRequested() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(),
                        json("list-copilot-coding-agent-selected-repositories-for-organization-response.json")));

        final GitHubPage<GitHubMinimalRepository> page =
                api().listOrganizationSelectedRepositories(ORG, GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .isEqualTo(
                            CopilotCatalogFixtures.listCopilotCodingAgentSelectedRepositoriesForOrganizationResponse()
                                    .repositories());
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/orgs/octo-org/copilot/coding-agent/permissions/repositories");
        });
    }

    @Test
    void setOrganizationSelectedRepositories_shouldPutPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setOrganizationSelectedRepositories(
                        ORG, CopilotCatalogFixtures.setCopilotCodingAgentSelectedRepositoriesForOrganizationPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/orgs/octo-org/copilot/coding-agent/permissions/repositories");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void enableRepositoryForOrganization_shouldPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().enableRepositoryForOrganization(ORG, REPOSITORY_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/orgs/octo-org/copilot/coding-agent/permissions/repositories/42");
        });
    }

    @Test
    void disableRepositoryForOrganization_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().disableRepositoryForOrganization(ORG, REPOSITORY_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/orgs/octo-org/copilot/coding-agent/permissions/repositories/42");
        });
    }

    @Test
    void setEnterprisePolicy_shouldThrow_whenPayloadNull() {
        final GitHubCopilotCopilotCodingAgentManagementApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setEnterprisePolicy(ENTERPRISE, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void setOrganizationPermissions_shouldThrow_whenPayloadNull() {
        final GitHubCopilotCopilotCodingAgentManagementApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setOrganizationPermissions(ORG, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @ParameterizedTest
    @MethodSource("invalidCalls")
    void call_shouldThrowIllegalArgument_whenPathBlank(final ApiCall call, final String messageFragment) {
        final GitHubCopilotCopilotCodingAgentManagementApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private static Stream<Arguments> invalidCalls() {
        return Stream.of(
                Arguments.of(
                        (ApiCall) api -> api.setEnterprisePolicy(
                                " ", CopilotCatalogFixtures.setEnterpriseCodingAgentPolicyPayload()),
                        "enterprise"),
                Arguments.of((ApiCall) api -> api.getOrganizationPermissions(" "), "org"),
                Arguments.of((ApiCall) api -> api.listOrganizationSelectedRepositories(" "), "org"),
                Arguments.of((ApiCall) api -> api.enableRepositoryForOrganization(" ", REPOSITORY_ID), "org"),
                Arguments.of((ApiCall) api -> api.disableRepositoryForOrganization(" ", REPOSITORY_ID), "org"));
    }

    private GitHubCopilotCopilotCodingAgentManagementApi api() {
        return new GitHubCopilotCopilotCodingAgentManagementApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCopilotCopilotCodingAgentManagementApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    @FunctionalInterface
    interface ApiCall {
        void execute(GitHubCopilotCopilotCodingAgentManagementApi api) throws Exception;
    }
}
