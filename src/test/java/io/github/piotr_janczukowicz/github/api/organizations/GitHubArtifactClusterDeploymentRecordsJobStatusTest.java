package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubArtifactClusterDeploymentRecordsJobStatusTest {

    @Test
    void constructor_shouldKeepNullErrors_whenAbsent() {
        final GitHubArtifactClusterDeploymentRecordsJobStatus status =
                new GitHubArtifactClusterDeploymentRecordsJobStatus(
                        123L, GitHubArtifactClusterJobStatus.COMPLETED, null, null, null);

        assertThat(status.errors()).isNull();
    }
}
