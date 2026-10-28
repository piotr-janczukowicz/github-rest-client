package io.github.piotr_janczukowicz.github.api.packages;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.packagesApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubPackagesApiIntegrationTest {

    private static final String ORG = "github";
    private static final String USERNAME = "octocat";
    private static final String PACKAGE_NAME = "hello_docker";
    private static final long VERSION_ID = 836L;

    private static final String USER_DOCKER_CONFLICTS_PATH = "/user/docker/conflicts";
    private static final String ORG_PACKAGES_PATH = "/orgs/github/packages";
    private static final String ORG_PACKAGE_PATH = "/orgs/github/packages/container/hello_docker";
    private static final String ORG_RESTORE_PATH = "/orgs/github/packages/container/hello_docker/restore";
    private static final String USER_VERSIONS_PATH = "/users/octocat/packages/container/hello_docker/versions";
    private static final String USER_PACKAGE_PATH = "/users/octocat/packages/container/hello_docker";
    private static final String ORG_VERSION_PATH = "/orgs/github/packages/container/hello_docker/versions/836";

    private static final JsonType<List<GitHubPackage>> PACKAGES_TYPE = JsonTypes.listOf(GitHubPackage.class);
    private static final JsonType<List<GitHubPackageVersion>> PACKAGE_VERSIONS_TYPE =
            JsonTypes.listOf(GitHubPackageVersion.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listDockerMigrationConflictingPackagesForAuthenticatedUser_shouldReturnPackages_whenResponseIsOk()
            throws Exception {
        HTTP.stub(
                GET,
                USER_DOCKER_CONFLICTS_PATH,
                okJson(jsonBytes(GitHubPackagesApiIntegrationTest.class, "packages-for-org.json")));

        final List<GitHubPackage> packages;
        try (final GitHubApiWireMockSupport.PackagesApiClient api = packagesApi(HTTP)) {
            packages = api.api().listDockerMigrationConflictingPackagesForAuthenticatedUser();
        }

        assertThat(packages).containsExactlyElementsOf(expectedPackages());
        HTTP.verifyRequested(GET, USER_DOCKER_CONFLICTS_PATH);
    }

    @Test
    void listPackagesForOrg_shouldReturnPackages_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_PACKAGES_PATH,
                okJson(jsonBytes(GitHubPackagesApiIntegrationTest.class, "packages-for-org.json")));

        final GitHubPage<GitHubPackage> page;
        try (final GitHubApiWireMockSupport.PackagesApiClient api = packagesApi(HTTP)) {
            page = api.api().listPackagesForOrg(ORG, GitHubListPackagesFilter.of(GitHubPackageType.CONTAINER));
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedPackages());
        HTTP.verifyRequested(GET, ORG_PACKAGES_PATH);
        HTTP.verifyQueryParam(GET, ORG_PACKAGES_PATH, "package_type", "container");
        HTTP.verifyQueryParam(GET, ORG_PACKAGES_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, ORG_PACKAGES_PATH, "per_page", "100");
    }

    @Test
    void listPackagesForOrg_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                ORG_PACKAGES_PATH,
                okJson(jsonBytes(GitHubPackagesApiIntegrationTest.class, "packages-for-org.json"))
                        .header(LINK, linkNext(HTTP.uri(ORG_PACKAGES_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(jsonBytes(GitHubPackagesApiIntegrationTest.class, "packages-for-org.json")));

        final GitHubPage<GitHubPackage> first;
        final Optional<GitHubPage<GitHubPackage>> second;
        final Optional<GitHubPage<GitHubPackage>> third;
        try (final GitHubApiWireMockSupport.PackagesApiClient api = packagesApi(HTTP)) {
            first = api.api().listPackagesForOrg(ORG, GitHubListPackagesFilter.of(GitHubPackageType.CONTAINER));
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedPackages());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedPackages());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, ORG_PACKAGES_PATH, 2);
    }

    @Test
    void listPackagesForOrg_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                ORG_PACKAGES_PATH,
                okJson(jsonBytes(GitHubPackagesApiIntegrationTest.class, "packages-for-org.json")));

        try (final GitHubApiWireMockSupport.PackagesApiClient api = packagesApi(HTTP)) {
            api.api()
                    .listPackagesForOrg(
                            ORG,
                            GitHubListPackagesFilter.of(GitHubPackageType.CONTAINER),
                            GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, ORG_PACKAGES_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, ORG_PACKAGES_PATH, "per_page", "10");
    }

    @Test
    void getPackageForOrg_shouldReturnPackage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ORG_PACKAGE_PATH, okJson(jsonBytes(GitHubPackagesApiIntegrationTest.class, "package-org.json")));

        final Optional<GitHubPackage> pkg;
        try (final GitHubApiWireMockSupport.PackagesApiClient api = packagesApi(HTTP)) {
            pkg = api.api().getPackageForOrg(ORG, GitHubPackageType.CONTAINER, PACKAGE_NAME);
        }

        final GitHubPackage expected = CODEC.decode(
                jsonBytes(GitHubPackagesApiIntegrationTest.class, "package-org.json"), GitHubPackage.class);

        assertThat(pkg).contains(expected);
        HTTP.verifyRequested(GET, ORG_PACKAGE_PATH);
    }

    @Test
    void getPackageForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_PACKAGE_PATH, notFound());

        final Optional<GitHubPackage> pkg;
        try (final GitHubApiWireMockSupport.PackagesApiClient api = packagesApi(HTTP)) {
            pkg = api.api().getPackageForOrg(ORG, GitHubPackageType.CONTAINER, PACKAGE_NAME);
        }

        assertThat(pkg).isEmpty();
        HTTP.verifyRequested(GET, ORG_PACKAGE_PATH);
    }

    @Test
    void restorePackageForOrg_shouldPostTokenQuery_whenFilterHasToken() throws Exception {
        HTTP.stub(POST, ORG_RESTORE_PATH, noContent());
        final GitHubRestorePackageFilter filter = GitHubRestorePackageFilterBuilder.builder()
                .token("restore-token")
                .build();

        try (final GitHubApiWireMockSupport.PackagesApiClient api = packagesApi(HTTP)) {
            api.api().restorePackageForOrg(ORG, GitHubPackageType.CONTAINER, PACKAGE_NAME, filter);
        }

        HTTP.verifyRequested(POST, ORG_RESTORE_PATH);
        HTTP.verifyQueryParam(POST, ORG_RESTORE_PATH, "token", "restore-token");
    }

    @Test
    void listPackageVersionsForUser_shouldReturnVersions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_VERSIONS_PATH,
                okJson(jsonBytes(GitHubPackagesApiIntegrationTest.class, "package-versions-for-org.json")));

        final List<GitHubPackageVersion> versions;
        try (final GitHubApiWireMockSupport.PackagesApiClient api = packagesApi(HTTP)) {
            versions = api.api().listPackageVersionsForUser(USERNAME, GitHubPackageType.CONTAINER, PACKAGE_NAME);
        }

        assertThat(versions).containsExactlyElementsOf(expectedVersions());
        HTTP.verifyRequested(GET, USER_VERSIONS_PATH);
    }

    @Test
    void getPackageVersionForOrg_shouldReturnVersion_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_VERSION_PATH,
                okJson(jsonBytes(GitHubPackagesApiIntegrationTest.class, "package-version-org.json")));

        final Optional<GitHubPackageVersion> version;
        try (final GitHubApiWireMockSupport.PackagesApiClient api = packagesApi(HTTP)) {
            version = api.api().getPackageVersionForOrg(ORG, GitHubPackageType.CONTAINER, PACKAGE_NAME, VERSION_ID);
        }

        final GitHubPackageVersion expected = CODEC.decode(
                jsonBytes(GitHubPackagesApiIntegrationTest.class, "package-version-org.json"),
                GitHubPackageVersion.class);

        assertThat(version).contains(expected);
        HTTP.verifyRequested(GET, ORG_VERSION_PATH);
    }

    @Test
    void deletePackageForUser_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_PACKAGE_PATH, noContent());

        try (final GitHubApiWireMockSupport.PackagesApiClient api = packagesApi(HTTP)) {
            api.api().deletePackageForUser(USERNAME, GitHubPackageType.CONTAINER, PACKAGE_NAME);
        }

        HTTP.verifyRequested(DELETE, USER_PACKAGE_PATH);
    }

    private static List<GitHubPackage> expectedPackages() {
        return CODEC.decode(jsonBytes(GitHubPackagesApiIntegrationTest.class, "packages-for-org.json"), PACKAGES_TYPE);
    }

    private static List<GitHubPackageVersion> expectedVersions() {
        return CODEC.decode(
                jsonBytes(GitHubPackagesApiIntegrationTest.class, "package-versions-for-org.json"),
                PACKAGE_VERSIONS_TYPE);
    }
}
