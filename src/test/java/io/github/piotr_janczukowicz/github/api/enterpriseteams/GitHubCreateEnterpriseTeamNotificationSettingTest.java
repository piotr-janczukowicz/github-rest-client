package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateEnterpriseTeamNotificationSettingTest {

    @Test
    void fromWire_shouldReturnNotificationsEnabled_whenValueNotificationsEnabled() {
        assertThat(GitHubCreateEnterpriseTeamNotificationSetting.fromWire("notifications_enabled"))
                .isEqualTo(GitHubCreateEnterpriseTeamNotificationSetting.NOTIFICATIONS_ENABLED);
    }

    @Test
    void fromWire_shouldReturnNotificationsDisabled_whenValueNotificationsDisabled() {
        assertThat(GitHubCreateEnterpriseTeamNotificationSetting.fromWire("notifications_disabled"))
                .isEqualTo(GitHubCreateEnterpriseTeamNotificationSetting.NOTIFICATIONS_DISABLED);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateEnterpriseTeamNotificationSetting.fromWire("muted"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("muted");
    }
}
