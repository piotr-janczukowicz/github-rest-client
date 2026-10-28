package io.github.piotr_janczukowicz.github.api.issues;

import static io.github.piotr_janczukowicz.util.ValidationUtils.requireNonNull;

import io.github.piotr_janczukowicz.github.client.GitHubCaller;
import io.github.piotr_janczukowicz.util.LazyValue;

/** GitHub Issues REST resource aggregator for API version {@code 2026-03-10}. */
public final class GitHubIssuesApi {

    private final LazyValue<GitHubIssuesAssigneesApi> assignees;
    private final LazyValue<GitHubIssuesCommentsApi> comments;
    private final LazyValue<GitHubIssuesEventsApi> events;
    private final LazyValue<GitHubIssueApi> issue;
    private final LazyValue<GitHubIssuesIssueDependenciesApi> issueDependencies;
    private final LazyValue<GitHubIssuesIssueFieldValuesApi> issueFieldValues;
    private final LazyValue<GitHubIssuesLabelsApi> labels;
    private final LazyValue<GitHubIssuesMilestonesApi> milestones;
    private final LazyValue<GitHubIssuesSubIssuesApi> subIssues;
    private final LazyValue<GitHubIssuesTimelineApi> timeline;

    /**
     * Issues resource aggregator over {@code caller}.
     *
     * @param caller executes and decodes the calls of this resource
     * @throws NullPointerException if {@code caller} is {@code null}
     */
    public GitHubIssuesApi(final GitHubCaller caller) {
        requireNonNull(caller, "caller");
        this.assignees = new LazyValue<>(() -> new GitHubIssuesAssigneesApi(caller));
        this.comments = new LazyValue<>(() -> new GitHubIssuesCommentsApi(caller));
        this.events = new LazyValue<>(() -> new GitHubIssuesEventsApi(caller));
        this.issue = new LazyValue<>(() -> new GitHubIssueApi(caller));
        this.issueDependencies = new LazyValue<>(() -> new GitHubIssuesIssueDependenciesApi(caller));
        this.issueFieldValues = new LazyValue<>(() -> new GitHubIssuesIssueFieldValuesApi(caller));
        this.labels = new LazyValue<>(() -> new GitHubIssuesLabelsApi(caller));
        this.milestones = new LazyValue<>(() -> new GitHubIssuesMilestonesApi(caller));
        this.subIssues = new LazyValue<>(() -> new GitHubIssuesSubIssuesApi(caller));
        this.timeline = new LazyValue<>(() -> new GitHubIssuesTimelineApi(caller));
    }

    /**
     * Assignee operations ({@code subcategory=assignees}).
     *
     * @return assignees API
     */
    public GitHubIssuesAssigneesApi assignees() {
        return assignees.get();
    }

    /**
     * Issue comment operations ({@code subcategory=comments}).
     *
     * @return comments API
     */
    public GitHubIssuesCommentsApi comments() {
        return comments.get();
    }

    /**
     * Issue event operations ({@code subcategory=events}).
     *
     * @return events API
     */
    public GitHubIssuesEventsApi events() {
        return events.get();
    }

    /**
     * Issue operations ({@code subcategory=issues}).
     *
     * @return issue API
     */
    public GitHubIssueApi issue() {
        return issue.get();
    }

    /**
     * Issue dependency operations ({@code subcategory=issue-dependencies}).
     *
     * @return issue dependencies API
     */
    public GitHubIssuesIssueDependenciesApi issueDependencies() {
        return issueDependencies.get();
    }

    /**
     * Issue field value operations ({@code subcategory=issue-field-values}).
     *
     * <p>The {@code CLOUD} and {@code GHEC} flavors carry this resource.
     *
     * @return issue field values API
     */
    public GitHubIssuesIssueFieldValuesApi issueFieldValues() {
        return issueFieldValues.get();
    }

    /**
     * Label operations ({@code subcategory=labels}).
     *
     * @return labels API
     */
    public GitHubIssuesLabelsApi labels() {
        return labels.get();
    }

    /**
     * Milestone operations ({@code subcategory=milestones}).
     *
     * @return milestones API
     */
    public GitHubIssuesMilestonesApi milestones() {
        return milestones.get();
    }

    /**
     * Sub-issue operations ({@code subcategory=sub-issues}).
     *
     * <p>The {@code CLOUD} and {@code GHEC} flavors carry this resource.
     *
     * @return sub-issues API
     */
    public GitHubIssuesSubIssuesApi subIssues() {
        return subIssues.get();
    }

    /**
     * Timeline operations ({@code subcategory=timeline}).
     *
     * @return timeline API
     */
    public GitHubIssuesTimelineApi timeline() {
        return timeline.get();
    }
}
