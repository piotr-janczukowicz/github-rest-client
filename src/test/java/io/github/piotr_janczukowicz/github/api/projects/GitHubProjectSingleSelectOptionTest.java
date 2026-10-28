package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProjectSingleSelectOptionTest {

    @Test
    void constructor_shouldKeepComponents_whenCalled() {
        final GitHubProjectFormattedText name = new GitHubProjectFormattedText("Todo", "<p>Todo</p>");
        final GitHubProjectFormattedText description = new GitHubProjectFormattedText("desc", "<p>desc</p>");

        final GitHubProjectSingleSelectOption option =
                new GitHubProjectSingleSelectOption("opt-1", name, description, "BLUE");

        assertSoftly(softly -> {
            softly.assertThat(option.id()).isEqualTo("opt-1");
            softly.assertThat(option.name()).isEqualTo(name);
            softly.assertThat(option.description()).isEqualTo(description);
            softly.assertThat(option.color()).isEqualTo("BLUE");
        });
    }
}
