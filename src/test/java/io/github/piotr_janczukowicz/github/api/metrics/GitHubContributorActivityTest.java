package io.github.piotr_janczukowicz.github.api.metrics;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubContributorActivityTest {

    @Test
    void constructor_shouldCopyWeeks_whenPresent() {
        final GitHubContributorActivity activity =
                new GitHubContributorActivity(null, 1L, List.of(MetricsCatalogFixtures.contributorActivityWeek()));

        assertSoftly(softly -> {
            softly.assertThat(activity.weeks()).hasSize(1);
            softly.assertThat(activity.author()).isNull();
        });
    }

    @Test
    void constructor_shouldThrow_whenWeeksNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubContributorActivity(null, 1L, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("weeks");
    }
}
