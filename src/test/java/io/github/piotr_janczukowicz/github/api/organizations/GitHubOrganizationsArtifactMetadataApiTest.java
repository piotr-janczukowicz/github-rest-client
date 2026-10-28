package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubOrganizationsArtifactMetadataApiTest {

    private static final String ORG = "github";
    private static final String CLUSTER = "moda-1";
    private static final String DIGEST = "sha256:1bb1e949e55dcefc6353e7b36c8897d2a107d8e8dca49d4e3c0ea8493fc0bc72";
    private static final long JOB_ID = 123L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsArtifactMetadataApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void createArtifactDeploymentRecord_shouldPostPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("artifact-deployment-record-list.json")));
        final GitHubCreateArtifactDeploymentRecordPayload payload =
                OrganizationsCatalogFixtures.createArtifactDeploymentRecordPayload();

        final GitHubArtifactCreatedDeploymentRecords records =
                callApi(api -> api.createArtifactDeploymentRecord(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(records).isEqualTo(OrganizationsCatalogFixtures.artifactCreatedDeploymentRecords());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().toString()).contains("/orgs/github/artifacts/metadata/deployment-record");
            softly.assertThat(requestBody(request))
                    .contains("\"name\":\"awesome-image\"")
                    .contains("\"digest\":\"" + DIGEST + "\"")
                    .contains("\"status\":\"deployed\"")
                    .contains("\"logical_environment\":\"prod\"")
                    .contains("\"deployment_name\":\"deployment-pod\"");
        });
    }

    @Test
    void setClusterDeploymentRecords_shouldPostPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("artifact-created-deployment-records.json")));
        final GitHubSetClusterDeploymentRecordsPayload payload =
                OrganizationsCatalogFixtures.setClusterDeploymentRecordsPayload();

        final GitHubArtifactDeploymentRecordsPage records =
                callApi(api -> api.setClusterDeploymentRecords(ORG, CLUSTER, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(records)
                    .isEqualTo(OrganizationsCatalogFixtures.artifactDeploymentRecordsPageWithoutErrors());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().toString())
                    .contains("/orgs/github/artifacts/metadata/deployment-record/cluster/moda-1");
            softly.assertThat(requestBody(request))
                    .contains("\"logical_environment\":\"prod\"")
                    .contains("\"deployments\"")
                    .contains("\"deployment_name\":\"deployment-pod\"");
        });
    }

    @Test
    void setClusterDeploymentRecords_shouldReturnErrors_whenResponseIsMultiStatus() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.MULTI_STATUS.code(), json("artifact-deployment-record-partial-success.json")));
        final GitHubSetClusterDeploymentRecordsPayload payload =
                OrganizationsCatalogFixtures.setClusterDeploymentRecordsPayload();

        final GitHubArtifactDeploymentRecordsPage records =
                callApi(api -> api.setClusterDeploymentRecords(ORG, CLUSTER, payload));

        assertSoftly(softly -> softly.assertThat(records)
                .isEqualTo(OrganizationsCatalogFixtures.artifactDeploymentRecordsPartialSuccess()));
    }

    @Test
    void createClusterDeploymentRecordsJob_shouldPostPayload_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("cluster-deployment-records-job.json")));
        final GitHubCreateClusterDeploymentRecordsJobPayload payload =
                OrganizationsCatalogFixtures.createClusterDeploymentRecordsJobPayload();

        final GitHubArtifactClusterDeploymentRecordsJob job =
                callApi(api -> api.createClusterDeploymentRecordsJob(ORG, CLUSTER, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(job).isEqualTo(OrganizationsCatalogFixtures.artifactClusterDeploymentRecordsJob());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().toString())
                    .contains("/orgs/github/artifacts/metadata/deployment-record/cluster/moda-1/jobs");
            softly.assertThat(requestBody(request))
                    .contains("\"logical_environment\":\"prod\"")
                    .contains("\"deployments\"");
        });
    }

    @Test
    void getClusterDeploymentRecordsJob_shouldReturnStatus_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("cluster-deployment-records-job-status.json")));

        final Optional<GitHubArtifactClusterDeploymentRecordsJobStatus> jobStatus =
                callApi(api -> api.getClusterDeploymentRecordsJob(ORG, CLUSTER, JOB_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(jobStatus)
                    .contains(OrganizationsCatalogFixtures.artifactClusterDeploymentRecordsJobStatus());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString())
                    .contains("/orgs/github/artifacts/metadata/deployment-record/cluster/moda-1/jobs/123");
        });
    }

    @Test
    void getClusterDeploymentRecordsJob_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubArtifactClusterDeploymentRecordsJobStatus> jobStatus =
                callApi(api -> api.getClusterDeploymentRecordsJob(ORG, CLUSTER, JOB_ID));

        assertThat(jobStatus).isEmpty();
    }

    @Test
    void createArtifactStorageRecord_shouldPostPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("artifact-storage-records-page.json")));
        final GitHubCreateArtifactStorageRecordPayload payload =
                OrganizationsCatalogFixtures.createArtifactStorageRecordPayload();

        final GitHubArtifactStorageRecordsPage page = callApi(api -> api.createArtifactStorageRecord(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page).isEqualTo(OrganizationsCatalogFixtures.artifactStorageRecordsPage());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().toString()).contains("/orgs/github/artifacts/metadata/storage-record");
            softly.assertThat(requestBody(request))
                    .contains("\"name\":\"libfoo\"")
                    .contains("\"digest\":\"" + DIGEST + "\"")
                    .contains("\"registry_url\":\"https://reg.example.com/artifactory/\"");
        });
    }

    @Test
    void listArtifactDeploymentRecords_shouldReturnRecords_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("artifact-deployment-record-list.json")));

        final GitHubArtifactListedDeploymentRecords records =
                callApi(api -> api.listArtifactDeploymentRecords(ORG, DIGEST));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(records).isEqualTo(OrganizationsCatalogFixtures.artifactListedDeploymentRecords());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString())
                    .contains("/orgs/github/artifacts/" + DIGEST + "/metadata/deployment-records");
        });
    }

    @Test
    void listArtifactStorageRecords_shouldReturnRecords_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("artifact-storage-records-page.json")));

        final GitHubArtifactListedStorageRecords records = callApi(api -> api.listArtifactStorageRecords(ORG, DIGEST));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(records).isEqualTo(OrganizationsCatalogFixtures.artifactListedStorageRecordsActive());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString())
                    .contains("/orgs/github/artifacts/" + DIGEST + "/metadata/storage-records");
        });
    }

    @Test
    void listArtifactStorageRecords_shouldPercentEncodeSlashInOrg_whenOrgContainsSlash() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("artifact-storage-records-page.json")));

        callApi(api -> api.listArtifactStorageRecords("a/b", DIGEST));

        assertThat(captureSingleRequest().uri().getRawPath())
                .isEqualTo("/orgs/a%2Fb/artifacts/" + DIGEST + "/metadata/storage-records");
    }

    @FunctionalInterface
    private interface ArtifactMetadataApiAction<T> {
        T apply(GitHubOrganizationsArtifactMetadataApi api) throws Exception;
    }

    private <T> T callApi(final ArtifactMetadataApiAction<T> action) throws Exception {
        try (final GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationsArtifactMetadataApi(TestClients.caller(client)));
        }
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubOrganizationsArtifactMetadataApiTest.class, fileName);
    }

    private static String requestBody(final HttpRequest request) {
        return new String(request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
