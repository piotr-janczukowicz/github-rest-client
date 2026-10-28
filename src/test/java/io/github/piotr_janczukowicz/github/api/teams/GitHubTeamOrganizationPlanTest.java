package io.github.piotr_janczukowicz.github.api.teams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubTeamOrganizationPlanTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubTeamOrganizationPlan base =
                GitHubTeamOrganizationPlanBuilder.builder().name("free").build();

        final Throwable thrown = catchThrowable(() -> new GitHubTeamOrganizationPlan(
                null, base.space(), base.privateRepos(), base.filledSeats(), base.seats()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }
}
