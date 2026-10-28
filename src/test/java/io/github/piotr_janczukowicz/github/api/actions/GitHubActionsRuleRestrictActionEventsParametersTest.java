package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsRuleRestrictActionEventsParametersTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenAllowedEventsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubActionsRuleRestrictActionEventsParametersBuilder.builder(
                        ActionsCatalogFixtures.actionsRuleRestrictActionEventsParameters())
                .allowedEvents(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("allowedEvents");
    }
}
