package io.github.piotr_janczukowicz.github.api.apps;

import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubWebhookConfigInsecureSsl;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubWebhookDeliveryStatus;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class AppsCatalogFixtures {

    private AppsCatalogFixtures() {}

    static GitHubAuthorization authorization() {
        return GitHubAuthorizationBuilder.builder()
                .id(1L)
                .url("https://api.github.com/authorizations/1")
                .scopes(List.of("public_repo", "repo"))
                .token("test-token-value")
                .tokenLastEight("ab12cd34")
                .hashedToken("a4b1c2d3e4f5")
                .app(authorizationApp())
                .note("Test note")
                .noteUrl("https://example.com/note")
                .updatedAt(Instant.parse("2024-01-01T12:00:00Z"))
                .createdAt(Instant.parse("2023-01-01T12:00:00Z"))
                .fingerprint("fingerprint-1")
                .user(UsersCatalogFixtures.octocat())
                .installation(scopedInstallation())
                .expiresAt(Instant.parse("2030-01-01T00:00:00Z"))
                .build();
    }

    static GitHubAuthorizationApp authorizationApp() {
        return new GitHubAuthorizationApp("Iv1.25b5d1e65ffc4022", "GitHub CLI", "https://cli.github.com");
    }

    static GitHubScopedInstallation scopedInstallation() {
        return GitHubScopedInstallationBuilder.builder()
                .permissions(OrganizationsCatalogFixtures.appPermissions())
                .repositorySelection(scopedInstallationRepositorySelection())
                .singleFileName("config.yaml")
                .hasMultipleSingleFiles(true)
                .singleFilePaths(List.of("config.yml", ".github/issue_TEMPLATE.md"))
                .repositoriesUrl("https://api.github.com/users/octocat/repos")
                .account(UsersCatalogFixtures.octocat())
                .build();
    }

    static GitHubInstallationToken installationToken() {
        return GitHubInstallationTokenBuilder.builder()
                .token("test-installation-token")
                .expiresAt(Instant.parse("2026-10-12T20:00:00Z"))
                .permissions(OrganizationsCatalogFixtures.appPermissions())
                .repositorySelection(installationTokenRepositorySelection())
                .repositories(List.of(RepositoriesCatalogFixtures.teamEnvironmentRepository()))
                .singleFile("config.yml")
                .hasMultipleSingleFiles(true)
                .singleFilePaths(List.of("config.yml"))
                .build();
    }

    static GitHubIntegrationInstallationRequest integrationInstallationRequest() {
        return GitHubIntegrationInstallationRequestBuilder.builder()
                .id(1L)
                .nodeId("MDIzOkludGVncmF0aW9uSW5zdGFsbGF0aW9uUmVxdWVzdDE=")
                .account(new GitHubIntegrationInstallationRequestAccount.SimpleUser(UsersCatalogFixtures.octocat()))
                .requester(UsersCatalogFixtures.octocat())
                .createdAt(Instant.parse("2024-02-01T10:00:00Z"))
                .build();
    }

    static GitHubMarketplaceAccount marketplaceAccount() {
        return GitHubMarketplaceAccountBuilder.builder()
                .url("https://api.github.com/orgs/github")
                .id(1L)
                .type("Organization")
                .nodeId("MDEyOk9yZ2FuaXphdGlvbjE=")
                .login("github")
                .email("billing@example.com")
                .organizationBillingEmail("billing@example.com")
                .build();
    }

    static GitHubMarketplaceListingPlan marketplaceListingPlan() {
        return GitHubMarketplaceListingPlanBuilder.builder()
                .url("https://api.github.com/marketplace_listing/plans/1313")
                .accountsUrl("https://api.github.com/marketplace_listing/plans/1313/accounts")
                .id(1313L)
                .number(3)
                .name("Pro")
                .description("A professional-grade CI solution")
                .monthlyPriceInCents(1099)
                .yearlyPriceInCents(11_870)
                .priceModel(marketplaceListingPlanPriceModel())
                .hasFreeTrial(true)
                .unitName("seat")
                .state("published")
                .bullets(List.of("Up to 25 private repositories", "11 concurrent builds"))
                .build();
    }

    static GitHubMarketplacePurchase marketplacePurchase() {
        return GitHubMarketplacePurchaseBuilder.builder()
                .url("https://api.github.com/orgs/github")
                .type("Organization")
                .id(1L)
                .login("github")
                .organizationBillingEmail("billing@example.com")
                .email("billing@example.com")
                .marketplacePendingChange(marketplacePurchasePendingChange())
                .marketplacePurchase(marketplacePurchaseMarketplacePurchase())
                .build();
    }

    static GitHubMarketplacePurchaseMarketplacePendingChange marketplacePurchasePendingChange() {
        return GitHubMarketplacePurchaseMarketplacePendingChangeBuilder.builder()
                .isInstalled(true)
                .effectiveDate("2024-03-01T00:00:00Z")
                .unitCount(5)
                .id(2L)
                .plan(marketplaceListingPlan())
                .build();
    }

    static GitHubMarketplacePurchaseMarketplacePurchase marketplacePurchaseMarketplacePurchase() {
        return GitHubMarketplacePurchaseMarketplacePurchaseBuilder.builder()
                .billingCycle("monthly")
                .nextBillingDate("2024-04-01T00:00:00Z")
                .isInstalled(true)
                .unitCount(5)
                .onFreeTrial(false)
                .freeTrialEndsOn("2024-03-15T00:00:00Z")
                .updatedAt("2024-03-01T00:00:00Z")
                .plan(marketplaceListingPlan())
                .build();
    }

    static GitHubUserMarketplacePurchase userMarketplacePurchase() {
        return GitHubUserMarketplacePurchaseBuilder.builder()
                .billingCycle("monthly")
                .nextBillingDate(Instant.parse("2024-04-01T00:00:00Z"))
                .unitCount(1)
                .onFreeTrial(true)
                .freeTrialEndsOn(Instant.parse("2024-03-15T00:00:00Z"))
                .updatedAt(Instant.parse("2024-03-01T00:00:00Z"))
                .account(marketplaceAccount())
                .plan(marketplaceListingPlan())
                .build();
    }

    static GitHubCreateFromManifestResponse createFromManifestResponse() {
        return GitHubCreateFromManifestResponseBuilder.builder()
                .id(1L)
                .slug("probot-owners")
                .nodeId("MDExOkludGVncmF0aW9uMQ==")
                .clientId("Iv1.25b5d1e65ffc4022")
                .clientSecret("test-client-secret")
                .webhookSecret("test-webhook-secret")
                .pem("test-pem")
                .owner(octocatIntegrationOwner())
                .name("probot-owners")
                .description("The description of the app.")
                .externalUrl("https://example.com")
                .htmlUrl("https://github.com/apps/probot-owners")
                .createdAt(Instant.parse("2017-07-08T20:18:44Z"))
                .updatedAt(Instant.parse("2017-07-08T20:18:44Z"))
                .permissions(integrationPermissions())
                .events(List.of("issues"))
                .installationsCount(3)
                .build();
    }

    static GitHubListInstallationsForAuthenticatedUserPage installationsForAuthenticatedUserPage() {
        return new GitHubListInstallationsForAuthenticatedUserPage(
                1, List.of(OrganizationsCatalogFixtures.installation()));
    }

    static GitHubListReposAccessibleToInstallationPage reposAccessibleToInstallationPage() {
        return new GitHubListReposAccessibleToInstallationPage(
                1, List.of(reposAccessibleToInstallationRepository()), "selected");
    }

    static GitHubListInstallationReposForAuthenticatedUserPage installationReposForAuthenticatedUserPage() {
        return new GitHubListInstallationReposForAuthenticatedUserPage(
                1, "all", List.of(installationReposForAuthenticatedUserRepository()));
    }

    static GitHubListReposAccessibleToInstallationRepository reposAccessibleToInstallationRepository() {
        final GitHubRepository repository = RepositoriesCatalogFixtures.teamEnvironmentRepository();
        return GitHubListReposAccessibleToInstallationRepositoryBuilder.builder()
                .id(repository.id())
                .nodeId(repository.nodeId())
                .name(repository.name())
                .fullName(repository.fullName())
                .license(repository.license())
                .forks(repository.forks())
                .permissions(repository.permissions())
                .owner(repository.owner())
                .isPrivate(repository.isPrivate())
                .htmlUrl(repository.htmlUrl())
                .description(repository.description())
                .fork(repository.fork())
                .url(repository.url())
                .archiveUrl(repository.archiveUrl())
                .assigneesUrl(repository.assigneesUrl())
                .blobsUrl(repository.blobsUrl())
                .branchesUrl(repository.branchesUrl())
                .collaboratorsUrl(repository.collaboratorsUrl())
                .commentsUrl(repository.commentsUrl())
                .commitsUrl(repository.commitsUrl())
                .compareUrl(repository.compareUrl())
                .contentsUrl(repository.contentsUrl())
                .contributorsUrl(repository.contributorsUrl())
                .deploymentsUrl(repository.deploymentsUrl())
                .downloadsUrl(repository.downloadsUrl())
                .eventsUrl(repository.eventsUrl())
                .forksUrl(repository.forksUrl())
                .gitCommitsUrl(repository.gitCommitsUrl())
                .gitRefsUrl(repository.gitRefsUrl())
                .gitTagsUrl(repository.gitTagsUrl())
                .gitUrl(repository.gitUrl())
                .issueCommentUrl(repository.issueCommentUrl())
                .issueEventsUrl(repository.issueEventsUrl())
                .issuesUrl(repository.issuesUrl())
                .keysUrl(repository.keysUrl())
                .labelsUrl(repository.labelsUrl())
                .languagesUrl(repository.languagesUrl())
                .mergesUrl(repository.mergesUrl())
                .milestonesUrl(repository.milestonesUrl())
                .notificationsUrl(repository.notificationsUrl())
                .pullsUrl(repository.pullsUrl())
                .releasesUrl(repository.releasesUrl())
                .sshUrl(repository.sshUrl())
                .stargazersUrl(repository.stargazersUrl())
                .statusesUrl(repository.statusesUrl())
                .subscribersUrl(repository.subscribersUrl())
                .subscriptionUrl(repository.subscriptionUrl())
                .tagsUrl(repository.tagsUrl())
                .teamsUrl(repository.teamsUrl())
                .treesUrl(repository.treesUrl())
                .cloneUrl(repository.cloneUrl())
                .mirrorUrl(repository.mirrorUrl())
                .hooksUrl(repository.hooksUrl())
                .svnUrl(repository.svnUrl())
                .homepage(repository.homepage())
                .language(repository.language())
                .forksCount(repository.forksCount())
                .stargazersCount(repository.stargazersCount())
                .watchersCount(repository.watchersCount())
                .size(repository.size())
                .defaultBranch(repository.defaultBranch())
                .openIssuesCount(repository.openIssuesCount())
                .isTemplate(repository.isTemplate())
                .topics(repository.topics())
                .hasIssues(repository.hasIssues())
                .hasProjects(repository.hasProjects())
                .hasWiki(repository.hasWiki())
                .hasPages(repository.hasPages())
                .hasDiscussions(repository.hasDiscussions())
                .hasPullRequests(repository.hasPullRequests())
                .pullRequestCreationPolicy(repository.pullRequestCreationPolicy())
                .archived(repository.archived())
                .disabled(repository.disabled())
                .visibility(repository.visibility())
                .pushedAt(repository.pushedAt())
                .createdAt(repository.createdAt())
                .updatedAt(repository.updatedAt())
                .allowRebaseMerge(repository.allowRebaseMerge())
                .tempCloneToken(repository.tempCloneToken())
                .allowSquashMerge(repository.allowSquashMerge())
                .allowAutoMerge(repository.allowAutoMerge())
                .deleteBranchOnMerge(repository.deleteBranchOnMerge())
                .allowUpdateBranch(repository.allowUpdateBranch())
                .squashMergeCommitTitle(repository.squashMergeCommitTitle())
                .squashMergeCommitMessage(repository.squashMergeCommitMessage())
                .mergeCommitTitle(repository.mergeCommitTitle())
                .mergeCommitMessage(repository.mergeCommitMessage())
                .allowMergeCommit(repository.allowMergeCommit())
                .allowForking(repository.allowForking())
                .webCommitSignoffRequired(repository.webCommitSignoffRequired())
                .openIssues(repository.openIssues())
                .watchers(repository.watchers())
                .starredAt(repository.starredAt())
                .anonymousAccessEnabled(repository.anonymousAccessEnabled())
                .codeSearchIndexStatus(repository.codeSearchIndexStatus())
                .customProperties(Map.of("team", "platform"))
                .build();
    }

    static GitHubListInstallationReposForAuthenticatedUserRepository installationReposForAuthenticatedUserRepository() {
        final GitHubRepository repository = RepositoriesCatalogFixtures.teamEnvironmentRepository();
        return GitHubListInstallationReposForAuthenticatedUserRepositoryBuilder.builder()
                .id(repository.id())
                .nodeId(repository.nodeId())
                .name(repository.name())
                .fullName(repository.fullName())
                .license(repository.license())
                .forks(repository.forks())
                .permissions(repository.permissions())
                .owner(repository.owner())
                .isPrivate(repository.isPrivate())
                .htmlUrl(repository.htmlUrl())
                .description(repository.description())
                .fork(repository.fork())
                .url(repository.url())
                .archiveUrl(repository.archiveUrl())
                .assigneesUrl(repository.assigneesUrl())
                .blobsUrl(repository.blobsUrl())
                .branchesUrl(repository.branchesUrl())
                .collaboratorsUrl(repository.collaboratorsUrl())
                .commentsUrl(repository.commentsUrl())
                .commitsUrl(repository.commitsUrl())
                .compareUrl(repository.compareUrl())
                .contentsUrl(repository.contentsUrl())
                .contributorsUrl(repository.contributorsUrl())
                .deploymentsUrl(repository.deploymentsUrl())
                .downloadsUrl(repository.downloadsUrl())
                .eventsUrl(repository.eventsUrl())
                .forksUrl(repository.forksUrl())
                .gitCommitsUrl(repository.gitCommitsUrl())
                .gitRefsUrl(repository.gitRefsUrl())
                .gitTagsUrl(repository.gitTagsUrl())
                .gitUrl(repository.gitUrl())
                .issueCommentUrl(repository.issueCommentUrl())
                .issueEventsUrl(repository.issueEventsUrl())
                .issuesUrl(repository.issuesUrl())
                .keysUrl(repository.keysUrl())
                .labelsUrl(repository.labelsUrl())
                .languagesUrl(repository.languagesUrl())
                .mergesUrl(repository.mergesUrl())
                .milestonesUrl(repository.milestonesUrl())
                .notificationsUrl(repository.notificationsUrl())
                .pullsUrl(repository.pullsUrl())
                .releasesUrl(repository.releasesUrl())
                .sshUrl(repository.sshUrl())
                .stargazersUrl(repository.stargazersUrl())
                .statusesUrl(repository.statusesUrl())
                .subscribersUrl(repository.subscribersUrl())
                .subscriptionUrl(repository.subscriptionUrl())
                .tagsUrl(repository.tagsUrl())
                .teamsUrl(repository.teamsUrl())
                .treesUrl(repository.treesUrl())
                .cloneUrl(repository.cloneUrl())
                .mirrorUrl(repository.mirrorUrl())
                .hooksUrl(repository.hooksUrl())
                .svnUrl(repository.svnUrl())
                .homepage(repository.homepage())
                .language(repository.language())
                .forksCount(repository.forksCount())
                .stargazersCount(repository.stargazersCount())
                .watchersCount(repository.watchersCount())
                .size(repository.size())
                .defaultBranch(repository.defaultBranch())
                .openIssuesCount(repository.openIssuesCount())
                .isTemplate(repository.isTemplate())
                .topics(repository.topics())
                .hasIssues(repository.hasIssues())
                .hasProjects(repository.hasProjects())
                .hasWiki(repository.hasWiki())
                .hasPages(repository.hasPages())
                .hasDiscussions(repository.hasDiscussions())
                .hasPullRequests(repository.hasPullRequests())
                .pullRequestCreationPolicy(repository.pullRequestCreationPolicy())
                .archived(repository.archived())
                .disabled(repository.disabled())
                .visibility(repository.visibility())
                .pushedAt(repository.pushedAt())
                .createdAt(repository.createdAt())
                .updatedAt(repository.updatedAt())
                .allowRebaseMerge(repository.allowRebaseMerge())
                .tempCloneToken(repository.tempCloneToken())
                .allowSquashMerge(repository.allowSquashMerge())
                .allowAutoMerge(repository.allowAutoMerge())
                .deleteBranchOnMerge(repository.deleteBranchOnMerge())
                .allowUpdateBranch(repository.allowUpdateBranch())
                .squashMergeCommitTitle(repository.squashMergeCommitTitle())
                .squashMergeCommitMessage(repository.squashMergeCommitMessage())
                .mergeCommitTitle(repository.mergeCommitTitle())
                .mergeCommitMessage(repository.mergeCommitMessage())
                .allowMergeCommit(repository.allowMergeCommit())
                .allowForking(repository.allowForking())
                .webCommitSignoffRequired(repository.webCommitSignoffRequired())
                .openIssues(repository.openIssues())
                .watchers(repository.watchers())
                .starredAt(repository.starredAt())
                .anonymousAccessEnabled(repository.anonymousAccessEnabled())
                .codeSearchIndexStatus(repository.codeSearchIndexStatus())
                .customProperties(Map.of("team", "platform"))
                .build();
    }

    static GitHubUpdateWebhookConfigForAppPayload updateWebhookConfigForAppPayload() {
        return GitHubUpdateWebhookConfigForAppPayloadBuilder.builder()
                .url(URI.create("https://example.com/webhook"))
                .contentType("json")
                .secret("test-secret")
                .insecureSsl(GitHubWebhookConfigInsecureSsl.text("0"))
                .build();
    }

    static GitHubCreateInstallationAccessTokenPayload createInstallationAccessTokenPayload() {
        return GitHubCreateInstallationAccessTokenPayloadBuilder.builder()
                .repositories(List.of("Hello-World"))
                .repositoryIds(List.of(1_296_269L, 1_296_270L))
                .permissions(OrganizationsCatalogFixtures.appPermissions())
                .build();
    }

    static GitHubDeleteAuthorizationPayload deleteAuthorizationPayload() {
        return new GitHubDeleteAuthorizationPayload("test-access-token");
    }

    static GitHubCheckTokenPayload checkTokenPayload() {
        return new GitHubCheckTokenPayload("test-access-token");
    }

    static GitHubResetTokenPayload resetTokenPayload() {
        return new GitHubResetTokenPayload("test-access-token");
    }

    static GitHubDeleteTokenPayload deleteTokenPayload() {
        return new GitHubDeleteTokenPayload("test-access-token");
    }

    static GitHubScopeTokenPayload scopeTokenPayload() {
        return GitHubScopeTokenPayloadBuilder.builder()
                .accessToken("test-access-token")
                .target("octocat")
                .targetId(1L)
                .repositories(List.of("Hello-World"))
                .repositoryIds(List.of(1_296_269L))
                .permissions(OrganizationsCatalogFixtures.appPermissions())
                .build();
    }

    static GitHubListWebhookDeliveriesFilter listWebhookDeliveriesFilter() {
        return GitHubListWebhookDeliveriesFilterBuilder.builder()
                .status(GitHubWebhookDeliveryStatus.SUCCESS)
                .build();
    }

    static GitHubListInstallationsFilter listInstallationsFilter() {
        return GitHubListInstallationsFilterBuilder.builder()
                .since(Instant.parse("2024-01-01T00:00:00Z"))
                .outdated("true")
                .build();
    }

    static GitHubInstallationTokenRepositorySelection installationTokenRepositorySelection() {
        return GitHubInstallationTokenRepositorySelection.SELECTED;
    }

    static GitHubScopedInstallationRepositorySelection scopedInstallationRepositorySelection() {
        return GitHubScopedInstallationRepositorySelection.ALL;
    }

    static GitHubMarketplaceListingPlanPriceModel marketplaceListingPlanPriceModel() {
        return GitHubMarketplaceListingPlanPriceModel.FLAT_RATE;
    }

    static GitHubSort sort() {
        return GitHubSort.UPDATED;
    }

    static GitHubListAccountsForPlanDirection listAccountsForPlanDirection() {
        return GitHubListAccountsForPlanDirection.ASC;
    }

    static GitHubListAccountsForPlanFilter listAccountsForPlanFilter() {
        return GitHubListAccountsForPlanFilterBuilder.builder()
                .sort(sort())
                .direction(listAccountsForPlanDirection())
                .build();
    }

    static GitHubListAccountsForPlanStubbedFilter listAccountsForPlanStubbedFilter() {
        return GitHubListAccountsForPlanStubbedFilterBuilder.builder()
                .sort(sort())
                .direction(listAccountsForPlanDirection())
                .build();
    }

    public static GitHubIntegrationPermissions integrationPermissions() {
        return new GitHubIntegrationPermissions("read", "read", "read", "read", "write", Map.of());
    }

    public static GitHubIntegrationOwner octocatIntegrationOwner() {
        return GitHubIntegrationOwner.ofUser(UsersCatalogFixtures.octocat());
    }

    public static GitHubIntegration probotIntegration() {
        return new GitHubIntegration(
                37L,
                "probot-owners",
                "MDExOkludGVncmF0aW9uMQ==",
                "Iv1.25b5d1e65ffc4022",
                octocatIntegrationOwner(),
                "Probot Owners",
                "The description of the app.",
                "https://example.com",
                "https://github.com/apps/super-ci",
                Instant.parse("2017-07-08T20:18:44Z"),
                Instant.parse("2017-07-08T20:18:44Z"),
                integrationPermissions(),
                List.of("label", "deployment"),
                5);
    }
}
