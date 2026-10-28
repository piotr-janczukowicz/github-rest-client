package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LOCATION;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.http.HttpBody;
import io.github.piotr_janczukowicz.http.HttpHeaders;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpResponse;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubDependencyGraphSbomsApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String SBOM_UUID = "3d9b6c3e-5b1e-4c7a-9f7e-0a1b2c3d4e5f";
    private static final URI REPORT_LOCATION = URI.create("https://sbom.example.com/reports/3d9b6c3e.json");
    private static final String SBOM_URI = "https://api.github.com/repos/octocat/Hello-World/dependency-graph/sbom";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDependencyGraphSbomsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    @SuppressWarnings("deprecation")
    void exportSbom_shouldReturnSbom_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("dependency-graph-spdx-sbom.json")));

        final GitHubDependencyGraphSpdxSbom sbom = api().exportSbom(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(sbom).isEqualTo(DependencyGraphCatalogFixtures.dependencyGraphSpdxSbom());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(SBOM_URI);
        });
    }

    @Test
    void fetchSbomReport_shouldReturnLocation_whenResponseIsFound() throws Exception {
        when(transport.execute(any()))
                .thenReturn(redirectResponse(HttpStatus.FOUND.code(), REPORT_LOCATION.toString()));

        final Optional<URI> location = api().fetchSbomReport(OWNER, REPO, SBOM_UUID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(location).contains(REPORT_LOCATION);
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(SBOM_URI + "/fetch-report/" + SBOM_UUID);
        });
    }

    @Test
    void fetchSbomReport_shouldReturnEmpty_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        final Optional<URI> location = api().fetchSbomReport(OWNER, REPO, SBOM_UUID);

        assertThat(location).isEmpty();
    }

    @Test
    void generateSbomReport_shouldReturnResponse_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("generate-sbom-report-response.json")));

        final GitHubGenerateSbomReportResponse response = api().generateSbomReport(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(response).isEqualTo(DependencyGraphCatalogFixtures.generateSbomReportResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(SBOM_URI + "/generate-report");
        });
    }

    private GitHubDependencyGraphSbomsApi api() {
        return new GitHubDependencyGraphSbomsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubDependencyGraphSbomsApiTest.class, fileName);
    }

    private static HttpResponse redirectResponse(final int statusCode, final String location) {
        return new HttpResponse(statusCode, HttpHeaders.of(Map.of(LOCATION, location)), HttpBody.absent());
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
