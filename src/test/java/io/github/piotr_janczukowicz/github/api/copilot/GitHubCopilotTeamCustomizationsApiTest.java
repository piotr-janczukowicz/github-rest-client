package io.github.piotr_janczukowicz.github.api.copilot;

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
class GitHubCopilotTeamCustomizationsApiTest {

    private static final String ORG = "octo-org";
    private static final String TEAM = "justice-league";
    private static final String BASE = "https://api.github.com";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotTeamCustomizationsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getCustomizations_shouldGet_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("get-copilot-team-customizations-response.json")));

        final Optional<GitHubGetCopilotTeamCustomizationsResponse> result = api().getCustomizations(ORG, TEAM);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result.orElseThrow())
                    .isEqualTo(CopilotCatalogFixtures.getCopilotTeamCustomizationsResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/orgs/octo-org/teams/justice-league/copilot/customizations");
        });
    }

    @Test
    void setCustomizations_shouldSend_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("set-copilot-team-customizations-response.json")));

        final GitHubSetCopilotTeamCustomizationsResponse result =
                api().setCustomizations(ORG, TEAM, CopilotCatalogFixtures.setCopilotTeamCustomizationsPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result).isEqualTo(CopilotCatalogFixtures.setCopilotTeamCustomizationsResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/orgs/octo-org/teams/justice-league/copilot/customizations");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void setCustomizations_shouldThrow_whenPayloadNull() {
        final GitHubCopilotTeamCustomizationsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setCustomizations(ORG, TEAM, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteCustomizations_shouldSend_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteCustomizations(ORG, TEAM);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().toString())
                    .isEqualTo(BASE + "/orgs/octo-org/teams/justice-league/copilot/customizations");
        });
    }

    @Test
    void getCustomizations_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubGetCopilotTeamCustomizationsResponse> result = api().getCustomizations(ORG, TEAM);

        assertThat(result).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("invalidCalls")
    void call_shouldThrowIllegalArgument_whenPathBlank(final ApiCall call, final String messageFragment) {
        final GitHubCopilotTeamCustomizationsApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private static Stream<Arguments> invalidCalls() {
        return Stream.of(
                Arguments.of((ApiCall) api -> api.getCustomizations(" ", TEAM), "org"),
                Arguments.of((ApiCall) api -> api.getCustomizations(ORG, " "), "team_slug"),
                Arguments.of(
                        (ApiCall) api -> api.setCustomizations(
                                " ", TEAM, CopilotCatalogFixtures.setCopilotTeamCustomizationsPayload()),
                        "org"),
                Arguments.of(
                        (ApiCall) api -> api.setCustomizations(
                                ORG, " ", CopilotCatalogFixtures.setCopilotTeamCustomizationsPayload()),
                        "team_slug"),
                Arguments.of((ApiCall) api -> api.deleteCustomizations(" ", TEAM), "org"),
                Arguments.of((ApiCall) api -> api.deleteCustomizations(ORG, " "), "team_slug"));
    }

    private GitHubCopilotTeamCustomizationsApi api() {
        return new GitHubCopilotTeamCustomizationsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCopilotTeamCustomizationsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    @FunctionalInterface
    interface ApiCall {
        void execute(GitHubCopilotTeamCustomizationsApi api) throws Exception;
    }
}
