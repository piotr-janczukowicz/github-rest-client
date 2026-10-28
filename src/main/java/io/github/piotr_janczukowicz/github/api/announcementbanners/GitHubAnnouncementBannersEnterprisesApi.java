package io.github.piotr_janczukowicz.github.api.announcementbanners;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub enterprise announcement banner REST resource for API version {@code 2026-03-10}. */
@SuppressWarnings("PMD.LinguisticNaming")
public final class GitHubAnnouncementBannersEnterprisesApi extends GitHubResourceApi {

    private static final PathTemplate ANNOUNCEMENT = PathTemplate.of("/enterprises/{enterprise}/announcement");

    /**
     * Enterprise announcement banner resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubAnnouncementBannersEnterprisesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the announcement banner for an enterprise ({@code GET /enterprises/{enterprise}/announcement}).
     *
     * @param enterprise the slug version of the enterprise name
     * @return announcement banner currently set for the enterprise
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubAnnouncementBanner getAnnouncementBannerForEnterprise(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        return caller.fetch(get(ANNOUNCEMENT, enterprise), GitHubAnnouncementBanner.class);
    }

    /**
     * Set the announcement banner for an enterprise ({@code PATCH /enterprises/{enterprise}/announcement}).
     *
     * @param enterprise the slug version of the enterprise name
     * @param payload announcement banner to display for the enterprise
     * @return announcement banner as set for the enterprise
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public GitHubAnnouncementBanner setAnnouncementBannerForEnterprise(
            final String enterprise, final GitHubAnnouncement payload) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(ANNOUNCEMENT, enterprise), payload), GitHubAnnouncementBanner.class);
    }

    /**
     * Remove the announcement banner from an enterprise ({@code DELETE /enterprises/{enterprise}/announcement}).
     *
     * @param enterprise the slug version of the enterprise name
     * @throws IllegalArgumentException if {@code enterprise} is empty
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHEC}
     */
    @Flavors(GitHubFlavor.GHEC)
    public void removeAnnouncementBannerForEnterprise(final String enterprise) throws IOException {
        requireFlavor(GitHubFlavor.GHEC);
        caller.send(delete(ANNOUNCEMENT, enterprise));
    }
}
