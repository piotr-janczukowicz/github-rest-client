package io.github.piotr_janczukowicz.github.api.licenses;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubLicensesApiTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final String LICENSE = "mit";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubLicensesApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listCommonlyUsed_shouldGetLicenses_whenDefaultOverload() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("license-simple.json") + "]"));

        final GitHubPage<GitHubLicenseSimple> page = api().listCommonlyUsed();
        final URI requestUri = captureSingleRequest().uri();
        final List<GitHubLicenseSimple> expected = List.of(LicensesCatalogFixtures.licenseSimple());

        assertSoftly(softly -> {
            softly.assertThat(page.items()).isEqualTo(expected);
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/licenses?page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listCommonlyUsed_shouldGetLicenses_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("license-simple-items.json")));

        api().listCommonlyUsed(GitHubPageNumberRequest.of(2, 10));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("page", "2");
            softly.assertThat(requestUri).hasParameter("per_page", "10");
            softly.assertThat(requestUri.getQuery()).doesNotContain("featured");
        });
    }

    @Test
    void listCommonlyUsed_shouldSendFeaturedQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("license-simple-items.json")));
        final GitHubGetAllCommonlyUsedFilter filter =
                GitHubGetAllCommonlyUsedFilterBuilder.builder().featured(true).build();

        api().listCommonlyUsed(filter);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("featured", "true");
            softly.assertThat(requestUri).hasParameter("page", "1");
            softly.assertThat(requestUri).hasParameter("per_page", "100");
        });
    }

    @Test
    void listCommonlyUsed_shouldSendFeaturedAndPageQuery_whenFilterAndRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("license-simple-items.json")));
        final GitHubGetAllCommonlyUsedFilter filter =
                GitHubGetAllCommonlyUsedFilterBuilder.builder().featured(true).build();
        final GitHubPageRequest request = GitHubPageNumberRequest.of(2, 10);

        api().listCommonlyUsed(filter, request);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(requestUri).hasParameter("featured", "true");
            softly.assertThat(requestUri).hasParameter("page", "2");
            softly.assertThat(requestUri).hasParameter("per_page", "10");
        });
    }

    @Test
    void listCommonlyUsed_shouldThrow_whenFilterNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubLicensesApi licensesApi = new GitHubLicensesApi(TestClients.caller(client));

            final Throwable thrown =
                    catchThrowable(() -> licensesApi.listCommonlyUsed((GitHubGetAllCommonlyUsedFilter) null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
        }
    }

    @Test
    void listCommonlyUsed_shouldThrow_whenRequestNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubLicensesApi licensesApi = new GitHubLicensesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> licensesApi.listCommonlyUsed((GitHubPageRequest) null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
        }
    }

    @Test
    void listCommonlyUsed_shouldThrow_whenFilterNullAndRequestProvided() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubLicensesApi licensesApi = new GitHubLicensesApi(TestClients.caller(client));

            final Throwable thrown =
                    catchThrowable(() -> licensesApi.listCommonlyUsed(null, GitHubPageRequest.byPage()));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
        }
    }

    @Test
    void listCommonlyUsed_shouldThrow_whenRequestNullAndFilterProvided() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubLicensesApi licensesApi = new GitHubLicensesApi(TestClients.caller(client));

            final Throwable thrown =
                    catchThrowable(() -> licensesApi.listCommonlyUsed(GitHubGetAllCommonlyUsedFilter.empty(), null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
        }
    }

    @Test
    void get_shouldReturnLicense_whenPresent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("license.json")));

        final Optional<GitHubLicense> license = api().get(LICENSE);
        final URI requestUri = captureSingleRequest().uri();
        final GitHubLicense expected = LicensesCatalogFixtures.license();

        assertSoftly(softly -> {
            softly.assertThat(license).contains(expected);
            softly.assertThat(requestUri).hasToString("https://api.github.com/licenses/mit");
        });
    }

    @Test
    void get_shouldEncodeLicensePath_whenLicenseContainsReservedCharacters() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("license.json")));

        api().get("a b/c");
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasToString("https://api.github.com/licenses/a%20b%2Fc");
    }

    @Test
    void get_shouldReturnEmpty_whenNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubLicense> license = api().get(LICENSE);

        assertThat(license).isEmpty();
    }

    @Test
    void get_shouldThrow_whenLicenseNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubLicensesApi licensesApi = new GitHubLicensesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> licensesApi.get(null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("license");
        }
    }

    @Test
    void get_shouldThrow_whenLicenseBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubLicensesApi licensesApi = new GitHubLicensesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> licensesApi.get(" "));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("license");
        }
    }

    @Test
    void getForRepo_shouldReturnLicenseContent_whenPresent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("license-content.json")));

        final Optional<GitHubLicenseContent> content = api().getForRepo(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();
        final GitHubLicenseContent expected = LicensesCatalogFixtures.licenseContent();

        assertSoftly(softly -> {
            softly.assertThat(content).contains(expected);
            softly.assertThat(requestUri).hasToString("https://api.github.com/repos/octocat/hello-world/license");
        });
    }

    @Test
    void getForRepo_shouldSendRefQuery_whenFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("license-content.json")));
        final GitHubGetForRepoFilter filter =
                GitHubGetForRepoFilterBuilder.builder().ref("main").build();

        api().getForRepo(OWNER, REPO, filter);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("ref", "main");
    }

    @Test
    void getForRepo_shouldReturnEmpty_whenNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubLicenseContent> content = api().getForRepo(OWNER, REPO);

        assertThat(content).isEmpty();
    }

    @Test
    void getForRepo_shouldThrow_whenOwnerNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubLicensesApi licensesApi = new GitHubLicensesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> licensesApi.getForRepo(null, REPO));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("owner");
        }
    }

    @Test
    void getForRepo_shouldThrow_whenOwnerBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubLicensesApi licensesApi = new GitHubLicensesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> licensesApi.getForRepo(" ", REPO));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("owner");
        }
    }

    @Test
    void getForRepo_shouldThrow_whenRepoNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubLicensesApi licensesApi = new GitHubLicensesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> licensesApi.getForRepo(OWNER, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repo");
        }
    }

    @Test
    void getForRepo_shouldThrow_whenRepoBlank() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubLicensesApi licensesApi = new GitHubLicensesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> licensesApi.getForRepo(OWNER, " "));

            assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("repo");
        }
    }

    @Test
    void getForRepo_shouldThrow_whenFilterNull() {
        try (GitHubClient client = TestClients.client(transport)) {
            final GitHubLicensesApi licensesApi = new GitHubLicensesApi(TestClients.caller(client));

            final Throwable thrown = catchThrowable(() -> licensesApi.getForRepo(OWNER, REPO, null));

            assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("filter");
        }
    }

    private GitHubLicensesApi api() {
        return new GitHubLicensesApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubLicensesApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
