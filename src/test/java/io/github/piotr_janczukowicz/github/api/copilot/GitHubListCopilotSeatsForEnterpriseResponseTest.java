package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListCopilotSeatsForEnterpriseResponseTest {

    @Test
    void accessors_shouldExposeComponents_whenBuiltFromFixture() {
        final GitHubListCopilotSeatsForEnterpriseResponse value =
                CopilotCatalogFixtures.listCopilotSeatsForEnterpriseResponse();

        assertThat(value.totalSeats()).isEqualTo(1);
        assertThat(value.seats()).isNotEmpty();
    }

    @Test
    void constructor_shouldLeaveSeatsNull_whenNull() {
        final GitHubListCopilotSeatsForEnterpriseResponse value =
                GitHubListCopilotSeatsForEnterpriseResponseBuilder.builder(
                                CopilotCatalogFixtures.listCopilotSeatsForEnterpriseResponse())
                        .seats(null)
                        .build();

        assertThat(value.seats()).isNull();
    }
}
