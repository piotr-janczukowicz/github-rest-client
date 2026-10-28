package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBranchRestrictionPolicyAppTest {

    @Test
    void constructor_shouldCopyEvents_whenEventsProvided() {
        final GitHubBranchRestrictionPolicyApp app = new GitHubBranchRestrictionPolicyApp(
                1L, "octoapp", null, null, null, null, null, null, null, null, null, null, List.of("push"));

        assertThat(app.events()).containsExactly("push");
    }

    @Test
    void constructor_shouldLeaveEventsNull_whenEventsNull() {
        final GitHubBranchRestrictionPolicyApp app = new GitHubBranchRestrictionPolicyApp(
                null, null, null, null, null, null, null, null, null, null, null, null, null);

        assertThat(app.events()).isNull();
    }
}
