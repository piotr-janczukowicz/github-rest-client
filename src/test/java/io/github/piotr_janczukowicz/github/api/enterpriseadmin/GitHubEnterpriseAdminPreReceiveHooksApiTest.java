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
class GitHubEnterpriseAdminPreReceiveHooksApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEnterpriseAdminPreReceiveHooksApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listPreReceiveHooks_shouldReturnPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("pre-receive-hook.json") + "]"));

        final GitHubPage<GitHubPreReceiveHook> page = api().listPreReceiveHooks();

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.preReceiveHook());
        assertThat(captureSingleRequest().uri().toString())
                .startsWith("https://api.github.com/admin/pre-receive-hooks?");
    }

    @Test
    void listPreReceiveHooks_shouldSendSortAndDirection_whenFilterGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("pre-receive-hook.json") + "]"));

        api().listPreReceiveHooks(EnterpriseAdminCatalogFixtures.listPreReceiveHooksFilter());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("sort", "created").hasParameter("direction", "desc");
    }

    @Test
    void listPreReceiveHooks_shouldThrow_whenRequestNull() {
        final GitHubEnterpriseAdminPreReceiveHooksApi api = api();

        final Throwable thrown =
                catchThrowable(() -> api.listPreReceiveHooks(GitHubListPreReceiveHooksFilter.empty(), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void createPreReceiveHook_shouldSendPostAndReturnResource_whenResponseIsSuccessful() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("pre-receive-hook.json")));

        final GitHubPreReceiveHook result =
                api().createPreReceiveHook(EnterpriseAdminCatalogFixtures.createPreReceiveHookPayload());

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.preReceiveHook());
        assertThat(line(captureSingleRequest())).isEqualTo("POST https://api.github.com/admin/pre-receive-hooks");
    }

    @Test
    void createPreReceiveHook_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminPreReceiveHooksApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createPreReceiveHook(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void getPreReceiveHook_shouldReturnResource_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pre-receive-hook.json")));

        final Optional<GitHubPreReceiveHook> result = api().getPreReceiveHook(1L);

        assertThat(result).contains(EnterpriseAdminCatalogFixtures.preReceiveHook());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/admin/pre-receive-hooks/1");
    }

    @Test
    void getPreReceiveHook_shouldBeEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubPreReceiveHook> result = api().getPreReceiveHook(1L);

        assertThat(result).isEmpty();
    }

    @Test
    void updatePreReceiveHook_shouldSendPatchAndReturnResource_whenResponseIsSuccessful() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("pre-receive-hook.json")));

        final GitHubPreReceiveHook result =
                api().updatePreReceiveHook(1L, EnterpriseAdminCatalogFixtures.updatePreReceiveHookPayload());

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.preReceiveHook());
        assertThat(line(captureSingleRequest())).isEqualTo("PATCH https://api.github.com/admin/pre-receive-hooks/1");
    }

    @Test
    void updatePreReceiveHook_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminPreReceiveHooksApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updatePreReceiveHook(1L, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deletePreReceiveHook_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deletePreReceiveHook(1L);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE https://api.github.com/admin/pre-receive-hooks/1");
    }

    private GitHubEnterpriseAdminPreReceiveHooksApi api() {
        return new GitHubEnterpriseAdminPreReceiveHooksApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubEnterpriseAdminPreReceiveHooksApiTest.class, fileName);
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
