package io.github.piotr_janczukowicz.github.api.codequality;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLongIdRoundTripTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void encode_shouldRoundTripRunId_whenRunIdAboveIntMax() {
        final GitHubCodeQualitySetupUpdateResponse value = GitHubCodeQualitySetupUpdateResponseBuilder.builder(
                        CodeQualityCatalogFixtures.codeQualitySetupUpdateResponse())
                .runId(3_000_000_000L)
                .build();

        final GitHubCodeQualitySetupUpdateResponse decoded =
                CODEC.decode(CODEC.encode(value), GitHubCodeQualitySetupUpdateResponse.class);

        assertThat(decoded).isEqualTo(value);
    }
}
