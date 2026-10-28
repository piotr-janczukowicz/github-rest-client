package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.api.users.GitHubAttestation;
import io.github.piotr_janczukowicz.github.api.users.GitHubAttestations;
import io.github.piotr_janczukowicz.github.api.users.GitHubAttestationsBulk;
import io.github.piotr_janczukowicz.github.api.users.GitHubDeleteAttestationsBulkPayload;
import io.github.piotr_janczukowicz.github.api.users.GitHubListAttestationsBulkPayload;
import io.github.piotr_janczukowicz.github.api.users.GitHubListAttestationsFilter;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
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

/** GitHub organization attestations REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsAttestationsApi extends GitHubResourceApi {

    private static final PathTemplate ATTESTATIONS_REPOSITORIES =
            PathTemplate.of("/orgs/{org}/attestations/repositories");
    private static final PathTemplate ATTESTATION = PathTemplate.of("/orgs/{org}/attestations/{attestation_id}");
    private static final PathTemplate ATTESTATIONS_DIGEST =
            PathTemplate.of("/orgs/{org}/attestations/digest/{+subject_digest}");
    private static final PathTemplate ATTESTATIONS_DELETE_REQUEST =
            PathTemplate.of("/orgs/{org}/attestations/delete-request");
    private static final PathTemplate ATTESTATIONS_BULK_LIST = PathTemplate.of("/orgs/{org}/attestations/bulk-list");
    private static final PathTemplate ATTESTATIONS_SUBJECT_DIGEST =
            PathTemplate.of("/orgs/{org}/attestations/{+subject_digest}");

    private static final JsonType<List<GitHubAttestationRepository>> ATTESTATION_REPOSITORIES_TYPE =
            JsonTypes.listOf(GitHubAttestationRepository.class);

    /**
     * Attestations resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsAttestationsApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * List attestations ({@code GET /orgs/{org}/attestations/{subject_digest}}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()}. See {@link #listAttestations(String, String,
     * GitHubListAttestationsFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @param subjectDigest subject digest
     * @return first HTTP page of attestations
     * @throws IllegalArgumentException if {@code org} or {@code subjectDigest} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubAttestation> listAttestations(final String org, final String subjectDigest)
            throws IOException {
        return listAttestations(org, subjectDigest, GitHubListAttestationsFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List attestations ({@code GET /orgs/{org}/attestations/{subject_digest}}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param subjectDigest subject digest
     * @param request pagination token
     * @return first HTTP page of attestations
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} or {@code subjectDigest} is blank, or {@code request} is a page
     *     or since variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubAttestation> listAttestations(
            final String org, final String subjectDigest, final GitHubPageRequest request) throws IOException {
        return listAttestations(org, subjectDigest, GitHubListAttestationsFilter.empty(), request);
    }

    /**
     * List attestations ({@code GET /orgs/{org}/attestations/{subject_digest}}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param subjectDigest subject digest
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of attestations
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} or {@code subjectDigest} is blank, or {@code request} is a page
     *     or since variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubAttestation> listAttestations(
            final String org,
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
                org,
                subjectDigest);
    }

    /**
     * List attestations by bulk subject digests ({@code POST /orgs/{org}/attestations/bulk-list}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()}. See {@link #listAttestationsBulk(String,
     * GitHubListAttestationsBulkPayload, GitHubPageRequest)}.
     *
     * @param org organization name
     * @param payload bulk subject digests
     * @return attestations bulk response
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubAttestationsBulk listAttestationsBulk(
            final String org, final GitHubListAttestationsBulkPayload payload) throws IOException {
        return listAttestationsBulk(org, payload, GitHubPageRequest.byCursor());
    }

    /**
     * List attestations by bulk subject digests ({@code POST /orgs/{org}/attestations/bulk-list}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param payload bulk subject digests
     * @param request pagination token
     * @return attestations bulk response
     * @throws NullPointerException if {@code payload} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a page or since variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is HTTP 404
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubAttestationsBulk listAttestationsBulk(
            final String org, final GitHubListAttestationsBulkPayload payload, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);
        requireNonNull(request, PARAM_REQUEST);
        return caller.fetch(
                caller.json(
                        caller.pageParameters(
                                post(ATTESTATIONS_BULK_LIST, org), request, GitHubPageRequestKindValidators.CURSOR),
                        payload),
                GitHubAttestationsBulk.class);
    }

    /**
     * Delete attestations in bulk ({@code POST /orgs/{org}/attestations/delete-request}).
     *
     * @param org organization name
     * @param payload attestations to delete
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteAttestationsBulk(final String org, final GitHubDeleteAttestationsBulkPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        caller.send(caller.json(post(ATTESTATIONS_DELETE_REQUEST, org), payload));
    }

    /**
     * Delete attestations by subject digest ({@code DELETE /orgs/{org}/attestations/digest/{subject_digest}}).
     *
     * @param org organization name
     * @param subjectDigest subject digest
     * @throws IllegalArgumentException if {@code org} or {@code subjectDigest} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteAttestationsBySubjectDigest(final String org, final String subjectDigest) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ATTESTATIONS_DIGEST, org, subjectDigest));
    }

    /**
     * Delete attestations by ID ({@code DELETE /orgs/{org}/attestations/{attestation_id}}).
     *
     * @param org organization name
     * @param attestationId attestation id
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public void deleteAttestationsById(final String org, final long attestationId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        caller.send(delete(ATTESTATION, org, attestationId));
    }

    /**
     * List attestation repositories ({@code GET /orgs/{org}/attestations/repositories}).
     *
     * <p>Uses {@link GitHubPageRequest#byCursor()}. See {@link #listAttestationRepositories(String,
     * GitHubListAttestationsFilter, GitHubPageRequest)}.
     *
     * @param org organization name
     * @return first HTTP page of attestation repositories
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubAttestationRepository> listAttestationRepositories(final String org) throws IOException {
        return listAttestationRepositories(org, GitHubListAttestationsFilter.empty(), GitHubPageRequest.byCursor());
    }

    /**
     * List attestation repositories ({@code GET /orgs/{org}/attestations/repositories}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param request pagination token
     * @return first HTTP page of attestation repositories
     * @throws NullPointerException if {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a page or since variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubAttestationRepository> listAttestationRepositories(
            final String org, final GitHubPageRequest request) throws IOException {
        return listAttestationRepositories(org, GitHubListAttestationsFilter.empty(), request);
    }

    /**
     * List attestation repositories ({@code GET /orgs/{org}/attestations/repositories}).
     *
     * <p>Accepts {@link io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest} and
     * {@link io.github.piotr_janczukowicz.github.pagination.GitHubNoneRequest} only.
     *
     * @param org organization name
     * @param filter query filters
     * @param request pagination token
     * @return first HTTP page of attestation repositories
     * @throws NullPointerException if {@code filter} or {@code request} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code request} is a page or since variant
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubPage<GitHubAttestationRepository> listAttestationRepositories(
            final String org, final GitHubListAttestationsFilter filter, final GitHubPageRequest request)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(filter, PARAM_FILTER);
        requireNonNull(request, PARAM_REQUEST);

        return list(
                ATTESTATIONS_REPOSITORIES,
                filter.toQueryParameters(),
                request,
                GitHubListSpec.array(GitHubPageRequestKindValidators.CURSOR, ATTESTATION_REPOSITORIES_TYPE),
                org);
    }
}
