package io.github.piotr_janczukowicz.github.api.packages;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubPackagesApiTest {

    private static final String ORG = "github";
    private static final String USERNAME = "octocat";
    private static final String PACKAGE_NAME = "hello_docker";
    private static final long VERSION_ID = 836L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubPackagesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listDockerMigrationConflictingPackagesForOrg_shouldReturnPackages_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("packages-for-org.json")));

        final List<GitHubPackage> packages = api().listDockerMigrationConflictingPackagesForOrg(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(packages).containsExactlyElementsOf(PackagesCatalogFixtures.packagesForOrg());
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/docker/conflicts");
        });
    }

    @Test
    void listDockerMigrationConflictingPackagesForAuthenticatedUser_shouldUseUserPath_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("packages-for-org.json")));

        api().listDockerMigrationConflictingPackagesForAuthenticatedUser();
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.toString()).contains("/user/docker/conflicts");
    }

    @Test
    void listDockerMigrationConflictingPackagesForUser_shouldUseUsersPath_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("packages-for-org.json")));

        api().listDockerMigrationConflictingPackagesForUser(USERNAME);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.toString()).contains("/users/octocat/docker/conflicts");
    }

    @Test
    void listPackagesForOrg_shouldReturnPackages_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("packages-for-org.json")));

        final GitHubPage<GitHubPackage> page =
                api().listPackagesForOrg(ORG, GitHubListPackagesFilter.of(GitHubPackageType.CONTAINER));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactlyElementsOf(PackagesCatalogFixtures.packagesForOrg());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri.toString()).contains("/orgs/github/packages");
            softly.assertThat(requestUri.getQuery()).contains("package_type=container");
        });
    }

    @Test
    void listPackagesForOrg_shouldUsePageRequest_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("packages-for-org.json")));

        api().listPackagesForOrg(
                        ORG, GitHubListPackagesFilter.of(GitHubPackageType.CONTAINER), GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri().toString()).contains("/orgs/github/packages");
    }

    @Test
    void listPackagesForOrg_shouldIncludeVisibility_whenFilterSet() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("packages-for-org.json")));
        final GitHubListPackagesFilter filter = GitHubListPackagesFilterBuilder.builder()
                .packageType(GitHubPackageType.CONTAINER)
                .visibility(GitHubPackageListVisibility.INTERNAL)
                .build();

        api().listPackagesForOrg(ORG, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("visibility=internal");
    }

    @Test
    void getPackageForOrg_shouldReturnPackage_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("package-org.json")));

        final Optional<GitHubPackage> pkg = api().getPackageForOrg(ORG, GitHubPackageType.CONTAINER, PACKAGE_NAME);

        assertThat(pkg).contains(PackagesCatalogFixtures.githubPackage());
    }

    @Test
    void getPackageForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubPackage> pkg = api().getPackageForOrg(ORG, GitHubPackageType.CONTAINER, PACKAGE_NAME);

        assertThat(pkg).isEmpty();
    }

    @Test
    void deletePackageForOrg_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deletePackageForOrg(ORG, GitHubPackageType.CONTAINER, PACKAGE_NAME);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/packages/container/hello_docker");
    }

    @Test
    void restorePackageForOrg_shouldPost_whenNoToken() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().restorePackageForOrg(ORG, GitHubPackageType.CONTAINER, PACKAGE_NAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().toString()).endsWith("/restore");
            softly.assertThat(request.uri().getQuery()).isNull();
        });
    }

    @Test
    void restorePackageForOrg_shouldPostTokenQuery_whenTokenPresent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().restorePackageForOrg(
                        ORG,
                        GitHubPackageType.CONTAINER,
                        PACKAGE_NAME,
                        GitHubRestorePackageFilterBuilder.builder()
                                .token("restore-token")
                                .build());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("token=restore-token");
    }

    @Test
    void listPackageVersionsForOrg_shouldReturnVersions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("package-versions-for-org.json")));

        final GitHubPage<GitHubPackageVersion> page =
                api().listPackageVersionsForOrg(ORG, GitHubPackageType.CONTAINER, PACKAGE_NAME);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactlyElementsOf(PackagesCatalogFixtures.packageVersionsForOrg());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listPackageVersionsForOrg_shouldUsePageRequest_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("package-versions-for-org.json")));

        api().listPackageVersionsForOrg(ORG, GitHubPackageType.CONTAINER, PACKAGE_NAME, GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri().toString()).contains("/versions");
    }

    @Test
    void listPackageVersionsForOrg_shouldIncludeState_whenFilterSet() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("package-versions-for-org.json")));
        final GitHubListPackageVersionsFilter filter = GitHubListPackageVersionsFilterBuilder.builder()
                .state(GitHubPackageVersionState.DELETED)
                .build();

        api().listPackageVersionsForOrg(ORG, GitHubPackageType.CONTAINER, PACKAGE_NAME, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("state=deleted");
    }

    @Test
    void getPackageVersionForOrg_shouldReturnVersion_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("package-version-org.json")));

        final Optional<GitHubPackageVersion> version =
                api().getPackageVersionForOrg(ORG, GitHubPackageType.CONTAINER, PACKAGE_NAME, VERSION_ID);

        assertThat(version).contains(PackagesCatalogFixtures.packageVersion());
    }

    @Test
    void getPackageVersionForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubPackageVersion> version =
                api().getPackageVersionForOrg(ORG, GitHubPackageType.CONTAINER, PACKAGE_NAME, VERSION_ID);

        assertThat(version).isEmpty();
    }

    @Test
    void deletePackageVersionForOrg_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deletePackageVersionForOrg(ORG, GitHubPackageType.CONTAINER, PACKAGE_NAME, VERSION_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void restorePackageVersionForOrg_shouldPost_whenNoTokenQuery() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().restorePackageVersionForOrg(ORG, GitHubPackageType.CONTAINER, PACKAGE_NAME, VERSION_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getQuery()).isNull();
        });
    }

    @Test
    void listPackagesForAuthenticatedUser_shouldUseUserPackagesPath_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("packages-for-org.json")));

        api().listPackagesForAuthenticatedUser(GitHubListPackagesFilter.of(GitHubPackageType.CONTAINER));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.toString()).contains("/user/packages");
    }

    @Test
    void listPackagesForAuthenticatedUser_shouldUsePageRequest_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("packages-for-org.json")));

        api().listPackagesForAuthenticatedUser(
                        GitHubListPackagesFilter.of(GitHubPackageType.CONTAINER), GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.toString()).contains("/user/packages");
    }

    @Test
    void getPackageForAuthenticatedUser_shouldReturnPackage_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("package-org.json")));

        final Optional<GitHubPackage> pkg =
                api().getPackageForAuthenticatedUser(GitHubPackageType.CONTAINER, PACKAGE_NAME);

        assertThat(pkg).contains(PackagesCatalogFixtures.githubPackage());
    }

    @Test
    void restorePackageForAuthenticatedUser_shouldPost_whenNoToken() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().restorePackageForAuthenticatedUser(GitHubPackageType.CONTAINER, PACKAGE_NAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getQuery()).isNull();
        });
    }

    @Test
    void restorePackageForAuthenticatedUser_shouldPostTokenQuery_whenTokenPresent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().restorePackageForAuthenticatedUser(
                        GitHubPackageType.CONTAINER,
                        PACKAGE_NAME,
                        GitHubRestorePackageFilterBuilder.builder()
                                .token("restore-token")
                                .build());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("token=restore-token");
    }

    @Test
    void deletePackageForAuthenticatedUser_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deletePackageForAuthenticatedUser(GitHubPackageType.CONTAINER, PACKAGE_NAME);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri().toString()).contains("/user/packages/container/hello_docker");
    }

    @Test
    void listPackageVersionsForAuthenticatedUser_shouldReturnVersions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("package-versions-for-org.json")));

        final GitHubPage<GitHubPackageVersion> page =
                api().listPackageVersionsForAuthenticatedUser(GitHubPackageType.CONTAINER, PACKAGE_NAME);

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactlyElementsOf(PackagesCatalogFixtures.packageVersionsForOrg());
            softly.assertThat(page.totalCount()).isNull();
        });
    }

    @Test
    void listPackageVersionsForAuthenticatedUser_shouldUsePageRequest_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("package-versions-for-org.json")));

        api().listPackageVersionsForAuthenticatedUser(
                        GitHubPackageType.CONTAINER, PACKAGE_NAME, GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.toString()).contains("/user/packages/");
    }

    @Test
    void listPackageVersionsForAuthenticatedUser_shouldIncludeState_whenFilterSet() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("package-versions-for-org.json")));
        final GitHubListPackageVersionsFilter filter = GitHubListPackageVersionsFilterBuilder.builder()
                .state(GitHubPackageVersionState.ACTIVE)
                .build();

        api().listPackageVersionsForAuthenticatedUser(GitHubPackageType.CONTAINER, PACKAGE_NAME, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("state=active");
    }

    @Test
    void getPackageVersionForAuthenticatedUser_shouldReturnVersion_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("package-version-org.json")));

        final Optional<GitHubPackageVersion> version =
                api().getPackageVersionForAuthenticatedUser(GitHubPackageType.CONTAINER, PACKAGE_NAME, VERSION_ID);

        assertThat(version).contains(PackagesCatalogFixtures.packageVersion());
    }

    @Test
    void deletePackageVersionForAuthenticatedUser_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deletePackageVersionForAuthenticatedUser(GitHubPackageType.CONTAINER, PACKAGE_NAME, VERSION_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void restorePackageVersionForAuthenticatedUser_shouldPost_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().restorePackageVersionForAuthenticatedUser(GitHubPackageType.CONTAINER, PACKAGE_NAME, VERSION_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.POST);
    }

    @Test
    void listPackagesForUser_shouldUseUsersPackagesPath_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("packages-for-org.json")));

        api().listPackagesForUser(USERNAME, GitHubListPackagesFilter.of(GitHubPackageType.CONTAINER));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.toString()).contains("/users/octocat/packages");
    }

    @Test
    void listPackagesForUser_shouldUsePageRequest_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("packages-for-org.json")));

        api().listPackagesForUser(
                        USERNAME, GitHubListPackagesFilter.of(GitHubPackageType.CONTAINER), GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.toString()).contains("/users/octocat/packages");
    }

    @Test
    void getPackageForUser_shouldReturnPackage_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("package-org.json")));

        final Optional<GitHubPackage> pkg =
                api().getPackageForUser(USERNAME, GitHubPackageType.CONTAINER, PACKAGE_NAME);

        assertThat(pkg).contains(PackagesCatalogFixtures.githubPackage());
    }

    @Test
    void deletePackageForUser_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deletePackageForUser(USERNAME, GitHubPackageType.CONTAINER, PACKAGE_NAME);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri().toString()).contains("/users/octocat/packages/container/hello_docker");
    }

    @Test
    void restorePackageForUser_shouldPost_whenNoToken() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().restorePackageForUser(USERNAME, GitHubPackageType.CONTAINER, PACKAGE_NAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().getQuery()).isNull();
        });
    }

    @Test
    void restorePackageForUser_shouldPostTokenQuery_whenTokenPresent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().restorePackageForUser(
                        USERNAME,
                        GitHubPackageType.CONTAINER,
                        PACKAGE_NAME,
                        GitHubRestorePackageFilterBuilder.builder()
                                .token("restore-token")
                                .build());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("token=restore-token");
    }

    @Test
    void listPackageVersionsForUser_shouldReturnVersions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("package-versions-for-org.json")));

        final List<GitHubPackageVersion> versions =
                api().listPackageVersionsForUser(USERNAME, GitHubPackageType.CONTAINER, PACKAGE_NAME);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(versions).containsExactlyElementsOf(PackagesCatalogFixtures.packageVersionsForOrg());
            softly.assertThat(requestUri.toString())
                    .isEqualTo("https://api.github.com/users/octocat/packages/container/hello_docker/versions");
            softly.assertThat(requestUri.getQuery()).isNull();
        });
    }

    @Test
    void getPackageVersionForUser_shouldReturnVersion_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("package-version-org.json")));

        final Optional<GitHubPackageVersion> version =
                api().getPackageVersionForUser(USERNAME, GitHubPackageType.CONTAINER, PACKAGE_NAME, VERSION_ID);

        assertThat(version).contains(PackagesCatalogFixtures.packageVersion());
    }

    @Test
    void deletePackageVersionForUser_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deletePackageVersionForUser(USERNAME, GitHubPackageType.CONTAINER, PACKAGE_NAME, VERSION_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
    }

    @Test
    void restorePackageVersionForUser_shouldPost_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().restorePackageVersionForUser(USERNAME, GitHubPackageType.CONTAINER, PACKAGE_NAME, VERSION_ID);
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.POST);
    }

    private GitHubPackagesApi api() {
        return new GitHubPackagesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubPackagesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
