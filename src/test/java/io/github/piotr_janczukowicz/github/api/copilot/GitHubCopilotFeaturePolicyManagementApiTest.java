package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.TestGitHubClients;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@UnitTest
class GitHubCopilotFeaturePolicyManagementApiTest {

    private static final String PARAM = "octo-org";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @ParameterizedTest
    @EnumSource(
            value = GitHubFlavor.class,
            names = {"GHEC"},
            mode = EnumSource.Mode.EXCLUDE)
    void getOrganizationFeaturePolicies_shouldThrowBeforeAnyRequest_whenClientIsNotGhec(final GitHubFlavor flavor)
            throws Exception {
        final GitHubCopilotFeaturePolicyManagementApi api = api(flavor);

        final Throwable thrown = catchThrowable(() -> api.getOrganizationFeaturePolicies(PARAM));

        assertThat(thrown).isInstanceOf(UnsupportedOperationException.class).hasMessageContaining("exists in: [GHEC]");
        verify(transport, never()).execute(any());
    }

    @ParameterizedTest
    @EnumSource(
            value = GitHubFlavor.class,
            names = {"GHEC"},
            mode = EnumSource.Mode.EXCLUDE)
    void updateOrganizationFeaturePolicies_shouldThrowBeforeAnyRequest_whenClientIsNotGhec(final GitHubFlavor flavor)
            throws Exception {
        final GitHubCopilotFeaturePolicyManagementApi api = api(flavor);
        final GitHubUpdateOrganizationFeaturePoliciesPayload payload =
                CopilotCatalogFixtures.updateOrganizationFeaturePoliciesPayload();

        final Throwable thrown = catchThrowable(() -> api.updateOrganizationFeaturePolicies(PARAM, payload));

        assertThat(thrown).isInstanceOf(UnsupportedOperationException.class).hasMessageContaining("exists in: [GHEC]");
        verify(transport, never()).execute(any());
    }

    @Test
    void updateOrganizationFeaturePolicies_shouldThrow_whenPayloadNull() {
        final GitHubCopilotFeaturePolicyManagementApi api = api(GitHubFlavor.GHEC);

        final Throwable thrown = catchThrowable(() -> api.updateOrganizationFeaturePolicies(PARAM, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void updateOrganizationFeaturePolicies_shouldThrow_whenOrgBlank() {
        final GitHubCopilotFeaturePolicyManagementApi api = api(GitHubFlavor.GHEC);
        final GitHubUpdateOrganizationFeaturePoliciesPayload payload =
                CopilotCatalogFixtures.updateOrganizationFeaturePoliciesPayload();

        final Throwable thrown = catchThrowable(() -> api.updateOrganizationFeaturePolicies(" ", payload));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updateOrganizationFeaturePolicies_shouldThrowApiException_whenResponseIsUnprocessable() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.UNPROCESSABLE_ENTITY.code(), "{\"message\":\"Validation Failed\"}"));
        final GitHubCopilotFeaturePolicyManagementApi api = api(GitHubFlavor.GHEC);
        final GitHubUpdateOrganizationFeaturePoliciesPayload payload =
                CopilotCatalogFixtures.updateOrganizationFeaturePoliciesPayload();

        final Throwable thrown = catchThrowable(() -> api.updateOrganizationFeaturePolicies(PARAM, payload));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
        assertThat(((GitHubApiException) thrown).statusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.code());
    }

    private GitHubCopilotFeaturePolicyManagementApi api(final GitHubFlavor flavor) {
        return new GitHubCopilotFeaturePolicyManagementApi(
                GitHubCaller.of(TestGitHubClients.flavored(transport, flavor)));
    }
}
