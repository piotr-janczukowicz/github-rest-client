package io.github.piotr_janczukowicz.github.api.codesofconduct;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.codeOfConductApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubCodeOfConductApiIntegrationTest {

    private static final String KEY = "contributor_covenant";
    private static final String COLLECTION_PATH = "/codes_of_conduct";
    private static final String ITEM_PATH = "/codes_of_conduct/contributor_covenant";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getAllCodesOfConduct_shouldReturnCodesOfConduct_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(listJsonBytes(GitHubCodeOfConductApiIntegrationTest.class, "code-of-conduct.json")));

        final List<GitHubCodeOfConduct> codes;
        try (final GitHubApiWireMockSupport.CodeOfConductApiClient api = codeOfConductApi(HTTP)) {
            codes = api.api().getAllCodesOfConduct();
        }

        assertThat(codes).containsExactly(RepositoriesCatalogFixtures.contributorCovenantWithBody());
        HTTP.verifyRequested(GET, COLLECTION_PATH);
    }

    @Test
    void getConductCode_shouldReturnCodeOfConduct_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, ITEM_PATH, okJson(jsonBytes(GitHubCodeOfConductApiIntegrationTest.class, "code-of-conduct.json")));

        final Optional<GitHubCodeOfConduct> code;
        try (final GitHubApiWireMockSupport.CodeOfConductApiClient api = codeOfConductApi(HTTP)) {
            code = api.api().getConductCode(KEY);
        }

        assertThat(code).contains(RepositoriesCatalogFixtures.contributorCovenantWithBody());
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void getConductCode_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ITEM_PATH, notFound());

        final Optional<GitHubCodeOfConduct> code;
        try (final GitHubApiWireMockSupport.CodeOfConductApiClient api = codeOfConductApi(HTTP)) {
            code = api.api().getConductCode(KEY);
        }

        assertThat(code).isEmpty();
        HTTP.verifyRequested(GET, ITEM_PATH);
    }
}
