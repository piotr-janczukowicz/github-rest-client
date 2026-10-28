package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotCustomAgentTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubCopilotCustomAgent value = CopilotCatalogFixtures.copilotCustomAgent();

        assertThat(value.name()).isEqualTo("sample-name");
        assertThat(value.filePath()).isEqualTo("sample-file_path");
        assertThat(value.url()).isEqualTo("sample-url");
    }
}
