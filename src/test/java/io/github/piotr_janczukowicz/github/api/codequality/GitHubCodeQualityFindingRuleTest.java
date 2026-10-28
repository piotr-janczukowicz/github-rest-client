package io.github.piotr_janczukowicz.github.api.codequality;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeQualityFindingRuleTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeQualityFindingRuleBuilder.builder(CodeQualityCatalogFixtures.codeQualityFindingRule())
                        .id(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenTitleNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeQualityFindingRuleBuilder.builder(CodeQualityCatalogFixtures.codeQualityFindingRule())
                        .title(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("title");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeQualityFindingRuleBuilder.builder(CodeQualityCatalogFixtures.codeQualityFindingRule())
                        .description(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("description");
    }

    @Test
    void constructor_shouldThrow_whenSeverityNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeQualityFindingRuleBuilder.builder(CodeQualityCatalogFixtures.codeQualityFindingRule())
                        .severity(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("severity");
    }

    @Test
    void constructor_shouldThrow_whenCategoryNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodeQualityFindingRuleBuilder.builder(CodeQualityCatalogFixtures.codeQualityFindingRule())
                        .category(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("category");
    }
}
