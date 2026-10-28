package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubEnterpriseAdminAdminStatsApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEnterpriseAdminAdminStatsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getAllStats_shouldReturnStatistics_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("enterprise-overview.json")));

        final GitHubEnterpriseOverview statistics = api().getAllStats();

        assertThat(statistics).isEqualTo(EnterpriseAdminCatalogFixtures.enterpriseOverview());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/enterprise/stats/all");
    }

    @Test
    void getCommentStats_shouldReturnStatistics_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("enterprise-comment-overview.json")));

        final GitHubEnterpriseCommentOverview statistics = api().getCommentStats();

        assertThat(statistics).isEqualTo(EnterpriseAdminCatalogFixtures.enterpriseCommentOverview());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/enterprise/stats/comments");
    }

    @Test
    void getGistStats_shouldReturnStatistics_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("enterprise-gist-overview.json")));

        final GitHubEnterpriseGistOverview statistics = api().getGistStats();

        assertThat(statistics).isEqualTo(EnterpriseAdminCatalogFixtures.enterpriseGistOverview());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/enterprise/stats/gists");
    }

    @Test
    void getHooksStats_shouldReturnStatistics_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("enterprise-hook-overview.json")));

        final GitHubEnterpriseHookOverview statistics = api().getHooksStats();

        assertThat(statistics).isEqualTo(EnterpriseAdminCatalogFixtures.enterpriseHookOverview());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/enterprise/stats/hooks");
    }

    @Test
    void getIssueStats_shouldReturnStatistics_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("enterprise-issue-overview.json")));

        final GitHubEnterpriseIssueOverview statistics = api().getIssueStats();

        assertThat(statistics).isEqualTo(EnterpriseAdminCatalogFixtures.enterpriseIssueOverview());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/enterprise/stats/issues");
    }

    @Test
    void getMilestoneStats_shouldReturnStatistics_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("enterprise-milestone-overview.json")));

        final GitHubEnterpriseMilestoneOverview statistics = api().getMilestoneStats();

        assertThat(statistics).isEqualTo(EnterpriseAdminCatalogFixtures.enterpriseMilestoneOverview());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/enterprise/stats/milestones");
    }

    @Test
    void getOrgStats_shouldReturnStatistics_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("enterprise-organization-overview.json")));

        final GitHubEnterpriseOrganizationOverview statistics = api().getOrgStats();

        assertThat(statistics).isEqualTo(EnterpriseAdminCatalogFixtures.enterpriseOrganizationOverview());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/enterprise/stats/orgs");
    }

    @Test
    void getPagesStats_shouldReturnStatistics_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("enterprise-page-overview.json")));

        final GitHubEnterprisePageOverview statistics = api().getPagesStats();

        assertThat(statistics).isEqualTo(EnterpriseAdminCatalogFixtures.enterprisePageOverview());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/enterprise/stats/pages");
    }

    @Test
    void getPullRequestStats_shouldReturnStatistics_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("enterprise-pull-request-overview.json")));

        final GitHubEnterprisePullRequestOverview statistics = api().getPullRequestStats();

        assertThat(statistics).isEqualTo(EnterpriseAdminCatalogFixtures.enterprisePullRequestOverview());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/enterprise/stats/pulls");
    }

    @Test
    void getRepoStats_shouldReturnStatistics_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("enterprise-repository-overview.json")));

        final GitHubEnterpriseRepositoryOverview statistics = api().getRepoStats();

        assertThat(statistics).isEqualTo(EnterpriseAdminCatalogFixtures.enterpriseRepositoryOverview());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/enterprise/stats/repos");
    }

    @Test
    void getSecurityProducts_shouldReturnStatistics_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("enterprise-security-products-overview.json")));

        final GitHubEnterpriseSecurityProductsOverview statistics = api().getSecurityProducts();

        assertThat(statistics).isEqualTo(EnterpriseAdminCatalogFixtures.enterpriseSecurityProductsOverview());
        assertThat(line(captureSingleRequest()))
                .isEqualTo("GET https://api.github.com/enterprise/stats/security-products");
    }

    @Test
    void getUserStats_shouldReturnStatistics_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("enterprise-user-overview.json")));

        final GitHubEnterpriseUserOverview statistics = api().getUserStats();

        assertThat(statistics).isEqualTo(EnterpriseAdminCatalogFixtures.enterpriseUserOverview());
        assertThat(line(captureSingleRequest())).isEqualTo("GET https://api.github.com/enterprise/stats/users");
    }

    private GitHubEnterpriseAdminAdminStatsApi api() {
        return new GitHubEnterpriseAdminAdminStatsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubEnterpriseAdminAdminStatsApiTest.class, fileName);
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static String line(final HttpRequest request) {
        return request.method() + " " + request.uri();
    }
}
