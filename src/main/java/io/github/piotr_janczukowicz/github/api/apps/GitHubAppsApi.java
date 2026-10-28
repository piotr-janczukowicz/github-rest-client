package io.github.piotr_janczukowicz.github.api.apps;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Apps REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubAppsApi {

    private final LazyValue<GitHubAppApi> app;
    private final LazyValue<GitHubAppsInstallationsApi> installations;
    private final LazyValue<GitHubAppsMarketplaceApi> marketplace;
    private final LazyValue<GitHubAppsOauthApplicationsApi> oauthApplications;
    private final LazyValue<GitHubAppsWebhooksApi> webhooks;

    /**
     * Apps resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubAppsApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.app = new LazyValue<>(() -> new GitHubAppApi(caller));
        this.installations = new LazyValue<>(() -> new GitHubAppsInstallationsApi(caller));
        this.marketplace = new LazyValue<>(() -> new GitHubAppsMarketplaceApi(caller));
        this.oauthApplications = new LazyValue<>(() -> new GitHubAppsOauthApplicationsApi(caller));
        this.webhooks = new LazyValue<>(() -> new GitHubAppsWebhooksApi(caller));
    }

    /**
     * GitHub App operations ({@code subcategory=apps}).
     *
     * @return app API
     */
    public GitHubAppApi app() {
        return app.get();
    }

    /**
     * Installation operations ({@code subcategory=installations}).
     *
     * @return installations API
     */
    public GitHubAppsInstallationsApi installations() {
        return installations.get();
    }

    /**
     * Marketplace operations ({@code subcategory=marketplace}).
     *
     * @return marketplace API
     */
    public GitHubAppsMarketplaceApi marketplace() {
        return marketplace.get();
    }

    /**
     * OAuth authorization operations ({@code subcategory=oauth-applications}).
     *
     * @return OAuth applications API
     */
    public GitHubAppsOauthApplicationsApi oauthApplications() {
        return oauthApplications.get();
    }

    /**
     * Webhook operations ({@code subcategory=webhooks}).
     *
     * @return webhooks API
     */
    public GitHubAppsWebhooksApi webhooks() {
        return webhooks.get();
    }
}
