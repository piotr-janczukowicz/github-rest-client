package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAvailableAutomationTriggersItemFieldTest {

    @Test
    void constructor_shouldThrow_whenIdNull() {
        final GitHubListAvailableAutomationTriggersItemFieldBuilder builder =
                GitHubListAvailableAutomationTriggersItemFieldBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItemField())
                        .id(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("id");
    }

    @Test
    void constructor_shouldThrow_whenLabelNull() {
        final GitHubListAvailableAutomationTriggersItemFieldBuilder builder =
                GitHubListAvailableAutomationTriggersItemFieldBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItemField())
                        .label(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("label");
    }

    @Test
    void constructor_shouldThrow_whenSchemaNull() {
        final GitHubListAvailableAutomationTriggersItemFieldBuilder builder =
                GitHubListAvailableAutomationTriggersItemFieldBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAvailableAutomationTriggersItemField())
                        .schema(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("schema");
    }
}
