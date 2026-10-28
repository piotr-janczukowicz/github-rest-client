package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.projectsApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
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
class GitHubProjectsItemsApiIntegrationTest {

    private static final String ORG = "octocat";
    private static final String USERNAME = "monalisa";
    private static final long PROJECT_NUMBER = 1L;
    private static final long ITEM_ID = 3L;
    private static final long VIEW_NUMBER = 1L;
    private static final String ORG_COLLECTION_PATH = "/orgs/octocat/projectsV2/1/items";
    private static final String ORG_ITEM_PATH = "/orgs/octocat/projectsV2/1/items/3";
    private static final String ORG_VIEW_ITEMS_PATH = "/orgs/octocat/projectsV2/1/views/1/items";
    private static final String USER_COLLECTION_PATH = "/users/monalisa/projectsV2/1/items";
    private static final String USER_ITEM_PATH = "/users/monalisa/projectsV2/1/items/3";
    private static final String USER_VIEW_ITEMS_PATH = "/users/monalisa/projectsV2/1/views/1/items";

    private static final JsonType<List<GitHubProjectItem>> ITEMS_TYPE = JsonTypes.listOf(GitHubProjectItem.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listForOrg_shouldReturnItems_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_COLLECTION_PATH,
                okJson(listJsonBytes(GitHubProjectsItemsApiIntegrationTest.class, "item-with-content.json")));

