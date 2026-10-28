package io.github.piotr_janczukowicz.github.api.deployments;

import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegrationOwner;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegrationPermissions;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.json.OptionalNull;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class DeploymentsCatalogFixtures {

    static final String PAYLOAD_TEXT = "{ \"deploy\": \"migrate\" }";

    static final Instant DEPLOYED_AT = Instant.parse("2012-07-20T01:19:13Z");

    static final Instant ENVIRONMENT_AT = Instant.parse("2020-11-23T22:00:40Z");

    private DeploymentsCatalogFixtures() {}

    static GitHubDeploymentPayloadValue payloadText() {
        return GitHubDeploymentPayloadValue.ofText(PAYLOAD_TEXT);
    }

    static GitHubDeploymentPayloadValue payloadObject() {
        return GitHubDeploymentPayloadValue.ofObject(Map.of("deploy", "migrate"));
    }

    public static GitHubDeployment deployment() {
        return deployment(payloadText());
    }

    static GitHubDeployment deploymentWithObjectPayload() {
        return deployment(payloadObject());
    }

    static GitHubDeploymentStatus deploymentStatus() {
        return new GitHubDeploymentStatus(
                "https://api.github.com/repos/octocat/example/deployments/42/statuses/1",
                1L,
                "MDE2OkRlcGxveW1lbnRTdGF0dXMx",
                GitHubDeploymentStatusState.SUCCESS,
                UsersCatalogFixtures.octocat(),
                "Deployment finished successfully.",
                "production",
                "https://example.com/deployment/42/output",
                DEPLOYED_AT,
                DEPLOYED_AT,
                "https://api.github.com/repos/octocat/example/deployments/42",
                "https://api.github.com/repos/octocat/example",
                "https://example.com/environment",
                "https://example.com/deployment/42/output",
                integration());
    }

    static GitHubDeploymentBranchPolicy deploymentBranchPolicy() {
        return new GitHubDeploymentBranchPolicy(
                361_471L, "MDE2OkdhdGVCcmFuY2hQb2xpY3kzNjE0NzE=", "release/*", GitHubDeploymentBranchPolicyType.BRANCH);
    }

    static GitHubDeploymentBranchPolicySettings deploymentBranchPolicySettings() {
        return new GitHubDeploymentBranchPolicySettings(false, true);
    }

    static GitHubDeploymentProtectionRule deploymentProtectionRule() {
        return new GitHubDeploymentProtectionRule(3, "IEH37kRlcGxveW1lbnRTdGF0ddiv", true, customDeploymentRuleApp());
    }

    static GitHubCustomDeploymentRuleApp customDeploymentRuleApp() {
        return new GitHubCustomDeploymentRuleApp(
                1, "a-custom-app", "https://api.github.com/apps/a-custom-app", "GHT58kRlcGxveW1lbnRTdTY!bbcy");
    }

    static GitHubEnvironment environment() {
        return new GitHubEnvironment(
                56_780_428L,
                "MDExOkVudmlyb25tZW50NTY3ODA0Mjg=",
                "staging",
                "https://api.github.com/repos/github/hello-world/environments/staging",
                "https://github.com/github/hello-world/deployments/activity_log?environments_filter=staging",
                ENVIRONMENT_AT,
                ENVIRONMENT_AT,
                List.of(environmentProtectionRule()),
                deploymentBranchPolicySettings());
    }

    static GitHubEnvironmentProtectionRule environmentProtectionRule() {
        return new GitHubEnvironmentProtectionRule(
                1, "n", "required_reviewers", 30, false, List.of(environmentReviewerEntry()));
    }

    static GitHubEnvironmentReviewerEntry environmentReviewerEntry() {
        return new GitHubEnvironmentReviewerEntry(
                GitHubDeploymentReviewerType.USER, GitHubEnvironmentReviewer.ofUser(UsersCatalogFixtures.octocat()));
    }

    static GitHubCreateDeploymentPayload createDeploymentPayload() {
        return createDeploymentPayload(payloadText());
    }

    static GitHubCreateDeploymentPayload createDeploymentPayloadWithObject() {
        return createDeploymentPayload(payloadObject());
    }

    static GitHubCreateDeploymentStatusPayload createDeploymentStatusPayload() {
        return new GitHubCreateDeploymentStatusPayload(
                GitHubDeploymentStatusState.SUCCESS,
                "https://example.com/deployment/42/output",
                "https://example.com/deployment/42/output",
                "Deployment finished successfully.",
                "production",
                "https://example.com/environment",
                true);
    }

    static GitHubCreateDeploymentProtectionRulePayload createDeploymentProtectionRulePayload() {
        return new GitHubCreateDeploymentProtectionRulePayload(1L);
    }

    static GitHubCreateDeploymentBranchPolicyPayload createDeploymentBranchPolicyPayload() {
        return new GitHubCreateDeploymentBranchPolicyPayload("release/*", GitHubDeploymentBranchPolicyType.BRANCH);
    }

    static GitHubCreateOrUpdateEnvironmentPayload createOrUpdateEnvironmentPayload() {
        return new GitHubCreateOrUpdateEnvironmentPayload(
                30,
                true,
                List.of(createOrUpdateEnvironmentReviewerPayload()),
                OptionalNull.of(deploymentBranchPolicySettings()));
    }

    static GitHubCreateOrUpdateEnvironmentReviewerPayload createOrUpdateEnvironmentReviewerPayload() {
        return new GitHubCreateOrUpdateEnvironmentReviewerPayload(GitHubDeploymentReviewerType.USER, 1L);
    }

    static GitHubUpdateDeploymentBranchPolicyPayload updateDeploymentBranchPolicyPayload() {
        return new GitHubUpdateDeploymentBranchPolicyPayload("release/*");
    }

    static GitHubListDeploymentsFilter listDeploymentsFilter() {
        return new GitHubListDeploymentsFilter(
                "a84d88e7554fc1fa21bcbc4efae3c782a70d2b9d", "topic-branch", "deploy", "production");
    }

    static GitHubDeploymentMergedBranch deploymentMergedBranch() {
        return new GitHubDeploymentMergedBranch("Auto-merged the default branch into the requested ref.");
    }

    static GitHubEnvironmentsPage environmentsPage() {
        return new GitHubEnvironmentsPage(1, List.of(environment()));
    }

    static GitHubDeploymentBranchPoliciesPage deploymentBranchPoliciesPage() {
        return new GitHubDeploymentBranchPoliciesPage(1, List.of(deploymentBranchPolicy()));
    }

    static GitHubDeploymentProtectionRulesPage deploymentProtectionRulesPage() {
        return new GitHubDeploymentProtectionRulesPage(1, List.of(deploymentProtectionRule()));
    }

    static GitHubCustomDeploymentRuleIntegrationsPage customDeploymentRuleIntegrationsPage() {
        return new GitHubCustomDeploymentRuleIntegrationsPage(1, List.of(customDeploymentRuleApp()));
    }

    private static GitHubDeployment deployment(final GitHubDeploymentPayloadValue payload) {
        return new GitHubDeployment(
                "https://api.github.com/repos/octocat/example/deployments/42",
                42L,
                "MDEwOkRlcGxveW1lbnQx",
                "a84d88e7554fc1fa21bcbc4efae3c782a70d2b9d",
                "topic-branch",
                "deploy",
                payload,
                "staging",
                "production",
                "Deploy request from hubot",
                UsersCatalogFixtures.octocat(),
                DEPLOYED_AT,
                DEPLOYED_AT,
                "https://api.github.com/repos/octocat/example/deployments/42/statuses",
                "https://api.github.com/repos/octocat/example",
                true,
                true,
                integration());
    }

    private static GitHubCreateDeploymentPayload createDeploymentPayload(final GitHubDeploymentPayloadValue payload) {
        return new GitHubCreateDeploymentPayload(
                "topic-branch",
                "deploy",
                true,
                List.of("ci/travis"),
                payload,
                "production",
                "Deploy request from hubot",
                true,
                true);
    }

    private static GitHubIntegration integration() {
        return new GitHubIntegration(
                37L,
                "probot-owners",
                "MDExOkludGVncmF0aW9uMQ==",
                "Iv1.25b5d1e65ffc4022",
                GitHubIntegrationOwner.ofUser(UsersCatalogFixtures.octocat()),
                "Probot Owners",
                "The description of the app.",
                "https://example.com",
                "https://github.com/apps/super-ci",
                Instant.parse("2017-07-08T20:18:44Z"),
                Instant.parse("2017-07-08T20:18:44Z"),
                new GitHubIntegrationPermissions("read", "read", "read", "read", "write", Map.of()),
                List.of("label", "deployment"),
                5);
    }
}
