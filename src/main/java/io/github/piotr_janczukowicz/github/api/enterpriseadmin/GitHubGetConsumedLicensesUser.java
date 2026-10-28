package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.piotr_janczukowicz.annotation.RecordBuilder;
import io.github.piotr_janczukowicz.github.api.Flavors;
import io.github.piotr_janczukowicz.github.client.GitHubFlavor;
import io.github.piotr_janczukowicz.openapi.model.CatalogSchema;
import java.util.List;
import org.jspecify.annotations.Nullable;

/**
 * A user consuming a license of an enterprise.
 *
 * @param githubComLogin Login on GitHub.com
 * @param githubComName Name on GitHub.com
 * @param enterpriseServerUserIds Enterprise Server user identifiers
 * @param githubComUser Whether the user is a GitHub.com user
 * @param enterpriseServerUser Whether the user is an Enterprise Server user
 * @param visualStudioSubscriptionUser Whether the user has a Visual Studio subscription
 * @param licenseType License type of the user
 * @param githubComProfile Profile URL on GitHub.com
 * @param githubComMemberRoles Organization member roles on GitHub.com
 * @param githubComEnterpriseRoles All enterprise roles for a user
 * @param githubComVerifiedDomainEmails Verified domain emails on GitHub.com
 * @param githubComSamlNameId SAML name identifier on GitHub.com
 * @param githubComOrgsWithPendingInvites Organizations with pending invitations
 * @param githubComTwoFactorAuth Whether two-factor authentication is enabled
 * @param enterpriseServerEmails Enterprise Server emails
 * @param visualStudioLicenseStatus Visual Studio license status
 * @param visualStudioSubscriptionEmail Visual Studio subscription email
 * @param totalUserAccounts Total number of user accounts
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@RecordBuilder
@CatalogSchema("components/schemas/get-consumed-licenses/properties/users/items")
@Flavors(GitHubFlavor.GHEC)
public record GitHubGetConsumedLicensesUser(
        @Nullable String githubComLogin,
        @Nullable String githubComName,
        @Nullable List<String> enterpriseServerUserIds,
        @Nullable Boolean githubComUser,
        @Nullable Boolean enterpriseServerUser,
        @Nullable Boolean visualStudioSubscriptionUser,
        @Nullable String licenseType,
        @Nullable String githubComProfile,
        @Nullable List<String> githubComMemberRoles,
        @Nullable List<String> githubComEnterpriseRoles,
        @Nullable List<String> githubComVerifiedDomainEmails,
        @Nullable String githubComSamlNameId,
        @Nullable List<String> githubComOrgsWithPendingInvites,
        @Nullable Boolean githubComTwoFactorAuth,
        @Nullable List<String> enterpriseServerEmails,
        @Nullable String visualStudioLicenseStatus,
        @Nullable String visualStudioSubscriptionEmail,
        @Nullable Integer totalUserAccounts) {}
