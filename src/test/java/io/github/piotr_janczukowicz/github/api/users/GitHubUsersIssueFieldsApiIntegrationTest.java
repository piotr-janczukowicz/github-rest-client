package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.usersApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubUsersIssueFieldsApiIntegrationTest {

    private static final long ISSUE_FIELD_ID = 1L;
    private static final String ISSUE_FIELDS_PATH = "/user/issue-fields";
    private static final String ISSUE_FIELD_PATH = "/user/issue-fields/1";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void list_shouldReturnIssueFields_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                ISSUE_FIELDS_PATH,
                okJson(listJsonBytes(GitHubUsersIssueFieldsApiIntegrationTest.class, "user-issue-field.json")));

        final List<GitHubUserIssueField> issueFields;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            issueFields = users.api().issueFields().list();
        }

        assertThat(issueFields).containsExactly(UsersCatalogFixtures.userIssueField());
        HTTP.verifyRequested(GET, ISSUE_FIELDS_PATH);
    }

    @Test
    void create_shouldReturnIssueField_whenResponseIsOk() throws Exception {
        HTTP.stub(
                POST,
                ISSUE_FIELDS_PATH,
                okJson(jsonBytes(GitHubUsersIssueFieldsApiIntegrationTest.class, "user-issue-field.json")));
        final GitHubCreateUserIssueFieldPayload payload = UsersCatalogFixtures.createUserIssueFieldPayload();

        final GitHubUserIssueField issueField;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            issueField = users.api().issueFields().create(payload);
        }

        assertThat(issueField).isEqualTo(UsersCatalogFixtures.userIssueField());
        HTTP.verifyRequested(POST, ISSUE_FIELDS_PATH);
        HTTP.verifyHeader(POST, ISSUE_FIELDS_PATH, CONTENT_TYPE, "application/json");
        HTTP.verifyBody(POST, ISSUE_FIELDS_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void update_shouldReturnIssueField_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                ISSUE_FIELD_PATH,
                okJson(jsonBytes(GitHubUsersIssueFieldsApiIntegrationTest.class, "user-issue-field.json")));
        final GitHubUpdateUserIssueFieldPayload payload = UsersCatalogFixtures.updateUserIssueFieldPayload();

        final GitHubUserIssueField issueField;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            issueField = users.api().issueFields().update(ISSUE_FIELD_ID, payload);
        }

        assertThat(issueField).isEqualTo(UsersCatalogFixtures.userIssueField());
        HTTP.verifyRequested(PATCH, ISSUE_FIELD_PATH);
        HTTP.verifyBody(PATCH, ISSUE_FIELD_PATH, new String(CODEC.encode(payload), StandardCharsets.UTF_8));
    }

    @Test
    void delete_shouldSucceed_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, ISSUE_FIELD_PATH, noContent());

        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            users.api().issueFields().delete(ISSUE_FIELD_ID);
        }

        HTTP.verifyRequested(DELETE, ISSUE_FIELD_PATH);
    }
}
