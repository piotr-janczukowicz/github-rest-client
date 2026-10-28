package io.github.piotr_janczukowicz.github.api.organizations;

import static io.github.piotr_janczukowicz.test.http.HttpResponses.response;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubCustomPropertyValue;
import io.github.piotr_janczukowicz.http.HttpMethod;
import io.github.piotr_janczukowicz.http.HttpRequest;
import io.github.piotr_janczukowicz.http.HttpStatus;
import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

@UnitTest
class GitHubOrganizationsCustomPropertiesForOrgsApiTest {

    private static final String ORG = "github";
    private static final String PATH = "/organizations/github/org-properties/values";

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationsCustomPropertiesForOrgsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void getOrganizationValues_shouldReturnValues_whenResponseIsOk() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.OK.code(), "[]"));

        final List<GitHubCustomPropertyValue> values = api().getOrganizationValues(ORG);

        assertThat(values).isEmpty();
        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.GET);
        assertThat(captureSingleRequest().uri().getPath()).isEqualTo(PATH);
    }

    @Test
    void createOrUpdateOrganizationValues_shouldSendPatch_whenResponseIsNoContent() throws Exception {
        when(transport.execute(any())).thenReturn(response(HttpStatus.NO_CONTENT.code(), null));

        api().createOrUpdateOrganizationValues(
                        ORG, OrganizationsCatalogFixtures.customPropertiesForOrgsCreateOrUpdateValuesPayload());

        assertThat(captureSingleRequest().method()).isEqualTo(HttpMethod.PATCH);
        assertThat(captureSingleRequest().uri().getPath()).isEqualTo(PATH);
    }

    @Test
    void createOrUpdateOrganizationValues_shouldThrow_whenPayloadNull() {
        final GitHubOrganizationsCustomPropertiesForOrgsApi api = api();

        final Throwable thrown = catchThrowable(() -> api.createOrUpdateOrganizationValues(ORG, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    private GitHubOrganizationsCustomPropertiesForOrgsApi api() {
        return new GitHubOrganizationsCustomPropertiesForOrgsApi(TestClients.caller(transport));
    }

    private HttpRequest captureSingleRequest() throws Exception {
        final ArgumentCaptor<HttpRequest> captor = ArgumentCaptor.forClass(HttpRequest.class);
        verify(transport).execute(captor.capture());
        return captor.getValue();
    }
}
