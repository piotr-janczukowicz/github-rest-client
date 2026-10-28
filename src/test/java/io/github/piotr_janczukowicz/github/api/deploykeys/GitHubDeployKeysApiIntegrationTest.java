package io.github.piotr_janczukowicz.github.api.deploykeys;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpHeaderNames.LINK;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.deployKeysApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.linkNext;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubDeployKeysApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int KEY_ID = 1;
    private static final String COLLECTION_PATH = "/repos/octocat/Hello-World/keys";
    private static final String ITEM_PATH = "/repos/octocat/Hello-World/keys/1";

    private static final JsonType<List<GitHubDeployKey>> DEPLOY_KEYS_TYPE = JsonTypes.listOf(GitHubDeployKey.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listDeployKeys_shouldReturnDeployKeys_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(listJsonBytes(GitHubDeployKeysApiIntegrationTest.class, "deploy-key.json")));

        final GitHubPage<GitHubDeployKey> page;
        try (final GitHubApiWireMockSupport.DeployKeysApiClient api = deployKeysApi(HTTP)) {
            page = api.api().listDeployKeys(OWNER, REPO);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedKeys());
        HTTP.verifyRequested(GET, COLLECTION_PATH);
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "page", "1");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "per_page", "100");
    }

    @Test
    void listDeployKeys_shouldFollowLinkNext_whenNextPagePresent() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(listJsonBytes(GitHubDeployKeysApiIntegrationTest.class, "deploy-key.json"))
                        .header(LINK, linkNext(HTTP.uri(COLLECTION_PATH + "?page=2&" + TestClients.perPageQuery()))),
                okJson(listJsonBytes(GitHubDeployKeysApiIntegrationTest.class, "deploy-key.json")));

        final GitHubPage<GitHubDeployKey> first;
        final Optional<GitHubPage<GitHubDeployKey>> second;
        final Optional<GitHubPage<GitHubDeployKey>> third;
        try (final GitHubApiWireMockSupport.DeployKeysApiClient api = deployKeysApi(HTTP)) {
            first = api.api().listDeployKeys(OWNER, REPO);
            second = first.next();
            third = second.isPresent() ? second.get().next() : Optional.empty();
        }

        assertSoftly(softly -> {
            softly.assertThat(first.items()).containsExactlyElementsOf(expectedKeys());
            softly.assertThat(second).isPresent();
            softly.assertThat(second.orElseThrow().items()).containsExactlyElementsOf(expectedKeys());
            softly.assertThat(third).isEmpty();
        });
        HTTP.verifyRequested(GET, COLLECTION_PATH, 2);
    }

    @Test
    void listDeployKeys_shouldSendPageAndPerPage_whenCustomGitHubPageNumberRequest() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(listJsonBytes(GitHubDeployKeysApiIntegrationTest.class, "deploy-key.json")));

        try (final GitHubApiWireMockSupport.DeployKeysApiClient api = deployKeysApi(HTTP)) {
            api.api().listDeployKeys(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));
        }

        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "page", "2");
        HTTP.verifyQueryParam(GET, COLLECTION_PATH, "per_page", "10");
    }

    @Test
    void createDeployKey_shouldReturnDeployKey_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                COLLECTION_PATH,
                createdJson(jsonBytes(GitHubDeployKeysApiIntegrationTest.class, "deploy-key.json")));
        final GitHubCreateDeployKeyPayload payload = DeployKeysCatalogFixtures.createDeployKeyPayload();

        final GitHubDeployKey deployKey;
        try (final GitHubApiWireMockSupport.DeployKeysApiClient api = deployKeysApi(HTTP)) {
            deployKey = api.api().createDeployKey(OWNER, REPO, payload);
        }

        final GitHubDeployKey expected = CODEC.decode(
                jsonBytes(GitHubDeployKeysApiIntegrationTest.class, "deploy-key.json"), GitHubDeployKey.class);

        assertThat(deployKey).isEqualTo(expected);
        HTTP.verifyRequested(POST, COLLECTION_PATH);
        HTTP.verifyHeader(POST, COLLECTION_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, COLLECTION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getDeployKey_shouldReturnDeployKey_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ITEM_PATH, okJson(jsonBytes(GitHubDeployKeysApiIntegrationTest.class, "deploy-key.json")));

        final Optional<GitHubDeployKey> deployKey;
        try (final GitHubApiWireMockSupport.DeployKeysApiClient api = deployKeysApi(HTTP)) {
            deployKey = api.api().getDeployKey(OWNER, REPO, KEY_ID);
        }

        final GitHubDeployKey expected = CODEC.decode(
                jsonBytes(GitHubDeployKeysApiIntegrationTest.class, "deploy-key.json"), GitHubDeployKey.class);

        assertThat(deployKey).contains(expected);
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void getDeployKey_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ITEM_PATH, notFound());

        final Optional<GitHubDeployKey> deployKey;
        try (final GitHubApiWireMockSupport.DeployKeysApiClient api = deployKeysApi(HTTP)) {
            deployKey = api.api().getDeployKey(OWNER, REPO, KEY_ID);
        }

        assertThat(deployKey).isEmpty();
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void deleteDeployKey_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.DeployKeysApiClient api = deployKeysApi(HTTP)) {
            api.api().deleteDeployKey(OWNER, REPO, KEY_ID);
        }

        HTTP.verifyRequested(DELETE, ITEM_PATH);
    }

    private static List<GitHubDeployKey> expectedKeys() {
        return CODEC.decode(
                listJsonBytes(GitHubDeployKeysApiIntegrationTest.class, "deploy-key.json"), DEPLOY_KEYS_TYPE);
    }
}
