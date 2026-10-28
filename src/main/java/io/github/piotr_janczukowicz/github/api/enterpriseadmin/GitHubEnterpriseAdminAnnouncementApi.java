package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.announcementbanners.GitHubAnnouncement;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub Enterprise Server announcement banner REST resource for API version {@code 2026-03-10}. */
@SuppressWarnings("PMD.LinguisticNaming")
public final class GitHubEnterpriseAdminAnnouncementApi extends GitHubResourceApi {

    private static final PathTemplate ANNOUNCEMENT = PathTemplate.of("/enterprise/announcement");

    /**
     * Resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminAnnouncementApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Get the global announcement banner ({@code GET /enterprise/announcement}).
     *
     * @return announcement
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubAnnouncement getAnnouncement() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(get(ANNOUNCEMENT), GitHubAnnouncement.class);
    }

    /**
     * Set the global announcement banner ({@code PATCH /enterprise/announcement}).
     *
     * @param payload announcement to set
     * @return announcement
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubAnnouncement setAnnouncement(final GitHubAnnouncement payload) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(patch(ANNOUNCEMENT), payload), GitHubAnnouncement.class);
    }

    /**
     * Remove the global announcement banner ({@code DELETE /enterprise/announcement}).
     *
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void removeAnnouncement() throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(delete(ANNOUNCEMENT));
    }
}
