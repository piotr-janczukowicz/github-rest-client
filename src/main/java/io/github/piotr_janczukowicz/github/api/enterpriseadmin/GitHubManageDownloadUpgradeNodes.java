package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import java.util.List;

/**
 * Per-node results of a download that was answered directly (HTTP 200). GitHub sends them as a JSON array, so this
 * wrapper has no schema of its own.
 *
 * @param nodes the result of each node
 */
public record GitHubManageDownloadUpgradeNodes(List<GitHubManageDownloadUpgradeNodeResult> nodes)
        implements GitHubManageDownloadUpgradeResult {

    public GitHubManageDownloadUpgradeNodes {
        requireNonNull(nodes, "nodes");
    }
}
