package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAccountsForPlanDirectionTest {

    @Test
    void fromWire_shouldReturnASC_whenWireasc() {
        final GitHubListAccountsForPlanDirection decoded = GitHubListAccountsForPlanDirection.fromWire("asc");

        assertThat(decoded).isEqualTo(GitHubListAccountsForPlanDirection.ASC);
    }

    @Test
    void fromWire_shouldReturnDESC_whenWiredesc() {
        final GitHubListAccountsForPlanDirection decoded = GitHubListAccountsForPlanDirection.fromWire("desc");

        assertThat(decoded).isEqualTo(GitHubListAccountsForPlanDirection.DESC);
    }

    @Test
    void fromWire_shouldThrow_whenUnknownValue() {
        final Throwable thrown = catchThrowable(() -> GitHubListAccountsForPlanDirection.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }

    @Test
    void fromWire_shouldThrow_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubListAccountsForPlanDirection.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
