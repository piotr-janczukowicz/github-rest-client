package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.client.GitHubClient;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationsApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void organization_shouldReturnOrganizationApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationApi organization = api.organization();

            assertThat(organization).isNotNull();
        }
    }

    @Test
    void apiInsights_shouldReturnApiInsightsApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsApiInsightsApi apiInsights = api.apiInsights();

            assertThat(apiInsights).isNotNull();
        }
    }

    @Test
    void artifactMetadata_shouldReturnArtifactMetadataApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsArtifactMetadataApi artifactMetadata = api.artifactMetadata();

            assertThat(artifactMetadata).isNotNull();
        }
    }

    @Test
    void attestations_shouldReturnAttestationsApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsAttestationsApi attestations = api.attestations();

            assertThat(attestations).isNotNull();
        }
    }

    @Test
    void blocking_shouldReturnBlockingApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsBlockingApi blocking = api.blocking();

            assertThat(blocking).isNotNull();
        }
    }

    @Test
    void customProperties_shouldReturnCustomPropertiesApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsCustomPropertiesApi customProperties = api.customProperties();

            assertThat(customProperties).isNotNull();
        }
    }

    @Test
    void customPropertiesForOrgs_shouldReturnCustomPropertiesForOrgsApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsCustomPropertiesForOrgsApi customPropertiesForOrgs = api.customPropertiesForOrgs();

            assertThat(customPropertiesForOrgs).isNotNull();
        }
    }

    @Test
    void customRoles_shouldReturnCustomRolesApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsCustomRolesApi customRoles = api.customRoles();

            assertThat(customRoles).isNotNull();
        }
    }

    @Test
    void bypassRequests_shouldReturnBypassRequestsApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsBypassRequestsApi bypassRequests = api.bypassRequests();

            assertThat(bypassRequests).isNotNull();
        }
    }

    @Test
    void issueFields_shouldReturnIssueFieldsApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsIssueFieldsApi issueFields = api.issueFields();

            assertThat(issueFields).isNotNull();
        }
    }

    @Test
    void issueTypes_shouldReturnIssueTypesApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsIssueTypesApi issueTypes = api.issueTypes();

            assertThat(issueTypes).isNotNull();
        }
    }

    @Test
    void members_shouldReturnMembersApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsMembersApi members = api.members();

            assertThat(members).isNotNull();
        }
    }

    @Test
    void networkConfigurations_shouldReturnNetworkConfigurationsApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsNetworkConfigurationsApi networkConfigurations = api.networkConfigurations();

            assertThat(networkConfigurations).isNotNull();
        }
    }

    @Test
    void organizationRoles_shouldReturnOrganizationRolesApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsOrganizationRolesApi organizationRoles = api.organizationRoles();

            assertThat(organizationRoles).isNotNull();
        }
    }

    @Test
    void outsideCollaborators_shouldReturnOutsideCollaboratorsApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsOutsideCollaboratorsApi outsideCollaborators = api.outsideCollaborators();

            assertThat(outsideCollaborators).isNotNull();
        }
    }

    @Test
    void personalAccessTokens_shouldReturnPersonalAccessTokensApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsPersonalAccessTokensApi personalAccessTokens = api.personalAccessTokens();

            assertThat(personalAccessTokens).isNotNull();
        }
    }

    @Test
    void ruleSuites_shouldReturnRuleSuitesApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsRuleSuitesApi ruleSuites = api.ruleSuites();

            assertThat(ruleSuites).isNotNull();
        }
    }

    @Test
    void rules_shouldReturnRulesApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsRulesApi rules = api.rules();

            assertThat(rules).isNotNull();
        }
    }

    @Test
    void securityManagers_shouldReturnSecurityManagersApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsSecurityManagersApi securityManagers = api.securityManagers();

            assertThat(securityManagers).isNotNull();
        }
    }

    @Test
    void webhooks_shouldReturnWebhooksApi_whenCalled() {
        try (final GitHubClient client = TestClients.client(transport)) {
            final GitHubOrganizationsApi api = new GitHubOrganizationsApi(TestClients.caller(client));

            final GitHubOrganizationsWebhooksApi webhooks = api.webhooks();

            assertThat(webhooks).isNotNull();
        }
    }
}
