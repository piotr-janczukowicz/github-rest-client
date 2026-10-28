package io.github.piotr_janczukowicz.github;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubAdvisoryPackage;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubAdvisoryPackageBuilder;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubCreatePrivateVulnerabilityReportPayload;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubCreatePrivateVulnerabilityReportPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubCreateRepositoryAdvisoryPayload;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubCreateRepositoryAdvisoryPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubGlobalAdvisoriesSort;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubGlobalAdvisoryType;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubListGlobalAdvisoriesFilter;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubListGlobalAdvisoriesFilterBuilder;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubListRepositoryAdvisoriesFilter;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubListRepositoryAdvisoriesFilterBuilder;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubRepositoryAdvisoriesFilterState;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubRepositoryAdvisoriesSort;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubRepositoryAdvisoryCreditPayload;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubRepositoryAdvisoryCreditPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubRepositoryAdvisoryPackagePayload;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubRepositoryAdvisoryPackagePayloadBuilder;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubRepositoryAdvisorySeverity;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubRepositoryAdvisoryUpdateState;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubRepositoryAdvisoryVulnerabilityPayload;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubRepositoryAdvisoryVulnerabilityPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubSecurityAdvisoryCreditType;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubSecurityAdvisoryEcosystem;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubSecurityAdvisorySeverity;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubUpdateRepositoryAdvisoryPayload;
import io.github.piotr_janczukowicz.github.api.securityadvisories.GitHubUpdateRepositoryAdvisoryPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.teams.GitHubCreateTeamPayload;
import io.github.piotr_janczukowicz.github.api.teams.GitHubCreateTeamPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamLegacyPermission;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamNotificationSetting;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamPrivacy;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeamRole;
import io.github.piotr_janczukowicz.github.api.teams.GitHubUpdateTeamPayload;
import io.github.piotr_janczukowicz.github.api.teams.GitHubUpdateTeamPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.teams.GitHubUpsertTeamMembershipPayload;
import io.github.piotr_janczukowicz.github.api.teams.GitHubUpsertTeamMembershipPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.teams.GitHubUpsertTeamRepositoryPermissionPayload;
import io.github.piotr_janczukowicz.github.api.teams.GitHubUpsertTeamRepositoryPermissionPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubCreateGpgKeyPayload;
import io.github.piotr_janczukowicz.github.api.users.GitHubCreateGpgKeyPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubCreatePublicSshKeyPayload;
import io.github.piotr_janczukowicz.github.api.users.GitHubCreatePublicSshKeyPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubCreateSshSigningKeyPayload;
import io.github.piotr_janczukowicz.github.api.users.GitHubCreateSshSigningKeyPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubDeleteAttestationsBulkPayload;
import io.github.piotr_janczukowicz.github.api.users.GitHubDeleteAttestationsBulkPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubEmailVisibility;
import io.github.piotr_janczukowicz.github.api.users.GitHubListAttestationsBulkPayload;
import io.github.piotr_janczukowicz.github.api.users.GitHubListAttestationsBulkPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubSetPrimaryEmailVisibilityPayload;
import io.github.piotr_janczukowicz.github.api.users.GitHubSetPrimaryEmailVisibilityPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubUpdateAuthenticatedUserPayload;
import io.github.piotr_janczukowicz.github.api.users.GitHubUpdateAuthenticatedUserPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubUpdateEmailsPayload;
import io.github.piotr_janczukowicz.github.api.users.GitHubUpdateEmailsPayloadBuilder;
import io.github.piotr_janczukowicz.github.api.users.GitHubUpdateSocialAccountsPayload;
import io.github.piotr_janczukowicz.github.api.users.GitHubUpdateSocialAccountsPayloadBuilder;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRecordBuilderTest {

    @Test
    void empty_shouldEqualBuilderBuild_whenListGlobalAdvisoriesFilter() {
        assertThat(GitHubListGlobalAdvisoriesFilter.empty())
                .isEqualTo(GitHubListGlobalAdvisoriesFilterBuilder.builder().build());
    }

    @Test
    void empty_shouldEqualBuilderBuild_whenListRepositoryAdvisoriesFilter() {
        assertThat(GitHubListRepositoryAdvisoriesFilter.empty())
                .isEqualTo(GitHubListRepositoryAdvisoriesFilterBuilder.builder().build());
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenCreateTeamPayload() {
        final List<String> maintainers = List.of("octocat");
        final List<String> repoNames = List.of("octocat/Hello-World");
        final GitHubCreateTeamPayload expected = new GitHubCreateTeamPayload(
                "justice-league",
                "A great team.",
                maintainers,
                repoNames,
                GitHubTeamPrivacy.CLOSED,
                GitHubTeamNotificationSetting.NOTIFICATIONS_ENABLED,
                42L,
                "justice-league-parent",
                null);

        final GitHubCreateTeamPayload built = GitHubCreateTeamPayloadBuilder.builder()
                .name("justice-league")
                .description("A great team.")
                .maintainers(maintainers)
                .repoNames(repoNames)
                .privacy(GitHubTeamPrivacy.CLOSED)
                .notificationSetting(GitHubTeamNotificationSetting.NOTIFICATIONS_ENABLED)
                .parentTeamId(42L)
                .parentTeamSlug("justice-league-parent")
                .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenUpdateTeamPayload() {
        final GitHubUpdateTeamPayload built = GitHubUpdateTeamPayloadBuilder.builder()
                .name("new-name")
                .description("new description")
                .privacy(GitHubTeamPrivacy.SECRET)
                .notificationSetting(GitHubTeamNotificationSetting.NOTIFICATIONS_DISABLED)
                .permission(GitHubTeamLegacyPermission.ADMIN)
                .parentTeamId(OptionalNull.ofNull())
                .parentTeamSlug(OptionalNull.of("parent"))
                .build();

        assertSoftly(softly -> {
            softly.assertThat(built.name()).isEqualTo("new-name");
            softly.assertThat(built.description()).isEqualTo("new description");
            softly.assertThat(built.privacy()).isEqualTo(GitHubTeamPrivacy.SECRET);
            softly.assertThat(built.notificationSetting())
                    .isEqualTo(GitHubTeamNotificationSetting.NOTIFICATIONS_DISABLED);
            softly.assertThat(built.permission()).isEqualTo(GitHubTeamLegacyPermission.ADMIN);
            softly.assertThat(built.parentTeamId()).isNotNull();
            softly.assertThat(built.parentTeamId().value()).isNull();
            softly.assertThat(built.parentTeamSlug()).isNotNull();
            softly.assertThat(built.parentTeamSlug().value()).isEqualTo("parent");
        });
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenUpsertTeamMembershipPayload() {
        final GitHubUpsertTeamMembershipPayload expected =
                new GitHubUpsertTeamMembershipPayload(GitHubTeamRole.MAINTAINER);

        final GitHubUpsertTeamMembershipPayload built = GitHubUpsertTeamMembershipPayloadBuilder.builder()
                .role(GitHubTeamRole.MAINTAINER)
                .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenUpsertTeamRepositoryPermissionPayload() {
        final GitHubUpsertTeamRepositoryPermissionPayload expected =
                new GitHubUpsertTeamRepositoryPermissionPayload("admin");

        final GitHubUpsertTeamRepositoryPermissionPayload built =
                GitHubUpsertTeamRepositoryPermissionPayloadBuilder.builder()
                        .permission("admin")
                        .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenUpdateAuthenticatedUserPayload() {
        final GitHubUpdateAuthenticatedUserPayload built = GitHubUpdateAuthenticatedUserPayloadBuilder.builder()
                .name("Mona Lisa")
                .email("mona@example.com")
                .blog("https://example.com")
                .twitterUsername(OptionalNull.of("mona"))
                .company("GitHub")
                .location("San Francisco")
                .hireable(true)
                .bio("There once was...")
                .build();

        assertSoftly(softly -> {
            softly.assertThat(built.name()).isEqualTo("Mona Lisa");
            softly.assertThat(built.email()).isEqualTo("mona@example.com");
            softly.assertThat(built.blog()).isEqualTo("https://example.com");
            softly.assertThat(built.twitterUsername()).isNotNull();
            softly.assertThat(built.twitterUsername().value()).isEqualTo("mona");
            softly.assertThat(built.company()).isEqualTo("GitHub");
            softly.assertThat(built.location()).isEqualTo("San Francisco");
            softly.assertThat(built.hireable()).isTrue();
            softly.assertThat(built.bio()).isEqualTo("There once was...");
        });
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenUpdateEmailsPayload() {
        final List<String> emails = List.of("user@example.com");
        final GitHubUpdateEmailsPayload expected = new GitHubUpdateEmailsPayload(emails);

        final GitHubUpdateEmailsPayload built =
                GitHubUpdateEmailsPayloadBuilder.builder().emails(emails).build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenUpdateSocialAccountsPayload() {
        final List<String> accountUrls = List.of("https://twitter.com/octocat");
        final GitHubUpdateSocialAccountsPayload expected = new GitHubUpdateSocialAccountsPayload(accountUrls);

        final GitHubUpdateSocialAccountsPayload built = GitHubUpdateSocialAccountsPayloadBuilder.builder()
                .accountUrls(accountUrls)
                .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenSetPrimaryEmailVisibilityPayload() {
        final GitHubSetPrimaryEmailVisibilityPayload expected =
                new GitHubSetPrimaryEmailVisibilityPayload(GitHubEmailVisibility.PUBLIC);

        final GitHubSetPrimaryEmailVisibilityPayload built = GitHubSetPrimaryEmailVisibilityPayloadBuilder.builder()
                .visibility(GitHubEmailVisibility.PUBLIC)
                .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenCreatePublicSshKeyPayload() {
        final GitHubCreatePublicSshKeyPayload expected =
                new GitHubCreatePublicSshKeyPayload("laptop", "ssh-rsa AAA...");

        final GitHubCreatePublicSshKeyPayload built = GitHubCreatePublicSshKeyPayloadBuilder.builder()
                .title("laptop")
                .key("ssh-rsa AAA...")
                .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenCreateSshSigningKeyPayload() {
        final GitHubCreateSshSigningKeyPayload expected =
                new GitHubCreateSshSigningKeyPayload("signing", "ssh-ed25519 AAA...");

        final GitHubCreateSshSigningKeyPayload built = GitHubCreateSshSigningKeyPayloadBuilder.builder()
                .title("signing")
                .key("ssh-ed25519 AAA...")
                .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenCreateGpgKeyPayload() {
        final GitHubCreateGpgKeyPayload expected =
                new GitHubCreateGpgKeyPayload("laptop", "-----BEGIN PGP PUBLIC KEY BLOCK-----");

        final GitHubCreateGpgKeyPayload built = GitHubCreateGpgKeyPayloadBuilder.builder()
                .name("laptop")
                .armoredPublicKey("-----BEGIN PGP PUBLIC KEY BLOCK-----")
                .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenListAttestationsBulkPayload() {
        final List<String> digests = List.of("sha256:abc");
        final GitHubListAttestationsBulkPayload expected = new GitHubListAttestationsBulkPayload(digests, "provenance");

        final GitHubListAttestationsBulkPayload built = GitHubListAttestationsBulkPayloadBuilder.builder()
                .subjectDigests(digests)
                .predicateType("provenance")
                .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenDeleteAttestationsBulkPayload() {
        final List<String> digests = List.of("sha256:abc");
        final GitHubDeleteAttestationsBulkPayload expected =
                GitHubDeleteAttestationsBulkPayload.bySubjectDigests(digests);

        final GitHubDeleteAttestationsBulkPayload built = GitHubDeleteAttestationsBulkPayloadBuilder.builder()
                .subjectDigests(digests)
                .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenCreateRepositoryAdvisoryPayload() {
        final GitHubRepositoryAdvisoryVulnerabilityPayload vulnerability = vulnerability();
        final List<String> cweIds = List.of("CWE-79");
        final List<GitHubRepositoryAdvisoryCreditPayload> credits = List.of(
                new GitHubRepositoryAdvisoryCreditPayload("octocat", GitHubSecurityAdvisoryCreditType.REPORTER));

        final GitHubCreateRepositoryAdvisoryPayload expected = new GitHubCreateRepositoryAdvisoryPayload(
                "summary",
                "description",
                List.of(vulnerability),
                "CVE-2024-0001",
                cweIds,
                credits,
                GitHubRepositoryAdvisorySeverity.HIGH,
                null,
                true);

        final GitHubCreateRepositoryAdvisoryPayload built = GitHubCreateRepositoryAdvisoryPayloadBuilder.builder()
                .summary("summary")
                .description("description")
                .vulnerabilities(List.of(vulnerability))
                .cveId("CVE-2024-0001")
                .cweIds(cweIds)
                .credits(credits)
                .severity(GitHubRepositoryAdvisorySeverity.HIGH)
                .startPrivateFork(true)
                .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenUpdateRepositoryAdvisoryPayload() {
        final List<String> collaboratingUsers = List.of("octocat");
        final GitHubUpdateRepositoryAdvisoryPayload expected = new GitHubUpdateRepositoryAdvisoryPayload(
                null,
                null,
                null,
                null,
                null,
                null,
                GitHubRepositoryAdvisorySeverity.CRITICAL,
                null,
                GitHubRepositoryAdvisoryUpdateState.PUBLISHED,
                collaboratingUsers,
                null);

        final GitHubUpdateRepositoryAdvisoryPayload built = GitHubUpdateRepositoryAdvisoryPayloadBuilder.builder()
                .severity(GitHubRepositoryAdvisorySeverity.CRITICAL)
                .state(GitHubRepositoryAdvisoryUpdateState.PUBLISHED)
                .collaboratingUsers(collaboratingUsers)
                .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenCreatePrivateVulnerabilityReportPayload() {
        final GitHubCreatePrivateVulnerabilityReportPayload expected =
                new GitHubCreatePrivateVulnerabilityReportPayload(
                        "summary", "description", null, null, GitHubRepositoryAdvisorySeverity.HIGH, null, null);

        final GitHubCreatePrivateVulnerabilityReportPayload built =
                GitHubCreatePrivateVulnerabilityReportPayloadBuilder.builder()
                        .summary("summary")
                        .description("description")
                        .severity(GitHubRepositoryAdvisorySeverity.HIGH)
                        .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenListGlobalAdvisoriesFilter() {
        final List<String> cwes = List.of("79", "284");
        final List<String> affects = List.of("pkg1", "pkg2@1.0.0");

        final GitHubListGlobalAdvisoriesFilter expected = new GitHubListGlobalAdvisoriesFilter(
                "GHSA-xxxx",
                GitHubGlobalAdvisoryType.REVIEWED,
                "CVE-2024-0001",
                GitHubSecurityAdvisoryEcosystem.NPM,
                GitHubSecurityAdvisorySeverity.HIGH,
                cwes,
                true,
                affects,
                "2024-01-01",
                "2024-02-01",
                "2024-03-01",
                ">0.5",
                "<0.9",
                GitHubDirection.DESC,
                GitHubGlobalAdvisoriesSort.UPDATED);

        final GitHubListGlobalAdvisoriesFilter built = GitHubListGlobalAdvisoriesFilterBuilder.builder()
                .ghsaId("GHSA-xxxx")
                .type(GitHubGlobalAdvisoryType.REVIEWED)
                .cveId("CVE-2024-0001")
                .ecosystem(GitHubSecurityAdvisoryEcosystem.NPM)
                .severity(GitHubSecurityAdvisorySeverity.HIGH)
                .cwes(cwes)
                .isWithdrawn(true)
                .affects(affects)
                .published("2024-01-01")
                .updated("2024-02-01")
                .modified("2024-03-01")
                .epssPercentage(">0.5")
                .epssPercentile("<0.9")
                .direction(GitHubDirection.DESC)
                .sort(GitHubGlobalAdvisoriesSort.UPDATED)
                .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenListRepositoryAdvisoriesFilter() {
        final GitHubListRepositoryAdvisoriesFilter expected = new GitHubListRepositoryAdvisoriesFilter(
                GitHubDirection.ASC,
                GitHubRepositoryAdvisoriesSort.CREATED,
                GitHubRepositoryAdvisoriesFilterState.DRAFT);

        final GitHubListRepositoryAdvisoriesFilter built = GitHubListRepositoryAdvisoriesFilterBuilder.builder()
                .direction(GitHubDirection.ASC)
                .sort(GitHubRepositoryAdvisoriesSort.CREATED)
                .state(GitHubRepositoryAdvisoriesFilterState.DRAFT)
                .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenGitHubRepositoryAdvisoryVulnerabilityPayload() {
        final GitHubRepositoryAdvisoryVulnerabilityPayload expected = vulnerability();

        final GitHubRepositoryAdvisoryVulnerabilityPayload built =
                GitHubRepositoryAdvisoryVulnerabilityPayloadBuilder.builder()
                        .advisoryPackage(new GitHubRepositoryAdvisoryPackagePayload(
                                GitHubSecurityAdvisoryEcosystem.NPM, "a-package"))
                        .vulnerableVersionRange("< 1.0.0")
                        .patchedVersions("1.0.0")
                        .vulnerableFunctions(List.of("important_function"))
                        .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenGitHubRepositoryAdvisoryCreditPayload() {
        final GitHubRepositoryAdvisoryCreditPayload expected =
                new GitHubRepositoryAdvisoryCreditPayload("octocat", GitHubSecurityAdvisoryCreditType.REPORTER);

        final GitHubRepositoryAdvisoryCreditPayload built = GitHubRepositoryAdvisoryCreditPayloadBuilder.builder()
                .login("octocat")
                .type(GitHubSecurityAdvisoryCreditType.REPORTER)
                .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenGitHubRepositoryAdvisoryPackagePayload() {
        final GitHubRepositoryAdvisoryPackagePayload expected =
                new GitHubRepositoryAdvisoryPackagePayload(GitHubSecurityAdvisoryEcosystem.NPM, "a-package");

        final GitHubRepositoryAdvisoryPackagePayload built = GitHubRepositoryAdvisoryPackagePayloadBuilder.builder()
                .ecosystem(GitHubSecurityAdvisoryEcosystem.NPM)
                .name("a-package")
                .build();

        assertThat(built).isEqualTo(expected);
    }

    @Test
    void build_shouldEqualCanonicalConstructor_whenGitHubAdvisoryPackage() {
        final GitHubAdvisoryPackage expected =
                new GitHubAdvisoryPackage(GitHubSecurityAdvisoryEcosystem.NPM, "a-package");

        final GitHubAdvisoryPackage built = GitHubAdvisoryPackageBuilder.builder()
                .ecosystem(GitHubSecurityAdvisoryEcosystem.NPM)
                .name("a-package")
                .build();

        assertThat(built).isEqualTo(expected);
    }

    private static GitHubRepositoryAdvisoryVulnerabilityPayload vulnerability() {
        return new GitHubRepositoryAdvisoryVulnerabilityPayload(
                new GitHubRepositoryAdvisoryPackagePayload(GitHubSecurityAdvisoryEcosystem.NPM, "a-package"),
                "< 1.0.0",
                "1.0.0",
                List.of("important_function"));
    }
}
