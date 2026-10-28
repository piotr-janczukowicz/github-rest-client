package io.github.piotr_janczukowicz.github.api.interactions;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Interactions REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubInteractionsApi {

    private final LazyValue<GitHubInteractionsOrgsApi> orgs;
    private final LazyValue<GitHubInteractionsReposApi> repos;
    private final LazyValue<GitHubInteractionsUserApi> user;

    /**
     * Interactions resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubInteractionsApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.orgs = new LazyValue<>(() -> new GitHubInteractionsOrgsApi(caller));
        this.repos = new LazyValue<>(() -> new GitHubInteractionsReposApi(caller));
        this.user = new LazyValue<>(() -> new GitHubInteractionsUserApi(caller));
    }

    /**
     * Organization interaction operations ({@code subcategory=orgs}).
     *
     * @return orgs API
     */
    public GitHubInteractionsOrgsApi orgs() {
        return orgs.get();
    }

    /**
     * Repository interaction operations ({@code subcategory=repos}).
     *
     * @return repos API
     */
    public GitHubInteractionsReposApi repos() {
        return repos.get();
    }

    /**
     * Authenticated-user interaction operations ({@code subcategory=user}).
     *
     * @return user API
     */
    public GitHubInteractionsUserApi user() {
        return user.get();
    }
}
