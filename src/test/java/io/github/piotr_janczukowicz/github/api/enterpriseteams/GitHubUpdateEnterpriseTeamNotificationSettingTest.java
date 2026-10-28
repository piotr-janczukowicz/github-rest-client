package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseTeamNotificationSettingTest {

    @Test
    void fromWire_shouldReturnNotificationsEnabled_whenValueNotificationsEnabled() {
        assertThat(GitHubUpdateEnterpriseTeamNotificationSetting.fromWire("notifications_enabled"))
                .isEqualTo(GitHubUpdateEnterpriseTeamNotificationSetting.NOTIFICATIONS_ENABLED);
    }

    @Test
    void fromWire_shouldReturnNotificationsDisabled_whenValueNotificationsDisabled() {
        assertThat(GitHubUpdateEnterpriseTeamNotificationSetting.fromWire("notifications_disabled"))
                .isEqualTo(GitHubUpdateEnterpriseTeamNotificationSetting.NOTIFICATIONS_DISABLED);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubUpdateEnterpriseTeamNotificationSetting.fromWire("muted"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("muted");
    }
}
