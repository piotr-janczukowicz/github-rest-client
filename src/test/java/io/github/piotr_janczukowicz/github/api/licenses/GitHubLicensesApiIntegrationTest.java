package io.github.piotr_janczukowicz.github.api.licenses;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.licensesApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubLicensesApiIntegrationTest {

    private static final String OWNER = "o";
    private static final String REPO = "r";
    private static final String LICENSE = "mit";

    private static final String LICENSES_PATH = "/licenses";
    private static final String LICENSE_PATH = "/licenses/mit";
    private static final String REPO_LICENSE_PATH = "/repos/o/r/license";

    private static final JsonType<List<GitHubLicenseSimple>> LICENSES_TYPE =
            JsonTypes.listOf(GitHubLicenseSimple.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listCommonlyUsed_shouldReturnLicenses_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                LICENSES_PATH,
                okJson(jsonBytes(GitHubLicensesApiIntegrationTest.class, "license-simple-items.json")));

        final GitHubPage<GitHubLicenseSimple> page;
        try (final GitHubApiWireMockSupport.LicensesApiClient licenses = licensesApi(HTTP)) {
            page = licenses.api().listCommonlyUsed();
        }

        final List<GitHubLicenseSimple> expected = CODEC.decode(
                jsonBytes(GitHubLicensesApiIntegrationTest.class, "license-simple-items.json"), LICENSES_TYPE);

        assertThat(page.items()).isEqualTo(expected);
        HTTP.verifyRequested(GET, LICENSES_PATH);
        HTTP.verifyQueryParam(GET, LICENSES_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, LICENSES_PATH, "per_page", "100");
    }

    @Test
    void listCommonlyUsed_shouldSendFeaturedQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                LICENSES_PATH,
                okJson(jsonBytes(GitHubLicensesApiIntegrationTest.class, "license-simple-items.json")));

        try (final GitHubApiWireMockSupport.LicensesApiClient licenses = licensesApi(HTTP)) {
            licenses.api()
                    .listCommonlyUsed(
                            GitHubGetAllCommonlyUsedFilterBuilder.builder()
                                    .featured(true)
                                    .build(),
                            GitHubPageRequest.byPage());
        }

        HTTP.verifyRequested(GET, LICENSES_PATH);
        HTTP.verifyQueryParam(GET, LICENSES_PATH, "featured", "true");
        HTTP.verifyQueryParam(GET, LICENSES_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, LICENSES_PATH, "per_page", "100");
    }

    @Test
    void get_shouldReturnLicense_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, LICENSE_PATH, okJson(jsonBytes(GitHubLicensesApiIntegrationTest.class, "license.json")));

        final Optional<GitHubLicense> license;
        try (final GitHubApiWireMockSupport.LicensesApiClient licenses = licensesApi(HTTP)) {
            license = licenses.api().get(LICENSE);
        }

        final GitHubLicense expected =
                CODEC.decode(jsonBytes(GitHubLicensesApiIntegrationTest.class, "license.json"), GitHubLicense.class);

        assertThat(license).contains(expected);
        HTTP.verifyRequested(GET, LICENSE_PATH);
    }

    @Test
    void get_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, LICENSE_PATH, notFound());

        final Optional<GitHubLicense> license;
        try (final GitHubApiWireMockSupport.LicensesApiClient licenses = licensesApi(HTTP)) {
            license = licenses.api().get(LICENSE);
        }

        assertThat(license).isEmpty();
    }

    @Test
    void getForRepo_shouldReturnLicenseContent_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                REPO_LICENSE_PATH,
                okJson(jsonBytes(GitHubLicensesApiIntegrationTest.class, "license-content.json")));

        final Optional<GitHubLicenseContent> content;
        try (final GitHubApiWireMockSupport.LicensesApiClient licenses = licensesApi(HTTP)) {
            content = licenses.api().getForRepo(OWNER, REPO);
        }

        final GitHubLicenseContent expected = CODEC.decode(
                jsonBytes(GitHubLicensesApiIntegrationTest.class, "license-content.json"), GitHubLicenseContent.class);

        assertThat(content).contains(expected);
        HTTP.verifyRequested(GET, REPO_LICENSE_PATH);
    }

    @Test
    void getForRepo_shouldSendRefQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                REPO_LICENSE_PATH,
                okJson(jsonBytes(GitHubLicensesApiIntegrationTest.class, "license-content.json")));

        try (final GitHubApiWireMockSupport.LicensesApiClient licenses = licensesApi(HTTP)) {
            licenses.api()
                    .getForRepo(
                            OWNER,
                            REPO,
                            GitHubGetForRepoFilterBuilder.builder().ref("main").build());
        }

        HTTP.verifyRequested(GET, REPO_LICENSE_PATH);
        HTTP.verifyQueryParam(GET, REPO_LICENSE_PATH, "ref", "main");
    }

    @Test
    void getForRepo_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, REPO_LICENSE_PATH, notFound());

        final Optional<GitHubLicenseContent> content;
        try (final GitHubApiWireMockSupport.LicensesApiClient licenses = licensesApi(HTTP)) {
            content = licenses.api().getForRepo(OWNER, REPO);
        }

        assertThat(content).isEmpty();
    }
}
