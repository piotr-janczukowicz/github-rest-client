package io.github.piotr_janczukowicz.github.api.campaigns;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCampaignSummaryTest {

    @Test
    void constructor_shouldThrow_whenManagersNull() {
        final GitHubCampaignSummaryBuilder builder = GitHubCampaignSummaryBuilder.builder(
                        CampaignsCatalogFixtures.campaignSummary())
                .managers(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("managers");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final GitHubCampaignSummaryBuilder builder = GitHubCampaignSummaryBuilder.builder(
                        CampaignsCatalogFixtures.campaignSummary())
                .state(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldLeaveTeamManagersNull_whenNull() {
        final GitHubCampaignSummaryBuilder builder = GitHubCampaignSummaryBuilder.builder(
                        CampaignsCatalogFixtures.campaignSummary())
                .teamManagers(null);

        final GitHubCampaignSummary summary = builder.build();

        assertThat(summary.teamManagers()).isNull();
    }
}
