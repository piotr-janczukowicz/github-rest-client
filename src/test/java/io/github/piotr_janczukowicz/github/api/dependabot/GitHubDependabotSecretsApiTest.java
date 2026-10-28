package io.github.piotr_janczukowicz.github.api.dependabot;

import static io.github.piotr_janczukowicz.http.HttpHeaderNames.CONTENT_TYPE;
import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.GitHubSecretChange;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubDependabotSecretsApiTest {

    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "hello-world";
    private static final String SECRET = "NPM_TOKEN";
    private static final long REPOSITORY_ID = 64_780_797L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDependabotSecretsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listOrgSecrets_shouldReturnSecrets_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-dependabot-secrets-page.json")));

        final GitHubPage<GitHubOrgDependabotSecret> page = api().listOrgSecrets(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            DependabotCatalogFixtures.orgDependabotSecretsPage().secrets());
            softly.assertThat(page.totalCount())
                    .isEqualTo(
                            DependabotCatalogFixtures.orgDependabotSecretsPage().totalCount());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octo-org/dependabot/secrets?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listOrgSecrets_shouldSendPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-dependabot-secrets-page.json")));

        api().listOrgSecrets(ORG, GitHubPageNumberRequest.of(2, 30));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "30");
    }

    @Test
    void getOrgPublicKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("dependabot-public-key.json")));

        final GitHubDependabotPublicKey key = api().getOrgPublicKey(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(key).isEqualTo(DependabotCatalogFixtures.dependabotPublicKey());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octo-org/dependabot/secrets/public-key");
        });
    }

    @Test
    void getOrgPublicKey_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final GitHubDependabotSecretsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getOrgPublicKey(ORG));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void getOrgSecret_shouldReturnSecret_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("org-dependabot-secret.json")));

        final Optional<GitHubOrgDependabotSecret> secret = api().getOrgSecret(ORG, SECRET);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(secret).contains(DependabotCatalogFixtures.orgDependabotSecret());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octo-org/dependabot/secrets/NPM_TOKEN");
        });
    }

    @Test
    void getOrgSecret_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubOrgDependabotSecret> result = api().getOrgSecret(ORG, SECRET);

        assertThat(result).isEmpty();
    }

    @Test
    void createOrUpdateOrgSecret_shouldPutPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), "{}"));

        api().createOrUpdateOrgSecret(ORG, SECRET, DependabotCatalogFixtures.createOrUpdateOrgSecretPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/dependabot/secrets/NPM_TOKEN");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @ParameterizedTest
    @CsvSource({"201, CREATED", "204, UPDATED"})
    void createOrUpdateOrgSecret_shouldReturnTheChangeOfTheStatus_whenResponseIsSuccessful(
            final int status, final GitHubSecretChange expected) throws Exception {
        when(transport.execute(any())).thenReturn(response(status, null));

        final GitHubSecretChange change =
                api().createOrUpdateOrgSecret(ORG, SECRET, DependabotCatalogFixtures.createOrUpdateOrgSecretPayload());

        assertThat(change).isEqualTo(expected);
    }

    @Test
    void createOrUpdateOrgSecret_shouldPutPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().createOrUpdateOrgSecret(ORG, SECRET, DependabotCatalogFixtures.createOrUpdateOrgSecretPayload());
        final HttpRequest request = captureSingleRequest();

        assertThat(request.method()).isEqualTo(HttpMethod.PUT);
    }

    @Test
    void deleteOrgSecret_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteOrgSecret(ORG, SECRET);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/dependabot/secrets/NPM_TOKEN");
        });
    }

    @Test
    void listSelectedReposForOrgSecret_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("dependabot-selected-repositories-page.json")));

        final GitHubPage<GitHubMinimalRepository> page = api().listSelectedReposForOrgSecret(ORG, SECRET);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(DependabotCatalogFixtures.dependabotSelectedRepositoriesPage()
                            .repositories());
            softly.assertThat(page.totalCount())
                    .isEqualTo(DependabotCatalogFixtures.dependabotSelectedRepositoriesPage()
                            .totalCount());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octo-org/dependabot/secrets/NPM_TOKEN/repositories?"
                            + "page=1&" + TestClients.perPageQuery());
        });
    }

    @Test
    void listSelectedReposForOrgSecret_shouldSendPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("dependabot-selected-repositories-page.json")));

        api().listSelectedReposForOrgSecret(ORG, SECRET, GitHubPageNumberRequest.of(2, 30));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "30");
    }

    @Test
    void setSelectedReposForOrgSecret_shouldPutPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setSelectedReposForOrgSecret(
                        ORG, SECRET, DependabotCatalogFixtures.setSelectedReposForOrgSecretPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/dependabot/secrets/NPM_TOKEN/repositories");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @Test
    void addSelectedRepoToOrgSecret_shouldPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().addSelectedRepoToOrgSecret(ORG, SECRET, REPOSITORY_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/orgs/octo-org/dependabot/secrets/NPM_TOKEN/repositories/64780797");
        });
    }

    @Test
    void removeSelectedRepoFromOrgSecret_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeSelectedRepoFromOrgSecret(ORG, SECRET, REPOSITORY_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/orgs/octo-org/dependabot/secrets/NPM_TOKEN/repositories/64780797");
        });
    }

    @Test
    void listRepoSecrets_shouldReturnSecrets_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("dependabot-secrets-page.json")));

        final GitHubPage<GitHubDependabotSecret> page = api().listRepoSecrets(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            DependabotCatalogFixtures.dependabotSecretsPage().secrets());
            softly.assertThat(page.totalCount())
                    .isEqualTo(DependabotCatalogFixtures.dependabotSecretsPage().totalCount());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/dependabot/secrets?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listRepoSecrets_shouldSendPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("dependabot-secrets-page.json")));

        api().listRepoSecrets(OWNER, REPO, GitHubPageNumberRequest.of(2, 30));
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "2").hasParameter("per_page", "30");
    }

    @Test
    void getRepoPublicKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("dependabot-public-key.json")));

        final GitHubDependabotPublicKey key = api().getRepoPublicKey(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(key).isEqualTo(DependabotCatalogFixtures.dependabotPublicKey());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/dependabot/secrets/public-key");
        });
    }

    @Test
    void getRepoPublicKey_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final GitHubDependabotSecretsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getRepoPublicKey(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void getRepoSecret_shouldReturnSecret_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("dependabot-secret.json")));

        final Optional<GitHubDependabotSecret> secret = api().getRepoSecret(OWNER, REPO, SECRET);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(secret).contains(DependabotCatalogFixtures.dependabotSecret());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/hello-world/dependabot/secrets/NPM_TOKEN");
        });
    }

    @Test
    void getRepoSecret_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubDependabotSecret> result = api().getRepoSecret(OWNER, REPO, SECRET);

        assertThat(result).isEmpty();
    }

    @Test
    void createOrUpdateRepoSecret_shouldPutPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), "{}"));

        api().createOrUpdateRepoSecret(
                        OWNER, REPO, SECRET, DependabotCatalogFixtures.createOrUpdateRepoSecretPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/dependabot/secrets/NPM_TOKEN");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @ParameterizedTest
    @CsvSource({"201, CREATED", "204, UPDATED"})
    void createOrUpdateRepoSecret_shouldReturnTheChangeOfTheStatus_whenResponseIsSuccessful(
            final int status, final GitHubSecretChange expected) throws Exception {
        when(transport.execute(any())).thenReturn(response(status, null));

        final GitHubSecretChange change = api().createOrUpdateRepoSecret(
                        OWNER, REPO, SECRET, DependabotCatalogFixtures.createOrUpdateRepoSecretPayload());

        assertThat(change).isEqualTo(expected);
    }

    @Test
    void deleteRepoSecret_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteRepoSecret(OWNER, REPO, SECRET);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/repos/octocat/hello-world/dependabot/secrets/NPM_TOKEN");
        });
    }

    @ParameterizedTest
    @MethodSource("nullRequestCalls")
    void validation_shouldThrow_whenRequestNull(final SecretsCall call) {
        final GitHubDependabotSecretsApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("request");
    }

    @ParameterizedTest
    @MethodSource("nullPayloadCalls")
    void validation_shouldThrow_whenPayloadNull(final SecretsCall call) {
        final GitHubDependabotSecretsApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @ParameterizedTest
    @MethodSource("invalidCalls")
    void call_shouldThrowIllegalArgument_whenInvalid(final SecretsCall call, final String messageFragment) {
        final GitHubDependabotSecretsApi api = api();

        final Throwable thrown = catchThrowable(() -> call.execute(api));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining(messageFragment);
    }

    private GitHubDependabotSecretsApi api() {
        return new GitHubDependabotSecretsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubDependabotSecretsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static Stream<SecretsCall> nullRequestCalls() {
        return Stream.of(
                api -> api.listOrgSecrets(ORG, null),
                api -> api.listSelectedReposForOrgSecret(ORG, SECRET, null),
                api -> api.listRepoSecrets(OWNER, REPO, null));
    }

    private static Stream<SecretsCall> nullPayloadCalls() {
        return Stream.of(
                api -> api.createOrUpdateOrgSecret(ORG, SECRET, null),
                api -> api.setSelectedReposForOrgSecret(ORG, SECRET, null),
                api -> api.createOrUpdateRepoSecret(OWNER, REPO, SECRET, null));
    }

    private static Stream<Arguments> invalidCalls() {
        return Stream.of(
                Arguments.of((SecretsCall) api -> api.listOrgSecrets(" "), "org"),
                Arguments.of((SecretsCall) api -> api.getOrgPublicKey(" "), "org"),
                Arguments.of((SecretsCall) api -> api.getOrgSecret(" ", SECRET), "org"),
                Arguments.of((SecretsCall) api -> api.getOrgSecret(ORG, " "), "secret_name"),
                Arguments.of((SecretsCall) api -> api.deleteOrgSecret(ORG, " "), "secret_name"),
                Arguments.of((SecretsCall) api -> api.listSelectedReposForOrgSecret(ORG, " "), "secret_name"),
                Arguments.of((SecretsCall) api -> api.addSelectedRepoToOrgSecret(" ", SECRET, REPOSITORY_ID), "org"),
                Arguments.of(
                        (SecretsCall) api -> api.removeSelectedRepoFromOrgSecret(ORG, " ", REPOSITORY_ID),
                        "secret_name"),
                Arguments.of((SecretsCall) api -> api.listRepoSecrets(" ", REPO), "owner"),
                Arguments.of((SecretsCall) api -> api.getRepoPublicKey(OWNER, " "), "repo"),
                Arguments.of((SecretsCall) api -> api.getRepoSecret(OWNER, REPO, " "), "secret_name"),
                Arguments.of((SecretsCall) api -> api.deleteRepoSecret(" ", REPO, SECRET), "owner"),
                Arguments.of(
                        (SecretsCall) api -> api.listOrgSecrets(ORG, GitHubPageRequest.byCursor()),
                        "unsupported pagination request: cursor"));
    }

    @FunctionalInterface
    interface SecretsCall {
        void execute(GitHubDependabotSecretsApi api) throws Exception;
    }
}
