package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.DELETE;
import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminLicensingApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.billing.BillingCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.billing.GitHubAdvancedSecurityActiveCommitters;
import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminLicensingApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminLicensingApiIntegrationTest.class;
    private static final String LICENSE_PATH = "/enterprise/settings/license";
    private static final String CONSUMED_LICENSES_PATH = "/enterprises/octo-enterprise/consumed-licenses";
    private static final String LICENSE_SYNC_STATUS_PATH = "/enterprises/octo-enterprise/license-sync-status";
    private static final String ADVANCED_SECURITY_PATH =
            "/enterprises/octo-enterprise/settings/billing/advanced-security";
    private static final String VISUAL_STUDIO_SUBSCRIPTIONS_PATH =
            "/enterprises/octo-enterprise/visual-studio-subscriptions";
    private static final String VISUAL_STUDIO_SUBSCRIPTION_PATH =
            "/enterprises/octo-enterprise/visual-studio-subscriptions/1";
    private static final String CONSUMED_LICENSES_JSON = "get-consumed-licenses.json";
    private static final String LICENSE_SYNC_STATUS_JSON = "get-license-sync-status.json";
    private static final String ADVANCED_SECURITY_JSON = "advanced-security-active-committers.json";
    private static final String VISUAL_STUDIO_SUBSCRIPTIONS_JSON = "visual-studio-subscriptions-page.json";
    private static final String VISUAL_STUDIO_SUBSCRIPTION_JSON = "visual-studio-subscription-assignment.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getLicenseInformation_shouldReturnLicense_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, LICENSE_PATH, okJson(jsonBytes(RESOURCES, "license-info.json")));

        final GitHubLicenseInfo license;
        try (final GitHubApiWireMockSupport.EnterpriseAdminLicensingApiClient api = enterpriseAdminLicensingApi(HTTP)) {
            license = api.api().getLicenseInformation();
        }

        assertThat(license).isEqualTo(EnterpriseAdminCatalogFixtures.licenseInfo());
        HTTP.verifyRequested(GET, LICENSE_PATH);
    }

    @Test
    void getConsumedLicenses_shouldReturnLicenses_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, CONSUMED_LICENSES_PATH, okJson(jsonBytes(RESOURCES, CONSUMED_LICENSES_JSON)));

        final GitHubGetConsumedLicenses licenses;
        try (final GitHubApiWireMockSupport.EnterpriseAdminLicensingApiClient api = enterpriseAdminLicensingApi(HTTP)) {
            licenses = api.api().getConsumedLicenses(ENTERPRISE);
        }

        assertThat(licenses).isEqualTo(EnterpriseAdminCatalogFixtures.getConsumedLicenses());
        HTTP.verifyRequested(GET, CONSUMED_LICENSES_PATH);
    }

    @Test
    void getConsumedLicensesWithPageRequest_shouldReturnLicenses_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, CONSUMED_LICENSES_PATH, okJson(jsonBytes(RESOURCES, CONSUMED_LICENSES_JSON)));

        final GitHubGetConsumedLicenses licenses;
        try (final GitHubApiWireMockSupport.EnterpriseAdminLicensingApiClient api = enterpriseAdminLicensingApi(HTTP)) {
            licenses = api.api().getConsumedLicenses(ENTERPRISE, GitHubPageRequest.byPage());
        }

        assertThat(licenses).isEqualTo(EnterpriseAdminCatalogFixtures.getConsumedLicenses());
        HTTP.verifyRequested(GET, CONSUMED_LICENSES_PATH);
    }

    @Test
    void getLicenseSyncStatus_shouldReturnStatus_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, LICENSE_SYNC_STATUS_PATH, okJson(jsonBytes(RESOURCES, LICENSE_SYNC_STATUS_JSON)));

        final GitHubGetLicenseSyncStatus status;
        try (final GitHubApiWireMockSupport.EnterpriseAdminLicensingApiClient api = enterpriseAdminLicensingApi(HTTP)) {
            status = api.api().getLicenseSyncStatus(ENTERPRISE);
        }

        assertThat(status).isEqualTo(EnterpriseAdminCatalogFixtures.getLicenseSyncStatus());
        HTTP.verifyRequested(GET, LICENSE_SYNC_STATUS_PATH);
    }

    @Test
    void getAdvancedSecurityBilling_shouldReturnCommitters_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ADVANCED_SECURITY_PATH, okJson(jsonBytes(RESOURCES, ADVANCED_SECURITY_JSON)));

        final GitHubAdvancedSecurityActiveCommitters committers;
        try (final GitHubApiWireMockSupport.EnterpriseAdminLicensingApiClient api = enterpriseAdminLicensingApi(HTTP)) {
            committers = api.api().getAdvancedSecurityBilling(ENTERPRISE);
        }

        assertThat(committers).isEqualTo(BillingCatalogFixtures.advancedSecurityActiveCommitters());
        HTTP.verifyRequested(GET, ADVANCED_SECURITY_PATH);
    }

    @Test
    void getAdvancedSecurityBillingWithFilter_shouldReturnCommitters_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ADVANCED_SECURITY_PATH, okJson(jsonBytes(RESOURCES, ADVANCED_SECURITY_JSON)));

        final GitHubAdvancedSecurityActiveCommitters committers;
        try (final GitHubApiWireMockSupport.EnterpriseAdminLicensingApiClient api = enterpriseAdminLicensingApi(HTTP)) {
            committers = api.api()
                    .getAdvancedSecurityBilling(
                            ENTERPRISE,
                            EnterpriseAdminCatalogFixtures.getGithubAdvancedSecurityBillingGheFilter(),
                            GitHubPageRequest.byPage());
        }

        assertThat(committers).isEqualTo(BillingCatalogFixtures.advancedSecurityActiveCommitters());
        HTTP.verifyRequested(GET, ADVANCED_SECURITY_PATH);
    }

    @Test
    void listVisualStudioSubscriptions_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, VISUAL_STUDIO_SUBSCRIPTIONS_PATH, okJson(jsonBytes(RESOURCES, VISUAL_STUDIO_SUBSCRIPTIONS_JSON)));

        final GitHubPage<GitHubVisualStudioSubscriptionAssignment> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminLicensingApiClient api = enterpriseAdminLicensingApi(HTTP)) {
            page = api.api().listVisualStudioSubscriptions(ENTERPRISE);
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.visualStudioSubscriptionAssignment());
        HTTP.verifyRequested(GET, VISUAL_STUDIO_SUBSCRIPTIONS_PATH);
    }

    @Test
    void listVisualStudioSubscriptionsWithFilter_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET, VISUAL_STUDIO_SUBSCRIPTIONS_PATH, okJson(jsonBytes(RESOURCES, VISUAL_STUDIO_SUBSCRIPTIONS_JSON)));

        final GitHubPage<GitHubVisualStudioSubscriptionAssignment> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminLicensingApiClient api = enterpriseAdminLicensingApi(HTTP)) {
            page = api.api()
                    .listVisualStudioSubscriptions(
                            ENTERPRISE, EnterpriseAdminCatalogFixtures.listVssFilter(), GitHubPageRequest.byPage());
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.visualStudioSubscriptionAssignment());
        HTTP.verifyRequested(GET, VISUAL_STUDIO_SUBSCRIPTIONS_PATH);
    }

    @Test
    void updateVisualStudioSubscriptionManualMatch_shouldReturnAssignment_whenResponseIsOk() throws Exception {
        HTTP.stub(PUT, VISUAL_STUDIO_SUBSCRIPTION_PATH, okJson(jsonBytes(RESOURCES, VISUAL_STUDIO_SUBSCRIPTION_JSON)));

        final GitHubVisualStudioSubscriptionAssignment assignment;
        try (final GitHubApiWireMockSupport.EnterpriseAdminLicensingApiClient api = enterpriseAdminLicensingApi(HTTP)) {
            assignment = api.api()
                    .updateVisualStudioSubscriptionManualMatch(
                            ENTERPRISE, "1", EnterpriseAdminCatalogFixtures.updateVssManualMatchPayload());
        }

        assertThat(assignment).isEqualTo(EnterpriseAdminCatalogFixtures.visualStudioSubscriptionAssignment());
        HTTP.verifyRequested(PUT, VISUAL_STUDIO_SUBSCRIPTION_PATH);
    }

    @Test
    void deleteVisualStudioSubscriptionManualMatch_shouldReturnAssignment_whenResponseIsOk() throws Exception {
        HTTP.stub(
                DELETE, VISUAL_STUDIO_SUBSCRIPTION_PATH, okJson(jsonBytes(RESOURCES, VISUAL_STUDIO_SUBSCRIPTION_JSON)));

        final GitHubVisualStudioSubscriptionAssignment assignment;
        try (final GitHubApiWireMockSupport.EnterpriseAdminLicensingApiClient api = enterpriseAdminLicensingApi(HTTP)) {
            assignment = api.api().deleteVisualStudioSubscriptionManualMatch(ENTERPRISE, "1");
        }

        assertThat(assignment).isEqualTo(EnterpriseAdminCatalogFixtures.visualStudioSubscriptionAssignment());
        HTTP.verifyRequested(DELETE, VISUAL_STUDIO_SUBSCRIPTION_PATH);
    }
}
