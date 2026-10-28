package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAddCopilotSeatsForEnterpriseTeamsResponseTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubAddCopilotSeatsForEnterpriseTeamsResponse value =
                CopilotCatalogFixtures.addCopilotSeatsForEnterpriseTeamsResponse();

        assertThat(value.seatsCreated()).isEqualTo(1);
    }
}
