package io.github.piotr_janczukowicz.github.api.copilotspaces;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateCollaboratorPayloadTest {

    @Test
    void constructor_shouldThrow_whenRoleNull() {
        final GitHubUpdateCollaboratorPayloadBuilder builder = GitHubUpdateCollaboratorPayloadBuilder.builder(
                        CopilotSpacesCatalogFixtures.updateCollaboratorPayload())
                .role(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("role");
    }
}
