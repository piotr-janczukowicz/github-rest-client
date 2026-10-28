package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.test.TestClients;
import io.github.piotr_janczukowicz.test.http.HttpTransportMocks;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningApiTest {

    @Test
    void constructor_shouldThrow_whenCallerNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCodeScanningApi(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("caller");
    }

    @Test
    void codeScanning_shouldReturnCodeScanningApi_whenCalled() {
        final GitHubCodeScanningApi api = new GitHubCodeScanningApi(TestClients.caller(HttpTransportMocks.mock()));

        assertThat(api.codeScanning()).isInstanceOf(GitHubCodeScanningCodeScanningApi.class);
        assertThat(api.codeScanning()).isSameAs(api.codeScanning());
    }

    @Test
    void alertDismissalRequests_shouldReturnAlertDismissalRequestsApi_whenCalled() {
        final GitHubCodeScanningApi api = new GitHubCodeScanningApi(TestClients.caller(HttpTransportMocks.mock()));

        assertThat(api.alertDismissalRequests()).isInstanceOf(GitHubCodeScanningAlertDismissalRequestsApi.class);
        assertThat(api.alertDismissalRequests()).isSameAs(api.alertDismissalRequests());
    }
}
