package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Repos REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubRepositoriesApi {

    private final LazyValue<GitHubRepositoryApi> repository;
    private final LazyValue<GitHubRepositoriesForksApi> forks;
    private final LazyValue<GitHubRepositoriesIssueTypesApi> issueTypes;
    private final LazyValue<GitHubRepositoriesAutolinksApi> autolinks;
    private final LazyValue<GitHubRepositoriesContentsApi> contents;
    private final LazyValue<GitHubRepositoriesWebhooksApi> webhooks;
    private final LazyValue<GitHubRepositoriesRulesApi> rules;
    private final LazyValue<GitHubRepositoriesRuleSuitesApi> ruleSuites;
    private final LazyValue<GitHubRepositoriesAttestationsApi> attestations;
    private final LazyValue<GitHubRepositoriesCustomPropertiesApi> customProperties;
    private final LazyValue<GitHubRepositoriesBypassRequestsApi> bypassRequests;
    private final LazyValue<GitHubRepositoriesLfsApi> lfs;

    /**
     * Repos resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubRepositoriesApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.repository = new LazyValue<>(() -> new GitHubRepositoryApi(caller));
        this.forks = new LazyValue<>(() -> new GitHubRepositoriesForksApi(caller));
        this.issueTypes = new LazyValue<>(() -> new GitHubRepositoriesIssueTypesApi(caller));
        this.autolinks = new LazyValue<>(() -> new GitHubRepositoriesAutolinksApi(caller));
        this.contents = new LazyValue<>(() -> new GitHubRepositoriesContentsApi(caller));
        this.webhooks = new LazyValue<>(() -> new GitHubRepositoriesWebhooksApi(caller));
        this.rules = new LazyValue<>(() -> new GitHubRepositoriesRulesApi(caller));
        this.ruleSuites = new LazyValue<>(() -> new GitHubRepositoriesRuleSuitesApi(caller));
        this.attestations = new LazyValue<>(() -> new GitHubRepositoriesAttestationsApi(caller));
        this.customProperties = new LazyValue<>(() -> new GitHubRepositoriesCustomPropertiesApi(caller));
        this.bypassRequests = new LazyValue<>(() -> new GitHubRepositoriesBypassRequestsApi(caller));
        this.lfs = new LazyValue<>(() -> new GitHubRepositoriesLfsApi(caller));
    }

    /**
     * Core repository operations ({@code subcategory=repos}).
     *
     * @return repository API
     */
    public GitHubRepositoryApi repository() {
        return repository.get();
    }

    /**
     * Repository fork operations ({@code subcategory=forks}).
     *
     * @return forks API
     */
    public GitHubRepositoriesForksApi forks() {
        return forks.get();
    }

    /**
     * Repository issue type operations ({@code subcategory=issue-types}).
     *
     * @return issue types API
     */
    public GitHubRepositoriesIssueTypesApi issueTypes() {
        return issueTypes.get();
    }

    /**
     * Repository autolink operations ({@code subcategory=autolinks}).
     *
     * @return autolinks API
     */
    public GitHubRepositoriesAutolinksApi autolinks() {
        return autolinks.get();
    }

    /**
     * Repository contents operations ({@code subcategory=contents}).
     *
     * @return contents API
     */
    public GitHubRepositoriesContentsApi contents() {
        return contents.get();
    }

    /**
     * Repository webhook operations ({@code subcategory=webhooks}).
     *
     * @return webhooks API
     */
    public GitHubRepositoriesWebhooksApi webhooks() {
        return webhooks.get();
    }

    /**
     * Repository and organization rules operations ({@code subcategory=rules}).
     *
     * @return rules API
     */
    public GitHubRepositoriesRulesApi rules() {
        return rules.get();
    }

    /**
     * Repository rule suite operations ({@code subcategory=rule-suites}).
     *
     * @return rule suites API
     */
    public GitHubRepositoriesRuleSuitesApi ruleSuites() {
        return ruleSuites.get();
    }

    /**
     * Repository attestation operations ({@code subcategory=attestations}).
     *
     * <p>The {@code CLOUD} and {@code GHEC} flavors carry this resource.
     *
     * @return attestations API
     */
    public GitHubRepositoriesAttestationsApi attestations() {
        return attestations.get();
    }

    /**
     * Repository custom property operations ({@code subcategory=custom-properties}).
     *
     * @return custom properties API
     */
    public GitHubRepositoriesCustomPropertiesApi customProperties() {
        return customProperties.get();
    }

    /**
     * Repository bypass request operations ({@code subcategory=bypass-requests}).
     *
     * <p>The {@code GHEC} and {@code GHES} flavors carry this resource.
     *
     * @return bypass requests API
     */
    public GitHubRepositoriesBypassRequestsApi bypassRequests() {
        return bypassRequests.get();
    }

    /**
     * Repository Git LFS operations ({@code subcategory=lfs}).
     *
     * <p>The {@code GHEC} and {@code GHES} flavors carry this resource.
     *
     * @return Git LFS API
     */
    public GitHubRepositoriesLfsApi lfs() {
        return lfs.get();
    }
}
