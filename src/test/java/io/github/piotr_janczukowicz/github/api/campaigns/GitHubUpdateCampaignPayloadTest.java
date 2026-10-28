package io.github.piotr_janczukowicz.github.api.campaigns;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateCampaignPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameTooLong() {
        final GitHubUpdateCampaignPayloadBuilder builder = GitHubUpdateCampaignPayloadBuilder.builder(
                        CampaignsCatalogFixtures.updateCampaignPayload())
                .name("x".repeat(51));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenNameEmpty() {
        final GitHubUpdateCampaignPayloadBuilder builder = GitHubUpdateCampaignPayloadBuilder.builder(
                        CampaignsCatalogFixtures.updateCampaignPayload())
                .name("");

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionTooLong() {
        final GitHubUpdateCampaignPayloadBuilder builder = GitHubUpdateCampaignPayloadBuilder.builder(
                        CampaignsCatalogFixtures.updateCampaignPayload())
                .description("x".repeat(256));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("description");
    }

    @Test
    void constructor_shouldThrow_whenManagersExceedMax() {
        final GitHubUpdateCampaignPayloadBuilder builder = GitHubUpdateCampaignPayloadBuilder.builder(
                        CampaignsCatalogFixtures.updateCampaignPayload())
                .managers(IntStream.range(0, 11)
                        .mapToObj(index -> "octocat" + index)
                        .toList());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("managers");
    }

    @Test
    void constructor_shouldLeaveManagersNull_whenNull() {
        final GitHubUpdateCampaignPayload payload = GitHubUpdateCampaignPayloadBuilder.builder(
                        CampaignsCatalogFixtures.updateCampaignPayload())
                .managers(null)
                .build();

        assertThat(payload.managers()).isNull();
    }

    @Test
    void constructor_shouldLeaveTeamManagersNull_whenNull() {
        final GitHubUpdateCampaignPayload payload = GitHubUpdateCampaignPayloadBuilder.builder(
                        CampaignsCatalogFixtures.updateCampaignPayload())
                .teamManagers(null)
                .build();

        assertThat(payload.teamManagers()).isNull();
    }
}
