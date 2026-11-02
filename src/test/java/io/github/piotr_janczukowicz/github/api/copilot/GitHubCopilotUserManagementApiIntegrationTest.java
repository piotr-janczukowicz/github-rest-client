package io.github.piotr_janczukowicz.github.api.copilot;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.acceptedJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.copilotApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCopilotUserManagementApiIntegrationTest {

    private static final String BILLING = "/orgs/octo-org/copilot/billing";
    private static final String SEATS = BILLING + "/seats";
    private static final String SELECTED_TEAMS = BILLING + "/selected_teams";
    private static final String SELECTED_USERS = BILLING + "/selected_users";
    private static final String MEMBER_COPILOT = "/orgs/octo-org/members/octocat/copilot";
    private static final String ENTERPRISE_SEATS = "/enterprises/octo-enterprise/copilot/billing/seats";
    private static final String ENTERPRISE_SELECTED_TEAMS =
            "/enterprises/octo-enterprise/copilot/billing/selected_enterprise_teams";
    private static final String ENTERPRISE_SELECTED_USERS =
            "/enterprises/octo-enterprise/copilot/billing/selected_users";
    private static final String ENTERPRISE_MEMBER_COPILOT = "/enterprises/octo-enterprise/members/octocat/copilot";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getOrganizationDetails_shouldReturnDetails_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, BILLING, ok("copilot-organization-details.json"));

        final GitHubCopilotOrganizationDetails details;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            details = copilot.api().userManagement().getOrganizationDetails("octo-org");
        }

        assertThat(details).isEqualTo(CopilotCatalogFixtures.copilotOrganizationDetails());
        HTTP.verifyRequested(GET, BILLING);
    }

    @Test
    void listSeats_shouldReturnSeats_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, SEATS, ok("list-copilot-seats-response.json"));

        final GitHubPage<GitHubCopilotSeatDetails> page;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            page = copilot.api().userManagement().listSeats("octo-org");
        }

        assertThat(page.items())
                .isEqualTo(CopilotCatalogFixtures.listCopilotSeatsResponse().seats());
        HTTP.verifyRequested(GET, SEATS);
    }

    @Test
    void addSeatsForTeams_shouldPostPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                SELECTED_TEAMS,
                createdJson(jsonBytes(
                        GitHubCopilotUserManagementApiIntegrationTest.class,
                        "add-copilot-seats-for-teams-response.json")));
        final GitHubAddCopilotSeatsForTeamsPayload payload = CopilotCatalogFixtures.addCopilotSeatsForTeamsPayload();

        final GitHubAddCopilotSeatsForTeamsResponse response;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            response = copilot.api().userManagement().addSeatsForTeams("octo-org", payload);
        }

        assertThat(response).isEqualTo(CopilotCatalogFixtures.addCopilotSeatsForTeamsResponse());
        HTTP.verifyRequested(POST, SELECTED_TEAMS);
        HTTP.verifyHeader(POST, SELECTED_TEAMS, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, SELECTED_TEAMS, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void cancelSeatAssignmentForTeams_shouldSendPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(DELETE, SELECTED_TEAMS, ok("cancel-copilot-seat-assignment-for-teams-response.json"));
        final GitHubCancelCopilotSeatAssignmentForTeamsPayload payload =
                CopilotCatalogFixtures.cancelCopilotSeatAssignmentForTeamsPayload();

        final GitHubCancelCopilotSeatAssignmentForTeamsResponse response;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            response = copilot.api().userManagement().cancelSeatAssignmentForTeams("octo-org", payload);
        }

        assertThat(response).isEqualTo(CopilotCatalogFixtures.cancelCopilotSeatAssignmentForTeamsResponse());
        HTTP.verifyRequested(DELETE, SELECTED_TEAMS);
        HTTP.verifyBody(DELETE, SELECTED_TEAMS, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void addSeatsForUsers_shouldPostPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                SELECTED_USERS,
                createdJson(jsonBytes(
                        GitHubCopilotUserManagementApiIntegrationTest.class,
                        "add-copilot-seats-for-users-response.json")));
        final GitHubAddCopilotSeatsForUsersPayload payload = CopilotCatalogFixtures.addCopilotSeatsForUsersPayload();

        final GitHubAddCopilotSeatsForUsersResponse response;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            response = copilot.api().userManagement().addSeatsForUsers("octo-org", payload);
        }

        assertThat(response).isEqualTo(CopilotCatalogFixtures.addCopilotSeatsForUsersResponse());
        HTTP.verifyRequested(POST, SELECTED_USERS);
        HTTP.verifyBody(POST, SELECTED_USERS, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void cancelSeatAssignmentForUsers_shouldSendPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(DELETE, SELECTED_USERS, ok("cancel-copilot-seat-assignment-for-users-response.json"));
        final GitHubCancelCopilotSeatAssignmentForUsersPayload payload =
                CopilotCatalogFixtures.cancelCopilotSeatAssignmentForUsersPayload();

        final GitHubCancelCopilotSeatAssignmentForUsersResponse response;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            response = copilot.api().userManagement().cancelSeatAssignmentForUsers("octo-org", payload);
        }

        assertThat(response).isEqualTo(CopilotCatalogFixtures.cancelCopilotSeatAssignmentForUsersResponse());
        HTTP.verifyRequested(DELETE, SELECTED_USERS);
        HTTP.verifyBody(DELETE, SELECTED_USERS, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getSeatDetailsForUser_shouldReturnSeat_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, MEMBER_COPILOT, ok("copilot-seat-details.json"));

        final GitHubCopilotSeatDetails seat;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            seat = copilot.api()
                    .userManagement()
                    .getSeatDetailsForUser("octo-org", "octocat")
                    .orElseThrow();
        }

        assertThat(seat).isEqualTo(CopilotCatalogFixtures.copilotSeatDetails());
        HTTP.verifyRequested(GET, MEMBER_COPILOT);
    }

    private static StubHttpServer.StubResponse ok(final String fileName) {
        return okJson(jsonBytes(GitHubCopilotUserManagementApiIntegrationTest.class, fileName));
    }

    @Test
    void listSeatsForEnterprise_shouldReturnSeats_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ENTERPRISE_SEATS,
                okJson(jsonBytes(
                        GitHubCopilotUserManagementApiIntegrationTest.class,
                        "list-copilot-seats-for-enterprise-response.json")));

        final GitHubPage<GitHubCopilotSeatDetails> page;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            page = copilot.api().userManagement().listSeatsForEnterprise("octo-enterprise");
        }

        assertThat(page.items())
                .isEqualTo(CopilotCatalogFixtures.listCopilotSeatsForEnterpriseResponse()
                        .seats());
        HTTP.verifyRequested(GET, ENTERPRISE_SEATS);
    }

    @Test
    void addSeatsForEnterpriseTeams_shouldPostPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                ENTERPRISE_SELECTED_TEAMS,
                createdJson(jsonBytes(
                        GitHubCopilotUserManagementApiIntegrationTest.class,
                        "add-copilot-seats-for-enterprise-teams-response.json")));
        final GitHubAddCopilotSeatsForEnterpriseTeamsPayload payload =
                CopilotCatalogFixtures.addCopilotSeatsForEnterpriseTeamsPayload();

        final GitHubAddCopilotSeatsForEnterpriseTeamsResponse response;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            response = copilot.api().userManagement().addSeatsForEnterpriseTeams("octo-enterprise", payload);
        }

        assertThat(response).isEqualTo(CopilotCatalogFixtures.addCopilotSeatsForEnterpriseTeamsResponse());
        HTTP.verifyRequested(POST, ENTERPRISE_SELECTED_TEAMS);
        HTTP.verifyBody(POST, ENTERPRISE_SELECTED_TEAMS, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void cancelSeatAssignmentForEnterpriseTeams_shouldReturnCancelled_whenResponseIsOk() throws Exception {
        HTTP.stub(
                DELETE,
                ENTERPRISE_SELECTED_TEAMS,
                okJson(jsonBytes(
                        GitHubCopilotUserManagementApiIntegrationTest.class,
                        "cancel-copilot-seat-assignment-for-enterprise-teams-response.json")));
        final GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsPayload payload =
                CopilotCatalogFixtures.cancelCopilotSeatAssignmentForEnterpriseTeamsPayload();

        final GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult result;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            result = copilot.api().userManagement().cancelSeatAssignmentForEnterpriseTeams("octo-enterprise", payload);
        }

        assertThat(result)
                .isEqualTo(GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult.ofCancelled(
                        CopilotCatalogFixtures.cancelCopilotSeatAssignmentForEnterpriseTeamsResponse()));
        HTTP.verifyRequested(DELETE, ENTERPRISE_SELECTED_TEAMS);
        HTTP.verifyBody(DELETE, ENTERPRISE_SELECTED_TEAMS, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void cancelSeatAssignmentForEnterpriseTeams_shouldReturnAccepted_whenResponseIsAccepted() throws Exception {
        HTTP.stub(
                DELETE,
                ENTERPRISE_SELECTED_TEAMS,
                acceptedJson(jsonBytes(
                        GitHubCopilotUserManagementApiIntegrationTest.class,
                        "cancel-copilot-seat-assignment-for-enterprise-teams-accepted-response.json")));

        final GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult result;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            result = copilot.api()
                    .userManagement()
                    .cancelSeatAssignmentForEnterpriseTeams(
                            "octo-enterprise",
                            CopilotCatalogFixtures.cancelCopilotSeatAssignmentForEnterpriseTeamsPayload());
        }

        assertThat(result)
                .isEqualTo(GitHubCancelCopilotSeatAssignmentForEnterpriseTeamsResult.ofAccepted(
                        CopilotCatalogFixtures.cancelCopilotSeatAssignmentForEnterpriseTeamsAcceptedResponse()));
        HTTP.verifyRequested(DELETE, ENTERPRISE_SELECTED_TEAMS);
    }

    @Test
    void addSeatsForEnterpriseUsers_shouldPostPayload_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                ENTERPRISE_SELECTED_USERS,
                createdJson(jsonBytes(
                        GitHubCopilotUserManagementApiIntegrationTest.class,
                        "add-copilot-seats-for-enterprise-users-response.json")));
        final GitHubAddCopilotSeatsForEnterpriseUsersPayload payload =
                CopilotCatalogFixtures.addCopilotSeatsForEnterpriseUsersPayload();

        final GitHubAddCopilotSeatsForEnterpriseUsersResponse response;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            response = copilot.api().userManagement().addSeatsForEnterpriseUsers("octo-enterprise", payload);
        }

        assertThat(response).isEqualTo(CopilotCatalogFixtures.addCopilotSeatsForEnterpriseUsersResponse());
        HTTP.verifyRequested(POST, ENTERPRISE_SELECTED_USERS);
        HTTP.verifyBody(POST, ENTERPRISE_SELECTED_USERS, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void cancelSeatAssignmentForEnterpriseUsers_shouldSendPayload_whenResponseIsOk() throws Exception {
        HTTP.stub(
                DELETE,
                ENTERPRISE_SELECTED_USERS,
                okJson(jsonBytes(
                        GitHubCopilotUserManagementApiIntegrationTest.class,
                        "cancel-copilot-seat-assignment-for-enterprise-users-response.json")));
        final GitHubCancelCopilotSeatAssignmentForEnterpriseUsersPayload payload =
                CopilotCatalogFixtures.cancelCopilotSeatAssignmentForEnterpriseUsersPayload();

        final GitHubCancelCopilotSeatAssignmentForEnterpriseUsersResponse response;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            response =
                    copilot.api().userManagement().cancelSeatAssignmentForEnterpriseUsers("octo-enterprise", payload);
        }

        assertThat(response).isEqualTo(CopilotCatalogFixtures.cancelCopilotSeatAssignmentForEnterpriseUsersResponse());
        HTTP.verifyRequested(DELETE, ENTERPRISE_SELECTED_USERS);
        HTTP.verifyBody(DELETE, ENTERPRISE_SELECTED_USERS, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getSeatDetailsForEnterpriseUser_shouldReturnDetails_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ENTERPRISE_MEMBER_COPILOT,
                okJson(jsonBytes(
                        GitHubCopilotUserManagementApiIntegrationTest.class,
                        "get-copilot-seat-details-for-enterprise-user-response.json")));

        final GitHubGetCopilotSeatDetailsForEnterpriseUserResponse response;
        try (final GitHubApiWireMockSupport.CopilotApiClient copilot = copilotApi(HTTP)) {
            response = copilot.api()
                    .userManagement()
                    .getSeatDetailsForEnterpriseUser("octo-enterprise", "octocat")
                    .orElseThrow();
        }

        assertThat(response).isEqualTo(CopilotCatalogFixtures.getCopilotSeatDetailsForEnterpriseUserResponse());
        HTTP.verifyRequested(GET, ENTERPRISE_MEMBER_COPILOT);
    }
}
