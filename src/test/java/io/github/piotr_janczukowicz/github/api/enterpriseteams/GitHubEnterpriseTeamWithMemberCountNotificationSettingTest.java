package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseTeamWithMemberCountNotificationSettingTest {

    @Test
    void fromWire_shouldReturnNotificationsEnabled_whenValueNotificationsEnabled() {
        assertThat(GitHubEnterpriseTeamWithMemberCountNotificationSetting.fromWire("notifications_enabled"))
                .isEqualTo(GitHubEnterpriseTeamWithMemberCountNotificationSetting.NOTIFICATIONS_ENABLED);
    }

    @Test
    void fromWire_shouldReturnNotificationsDisabled_whenValueNotificationsDisabled() {
        assertThat(GitHubEnterpriseTeamWithMemberCountNotificationSetting.fromWire("notifications_disabled"))
                .isEqualTo(GitHubEnterpriseTeamWithMemberCountNotificationSetting.NOTIFICATIONS_DISABLED);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubEnterpriseTeamWithMemberCountNotificationSetting decoded =
                GitHubEnterpriseTeamWithMemberCountNotificationSetting.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubEnterpriseTeamWithMemberCountNotificationSetting.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
