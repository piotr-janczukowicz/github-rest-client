package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProtectedBranchDismissalRestrictionsTest {

    @Test
    void constructor_shouldLeaveAppsNull_whenAppsNull() {
        final GitHubProtectedBranchDismissalRestrictions restrictions = new GitHubProtectedBranchDismissalRestrictions(
                List.of(),
                List.of(),
                null,
                "https://api.github.com/repos/o/r/branches/b/protection/dismissal_restrictions",
                "https://api.github.com/repos/o/r/branches/b/protection/dismissal_restrictions/users",
                "https://api.github.com/repos/o/r/branches/b/protection/dismissal_restrictions/teams");

        assertThat(restrictions.apps()).isNull();
    }

    @Test
    void constructor_shouldCopyApps_whenAppsProvided() {
        final GitHubProtectedBranchDismissalRestrictions restrictions = new GitHubProtectedBranchDismissalRestrictions(
                List.of(),
                List.of(),
                List.of(BranchesCatalogFixtures.integration()),
                "https://api.github.com/repos/o/r/branches/b/protection/dismissal_restrictions",
                "https://api.github.com/repos/o/r/branches/b/protection/dismissal_restrictions/users",
                "https://api.github.com/repos/o/r/branches/b/protection/dismissal_restrictions/teams");

        assertThat(restrictions.apps()).hasSize(1);
    }
}
