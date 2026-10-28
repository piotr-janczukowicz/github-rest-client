package io.github.piotr_janczukowicz.github.api.packages;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/** GitHub packages REST resource for API version {@code 2026-03-10}. */
public final class GitHubPackagesApi extends GitHubResourceApi {

    private static final PathTemplate ORG_DOCKER_CONFLICTS = PathTemplate.of("/orgs/{org}/docker/conflicts");
    private static final PathTemplate USERS_DOCKER_CONFLICTS = PathTemplate.of("/users/{username}/docker/conflicts");
    private static final PathTemplate ORG_PACKAGES = PathTemplate.of("/orgs/{org}/packages");
    private static final PathTemplate USERS_PACKAGES = PathTemplate.of("/users/{username}/packages");
    private static final PathTemplate ORG_PACKAGE =
            PathTemplate.of("/orgs/{org}/packages/{package_type}/{package_name}");
    private static final PathTemplate USER_PACKAGE = PathTemplate.of("/user/packages/{package_type}/{package_name}");
    private static final PathTemplate USERS_PACKAGE =
            PathTemplate.of("/users/{username}/packages/{package_type}/{package_name}");
    private static final PathTemplate ORG_PACKAGE_RESTORE =
            PathTemplate.of("/orgs/{org}/packages/{package_type}/{package_name}/restore");
    private static final PathTemplate USER_PACKAGE_RESTORE =
            PathTemplate.of("/user/packages/{package_type}/{package_name}/restore");
    private static final PathTemplate USERS_PACKAGE_RESTORE =
            PathTemplate.of("/users/{username}/packages/{package_type}/{package_name}/restore");
    private static final PathTemplate ORG_PACKAGE_VERSIONS =
            PathTemplate.of("/orgs/{org}/packages/{package_type}/{package_name}/versions");
    private static final PathTemplate USER_PACKAGE_VERSIONS =
            PathTemplate.of("/user/packages/{package_type}/{package_name}/versions");
    private static final PathTemplate USERS_PACKAGE_VERSIONS =
            PathTemplate.of("/users/{username}/packages/{package_type}/{package_name}/versions");
    private static final PathTemplate ORG_PACKAGE_VERSION =
            PathTemplate.of("/orgs/{org}/packages/{package_type}/{package_name}/versions/{package_version_id}");
    private static final PathTemplate USER_PACKAGE_VERSION =
            PathTemplate.of("/user/packages/{package_type}/{package_name}/versions/{package_version_id}");
    private static final PathTemplate USERS_PACKAGE_VERSION =
            PathTemplate.of("/users/{username}/packages/{package_type}/{package_name}/versions/{package_version_id}");
    private static final PathTemplate ORG_PACKAGE_VERSION_RESTORE =
            PathTemplate.of("/orgs/{org}/packages/{package_type}/{package_name}/versions/{package_version_id}/restore");
    private static final PathTemplate USER_PACKAGE_VERSION_RESTORE =
            PathTemplate.of("/user/packages/{package_type}/{package_name}/versions/{package_version_id}/restore");
    private static final PathTemplate USERS_PACKAGE_VERSION_RESTORE = PathTemplate.of(
            "/users/{username}/packages/{package_type}/{package_name}/versions/{package_version_id}/restore");
    private static final PathTemplate USER_PACKAGES = PathTemplate.of("/user/packages");
    private static final PathTemplate USER_DOCKER_CONFLICTS = PathTemplate.of("/user/docker/conflicts");

    private static final String PARAM_PACKAGE_TYPE = "packageType";

    private static final JsonType<List<GitHubPackage>> PACKAGES_TYPE = JsonTypes.listOf(GitHubPackage.class);
    private static final JsonType<List<GitHubPackageVersion>> PACKAGE_VERSIONS_TYPE =
            JsonTypes.listOf(GitHubPackageVersion.class);

