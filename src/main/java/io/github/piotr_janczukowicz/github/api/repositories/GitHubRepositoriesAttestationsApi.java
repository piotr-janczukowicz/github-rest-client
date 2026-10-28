package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.users.GitHubAttestation;
import io.github.piotr_janczukowicz.github.api.users.GitHubAttestations;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.github.client.pagination.GitHubListSpec;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequestKindValidators;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub repository attestations REST resource for API version {@code 2026-03-10}. */
public final class GitHubRepositoriesAttestationsApi extends GitHubResourceApi {

    private static final PathTemplate ATTESTATIONS = PathTemplate.of("/repos/{owner}/{repo}/attestations");
    private static final PathTemplate ATTESTATIONS_SUBJECT_DIGEST =
            PathTemplate.of("/repos/{owner}/{repo}/attestations/{+subject_digest}");

    /**
     * Attestations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubRepositoriesAttestationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List attestations ({@code GET /repos/{owner}/{repo}/attestations/{subject_digest}}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()}. See {@link #listAttestations(String, String, String,
     * GitHubListAttestationsFilter, GitHubPageRequest)}.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param subjectDigest digest of the attestation subject, for example {@code sha256:...}
     * @return first HTTP page of attestations
     * @throws IllegalArgumentException if any argument is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubAttestation> listAttestations(
            final String owner, final String repo, final String subjectDigest) throws IOException {
        return listAttestations(
                owner, repo, subjectDigest, GitHubListAttestationsFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List attestations ({@code GET /repos/{owner}/{repo}/attestations/{subject_digest}}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param owner repository owner
     * @param repo repository name
     * @param subjectDigest digest of the attestation subject, for example {@code sha256:...}
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of attestations
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if any argument is blank, or {@code request} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubAttestation> listAttestations(
            final String owner,
            final String repo,
            final String subjectDigest,
            final GitHubListAttestationsFilter filter,
            final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ATTESTATIONS_SUBJECT_DIGEST,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.wrapper(
                        GitHubPageRequestKindValidators.CURSOR,
                        GitHubAttestations.class,
                        GitHubAttestations::attestations,
                        null),
                owner,
                repo,
                subjectDigest);
    }

    /**
     * Create an attestation ({@code POST /repos/{owner}/{repo}/attestations}).
     *
     * @param owner repository owner
     * @param repo repository name
     * @param payload attestation bundle
     * @return created attestation ID
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code owner} or {@code repo} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubCreatedAttestation createAttestation(
            final String owner, final String repo, final GitHubCreateAttestationPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(ATTESTATIONS, owner, repo), payload), GitHubCreatedAttestation.class);
    }
}
