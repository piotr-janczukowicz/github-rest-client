package io.github.piotr_janczukowicz.github.api.campaigns;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateCampaignPayloadTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubCreateCampaignPayloadBuilder builder = GitHubCreateCampaignPayloadBuilder.builder(
                        CampaignsCatalogFixtures.createCampaignPayload())
                .name(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenNameTooLong() {
        final GitHubCreateCampaignPayloadBuilder builder = GitHubCreateCampaignPayloadBuilder.builder(
                        CampaignsCatalogFixtures.createCampaignPayload())
                .name("x".repeat(51));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenDescriptionTooLong() {
        final GitHubCreateCampaignPayloadBuilder builder = GitHubCreateCampaignPayloadBuilder.builder(
                        CampaignsCatalogFixtures.createCampaignPayload())
                .description("x".repeat(256));

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("description");
    }

    @Test
    void constructor_shouldThrow_whenManagersExceedMax() {
        final GitHubCreateCampaignPayloadBuilder builder = GitHubCreateCampaignPayloadBuilder.builder(
                        CampaignsCatalogFixtures.createCampaignPayload())
                .managers(IntStream.range(0, 11)
                        .mapToObj(index -> "octocat" + index)
                        .toList());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("managers");
    }

    @Test
    void constructor_shouldThrow_whenCodeScanningAlertsEmpty() {
        final GitHubCreateCampaignPayloadBuilder builder = GitHubCreateCampaignPayloadBuilder.builder(
                        CampaignsCatalogFixtures.createCampaignPayload())
                .codeScanningAlerts(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("codeScanningAlerts");
    }

    @Test
    void constructor_shouldLeaveManagersNull_whenNull() {
        final GitHubCreateCampaignPayload payload = GitHubCreateCampaignPayloadBuilder.builder(
                        CampaignsCatalogFixtures.createCampaignPayload())
                .managers(null)
                .build();

        assertThat(payload.managers()).isNull();
    }

    @Test
    void constructor_shouldLeaveTeamManagersNull_whenNull() {
        final GitHubCreateCampaignPayload payload = GitHubCreateCampaignPayloadBuilder.builder(
                        CampaignsCatalogFixtures.createCampaignPayload())
                .teamManagers(null)
                .build();

        assertThat(payload.teamManagers()).isNull();
    }

    @Test
    void constructor_shouldLeaveCodeScanningAlertsNull_whenNull() {
        final GitHubCreateCampaignPayload payload = GitHubCreateCampaignPayloadBuilder.builder(
                        CampaignsCatalogFixtures.createCampaignPayload())
                .codeScanningAlerts(null)
                .build();

        assertThat(payload.codeScanningAlerts()).isNull();
    }
}
