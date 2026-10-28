package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub dependency graph REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubDependencyGraphApi {

    private final LazyValue<GitHubDependencyGraphDependencyReviewApi> dependencyReview;
    private final LazyValue<GitHubDependencyGraphDependencySubmissionApi> dependencySubmission;
    private final LazyValue<GitHubDependencyGraphLicenseComplianceApi> licenseCompliance;
    private final LazyValue<GitHubDependencyGraphSbomsApi> sboms;

    /**
     * Dependency graph resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDependencyGraphApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.dependencyReview = new LazyValue<>(() -> new GitHubDependencyGraphDependencyReviewApi(caller));
        this.dependencySubmission = new LazyValue<>(() -> new GitHubDependencyGraphDependencySubmissionApi(caller));
        this.licenseCompliance = new LazyValue<>(() -> new GitHubDependencyGraphLicenseComplianceApi(caller));
        this.sboms = new LazyValue<>(() -> new GitHubDependencyGraphSbomsApi(caller));
    }

    /**
     * Dependency review operations ({@code subcategory=dependency-review}).
     *
     * @return dependency review API
     */
    public GitHubDependencyGraphDependencyReviewApi dependencyReview() {
        return dependencyReview.get();
    }

    /**
     * Dependency submission operations ({@code subcategory=dependency-submission}).
     *
     * @return dependency submission API
     */
    public GitHubDependencyGraphDependencySubmissionApi dependencySubmission() {
        return dependencySubmission.get();
    }

    /**
     * License compliance operations ({@code subcategory=license-compliance}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return license compliance API
     */
    public GitHubDependencyGraphLicenseComplianceApi licenseCompliance() {
        return licenseCompliance.get();
    }

    /**
     * Software bill of materials operations ({@code subcategory=sboms}).
     *
     * @return SBOMs API
     */
    public GitHubDependencyGraphSbomsApi sboms() {
        return sboms.get();
    }
}
