package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLongIdRoundTripTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void encode_shouldRoundTripId_whenIdAboveIntMax() {
        final GitHubBranchRestrictionPolicyApp app = GitHubBranchRestrictionPolicyAppBuilder.builder(
                        BranchesCatalogFixtures.branchRestrictionPolicyApp())
                .id(3_000_000_000L)
                .build();

        final GitHubBranchRestrictionPolicyApp decoded =
                CODEC.decode(CODEC.encode(app), GitHubBranchRestrictionPolicyApp.class);

        assertThat(decoded).isEqualTo(app);
    }
}
