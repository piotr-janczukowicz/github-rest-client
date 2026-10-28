package io.github.piotr_janczukowicz.github.api.repositories;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.reposAutolinksApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubRepositoriesAutolinksApiIntegrationTest {

    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int AUTOLINK_ID = 3;
    private static final String COLLECTION_PATH = "/repos/octocat/Hello-World/autolinks";
    private static final String ITEM_PATH = "/repos/octocat/Hello-World/autolinks/3";

    private static final JsonType<List<GitHubAutolink>> AUTOLINKS_TYPE = JsonTypes.listOf(GitHubAutolink.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listAutolinks_shouldReturnAutolinks_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                COLLECTION_PATH,
                okJson(listJsonBytes(GitHubRepositoriesAutolinksApiIntegrationTest.class, "autolink.json")));

        final List<GitHubAutolink> autolinks;
        try (final GitHubApiWireMockSupport.ReposAutolinksApiClient api = reposAutolinksApi(HTTP)) {
            autolinks = api.api().listAutolinks(OWNER, REPO);
        }

        final List<GitHubAutolink> expected = CODEC.decode(
                listJsonBytes(GitHubRepositoriesAutolinksApiIntegrationTest.class, "autolink.json"), AUTOLINKS_TYPE);

        assertThat(autolinks).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, COLLECTION_PATH);
    }

    @Test
    void createAutolink_shouldReturnAutolink_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                COLLECTION_PATH,
                createdJson(jsonBytes(GitHubRepositoriesAutolinksApiIntegrationTest.class, "autolink.json")));
        final GitHubCreateAutolinkPayload payload = RepositoriesCatalogFixtures.createAutolinkPayload();

        final GitHubAutolink autolink;
        try (final GitHubApiWireMockSupport.ReposAutolinksApiClient api = reposAutolinksApi(HTTP)) {
            autolink = api.api().createAutolink(OWNER, REPO, payload);
        }

        final GitHubAutolink expected = CODEC.decode(
                jsonBytes(GitHubRepositoriesAutolinksApiIntegrationTest.class, "autolink.json"), GitHubAutolink.class);

        assertThat(autolink).isEqualTo(expected);
        HTTP.verifyRequested(POST, COLLECTION_PATH);
        HTTP.verifyHeader(POST, COLLECTION_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, COLLECTION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getAutolink_shouldReturnAutolink_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ITEM_PATH,
                okJson(jsonBytes(GitHubRepositoriesAutolinksApiIntegrationTest.class, "autolink.json")));

        final Optional<GitHubAutolink> autolink;
        try (final GitHubApiWireMockSupport.ReposAutolinksApiClient api = reposAutolinksApi(HTTP)) {
            autolink = api.api().getAutolink(OWNER, REPO, AUTOLINK_ID);
        }

        final GitHubAutolink expected = CODEC.decode(
                jsonBytes(GitHubRepositoriesAutolinksApiIntegrationTest.class, "autolink.json"), GitHubAutolink.class);

        assertThat(autolink).contains(expected);
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void getAutolink_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ITEM_PATH, notFound());

        final Optional<GitHubAutolink> autolink;
        try (final GitHubApiWireMockSupport.ReposAutolinksApiClient api = reposAutolinksApi(HTTP)) {
            autolink = api.api().getAutolink(OWNER, REPO, AUTOLINK_ID);
        }

        assertThat(autolink).isEmpty();
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void deleteAutolink_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.ReposAutolinksApiClient api = reposAutolinksApi(HTTP)) {
            api.api().deleteAutolink(OWNER, REPO, AUTOLINK_ID);
        }

        HTTP.verifyRequested(DELETE, ITEM_PATH);
    }
}
