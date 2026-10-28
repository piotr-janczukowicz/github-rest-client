package io.github.piotr_janczukowicz.github.api.deployments;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

/**
 * Result of {@code POST /repos/{owner}/{repo}/deployments}: HTTP 201 created deployment or HTTP 202 merged-branch
 * acceptance.
 */
public sealed interface GitHubCreateDeploymentResult
        permits GitHubCreateDeploymentResult.Created, GitHubCreateDeploymentResult.MergedBranch {

    /**
     * Created deployment (HTTP 201).
     *
     * @param deployment created deployment
     * @return result
     */
    static GitHubCreateDeploymentResult ofCreated(final GitHubDeployment deployment) {
        return new Created(deployment);
    }

    /**
     * Auto-merged branch acceptance (HTTP 202).
     *
     * @param mergedBranch merged-branch response
     * @return result
     */
    static GitHubCreateDeploymentResult ofMergedBranch(final GitHubDeploymentMergedBranch mergedBranch) {
        return new MergedBranch(mergedBranch);
    }

    /**
     * Created deployment.
     *
     * @param deployment deployment
     */
    record Created(GitHubDeployment deployment) implements GitHubCreateDeploymentResult {
        public Created {
            requireNonNull(deployment, "deployment");
        }
    }

    /**
     * Merged-branch acceptance.
     *
     * @param mergedBranch merged-branch body
     */
    record MergedBranch(GitHubDeploymentMergedBranch mergedBranch) implements GitHubCreateDeploymentResult {
        public MergedBranch {
            requireNonNull(mergedBranch, "mergedBranch");
        }
    }
}
