package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.enterpriseAdminAdminStatsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubEnterpriseAdminAdminStatsApiIntegrationTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final Class<?> RESOURCES = GitHubEnterpriseAdminAdminStatsApiIntegrationTest.class;
    private static final String SERVER_STATISTICS_PATH = "/enterprise-installation/octo-enterprise/server-statistics";
    private static final String SERVER_STATISTICS_JSON = "server-statistics-list.json";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void getServerStatistics_shouldReturnStatistics_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, SERVER_STATISTICS_PATH, okJson(jsonBytes(RESOURCES, SERVER_STATISTICS_JSON)));

        final List<GitHubServerStatistics> statistics;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAdminStatsApiClient api =
                enterpriseAdminAdminStatsApi(HTTP)) {
            statistics = api.api().getServerStatistics(ENTERPRISE);
        }

        assertThat(statistics).containsExactly(EnterpriseAdminCatalogFixtures.serverStatistics());
        HTTP.verifyRequested(GET, SERVER_STATISTICS_PATH);
    }

    @Test
    void getServerStatisticsWithFilter_shouldReturnStatistics_whenResponseIsOk() throws Exception {
        HTTP.stub(GET, SERVER_STATISTICS_PATH, okJson(jsonBytes(RESOURCES, SERVER_STATISTICS_JSON)));

        final List<GitHubServerStatistics> statistics;
        try (final GitHubApiWireMockSupport.EnterpriseAdminAdminStatsApiClient api =
                enterpriseAdminAdminStatsApi(HTTP)) {
            statistics = api.api()
                    .getServerStatistics(ENTERPRISE, EnterpriseAdminCatalogFixtures.getServerStatisticsFilter());
        }

        assertThat(statistics).containsExactly(EnterpriseAdminCatalogFixtures.serverStatistics());
        HTTP.verifyRequested(GET, SERVER_STATISTICS_PATH);
    }
}
