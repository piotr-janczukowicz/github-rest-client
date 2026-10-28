package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsPolicyWorkflowPathConditionWorkflowPathTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenIncludeNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubActionsPolicyWorkflowPathConditionWorkflowPathBuilder.builder(
                                ActionsCatalogFixtures.actionsPolicyWorkflowPath())
                        .include(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("include");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenExcludeNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubActionsPolicyWorkflowPathConditionWorkflowPathBuilder.builder(
                                ActionsCatalogFixtures.actionsPolicyWorkflowPath())
                        .exclude(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("exclude");
    }
}
