package io.github.piotr_janczukowicz.github.api.dependencygraph;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

/** GitHub software bill of materials (SBOM) REST resource for API version {@code 2026-03-10}. */
public final class GitHubDependencyGraphSbomsApi extends GitHubResourceApi {

    private static final PathTemplate SBOM = PathTemplate.of("/repos/{owner}/{repo}/dependency-graph/sbom");
    private static final PathTemplate SBOM_REPORT =
            PathTemplate.of("/repos/{owner}/{repo}/dependency-graph/sbom/fetch-report/{sbom_uuid}");
    private static final PathTemplate SBOM_GENERATE_REPORT =
            PathTemplate.of("/repos/{owner}/{repo}/dependency-graph/sbom/generate-report");

    /**
     * SBOM resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubDependencyGraphSbomsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Export a software bill of materials (SBOM) for a repository ({@code GET
     * /repos/{owner}/{repo}/dependency-graph/sbom}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return SBOM in SPDX JSON format
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @deprecated Closing down notice: this operation will not be accessible after {@code 2026-11-13}; migrate to the
     *     asynchronous flow ({@link #generateSbomReport(String, String)}, then {@link #fetchSbomReport(String, String,
     *     String)}). Deprecation date {@code 2026-05-12}; removal date {@code 2026-11-13}.
     */
    @Deprecated(since = "2026-05-12")
    public GitHubDependencyGraphSpdxSbom exportSbom(final String owner, final String repo) throws IOException {
        return caller.fetch(get(SBOM, owner, repo), GitHubDependencyGraphSpdxSbom.class);
    }

    /**
     * Fetch a software bill of materials (SBOM) for a repository ({@code GET
     * /repos/{owner}/{repo}/dependency-graph/sbom/fetch-report/{sbom_uuid}}). When the SBOM is ready GitHub answers
     * with a redirect to a temporary download URL.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param sbomUuid SBOM report UUID
     * @return redirect location of the completed SBOM, or empty while it is still being processed (HTTP 202)
     * @throws IllegalArgumentException if {@code owner}, {@code repo}, or {@code sbomUuid} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful, or is a redirect without a usable {@code Location}
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<URI> fetchSbomReport(final String owner, final String repo, final String sbomUuid)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.redirectLocationIfAny(get(SBOM_REPORT, owner, repo, sbomUuid));
    }

    /**
     * Request generation of a software bill of materials (SBOM) for a repository ({@code GET
     * /repos/{owner}/{repo}/dependency-graph/sbom/generate-report}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @return response holding the URL to poll for the SBOM
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubGenerateSbomReportResponse generateSbomReport(final String owner, final String repo)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        return caller.fetch(get(SBOM_GENERATE_REPORT, owner, repo), GitHubGenerateSbomReportResponse.class);
    }
}
