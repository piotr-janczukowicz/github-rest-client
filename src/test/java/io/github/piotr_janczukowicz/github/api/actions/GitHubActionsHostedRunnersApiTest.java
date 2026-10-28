package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubActionsHostedRunnersApiTest {

    private static final String ORG = "github";
    private static final String ENTERPRISE = "github";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsHostedRunnersApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listHostedRunnersForOrg_shouldReturnRunners_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runners.json")));

        final GitHubPage<GitHubActionsHostedRunner> page = api().listHostedRunnersForOrg(ORG);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsHostedRunners().runners());
    }

    @Test
    void listHostedRunnersForOrg_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runners.json")));

        api().listHostedRunnersForOrg(ORG, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void createHostedRunnerForOrg_shouldReturnRunner_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("actions-hosted-runner.json")));

        final GitHubActionsHostedRunner runner =
                api().createHostedRunnerForOrg(ORG, ActionsCatalogFixtures.createHostedRunnerForOrgPayload());

        assertThat(runner).isEqualTo(ActionsCatalogFixtures.actionsHostedRunner());
    }

    @Test
    void listCustomImagesForOrg_shouldReturnImages_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-custom-images.json")));

        final GitHubActionsCustomImages images = api().listCustomImagesForOrg(ORG);

        assertThat(images).isEqualTo(ActionsCatalogFixtures.actionsCustomImages());
    }

    @Test
    void getCustomImageForOrg_shouldReturnImage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runner-custom-image.json")));

        final GitHubActionsHostedRunnerCustomImage image =
                api().getCustomImageForOrg(ORG, 1L).orElseThrow();

        assertThat(image).isEqualTo(ActionsCatalogFixtures.actionsHostedRunnerCustomImage());
    }

    @Test
    void getCustomImageForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubActionsHostedRunnerCustomImage> result = api().getCustomImageForOrg(ORG, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void deleteCustomImageFromOrg_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteCustomImageFromOrg(ORG, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE https://api.github.com/orgs/github/actions/hosted-runners/images/custom/1");
    }

    @Test
    void listCustomImageVersionsForOrg_shouldReturnVersions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-custom-image-versions.json")));

        final GitHubActionsCustomImageVersions versions = api().listCustomImageVersionsForOrg(ORG, 1L);

        assertThat(versions).isEqualTo(ActionsCatalogFixtures.actionsCustomImageVersions());
    }

    @Test
    void getCustomImageVersionForOrg_shouldReturnVersion_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runner-custom-image-version.json")));

        final Optional<GitHubActionsHostedRunnerCustomImageVersion> version =
                api().getCustomImageVersionForOrg(ORG, 1L, "1.0.0");

        assertThat(version).contains(ActionsCatalogFixtures.actionsHostedRunnerCustomImageVersion());
    }

    @Test
    void getCustomImageVersionForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubActionsHostedRunnerCustomImageVersion> result =
                api().getCustomImageVersionForOrg(ORG, 1L, "1.0.0");

        assertThat(result).isEmpty();
    }

    @Test
    void deleteCustomImageVersionFromOrg_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteCustomImageVersionFromOrg(ORG, 1L, "1.0.0");

        assertThat(line(captureSingleRequest()))
                .isEqualTo(
                        "DELETE https://api.github.com/orgs/github/actions/hosted-runners/images/custom/1/versions/1.0.0");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "1.0", "v1.0.0", "1.0.0-beta"})
    void getCustomImageVersionForOrg_shouldThrowIllegalArgument_whenVersionIsInvalid(final String version)
            throws Exception {
        final GitHubActionsHostedRunnersApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getCustomImageVersionForOrg(ORG, 1L, version));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("version");
        verify(transport, never()).execute(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "1.0", "v1.0.0", "1.0.0-beta"})
    void deleteCustomImageVersionFromOrg_shouldThrowIllegalArgument_whenVersionIsInvalid(final String version)
            throws Exception {
        final GitHubActionsHostedRunnersApi api = api();

        final Throwable thrown = catchThrowable(() -> api.deleteCustomImageVersionFromOrg(ORG, 1L, version));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("version");
        verify(transport, never()).execute(any());
    }

    @Test
    void getGitHubOwnedImagesForOrg_shouldReturnImages_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runner-images.json")));

        final GitHubActionsHostedRunnerImages images = api().getGitHubOwnedImagesForOrg(ORG);

        assertThat(images).isEqualTo(ActionsCatalogFixtures.actionsHostedRunnerImages());
    }

    @Test
    void getPartnerImagesForOrg_shouldReturnImages_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runner-images.json")));

        final GitHubActionsHostedRunnerImages images = api().getPartnerImagesForOrg(ORG);

        assertThat(images).isEqualTo(ActionsCatalogFixtures.actionsHostedRunnerImages());
    }

    @Test
    void getHostedRunnersLimitsForOrg_shouldReturnLimits_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runner-limits.json")));

        final GitHubActionsHostedRunnerLimits limits = api().getHostedRunnersLimitsForOrg(ORG);

        assertThat(limits).isEqualTo(ActionsCatalogFixtures.actionsHostedRunnerLimits());
    }

    @Test
    void getHostedRunnersMachineSpecsForOrg_shouldReturnSpecs_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runner-machine-specs.json")));

        final GitHubActionsHostedRunnerMachineSpecs specs = api().getHostedRunnersMachineSpecsForOrg(ORG);

        assertThat(specs).isEqualTo(ActionsCatalogFixtures.actionsHostedRunnerMachineSpecs());
    }

    @Test
    void getHostedRunnersPlatformsForOrg_shouldReturnPlatforms_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runner-platforms.json")));

        final GitHubActionsHostedRunnerPlatforms platforms = api().getHostedRunnersPlatformsForOrg(ORG);

        assertThat(platforms).isEqualTo(ActionsCatalogFixtures.actionsHostedRunnerPlatforms());
    }

    @Test
    void getHostedRunnerForOrg_shouldReturnRunner_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runner.json")));

        final GitHubActionsHostedRunner runner =
                api().getHostedRunnerForOrg(ORG, 1L).orElseThrow();

        assertThat(runner).isEqualTo(ActionsCatalogFixtures.actionsHostedRunner());
    }

    @Test
    void getHostedRunnerForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubActionsHostedRunner> result = api().getHostedRunnerForOrg(ORG, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void updateHostedRunnerForOrg_shouldReturnRunner_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runner.json")));

        final GitHubActionsHostedRunner runner =
                api().updateHostedRunnerForOrg(ORG, 1L, ActionsCatalogFixtures.updateHostedRunnerForOrgPayload());

        assertThat(runner).isEqualTo(ActionsCatalogFixtures.actionsHostedRunner());
    }

    @Test
    void deleteHostedRunnerForOrg_shouldReturnRunner_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("actions-hosted-runner.json")));

        final GitHubActionsHostedRunner runner = api().deleteHostedRunnerForOrg(ORG, 1L);

        assertThat(runner).isEqualTo(ActionsCatalogFixtures.actionsHostedRunner());
    }

    @Test
    void listHostedRunnersForEnterprise_shouldReturnRunners_whenRequestOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runners-for-enterprise.json")));

        final GitHubPage<GitHubActionsHostedRunner> page = api().listHostedRunnersForEnterprise(ENTERPRISE);

        assertThat(page.items())
                .containsExactlyElementsOf(ActionsCatalogFixtures.actionsHostedRunnersForEnterprise()
                        .runners());
    }

    @Test
    void listHostedRunnersForEnterprise_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runners-for-enterprise.json")));

        api().listHostedRunnersForEnterprise(ENTERPRISE, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void createHostedRunnerForEnterprise_shouldReturnRunner_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("actions-hosted-runner.json")));

        final GitHubActionsHostedRunner runner = api().createHostedRunnerForEnterprise(
                        ENTERPRISE, ActionsCatalogFixtures.createHostedRunnerForEnterprisePayload());

        assertThat(runner).isEqualTo(ActionsCatalogFixtures.actionsHostedRunner());
    }

    @Test
    void listCustomImagesForEnterprise_shouldReturnImages_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-custom-images-for-enterprise.json")));

        final GitHubActionsCustomImagesForEnterprise images = api().listCustomImagesForEnterprise(ENTERPRISE);

        assertThat(images).isEqualTo(ActionsCatalogFixtures.actionsCustomImagesForEnterprise());
    }

    @Test
    void getCustomImageForEnterprise_shouldReturnImage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runner-custom-image.json")));

        final GitHubActionsHostedRunnerCustomImage image =
                api().getCustomImageForEnterprise(ENTERPRISE, 1L).orElseThrow();

        assertThat(image).isEqualTo(ActionsCatalogFixtures.actionsHostedRunnerCustomImage());
    }

    @Test
    void getCustomImageForEnterprise_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubActionsHostedRunnerCustomImage> result = api().getCustomImageForEnterprise(ENTERPRISE, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void deleteCustomImageFromEnterprise_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteCustomImageFromEnterprise(ENTERPRISE, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE https://api.github.com/enterprises/github/actions/hosted-runners/images/custom/1");
    }

    @Test
    void listCustomImageVersionsForEnterprise_shouldReturnVersions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-custom-image-versions-for-enterprise.json")));

        final GitHubActionsCustomImageVersionsForEnterprise versions =
                api().listCustomImageVersionsForEnterprise(ENTERPRISE, 1L);

        assertThat(versions).isEqualTo(ActionsCatalogFixtures.actionsCustomImageVersionsForEnterprise());
    }

    @Test
    void getCustomImageVersionForEnterprise_shouldReturnVersion_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runner-custom-image-version.json")));

        final Optional<GitHubActionsHostedRunnerCustomImageVersion> version =
                api().getCustomImageVersionForEnterprise(ENTERPRISE, 1L, "1.0.0");

        assertThat(version).contains(ActionsCatalogFixtures.actionsHostedRunnerCustomImageVersion());
    }

    @Test
    void getCustomImageVersionForEnterprise_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubActionsHostedRunnerCustomImageVersion> result =
                api().getCustomImageVersionForEnterprise(ENTERPRISE, 1L, "1.0.0");

        assertThat(result).isEmpty();
    }

    @Test
    void deleteCustomImageVersionFromEnterprise_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteCustomImageVersionFromEnterprise(ENTERPRISE, 1L, "1.0.0");

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE https://api.github.com/enterprises/github/actions/hosted-runners/"
                        + "images/custom/1/versions/1.0.0");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "1.0", "v1.0.0", "1.0.0-beta"})
    void getCustomImageVersionForEnterprise_shouldThrowIllegalArgument_whenVersionIsInvalid(final String version)
            throws Exception {
        final GitHubActionsHostedRunnersApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getCustomImageVersionForEnterprise(ENTERPRISE, 1L, version));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("version");
        verify(transport, never()).execute(any());
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "1.0", "v1.0.0", "1.0.0-beta"})
    void deleteCustomImageVersionFromEnterprise_shouldThrowIllegalArgument_whenVersionIsInvalid(final String version)
            throws Exception {
        final GitHubActionsHostedRunnersApi api = api();

        final Throwable thrown =
                catchThrowable(() -> api.deleteCustomImageVersionFromEnterprise(ENTERPRISE, 1L, version));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("version");
        verify(transport, never()).execute(any());
    }

    @Test
    void getGitHubOwnedImagesForEnterprise_shouldReturnImages_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-github-owned-images-for-enterprise.json")));

        final GitHubActionsGitHubOwnedImagesForEnterprise images = api().getGitHubOwnedImagesForEnterprise(ENTERPRISE);

        assertThat(images).isEqualTo(ActionsCatalogFixtures.actionsGitHubOwnedImagesForEnterprise());
    }

    @Test
    void getPartnerImagesForEnterprise_shouldReturnImages_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-partner-images-for-enterprise.json")));

        final GitHubActionsPartnerImagesForEnterprise images = api().getPartnerImagesForEnterprise(ENTERPRISE);

        assertThat(images).isEqualTo(ActionsCatalogFixtures.actionsPartnerImagesForEnterprise());
    }

    @Test
    void getHostedRunnersLimitsForEnterprise_shouldReturnLimits_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runner-limits.json")));

        final GitHubActionsHostedRunnerLimits limits = api().getHostedRunnersLimitsForEnterprise(ENTERPRISE);

        assertThat(limits).isEqualTo(ActionsCatalogFixtures.actionsHostedRunnerLimits());
    }

    @Test
    void getHostedRunnersMachineSpecsForEnterprise_shouldReturnSpecs_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(), json("actions-hosted-runner-machine-specs-for-enterprise.json")));

        final GitHubActionsHostedRunnerMachineSpecsForEnterprise specs =
                api().getHostedRunnersMachineSpecsForEnterprise(ENTERPRISE);

        assertThat(specs).isEqualTo(ActionsCatalogFixtures.actionsHostedRunnerMachineSpecsForEnterprise());
    }

    @Test
    void getHostedRunnersPlatformsForEnterprise_shouldReturnPlatforms_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), json("actions-hosted-runner-platforms-for-enterprise.json")));

        final GitHubActionsHostedRunnerPlatformsForEnterprise platforms =
                api().getHostedRunnersPlatformsForEnterprise(ENTERPRISE);

        assertThat(platforms).isEqualTo(ActionsCatalogFixtures.actionsHostedRunnerPlatformsForEnterprise());
    }

    @Test
    void getHostedRunnerForEnterprise_shouldReturnRunner_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runner.json")));

        final GitHubActionsHostedRunner runner =
                api().getHostedRunnerForEnterprise(ENTERPRISE, 1L).orElseThrow();

        assertThat(runner).isEqualTo(ActionsCatalogFixtures.actionsHostedRunner());
    }

    @Test
    void getHostedRunnerForEnterprise_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubActionsHostedRunner> result = api().getHostedRunnerForEnterprise(ENTERPRISE, 1L);

        assertThat(result).isEmpty();
    }

    @Test
    void updateHostedRunnerForEnterprise_shouldReturnRunner_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-hosted-runner.json")));

        final GitHubActionsHostedRunner runner = api().updateHostedRunnerForEnterprise(
                        ENTERPRISE, 1L, ActionsCatalogFixtures.updateHostedRunnerForEnterprisePayload());

        assertThat(runner).isEqualTo(ActionsCatalogFixtures.actionsHostedRunner());
    }

    @Test
    void deleteHostedRunnerForEnterprise_shouldReturnRunner_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("actions-hosted-runner.json")));

        final GitHubActionsHostedRunner runner = api().deleteHostedRunnerForEnterprise(ENTERPRISE, 1L);

        assertThat(runner).isEqualTo(ActionsCatalogFixtures.actionsHostedRunner());
    }

    private GitHubActionsHostedRunnersApi api() {
        return new GitHubActionsHostedRunnersApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActionsHostedRunnersApiTest.class, fileName);
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
