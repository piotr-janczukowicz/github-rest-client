package io.github.piotr_janczukowicz.github.api.codequality;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import java.time.Instant;
import java.util.List;

public final class CodeQualityCatalogFixtures {

    private static final Instant CREATED_AT = Instant.parse("2026-01-23T12:34:56Z");
    private static final Instant UPDATED_AT = Instant.parse("2023-01-01T00:00:00Z");

    private CodeQualityCatalogFixtures() {}

    public static GitHubCodeQualityFinding codeQualityFinding() {
        return GitHubCodeQualityFindingBuilder.builder()
                .number(42)
                .state(GitHubCodeQualityFindingState.OPEN)
                .url("https://api.github.com/repos/octocat/hello-world/code-quality/findings/42")
                .rule(codeQualityFindingRule())
                .location(codeQualityFindingLocation())
                .message(codeQualityFindingMessage())
                .createdAt(CREATED_AT)
                .build();
    }

    public static GitHubCodeQualityFindingRule codeQualityFindingRule() {
        return GitHubCodeQualityFindingRuleBuilder.builder()
                .id("java/useless-null-check")
                .title("Useless null check")
                .description(
                        "Checking whether an expression is null when that expression cannot possibly be null is useless.")
                .help(
                        "# Useless null check\nSometimes you can guarantee that a particular variable will never be null.")
                .severity(GitHubCodeQualityFindingRuleSeverity.WARNING)
                .category(GitHubCodeQualityFindingRuleCategory.MAINTAINABILITY)
                .build();
    }

    public static GitHubCodeQualityFindingLocation codeQualityFindingLocation() {
        return GitHubCodeQualityFindingLocationBuilder.builder()
                .path("java/UselessNullCheck.java")
                .startLine(9)
                .startColumn(4)
                .endLine(9)
                .endColumn(18)
                .build();
    }

    public static GitHubCodeQualityFindingMessage codeQualityFindingMessage() {
        return GitHubCodeQualityFindingMessageBuilder.builder()
                .text("This check is useless. o cannot be null at this check, since it is guarded by instanceof.")
                .markdown("This check is useless. [o](java/UselessNullCheck.java#L9C4-L9C18) cannot be null at this "
                        + "check, since it is guarded by [...instanceof...](java/UselessNullCheck.java#L7C13-L7C25).")
                .build();
    }

    public static GitHubCodeQualitySetup codeQualitySetup() {
        return GitHubCodeQualitySetupBuilder.builder()
                .state(GitHubCodeQualitySetupState.CONFIGURED)
                .languages(List.of(
                        GitHubCodeQualitySetupLanguage.JAVASCRIPT_TYPESCRIPT, GitHubCodeQualitySetupLanguage.PYTHON))
                .runnerType(GitHubCodeQualitySetupRunnerType.STANDARD)
                .runnerLabel("code-scanning")
                .updatedAt(UPDATED_AT)
                .schedule(GitHubCodeQualitySetupSchedule.WEEKLY)
                .aiFindingsOption(GitHubCodeQualitySetupAiFindingsOption.ON_PUSH)
                .build();
    }

    public static GitHubCodeQualitySetupUpdate codeQualitySetupUpdate() {
        return GitHubCodeQualitySetupUpdateBuilder.builder()
                .state(GitHubCodeQualitySetupUpdateState.CONFIGURED)
                .runnerType(GitHubCodeQualitySetupUpdateRunnerType.STANDARD)
                .runnerLabel("code-scanning")
                .languages(List.of(
                        GitHubCodeQualitySetupUpdateLanguage.JAVASCRIPT_TYPESCRIPT,
                        GitHubCodeQualitySetupUpdateLanguage.PYTHON,
                        GitHubCodeQualitySetupUpdateLanguage.RUBY))
                .aiFindingsOption(GitHubCodeQualitySetupUpdateAiFindingsOption.ON_PUSH)
                .build();
    }

    public static GitHubCodeQualitySetupUpdateResponse codeQualitySetupUpdateResponse() {
        return GitHubCodeQualitySetupUpdateResponseBuilder.builder()
                .runId(42L)
                .runUrl("https://api.github.com/repos/octocat/hello-world/actions/runs/42")
                .build();
    }

    public static GitHubListFindingsForRepoFilter listFindingsForRepoFilter() {
        return GitHubListFindingsForRepoFilterBuilder.builder()
                .direction(GitHubDirection.DESC)
                .state(GitHubListFindingsForRepoState.OPEN)
                .build();
    }

    public static GitHubCodeQualityFindingState codeQualityFindingState() {
        return GitHubCodeQualityFindingState.OPEN;
    }

    public static GitHubCodeQualityFindingRuleSeverity codeQualityFindingRuleSeverity() {
        return GitHubCodeQualityFindingRuleSeverity.WARNING;
    }

    public static GitHubCodeQualityFindingRuleCategory codeQualityFindingRuleCategory() {
        return GitHubCodeQualityFindingRuleCategory.MAINTAINABILITY;
    }

    public static GitHubCodeQualitySetupState codeQualitySetupState() {
        return GitHubCodeQualitySetupState.CONFIGURED;
    }

    public static GitHubCodeQualitySetupLanguage codeQualitySetupLanguage() {
        return GitHubCodeQualitySetupLanguage.JAVASCRIPT_TYPESCRIPT;
    }

    public static GitHubCodeQualitySetupRunnerType codeQualitySetupRunnerType() {
        return GitHubCodeQualitySetupRunnerType.STANDARD;
    }

    public static GitHubCodeQualitySetupSchedule codeQualitySetupSchedule() {
        return GitHubCodeQualitySetupSchedule.WEEKLY;
    }

    public static GitHubCodeQualitySetupAiFindingsOption codeQualitySetupAiFindingsOption() {
        return GitHubCodeQualitySetupAiFindingsOption.ON_PUSH;
    }

    public static GitHubCodeQualitySetupUpdateState codeQualitySetupUpdateState() {
        return GitHubCodeQualitySetupUpdateState.CONFIGURED;
    }

    public static GitHubCodeQualitySetupUpdateRunnerType codeQualitySetupUpdateRunnerType() {
        return GitHubCodeQualitySetupUpdateRunnerType.STANDARD;
    }

    public static GitHubCodeQualitySetupUpdateLanguage codeQualitySetupUpdateLanguage() {
        return GitHubCodeQualitySetupUpdateLanguage.JAVASCRIPT_TYPESCRIPT;
    }

    public static GitHubCodeQualitySetupUpdateAiFindingsOption codeQualitySetupUpdateAiFindingsOption() {
        return GitHubCodeQualitySetupUpdateAiFindingsOption.ON_PUSH;
    }

    public static GitHubListFindingsForRepoState listFindingsForRepoState() {
        return GitHubListFindingsForRepoState.OPEN;
    }
}
