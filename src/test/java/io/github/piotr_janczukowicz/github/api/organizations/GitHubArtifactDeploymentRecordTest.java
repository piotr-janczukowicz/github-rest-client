package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubArtifactDeploymentRecordTest {

    @Test
    void constructor_shouldLeaveRuntimeRisksNull_whenNull() {
        final GitHubArtifactDeploymentRecord deploymentRecord = GitHubArtifactDeploymentRecordBuilder.builder(
                        OrganizationsCatalogFixtures.artifactDeploymentRecord())
                .runtimeRisks(null)
                .build();

        assertThat(deploymentRecord.runtimeRisks()).isNull();
    }

    @Test
    void constructor_shouldKeepNullCollections_whenAbsent() {
        final GitHubArtifactDeploymentRecord deploymentRecord = new GitHubArtifactDeploymentRecord(
                1L, "digest", "prod", null, null, "deploy", null, null, null, null, null);

        assertThat(deploymentRecord.tags()).isNull();
        assertThat(deploymentRecord.runtimeRisks()).isNull();
    }

    @Test
    void constructor_shouldCopyRuntimeRisks_whenPresent() {
        final GitHubArtifactDeploymentRecord deploymentRecord = new GitHubArtifactDeploymentRecord(
                1L,
                "digest",
                "prod",
                null,
                null,
                "deploy",
                null,
                List.of(GitHubArtifactRuntimeRisk.SENSITIVE_DATA),
                null,
                null,
                null);

        assertThat(deploymentRecord.runtimeRisks()).containsExactly(GitHubArtifactRuntimeRisk.SENSITIVE_DATA);
    }

    @Test
    void constructor_shouldThrow_whenRuntimeRisksContainDuplicates() {
        final Throwable thrown = catchThrowable(() -> GitHubArtifactDeploymentRecordBuilder.builder(
                        OrganizationsCatalogFixtures.artifactDeploymentRecord())
                .runtimeRisks(
                        List.of(GitHubArtifactRuntimeRisk.SENSITIVE_DATA, GitHubArtifactRuntimeRisk.SENSITIVE_DATA))
                .build());

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("runtimeRisks")
                .hasStackTraceContaining("requireUniqueItems");
    }

    @Test
    void constructor_shouldThrow_whenRuntimeRisksExceedMaxItems() {
        final Throwable thrown = catchThrowable(() -> GitHubArtifactDeploymentRecordBuilder.builder(
                        OrganizationsCatalogFixtures.artifactDeploymentRecord())
                .runtimeRisks(List.of(
                        GitHubArtifactRuntimeRisk.CRITICAL_RESOURCE,
                        GitHubArtifactRuntimeRisk.INTERNET_EXPOSED,
                        GitHubArtifactRuntimeRisk.LATERAL_MOVEMENT,
                        GitHubArtifactRuntimeRisk.SENSITIVE_DATA,
                        GitHubArtifactRuntimeRisk.CRITICAL_RESOURCE))
                .build());

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("runtimeRisks")
                .hasStackTraceContaining("requireSizeAtMost");
        assertThat(thrown.getStackTrace())
                .extracting(StackTraceElement::getMethodName)
                .doesNotContain("requireUniqueItems");
    }
}
