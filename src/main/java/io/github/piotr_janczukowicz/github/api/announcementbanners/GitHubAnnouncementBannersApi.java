package io.github.piotr_janczukowicz.github.api.announcementbanners;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Announcement banners REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubAnnouncementBannersApi {

    private final LazyValue<GitHubAnnouncementBannersEnterprisesApi> enterprises;
    private final LazyValue<GitHubAnnouncementBannersOrganizationsApi> organizations;

    /**
     * Announcement banners resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubAnnouncementBannersApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.enterprises = new LazyValue<>(() -> new GitHubAnnouncementBannersEnterprisesApi(caller));
        this.organizations = new LazyValue<>(() -> new GitHubAnnouncementBannersOrganizationsApi(caller));
    }

    /**
     * Enterprise announcement banner operations ({@code subcategory=enterprises}).
     *
     * <p>Only the {@code GHEC} flavor carries this resource. On a client limited to another flavor, its methods throw
     * {@link UnsupportedOperationException}.
     *
     * @return enterprise announcement banner API
     */
    public GitHubAnnouncementBannersEnterprisesApi enterprises() {
        return enterprises.get();
    }

    /**
     * Organization announcement banner operations ({@code subcategory=organizations}).
     *
     * <p>Only the {@code GHEC} and {@code GHES} flavors carry this resource. On a client limited to another flavor, its
     * methods throw {@link UnsupportedOperationException}.
     *
     * @return organization announcement banner API
     */
    public GitHubAnnouncementBannersOrganizationsApi organizations() {
        return organizations.get();
    }
}
