package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubProjectFormattedTextTest {

    @Test
    void constructor_shouldKeepRawAndHtml_whenCalled() {
        final GitHubProjectFormattedText text = new GitHubProjectFormattedText("raw", "<p>html</p>");

        assertSoftly(softly -> {
            softly.assertThat(text.raw()).isEqualTo("raw");
            softly.assertThat(text.html()).isEqualTo("<p>html</p>");
        });
    }
}
