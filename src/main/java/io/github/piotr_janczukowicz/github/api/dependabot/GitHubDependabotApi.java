package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/**
 * GitHub Dependabot REST resource aggregator for API version {@code 2026-03-10}.
 *
 * <p>Dependabot alerts, repository access, and organization and repository secrets.
 */
public final class GitHubDependabotApi {

    private final LazyValue<GitHubDependabotAlertDismissalRequestsApi> alertDismissalRequests;
    private final LazyValue<GitHubDependabotAlertsApi> alerts;
    private final LazyValue<GitHubDependabotRepositoryAccessApi> repositoryAccess;
    private final LazyValue<GitHubDependabotSecretsApi> secrets;

    /**
     * Dependabot resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDependabotApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.alertDismissalRequests = new LazyValue<>(() -> new GitHubDependabotAlertDismissalRequestsApi(caller));
        this.alerts = new LazyValue<>(() -> new GitHubDependabotAlertsApi(caller));
        this.repositoryAccess = new LazyValue<>(() -> new GitHubDependabotRepositoryAccessApi(caller));
        this.secrets = new LazyValue<>(() -> new GitHubDependabotSecretsApi(caller));
    }

    /**
     * Dependabot alert dismissal request operations ({@code subcategory=alert-dismissal-requests}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return alert dismissal requests API
     */
    public GitHubDependabotAlertDismissalRequestsApi alertDismissalRequests() {
        return alertDismissalRequests.get();
    }

    /**
     * Dependabot alert operations ({@code subcategory=alerts}).
     *
     * @return alerts API
     */
    public GitHubDependabotAlertsApi alerts() {
        return alerts.get();
    }

    /**
     * Dependabot repository access operations ({@code subcategory=repository-access}).
     *
     * @return repository access API
     */
    public GitHubDependabotRepositoryAccessApi repositoryAccess() {
        return repositoryAccess.get();
    }

    /**
     * Dependabot secret operations ({@code subcategory=secrets}).
     *
     * @return secrets API
     */
    public GitHubDependabotSecretsApi secrets() {
        return secrets.get();
    }
}
