package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubArtifactClusterDeploymentRecordsJobTest {

    @Test
    void constructor_shouldKeepNullErrors_whenAbsent() {
        final GitHubArtifactClusterDeploymentRecordsJob job = new GitHubArtifactClusterDeploymentRecordsJob(123L, null);

        assertThat(job.errors()).isNull();
    }
}
