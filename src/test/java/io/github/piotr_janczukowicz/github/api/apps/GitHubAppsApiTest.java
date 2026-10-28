package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.http.transport.HttpTransport;
import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAppsApiTest {

    private HttpTransport transport;

    @BeforeEach
    void setUpTransport() {
        transport = HttpTransportMocks.mock();
    }

    @Test
    void constructor_shouldThrow_whenClientNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubAppsApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void accessors_shouldReturnLeafApis_whenCalled() {
        final GitHubAppsApi api = new GitHubAppsApi(TestClients.caller(transport));

        assertSoftly(softly -> {
            softly.assertThat(api.app()).isNotNull();
            softly.assertThat(api.installations()).isNotNull();
            softly.assertThat(api.marketplace()).isNotNull();
            softly.assertThat(api.oauthApplications()).isNotNull();
            softly.assertThat(api.webhooks()).isNotNull();
        });
    }
}
