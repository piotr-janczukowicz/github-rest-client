package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeScanningAlertDismissalRequestTest {

    @Test
    void gitHubCodeScanningAlertDismissalRequest_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCodeScanningAlertDismissalRequest value =
                CodeScanningCatalogFixtures.codeScanningAlertDismissalRequest();

        assertThat(value.id()).isEqualTo(1L);
        assertThat(value.number()).isEqualTo(1);
        assertThat(value.repository())
                .isEqualTo(CodeScanningCatalogFixtures.codeScanningAlertDismissalRequestRepository());
        assertThat(value.organization())
                .isEqualTo(CodeScanningCatalogFixtures.codeScanningAlertDismissalRequestOrganization());
        assertThat(value.requester())
                .isEqualTo(CodeScanningCatalogFixtures.codeScanningAlertDismissalRequestRequester());
        assertThat(value.requestType()).isEqualTo("code_scanning_alert");
        assertThat(value.data())
                .isEqualTo(List.of(CodeScanningCatalogFixtures.codeScanningAlertDismissalRequestData()));
        assertThat(value.resourceIdentifier()).isEqualTo("1");
        assertThat(value.status()).isEqualTo(GitHubCodeScanningAlertDismissalRequestStatus.APPROVED);
        assertThat(value.requesterComment()).isEqualTo("This is a false positive.");
        assertThat(value.expiresAt()).isEqualTo(Instant.parse("2026-09-01T00:00:00Z"));
        assertThat(value.createdAt()).isEqualTo(Instant.parse("2026-08-01T10:15:30Z"));
        assertThat(value.responses()).isEqualTo(List.of(CodeScanningCatalogFixtures.dismissalRequestResponse()));
        assertThat(value.url())
                .isEqualTo("https://api.github.com/repos/octo-org/octo-repo/dismissal-requests/code-scanning/1");
        assertThat(value.htmlUrl())
                .isEqualTo("https://github.com/octo-org/octo-repo/security/dismissal-requests/code-scanning/1");
    }

    @Test
    void constructor_shouldLeaveDataNull_whenNull() {
        final GitHubCodeScanningAlertDismissalRequest value = GitHubCodeScanningAlertDismissalRequestBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningAlertDismissalRequest())
                .data(null)
                .build();

        assertThat(value.data()).isNull();
    }

    @Test
    void constructor_shouldLeaveResponsesNull_whenNull() {
        final GitHubCodeScanningAlertDismissalRequest value = GitHubCodeScanningAlertDismissalRequestBuilder.builder(
                        CodeScanningCatalogFixtures.codeScanningAlertDismissalRequest())
                .responses(null)
                .build();

        assertThat(value.responses()).isNull();
    }
}
