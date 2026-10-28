package io.github.piotr_janczukowicz.github.api.meta;

import java.util.List;

final class MetaCatalogFixtures {

    private MetaCatalogFixtures() {}

    static GitHubSshKeyFingerprints sshKeyFingerprints() {
        return new GitHubSshKeyFingerprints(
                "uNiVztksCsDhcc0u9e8BujQXVUpKZIDTMczCvj3tD2s",
                "br9IjFspm1vxR3iA35FWE+4VTyz1hYVLIE2t1/CeyWQ",
                "p2QAMXNIC1TJYWeIOttrVc98/R1BUFWu3/LiyKgUfQM",
                "+DiY3wvvV6TuJJhbpZisF/zLDA0zPMSvHdkr4UvCOqU");
    }

    static GitHubApiRoot apiRoot() {
        return new GitHubApiRoot(
                "https://api.github.com/user",
                "https://github.com/settings/connections/applications{/client_id}",
                "https://api.github.com/search/code?q={query}{&page,per_page,sort,order}",
                "https://api.github.com/search/commits?q={query}{&page,per_page,sort,order}",
                "https://api.github.com/user/emails",
                "https://api.github.com/emojis",
                "https://api.github.com/events",
                "https://api.github.com/feeds",
                "https://api.github.com/user/followers",
                "https://api.github.com/user/following{/target}",
                "https://api.github.com/gists{/gist_id}",
                "https://api.github.com/search/issues?q={query}{&page,per_page,sort,order}",
                "https://api.github.com/issues",
                "https://api.github.com/user/keys",
                "https://api.github.com/search/labels?q={query}&repository_id={repository_id}{&page,per_page}",
                "https://api.github.com/notifications",
                "https://api.github.com/orgs/{org}",
                "https://api.github.com/orgs/{org}/repos{?type,page,per_page,sort}",
                "https://api.github.com/orgs/{org}/teams",
                "https://api.github.com/gists/public",
                "https://api.github.com/rate_limit",
                "https://api.github.com/repos/{owner}/{repo}",
                "https://api.github.com/search/repositories?q={query}{&page,per_page,sort,order}",
                "https://api.github.com/user/repos{?type,page,per_page,sort}",
                "https://api.github.com/user/starred{/owner}{/repo}",
                "https://api.github.com/gists/starred",
                "https://api.github.com/search/topics?q={query}{&page,per_page}",
                "https://api.github.com/users/{user}",
                "https://api.github.com/user/orgs",
                "https://api.github.com/users/{user}/repos{?type,page,per_page,sort}",
                "https://api.github.com/search/users?q={query}{&page,per_page,sort,order}");
    }

    static GitHubApiOverviewArtifactAttestations artifactAttestations() {
        return new GitHubApiOverviewArtifactAttestations("example", List.of("attestations.example.com"));
    }

    static GitHubApiOverviewActionsInbound actionsInbound() {
        return new GitHubApiOverviewActionsInbound(List.of("actions.example.com"), List.of("*.actions.example.com"));
    }

    static GitHubApiOverviewDomains domains() {
        return new GitHubApiOverviewDomains(
                List.of("*.example.com"),
                List.of("*.example.com"),
                List.of("*.example.com"),
                List.of("*.example.com"),
                List.of("*.example.com"),
                List.of("*.example.com"),
                actionsInbound(),
                artifactAttestations());
    }

    static GitHubGetOctocatFilter getOctocatFilter() {
        return GitHubGetOctocatFilterBuilder.builder().s("Hello").build();
    }

    static GitHubApiOverview apiOverview() {
        return new GitHubApiOverview(
                true,
                new GitHubSshKeyFingerprints(
                        "uNiVztksCsDhcc0u9e8BujQXVUpKZIDTMczCvj3tD2s",
                        "br9IjFspm1vxR3iA35FWE+4VTyz1hYVLIE2t1/CeyWQ",
                        "p2QAMXNIC1TJYWeIOttrVc98/R1BUFWu3/LiyKgUfQM",
                        "+DiY3wvvV6TuJJhbpZisF/zLDA0zPMSvHdkr4UvCOqU"),
                List.of(
                        "ssh-ed25519 ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                        "ecdsa-sha2-nistp256 ABCDEFGHIJKLMNOPQRSTUVWXYZ",
                        "ssh-rsa ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
                List.of("192.0.2.1"),
                List.of("192.0.2.1"),
                List.of("192.0.2.1"),
                List.of("192.0.2.1"),
                List.of("192.0.2.1"),
                List.of("192.0.2.1"),
                List.of("192.0.2.1"),
                List.of("192.0.2.1"),
                List.of("192.0.2.1"),
                List.of("192.0.2.1"),
                List.of("192.0.2.1"),
                List.of("192.0.2.1"),
                List.of("192.0.2.1"),
                List.of("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
                new GitHubApiOverviewDomains(
                        List.of("*.example.com"),
                        List.of("*.example.com"),
                        List.of("*.example.com"),
                        List.of("*.example.com"),
                        List.of("*.example.com"),
                        List.of("*.example.com"),
                        new GitHubApiOverviewActionsInbound(
                                List.of("actions.example.com"), List.of("*.actions.example.com")),
                        new GitHubApiOverviewArtifactAttestations("example", List.of("attestations.example.com"))),
                "3.22.0");
    }
}
