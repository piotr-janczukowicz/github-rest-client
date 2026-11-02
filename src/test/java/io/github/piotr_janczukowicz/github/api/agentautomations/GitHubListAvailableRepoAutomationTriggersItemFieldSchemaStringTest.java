package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAvailableRepoAutomationTriggersItemFieldSchemaStringTest {

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final GitHubListAvailableRepoAutomationTriggersItemFieldSchemaStringBuilder builder =
                GitHubListAvailableRepoAutomationTriggersItemFieldSchemaStringBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAvailableRepoAutomationTriggersItemFieldSchemaString())
                        .type(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }

    @Test
    void constructor_shouldLeaveEnumValuesNull_whenNull() {
        final GitHubListAvailableRepoAutomationTriggersItemFieldSchemaStringBuilder builder =
                GitHubListAvailableRepoAutomationTriggersItemFieldSchemaStringBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAvailableRepoAutomationTriggersItemFieldSchemaString())
                        .enumValues(null);

        final GitHubListAvailableRepoAutomationTriggersItemFieldSchemaString sut = builder.build();

        assertThat(sut.enumValues()).isNull();
    }

    @Test
    void constructor_shouldThrow_whenMinLengthBelowMinimum() {
        final GitHubListAvailableRepoAutomationTriggersItemFieldSchemaStringBuilder builder =
                GitHubListAvailableRepoAutomationTriggersItemFieldSchemaStringBuilder.builder(
                                AgentAutomationsCatalogFixtures
                                        .listAvailableRepoAutomationTriggersItemFieldSchemaString())
                        .minLength(-1);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("minLength");
    }
}
