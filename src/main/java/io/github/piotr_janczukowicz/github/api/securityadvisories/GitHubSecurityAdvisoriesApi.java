package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/**
 * GitHub Security Advisories REST resource aggregator for API version {@code 2026-03-10}.
 *
 * <p>Read and write global and repository security advisories.
 */
public final class GitHubSecurityAdvisoriesApi {

    private final LazyValue<GitHubSecurityAdvisoriesGlobalAdvisoriesApi> globalAdvisories;
    private final LazyValue<GitHubSecurityAdvisoriesRepositoryAdvisoriesApi> repositoryAdvisories;

    /**
     * Security advisories resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubSecurityAdvisoriesApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.globalAdvisories = new LazyValue<>(() -> new GitHubSecurityAdvisoriesGlobalAdvisoriesApi(caller));
        this.repositoryAdvisories = new LazyValue<>(() -> new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(caller));
    }

    /**
     * Global security advisory operations ({@code subcategory=global-advisories}).
     *
     * @return global advisories API
     */
    public GitHubSecurityAdvisoriesGlobalAdvisoriesApi globalAdvisories() {
        return globalAdvisories.get();
    }

    /**
     * Repository security advisory operations ({@code subcategory=repository-advisories}).
     *
     * @return repository advisories API
     */
    public GitHubSecurityAdvisoriesRepositoryAdvisoriesApi repositoryAdvisories() {
        return repositoryAdvisories.get();
    }
}
