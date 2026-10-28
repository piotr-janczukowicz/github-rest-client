package io.github.piotr_janczukowicz.github.api.deployments;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeploymentBranchPoliciesPageTest {

    @Test
    void constructor_shouldThrow_whenBranchPoliciesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubDeploymentBranchPoliciesPage(0, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("branchPolicies");
    }

    @Test
    void constructor_shouldCopyBranchPolicies_whenPresent() {
        final GitHubDeploymentBranchPolicy policy = DeploymentsCatalogFixtures.deploymentBranchPolicy();

        final GitHubDeploymentBranchPoliciesPage page = new GitHubDeploymentBranchPoliciesPage(1, List.of(policy));

        assertThat(page.branchPolicies()).containsExactly(policy);
    }
}
