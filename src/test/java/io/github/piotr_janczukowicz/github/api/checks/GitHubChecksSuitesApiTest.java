package io.github.piotr_janczukowicz.github.api.checks;

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
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubChecksSuitesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int CHECK_SUITE_ID = 5;
    private static final String SUITES_PATH = "https://api.github.com/repos/octocat/Hello-World/check-suites";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubChecksSuitesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void createSuite_shouldReturnCheckSuite_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), json("check-suite.json")));

        final GitHubCheckSuite suite = api().createSuite(OWNER, REPO, ChecksCatalogFixtures.createSuitePayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(suite).isEqualTo(ChecksCatalogFixtures.checkSuite());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString(SUITES_PATH);
        });
    }

    @Test
    void updateSuitesPreferences_shouldReturnPreferences_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("check-suite-preference.json")));

        final GitHubCheckSuitePreference preference =
                api().updateSuitesPreferences(OWNER, REPO, ChecksCatalogFixtures.setSuitesPreferencesPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(preference).isEqualTo(ChecksCatalogFixtures.checkSuitePreference());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri()).hasToString(SUITES_PATH + "/preferences");
        });
    }

    @Test
    void getSuite_shouldReturnCheckSuite_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("check-suite.json")));

        final GitHubCheckSuite suite =
                api().getSuite(OWNER, REPO, CHECK_SUITE_ID).orElseThrow();
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(suite).isEqualTo(ChecksCatalogFixtures.checkSuite());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(request.uri()).hasToString(SUITES_PATH + "/5");
        });
    }

    @Test
    void getSuite_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCheckSuite> result = api().getSuite(OWNER, REPO, CHECK_SUITE_ID);

        assertThat(result).isEmpty();
    }

    @Test
    void rerequestSuite_shouldSendPost_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), ""));

        api().rerequestSuite(OWNER, REPO, CHECK_SUITE_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri()).hasToString(SUITES_PATH + "/5/rerequest");
        });
    }

    @Test
    void listSuitesForRef_shouldReturnCheckSuites_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("check-suites-page.json")));

        final GitHubPage<GitHubCheckSuite> page = api().listSuitesForRef(OWNER, REPO, "feature/x");
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(ChecksCatalogFixtures.checkSuite());
            softly.assertThat(page.totalCount()).isEqualTo(1);
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/repos/octocat/Hello-World/commits/feature/x/check-suites?page=1&"
                                    + TestClients.perPageQuery());
        });
    }

    @Test
    void listSuitesForRef_shouldSendFilterQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("check-suites-page.json")));

        api().listSuitesForRef(
                        OWNER,
                        REPO,
                        "main",
                        ChecksCatalogFixtures.listSuitesForRefFilter(),
                        GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("app_id", "1");
            softly.assertThat(requestUri).hasParameter("check_name", "mighty_readme");
        });
    }

    @Test
    void getSuite_shouldPutLongIdInPath_whenIdExceedsIntRange() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("check-suite.json")));

        api().getSuite(OWNER, REPO, 3_000_000_000L);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasToString(SUITES_PATH + "/3000000000");
    }

    private GitHubChecksSuitesApi api() {
        return new GitHubChecksSuitesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubChecksSuitesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
