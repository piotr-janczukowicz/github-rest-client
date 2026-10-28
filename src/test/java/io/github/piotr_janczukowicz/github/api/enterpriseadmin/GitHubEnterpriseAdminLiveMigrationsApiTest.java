package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
class GitHubEnterpriseAdminLiveMigrationsApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEnterpriseAdminLiveMigrationsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void liveMigrationCreate_shouldSendPostAndReturnResource_whenResponseIsSuccessful() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("live-migration-create-response.json")));

        final GitHubLiveMigrationCreateResponse result =
                api().liveMigrationCreate(EnterpriseAdminCatalogFixtures.liveMigrationCreatePayload());

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.liveMigrationCreateResponse());
        assertThat(line(captureSingleRequest())).isEqualTo("POST https://api.github.com/enterprise/live-migrations");
    }

    @Test
    void liveMigrationCreate_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminLiveMigrationsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.liveMigrationCreate(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void liveMigrationGet_shouldReturnResource_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("live-migration-details.json")));

        final Optional<GitHubLiveMigrationDetails> result = api().liveMigrationGet("octocat");

        assertThat(result).contains(EnterpriseAdminCatalogFixtures.liveMigrationDetails());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("GET https://api.github.com/enterprise/live-migrations/octocat");
    }

    @Test
    void liveMigrationGet_shouldBeEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubLiveMigrationDetails> result = api().liveMigrationGet("octocat");

        assertThat(result).isEmpty();
    }

    @Test
    void liveMigrationRevertCutover_shouldSendPostAndReturnResource_whenResponseIsSuccessful() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.ACCEPTED.code(), json("live-migration-revert-cutover-result.json")));

        final GitHubLiveMigrationRevertCutoverResult result = api().liveMigrationRevertCutover("octocat");

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.liveMigrationRevertCutoverResult());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/enterprise/live-migrations/octocat/revert-cutover");
    }

    @Test
    void liveMigrationCancel_shouldSendPost_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().liveMigrationCancel("octocat");

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/enterprise/live-migrations/octocat/cancel");
    }

    @Test
    void liveMigrationCutover_shouldSendPost_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().liveMigrationCutover("octocat");

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/enterprise/live-migrations/octocat/cutover");
    }

    @Test
    void liveMigrationCutover_shouldSendPayload_whenPayloadGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().liveMigrationCutover("octocat", EnterpriseAdminCatalogFixtures.liveMigrationCutoverPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/enterprise/live-migrations/octocat/cutover");
    }

    @Test
    void liveMigrationCutover_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminLiveMigrationsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.liveMigrationCutover("octocat", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void liveMigrationPause_shouldSendPost_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().liveMigrationPause("octocat");

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/enterprise/live-migrations/octocat/pause");
    }

    @Test
    void liveMigrationResume_shouldSendPost_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().liveMigrationResume("octocat");

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/enterprise/live-migrations/octocat/resume");
    }

    @Test
    void liveMigrationStart_shouldSendPost_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().liveMigrationStart("octocat");

        assertThat(line(captureSingleRequest()))
                .isEqualTo("POST https://api.github.com/enterprise/live-migrations/octocat/start");
    }

    @Test
    void liveMigrationList_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("live-migration-list.json")));

        final GitHubLiveMigrationList page = api().liveMigrationList();

        assertThat(page).isEqualTo(EnterpriseAdminCatalogFixtures.liveMigrationList());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/enterprise/live-migrations");
    }

    @Test
    void liveMigrationList_shouldSendFilter_whenFilterGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("live-migration-list.json")));

        api().liveMigrationList(EnterpriseAdminCatalogFixtures.liveMigrationListFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page_size", "1").hasParameter("status", "created");
    }

    @Test
    void liveMigrationList_shouldSendCursor_whenAfterGiven() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("live-migration-list.json")));

        api().liveMigrationList(GitHubLiveMigrationListFilter.empty(), "cursor");
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("after", "cursor");
    }

    @Test
    void liveMigrationList_shouldThrow_whenFilterNull() {
        final GitHubEnterpriseAdminLiveMigrationsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.liveMigrationList(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void liveMigrationList_shouldThrow_whenAfterBlank() {
        final GitHubEnterpriseAdminLiveMigrationsApi api = api();

        final Throwable thrown =
                catchThrowable(() -> api.liveMigrationList(GitHubLiveMigrationListFilter.empty(), " "));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("after");
    }

    private GitHubEnterpriseAdminLiveMigrationsApi api() {
        return new GitHubEnterpriseAdminLiveMigrationsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubEnterpriseAdminLiveMigrationsApiTest.class, fileName);
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
