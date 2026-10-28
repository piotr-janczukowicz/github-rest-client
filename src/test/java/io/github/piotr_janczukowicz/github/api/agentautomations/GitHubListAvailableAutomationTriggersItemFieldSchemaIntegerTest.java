package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAvailableAutomationTriggersItemFieldSchemaIntegerTest {

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubListAvailableAutomationTriggersItemFieldSchemaIntegerBuilder builder =
                GitHubListAvailableAutomationTriggersItemFieldSchemaIntegerBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItemFieldSchemaInteger())
                        .type(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldLeaveEnumValuesNull_whenNull() {
        final GitHubListAvailableAutomationTriggersItemFieldSchemaIntegerBuilder builder =
                GitHubListAvailableAutomationTriggersItemFieldSchemaIntegerBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItemFieldSchemaInteger())
                        .enumValues(null);

        final GitHubListAvailableAutomationTriggersItemFieldSchemaInteger sut = builder.build();

        assertThat(sut.enumValues()).isNull();
    }
}
