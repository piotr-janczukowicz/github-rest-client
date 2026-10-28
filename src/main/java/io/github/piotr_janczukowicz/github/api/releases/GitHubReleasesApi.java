package io.github.piotr_janczukowicz.github.api.releases;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Releases REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubReleasesApi {

    private final LazyValue<GitHubReleaseApi> release;
    private final LazyValue<GitHubReleasesAssetsApi> assets;

    /**
     * Releases resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubReleasesApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.release = new LazyValue<>(() -> new GitHubReleaseApi(caller));
        this.assets = new LazyValue<>(() -> new GitHubReleasesAssetsApi(caller));
    }

    /**
     * Release operations ({@code subcategory=releases}).
     *
     * @return release API
     */
    public GitHubReleaseApi release() {
        return release.get();
    }

    /**
     * Release asset operations ({@code subcategory=assets}).
     *
     * @return assets API
     */
    public GitHubReleasesAssetsApi assets() {
        return assets.get();
    }
}
