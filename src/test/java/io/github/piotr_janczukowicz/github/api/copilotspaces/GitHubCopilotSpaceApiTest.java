package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
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
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCopilotSpaceApiTest {

    private static final String ORG = "octo-org";
    private static final String USERNAME = "octocat";
    private static final long SPACE_NUMBER = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotSpaceApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listForOrg_shouldReturnSpaces_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("copilot-spaces-page.json")));

        final GitHubPage<GitHubCopilotSpace> page = api().listForOrg(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            CopilotSpacesCatalogFixtures.copilotSpacesPage().spaces());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/copilot-spaces?" + TestClients.perPageQuery());
        });
    }

    @Test
    void listForOrg_shouldSendCursor_whenCursorRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("copilot-spaces-page.json")));

        api().listForOrg(ORG, GitHubCursorRequest.after("tok", 50));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("after", "tok").hasParameter("per_page", "50");
    }

    @Test
    void createForOrg_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("copilot-space.json")));

        final GitHubCopilotSpace space = api().createForOrg(ORG, CopilotSpacesCatalogFixtures.createForOrgPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(space).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpace());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/octo-org/copilot-spaces");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void getForOrg_shouldReturnSpace_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("copilot-space.json")));

        final Optional<GitHubCopilotSpace> space = api().getForOrg(ORG, SPACE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(space).contains(CopilotSpacesCatalogFixtures.copilotSpace());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/octo-org/copilot-spaces/1");
        });
    }

    @Test
    void getForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCopilotSpace> space = api().getForOrg(ORG, SPACE_NUMBER);

        assertThat(space).isEmpty();
    }

    @Test
    void updateForOrg_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("copilot-space.json")));

        final GitHubCopilotSpace space =
                api().updateForOrg(ORG, SPACE_NUMBER, CopilotSpacesCatalogFixtures.updateForOrgPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(space).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpace());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/octo-org/copilot-spaces/1");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void deleteForOrg_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteForOrg(ORG, SPACE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/octo-org/copilot-spaces/1");
        });
    }

    @Test
    void listForUser_shouldReturnSpaces_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("copilot-spaces-page.json")));

        final GitHubPage<GitHubCopilotSpace> page = api().listForUser(USERNAME);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            CopilotSpacesCatalogFixtures.copilotSpacesPage().spaces());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/copilot-spaces?" + TestClients.perPageQuery());
        });
    }

    @Test
    void listForUser_shouldSendCursor_whenCursorRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("copilot-spaces-page.json")));

        api().listForUser(USERNAME, GitHubCursorRequest.after("tok", 50));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("after", "tok").hasParameter("per_page", "50");
    }

    @Test
    void createForUser_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("copilot-space.json")));

        final GitHubCopilotSpace space =
                api().createForUser(USERNAME, CopilotSpacesCatalogFixtures.createForUserPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(space).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpace());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/users/octocat/copilot-spaces");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void getForUser_shouldReturnSpace_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("copilot-space.json")));

        final Optional<GitHubCopilotSpace> space = api().getForUser(USERNAME, SPACE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(space).contains(CopilotSpacesCatalogFixtures.copilotSpace());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/users/octocat/copilot-spaces/1");
        });
    }

    @Test
    void getForUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCopilotSpace> space = api().getForUser(USERNAME, SPACE_NUMBER);

        assertThat(space).isEmpty();
    }

    @Test
    void updateForUser_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("copilot-space.json")));

        final GitHubCopilotSpace space =
                api().updateForUser(USERNAME, SPACE_NUMBER, CopilotSpacesCatalogFixtures.updateForUserPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(space).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpace());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/users/octocat/copilot-spaces/1");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void deleteForUser_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteForUser(USERNAME, SPACE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/users/octocat/copilot-spaces/1");
        });
    }

    @ParameterizedTest
    @MethodSource("nullRequestCalls")
    void validation_shouldThrow_whenRequestNull(final ApiCall call) {
        final GitHubCopilotSpaceApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @ParameterizedTest
    @MethodSource("unsupportedRequestCalls")
    void listEndpoint_shouldThrow_whenUnsupportedPagination(final ApiCall call) {
        final GitHubCopilotSpaceApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unsupported pagination");
    }

    @ParameterizedTest
    @MethodSource("nullPayloadCalls")
    void validation_shouldThrow_whenPayloadNull(final ApiCall call) {
        final GitHubCopilotSpaceApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @ParameterizedTest
    @MethodSource("invalidCalls")
    void call_shouldThrowIllegalArgument_whenInvalid(final ApiCall call, final String messageFragment) {
        final GitHubCopilotSpaceApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private GitHubCopilotSpaceApi api() {
        return new GitHubCopilotSpaceApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCopilotSpaceApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<ApiCall> nullRequestCalls() {
        return Stream.of(api -> api.listForOrg(ORG, null), api -> api.listForUser(USERNAME, null));
    }

    private static Stream<ApiCall> unsupportedRequestCalls() {
        return Stream.of(
                api -> api.listForOrg(ORG, GitHubPageRequest.byPage()),
                api -> api.listForUser(USERNAME, GitHubPageRequest.bySince()));
    }

    private static Stream<ApiCall> nullPayloadCalls() {
        return Stream.of(
                api -> api.createForOrg(ORG, null),
                api -> api.updateForOrg(ORG, SPACE_NUMBER, null),
                api -> api.createForUser(USERNAME, null),
                api -> api.updateForUser(USERNAME, SPACE_NUMBER, null));
    }

    private static Stream<Arguments> invalidCalls() {
        return Stream.of(
                Arguments.of((ApiCall) api -> api.listForOrg(" "), "org"),
                Arguments.of(
                        (ApiCall) api -> api.createForOrg(" ", CopilotSpacesCatalogFixtures.createForOrgPayload()),
                        "org"),
                Arguments.of((ApiCall) api -> api.getForOrg(" ", SPACE_NUMBER), "org"),
                Arguments.of(
                        (ApiCall) api ->
                                api.updateForOrg(" ", SPACE_NUMBER, CopilotSpacesCatalogFixtures.updateForOrgPayload()),
                        "org"),
                Arguments.of((ApiCall) api -> api.deleteForOrg(" ", SPACE_NUMBER), "org"),
                Arguments.of((ApiCall) api -> api.listForUser(" "), "username"),
                Arguments.of(
                        (ApiCall) api -> api.createForUser(" ", CopilotSpacesCatalogFixtures.createForUserPayload()),
                        "username"),
                Arguments.of((ApiCall) api -> api.getForUser(" ", SPACE_NUMBER), "username"),
                Arguments.of(
                        (ApiCall) api -> api.updateForUser(
                                " ", SPACE_NUMBER, CopilotSpacesCatalogFixtures.updateForUserPayload()),
                        "username"),
                Arguments.of((ApiCall) api -> api.deleteForUser(" ", SPACE_NUMBER), "username"));
    }

    @FunctionalInterface
    interface ApiCall {
        void execute(GitHubCopilotSpaceApi api) throws Exception;
    }
}
