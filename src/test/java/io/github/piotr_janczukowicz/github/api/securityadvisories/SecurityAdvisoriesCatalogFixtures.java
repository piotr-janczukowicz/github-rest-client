package io.github.piotr_janczukowicz.github.api.securityadvisories;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryPermissions;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamAccessSource;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamOwnershipType;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamSimple;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;
import java.util.List;

final class SecurityAdvisoriesCatalogFixtures {

    private SecurityAdvisoriesCatalogFixtures() {}

    static GitHubTeam collaboratingTeam() {
        return new GitHubTeam(
                1L,
                "MDQ6VGVhbTE=",
                "Justice League",
                "justice-league",
                "A great team.",
                "closed",
                "notifications_enabled",
                "push",
                new GitHubRepositoryPermissions(true, false, true, false, true),
                "https://api.github.com/organizations/1/team/1",
                "https://github.com/orgs/rails/teams/core",
                "https://api.github.com/organizations/1/team/1/members{/member}",
                "https://api.github.com/organizations/1/team/1/repos",
                GitHubTeamOwnershipType.ORGANIZATION,
                GitHubTeamAccessSource.ORGANIZATION,
                1L,
                2L,
                collaboratingTeamParent());
    }

    private static GitHubTeamSimple collaboratingTeamParent() {
        return new GitHubTeamSimple(
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
                2L);
    }

    static GitHubCreatePrivateVulnerabilityReportPayload createPrivateVulnerabilityReportPayload() {
        return new GitHubCreatePrivateVulnerabilityReportPayload(
                "A newly discovered vulnerability", "A more in-depth description.", null, null, null, null, null);
    }

    static GitHubCreateRepositoryAdvisoryPayload createRepositoryAdvisoryPayload() {
        return new GitHubCreateRepositoryAdvisoryPayload(
                "A new important advisory",
                "A more in-depth description.",
                List.of(repositoryAdvisoryVulnerabilityPayload()),
                null,
                null,
                null,
                null,
                null,
                null);
    }

    static GitHubRepositoryAdvisoryPackagePayload repositoryAdvisoryPackagePayload() {
        return new GitHubRepositoryAdvisoryPackagePayload(GitHubSecurityAdvisoryEcosystem.NPM, "a-package");
    }

    static GitHubRepositoryAdvisoryVulnerabilityPayload repositoryAdvisoryVulnerabilityPayload() {
        return new GitHubRepositoryAdvisoryVulnerabilityPayload(
                repositoryAdvisoryPackagePayload(), "< 1.0.0", "1.0.0", List.of("important_function"));
    }

    static GitHubUpdateRepositoryAdvisoryPayload updateRepositoryAdvisoryPayload() {
        return new GitHubUpdateRepositoryAdvisoryPayload(
                null,
                null,
                null,
                null,
                null,
                null,
                GitHubRepositoryAdvisorySeverity.CRITICAL,
                null,
                GitHubRepositoryAdvisoryUpdateState.PUBLISHED,
                null,
                null);
    }

    static GitHubGlobalAdvisory globalAdvisory() {
        final String ghsaId = "GHSA-jfh8-c2jp-5v3q";
        return GitHubGlobalAdvisoryBuilder.builder()
                .ghsaId(ghsaId)
                .cveId("CVE-2021-44228")
                .url("https://api.github.com/advisories/" + ghsaId)
                .htmlUrl("https://github.com/advisories/" + ghsaId)
                .repositoryAdvisoryUrl(
                        "https://api.github.com/repos/apache/logging-log4j2/security-advisories/" + ghsaId)
                .summary("Remote code injection in Log4j")
                .description("Apache Log4j2 remote code execution vulnerability.")
                .type(GitHubGlobalAdvisoryType.REVIEWED)
                .severity(GitHubSecurityAdvisorySeverity.CRITICAL)
                .sourceCodeLocation("https://github.com/apache/logging-log4j2")
                .identifiers(List.of(
                        new GitHubAdvisoryIdentifier(GitHubAdvisoryIdentifierType.GHSA, ghsaId),
                        new GitHubAdvisoryIdentifier(GitHubAdvisoryIdentifierType.CVE, "CVE-2021-44228")))
                .references(List.of("https://nvd.nist.gov/vuln/detail/CVE-2021-44228"))
                .publishedAt(Instant.parse("2021-12-10T00:00:00Z"))
                .updatedAt(Instant.parse("2022-02-08T12:00:00Z"))
                .githubReviewedAt(Instant.parse("2021-12-12T00:00:00Z"))
                .nvdPublishedAt(Instant.parse("2021-12-10T09:00:00Z"))
                .withdrawnAt(Instant.parse("2022-06-01T00:00:00Z"))
                .vulnerabilities(List.of(new GitHubVulnerability(
                        GitHubAdvisoryPackageBuilder.builder()
                                .ecosystem(GitHubSecurityAdvisoryEcosystem.MAVEN)
                                .name("org.apache.logging.log4j:log4j-core")
                                .build(),
                        "< 2.15.0",
                        "2.15.0",
                        List.of("jndiLookup"))))
                .cvssSeverities(new GitHubCvssSeverities(
                        new GitHubCvssScore("CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:H/A:H", 10.0),
                        new GitHubCvssScore("CVSS:4.0/AV:N/AC:L/AT:N/PR:N/UI:N/VC:H/VI:H/VA:H/SC:N/SI:N/SA:N", 8.5)))
                .epss(new GitHubSecurityAdvisoryEpss(12.5, 87.3))
                .cwes(List.of(new GitHubAdvisoryCwe("CWE-502", "Deserialization of Untrusted Data")))
                .credits(List.of(new GitHubGlobalAdvisoryCredit(
                        UsersCatalogFixtures.octocat(), GitHubSecurityAdvisoryCreditType.FINDER)))
                .build();
    }

