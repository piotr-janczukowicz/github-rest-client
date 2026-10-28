package io.github.piotr_janczukowicz.github.api.projects;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.projectsApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
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
class GitHubProjectsFieldsApiIntegrationTest {

    private static final String ORG = "octocat";
    private static final String USERNAME = "monalisa";
    private static final long PROJECT_NUMBER = 1L;
    private static final long FIELD_ID = 123L;
    private static final String ORG_COLLECTION_PATH = "/orgs/octocat/projectsV2/1/fields";
    private static final String ORG_ITEM_PATH = "/orgs/octocat/projectsV2/1/fields/123";
    private static final String USER_COLLECTION_PATH = "/users/monalisa/projectsV2/1/fields";
    private static final String USER_ITEM_PATH = "/users/monalisa/projectsV2/1/fields/123";

    private static final JsonType<List<GitHubProjectField>> FIELDS_TYPE = JsonTypes.listOf(GitHubProjectField.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listForOrg_shouldReturnFields_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ORG_COLLECTION_PATH,
                okJson(listJsonBytes(GitHubProjectsFieldsApiIntegrationTest.class, "field-text.json")));

        final GitHubPage<GitHubProjectField> page;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            page = api.api().fields().listForOrg(ORG, PROJECT_NUMBER);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedFields());
        HTTP.verifyRequested(GET, ORG_COLLECTION_PATH);
        HTTP.verifyQueryParam(GET, ORG_COLLECTION_PATH, "per_page", "100");
        HTTP.verifyNoQueryParam(GET, ORG_COLLECTION_PATH, "page");
    }

    @Test
    void addForOrg_shouldReturnField_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                ORG_COLLECTION_PATH,
                createdJson(jsonBytes(GitHubProjectsFieldsApiIntegrationTest.class, "field-text.json")));
        final GitHubAddProjectFieldPayload payload =
                GitHubAddProjectFieldPayload.basic("Team notes", GitHubProjectFieldDataType.TEXT);

        final GitHubProjectField field;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            field = api.api().fields().addForOrg(ORG, PROJECT_NUMBER, payload);
        }

        assertThat(field).isEqualTo(expectedField());
        HTTP.verifyRequested(POST, ORG_COLLECTION_PATH);
        HTTP.verifyHeader(POST, ORG_COLLECTION_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, ORG_COLLECTION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getForOrg_shouldReturnField_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, ORG_ITEM_PATH, okJson(jsonBytes(GitHubProjectsFieldsApiIntegrationTest.class, "field-text.json")));

        final Optional<GitHubProjectField> field;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            field = api.api().fields().getForOrg(ORG, PROJECT_NUMBER, FIELD_ID);
        }

        assertThat(field).contains(expectedField());
        HTTP.verifyRequested(GET, ORG_ITEM_PATH);
    }

    @Test
    void getForOrg_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ORG_ITEM_PATH, notFound());

        final Optional<GitHubProjectField> field;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            field = api.api().fields().getForOrg(ORG, PROJECT_NUMBER, FIELD_ID);
        }

        assertThat(field).isEmpty();
        HTTP.verifyRequested(GET, ORG_ITEM_PATH);
    }

    @Test
    void listForUser_shouldReturnFields_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_COLLECTION_PATH,
                okJson(listJsonBytes(GitHubProjectsFieldsApiIntegrationTest.class, "field-text.json")));

        final GitHubPage<GitHubProjectField> page;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            page = api.api().fields().listForUser(USERNAME, PROJECT_NUMBER);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedFields());
        HTTP.verifyRequested(GET, USER_COLLECTION_PATH);
        HTTP.verifyNoQueryParam(GET, USER_COLLECTION_PATH, "page");
    }

    @Test
    void addForUser_shouldReturnField_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                USER_COLLECTION_PATH,
                createdJson(jsonBytes(GitHubProjectsFieldsApiIntegrationTest.class, "field-text.json")));
        final GitHubAddFieldForUserPayload payload =
                GitHubAddFieldForUserPayload.basic("Team notes", GitHubProjectFieldDataType.TEXT);

        final GitHubProjectField field;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            field = api.api().fields().addForUser(USERNAME, PROJECT_NUMBER, payload);
        }

        assertThat(field).isEqualTo(expectedField());
        HTTP.verifyRequested(POST, USER_COLLECTION_PATH);
        HTTP.verifyBody(POST, USER_COLLECTION_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void getForUser_shouldReturnField_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_ITEM_PATH,
                okJson(jsonBytes(GitHubProjectsFieldsApiIntegrationTest.class, "field-text.json")));

        final Optional<GitHubProjectField> field;
        try (final GitHubApiWireMockSupport.ProjectsApiClient api = projectsApi(HTTP)) {
            field = api.api().fields().getForUser(USERNAME, PROJECT_NUMBER, FIELD_ID);
        }

        assertThat(field).contains(expectedField());
        HTTP.verifyRequested(GET, USER_ITEM_PATH);
    }

    private static List<GitHubProjectField> expectedFields() {
        return CODEC.decode(
                listJsonBytes(GitHubProjectsFieldsApiIntegrationTest.class, "field-text.json"), FIELDS_TYPE);
    }

    private static GitHubProjectField expectedField() {
        return CODEC.decode(
                jsonBytes(GitHubProjectsFieldsApiIntegrationTest.class, "field-text.json"), GitHubProjectField.class);
    }
}
