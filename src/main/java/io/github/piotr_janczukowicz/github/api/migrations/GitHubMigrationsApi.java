package io.github.piotr_janczukowicz.github.api.migrations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Migrations REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubMigrationsApi {

    private final LazyValue<GitHubMigrationsOrgsApi> orgs;
    private final LazyValue<GitHubMigrationsSourceImportsApi> sourceImports;
    private final LazyValue<GitHubMigrationsUsersApi> users;

    /**
     * Migrations resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubMigrationsApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.orgs = new LazyValue<>(() -> new GitHubMigrationsOrgsApi(caller));
        this.sourceImports = new LazyValue<>(() -> new GitHubMigrationsSourceImportsApi(caller));
        this.users = new LazyValue<>(() -> new GitHubMigrationsUsersApi(caller));
    }

    /**
     * Organization migrations operations ({@code subcategory=orgs}).
     *
     * @return organization migrations API
     */
    public GitHubMigrationsOrgsApi orgs() {
        return orgs.get();
    }

    /**
     * Repository source imports operations ({@code subcategory=source-imports}).
     *
     * @return repository source imports API
     * @deprecated Endpoint closing down notice: due to very low levels of usage and available alternatives, this
     *     endpoint is closing down and will no longer be available from 00:00 UTC on April 12, 2024. Deprecation date
     *     {@code 2023-10-12}; removal date {@code 2024-04-12}.
     */
    @Deprecated(since = "2023-10-12")
    public GitHubMigrationsSourceImportsApi sourceImports() {
        return sourceImports.get();
    }

    /**
     * Authenticated-user migrations operations ({@code subcategory=users}).
     *
     * @return authenticated-user migrations API
     */
    public GitHubMigrationsUsersApi users() {
        return users.get();
    }
}