    static GitHubRepositoryAdvisory repositoryAdvisory() {
        final GitHubSimpleUser octocat = UsersCatalogFixtures.octocat();
        return new GitHubRepositoryAdvisory(
                "GHSA-49wp-qq6x-g2rf",
                "CVE-2022-12345",
                "https://api.github.com/repos/octocat/hello-world/security-advisories/GHSA-49wp-qq6x-g2rf",
                "https://github.com/octocat/hello-world/security/advisories/GHSA-49wp-qq6x-g2rf",
                "Example repository advisory",
                "Detailed description of the repository advisory.",
                GitHubRepositoryAdvisorySeverity.HIGH,
                octocat,
                octocat,
                List.of(
                        new GitHubAdvisoryIdentifier(GitHubAdvisoryIdentifierType.GHSA, "GHSA-49wp-qq6x-g2rf"),
                        new GitHubAdvisoryIdentifier(GitHubAdvisoryIdentifierType.CVE, "CVE-2022-12345")),
                GitHubRepositoryAdvisoryState.PUBLISHED,
                Instant.parse("2022-01-01T00:00:00Z"),
                Instant.parse("2022-02-01T00:00:00Z"),
                Instant.parse("2022-02-01T00:00:00Z"),
                Instant.parse("2022-03-01T00:00:00Z"),
                Instant.parse("2022-04-01T00:00:00Z"),
                new GitHubRepositoryAdvisorySubmission(true),
                List.of(new GitHubRepositoryAdvisoryVulnerability(
                        GitHubAdvisoryPackageBuilder.builder()
                                .ecosystem(GitHubSecurityAdvisoryEcosystem.NPM)
                                .name("a-package")
                                .build(),
                        "< 1.0.0",
                        "1.0.0",
                        List.of("important_function"))),
                new GitHubCvssSeverities(
                        new GitHubCvssScore("CVSS:3.1/AV:N/AC:L/PR:N/UI:N/S:U/C:H/I:N/A:N", 7.5),
                        new GitHubCvssScore("CVSS:4.0/AV:N/AC:L/AT:N/PR:N/UI:N/VC:H/VI:H/VA:H/SC:N/SI:N/SA:N", 8.5)),
                List.of(new GitHubAdvisoryCwe(
                        "CWE-79",
                        "Improper Neutralization of Input During Web Page Generation ('Cross-site Scripting')")),
                List.of("CWE-79"),
                List.of(new GitHubRepositoryAdvisoryCreditSummary("octocat", GitHubSecurityAdvisoryCreditType.FINDER)),
                List.of(new GitHubRepositoryAdvisoryCredit(
                        octocat,
                        GitHubSecurityAdvisoryCreditType.FINDER,
                        GitHubRepositoryAdvisoryCreditState.ACCEPTED)),
                List.of(octocat),
                List.of(collaboratingTeam()),
                privateFork());
    }

    private static GitHubSimpleRepository privateFork() {
        return new GitHubSimpleRepository(
                987_654_321L,
                "R_kgDOOprivatefork",
                "GHSA-49wp-qq6x-g2rf",
                "octocat/GHSA-49wp-qq6x-g2rf",
                UsersCatalogFixtures.octocat(),
                true,
                "https://github.com/octocat/GHSA-49wp-qq6x-g2rf",
                "Private fork",
                true,
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/{archive_format}{/ref}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/assignees{/user}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/git/blobs{/sha}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/branches{/branch}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/collaborators{/collaborator}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/comments{/number}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/commits{/sha}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/compare/{base}...{head}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/contents/{+path}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/contributors",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/deployments",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/downloads",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/events",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/forks",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/git/commits{/sha}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/git/refs{/sha}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/git/tags{/sha}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/issues/comments{/number}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/issues/events{/number}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/issues{/number}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/keys{/key_id}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/labels{/name}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/languages",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/merges",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/milestones{/number}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/notifications{?since,all,participating}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/pulls{/number}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/releases{/id}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/stargazers",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/statuses/{sha}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/subscribers",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/subscription",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/tags",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/teams",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/git/trees{/sha}",
                "https://api.github.com/repos/octocat/GHSA-49wp-qq6x-g2rf/hooks");
    }
}
