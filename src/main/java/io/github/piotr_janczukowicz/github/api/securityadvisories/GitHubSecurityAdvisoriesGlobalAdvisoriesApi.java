package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * GitHub global security advisories REST resource for API version {@code 2026-03-10}.
 *
 * <p>Read global security advisories.
 */
public final class GitHubSecurityAdvisoriesGlobalAdvisoriesApi extends GitHubResourceApi {

    private static final PathTemplate ADVISORY = PathTemplate.of("/advisories/{ghsa_id}");
    private static final PathTemplate ADVISORIES = PathTemplate.of("/advisories");

    private static final JsonType<List<GitHubGlobalAdvisory>> GLOBAL_ADVISORIES_TYPE =
            JsonTypes.listOf(GitHubGlobalAdvisory.class);

    /**
     * Global security advisories resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubSecurityAdvisoriesGlobalAdvisoriesApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List global advisories ({@code GET /advisories}).
     *
     * @return first HTTP page of global advisories
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubGlobalAdvisory> listGlobalAdvisories() throws IOException {
        return listGlobalAdvisories(GitHubListGlobalAdvisoriesFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List global advisories ({@code GET /advisories}).
     *
     * @param filter query filters
     * @return first HTTP page of global advisories
     * @throws NullPointerException if {@code filter} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubGlobalAdvisory> listGlobalAdvisories(final GitHubListGlobalAdvisoriesFilter filter)
            throws IOException {
        return listGlobalAdvisories(filter, GitHubPageRequest.byCursor());
    }

    /**
     * List global advisories ({@code GET /advisories}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of global advisories
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code request} is a page or since variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public GitHubPage<GitHubGlobalAdvisory> listGlobalAdvisories(
            final GitHubListGlobalAdvisoriesFilter filter, final GitHubPageRequest request) throws IOException {
        requireNonNull(filter, PARAM_FILTER);

        return list(
                ADVISORIES,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, GLOBAL_ADVISORIES_TYPE));
    }

    /**
     * Get a global advisory ({@code GET /advisories/{ghsa_id}}).
     *
     * @param ghsaId GHSA identifier
     * @return the global advisory, or empty if not found (HTTP 404)
     * @throws IllegalArgumentException if {@code ghsaId} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     */
    public Optional<GitHubGlobalAdvisory> getGlobalAdvisory(final String ghsaId) throws IOException {
        return caller.fetchOrEmpty(get(ADVISORY, ghsaId), GitHubGlobalAdvisory.class);
    }
}
