package io.github.piotr_janczukowicz.github.api.metrics;

import io.github.piotr_janczukowicz.github.api.licenses.GitHubLicenseSimple;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubCodeOfConductSimple;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import io.github.piotr_janczukowicz.json.OptionalNull;
import java.time.Instant;
import java.util.List;

final class MetricsCatalogFixtures {

    private MetricsCatalogFixtures() {}

    static GitHubGetClonesFilter getClonesFilter() {
        return GitHubGetClonesFilterBuilder.builder().per(GitHubTrafficPer.DAY).build();
    }

    static GitHubTraffic traffic() {
        return new GitHubTraffic(Instant.parse("2016-10-10T00:00:00Z"), 18L, 23L);
    }

    static GitHubViewTraffic viewTraffic() {
        return new GitHubViewTraffic(
                14_850L, 3_782L, List.of(new GitHubTraffic(Instant.parse("2016-10-10T00:00:00Z"), 545L, 2_718L)));
    }

    static GitHubCloneTraffic cloneTraffic() {
        return new GitHubCloneTraffic(173L, 128L, List.of(traffic()));
    }

    static GitHubContentTraffic contentTraffic() {
        return new GitHubContentTraffic(
                "/github/hubot", "github/hubot: A customizable life embetterment robot.", 3_542L, 2_225L);
    }

    static GitHubReferrerTraffic referrerTraffic() {
        return new GitHubReferrerTraffic("Google", 4L, 3L);
    }

    static GitHubParticipationStats participationStats() {
        return new GitHubParticipationStats(
                List.of(
                        11, 21, 15, 2, 8, 1, 8, 23, 17, 21, 11, 22, 15, 2, 8, 1, 8, 23, 17, 21, 11, 22, 15, 2, 8, 1, 8,
                        23, 17, 21, 11, 22, 15, 2, 8, 1, 8, 23, 17, 21, 11, 22, 15, 2, 8, 1, 8, 23, 17, 21, 11, 22),
                List.of(
                        3, 2, 3, 0, 2, 0, 1, 3, 2, 4, 2, 5, 3, 0, 2, 0, 1, 3, 2, 4, 2, 5, 3, 0, 2, 0, 1, 3, 2, 4, 2, 5,
                        3, 0, 2, 0, 1, 3, 2, 4, 2, 5, 3, 0, 2, 0, 1, 3, 2, 4, 2, 5));
    }

    static GitHubCommitActivity commitActivity() {
        return new GitHubCommitActivity(List.of(0, 3, 26, 20, 39, 1, 0), 89L, 1_336_280_400L);
    }

    static GitHubContributorActivityWeek contributorActivityWeek() {
        return new GitHubContributorActivityWeek(1L, 1L, 1L, 1L);
    }

    static GitHubContributorActivity contributorActivity() {
        return new GitHubContributorActivity(
                UsersCatalogFixtures.octocat(),
                135L,
                List.of(new GitHubContributorActivityWeek(1_367_712_000L, 6_898L, 77L, 10L)));
    }

    static GitHubCommunityHealthFile communityHealthFile() {
        return healthFile(
                "https://api.github.com/repos/octocat/Hello-World/contents/README.md",
                "https://github.com/octocat/Hello-World/blob/master/README.md");
    }

    static GitHubCommunityProfileFiles communityProfileFiles() {
        return new GitHubCommunityProfileFiles(
                OptionalNull.of(new GitHubCodeOfConductSimple(
                        "https://api.github.com/codes_of_conduct/contributor_covenant",
                        "contributor_covenant",
                        "Contributor Covenant",
                        "https://github.com/octocat/Hello-World/blob/master/CODE_OF_CONDUCT.md")),
                OptionalNull.of(healthFile(
                        "https://api.github.com/repos/octocat/Hello-World/contents/CODE_OF_CONDUCT.md",
                        "https://github.com/octocat/Hello-World/blob/master/CODE_OF_CONDUCT.md")),
                OptionalNull.of(new GitHubLicenseSimple(
                        "mit",
                        "MIT License",
                        "https://api.github.com/licenses/mit",
                        "MIT",
                        "MDc6TGljZW5zZW1pdA==",
                        "https://github.com/octocat/Hello-World/blob/master/LICENSE")),
                OptionalNull.of(healthFile(
                        "https://api.github.com/repos/octocat/Hello-World/contents/CONTRIBUTING",
                        "https://github.com/octocat/Hello-World/blob/master/CONTRIBUTING")),
                OptionalNull.of(healthFile(
                        "https://api.github.com/repos/octocat/Hello-World/contents/README.md",
                        "https://github.com/octocat/Hello-World/blob/master/README.md")),
                OptionalNull.of(healthFile(
                        "https://api.github.com/repos/octocat/Hello-World/contents/ISSUE_TEMPLATE",
                        "https://github.com/octocat/Hello-World/blob/master/ISSUE_TEMPLATE")),
                OptionalNull.of(healthFile(
                        "https://api.github.com/repos/octocat/Hello-World/contents/PULL_REQUEST_TEMPLATE",
                        "https://github.com/octocat/Hello-World/blob/master/PULL_REQUEST_TEMPLATE")));
    }

    static GitHubCommunityProfile communityProfile() {
        return new GitHubCommunityProfile(
                100,
                "My first repository on GitHub!",
                "example.com",
                communityProfileFiles(),
                Instant.parse("2017-02-28T19:09:29Z"),
                true);
    }

    static GitHubCodeFrequencyStat codeFrequencyStat() {
        return new GitHubCodeFrequencyStat(List.of(1_302_998_400, 1_124, -435));
    }

    static GitHubPunchCardStats punchCardStats() {
        return new GitHubPunchCardStats(List.of(new GitHubCodeFrequencyStat(List.of(0, 9, 3))));
    }

    static GitHubCodeFrequencyStats codeFrequencyStats() {
        return new GitHubCodeFrequencyStats(List.of(new GitHubCodeFrequencyStat(List.of(1_336_280_400, 1_049, -79))));
    }

    private static GitHubCommunityHealthFile healthFile(final String url, final String htmlUrl) {
        return new GitHubCommunityHealthFile(url, htmlUrl);
    }
}
