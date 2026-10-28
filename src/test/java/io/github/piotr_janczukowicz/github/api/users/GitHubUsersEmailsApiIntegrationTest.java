package io.github.piotr_janczukowicz.github.api.users;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PATCH;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.CODEC;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.noContent;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.usersApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubUsersEmailsApiIntegrationTest {

    private static final String USER_EMAILS_PATH = "/user/emails";
    private static final String USER_PUBLIC_EMAILS_PATH = "/user/public_emails";
    private static final String EMAIL_VISIBILITY_PATH = "/user/email/visibility";
    private static final JsonType<List<GitHubEmail>> EMAILS_TYPE = JsonTypes.listOf(GitHubEmail.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listEmails_shouldReturnEmails_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, USER_EMAILS_PATH, okJson(jsonBytes(GitHubUsersEmailsApiIntegrationTest.class, "emails-one.json")));

        final GitHubPage<GitHubEmail> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().emails().listEmails();
        }

        assertThat(page.items()).isEqualTo(expectedEmails());
    }

    @Test
    void listPublicEmails_shouldReturnEmails_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_PUBLIC_EMAILS_PATH,
                okJson(jsonBytes(GitHubUsersEmailsApiIntegrationTest.class, "emails-one.json")));

        final GitHubPage<GitHubEmail> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().emails().listPublicEmails();
        }

        assertThat(page.items()).isEqualTo(expectedEmails());
    }

    @Test
    void setPrimaryEmailVisibility_shouldReturnEmails_whenResponseIsOk() throws Exception {
        HTTP.stub(
                PATCH,
                EMAIL_VISIBILITY_PATH,
                okJson(jsonBytes(GitHubUsersEmailsApiIntegrationTest.class, "emails-one.json")));

        final List<GitHubEmail> emails;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            emails = users.api().emails().setPrimaryEmailVisibility(GitHubEmailVisibility.PUBLIC);
        }

        assertThat(emails).isEqualTo(expectedEmails());
        HTTP.verifyHeader(PATCH, EMAIL_VISIBILITY_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void addEmail_shouldReturnEmails_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                USER_EMAILS_PATH,
                createdJson(jsonBytes(GitHubUsersEmailsApiIntegrationTest.class, "emails-one.json")));

        final List<GitHubEmail> emails;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            emails = users.api().emails().addEmail("octocat@github.com");
        }

        assertThat(emails).isEqualTo(expectedEmails());
        HTTP.verifyHeader(POST, USER_EMAILS_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void addEmails_shouldReturnEmails_whenPayloadProvided() throws Exception {
        HTTP.stub(
                POST,
                USER_EMAILS_PATH,
                createdJson(jsonBytes(GitHubUsersEmailsApiIntegrationTest.class, "emails-one.json")));

        final List<GitHubEmail> emails;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            emails = users.api().emails().addEmails(UsersCatalogFixtures.updateEmailsPayload());
        }

        assertThat(emails).isEqualTo(expectedEmails());
        HTTP.verifyHeader(POST, USER_EMAILS_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void deleteEmail_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_EMAILS_PATH, noContent());

        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            users.api().emails().deleteEmail("octocat@github.com");
        }

        HTTP.verifyHeader(DELETE, USER_EMAILS_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void deleteEmails_shouldComplete_whenListProvided() throws Exception {
        HTTP.stub(DELETE, USER_EMAILS_PATH, noContent());

        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            users.api().emails().deleteEmails(List.of("octocat@github.com"));
        }

        HTTP.verifyHeader(DELETE, USER_EMAILS_PATH, CONTENT_TYPE, "application/json");
    }

    private static List<GitHubEmail> expectedEmails() {
        return CODEC.decode(jsonBytes(GitHubUsersEmailsApiIntegrationTest.class, "emails-one.json"), EMAILS_TYPE);
    }
}
