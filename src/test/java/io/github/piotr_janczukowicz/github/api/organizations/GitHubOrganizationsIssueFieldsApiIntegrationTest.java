package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.organizationsApi;
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
class GitHubOrganizationsIssueFieldsApiIntegrationTest {

    private static final JsonType<List<GitHubIssueField>> ISSUE_FIELDS_TYPE = JsonTypes.listOf(GitHubIssueField.class);

    private static final String ORG = "github";
    private static final int ISSUE_FIELD_ID = 512;
    private static final String ISSUE_FIELDS_PATH = "/orgs/github/issue-fields";
    private static final String ISSUE_FIELD_PATH = "/orgs/github/issue-fields/512";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void list_shouldReturnIssueFields_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ISSUE_FIELDS_PATH,
                okJson(jsonBytes(GitHubOrganizationsIssueFieldsApiIntegrationTest.class, "issue-field-items.json")));

        final List<GitHubIssueField> issueFields;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            issueFields = organizations.api().issueFields().list(ORG);
        }

        final List<GitHubIssueField> expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsIssueFieldsApiIntegrationTest.class, "issue-field-items.json"),
                ISSUE_FIELDS_TYPE);

        assertThat(issueFields).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, ISSUE_FIELDS_PATH);
    }

    @Test
    void create_shouldReturnIssueField_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                ISSUE_FIELDS_PATH,
                okJson(jsonBytes(GitHubOrganizationsIssueFieldsApiIntegrationTest.class, "issue-field.json")));
        final GitHubCreateIssueFieldPayload payload = OrganizationsCatalogFixtures.createIssueFieldPayload();

        final GitHubIssueField issueField;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            issueField = organizations.api().issueFields().create(ORG, payload);
        }

        final GitHubIssueField expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsIssueFieldsApiIntegrationTest.class, "issue-field.json"),
                GitHubIssueField.class);

        assertThat(issueField).isEqualTo(expected);
        HTTP.verifyRequested(POST, ISSUE_FIELDS_PATH);
        HTTP.verifyHeader(POST, ISSUE_FIELDS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, ISSUE_FIELDS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void update_shouldReturnIssueField_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                ISSUE_FIELD_PATH,
                okJson(jsonBytes(GitHubOrganizationsIssueFieldsApiIntegrationTest.class, "issue-field.json")));
        final GitHubUpdateIssueFieldPayload payload = OrganizationsCatalogFixtures.updateIssueFieldPayload();

        final GitHubIssueField issueField;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            issueField = organizations.api().issueFields().update(ORG, ISSUE_FIELD_ID, payload);
        }

        final GitHubIssueField expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsIssueFieldsApiIntegrationTest.class, "issue-field.json"),
                GitHubIssueField.class);

        assertThat(issueField).isEqualTo(expected);
        HTTP.verifyRequested(PATCH, ISSUE_FIELD_PATH);
        HTTP.verifyHeader(PATCH, ISSUE_FIELD_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PATCH, ISSUE_FIELD_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void delete_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ISSUE_FIELD_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().issueFields().delete(ORG, ISSUE_FIELD_ID);
        }

        HTTP.verifyRequested(DELETE, ISSUE_FIELD_PATH);
    }
}
