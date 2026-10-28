package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsItemTriggersSubIssueAddedTest {

    @Test
    void constructor_shouldThrow_whenQueryTooShort() {
        final GitHubListAutomationsItemTriggersSubIssueAddedBuilder builder =
                GitHubListAutomationsItemTriggersSubIssueAddedBuilder.builder(
                                AgentAutomationsCatalogFixtures.listAutomationsItemTriggersSubIssueAdded())
                        .query("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("query");
    }
}
