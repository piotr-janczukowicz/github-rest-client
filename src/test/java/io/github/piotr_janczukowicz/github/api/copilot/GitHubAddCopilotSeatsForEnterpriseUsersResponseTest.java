package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAddCopilotSeatsForEnterpriseUsersResponseTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubAddCopilotSeatsForEnterpriseUsersResponse value =
                CopilotCatalogFixtures.addCopilotSeatsForEnterpriseUsersResponse();

        assertThat(value.seatsCreated()).isEqualTo(1);
    }
}
