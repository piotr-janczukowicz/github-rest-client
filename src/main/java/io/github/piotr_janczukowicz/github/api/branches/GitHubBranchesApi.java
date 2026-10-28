package io.github.piotr_janczukowicz.github.api.branches;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Branches REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubBranchesApi {

    private final LazyValue<GitHubBranchApi> branch;
    private final LazyValue<GitHubBranchesBranchProtectionApi> branchProtection;

    /**
     * Branches resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubBranchesApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.branch = new LazyValue<>(() -> new GitHubBranchApi(caller));
        this.branchProtection = new LazyValue<>(() -> new GitHubBranchesBranchProtectionApi(caller));
    }

    /**
     * Branch operations ({@code subcategory=branches}).
     *
     * @return branch API
     */
    public GitHubBranchApi branch() {
        return branch.get();
    }

    /**
     * Branch protection operations ({@code subcategory=branch-protection}).
     *
     * @return branch protection API
     */
    public GitHubBranchesBranchProtectionApi branchProtection() {
        return branchProtection.get();
    }
}
