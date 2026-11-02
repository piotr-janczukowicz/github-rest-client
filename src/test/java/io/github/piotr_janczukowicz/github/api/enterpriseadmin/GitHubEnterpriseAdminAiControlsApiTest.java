package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

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
class GitHubEnterpriseAdminAiControlsApiTest {

    private static final String PARAM = "octo-ent";

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
    void getEnterpriseFeaturePolicies_shouldThrowBeforeAnyRequest_whenClientIsNotGhec(final GitHubFlavor flavor)
            throws Exception {
        final GitHubEnterpriseAdminAiControlsApi api = api(flavor);

        final Throwable thrown = catchThrowable(() -> api.getEnterpriseFeaturePolicies(PARAM));

        assertThat(thrown).isInstanceOf(UnsupportedOperationException.class).hasMessageContaining("exists in: [GHEC]");
        verify(transport, never()).execute(any());
    }

    @ParameterizedTest
    @EnumSource(
            value = GitHubFlavor.class,
            names = {"GHEC"},
            mode = EnumSource.Mode.EXCLUDE)
    void updateEnterpriseFeaturePolicies_shouldThrowBeforeAnyRequest_whenClientIsNotGhec(final GitHubFlavor flavor)
            throws Exception {
        final GitHubEnterpriseAdminAiControlsApi api = api(flavor);
        final GitHubUpdateEnterpriseFeaturePoliciesPayload payload =
                EnterpriseAdminCatalogFixtures.updateEnterpriseFeaturePoliciesPayload();

        final Throwable thrown = catchThrowable(() -> api.updateEnterpriseFeaturePolicies(PARAM, payload));

        assertThat(thrown).isInstanceOf(UnsupportedOperationException.class).hasMessageContaining("exists in: [GHEC]");
        verify(transport, never()).execute(any());
    }

    @Test
    void updateEnterpriseFeaturePolicies_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminAiControlsApi api = api(GitHubFlavor.GHEC);

        final Throwable thrown = catchThrowable(() -> api.updateEnterpriseFeaturePolicies(PARAM, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void updateEnterpriseFeaturePolicies_shouldThrow_whenEnterpriseBlank() {
        final GitHubEnterpriseAdminAiControlsApi api = api(GitHubFlavor.GHEC);
        final GitHubUpdateEnterpriseFeaturePoliciesPayload payload =
                EnterpriseAdminCatalogFixtures.updateEnterpriseFeaturePoliciesPayload();

        final Throwable thrown = catchThrowable(() -> api.updateEnterpriseFeaturePolicies(" ", payload));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void updateEnterpriseFeaturePolicies_shouldThrowApiException_whenResponseIsUnprocessable() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.UNPROCESSABLE_ENTITY.code(), "{\"message\":\"Validation Failed\"}"));
        final GitHubEnterpriseAdminAiControlsApi api = api(GitHubFlavor.GHEC);
        final GitHubUpdateEnterpriseFeaturePoliciesPayload payload =
                EnterpriseAdminCatalogFixtures.updateEnterpriseFeaturePoliciesPayload();

        final Throwable thrown = catchThrowable(() -> api.updateEnterpriseFeaturePolicies(PARAM, payload));

        assertThat(thrown).isInstanceOf(GitHubApiException.class);
        assertThat(((GitHubApiException) thrown).statusCode()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.code());
    }

    private GitHubEnterpriseAdminAiControlsApi api(final GitHubFlavor flavor) {
        return new GitHubEnterpriseAdminAiControlsApi(GitHubCaller.of(TestGitHubClients.flavored(transport, flavor)));
    }
}
