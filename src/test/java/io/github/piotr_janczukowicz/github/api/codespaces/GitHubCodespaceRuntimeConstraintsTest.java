package io.github.piotr_janczukowicz.github.api.codespaces;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodespaceRuntimeConstraintsTest {

    @Test
    void constructor_shouldLeaveAllowedPortPrivacySettingsNull_whenNull() {
        final GitHubCodespaceRuntimeConstraints sut = GitHubCodespaceRuntimeConstraintsBuilder.builder(
                        CodespacesCatalogFixtures.codespaceRuntimeConstraints())
                .allowedPortPrivacySettings(null)
                .build();

        assertThat(sut.allowedPortPrivacySettings()).isNull();
    }
}