    /**
     * Packages resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubPackagesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List conflicting packages during Docker migration for an organization ({@code GET /orgs/{org}/docker/conflicts}).
     *
     * @param org organization login
     * @return conflicting packages
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubPackage> listDockerMigrationConflictingPackagesForOrg(final String org) throws IOException {
        return caller.fetch(get(ORG_DOCKER_CONFLICTS, org), PACKAGES_TYPE);
    }

    /**
     * List conflicting packages during Docker migration for the authenticated user ({@code GET
     * /user/docker/conflicts}).
     *
     * @return conflicting packages
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubPackage> listDockerMigrationConflictingPackagesForAuthenticatedUser() throws IOException {
        return caller.fetch(get(USER_DOCKER_CONFLICTS), PACKAGES_TYPE);
    }

    /**
     * List conflicting packages during Docker migration for a user ({@code GET /users/{username}/docker/conflicts}).
     *
     * @param username GitHub username
     * @return conflicting packages
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubPackage> listDockerMigrationConflictingPackagesForUser(final String username) throws IOException {
        return caller.fetch(get(USERS_DOCKER_CONFLICTS, username), PACKAGES_TYPE);
    }

    /**
     * List packages for an organization ({@code GET /orgs/{org}/packages}).
     *
     * @param org organization login
     * @param filter query filters
     * @return first HTTP page of packages
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPackage> listPackagesForOrg(final String org, final GitHubListPackagesFilter filter)
            throws IOException {
        return listPackagesForOrg(org, filter, GitHubPageRequest.byPage());
    }

    /**
     * List packages for an organization ({@code GET /orgs/{org}/packages}).
     *
     * @param org organization login
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of packages
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPackage> listPackagesForOrg(
            final String org, final GitHubListPackagesFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(ORG_PACKAGES, filter.toQueryParameters(), request, PACKAGES_TYPE, org);
    }

    /**
     * List packages for the authenticated user ({@code GET /user/packages}).
     *
     * @param filter query filters
     * @return first HTTP page of packages
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPackage> listPackagesForAuthenticatedUser(final GitHubListPackagesFilter filter)
            throws IOException {
        return listPackagesForAuthenticatedUser(filter, GitHubPageRequest.byPage());
    }

    /**
     * List packages for the authenticated user ({@code GET /user/packages}).
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of packages
     * @throws IllegalArgumentException if {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPackage> listPackagesForAuthenticatedUser(
            final GitHubListPackagesFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USER_PACKAGES, filter.toQueryParameters(), request, PACKAGES_TYPE);
    }

    /**
     * List packages for a user ({@code GET /users/{username}/packages}).
     *
     * @param username GitHub username
     * @param filter query filters
     * @return first HTTP page of packages
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPackage> listPackagesForUser(final String username, final GitHubListPackagesFilter filter)
            throws IOException {
        return listPackagesForUser(username, filter, GitHubPageRequest.byPage());
    }

    /**
     * List packages for a user ({@code GET /users/{username}/packages}).
     *
     * @param username GitHub username
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of packages
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a since or cursor variant
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPackage> listPackagesForUser(
            final String username, final GitHubListPackagesFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(USERS_PACKAGES, filter.toQueryParameters(), request, PACKAGES_TYPE, username);
    }

    /**
     * Get a package for an organization ({@code GET /orgs/{org}/packages/{package_type}/{package_name}}).
     *
     * @param org organization login
     * @param packageType package type
     * @param packageName package name
     * @return package, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubPackage> getPackageForOrg(
            final String org, final GitHubPackageType packageType, final String packageName) throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        return caller.fetchOrEmpty(get(ORG_PACKAGE, org, packageType.wireValue(), packageName), GitHubPackage.class);
    }

    /**
     * Get a package for the authenticated user ({@code GET /user/packages/{package_type}/{package_name}}).
     *
     * @param packageType package type
     * @param packageName package name
     * @return package, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubPackage> getPackageForAuthenticatedUser(
            final GitHubPackageType packageType, final String packageName) throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        return caller.fetchOrEmpty(get(USER_PACKAGE, packageType.wireValue(), packageName), GitHubPackage.class);
    }

    /**
     * Get a package for a user ({@code GET /users/{username}/packages/{package_type}/{package_name}}).
     *
     * @param username GitHub username
     * @param packageType package type
     * @param packageName package name
     * @return package, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code username} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubPackage> getPackageForUser(
            final String username, final GitHubPackageType packageType, final String packageName) throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        return caller.fetchOrEmpty(
                get(USERS_PACKAGE, username, packageType.wireValue(), packageName), GitHubPackage.class);
    }

    /**
     * Delete a package for an organization ({@code DELETE /orgs/{org}/packages/{package_type}/{package_name}}).
     *
     * @param org organization login
     * @param packageType package type
     * @param packageName package name
     * @throws IllegalArgumentException if {@code org} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deletePackageForOrg(final String org, final GitHubPackageType packageType, final String packageName)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        caller.send(delete(ORG_PACKAGE, org, packageType.wireValue(), packageName));
    }

    /**
     * Delete a package for the authenticated user ({@code DELETE /user/packages/{package_type}/{package_name}}).
     *
     * @param packageType package type
     * @param packageName package name
     * @throws IllegalArgumentException if {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deletePackageForAuthenticatedUser(final GitHubPackageType packageType, final String packageName)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        caller.send(delete(USER_PACKAGE, packageType.wireValue(), packageName));
    }

    /**
     * Delete a package for a user ({@code DELETE /users/{username}/packages/{package_type}/{package_name}}).
     *
     * @param username GitHub username
     * @param packageType package type
     * @param packageName package name
     * @throws IllegalArgumentException if {@code username} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deletePackageForUser(
            final String username, final GitHubPackageType packageType, final String packageName) throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        caller.send(delete(USERS_PACKAGE, username, packageType.wireValue(), packageName));
    }

    /**
     * Restore a package for an organization ({@code POST /orgs/{org}/packages/{package_type}/{package_name}/restore}).
     *
     * @param org organization login
     * @param packageType package type
     * @param packageName package name
     * @throws IllegalArgumentException if {@code org} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void restorePackageForOrg(final String org, final GitHubPackageType packageType, final String packageName)
            throws IOException {
        restorePackageForOrg(org, packageType, packageName, GitHubRestorePackageFilter.empty());
    }

    /**
     * Restore a package for an organization ({@code POST /orgs/{org}/packages/{package_type}/{package_name}/restore}).
     *
     * @param org organization login
     * @param packageType package type
     * @param packageName package name
     * @param filter query filters
     * @throws IllegalArgumentException if {@code org} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} or {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void restorePackageForOrg(
            final String org,
            final GitHubPackageType packageType,
            final String packageName,
            final GitHubRestorePackageFilter filter)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);
        requireNonNull(filter, PARAM_FILTER);

        caller.send(post(ORG_PACKAGE_RESTORE, org, packageType.wireValue(), packageName)
                .withQuery(filter.toQueryParameters()));
    }

    /**
     * Restore a package for the authenticated user ({@code POST /user/packages/{package_type}/{package_name}/restore}).
     *
     * @param packageType package type
     * @param packageName package name
     * @throws IllegalArgumentException if {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void restorePackageForAuthenticatedUser(final GitHubPackageType packageType, final String packageName)
            throws IOException {
        restorePackageForAuthenticatedUser(packageType, packageName, GitHubRestorePackageFilter.empty());
    }

    /**
     * Restore a package for the authenticated user ({@code POST /user/packages/{package_type}/{package_name}/restore}).
     *
     * @param packageType package type
     * @param packageName package name
     * @param filter query filters
     * @throws IllegalArgumentException if {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} or {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void restorePackageForAuthenticatedUser(
            final GitHubPackageType packageType, final String packageName, final GitHubRestorePackageFilter filter)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);
        requireNonNull(filter, PARAM_FILTER);

        caller.send(
                post(USER_PACKAGE_RESTORE, packageType.wireValue(), packageName).withQuery(filter.toQueryParameters()));
    }

    /**
     * Restore a package for a user ({@code POST /users/{username}/packages/{package_type}/{package_name}/restore}).
     *
     * @param username GitHub username
     * @param packageType package type
     * @param packageName package name
     * @throws IllegalArgumentException if {@code username} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void restorePackageForUser(
            final String username, final GitHubPackageType packageType, final String packageName) throws IOException {
        restorePackageForUser(username, packageType, packageName, GitHubRestorePackageFilter.empty());
    }

    /**
     * Restore a package for a user ({@code POST /users/{username}/packages/{package_type}/{package_name}/restore}).
     *
     * @param username GitHub username
     * @param packageType package type
     * @param packageName package name
     * @param filter query filters
     * @throws IllegalArgumentException if {@code username} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} or {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void restorePackageForUser(
            final String username,
            final GitHubPackageType packageType,
            final String packageName,
            final GitHubRestorePackageFilter filter)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);
        requireNonNull(filter, PARAM_FILTER);

        caller.send(post(USERS_PACKAGE_RESTORE, username, packageType.wireValue(), packageName)
                .withQuery(filter.toQueryParameters()));
    }

    /**
     * List package versions for a package owned by an organization ({@code GET
     * /orgs/{org}/packages/{package_type}/{package_name}/versions}).
     *
     * @param org organization login
     * @param packageType package type
     * @param packageName package name
     * @return first HTTP page of package versions
     * @throws IllegalArgumentException if {@code org} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPackageVersion> listPackageVersionsForOrg(
            final String org, final GitHubPackageType packageType, final String packageName) throws IOException {
        return listPackageVersionsForOrg(
                org, packageType, packageName, GitHubListPackageVersionsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List package versions for a package owned by an organization ({@code GET
     * /orgs/{org}/packages/{package_type}/{package_name}/versions}).
     *
     * @param org organization login
     * @param packageType package type
     * @param packageName package name
     * @param filter query filters
     * @return first HTTP page of package versions
     * @throws IllegalArgumentException if {@code org} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} or {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPackageVersion> listPackageVersionsForOrg(
            final String org,
            final GitHubPackageType packageType,
            final String packageName,
            final GitHubListPackageVersionsFilter filter)
            throws IOException {
        return listPackageVersionsForOrg(org, packageType, packageName, filter, GitHubPageRequest.byPage());
    }

    /**
     * List package versions for a package owned by an organization ({@code GET
     * /orgs/{org}/packages/{package_type}/{package_name}/versions}).
     *
     * @param org organization login
     * @param packageType package type
     * @param packageName package name
     * @param request pagination token
     * @return first HTTP page of package versions
     * @throws IllegalArgumentException if {@code org} or {@code packageName} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code packageType} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPackageVersion> listPackageVersionsForOrg(
            final String org,
            final GitHubPackageType packageType,
            final String packageName,
            final GitHubPageRequest request)
            throws IOException {
        return listPackageVersionsForOrg(
                org, packageType, packageName, GitHubListPackageVersionsFilter.empty(), request);
    }

    /**
     * List package versions for a package owned by an organization ({@code GET
     * /orgs/{org}/packages/{package_type}/{package_name}/versions}).
     *
     * @param org organization login
     * @param packageType package type
     * @param packageName package name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of package versions
     * @throws IllegalArgumentException if {@code org} or {@code packageName} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code packageType}, {@code filter}, or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPackageVersion> listPackageVersionsForOrg(
            final String org,
            final GitHubPackageType packageType,
            final String packageName,
            final GitHubListPackageVersionsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                ORG_PACKAGE_VERSIONS,
                filter.toQueryParameters(),
                request,
                PACKAGE_VERSIONS_TYPE,
                org,
                packageType.wireValue(),
                packageName);
    }

    /**
     * List package versions for a package owned by the authenticated user ({@code GET
     * /user/packages/{package_type}/{package_name}/versions}).
     *
     * @param packageType package type
     * @param packageName package name
     * @return first HTTP page of package versions
     * @throws IllegalArgumentException if {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPackageVersion> listPackageVersionsForAuthenticatedUser(
            final GitHubPackageType packageType, final String packageName) throws IOException {
        return listPackageVersionsForAuthenticatedUser(
                packageType, packageName, GitHubListPackageVersionsFilter.empty(), GitHubPageRequest.byPage());
    }

    /**
     * List package versions for a package owned by the authenticated user ({@code GET
     * /user/packages/{package_type}/{package_name}/versions}).
     *
     * @param packageType package type
     * @param packageName package name
     * @param filter query filters
     * @return first HTTP page of package versions
     * @throws IllegalArgumentException if {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} or {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPackageVersion> listPackageVersionsForAuthenticatedUser(
            final GitHubPackageType packageType, final String packageName, final GitHubListPackageVersionsFilter filter)
            throws IOException {
        return listPackageVersionsForAuthenticatedUser(packageType, packageName, filter, GitHubPageRequest.byPage());
    }

    /**
     * List package versions for a package owned by the authenticated user ({@code GET
     * /user/packages/{package_type}/{package_name}/versions}).
     *
     * @param packageType package type
     * @param packageName package name
     * @param request pagination token
     * @return first HTTP page of package versions
     * @throws IllegalArgumentException if {@code packageName} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code packageType} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPackageVersion> listPackageVersionsForAuthenticatedUser(
            final GitHubPackageType packageType, final String packageName, final GitHubPageRequest request)
            throws IOException {
        return listPackageVersionsForAuthenticatedUser(
                packageType, packageName, GitHubListPackageVersionsFilter.empty(), request);
    }

    /**
     * List package versions for a package owned by the authenticated user ({@code GET
     * /user/packages/{package_type}/{package_name}/versions}).
     *
     * @param packageType package type
     * @param packageName package name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of package versions
     * @throws IllegalArgumentException if {@code packageName} is blank, or {@code request} is invalid
     * @throws NullPointerException if {@code packageType}, {@code filter}, or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubPackageVersion> listPackageVersionsForAuthenticatedUser(
            final GitHubPackageType packageType,
            final String packageName,
            final GitHubListPackageVersionsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return pageList(
                USER_PACKAGE_VERSIONS,
                filter.toQueryParameters(),
                request,
                PACKAGE_VERSIONS_TYPE,
                packageType.wireValue(),
                packageName);
    }

    /**
     * List package versions for a package owned by a user ({@code GET
     * /users/{username}/packages/{package_type}/{package_name}/versions}).
     *
     * <p>The spec defines no {@code page}, {@code per_page} or {@code state} parameters for this endpoint, and it is
     * not paginated (checked against the live API), so all versions come back as one list.
     *
     * @param username GitHub username
     * @param packageType package type
     * @param packageName package name
     * @return package versions
     * @throws IllegalArgumentException if {@code username} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public List<GitHubPackageVersion> listPackageVersionsForUser(
            final String username, final GitHubPackageType packageType, final String packageName) throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        return caller.fetch(
                get(USERS_PACKAGE_VERSIONS, username, packageType.wireValue(), packageName), PACKAGE_VERSIONS_TYPE);
    }

    /**
     * Get a package version for an organization ({@code GET
     * /orgs/{org}/packages/{package_type}/{package_name}/versions/{package_version_id}}).
     *
     * @param org organization login
     * @param packageType package type
     * @param packageName package name
     * @param packageVersionId package version id
     * @return package version, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code org} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubPackageVersion> getPackageVersionForOrg(
            final String org,
            final GitHubPackageType packageType,
            final String packageName,
            final long packageVersionId)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        return caller.fetchOrEmpty(
                get(ORG_PACKAGE_VERSION, org, packageType.wireValue(), packageName, packageVersionId),
                GitHubPackageVersion.class);
    }

    /**
     * Get a package version for the authenticated user ({@code GET
     * /user/packages/{package_type}/{package_name}/versions/{package_version_id}}).
     *
     * @param packageType package type
     * @param packageName package name
     * @param packageVersionId package version id
     * @return package version, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubPackageVersion> getPackageVersionForAuthenticatedUser(
            final GitHubPackageType packageType, final String packageName, final long packageVersionId)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        return caller.fetchOrEmpty(
                get(USER_PACKAGE_VERSION, packageType.wireValue(), packageName, packageVersionId),
                GitHubPackageVersion.class);
    }

    /**
     * Get a package version for a user ({@code GET
     * /users/{username}/packages/{package_type}/{package_name}/versions/{package_version_id}}).
     *
     * @param username GitHub username
     * @param packageType package type
     * @param packageName package name
     * @param packageVersionId package version id
     * @return package version, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code username} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty)
     */
    public Optional<GitHubPackageVersion> getPackageVersionForUser(
            final String username,
            final GitHubPackageType packageType,
            final String packageName,
            final long packageVersionId)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        return caller.fetchOrEmpty(
                get(USERS_PACKAGE_VERSION, username, packageType.wireValue(), packageName, packageVersionId),
                GitHubPackageVersion.class);
    }

    /**
     * Delete a package version for an organization ({@code DELETE
     * /orgs/{org}/packages/{package_type}/{package_name}/versions/{package_version_id}}).
     *
     * @param org organization login
     * @param packageType package type
     * @param packageName package name
     * @param packageVersionId package version id
     * @throws IllegalArgumentException if {@code org} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deletePackageVersionForOrg(
            final String org,
            final GitHubPackageType packageType,
            final String packageName,
            final long packageVersionId)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        caller.send(delete(ORG_PACKAGE_VERSION, org, packageType.wireValue(), packageName, packageVersionId));
    }

    /**
     * Delete a package version for the authenticated user ({@code DELETE
     * /user/packages/{package_type}/{package_name}/versions/{package_version_id}}).
     *
     * @param packageType package type
     * @param packageName package name
     * @param packageVersionId package version id
     * @throws IllegalArgumentException if {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deletePackageVersionForAuthenticatedUser(
            final GitHubPackageType packageType, final String packageName, final long packageVersionId)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        caller.send(delete(USER_PACKAGE_VERSION, packageType.wireValue(), packageName, packageVersionId));
    }

    /**
     * Delete a package version for a user ({@code DELETE
     * /users/{username}/packages/{package_type}/{package_name}/versions/{package_version_id}}).
     *
     * @param username GitHub username
     * @param packageType package type
     * @param packageName package name
     * @param packageVersionId package version id
     * @throws IllegalArgumentException if {@code username} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void deletePackageVersionForUser(
            final String username,
            final GitHubPackageType packageType,
            final String packageName,
            final long packageVersionId)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        caller.send(delete(USERS_PACKAGE_VERSION, username, packageType.wireValue(), packageName, packageVersionId));
    }

    /**
     * Restore a package version for an organization ({@code POST
     * /orgs/{org}/packages/{package_type}/{package_name}/versions/{package_version_id}/restore}).
     *
     * @param org organization login
     * @param packageType package type
     * @param packageName package name
     * @param packageVersionId package version id
     * @throws IllegalArgumentException if {@code org} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void restorePackageVersionForOrg(
            final String org,
            final GitHubPackageType packageType,
            final String packageName,
            final long packageVersionId)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        caller.send(post(ORG_PACKAGE_VERSION_RESTORE, org, packageType.wireValue(), packageName, packageVersionId));
    }

    /**
     * Restore a package version for the authenticated user ({@code POST
     * /user/packages/{package_type}/{package_name}/versions/{package_version_id}/restore}).
     *
     * @param packageType package type
     * @param packageName package name
     * @param packageVersionId package version id
     * @throws IllegalArgumentException if {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void restorePackageVersionForAuthenticatedUser(
            final GitHubPackageType packageType, final String packageName, final long packageVersionId)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        caller.send(post(USER_PACKAGE_VERSION_RESTORE, packageType.wireValue(), packageName, packageVersionId));
    }

    /**
     * Restore a package version for a user ({@code POST
     * /users/{username}/packages/{package_type}/{package_name}/versions/{package_version_id}/restore}).
     *
     * @param username GitHub username
     * @param packageType package type
     * @param packageName package name
     * @param packageVersionId package version id
     * @throws IllegalArgumentException if {@code username} or {@code packageName} is blank
     * @throws NullPointerException if {@code packageType} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public void restorePackageVersionForUser(
            final String username,
            final GitHubPackageType packageType,
            final String packageName,
            final long packageVersionId)
            throws IOException {
        requireNonNull(packageType, PARAM_PACKAGE_TYPE);

        caller.send(
                post(USERS_PACKAGE_VERSION_RESTORE, username, packageType.wireValue(), packageName, packageVersionId));
    }
}
