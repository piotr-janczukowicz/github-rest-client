package io.github.piotr_janczukowicz.github.api.meta;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubApiOverviewArtifactAttestationsTest {

    @Test
    void constructor_shouldKeepNullServices_whenAbsent() {
        final GitHubApiOverviewArtifactAttestations attestations =
                GitHubApiOverviewArtifactAttestationsBuilder.builder().build();

        assertThat(attestations.services()).isNull();
    }
}
