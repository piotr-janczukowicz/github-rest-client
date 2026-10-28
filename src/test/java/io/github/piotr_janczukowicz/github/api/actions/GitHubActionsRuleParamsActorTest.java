package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsRuleParamsActorTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenTypeNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubActionsRuleParamsActorBuilder.builder(ActionsCatalogFixtures.actionsRuleParamsActor())
                        .type(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }
}
