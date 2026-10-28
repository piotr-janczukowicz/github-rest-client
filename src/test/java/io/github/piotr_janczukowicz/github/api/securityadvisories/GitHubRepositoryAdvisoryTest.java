package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryAdvisoryTest {

    @Test
    void constructor_shouldThrow_whenSummaryExceedsMaxLength() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryAdvisory(
                "GHSA-test",
                null,
                "https://api.github.com/repos/o/r/security-advisories/GHSA-test",
                "https://github.com/o/r/security/advisories/GHSA-test",
                "s".repeat(1025),
                null,
                GitHubRepositoryAdvisorySeverity.HIGH,
                null,
                null,
                List.of(new GitHubAdvisoryIdentifier(GitHubAdvisoryIdentifierType.CVE, "CVE-1")),
                GitHubRepositoryAdvisoryState.PUBLISHED,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("summary");
    }
}
