package io.github.piotr_janczukowicz.github.api.users;

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
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.usersApi;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.json.JsonType;
import io.github.piotr_janczukowicz.json.JsonTypes;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubUsersKeysApiIntegrationTest {

    private static final String OCTOCAT = "octocat";
    private static final String USER_GPG_KEYS_PATH = "/user/gpg_keys";
    private static final String OCTOCAT_GPG_KEYS_PATH = "/users/octocat/gpg_keys";
    private static final String USER_GPG_KEY_PATH = "/user/gpg_keys/3";
    private static final String MISSING_GPG_KEY_PATH = "/user/gpg_keys/1";
    private static final String USER_KEYS_PATH = "/user/keys";
    private static final String OCTOCAT_KEYS_PATH = "/users/octocat/keys";
    private static final String USER_KEY_PATH = "/user/keys/2";
    private static final String MISSING_KEY_PATH = "/user/keys/1";
    private static final String USER_SSH_SIGNING_KEYS_PATH = "/user/ssh_signing_keys";
    private static final String OCTOCAT_SSH_SIGNING_KEYS_PATH = "/users/octocat/ssh_signing_keys";
    private static final String USER_SSH_SIGNING_KEY_PATH = "/user/ssh_signing_keys/2";
    private static final String MISSING_SSH_SIGNING_KEY_PATH = "/user/ssh_signing_keys/1";

    private static final JsonType<List<GitHubGpgKey>> GPG_KEYS_TYPE = JsonTypes.listOf(GitHubGpgKey.class);
    private static final JsonType<List<GitHubKey>> KEYS_TYPE = JsonTypes.listOf(GitHubKey.class);
    private static final JsonType<List<GitHubKeySimple>> KEY_SIMPLE_TYPE = JsonTypes.listOf(GitHubKeySimple.class);
    private static final JsonType<List<GitHubSshSigningKey>> SSH_SIGNING_KEYS_TYPE =
            JsonTypes.listOf(GitHubSshSigningKey.class);

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listGpgKeys_shouldReturnKeys_whenAuthenticatedUser() throws Exception {
        HTTP.stub(
                GET,
                USER_GPG_KEYS_PATH,
                okJson(listJsonBytes(GitHubUsersKeysApiIntegrationTest.class, "gpg-key.json")));

        final GitHubPage<GitHubGpgKey> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().gpgKeys().listGpgKeys();
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedGpgKeys());
    }

    @Test
    void listGpgKeys_shouldReturnKeys_whenUsernameProvided() throws Exception {
        HTTP.stub(
                GET,
                OCTOCAT_GPG_KEYS_PATH,
                okJson(listJsonBytes(GitHubUsersKeysApiIntegrationTest.class, "gpg-key.json")));

        final GitHubPage<GitHubGpgKey> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().gpgKeys().listGpgKeys(OCTOCAT);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedGpgKeys());
    }

    @Test
    void createGpgKey_shouldReturnKey_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                USER_GPG_KEYS_PATH,
                createdJson(jsonBytes(GitHubUsersKeysApiIntegrationTest.class, "gpg-key.json")));

        final GitHubGpgKey key;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            key = users.api().gpgKeys().createGpgKey(UsersCatalogFixtures.createGpgKeyPayload());
        }

        assertThat(key).isEqualTo(expectedGpgKey());
        HTTP.verifyHeader(POST, USER_GPG_KEYS_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void getGpgKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, USER_GPG_KEY_PATH, okJson(jsonBytes(GitHubUsersKeysApiIntegrationTest.class, "gpg-key.json")));

        final Optional<GitHubGpgKey> key;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            key = users.api().gpgKeys().getGpgKey(3L);
        }

        assertThat(key).contains(expectedGpgKey());
    }

    @Test
    void getGpgKey_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, MISSING_GPG_KEY_PATH, notFound());

        final Optional<GitHubGpgKey> key;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            key = users.api().gpgKeys().getGpgKey(1L);
        }

        assertThat(key).isEmpty();
    }

    @Test
    void deleteGpgKey_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_GPG_KEY_PATH, noContent());

        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            users.api().gpgKeys().deleteGpgKey(3L);
        }

        HTTP.verifyRequested(DELETE, USER_GPG_KEY_PATH);
    }

    @Test
    void listPublicKeys_shouldReturnKeys_whenAuthenticatedUser() throws Exception {
        HTTP.stub(GET, USER_KEYS_PATH, okJson(listJsonBytes(GitHubUsersKeysApiIntegrationTest.class, "key.json")));

        final GitHubPage<GitHubKey> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().keys().listPublicKeys();
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedKeys());
    }

    @Test
    void listPublicKeys_shouldReturnSimpleKeys_whenUsernameProvided() throws Exception {
        HTTP.stub(
                GET,
                OCTOCAT_KEYS_PATH,
                okJson(listJsonBytes(GitHubUsersKeysApiIntegrationTest.class, "key-simple.json")));

        final GitHubPage<GitHubKeySimple> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().keys().listPublicKeys(OCTOCAT);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedKeySimpleList());
    }

    @Test
    void createPublicSshKey_shouldReturnKey_whenResponseIsCreated() throws Exception {
        HTTP.stub(POST, USER_KEYS_PATH, createdJson(jsonBytes(GitHubUsersKeysApiIntegrationTest.class, "key.json")));

        final GitHubKey key;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            key = users.api().keys().createPublicSshKey(UsersCatalogFixtures.createPublicSshKeyPayload());
        }

        assertThat(key).isEqualTo(expectedKey());
        HTTP.verifyHeader(POST, USER_KEYS_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void getPublicKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, USER_KEY_PATH, okJson(jsonBytes(GitHubUsersKeysApiIntegrationTest.class, "key.json")));

        final Optional<GitHubKey> key;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            key = users.api().keys().getPublicKey(2L);
        }

        assertThat(key).contains(expectedKey());
    }

    @Test
    void getPublicKey_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, MISSING_KEY_PATH, notFound());

        final Optional<GitHubKey> key;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            key = users.api().keys().getPublicKey(1L);
        }

        assertThat(key).isEmpty();
    }

    @Test
    void deletePublicKey_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_KEY_PATH, noContent());

        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            users.api().keys().deletePublicKey(2L);
        }

        HTTP.verifyRequested(DELETE, USER_KEY_PATH);
    }

    @Test
    void listSshSigningKeys_shouldReturnKeys_whenAuthenticatedUser() throws Exception {
        HTTP.stub(
                GET,
                USER_SSH_SIGNING_KEYS_PATH,
                okJson(listJsonBytes(GitHubUsersKeysApiIntegrationTest.class, "ssh-signing-key.json")));

        final GitHubPage<GitHubSshSigningKey> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().sshSigningKeys().listSshSigningKeys();
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedSshSigningKeys());
    }

    @Test
    void listSshSigningKeys_shouldReturnKeys_whenUsernameProvided() throws Exception {
        HTTP.stub(
                GET,
                OCTOCAT_SSH_SIGNING_KEYS_PATH,
                okJson(listJsonBytes(GitHubUsersKeysApiIntegrationTest.class, "ssh-signing-key.json")));

        final GitHubPage<GitHubSshSigningKey> page;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            page = users.api().sshSigningKeys().listSshSigningKeys(OCTOCAT);
        }

        assertThat(page.items()).containsExactlyElementsOf(expectedSshSigningKeys());
    }

    @Test
    void createSshSigningKey_shouldReturnKey_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                USER_SSH_SIGNING_KEYS_PATH,
                createdJson(jsonBytes(GitHubUsersKeysApiIntegrationTest.class, "ssh-signing-key.json")));

        final GitHubSshSigningKey key;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            key = users.api().sshSigningKeys().createSshSigningKey(UsersCatalogFixtures.createSshSigningKeyPayload());
        }

        assertThat(key).isEqualTo(expectedSshSigningKey());
        HTTP.verifyHeader(POST, USER_SSH_SIGNING_KEYS_PATH, CONTENT_TYPE, "application/json");
    }

    @Test
    void getSshSigningKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                USER_SSH_SIGNING_KEY_PATH,
                okJson(jsonBytes(GitHubUsersKeysApiIntegrationTest.class, "ssh-signing-key.json")));

        final Optional<GitHubSshSigningKey> key;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            key = users.api().sshSigningKeys().getSshSigningKey(2L);
        }

        assertThat(key).contains(expectedSshSigningKey());
    }

    @Test
    void getSshSigningKey_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, MISSING_SSH_SIGNING_KEY_PATH, notFound());

        final Optional<GitHubSshSigningKey> key;
        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            key = users.api().sshSigningKeys().getSshSigningKey(1L);
        }

        assertThat(key).isEmpty();
    }

    @Test
    void deleteSshSigningKey_shouldComplete_whenResponseIsNoContent() throws Exception {
        HTTP.stub(DELETE, USER_SSH_SIGNING_KEY_PATH, noContent());

        try (final GitHubApiWireMockSupport.UsersApiClient users = usersApi(HTTP)) {
            users.api().sshSigningKeys().deleteSshSigningKey(2L);
        }

        HTTP.verifyRequested(DELETE, USER_SSH_SIGNING_KEY_PATH);
    }

    private static List<GitHubGpgKey> expectedGpgKeys() {
        return CODEC.decode(listJsonBytes(GitHubUsersKeysApiIntegrationTest.class, "gpg-key.json"), GPG_KEYS_TYPE);
    }

    private static GitHubGpgKey expectedGpgKey() {
        return CODEC.decode(jsonBytes(GitHubUsersKeysApiIntegrationTest.class, "gpg-key.json"), GitHubGpgKey.class);
    }

    private static List<GitHubKey> expectedKeys() {
        return CODEC.decode(listJsonBytes(GitHubUsersKeysApiIntegrationTest.class, "key.json"), KEYS_TYPE);
    }

    private static GitHubKey expectedKey() {
        return CODEC.decode(jsonBytes(GitHubUsersKeysApiIntegrationTest.class, "key.json"), GitHubKey.class);
    }

    private static List<GitHubKeySimple> expectedKeySimpleList() {
        return CODEC.decode(listJsonBytes(GitHubUsersKeysApiIntegrationTest.class, "key-simple.json"), KEY_SIMPLE_TYPE);
    }

    private static List<GitHubSshSigningKey> expectedSshSigningKeys() {
        return CODEC.decode(
                listJsonBytes(GitHubUsersKeysApiIntegrationTest.class, "ssh-signing-key.json"), SSH_SIGNING_KEYS_TYPE);
    }

    private static GitHubSshSigningKey expectedSshSigningKey() {
        return CODEC.decode(
                jsonBytes(GitHubUsersKeysApiIntegrationTest.class, "ssh-signing-key.json"), GitHubSshSigningKey.class);
    }
}
