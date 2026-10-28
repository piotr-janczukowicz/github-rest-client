package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetTaskByIdResponseSessionUsageTest {

    @Test
    void constructor_shouldThrow_whenTypeNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetTaskByIdResponseSessionUsageBuilder.builder(
                        AgentTasksCatalogFixtures.getTaskByIdResponseSessionUsage())
                .type(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("type");
    }
}
