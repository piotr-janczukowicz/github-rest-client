package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCopilotOrganizationDetailsTest {

    @Test
    void constructor_shouldThrow_whenSeatBreakdownNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotOrganizationDetailsBuilder.builder(
                        CopilotCatalogFixtures.copilotOrganizationDetails())
                .seatBreakdown(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("seatBreakdown");
    }

    @Test
    void constructor_shouldThrow_whenPublicCodeSuggestionsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotOrganizationDetailsBuilder.builder(
                        CopilotCatalogFixtures.copilotOrganizationDetails())
                .publicCodeSuggestions(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("publicCodeSuggestions");
    }

    @Test
    void constructor_shouldThrow_whenSeatManagementSettingNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCopilotOrganizationDetailsBuilder.builder(
                        CopilotCatalogFixtures.copilotOrganizationDetails())
                .seatManagementSetting(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("seatManagementSetting");
    }

    @Test
    void constructor_shouldLeaveIdeChatNull_whenNull() {
        final GitHubCopilotOrganizationDetails value = GitHubCopilotOrganizationDetailsBuilder.builder(
                        CopilotCatalogFixtures.copilotOrganizationDetails())
                .ideChat(null)
                .build();

        assertThat(value.ideChat()).isNull();
    }
}
