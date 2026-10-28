package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ARTIFACT_CLUSTER;
import static io.github.piotr_janczukowicz.github.api.GitHubSchemaPatterns.ARTIFACT_DIGEST;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMatches;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireMaxLength;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;
import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNotBlank;

import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.github.client.GitHubResourceApi;
import io.github.piotr_janczukowicz.github.client.exception.GitHubApiException;
import io.github.piotr_janczukowicz.uri.PathTemplate;
import java.io.IOException;
import java.util.Optional;

/** GitHub organization artifact metadata REST resource for API version {@code 2026-03-10}. */
public final class GitHubOrganizationsArtifactMetadataApi extends GitHubResourceApi {

    private static final PathTemplate ARTIFACT_STORAGE_RECORDS =
            PathTemplate.of("/orgs/{org}/artifacts/{+subject_digest}/metadata/storage-records");
    private static final PathTemplate ARTIFACT_DEPLOYMENT_RECORDS =
            PathTemplate.of("/orgs/{org}/artifacts/{subject_digest}/metadata/deployment-records");
    private static final PathTemplate STORAGE_RECORD = PathTemplate.of("/orgs/{org}/artifacts/metadata/storage-record");
    private static final PathTemplate DEPLOYMENT_RECORD_CLUSTER_JOB =
            PathTemplate.of("/orgs/{org}/artifacts/metadata/deployment-record/cluster/{cluster}/jobs/{job_id}");
    private static final PathTemplate DEPLOYMENT_RECORD_CLUSTER_JOBS =
            PathTemplate.of("/orgs/{org}/artifacts/metadata/deployment-record/cluster/{cluster}/jobs");
    private static final PathTemplate DEPLOYMENT_RECORD_CLUSTER =
            PathTemplate.of("/orgs/{org}/artifacts/metadata/deployment-record/cluster/{cluster}");
    private static final PathTemplate DEPLOYMENT_RECORD =
            PathTemplate.of("/orgs/{org}/artifacts/metadata/deployment-record");

    private static final String PARAM_CLUSTER = "cluster";
    private static final String PARAM_SUBJECT_DIGEST = "subjectDigest";

