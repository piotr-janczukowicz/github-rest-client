package io.github.piotr_janczukowicz.github.api.dependabot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.codescanning.CodeScanningCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDependabotDismissalRequestTest {

    @Test
    void gitHubDependabotDismissalRequest_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubDependabotDismissalRequest value = DependabotCatalogFixtures.dependabotDismissalRequest();

        assertThat(value.id()).isEqualTo(1L);
        assertThat(value.number()).isEqualTo(1L);
        assertThat(value.repository()).isEqualTo(DependabotCatalogFixtures.dependabotDismissalRequestRepository());
        assertThat(value.organization()).isEqualTo(DependabotCatalogFixtures.dependabotDismissalRequestOrganization());
        assertThat(value.requester()).isEqualTo(DependabotCatalogFixtures.dependabotDismissalRequestRequester());
        assertThat(value.requestType()).isEqualTo("dependabot_alert");
        assertThat(value.data()).isEqualTo(List.of(DependabotCatalogFixtures.dependabotDismissalRequestData()));
        assertThat(value.resourceIdentifier()).isEqualTo("1");
        assertThat(value.status()).isEqualTo(GitHubDependabotDismissalRequestStatus.PENDING);
        assertThat(value.requesterComment()).isEqualTo("Please dismiss this alert.");
        assertThat(value.expiresAt()).isEqualTo(Instant.parse("2026-09-01T00:00:00Z"));
        assertThat(value.createdAt()).isEqualTo(Instant.parse("2026-08-01T10:15:30Z"));
        assertThat(value.responses()).isEqualTo(List.of(CodeScanningCatalogFixtures.dismissalRequestResponse()));
        assertThat(value.url())
                .isEqualTo("https://api.github.com/repos/octo-org/octo-repo/dismissal-requests/dependabot/1");
        assertThat(value.htmlUrl())
                .isEqualTo("https://github.com/octo-org/octo-repo/security/dismissal-requests/dependabot/1");
    }

    @Test
    void constructor_shouldLeaveDataNull_whenNull() {
        final GitHubDependabotDismissalRequest value = GitHubDependabotDismissalRequestBuilder.builder(
                        DependabotCatalogFixtures.dependabotDismissalRequest())
                .data(null)
                .build();

        assertThat(value.data()).isNull();
    }

    @Test
    void constructor_shouldLeaveResponsesNull_whenNull() {
        final GitHubDependabotDismissalRequest value = GitHubDependabotDismissalRequestBuilder.builder(
                        DependabotCatalogFixtures.dependabotDismissalRequest())
                .responses(null)
                .build();

        assertThat(value.responses()).isNull();
    }
}
