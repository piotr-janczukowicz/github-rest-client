package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateBranchProtectionRequiredStatusChecksTest {

    @Test
    void constructor_shouldLeaveChecksNull_whenChecksNull() {
        final GitHubUpdateBranchProtectionRequiredStatusChecks payload =
                GitHubUpdateBranchProtectionRequiredStatusChecksBuilder.builder()
                        .strict(true)
                        .contexts(List.of("ci"))
                        .build();

        assertThat(payload.checks()).isNull();
    }

    @Test
    void constructor_shouldCopyChecks_whenChecksProvided() {
        final GitHubUpdateBranchProtectionRequiredStatusChecks payload =
                GitHubUpdateBranchProtectionRequiredStatusChecksBuilder.builder()
                        .strict(true)
                        .contexts(List.of("ci"))
                        .checks(List.of(BranchesCatalogFixtures.updateBranchProtectionRequiredStatusCheck()))
                        .build();

        assertThat(payload.checks()).hasSize(1);
    }
}
