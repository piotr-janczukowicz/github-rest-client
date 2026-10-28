package io.github.piotr_janczukowicz.github.api.checks;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Checks REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubChecksApi {

    private final LazyValue<GitHubChecksRunsApi> runs;
    private final LazyValue<GitHubChecksSuitesApi> suites;

    /**
     * Checks resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubChecksApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.runs = new LazyValue<>(() -> new GitHubChecksRunsApi(caller));
        this.suites = new LazyValue<>(() -> new GitHubChecksSuitesApi(caller));
    }

    /**
     * Check run operations ({@code subcategory=runs}).
     *
     * @return check runs API
     */
    public GitHubChecksRunsApi runs() {
        return runs.get();
    }

    /**
     * Check suite operations ({@code subcategory=suites}).
     *
     * @return check suites API
     */
    public GitHubChecksSuitesApi suites() {
        return suites.get();
    }
}
