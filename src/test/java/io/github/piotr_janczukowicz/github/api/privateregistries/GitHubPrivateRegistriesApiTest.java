package io.github.piotr_janczukowicz.github.api.privateregistries;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
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
class GitHubPrivateRegistriesApiTest {

    private static final String ORG = "github";
    private static final String SECRET_NAME = "MAVEN_REPOSITORY_SECRET";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubPrivateRegistriesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listOrgPrivateRegistries_shouldReturnConfigurations_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-private-registry-configurations-page.json")));

        final GitHubPage<GitHubOrgPrivateRegistryConfiguration> page = api().listOrgPrivateRegistries(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.totalCount())
                    .isEqualTo(PrivateRegistriesCatalogFixtures.orgPrivateRegistryConfigurationsPage()
                            .totalCount());
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(PrivateRegistriesCatalogFixtures.orgPrivateRegistryConfigurationsPage()
                            .configurations());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/github/private-registries?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listOrgPrivateRegistries_shouldUsePageRequest_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-private-registry-configurations-page.json")));

        api().listOrgPrivateRegistries(ORG, GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("page=2").contains("per_page=10");
    }

    @Test
    void listOrgPrivateRegistries_shouldThrow_whenRequestNull() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubPrivateRegistriesApi api = new GitHubPrivateRegistriesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.listOrgPrivateRegistries(ORG, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
        }
    }

    @Test
    void listOrgPrivateRegistries_shouldThrow_whenOrgBlank() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubPrivateRegistriesApi api = new GitHubPrivateRegistriesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.listOrgPrivateRegistries(" "));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
        }
    }

    @Test
    void createOrgPrivateRegistry_shouldReturnConfiguration_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.CREATED.code(),
                        json("org-private-registry-configuration-with-selected-repositories.json")));
        final GitHubCreateOrgPrivateRegistryPayload payload =
                PrivateRegistriesCatalogFixtures.createOrgPrivateRegistryPayload();

        final GitHubOrgPrivateRegistryConfigurationWithSelectedRepositories configuration =
                api().createOrgPrivateRegistry(ORG, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(configuration)
                    .isEqualTo(
                            PrivateRegistriesCatalogFixtures.orgPrivateRegistryConfigurationWithSelectedRepositories());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/private-registries");
        });
    }

    @Test
    void createOrgPrivateRegistry_shouldThrow_whenPayloadNull() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubPrivateRegistriesApi api = new GitHubPrivateRegistriesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.createOrgPrivateRegistry(ORG, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    @Test
    void getOrgPublicKey_shouldReturnPublicKey_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("private-registries-public-key.json")));

        final GitHubPrivateRegistriesPublicKey publicKey = api().getOrgPublicKey(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(publicKey).isEqualTo(PrivateRegistriesCatalogFixtures.privateRegistriesPublicKey());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/github/private-registries/public-key");
        });
    }

    @Test
    void getOrgPublicKey_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final GitHubPrivateRegistriesApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getOrgPublicKey(ORG));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void getOrgPrivateRegistry_shouldReturnConfiguration_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-private-registry-configuration.json")));

        final Optional<GitHubOrgPrivateRegistryConfiguration> configuration =
                api().getOrgPrivateRegistry(ORG, SECRET_NAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(configuration)
                    .contains(PrivateRegistriesCatalogFixtures.orgPrivateRegistryConfiguration());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/github/private-registries/MAVEN_REPOSITORY_SECRET");
        });
    }

    @Test
    void getOrgPrivateRegistry_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubOrgPrivateRegistryConfiguration> configuration =
                api().getOrgPrivateRegistry(ORG, SECRET_NAME);

        assertThat(configuration).isEmpty();
    }

    @Test
    void updateOrgPrivateRegistry_shouldSendPatch_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubUpdateOrgPrivateRegistryPayload payload =
                PrivateRegistriesCatalogFixtures.updateOrgPrivateRegistryPayload();

        api().updateOrgPrivateRegistry(ORG, SECRET_NAME, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/private-registries/MAVEN_REPOSITORY_SECRET");
        });
    }

    @Test
    void updateOrgPrivateRegistry_shouldThrow_whenPayloadNull() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubPrivateRegistriesApi api = new GitHubPrivateRegistriesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.updateOrgPrivateRegistry(ORG, SECRET_NAME, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
        }
    }

    @Test
    void deleteOrgPrivateRegistry_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteOrgPrivateRegistry(ORG, SECRET_NAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/private-registries/MAVEN_REPOSITORY_SECRET");
        });
    }

    @Test
    void deleteOrgPrivateRegistry_shouldThrow_whenSecretNameBlank() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubPrivateRegistriesApi api = new GitHubPrivateRegistriesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.deleteOrgPrivateRegistry(ORG, " "));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("{secret_name}");
        }
    }

    @ParameterizedTest
    @MethodSource("unsupportedPaginationRequests")
    void listOrgPrivateRegistries_shouldThrow_whenUnsupportedPaginationRequest(
            final PrivateRegistriesCall call, final String message) {
        final GitHubPrivateRegistriesApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(message);
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubPageRequest unsupportedForPageLists = GitHubPageRequest.bySince();
        final GitHubPageRequest unsupportedCursorForPageLists = GitHubPageRequest.byCursor();
        return Stream.of(
                Arguments.of(
                        (PrivateRegistriesCall) api -> api.listOrgPrivateRegistries(ORG, unsupportedCursorForPageLists),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (PrivateRegistriesCall) api -> api.listOrgPrivateRegistries(ORG, unsupportedForPageLists),
                        "unsupported pagination request: since"));
    }

    @FunctionalInterface
    interface PrivateRegistriesCall {
        void execute(GitHubPrivateRegistriesApi api) throws Exception;
    }

    private GitHubPrivateRegistriesApi api() {
        return new GitHubPrivateRegistriesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubPrivateRegistriesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
