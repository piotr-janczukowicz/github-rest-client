package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCommitStatsTest {

    @Test
    void constructor_shouldExposeValues_whenProvided() {
        final GitHubCommitStats stats = new GitHubCommitStats(10, 2, 12);

        assertSoftly(softly -> {
            softly.assertThat(stats).hasNoNullFieldsOrProperties();
            softly.assertThat(stats.additions()).isEqualTo(10);
            softly.assertThat(stats.deletions()).isEqualTo(2);
            softly.assertThat(stats.total()).isEqualTo(12);
        });
    }

    @Test
    void constructor_shouldAllowNullFields_whenNull() {
        final GitHubCommitStats stats = new GitHubCommitStats(null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(stats.additions()).isNull();
            softly.assertThat(stats.deletions()).isNull();
            softly.assertThat(stats.total()).isNull();
        });
    }
}
