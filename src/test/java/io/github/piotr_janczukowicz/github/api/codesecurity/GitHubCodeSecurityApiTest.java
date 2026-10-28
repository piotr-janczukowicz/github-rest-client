package io.github.piotr_janczukowicz.github.api.codesecurity;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.pagination.GitHubCursorRequest;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubCodeSecurityApiTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final String ORG = "octo-org";
    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final int CONFIGURATION_ID = 1;

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodeSecurityApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listConfigurationsForOrg_shouldSendCursor_whenCursorRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("code-security-configuration.json") + "]"));

        final GitHubPage<GitHubCodeSecurityConfiguration> page = api().listConfigurationsForOrg(
                        ORG,
                        CodeSecurityCatalogFixtures.getConfigurationsForOrgFilter(),
                        GitHubCursorRequest.after("tok", 50));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(CodeSecurityCatalogFixtures.codeSecurityConfiguration());
            softly.assertThat(requestUri).hasParameter("target_type", "global").hasParameter("after", "tok");
            softly.assertThat(requestUri).hasParameter("per_page", "50");
        });
    }

    @Test
    void listConfigurationsForOrg_shouldUseDefaultCursor_whenNoRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listConfigurationsForOrg(ORG);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/orgs/octo-org/code-security/configurations?"
                        + TestClients.perPageQuery());
    }

    @Test
    void createConfiguration_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("code-security-configuration.json")));

        final GitHubCodeSecurityConfiguration configuration =
                api().createConfiguration(ORG, CodeSecurityCatalogFixtures.createConfigurationPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(configuration).isEqualTo(CodeSecurityCatalogFixtures.codeSecurityConfiguration());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/code-security/configurations");
        });
    }

    @Test
    void createConfigurationForEnterprise_shouldPostPayload_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("code-security-configuration.json")));

        final GitHubCodeSecurityConfiguration configuration = api().createConfigurationForEnterprise(
                        ENTERPRISE, CodeSecurityCatalogFixtures.createConfigurationForEnterprisePayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(configuration).isEqualTo(CodeSecurityCatalogFixtures.codeSecurityConfiguration());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/enterprises/octo-enterprise/code-security/configurations");
        });
    }

    @Test
    void getConfiguration_shouldReturnConfiguration_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-security-configuration.json")));

        final Optional<GitHubCodeSecurityConfiguration> configuration = api().getConfiguration(ORG, CONFIGURATION_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(configuration).contains(CodeSecurityCatalogFixtures.codeSecurityConfiguration());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/orgs/octo-org/code-security/configurations/1");
        });
    }

    @Test
    void getConfiguration_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubCodeSecurityConfiguration> configuration = api().getConfiguration(ORG, CONFIGURATION_ID);

        assertThat(configuration).isEmpty();
    }

    @Test
    void getSingleConfigurationForEnterprise_shouldReturnConfiguration_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-security-configuration.json")));

        final Optional<GitHubCodeSecurityConfiguration> configuration =
                api().getSingleConfigurationForEnterprise(ENTERPRISE, CONFIGURATION_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(configuration).contains(CodeSecurityCatalogFixtures.codeSecurityConfiguration());
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/enterprises/octo-enterprise/code-security/configurations/1");
        });
    }

    @Test
    void getSingleConfigurationForEnterprise_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubCodeSecurityConfiguration> configuration =
                api().getSingleConfigurationForEnterprise(ENTERPRISE, CONFIGURATION_ID);

        assertThat(configuration).isEmpty();
    }

    @Test
    void updateConfiguration_shouldReturnConfiguration_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-security-configuration.json")));

        final Optional<GitHubCodeSecurityConfiguration> configuration = api().updateConfiguration(
                        ORG, CONFIGURATION_ID, CodeSecurityCatalogFixtures.updateConfigurationPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(configuration).contains(CodeSecurityCatalogFixtures.codeSecurityConfiguration());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
        });
    }

    @Test
    void updateConfiguration_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final Optional<GitHubCodeSecurityConfiguration> configuration = api().updateConfiguration(
                        ORG, CONFIGURATION_ID, CodeSecurityCatalogFixtures.updateConfigurationPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(configuration).isEmpty();
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
        });
    }

    @Test
    void updateEnterpriseConfiguration_shouldReturnConfiguration_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-security-configuration.json")));

        final GitHubCodeSecurityConfiguration configuration = api().updateEnterpriseConfiguration(
                        ENTERPRISE,
                        CONFIGURATION_ID,
                        CodeSecurityCatalogFixtures.updateEnterpriseConfigurationPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(configuration).isEqualTo(CodeSecurityCatalogFixtures.codeSecurityConfiguration());
            softly.assertThat(request.method()).isEqualTo(HttpMethod.PATCH);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/enterprises/octo-enterprise/code-security/configurations/1");
        });
    }

    @Test
    void deleteConfiguration_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteConfiguration(ORG, CONFIGURATION_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/code-security/configurations/1");
        });
    }

    @Test
    void deleteConfigurationForEnterprise_shouldSendDelete_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteConfigurationForEnterprise(ENTERPRISE, CONFIGURATION_ID);
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/enterprises/octo-enterprise/code-security/configurations/1");
        });
    }

    @Test
    void attachConfiguration_shouldPostPayload_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        api().attachConfiguration(ORG, CONFIGURATION_ID, CodeSecurityCatalogFixtures.attachConfigurationPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/code-security/configurations/1/attach");
        });
    }

    @Test
    void attachEnterpriseConfiguration_shouldPostPayload_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), null));

        api().attachEnterpriseConfiguration(
                        ENTERPRISE,
                        CONFIGURATION_ID,
                        CodeSecurityCatalogFixtures.attachEnterpriseConfigurationPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(request.uri())
                    .hasToString(
                            "https://api.github.com/enterprises/octo-enterprise/code-security/configurations/1/attach");
        });
    }

    @Test
    void detachConfiguration_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().detachConfiguration(ORG, CodeSecurityCatalogFixtures.detachConfigurationPayload());
        final HttpRequest request = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(request.method()).isEqualTo(HttpMethod.DELETE);
            softly.assertThat(request.uri())
                    .hasToString("https://api.github.com/orgs/octo-org/code-security/configurations/detach");
        });
    }

    @Test
    void getDefaultConfigurations_shouldReturnDefaults_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), "[" + json("code-security-default-configuration.json") + "]"));

        final List<GitHubCodeSecurityDefaultConfiguration> defaults = api().getDefaultConfigurations(ORG);

        assertThat(defaults).containsExactly(CodeSecurityCatalogFixtures.codeSecurityDefaultConfiguration());
    }

    @Test
    void getDefaultConfigurationsForEnterprise_shouldReturnDefaults_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), "[" + json("code-security-default-configuration.json") + "]"));

        final List<GitHubCodeSecurityDefaultConfiguration> defaults =
                api().getDefaultConfigurationsForEnterprise(ENTERPRISE);

        assertThat(defaults).containsExactly(CodeSecurityCatalogFixtures.codeSecurityDefaultConfiguration());
    }

    @Test
    void makeConfigurationDefault_shouldReturnResponse_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("set-configuration-as-default-response.json")));

        final GitHubSetConfigurationAsDefaultResponse response = api().makeConfigurationDefault(
                        ORG, CONFIGURATION_ID, CodeSecurityCatalogFixtures.setConfigurationAsDefaultPayload());

        assertThat(response).isEqualTo(CodeSecurityCatalogFixtures.setConfigurationAsDefaultResponse());
    }

    @Test
    void makeConfigurationDefaultForEnterprise_shouldReturnResponse_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(), json("set-configuration-as-default-for-enterprise-response.json")));

        final GitHubSetConfigurationAsDefaultForEnterpriseResponse response =
                api().makeConfigurationDefaultForEnterprise(
                                ENTERPRISE,
                                CONFIGURATION_ID,
                                CodeSecurityCatalogFixtures.setConfigurationAsDefaultForEnterprisePayload());

        assertThat(response).isEqualTo(CodeSecurityCatalogFixtures.setConfigurationAsDefaultForEnterpriseResponse());
    }

    @Test
    void listRepositoriesForConfiguration_shouldSendFilterAndCursor_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(), "[" + json("code-security-configuration-repositories.json") + "]"));

        final GitHubPage<GitHubCodeSecurityConfigurationRepositories> page = api().listRepositoriesForConfiguration(
                        ORG,
                        CONFIGURATION_ID,
                        CodeSecurityCatalogFixtures.getRepositoriesForConfigurationFilter(),
                        GitHubCursorRequest.after("tok", 50));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(CodeSecurityCatalogFixtures.codeSecurityConfigurationRepositories());
            softly.assertThat(requestUri).hasParameter("status", "attached").hasParameter("after", "tok");
        });
    }

    @Test
    void listRepositoriesForConfiguration_shouldUseDefaultCursor_whenNoFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listRepositoriesForConfiguration(ORG, CONFIGURATION_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/orgs/octo-org/code-security/configurations/1/repositories?"
                        + TestClients.perPageQuery());
    }

    @Test
    void listRepositoriesForEnterpriseConfiguration_shouldSendFilterAndCursor_whenFilterProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(
                        HttpStatus.OK.code(), "[" + json("code-security-configuration-repositories.json") + "]"));

        final GitHubPage<GitHubCodeSecurityConfigurationRepositories> page =
                api().listRepositoriesForEnterpriseConfiguration(
                                ENTERPRISE,
                                CONFIGURATION_ID,
                                CodeSecurityCatalogFixtures.getRepositoriesForEnterpriseConfigurationFilter(),
                                GitHubCursorRequest.after("tok", 50));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items())
                    .containsExactly(CodeSecurityCatalogFixtures.codeSecurityConfigurationRepositories());
            softly.assertThat(requestUri).hasParameter("status", "attached").hasParameter("after", "tok");
        });
    }

    @Test
    void listRepositoriesForEnterpriseConfiguration_shouldUseDefaultCursor_whenNoFilterProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listRepositoriesForEnterpriseConfiguration(ENTERPRISE, CONFIGURATION_ID);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString(
                        "https://api.github.com/enterprises/octo-enterprise/code-security/configurations/1/repositories?"
                                + TestClients.perPageQuery());
    }

    @Test
    void getConfigurationForRepository_shouldReturnEmpty_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        final Optional<GitHubCodeSecurityConfigurationForRepository> attachment =
                api().getConfigurationForRepository(OWNER, REPO);
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(attachment).isEmpty();
            softly.assertThat(requestUri)
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/code-security-configuration");
        });
    }

    @Test
    void getConfigurationForRepository_shouldReturnAttachment_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-security-configuration-for-repository.json")));

        final Optional<GitHubCodeSecurityConfigurationForRepository> attachment =
                api().getConfigurationForRepository(OWNER, REPO);

        assertThat(attachment).contains(CodeSecurityCatalogFixtures.codeSecurityConfigurationForRepository());
    }

    @Test
    void listConfigurationsForEnterprise_shouldUseDefaultCursor_whenNoRequestProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        api().listConfigurationsForEnterprise(ENTERPRISE);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/enterprises/octo-enterprise/code-security/configurations?"
                        + TestClients.perPageQuery());
    }

    @Test
    void listConfigurationsForEnterprise_shouldSendCursor_whenCursorRequestProvided() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("code-security-configuration.json") + "]"));

        final GitHubPage<GitHubCodeSecurityConfiguration> page =
                api().listConfigurationsForEnterprise(ENTERPRISE, GitHubCursorRequest.after("tok", 50));
        final URI requestUri = captureSingleRequest().uri();

        assertSoftly(softly -> {
            softly.assertThat(page.items()).containsExactly(CodeSecurityCatalogFixtures.codeSecurityConfiguration());
            softly.assertThat(requestUri).hasParameter("after", "tok").hasParameter("per_page", "50");
        });
    }

    @Test
    void getConfiguration_shouldPutLongIdInPath_whenIdExceedsIntRange() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("code-security-configuration.json")));

        api().getConfiguration(ORG, 3_000_000_000L);
        final URI requestUri = captureSingleRequest().uri();

        assertThat(requestUri)
                .hasToString("https://api.github.com/orgs/octo-org/code-security/configurations/3000000000");
    }

    private GitHubCodeSecurityApi api() {
        return new GitHubCodeSecurityApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubCodeSecurityApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
