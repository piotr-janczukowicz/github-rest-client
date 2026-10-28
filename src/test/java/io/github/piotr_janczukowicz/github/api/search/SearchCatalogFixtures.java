package io.github.piotr_janczukowicz.github.api.search;

import io.github.piotr_janczukowicz.github.api.apps.AppsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.licenses.GitHubLicenseSimple;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueType;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueTypeColor;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCodeSearchIndexStatus;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMergeCommitMessage;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMergeCommitTitle;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepository;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubMinimalRepositoryBuilder;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubPullRequestCreationPolicy;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepository;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSquashMergeCommitMessage;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSquashMergeCommitTitle;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUserBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserType;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserViewType;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.json.OptionalNull;
import java.time.Instant;
import java.util.List;
import java.util.Map;

public final class SearchCatalogFixtures {

    static final String VERIFICATION_PAYLOAD = """
            tree a639e96f9038797fba6e0469f94a4b0cc459fa68
            parent a30c19e3f13765a3b48829788bc1cb8b4e95cee4
            author The Octocat <octocat@nowhere.com> 1391563786 -0800
            committer The Octocat <octocat@nowhere.com> 1392160735 -0800

            Create styles.css and updated README""";
    static final String VERIFICATION_SIGNATURE =
            "-----BEGIN PGP SIGNATURE-----\n\nwl4/Tl8KlXNjN5NAh0EWIhZnigw/9PG0u3DoJoTh5H3XI4PLIApywS3NXBTXrPT\n6A0SLdHZHwXG3smNpyGLkiE84qQ6pDUfpBIK0gu/s+pbBhrc0CA==\n-----END PGP SIGNATURE-----";
    static final Instant VERIFICATION_VERIFIED_AT = Instant.parse("2014-02-12T23:18:55Z");

    private SearchCatalogFixtures() {}

    public static GitHubVerification unsignedVerification() {
        return new GitHubVerification(
                false, "unsigned", VERIFICATION_PAYLOAD, VERIFICATION_SIGNATURE, VERIFICATION_VERIFIED_AT);
    }

    static GitHubMinimalRepository spoonKnifeMinimalRepository() {
        return searchMinimalRepository(
                "https://api.github.com/repos/octocat/Spoon-Knife",
                "https://github.com/octocat/Spoon-Knife",
                "Spoon-Knife",
                "octocat/Spoon-Knife",
                "This repo is for demonstration purposes only.",
                UsersCatalogFixtures.octocat(),
                GitHubPullRequestCreationPolicy.ALL);
    }

    static GitHubMinimalRepository jqueryMinimalRepository() {
        return searchMinimalRepository(
                "https://api.github.com/repos/jquery/jquery",
                "https://github.com/jquery/jquery",
                "jquery",
                "jquery/jquery",
                "jQuery JavaScript Library",
                UsersCatalogFixtures.jquery(),
                GitHubPullRequestCreationPolicy.COLLABORATORS_ONLY);
    }

    static GitHubRepository spoonKnifeRepository() {
        final String api = "https://api.github.com/repos/octocat/Spoon-Knife";
        final String http = "http://api.github.com/repos/octocat/Spoon-Knife";
        return new GitHubRepository(
                1_300_192L,
                "MDEwOlJlcG9zaXRvcnkxMzAwMTky",
                "Spoon-Knife",
                "octocat/Spoon-Knife",
                OptionalNull.of(new GitHubLicenseSimple(
                        "mit",
                        "MIT License",
                        "https://api.github.com/licenses/mit",
                        "MIT",
                        "MDc6TGljZW5zZW1pdA==",
                        "https://api.github.com/licenses/mit")),
                9L,
                RepositoriesCatalogFixtures.repositoryPermissions(),
                UsersCatalogFixtures.octocat(),
                false,
                "https://github.com/octocat/Spoon-Knife",
                "This repo is for demonstration purposes only.",
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
                "git:github.com/octocat/Spoon-Knife.git",
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
                "git@github.com:octocat/Spoon-Knife.git",
                http + "/stargazers",
                http + "/statuses/{sha}",
                http + "/subscribers",
                http + "/subscription",
                http + "/tags",
                http + "/teams",
                http + "/git/trees{/sha}",
                "https://github.com/octocat/Spoon-Knife.git",
                OptionalNull.of("https://github.com/octocat/Spoon-Knife-mirror.git"),
                http + "/hooks",
                "https://svn.github.com/octocat/Spoon-Knife",
                "https://github.com",
                OptionalNull.of("JavaScript"),
                9L,
                80L,
                80L,
                108L,
                "master",
                0L,
                false,
                List.of("octocat", "atom", "electron", "api"),
                true,
                true,
                true,
                false,
                false,
                true,
                GitHubPullRequestCreationPolicy.ALL,
                false,
                false,
                "public",
                Instant.parse("2011-01-26T19:06:43Z"),
                Instant.parse("2011-01-26T19:01:12Z"),
                Instant.parse("2011-01-26T19:14:43Z"),
                true,
                "abc123token",
                true,
                false,
                false,
                true,
                GitHubSquashMergeCommitTitle.PR_TITLE,
                GitHubSquashMergeCommitMessage.PR_BODY,
                GitHubMergeCommitTitle.PR_TITLE,
                GitHubMergeCommitMessage.PR_BODY,
                true,
                true,
                false,
                0L,
                80L,
                "2020-11-02T16:12:33Z",
                false,
                new GitHubCodeSearchIndexStatus(true, "6dcb09b5b57875f334f61aebed695e2e4193db5e"));
    }