    /**
     * Artifact metadata resource over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubOrganizationsArtifactMetadataApi(final GitHubCaller caller) {
        super(caller);
    }

    /**
     * Create an artifact deployment record ({@code POST /orgs/{org}/artifacts/metadata/deployment-record}).
     *
     * @param org organization name
     * @param payload deployment record fields
     * @return created or updated deployment records
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubArtifactCreatedDeploymentRecords createArtifactDeploymentRecord(
            final String org, final GitHubCreateArtifactDeploymentRecordPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(DEPLOYMENT_RECORD, org), payload), GitHubArtifactCreatedDeploymentRecords.class);
    }

    /**
     * Set cluster deployment records ({@code POST /orgs/{org}/artifacts/metadata/deployment-record/cluster/{cluster}}).
     *
     * <p>HTTP 200 is a full success. HTTP 207 Multi-Status is also success: it is returned when {@code partial_success}
     * is true and some deployments were skipped. Skipped items are in {@code errors}.
     *
     * @param org organization name
     * @param cluster cluster name
     * @param payload cluster deployment records
     * @return created or updated deployment records, including {@code errors} on HTTP 207
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code cluster} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @SuppressWarnings("PMD.LinguisticNaming")
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubArtifactDeploymentRecordsPage setClusterDeploymentRecords(
            final String org, final String cluster, final GitHubSetClusterDeploymentRecordsPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(DEPLOYMENT_RECORD_CLUSTER, org, requireCluster(cluster)), payload),
                GitHubArtifactDeploymentRecordsPage.class);
    }

    /**
     * Create a cluster deployment records job ({@code POST
     * /orgs/{org}/artifacts/metadata/deployment-record/cluster/{cluster}/jobs}).
     *
     * @param org organization name
     * @param cluster cluster name
     * @param payload job deployment records
     * @return background job creation response
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank, or {@code cluster} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubArtifactClusterDeploymentRecordsJob createClusterDeploymentRecordsJob(
            final String org, final String cluster, final GitHubCreateClusterDeploymentRecordsJobPayload payload)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(
                caller.json(post(DEPLOYMENT_RECORD_CLUSTER_JOBS, org, requireCluster(cluster)), payload),
                GitHubArtifactClusterDeploymentRecordsJob.class);
    }

    /**
     * Get cluster deployment records job status ({@code GET
     * /orgs/{org}/artifacts/metadata/deployment-record/cluster/{cluster}/jobs/{job_id}}).
     *
     * <p>Returns {@link Optional#empty()} on HTTP 404.
     *
     * @param org organization name
     * @param cluster cluster name
     * @param jobId job id
     * @return job status, or empty when GitHub has no such job
     * @throws IllegalArgumentException if {@code org} is blank, or {@code cluster} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful (HTTP 404 yields empty, not thrown)
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public Optional<GitHubArtifactClusterDeploymentRecordsJobStatus> getClusterDeploymentRecordsJob(
            final String org, final String cluster, final long jobId) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetchOrEmpty(
                get(DEPLOYMENT_RECORD_CLUSTER_JOB, org, requireCluster(cluster), jobId),
                GitHubArtifactClusterDeploymentRecordsJobStatus.class);
    }

    /**
     * Create an artifact storage record ({@code POST /orgs/{org}/artifacts/metadata/storage-record}).
     *
     * @param org organization name
     * @param payload storage record fields
     * @return created storage records page
     * @throws NullPointerException if {@code payload} is {@code null}
     * @throws IllegalArgumentException if {@code org} is blank
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubArtifactStorageRecordsPage createArtifactStorageRecord(
            final String org, final GitHubCreateArtifactStorageRecordPayload payload) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);
        requireNonNull(payload, PARAM_PAYLOAD);

        return caller.fetch(caller.json(post(STORAGE_RECORD, org), payload), GitHubArtifactStorageRecordsPage.class);
    }

    /**
     * List artifact deployment records ({@code GET
     * /orgs/{org}/artifacts/{subject_digest}/metadata/deployment-records}).
     *
     * @param org organization name
     * @param subjectDigest artifact subject digest
     * @return listed deployment records
     * @throws IllegalArgumentException if {@code org} is blank, or {@code subjectDigest} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubArtifactListedDeploymentRecords listArtifactDeploymentRecords(
            final String org, final String subjectDigest) throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetch(
                get(ARTIFACT_DEPLOYMENT_RECORDS, org, requireSubjectDigest(subjectDigest)),
                GitHubArtifactListedDeploymentRecords.class);
    }

    /**
     * List artifact storage records ({@code GET /orgs/{org}/artifacts/{subject_digest}/metadata/storage-records}).
     *
     * @param org organization name
     * @param subjectDigest artifact subject digest
     * @return listed storage records
     * @throws IllegalArgumentException if {@code org} is blank, or {@code subjectDigest} is invalid
     * @throws IOException if the transport fails
     * @throws GitHubApiException if the response is unsuccessful
     * @throws UnsupportedOperationException if the client is limited to a flavor other than {@code CLOUD} or
     *     {@code GHEC}
     */
    @Flavors({GitHubFlavor.CLOUD, GitHubFlavor.GHEC})
    public GitHubArtifactListedStorageRecords listArtifactStorageRecords(final String org, final String subjectDigest)
            throws IOException {
        requireFlavor(GitHubFlavor.CLOUD, GitHubFlavor.GHEC);

        return caller.fetch(
                get(ARTIFACT_STORAGE_RECORDS, org, requireSubjectDigest(subjectDigest)),
                GitHubArtifactListedStorageRecords.class);
    }

    private static String requireCluster(final String cluster) {
        return requireMatches(
                requireMaxLength(requireNotBlank(cluster, PARAM_CLUSTER), 128, PARAM_CLUSTER),
                ARTIFACT_CLUSTER,
                PARAM_CLUSTER);
    }

    private static String requireSubjectDigest(final String subjectDigest) {
        return requireMatches(
                requireNotBlank(subjectDigest, PARAM_SUBJECT_DIGEST), ARTIFACT_DIGEST, PARAM_SUBJECT_DIGEST);
    }
}
