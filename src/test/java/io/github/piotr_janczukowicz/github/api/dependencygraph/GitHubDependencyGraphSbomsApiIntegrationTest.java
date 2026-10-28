package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpStatus.FOUND;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.accepted;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.dependencyGraphApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.forbidden;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.exception.GitHubForbiddenException;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubDependencyGraphSbomsApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String SBOM_UUID = "3d9b6c3e-5b1e-4c7a-9f7e-0a1b2c3d4e5f";
    private static final URI REPORT_LOCATION = URI.create("https://sbom.example.com/reports/3d9b6c3e.json");
    private static final String SBOM_PATH = "/repos/octocat/Hello-World/dependency-graph/sbom";
    private static final String FETCH_REPORT_PATH = SBOM_PATH + "/fetch-report/" + SBOM_UUID;
    private static final String GENERATE_REPORT_PATH = SBOM_PATH + "/generate-report";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    @SuppressWarnings("deprecation")
    void exportSbom_shouldReturnSbom_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                SBOM_PATH,
                okJson(jsonBytes(
                        GitHubDependencyGraphSbomsApiIntegrationTest.class, "dependency-graph-spdx-sbom.json")));

        final GitHubDependencyGraphSpdxSbom sbom;
        try (final GitHubApiWireMockSupport.DependencyGraphApiClient api = dependencyGraphApi(HTTP)) {
            sbom = api.api().sboms().exportSbom(OWNER, REPO);
        }

        final GitHubDependencyGraphSpdxSbom expected = CODEC.decode(
                jsonBytes(GitHubDependencyGraphSbomsApiIntegrationTest.class, "dependency-graph-spdx-sbom.json"),
                GitHubDependencyGraphSpdxSbom.class);

        assertThat(sbom).isEqualTo(expected);
        HTTP.verifyRequested(GET, SBOM_PATH);
    }

    @Test
    void fetchSbomReport_shouldReturnLocation_whenResponseIsFound() throws Exception {
        HTTP.stubRedirect(FETCH_REPORT_PATH, REPORT_LOCATION.toString(), FOUND.code());

        final Optional<URI> location;
        try (final GitHubApiWireMockSupport.DependencyGraphApiClient api = dependencyGraphApi(HTTP)) {
            location = api.api().sboms().fetchSbomReport(OWNER, REPO, SBOM_UUID);
        }

        assertThat(location).contains(REPORT_LOCATION);
        HTTP.verifyRequested(GET, FETCH_REPORT_PATH);
    }

    @Test
    void fetchSbomReport_shouldReturnEmpty_whenResponseIsAccepted() throws Exception {
        HTTP.stub(GET, FETCH_REPORT_PATH, accepted());

        final Optional<URI> location;
        try (final GitHubApiWireMockSupport.DependencyGraphApiClient api = dependencyGraphApi(HTTP)) {
            location = api.api().sboms().fetchSbomReport(OWNER, REPO, SBOM_UUID);
        }

        assertThat(location).isEmpty();
        HTTP.verifyRequested(GET, FETCH_REPORT_PATH);
    }

    @Test
    void fetchSbomReport_shouldThrow_whenResponseIsForbidden() throws Exception {
        HTTP.stub(GET, FETCH_REPORT_PATH, forbidden());

        final Throwable thrown;
        try (final GitHubApiWireMockSupport.DependencyGraphApiClient api = dependencyGraphApi(HTTP)) {
            final GitHubDependencyGraphSbomsApi sboms = api.api().sboms();

            thrown = catchThrowable(() -> sboms.fetchSbomReport(OWNER, REPO, SBOM_UUID));
        }

        assertThat(thrown).isInstanceOf(GitHubForbiddenException.class);
        HTTP.verifyRequested(GET, FETCH_REPORT_PATH);
    }

    @Test
    void generateSbomReport_shouldReturnResponse_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                GET,
                GENERATE_REPORT_PATH,
                createdJson(jsonBytes(
                        GitHubDependencyGraphSbomsApiIntegrationTest.class, "generate-sbom-report-response.json")));

        final GitHubGenerateSbomReportResponse response;
        try (final GitHubApiWireMockSupport.DependencyGraphApiClient api = dependencyGraphApi(HTTP)) {
            response = api.api().sboms().generateSbomReport(OWNER, REPO);
        }

        final GitHubGenerateSbomReportResponse expected = CODEC.decode(
                jsonBytes(GitHubDependencyGraphSbomsApiIntegrationTest.class, "generate-sbom-report-response.json"),
                GitHubGenerateSbomReportResponse.class);

        assertThat(response).isEqualTo(expected);
        HTTP.verifyRequested(GET, GENERATE_REPORT_PATH);
    }
}
