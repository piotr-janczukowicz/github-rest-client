package io.github.piotr_janczukowicz.github.api.codespaces;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCodespacesOrganizationSecretsApiTest {

    private static final String ORG = "octo-org";
    private static final String SECRET = "NPM_TOKEN";
    private static final long REPOSITORY_ID = 64_780_797L;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodespacesOrganizationSecretsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listOrgSecrets_shouldReturnSecrets_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-codespaces-secrets-page.json")));

        final GitHubPage<GitHubCodespacesOrgSecret> page = api().listOrgSecrets(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(
                            CodespacesCatalogFixtures.orgCodespacesSecretsPage().secrets());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octo-org/codespaces/secrets?page=1&"
                            + TestClients.perPageQuery());
        });
    }

    @Test
    void listOrgSecrets_shouldSendPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("org-codespaces-secrets-page.json")));

        api().listOrgSecrets(ORG, GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "1");
    }

    @Test
    void getOrgPublicKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespaces-public-key.json")));

        final GitHubCodespacesPublicKey key = api().getOrgPublicKey(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(key).isEqualTo(CodespacesCatalogFixtures.codespacesPublicKey());
            softly.assertThat(requestUri.getPath()).isEqualTo("/orgs/octo-org/codespaces/secrets/public-key");
        });
    }

    @Test
    void getOrgPublicKey_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final GitHubCodespacesOrganizationSecretsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.getOrgPublicKey(ORG));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void getOrgSecret_shouldReturnSecret_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("codespaces-org-secret.json")));

        final Optional<GitHubCodespacesOrgSecret> secret = api().getOrgSecret(ORG, SECRET);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(secret).contains(CodespacesCatalogFixtures.codespacesOrgSecret());
            softly.assertThat(requestUri.getPath()).isEqualTo("/orgs/octo-org/codespaces/secrets/NPM_TOKEN");
        });
    }

    @Test
    void getOrgSecret_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubCodespacesOrgSecret> secret = api().getOrgSecret(ORG, SECRET);

        assertThat(secret).isEmpty();
    }

    @Test
    void createOrUpdateOrgSecret_shouldPutPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), "{}"));

        api().createOrUpdateOrgSecret(ORG, SECRET, CodespacesCatalogFixtures.createOrUpdateOrgSecretPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/octo-org/codespaces/secrets/NPM_TOKEN");
            softly.assertThat(request.headers().get(CONTENT_TYPE)).contains("application/json");
        });
    }

    @ParameterizedTest
    @CsvSource({"201, CREATED", "204, UPDATED"})
    void createOrUpdateOrgSecret_shouldReturnTheChangeOfTheStatus_whenResponseIsSuccessful(
            final int status, final GitHubSecretChange expected) throws Exception {
        when(transport.execute(any())).thenReturn(response(status, null));

        final GitHubSecretChange change =
                api().createOrUpdateOrgSecret(ORG, SECRET, CodespacesCatalogFixtures.createOrUpdateOrgSecretPayload());

        assertThat(change).isEqualTo(expected);
    }

    @Test
    void createOrUpdateOrgSecret_shouldThrow_whenPayloadNull() {
        final GitHubCodespacesOrganizationSecretsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createOrUpdateOrgSecret(ORG, SECRET, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void deleteOrgSecret_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteOrgSecret(ORG, SECRET);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().getPath()).isEqualTo("/orgs/octo-org/codespaces/secrets/NPM_TOKEN");
        });
    }

    @Test
    void listSelectedReposForOrgSecret_shouldReturnRepositories_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("codespaces-selected-repositories-page.json")));

        final GitHubPage<GitHubMinimalRepository> page = api().listSelectedReposForOrgSecret(ORG, SECRET);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactlyElementsOf(CodespacesCatalogFixtures.codespacesSelectedRepositoriesPage()
                            .repositories());
            softly.assertThat(requestUri)
                    .hasToString(
                            "https://api.github.com/orgs/octo-org/codespaces/secrets/NPM_TOKEN/repositories?page=1&"
                                    + TestClients.perPageQuery());
        });
    }

    @Test
    void listSelectedReposForOrgSecret_shouldSendPage_whenPageRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("codespaces-selected-repositories-page.json")));

        api().listSelectedReposForOrgSecret(ORG, SECRET, GitHubPageRequest.byPage());
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri).hasParameter("page", "1");
    }

    @Test
    void setSelectedReposForOrgSecret_shouldPutPayload_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setSelectedReposForOrgSecret(
                        ORG, SECRET, CodespacesCatalogFixtures.setSelectedReposForOrgSecretPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/orgs/octo-org/codespaces/secrets/NPM_TOKEN/repositories");
        });
    }

    @Test
    void addSelectedRepoToOrgSecret_shouldPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().addSelectedRepoToOrgSecret(ORG, SECRET, REPOSITORY_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/orgs/octo-org/codespaces/secrets/NPM_TOKEN/repositories/64780797");
        });
    }

    @Test
    void removeSelectedRepoFromOrgSecret_shouldDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeSelectedRepoFromOrgSecret(ORG, SECRET, REPOSITORY_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri().getPath())
                    .isEqualTo("/orgs/octo-org/codespaces/secrets/NPM_TOKEN/repositories/64780797");
        });
    }

    private GitHubCodespacesOrganizationSecretsApi api() {
        return new GitHubCodespacesOrganizationSecretsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCodespacesOrganizationSecretsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
