package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCopilotCopilotUserManagementApiTest {

    private static final String ORG = "octo-org";
    private static final String USERNAME = "octocat";
    private static final String BASE = "https://api.github.com/orgs/octo-org";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCopilotCopilotUserManagementApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getOrganizationDetails_shouldReturnDetails_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("copilot-organization-details.json")));

        final GitHubCopilotOrganizationDetails details = api().getOrganizationDetails(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(details).isEqualTo(CopilotCatalogFixtures.copilotOrganizationDetails());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString()).isEqualTo(BASE + "/copilot/billing");
        });
    }

    @Test
    void listSeats_shouldReturnSeats_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-copilot-seats-response.json")));

        final GitHubPage<GitHubCopilotSeatDetails> page = api().listSeats(ORG);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .isEqualTo(CopilotCatalogFixtures.listCopilotSeatsResponse().seats());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/octo-org/copilot/billing/seats");
        });
    }

    @Test
    void listSeats_shouldUsePageRequest_whenRequestGiven() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-copilot-seats-response.json")));

        final GitHubPage<GitHubCopilotSeatDetails> page = api().listSeats(ORG, GitHubPageRequest.byPage());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .isEqualTo(CopilotCatalogFixtures.listCopilotSeatsResponse().seats());
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/octo-org/copilot/billing/seats");
        });
    }

    @Test
    void listSeats_shouldExposeTotalSeatsAsTotalCount_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("list-copilot-seats-response-total-two.json")));

        final GitHubPage<GitHubCopilotSeatDetails> page = api().listSeats(ORG);

        assertThat(page.totalCount()).isEqualTo(2);
    }

    @Test
    void listSeats_shouldThrow_whenRequestNull() {
        final GitHubCopilotCopilotUserManagementApi api = api();

        final Throwable thrown = catchThrowable(() -> api.listSeats(ORG, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @Test
    void addSeatsForTeams_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("add-copilot-seats-for-teams-response.json")));

        final GitHubAddCopilotSeatsForTeamsResponse response =
                api().addSeatsForTeams(ORG, CopilotCatalogFixtures.addCopilotSeatsForTeamsPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(response).isEqualTo(CopilotCatalogFixtures.addCopilotSeatsForTeamsResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().toString()).isEqualTo(BASE + "/copilot/billing/selected_teams");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void cancelSeatAssignmentForTeams_shouldDeletePayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), json("cancel-copilot-seat-assignment-for-teams-response.json")));

        final GitHubCancelCopilotSeatAssignmentForTeamsResponse response = api().cancelSeatAssignmentForTeams(
                        ORG, CopilotCatalogFixtures.cancelCopilotSeatAssignmentForTeamsPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(response).isEqualTo(CopilotCatalogFixtures.cancelCopilotSeatAssignmentForTeamsResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().toString()).isEqualTo(BASE + "/copilot/billing/selected_teams");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void addSeatsForUsers_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("add-copilot-seats-for-users-response.json")));

        final GitHubAddCopilotSeatsForUsersResponse response =
                api().addSeatsForUsers(ORG, CopilotCatalogFixtures.addCopilotSeatsForUsersPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(response).isEqualTo(CopilotCatalogFixtures.addCopilotSeatsForUsersResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri().toString()).isEqualTo(BASE + "/copilot/billing/selected_users");
        });
    }

    @Test
    void cancelSeatAssignmentForUsers_shouldDeletePayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), json("cancel-copilot-seat-assignment-for-users-response.json")));

        final GitHubCancelCopilotSeatAssignmentForUsersResponse response = api().cancelSeatAssignmentForUsers(
                        ORG, CopilotCatalogFixtures.cancelCopilotSeatAssignmentForUsersPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(response).isEqualTo(CopilotCatalogFixtures.cancelCopilotSeatAssignmentForUsersResponse());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().toString()).isEqualTo(BASE + "/copilot/billing/selected_users");
        });
    }

    @Test
    void getSeatDetailsForUser_shouldReturnSeat_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("copilot-seat-details.json")));

        final GitHubCopilotSeatDetails seat =
                api().getSeatDetailsForUser(ORG, USERNAME).orElseThrow();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(seat).isEqualTo(CopilotCatalogFixtures.copilotSeatDetails());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().toString()).isEqualTo(BASE + "/members/octocat/copilot");
        });
    }

    @Test
    void getSeatDetailsForUser_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCopilotSeatDetails> result = api().getSeatDetailsForUser(ORG, USERNAME);

        assertThat(result).isEmpty();
    }

    @Test
    void addSeatsForTeams_shouldThrow_whenPayloadNull() {
        final GitHubCopilotCopilotUserManagementApi api = api();

        final Throwable thrown = catchThrowable(() -> api.addSeatsForTeams(ORG, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @ParameterizedTest
    @MethodSource("invalidCalls")
    void call_shouldThrowIllegalArgument_whenPathBlank(final ApiCall call, final String messageFragment) {
        final GitHubCopilotCopilotUserManagementApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private static Stream<Arguments> invalidCalls() {
        return Stream.of(
                Arguments.of((ApiCall) api -> api.getOrganizationDetails(" "), "org"),
                Arguments.of((ApiCall) api -> api.listSeats(" "), "org"),
                Arguments.of((ApiCall) api -> api.getSeatDetailsForUser(ORG, " "), "username"));
    }

    private GitHubCopilotCopilotUserManagementApi api() {
        return new GitHubCopilotCopilotUserManagementApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCopilotCopilotUserManagementApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    @FunctionalInterface
    interface ApiCall {
        void execute(GitHubCopilotCopilotUserManagementApi api) throws Exception;
    }
}
