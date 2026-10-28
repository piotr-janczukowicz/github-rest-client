package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBranchRestrictionPolicyTest {

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final String uri = "https://example.com";

        final Throwable thrown = catchThrowable(
                () -> new GitHubBranchRestrictionPolicy(null, uri, uri, uri, List.of(), List.of(), List.of()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldCopyUsers_whenUsersProvided() {
        final String uri = "https://example.com";
        final GitHubBranchRestrictionPolicyUser user = BranchesCatalogFixtures.branchRestrictionPolicyUser();

        final GitHubBranchRestrictionPolicy policy =
                new GitHubBranchRestrictionPolicy(uri, uri, uri, uri, List.of(user), List.of(), List.of());

        assertThat(policy.users()).containsExactly(user);
    }
}