        final GitHubPage<GitHubProjectItem> page;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            page = api.api().items().listForOrg(ORG, PROJECT_NUMBER);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedItems());
        HTTP.verifyRequested(GET, ORG_COLLECTION_PATH);
        HTTP.verifyQueryParam(GET, ORG_COLLECTION_PATH, "per_page", "100");
        HTTP.verifyNoQueryParam(GET, ORG_COLLECTION_PATH, "page");
    }

    @Test
    void addForOrg_shouldReturnItem_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                ORG_COLLECTION_PATH,
                createdJson(jsonBytes(GitHubProjectsItemsApiIntegrationTest.class, "item-simple.json")));
        final GitHubAddProjectItemPayload payload =
                GitHubAddProjectItemPayload.byId(GitHubProjectItemContentType.ISSUE, 42L);

        final GitHubProjectItemSimple item;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            item = api.api().items().addForOrg(ORG, PROJECT_NUMBER, payload);
        }

        assertThat(item).isEqualTo(expectedSimpleItem());
        HTTP.verifyRequested(POST, ORG_COLLECTION_PATH);
        HTTP.verifyBody(POST, ORG_COLLECTION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getForOrg_shouldReturnItem_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_ITEM_PATH,
                okJson(jsonBytes(GitHubProjectsItemsApiIntegrationTest.class, "item-with-content.json")));

        final Optional<GitHubProjectItem> item;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            item = api.api().items().getForOrg(ORG, PROJECT_NUMBER, ITEM_ID);
        }

        assertThat(item).contains(expectedItem());
        HTTP.verifyRequested(GET, ORG_ITEM_PATH);
    }

    @Test
    void getForOrg_shouldSendFieldsQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                ORG_ITEM_PATH,
                okJson(jsonBytes(GitHubProjectsItemsApiIntegrationTest.class, "item-with-content.json")));
        final GitHubListProjectViewItemsFilter filter = GitHubListProjectViewItemsFilterBuilder.builder()
                .fields(List.of("123", "456"))
                .build();

        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            api.api().items().getForOrg(ORG, PROJECT_NUMBER, ITEM_ID, filter);
        }

        HTTP.verifyQueryParam(GET, ORG_ITEM_PATH, "fields", "123,456");
    }

    @Test
    void getForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_ITEM_PATH, notFound());

        final Optional<GitHubProjectItem> item;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            item = api.api().items().getForOrg(ORG, PROJECT_NUMBER, ITEM_ID);
        }

        assertThat(item).isEmpty();
    }

    @Test
    void updateForOrg_shouldReturnItem_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                ORG_ITEM_PATH,
                okJson(jsonBytes(GitHubProjectsItemsApiIntegrationTest.class, "item-with-content.json")));
        final GitHubUpdateProjectItemPayload payload = ProjectsCatalogFixtures.updateProjectItemPayload();

        final GitHubProjectItem item;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            item = api.api().items().updateForOrg(ORG, PROJECT_NUMBER, ITEM_ID, payload);
        }

        assertThat(item).isEqualTo(expectedItem());
        HTTP.verifyRequested(PATCH, ORG_ITEM_PATH);
        HTTP.verifyBody(PATCH, ORG_ITEM_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void deleteForOrg_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ORG_ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            api.api().items().deleteForOrg(ORG, PROJECT_NUMBER, ITEM_ID);
        }

        HTTP.verifyRequested(DELETE, ORG_ITEM_PATH);
    }

    @Test
    void listViewItemsForOrg_shouldReturnItems_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_VIEW_ITEMS_PATH,
                okJson(listJsonBytes(GitHubProjectsItemsApiIntegrationTest.class, "item-with-content.json")));
        final GitHubListProjectViewItemsFilter filter = GitHubListProjectViewItemsFilterBuilder.builder()
                .fields(List.of("123"))
                .build();

        final GitHubPage<GitHubProjectItem> page;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            page = api.api()
                    .items()
                    .listViewItemsForOrg(ORG, PROJECT_NUMBER, VIEW_NUMBER, filter, GitHubPageRequest.byCursor());
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedItems());
        HTTP.verifyRequested(GET, ORG_VIEW_ITEMS_PATH);
        HTTP.verifyQueryParam(GET, ORG_VIEW_ITEMS_PATH, "fields", "123");
        HTTP.verifyNoQueryParam(GET, ORG_VIEW_ITEMS_PATH, "page");
    }

    @Test
    void listForUser_shouldReturnItems_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_COLLECTION_PATH,
                okJson(listJsonBytes(GitHubProjectsItemsApiIntegrationTest.class, "item-with-content.json")));

        final GitHubPage<GitHubProjectItem> page;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            page = api.api().items().listForUser(USERNAME, PROJECT_NUMBER);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedItems());
        HTTP.verifyRequested(GET, USER_COLLECTION_PATH);
        HTTP.verifyNoQueryParam(GET, USER_COLLECTION_PATH, "page");
    }

    @Test
    void addForUser_shouldReturnItem_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                USER_COLLECTION_PATH,
                createdJson(jsonBytes(GitHubProjectsItemsApiIntegrationTest.class, "item-simple.json")));
        final GitHubAddProjectItemPayload payload =
                GitHubAddProjectItemPayload.byId(GitHubProjectItemContentType.ISSUE, 42L);

        final GitHubProjectItemSimple item;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            item = api.api().items().addForUser(USERNAME, PROJECT_NUMBER, payload);
        }

        assertThat(item).isEqualTo(expectedSimpleItem());
        HTTP.verifyRequested(POST, USER_COLLECTION_PATH);
    }

    @Test
    void getForUser_shouldReturnItem_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_ITEM_PATH,
                okJson(jsonBytes(GitHubProjectsItemsApiIntegrationTest.class, "item-with-content.json")));

        final Optional<GitHubProjectItem> item;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            item = api.api().items().getForUser(USERNAME, PROJECT_NUMBER, ITEM_ID);
        }

        assertThat(item).contains(expectedItem());
        HTTP.verifyRequested(GET, USER_ITEM_PATH);
    }

    @Test
    void getForUser_shouldSendFieldsQuery_whenFilterProvided() throws Exception {
        HTTP.stub(
                GET,
                USER_ITEM_PATH,
                okJson(jsonBytes(GitHubProjectsItemsApiIntegrationTest.class, "item-with-content.json")));
        final GitHubListProjectViewItemsFilter filter = GitHubListProjectViewItemsFilterBuilder.builder()
                .fields(List.of("123"))
                .build();

        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            api.api().items().getForUser(USERNAME, PROJECT_NUMBER, ITEM_ID, filter);
        }

        HTTP.verifyQueryParam(GET, USER_ITEM_PATH, "fields", "123");
    }

    @Test
    void updateForUser_shouldReturnItem_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                USER_ITEM_PATH,
                okJson(jsonBytes(GitHubProjectsItemsApiIntegrationTest.class, "item-with-content.json")));
        final GitHubUpdateProjectItemPayload payload = ProjectsCatalogFixtures.updateProjectItemPayload();

        final GitHubProjectItem item;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            item = api.api().items().updateForUser(USERNAME, PROJECT_NUMBER, ITEM_ID, payload);
        }

        assertThat(item).isEqualTo(expectedItem());
        HTTP.verifyRequested(PATCH, USER_ITEM_PATH);
    }

    @Test
    void deleteForUser_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_ITEM_PATH, noContent());

        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            api.api().items().deleteForUser(USERNAME, PROJECT_NUMBER, ITEM_ID);
        }

        HTTP.verifyRequested(DELETE, USER_ITEM_PATH);
    }

    @Test
    void listViewItemsForUser_shouldReturnItems_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_VIEW_ITEMS_PATH,
                okJson(listJsonBytes(GitHubProjectsItemsApiIntegrationTest.class, "item-with-content.json")));

        final GitHubPage<GitHubProjectItem> page;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            page = api.api().items().listViewItemsForUser(USERNAME, PROJECT_NUMBER, VIEW_NUMBER);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedItems());
        HTTP.verifyRequested(GET, USER_VIEW_ITEMS_PATH);
        HTTP.verifyNoQueryParam(GET, USER_VIEW_ITEMS_PATH, "page");
    }

    private static List<GitHubProjectItem> expectedItems() {
        return CODEC.decode(
                listJsonBytes(GitHubProjectsItemsApiIntegrationTest.class, "item-with-content.json"), ITEMS_TYPE);
    }

    private static GitHubProjectItem expectedItem() {
        return CODEC.decode(
                jsonBytes(GitHubProjectsItemsApiIntegrationTest.class, "item-with-content.json"),
                GitHubProjectItem.class);
    }

    private static GitHubProjectItemSimple expectedSimpleItem() {
        return CODEC.decode(
                jsonBytes(GitHubProjectsItemsApiIntegrationTest.class, "item-simple.json"),
                GitHubProjectItemSimple.class);
    }
}
