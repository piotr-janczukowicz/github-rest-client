package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
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
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubOrganizationsNetworkConfigurationsApiTest {

    private static final String ORG = "github";
    private static final String NETWORK_CONFIGURATION_ID = "123456789ABCDEF";
    private static final String NETWORK_SETTINGS_ID = "220F78DACB92BBFBC5E6F22DE1CCF52309D";
    private static final String NETWORK_CONFIGURATIONS_PATH = "/orgs/github/settings/network-configurations";
    private static final String NETWORK_CONFIGURATION_PATH =
            "/orgs/github/settings/network-configurations/123456789ABCDEF";
    private static final String NETWORK_SETTINGS_PATH =
            "/orgs/github/settings/network-settings/220F78DACB92BBFBC5E6F22DE1CCF52309D";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsNetworkConfigurationsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void list_shouldReturnNetworkConfigurationsPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("network-configurations-page.json")));

        final GitHubPage<GitHubNetworkConfiguration> page = callApi(api -> api.list(ORG));
        final HttpRequest request = captureSingleRequest();
        final GitHubNetworkConfigurationsPage expected = OrganizationsCatalogFixtures.networkConfigurationsPage();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactlyElementsOf(expected.networkConfigurations());
            softly.assertThat(page.totalCount()).isEqualTo(expected.totalCount());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString()).contains(NETWORK_CONFIGURATIONS_PATH);
        });
    }

    @Test
    void list_shouldUsePageRequest_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("network-configurations-page.json")));

        callApi(api -> api.list(ORG, GitHubPageNumberRequest.of(2, 30)));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("page=2");
    }

    @Test
    void list_shouldThrow_whenRequestNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsNetworkConfigurationsApi api =
                    new GitHubOrganizationsNetworkConfigurationsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.list(ORG, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
        }
    }

    @Test
    void list_shouldThrow_whenRequestKindUnsupported() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsNetworkConfigurationsApi api =
                    new GitHubOrganizationsNetworkConfigurationsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.list(ORG, GitHubPageRequest.byCursor()));

            assertThat(thrown)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("unsupported pagination request: cursor");
        }
    }

    @Test
    void list_shouldThrow_whenOrgBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsNetworkConfigurationsApi api =
                    new GitHubOrganizationsNetworkConfigurationsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.list(" "));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
        }
    }

    @Test
    void create_shouldPostPayload_whenCalled() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("network-configuration.json")));
        final GitHubCreateNetworkConfigurationPayload payload =
                OrganizationsCatalogFixtures.createNetworkConfigurationPayload();

        final GitHubNetworkConfiguration created = callApi(api -> api.create(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(created).isEqualTo(OrganizationsCatalogFixtures.networkConfiguration());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + NETWORK_CONFIGURATIONS_PATH);
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo(new String(GitHubJsonCodecs.defaults().encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void create_shouldThrow_whenPayloadNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsNetworkConfigurationsApi api =
                    new GitHubOrganizationsNetworkConfigurationsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.create(ORG, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    @Test
    void get_shouldReturnNetworkConfiguration_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("network-configuration.json")));

        final GitHubNetworkConfiguration networkConfiguration =
                callApi(api -> api.get(ORG, NETWORK_CONFIGURATION_ID).orElseThrow());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(networkConfiguration).isEqualTo(OrganizationsCatalogFixtures.networkConfiguration());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + NETWORK_CONFIGURATION_PATH);
        });
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubNetworkConfiguration> networkConfiguration =
                callApi(api -> api.get(ORG, NETWORK_CONFIGURATION_ID));

        assertThat(networkConfiguration).isEmpty();
    }

    @Test
    void get_shouldThrow_whenNetworkConfigurationIdBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsNetworkConfigurationsApi api =
                    new GitHubOrganizationsNetworkConfigurationsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.get(ORG, " "));

            assertThat(thrown)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("{network_configuration_id}");
        }
    }

    @Test
    void update_shouldPatchPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("network-configuration.json")));
        final GitHubUpdateNetworkConfigurationPayload payload =
                OrganizationsCatalogFixtures.updateNetworkConfigurationPayload();

        final GitHubNetworkConfiguration updated = callApi(api -> api.update(ORG, NETWORK_CONFIGURATION_ID, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(updated).isEqualTo(OrganizationsCatalogFixtures.networkConfiguration());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + NETWORK_CONFIGURATION_PATH);
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo(new String(GitHubJsonCodecs.defaults().encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void update_shouldThrow_whenPayloadNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsNetworkConfigurationsApi api =
                    new GitHubOrganizationsNetworkConfigurationsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.update(ORG, NETWORK_CONFIGURATION_ID, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    @Test
    void delete_shouldDeleteNetworkConfiguration_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.delete(ORG, NETWORK_CONFIGURATION_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + NETWORK_CONFIGURATION_PATH);
        });
    }

    @Test
    void delete_shouldThrow_whenOrgBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsNetworkConfigurationsApi api =
                    new GitHubOrganizationsNetworkConfigurationsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.delete(" ", NETWORK_CONFIGURATION_ID));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
        }
    }

    @Test
    void getNetworkSettings_shouldReturnNetworkSettings_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("network-settings.json")));

        final GitHubNetworkSettings networkSettings =
                callApi(api -> api.getNetworkSettings(ORG, NETWORK_SETTINGS_ID).orElseThrow());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(networkSettings).isEqualTo(OrganizationsCatalogFixtures.networkSettings());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + NETWORK_SETTINGS_PATH);
        });
    }

    @Test
    void getNetworkSettings_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubNetworkSettings> networkSettings =
                callApi(api -> api.getNetworkSettings(ORG, NETWORK_SETTINGS_ID));

        assertThat(networkSettings).isEmpty();
    }

    @Test
    void getNetworkSettings_shouldThrow_whenNetworkSettingsIdBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsNetworkConfigurationsApi api =
                    new GitHubOrganizationsNetworkConfigurationsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.getNetworkSettings(ORG, " "));

            assertThat(thrown)
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("{network_settings_id}");
        }
    }

    @FunctionalInterface
    private interface NetworkConfigurationsApiCall {
        void execute(GitHubOrganizationsNetworkConfigurationsApi api) throws Exception;
    }

    @FunctionalInterface
    private interface NetworkConfigurationsApiAction<T> {
        T apply(GitHubOrganizationsNetworkConfigurationsApi api) throws Exception;
    }

    private void callApiVoid(final NetworkConfigurationsApiCall action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            action.execute(new GitHubOrganizationsNetworkConfigurationsApi(TestClients.caller(client)));
        }
    }

    private <T> T callApi(final NetworkConfigurationsApiAction<T> action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationsNetworkConfigurationsApi(TestClients.caller(client)));
        }
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubOrganizationsNetworkConfigurationsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
