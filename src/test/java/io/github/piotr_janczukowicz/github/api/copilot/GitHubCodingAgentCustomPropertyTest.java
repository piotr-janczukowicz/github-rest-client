package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodingAgentCustomPropertyTest {

    @Test
    void constructor_shouldThrow_whenPropertyNameNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodingAgentCustomPropertyBuilder.builder(CopilotCatalogFixtures.codingAgentCustomProperty())
                        .propertyName(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("propertyName");
    }

    @Test
    void constructor_shouldThrow_whenValuesNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubCodingAgentCustomPropertyBuilder.builder(CopilotCatalogFixtures.codingAgentCustomProperty())
                        .values(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("values");
    }
}
