package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
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
class GitHubOrganizationsIssueTypesApiIntegrationTest {

    private static final JsonType<List<GitHubIssueType>> ISSUE_TYPES_TYPE = JsonTypes.listOf(GitHubIssueType.class);

    private static final String ORG = "github";
    private static final int ISSUE_TYPE_ID = 1;
    private static final String ISSUE_TYPES_PATH = "/orgs/github/issue-types";
    private static final String ISSUE_TYPE_PATH = "/orgs/github/issue-types/1";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void list_shouldReturnIssueTypes_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ISSUE_TYPES_PATH,
                okJson(jsonBytes(GitHubOrganizationsIssueTypesApiIntegrationTest.class, "issue-type-items.json")));

        final List<GitHubIssueType> issueTypes;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            issueTypes = organizations.api().issueTypes().list(ORG);
        }

        final List<GitHubIssueType> expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsIssueTypesApiIntegrationTest.class, "issue-type-items.json"),
                ISSUE_TYPES_TYPE);

        assertThat(issueTypes).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, ISSUE_TYPES_PATH);
    }

    @Test
    void create_shouldReturnIssueType_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                ISSUE_TYPES_PATH,
                okJson(jsonBytes(GitHubOrganizationsIssueTypesApiIntegrationTest.class, "issue-type.json")));
        final GitHubCreateIssueTypePayload payload = OrganizationsCatalogFixtures.createIssueTypePayload();

        final GitHubIssueType issueType;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            issueType = organizations.api().issueTypes().create(ORG, payload);
        }

        final GitHubIssueType expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsIssueTypesApiIntegrationTest.class, "issue-type.json"),
                GitHubIssueType.class);

        assertThat(issueType).isEqualTo(expected);
        HTTP.verifyRequested(POST, ISSUE_TYPES_PATH);
        HTTP.verifyHeader(POST, ISSUE_TYPES_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, ISSUE_TYPES_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void update_shouldReturnIssueType_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PUT,
                ISSUE_TYPE_PATH,
                okJson(jsonBytes(GitHubOrganizationsIssueTypesApiIntegrationTest.class, "issue-type.json")));
        final GitHubUpdateIssueTypePayload payload = OrganizationsCatalogFixtures.updateIssueTypePayload();

        final GitHubIssueType issueType;
        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            issueType = organizations.api().issueTypes().update(ORG, ISSUE_TYPE_ID, payload);
        }

        final GitHubIssueType expected = CODEC.decode(
                jsonBytes(GitHubOrganizationsIssueTypesApiIntegrationTest.class, "issue-type.json"),
                GitHubIssueType.class);

        assertThat(issueType).isEqualTo(expected);
        HTTP.verifyRequested(PUT, ISSUE_TYPE_PATH);
        HTTP.verifyHeader(PUT, ISSUE_TYPE_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(PUT, ISSUE_TYPE_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void delete_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ISSUE_TYPE_PATH, noContent());

        try (final GitHubApiWireMockSupport.OrganizationsApiClient organizations = organizationsApi(HTTP)) {
            organizations.api().issueTypes().delete(ORG, ISSUE_TYPE_ID);
        }

        HTTP.verifyRequested(DELETE, ISSUE_TYPE_PATH);
    }
}
