package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
class GitHubCopilotCodeReviewApiTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final String BASE = "https://api.github.com";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotCodeReviewApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getEnterprisePolicy_shouldGet_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("enterprise-copilot-code-review-policy.json")));

        final GitHubEnterpriseCopilotCodeReviewPolicy result = api().getEnterprisePolicy(ENTERPRISE);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(CopilotCatalogFixtures.enterpriseCopilotCodeReviewPolicy());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/enterprises/octo-enterprise/copilot/policies/code-review");
        });
    }

    @Test
    void setEnterprisePolicy_shouldSend_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setEnterprisePolicy(ENTERPRISE, CopilotCatalogFixtures.setEnterpriseCodeReviewPolicyPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/enterprises/octo-enterprise/copilot/policies/code-review");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void setEnterprisePolicy_shouldThrow_whenPayloadNull() {
        final GitHubCopilotCodeReviewApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setEnterprisePolicy(ENTERPRISE, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getOrganizationRunner_shouldGet_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-copilot-code-review-runner.json")));

        final GitHubOrganizationCopilotCodeReviewRunner result = api().getOrganizationRunner(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(CopilotCatalogFixtures.organizationCopilotCodeReviewRunner());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString()).isEqualTo(BASE + "/orgs/octo-org/copilot/code-review/runner");
        });
    }

    @Test
    void setOrganizationRunner_shouldSend_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-copilot-code-review-runner.json")));

        final GitHubOrganizationCopilotCodeReviewRunner result =
                api().setOrganizationRunner(ORG, CopilotCatalogFixtures.setOrganizationCodeReviewRunnerPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(CopilotCatalogFixtures.organizationCopilotCodeReviewRunner());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().toString()).isEqualTo(BASE + "/orgs/octo-org/copilot/code-review/runner");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void setOrganizationRunner_shouldThrow_whenPayloadNull() {
        final GitHubCopilotCodeReviewApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setOrganizationRunner(ORG, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getOrganizationSettings_shouldGet_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-copilot-code-review-settings.json")));

        final GitHubOrganizationCopilotCodeReviewSettings result = api().getOrganizationSettings(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(CopilotCatalogFixtures.organizationCopilotCodeReviewSettings());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString()).isEqualTo(BASE + "/orgs/octo-org/copilot/code-review/settings");
        });
    }

    @Test
    void updateOrganizationSettings_shouldSend_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-copilot-code-review-settings.json")));

        final GitHubOrganizationCopilotCodeReviewSettings result = api().updateOrganizationSettings(
                        ORG, CopilotCatalogFixtures.updateOrganizationCodeReviewSettingsPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(CopilotCatalogFixtures.organizationCopilotCodeReviewSettings());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri().toString()).isEqualTo(BASE + "/orgs/octo-org/copilot/code-review/settings");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void updateOrganizationSettings_shouldThrow_whenPayloadNull() {
        final GitHubCopilotCodeReviewApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updateOrganizationSettings(ORG, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getOrganizationPolicy_shouldGet_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-copilot-code-review-policy.json")));

        final GitHubOrganizationCopilotCodeReviewPolicy result = api().getOrganizationPolicy(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(CopilotCatalogFixtures.organizationCopilotCodeReviewPolicy());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString()).isEqualTo(BASE + "/orgs/octo-org/copilot/policies/code-review");
        });
    }

    @Test
    void setOrganizationPolicy_shouldSend_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setOrganizationPolicy(ORG, CopilotCatalogFixtures.setOrganizationCodeReviewPolicyPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().toString()).isEqualTo(BASE + "/orgs/octo-org/copilot/policies/code-review");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void setOrganizationPolicy_shouldThrow_whenPayloadNull() {
        final GitHubCopilotCodeReviewApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setOrganizationPolicy(ORG, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getRepositorySettings_shouldGet_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("repository-copilot-code-review-settings.json")));

        final GitHubRepositoryCopilotCodeReviewSettings result = api().getRepositorySettings(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(CopilotCatalogFixtures.repositoryCopilotCodeReviewSettings());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/repos/octocat/hello-world/copilot/code-review/settings");
        });
    }

    @Test
    void updateRepositorySettings_shouldSend_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("repository-copilot-code-review-settings.json")));

        final GitHubRepositoryCopilotCodeReviewSettings result = api().updateRepositorySettings(
                        OWNER, REPO, CopilotCatalogFixtures.updateRepositoryCodeReviewSettingsPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(CopilotCatalogFixtures.repositoryCopilotCodeReviewSettings());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/repos/octocat/hello-world/copilot/code-review/settings");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void updateRepositorySettings_shouldThrow_whenPayloadNull() {
        final GitHubCopilotCodeReviewApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updateRepositorySettings(OWNER, REPO, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @ParameterizedTest
    @MethodSource("invalidCalls")
    void call_shouldThrowIllegalArgument_whenPathBlank(final ApiCall call, final String messageFragment) {
        final GitHubCopilotCodeReviewApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private static Stream<Arguments> invalidCalls() {
        return Stream.of(
                Arguments.of((ApiCall) api -> api.getEnterprisePolicy(" "), "enterprise"),
                Arguments.of(
                        (ApiCall) api -> api.setEnterprisePolicy(
                                " ", CopilotCatalogFixtures.setEnterpriseCodeReviewPolicyPayload()),
                        "enterprise"),
                Arguments.of((ApiCall) api -> api.getOrganizationRunner(" "), "org"),
                Arguments.of(
                        (ApiCall) api -> api.setOrganizationRunner(
                                " ", CopilotCatalogFixtures.setOrganizationCodeReviewRunnerPayload()),
                        "org"),
                Arguments.of((ApiCall) api -> api.getOrganizationSettings(" "), "org"),
                Arguments.of(
                        (ApiCall) api -> api.updateOrganizationSettings(
                                " ", CopilotCatalogFixtures.updateOrganizationCodeReviewSettingsPayload()),
                        "org"),
                Arguments.of((ApiCall) api -> api.getOrganizationPolicy(" "), "org"),
                Arguments.of(
                        (ApiCall) api -> api.setOrganizationPolicy(
                                " ", CopilotCatalogFixtures.setOrganizationCodeReviewPolicyPayload()),
                        "org"),
                Arguments.of((ApiCall) api -> api.getRepositorySettings(" ", REPO), "owner"),
                Arguments.of((ApiCall) api -> api.getRepositorySettings(OWNER, " "), "repo"),
                Arguments.of(
                        (ApiCall) api -> api.updateRepositorySettings(
                                " ", REPO, CopilotCatalogFixtures.updateRepositoryCodeReviewSettingsPayload()),
                        "owner"),
                Arguments.of(
                        (ApiCall) api -> api.updateRepositorySettings(
                                OWNER, " ", CopilotCatalogFixtures.updateRepositoryCodeReviewSettingsPayload()),
                        "repo"));
    }

    private GitHubCopilotCodeReviewApi api() {
        return new GitHubCopilotCodeReviewApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCopilotCodeReviewApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    @FunctionalInterface
    interface ApiCall {
        void execute(GitHubCopilotCodeReviewApi api) throws Exception;
    }
}
