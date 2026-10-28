package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotSpaceCollaboratorsPageTest {

    @Test
    void constructor_shouldThrow_whenCollaboratorsNull() {
        final GitHubCopilotSpaceCollaboratorsPageBuilder builder = GitHubCopilotSpaceCollaboratorsPageBuilder.builder(
                        CopilotSpacesCatalogFixtures.copilotSpaceCollaboratorsPage())
                .collaborators(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("collaborators");
    }
}
