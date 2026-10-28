package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateEnterpriseTeamPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubUpdateEnterpriseTeamPayload payload = EnterpriseTeamsCatalogFixtures.updateEnterpriseTeamPayload();

        assertThat(payload.description()).isEqualTo("A new description for the team.");
        assertThat(payload.organizationSelectionType())
                .isEqualTo(GitHubUpdateEnterpriseTeamOrganizationSelectionType.ALL);
        assertThat(payload.notificationSetting())
                .isEqualTo(GitHubUpdateEnterpriseTeamNotificationSetting.NOTIFICATIONS_DISABLED);
    }
}
