package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCopilotSpacesResourcesApiTest {

    private static final String ORG = "octo-org";
    private static final String USERNAME = "octocat";
    private static final long SPACE_NUMBER = 1L;
    private static final long RESOURCE_ID = 7L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotSpacesResourcesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listResourcesForOrg_shouldReturnResources_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("copilot-space-resources-page.json")));

        final GitHubCopilotSpaceResourcesPage page = api().listResourcesForOrg(ORG, SPACE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceResourcesPage());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/copilot-spaces/1/resources");
        });
    }

    @Test
    void createResourceForOrg_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("copilot-space-resource.json")));

        final GitHubCopilotSpaceResource resource =
                api().createResourceForOrg(ORG, SPACE_NUMBER, CopilotSpacesCatalogFixtures.createResourcePayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(resource).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceResource());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/copilot-spaces/1/resources");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void createResourceForOrg_shouldReturnResource_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("copilot-space-resource.json")));

        final GitHubCopilotSpaceResource resource =
                api().createResourceForOrg(ORG, SPACE_NUMBER, CopilotSpacesCatalogFixtures.createResourcePayload());

        assertThat(resource).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceResource());
    }

    @Test
    void getResourceForOrg_shouldReturnResource_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("copilot-space-resource.json")));

        final Optional<GitHubCopilotSpaceResource> resource = api().getResourceForOrg(ORG, SPACE_NUMBER, RESOURCE_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(resource).contains(CopilotSpacesCatalogFixtures.copilotSpaceResource());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/copilot-spaces/1/resources/7");
        });
    }

    @Test
    void getResourceForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCopilotSpaceResource> resource = api().getResourceForOrg(ORG, SPACE_NUMBER, RESOURCE_ID);

        assertThat(resource).isEmpty();
    }

    @Test
    void updateResourceForOrg_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("copilot-space-resource.json")));

        final GitHubCopilotSpaceResource resource = api().updateResourceForOrg(
                        ORG, SPACE_NUMBER, RESOURCE_ID, CopilotSpacesCatalogFixtures.updateResourcePayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(resource).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceResource());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/copilot-spaces/1/resources/7");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void deleteResourceForOrg_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteResourceForOrg(ORG, SPACE_NUMBER, RESOURCE_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/copilot-spaces/1/resources/7");
        });
    }

    @Test
    void listResourcesForUser_shouldReturnResources_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("copilot-space-resources-page.json")));

        final GitHubCopilotSpaceResourcesPage page = api().listResourcesForUser(USERNAME, SPACE_NUMBER);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceResourcesPage());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/copilot-spaces/1/resources");
        });
    }

    @Test
    void createResourceForUser_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("copilot-space-resource.json")));

        final GitHubCopilotSpaceResource resource = api().createResourceForUser(
                        USERNAME, SPACE_NUMBER, CopilotSpacesCatalogFixtures.createResourcePayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(resource).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceResource());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/copilot-spaces/1/resources");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void createResourceForUser_shouldReturnResource_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("copilot-space-resource.json")));

        final GitHubCopilotSpaceResource resource = api().createResourceForUser(
                        USERNAME, SPACE_NUMBER, CopilotSpacesCatalogFixtures.createResourcePayload());

        assertThat(resource).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceResource());
    }

    @Test
    void getResourceForUser_shouldReturnResource_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("copilot-space-resource.json")));

        final Optional<GitHubCopilotSpaceResource> resource =
                api().getResourceForUser(USERNAME, SPACE_NUMBER, RESOURCE_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(resource).contains(CopilotSpacesCatalogFixtures.copilotSpaceResource());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/copilot-spaces/1/resources/7");
        });
    }

    @Test
    void getResourceForUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCopilotSpaceResource> resource =
                api().getResourceForUser(USERNAME, SPACE_NUMBER, RESOURCE_ID);

        assertThat(resource).isEmpty();
    }

    @Test
    void updateResourceForUser_shouldPutPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("copilot-space-resource.json")));

        final GitHubCopilotSpaceResource resource = api().updateResourceForUser(
                        USERNAME, SPACE_NUMBER, RESOURCE_ID, CopilotSpacesCatalogFixtures.updateResourcePayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(resource).isEqualTo(CopilotSpacesCatalogFixtures.copilotSpaceResource());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/copilot-spaces/1/resources/7");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void deleteResourceForUser_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteResourceForUser(USERNAME, SPACE_NUMBER, RESOURCE_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/users/octocat/copilot-spaces/1/resources/7");
        });
    }

    @ParameterizedTest
    @MethodSource("nullPayloadCalls")
    void validation_shouldThrow_whenPayloadNull(final ApiCall call) {
        final GitHubCopilotSpacesResourcesApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @ParameterizedTest
    @MethodSource("invalidCalls")
    void call_shouldThrowIllegalArgument_whenInvalid(final ApiCall call, final String messageFragment) {
        final GitHubCopilotSpacesResourcesApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private GitHubCopilotSpacesResourcesApi api() {
        return new GitHubCopilotSpacesResourcesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCopilotSpacesResourcesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<ApiCall> nullPayloadCalls() {
        return Stream.of(
                api -> api.createResourceForOrg(ORG, SPACE_NUMBER, null),
                api -> api.updateResourceForOrg(ORG, SPACE_NUMBER, RESOURCE_ID, null),
                api -> api.createResourceForUser(USERNAME, SPACE_NUMBER, null),
                api -> api.updateResourceForUser(USERNAME, SPACE_NUMBER, RESOURCE_ID, null));
    }

    private static Stream<Arguments> invalidCalls() {
        return Stream.of(
                Arguments.of((ApiCall) api -> api.listResourcesForOrg(" ", SPACE_NUMBER), "org"),
                Arguments.of(
                        (ApiCall) api -> api.createResourceForOrg(
                                " ", SPACE_NUMBER, CopilotSpacesCatalogFixtures.createResourcePayload()),
                        "org"),
                Arguments.of((ApiCall) api -> api.getResourceForOrg(" ", SPACE_NUMBER, RESOURCE_ID), "org"),
                Arguments.of(
                        (ApiCall) api -> api.updateResourceForOrg(
                                " ", SPACE_NUMBER, RESOURCE_ID, CopilotSpacesCatalogFixtures.updateResourcePayload()),
                        "org"),
                Arguments.of((ApiCall) api -> api.deleteResourceForOrg(" ", SPACE_NUMBER, RESOURCE_ID), "org"),
                Arguments.of((ApiCall) api -> api.listResourcesForUser(" ", SPACE_NUMBER), "username"),
                Arguments.of(
                        (ApiCall) api -> api.createResourceForUser(
                                " ", SPACE_NUMBER, CopilotSpacesCatalogFixtures.createResourcePayload()),
                        "username"),
                Arguments.of((ApiCall) api -> api.getResourceForUser(" ", SPACE_NUMBER, RESOURCE_ID), "username"),
                Arguments.of(
                        (ApiCall) api -> api.updateResourceForUser(
                                " ", SPACE_NUMBER, RESOURCE_ID, CopilotSpacesCatalogFixtures.updateResourcePayload()),
                        "username"),
                Arguments.of((ApiCall) api -> api.deleteResourceForUser(" ", SPACE_NUMBER, RESOURCE_ID), "username"));
    }

    @FunctionalInterface
    interface ApiCall {
        void execute(GitHubCopilotSpacesResourcesApi api) throws Exception;
    }
}
