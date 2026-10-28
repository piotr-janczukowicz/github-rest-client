package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubDependabotRepositoryAccessApiTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final String ORG = "octo-org";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDependabotRepositoryAccessApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void repositoryAccessForEnterprise_shouldReturnDetails_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("dependabot-repository-access-details.json")));

        final GitHubDependabotRepositoryAccessDetails details = api().repositoryAccessForEnterprise(ENTERPRISE);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(details).isEqualTo(DependabotCatalogFixtures.dependabotRepositoryAccessDetails());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/enterprises/octo-enterprise/dependabot/repository-access?"
                            + "page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void repositoryAccessForEnterprise_shouldSendPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("dependabot-repository-access-details.json")));

        api().repositoryAccessForEnterprise(ENTERPRISE, GitHubPageNumberRequest.of(2, 30));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "30");
    }

    @Test
    void repositoryAccessForEnterprise_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final GitHubDependabotRepositoryAccessApi api = api();

        final Throwable thrown = catchThrowable(() -> api.repositoryAccessForEnterprise(ENTERPRISE));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void updateRepositoryAccessForEnterprise_shouldPatchPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().updateRepositoryAccessForEnterprise(
                        ENTERPRISE, DependabotCatalogFixtures.updateRepositoryAccessPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/enterprises/octo-enterprise/dependabot/repository-access");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void setRepositoryAccessDefaultLevelForEnterprise_shouldPutPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setRepositoryAccessDefaultLevelForEnterprise(
                        ENTERPRISE, DependabotCatalogFixtures.setRepositoryAccessDefaultLevelPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/enterprises/octo-enterprise/dependabot/repository-access/default-level");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void repositoryAccessForOrg_shouldReturnDetails_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("dependabot-repository-access-details.json")));

        final GitHubDependabotRepositoryAccessDetails details = api().repositoryAccessForOrg(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(details).isEqualTo(DependabotCatalogFixtures.dependabotRepositoryAccessDetails());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octo-org/dependabot/repository-access?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void repositoryAccessForOrg_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final GitHubDependabotRepositoryAccessApi api = api();

        final Throwable thrown = catchThrowable(() -> api.repositoryAccessForOrg(ORG));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void repositoryAccessForOrg_shouldSendPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("dependabot-repository-access-details.json")));

        api().repositoryAccessForOrg(ORG, GitHubPageNumberRequest.of(3, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "3").hasParameter("per_page", "10");
    }

    @Test
    void updateRepositoryAccessForOrg_shouldPatchPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().updateRepositoryAccessForOrg(ORG, DependabotCatalogFixtures.updateRepositoryAccessPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/dependabot/repository-access");
        });
    }

    @Test
    void setRepositoryAccessDefaultLevel_shouldPutPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setRepositoryAccessDefaultLevel(ORG, DependabotCatalogFixtures.setRepositoryAccessDefaultLevelPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/dependabot/repository-access/default-level");
        });
    }

    @ParameterizedTest
    @MethodSource("nullRequestCalls")
    void validation_shouldThrow_whenRequestNull(final AccessCall call) {
        final GitHubDependabotRepositoryAccessApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @ParameterizedTest
    @MethodSource("nullPayloadCalls")
    void validation_shouldThrow_whenPayloadNull(final AccessCall call) {
        final GitHubDependabotRepositoryAccessApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @ParameterizedTest
    @MethodSource("invalidCalls")
    void call_shouldThrowIllegalArgument_whenInvalid(final AccessCall call, final String messageFragment) {
        final GitHubDependabotRepositoryAccessApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private GitHubDependabotRepositoryAccessApi api() {
        return new GitHubDependabotRepositoryAccessApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubDependabotRepositoryAccessApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<AccessCall> nullRequestCalls() {
        return Stream.of(
                api -> api.repositoryAccessForEnterprise(ENTERPRISE, null),
                api -> api.repositoryAccessForOrg(ORG, null));
    }

    private static Stream<AccessCall> nullPayloadCalls() {
        return Stream.of(
                api -> api.updateRepositoryAccessForEnterprise(ENTERPRISE, null),
                api -> api.setRepositoryAccessDefaultLevelForEnterprise(ENTERPRISE, null),
                api -> api.updateRepositoryAccessForOrg(ORG, null),
                api -> api.setRepositoryAccessDefaultLevel(ORG, null));
    }

    private static Stream<Arguments> invalidCalls() {
        final GitHubUpdateRepositoryAccessPayload update = DependabotCatalogFixtures.updateRepositoryAccessPayload();
        final GitHubSetRepositoryAccessDefaultLevelPayload level =
                DependabotCatalogFixtures.setRepositoryAccessDefaultLevelPayload();
        return Stream.of(
                Arguments.of((AccessCall) api -> api.repositoryAccessForEnterprise(" "), "enterprise"),
                Arguments.of((AccessCall) api -> api.updateRepositoryAccessForEnterprise(" ", update), "enterprise"),
                Arguments.of(
                        (AccessCall) api -> api.setRepositoryAccessDefaultLevelForEnterprise(" ", level), "enterprise"),
                Arguments.of((AccessCall) api -> api.repositoryAccessForOrg(" "), "org"),
                Arguments.of((AccessCall) api -> api.updateRepositoryAccessForOrg(" ", update), "org"),
                Arguments.of((AccessCall) api -> api.setRepositoryAccessDefaultLevel(" ", level), "org"),
                Arguments.of(
                        (AccessCall) api -> api.repositoryAccessForOrg(ORG, GitHubPageRequest.byCursor()),
                        "unsupported pagination request: cursor"));
    }

    @FunctionalInterface
    interface AccessCall {
        void execute(GitHubDependabotRepositoryAccessApi api) throws Exception;
    }
}
