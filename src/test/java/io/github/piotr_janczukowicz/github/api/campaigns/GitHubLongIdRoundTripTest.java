package io.github.piotr_janczukowicz.github.api.campaigns;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLongIdRoundTripTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void encode_shouldRoundTripRepositoryId_whenRepositoryIdAboveIntMax() {
        final GitHubCreateCampaignCodeScanningAlert value = GitHubCreateCampaignCodeScanningAlertBuilder.builder(
                        CampaignsCatalogFixtures.createCampaignCodeScanningAlert())
                .repositoryId(3_000_000_000L)
                .build();

        final GitHubCreateCampaignCodeScanningAlert decoded =
                CODEC.decode(CODEC.encode(value), GitHubCreateCampaignCodeScanningAlert.class);

        assertThat(decoded).isEqualTo(value);
    }
}
