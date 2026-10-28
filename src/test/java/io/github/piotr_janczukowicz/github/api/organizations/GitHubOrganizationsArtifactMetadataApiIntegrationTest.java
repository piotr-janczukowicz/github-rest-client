package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.acceptedJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.multiStatusJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.organizationsApi;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubOrganizationsArtifactMetadataApiIntegrationTest {

    private static final String ORG = "github";
    private static final String CLUSTER = "moda-1";
    private static final String DIGEST = "sha256:1bb1e949e55dcefc6353e7b36c8897d2a107d8e8dca49d4e3c0ea8493fc0bc72";
    private static final long JOB_ID = 123L;

    private static final String CREATE_DEPLOYMENT_RECORD_PATH = "/orgs/github/artifacts/metadata/deployment-record";
    private static final String SET_CLUSTER_DEPLOYMENT_RECORDS_PATH =
            "/orgs/github/artifacts/metadata/deployment-record/cluster/moda-1";
    private static final String CREATE_CLUSTER_JOB_PATH =
            "/orgs/github/artifacts/metadata/deployment-record/cluster/moda-1/jobs";
    private static final String GET_CLUSTER_JOB_PATH =
            "/orgs/github/artifacts/metadata/deployment-record/cluster/moda-1/jobs/123";
    private static final String CREATE_STORAGE_RECORD_PATH = "/orgs/github/artifacts/metadata/storage-record";
    private static final String LIST_DEPLOYMENT_RECORDS_PATH =
            "/orgs/github/artifacts/sha256:1bb1e949e55dcefc6353e7b36c8897d2a107d8e8dca49d4e3c0ea8493fc0bc72/metadata/deployment-records";
    private static final String LIST_STORAGE_RECORDS_PATH =
            "/orgs/github/artifacts/sha256:1bb1e949e55dcefc6353e7b36c8897d2a107d8e8dca49d4e3c0ea8493fc0bc72/metadata/storage-records";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void createArtifactDeploymentRecord_shouldReturnRecords_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, CREATE_DEPLOYMENT_RECORD_PATH, okJson(json("artifact-deployment-record-list.json")));
        final GitHubCreateArtifactDeploymentRecordPayload payload =
                OrganizationsCatalogFixtures.createArtifactDeploymentRecordPayload();

        final GitHubArtifactCreatedDeploymentRecords records;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            records = organizations.api().artifactMetadata().createArtifactDeploymentRecord(ORG, payload);
        }

        assertThat(records).isEqualTo(expectedCreatedDeploymentRecords());
        HTTP.verifyRequested(POST, CREATE_DEPLOYMENT_RECORD_PATH);
        HTTP.verifyHeader(POST, CREATE_DEPLOYMENT_RECORD_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void setClusterDeploymentRecords_shouldReturnRecords_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, SET_CLUSTER_DEPLOYMENT_RECORDS_PATH, okJson(json("artifact-created-deployment-records.json")));
        final GitHubSetClusterDeploymentRecordsPayload payload =
                OrganizationsCatalogFixtures.setClusterDeploymentRecordsPayload();

        final GitHubArtifactDeploymentRecordsPage records;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            records = organizations.api().artifactMetadata().setClusterDeploymentRecords(ORG, CLUSTER, payload);
        }

        assertThat(records).isEqualTo(expectedSetClusterDeploymentRecords());
        HTTP.verifyRequested(POST, SET_CLUSTER_DEPLOYMENT_RECORDS_PATH);
        HTTP.verifyHeader(POST, SET_CLUSTER_DEPLOYMENT_RECORDS_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void setClusterDeploymentRecords_shouldReturnErrors_whenResponseIsMultiStatus() throws Exception {
        HTTP.stub(
                POST,
                SET_CLUSTER_DEPLOYMENT_RECORDS_PATH,
                multiStatusJson(json("artifact-deployment-record-partial-success.json")));
        final GitHubSetClusterDeploymentRecordsPayload payload =
                OrganizationsCatalogFixtures.setClusterDeploymentRecordsPayload();

        final GitHubArtifactDeploymentRecordsPage records;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            records = organizations.api().artifactMetadata().setClusterDeploymentRecords(ORG, CLUSTER, payload);
        }

        assertThat(records).isEqualTo(expectedPartialSuccessDeploymentRecords());
        HTTP.verifyRequested(POST, SET_CLUSTER_DEPLOYMENT_RECORDS_PATH);
    }

    @Test
    void createClusterDeploymentRecordsJob_shouldReturnJob_whenResponseIsAccepted() throws Exception {
        HTTP.stub(POST, CREATE_CLUSTER_JOB_PATH, acceptedJson(json("cluster-deployment-records-job.json")));
        final GitHubCreateClusterDeploymentRecordsJobPayload payload =
                OrganizationsCatalogFixtures.createClusterDeploymentRecordsJobPayload();

        final GitHubArtifactClusterDeploymentRecordsJob job;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            job = organizations.api().artifactMetadata().createClusterDeploymentRecordsJob(ORG, CLUSTER, payload);
        }

        assertThat(job).isEqualTo(expectedClusterJob());
        HTTP.verifyRequested(POST, CREATE_CLUSTER_JOB_PATH);
        HTTP.verifyHeader(POST, CREATE_CLUSTER_JOB_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void getClusterDeploymentRecordsJob_shouldReturnStatus_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, GET_CLUSTER_JOB_PATH, okJson(json("cluster-deployment-records-job-status.json")));

        final Optional<GitHubArtifactClusterDeploymentRecordsJobStatus> jobStatus;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            jobStatus = organizations.api().artifactMetadata().getClusterDeploymentRecordsJob(ORG, CLUSTER, JOB_ID);
        }

        assertSoftly(softly -> {
            softly.assertThat(jobStatus).isPresent();
            softly.assertThat(jobStatus.orElseThrow()).isEqualTo(expectedClusterJobStatus());
        });
        HTTP.verifyRequested(GET, GET_CLUSTER_JOB_PATH);
    }

    @Test
    void getClusterDeploymentRecordsJob_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, GET_CLUSTER_JOB_PATH, notFound());

        final Optional<GitHubArtifactClusterDeploymentRecordsJobStatus> jobStatus;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            jobStatus = organizations.api().artifactMetadata().getClusterDeploymentRecordsJob(ORG, CLUSTER, JOB_ID);
        }

        assertThat(jobStatus).isEmpty();
        HTTP.verifyRequested(GET, GET_CLUSTER_JOB_PATH);
    }

    @Test
    void createArtifactStorageRecord_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(POST, CREATE_STORAGE_RECORD_PATH, okJson(json("artifact-storage-records-page.json")));
        final GitHubCreateArtifactStorageRecordPayload payload =
                OrganizationsCatalogFixtures.createArtifactStorageRecordPayload();

        final GitHubArtifactStorageRecordsPage page;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            page = organizations.api().artifactMetadata().createArtifactStorageRecord(ORG, payload);
        }

        assertThat(page).isEqualTo(expectedStorageRecordsPage());
        HTTP.verifyRequested(POST, CREATE_STORAGE_RECORD_PATH);
        HTTP.verifyHeader(POST, CREATE_STORAGE_RECORD_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void listArtifactDeploymentRecords_shouldReturnRecords_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, LIST_DEPLOYMENT_RECORDS_PATH, okJson(json("artifact-deployment-record-list.json")));

        final GitHubArtifactListedDeploymentRecords records;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            records = organizations.api().artifactMetadata().listArtifactDeploymentRecords(ORG, DIGEST);
        }

        assertThat(records).isEqualTo(expectedListedDeploymentRecords());
        HTTP.verifyRequested(GET, LIST_DEPLOYMENT_RECORDS_PATH);
    }

    @Test
    void listArtifactStorageRecords_shouldReturnRecords_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, LIST_STORAGE_RECORDS_PATH, okJson(json("artifact-storage-records-page.json")));

        final GitHubArtifactListedStorageRecords records;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            records = organizations.api().artifactMetadata().listArtifactStorageRecords(ORG, DIGEST);
        }

        assertThat(records).isEqualTo(expectedListedStorageRecords());
        HTTP.verifyRequested(GET, LIST_STORAGE_RECORDS_PATH);
    }

    private static byte[] json(final String fileName) {
        return jsonBytes(GitHubOrganizationsArtifactMetadataApiIntegrationTest.class, fileName);
    }

    private static GitHubArtifactCreatedDeploymentRecords expectedCreatedDeploymentRecords() {
        return CODEC.decode(
                json("artifact-created-deployment-records.json"), GitHubArtifactCreatedDeploymentRecords.class);
    }

    private static GitHubArtifactDeploymentRecordsPage expectedSetClusterDeploymentRecords() {
        return CODEC.decode(
                json("artifact-created-deployment-records.json"), GitHubArtifactDeploymentRecordsPage.class);
    }

    private static GitHubArtifactDeploymentRecordsPage expectedPartialSuccessDeploymentRecords() {
        return CODEC.decode(
                json("artifact-deployment-record-partial-success.json"), GitHubArtifactDeploymentRecordsPage.class);
    }

    private static GitHubArtifactListedDeploymentRecords expectedListedDeploymentRecords() {
        return CODEC.decode(json("artifact-deployment-record-list.json"), GitHubArtifactListedDeploymentRecords.class);
    }

    private static GitHubArtifactClusterDeploymentRecordsJob expectedClusterJob() {
        return CODEC.decode(
                json("cluster-deployment-records-job.json"), GitHubArtifactClusterDeploymentRecordsJob.class);
    }

    private static GitHubArtifactClusterDeploymentRecordsJobStatus expectedClusterJobStatus() {
        return CODEC.decode(
                json("cluster-deployment-records-job-status.json"),
                GitHubArtifactClusterDeploymentRecordsJobStatus.class);
    }

    private static GitHubArtifactListedStorageRecords expectedListedStorageRecords() {
        return CODEC.decode(json("artifact-storage-records-page.json"), GitHubArtifactListedStorageRecords.class);
    }

    private static GitHubArtifactStorageRecordsPage expectedStorageRecordsPage() {
        return CODEC.decode(json("artifact-storage-records-page.json"), GitHubArtifactStorageRecordsPage.class);
    }
}
