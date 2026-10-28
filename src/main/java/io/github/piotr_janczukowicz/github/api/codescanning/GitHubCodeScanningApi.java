package io.github.piotr_janczukowicz.github.api.codescanning;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/**
 * GitHub code scanning REST resource aggregator for API version {@code 2026-03-10}.
 *
 * <p>Code scanning alerts, analyses, SARIF uploads, CodeQL databases and variant analyses, and alert dismissal
 * requests.
 */
public final class GitHubCodeScanningApi {

    private final LazyValue<GitHubCodeScanningCodeScanningApi> codeScanning;
    private final LazyValue<GitHubCodeScanningAlertDismissalRequestsApi> alertDismissalRequests;

    /**
     * Code scanning resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCodeScanningApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.codeScanning = new LazyValue<>(() -> new GitHubCodeScanningCodeScanningApi(caller));
        this.alertDismissalRequests = new LazyValue<>(() -> new GitHubCodeScanningAlertDismissalRequestsApi(caller));
    }

    /**
     * Code scanning operations ({@code subcategory=code-scanning}).
     *
     * @return code scanning API
     */
    public GitHubCodeScanningCodeScanningApi codeScanning() {
        return codeScanning.get();
    }

    /**
     * Code scanning alert dismissal request operations ({@code subcategory=alert-dismissal-requests}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return alert dismissal requests API
     */
    public GitHubCodeScanningAlertDismissalRequestsApi alertDismissalRequests() {
        return alertDismissalRequests.get();
    }
}
