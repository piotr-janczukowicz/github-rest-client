package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminCredentialAuthorizationsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminCredentialAuthorizationsApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminCredentialAuthorizationsApiIntegrationTest.class;
    private static final String CREDENTIAL_AUTHORIZATIONS_PATH =
            "/enterprises/octo-enterprise/credential-authorizations";
    private static final String REVOKE_ALL_PATH = "/enterprises/octo-enterprise/credential-authorizations/revoke-all";
    private static final String REVOKE_CREDENTIAL_TYPE_PATH =
            "/enterprises/octo-enterprise/credential-authorizations/revoke-credential-type";
    private static final String REVOKE_USER_PATH =
            "/enterprises/octo-enterprise/credential-authorizations/octocat/revoke";
    private static final String REVOKE_USER_CREDENTIAL_TYPE_PATH =
            "/enterprises/octo-enterprise/credential-authorizations/octocat/revoke-credential-type";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void grantCredentialAuthorizations_shouldReturnResponse_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                CREDENTIAL_AUTHORIZATIONS_PATH,
                okJson(jsonBytes(RESOURCES, "grant-credential-authorizations-response-classic-pat.json")));

        final GitHubGrantCredentialAuthorizationsResponse response;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCredentialAuthorizationsApiClient api =
                enterpriseAdminCredentialAuthorizationsApi(HTTP)) {
            response = api.api()
                    .grantCredentialAuthorizations(
                            ENTERPRISE, EnterpriseAdminCatalogFixtures.grantCredentialAuthorizationsPayload());
        }

        assertThat(response)
                .isEqualTo(EnterpriseAdminCatalogFixtures.grantCredentialAuthorizationsClassicPatResponse());
        HTTP.verifyRequested(POST, CREDENTIAL_AUTHORIZATIONS_PATH);
    }

    @Test
    void revokeDelegatedCredentialAuthorizations_shouldReturnResponse_whenResponseIsAccepted() throws Exception {
        HTTP.stub(
                DELETE,
                CREDENTIAL_AUTHORIZATIONS_PATH,
                okJson(jsonBytes(RESOURCES, "revoke-delegated-credential-authorizations-response.json")));

        final GitHubRevokeDelegatedCredentialAuthorizationsResponse response;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCredentialAuthorizationsApiClient api =
                enterpriseAdminCredentialAuthorizationsApi(HTTP)) {
            response = api.api()
                    .revokeDelegatedCredentialAuthorizations(
                            ENTERPRISE, EnterpriseAdminCatalogFixtures.revokeDelegatedCredentialAuthorizationsFilter());
        }

        assertThat(response)
                .isEqualTo(EnterpriseAdminCatalogFixtures.revokeDelegatedCredentialAuthorizationsResponse());
        HTTP.verifyRequested(DELETE, CREDENTIAL_AUTHORIZATIONS_PATH);
    }

    @Test
    void revokeAllCredentialAuthorizations_shouldReturnResponse_whenPayloadIsSent() throws Exception {
        HTTP.stub(
                POST,
                REVOKE_ALL_PATH,
                okJson(jsonBytes(RESOURCES, "revoke-all-credential-authorizations-response.json")));

        final GitHubRevokeAllCredentialAuthorizationsResponse response;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCredentialAuthorizationsApiClient api =
                enterpriseAdminCredentialAuthorizationsApi(HTTP)) {
            response = api.api()
                    .revokeAllCredentialAuthorizations(
                            ENTERPRISE, EnterpriseAdminCatalogFixtures.revokeAllCredentialAuthorizationsPayload());
        }

        assertThat(response).isEqualTo(EnterpriseAdminCatalogFixtures.revokeAllCredentialAuthorizationsResponse());
        HTTP.verifyRequested(POST, REVOKE_ALL_PATH);
    }

    @Test
    void revokeAllCredentialAuthorizations_shouldReturnResponse_whenNoPayloadIsSent() throws Exception {
        HTTP.stub(
                POST,
                REVOKE_ALL_PATH,
                okJson(jsonBytes(RESOURCES, "revoke-all-credential-authorizations-response.json")));

        final GitHubRevokeAllCredentialAuthorizationsResponse response;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCredentialAuthorizationsApiClient api =
                enterpriseAdminCredentialAuthorizationsApi(HTTP)) {
            response = api.api().revokeAllCredentialAuthorizations(ENTERPRISE, null);
        }

        assertThat(response).isEqualTo(EnterpriseAdminCatalogFixtures.revokeAllCredentialAuthorizationsResponse());
        HTTP.verifyRequested(POST, REVOKE_ALL_PATH);
    }

    @Test
    void revokeCredentialTypeForEnterprise_shouldReturnResponse_whenResponseIsAccepted() throws Exception {
        HTTP.stub(
                POST,
                REVOKE_CREDENTIAL_TYPE_PATH,
                okJson(jsonBytes(RESOURCES, "revoke-credential-type-for-enterprise-response.json")));

        final GitHubRevokeCredentialTypeForEnterpriseResponse response;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCredentialAuthorizationsApiClient api =
                enterpriseAdminCredentialAuthorizationsApi(HTTP)) {
            response = api.api()
                    .revokeCredentialTypeForEnterprise(
                            ENTERPRISE, EnterpriseAdminCatalogFixtures.revokeCredentialTypeForEnterprisePayload());
        }

        assertThat(response).isEqualTo(EnterpriseAdminCatalogFixtures.revokeCredentialTypeForEnterpriseResponse());
        HTTP.verifyRequested(POST, REVOKE_CREDENTIAL_TYPE_PATH);
    }

    @Test
    void revokeCredentialAuthorizationsForUser_shouldReturnResponse_whenPayloadIsSent() throws Exception {
        HTTP.stub(
                POST,
                REVOKE_USER_PATH,
                okJson(jsonBytes(RESOURCES, "revoke-credential-authorizations-for-user-response.json")));

        final GitHubRevokeCredentialAuthorizationsForUserResponse response;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCredentialAuthorizationsApiClient api =
                enterpriseAdminCredentialAuthorizationsApi(HTTP)) {
            response = api.api()
                    .revokeCredentialAuthorizationsForUser(
                            ENTERPRISE,
                            "octocat",
                            EnterpriseAdminCatalogFixtures.revokeCredentialAuthorizationsForUserPayload());
        }

        assertThat(response).isEqualTo(EnterpriseAdminCatalogFixtures.revokeCredentialAuthorizationsForUserResponse());
        HTTP.verifyRequested(POST, REVOKE_USER_PATH);
    }

    @Test
    void revokeCredentialAuthorizationsForUser_shouldReturnResponse_whenNoPayloadIsSent() throws Exception {
        HTTP.stub(
                POST,
                REVOKE_USER_PATH,
                okJson(jsonBytes(RESOURCES, "revoke-credential-authorizations-for-user-response.json")));

        final GitHubRevokeCredentialAuthorizationsForUserResponse response;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCredentialAuthorizationsApiClient api =
                enterpriseAdminCredentialAuthorizationsApi(HTTP)) {
            response = api.api().revokeCredentialAuthorizationsForUser(ENTERPRISE, "octocat", null);
        }

        assertThat(response).isEqualTo(EnterpriseAdminCatalogFixtures.revokeCredentialAuthorizationsForUserResponse());
        HTTP.verifyRequested(POST, REVOKE_USER_PATH);
    }

    @Test
    void revokeCredentialTypeForUser_shouldReturnResponse_whenResponseIsAccepted() throws Exception {
        HTTP.stub(
                POST,
                REVOKE_USER_CREDENTIAL_TYPE_PATH,
                okJson(jsonBytes(RESOURCES, "revoke-credential-type-for-user-response.json")));

        final GitHubRevokeCredentialTypeForUserResponse response;
        try (final GitHubApiWireMockSupport.EnterpriseAdminCredentialAuthorizationsApiClient api =
                enterpriseAdminCredentialAuthorizationsApi(HTTP)) {
            response = api.api()
                    .revokeCredentialTypeForUser(
                            ENTERPRISE, "octocat", EnterpriseAdminCatalogFixtures.revokeCredentialTypeForUserPayload());
        }

        assertThat(response).isEqualTo(EnterpriseAdminCatalogFixtures.revokeCredentialTypeForUserResponse());
        HTTP.verifyRequested(POST, REVOKE_USER_CREDENTIAL_TYPE_PATH);
    }
}
