package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
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
import java.time.Instant;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubOrganizationsPersonalAccessTokensApiTest {

    private static final String ORG = "github";
    private static final long PAT_REQUEST_ID = 25_381L;
    private static final long PAT_ID = 25_381L;
    private static final Instant LAST_USED_BEFORE = Instant.parse("2024-01-01T00:00:00Z");
    private static final Instant LAST_USED_AFTER = Instant.parse("2023-01-01T00:00:00Z");

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsPersonalAccessTokensApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listPatGrantRequests_shouldReturnGrantRequests_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), patGrantRequestItemsJson()));

        final GitHubPage<GitHubOrganizationPatGrantRequest> page = callApi(api -> api.listPatGrantRequests(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(OrganizationsCatalogFixtures.organizationPatGrantRequestFromStub());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/personal-access-token-requests?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listPatGrantRequests_shouldOmitPageQuery_whenNoneRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), patGrantRequestItemsJson()));

        callApi(api -> api.listPatGrantRequests(ORG, GitHubPageRequest.none()));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/personal-access-token-requests");
    }

    @Test
    void listPatGrants_shouldOmitPageQuery_whenNoneRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), patGrantItemsJson()));

        callApi(api -> api.listPatGrants(ORG, GitHubPageRequest.none()));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/personal-access-tokens");
    }

    @Test
    void listPatGrants_shouldSendFilterQuery_whenFilterAndRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), patGrantItemsJson()));
        final GitHubOrganizationsPersonalAccessTokensFilter filter = patFilter();

        callApi(api -> api.listPatGrants(ORG, filter, GitHubPageRequest.none()));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri()).hasParameter("token_id[]", "1");
    }

    @Test
    void listPatGrantRequestRepositories_shouldOmitPageQuery_whenNoneRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), minimalRepositoriesJson()));

        callApi(api -> api.listPatGrantRequestRepositories(ORG, PAT_REQUEST_ID, GitHubPageRequest.none()));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri())
                .hasToString("https://api.github.com/orgs/github/personal-access-token-requests/25381/repositories");
    }

    @Test
    void listPatGrantRepositories_shouldOmitPageQuery_whenNoneRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), minimalRepositoriesJson()));

        callApi(api -> api.listPatGrantRepositories(ORG, PAT_ID, GitHubPageRequest.none()));
        final HttpRequest request = captureSingleRequest();

        assertThat(request.uri())
                .hasToString("https://api.github.com/orgs/github/personal-access-tokens/25381/repositories");
    }

    @Test
    void listPatGrantRequests_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), patGrantRequestItemsJson()));
        final GitHubOrganizationsPersonalAccessTokensFilter filter = patFilter();

        callApi(api -> api.listPatGrantRequests(ORG, filter));
        final HttpRequest request = captureSingleRequest();

        assertPatFilterQuery(request, "/orgs/github/personal-access-token-requests");
    }

    @Test
    void listPatGrants_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), patGrantItemsJson()));
        final GitHubOrganizationsPersonalAccessTokensFilter filter = patFilter();

        callApi(api -> api.listPatGrants(ORG, filter));
        final HttpRequest request = captureSingleRequest();

        assertPatFilterQuery(request, "/orgs/github/personal-access-tokens");
    }

    @Test
    void listPatGrantRequests_shouldThrow_whenFilterNull() {
        final Throwable thrown = catchThrowable(
                () -> callApiVoid(api -> api.listPatGrantRequests(ORG, null, GitHubPageRequest.byPage())));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @Test
    void reviewPatGrantRequestsInBulk_shouldSucceed_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), "{}"));
        final GitHubReviewPatGrantRequestsInBulkPayload payload =
                GitHubReviewPatGrantRequestsInBulkPayloadBuilder.builder(
                                OrganizationsCatalogFixtures.reviewPatGrantRequestsInBulkPayload())
                        .action(GitHubOrganizationPatGrantReviewAction.APPROVE)
                        .patRequestIds(null)
                        .reason(null)
                        .build();

        callApiVoid(api -> api.reviewPatGrantRequestsInBulk(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/personal-access-token-requests");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo("{\"action\":\"approve\"}");
        });
    }

    @Test
    void reviewPatGrantRequestsInBulk_shouldSendDenyPayload_whenDenyActionProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), "{}"));
        final GitHubReviewPatGrantRequestsInBulkPayload payload =
                GitHubReviewPatGrantRequestsInBulkPayloadBuilder.builder(
                                OrganizationsCatalogFixtures.reviewPatGrantRequestsInBulkPayload())
                        .action(GitHubOrganizationPatGrantReviewAction.DENY)
                        .build();

        callApiVoid(api -> api.reviewPatGrantRequestsInBulk(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo(new String(GitHubJsonCodecs.defaults().encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void reviewPatGrantRequest_shouldSucceed_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubReviewPatGrantRequestPayload payload = GitHubReviewPatGrantRequestPayloadBuilder.builder(
                        OrganizationsCatalogFixtures.reviewPatGrantRequestPayload())
                .action(GitHubOrganizationPatGrantReviewAction.APPROVE)
                .reason(null)
                .build();

        callApiVoid(api -> api.reviewPatGrantRequest(ORG, PAT_REQUEST_ID, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/personal-access-token-requests/25381");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo("{\"action\":\"approve\"}");
        });
    }

    @Test
    void reviewPatGrantRequest_shouldSendDenyPayload_whenDenyActionProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubReviewPatGrantRequestPayload payload = GitHubReviewPatGrantRequestPayloadBuilder.builder(
                        OrganizationsCatalogFixtures.reviewPatGrantRequestPayload())
                .action(GitHubOrganizationPatGrantReviewAction.DENY)
                .build();

        callApiVoid(api -> api.reviewPatGrantRequest(ORG, PAT_REQUEST_ID, payload));
        final HttpRequest request = captureSingleRequest();

        assertThat(new String(request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                .isEqualTo(new String(GitHubJsonCodecs.defaults().encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void listPatGrantRequestRepositories_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), minimalRepositoriesJson()));

        final GitHubPage<GitHubMinimalRepository> page =
                callApi(api -> api.listPatGrantRequestRepositories(ORG, PAT_REQUEST_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.helloWorldMinimalRepository());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/orgs/github/personal-access-token-requests/25381/repositories?page=1&"
                                    + TestClients.perPageQuery());
        });
    }

    @Test
    void listPatGrants_shouldReturnGrants_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), patGrantItemsJson()));

        final GitHubPage<GitHubOrganizationPatGrant> page = callApi(api -> api.listPatGrants(ORG));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(OrganizationsCatalogFixtures.organizationPatGrantFromStub());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/personal-access-tokens?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void updatePatAccesses_shouldSucceed_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), "{}"));
        final GitHubUpdatePatAccessesPayload payload = GitHubUpdatePatAccessesPayloadBuilder.builder(
                        OrganizationsCatalogFixtures.updatePatAccessesPayload())
                .action(GitHubOrganizationPatAccessUpdateAction.REVOKE)
                .build();

        callApiVoid(api -> api.updatePatAccesses(ORG, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString("https://api.github.com/orgs/github/personal-access-tokens");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo(new String(GitHubJsonCodecs.defaults().encode(payload), StandardCharsets.UTF_8));
        });
    }

    @Test
    void updatePatAccess_shouldSucceed_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubUpdatePatAccessPayload payload = GitHubUpdatePatAccessPayloadBuilder.builder(
                        OrganizationsCatalogFixtures.updatePatAccessPayload())
                .action(GitHubOrganizationPatAccessUpdateAction.REVOKE)
                .build();

        callApiVoid(api -> api.updatePatAccess(ORG, PAT_ID, payload));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/personal-access-tokens/25381");
            softly.assertThat(new String(
                            request.body().mapBytes(bytes -> bytes, () -> new byte[0]), StandardCharsets.UTF_8))
                    .isEqualTo("{\"action\":\"revoke\"}");
        });
    }

    @Test
    void listPatGrantRepositories_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), minimalRepositoriesJson()));

        final GitHubPage<GitHubMinimalRepository> page = callApi(api -> api.listPatGrantRepositories(ORG, PAT_ID));
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(RepositoriesCatalogFixtures.helloWorldMinimalRepository());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/github/personal-access-tokens/25381/repositories?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @ParameterizedTest
    @MethodSource("blankOrgCalls")
    void validation_shouldThrow_whenOrgBlank(final PersonalAccessTokensApiCall call) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("org");
    }

    @ParameterizedTest
    @MethodSource("nullPayloadCalls")
    void validation_shouldThrow_whenPayloadNull(final PersonalAccessTokensApiCall call) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @ParameterizedTest
    @MethodSource("unsupportedPaginationRequests")
    void listEndpoint_shouldThrow_whenUnsupportedPagination(
            final PersonalAccessTokensApiCall call, final String messageFragment) {
        final Throwable thrown = catchThrowable(() -> callApiVoid(call));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @FunctionalInterface
    interface PersonalAccessTokensApiCall {
        void execute(GitHubOrganizationsPersonalAccessTokensApi api) throws Exception;
    }

    @FunctionalInterface
    private interface PersonalAccessTokensApiAction<T> {
        T apply(GitHubOrganizationsPersonalAccessTokensApi api) throws Exception;
    }

    private void callApiVoid(final PersonalAccessTokensApiCall action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            action.execute(new GitHubOrganizationsPersonalAccessTokensApi(TestClients.caller(client)));
        }
    }

    private <T> T callApi(final PersonalAccessTokensApiAction<T> action) throws Exception {
        try (GitHubClient client = TestClients.client(transport)) {
            return action.apply(new GitHubOrganizationsPersonalAccessTokensApi(TestClients.caller(client)));
        }
    }

    private static GitHubOrganizationsPersonalAccessTokensFilter patFilter() {
        return GitHubOrganizationsPersonalAccessTokensFilterBuilder.builder()
                .sort(GitHubOrganizationPersonalAccessTokenSort.CREATED_AT)
                .direction(GitHubDirection.DESC)
                .owner(List.of("octocat", "hubot"))
                .repository("Hello-World")
                .permission("metadata")
                .lastUsedBefore(LAST_USED_BEFORE)
                .lastUsedAfter(LAST_USED_AFTER)
                .tokenId(List.of("1", "2"))
                .build();
    }

    private static void assertPatFilterQuery(final HttpRequest request, final String path) {
        assertSoftly(softly -> {
            softly.assertThat(request.uri().getPath()).isEqualTo(path);
            softly.assertThat(request.uri()).hasParameter("sort", "created_at");
            softly.assertThat(request.uri()).hasParameter("direction", "desc");
            softly.assertThat(request.uri()).hasParameter("owner[]", "octocat");
            softly.assertThat(request.uri()).hasParameter("owner[]", "hubot");
            softly.assertThat(request.uri()).hasParameter("repository", "Hello-World");
            softly.assertThat(request.uri()).hasParameter("permission", "metadata");
            softly.assertThat(request.uri()).hasParameter("last_used_before", LAST_USED_BEFORE.toString());
            softly.assertThat(request.uri()).hasParameter("last_used_after", LAST_USED_AFTER.toString());
            softly.assertThat(request.uri()).hasParameter("token_id[]", "1");
            softly.assertThat(request.uri()).hasParameter("token_id[]", "2");
        });
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubPageRequest unsupportedSince = GitHubPageRequest.bySince();
        final GitHubPageRequest unsupportedCursor = GitHubPageRequest.byCursor();
        final GitHubOrganizationsPersonalAccessTokensFilter filter =
                GitHubOrganizationsPersonalAccessTokensFilter.empty();
        return Stream.of(
                Arguments.of(
                        (PersonalAccessTokensApiCall) api -> api.listPatGrantRequests(ORG, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (PersonalAccessTokensApiCall) api -> api.listPatGrantRequests(ORG, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (PersonalAccessTokensApiCall) api -> api.listPatGrantRequests(ORG, filter, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (PersonalAccessTokensApiCall) api -> api.listPatGrants(ORG, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (PersonalAccessTokensApiCall)
                                api -> api.listPatGrantRequestRepositories(ORG, PAT_REQUEST_ID, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (PersonalAccessTokensApiCall)
                                api -> api.listPatGrantRequestRepositories(ORG, PAT_REQUEST_ID, unsupportedCursor),
                        "unsupported pagination request: cursor"),
                Arguments.of(
                        (PersonalAccessTokensApiCall)
                                api -> api.listPatGrantRepositories(ORG, PAT_ID, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (PersonalAccessTokensApiCall)
                                api -> api.listPatGrantRepositories(ORG, PAT_ID, unsupportedCursor),
                        "unsupported pagination request: cursor"));
    }

    private static List<PersonalAccessTokensApiCall> blankOrgCalls() {
        return List.of(
                api -> api.listPatGrantRequests(" "),
                api -> api.reviewPatGrantRequestsInBulk(" ", denyBulkPayload()),
                api -> api.reviewPatGrantRequest(" ", PAT_REQUEST_ID, denySinglePayload()),
                api -> api.listPatGrantRequestRepositories(" ", PAT_REQUEST_ID),
                api -> api.listPatGrants(" "),
                api -> api.updatePatAccesses(" ", revokeBulkPayload()),
                api -> api.updatePatAccess(" ", PAT_ID, revokeSinglePayload()),
                api -> api.listPatGrantRepositories(" ", PAT_ID));
    }

    private static List<PersonalAccessTokensApiCall> nullPayloadCalls() {
        return List.of(
                api -> api.reviewPatGrantRequestsInBulk(ORG, null),
                api -> api.reviewPatGrantRequest(ORG, PAT_REQUEST_ID, null),
                api -> api.updatePatAccesses(ORG, null),
                api -> api.updatePatAccess(ORG, PAT_ID, null));
    }

    private static GitHubReviewPatGrantRequestsInBulkPayload denyBulkPayload() {
        return GitHubReviewPatGrantRequestsInBulkPayloadBuilder.builder(
                        OrganizationsCatalogFixtures.reviewPatGrantRequestsInBulkPayload())
                .action(GitHubOrganizationPatGrantReviewAction.DENY)
                .build();
    }

    private static GitHubReviewPatGrantRequestPayload denySinglePayload() {
        return GitHubReviewPatGrantRequestPayloadBuilder.builder(
                        OrganizationsCatalogFixtures.reviewPatGrantRequestPayload())
                .action(GitHubOrganizationPatGrantReviewAction.DENY)
                .build();
    }

    private static GitHubUpdatePatAccessesPayload revokeBulkPayload() {
        return GitHubUpdatePatAccessesPayloadBuilder.builder(OrganizationsCatalogFixtures.updatePatAccessesPayload())
                .action(GitHubOrganizationPatAccessUpdateAction.REVOKE)
                .build();
    }

    private static GitHubUpdatePatAccessPayload revokeSinglePayload() {
        return GitHubUpdatePatAccessPayloadBuilder.builder(OrganizationsCatalogFixtures.updatePatAccessPayload())
                .action(GitHubOrganizationPatAccessUpdateAction.REVOKE)
                .build();
    }

    private static String patGrantRequestItemsJson() {
        return FileUtils.readString(
                GitHubOrganizationsPersonalAccessTokensApiTest.class, "org-pat-grant-request-items.json");
    }

    private static String patGrantItemsJson() {
        return FileUtils.readString(GitHubOrganizationsPersonalAccessTokensApiTest.class, "org-pat-grant-items.json");
    }

    private static String minimalRepositoriesJson() {
        return "[" + FileUtils.readString(GitHubMinimalRepository.class, "minimal-repository.json") + "]";
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
