package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProjectIterationSettingTest {

    @Test
    void constructor_shouldKeepComponents_whenCalled() {
        final GitHubProjectFormattedText title = new GitHubProjectFormattedText("Sprint 1", "<p>Sprint 1</p>");

        final GitHubProjectIterationSetting setting =
                new GitHubProjectIterationSetting("iter-1", "2022-04-28", 14, title, true);

        assertSoftly(softly -> {
            softly.assertThat(setting.id()).isEqualTo("iter-1");
            softly.assertThat(setting.startDate()).isEqualTo("2022-04-28");
            softly.assertThat(setting.duration()).isEqualTo(14);
            softly.assertThat(setting.title()).isEqualTo(title);
            softly.assertThat(setting.completed()).isTrue();
        });
    }
}
