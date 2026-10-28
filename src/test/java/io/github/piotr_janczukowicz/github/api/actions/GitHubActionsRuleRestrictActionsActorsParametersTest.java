package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsRuleRestrictActionsActorsParametersTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenAllowedActorsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubActionsRuleRestrictActionsActorsParametersBuilder.builder(
                        ActionsCatalogFixtures.actionsRuleRestrictActionsActorsParameters())
                .allowedActors(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("allowedActors");
    }
}
