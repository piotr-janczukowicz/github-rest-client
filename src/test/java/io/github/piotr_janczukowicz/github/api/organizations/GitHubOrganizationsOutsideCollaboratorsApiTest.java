package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.client.exception.GitHubValidationException;
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
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubOrganizationsOutsideCollaboratorsApiTest {

    private static final String ORG = "github";
    private static final String USERNAME = "octocat";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsOutsideCollaboratorsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listOutsideCollaborators_shouldReturnUsers_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), usersJson()));

        final GitHubPage<GitHubSimpleUser> page = callApi(api -> api.listOutsideCollaborators(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(OrganizationsCatalogFixtures.usersOne());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/outside_collaborators?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listOutsideCollaborators_shouldOmitPageQuery_whenNoneRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), usersJson()));

        callApi(api -> api.listOutsideCollaborators(ORG, GitHubPageRequest.none()));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/outside_collaborators");
    }

    @Test
    void listOutsideCollaborators_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), usersJson()));
        final GitHubOrganizationsOutsideCollaboratorsFilter filter =
                GitHubOrganizationsOutsideCollaboratorsFilterBuilder.builder()
                        .filter(GitHubOrganizationOutsideCollaboratorsKind.TWO_FA_INSECURE)
                        .build();

        callApi(api -> api.listOutsideCollaborators(ORG, filter));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/github/outside_collaborators");
            softly.assertThat(request.uri()).hasParameter("filter", "2fa_insecure");
            softly.assertThat(request.uri()).hasParameter("page", "1");
            softly.assertThat(request.uri()).hasParameter("per_page", "100");
        });
    }

    @Test
    void listOutsideCollaborators_shouldSendFilterQuery_whenFilterAndRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), usersJson()));
        final GitHubOrganizationsOutsideCollaboratorsFilter filter =
                GitHubOrganizationsOutsideCollaboratorsFilterBuilder.builder()
                        .filter(GitHubOrganizationOutsideCollaboratorsKind.ALL)
                        .build();

        callApi(api -> api.listOutsideCollaborators(ORG, filter, GitHubPageRequest.none()));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/github/outside_collaborators");
            softly.assertThat(request.uri()).hasParameter("filter", "all");
        });
    }

    @Test
    void listOutsideCollaborators_shouldThrow_whenFilterNull() {
        final Throwable thrown = catchThrowable(
                () -> callApiVoid(api -> api.listOutsideCollaborators(ORG, null, GitHubPageRequest.byPage())));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @ParameterizedTest
    @MethodSource("convertWithoutBody")
    void convertMemberToOutsideCollaborator_shouldSendPutWithAbsentBody_whenPayloadOmitted(
            final OutsideCollaboratorsApiCall call) throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(call);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/outside_collaborators/octocat");
            softly.assertThat(request.body().isAbsent()).isTrue();
        });
    }

    @Test
    void convertMemberToOutsideCollaborator_shouldSucceed_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), "{}"));

        callApiVoid(api -> api.convertMemberToOutsideCollaborator(ORG, USERNAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/outside_collaborators/octocat");
        });
    }

    @Test
    void convertMemberToOutsideCollaborator_shouldSendPutWithBody_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), "{}"));
        final GitHubConvertMemberToOutsideCollaboratorPayload payload =
                GitHubConvertMemberToOutsideCollaboratorPayloadBuilder.builder(
                                OrganizationsCatalogFixtures.convertMemberToOutsideCollaboratorPayload())
                        .async(true)
                        .build();

        callApiVoid(api -> api.convertMemberToOutsideCollaborator(ORG, USERNAME, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo("{\"async\":true}");
        });
    }

    @Test
    void removeOutsideCollaborator_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        callApiVoid(api -> api.removeOutsideCollaborator(ORG, USERNAME));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/outside_collaborators/octocat");
        });
    }

    @Test
    void removeOutsideCollaborator_shouldThrowValidationException_whenResponseIsUnprocessable() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.UNPROCESSABLE_ENTITY.code(),
                        FileUtils.readString(GitHubClient.class, "validation-failed.json")));
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsOutsideCollaboratorsApi api =
                    new GitHubOrganizationsOutsideCollaboratorsApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> api.removeOutsideCollaborator(ORG, USERNAME));

            assertThat(thrown).isInstanceOf(GitHubValidationException.class);
        }
    }

    @ParameterizedTest
    @MethodSource("blankOrgCalls")
    void validation_shouldThrow_whenOrgBlank(final OutsideCollaboratorsApiCall call) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
    }

    @ParameterizedTest
    @MethodSource("blankUsernameCalls")
    void validation_shouldThrow_whenUsernameBlank(final OutsideCollaboratorsApiCall call) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("username");
    }

    @ParameterizedTest
    @MethodSource("unsupportedPaginationRequests")
    void listEndpoint_shouldThrow_whenUnsupportedPagination(
            final OutsideCollaboratorsApiCall call, final String messageFragment) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @FunctionalInterface
    interface OutsideCollaboratorsApiCall {
        void execute(GitHubOrganizationsOutsideCollaboratorsApi api) throws Exception;
    }

    @FunctionalInterface
    private interface OutsideCollaboratorsApiAction<T> {
        T apply(GitHubOrganizationsOutsideCollaboratorsApi api) throws Exception;
    }

    private void callApiVoid(final OutsideCollaboratorsApiCall action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            action.execute(new GitHubOrganizationsOutsideCollaboratorsApi(TestClients.caller(client)));
        }
    }

    private <T> T callApi(final OutsideCollaboratorsApiAction<T> action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationsOutsideCollaboratorsApi(TestClients.caller(client)));
        }
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubPageRequest unsupportedSince = GitHubPageRequest.bySince();
        final GitHubPageRequest unsupportedCursor = GitHubPageRequest.byCursor();
        final GitHubOrganizationsOutsideCollaboratorsFilter filter =
                GitHubOrganizationsOutsideCollaboratorsFilter.empty();
        return Stream.of(
                Arguments.of(
                        (OutsideCollaboratorsApiCall) api -> api.listOutsideCollaborators(ORG, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (OutsideCollaboratorsApiCall) api -> api.listOutsideCollaborators(ORG, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (OutsideCollaboratorsApiCall)
                                api -> api.listOutsideCollaborators(ORG, filter, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (OutsideCollaboratorsApiCall)
                                api -> api.listOutsideCollaborators(ORG, filter, unsupportedCursor),
                        "unsupported pagination request: cursor"));
    }

    private static List<OutsideCollaboratorsApiCall> convertWithoutBody() {
        return List.of(
                api -> api.convertMemberToOutsideCollaborator(ORG, USERNAME),
                api -> api.convertMemberToOutsideCollaborator(ORG, USERNAME, null));
    }

    private static List<OutsideCollaboratorsApiCall> blankOrgCalls() {
        return List.of(
                api -> api.listOutsideCollaborators(" "),
                api -> api.convertMemberToOutsideCollaborator(" ", USERNAME),
                api -> api.convertMemberToOutsideCollaborator(
                        " ", USERNAME, OrganizationsCatalogFixtures.convertMemberToOutsideCollaboratorPayload()),
                api -> api.removeOutsideCollaborator(" ", USERNAME));
    }

    private static List<OutsideCollaboratorsApiCall> blankUsernameCalls() {
        return List.of(
                api -> api.convertMemberToOutsideCollaborator(ORG, " "),
                api -> api.convertMemberToOutsideCollaborator(
                        ORG, " ", OrganizationsCatalogFixtures.convertMemberToOutsideCollaboratorPayload()),
                api -> api.removeOutsideCollaborator(ORG, " "));
    }

    private static String usersJson() {
        return FileUtils.readString(GitHubSimpleUser.class, "users-one.json");
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
