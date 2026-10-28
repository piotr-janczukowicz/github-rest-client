package io.github.piotr_janczukowicz.github.api.dependencygraph;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.http.HttpMethod;
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
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubDependencyGraphLicenseComplianceApiTest {

    private static final String ENTERPRISE = "octo-enterprise";
    private static final String POLICY_ID = "policy-1";
    private static final String OWNER = "octocat";
    private static final String REPO = "Hello-World";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDependencyGraphLicenseComplianceApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getEnterpriseLicensePolicy_shouldReturnPolicy_whenResponseIsOk() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.OK.code(), json("enterprise-license-policy-response.json")));

        final Optional<GitHubEnterpriseLicensePolicyResponse> policy =
                api().getEnterpriseLicensePolicy(ENTERPRISE, POLICY_ID);
        final HttpRequest sent = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(policy).contains(DependencyGraphCatalogFixtures.enterpriseLicensePolicyResponse());
            softly.assertThat(sent.method()).isEqualTo(HttpMethod.GET);
            softly.assertThat(sent.uri())
                    .hasToString(
                            "https://api.github.com/enterprises/octo-enterprise/license-compliance/policies/policy-1");
        });
    }

    @Test
    void getEnterpriseLicensePolicy_shouldReturnEmpty_whenResponseIsNotFound() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NOT_FOUND.code(), null));

        final Optional<GitHubEnterpriseLicensePolicyResponse> policy =
                api().getEnterpriseLicensePolicy(ENTERPRISE, POLICY_ID);

        assertThat(policy).isEmpty();
    }

    @Test
    void setEnterpriseLicensePolicy_shouldPutPayload_whenCalled() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().setEnterpriseLicensePolicy(
                        ENTERPRISE, POLICY_ID, DependencyGraphCatalogFixtures.enterpriseLicensePolicyItem());
        final HttpRequest sent = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(sent.method()).isEqualTo(HttpMethod.PUT);
            softly.assertThat(sent.uri())
                    .hasToString(
                            "https://api.github.com/enterprises/octo-enterprise/license-compliance/policies/policy-1");
        });
    }

    @Test
    void setEnterpriseLicensePolicy_shouldThrow_whenPayloadNull() {
        final GitHubDependencyGraphLicenseComplianceApi api = api();

        final Throwable thrown = catchThrowable(() -> api.setEnterpriseLicensePolicy(ENTERPRISE, POLICY_ID, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void checkLicenseComplianceForPackages_shouldPostPayloadAndDecodeResult_whenCalled() throws Exception {
        when(transport.execute(any()))
                .thenReturn(
                        response(HttpStatus.OK.code(), json("check-license-compliance-for-packages-response.json")));

        final GitHubCheckLicenseComplianceForPackagesResponse response = api().checkLicenseComplianceForPackages(
                        OWNER, REPO, DependencyGraphCatalogFixtures.checkLicenseComplianceForPackagesPayload());
        final HttpRequest sent = captureSingleRequest();

        assertSoftly(softly -> {
            softly.assertThat(response)
                    .isEqualTo(DependencyGraphCatalogFixtures.checkLicenseComplianceForPackagesResponse());
            softly.assertThat(sent.method()).isEqualTo(HttpMethod.POST);
            softly.assertThat(sent.uri())
                    .hasToString("https://api.github.com/repos/octocat/Hello-World/license-compliance/package-checks");
        });
    }

    @Test
    void checkLicenseComplianceForPackages_shouldThrow_whenPayloadNull() {
        final GitHubDependencyGraphLicenseComplianceApi api = api();

        final Throwable thrown = catchThrowable(() -> api.checkLicenseComplianceForPackages(OWNER, REPO, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    private GitHubDependencyGraphLicenseComplianceApi api() {
        return new GitHubDependencyGraphLicenseComplianceApi(TestClients.caller(transport));
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubDependencyGraphLicenseComplianceApiTest.class, fileName);
    }
}
