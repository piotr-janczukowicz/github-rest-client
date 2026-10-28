package io.github.piotr_janczukowicz.github.api.securityadvisories;

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
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubSecurityAdvisoriesGlobalAdvisoriesApiTest {

    private static final String GLOBAL_GHSA_ID = "GHSA-jfh8-c2jp-5v3q";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecurityAdvisoriesGlobalAdvisoriesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listGlobalAdvisories_shouldReturnAdvisories_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("global-advisory.json") + "]"));

        final GitHubPage<GitHubGlobalAdvisory> page =
                new GitHubSecurityAdvisoriesGlobalAdvisoriesApi(TestClients.caller(transport)).listGlobalAdvisories();
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(SecurityAdvisoriesCatalogFixtures.globalAdvisory());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/advisories?" + TestClients.perPageQuery());
        });
    }

    @Test
    void listGlobalAdvisories_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("global-advisory.json") + "]"));
        final GitHubListGlobalAdvisoriesFilter filter = GitHubListGlobalAdvisoriesFilterBuilder.builder()
                .type(GitHubGlobalAdvisoryType.REVIEWED)
                .ecosystem(GitHubSecurityAdvisoryEcosystem.NPM)
                .severity(GitHubSecurityAdvisorySeverity.HIGH)
                .direction(GitHubDirection.DESC)
                .sort(GitHubGlobalAdvisoriesSort.UPDATED)
                .build();

        new GitHubSecurityAdvisoriesGlobalAdvisoriesApi(TestClients.caller(transport)).listGlobalAdvisories(filter);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("type", "reviewed");
            softly.assertThat(requestUri).hasParameter("ecosystem", "npm");
            softly.assertThat(requestUri).hasParameter("severity", "high");
            softly.assertThat(requestUri).hasParameter("sort", "updated");
            softly.assertThat(requestUri).hasParameter("direction", "desc");
        });
    }

    @Test
    void listGlobalAdvisories_shouldOmitPageQuery_whenGitHubNoneRequest() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("global-advisory.json") + "]"));

        final GitHubPage<GitHubGlobalAdvisory> page = new GitHubSecurityAdvisoriesGlobalAdvisoriesApi(
                        TestClients.caller(transport))
                .listGlobalAdvisories(GitHubListGlobalAdvisoriesFilter.empty(), GitHubPageRequest.none());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(SecurityAdvisoriesCatalogFixtures.globalAdvisory());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri).hasToString("https://api.github.com/advisories");
            softly.assertThat(requestUri.getQuery()).isNull();
        });
    }

    @Test
    void listGlobalAdvisories_shouldReturnAdvisories_whenCursorAfterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("global-advisory.json") + "]"));

        final GitHubPage<GitHubGlobalAdvisory> page = new GitHubSecurityAdvisoriesGlobalAdvisoriesApi(
                        TestClients.caller(transport))
                .listGlobalAdvisories(
                        GitHubListGlobalAdvisoriesFilter.empty(), GitHubCursorRequest.after("cursor-token"));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(SecurityAdvisoriesCatalogFixtures.globalAdvisory());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/advisories?after=cursor-token&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listGlobalAdvisories_shouldThrow_whenFilterNull() {
        final GitHubSecurityAdvisoriesGlobalAdvisoriesApi api =
                new GitHubSecurityAdvisoriesGlobalAdvisoriesApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> api.listGlobalAdvisories(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
    }

    @ParameterizedTest
    @MethodSource({"blankArgCalls", "unsupportedPaginationRequests"})
    void call_shouldThrowIllegalArgument_whenInvalid(final GlobalAdvisoriesCall call, final String messageFragment) {
        final GitHubSecurityAdvisoriesGlobalAdvisoriesApi api =
                new GitHubSecurityAdvisoriesGlobalAdvisoriesApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    @Test
    void getGlobalAdvisory_shouldReturnAdvisory_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("global-advisory.json")));

        final GitHubGlobalAdvisory advisory = new GitHubSecurityAdvisoriesGlobalAdvisoriesApi(
                        TestClients.caller(transport))
                .getGlobalAdvisory(GLOBAL_GHSA_ID)
                .orElseThrow();
        final URI requestUri = captureSingleRequest().uri();
        final GitHubGlobalAdvisory expected = SecurityAdvisoriesCatalogFixtures.globalAdvisory();

        assertSoftly(softly -> {
            softly.assertThat(advisory).isEqualTo(expected);
            softly.assertThat(requestUri).hasToString("https://api.github.com/advisories/" + GLOBAL_GHSA_ID);
        });
    }

    @Test
    void getGlobalAdvisory_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubGlobalAdvisory> advisory = new GitHubSecurityAdvisoriesGlobalAdvisoriesApi(
                        TestClients.caller(transport))
                .getGlobalAdvisory(GLOBAL_GHSA_ID);

        assertThat(advisory).isEmpty();
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubSecurityAdvisoriesGlobalAdvisoriesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<Arguments> blankArgCalls() {
        return Stream.of(Arguments.of((GlobalAdvisoriesCall) api -> api.getGlobalAdvisory(" "), "{ghsa_id}"));
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubListGlobalAdvisoriesFilter globalFilter = GitHubListGlobalAdvisoriesFilter.empty();
        final GitHubPageRequest unsupportedPage = GitHubPageRequest.byPage();
        final GitHubPageRequest unsupportedSince = GitHubPageRequest.bySince();
        return Stream.of(
                Arguments.of(
                        (GlobalAdvisoriesCall) api -> api.listGlobalAdvisories(globalFilter, unsupportedPage),
                        "unsupported pagination request: page"),
                Arguments.of(
                        (GlobalAdvisoriesCall) api -> api.listGlobalAdvisories(globalFilter, unsupportedSince),
                        "unsupported pagination request: since"));
    }

    @FunctionalInterface
    interface GlobalAdvisoriesCall {
        void execute(GitHubSecurityAdvisoriesGlobalAdvisoriesApi api) throws Exception;
    }
}
