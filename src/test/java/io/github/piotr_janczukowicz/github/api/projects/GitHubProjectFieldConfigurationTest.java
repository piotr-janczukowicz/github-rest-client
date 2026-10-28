package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProjectFieldConfigurationTest {

    @Test
    void constructor_shouldKeepNullIterations_whenAbsent() {
        final GitHubProjectFieldConfiguration configuration = new GitHubProjectFieldConfiguration(1, 14, null);

        assertSoftly(softly -> {
            softly.assertThat(configuration.startDay()).isEqualTo(1);
            softly.assertThat(configuration.duration()).isEqualTo(14);
            softly.assertThat(configuration.iterations()).isNull();
        });
    }

    @Test
    void constructor_shouldCopyIterations_whenPresent() {
        final GitHubProjectIterationSetting iteration = ProjectsCatalogFixtures.iterationSetting();

        final GitHubProjectFieldConfiguration configuration =
                new GitHubProjectFieldConfiguration(1, 14, List.of(iteration));

        assertThat(configuration.iterations()).containsExactly(iteration);
    }
}
