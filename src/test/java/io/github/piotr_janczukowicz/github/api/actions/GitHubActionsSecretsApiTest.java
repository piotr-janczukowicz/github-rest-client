package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.GitHubSecretChange;
import io.github.piotr_janczukowicz.github.api.agents.AgentsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.agents.GitHubActionsPublicKey;
import io.github.piotr_janczukowicz.github.api.agents.GitHubActionsSecret;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationActionsSecret;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageNumberRequest;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubActionsSecretsApiTest {

    private static final String ORG = "github";
    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ENVIRONMENT = "production";
    private static final String SECRET = "API_TOKEN";
    private static final String BASE = "https://api.github.com/";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsSecretsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listOrgSecrets_shouldReturnSecrets_whenRequestOmitted() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-actions-secrets.json")));

        final GitHubPage<GitHubOrganizationActionsSecret> page = api().listOrgSecrets(ORG);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.organizationActionsSecrets().secrets());
    }

    @Test
    void listOrgSecrets_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-actions-secrets.json")));

        api().listOrgSecrets(ORG, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void getOrgPublicKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-public-key.json")));

        final GitHubActionsPublicKey key = api().getOrgPublicKey(ORG);

        assertThat(key).isEqualTo(AgentsCatalogFixtures.actionsPublicKey());
    }

    @Test
    void getOrgSecret_shouldReturnSecret_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("organization-actions-secret.json")));

        final GitHubOrganizationActionsSecret secret =
                api().getOrgSecret(ORG, SECRET).orElseThrow();

        assertThat(secret).isEqualTo(OrganizationsCatalogFixtures.organizationActionsSecret());
    }

    @Test
    void getOrgSecret_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubOrganizationActionsSecret> result = api().getOrgSecret(ORG, SECRET);

        assertThat(result).isEmpty();
    }

    @Test
    void createOrUpdateOrgSecret_shouldSendPut_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().createOrUpdateOrgSecret(ORG, SECRET, ActionsCatalogFixtures.createOrUpdateOrgSecretPayload());

        assertThat(line(captureSingleRequest())).isEqualTo("PUT " + BASE + "orgs/github/actions/secrets/API_TOKEN");
    }

    @ParameterizedTest
    @CsvSource({"201, CREATED", "204, UPDATED"})
    void createOrUpdateOrgSecret_shouldReturnTheChangeOfTheStatus_whenResponseIsSuccessful(
            final int status, final GitHubSecretChange expected) throws Exception {
        when(transport.execute(any())).thenReturn(response(status, null));

        final GitHubSecretChange change =
                api().createOrUpdateOrgSecret(ORG, SECRET, ActionsCatalogFixtures.createOrUpdateOrgSecretPayload());

        assertThat(change).isEqualTo(expected);
    }

    @Test
    void deleteOrgSecret_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteOrgSecret(ORG, SECRET);

        assertThat(line(captureSingleRequest())).isEqualTo("DELETE " + BASE + "orgs/github/actions/secrets/API_TOKEN");
    }

    @Test
    void listSelectedReposForOrgSecret_shouldReturnRepositories_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("selected-repositories.json")));

        final GitHubPage<GitHubMinimalRepository> page = api().listSelectedReposForOrgSecret(ORG, SECRET);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.selectedRepositories().repositories());
    }

    @Test
    void listSelectedReposForOrgSecret_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("selected-repositories.json")));

        api().listSelectedReposForOrgSecret(ORG, SECRET, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void setSelectedReposForOrgSecret_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setSelectedReposForOrgSecret(ORG, SECRET, ActionsCatalogFixtures.setSelectedReposForOrgSecretPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "orgs/github/actions/secrets/API_TOKEN/repositories");
    }

    @Test
    void addSelectedRepoToOrgSecret_shouldSendPut_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().addSelectedRepoToOrgSecret(ORG, SECRET, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "orgs/github/actions/secrets/API_TOKEN/repositories/1");
    }

    @Test
    void removeSelectedRepoFromOrgSecret_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().removeSelectedRepoFromOrgSecret(ORG, SECRET, 1L);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE " + BASE + "orgs/github/actions/secrets/API_TOKEN/repositories/1");
    }

    @Test
    void listRepoOrganizationSecrets_shouldReturnSecrets_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-secrets.json")));

        final GitHubPage<GitHubActionsSecret> page = api().listRepoOrganizationSecrets(OWNER, REPO);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsSecrets().secrets());
    }

    @Test
    void listRepoOrganizationSecrets_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-secrets.json")));

        api().listRepoOrganizationSecrets(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void listRepoSecrets_shouldReturnSecrets_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-secrets.json")));

        final GitHubPage<GitHubActionsSecret> page = api().listRepoSecrets(OWNER, REPO);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsSecrets().secrets());
    }

    @Test
    void listRepoSecrets_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-secrets.json")));

        api().listRepoSecrets(OWNER, REPO, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void getRepoPublicKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-public-key.json")));

        final GitHubActionsPublicKey key = api().getRepoPublicKey(OWNER, REPO);

        assertThat(key).isEqualTo(AgentsCatalogFixtures.actionsPublicKey());
    }

    @Test
    void getRepoSecret_shouldReturnSecret_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-secret.json")));

        final GitHubActionsSecret secret =
                api().getRepoSecret(OWNER, REPO, SECRET).orElseThrow();

        assertThat(secret).isEqualTo(AgentsCatalogFixtures.actionsSecret());
    }

    @Test
    void getRepoSecret_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubActionsSecret> result = api().getRepoSecret(OWNER, REPO, SECRET);

        assertThat(result).isEmpty();
    }

    @Test
    void createOrUpdateRepoSecret_shouldSendPut_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().createOrUpdateRepoSecret(OWNER, REPO, SECRET, ActionsCatalogFixtures.createOrUpdateRepoSecretPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "repos/octocat/Hello-World/actions/secrets/API_TOKEN");
    }

    @ParameterizedTest
    @CsvSource({"201, CREATED", "204, UPDATED"})
    void createOrUpdateRepoSecret_shouldReturnTheChangeOfTheStatus_whenResponseIsSuccessful(
            final int status, final GitHubSecretChange expected) throws Exception {
        when(transport.execute(any())).thenReturn(response(status, null));

        final GitHubSecretChange change = api().createOrUpdateRepoSecret(
                        OWNER, REPO, SECRET, ActionsCatalogFixtures.createOrUpdateRepoSecretPayload());

        assertThat(change).isEqualTo(expected);
    }

    @Test
    void deleteRepoSecret_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteRepoSecret(OWNER, REPO, SECRET);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE " + BASE + "repos/octocat/Hello-World/actions/secrets/API_TOKEN");
    }

    @Test
    void listEnvironmentSecrets_shouldReturnSecrets_whenRequestOmitted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-secrets.json")));

        final GitHubPage<GitHubActionsSecret> page = api().listEnvironmentSecrets(OWNER, REPO, ENVIRONMENT);

        assertThat(page.items())
                .containsExactlyElementsOf(
                        ActionsCatalogFixtures.actionsSecrets().secrets());
    }

    @Test
    void listEnvironmentSecrets_shouldSendPageNumber_whenRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-secrets.json")));

        api().listEnvironmentSecrets(OWNER, REPO, ENVIRONMENT, GitHubPageNumberRequest.of(2, 10));

        assertThat(captureSingleRequest().uri()).hasParameter("page", "2");
    }

    @Test
    void getEnvironmentPublicKey_shouldReturnKey_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-public-key.json")));

        final GitHubActionsPublicKey key = api().getEnvironmentPublicKey(OWNER, REPO, ENVIRONMENT);

        assertThat(key).isEqualTo(AgentsCatalogFixtures.actionsPublicKey());
    }

    @Test
    void getEnvironmentSecret_shouldReturnSecret_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("actions-secret.json")));

        final GitHubActionsSecret secret =
                api().getEnvironmentSecret(OWNER, REPO, ENVIRONMENT, SECRET).orElseThrow();

        assertThat(secret).isEqualTo(AgentsCatalogFixtures.actionsSecret());
    }

    @Test
    void getEnvironmentSecret_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), "{\"message\":\"Not Found\"}"));

        final Optional<GitHubActionsSecret> result = api().getEnvironmentSecret(OWNER, REPO, ENVIRONMENT, SECRET);

        assertThat(result).isEmpty();
    }

    @Test
    void createOrUpdateEnvironmentSecret_shouldSendPut_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().createOrUpdateEnvironmentSecret(
                        OWNER,
                        REPO,
                        ENVIRONMENT,
                        SECRET,
                        ActionsCatalogFixtures.createOrUpdateEnvironmentSecretPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT " + BASE + "repos/octocat/Hello-World/environments/production/secrets/API_TOKEN");
    }

    @ParameterizedTest
    @CsvSource({"201, CREATED", "204, UPDATED"})
    void createOrUpdateEnvironmentSecret_shouldReturnTheChangeOfTheStatus_whenResponseIsSuccessful(
            final int status, final GitHubSecretChange expected) throws Exception {
        when(transport.execute(any())).thenReturn(response(status, null));

        final GitHubSecretChange change = api().createOrUpdateEnvironmentSecret(
                        OWNER,
                        REPO,
                        ENVIRONMENT,
                        SECRET,
                        ActionsCatalogFixtures.createOrUpdateEnvironmentSecretPayload());

        assertThat(change).isEqualTo(expected);
    }

    @Test
    void deleteEnvironmentSecret_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteEnvironmentSecret(OWNER, REPO, ENVIRONMENT, SECRET);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE " + BASE + "repos/octocat/Hello-World/environments/production/secrets/API_TOKEN");
    }

    private GitHubActionsSecretsApi api() {
        return new GitHubActionsSecretsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActionsSecretsApiTest.class, fileName);
    }

    private static String line(final HttpRequest request) {
        return request.method() + " " + request.uri();
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
