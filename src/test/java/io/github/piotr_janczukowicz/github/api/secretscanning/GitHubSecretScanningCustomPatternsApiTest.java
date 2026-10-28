package io.github.piotr_janczukowicz.github.api.secretscanning;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
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
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubSecretScanningCustomPatternsApiTest {

    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final long PATTERN_ID = 1L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void listRepoCustomPatterns_shouldReturnPatterns_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-custom-pattern.json") + "]"));

        final GitHubPage<GitHubSecretScanningCustomPattern> page = new GitHubSecretScanningCustomPatternsApi(
                        TestClients.caller(transport))
                .listRepoCustomPatterns(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(SecretScanningCatalogFixtures.secretScanningCustomPattern());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/secret-scanning/custom-patterns");
        });
    }

    @Test
    void bulkCreateRepoCustomPatterns_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.CREATED.code(), json("secret-scanning-custom-pattern-bulk-create-response.json")));
        final GitHubBulkCreateSecretScanningCustomPatternsPayload payload =
                SecretScanningCatalogFixtures.bulkCreateSecretScanningCustomPatternsPayload();

        final GitHubBulkCreateSecretScanningCustomPatternsResult result = new GitHubSecretScanningCustomPatternsApi(
                        TestClients.caller(transport))
                .bulkCreateRepoCustomPatterns(OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result)
                    .isEqualTo(SecretScanningCatalogFixtures.bulkCreateSecretScanningCustomPatternsResult());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/secret-scanning/custom-patterns");
        });
    }

    @Test
    void bulkDeleteOrgCustomPatterns_shouldDeletePayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubBulkDeleteSecretScanningCustomPatternsPayload payload =
                SecretScanningCatalogFixtures.bulkDeleteSecretScanningCustomPatternsPayload();

        new GitHubSecretScanningCustomPatternsApi(TestClients.caller(transport))
                .bulkDeleteOrgCustomPatterns(ORG, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/secret-scanning/custom-patterns");
        });
    }

    @Test
    void updateRepoCustomPattern_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("secret-scanning-custom-pattern.json")));

        final GitHubSecretScanningCustomPattern pattern = new GitHubSecretScanningCustomPatternsApi(
                        TestClients.caller(transport))
                .updateRepoCustomPattern(
                        OWNER,
                        REPO,
                        PATTERN_ID,
                        SecretScanningCatalogFixtures.updateSecretScanningCustomPatternPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(pattern).isEqualTo(SecretScanningCatalogFixtures.secretScanningCustomPattern());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/secret-scanning/custom-patterns/1");
        });
    }

    @Test
    void listRepoCustomPatterns_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-custom-pattern.json") + "]"));
        final GitHubListSecretScanningCustomPatternsFilter filter =
                GitHubListSecretScanningCustomPatternsFilterBuilder.builder()
                        .state(GitHubSecretScanningCustomPatternState.PUBLISHED)
                        .build();

        new GitHubSecretScanningCustomPatternsApi(TestClients.caller(transport))
                .listRepoCustomPatterns(OWNER, REPO, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("state", "published");
    }

    @Test
    void listOrgCustomPatterns_shouldReturnPatterns_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-custom-pattern.json") + "]"));

        final GitHubPage<GitHubSecretScanningCustomPattern> page =
                new GitHubSecretScanningCustomPatternsApi(TestClients.caller(transport)).listOrgCustomPatterns(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(SecretScanningCatalogFixtures.secretScanningCustomPattern());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octo-org/secret-scanning/custom-patterns");
        });
    }

    @Test
    void listOrgCustomPatterns_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("secret-scanning-custom-pattern.json") + "]"));
        final GitHubListSecretScanningCustomPatternsFilter filter =
                GitHubListSecretScanningCustomPatternsFilterBuilder.builder()
                        .state(GitHubSecretScanningCustomPatternState.PUBLISHED)
                        .pushProtection(GitHubSecretScanningCustomPatternPushProtection.ENABLED)
                        .sort(GitHubSecretScanningCustomPatternSort.NAME)
                        .direction(GitHubDirection.ASC)
                        .build();

        new GitHubSecretScanningCustomPatternsApi(TestClients.caller(transport)).listOrgCustomPatterns(ORG, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("state", "published");
            softly.assertThat(requestUri).hasParameter("push_protection", "enabled");
            softly.assertThat(requestUri).hasParameter("sort", "name");
            softly.assertThat(requestUri).hasParameter("direction", "asc");
        });
    }

    @Test
    void listRepoCustomPatterns_shouldRejectPageRequest_whenPagePaginationUsed() {
        final GitHubSecretScanningCustomPatternsApi api =
                new GitHubSecretScanningCustomPatternsApi(TestClients.caller(transport));

        final GitHubListSecretScanningCustomPatternsFilter filter =
                GitHubListSecretScanningCustomPatternsFilter.empty();
        final GitHubPageRequest request = GitHubPageRequest.byPage();

        final Throwable thrown = catchThrowable(() -> api.listRepoCustomPatterns(OWNER, REPO, filter, request));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("unsupported pagination request: page");
    }

    @Test
    void listRepoCustomPatterns_shouldRejectExplicitPerPage_whenPerPageSet() {
        final GitHubSecretScanningCustomPatternsApi api =
                new GitHubSecretScanningCustomPatternsApi(TestClients.caller(transport));

        final GitHubListSecretScanningCustomPatternsFilter filter =
                GitHubListSecretScanningCustomPatternsFilter.empty();
        final GitHubCursorRequest request = GitHubCursorRequest.after("tok", 30);

        final Throwable thrown = catchThrowable(() -> api.listRepoCustomPatterns(OWNER, REPO, filter, request));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("explicit per_page");
    }

    @Test
    void listOrgCustomPatterns_shouldRejectExplicitPerPage_whenPerPageSet() {
        final GitHubSecretScanningCustomPatternsApi api =
                new GitHubSecretScanningCustomPatternsApi(TestClients.caller(transport));

        final GitHubListSecretScanningCustomPatternsFilter filter =
                GitHubListSecretScanningCustomPatternsFilter.empty();
        final GitHubCursorRequest request = GitHubCursorRequest.after("tok", 30);

        final Throwable thrown = catchThrowable(() -> api.listOrgCustomPatterns(ORG, filter, request));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("explicit per_page");
    }

    @Test
    void bulkCreateOrgCustomPatterns_shouldPostPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.CREATED.code(), json("secret-scanning-custom-pattern-bulk-create-response.json")));
        final GitHubBulkCreateSecretScanningCustomPatternsPayload payload =
                SecretScanningCatalogFixtures.bulkCreateSecretScanningCustomPatternsPayload();

        final GitHubBulkCreateSecretScanningCustomPatternsResult result = new GitHubSecretScanningCustomPatternsApi(
                        TestClients.caller(transport))
                .bulkCreateOrgCustomPatterns(ORG, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(result)
                    .isEqualTo(SecretScanningCatalogFixtures.bulkCreateSecretScanningCustomPatternsResult());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/secret-scanning/custom-patterns");
        });
    }

    @Test
    void bulkDeleteRepoCustomPatterns_shouldDeletePayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));
        final GitHubBulkDeleteSecretScanningCustomPatternsPayload payload =
                SecretScanningCatalogFixtures.bulkDeleteSecretScanningCustomPatternsPayload();

        new GitHubSecretScanningCustomPatternsApi(TestClients.caller(transport))
                .bulkDeleteRepoCustomPatterns(OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/secret-scanning/custom-patterns");
        });
    }

    @Test
    void updateOrgCustomPattern_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("secret-scanning-custom-pattern.json")));

        final GitHubSecretScanningCustomPattern pattern = new GitHubSecretScanningCustomPatternsApi(
                        TestClients.caller(transport))
                .updateOrgCustomPattern(
                        ORG, PATTERN_ID, SecretScanningCatalogFixtures.updateSecretScanningCustomPatternPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(pattern).isEqualTo(SecretScanningCatalogFixtures.secretScanningCustomPattern());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/secret-scanning/custom-patterns/1");
        });
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecretScanningCustomPatternsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @ParameterizedTest
    @MethodSource("nullFilterCalls")
    void validation_shouldThrow_whenFilterNull(final CustomPatternsCall call) {
        final GitHubSecretScanningCustomPatternsApi api =
                new GitHubSecretScanningCustomPatternsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @ParameterizedTest
    @MethodSource({"blankArgCalls", "unsupportedPaginationRequests"})
    void call_shouldThrowIllegalArgument_whenInvalid(final CustomPatternsCall call, final String messageFragment) {
        final GitHubSecretScanningCustomPatternsApi api =
                new GitHubSecretScanningCustomPatternsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @ParameterizedTest
    @MethodSource("nullPayloadCalls")
    void validation_shouldThrow_whenPayloadNull(final CustomPatternsCall call) {
        final GitHubSecretScanningCustomPatternsApi api =
                new GitHubSecretScanningCustomPatternsApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubSecretScanningCustomPatternsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<CustomPatternsCall> nullFilterCalls() {
        return Stream.of(
                api -> api.listOrgCustomPatterns(ORG, null),
                api -> api.listOrgCustomPatterns(ORG, null, GitHubPageRequest.byCursor()),
                api -> api.listRepoCustomPatterns(OWNER, REPO, null),
                api -> api.listRepoCustomPatterns(OWNER, REPO, null, GitHubPageRequest.byCursor()));
    }

    private static Stream<Arguments> nullPayloadCalls() {
        return Stream.of(
                Arguments.of((CustomPatternsCall) api -> api.bulkCreateOrgCustomPatterns(ORG, null)),
                Arguments.of((CustomPatternsCall) api -> api.bulkCreateRepoCustomPatterns(OWNER, REPO, null)),
                Arguments.of((CustomPatternsCall) api -> api.bulkDeleteOrgCustomPatterns(ORG, null)),
                Arguments.of((CustomPatternsCall) api -> api.bulkDeleteRepoCustomPatterns(OWNER, REPO, null)),
                Arguments.of((CustomPatternsCall) api -> api.updateOrgCustomPattern(ORG, PATTERN_ID, null)),
                Arguments.of((CustomPatternsCall) api -> api.updateRepoCustomPattern(OWNER, REPO, PATTERN_ID, null)));
    }

    private static Stream<Arguments> blankArgCalls() {
        final GitHubListSecretScanningCustomPatternsFilter customPatternsFilter =
                GitHubListSecretScanningCustomPatternsFilter.empty();
        final GitHubBulkCreateSecretScanningCustomPatternsPayload bulkCreatePayload =
                SecretScanningCatalogFixtures.bulkCreateSecretScanningCustomPatternsPayload();
        final GitHubBulkDeleteSecretScanningCustomPatternsPayload bulkDeletePayload =
                SecretScanningCatalogFixtures.bulkDeleteSecretScanningCustomPatternsPayload();
        final GitHubUpdateSecretScanningCustomPatternPayload customPatternPayload =
                SecretScanningCatalogFixtures.updateSecretScanningCustomPatternPayload();
        return Stream.of(
                Arguments.of((CustomPatternsCall) api -> api.listOrgCustomPatterns(" "), "org"),
                Arguments.of((CustomPatternsCall) api -> api.listOrgCustomPatterns(" ", customPatternsFilter), "org"),
                Arguments.of(
                        (CustomPatternsCall) api ->
                                api.listOrgCustomPatterns(" ", customPatternsFilter, GitHubPageRequest.byCursor()),
                        "org"),
                Arguments.of((CustomPatternsCall) api -> api.listRepoCustomPatterns(" ", REPO), "owner"),
                Arguments.of(
                        (CustomPatternsCall) api -> api.listRepoCustomPatterns(" ", REPO, customPatternsFilter),
                        "owner"),
                Arguments.of((CustomPatternsCall) api -> api.listRepoCustomPatterns(OWNER, " "), "repo"),
                Arguments.of(
                        (CustomPatternsCall) api -> api.bulkCreateOrgCustomPatterns(" ", bulkCreatePayload), "org"),
                Arguments.of(
                        (CustomPatternsCall) api -> api.bulkCreateRepoCustomPatterns(" ", REPO, bulkCreatePayload),
                        "owner"),
                Arguments.of(
                        (CustomPatternsCall) api -> api.bulkCreateRepoCustomPatterns(OWNER, " ", bulkCreatePayload),
                        "repo"),
                Arguments.of(
                        (CustomPatternsCall) api -> api.bulkDeleteOrgCustomPatterns(" ", bulkDeletePayload), "org"),
                Arguments.of(
                        (CustomPatternsCall) api -> api.bulkDeleteRepoCustomPatterns(" ", REPO, bulkDeletePayload),
                        "owner"),
                Arguments.of(
                        (CustomPatternsCall) api -> api.bulkDeleteRepoCustomPatterns(OWNER, " ", bulkDeletePayload),
                        "repo"),
                Arguments.of(
                        (CustomPatternsCall) api -> api.updateOrgCustomPattern(" ", PATTERN_ID, customPatternPayload),
                        "org"),
                Arguments.of(
                        (CustomPatternsCall)
                                api -> api.updateRepoCustomPattern(" ", REPO, PATTERN_ID, customPatternPayload),
                        "owner"),
                Arguments.of(
                        (CustomPatternsCall)
                                api -> api.updateRepoCustomPattern(OWNER, " ", PATTERN_ID, customPatternPayload),
                        "repo"));
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubListSecretScanningCustomPatternsFilter customPatternsFilter =
                GitHubListSecretScanningCustomPatternsFilter.empty();
        final GitHubPageRequest unsupportedSince = GitHubPageRequest.bySince();
        return Stream.of(
                Arguments.of(
                        (CustomPatternsCall)
                                api -> api.listOrgCustomPatterns(ORG, customPatternsFilter, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (CustomPatternsCall)
                                api -> api.listOrgCustomPatterns(ORG, customPatternsFilter, GitHubPageRequest.byPage()),
                        "unsupported pagination request: page"),
                Arguments.of(
                        (CustomPatternsCall)
                                api -> api.listRepoCustomPatterns(OWNER, REPO, customPatternsFilter, unsupportedSince),
                        "unsupported pagination request: since"));
    }

    @FunctionalInterface
    interface CustomPatternsCall {
        void execute(GitHubSecretScanningCustomPatternsApi api) throws Exception;
    }
}
