package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.organizationsApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubOrganizationApiIntegrationTest {

    private static final String ORG = "github";
    private static final String ORGANIZATIONS_PATH = "/organizations";
    private static final String ORG_PATH = "/orgs/github";
    private static final String INSTALLATIONS_PATH = "/orgs/github/installations";
    private static final String IMMUTABLE_PATH = "/orgs/github/settings/immutable-releases";
    private static final String IMMUTABLE_REPOSITORIES_PATH = "/orgs/github/settings/immutable-releases/repositories";
    private static final String IMMUTABLE_REPOSITORY_PATH =
            "/orgs/github/settings/immutable-releases/repositories/64780797";
    private static final String SECURITY_PRODUCT_PATH = "/orgs/github/advanced_security/enable_all";
    private static final String USER_ORGS_PATH = "/user/orgs";
    private static final String OCTOCAT_ORGS_PATH = "/users/octocat/orgs";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void list_shouldReturnOrganizations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORGANIZATIONS_PATH,
                okJson(listJsonBytes(GitHubOrganizationApiIntegrationTest.class, "organization-simple.json")));

        final GitHubPage<GitHubOrganizationSimple> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().organization().list();
        }

        assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.organizationSimple());
        HTTP.verifyRequested(GET, ORGANIZATIONS_PATH);
    }

    @Test
    void listForAuthenticatedUser_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                USER_ORGS_PATH,
                okJson(listJsonBytes(GitHubOrganizationApiIntegrationTest.class, "organization-simple.json"))
                        .header(LINK, linkNext(HTTP.uri(USER_ORGS_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubOrganizationApiIntegrationTest.class, "organization-simple.json")));

        final GitHubPage<GitHubOrganizationSimple> first;
        final Optional<GitHubPage<GitHubOrganizationSimple>> second;
        final Optional<GitHubPage<GitHubOrganizationSimple>> third;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            first = organizations.api().organization().listForAuthenticatedUser();
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).hasSize(1);
            softly.assertThat(second).isPresent();
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, USER_ORGS_PATH, 2);
    }

    @Test
    void listForUser_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                OCTOCAT_ORGS_PATH,
                okJson(listJsonBytes(GitHubOrganizationApiIntegrationTest.class, "organization-simple.json")));

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().organization().listForUser("octocat", GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, OCTOCAT_ORGS_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, OCTOCAT_ORGS_PATH, "per_page", "10");
    }

    @Test
    void get_shouldReturnOrganization_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, ORG_PATH, okJson(jsonBytes(GitHubOrganizationApiIntegrationTest.class, "organization-full.json")));

        final Optional<GitHubOrganizationFull> organization;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organization = organizations.api().organization().get(ORG);
        }

        assertThat(organization).contains(OrganizationsCatalogFixtures.organizationFull());
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_PATH, notFound());

        final Optional<GitHubOrganizationFull> organization;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organization = organizations.api().organization().get(ORG);
        }

        assertThat(organization).isEmpty();
    }

    @Test
    void update_shouldPatchPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                ORG_PATH,
                okJson(jsonBytes(GitHubOrganizationApiIntegrationTest.class, "organization-full.json")));

        final GitHubOrganizationFull organization;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organization = organizations
                    .api()
                    .organization()
                    .update(ORG, OrganizationsCatalogFixtures.updateOrganizationPayload());
        }

        assertThat(organization).isEqualTo(OrganizationsCatalogFixtures.organizationFull());
        HTTP.verifyHeader(PATCH, ORG_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void delete_shouldSendDelete_whenCalled() throws Exception {
        HTTP.stub(DELETE, ORG_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().organization().delete(ORG);
        }

        HTTP.verifyRequested(DELETE, ORG_PATH);
    }

    @Test
    void listAppInstallations_shouldReturnInstallations_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                INSTALLATIONS_PATH,
                okJson(jsonBytes(GitHubOrganizationApiIntegrationTest.class, "installation-paginated.json")));

        final GitHubPage<GitHubInstallation> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().organization().listAppInstallations(ORG);
        }

        final GitHubOrganizationInstallationsPage expected =
                OrganizationsCatalogFixtures.organizationInstallationsPage();
        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactlyElementsOf(expected.installations());
            softly.assertThat(page.totalCount()).isEqualTo(expected.totalCount());
        });
    }

    @Test
    void listAppInstallations_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        final byte[] body = jsonBytes(GitHubOrganizationApiIntegrationTest.class, "installation-paginated.json");
        HTTP.stub(
                GET,
                INSTALLATIONS_PATH,
                okJson(body)
                        .header(LINK, linkNext(HTTP.uri(INSTALLATIONS_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(body));

        final GitHubPage<GitHubInstallation> first;
        final Optional<GitHubPage<GitHubInstallation>> second;
        final Optional<GitHubPage<GitHubInstallation>> third;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            first = organizations.api().organization().listAppInstallations(ORG);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        final GitHubOrganizationInstallationsPage expected =
                OrganizationsCatalogFixtures.organizationInstallationsPage();
        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expected.installations());
            softly.assertThat(first.totalCount()).isEqualTo(expected.totalCount());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expected.installations());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, INSTALLATIONS_PATH, 2);
        HTTP.verifyQueryParam(GET, INSTALLATIONS_PATH, "page", "2");
    }

    @Test
    void getImmutableReleasesSettings_shouldReturnSettings_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                IMMUTABLE_PATH,
                okJson(jsonBytes(
                        GitHubOrganizationApiIntegrationTest.class, "immutable-releases-organization-settings.json")));

        final GitHubImmutableReleasesOrganizationSettings settings;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            settings = organizations.api().organization().getImmutableReleasesSettings(ORG);
        }

        assertThat(settings).isEqualTo(OrganizationsCatalogFixtures.immutableReleasesOrganizationSettings());
    }

    @Test
    void getImmutableReleasesSettings_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, IMMUTABLE_PATH, notFound());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            final GitHubOrganizationApi organization = organizations.api().organization();

            thrown = catchThrowable(() -> organization.getImmutableReleasesSettings(ORG));
        }

        assertThat(thrown).isExactlyInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setImmutableReleasesSettings_shouldPutPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, IMMUTABLE_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations
                    .api()
                    .organization()
                    .setImmutableReleasesSettings(
                            ORG, OrganizationsCatalogFixtures.setImmutableReleasesSettingsPayload());
        }

        HTTP.verifyHeader(PUT, IMMUTABLE_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void getImmutableReleasesSettingsRepositories_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                IMMUTABLE_REPOSITORIES_PATH,
                okJson(jsonBytes(
                        GitHubOrganizationApiIntegrationTest.class, "immutable-releases-repositories-page.json")));

        final GitHubPage<GitHubMinimalRepository> page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().organization().getImmutableReleasesSettingsRepositories(ORG);
        }

        final GitHubImmutableReleasesSelectedRepositoriesPage expected =
                OrganizationsCatalogFixtures.immutableReleasesSelectedRepositoriesPage();
        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactlyElementsOf(expected.repositories());
            softly.assertThat(page.totalCount()).isEqualTo(expected.totalCount());
        });
        HTTP.verifyQueryParam(GET, IMMUTABLE_REPOSITORIES_PATH, "page", "1");
    }

    @Test
    void getImmutableReleasesSettingsRepositories_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        final byte[] body =
                jsonBytes(GitHubOrganizationApiIntegrationTest.class, "immutable-releases-repositories-page.json");
        HTTP.stub(
                GET,
                IMMUTABLE_REPOSITORIES_PATH,
                okJson(body)
                        .header(
                                LINK,
                                linkNext(HTTP.uri(
                                        IMMUTABLE_REPOSITORIES_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(body));

        final GitHubPage<GitHubMinimalRepository> first;
        final Optional<GitHubPage<GitHubMinimalRepository>> second;
        final Optional<GitHubPage<GitHubMinimalRepository>> third;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            first = organizations.api().organization().getImmutableReleasesSettingsRepositories(ORG);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        final GitHubImmutableReleasesSelectedRepositoriesPage expected =
                OrganizationsCatalogFixtures.immutableReleasesSelectedRepositoriesPage();
        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expected.repositories());
            softly.assertThat(first.totalCount()).isEqualTo(expected.totalCount());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expected.repositories());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, IMMUTABLE_REPOSITORIES_PATH, 2);
        HTTP.verifyQueryParam(GET, IMMUTABLE_REPOSITORIES_PATH, "page", "2");
    }

    @Test
    void setImmutableReleasesSettingsRepositories_shouldPutPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, IMMUTABLE_REPOSITORIES_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations
                    .api()
                    .organization()
                    .setImmutableReleasesSettingsRepositories(
                            ORG, OrganizationsCatalogFixtures.setImmutableReleasesSelectedRepositoriesPayload());
        }

        HTTP.verifyHeader(PUT, IMMUTABLE_REPOSITORIES_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void enableSelectedRepositoryImmutableReleases_shouldPut_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PUT, IMMUTABLE_REPOSITORY_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().organization().enableSelectedRepositoryImmutableReleases(ORG, 64_780_797L);
        }

        HTTP.verifyRequested(PUT, IMMUTABLE_REPOSITORY_PATH);
    }

    @Test
    void disableSelectedRepositoryImmutableReleases_shouldDelete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, IMMUTABLE_REPOSITORY_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().organization().disableSelectedRepositoryImmutableReleases(ORG, 64_780_797L);
        }

        HTTP.verifyRequested(DELETE, IMMUTABLE_REPOSITORY_PATH);
    }

    @Test
    void enableOrDisableSecurityProductOnAllOrgRepos_shouldPostPayload_whenResponseIsNoContent() throws Exception {
        HTTP.stub(POST, SECURITY_PRODUCT_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations
                    .api()
                    .organization()
                    .enableOrDisableSecurityProductOnAllOrgRepos(
                            ORG,
                            GitHubOrganizationSecurityProduct.ADVANCED_SECURITY,
                            GitHubOrganizationSecurityProductEnablement.ENABLE_ALL,
                            OrganizationsCatalogFixtures.enableOrganizationSecurityProductPayload());
        }

        HTTP.verifyHeader(POST, SECURITY_PRODUCT_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void enableOrDisableSecurityProductOnAllOrgRepos_shouldSendNoBody_whenPayloadOmitted() throws Exception {
        HTTP.stub(POST, SECURITY_PRODUCT_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations
                    .api()
                    .organization()
                    .enableOrDisableSecurityProductOnAllOrgRepos(
                            ORG,
                            GitHubOrganizationSecurityProduct.ADVANCED_SECURITY,
                            GitHubOrganizationSecurityProductEnablement.ENABLE_ALL);
        }

        HTTP.verifyAbsentBody(POST, SECURITY_PRODUCT_PATH);
    }
}
