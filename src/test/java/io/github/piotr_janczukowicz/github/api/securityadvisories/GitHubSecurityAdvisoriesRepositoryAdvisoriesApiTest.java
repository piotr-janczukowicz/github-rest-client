package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubFullRepository;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
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
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubSecurityAdvisoriesRepositoryAdvisoriesApiTest {

    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final String REPOSITORY_GHSA_ID = "GHSA-49wp-qq6x-g2rf";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listOrgRepositoryAdvisories_shouldReturnAdvisories_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-advisory.json") + "]"));

        final GitHubPage<GitHubRepositoryAdvisory> page = new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(
                        TestClients.caller(transport))
                .listOrgRepositoryAdvisories(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(SecurityAdvisoriesCatalogFixtures.repositoryAdvisory());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/orgs/octo-org/security-advisories?" + TestClients.perPageQuery());
        });
    }

    @Test
    void listOrgRepositoryAdvisories_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-advisory.json") + "]"));
        final GitHubListRepositoryAdvisoriesFilter filter = GitHubListRepositoryAdvisoriesFilterBuilder.builder()
                .direction(GitHubDirection.ASC)
                .sort(GitHubRepositoryAdvisoriesSort.CREATED)
                .state(GitHubRepositoryAdvisoriesFilterState.DRAFT)
                .build();

        new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(TestClients.caller(transport))
                .listOrgRepositoryAdvisories(ORG, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("state", "draft");
            softly.assertThat(requestUri).hasParameter("sort", "created");
            softly.assertThat(requestUri).hasParameter("direction", "asc");
        });
    }

    @Test
    void listRepositoryAdvisories_shouldReturnAdvisories_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-advisory.json") + "]"));

        final GitHubPage<GitHubRepositoryAdvisory> page = new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(
                        TestClients.caller(transport))
                .listRepositoryAdvisories(OWNER, REPO);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(SecurityAdvisoriesCatalogFixtures.repositoryAdvisory());
            softly.assertThat(page.totalCount()).isNull();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri().getPath()).endsWith("/repos/octocat/hello-world/security-advisories");
        });
    }

    @Test
    void listRepositoryAdvisories_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("repository-advisory.json") + "]"));
        final GitHubListRepositoryAdvisoriesFilter filter = GitHubListRepositoryAdvisoriesFilterBuilder.builder()
                .direction(GitHubDirection.ASC)
                .sort(GitHubRepositoryAdvisoriesSort.CREATED)
                .state(GitHubRepositoryAdvisoriesFilterState.DRAFT)
                .build();

        new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(TestClients.caller(transport))
                .listRepositoryAdvisories(OWNER, REPO, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("state", "draft");
            softly.assertThat(requestUri).hasParameter("sort", "created");
            softly.assertThat(requestUri).hasParameter("direction", "asc");
        });
    }

    @Test
    void getRepositoryAdvisory_shouldReturnAdvisory_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repository-advisory.json")));

        final GitHubRepositoryAdvisory advisory = new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(
                        TestClients.caller(transport))
                .getRepositoryAdvisory(OWNER, REPO, REPOSITORY_GHSA_ID)
                .orElseThrow();
        final URI requestUri = captureSingleRequest().uri();
        final GitHubRepositoryAdvisory expected = SecurityAdvisoriesCatalogFixtures.repositoryAdvisory();

        assertSoftly(softly -> {
            softly.assertThat(advisory).isEqualTo(expected);
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/security-advisories/"
                            + REPOSITORY_GHSA_ID);
        });
    }

    @Test
    void getRepositoryAdvisory_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubRepositoryAdvisory> advisory = new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(
                        TestClients.caller(transport))
                .getRepositoryAdvisory(OWNER, REPO, REPOSITORY_GHSA_ID);

        assertThat(advisory).isEmpty();
    }

    @Test
    void createRepositoryAdvisory_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("repository-advisory.json")));
        final GitHubCreateRepositoryAdvisoryPayload payload =
                SecurityAdvisoriesCatalogFixtures.createRepositoryAdvisoryPayload();

        final GitHubRepositoryAdvisory advisory = new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(
                        TestClients.caller(transport))
                .createRepositoryAdvisory(OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();
        final GitHubRepositoryAdvisory expected = SecurityAdvisoriesCatalogFixtures.repositoryAdvisory();

        assertSoftly(softly -> {
            softly.assertThat(advisory).isEqualTo(expected);
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/security-advisories");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void createPrivateVulnerabilityReport_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("repository-advisory.json")));
        final GitHubCreatePrivateVulnerabilityReportPayload payload =
                SecurityAdvisoriesCatalogFixtures.createPrivateVulnerabilityReportPayload();

        final GitHubRepositoryAdvisory advisory = new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(
                        TestClients.caller(transport))
                .createPrivateVulnerabilityReport(OWNER, REPO, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(advisory).isEqualTo(SecurityAdvisoriesCatalogFixtures.repositoryAdvisory());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/security-advisories/reports");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void updateRepositoryAdvisory_shouldPatchPayload_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("repository-advisory.json")));
        final GitHubUpdateRepositoryAdvisoryPayload payload =
                SecurityAdvisoriesCatalogFixtures.updateRepositoryAdvisoryPayload();

        final GitHubRepositoryAdvisory advisory = new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(
                        TestClients.caller(transport))
                .updateRepositoryAdvisory(OWNER, REPO, REPOSITORY_GHSA_ID, payload);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(advisory).isEqualTo(SecurityAdvisoriesCatalogFixtures.repositoryAdvisory());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/security-advisories/"
                            + REPOSITORY_GHSA_ID);
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void createRepositoryAdvisoryCveRequest_shouldPostWithoutBody_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(TestClients.caller(transport))
                .createRepositoryAdvisoryCveRequest(OWNER, REPO, REPOSITORY_GHSA_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.body().isAbsent()).isTrue();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/security-advisories/"
                            + REPOSITORY_GHSA_ID
                            + "/cve");
        });
    }

    @Test
    void createFork_shouldReturnRepository_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.ACCEPTED.code(),
                        FileUtils.readString(GitHubFullRepository.class, "full-repository.json")));

        final GitHubFullRepository repository = new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(
                        TestClients.caller(transport))
                .createFork(OWNER, REPO, REPOSITORY_GHSA_ID);
        final HttpRequest request = captureSingleRequest();
        final GitHubFullRepository expected = RepositoriesCatalogFixtures.fullRepository();

        assertSoftly(softly -> {
            softly.assertThat(repository).isEqualTo(expected);
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.body().isAbsent()).isTrue();
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/security-advisories/"
                            + REPOSITORY_GHSA_ID
                            + "/forks");
        });
    }

    @ParameterizedTest
    @MethodSource("nullPayloadCalls")
    void validation_shouldThrow_whenPayloadNull(final RepositoryAdvisoriesCall call) {
        final GitHubSecurityAdvisoriesRepositoryAdvisoriesApi api =
                new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @ParameterizedTest
    @MethodSource({"blankArgCalls", "unsupportedPaginationRequests"})
    void call_shouldThrowIllegalArgument_whenInvalid(
            final RepositoryAdvisoriesCall call, final String messageFragment) {
        final GitHubSecurityAdvisoriesRepositoryAdvisoriesApi api =
                new GitHubSecurityAdvisoriesRepositoryAdvisoriesApi(TestClients.caller(transport));

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubSecurityAdvisoriesRepositoryAdvisoriesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<Arguments> blankArgCalls() {
        final GitHubCreateRepositoryAdvisoryPayload createPayload =
                SecurityAdvisoriesCatalogFixtures.createRepositoryAdvisoryPayload();
        final GitHubCreatePrivateVulnerabilityReportPayload reportPayload =
                SecurityAdvisoriesCatalogFixtures.createPrivateVulnerabilityReportPayload();
        final GitHubUpdateRepositoryAdvisoryPayload updatePayload =
                SecurityAdvisoriesCatalogFixtures.updateRepositoryAdvisoryPayload();
        final GitHubListRepositoryAdvisoriesFilter filter = GitHubListRepositoryAdvisoriesFilter.empty();
        return Stream.of(
                Arguments.of((RepositoryAdvisoriesCall) api -> api.listOrgRepositoryAdvisories(" "), "org"),
                Arguments.of((RepositoryAdvisoriesCall) api -> api.listOrgRepositoryAdvisories(" ", filter), "org"),
                Arguments.of(
                        (RepositoryAdvisoriesCall)
                                api -> api.listOrgRepositoryAdvisories(" ", filter, GitHubPageRequest.byCursor()),
                        "org"),
                Arguments.of((RepositoryAdvisoriesCall) api -> api.listRepositoryAdvisories(" ", REPO), "owner"),
                Arguments.of(
                        (RepositoryAdvisoriesCall) api -> api.listRepositoryAdvisories(" ", REPO, filter), "owner"),
                Arguments.of(
                        (RepositoryAdvisoriesCall)
                                api -> api.listRepositoryAdvisories(" ", REPO, filter, GitHubPageRequest.byCursor()),
                        "owner"),
                Arguments.of(
                        (RepositoryAdvisoriesCall) api -> api.getRepositoryAdvisory(" ", REPO, REPOSITORY_GHSA_ID),
                        "owner"),
                Arguments.of(
                        (RepositoryAdvisoriesCall) api -> api.createRepositoryAdvisory(" ", REPO, createPayload),
                        "owner"),
                Arguments.of(
                        (RepositoryAdvisoriesCall)
                                api -> api.createPrivateVulnerabilityReport(" ", REPO, reportPayload),
                        "owner"),
                Arguments.of(
                        (RepositoryAdvisoriesCall)
                                api -> api.updateRepositoryAdvisory(" ", REPO, REPOSITORY_GHSA_ID, updatePayload),
                        "owner"),
                Arguments.of(
                        (RepositoryAdvisoriesCall)
                                api -> api.createRepositoryAdvisoryCveRequest(" ", REPO, REPOSITORY_GHSA_ID),
                        "owner"),
                Arguments.of((RepositoryAdvisoriesCall) api -> api.createFork(" ", REPO, REPOSITORY_GHSA_ID), "owner"),
                Arguments.of((RepositoryAdvisoriesCall) api -> api.listRepositoryAdvisories(OWNER, " "), "repo"),
                Arguments.of(
                        (RepositoryAdvisoriesCall) api -> api.listRepositoryAdvisories(OWNER, " ", filter), "repo"),
                Arguments.of(
                        (RepositoryAdvisoriesCall)
                                api -> api.listRepositoryAdvisories(OWNER, " ", filter, GitHubPageRequest.byCursor()),
                        "repo"),
                Arguments.of(
                        (RepositoryAdvisoriesCall) api -> api.getRepositoryAdvisory(OWNER, " ", REPOSITORY_GHSA_ID),
                        "repo"),
                Arguments.of(
                        (RepositoryAdvisoriesCall) api -> api.createRepositoryAdvisory(OWNER, " ", createPayload),
                        "repo"),
                Arguments.of(
                        (RepositoryAdvisoriesCall)
                                api -> api.createPrivateVulnerabilityReport(OWNER, " ", reportPayload),
                        "repo"),
                Arguments.of(
                        (RepositoryAdvisoriesCall)
                                api -> api.updateRepositoryAdvisory(OWNER, " ", REPOSITORY_GHSA_ID, updatePayload),
                        "repo"),
                Arguments.of(
                        (RepositoryAdvisoriesCall)
                                api -> api.createRepositoryAdvisoryCveRequest(OWNER, " ", REPOSITORY_GHSA_ID),
                        "repo"),
                Arguments.of((RepositoryAdvisoriesCall) api -> api.createFork(OWNER, " ", REPOSITORY_GHSA_ID), "repo"),
                Arguments.of(
                        (RepositoryAdvisoriesCall) api -> api.getRepositoryAdvisory(OWNER, REPO, " "), "{ghsa_id}"),
                Arguments.of(
                        (RepositoryAdvisoriesCall) api -> api.updateRepositoryAdvisory(OWNER, REPO, " ", updatePayload),
                        "{ghsa_id}"),
                Arguments.of(
                        (RepositoryAdvisoriesCall) api -> api.createRepositoryAdvisoryCveRequest(OWNER, REPO, " "),
                        "{ghsa_id}"),
                Arguments.of((RepositoryAdvisoriesCall) api -> api.createFork(OWNER, REPO, " "), "{ghsa_id}"));
    }

    private static Stream<Arguments> nullPayloadCalls() {
        return Stream.of(
                Arguments.of((RepositoryAdvisoriesCall) api -> api.createRepositoryAdvisory(OWNER, REPO, null)),
                Arguments.of((RepositoryAdvisoriesCall)
                        api -> api.updateRepositoryAdvisory(OWNER, REPO, REPOSITORY_GHSA_ID, null)),
                Arguments.of(
                        (RepositoryAdvisoriesCall) api -> api.createPrivateVulnerabilityReport(OWNER, REPO, null)));
    }

    private static Stream<Arguments> unsupportedPaginationRequests() {
        final GitHubListRepositoryAdvisoriesFilter repoFilter = GitHubListRepositoryAdvisoriesFilter.empty();
        final GitHubPageRequest unsupportedPage = GitHubPageRequest.byPage();
        final GitHubPageRequest unsupportedSince = GitHubPageRequest.bySince();
        return Stream.of(
                Arguments.of(
                        (RepositoryAdvisoriesCall)
                                api -> api.listOrgRepositoryAdvisories(ORG, repoFilter, unsupportedPage),
                        "unsupported pagination request: page"),
                Arguments.of(
                        (RepositoryAdvisoriesCall)
                                api -> api.listOrgRepositoryAdvisories(ORG, repoFilter, unsupportedSince),
                        "unsupported pagination request: since"),
                Arguments.of(
                        (RepositoryAdvisoriesCall)
                                api -> api.listRepositoryAdvisories(OWNER, REPO, repoFilter, unsupportedPage),
                        "unsupported pagination request: page"),
                Arguments.of(
                        (RepositoryAdvisoriesCall)
                                api -> api.listRepositoryAdvisories(OWNER, REPO, repoFilter, unsupportedSince),
                        "unsupported pagination request: since"));
    }

    @FunctionalInterface
    interface RepositoryAdvisoriesCall {
        void execute(GitHubSecurityAdvisoriesRepositoryAdvisoriesApi api) throws Exception;
    }
}
