package io.github.piotr_janczukowicz.github.api.announcementbanners;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub organization announcement banner REST resource for API version {@code 2026-03-10}. */
@SuppressWarnings("PMD.LinguisticNaming")
public final class GitHubAnnouncementBannersOrganizationsApi extends GitHubResourceApi {

    private static final PathTemplate ANNOUNCEMENT = PathTemplate.of("/orgs/{org}/announcement");

    /**
     * Organization announcement banner resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubAnnouncementBannersOrganizationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the announcement banner for an organization ({@code GET /orgs/{org}/announcement}). Only the banner set at
     * the organization level is returned.
     *
     * @param org the organization name. The name is not case sensitive.
     * @return announcement banner currently set for the organization
     * @throws IllegalArgumentException if {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubAnnouncementBanner getAnnouncementBannerForOrg(final String org) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        return caller.fetch(get(ANNOUNCEMENT, org), GitHubAnnouncementBanner.class);
    }

    /**
     * Set the announcement banner for an organization ({@code PATCH /orgs/{org}/announcement}).
     *
     * @param org the organization name. The name is not case sensitive.
     * @param payload announcement banner to display for the organization
     * @return announcement banner as set for the organization
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public GitHubAnnouncementBanner setAnnouncementBannerForOrg(final String org, final GitHubAnnouncement payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(ANNOUNCEMENT, org), payload), GitHubAnnouncementBanner.class);
    }

    /**
     * Remove the announcement banner from an organization ({@code DELETE /orgs/{org}/announcement}).
     *
     * @param org the organization name. The name is not case sensitive.
     * @throws IllegalArgumentException if {@code org} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC} or
     *     {@code GHES}
     */
    @Flavors({GitHubFlavor.GHEC, GitHubFlavor.GHES})
    public void removeAnnouncementBannerForOrg(final String org) throws IOException {
        requireFlavor(GitHubFlavor.GHEC, GitHubFlavor.GHES);
        caller.send(delete(ANNOUNCEMENT, org));
    }
}
