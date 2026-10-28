package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
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
class GitHubEnterpriseAdminOrgsApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubEnterpriseAdminOrgsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void createOrg_shouldPostAndReturnOrganization_whenResponseIsCreated() throws Exception {
        when(transport.execute(any()))
                .thenReturn(response(HttpStatus.CREATED.code(), json("organization-simple.json")));

        final GitHubOrganizationSimple organization =
                api().createOrg(EnterpriseAdminCatalogFixtures.createOrgPayload());

        assertThat(organization).isEqualTo(OrganizationsCatalogFixtures.organizationSimple());
        assertThat(line(captureSingleRequest())).isEqualTo("POST https://api.github.com/admin/organizations");
    }

    @Test
    void createOrg_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminOrgsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createOrg(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    @Test
    void updateOrgName_shouldPatchAndReturnAcknowledgement_whenResponseIsAccepted() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.ACCEPTED.code(), json("update-org-name.json")));

        final GitHubUpdateOrgName result =
                api().updateOrgName("github", EnterpriseAdminCatalogFixtures.updateOrgNamePayload());

        assertThat(result).isEqualTo(EnterpriseAdminCatalogFixtures.updateOrgName());
        assertThat(line(captureSingleRequest())).isEqualTo("PATCH https://api.github.com/admin/organizations/github");
    }

    @Test
    void updateOrgName_shouldThrow_whenPayloadNull() {
        final GitHubEnterpriseAdminOrgsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.updateOrgName("github", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("payload");
    }

    private GitHubEnterpriseAdminOrgsApi api() {
        return new GitHubEnterpriseAdminOrgsApi(TestClients.caller(transport));
    }

    private static String json(final String fileName) {
        return FileUtils.readString(GitHubEnterpriseAdminOrgsApiTest.class, fileName);
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
