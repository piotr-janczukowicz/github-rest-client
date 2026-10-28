package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

/**
 * Answer to starting an upgrade package download: the per-node results (HTTP 200) or the orchestration of a download
 * that runs on several nodes in parallel (HTTP 202).
 */
public sealed interface GitHubManageDownloadUpgradeResult
        permits GitHubManageDownloadUpgradeNodes, GitHubManageDownloadUpgradeOrchestration {}
