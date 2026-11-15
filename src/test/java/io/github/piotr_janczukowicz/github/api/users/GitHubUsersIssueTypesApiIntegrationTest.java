package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.usersApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueType;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueTypeRequest;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
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
class GitHubUsersIssueTypesApiIntegrationTest {

    private static final JsonType<List<GitHubIssueType>> ISSUE_TYPES_TYPE = JsonTypes.listOf(GitHubIssueType.class);

    private static final long ISSUE_TYPE_ID = 1L;
    private static final String ISSUE_TYPES_PATH = "/user/issue-types";
    private static final String ISSUE_TYPE_PATH = "/user/issue-types/1";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void list_shouldReturnIssueTypes_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ISSUE_TYPES_PATH,
                okJson(jsonBytes(GitHubUsersIssueTypesApiIntegrationTest.class, "issue-type-items.json")));

        final List<GitHubIssueType> issueTypes;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            issueTypes = users.api().issueTypes().list();
        }

        final List<GitHubIssueType> expected = CODEC.decode(
                jsonBytes(GitHubUsersIssueTypesApiIntegrationTest.class, "issue-type-items.json"), ISSUE_TYPES_TYPE);

        assertThat(issueTypes).containsExactlyElementsOf(expected);
        HTTP.verifyRequested(GET, ISSUE_TYPES_PATH);
    }

    @Test
    void create_shouldReturnIssueType_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                ISSUE_TYPES_PATH,
                okJson(jsonBytes(GitHubUsersIssueTypesApiIntegrationTest.class, "issue-type.json")));
        final GitHubIssueTypeRequest payload = OrganizationsCatalogFixtures.issueTypeRequest();

        final GitHubIssueType issueType;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            issueType = users.api().issueTypes().create(payload);
        }

        final GitHubIssueType expected = CODEC.decode(
                jsonBytes(GitHubUsersIssueTypesApiIntegrationTest.class, "issue-type.json"), GitHubIssueType.class);

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
                okJson(jsonBytes(GitHubUsersIssueTypesApiIntegrationTest.class, "issue-type.json")));
        final GitHubIssueTypeRequest payload = OrganizationsCatalogFixtures.issueTypeRequest();

        final GitHubIssueType issueType;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            issueType = users.api().issueTypes().update(ISSUE_TYPE_ID, payload);
        }

        final GitHubIssueType expected = CODEC.decode(
                jsonBytes(GitHubUsersIssueTypesApiIntegrationTest.class, "issue-type.json"), GitHubIssueType.class);

        assertThat(issueType).isEqualTo(expected);
        HTTP.verifyRequested(PUT, ISSUE_TYPE_PATH);
        HTTP.verifyBody(PUT, ISSUE_TYPE_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void delete_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ISSUE_TYPE_PATH, noContent());

        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            users.api().issueTypes().delete(ISSUE_TYPE_ID);
        }

        HTTP.verifyRequested(DELETE, ISSUE_TYPE_PATH);
    }
}
