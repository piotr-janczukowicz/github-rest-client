package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.test.http.StubHttpServer.status;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminTokenInventoryApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.notFound;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.pagination.GitHubPage;
import io.github.piotr_janczukowicz.github.pagination.GitHubPageRequest;
import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.net.URI;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminTokenInventoryApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminTokenInventoryApiIntegrationTest.class;
    private static final String CREDENTIALS_PATH = "/enterprises/octo-enterprise/credentials";
    private static final String ITEM_PATH = CREDENTIALS_PATH + "/inv-1";
    private static final String EXPORTS_PATH = CREDENTIALS_PATH + "/exports";
    private static final String EXPORT_PATH = EXPORTS_PATH + "/export-1";
    private static final String ITEM_LIST_JSON = "enterprise-token-inventory-item-list.json";
    private static final String ITEM_JSON = "enterprise-token-inventory-item.json";
    private static final String EXPORT_JSON = "enterprise-token-inventory-export.json";
    private static final String DOWNLOAD_URL = "https://downloads.example.com/token-inventory.csv";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listEnterpriseTokenInventory_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, CREDENTIALS_PATH, okJson(jsonBytes(RESOURCES, ITEM_LIST_JSON)));

        final GitHubPage<GitHubEnterpriseTokenInventoryItem> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminTokenInventoryApiClient api =
                enterpriseAdminTokenInventoryApi(HTTP)) {
            page = api.api().listEnterpriseTokenInventory(ENTERPRISE);
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.enterpriseTokenInventoryItem());
        HTTP.verifyRequested(GET, CREDENTIALS_PATH);
    }

    @Test
    void listEnterpriseTokenInventoryWithFilter_shouldReturnPage_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, CREDENTIALS_PATH, okJson(jsonBytes(RESOURCES, ITEM_LIST_JSON)));

        final GitHubPage<GitHubEnterpriseTokenInventoryItem> page;
        try (final GitHubApiWireMockSupport.EnterpriseAdminTokenInventoryApiClient api =
                enterpriseAdminTokenInventoryApi(HTTP)) {
            page = api.api()
                    .listEnterpriseTokenInventory(
                            ENTERPRISE,
                            EnterpriseAdminCatalogFixtures.listEnterpriseTokenInventoryFilter(),
                            GitHubPageRequest.byCursor());
        }

        assertThat(page.items()).containsExactly(EnterpriseAdminCatalogFixtures.enterpriseTokenInventoryItem());
        HTTP.verifyRequested(GET, CREDENTIALS_PATH);
    }

    @Test
    void createEnterpriseTokenInventoryExport_shouldReturnExport_whenResponseIsAccepted() throws Exception {
        HTTP.stub(POST, EXPORTS_PATH, okJson(jsonBytes(RESOURCES, EXPORT_JSON)));

        final GitHubEnterpriseTokenInventoryExport export;
        try (final GitHubApiWireMockSupport.EnterpriseAdminTokenInventoryApiClient api =
                enterpriseAdminTokenInventoryApi(HTTP)) {
            export = api.api()
                    .createEnterpriseTokenInventoryExport(
                            ENTERPRISE, EnterpriseAdminCatalogFixtures.createEnterpriseTokenInventoryExportPayload());
        }

        assertThat(export).isEqualTo(EnterpriseAdminCatalogFixtures.enterpriseTokenInventoryExport());
        HTTP.verifyRequested(POST, EXPORTS_PATH);
    }

    @Test
    void getEnterpriseTokenInventoryExport_shouldReturnExport_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, EXPORT_PATH, okJson(jsonBytes(RESOURCES, EXPORT_JSON)));

        final Optional<GitHubEnterpriseTokenInventoryExport> export;
        try (final GitHubApiWireMockSupport.EnterpriseAdminTokenInventoryApiClient api =
                enterpriseAdminTokenInventoryApi(HTTP)) {
            export = api.api().getEnterpriseTokenInventoryExport(ENTERPRISE, "export-1");
        }

        assertThat(export).contains(EnterpriseAdminCatalogFixtures.enterpriseTokenInventoryExport());
        HTTP.verifyRequested(GET, EXPORT_PATH);
    }

    @Test
    void getEnterpriseTokenInventoryExportLocation_shouldReturnLocation_whenResponseIsRedirect() throws Exception {
        HTTP.stub(GET, EXPORT_PATH, status(302).header("Location", DOWNLOAD_URL));

        final URI location;
        try (final GitHubApiWireMockSupport.EnterpriseAdminTokenInventoryApiClient api =
                enterpriseAdminTokenInventoryApi(HTTP)) {
            location = api.api().getEnterpriseTokenInventoryExportLocation(ENTERPRISE, "export-1");
        }

        assertThat(location).isEqualTo(URI.create(DOWNLOAD_URL));
        HTTP.verifyRequested(GET, EXPORT_PATH);
    }

    @Test
    void getEnterpriseTokenInventoryItem_shouldReturnItem_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, ITEM_PATH, okJson(jsonBytes(RESOURCES, ITEM_JSON)));

        final Optional<GitHubEnterpriseTokenInventoryItem> item;
        try (final GitHubApiWireMockSupport.EnterpriseAdminTokenInventoryApiClient api =
                enterpriseAdminTokenInventoryApi(HTTP)) {
            item = api.api().getEnterpriseTokenInventoryItem(ENTERPRISE, "inv-1");
        }

        assertThat(item).contains(EnterpriseAdminCatalogFixtures.enterpriseTokenInventoryItem());
        HTTP.verifyRequested(GET, ITEM_PATH);
    }

    @Test
    void getEnterpriseTokenInventoryItem_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        HTTP.stub(GET, ITEM_PATH, notFound());

        final Optional<GitHubEnterpriseTokenInventoryItem> item;
        try (final GitHubApiWireMockSupport.EnterpriseAdminTokenInventoryApiClient api =
                enterpriseAdminTokenInventoryApi(HTTP)) {
            item = api.api().getEnterpriseTokenInventoryItem(ENTERPRISE, "inv-1");
        }

        assertThat(item).isEmpty();
    }
}
