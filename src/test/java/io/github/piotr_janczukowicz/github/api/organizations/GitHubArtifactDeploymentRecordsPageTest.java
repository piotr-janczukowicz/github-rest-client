package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubArtifactDeploymentRecordsPageTest {

    @Test
    void constructor_shouldKeepNullLists_whenAbsent() {
        final GitHubArtifactDeploymentRecordsPage page = new GitHubArtifactDeploymentRecordsPage(0, null, null);

        assertThat(page.deploymentRecords()).isNull();
        assertThat(page.errors()).isNull();
    }
}
