package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAddCopilotSeatsForTeamsPayloadTest {

    @Test
    void constructor_shouldThrow_whenSelectedTeamsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAddCopilotSeatsForTeamsPayloadBuilder.builder(
                        CopilotCatalogFixtures.addCopilotSeatsForTeamsPayload())
                .selectedTeams(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("selectedTeams");
    }

    @Test
    void constructor_shouldThrow_whenSelectedTeamsEmpty() {
        final Throwable thrown = catchThrowable(() -> GitHubAddCopilotSeatsForTeamsPayloadBuilder.builder(
                        CopilotCatalogFixtures.addCopilotSeatsForTeamsPayload())
                .selectedTeams(List.of())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("selectedTeams");
    }
}
