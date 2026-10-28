package io.github.piotr_janczukowicz.github.api.users;

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
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;

/** GitHub user attestations REST resource for API version {@code 2026-03-10}. */
public final class GitHubUsersAttestationsApi extends GitHubResourceApi {

    private static final PathTemplate ATTESTATION = PathTemplate.of("/users/{username}/attestations/{attestation_id}");
    private static final PathTemplate ATTESTATIONS_DIGEST =
            PathTemplate.of("/users/{username}/attestations/digest/{+subject_digest}");
    private static final PathTemplate ATTESTATIONS_DELETE_REQUEST =
            PathTemplate.of("/users/{username}/attestations/delete-request");
    private static final PathTemplate ATTESTATIONS_BULK_LIST =
            PathTemplate.of("/users/{username}/attestations/bulk-list");
    private static final PathTemplate ATTESTATIONS_SUBJECT_DIGEST =
            PathTemplate.of("/users/{username}/attestations/{+subject_digest}");

    /**
     * Users resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubUsersAttestationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List attestations ({@code GET /users/{username}/attestations/{subject_digest}}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()}. See {@link #listAttestations(String, String,
     * GitHubListAttestationsFilter, GitHubPageRequest)}.
     *
     * @param username GitHub username
     * @param subjectDigest subject digest
     * @return first HTTP page of attestations, empty when GitHub answers 204
     * @throws IllegalArgumentException if {@code username} or {@code subjectDigest} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubAttestation> listAttestations(final String username, final String subjectDigest)
            throws IOException {
        return listAttestations(
                username, subjectDigest, GitHubListAttestationsFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List attestations ({@code GET /users/{username}/attestations/{subject_digest}}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param username GitHub username
     * @param subjectDigest subject digest
     * @param request pagination token
     * @return first HTTP page of attestations, empty when GitHub answers 204
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code username} or {@code subjectDigest} is blank, or {@code request} is a
     *     page or since variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubAttestation> listAttestations(
            final String username, final String subjectDigest, final GitHubPageRequest request) throws IOException {
        return listAttestations(username, subjectDigest, GitHubListAttestationsFilter.empty(), request);
    }

    /**
     * List attestations ({@code GET /users/{username}/attestations/{subject_digest}}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param username GitHub username
     * @param subjectDigest subject digest
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of attestations, empty when GitHub answers 204
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code username} or {@code subjectDigest} is blank, or {@code request} is a
     *     page or since variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubAttestation> listAttestations(
            final String username,
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
                username,
                subjectDigest);
    }

    /**
     * List attestations by bulk subject digests ({@code POST /users/{username}/attestations/bulk-list}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()}. See {@link #listAttestationsBulk(String,
     * GitHubListAttestationsBulkPayload, GitHubPageRequest)}.
     *
     * @param username GitHub username
     * @param payload bulk subject digests
     * @return attestations bulk response
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubAttestationsBulk listAttestationsBulk(
            final String username, final GitHubListAttestationsBulkPayload payload) throws IOException {
        return listAttestationsBulk(username, payload, GitHubPageRequest.byCursor());
    }

    /**
     * List attestations by bulk subject digests ({@code POST /users/{username}/attestations/bulk-list}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param username GitHub username
     * @param payload bulk subject digests
     * @param request pagination token
     * @return attestations bulk response
     * @throws NullPointerException if {@code payload} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank, or {@code request} is a page or since variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubAttestationsBulk listAttestationsBulk(
            final String username, final GitHubListAttestationsBulkPayload payload, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);
        requireNonNull(request, PARAM_REQUEST);
        return caller.fetch(
                caller.json(
                        caller.pageParameters(
                                post(ATTESTATIONS_BULK_LIST, username),
                                request,
                                GitHubPageRequestKindValidators.CURSOR),
                        payload),
                GitHubAttestationsBulk.class);
    }

    /**
     * Delete attestations in bulk ({@code POST /users/{username}/attestations/delete-request}).
     *
     * @param username GitHub username
     * @param payload attestations to delete
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteAttestationsBulk(final String username, final GitHubDeleteAttestationsBulkPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(ATTESTATIONS_DELETE_REQUEST, username), payload));
    }

    /**
     * Delete attestations by subject digest ({@code DELETE /users/{username}/attestations/digest/{subject_digest}}).
     *
     * @param username GitHub username
     * @param subjectDigest subject digest
     * @throws IllegalArgumentException if {@code username} or {@code subjectDigest} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteAttestationsBySubjectDigest(final String username, final String subjectDigest)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        caller.send(delete(ATTESTATIONS_DIGEST, username, subjectDigest));
    }

    /**
     * Delete attestations by ID ({@code DELETE /users/{username}/attestations/{attestation_id}}).
     *
     * @param username GitHub username
     * @param attestationId attestation id
     * @throws IllegalArgumentException if {@code username} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteAttestationsById(final String username, final long attestationId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ATTESTATION, username, attestationId));
    }
}
