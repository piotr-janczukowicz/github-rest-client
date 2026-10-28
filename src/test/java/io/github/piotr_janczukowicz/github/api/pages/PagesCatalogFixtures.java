package io.github.piotr_janczukowicz.github.api.pages;

import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

final class PagesCatalogFixtures {

    private PagesCatalogFixtures() {}

    static GitHubCreatePagesDeploymentPayload createPagesDeploymentPayload() {
        return GitHubCreatePagesDeploymentPayloadBuilder.builder()
                .pagesBuildVersion("sha")
                .oidcToken("token")
                .build();
    }

    static GitHubCreatePagesSitePayload createPagesSitePayload() {
        return GitHubCreatePagesSitePayloadBuilder.builder()
                .source(GitHubCreatePagesSiteSourceBuilder.builder()
                        .branch("main")
                        .build())
                .build();
    }

    static GitHubUpdatePagesSitePayload updatePagesSitePayload() {
        return GitHubUpdatePagesSitePayloadBuilder.builder().httpsEnforced(true).build();
    }

    static GitHubPagesDeploymentStatusResponse pagesDeploymentStatusResponse() {
        return new GitHubPagesDeploymentStatusResponse(GitHubPagesDeploymentStatus.SUCCEED);
    }

    static GitHubPagesHealthCheck pagesHealthCheck() {
        return new GitHubPagesHealthCheck(healthCheckDomain(), altHealthCheckDomain());
    }

    static GitHubPagesSite pagesSite() {
        return new GitHubPagesSite(
                "https://api.github.com/repos/github/hello-world/pages",
                GitHubPagesSiteStatus.BUILT,
                "example.com",
                GitHubPagesProtectedDomainState.PENDING,
                Instant.parse("2026-09-01T12:00:00Z"),
                false,
                "https://example.com",
                GitHubPagesBuildType.LEGACY,
                new GitHubPagesSource("main", "/docs"),
                true,
                new GitHubPagesHttpsCertificate(
                        GitHubPagesHttpsCertificateState.APPROVED,
                        "Certificate is approved",
                        List.of("example.com", "www.example.com"),
                        LocalDate.parse("2026-12-31")),
                true);
    }

    static GitHubPageBuild pageBuild() {
        return new GitHubPageBuild(
                "https://api.github.com/repos/github/hello-world/pages/builds/1",
                "built",
                new GitHubPageBuildError("Page build failed"),
                pageBuildPusher(),
                "a84d88e7554fc1fa21bcbc4efae3c782a70d2b9d",
                2104L,
                Instant.parse("2014-08-10T10:10:10Z"),
                Instant.parse("2014-08-10T10:10:10Z"));
    }

    static GitHubPageBuildStatus pageBuildStatus() {
        return new GitHubPageBuildStatus(
                "https://api.github.com/repos/github/hello-world/pages/builds/latest", "queued");
    }

    static GitHubPagesHealthCheckDomain healthCheckDomain() {
        return new GitHubPagesHealthCheckDomain(
                "example.com",
                "http://example.com/",
                "default",
                true,
                false,
                false,
                false,
                false,
                true,
                false,
                false,
                true,
                true,
                true,
                false,
                false,
                false,
                true,
                false,
                true,
                true,
                true,
                "Domain is properly configured",
                true,
                true,
                "Certificate is pending renewal",
                true,
                "CAA record does not allow letsencrypt.org");
    }

    static GitHubPagesHealthCheckDomain altHealthCheckDomain() {
        return new GitHubPagesHealthCheckDomain(
                "www.example.com",
                "http://www.example.com/",
                "default",
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                false,
                true,
                false,
                false,
                true,
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                "Domain redirects to example.com",
                true,
                false,
                "Certificate is not yet issued",
                false,
                "CAA record is missing");
    }

    static GitHubSimpleUser pageBuildPusher() {
        return UsersCatalogFixtures.octocat();
    }

    static GitHubPagesDeployment pagesDeployment() {
        return new GitHubPagesDeployment(
                "4fd754f7e594640989b406850d0bc8f06a121251",
                "https://api.github.com/repos/github/hello-world/pages/deployments/4fd754f7e594640989b406850d0bc8f06a121251",
                "hello-world.github.io",
                "monalisa-1231a2312sa32-23sda74.drafts.github.io");
    }
}
