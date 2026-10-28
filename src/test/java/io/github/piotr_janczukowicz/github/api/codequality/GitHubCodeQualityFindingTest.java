package io.github.piotr_janczukowicz.github.api.codequality;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeQualityFindingTest {

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeQualityFindingBuilder.builder(CodeQualityCatalogFixtures.codeQualityFinding())
                        .state(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeQualityFindingBuilder.builder(CodeQualityCatalogFixtures.codeQualityFinding())
                        .url(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenRuleNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeQualityFindingBuilder.builder(CodeQualityCatalogFixtures.codeQualityFinding())
                        .rule(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("rule");
    }

    @Test
    void constructor_shouldThrow_whenLocationNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeQualityFindingBuilder.builder(CodeQualityCatalogFixtures.codeQualityFinding())
                        .location(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("location");
    }

    @Test
    void constructor_shouldThrow_whenMessageNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeQualityFindingBuilder.builder(CodeQualityCatalogFixtures.codeQualityFinding())
                        .message(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("message");
    }
}
