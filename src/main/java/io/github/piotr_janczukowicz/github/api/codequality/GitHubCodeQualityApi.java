package io.github.piotr_janczukowicz.github.api.codequality;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub code quality REST resource for API version {@code 2026-03-10}. */
public final class GitHubCodeQualityApi extends GitHubResourceApi {

    private static final PathTemplate CODE_QUALITY_FINDINGS =
            PathTemplate.of("/repos/{owner}/{repo}/code-quality/findings");
    private static final PathTemplate CODE_QUALITY_FINDING =
            PathTemplate.of("/repos/{owner}/{repo}/code-quality/findings/{finding_number}");
    private static final PathTemplate CODE_QUALITY_SETUP = PathTemplate.of("/repos/{owner}/{repo}/code-quality/setup");

    private static final GitHubListSpec<GitHubCodeQualityFinding> FINDINGS_SPEC = GitHubListSpec.array(
            GitHubPageRequestKindValidators.CURSOR, JsonTypes.listOf(GitHubCodeQualityFinding.class));

    /**
     * Code quality resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubCodeQualityApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List code quality findings for a repository ({@code GET /repos/{owner}/{repo}/code-quality/findings}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()} and an empty filter. See {@link #listFindingsForRepo(String, String,
     * GitHubListFindingsForRepoFilter, GitHubPageRequest)}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @return first page of findings
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCodeQualityFinding> listFindingsForRepo(final String owner, final String repo)
            throws IOException {
        return listFindingsForRepo(owner, repo, GitHubListFindingsForRepoFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List code quality findings for a repository ({@code GET /repos/{owner}/{repo}/code-quality/findings}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param filter query filters
     * @param request cursor pagination token
     * @return page of findings
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank or {@code request} is not a cursor
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubCodeQualityFinding> listFindingsForRepo(
            final String owner,
            final String repo,
            final GitHubListFindingsForRepoFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(CODE_QUALITY_FINDINGS, filter.toQueryParameters(), request, FINDINGS_SPEC, owner, repo);
    }

    /**
     * Get a code quality finding ({@code GET /repos/{owner}/{repo}/code-quality/findings/{finding_number}}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param findingNumber finding number
     * @return finding, or empty on HTTP 404
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCodeQualityFinding> getFinding(final String owner, final String repo, final int findingNumber)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetchOrEmpty(
                get(CODE_QUALITY_FINDING, owner, repo, findingNumber), GitHubCodeQualityFinding.class);
    }

    /**
     * Get a code quality setup configuration ({@code GET /repos/{owner}/{repo}/code-quality/setup}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return setup configuration
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCodeQualitySetup getSetup(final String owner, final String repo) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(CODE_QUALITY_SETUP, owner, repo), GitHubCodeQualitySetup.class);
    }

    /**
     * Update a code quality setup configuration ({@code PATCH /repos/{owner}/{repo}/code-quality/setup}). GitHub
     * answers HTTP 202 with the update response; HTTP 200 carries an empty object.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload configuration to apply
     * @return update response when GitHub answers HTTP 202, or empty on HTTP 200
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubCodeQualitySetupUpdateResponse> updateSetup(
            final String owner, final String repo, final GitHubCodeQualitySetupUpdate payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetchUnless(
                caller.json(patch(CODE_QUALITY_SETUP, owner, repo), payload),
                GitHubCodeQualitySetupUpdateResponse.class,
                HttpStatus.OK);
    }
}
