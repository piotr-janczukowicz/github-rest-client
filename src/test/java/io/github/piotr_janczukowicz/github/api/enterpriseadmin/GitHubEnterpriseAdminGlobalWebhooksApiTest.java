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
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubEnterpriseAdminGlobalWebhooksApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEnterpriseAdminGlobalWebhooksApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void createGlobalWebhook_shouldSendPostAndReturnResource_whenResponseIsSuccessful() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("global-hook.json")));

        final GitHubGlobalHook result =
                api().createGlobalWebhook(EnterpriseAdminCatalogFixtures.createGlobalWebhookPayload());

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.globalHook());
        assertThat(line(captureSingleRequest())).isEqualTo("POST https://api.github.com/admin/hooks");
    }

    @Test
    void createGlobalWebhook_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminGlobalWebhooksApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createGlobalWebhook(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getGlobalWebhook_shouldReturnResource_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("global-hook.json")));

        final Optional<GitHubGlobalHook> result = api().getGlobalWebhook(1L);

        assertThat(result).contains(EnterpriseAdminCatalogFixtures.globalHook());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/admin/hooks/1");
    }

    @Test
    void getGlobalWebhook_shouldBeEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubGlobalHook> result = api().getGlobalWebhook(1L);

        assertThat(result).isEmpty();
    }

    @Test
    void updateGlobalWebhook_shouldSendPatchAndReturnResource_whenResponseIsSuccessful() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("global-hook-without-secret.json")));

        final GitHubGlobalHookWithoutSecret result =
                api().updateGlobalWebhook(1L, EnterpriseAdminCatalogFixtures.updateGlobalWebhookPayload());

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.globalHookWithoutSecret());
        assertThat(line(captureSingleRequest())).isEqualTo("PATCH https://api.github.com/admin/hooks/1");
    }

    @Test
    void updateGlobalWebhook_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminGlobalWebhooksApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updateGlobalWebhook(1L, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteGlobalWebhook_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteGlobalWebhook(1L);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE https://api.github.com/admin/hooks/1");
    }

    @Test
    void listGlobalWebhooks_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[" + json("global-hook.json") + "]"));

        final GitHubPage<GitHubGlobalHook> page = api().listGlobalWebhooks();

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.globalHook());
        assertThat(captureSingleRequest().uri().toString()).startsWith("https://api.github.com/admin/hooks?");
    }

    @Test
    void listGlobalWebhooks_shouldThrow_whenRequestNull() {
        final GitHubEnterpriseAdminGlobalWebhooksApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listGlobalWebhooks(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void pingGlobalWebhook_shouldSendPost_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().pingGlobalWebhook(1L);

        assertThat(line(captureSingleRequest())).isEqualTo("POST https://api.github.com/admin/hooks/1/pings");
    }

    private GitHubEnterpriseAdminGlobalWebhooksApi api() {
        return new GitHubEnterpriseAdminGlobalWebhooksApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubEnterpriseAdminGlobalWebhooksApiTest.class, fileName);
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
