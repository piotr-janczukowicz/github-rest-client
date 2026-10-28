package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProtectedBranchBypassPullRequestAllowancesTest {

    @Test
    void constructor_shouldLeaveAppsNull_whenAppsNull() {
        final GitHubProtectedBranchBypassPullRequestAllowances allowances =
                new GitHubProtectedBranchBypassPullRequestAllowances(List.of(), List.of(), null);

        assertThat(allowances.apps()).isNull();
    }
}
