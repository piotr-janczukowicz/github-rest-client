package io.github.piotr_janczukowicz.github.api.actions;

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
        final GitHubArtifact artifact = GitHubArtifactBuilder.builder(ActionsCatalogFixtures.artifact())
                .id(3_000_000_000L)
                .build();

        final GitHubArtifact decoded = CODEC.decode(CODEC.encode(artifact), GitHubArtifact.class);

        assertThat(decoded).isEqualTo(artifact);
    }

    @Test
    void encode_shouldRoundTripWorkflowId_whenWorkflowIdAboveIntMax() {
        final GitHubWorkflowRun workflowRun = GitHubWorkflowRunBuilder.builder(ActionsCatalogFixtures.workflowRun())
                .workflowId(3_000_000_000L)
                .build();

        final GitHubWorkflowRun decoded = CODEC.decode(CODEC.encode(workflowRun), GitHubWorkflowRun.class);

        assertThat(decoded).isEqualTo(workflowRun);
    }
}
