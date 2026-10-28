package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAvailableAutomationTriggersItemFieldSchemaStringTest {

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubListAvailableAutomationTriggersItemFieldSchemaStringBuilder builder =
                GitHubListAvailableAutomationTriggersItemFieldSchemaStringBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItemFieldSchemaString())
                        .type(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldLeaveEnumValuesNull_whenNull() {
        final GitHubListAvailableAutomationTriggersItemFieldSchemaStringBuilder builder =
                GitHubListAvailableAutomationTriggersItemFieldSchemaStringBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItemFieldSchemaString())
                        .enumValues(null);

        final GitHubListAvailableAutomationTriggersItemFieldSchemaString sut = builder.build();

        assertThat(sut.enumValues()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenMinLengthBelowMinimum() {
        final GitHubListAvailableAutomationTriggersItemFieldSchemaStringBuilder builder =
                GitHubListAvailableAutomationTriggersItemFieldSchemaStringBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItemFieldSchemaString())
                        .minLength(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("minLength");
    }
}
