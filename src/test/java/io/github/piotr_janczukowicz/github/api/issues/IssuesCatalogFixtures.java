package io.github.piotr_janczukowicz.github.api.issues;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegration;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegrationOwner;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegrationPermissions;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueType;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueTypeColor;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueTypeWebhook;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestReviewComment;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestReviewCommentLinks;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestReviewCommentSide;
import io.github.piotr_janczukowicz.github.api.pullrequests.GitHubPullRequestReviewCommentSubjectType;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubLink;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryPermissions;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.search.GitHubAuthorAssociation;
import io.github.piotr_janczukowicz.github.api.search.GitHubIssueComment;
import io.github.piotr_janczukowicz.github.api.search.GitHubIssueDependenciesSummary;
import io.github.piotr_janczukowicz.github.api.search.GitHubIssueFieldDataType;
import io.github.piotr_janczukowicz.github.api.search.GitHubIssueFieldValue;
import io.github.piotr_janczukowicz.github.api.search.GitHubMilestone;
import io.github.piotr_janczukowicz.github.api.search.GitHubMilestoneState;
import io.github.piotr_janczukowicz.github.api.search.GitHubReactionRollup;
import io.github.piotr_janczukowicz.github.api.search.GitHubSubIssuesSummary;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamAccessSource;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamOwnershipType;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamSimple;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.json.OptionalNull;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class IssuesCatalogFixtures {

    private static final Instant CREATED = Instant.parse("2011-04-22T13:33:48Z");
    private static final Instant CLOSED = Instant.parse("2011-04-30T12:00:00Z");
    private static final Instant PINNED = Instant.parse("2011-04-14T16:00:49Z");
    private static final String ISSUE_API = "https://api.github.com/repos/octocat/Spoon-Knife/issues/1309";
    private static final String ISSUE_HTML = "https://github.com/octocat/Spoon-Knife/issues/1309";

    private IssuesCatalogFixtures() {}

    public static GitHubIssue issue() {
        return new GitHubIssue(
                ISSUE_API,
                "https://api.github.com/repos/octocat/Spoon-Knife",
                ISSUE_API + "/labels{/name}",
                ISSUE_API + "/comments",
                ISSUE_API + "/events",
                ISSUE_HTML,
                215_335_132L,
                "MDU6SXNzdWUyMTUzMzUxMzI=",
                1309,
                "Found a bug",
                false,
                "resolved",
                List.of(UsersCatalogFixtures.octocat()),
                UsersCatalogFixtures.octocat(),
                List.of(issueLabel()),
                new GitHubSubIssuesSummary(3, 1, 33),
                new GitHubIssueDependenciesSummary(1, 2, 1, 2),
                List.of(issueFieldValue()),
                "open",
                GitHubIssueStateReason.COMPLETED,
                milestone(),
                5,
                CREATED,
                CREATED,
                CLOSED,
                issuePullRequest(),
                "I'm having a problem with this.",
                GitHubAuthorAssociation.OWNER,
                false,
                UsersCatalogFixtures.octocat(),
                "<p>I'm having a problem with this.</p>",
                "I'm having a problem with this.",
                ISSUE_API + "/timeline",
                issueType(),
                RepositoriesCatalogFixtures.teamEnvironmentRepository(),
                integration(),
                issueComment(),
                issueReactions(),
                "https://api.github.com/repos/octocat/Spoon-Knife/issues/1");
    }

    static GitHubIssueUpdate issueUpdate() {
        return new GitHubIssueUpdate(
                ISSUE_API,
                "https://api.github.com/repos/octocat/Spoon-Knife",
                ISSUE_API + "/labels{/name}",
                ISSUE_API + "/comments",
                ISSUE_API + "/events",
                ISSUE_HTML,
                215_335_132L,
                "MDU6SXNzdWUyMTUzMzUxMzI=",
                1309,
                "Found a bug",
                false,
                "resolved",
                List.of(UsersCatalogFixtures.octocat()),
                UsersCatalogFixtures.octocat(),
                List.of(issueLabel()),
                new GitHubSubIssuesSummary(3, 1, 33),
                new GitHubIssueDependenciesSummary(1, 2, 1, 2),
                List.of(issueFieldValue()),
                "open",
                GitHubIssueStateReason.COMPLETED,
                milestone(),
                5,
                CREATED,
                CREATED,
                CLOSED,
                issuePullRequest(),
                "I'm having a problem with this.",
                GitHubAuthorAssociation.OWNER,
                false,
                UsersCatalogFixtures.octocat(),
                "<p>I'm having a problem with this.</p>",
                "I'm having a problem with this.",
                ISSUE_API + "/timeline",
                issueType(),
                RepositoriesCatalogFixtures.teamEnvironmentRepository(),
                integration(),
                issueComment(),
                issueReactions(),
                "https://api.github.com/repos/octocat/Spoon-Knife/issues/1",
                issueUpdateSuggestions());
    }

    static GitHubIssueUpdateSuggestions issueUpdateSuggestions() {
        return new GitHubIssueUpdateSuggestions(
                List.of(issueUpdateTypeSuggestion()),
                List.of(issueUpdateFieldSuggestion()),
                List.of(issueUpdateLabelSuggestion()),
                List.of(issueUpdateAssigneeSuggestion()),
                List.of(issueUpdateStateSuggestion()));
    }

    static GitHubIssueUpdateTypeSuggestion issueUpdateTypeSuggestion() {
        return new GitHubIssueUpdateTypeSuggestion(
                "Bug",
                "looks like a bug",
                true,
                GitHubIssueUpdateConfidence.HIGH,
                false,
                GitHubIssueUpdateIgnoredReason.ALREADY_APPLIED);
    }

    static GitHubIssueUpdateFieldSuggestion issueUpdateFieldSuggestion() {
        return new GitHubIssueUpdateFieldSuggestion(
                1L,
                GitHubIssueFieldValueWire.text("High"),
                "priority",
                true,
                GitHubIssueUpdateConfidence.MEDIUM,
                false,
                GitHubIssueUpdateIgnoredReason.ALREADY_APPLIED);
    }

    static GitHubIssueUpdateLabelSuggestion issueUpdateLabelSuggestion() {
        return new GitHubIssueUpdateLabelSuggestion(
                "bug",
                "because",
                true,
                GitHubIssueUpdateConfidence.HIGH,
                false,
                GitHubIssueUpdateIgnoredReason.ALREADY_APPLIED);
    }

    static GitHubIssueUpdateAssigneeSuggestion issueUpdateAssigneeSuggestion() {
        return new GitHubIssueUpdateAssigneeSuggestion(
                "octocat",
                "because",
                true,
                GitHubIssueUpdateConfidence.LOW,
                false,
                GitHubIssueUpdateIgnoredReason.ISSUE_ALREADY_CLOSED);
    }

    static GitHubIssueUpdateStateSuggestion issueUpdateStateSuggestion() {
        return new GitHubIssueUpdateStateSuggestion(
                "closed",
                "completed",
                99L,
                "done",
                true,
                GitHubIssueUpdateConfidence.HIGH,
                false,
                GitHubIssueUpdateIgnoredReason.ALREADY_APPLIED);
    }

    static GitHubIssueLabel issueLabel() {
        return new GitHubIssueLabel(
                208_045_946L,
                "MDU6TGFiZWwyMDgwNDU5NDY=",
                "https://api.github.com/repos/octocat/Spoon-Knife/labels/bug",
                "bug",
                "Something isn't working",
                "d73a4a",
                true,
                UsersCatalogFixtures.octocat());
    }

    static GitHubIssuePullRequest issuePullRequest() {
        return new GitHubIssuePullRequest(
                "https://github.com/octocat/Spoon-Knife/pull/1309.diff",
                "https://github.com/octocat/Spoon-Knife/pull/1309",
                "https://github.com/octocat/Spoon-Knife/pull/1309.patch",
                "https://api.github.com/repos/octocat/Spoon-Knife/pulls/1309",
                PINNED);
    }

    static GitHubIssueSuggestion issueSuggestion() {
        return GitHubIssueSuggestionBuilder.builder()
                .id(12L)
                .issueId(4567L)
                .action(GitHubIssueSuggestionAction.ADD_LABEL)
                .state(GitHubIssueSuggestionState.APPROVED)
                .targetId(208_045_946L)
                .targetValue(GitHubIssueSuggestionTargetValue.text("bug"))
                .rationale("This looks like a bug report based on the stack trace in the description.")
                .confidence(GitHubIssueSuggestionConfidence.HIGH)
                .actorId(41_898_282L)
                .issueEventId(6_430_295_168L)
                .resolvedBy(583_231L)
                .createdAt(Instant.parse("2026-06-01T14:20:00Z"))
                .updatedAt(Instant.parse("2026-06-01T15:05:00Z"))
                .build();
    }

    static GitHubCreateIssueLabel.Details createIssueLabelDetails() {
        return new GitHubCreateIssueLabel.Details(1L, "bug", "Bug label", "d73a4a");
    }

    static GitHubCreateIssueLabel createIssueLabelName() {
        return GitHubCreateIssueLabel.ofName("bug");
    }

    static GitHubCreateIssueLabel createIssueLabelObject() {
        return GitHubCreateIssueLabel.ofDetails(createIssueLabelDetails());
    }

    static GitHubCreateIssueFieldValue createIssueFieldValue() {
        return new GitHubCreateIssueFieldValue(1, GitHubIssueFieldValueWire.text("value"));
    }

    static GitHubCreatePayload createPayload() {
        return new GitHubCreatePayload(
                "n",
                "Issue body",
                GitHubIssueMilestoneReference.ofNumber(1L),
                List.of(createIssueLabelName()),
                List.of("octocat"),
                List.of(createIssueFieldValue()),
                "Bug",
                1000L);
    }

    static GitHubUpdateIssueType.Details updateIssueTypeDetails() {
        return new GitHubUpdateIssueType.Details(
                OptionalNull.of("bug"), "because", true, GitHubIssueUpdateConfidence.HIGH);
    }

    static GitHubUpdateIssueLabel.Details updateIssueLabelDetails() {
        return new GitHubUpdateIssueLabel.Details(
                1L, "bug", "Bug label", "d73a4a", "because", true, GitHubIssueUpdateConfidence.HIGH);
    }

    static GitHubUpdateIssueAssignee.Details updateIssueAssigneeDetails() {
        return new GitHubUpdateIssueAssignee.Details("octocat", "because", true, GitHubIssueUpdateConfidence.HIGH);
    }

    static GitHubUpdateIssueFieldValue updateIssueFieldValue() {
        return new GitHubUpdateIssueFieldValue(
                1, GitHubIssueFieldValueWire.text("value"), "because", true, GitHubIssueUpdateConfidence.HIGH);
    }

    static GitHubUpdatePayload updatePayload() {
        return new GitHubUpdatePayload(
                OptionalNull.of("Updated title"),
                OptionalNull.of("Updated body"),
                GitHubIssueUpdateState.OPEN,
                OptionalNull.of(GitHubIssueStateReason.REOPENED),
                99L,
                OptionalNull.of(GitHubIssueMilestoneReference.ofNumber(1L)),
                List.of(GitHubUpdateIssueLabel.ofName("bug")),
                List.of(GitHubUpdateIssueAssignee.ofLogin("octocat")),
                List.of(updateIssueFieldValue()),
                OptionalNull.of(GitHubUpdateIssueType.ofName("bug")));
    }

    static GitHubLockPayload lockPayload() {
        return new GitHubLockPayload(GitHubIssueLockReason.RESOLVED);
    }

    static GitHubListFilter listFilter() {
        return GitHubListFilterBuilder.builder()
                .filter(GitHubListIssuesFilterKind.ASSIGNED)
                .state(GitHubListIssuesState.OPEN)
                .labels("bug,enhancement")
                .sort(GitHubListIssuesSort.CREATED)
                .direction(GitHubDirection.DESC)
                .since(CREATED)
                .collab(true)
                .orgs(false)
                .owned(true)
                .pulls(false)
                .build();
    }

    static GitHubListForRepoFilter listForRepoFilter() {
        return GitHubListForRepoFilterBuilder.builder()
                .milestone("1")
                .state(GitHubListIssuesState.OPEN)
                .assignee("octocat")
                .type("Bug")
                .creator("octocat")
                .mentioned("hubot")
                .issueFieldValues("priority:high")
                .labels("bug")
                .sort(GitHubListIssuesSort.UPDATED)
                .direction(GitHubDirection.ASC)
                .since(CREATED)
                .build();
    }

    static GitHubListForOrgFilter listForOrgFilter() {
        return GitHubListForOrgFilterBuilder.builder()
                .filter(GitHubListIssuesFilterKind.CREATED)
                .state(GitHubListIssuesState.CLOSED)
                .labels("bug")
                .type("Bug")
                .sort(GitHubListIssuesSort.COMMENTS)
                .direction(GitHubDirection.ASC)
                .since(CREATED)
                .build();
    }

    static GitHubListForAuthenticatedUserFilter listForAuthenticatedUserFilter() {
        return GitHubListForAuthenticatedUserFilterBuilder.builder()
                .filter(GitHubListIssuesFilterKind.MENTIONED)
                .state(GitHubListIssuesState.ALL)
                .labels("help wanted")
                .sort(GitHubListIssuesSort.UPDATED)
                .direction(GitHubDirection.DESC)
                .since(CREATED)
                .build();
    }

    static GitHubListSuggestionsFilter listSuggestionsFilter() {
        return GitHubListSuggestionsFilterBuilder.builder()
                .state(GitHubListIssueSuggestionsState.APPROVED)
                .action(GitHubIssueSuggestionAction.ADD_LABEL)
                .build();
    }

    static GitHubListCommentsFilter listCommentsFilter() {
        return new GitHubListCommentsFilter(CREATED);
    }

    static GitHubListCommentsForRepoFilter listCommentsForRepoFilter() {
        return new GitHubListCommentsForRepoFilter(GitHubListCommentsForRepoSort.UPDATED, GitHubDirection.ASC, CREATED);
    }

    static GitHubListMilestonesFilter listMilestonesFilter() {
        return new GitHubListMilestonesFilter(
                GitHubListIssuesState.OPEN, GitHubListMilestonesSort.DUE_ON, GitHubDirection.DESC);
    }

    static GitHubListEventsForTimelineFilter listEventsForTimelineFilter() {
        return new GitHubListEventsForTimelineFilter("committed");
    }

    static GitHubCreateCommentPayload createCommentPayload() {
        return new GitHubCreateCommentPayload("Me too");
    }

    static GitHubUpdateCommentPayload updateCommentPayload() {
        return new GitHubUpdateCommentPayload("Still happening");
    }

    static GitHubCreateLabelPayload createLabelPayload() {
        return new GitHubCreateLabelPayload("bug", "d73a4a", "Something isn't working");
    }

    static GitHubUpdateLabelPayload updateLabelPayload() {
        return new GitHubUpdateLabelPayload("bug", "cfd3d7", "updated", false);
    }

    public static GitHubLabel label() {
        return new GitHubLabel(
                208_045_946L,
                "MDU6TGFiZWwyMDgwNDU5NDY=",
                "https://api.github.com/repos/octocat/Spoon-Knife/labels/bug",
                "bug",
                "Something isn't working",
                "d73a4a",
                true,
                Instant.parse("2023-01-01T12:00:00Z"),
                UsersCatalogFixtures.octocat());
    }

    static GitHubCreateMilestonePayload createMilestonePayload() {
        return new GitHubCreateMilestonePayload(
                "v1.0", GitHubMilestoneState.OPEN, "Tracking milestone for version 1.0", PINNED);
    }

    static GitHubUpdateMilestonePayload updateMilestonePayload() {
        return new GitHubUpdateMilestonePayload(
                "v1.0", GitHubMilestoneState.CLOSED, "Tracking milestone for version 1.0", PINNED);
    }

    static GitHubAddAssigneesAssignee.Details addAssigneesAssigneeDetails() {
        return new GitHubAddAssigneesAssignee.Details("octocat", "because", true, GitHubIssueUpdateConfidence.HIGH);
    }

    static GitHubAddAssigneesPayload addAssigneesPayload() {
        return new GitHubAddAssigneesPayload(
                List.of(GitHubAddAssigneesAssignee.ofDetails(addAssigneesAssigneeDetails())));
    }

    static GitHubRemoveAssigneesPayload removeAssigneesPayload() {
        return new GitHubRemoveAssigneesPayload(List.of("hubot"));
    }

    static GitHubAddLabelsLabel.Details addLabelsLabelDetails() {
        return new GitHubAddLabelsLabel.Details("bug", "because", true, GitHubIssueUpdateConfidence.HIGH);
    }

    static GitHubAddLabelsPayload addLabelsPayload() {
        return new GitHubAddLabelsPayload(List.of(GitHubAddLabelsLabel.ofDetails(addLabelsLabelDetails())));
    }

    static GitHubSetLabelsLabel.Details setLabelsLabelDetails() {
        return new GitHubSetLabelsLabel.Details("bug");
    }

    static GitHubSetLabelsPayload setLabelsPayload() {
        return new GitHubSetLabelsPayload(List.of(GitHubSetLabelsLabel.ofDetails(setLabelsLabelDetails())));
    }

    static GitHubAddIssueFieldValuesPayload addIssueFieldValuesPayload() {
        return new GitHubAddIssueFieldValuesPayload(List.of(createIssueFieldValue()));
    }

    static GitHubSetIssueFieldValue setIssueFieldValue() {
        return new GitHubSetIssueFieldValue(1, "value");
    }

    static GitHubSetIssueFieldValuesPayload setIssueFieldValuesPayload() {
        return new GitHubSetIssueFieldValuesPayload(List.of(setIssueFieldValue()));
    }

    static GitHubAddSubIssuePayload addSubIssuePayload() {
        return new GitHubAddSubIssuePayload(1L, true);
    }

    static GitHubRemoveSubIssuePayload removeSubIssuePayload() {
        return new GitHubRemoveSubIssuePayload(1L);
    }

    static GitHubReprioritizeSubIssuePayload reprioritizeSubIssuePayload() {
        return new GitHubReprioritizeSubIssuePayload(1L, 2L, 3L);
    }

    static GitHubAddBlockedByDependencyPayload addBlockedByDependencyPayload() {
        return new GitHubAddBlockedByDependencyPayload(99L);
    }

    static GitHubAddRelatesToPayload addRelatesToPayload() {
        return new GitHubAddRelatesToPayload(99L);
    }

    static GitHubIssueEventLabel issueEventLabel() {
        return new GitHubIssueEventLabel("bug", "d73a4a");
    }

    static GitHubIssueEventMilestone issueEventMilestone() {
        return new GitHubIssueEventMilestone("v1.0");
    }

    static GitHubIssueEventRename issueEventRename() {
        return new GitHubIssueEventRename("old", "new");
    }

    static GitHubIssueEventDismissedReview issueEventDismissedReview() {
        return new GitHubIssueEventDismissedReview("dismissed", 12, "not relevant", "abc123");
    }

    static GitHubIssueEventProjectCard issueEventProjectCard() {
        return new GitHubIssueEventProjectCard(
                "https://api.github.com/projects/columns/cards/1",
                1,
                "https://api.github.com/projects/1",
                1,
                "Done",
                "In Progress");
    }

    static GitHubIssueEventIntent issueEventIntent() {
        return new GitHubIssueEventIntent("looks like a bug", GitHubIssueSuggestionConfidence.HIGH);
    }

    static GitHubIssueReference.IssueType issueReferenceIssueType() {
        return new GitHubIssueReference.IssueType(1, "IT_kwDOA", "Bug", "red");
    }

    static GitHubIssueReference issueReference() {
        return new GitHubIssueReference(
                1309, "Found a bug", "open", "completed", simpleRepository(), issueReferenceIssueType());
    }

    static GitHubIssueEvent issueEvent() {
        return GitHubIssueEventBuilder.builder()
                .id(1L)
                .nodeId("MDEwOklzc3VlRXZlbnQx")
                .url("https://api.github.com/repos/octocat/Spoon-Knife/issues/events/1")
                .actor(UsersCatalogFixtures.octocat())
                .event("labeled")
                .commitId("6dcb09b5b57875f334f61aebed695e2e4193db5e")
                .commitUrl(
                        "https://api.github.com/repos/octocat/Spoon-Knife/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e")
                .createdAt(CREATED)
                .issue(issue())
                .label(issueEventLabel())
                .assignee(UsersCatalogFixtures.octocat())
                .assigner(UsersCatalogFixtures.octocat())
                .reviewRequester(UsersCatalogFixtures.octocat())
                .requestedReviewer(UsersCatalogFixtures.octocat())
                .requestedTeam(team())
                .dismissedReview(issueEventDismissedReview())
                .milestone(issueEventMilestone())
                .projectCard(issueEventProjectCard())
                .rename(issueEventRename())
                .issueType(issueTypeWebhook())
                .prevIssueType(issueTypeWebhook())
                .subIssue(issueReference())
                .parentIssue(issueReference())
                .blockedBy(issueReference())
                .blocking(issueReference())
                .intent(issueEventIntent())
                .authorAssociation(GitHubAuthorAssociation.OWNER)
                .lockReason("resolved")
                .performedViaGithubApp(integration())
                .build();
    }

    static GitHubTimelineEventLinks timelineEventLinks() {
        return new GitHubTimelineEventLinks(
                new GitHubLink("https://github.com/octocat/Hello-World/pull/1#pullrequestreview-1"),
                new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/1"));
    }

    static GitHubTimelineGitUser timelineGitUser() {
        return new GitHubTimelineGitUser(PINNED, "octocat@github.com", "The Octocat");
    }

    static GitHubTimelineCommitTree timelineCommitTree() {
        return new GitHubTimelineCommitTree(
                "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "https://api.github.com/repos/octocat/Hello-World/git/trees/6dcb09b5b57875f334f61aebed695e2e4193db5e");
    }

    static GitHubTimelineCommitParent timelineCommitParent() {
        return new GitHubTimelineCommitParent(
                "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "https://api.github.com/repos/octocat/Hello-World/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "https://github.com/octocat/Hello-World/commit/6dcb09b5b57875f334f61aebed695e2e4193db5e");
    }

    static GitHubTimelineVerification timelineVerification() {
        return new GitHubTimelineVerification(true, "valid", "sig", "payload", "2014-02-12T23:18:55Z");
    }

    static GitHubTimelineEventSource timelineEventSource() {
        return new GitHubTimelineEventSource("issue", issue());
    }

    static GitHubTimelineEvent timelineEvent() {
        return GitHubTimelineEventBuilder.builder()
                .id(1L)
                .nodeId("MDEwOklzc3VlRXZlbnQx")
                .url("https://api.github.com/repos/octocat/Spoon-Knife/issues/events/1")
                .actor(UsersCatalogFixtures.octocat())
                .event("committed")
                .commitId("6dcb09b5b57875f334f61aebed695e2e4193db5e")
                .commitUrl(
                        "https://api.github.com/repos/octocat/Spoon-Knife/commits/6dcb09b5b57875f334f61aebed695e2e4193db5e")
                .createdAt(CREATED)
                .label(issueEventLabel())
                .assignee(UsersCatalogFixtures.octocat())
                .reviewRequester(UsersCatalogFixtures.octocat())
                .requestedReviewer(UsersCatalogFixtures.octocat())
                .requestedTeam(team())
                .dismissedReview(issueEventDismissedReview())
                .milestone(issueEventMilestone())
                .projectCard(issueEventProjectCard())
                .rename(issueEventRename())
                .issueType(issueTypeWebhook())
                .prevIssueType(issueTypeWebhook())
                .subIssue(issueReference())
                .parentIssue(issueReference())
                .blockedBy(issueReference())
                .blocking(issueReference())
                .intent(issueEventIntent())
                .authorAssociation(GitHubAuthorAssociation.OWNER)
                .lockReason("resolved")
                .performedViaGithubApp(integration())
                .links(timelineEventLinks())
                .author(timelineGitUser())
                .body("commit message body")
                .bodyHtml("<p>commit message body</p>")
                .bodyText("commit message body")
                .comments(List.of(pullRequestReviewComment()))
                .committer(timelineGitUser())
                .htmlUrl("https://github.com/octocat/Hello-World/commit/6dcb09b5")
                .issueUrl(ISSUE_API)
                .message("Fix all the bugs")
                .minimized(new GitHubIssueComment.Minimized("low-quality"))
                .parents(List.of(timelineCommitParent()))
                .pin(new GitHubIssueComment.PinnedComment(PINNED, UsersCatalogFixtures.octocat()))
                .pullRequestUrl("https://api.github.com/repos/octocat/Hello-World/pulls/1")
                .reactions(issueReactions())
                .sha("6dcb09b5b57875f334f61aebed695e2e4193db5e")
                .source(timelineEventSource())
                .state("APPROVED")
                .stateReason("completed")
                .submittedAt(PINNED)
                .tree(timelineCommitTree())
                .updatedAt(CREATED)
                .user(UsersCatalogFixtures.octocat())
                .verification(timelineVerification())
                .build();
    }

    static GitHubMilestone milestone() {
        return new GitHubMilestone(
                "https://api.github.com/repos/octocat/Spoon-Knife/milestones/1",
                "https://github.com/octocat/Spoon-Knife/milestone/1",
                "https://api.github.com/repos/octocat/Spoon-Knife/milestones/1/labels",
                1_002_604L,
                "MDk6TWlsZXN0b25lMTAwMjYwNA==",
                1,
                GitHubMilestoneState.OPEN,
                "v1.0",
                "Tracking milestone for version 1.0",
                UsersCatalogFixtures.octocat(),
                4,
                8,
                Instant.parse("2011-04-10T20:09:31Z"),
                Instant.parse("2014-03-03T18:58:10Z"),
                Instant.parse("2011-04-14T16:00:49Z"),
                Instant.parse("2012-10-09T23:39:01Z"));
    }

    static GitHubIssueFieldValue issueFieldValue() {
        return new GitHubIssueFieldValue(
                1L,
                "Priority",
                "IFT_GDKND",
                GitHubIssueFieldDataType.SINGLE_SELECT,
                "High",
                new GitHubIssueFieldValue.SelectOption(1L, "High", "red"),
                List.of(new GitHubIssueFieldValue.SelectOption(3L, "Urgent", "orange")));
    }

    private static GitHubIssueType issueType() {
        return new GitHubIssueType(
                1,
                "IT_kwDOA",
                "Bug",
                "An unexpected problem",
                GitHubIssueTypeColor.RED,
                Instant.parse("2024-01-01T00:00:00Z"),
                Instant.parse("2024-01-02T00:00:00Z"),
                true);
    }

    private static GitHubIssueTypeWebhook issueTypeWebhook() {
        return new GitHubIssueTypeWebhook(1, "Bug", GitHubIssueTypeColor.RED);
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

    public static GitHubIssueComment issueComment() {
        return new GitHubIssueComment(
                42L,
                "IC_kwDOA",
                "https://api.github.com/repos/octocat/Spoon-Knife/issues/comments/42",
                "Pinned comment body",
                "Pinned comment body",
                "<p>Pinned comment body</p>",
                "https://github.com/octocat/Spoon-Knife/issues/1309#issuecomment-42",
                UsersCatalogFixtures.octocat(),
                PINNED,
                PINNED,
                ISSUE_API,
                GitHubAuthorAssociation.OWNER,
                integration(),
                commentReactions(),
                new GitHubIssueComment.PinnedComment(PINNED, UsersCatalogFixtures.octocat()),
                new GitHubIssueComment.Minimized("low-quality"));
    }

    public static GitHubReactionRollup issueReactions() {
        return new GitHubReactionRollup(
                "https://api.github.com/repos/octocat/Spoon-Knife/issues/1309/reactions", 3, 2, 0, 0, 0, 1, 0, 0, 0);
    }

    private static GitHubReactionRollup commentReactions() {
        return new GitHubReactionRollup(
                "https://api.github.com/repos/octocat/Spoon-Knife/issues/comments/42/reactions",
                2,
                1,
                0,
                0,
                0,
                1,
                0,
                0,
                0);
    }

    private static GitHubSimpleRepository simpleRepository() {
        final String api = "https://api.github.com/repos/octocat/Hello-World";
        final String http = "http://api.github.com/repos/octocat/Hello-World";
        return new GitHubSimpleRepository(
                1_296_269L,
                "MDEwOlJlcG9zaXRvcnkxMjk2MjY5",
                "Hello-World",
                "octocat/Hello-World",
                UsersCatalogFixtures.octocat(),
                false,
                "https://github.com/octocat/Hello-World",
                "This your first repo!",
                false,
                api,
                http + "/{archive_format}{/ref}",
                http + "/assignees{/user}",
                http + "/git/blobs{/sha}",
                http + "/branches{/branch}",
                http + "/collaborators{/collaborator}",
                http + "/comments{/number}",
                http + "/commits{/sha}",
                http + "/compare/{base}...{head}",
                http + "/contents/{+path}",
                http + "/contributors",
                http + "/deployments",
                http + "/downloads",
                http + "/events",
                http + "/forks",
                http + "/git/commits{/sha}",
                http + "/git/refs{/sha}",
                http + "/git/tags{/sha}",
                http + "/issues/comments{/number}",
                http + "/issues/events{/number}",
                http + "/issues{/number}",
                http + "/keys{/key_id}",
                http + "/labels{/name}",
                http + "/languages",
                http + "/merges",
                http + "/milestones{/number}",
                http + "/notifications{?since,all,participating}",
                http + "/pulls{/number}",
                http + "/releases{/id}",
                http + "/stargazers",
                http + "/statuses/{sha}",
                http + "/subscribers",
                http + "/subscription",
                http + "/tags",
                http + "/teams",
                http + "/git/trees{/sha}",
                http + "/hooks");
    }

    private static GitHubTeam team() {
        return new GitHubTeam(
                1L,
                "MDQ6VGVhbTE=",
                "Justice League",
                "justice-league",
                "A great team.",
                "closed",
                "notifications_enabled",
                "admin",
                new GitHubRepositoryPermissions(true, false, true, false, true),
                "https://api.github.com/organizations/1/team/1",
                "https://github.com/orgs/github/teams/justice-league",
                "https://api.github.com/organizations/1/team/1/members{/member}",
                "https://api.github.com/organizations/1/team/1/repos",
                GitHubTeamOwnershipType.ORGANIZATION,
                GitHubTeamAccessSource.ORGANIZATION,
                1L,
                2L,
                new GitHubTeamSimple(
                        2L,
                        "MDQ6VGVhbTI=",
                        "https://api.github.com/organizations/1/team/2",
                        "https://api.github.com/organizations/1/team/2/members{/member}",
                        "Parent Team",
                        "Parent team.",
                        "admin",
                        "closed",
                        "notifications_enabled",
                        "https://github.com/orgs/rails/teams/parent",
                        "https://api.github.com/organizations/1/team/2/repos",
                        "parent-team",
                        "cn=parent-team,ou=groups,dc=github,dc=com",
                        GitHubTeamOwnershipType.ORGANIZATION,
                        1L,
                        2L));
    }

    private static GitHubPullRequestReviewComment pullRequestReviewComment() {
        return new GitHubPullRequestReviewComment(
                "https://api.github.com/repos/octocat/Hello-World/pulls/comments/1",
                42L,
                1,
                "MDI0OlB1bGxSZXF1ZXN0UmV2aWV3Q29tbWVudDEw",
                "@@ -16,33 +16,40 @@ public class Connection : IConnection...",
                "config/database.yaml",
                1,
                4,
                "6dcb09b5b57875f334f61aebed695e2e4193db5e",
                "9c48853fa3dc5c1c3d6f1f1cd1f2743e72652840",
                2L,
                OptionalNull.of(UsersCatalogFixtures.octocat()),
                "We should probably include a check for null values here.",
                PINNED,
                PINNED,
                "https://github.com/octocat/Hello-World/pull/1#discussion-diff-1",
                "https://api.github.com/repos/octocat/Hello-World/pulls/1",
                GitHubAuthorAssociation.NONE,
                new GitHubPullRequestReviewCommentLinks(
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/comments/1"),
                        new GitHubLink("https://github.com/octocat/Hello-World/pull/1#discussion-diff-1"),
                        new GitHubLink("https://api.github.com/repos/octocat/Hello-World/pulls/1")),
                1,
                1,
                GitHubPullRequestReviewCommentSide.RIGHT,
                2,
                2,
                GitHubPullRequestReviewCommentSide.RIGHT,
                GitHubPullRequestReviewCommentSubjectType.LINE,
                commentReactions(),
                "<p>We should probably include a check for null values here.</p>",
                "We should probably include a check for null values here.");
    }
}
