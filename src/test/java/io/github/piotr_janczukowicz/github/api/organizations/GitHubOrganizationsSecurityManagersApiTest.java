package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamSimple;
import io.github.piotr_janczukowicz.github.api.teams.TeamSimpleCatalogFixtures;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubOrganizationsSecurityManagersApiTest {

    private static final String ORG = "github";
    private static final String TEAM_SLUG = "justice-league";
    private static final String SECURITY_MANAGERS_PATH = "/orgs/github/security-managers";
    private static final String SECURITY_MANAGER_TEAM_PATH = "/orgs/github/security-managers/teams/justice-league";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsSecurityManagersApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void list_shouldReturnSecurityManagerTeams_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), teamSimpleItemsJson()));

        final List<GitHubTeamSimple> teams = callApi(api -> api.list(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(teams).isEqualTo(List.of(TeamSimpleCatalogFixtures.sampleParentTeam()));
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + SECURITY_MANAGERS_PATH);
            softly.assertThat(request.uri().getQuery()).isNull();
        });
    }

    @Test
    void addTeam_shouldPutWithoutBody_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.addTeam(ORG, TEAM_SLUG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + SECURITY_MANAGER_TEAM_PATH);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void removeTeam_shouldDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.removeTeam(ORG, TEAM_SLUG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri()).hasToString("https://api.github.com" + SECURITY_MANAGER_TEAM_PATH);
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @ParameterizedTest
    @MethodSource("blankOrgCalls")
    void validation_shouldThrow_whenOrgBlank(final SecurityManagersApiCall call) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
    }

    @ParameterizedTest
    @MethodSource("blankTeamSlugCalls")
    void validation_shouldThrow_whenTeamSlugBlank(final SecurityManagersApiCall call) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("{team_slug}");
    }

    @FunctionalInterface
    interface SecurityManagersApiCall {
        void execute(GitHubOrganizationsSecurityManagersApi api) throws Exception;
    }

    @FunctionalInterface
    private interface SecurityManagersApiAction<T> {
        T apply(GitHubOrganizationsSecurityManagersApi api) throws Exception;
    }

    private void callApiVoid(final SecurityManagersApiCall action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            action.execute(new GitHubOrganizationsSecurityManagersApi(TestClients.caller(client)));
        }
    }

    private <T> T callApi(final SecurityManagersApiAction<T> action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationsSecurityManagersApi(TestClients.caller(client)));
        }
    }

    private static List<SecurityManagersApiCall> blankOrgCalls() {
        return List.of(api -> api.list(" "), api -> api.addTeam(" ", TEAM_SLUG), api -> api.removeTeam(" ", TEAM_SLUG));
    }

    private static List<SecurityManagersApiCall> blankTeamSlugCalls() {
        return List.of(api -> api.addTeam(ORG, " "), api -> api.removeTeam(ORG, " "));
    }

    private static String teamSimpleItemsJson() {
        return "["
                + new String(FileUtils.readBytes(GitHubTeamSimple.class, "team-simple.json"), StandardCharsets.UTF_8)
                + "]";
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
