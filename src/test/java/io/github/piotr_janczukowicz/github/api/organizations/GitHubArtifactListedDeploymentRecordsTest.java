package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubArtifactListedDeploymentRecordsTest {

    @Test
    void constructor_shouldKeepNullFields_whenAbsent() {
        final GitHubArtifactListedDeploymentRecords page = new GitHubArtifactListedDeploymentRecords(null, null);

        assertThat(page.totalCount()).isNull();
        assertThat(page.deploymentRecords()).isNull();
    }
}
