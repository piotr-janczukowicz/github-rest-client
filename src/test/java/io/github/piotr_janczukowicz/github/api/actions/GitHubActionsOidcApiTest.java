package io.github.piotr_janczukowicz.github.api.actions;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.client.exception.GitHubNotFoundException;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import io.github.piotr_janczukowicz.util.FileUtils;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubActionsOidcApiTest {

    private static final String ORG = "github";
    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";
    private static final String ENTERPRISE = "my-enterprise";
    private static final String PROPERTY = "team";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsOidcApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void listOidcCustomPropertyInclusionsForEnterprise_shouldReturnInclusions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("oidc-custom-property-inclusion.json") + "]"));

        final List<GitHubOidcCustomPropertyInclusion> inclusions =
                api().listOidcCustomPropertyInclusionsForEnterprise(ENTERPRISE);

        assertThat(inclusions).containsExactly(ActionsCatalogFixtures.oidcCustomPropertyInclusion());
    }

    @Test
    void createOidcCustomPropertyInclusionForEnterprise_shouldReturnInclusion_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("oidc-custom-property-inclusion.json")));

        final GitHubOidcCustomPropertyInclusion inclusion = api().createOidcCustomPropertyInclusionForEnterprise(
                        ENTERPRISE, ActionsCatalogFixtures.oidcCustomPropertyInclusionInput());

        assertThat(inclusion).isEqualTo(ActionsCatalogFixtures.oidcCustomPropertyInclusion());
    }

    @Test
    void deleteOidcCustomPropertyInclusionForEnterprise_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteOidcCustomPropertyInclusionForEnterprise(ENTERPRISE, PROPERTY);

        assertThat(line(captureSingleRequest()))
                .isEqualTo(
                        "DELETE https://api.github.com/enterprises/my-enterprise/actions/oidc/customization/properties/repo/team");
    }

    @Test
    void listOidcCustomPropertyInclusionsForOrg_shouldReturnInclusions_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), "[" + json("oidc-custom-property-inclusion.json") + "]"));

        final List<GitHubOidcCustomPropertyInclusion> inclusions = api().listOidcCustomPropertyInclusionsForOrg(ORG);

        assertThat(inclusions).containsExactly(ActionsCatalogFixtures.oidcCustomPropertyInclusion());
    }

    @Test
    void createOidcCustomPropertyInclusionForOrg_shouldReturnInclusion_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("oidc-custom-property-inclusion.json")));

        final GitHubOidcCustomPropertyInclusion inclusion = api().createOidcCustomPropertyInclusionForOrg(
                        ORG, ActionsCatalogFixtures.oidcCustomPropertyInclusionInput());

        assertThat(inclusion).isEqualTo(ActionsCatalogFixtures.oidcCustomPropertyInclusion());
    }

    @Test
    void deleteOidcCustomPropertyInclusionForOrg_shouldSendDelete_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().deleteOidcCustomPropertyInclusionForOrg(ORG, PROPERTY);

        assertThat(line(captureSingleRequest()))
                .isEqualTo("DELETE https://api.github.com/orgs/github/actions/oidc/customization/properties/repo/team");
    }

    @Test
    void getOidcCustomSubTemplateForOrg_shouldReturnTemplate_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("oidc-custom-sub.json")));

        final GitHubOidcCustomSub template = api().getOidcCustomSubTemplateForOrg(ORG);

        assertThat(template).isEqualTo(ActionsCatalogFixtures.oidcCustomSub());
    }

    @Test
    void updateOidcCustomSubTemplateForOrg_shouldSendPut_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().updateOidcCustomSubTemplateForOrg(ORG, ActionsCatalogFixtures.updateOidcCustomSubTemplateForOrgPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT https://api.github.com/orgs/github/actions/oidc/customization/sub");
    }

    @Test
    void getCustomOidcSubClaimForRepo_shouldReturnClaim_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), json("oidc-custom-sub-repo.json")));

        final GitHubOidcCustomSubRepo claim = api().getCustomOidcSubClaimForRepo(OWNER, REPO);

        assertThat(claim).isEqualTo(ActionsCatalogFixtures.oidcCustomSubRepo());
    }

    @Test
    void getCustomOidcSubClaimForRepo_shouldThrowNotFound_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Throwable thrown = catchThrowable(() -> api().getCustomOidcSubClaimForRepo(OWNER, REPO));

        assertThat(thrown).isInstanceOf(GitHubNotFoundException.class);
    }

    @Test
    void setCustomOidcSubClaimForRepo_shouldSendPut_whenResponseIsCreated() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.CREATED.code(), null));

        api().setCustomOidcSubClaimForRepo(OWNER, REPO, ActionsCatalogFixtures.setCustomOidcSubClaimForRepoPayload());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT https://api.github.com/repos/octocat/Hello-World/actions/oidc/customization/sub");
    }

    @Test
    void setOidcCustomIssuerPolicyForEnterprise_shouldSendPut_whenPayloadProvided() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setOidcCustomIssuerPolicyForEnterprise(
                        ENTERPRISE, ActionsCatalogFixtures.actionsOidcCustomIssuerPolicyForEnterprise());

        assertThat(line(captureSingleRequest()))
                .isEqualTo("PUT https://api.github.com/enterprises/my-enterprise/actions/oidc/customization/issuer");
    }

    private GitHubActionsOidcApi api() {
        return new GitHubActionsOidcApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubActionsOidcApiTest.class, fileName);
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
