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
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCopilotCopilotContentExclusionManagementApiTest {

    private static final String ORG = "octo-org";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotCopilotContentExclusionManagementApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getForOrganization_shouldReturnDetails_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), json("copilot-organization-content-exclusion-details.json")));

        final GitHubCopilotOrganizationContentExclusionDetails details = api().getForOrganization(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(details).isEqualTo(CopilotCatalogFixtures.copilotOrganizationContentExclusionDetails());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString())
                    .isEqualTo("https://api.github.com/orgs/octo-org/copilot/content_exclusion");
        });
    }

    @Test
    void updateForOrganization_shouldReturnResponse_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(), json("set-copilot-content-exclusion-for-organization-response.json")));

        final GitHubSetCopilotContentExclusionForOrganizationResponse response = api().updateForOrganization(
                        ORG, CopilotCatalogFixtures.setCopilotContentExclusionForOrganizationPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(response)
                    .isEqualTo(CopilotCatalogFixtures.setCopilotContentExclusionForOrganizationResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().toString())
                    .isEqualTo("https://api.github.com/orgs/octo-org/copilot/content_exclusion");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void updateForOrganization_shouldThrow_whenPayloadNull() {
        final GitHubCopilotCopilotContentExclusionManagementApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updateForOrganization(ORG, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @ParameterizedTest
    @MethodSource("invalidCalls")
    void call_shouldThrowIllegalArgument_whenOrgBlank(final ApiCall call, final String messageFragment) {
        final GitHubCopilotCopilotContentExclusionManagementApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private static Stream<Arguments> invalidCalls() {
        return Stream.of(
                Arguments.of((ApiCall) api -> api.getForOrganization(" "), "org"),
                Arguments.of(
                        (ApiCall) api -> api.updateForOrganization(
                                " ", CopilotCatalogFixtures.setCopilotContentExclusionForOrganizationPayload()),
                        "org"));
    }

    private GitHubCopilotCopilotContentExclusionManagementApi api() {
        return new GitHubCopilotCopilotContentExclusionManagementApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCopilotCopilotContentExclusionManagementApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    @FunctionalInterface
    interface ApiCall {
        void execute(GitHubCopilotCopilotContentExclusionManagementApi api) throws Exception;
    }
}
