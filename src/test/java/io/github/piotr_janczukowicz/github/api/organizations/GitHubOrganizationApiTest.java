package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubSinceRequest;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubOrganizationApiTest {

    private static final String ORG = "github";
    private static final String USERNAME = "octocat";
    private static final long REPOSITORY_ID = 64_780_797L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void list_shouldReturnOrganizations_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("organization-simple.json") + "]"));

        final GitHubPage<GitHubOrganizationSimple> page = callApi(GitHubOrganizationApi::list);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.organizationSimple());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/organizations");
        });
    }

    @Test
    void list_shouldSendSinceQuery_whenGitHubSinceRequest() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("organization-simple.json") + "]"));

        runApi(api -> api.list(GitHubSinceRequest.of(42L)));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("since", "42");
    }

    @Test
    void get_shouldReturnOrganization_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("organization-full.json")));

        final Optional<GitHubOrganizationFull> organization = callApi(api -> api.get(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(organization).contains(OrganizationsCatalogFixtures.organizationFull());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github");
        });
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubOrganizationFull> organization = callApi(api -> api.get(ORG));

        assertThat(organization).isEmpty();
    }

    @Test
    void update_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("organization-full.json")));
        final GitHubUpdateOrganizationPayload payload = OrganizationsCatalogFixtures.updateOrganizationPayload();

        runApi(api -> api.update(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github");
        });
    }

    @Test
    void delete_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        runApi(api -> api.delete(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github");
        });
    }

    @Test
    void listAppInstallations_shouldReturnInstallations_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("installation-paginated.json")));

        final GitHubPage<GitHubInstallation> page = callApi(api -> api.listAppInstallations(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(OrganizationsCatalogFixtures.organizationInstallationsPage()
                            .installations());
            softly.assertThat(page.totalCount())
                    .isEqualTo(OrganizationsCatalogFixtures.organizationInstallationsPage()
                            .totalCount());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString()).contains("/orgs/github/installations");
        });
    }

    @Test
    void listAppInstallations_shouldUsePageRequest_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("installation-paginated.json")));

        runApi(api -> api.listAppInstallations(ORG, GitHubPageRequest.byPage()));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("page=1");
    }

    @Test
    void getImmutableReleasesSettings_shouldReturnSettings_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("immutable-releases-organization-settings.json")));

        final GitHubImmutableReleasesOrganizationSettings settings =
                callApi(api -> api.getImmutableReleasesSettings(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(settings).isEqualTo(OrganizationsCatalogFixtures.immutableReleasesOrganizationSettings());
            softly.assertThat(request.uri().toString()).contains("/orgs/github/settings/immutable-releases");
        });
    }

    @Test
    void getImmutableReleasesSettings_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> callApi(api -> api.getImmutableReleasesSettings(ORG)));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setImmutableReleasesSettings_shouldPutPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubSetImmutableReleasesSettingsPayload payload =
                OrganizationsCatalogFixtures.setImmutableReleasesSettingsPayload();

        runApi(api -> api.setImmutableReleasesSettings(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().toString()).contains("/orgs/github/settings/immutable-releases");
        });
    }

    @Test
    void getImmutableReleasesSettingsRepositories_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("immutable-releases-repositories-page.json")));

        final GitHubPage<GitHubMinimalRepository> page =
                callApi(api -> api.getImmutableReleasesSettingsRepositories(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(OrganizationsCatalogFixtures.immutableReleasesSelectedRepositoriesPage()
                            .repositories());
            softly.assertThat(page.totalCount())
                    .isEqualTo(OrganizationsCatalogFixtures.immutableReleasesSelectedRepositoriesPage()
                            .totalCount());
            softly.assertThat(request.uri().toString())
                    .contains("/orgs/github/settings/immutable-releases/repositories");
        });
    }

    @Test
    void getImmutableReleasesSettingsRepositories_shouldUsePageRequest_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("immutable-releases-repositories-page.json")));

        runApi(api -> api.getImmutableReleasesSettingsRepositories(ORG, GitHubPageRequest.byPage()));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("page=1");
    }

    @Test
    void setImmutableReleasesSettingsRepositories_shouldPutPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubSetImmutableReleasesSelectedRepositoriesPayload payload =
                OrganizationsCatalogFixtures.setImmutableReleasesSelectedRepositoriesPayload();

        runApi(api -> api.setImmutableReleasesSettingsRepositories(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().toString())
                    .contains("/orgs/github/settings/immutable-releases/repositories");
        });
    }

    @Test
    void enableSelectedRepositoryImmutableReleases_shouldPutRepositoryId_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        runApi(api -> api.enableSelectedRepositoryImmutableReleases(ORG, REPOSITORY_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/orgs/github/settings/immutable-releases/repositories/64780797");
        });
    }

    @Test
    void disableSelectedRepositoryImmutableReleases_shouldDeleteRepositoryId_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        runApi(api -> api.disableSelectedRepositoryImmutableReleases(ORG, REPOSITORY_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/orgs/github/settings/immutable-releases/repositories/64780797");
        });
    }

    @Test
    void enableOrDisableSecurityProductOnAllOrgRepos_shouldPostWithoutBody_whenPayloadNull() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        runApi(api -> api.enableOrDisableSecurityProductOnAllOrgRepos(
                ORG,
                GitHubOrganizationSecurityProduct.SECRET_SCANNING,
                GitHubOrganizationSecurityProductEnablement.ENABLE_ALL));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/secret_scanning/enable_all");
        });
    }

    @Test
    void enableOrDisableSecurityProductOnAllOrgRepos_shouldPostPayload_whenPayloadPresent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubEnableOrganizationSecurityProductPayload payload =
                OrganizationsCatalogFixtures.enableOrganizationSecurityProductPayload();

        runApi(api -> api.enableOrDisableSecurityProductOnAllOrgRepos(
                ORG,
                GitHubOrganizationSecurityProduct.CODE_SCANNING_DEFAULT_SETUP,
                GitHubOrganizationSecurityProductEnablement.DISABLE_ALL,
                payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/code_scanning_default_setup/disable_all");
        });
    }

    @Test
    void listForAuthenticatedUser_shouldReturnOrganizations_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("organization-simple.json") + "]"));

        final GitHubPage<GitHubOrganizationSimple> page = callApi(GitHubOrganizationApi::listForAuthenticatedUser);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.organizationSimple());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/user/orgs?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listForAuthenticatedUser_shouldUsePageRequest_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("organization-simple.json") + "]"));

        runApi(api -> api.listForAuthenticatedUser(GitHubPageRequest.byPage()));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasToString("https://api.github.com/user/orgs?page=1&" + TestClients.perPageQuery());
    }

    @Test
    void listForUser_shouldReturnOrganizations_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("organization-simple.json") + "]"));

        final GitHubPage<GitHubOrganizationSimple> page = callApi(api -> api.listForUser(USERNAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.organizationSimple());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/orgs?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listForUser_shouldUsePageRequest_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("organization-simple.json") + "]"));

        runApi(api -> api.listForUser(USERNAME, GitHubPageRequest.byPage()));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/users/octocat/orgs?page=1&" + TestClients.perPageQuery());
    }

    @FunctionalInterface
    private interface OrganizationApiAction<T> {
        T apply(GitHubOrganizationApi api) throws Exception;
    }

    @FunctionalInterface
    private interface OrganizationApiVoid {
        void accept(GitHubOrganizationApi api) throws Exception;
    }

    private <T> T callApi(final OrganizationApiAction<T> action) throws Exception {
        try (final GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationApi(TestClients.caller(client)));
        }
    }

    private void runApi(final OrganizationApiVoid action) throws Exception {
        try (final GitHubClient client = TestClients.client(transport)) {
            action.accept(new GitHubOrganizationApi(TestClients.caller(client)));
        }
    }

    @Test
    void getAuditLog_shouldReturnFirstPage_whenFilterOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("audit-log-event.json") + "]"));

        final GitHubPage<GitHubAuditLogEvent> page = api().getAuditLog(ORG);

        assertThat(page.items()).hasSize(1);
        assertThat(captureSingleRequest().uri().getPath()).isEqualTo("/orgs/github/audit-log");
    }

    @Test
    void getAuditLog_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().getAuditLog(ORG, OrganizationsCatalogFixtures.getAuditLogFilter(), GitHubPageRequest.byCursor());

        assertThat(captureSingleRequest().uri()).hasParameter("include", "all").hasParameter("order", "desc");
    }

    @Test
    void listSamlSsoAuthorizations_shouldReturnPage_whenFilterOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("credential-authorization.json") + "]"));

        final GitHubPage<GitHubCredentialAuthorization> page = api().listSamlSsoAuthorizations(ORG);

        assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.credentialAuthorization());
    }

    @Test
    void listSamlSsoAuthorizations_shouldSendLoginQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listSamlSsoAuthorizations(
                        ORG,
                        OrganizationsCatalogFixtures.listSamlSsoAuthorizationsFilter(),
                        GitHubPageRequest.byPage());

        assertThat(captureSingleRequest().uri()).hasParameter("login", "monalisa");
    }

    @Test
    void revokeAllCredentialAuthorizations_shouldReturnQueuedRevocation_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.ACCEPTED.code(), json("revoke-all-credential-authorizations-response.json")));

        final GitHubRevokeAllCredentialAuthorizationsResponse response = api().revokeAllCredentialAuthorizations(ORG);

        assertThat(response).isEqualTo(OrganizationsCatalogFixtures.revokeAllCredentialAuthorizationsResponse());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/orgs/github/credential-authorizations/revoke-all");
    }

    @Test
    void revokeCredentialType_shouldReturnQueuedRevocation_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("revoke-credential-type-response.json")));

        final GitHubRevokeCredentialTypeResponse response =
                api().revokeCredentialType(ORG, OrganizationsCatalogFixtures.revokeCredentialTypePayload());

        assertThat(response).isEqualTo(OrganizationsCatalogFixtures.revokeCredentialTypeResponse());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/orgs/github/credential-authorizations/revoke-credential-type");
    }

    @Test
    void removeSamlSsoAuthorization_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeSamlSsoAuthorization(ORG, 7L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE https://api.github.com/orgs/github/credential-authorizations/7");
    }

    @Test
    void revokeCredentialAuthorizationsForUser_shouldReturnQueuedRevocation_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.ACCEPTED.code(), json("revoke-credential-authorizations-for-user-response.json")));

        final GitHubRevokeCredentialAuthorizationsForUserResponse response =
                api().revokeCredentialAuthorizationsForUser(ORG, USERNAME);

        assertThat(response).isEqualTo(OrganizationsCatalogFixtures.revokeCredentialAuthorizationsForUserResponse());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/orgs/github/credential-authorizations/octocat/revoke");
    }

    @Test
    void revokeCredentialTypeForUser_shouldReturnQueuedRevocation_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.ACCEPTED.code(), json("revoke-credential-type-for-user-response.json")));

        final GitHubRevokeCredentialTypeForUserResponse response = api().revokeCredentialTypeForUser(
                        ORG, USERNAME, OrganizationsCatalogFixtures.revokeCredentialTypeForUserPayload());

        assertThat(response).isEqualTo(OrganizationsCatalogFixtures.revokeCredentialTypeForUserResponse());
        assertThat(line(captureSingleRequest()))
                .isEqualTo(
                        "POST https://api.github.com/orgs/github/credential-authorizations/octocat/revoke-credential-type");
    }

    private static String line(final HttpRequest request) {
        return request.method() + " " + request.uri();
    }

    private GitHubOrganizationApi api() {
        return new GitHubOrganizationApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubOrganizationApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
