package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAvailableAutomationTriggersItemFieldSchemaArrayTest {

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubListAvailableAutomationTriggersItemFieldSchemaArrayBuilder builder =
                GitHubListAvailableAutomationTriggersItemFieldSchemaArrayBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItemFieldSchemaArray())
                        .type(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldThrow_whenItemsNull() {
        final GitHubListAvailableAutomationTriggersItemFieldSchemaArrayBuilder builder =
                GitHubListAvailableAutomationTriggersItemFieldSchemaArrayBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItemFieldSchemaArray())
                        .items(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("items");
    }

    @Test
    void constructor_shouldThrow_whenMinItemsBelowMinimum() {
        final GitHubListAvailableAutomationTriggersItemFieldSchemaArrayBuilder builder =
                GitHubListAvailableAutomationTriggersItemFieldSchemaArrayBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItemFieldSchemaArray())
                        .minItems(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("minItems");
    }

    @Test
    void constructor_shouldThrow_whenMaxItemsBelowMinimum() {
        final GitHubListAvailableAutomationTriggersItemFieldSchemaArrayBuilder builder =
                GitHubListAvailableAutomationTriggersItemFieldSchemaArrayBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItemFieldSchemaArray())
                        .maxItems(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("maxItems");
    }
}
