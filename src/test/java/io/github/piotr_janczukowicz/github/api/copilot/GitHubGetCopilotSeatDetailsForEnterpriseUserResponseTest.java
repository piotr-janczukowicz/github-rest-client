package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetCopilotSeatDetailsForEnterpriseUserResponseTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubGetCopilotSeatDetailsForEnterpriseUserResponse value =
                CopilotCatalogFixtures.getCopilotSeatDetailsForEnterpriseUserResponse();

        assertThat(value.totalSeats()).isEqualTo(1);
        assertThat(value.seats()).isNotEmpty();
    }

    @Test
    void constructor_shouldLeaveSeatsNull_whenNull() {
        final GitHubGetCopilotSeatDetailsForEnterpriseUserResponse value =
                GitHubGetCopilotSeatDetailsForEnterpriseUserResponseBuilder.builder(
                                CopilotCatalogFixtures.getCopilotSeatDetailsForEnterpriseUserResponse())
                        .seats(null)
                        .build();

        assertThat(value.seats()).isNull();
    }
}
