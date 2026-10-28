package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAddCopilotSeatsForEnterpriseUsersPayloadTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubAddCopilotSeatsForEnterpriseUsersPayload value =
                CopilotCatalogFixtures.addCopilotSeatsForEnterpriseUsersPayload();

        assertThat(value.selectedUsernames()).isNotEmpty();
    }
}
