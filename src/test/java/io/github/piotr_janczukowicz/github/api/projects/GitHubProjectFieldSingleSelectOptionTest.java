package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProjectFieldSingleSelectOptionTest {

    @Test
    void constructor_shouldKeepComponents_whenCalled() {
        final GitHubProjectFieldSingleSelectOption option =
                new GitHubProjectFieldSingleSelectOption("Todo", GitHubProjectFieldSingleSelectColor.BLUE, "desc");

        assertSoftly(softly -> {
            softly.assertThat(option.name()).isEqualTo("Todo");
            softly.assertThat(option.color()).isEqualTo(GitHubProjectFieldSingleSelectColor.BLUE);
            softly.assertThat(option.description()).isEqualTo("desc");
        });
    }
}
