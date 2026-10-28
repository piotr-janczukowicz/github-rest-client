package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateDeploymentResultTest {

    @Test
    void ofCreated_shouldStoreDeployment_whenCalled() {
        final GitHubDeployment deployment = DeploymentsCatalogFixtures.deployment();

        final GitHubCreateDeploymentResult result = GitHubCreateDeploymentResult.ofCreated(deployment);

        assertThat(result).isEqualTo(new GitHubCreateDeploymentResult.Created(deployment));
    }

    @Test
    void ofCreated_shouldThrow_whenDeploymentNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateDeploymentResult.ofCreated(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("deployment");
    }

    @Test
    void ofMergedBranch_shouldStoreValue_whenCalled() {
        final GitHubDeploymentMergedBranch mergedBranch = DeploymentsCatalogFixtures.deploymentMergedBranch();

        final GitHubCreateDeploymentResult result = GitHubCreateDeploymentResult.ofMergedBranch(mergedBranch);

        assertThat(result).isEqualTo(new GitHubCreateDeploymentResult.MergedBranch(mergedBranch));
    }

    @Test
    void ofMergedBranch_shouldThrow_whenMergedBranchNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateDeploymentResult.ofMergedBranch(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("mergedBranch");
    }

    @Test
    void constructor_shouldThrow_whenDeploymentNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateDeploymentResult.Created(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("deployment");
    }

    @Test
    void constructor_shouldThrow_whenMergedBranchNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubCreateDeploymentResult.MergedBranch(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("mergedBranch");
    }
}
