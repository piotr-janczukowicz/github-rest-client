package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNotBlank;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/** GitHub Enterprise Server live migration REST resource for API version {@code 2026-03-10}. */
public final class GitHubEnterpriseAdminLiveMigrationsApi extends GitHubResourceApi {

    private static final PathTemplate MIGRATIONS = PathTemplate.of("/enterprise/live-migrations");
    private static final PathTemplate MIGRATION = PathTemplate.of("/enterprise/live-migrations/{migration_id}");
    private static final PathTemplate CANCEL = PathTemplate.of("/enterprise/live-migrations/{migration_id}/cancel");
    private static final PathTemplate CUTOVER = PathTemplate.of("/enterprise/live-migrations/{migration_id}/cutover");
    private static final PathTemplate PAUSE = PathTemplate.of("/enterprise/live-migrations/{migration_id}/pause");
    private static final PathTemplate RESUME = PathTemplate.of("/enterprise/live-migrations/{migration_id}/resume");
    private static final PathTemplate REVERT_CUTOVER =
            PathTemplate.of("/enterprise/live-migrations/{migration_id}/revert-cutover");
    private static final PathTemplate START = PathTemplate.of("/enterprise/live-migrations/{migration_id}/start");

    /**
     * Resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubEnterpriseAdminLiveMigrationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List live migrations ({@code GET /enterprise/live-migrations}).
     *
     * @return one page of migrations
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubLiveMigrationList liveMigrationList() throws IOException {
        return liveMigrationList(GitHubLiveMigrationListFilter.empty());
    }

    /**
     * List live migrations ({@code GET /enterprise/live-migrations}).
     *
     * @param filter page size and status
     * @return one page of migrations
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubLiveMigrationList liveMigrationList(final GitHubLiveMigrationListFilter filter) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);

        return caller.fetch(get(MIGRATIONS).withQuery(filter.toQueryParameters()), GitHubLiveMigrationList.class);
    }

    /**
     * List live migrations ({@code GET /enterprise/live-migrations}).
     *
     * @param filter page size and status
     * @param after cursor: the {@code next_cursor} of the previous page
     * @return the page after the cursor
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IllegalArgumentException if {@code after} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubLiveMigrationList liveMigrationList(final GitHubLiveMigrationListFilter filter, final String after)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(filter, PARAM_FILTER);
        requireNotBlank(after, "after");

        final Map<String, String> query = new LinkedHashMap<>(filter.toQueryParameters());
        query.put("after", after);
        return caller.fetch(get(MIGRATIONS).withQuery(query), GitHubLiveMigrationList.class);
    }

    /**
     * Create a live migration ({@code POST /enterprise/live-migrations}).
     *
     * @param payload request body
     * @return the resource
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubLiveMigrationCreateResponse liveMigrationCreate(final GitHubLiveMigrationCreatePayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(MIGRATIONS), payload), GitHubLiveMigrationCreateResponse.class);
    }

    /**
     * Get a live migration ({@code GET /enterprise/live-migrations/{migration_id}}).
     *
     * @param migrationId migration id
     * @return the resource, or empty if it does not exist
     * @throws IllegalArgumentException if {@code migrationId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public Optional<GitHubLiveMigrationDetails> liveMigrationGet(final String migrationId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetchOrEmpty(get(MIGRATION, migrationId), GitHubLiveMigrationDetails.class);
    }

    /**
     * Cancel a live migration ({@code POST /enterprise/live-migrations/{migration_id}/cancel}).
     *
     * @param migrationId migration id
     * @throws IllegalArgumentException if {@code migrationId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void liveMigrationCancel(final String migrationId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(post(CANCEL, migrationId));
    }

    /**
     * Cut over a live migration ({@code POST /enterprise/live-migrations/{migration_id}/cutover}).
     *
     * @param migrationId migration id
     * @throws IllegalArgumentException if {@code migrationId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void liveMigrationCutover(final String migrationId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(post(CUTOVER, migrationId));
    }

    /**
     * Cut over a live migration ({@code POST /enterprise/live-migrations/{migration_id}/cutover}).
     *
     * @param migrationId migration id
     * @param payload request body
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code migrationId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void liveMigrationCutover(final String migrationId, final GitHubLiveMigrationCutoverPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(CUTOVER, migrationId), payload));
    }

    /**
     * Pause a live migration ({@code POST /enterprise/live-migrations/{migration_id}/pause}).
     *
     * @param migrationId migration id
     * @throws IllegalArgumentException if {@code migrationId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void liveMigrationPause(final String migrationId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(post(PAUSE, migrationId));
    }

    /**
     * Resume a live migration ({@code POST /enterprise/live-migrations/{migration_id}/resume}).
     *
     * @param migrationId migration id
     * @throws IllegalArgumentException if {@code migrationId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void liveMigrationResume(final String migrationId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(post(RESUME, migrationId));
    }

    /**
     * Revert the cutover of a live migration ({@code POST /enterprise/live-migrations/{migration_id}/revert-cutover}).
     *
     * @param migrationId migration id
     * @return result
     * @throws IllegalArgumentException if {@code migrationId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public GitHubLiveMigrationRevertCutoverResult liveMigrationRevertCutover(final String migrationId)
            throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        return caller.fetch(post(REVERT_CUTOVER, migrationId), GitHubLiveMigrationRevertCutoverResult.class);
    }

    /**
     * Start a live migration ({@code POST /enterprise/live-migrations/{migration_id}/start}).
     *
     * @param migrationId migration id
     * @throws IllegalArgumentException if {@code migrationId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code GHES}
     */
    @Flavors(GitHubFlavor.GHES)
    public void liveMigrationStart(final String migrationId) throws IOException {
        requireFlavor(GitHubFlavor.GHES);
        caller.send(post(START, migrationId));
    }
}
