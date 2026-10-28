package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/**
 * GitHub Secret Scanning REST resource aggregator for API version {@code 2026-03-10}.
 *
 * <p>Read and update secret scanning alerts, custom patterns, push protection pattern configurations, push protection
 * bypasses, and scan history for organizations and repositories.
 */
public final class GitHubSecretScanningApi {

    private final LazyValue<GitHubSecretScanningAlertsApi> alerts;
    private final LazyValue<GitHubSecretScanningCustomPatternsApi> customPatterns;
    private final LazyValue<GitHubSecretScanningPushProtectionApi> pushProtection;
    private final LazyValue<GitHubSecretScanningDelegatedBypassApi> delegatedBypass;
    private final LazyValue<GitHubSecretScanningAlertDismissalRequestsApi> alertDismissalRequests;

    /**
     * Secret scanning resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubSecretScanningApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.alerts = new LazyValue<>(() -> new GitHubSecretScanningAlertsApi(caller));
        this.customPatterns = new LazyValue<>(() -> new GitHubSecretScanningCustomPatternsApi(caller));
        this.pushProtection = new LazyValue<>(() -> new GitHubSecretScanningPushProtectionApi(caller));
        this.delegatedBypass = new LazyValue<>(() -> new GitHubSecretScanningDelegatedBypassApi(caller));
        this.alertDismissalRequests = new LazyValue<>(() -> new GitHubSecretScanningAlertDismissalRequestsApi(caller));
    }

    /**
     * Secret scanning alert operations ({@code subcategory=secret-scanning}).
     *
     * @return alerts API
     */
    public GitHubSecretScanningAlertsApi alerts() {
        return alerts.get();
    }

    /**
     * Secret scanning custom pattern operations ({@code subcategory=custom-patterns}).
     *
     * @return custom patterns API
     */
    public GitHubSecretScanningCustomPatternsApi customPatterns() {
        return customPatterns.get();
    }

    /**
     * Secret scanning push protection operations ({@code subcategory=push-protection}).
     *
     * @return push protection API
     */
    public GitHubSecretScanningPushProtectionApi pushProtection() {
        return pushProtection.get();
    }

    /**
     * Secret scanning delegated bypass operations ({@code subcategory=delegated-bypass}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return delegated bypass API
     */
    public GitHubSecretScanningDelegatedBypassApi delegatedBypass() {
        return delegatedBypass.get();
    }

    /**
     * Secret scanning alert dismissal request operations ({@code subcategory=alert-dismissal-requests}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return alert dismissal requests API
     */
    public GitHubSecretScanningAlertDismissalRequestsApi alertDismissalRequests() {
        return alertDismissalRequests.get();
    }
}
