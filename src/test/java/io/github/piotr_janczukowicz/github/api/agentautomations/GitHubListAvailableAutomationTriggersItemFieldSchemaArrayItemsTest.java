package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItemsTest {

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItemsBuilder builder =
                GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItemsBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAvailableAutomationTriggersItemFieldSchemaArrayItems())
                        .type(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldLeaveEnumValuesNull_whenNull() {
        final GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItemsBuilder builder =
                GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItemsBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAvailableAutomationTriggersItemFieldSchemaArrayItems())
                        .enumValues(null);

        final GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItems sut = builder.build();

        assertThat(sut.enumValues()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenMinLengthBelowMinimum() {
        final GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItemsBuilder builder =
                GitHubListAvailableAutomationTriggersItemFieldSchemaArrayItemsBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAvailableAutomationTriggersItemFieldSchemaArrayItems())
                        .minLength(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("minLength");
    }
}