    static GitHubSimpleUser dtrupennUser() {
        final String api = "https://api.github.com/users/dtrupenn";
        return GitHubSimpleUserBuilder.builder()
                .login("dtrupenn")
                .id(872_147L)
                .nodeId("MDQ6VXNlcjg3MjE0Nw==")
                .avatarUrl(
                        "https://secure.gravatar.com/avatar/e7956084e75f239de85d3a31bc172ace?d=https://a248.e.akamai.net/assets.github.com%2Fimages%2Fgravatars%2Fgravatar-user-420.png")
                .gravatarId("")
                .url(api)
                .htmlUrl("https://github.com/dtrupenn")
                .followersUrl(api + "/followers")
                .followingUrl(api + "/following{/other_user}")
                .gistsUrl(api + "/gists{/gist_id}")
                .starredUrl(api + "/starred{/owner}{/repo}")
                .subscriptionsUrl(api + "/subscriptions")
                .organizationsUrl(api + "/orgs")
                .reposUrl(api + "/repos")
                .eventsUrl(api + "/events{/privacy}")
                .receivedEventsUrl(api + "/received_events")
                .type("User")
                .siteAdmin(false)
                .name("Dan Trupenn")
                .email("dtrupenn@example.com")
                .starredAt("2020-11-02T16:12:33Z")
                .userViewType(GitHubUserViewType.PUBLIC)
                .build();
    }

