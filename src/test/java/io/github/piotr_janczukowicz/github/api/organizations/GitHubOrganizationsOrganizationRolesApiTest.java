package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubOrganizationsOrganizationRolesApiTest {

    private static final String ORG = "github";
    private static final String TEAM_SLUG = "justice-league";
    private static final String USERNAME = "octocat";
    private static final long ROLE_ID = 8030L;
    private static final String ORGANIZATION_ROLES_PATH = "/orgs/github/organization-roles";
    private static final String TEAM_ROLES_PATH = "/orgs/github/organization-roles/teams/justice-league";
    private static final String TEAM_ROLE_PATH = "/orgs/github/organization-roles/teams/justice-league/8030";
    private static final String USER_ROLES_PATH = "/orgs/github/organization-roles/users/octocat";
    private static final String USER_ROLE_PATH = "/orgs/github/organization-roles/users/octocat/8030";
    private static final String ORGANIZATION_ROLE_PATH = "/orgs/github/organization-roles/8030";
    private static final String ORGANIZATION_ROLE_TEAMS_PATH = "/orgs/github/organization-roles/8030/teams";
    private static final String ORGANIZATION_ROLE_USERS_PATH = "/orgs/github/organization-roles/8030/users";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsOrganizationRolesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void list_shouldReturnOrganizationRolesPage_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("organization-roles-page.json")));

        final GitHubOrganizationRolesPage page = callApi(api -> api.list(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page).isEqualTo(OrganizationsCatalogFixtures.organizationRolesPage());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ORGANIZATION_ROLES_PATH);
            softly.assertThat(request.uri().getQuery()).isNull();
        });
    }

    @Test
    void revokeAllForTeam_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.revokeAllForTeam(ORG, TEAM_SLUG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + TEAM_ROLES_PATH);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void assignTeam_shouldPutWithoutBody_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.assignTeam(ORG, TEAM_SLUG, ROLE_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + TEAM_ROLE_PATH);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void revokeTeam_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.revokeTeam(ORG, TEAM_SLUG, ROLE_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + TEAM_ROLE_PATH);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void revokeAllForUser_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.revokeAllForUser(ORG, USERNAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + USER_ROLES_PATH);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void assignUser_shouldPutWithoutBody_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.assignUser(ORG, USERNAME, ROLE_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + USER_ROLE_PATH);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void revokeUser_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.revokeUser(ORG, USERNAME, ROLE_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + USER_ROLE_PATH);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void get_shouldReturnOrganizationRole_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("organization-role.json")));

        final Optional<GitHubOrganizationRole> role = callApi(api -> api.get(ORG, ROLE_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(role).contains(OrganizationsCatalogFixtures.organizationRole());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + ORGANIZATION_ROLE_PATH);
        });
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubOrganizationRole> role = callApi(api -> api.get(ORG, ROLE_ID));

        assertThat(role).isEmpty();
    }

    @Test
    void listTeams_shouldReturnTeamRoleAssignments_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("team-role-assignment.json") + "]"));

        final GitHubPage<GitHubTeamRoleAssignment> page = callApi(api -> api.listTeams(ORG, ROLE_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.teamRoleAssignment());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com" + ORGANIZATION_ROLE_TEAMS_PATH + "?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listTeams_shouldUsePageRequest_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("team-role-assignment.json") + "]"));

        callApi(api -> api.listTeams(ORG, ROLE_ID, GitHubPageNumberRequest.of(2, 30)));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("page=2");
    }

    @Test
    void listUsers_shouldReturnUserRoleAssignments_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("user-role-assignment.json") + "]"));

        final GitHubPage<GitHubUserRoleAssignment> page = callApi(api -> api.listUsers(ORG, ROLE_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.userRoleAssignment());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com" + ORGANIZATION_ROLE_USERS_PATH + "?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listUsers_shouldUsePageRequest_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("user-role-assignment.json") + "]"));

        callApi(api -> api.listUsers(ORG, ROLE_ID, GitHubPageNumberRequest.of(2, 30)));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri.getQuery()).contains("page=2");
    }

    @ParameterizedTest
    @MethodSource("blankOrgCalls")
    void validation_shouldThrow_whenOrgBlank(final OrganizationRolesApiCall call) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
    }

    @ParameterizedTest
    @MethodSource("blankTeamSlugCalls")
    void validation_shouldThrow_whenTeamSlugBlank(final OrganizationRolesApiCall call) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("{team_slug}");
    }

    @ParameterizedTest
    @MethodSource("blankUsernameCalls")
    void validation_shouldThrow_whenUsernameBlank(final OrganizationRolesApiCall call) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("username");
    }

    @ParameterizedTest
    @MethodSource("unsupportedPaginationRequests")
    void listEndpoint_shouldThrow_whenUnsupportedPagination(
            final OrganizationRolesApiCall call, final String messageFragment) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @FunctionalInterface
    interface OrganizationRolesApiCall {
        void execute(GitHubOrganizationsOrganizationRolesApi api) throws Exception;
    }

    @FunctionalInterface
    private interface OrganizationRolesApiAction<T> {
        T apply(GitHubOrganizationsOrganizationRolesApi api) throws Exception;
    }

    private void callApiVoid(final OrganizationRolesApiCall action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            action.execute(new GitHubOrganizationsOrganizationRolesApi(TestClients.caller(client)));
        }
    }

    private <T> T callApi(final OrganizationRolesApiAction<T> action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationsOrganizationRolesApi(TestClients.caller(client)));
        }
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubPageRequest unsupportedSince = GitHubPageRequest.bySince();
        final GitHubPageRequest unsupportedCursor = GitHubPageRequest.byCursor();
        return Stream.of(
                Arguments.of(
                        (OrganizationRolesApiCall) api -> api.listTeams(ORG, ROLE_ID, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (OrganizationRolesApiCall) api -> api.listTeams(ORG, ROLE_ID, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (OrganizationRolesApiCall) api -> api.listUsers(ORG, ROLE_ID, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (OrganizationRolesApiCall) api -> api.listUsers(ORG, ROLE_ID, unsupportedCursor),
                        "unsupported pagination request: cursor"));
    }

    private static List<OrganizationRolesApiCall> blankOrgCalls() {
        return List.of(
                api -> api.list(" "),
                api -> api.revokeAllForTeam(" ", TEAM_SLUG),
                api -> api.assignTeam(" ", TEAM_SLUG, ROLE_ID),
                api -> api.revokeTeam(" ", TEAM_SLUG, ROLE_ID),
                api -> api.revokeAllForUser(" ", USERNAME),
                api -> api.assignUser(" ", USERNAME, ROLE_ID),
                api -> api.revokeUser(" ", USERNAME, ROLE_ID),
                api -> api.get(" ", ROLE_ID),
                api -> api.listTeams(" ", ROLE_ID),
                api -> api.listUsers(" ", ROLE_ID));
    }

    private static List<OrganizationRolesApiCall> blankTeamSlugCalls() {
        return List.of(
                api -> api.revokeAllForTeam(ORG, " "),
                api -> api.assignTeam(ORG, " ", ROLE_ID),
                api -> api.revokeTeam(ORG, " ", ROLE_ID));
    }

    private static List<OrganizationRolesApiCall> blankUsernameCalls() {
        return List.of(
                api -> api.revokeAllForUser(ORG, " "),
                api -> api.assignUser(ORG, " ", ROLE_ID),
                api -> api.revokeUser(ORG, " ", ROLE_ID));
    }

    @Test
    void listOrganizationFineGrainedPermissions_shouldReturnPermissions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), "[" + json("organization-fine-grained-permission.json") + "]"));

        final List<GitHubOrganizationFineGrainedPermission> permissions =
                api().listOrganizationFineGrainedPermissions(ORG);

        assertThat(permissions).containsExactly(OrganizationsCatalogFixtures.organizationFineGrainedPermission());
        assertThat(captureSingleRequest().uri().getPath())
                .isEqualTo("/orgs/github/organization-fine-grained-permissions");
    }

    @Test
    void createCustomOrganizationRole_shouldReturnRole_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("organization-role.json")));

        final GitHubOrganizationRole role = api().createCustomOrganizationRole(
                        ORG, OrganizationsCatalogFixtures.createCustomOrganizationRolePayload());

        assertThat(role).isEqualTo(OrganizationsCatalogFixtures.organizationRole());
        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.POST);
        assertThat(captureSingleRequest().uri().getPath()).isEqualTo(ORGANIZATION_ROLES_PATH);
    }

    @Test
    void patchCustomOrganizationRole_shouldReturnRole_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("organization-role.json")));

        final GitHubOrganizationRole role = api().patchCustomOrganizationRole(
                        ORG, ROLE_ID, OrganizationsCatalogFixtures.patchCustomOrganizationRolePayload());

        assertThat(role).isEqualTo(OrganizationsCatalogFixtures.organizationRole());
        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.PATCH);
        assertThat(captureSingleRequest().uri().getPath()).isEqualTo(ORGANIZATION_ROLE_PATH);
    }

    @Test
    void deleteCustomOrganizationRole_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteCustomOrganizationRole(ORG, ROLE_ID);

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.DELETE);
        assertThat(captureSingleRequest().uri().getPath()).isEqualTo(ORGANIZATION_ROLE_PATH);
    }

    private GitHubOrganizationsOrganizationRolesApi api() {
        return new GitHubOrganizationsOrganizationRolesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubOrganizationsOrganizationRolesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
