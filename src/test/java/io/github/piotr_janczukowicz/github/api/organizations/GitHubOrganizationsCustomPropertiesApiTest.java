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
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubOrganizationsCustomPropertiesApiTest {

    private static final String ORG = "github";
    private static final String PROPERTY_NAME = "environment";
    private static final String SCHEMA_PATH = "/orgs/github/properties/schema";
    private static final String DEFINITION_PATH = "/orgs/github/properties/schema/environment";
    private static final String VALUES_PATH = "/orgs/github/properties/values";
    private static final String INSTALLATIONS_PATH = "/orgs/github/properties/installations";
    private static final String INSTALLATIONS_SCHEMA_PATH = "/orgs/github/properties/installations/schema";
    private static final String INSTALLATIONS_VALUES_PATH = "/orgs/github/properties/installations/values";
    private static final String INSTALLATIONS_VALUES_PROPERTY_PATH =
            "/orgs/github/properties/installations/values/environment";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsCustomPropertiesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listDefinitions_shouldReturnDefinitions_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("custom-properties.json")));

        final List<GitHubCustomProperty> definitions = callApi(api -> api.listDefinitions(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(definitions)
                    .containsExactlyElementsOf(OrganizationsCatalogFixtures.customPropertyDefinitions());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + SCHEMA_PATH);
        });
    }

    @Test
    void createOrUpdateDefinitions_shouldPatchSchema_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("custom-properties.json")));
        final GitHubCreateOrUpdateOrganizationDefinitionsPayload payload =
                OrganizationsCatalogFixtures.createOrUpdateOrganizationDefinitionsPayload();

        callApi(api -> api.createOrUpdateDefinitions(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + SCHEMA_PATH);
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo(new String(GitHubJsonCodecs.defaults().encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void getDefinition_shouldReturnDefinition_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("custom-property.json")));

        final Optional<GitHubCustomProperty> definition = callApi(api -> api.getDefinition(ORG, PROPERTY_NAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(definition)
                    .map(GitHubCustomProperty::propertyName)
                    .contains(PROPERTY_NAME);
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + DEFINITION_PATH);
        });
    }

    @Test
    void getDefinition_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubCustomProperty> definition = callApi(api -> api.getDefinition(ORG, "missing"));

        assertThat(definition).isEmpty();
    }

    @Test
    void createOrUpdateDefinition_shouldPutDefinition_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("custom-property.json")));
        final GitHubCustomPropertySetPayload payload = OrganizationsCatalogFixtures.customPropertySetPayload();

        callApi(api -> api.createOrUpdateDefinition(ORG, PROPERTY_NAME, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + DEFINITION_PATH);
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo(new String(GitHubJsonCodecs.defaults().encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void deleteDefinition_shouldDeleteDefinition_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.deleteDefinition(ORG, PROPERTY_NAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + DEFINITION_PATH);
        });
    }

    @Test
    void listValues_shouldReturnValues_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-repo-custom-property-values.json")));

        final GitHubPage<GitHubOrgRepoCustomPropertyValues> page = callApi(api -> api.listValues(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.orgRepoCustomPropertyValues());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com" + VALUES_PATH + "?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listValues_shouldSendRepositoryQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-repo-custom-property-values.json")));
        final GitHubOrganizationsCustomPropertyValuesFilter filter =
                GitHubOrganizationsCustomPropertyValuesFilterBuilder.builder()
                        .repositoryQuery("Hello-World")
                        .build();

        callApi(api -> api.listValues(ORG, filter, GitHubPageRequest.none()));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com" + VALUES_PATH + "?repository_query=Hello-World");
    }

    @Test
    void listValues_shouldReturnValues_whenFilterOverloadUsed() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-repo-custom-property-values.json")));
        final GitHubOrganizationsCustomPropertyValuesFilter filter =
                GitHubOrganizationsCustomPropertyValuesFilterBuilder.builder()
                        .repositoryQuery("Hello-World")
                        .build();

        final GitHubPage<GitHubOrgRepoCustomPropertyValues> page = callApi(api -> api.listValues(ORG, filter));

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.orgRepoCustomPropertyValues());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listValues_shouldReturnValues_whenRequestOverloadUsed() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-repo-custom-property-values.json")));

        final GitHubPage<GitHubOrgRepoCustomPropertyValues> page =
                callApi(api -> api.listValues(ORG, GitHubPageRequest.none()));

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.orgRepoCustomPropertyValues());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void createOrUpdateValues_shouldPatchValues_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubCreateOrUpdateOrganizationValuesPayload payload =
                OrganizationsCatalogFixtures.createOrUpdateOrganizationValuesPayload();

        callApiVoid(api -> api.createOrUpdateValues(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + VALUES_PATH);
        });
    }

    @Test
    void listExternalAppInstallations_shouldReturnInstallations_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-external-property-installations.json")));

        final List<GitHubOrganizationExternalPropertyInstallation> installations =
                callApi(api -> api.listExternalAppInstallations(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(installations)
                    .containsExactly(OrganizationsCatalogFixtures.organizationExternalPropertyInstallation());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + INSTALLATIONS_PATH);
        });
    }

    @Test
    void registerExternalAppInstallation_shouldPostInstallation_whenCalled() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.CREATED.code(), json("organization-external-property-installation.json")));
        final GitHubExternalPropertiesRegisterInstallationPayload payload =
                OrganizationsCatalogFixtures.externalPropertiesRegisterInstallationPayload();

        final GitHubOrganizationExternalPropertyInstallation installation =
                callApi(api -> api.registerExternalAppInstallation(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(installation)
                    .isEqualTo(OrganizationsCatalogFixtures.organizationExternalPropertyInstallation());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + INSTALLATIONS_PATH);
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo(new String(GitHubJsonCodecs.defaults().encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void registerExternalAppInstallation_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> callApi(api -> api.registerExternalAppInstallation(ORG, null)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void listExternalDefinitions_shouldReturnDefinitions_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("external-properties.json")));

        final List<GitHubExternalProperty> definitions = callApi(api -> api.listExternalDefinitions(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(definitions).containsExactlyElementsOf(OrganizationsCatalogFixtures.externalProperties());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + INSTALLATIONS_SCHEMA_PATH);
        });
    }

    @Test
    void createOrUpdateExternalValues_shouldPatchValues_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubExternalPropertiesCreateOrUpdateValuesPayload payload =
                OrganizationsCatalogFixtures.externalPropertiesCreateOrUpdateValuesPayload();

        callApiVoid(api -> api.createOrUpdateExternalValues(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + INSTALLATIONS_VALUES_PATH);
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo(new String(GitHubJsonCodecs.defaults().encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void createOrUpdateExternalValues_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(() -> callApiVoid(api -> api.createOrUpdateExternalValues(ORG, null)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void createOrUpdateExternalValuesForProperty_shouldPatchValues_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubExternalPropertiesCreateOrUpdatePropertyValuesPayload payload =
                OrganizationsCatalogFixtures.externalPropertiesCreateOrUpdatePropertyValuesPayload();

        callApiVoid(api -> api.createOrUpdateExternalValuesForProperty(ORG, PROPERTY_NAME, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + INSTALLATIONS_VALUES_PROPERTY_PATH);
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo(new String(GitHubJsonCodecs.defaults().encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void createOrUpdateExternalValuesForProperty_shouldThrow_whenPayloadNull() {
        final Throwable thrown = catchThrowable(
                () -> callApiVoid(api -> api.createOrUpdateExternalValuesForProperty(ORG, PROPERTY_NAME, null)));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteExternalValues_shouldDeleteValues_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.deleteExternalValues(ORG, PROPERTY_NAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + INSTALLATIONS_VALUES_PROPERTY_PATH);
        });
    }

    @ParameterizedTest
    @MethodSource("unsupportedPaginationRequests")
    void listValues_shouldThrow_whenUnsupportedPagination(
            final CustomPropertiesApiCall call, final String messageFragment) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @FunctionalInterface
    interface CustomPropertiesApiCall {
        void execute(GitHubOrganizationsCustomPropertiesApi api) throws Exception;
    }

    @FunctionalInterface
    private interface CustomPropertiesApiAction<T> {
        T apply(GitHubOrganizationsCustomPropertiesApi api) throws Exception;
    }

    private void callApiVoid(final CustomPropertiesApiCall action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            action.execute(new GitHubOrganizationsCustomPropertiesApi(TestClients.caller(client)));
        }
    }

    private <T> T callApi(final CustomPropertiesApiAction<T> action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationsCustomPropertiesApi(TestClients.caller(client)));
        }
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubPageRequest unsupportedForPageLists = GitHubPageRequest.bySince();
        final GitHubPageRequest unsupportedCursorForPageLists = GitHubPageRequest.byCursor();
        return Stream.of(
                Arguments.of(
                        (CustomPropertiesApiCall) api -> api.listValues(ORG, unsupportedCursorForPageLists),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (CustomPropertiesApiCall) api -> api.listValues(ORG, unsupportedForPageLists),
                        "unsupported pagination request: since"));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubOrganizationsCustomPropertiesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
