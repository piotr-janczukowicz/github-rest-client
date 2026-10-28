package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.reposCustomPropertiesApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubRepositoriesCustomPropertiesApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String PATH = "/repos/octocat/Hello-World/properties/values";

    private static final JsonType<List<GitHubCustomPropertyValue>> VALUES_TYPE =
            JsonTypes.listOf(GitHubCustomPropertyValue.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getRepositoryValues_shouldReturnValues_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                PATH,
                okJson(jsonBytes(
                        GitHubRepositoriesCustomPropertiesApiIntegrationTest.class, "custom-property-values.json")));

        final List<GitHubCustomPropertyValue> values;
        try (final GitHubApiWireMockSupport.ReposCustomPropertiesApiClient customProperties =
                reposCustomPropertiesApi(HTTP)) {
            values = customProperties.api().getRepositoryValues(OWNER, REPO);
        }

        final List<GitHubCustomPropertyValue> expected = CODEC.decode(
                jsonBytes(GitHubRepositoriesCustomPropertiesApiIntegrationTest.class, "custom-property-values.json"),
                VALUES_TYPE);

        assertThat(values).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, PATH);
    }

    @Test
    void createOrUpdateRepositoryValues_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(PATCH, PATH, noContent());
        final GitHubCustomPropertiesForReposCreateOrUpdateRepositoryValuesPayload payload =
                RepositoriesCatalogFixtures.customPropertiesForReposCreateOrUpdateRepositoryValuesPayload();

        try (final GitHubApiWireMockSupport.ReposCustomPropertiesApiClient customProperties =
                reposCustomPropertiesApi(HTTP)) {
            customProperties.api().createOrUpdateRepositoryValues(OWNER, REPO, payload);
        }

        HTTP.verifyRequested(PATCH, PATH);
        HTTP.verifyHeader(PATCH, PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }
}
