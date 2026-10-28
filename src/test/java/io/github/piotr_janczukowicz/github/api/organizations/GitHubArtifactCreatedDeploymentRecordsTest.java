package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubArtifactCreatedDeploymentRecordsTest {

    @Test
    void constructor_shouldKeepNullDeploymentRecords_whenAbsent() {
        final GitHubArtifactCreatedDeploymentRecords page = new GitHubArtifactCreatedDeploymentRecords(0, null);

        assertThat(page.deploymentRecords()).isNull();
    }
}
