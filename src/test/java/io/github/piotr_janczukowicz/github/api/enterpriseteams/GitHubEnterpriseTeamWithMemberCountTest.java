package io.github.piotr_janczukowicz.github.api.enterpriseteams;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseTeamWithMemberCountTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubEnterpriseTeamWithMemberCount team = EnterpriseTeamsCatalogFixtures.enterpriseTeamWithMemberCount();

        assertThat(team.membersCount()).isEqualTo(3);
        assertThat(team.groupName()).isEqualTo("Octo group");
        assertThat(team.notificationSetting())
                .isEqualTo(GitHubEnterpriseTeamWithMemberCountNotificationSetting.NOTIFICATIONS_ENABLED);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenUrlNull() {
        final GitHubEnterpriseTeamWithMemberCountBuilder builder = GitHubEnterpriseTeamWithMemberCountBuilder.builder(
                        EnterpriseTeamsCatalogFixtures.enterpriseTeamWithMemberCount())
                .url(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }
}
