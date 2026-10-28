package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubEnterpriseAdminPreReceiveEnvironmentsApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEnterpriseAdminPreReceiveEnvironmentsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listPreReceiveEnvironments_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("pre-receive-environment.json") + "]"));

        final GitHubPage<GitHubPreReceiveEnvironment> page = api().listPreReceiveEnvironments();

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.preReceiveEnvironment());
        assertThat(captureSingleRequest().uri().toString())
                .startsWith("https://api.github.com/admin/pre-receive-environments?");
    }

    @Test
    void listPreReceiveEnvironments_shouldSendSortAndDirection_whenFilterGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("pre-receive-environment.json") + "]"));

        api().listPreReceiveEnvironments(EnterpriseAdminCatalogFixtures.listPreReceiveEnvironmentsFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("sort", "created").hasParameter("direction", "desc");
    }

    @Test
    void listPreReceiveEnvironments_shouldThrow_whenRequestNull() {
        final GitHubEnterpriseAdminPreReceiveEnvironmentsApi api = api();

        final Throwable thrown = catchThrowable(
                () -> api.listPreReceiveEnvironments(GitHubListPreReceiveEnvironmentsFilter.empty(), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void createPreReceiveEnvironment_shouldSendPostAndReturnResource_whenResponseIsSuccessful() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("pre-receive-environment.json")));

        final GitHubPreReceiveEnvironment result =
                api().createPreReceiveEnvironment(EnterpriseAdminCatalogFixtures.createPreReceiveEnvironmentPayload());

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.preReceiveEnvironment());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/admin/pre-receive-environments");
    }

    @Test
    void createPreReceiveEnvironment_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminPreReceiveEnvironmentsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createPreReceiveEnvironment(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getPreReceiveEnvironment_shouldReturnResource_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pre-receive-environment.json")));

        final Optional<GitHubPreReceiveEnvironment> result = api().getPreReceiveEnvironment(1L);

        assertThat(result).contains(EnterpriseAdminCatalogFixtures.preReceiveEnvironment());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("GET https://api.github.com/admin/pre-receive-environments/1");
    }

    @Test
    void getPreReceiveEnvironment_shouldBeEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubPreReceiveEnvironment> result = api().getPreReceiveEnvironment(1L);

        assertThat(result).isEmpty();
    }

    @Test
    void updatePreReceiveEnvironment_shouldSendPatchAndReturnResource_whenResponseIsSuccessful() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pre-receive-environment.json")));

        final GitHubPreReceiveEnvironment result = api().updatePreReceiveEnvironment(
                        1L, EnterpriseAdminCatalogFixtures.updatePreReceiveEnvironmentPayload());

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.preReceiveEnvironment());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("PATCH https://api.github.com/admin/pre-receive-environments/1");
    }

    @Test
    void updatePreReceiveEnvironment_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminPreReceiveEnvironmentsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updatePreReceiveEnvironment(1L, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deletePreReceiveEnvironment_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deletePreReceiveEnvironment(1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE https://api.github.com/admin/pre-receive-environments/1");
    }

    @Test
    void startPreReceiveEnvironmentDownload_shouldSendPostAndReturnResource_whenResponseIsSuccessful()
            throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("pre-receive-environment-download-status.json")));

        final GitHubPreReceiveEnvironmentDownloadStatus result = api().startPreReceiveEnvironmentDownload(1L);

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.preReceiveEnvironmentDownloadStatus());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/admin/pre-receive-environments/1/downloads");
    }

    @Test
    void getDownloadStatusForPreReceiveEnvironment_shouldReturnResource_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("pre-receive-environment-download-status.json")));

        final Optional<GitHubPreReceiveEnvironmentDownloadStatus> result =
                api().getDownloadStatusForPreReceiveEnvironment(1L);

        assertThat(result).contains(EnterpriseAdminCatalogFixtures.preReceiveEnvironmentDownloadStatus());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("GET https://api.github.com/admin/pre-receive-environments/1/downloads/latest");
    }

    @Test
    void getDownloadStatusForPreReceiveEnvironment_shouldBeEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubPreReceiveEnvironmentDownloadStatus> result =
                api().getDownloadStatusForPreReceiveEnvironment(1L);

        assertThat(result).isEmpty();
    }

    private GitHubEnterpriseAdminPreReceiveEnvironmentsApi api() {
        return new GitHubEnterpriseAdminPreReceiveEnvironmentsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubEnterpriseAdminPreReceiveEnvironmentsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static String line(final HttpRequest request) {
        return request.method() + " " + request.uri();
    }
}