    static GitHubMilestone spoonKnifeMilestoneV1() {
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

    static GitHubCommitSearchResultItem commitSearchResultItem() {
        final String sha = "bb4cc8d3b2e14b3af5df699876dd4ff3acd00b7f";
        final String commitApiUrl = "https://api.github.com/repos/octocat/Spoon-Knife/commits/" + sha;
        return new GitHubCommitSearchResultItem(
                commitApiUrl,
                sha,
                "https://github.com/octocat/Spoon-Knife/commit/" + sha,
                commitApiUrl + "/comments",
                new GitHubCommitSearchResultItem.Commit(
                        new GitHubCommitSearchResultItem.CommitAuthor(
                                "The Octocat", "octocat@nowhere.com", Instant.parse("2014-02-04T22:38:36Z")),
                        new GitHubGitUser("The Octocat", "octocat@nowhere.com", Instant.parse("2014-02-12T23:18:55Z")),
                        8,
                        "Create styles.css and updated README",
                        new GitHubCommitSearchResultItem.CommitTree(
                                "a639e96f9038797fba6e0469f94a4b0cc459fa68",
                                "https://api.github.com/repos/octocat/Spoon-Knife/git/trees/a639e96f9038797fba6e0469f94a4b0cc459fa68"),
                        "https://api.github.com/repos/octocat/Spoon-Knife/git/commits/bb4cc8d3b2e14b3af5df699876dd4ff3acd00b7f",
                        unsignedVerification()),
                UsersCatalogFixtures.octocat(),
                new GitHubGitUser("The Octocat", "octocat@nowhere.com", Instant.parse("2014-02-12T23:18:55Z")),
                List.of(new GitHubCommitSearchResultItem.Parent(
                        "https://api.github.com/repos/octocat/Spoon-Knife/commits/a30c19e3f13765a3b48829788bc1cb8b4e95cee4",
                        "https://github.com/octocat/Spoon-Knife/commit/a30c19e3f13765a3b48829788bc1cb8b4e95cee4",
                        "a30c19e3f13765a3b48829788bc1cb8b4e95cee4")),
                spoonKnifeMinimalRepository(),
                1.0,
                "C_kwDOBqX8As4Bb4zI0zI",
                List.of(new GitHubSearchTextMatchItem(
                        commitApiUrl,
                        "Commit",
                        "message",
                        "Create styles.css and updated README",
                        List.of(new GitHubSearchTextMatch("css", List.of(14, 17))))));
    }

    static GitHubCodeSearchResultItem codeSearchResultItem() {
        return new GitHubCodeSearchResultItem(
                "classes.js",
                "src/attributes/classes.js",
                "45b0b1303b8678da3678a0741b2d7d7026ed58fc",
                "https://api.github.com/repositories/1300192/contents/src/attributes/classes.js?ref=master",
                "https://api.github.com/repositories/1300192/git/blobs/45b0b1303b8678da3678a0741b2d7d7026ed58fc",
                "https://github.com/jquery/jquery/blob/master/src/attributes/classes.js",
                jqueryMinimalRepository(),
                1.0,
                4430,
                "JavaScript",
                Instant.parse("2011-04-22T20:33:13Z"),
                List.of("73..77", "77..78"),
                List.of(
                        new GitHubSearchTextMatchItem(
                                "https://api.github.com/repositories/1300192/contents/src/attributes/classes.js?ref=master",
                                "FileContent",
                                "content",
                                "jQuery.fn.extend({\n\taddClass: function( value ) {\n\t\tvar classes, elem, cur, clazz, j, finalValue,\n\t\t\ti = 0,\n\t\t\tlen = this.length,\n\t\t\tproceed = typeof value === \"string\" && value;",
                                List.of(new GitHubSearchTextMatch("addClass", List.of(18, 26)))),
                        new GitHubSearchTextMatchItem(
                                "https://api.github.com/repositories/1300192/contents/src/attributes/classes.js?ref=master",
                                "FileContent",
                                "path",
                                "src/attributes/classes.js",
                                List.of(new GitHubSearchTextMatch("classes.js", List.of(15, 25))))));
    }

    static GitHubIssueSearchResultItem issueSearchResultItem() {
        final String issueApiUrl = "https://api.github.com/repos/octocat/Spoon-Knife/issues/1309";
        final String issueHtmlUrl = "https://github.com/octocat/Spoon-Knife/issues/1309";
        return new GitHubIssueSearchResultItem(
                issueApiUrl,
                "https://api.github.com/repos/octocat/Spoon-Knife",
                issueApiUrl + "/labels{/name}",
                issueApiUrl + "/comments",
                issueApiUrl + "/events",
                issueHtmlUrl,
                215_335_132L,
                "MDU6SXNzdWUyMTUzMzUxMzI=",
                1309,
                "Found a bug",
                false,
                "resolved",
                List.of(UsersCatalogFixtures.octocat()),
                UsersCatalogFixtures.octocat(),
                List.of(new GitHubIssueSearchResultItem.Label(
                        208_045_946L,
                        "MDU6TGFiZWwyMDgwNDU5NDY=",
                        "https://api.github.com/repos/octocat/Spoon-Knife/labels/bug",
                        "bug",
                        "d73a4a",
                        true,
                        "Something isn't working",
                        Instant.parse("2023-01-01T12:00:00Z"),
                        UsersCatalogFixtures.octocat())),
                new GitHubSubIssuesSummary(3, 1, 33),
                new GitHubIssueDependenciesSummary(1, 2, 1, 2),
                List.of(
                        new GitHubIssueFieldValue(
                                1L,
                                "Priority",
                                "IFT_GDKND",
                                GitHubIssueFieldDataType.SINGLE_SELECT,
                                "High",
                                new GitHubIssueFieldValue.SelectOption(1L, "High", "red"),
                                List.of(new GitHubIssueFieldValue.SelectOption(3L, "Urgent", "orange"))),
                        new GitHubIssueFieldValue(
                                2L,
                                "Tags",
                                "IFT_TAGS",
                                GitHubIssueFieldDataType.MULTI_SELECT,
                                "Backend",
                                new GitHubIssueFieldValue.SelectOption(4L, "Frontend", "green"),
                                List.of(new GitHubIssueFieldValue.SelectOption(2L, "Backend", "blue")))),
                "open",
                "completed",
                spoonKnifeMilestoneV1(),
                5,
                Instant.parse("2011-04-22T13:33:48Z"),
                Instant.parse("2011-04-22T13:33:48Z"),
                Instant.parse("2011-04-30T12:00:00Z"),
                List.of(new GitHubSearchTextMatchItem(
                        "https://api.github.com/repositories/215335/issues/132",
                        "Issue",
                        "body",
                        "comprehensive windows font",
                        List.of(new GitHubSearchTextMatch("windows", List.of(14, 21))))),
                new GitHubIssueSearchResultItem.PullRequest(
                        Instant.parse("2011-04-14T16:00:49Z"),
                        "https://github.com/octocat/Spoon-Knife/pull/1309.diff",
                        "https://github.com/octocat/Spoon-Knife/pull/1309",
                        "https://github.com/octocat/Spoon-Knife/pull/1309.patch",
                        "https://api.github.com/repos/octocat/Spoon-Knife/pulls/1309"),
                "I'm having a problem with this.",
                1.0,
                GitHubAuthorAssociation.OWNER,
                false,
                spoonKnifeRepository(),
                "<p>I'm having a problem with this.</p>",
                "I'm having a problem with this.",
                issueApiUrl + "/timeline",
                issueType(),
                AppsCatalogFixtures.probotIntegration(),
                pinnedIssueComment(),
                issueReactions());
    }

    static GitHubIssueComment pinnedIssueComment() {
        return new GitHubIssueComment(
                42L,
                "IC_kwDOA",
                "https://api.github.com/repos/octocat/Spoon-Knife/issues/comments/42",
                "Pinned comment body",
                "Pinned comment body",
                "<p>Pinned comment body</p>",
                "https://github.com/octocat/Spoon-Knife/issues/1309#issuecomment-42",
                UsersCatalogFixtures.octocat(),
                Instant.parse("2011-04-14T16:00:49Z"),
                Instant.parse("2011-04-14T16:00:49Z"),
                "https://api.github.com/repos/octocat/Spoon-Knife/issues/1309",
                GitHubAuthorAssociation.OWNER,
                AppsCatalogFixtures.probotIntegration(),
                commentReactions(),
                new GitHubIssueComment.PinnedComment(
                        Instant.parse("2011-04-14T16:00:49Z"), UsersCatalogFixtures.octocat()),
                new GitHubIssueComment.Minimized("low-quality"));
    }

    static GitHubRepoSearchResultItem repoSearchTetris() {
        final String apiUrl = "https://api.github.com/repos/dtrupenn/Tetris";
        final String htmlUrl = "https://github.com/dtrupenn/Tetris";
        final String description = "A C implementation of Tetris using Pennsim through LC4";
        return new GitHubRepoSearchResultItem(
                3_081_286L,
                "MDEwOlJlcG9zaXRvcnkzMDgxMjg2",
                "Tetris",
                "dtrupenn/Tetris",
                dtrupennUser(),
                false,
                htmlUrl,
                description,
                false,
                apiUrl,
                Instant.parse("2012-01-01T00:31:50Z"),
                Instant.parse("2013-01-05T17:58:47Z"),
                Instant.parse("2012-01-01T00:37:02Z"),
                "",
                524,
                1,
                1,
                "Assembly",
                0,
                0,
                "master",
                "master",
                10.309_712d,
                apiUrl + "/forks",
                apiUrl + "/keys{/key_id}",
                apiUrl + "/collaborators{/collaborator}",
                apiUrl + "/teams",
                apiUrl + "/hooks",
                apiUrl + "/issues/events{/number}",
                apiUrl + "/events",
                apiUrl + "/assignees{/user}",
                apiUrl + "/branches{/branch}",
                apiUrl + "/tags",
                apiUrl + "/git/blobs{/sha}",
                apiUrl + "/git/tags{/sha}",
                apiUrl + "/git/refs{/sha}",
                apiUrl + "/git/trees{/sha}",
                apiUrl + "/statuses/{sha}",
                apiUrl + "/languages",
                apiUrl + "/stargazers",
                apiUrl + "/contributors",
                apiUrl + "/subscribers",
                apiUrl + "/subscription",
                apiUrl + "/commits{/sha}",
                apiUrl + "/git/commits{/sha}",
                apiUrl + "/comments{/number}",
                apiUrl + "/issues/comments{/number}",
                apiUrl + "/contents/{+path}",
                apiUrl + "/compare/{base}...{head}",
                apiUrl + "/merges",
                apiUrl + "/{archive_format}{/ref}",
                apiUrl + "/downloads",
                apiUrl + "/issues{/number}",
                apiUrl + "/pulls{/number}",
                apiUrl + "/milestones{/number}",
                apiUrl + "/notifications{?since,all,participating}",
                apiUrl + "/labels{/name}",
                apiUrl + "/releases{/id}",
                apiUrl + "/deployments",
                "git://github.com/dtrupenn/Tetris.git",
                "git@github.com:dtrupenn/Tetris.git",
                "https://github.com/dtrupenn/Tetris.git",
                htmlUrl,
                0,
                0,
                1,
                List.of("tetris", "assembly"),
                "https://github.com/dtrupenn/Tetris-mirror.git",
                true,
                true,
                false,
                true,
                true,
                false,
                true,
                GitHubPullRequestCreationPolicy.ALL,
                false,
                false,
                "public",
                mitLicenseWithHtmlUrl(),
                new GitHubRepoSearchResultItem.Permissions(false, false, false, false, true),
                List.of(
                        new GitHubSearchTextMatchItem(
                                "https://api.github.com/repositories/3081286",
                                "Repository",
                                "name",
                                "Tetris",
                                List.of(new GitHubSearchTextMatch("Tetris", List.of(0, 6)))),
                        new GitHubSearchTextMatchItem(
                                "https://api.github.com/repositories/3081286",
                                "Repository",
                                "description",
                                description,
                                List.of(new GitHubSearchTextMatch("Tetris", List.of(22, 28))))),
                "abc123",
                true,
                true,
                true,
                false,
                false,
                true,
                false,
                false);
    }

    static GitHubUserSearchResultItem userSearchOctocat() {
        return new GitHubUserSearchResultItem(
                "octocat",
                1L,
                "MDQ6VXNlcjE=",
                "https://github.com/images/error/octocat_happy.gif",
                "",
                "https://api.github.com/users/octocat",
                "https://github.com/octocat",
                "https://api.github.com/users/octocat/followers",
                "https://api.github.com/users/octocat/subscriptions",
                "https://api.github.com/users/octocat/orgs",
                "https://api.github.com/users/octocat/repos",
                "https://api.github.com/users/octocat/received_events",
                GitHubUserType.USER,
                1.0,
                "https://api.github.com/users/octocat/following{/other_user}",
                "https://api.github.com/users/octocat/gists{/gist_id}",
                "https://api.github.com/users/octocat/starred{/owner}{/repo}",
                "https://api.github.com/users/octocat/events{/privacy}",
                2,
                1,
                20,
                0,
                Instant.parse("2008-01-14T04:33:35Z"),
                Instant.parse("2008-01-14T04:33:35Z"),
                "monalisa octocat",
                "There once was...",
                "octocat@github.com",
                "San Francisco",
                false,
                false,
                List.of(new GitHubSearchTextMatchItem(
                        "https://api.github.com/search/users?q=octocat",
                        "User",
                        "login",
                        "octocat",
                        List.of(new GitHubSearchTextMatch("octocat", List.of(0, 7))))),
                "https://github.com/blog",
                "GitHub",
                Instant.parse("2016-01-01T00:00:00Z"),
                GitHubUserViewType.PUBLIC);
    }

    private static GitHubLicenseSimple mitLicenseWithHtmlUrl() {
        return new GitHubLicenseSimple(
                "mit",
                "MIT License",
                "https://api.github.com/licenses/mit",
                "MIT",
                "MDc6TGljZW5zZTEz",
                "https://api.github.com/licenses/mit");
    }

    private static GitHubReactionRollup issueReactions() {
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

    private static GitHubMinimalRepository searchMinimalRepository(
            final String api,
            final String htmlUrl,
            final String name,
            final String fullName,
            final String description,
            final GitHubSimpleUser owner,
            final GitHubPullRequestCreationPolicy pullRequestCreationPolicy) {
        return GitHubMinimalRepositoryBuilder.builder()
                .id(1_300_192L)
                .nodeId("MDEwOlJlcG9zaXRvcnkxMzAwMTky")
                .name(name)
                .fullName(fullName)
                .owner(owner)
                .isPrivate(false)
                .htmlUrl(htmlUrl)
                .description(description)
                .fork(false)
                .url(api)
                .archiveUrl(api + "/{archive_format}{/ref}")
                .assigneesUrl(api + "/assignees{/user}")
                .blobsUrl(api + "/git/blobs{/sha}")
                .branchesUrl(api + "/branches{/branch}")
                .collaboratorsUrl(api + "/collaborators{/collaborator}")
                .commentsUrl(api + "/comments{/number}")
                .commitsUrl(api + "/commits{/sha}")
                .compareUrl(api + "/compare/{base}...{head}")
                .contentsUrl(api + "/contents/{+path}")
                .contributorsUrl(api + "/contributors")
                .deploymentsUrl(api + "/deployments")
                .downloadsUrl(api + "/downloads")
                .eventsUrl(api + "/events")
                .forksUrl(api + "/forks")
                .gitCommitsUrl(api + "/git/commits{/sha}")
                .gitRefsUrl(api + "/git/refs{/sha}")
                .gitTagsUrl(api + "/git/tags{/sha}")
                .gitUrl("git:github.com/" + fullName + ".git")
                .issueCommentUrl(api + "/issues/comments{/number}")
                .issueEventsUrl(api + "/issues/events{/number}")
                .issuesUrl(api + "/issues{/number}")
                .keysUrl(api + "/keys{/key_id}")
                .labelsUrl(api + "/labels{/name}")
                .languagesUrl(api + "/languages")
                .mergesUrl(api + "/merges")
                .milestonesUrl(api + "/milestones{/number}")
                .notificationsUrl(api + "/notifications{?since,all,participating}")
                .pullsUrl(api + "/pulls{/number}")
                .releasesUrl(api + "/releases{/id}")
                .sshUrl("git@github.com:" + fullName + ".git")
                .stargazersUrl(api + "/stargazers")
                .statusesUrl(api + "/statuses/{sha}")
                .subscribersUrl(api + "/subscribers")
                .subscriptionUrl(api + "/subscription")
                .tagsUrl(api + "/tags")
                .teamsUrl(api + "/teams")
                .treesUrl(api + "/git/trees{/sha}")
                .cloneUrl(htmlUrl + ".git")
                .mirrorUrl(htmlUrl + "-mirror.git")
                .hooksUrl(api + "/hooks")
                .svnUrl("https://svn.github.com/" + fullName)
                .homepage("https://github.com")
                .language("C")
                .forksCount(9L)
                .stargazersCount(80L)
                .watchersCount(80L)
                .size(108L)
                .defaultBranch("master")
                .openIssuesCount(0L)
                .isTemplate(false)
                .topics(List.of("octocat", "atom", "electron", "api"))
                .hasIssues(true)
                .hasProjects(true)
                .hasWiki(true)
                .hasPages(false)
                .hasDiscussions(false)
                .hasPullRequests(true)
                .pullRequestCreationPolicy(pullRequestCreationPolicy)
                .archived(false)
                .disabled(false)
                .visibility("public")
                .pushedAt(Instant.parse("2011-01-26T19:06:43Z"))
                .createdAt(Instant.parse("2011-01-26T19:01:12Z"))
                .updatedAt(Instant.parse("2011-01-26T19:14:43Z"))
                .permissions(RepositoriesCatalogFixtures.repositoryPermissions())
                .tempCloneToken("abc123token")
                .deleteBranchOnMerge(false)
                .subscribersCount(80L)
                .networkCount(0L)
                .roleName("admin")
                .codeOfConduct(RepositoriesCatalogFixtures.contributorCovenantWithBody())
                .license(RepositoriesCatalogFixtures.minimalRepositoryLicense())
                .forks(9L)
                .openIssues(0L)
                .watchers(80L)
                .allowForking(true)
                .webCommitSignoffRequired(false)
                .securityAndAnalysis(RepositoriesCatalogFixtures.securityAndAnalysis())
                .customProperties(Map.of("team", "core"))
                .build();
    }

    static GitHubSearchTextMatchItem searchTextMatchItem() {
        return new GitHubSearchTextMatchItem(
                "https://api.github.com/repos/octocat/Hello-World/issues/1",
                "Issue",
                "title",
                "title",
                List.of(new GitHubSearchTextMatch("title", List.of(0, 5))));
    }

    public static GitHubIssueType issueType() {
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
}
