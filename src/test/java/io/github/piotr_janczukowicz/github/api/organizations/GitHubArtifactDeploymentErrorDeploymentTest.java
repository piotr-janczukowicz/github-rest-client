package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubArtifactDeploymentErrorDeploymentTest {

    @Test
    void constructor_shouldKeepNullCollections_whenAbsent() {
        final GitHubArtifactDeploymentErrorDeployment deployment =
                new GitHubArtifactDeploymentErrorDeployment("name", "digest", "deploy", null, null, null, null, null);

        assertThat(deployment.tags()).isNull();
        assertThat(deployment.runtimeRisks()).isNull();
    }

    @Test
    void constructor_shouldCopyTags_whenPresent() {
        final GitHubArtifactDeploymentErrorDeployment deployment = new GitHubArtifactDeploymentErrorDeployment(
                "name", "digest", "deploy", null, null, null, Map.of("team", "platform"), null);

        assertThat(deployment.tags()).containsEntry("team", "platform");
    }
}
