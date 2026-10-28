package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubSecurityAdvisoryEpssTest {

    @Test
    void constructor_shouldAllow_whenBothNull() {
        final GitHubSecurityAdvisoryEpss epss = new GitHubSecurityAdvisoryEpss(null, null);

        assertSoftly(softly -> {
            softly.assertThat(epss.percentage()).isNull();
            softly.assertThat(epss.percentile()).isNull();
        });
    }

    @Test
    void constructor_shouldThrow_whenPercentageBelowMinimum() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecurityAdvisoryEpss(-1.0, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("percentage");
    }

    @Test
    void constructor_shouldThrow_whenPercentageAboveMaximum() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecurityAdvisoryEpss(101.0, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("percentage");
    }

    @Test
    void constructor_shouldThrow_whenPercentileBelowMinimum() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecurityAdvisoryEpss(null, -1.0));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("percentile");
    }

    @Test
    void constructor_shouldThrow_whenPercentileAboveMaximum() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecurityAdvisoryEpss(null, 101.0));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("percentile");
    }

    @Test
    void constructor_shouldThrow_whenPercentageIsNaN() {
        final Throwable thrown = catchThrowable(() -> new GitHubSecurityAdvisoryEpss(Double.NaN, null));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("percentage");
    }
}
