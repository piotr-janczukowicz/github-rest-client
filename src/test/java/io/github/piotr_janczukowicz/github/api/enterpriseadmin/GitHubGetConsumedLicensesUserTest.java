package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetConsumedLicensesUserTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubGetConsumedLicensesUser value = EnterpriseAdminCatalogFixtures.getConsumedLicensesUser();

        assertThat(value.githubComLogin()).isEqualTo("sample-github_com_login");
        assertThat(value.githubComName()).isEqualTo("sample-github_com_name");
        assertThat(value.enterpriseServerUserIds()).isNotEmpty();
        assertThat(value.githubComUser()).isTrue();
        assertThat(value.enterpriseServerUser()).isTrue();
        assertThat(value.visualStudioSubscriptionUser()).isTrue();
        assertThat(value.licenseType()).isEqualTo("sample-license_type");
        assertThat(value.githubComProfile()).isEqualTo("sample-github_com_profile");
        assertThat(value.githubComMemberRoles()).isNotEmpty();
        assertThat(value.githubComEnterpriseRoles()).isNotEmpty();
        assertThat(value.githubComVerifiedDomainEmails()).isNotEmpty();
        assertThat(value.githubComSamlNameId()).isEqualTo("sample-github_com_saml_name_id");
        assertThat(value.githubComOrgsWithPendingInvites()).isNotEmpty();
        assertThat(value.githubComTwoFactorAuth()).isTrue();
        assertThat(value.enterpriseServerEmails()).isNotEmpty();
        assertThat(value.visualStudioLicenseStatus()).isEqualTo("sample-visual_studio_license_status");
        assertThat(value.visualStudioSubscriptionEmail()).isEqualTo("sample-visual_studio_subscription_email");
        assertThat(value.totalUserAccounts()).isEqualTo(1);
    }

    @Test
    void constructor_shouldLeaveEnterpriseServerUserIdsNull_whenNull() {
        final GitHubGetConsumedLicensesUser value = GitHubGetConsumedLicensesUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.getConsumedLicensesUser())
                .enterpriseServerUserIds(null)
                .build();

        assertThat(value.enterpriseServerUserIds()).isNull();
    }

    @Test
    void constructor_shouldLeaveGithubComMemberRolesNull_whenNull() {
        final GitHubGetConsumedLicensesUser value = GitHubGetConsumedLicensesUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.getConsumedLicensesUser())
                .githubComMemberRoles(null)
                .build();

        assertThat(value.githubComMemberRoles()).isNull();
    }

    @Test
    void constructor_shouldLeaveGithubComEnterpriseRolesNull_whenNull() {
        final GitHubGetConsumedLicensesUser value = GitHubGetConsumedLicensesUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.getConsumedLicensesUser())
                .githubComEnterpriseRoles(null)
                .build();

        assertThat(value.githubComEnterpriseRoles()).isNull();
    }

    @Test
    void constructor_shouldLeaveGithubComVerifiedDomainEmailsNull_whenNull() {
        final GitHubGetConsumedLicensesUser value = GitHubGetConsumedLicensesUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.getConsumedLicensesUser())
                .githubComVerifiedDomainEmails(null)
                .build();

        assertThat(value.githubComVerifiedDomainEmails()).isNull();
    }

    @Test
    void constructor_shouldLeaveGithubComOrgsWithPendingInvitesNull_whenNull() {
        final GitHubGetConsumedLicensesUser value = GitHubGetConsumedLicensesUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.getConsumedLicensesUser())
                .githubComOrgsWithPendingInvites(null)
                .build();

        assertThat(value.githubComOrgsWithPendingInvites()).isNull();
    }

    @Test
    void constructor_shouldLeaveEnterpriseServerEmailsNull_whenNull() {
        final GitHubGetConsumedLicensesUser value = GitHubGetConsumedLicensesUserBuilder.builder(
                        EnterpriseAdminCatalogFixtures.getConsumedLicensesUser())
                .enterpriseServerEmails(null)
                .build();

        assertThat(value.enterpriseServerEmails()).isNull();
    }
}
