package io.github.piotr_janczukowicz.github.api.apps;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubMarketplaceListingPlanPriceModelTest {

    @Test
    void fromWire_shouldReturnFREE_whenWireFREE() {
        final GitHubMarketplaceListingPlanPriceModel decoded = GitHubMarketplaceListingPlanPriceModel.fromWire("FREE");

        assertThat(decoded).isEqualTo(GitHubMarketplaceListingPlanPriceModel.FREE);
    }

    @Test
    void fromWire_shouldReturnFlatRate_whenWireFlatRate() {
        final GitHubMarketplaceListingPlanPriceModel decoded =
                GitHubMarketplaceListingPlanPriceModel.fromWire("FLAT_RATE");

        assertThat(decoded).isEqualTo(GitHubMarketplaceListingPlanPriceModel.FLAT_RATE);
    }

    @Test
    void fromWire_shouldReturnPerUnit_whenWirePerUnit() {
        final GitHubMarketplaceListingPlanPriceModel decoded =
                GitHubMarketplaceListingPlanPriceModel.fromWire("PER_UNIT");

        assertThat(decoded).isEqualTo(GitHubMarketplaceListingPlanPriceModel.PER_UNIT);
    }

    @Test
    void fromWire_shouldThrow_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubMarketplaceListingPlanPriceModel.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubMarketplaceListingPlanPriceModel decoded = GitHubMarketplaceListingPlanPriceModel.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubMarketplaceListingPlanPriceModel.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
