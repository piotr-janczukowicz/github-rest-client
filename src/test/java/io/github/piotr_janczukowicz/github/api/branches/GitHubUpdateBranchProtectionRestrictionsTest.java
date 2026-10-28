package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateBranchProtectionRestrictionsTest {

    @Test
    void constructor_shouldLeaveAppsNull_whenAppsNull() {
        final GitHubUpdateBranchProtectionRestrictions payload =
                GitHubUpdateBranchProtectionRestrictionsBuilder.builder()
                        .users(List.of("octocat"))
                        .teams(List.of("justice-league"))
                        .build();

        assertThat(payload.apps()).isNull();
    }

    @Test
    void constructor_shouldCopyApps_whenAppsProvided() {
        final GitHubUpdateBranchProtectionRestrictions payload =
                GitHubUpdateBranchProtectionRestrictionsBuilder.builder()
                        .users(List.of("octocat"))
                        .teams(List.of("justice-league"))
                        .apps(List.of("octoapp"))
                        .build();

        assertThat(payload.apps()).containsExactly("octoapp");
    }
}
