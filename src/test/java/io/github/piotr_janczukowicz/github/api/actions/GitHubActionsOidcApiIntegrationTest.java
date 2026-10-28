package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.http.HttpMethod.GET;
import static io.github.piotr_janczukowicz.http.HttpMethod.POST;
import static io.github.piotr_janczukowicz.http.HttpMethod.PUT;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.actionsApi;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.createdJson;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.jsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.listJsonBytes;
import static io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport.okJson;
import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.test.http.StubHttpServer;
import io.github.piotr_janczukowicz.test.support.GitHubApiWireMockSupport;
import io.github.piotr_janczukowicz.testsupport.IntegrationTest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

@IntegrationTest
class GitHubActionsOidcApiIntegrationTest {

    private static final String INCLUSIONS_PATH = "/orgs/github/actions/oidc/customization/properties/repo";
    private static final String SUB_TEMPLATE_PATH = "/orgs/github/actions/oidc/customization/sub";
    private static final String REPO_SUB_PATH = "/repos/octocat/Hello-World/actions/oidc/customization/sub";

    @RegisterExtension
    static final StubHttpServer HTTP = new StubHttpServer();

    @Test
    void listOidcCustomPropertyInclusionsForOrg_shouldReturnInclusions_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                INCLUSIONS_PATH,
                okJson(listJsonBytes(
                        GitHubActionsOidcApiIntegrationTest.class, "oidc-custom-property-inclusion.json")));

        final List<GitHubOidcCustomPropertyInclusion> inclusions;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            inclusions = actions.api().oidc().listOidcCustomPropertyInclusionsForOrg("github");
        }

        assertThat(inclusions).containsExactly(ActionsCatalogFixtures.oidcCustomPropertyInclusion());
        HTTP.verifyRequested(GET, INCLUSIONS_PATH);
    }

    @Test
    void createOidcCustomPropertyInclusionForOrg_shouldReturnInclusion_whenResponseIsCreated() throws Exception {
        HTTP.stub(
                POST,
                INCLUSIONS_PATH,
                createdJson(
                        jsonBytes(GitHubActionsOidcApiIntegrationTest.class, "oidc-custom-property-inclusion.json")));

        final GitHubOidcCustomPropertyInclusion inclusion;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            inclusion = actions.api()
                    .oidc()
                    .createOidcCustomPropertyInclusionForOrg(
                            "github", ActionsCatalogFixtures.oidcCustomPropertyInclusionInput());
        }

        assertThat(inclusion).isEqualTo(ActionsCatalogFixtures.oidcCustomPropertyInclusion());
        HTTP.verifyRequested(POST, INCLUSIONS_PATH);
    }

    @Test
    void getOidcCustomSubTemplateForOrg_shouldReturnTemplate_whenResponseIsOk() throws Exception {
        HTTP.stub(
                GET,
                SUB_TEMPLATE_PATH,
                okJson(jsonBytes(GitHubActionsOidcApiIntegrationTest.class, "oidc-custom-sub.json")));

        final GitHubOidcCustomSub template;
        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            template = actions.api().oidc().getOidcCustomSubTemplateForOrg("github");
        }

        assertThat(template).isEqualTo(ActionsCatalogFixtures.oidcCustomSub());
        HTTP.verifyRequested(GET, SUB_TEMPLATE_PATH);
    }

    @Test
    void setCustomOidcSubClaimForRepo_shouldSendPut_whenResponseIsCreated() throws Exception {
        HTTP.stub(PUT, REPO_SUB_PATH, createdJson("{}".getBytes(StandardCharsets.UTF_8)));

        try (final GitHubApiWireMockSupport.ActionsApiClient actions = actionsApi(HTTP)) {
            actions.api()
                    .oidc()
                    .setCustomOidcSubClaimForRepo(
                            "octocat", "Hello-World", ActionsCatalogFixtures.setCustomOidcSubClaimForRepoPayload());
        }

        HTTP.verifyRequested(PUT, REPO_SUB_PATH);
    }
}
