package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGlobalAdvisoryTest {

    @Test
    void builder_shouldLeaveReferencesNull_whenAbsent() {
        final GitHubGlobalAdvisory advisory = minimalAdvisoryBuilder().build();

        assertThat(advisory.references()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenSummaryExceedsMaxLength() {
        final Throwable thrown = catchThrowable(
                () -> minimalAdvisoryBuilder().summary("s".repeat(1025)).build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("summary");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionExceedsMaxLength() {
        final Throwable thrown = catchThrowable(
                () -> minimalAdvisoryBuilder().description("d".repeat(65_536)).build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("description");
    }

    private static GitHubGlobalAdvisoryBuilder minimalAdvisoryBuilder() {
        return GitHubGlobalAdvisoryBuilder.builder()
                .ghsaId("GHSA-test")
                .url("https://api.github.com/advisories/GHSA-test")
                .htmlUrl("https://github.com/advisories/GHSA-test")
                .summary("summary")
                .type(GitHubGlobalAdvisoryType.REVIEWED)
                .severity(GitHubSecurityAdvisorySeverity.HIGH)
                .publishedAt(Instant.EPOCH)
                .updatedAt(Instant.EPOCH);
    }
}
