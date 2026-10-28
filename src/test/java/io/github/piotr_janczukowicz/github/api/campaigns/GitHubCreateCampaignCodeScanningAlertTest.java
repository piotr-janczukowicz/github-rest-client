package io.github.piotr_janczukowicz.github.api.campaigns;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateCampaignCodeScanningAlertTest {

    @Test
    void constructor_shouldThrow_whenAlertNumbersEmpty() {
        final GitHubCreateCampaignCodeScanningAlertBuilder builder =
                GitHubCreateCampaignCodeScanningAlertBuilder.builder(
                                CampaignsCatalogFixtures.createCampaignCodeScanningAlert())
                        .alertNumbers(List.of());

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("alertNumbers");
    }

    @Test
    void constructor_shouldThrow_whenAlertNumbersNull() {
        final GitHubCreateCampaignCodeScanningAlertBuilder builder =
                GitHubCreateCampaignCodeScanningAlertBuilder.builder(
                                CampaignsCatalogFixtures.createCampaignCodeScanningAlert())
                        .alertNumbers(null);

        final Throwable thrown = catchThrowable(builder::build);

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("alertNumbers");
    }
}
