package io.github.piotr_janczukowicz.github.api.codequality;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeQualityFindingMessageTest {

    @Test
    void constructor_shouldThrow_whenTextNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeQualityFindingMessageBuilder.builder(
                        CodeQualityCatalogFixtures.codeQualityFindingMessage())
                .text(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("text");
    }

    @Test
    void constructor_shouldThrow_whenMarkdownNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCodeQualityFindingMessageBuilder.builder(
                        CodeQualityCatalogFixtures.codeQualityFindingMessage())
                .markdown(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("markdown");
    }
}
