package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.github.api.actions.ActionsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.organizations.OrganizationsCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubCustomPropertyValue;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryRuleEnforcement;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRuleSuiteTimePeriod;
import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.teams.TeamCatalogFixtures;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserViewType;
import java.time.Instant;
import java.util.List;
import java.util.Map;

final class EnterpriseAdminCatalogFixtures {

    private EnterpriseAdminCatalogFixtures() {}

    static GitHubEnterpriseRole enterpriseRole() {
        return GitHubEnterpriseRoleBuilder.builder()
                .id(1L)
                .name("Octo custom role")
                .description("Manages the octo enterprise.")
                .source(GitHubEnterpriseRoleSource.ENTERPRISE)
                .permissions(List.of("read:enterprise", "write:enterprise"))
                .enterprise(OrganizationsCatalogFixtures.enterprise())
                .createdAt(Instant.parse("2023-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2023-01-01T12:00:00Z"))
                .build();
    }

    static GitHubEnterpriseRoleList enterpriseRoleList() {
        return GitHubEnterpriseRoleListBuilder.builder()
                .totalCount(1)
                .roles(List.of(enterpriseRole()))
                .build();
    }

    static GitHubEnterpriseUserRoleAssignment enterpriseUserRoleAssignment() {
        return GitHubEnterpriseUserRoleAssignmentBuilder.builder()
                .name("Mona Lisa")
                .email("monalisa@github.example")
                .login("monalisa")
                .id(2L)
                .nodeId("MDQ6VXNlcjI=")
                .avatarUrl("https://github.com/images/error/monalisa_happy.gif")
                .gravatarId("41d064eb2195891e12d0413f63227ea7")
                .url("https://api.github.com/users/monalisa")
                .htmlUrl("https://github.com/monalisa")
                .followersUrl("https://api.github.com/users/monalisa/followers")
                .followingUrl("https://api.github.com/users/monalisa/following{/other_user}")
                .gistsUrl("https://api.github.com/users/monalisa/gists{/gist_id}")
                .starredUrl("https://api.github.com/users/monalisa/starred{/owner}{/repo}")
                .subscriptionsUrl("https://api.github.com/users/monalisa/subscriptions")
                .organizationsUrl("https://api.github.com/users/monalisa/orgs")
                .reposUrl("https://api.github.com/users/monalisa/repos")
                .eventsUrl("https://api.github.com/users/monalisa/events{/privacy}")
                .receivedEventsUrl("https://api.github.com/users/monalisa/received_events")
                .type("User")
                .siteAdmin(false)
                .starredAt("2023-01-01T00:00:00Z")
                .userViewType(GitHubUserViewType.PUBLIC)
                .assignment(GitHubEnterpriseRoleAssignment.DIRECT)
                .inheritedFrom(List.of(TeamCatalogFixtures.enterpriseTeam()))
                .build();
    }

    static GitHubScimEnterpriseGroup scimEnterpriseGroup() {
        return GitHubScimEnterpriseGroupBuilder.builder()
                .schemas(List.of(GitHubScimEnterpriseGroupSchemaUrn.GROUP))
                .externalId("8aa1a0c0-c4c3-4bc0-b4a5-2ef676900159")
                .displayName("Engineering")
                .members(List.of(scimEnterpriseGroupMember()))
                .build();
    }

    static GitHubScimEnterpriseGroupMember scimEnterpriseGroupMember() {
        return GitHubScimEnterpriseGroupMemberBuilder.builder()
                .value("879db59-3bdf-4490-ad68-ab880a2694745")
                .displayName("Mona Lisa")
                .build();
    }

    static GitHubGroupResponse groupResponse() {
        return GitHubGroupResponseBuilder.builder()
                .schemas(List.of(GitHubGroupResponseSchemaUrn.GROUP, GitHubGroupResponseSchemaUrn.LIST_RESPONSE))
                .externalId("8aa1a0c0-c4c3-4bc0-b4a5-2ef676900159")
                .displayName("Engineering")
                .members(List.of(groupResponseMember()))
                .build();
    }

    static GitHubGroupResponseMember groupResponseMember() {
        return GitHubGroupResponseMemberBuilder.builder()
                .value("879db59-3bdf-4490-ad68-ab880a2694745")
                .ref("https://api.github.com/scim/v2/Users/879db59-3bdf-4490-ad68-ab880a2694745")
                .display("Mona Lisa")
                .build();
    }

    static GitHubScimEnterpriseGroupResponse scimEnterpriseGroupResponse() {
        return GitHubScimEnterpriseGroupResponseBuilder.builder()
                .schemas(List.of(GitHubGroupResponseSchemaUrn.GROUP, GitHubGroupResponseSchemaUrn.LIST_RESPONSE))
                .externalId("8aa1a0c0-c4c3-4bc0-b4a5-2ef676900159")
                .displayName("Engineering")
                .members(List.of(groupResponseMember()))
                .id("24b28bbb-5fc4-4686-a153-a020debb1155")
                .meta(meta())
                .build();
    }

    static GitHubScimEnterpriseGroupList scimEnterpriseGroupList() {
        return GitHubScimEnterpriseGroupListBuilder.builder()
                .schemas(List.of(GitHubScimEnterpriseGroupListSchemaUrn.LIST_RESPONSE))
                .totalResults(1)
                .resources(List.of(scimEnterpriseGroupResponse()))
                .startIndex(1)
                .itemsPerPage(20)
                .build();
    }

    static GitHubMeta meta() {
        return GitHubMetaBuilder.builder()
                .resourceType(GitHubMetaResourceType.GROUP)
                .created("2023-01-01T00:00:00Z")
                .lastModified("2023-01-01T12:00:00Z")
                .location("https://api.github.com/scim/v2/Groups/24b28bbb-5fc4-4686-a153-a020debb1155")
                .build();
    }

    static GitHubScimEnterpriseUser scimEnterpriseUser() {
        return GitHubScimEnterpriseUserBuilder.builder()
                .schemas(List.of(GitHubScimEnterpriseUserSchemaUrn.USER))
                .externalId("a7d0f98c-e026-40e8-9a1e-4cb1a3dd8e4d")
                .active(true)
                .userName("monalisa@github.example")
                .name(userName())
                .displayName("Mona Lisa")
                .emails(List.of(userEmail()))
                .roles(List.of(userRole()))
                .build();
    }

    static GitHubUserName userName() {
        return GitHubUserNameBuilder.builder()
                .formatted("Mona Lisa Octocat")
                .familyName("Octocat")
                .givenName("Mona")
                .middleName("Lisa")
                .build();
    }

    static GitHubUserNameResponse userNameResponse() {
        return GitHubUserNameResponseBuilder.builder()
                .formatted("Mona Lisa Octocat")
                .familyName("Octocat")
                .givenName("Mona")
                .middleName("Lisa")
                .build();
    }

    static GitHubUserEmail userEmail() {
        return GitHubUserEmailBuilder.builder()
                .value("monalisa@github.example")
                .type("work")
                .primary(true)
                .build();
    }

    static GitHubUserEmailResponse userEmailResponse() {
        return GitHubUserEmailResponseBuilder.builder()
                .value("monalisa@github.example")
                .type("work")
                .primary(true)
                .build();
    }

    static GitHubUserRole userRole() {
        return GitHubUserRoleBuilder.builder()
                .display("User")
                .type("user")
                .value(GitHubUserRoleValue.USER)
                .primary(true)
                .build();
    }

    static GitHubUserResponse userResponse() {
        return GitHubUserResponseBuilder.builder()
                .schemas(List.of(GitHubUserResponseSchemaUrn.USER))
                .externalId("a7d0f98c-e026-40e8-9a1e-4cb1a3dd8e4d")
                .active(true)
                .userName("monalisa@github.example")
                .name(userNameResponse())
                .displayName("Mona Lisa")
                .emails(List.of(userEmailResponse()))
                .roles(List.of(userRole()))
                .build();
    }

    static GitHubScimEnterpriseUserResponse scimEnterpriseUserResponse() {
        return GitHubScimEnterpriseUserResponseBuilder.builder()
                .schemas(List.of(GitHubUserResponseSchemaUrn.USER))
                .externalId("a7d0f98c-e026-40e8-9a1e-4cb1a3dd8e4d")
                .active(true)
                .userName("monalisa@github.example")
                .name(userNameResponse())
                .displayName("Mona Lisa")
                .emails(List.of(userEmailResponse()))
                .roles(List.of(userRole()))
                .id("2819c223-7f76-453a-919d-413861904646")
                .groups(List.of(scimEnterpriseUserGroup()))
                .meta(meta())
                .build();
    }

    static GitHubScimEnterpriseUserGroup scimEnterpriseUserGroup() {
        return GitHubScimEnterpriseUserGroupBuilder.builder()
                .value("24b28bbb-5fc4-4686-a153-a020debb1155")
                .ref("https://api.github.com/scim/v2/Groups/24b28bbb-5fc4-4686-a153-a020debb1155")
                .display("Engineering")
                .build();
    }

    static GitHubScimEnterpriseUserList scimEnterpriseUserList() {
        return GitHubScimEnterpriseUserListBuilder.builder()
                .schemas(List.of(GitHubScimEnterpriseUserListSchemaUrn.LIST_RESPONSE))
                .totalResults(1)
                .resources(List.of(scimEnterpriseUserResponse()))
                .startIndex(1)
                .itemsPerPage(20)
                .build();
    }

    static GitHubPatchSchema patchSchema() {
        return GitHubPatchSchemaBuilder.builder()
                .operations(List.of(patchSchemaOperation()))
                .schemas(List.of(GitHubPatchOpSchemaUrn.PATCH_OP))
                .build();
    }

    static GitHubPatchSchemaOperation patchSchemaOperation() {
        return GitHubPatchSchemaOperationBuilder.builder()
                .op(GitHubPatchSchemaOperationOp.REPLACE)
                .path("name.givenName")
                .value("Mona")
                .build();
    }

    static GitHubListProvisionedGroupsEnterpriseFilter listProvisionedGroupsEnterpriseFilter() {
        return GitHubListProvisionedGroupsEnterpriseFilterBuilder.builder()
                .filter("displayName eq \"Engineering\"")
                .excludedAttributes("members")
                .build();
    }

    static GitHubGetProvisioningInformationForEnterpriseGroupFilter getGroupProvisioningFilter() {
        return GitHubGetProvisioningInformationForEnterpriseGroupFilterBuilder.builder()
                .excludedAttributes("members")
                .build();
    }

    static GitHubListProvisionedIdentitiesEnterpriseFilter listProvisionedIdentitiesEnterpriseFilter() {
        return GitHubListProvisionedIdentitiesEnterpriseFilterBuilder.builder()
                .filter("userName eq \"Mona Lisa\"")
                .build();
    }

    static GitHubAccessibleRepository accessibleRepository() {
        return GitHubAccessibleRepositoryBuilder.builder()
                .id(1_296_269L)
                .name("Hello-World")
                .fullName("octocat/Hello-World")
                .build();
    }

    static GitHubInstallableOrganization installableOrganization() {
        return GitHubInstallableOrganizationBuilder.builder()
                .id(1L)
                .login("octo-org")
                .accessibleRepositoriesUrl(
                        "https://api.github.com/enterprises/octo-enterprise/apps/installable_organizations/octo-org/accessible_repositories")
                .build();
    }

    static GitHubEnterpriseOrganizationInstallation enterpriseOrganizationInstallation() {
        return GitHubEnterpriseOrganizationInstallationBuilder.builder()
                .id(1L)
                .appSlug("octo-app")
                .clientId("Iv1.8a61f9b3a7aba766")
                .repositorySelection(GitHubEnterpriseOrganizationInstallationRepositorySelection.SELECTED)
                .repositoriesUrl("https://api.github.com/organizations/octo-org/installations/1/repositories")
                .permissions(OrganizationsCatalogFixtures.appPermissions())
                .events(List.of("push", "pull_request"))
                .createdAt(Instant.parse("2023-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2023-01-01T12:00:00Z"))
                .build();
    }

    static GitHubCustomPropertyBase customPropertyBase() {
        return GitHubCustomPropertyBaseBuilder.builder()
                .propertyName("environment")
                .url("https://api.github.com/enterprises/octo-enterprise/properties/schema/environment")
                .sourceType(GitHubCustomPropertyBaseSourceType.ENTERPRISE)
                .valueType(GitHubCustomPropertyBaseValueType.SINGLE_SELECT)
                .required(true)
                .defaultValue(List.of("production"))
                .description("Deployment environment")
                .allowedValues(List.of("production", "staging"))
                .build();
    }

    static GitHubAuditLogStreamKey auditLogStreamKey() {
        return GitHubAuditLogStreamKeyBuilder.builder()
                .keyId("key-1")
                .key("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA")
                .build();
    }

    static GitHubGetAuditLogStreamConfig getAuditLogStreamConfig() {
        return GitHubGetAuditLogStreamConfigBuilder.builder()
                .id(1L)
                .streamType("Splunk")
                .streamDetails("splunk.example.com")
                .enabled(true)
                .createdAt(Instant.parse("2023-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2023-01-01T12:00:00Z"))
                .pausedAt(Instant.parse("2023-01-02T00:00:00Z"))
                .build();
    }

    static GitHubGetAuditLogStreamConfigsItem getAuditLogStreamConfigsItem() {
        return GitHubGetAuditLogStreamConfigsItemBuilder.builder()
                .id(1L)
                .streamType("Splunk")
                .streamDetails("splunk.example.com")
                .enabled(true)
                .createdAt(Instant.parse("2023-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2023-01-01T12:00:00Z"))
                .pausedAt(Instant.parse("2023-01-02T00:00:00Z"))
                .build();
    }

    static GitHubEnterpriseOrgPropertyValues enterpriseOrgPropertyValues() {
        return GitHubEnterpriseOrgPropertyValuesBuilder.builder()
                .organizationId(1L)
                .organizationLogin("octo-org")
                .properties(List.of(new GitHubCustomPropertyValue("environment", List.of("production"))))
                .build();
    }

    static GitHubCreateInstallationPayload createInstallationPayload() {
        return GitHubCreateInstallationPayloadBuilder.builder()
                .clientId("Iv1.8a61f9b3a7aba766")
                .repositorySelection(GitHubCreateInstallationRepositorySelection.SELECTED)
                .repositories(List.of("octo-repo"))
                .build();
    }

    static GitHubChangeRepositoryAccessSelectionPayload changeRepositoryAccessSelectionPayload() {
        return GitHubChangeRepositoryAccessSelectionPayloadBuilder.builder()
                .repositorySelection(GitHubChangeRepositoryAccessSelection.SELECTED)
                .repositories(List.of("octo-repo"))
                .build();
    }

    static GitHubGrantRepositoryAccessPayload grantRepositoryAccessPayload() {
        return new GitHubGrantRepositoryAccessPayload(List.of("octo-repo"));
    }

    static GitHubRemoveRepositoryAccessPayload removeRepositoryAccessPayload() {
        return new GitHubRemoveRepositoryAccessPayload(List.of("octo-repo"));
    }

    static GitHubCreateAuditLogStreamPayload createAuditLogStreamPayload() {
        return new GitHubCreateAuditLogStreamPayload(true, GitHubCreateAuditLogStreamType.SPLUNK, splunkConfig());
    }

    static GitHubUpdateAuditLogStreamPayload updateAuditLogStreamPayload() {
        return new GitHubUpdateAuditLogStreamPayload(true, GitHubUpdateAuditLogStreamType.DATADOG, datadogConfig());
    }

    static GitHubGetAuditLogFilter getAuditLogFilter() {
        return GitHubGetAuditLogFilterBuilder.builder()
                .phrase("action:org.create")
                .include(GitHubGetAuditLogInclude.WEB)
                .order(GitHubGetAuditLogOrder.DESC)
                .build();
    }

    static GitHubCreateOrUpdateEnterpriseDefinitionsPayload createOrUpdateEnterpriseDefinitionsPayload() {
        return new GitHubCreateOrUpdateEnterpriseDefinitionsPayload(
                List.of(OrganizationsCatalogFixtures.organizationCustomProperty()));
    }

    static GitHubCreateOrUpdateEnterpriseValuesPayload createOrUpdateEnterpriseValuesPayload() {
        return new GitHubCreateOrUpdateEnterpriseValuesPayload(
                List.of("octo-org"), List.of(new GitHubCustomPropertyValue("environment", List.of("production"))));
    }

    static GitHubAzureBlobConfig azureBlobConfig() {
        return GitHubAzureBlobConfigBuilder.builder()
                .keyId("key-1")
                .encryptedSasUrl("encrypted-sas-url")
                .container("audit-logs")
                .build();
    }

    static GitHubAzureHubConfig azureHubConfig() {
        return GitHubAzureHubConfigBuilder.builder()
                .name("octo-hub")
                .encryptedConnstring("encrypted-connstring")
                .keyId("key-1")
                .build();
    }

    static GitHubAmazonS3OidcConfig amazonS3OidcConfig() {
        return GitHubAmazonS3OidcConfigBuilder.builder()
                .bucket("octo-bucket")
                .region("us-east-1")
                .keyId("key-1")
                .authenticationType(GitHubAmazonS3OidcConfigAuthenticationType.OIDC)
                .arnRole("arn:aws:iam::123456789012:role/audit-logs")
                .build();
    }

    static GitHubAmazonS3AccessKeysConfig amazonS3AccessKeysConfig() {
        return GitHubAmazonS3AccessKeysConfigBuilder.builder()
                .bucket("octo-bucket")
                .region("us-east-1")
                .keyId("key-1")
                .authenticationType(GitHubAmazonS3AccessKeysConfigAuthenticationType.ACCESS_KEYS)
                .encryptedSecretKey("encrypted-secret-key")
                .encryptedAccessKeyId("encrypted-access-key-id")
                .build();
    }

    static GitHubSplunkConfig splunkConfig() {
        return GitHubSplunkConfigBuilder.builder()
                .domain("splunk.example.com")
                .port(8088)
                .keyId("key-1")
                .encryptedToken("encrypted-token")
                .sslVerify(true)
                .build();
    }

    static GitHubHecConfig hecConfig() {
        return GitHubHecConfigBuilder.builder()
                .domain("hec.example.com")
                .port(8088)
                .keyId("key-1")
                .encryptedToken("encrypted-token")
                .path("/services/collector")
                .sslVerify(true)
                .build();
    }

    static GitHubGoogleCloudConfig googleCloudConfig() {
        return GitHubGoogleCloudConfigBuilder.builder()
                .bucket("octo-bucket")
                .keyId("key-1")
                .encryptedJsonCredentials("encrypted-json-credentials")
                .build();
    }

    static GitHubDatadogConfig datadogConfig() {
        return GitHubDatadogConfigBuilder.builder()
                .encryptedToken("encrypted-token")
                .site(GitHubDatadogConfigSite.US)
                .keyId("key-1")
                .build();
    }

    static GitHubCreateAuditLogStreamPayload createAuditLogStreamPayload(
            final GitHubCreateAuditLogStreamType streamType,
            final GitHubCreateAuditLogStreamVendorSpecific vendorSpecific) {
        return new GitHubCreateAuditLogStreamPayload(true, streamType, vendorSpecific);
    }

    static GitHubUpdateAuditLogStreamPayload updateAuditLogStreamPayload(
            final GitHubUpdateAuditLogStreamType streamType,
            final GitHubUpdateAuditLogStreamVendorSpecific vendorSpecific) {
        return new GitHubUpdateAuditLogStreamPayload(true, streamType, vendorSpecific);
    }

    static GitHubUpdateAuditLogStreamEmptyVendorSpecific updateAuditLogStreamEmptyVendorSpecific() {
        return new GitHubUpdateAuditLogStreamEmptyVendorSpecific();
    }

    static GitHubGetConsumedLicenses getConsumedLicenses() {
        return GitHubGetConsumedLicensesBuilder.builder()
                .totalSeatsConsumed(1)
                .totalSeatsPurchased(1)
                .users(List.of(getConsumedLicensesUser()))
                .build();
    }

    static GitHubGetConsumedLicensesUser getConsumedLicensesUser() {
        return GitHubGetConsumedLicensesUserBuilder.builder()
                .githubComLogin("sample-github_com_login")
                .githubComName("sample-github_com_name")
                .enterpriseServerUserIds(List.of("sample-enterprise_server_user_ids"))
                .githubComUser(true)
                .enterpriseServerUser(true)
                .visualStudioSubscriptionUser(true)
                .licenseType("sample-license_type")
                .githubComProfile("sample-github_com_profile")
                .githubComMemberRoles(List.of("sample-github_com_member_roles"))
                .githubComEnterpriseRoles(List.of("sample-github_com_enterprise_roles"))
                .githubComVerifiedDomainEmails(List.of("sample-github_com_verified_domain_emails"))
                .githubComSamlNameId("sample-github_com_saml_name_id")
                .githubComOrgsWithPendingInvites(List.of("sample-github_com_orgs_with_pending_invites"))
                .githubComTwoFactorAuth(true)
                .enterpriseServerEmails(List.of("sample-enterprise_server_emails"))
                .visualStudioLicenseStatus("sample-visual_studio_license_status")
                .visualStudioSubscriptionEmail("sample-visual_studio_subscription_email")
                .totalUserAccounts(1)
                .build();
    }

    static GitHubGetLicenseSyncStatus getLicenseSyncStatus() {
        return GitHubGetLicenseSyncStatusBuilder.builder()
                .serverInstances(List.of(getLicenseSyncStatusServerInstance()))
                .build();
    }

    static GitHubGetLicenseSyncStatusServerInstance getLicenseSyncStatusServerInstance() {
        return GitHubGetLicenseSyncStatusServerInstanceBuilder.builder()
                .serverId("sample-server_id")
                .hostname("sample-hostname")
                .lastSync(getLicenseSyncStatusLastSync())
                .build();
    }

    static GitHubGetLicenseSyncStatusLastSync getLicenseSyncStatusLastSync() {
        return GitHubGetLicenseSyncStatusLastSyncBuilder.builder()
                .date("sample-date")
                .status("sample-status")
                .error("sample-error")
                .build();
    }

    static GitHubGetGithubAdvancedSecurityBillingGheFilter getGithubAdvancedSecurityBillingGheFilter() {
        return GitHubGetGithubAdvancedSecurityBillingGheFilterBuilder.builder()
                .advancedSecurityProduct(GitHubAdvancedSecurityProduct.CODE_SECURITY)
                .build();
    }

    static GitHubVisualStudioSubscriptionAssignment visualStudioSubscriptionAssignment() {
        return GitHubVisualStudioSubscriptionAssignmentBuilder.builder()
                .visualStudioSubscriptionEmail("sample-visual_studio_subscription_email")
                .subscriptionId("sample-subscription_id")
                .username("sample-username")
                .manualMatch(true)
                .build();
    }

    static GitHubVisualStudioSubscriptionsPage visualStudioSubscriptionsPage() {
        return GitHubVisualStudioSubscriptionsPageBuilder.builder()
                .totalCount(1)
                .visualStudioSubscriptions(List.of(visualStudioSubscriptionAssignment()))
                .build();
    }

    static GitHubListVssFilter listVssFilter() {
        return GitHubListVssFilterBuilder.builder().isUnmatchedOnly(true).build();
    }

    static GitHubUpdateVssManualMatchPayload updateVssManualMatchPayload() {
        return GitHubUpdateVssManualMatchPayloadBuilder.builder()
                .userIdentifier("sample-user_identifier")
                .build();
    }

    static GitHubGetEnterpriseAppsModelPoliciesResponse getEnterpriseAppsModelPoliciesResponse() {
        return GitHubGetEnterpriseAppsModelPoliciesResponseBuilder.builder()
                .models(List.of(getEnterpriseAppsModelPoliciesResponseModel()))
                .build();
    }

    static GitHubGetEnterpriseAppsModelPoliciesResponseModel getEnterpriseAppsModelPoliciesResponseModel() {
        return GitHubGetEnterpriseAppsModelPoliciesResponseModelBuilder.builder()
                .modelName("sample-model_name")
                .displayName("sample-display_name")
                .policyState(GitHubGetEnterpriseAppsModelPoliciesResponsePolicyState.ENABLED)
                .enterprisePolicyState(GitHubGetEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.ENABLED)
                .editable(true)
                .build();
    }

    static GitHubUpdateEnterpriseAppsModelPoliciesPayload updateEnterpriseAppsModelPoliciesPayload() {
        return GitHubUpdateEnterpriseAppsModelPoliciesPayloadBuilder.builder()
                .models(List.of(updateEnterpriseAppsModelPoliciesModel()))
                .build();
    }

    static GitHubUpdateEnterpriseAppsModelPoliciesModel updateEnterpriseAppsModelPoliciesModel() {
        return GitHubUpdateEnterpriseAppsModelPoliciesModelBuilder.builder()
                .modelName("sample-model_name")
                .policyState(GitHubUpdateEnterpriseAppsModelPoliciesPolicyState.ENABLED)
                .build();
    }

    static GitHubUpdateEnterpriseAppsModelPoliciesResponse updateEnterpriseAppsModelPoliciesResponse() {
        return GitHubUpdateEnterpriseAppsModelPoliciesResponseBuilder.builder()
                .models(List.of(updateEnterpriseAppsModelPoliciesResponseModel()))
                .build();
    }

    static GitHubUpdateEnterpriseAppsModelPoliciesResponseModel updateEnterpriseAppsModelPoliciesResponseModel() {
        return GitHubUpdateEnterpriseAppsModelPoliciesResponseModelBuilder.builder()
                .modelName("sample-model_name")
                .displayName("sample-display_name")
                .policyState(GitHubUpdateEnterpriseAppsModelPoliciesResponsePolicyState.ENABLED)
                .enterprisePolicyState(GitHubUpdateEnterpriseAppsModelPoliciesResponseEnterprisePolicyState.ENABLED)
                .editable(true)
                .build();
    }

    static GitHubGetEnterpriseModelPoliciesResponse getEnterpriseModelPoliciesResponse() {
        return GitHubGetEnterpriseModelPoliciesResponseBuilder.builder()
                .defaultAvailability(getEnterpriseModelPoliciesResponseDefaultAvailability())
                .models(List.of(getEnterpriseModelPoliciesResponseModel()))
                .build();
    }

    static GitHubGetEnterpriseModelPoliciesResponseDefaultAvailability
            getEnterpriseModelPoliciesResponseDefaultAvailability() {
        return GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityBuilder.builder()
                .policyState(GitHubGetEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.ENABLED)
                .build();
    }

    static GitHubGetEnterpriseModelPoliciesResponseModel getEnterpriseModelPoliciesResponseModel() {
        return GitHubGetEnterpriseModelPoliciesResponseModelBuilder.builder()
                .modelName("sample-model_name")
                .displayName("sample-display_name")
                .policyState(GitHubGetEnterpriseModelPoliciesResponsePolicyState.ENABLED)
                .build();
    }

    static GitHubUpdateEnterpriseModelPoliciesPayload updateEnterpriseModelPoliciesPayload() {
        return GitHubUpdateEnterpriseModelPoliciesPayloadBuilder.builder()
                .defaultAvailability(updateEnterpriseModelPoliciesPayloadDefaultAvailability())
                .models(List.of(updateEnterpriseModelPoliciesPayloadModel()))
                .build();
    }

    static GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailability
            updateEnterpriseModelPoliciesPayloadDefaultAvailability() {
        return GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityBuilder.builder()
                .policyState(GitHubUpdateEnterpriseModelPoliciesPayloadDefaultAvailabilityPolicyState.ENABLED)
                .build();
    }

    static GitHubUpdateEnterpriseModelPoliciesPayloadModel updateEnterpriseModelPoliciesPayloadModel() {
        return GitHubUpdateEnterpriseModelPoliciesPayloadModelBuilder.builder()
                .modelName("sample-model_name")
                .policyState(GitHubUpdateEnterpriseModelPoliciesPayloadModelPolicyState.ENABLED)
                .build();
    }

    static GitHubUpdateEnterpriseModelPoliciesResponse updateEnterpriseModelPoliciesResponse() {
        return GitHubUpdateEnterpriseModelPoliciesResponseBuilder.builder()
                .defaultAvailability(updateEnterpriseModelPoliciesResponseDefaultAvailability())
                .models(List.of(updateEnterpriseModelPoliciesResponseModel()))
                .build();
    }

    static GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailability
            updateEnterpriseModelPoliciesResponseDefaultAvailability() {
        return GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailabilityBuilder.builder()
                .policyState(GitHubUpdateEnterpriseModelPoliciesResponseDefaultAvailabilityPolicyState.ENABLED)
                .build();
    }

    static GitHubUpdateEnterpriseModelPoliciesResponseModel updateEnterpriseModelPoliciesResponseModel() {
        return GitHubUpdateEnterpriseModelPoliciesResponseModelBuilder.builder()
                .modelName("sample-model_name")
                .displayName("sample-display_name")
                .policyState(GitHubUpdateEnterpriseModelPoliciesResponsePolicyState.ENABLED)
                .build();
    }

    static GitHubGetEnterpriseTeamModelPoliciesResponse getEnterpriseTeamModelPoliciesResponse() {
        return GitHubGetEnterpriseTeamModelPoliciesResponseBuilder.builder()
                .models(List.of(getEnterpriseTeamModelPoliciesResponseModel()))
                .build();
    }

    static GitHubGetEnterpriseTeamModelPoliciesResponseModel getEnterpriseTeamModelPoliciesResponseModel() {
        return GitHubGetEnterpriseTeamModelPoliciesResponseModelBuilder.builder()
                .modelName("sample-model_name")
                .displayName("sample-display_name")
                .policyState(GitHubGetEnterpriseTeamModelPoliciesResponsePolicyState.ENABLED)
                .enterprisePolicyState(GitHubGetEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.ENABLED)
                .editable(true)
                .build();
    }

    static GitHubUpdateEnterpriseTeamModelPoliciesPayload updateEnterpriseTeamModelPoliciesPayload() {
        return GitHubUpdateEnterpriseTeamModelPoliciesPayloadBuilder.builder()
                .models(List.of(updateEnterpriseTeamModelPoliciesModel()))
                .build();
    }

    static GitHubUpdateEnterpriseTeamModelPoliciesModel updateEnterpriseTeamModelPoliciesModel() {
        return GitHubUpdateEnterpriseTeamModelPoliciesModelBuilder.builder()
                .modelName("sample-model_name")
                .policyState(GitHubUpdateEnterpriseTeamModelPoliciesPolicyState.ENABLED)
                .build();
    }

    static GitHubUpdateEnterpriseTeamModelPoliciesResponse updateEnterpriseTeamModelPoliciesResponse() {
        return GitHubUpdateEnterpriseTeamModelPoliciesResponseBuilder.builder()
                .models(List.of(updateEnterpriseTeamModelPoliciesResponseModel()))
                .build();
    }

    static GitHubUpdateEnterpriseTeamModelPoliciesResponseModel updateEnterpriseTeamModelPoliciesResponseModel() {
        return GitHubUpdateEnterpriseTeamModelPoliciesResponseModelBuilder.builder()
                .modelName("sample-model_name")
                .displayName("sample-display_name")
                .policyState(GitHubUpdateEnterpriseTeamModelPoliciesResponsePolicyState.ENABLED)
                .enterprisePolicyState(GitHubUpdateEnterpriseTeamModelPoliciesResponseEnterprisePolicyState.ENABLED)
                .editable(true)
                .build();
    }

    static GitHubRevokeDelegatedCredentialAuthorizationsFilter revokeDelegatedCredentialAuthorizationsFilter() {
        return GitHubRevokeDelegatedCredentialAuthorizationsFilterBuilder.builder()
                .integrationId(1L)
                .build();
    }

    static GitHubRevokeDelegatedCredentialAuthorizationsResponse revokeDelegatedCredentialAuthorizationsResponse() {
        return GitHubRevokeDelegatedCredentialAuthorizationsResponseBuilder.builder()
                .message("sample-message")
                .build();
    }

    static GitHubRevokeAllCredentialAuthorizationsPayload revokeAllCredentialAuthorizationsPayload() {
        return GitHubRevokeAllCredentialAuthorizationsPayloadBuilder.builder()
                .revokeCredentials(true)
                .build();
    }

    static GitHubRevokeAllCredentialAuthorizationsResponse revokeAllCredentialAuthorizationsResponse() {
        return GitHubRevokeAllCredentialAuthorizationsResponseBuilder.builder()
                .message("sample-message")
                .warning("sample-warning")
                .build();
    }

    static GitHubRevokeCredentialTypeForEnterprisePayload revokeCredentialTypeForEnterprisePayload() {
        return GitHubRevokeCredentialTypeForEnterprisePayloadBuilder.builder()
                .credentialType(GitHubRevokeCredentialTypeForEnterpriseCredentialType.CLASSIC_PAT)
                .revokeCredentials(true)
                .build();
    }

    static GitHubRevokeCredentialTypeForEnterpriseResponse revokeCredentialTypeForEnterpriseResponse() {
        return GitHubRevokeCredentialTypeForEnterpriseResponseBuilder.builder()
                .message("sample-message")
                .warning("sample-warning")
                .build();
    }

    static GitHubRevokeCredentialAuthorizationsForUserPayload revokeCredentialAuthorizationsForUserPayload() {
        return GitHubRevokeCredentialAuthorizationsForUserPayloadBuilder.builder()
                .revokeCredentials(true)
                .build();
    }

    static GitHubRevokeCredentialAuthorizationsForUserResponse revokeCredentialAuthorizationsForUserResponse() {
        return GitHubRevokeCredentialAuthorizationsForUserResponseBuilder.builder()
                .message("sample-message")
                .warning("sample-warning")
                .build();
    }

    static GitHubRevokeCredentialTypeForUserPayload revokeCredentialTypeForUserPayload() {
        return GitHubRevokeCredentialTypeForUserPayloadBuilder.builder()
                .credentialType(GitHubRevokeCredentialTypeForUserCredentialType.CLASSIC_PAT)
                .revokeCredentials(true)
                .build();
    }

    static GitHubRevokeCredentialTypeForUserResponse revokeCredentialTypeForUserResponse() {
        return GitHubRevokeCredentialTypeForUserResponseBuilder.builder()
                .message("sample-message")
                .warning("sample-warning")
                .build();
    }

    static GitHubEnterpriseNetworkConfigurationsPage enterpriseNetworkConfigurationsPage() {
        return GitHubEnterpriseNetworkConfigurationsPageBuilder.builder()
                .totalCount(1)
                .networkConfigurations(List.of(OrganizationsCatalogFixtures.networkConfiguration()))
                .build();
    }

    static GitHubCreateNetworkConfigurationForEnterprisePayload createNetworkConfigurationForEnterprisePayload() {
        return GitHubCreateNetworkConfigurationForEnterprisePayloadBuilder.builder()
                .name("sample-name")
                .computeService(GitHubCreateNetworkConfigurationForEnterpriseComputeService.NONE)
                .networkSettingsIds(List.of("sample-network_settings_ids"))
                .failoverNetworkSettingsIds(List.of("sample-failover_network_settings_ids"))
                .failoverNetworkEnabled(true)
                .build();
    }

    static GitHubUpdateNetworkConfigurationForEnterprisePayload updateNetworkConfigurationForEnterprisePayload() {
        return GitHubUpdateNetworkConfigurationForEnterprisePayloadBuilder.builder()
                .name("sample-name")
                .computeService(GitHubUpdateNetworkConfigurationForEnterpriseComputeService.NONE)
                .networkSettingsIds(List.of("sample-network_settings_ids"))
                .failoverNetworkSettingsIds(List.of("sample-failover_network_settings_ids"))
                .failoverNetworkEnabled(true)
                .build();
    }

    static GitHubGrantCredentialAuthorizationsPayload grantCredentialAuthorizationsPayload() {
        return GitHubGrantCredentialAuthorizationsPayloadBuilder.builder()
                .credentialId("123")
                .credentialType(GitHubGrantCredentialAuthorizationsCredentialType.CLASSIC_PAT)
                .organizations(List.of("octo-org"))
                .build();
    }

    static GitHubGrantCredentialAuthorizationsResponse grantCredentialAuthorizationsResponse() {
        return GitHubGrantCredentialAuthorizationsResponseBuilder.builder()
                .credentialId(1L)
                .credentialType(GitHubGrantCredentialAuthorizationsCredentialType.SSH_KEY)
                .fingerprint("SHA256:sample")
                .organizations(List.of("octo-org"))
                .build();
    }

    static GitHubGrantCredentialAuthorizationsResponse grantCredentialAuthorizationsClassicPatResponse() {
        return GitHubGrantCredentialAuthorizationsResponseBuilder.builder()
                .credentialId(1L)
                .credentialType(GitHubGrantCredentialAuthorizationsCredentialType.CLASSIC_PAT)
                .organizations(List.of("octo-org"))
                .build();
    }

    static GitHubServerStatistics serverStatistics() {
        return GitHubServerStatisticsBuilder.builder()
                .serverId("sample-server_id")
                .collectionDate("sample-collection_date")
                .schemaVersion("sample-schema_version")
                .ghesVersion("sample-ghes_version")
                .hostName("sample-host_name")
                .githubConnect(serverStatisticsGithubConnect())
                .gheStats(serverStatisticsGheStats())
                .dormantUsers(serverStatisticsDormantUsers())
                .actionsStats(serverStatisticsActions())
                .packagesStats(serverStatisticsPackages())
                .build();
    }

    static GitHubServerStatisticsGithubConnect serverStatisticsGithubConnect() {
        return GitHubServerStatisticsGithubConnectBuilder.builder()
                .featuresEnabled(List.of("sample-features_enabled"))
                .build();
    }

    static GitHubServerStatisticsGheStats serverStatisticsGheStats() {
        return GitHubServerStatisticsGheStatsBuilder.builder()
                .comments(serverStatisticsGheStatsComments())
                .gists(serverStatisticsGheStatsGists())
                .hooks(serverStatisticsGheStatsHooks())
                .issues(serverStatisticsGheStatsIssues())
                .milestones(serverStatisticsGheStatsMilestones())
                .orgs(serverStatisticsGheStatsOrgs())
                .pages(serverStatisticsGheStatsPages())
                .pulls(serverStatisticsGheStatsPulls())
                .repos(serverStatisticsGheStatsRepos())
                .users(serverStatisticsGheStatsUsers())
                .build();
    }

    static GitHubServerStatisticsGheStatsComments serverStatisticsGheStatsComments() {
        return GitHubServerStatisticsGheStatsCommentsBuilder.builder()
                .totalCommitComments(1)
                .totalGistComments(1)
                .totalIssueComments(1)
                .totalPullRequestComments(1)
                .build();
    }

    static GitHubServerStatisticsGheStatsGists serverStatisticsGheStatsGists() {
        return GitHubServerStatisticsGheStatsGistsBuilder.builder()
                .totalGists(1)
                .privateGists(1)
                .publicGists(1)
                .build();
    }

    static GitHubServerStatisticsGheStatsHooks serverStatisticsGheStatsHooks() {
        return GitHubServerStatisticsGheStatsHooksBuilder.builder()
                .totalHooks(1)
                .activeHooks(1)
                .inactiveHooks(1)
                .build();
    }

    static GitHubServerStatisticsGheStatsIssues serverStatisticsGheStatsIssues() {
        return GitHubServerStatisticsGheStatsIssuesBuilder.builder()
                .totalIssues(1)
                .openIssues(1)
                .closedIssues(1)
                .build();
    }

    static GitHubServerStatisticsGheStatsMilestones serverStatisticsGheStatsMilestones() {
        return GitHubServerStatisticsGheStatsMilestonesBuilder.builder()
                .totalMilestones(1)
                .openMilestones(1)
                .closedMilestones(1)
                .build();
    }

    static GitHubServerStatisticsGheStatsOrgs serverStatisticsGheStatsOrgs() {
        return GitHubServerStatisticsGheStatsOrgsBuilder.builder()
                .totalOrgs(1)
                .disabledOrgs(1)
                .totalTeams(1)
                .totalTeamMembers(1)
                .build();
    }

    static GitHubServerStatisticsGheStatsPages serverStatisticsGheStatsPages() {
        return GitHubServerStatisticsGheStatsPagesBuilder.builder()
                .totalPages(1)
                .build();
    }

    static GitHubServerStatisticsGheStatsPulls serverStatisticsGheStatsPulls() {
        return GitHubServerStatisticsGheStatsPullsBuilder.builder()
                .totalPulls(1)
                .mergedPulls(1)
                .mergeablePulls(1)
                .unmergeablePulls(1)
                .build();
    }

    static GitHubServerStatisticsGheStatsRepos serverStatisticsGheStatsRepos() {
        return GitHubServerStatisticsGheStatsReposBuilder.builder()
                .totalRepos(1)
                .rootRepos(1)
                .forkRepos(1)
                .orgRepos(1)
                .totalPushes(1)
                .totalWikis(1)
                .build();
    }

    static GitHubServerStatisticsGheStatsUsers serverStatisticsGheStatsUsers() {
        return GitHubServerStatisticsGheStatsUsersBuilder.builder()
                .totalUsers(1)
                .adminUsers(1)
                .suspendedUsers(1)
                .build();
    }

    static GitHubServerStatisticsDormantUsers serverStatisticsDormantUsers() {
        return GitHubServerStatisticsDormantUsersBuilder.builder()
                .totalDormantUsers(1)
                .dormancyThreshold("sample-dormancy_threshold")
                .build();
    }

    static GitHubServerStatisticsActions serverStatisticsActions() {
        return GitHubServerStatisticsActionsBuilder.builder()
                .numberOfReposUsingActions(1)
                .percentageOfReposUsingActions("sample-percentage_of_repos_using_actions")
                .build();
    }

    static GitHubServerStatisticsPackages serverStatisticsPackages() {
        return GitHubServerStatisticsPackagesBuilder.builder()
                .registryEnabled(true)
                .registryV2Enabled(true)
                .ecosystems(List.of(serverStatisticsPackagesEcosystem()))
                .build();
    }

    static GitHubServerStatisticsPackagesEcosystem serverStatisticsPackagesEcosystem() {
        return GitHubServerStatisticsPackagesEcosystemBuilder.builder()
                .name(GitHubServerStatisticsPackagesEcosystemName.NPM)
                .enabled(GitHubServerStatisticsPackagesEcosystemEnabled.TRUE)
                .publishedPackagesCount(1)
                .privatePackagesCount(1)
                .publicPackagesCount(1)
                .internalPackagesCount(1)
                .userPackagesCount(1)
                .organizationPackagesCount(1)
                .dailyDownloadCount(1)
                .dailyUpdateCount(1)
                .dailyDeleteCount(1)
                .dailyCreateCount(1)
                .build();
    }

    static GitHubEnterpriseAccessRestrictions enterpriseAccessRestrictions() {
        return GitHubEnterpriseAccessRestrictionsBuilder.builder()
                .message("sample-message")
                .headerName("sample-header_name")
                .headerValue("sample-header_value")
                .build();
    }

    static GitHubEnterpriseSecurityAnalysisSettings enterpriseSecurityAnalysisSettings() {
        return GitHubEnterpriseSecurityAnalysisSettingsBuilder.builder()
                .advancedSecurityEnabledForNewRepositories(true)
                .advancedSecurityEnabledForNewUserNamespaceRepositories(true)
                .dependabotAlertsEnabledForNewRepositories(true)
                .secretScanningEnabledForNewRepositories(true)
                .secretScanningPushProtectionEnabledForNewRepositories(true)
                .secretScanningPushProtectionCustomLink("sample-secret_scanning_push_protection_custom_link")
                .secretScanningNonProviderPatternsEnabledForNewRepositories(true)
                .secretScanningValidityChecksEnabled(true)
                .build();
    }

    static GitHubPatchSecurityAnalysisSettingsForEnterprisePayload patchSecurityAnalysisSettingsForEnterprisePayload() {
        return GitHubPatchSecurityAnalysisSettingsForEnterprisePayloadBuilder.builder()
                .advancedSecurityEnabledForNewRepositories(true)
                .advancedSecurityEnabledNewUserNamespaceRepos(true)
                .dependabotAlertsEnabledForNewRepositories(true)
                .secretScanningEnabledForNewRepositories(true)
                .secretScanningPushProtectionEnabledForNewRepositories(true)
                .secretScanningPushProtectionCustomLink("sample-secret_scanning_push_protection_custom_link")
                .secretScanningNonProviderPatternsEnabledForNewRepositories(true)
                .build();
    }

    static GitHubEnterpriseTokenInventoryItem enterpriseTokenInventoryItem() {
        return GitHubEnterpriseTokenInventoryItemBuilder.builder()
                .inventoryId("sample-inventory_id")
                .credentialId(1L)
                .hashedToken("sample-hashed_token")
                .fingerprint("sample-fingerprint")
                .itemType(GitHubEnterpriseTokenInventoryItemType.CREDENTIAL)
                .credentialType(GitHubEnterpriseTokenInventoryItemCredentialType.CLASSIC_PAT)
                .displayName("sample-display_name")
                .owner(enterpriseTokenInventoryItemOwner())
                .ownerType(GitHubEnterpriseTokenInventoryItemOwnerType.USER)
                .application(enterpriseTokenInventoryItemApplication())
                .credentialState(GitHubEnterpriseTokenInventoryItemCredentialState.ACTIVE)
                .authorizationState(GitHubEnterpriseTokenInventoryItemAuthorizationState.CURRENTLY_AUTHORIZED)
                .effectiveAccessState(GitHubEnterpriseTokenInventoryItemEffectiveAccessState.EFFECTIVE)
                .stateReason("sample-state_reason")
                .createdAt(Instant.parse("2026-01-02T03:04:05Z"))
                .lastUsedAt(Instant.parse("2026-01-02T03:04:05Z"))
                .expiresAt(Instant.parse("2026-01-02T03:04:05Z"))
                .nextExpiresAt(Instant.parse("2026-01-02T03:04:05Z"))
                .credentialInstanceCount(1)
                .enterpriseAuthorized(true)
                .authorizationCount(1)
                .authorizedOrganizations(List.of(enterpriseTokenInventoryItemAuthorizedOrganization()))
                .ageDays(1)
                .neverExpires(true)
                .pastExpirationPolicy(true)
                .pastExpirationPolicyBasis(GitHubEnterpriseTokenInventoryItemPastExpirationPolicyBasis.ENFORCED_LIMIT)
                .expiryUnknown(true)
                .scopes(List.of("sample-scopes"))
                .permissions(Map.of("contents", "read"))
                .repositorySelection(GitHubEnterpriseTokenInventoryItemRepositorySelection.ALL)
                .build();
    }

    static GitHubEnterpriseTokenInventoryItemOwner enterpriseTokenInventoryItemOwner() {
        return GitHubEnterpriseTokenInventoryItemOwnerBuilder.builder()
                .id(1L)
                .login("sample-login")
                .name("sample-name")
                .build();
    }

    static GitHubEnterpriseTokenInventoryItemApplication enterpriseTokenInventoryItemApplication() {
        return GitHubEnterpriseTokenInventoryItemApplicationBuilder.builder()
                .id(1L)
                .name("sample-name")
                .build();
    }

    static GitHubEnterpriseTokenInventoryItemAuthorizedOrganization
            enterpriseTokenInventoryItemAuthorizedOrganization() {
        return GitHubEnterpriseTokenInventoryItemAuthorizedOrganizationBuilder.builder()
                .id(1L)
                .login("sample-login")
                .build();
    }

    static GitHubEnterpriseTokenInventoryExport enterpriseTokenInventoryExport() {
        return GitHubEnterpriseTokenInventoryExportBuilder.builder()
                .exportId("sample-export_id")
                .status(GitHubEnterpriseTokenInventoryExportStatus.PENDING)
                .asOf("sample-as_of")
                .build();
    }

    static GitHubCreateEnterpriseTokenInventoryExportPayload createEnterpriseTokenInventoryExportPayload() {
        return GitHubCreateEnterpriseTokenInventoryExportPayloadBuilder.builder()
                .tokenTypes(List.of("sample-token_types"))
                .authorizationState(GitHubCreateEnterpriseTokenInventoryExportAuthorizationState.CURRENTLY_AUTHORIZED)
                .owner("sample-owner")
                .organization("sample-organization")
                .application("sample-application")
                .build();
    }

    static GitHubOsvVulnerability osvVulnerability() {
        return GitHubOsvVulnerabilityBuilder.builder()
                .id("sample-id")
                .schemaVersion("sample-schema_version")
                .summary("sample-summary")
                .details("sample-details")
                .aliases(List.of("sample-aliases"))
                .severity(List.of(osvVulnerabilitySeverity()))
                .affected(List.of(osvVulnerabilityAffected()))
                .references(List.of(osvVulnerabilityReference()))
                .published(Instant.parse("2026-01-02T03:04:05Z"))
                .modified(Instant.parse("2026-01-02T03:04:05Z"))
                .withdrawn(Instant.parse("2026-01-02T03:04:05Z"))
                .build();
    }

    static GitHubOsvVulnerabilitySeverity osvVulnerabilitySeverity() {
        return GitHubOsvVulnerabilitySeverityBuilder.builder()
                .type("sample-type")
                .score("sample-score")
                .build();
    }

    static GitHubOsvVulnerabilityAffected osvVulnerabilityAffected() {
        return GitHubOsvVulnerabilityAffectedBuilder.builder()
                .pkg(osvVulnerabilityAffectedPackage())
                .ranges(List.of(osvVulnerabilityAffectedRange()))
                .build();
    }

    static GitHubOsvVulnerabilityAffectedPackage osvVulnerabilityAffectedPackage() {
        return GitHubOsvVulnerabilityAffectedPackageBuilder.builder()
                .ecosystem("sample-ecosystem")
                .name("sample-name")
                .build();
    }

    static GitHubOsvVulnerabilityAffectedRange osvVulnerabilityAffectedRange() {
        return GitHubOsvVulnerabilityAffectedRangeBuilder.builder()
                .type("sample-type")
                .events(List.of(osvVulnerabilityAffectedRangeEvent()))
                .build();
    }

    static GitHubOsvVulnerabilityAffectedRangeEvent osvVulnerabilityAffectedRangeEvent() {
        return GitHubOsvVulnerabilityAffectedRangeEventBuilder.builder()
                .introduced("sample-introduced")
                .fixed("sample-fixed")
                .lastAffected("sample-last_affected")
                .limit("sample-limit")
                .build();
    }

    static GitHubOsvVulnerabilityReference osvVulnerabilityReference() {
        return GitHubOsvVulnerabilityReferenceBuilder.builder()
                .type("sample-type")
                .url("sample-url")
                .build();
    }

    static GitHubExternalVulnerabilitySyncAccepted externalVulnerabilitySyncAccepted() {
        return GitHubExternalVulnerabilitySyncAcceptedBuilder.builder()
                .id("sample-id")
                .url("sample-url")
                .status(GitHubExternalVulnerabilitySyncAcceptedStatus.QUEUED)
                .build();
    }

    static GitHubExternalVulnerabilitySyncResult externalVulnerabilitySyncResult() {
        return GitHubExternalVulnerabilitySyncResultBuilder.builder()
                .processed(1)
                .created(1)
                .updated(1)
                .withdrawn(1)
                .errors(1)
                .results(List.of(externalVulnerabilitySyncResultItem()))
                .build();
    }

    static GitHubExternalVulnerabilitySyncResultItem externalVulnerabilitySyncResultItem() {
        return GitHubExternalVulnerabilitySyncResultItemBuilder.builder()
                .externalId("sample-external_id")
                .status(GitHubExternalVulnerabilitySyncResultItemStatus.CREATED)
                .ghsaId("sample-ghsa_id")
                .error("sample-error")
                .build();
    }

    static GitHubExternalVulnerabilitySyncStatus externalVulnerabilitySyncStatus() {
        return GitHubExternalVulnerabilitySyncStatusBuilder.builder()
                .id("sample-id")
                .status(GitHubExternalVulnerabilitySyncStatusStatus.PROCESSING)
                .build();
    }

    static GitHubGetServerStatisticsFilter getServerStatisticsFilter() {
        return GitHubGetServerStatisticsFilterBuilder.builder()
                .dateStart("cursor-start")
                .dateEnd("cursor-end")
                .build();
    }

    static GitHubListPushBypassRequestsFilter listPushBypassRequestsFilter() {
        return GitHubListPushBypassRequestsFilterBuilder.builder()
                .organizationName("octo-org")
                .reviewer("octocat")
                .requester("hubot")
                .timePeriod(GitHubRuleSuiteTimePeriod.WEEK)
                .requestStatus(GitHubBypassRequestStatus.OPEN)
                .build();
    }

    static GitHubListEnterpriseTokenInventoryFilter listEnterpriseTokenInventoryFilter() {
        return GitHubListEnterpriseTokenInventoryFilterBuilder.builder()
                .tokenTypes("classic_pat,ssh_key")
                .authorizationState(GitHubListEnterpriseTokenInventoryAuthorizationState.CURRENTLY_AUTHORIZED)
                .owner("octocat")
                .organization("octo-org")
                .application("octo-app")
                .build();
    }

    static GitHubGetEnterpriseInnersourceVulnerabilitySyncStatusResponse
            getEnterpriseInnersourceVulnerabilitySyncStatusResponseResult() {
        return GitHubGetEnterpriseInnersourceVulnerabilitySyncStatusResponseBuilder.builder()
                .processed(1)
                .created(1)
                .updated(1)
                .withdrawn(1)
                .errors(1)
                .results(List.of(externalVulnerabilitySyncResultItem()))
                .build();
    }

    static GitHubGetEnterpriseInnersourceVulnerabilitySyncStatusResponse
            getEnterpriseInnersourceVulnerabilitySyncStatusResponseError() {
        return GitHubGetEnterpriseInnersourceVulnerabilitySyncStatusResponseBuilder.builder()
                .status(GitHubGetEnterpriseInnersourceVulnerabilitySyncStatusResponseStatus.ERROR)
                .error("sample-error")
                .build();
    }

    static GitHubSyncEnterpriseInnersourceVulnerabilitiesPayload syncEnterpriseInnersourceVulnerabilitiesPayload() {
        return GitHubSyncEnterpriseInnersourceVulnerabilitiesPayloadBuilder.builder()
                .vulnerabilities(List.of(osvVulnerability()))
                .build();
    }

    static GitHubCustomPropertiesCreateOrUpdateEnterpriseDefinitionsPayload
            customPropertiesCreateOrUpdateEnterpriseDefinitionsPayload() {
        return GitHubCustomPropertiesCreateOrUpdateEnterpriseDefinitionsPayloadBuilder.builder()
                .properties(List.of(OrganizationsCatalogFixtures.customProperty()))
                .build();
    }

    static GitHubEnterpriseRulesetConditions enterpriseRulesetConditions() {
        return enterpriseRulesetConditionsOrganizationNameAndRepositoryName();
    }

    static GitHubEnterpriseRulesetConditions enterpriseRulesetConditionsOrganizationNameAndRepositoryName() {
        return GitHubEnterpriseRulesetConditionsBuilder.builder()
                .organizationName(
                        ActionsCatalogFixtures.enterpriseRulesetConditionsOrganizationNameTargetOrganizationName())
                .repositoryName(RepositoriesCatalogFixtures.repositoryNameConditions())
                .refName(RepositoriesCatalogFixtures.rulesetConditionsRefName())
                .build();
    }

    static GitHubEnterpriseRulesetConditions enterpriseRulesetConditionsOrganizationNameAndRepositoryProperty() {
        return GitHubEnterpriseRulesetConditionsBuilder.builder()
                .organizationName(
                        ActionsCatalogFixtures.enterpriseRulesetConditionsOrganizationNameTargetOrganizationName())
                .repositoryProperty(RepositoriesCatalogFixtures.repositoryPropertyTargetProperty())
                .refName(RepositoriesCatalogFixtures.rulesetConditionsRefName())
                .build();
    }

    static GitHubEnterpriseRulesetConditions enterpriseRulesetConditionsOrganizationIdAndRepositoryName() {
        return GitHubEnterpriseRulesetConditionsBuilder.builder()
                .organizationId(ActionsCatalogFixtures.enterpriseRulesetConditionsOrganizationIdTargetOrganizationId())
                .repositoryName(RepositoriesCatalogFixtures.repositoryNameConditions())
                .refName(RepositoriesCatalogFixtures.rulesetConditionsRefName())
                .build();
    }

    static GitHubEnterpriseRulesetConditions enterpriseRulesetConditionsOrganizationIdAndRepositoryProperty() {
        return GitHubEnterpriseRulesetConditionsBuilder.builder()
                .organizationId(ActionsCatalogFixtures.enterpriseRulesetConditionsOrganizationIdTargetOrganizationId())
                .repositoryProperty(RepositoriesCatalogFixtures.repositoryPropertyTargetProperty())
                .refName(RepositoriesCatalogFixtures.rulesetConditionsRefName())
                .build();
    }

    static GitHubEnterpriseRulesetConditions enterpriseRulesetConditionsOrganizationPropertyAndRepositoryName() {
        return GitHubEnterpriseRulesetConditionsBuilder.builder()
                .organizationProperty(
                        ActionsCatalogFixtures
                                .enterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty())
                .repositoryName(RepositoriesCatalogFixtures.repositoryNameConditions())
                .refName(RepositoriesCatalogFixtures.rulesetConditionsRefName())
                .build();
    }

    static GitHubEnterpriseRulesetConditions enterpriseRulesetConditionsOrganizationPropertyAndRepositoryProperty() {
        return GitHubEnterpriseRulesetConditionsBuilder.builder()
                .organizationProperty(
                        ActionsCatalogFixtures
                                .enterpriseRulesetConditionsOrganizationPropertyTargetOrganizationProperty())
                .repositoryProperty(RepositoriesCatalogFixtures.repositoryPropertyTargetProperty())
                .refName(RepositoriesCatalogFixtures.rulesetConditionsRefName())
                .build();
    }

    static GitHubCreateEnterpriseRulesetPayload createEnterpriseRulesetPayload() {
        return GitHubCreateEnterpriseRulesetPayloadBuilder.builder()
                .name("sample-ruleset")
                .target(GitHubCreateEnterpriseRulesetTarget.BRANCH)
                .enforcement(GitHubRepositoryRuleEnforcement.ACTIVE)
                .bypassActors(List.of(RepositoriesCatalogFixtures.rulesetBypassActor()))
                .conditions(enterpriseRulesetConditions())
                .rules(List.of(RepositoriesCatalogFixtures.repositoryRuleCreation()))
                .build();
    }

    static GitHubUpdateEnterpriseRulesetPayload updateEnterpriseRulesetPayload() {
        return GitHubUpdateEnterpriseRulesetPayloadBuilder.builder()
                .name("sample-ruleset")
                .target(GitHubUpdateEnterpriseRulesetTarget.BRANCH)
                .enforcement(GitHubRepositoryRuleEnforcement.ACTIVE)
                .bypassActors(List.of(RepositoriesCatalogFixtures.rulesetBypassActor()))
                .conditions(enterpriseRulesetConditions())
                .rules(List.of(RepositoriesCatalogFixtures.repositoryRuleCreation()))
                .build();
    }

    static GitHubCreateOrgPayload createOrgPayload() {
        return GitHubCreateOrgPayloadBuilder.builder()
                .login("github")
                .admin("monalisa")
                .profileName("GitHub, Inc.")
                .build();
    }

    static GitHubUpdateOrgNamePayload updateOrgNamePayload() {
        return GitHubUpdateOrgNamePayloadBuilder.builder()
                .login("the-new-octocats")
                .build();
    }

    static GitHubUpdateOrgName updateOrgName() {
        return GitHubUpdateOrgNameBuilder.builder()
                .message("Job queued to rename organization. It may take a few minutes to complete.")
                .url("https://api.github.com/organizations/1")
                .build();
    }

    static GitHubSyncLdapMappingForTeam syncLdapMappingForTeam() {
        return GitHubSyncLdapMappingForTeamBuilder.builder().status("queued").build();
    }

    static GitHubSyncLdapMappingForUser syncLdapMappingForUser() {
        return GitHubSyncLdapMappingForUserBuilder.builder().status("queued").build();
    }

    static GitHubUpdateLdapMappingForTeamPayload updateLdapMappingForTeamPayload() {
        return GitHubUpdateLdapMappingForTeamPayloadBuilder.builder()
                .ldapDn("cn=Enterprise Ops,ou=teams,dc=github,dc=com")
                .build();
    }

    static GitHubUpdateLdapMappingForUserPayload updateLdapMappingForUserPayload() {
        return GitHubUpdateLdapMappingForUserPayloadBuilder.builder()
                .ldapDn("ldap_dn")
                .build();
    }

    static GitHubLdapMappingTeam ldapMappingTeam() {
        return GitHubLdapMappingTeamBuilder.builder()
                .ldapDn("ldap_dn")
                .id(1)
                .nodeId("node_id")
                .url("url")
                .htmlUrl("html_url")
                .name("name")
                .slug("slug")
                .description("description")
                .privacy("privacy")
                .notificationSetting("notification_setting")
                .permission("permission")
                .membersUrl("members_url")
                .repositoriesUrl("repositories_url")
                .type("type")
                .organizationId(1)
                .enterpriseId(1)
                .build();
    }

    static GitHubLdapMappingUserPlan ldapMappingUserPlan() {
        return GitHubLdapMappingUserPlanBuilder.builder()
                .collaborators(1)
                .name("name")
                .space(1)
                .privateRepos(1)
                .build();
    }

    static GitHubLdapMappingUser ldapMappingUser() {
        return GitHubLdapMappingUserBuilder.builder()
                .ldapDn("ldap_dn")
                .login("octocat")
                .id(1L)
                .nodeId("MDQ6VXNlcjE=")
                .avatarUrl("https://github.com/images/error/octocat_happy.gif")
                .gravatarId("41d064eb2195891e12d0413f63227ea7")
                .url("https://api.github.com/users/octocat")
                .htmlUrl("https://github.com/octocat")
                .followersUrl("https://api.github.com/users/octocat/followers")
                .followingUrl("https://api.github.com/users/octocat/following{/other_user}")
                .gistsUrl("https://api.github.com/users/octocat/gists{/gist_id}")
                .starredUrl("https://api.github.com/users/octocat/starred{/owner}{/repo}")
                .subscriptionsUrl("https://api.github.com/users/octocat/subscriptions")
                .organizationsUrl("https://api.github.com/users/octocat/orgs")
                .reposUrl("https://api.github.com/users/octocat/repos")
                .eventsUrl("https://api.github.com/users/octocat/events{/privacy}")
                .receivedEventsUrl("https://api.github.com/users/octocat/received_events")
                .type("User")
                .siteAdmin(true)
                .name("monalisa octocat")
                .company("GitHub")
                .blog("https://github.com/blog")
                .location("San Francisco")
                .email("octocat@github.com")
                .hireable(true)
                .bio("There once was...")
                .twitterUsername("monalisa")
                .publicRepos(1)
                .publicGists(1)
                .followers(1)
                .following(1)
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .updatedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .privateGists(1)
                .totalPrivateRepos(1)
                .ownedPrivateRepos(1)
                .diskUsage(1)
                .collaborators(1)
                .twoFactorAuthentication(true)
                .plan(ldapMappingUserPlan())
                .suspendedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .businessPlus(true)
                .userViewType("public")
                .build();
    }

    static GitHubEnterpriseCommentOverview enterpriseCommentOverview() {
        return GitHubEnterpriseCommentOverviewBuilder.builder()
                .totalCommitComments(1)
                .totalGistComments(1)
                .totalIssueComments(1)
                .totalPullRequestComments(1)
                .build();
    }

    static GitHubEnterpriseGistOverview enterpriseGistOverview() {
        return GitHubEnterpriseGistOverviewBuilder.builder()
                .totalGists(1)
                .privateGists(1)
                .publicGists(1)
                .build();
    }

    static GitHubEnterpriseHookOverview enterpriseHookOverview() {
        return GitHubEnterpriseHookOverviewBuilder.builder()
                .totalHooks(1)
                .activeHooks(1)
                .inactiveHooks(1)
                .build();
    }

    static GitHubEnterpriseIssueOverview enterpriseIssueOverview() {
        return GitHubEnterpriseIssueOverviewBuilder.builder()
                .totalIssues(1)
                .openIssues(1)
                .closedIssues(1)
                .build();
    }

    static GitHubEnterpriseMilestoneOverview enterpriseMilestoneOverview() {
        return GitHubEnterpriseMilestoneOverviewBuilder.builder()
                .totalMilestones(1)
                .openMilestones(1)
                .closedMilestones(1)
                .build();
    }

    static GitHubEnterpriseOrganizationOverview enterpriseOrganizationOverview() {
        return GitHubEnterpriseOrganizationOverviewBuilder.builder()
                .totalOrgs(1)
                .disabledOrgs(1)
                .totalTeams(1)
                .totalTeamMembers(1)
                .build();
    }

    static GitHubEnterprisePageOverview enterprisePageOverview() {
        return GitHubEnterprisePageOverviewBuilder.builder().totalPages(1).build();
    }

    static GitHubEnterprisePullRequestOverview enterprisePullRequestOverview() {
        return GitHubEnterprisePullRequestOverviewBuilder.builder()
                .totalPulls(1)
                .mergedPulls(1)
                .mergeablePulls(1)
                .unmergeablePulls(1)
                .build();
    }

    static GitHubEnterpriseRepositoryOverview enterpriseRepositoryOverview() {
        return GitHubEnterpriseRepositoryOverviewBuilder.builder()
                .totalRepos(1)
                .rootRepos(1)
                .forkRepos(1)
                .orgRepos(1)
                .totalPushes(1)
                .totalWikis(1)
                .build();
    }

    static GitHubEnterpriseSecurityProductsOverview enterpriseSecurityProductsOverview() {
        return GitHubEnterpriseSecurityProductsOverviewBuilder.builder()
                .totalRepos(1)
                .nonarchivedRepos(1)
                .secretScanningEnabledRepos(1)
                .secretScanningPushProtectionEnabledRepos(1)
                .codeScanningEnabledRepos(1)
                .codeScanningPrReviewsEnabledRepos(1)
                .codeScanningDefaultSetupEnabledRepos(1)
                .codeScanningDefaultSetupEligibleRepos(1)
                .dependabotAlertsEnabledRepos(1)
                .dependabotSecurityUpdatesEnabledRepos(1)
                .dependabotVersionUpdatesEnabledRepos(1)
                .advancedSecurityEnabledRepos(1)
                .activeCommitters(1)
                .purchasedCommitters(1)
                .maximumCommitters(1)
                .secretProtectionLicenses(1)
                .secretProtectionActiveCommitters(1)
                .codeSecurityLicenses(1)
                .codeSecurityActiveCommitters(1)
                .build();
    }

    static GitHubEnterpriseUserOverview enterpriseUserOverview() {
        return GitHubEnterpriseUserOverviewBuilder.builder()
                .totalUsers(1)
                .adminUsers(1)
                .suspendedUsers(1)
                .build();
    }

    static GitHubEnterpriseOverviewRepos enterpriseOverviewRepos() {
        return GitHubEnterpriseOverviewReposBuilder.builder()
                .totalRepos(1)
                .rootRepos(1)
                .forkRepos(1)
                .orgRepos(1)
                .totalPushes(1)
                .totalWikis(1)
                .build();
    }

    static GitHubEnterpriseOverviewHooks enterpriseOverviewHooks() {
        return GitHubEnterpriseOverviewHooksBuilder.builder()
                .totalHooks(1)
                .activeHooks(1)
                .inactiveHooks(1)
                .build();
    }

    static GitHubEnterpriseOverviewPages enterpriseOverviewPages() {
        return GitHubEnterpriseOverviewPagesBuilder.builder().totalPages(1).build();
    }

    static GitHubEnterpriseOverviewOrgs enterpriseOverviewOrgs() {
        return GitHubEnterpriseOverviewOrgsBuilder.builder()
                .totalOrgs(1)
                .disabledOrgs(1)
                .totalTeams(1)
                .totalTeamMembers(1)
                .build();
    }

    static GitHubEnterpriseOverviewUsers enterpriseOverviewUsers() {
        return GitHubEnterpriseOverviewUsersBuilder.builder()
                .totalUsers(1)
                .adminUsers(1)
                .suspendedUsers(1)
                .build();
    }

    static GitHubEnterpriseOverviewPulls enterpriseOverviewPulls() {
        return GitHubEnterpriseOverviewPullsBuilder.builder()
                .totalPulls(1)
                .mergedPulls(1)
                .mergeablePulls(1)
                .unmergeablePulls(1)
                .build();
    }

    static GitHubEnterpriseOverviewIssues enterpriseOverviewIssues() {
        return GitHubEnterpriseOverviewIssuesBuilder.builder()
                .totalIssues(1)
                .openIssues(1)
                .closedIssues(1)
                .build();
    }

    static GitHubEnterpriseOverviewMilestones enterpriseOverviewMilestones() {
        return GitHubEnterpriseOverviewMilestonesBuilder.builder()
                .totalMilestones(1)
                .openMilestones(1)
                .closedMilestones(1)
                .build();
    }

    static GitHubEnterpriseOverviewGists enterpriseOverviewGists() {
        return GitHubEnterpriseOverviewGistsBuilder.builder()
                .totalGists(1)
                .privateGists(1)
                .publicGists(1)
                .build();
    }

    static GitHubEnterpriseOverviewComments enterpriseOverviewComments() {
        return GitHubEnterpriseOverviewCommentsBuilder.builder()
                .totalCommitComments(1)
                .totalGistComments(1)
                .totalIssueComments(1)
                .totalPullRequestComments(1)
                .build();
    }

    static GitHubEnterpriseOverview enterpriseOverview() {
        return GitHubEnterpriseOverviewBuilder.builder()
                .repos(enterpriseOverviewRepos())
                .hooks(enterpriseOverviewHooks())
                .pages(enterpriseOverviewPages())
                .orgs(enterpriseOverviewOrgs())
                .users(enterpriseOverviewUsers())
                .pulls(enterpriseOverviewPulls())
                .issues(enterpriseOverviewIssues())
                .milestones(enterpriseOverviewMilestones())
                .gists(enterpriseOverviewGists())
                .comments(enterpriseOverviewComments())
                .build();
    }

    static GitHubPreReceiveEnvironmentDownload preReceiveEnvironmentDownload() {
        return GitHubPreReceiveEnvironmentDownloadBuilder.builder()
                .url("url")
                .state("state")
                .downloadedAt("downloaded_at")
                .message("message")
                .build();
    }

    static GitHubPreReceiveEnvironment preReceiveEnvironment() {
        return GitHubPreReceiveEnvironmentBuilder.builder()
                .id(1L)
                .name("name")
                .imageUrl("image_url")
                .url("url")
                .htmlUrl("html_url")
                .defaultEnvironment(true)
                .createdAt("created_at")
                .hooksCount(1)
                .download(preReceiveEnvironmentDownload())
                .build();
    }

    static GitHubPreReceiveEnvironmentDownloadStatus preReceiveEnvironmentDownloadStatus() {
        return GitHubPreReceiveEnvironmentDownloadStatusBuilder.builder()
                .url("url")
                .state("state")
                .downloadedAt("downloaded_at")
                .message("message")
                .build();
    }

    static GitHubPreReceiveHookScriptRepository preReceiveHookScriptRepository() {
        return GitHubPreReceiveHookScriptRepositoryBuilder.builder()
                .id(1)
                .fullName("full_name")
                .url("url")
                .htmlUrl("html_url")
                .build();
    }

    static GitHubPreReceiveHookEnvironmentDownload preReceiveHookEnvironmentDownload() {
        return GitHubPreReceiveHookEnvironmentDownloadBuilder.builder()
                .url("url")
                .state("state")
                .downloadedAt("downloaded_at")
                .message("message")
                .build();
    }

    static GitHubPreReceiveHookEnvironment preReceiveHookEnvironment() {
        return GitHubPreReceiveHookEnvironmentBuilder.builder()
                .id(1L)
                .name("name")
                .imageUrl("image_url")
                .url("url")
                .htmlUrl("html_url")
                .defaultEnvironment(true)
                .createdAt("created_at")
                .hooksCount(1)
                .download(preReceiveHookEnvironmentDownload())
                .build();
    }

    static GitHubPreReceiveHook preReceiveHook() {
        return GitHubPreReceiveHookBuilder.builder()
                .id(1)
                .name("name")
                .enforcement("enforcement")
                .script("script")
                .scriptRepository(preReceiveHookScriptRepository())
                .environment(preReceiveHookEnvironment())
                .allowDownstreamConfiguration(true)
                .build();
    }

    static GitHubOrgPreReceiveHook orgPreReceiveHook() {
        return GitHubOrgPreReceiveHookBuilder.builder()
                .id(1)
                .name("name")
                .enforcement("enforcement")
                .configurationUrl("configuration_url")
                .allowDownstreamConfiguration(true)
                .build();
    }

    static GitHubRepositoryPreReceiveHook repositoryPreReceiveHook() {
        return GitHubRepositoryPreReceiveHookBuilder.builder()
                .id(1)
                .name("name")
                .enforcement("enforcement")
                .configurationUrl("configuration_url")
                .build();
    }

    static GitHubCreatePreReceiveEnvironmentPayload createPreReceiveEnvironmentPayload() {
        return GitHubCreatePreReceiveEnvironmentPayloadBuilder.builder()
                .name("name")
                .imageUrl("image_url")
                .build();
    }

    static GitHubUpdatePreReceiveEnvironmentPayload updatePreReceiveEnvironmentPayload() {
        return GitHubUpdatePreReceiveEnvironmentPayloadBuilder.builder()
                .name("name")
                .imageUrl("image_url")
                .build();
    }

    static GitHubUpdatePreReceiveHookEnforcementForOrgPayload updatePreReceiveHookEnforcementForOrgPayload() {
        return GitHubUpdatePreReceiveHookEnforcementForOrgPayloadBuilder.builder()
                .enforcement("enforcement")
                .allowDownstreamConfiguration(true)
                .build();
    }

    static GitHubListPreReceiveEnvironmentsFilter listPreReceiveEnvironmentsFilter() {
        return GitHubListPreReceiveEnvironmentsFilterBuilder.builder()
                .sort(GitHubListPreReceiveEnvironmentsSort.CREATED)
                .direction(GitHubDirection.DESC)
                .build();
    }

    static GitHubListPreReceiveHooksFilter listPreReceiveHooksFilter() {
        return GitHubListPreReceiveHooksFilterBuilder.builder()
                .sort(GitHubListPreReceiveHooksSort.CREATED)
                .direction(GitHubDirection.DESC)
                .build();
    }

    static GitHubListPreReceiveHooksForOrgFilter listPreReceiveHooksForOrgFilter() {
        return GitHubListPreReceiveHooksForOrgFilterBuilder.builder()
                .sort(GitHubListPreReceiveHooksForOrgSort.CREATED)
                .direction(GitHubDirection.DESC)
                .build();
    }

    static GitHubListPreReceiveHooksForRepoFilter listPreReceiveHooksForRepoFilter() {
        return GitHubListPreReceiveHooksForRepoFilterBuilder.builder()
                .sort(GitHubListPreReceiveHooksForRepoSort.CREATED)
                .direction(GitHubDirection.DESC)
                .build();
    }

    static GitHubCreatePreReceiveHookPayload createPreReceiveHookPayload() {
        return GitHubCreatePreReceiveHookPayloadBuilder.builder()
                .name("Check Commits")
                .script("scripts/commit_check.sh")
                .scriptRepository(Map.of("full_name", "DevIns/hooks-repo"))
                .environment(Map.of("id", 2))
                .enforcement("disabled")
                .allowDownstreamConfiguration(false)
                .build();
    }

    static GitHubUpdatePreReceiveHookPayload updatePreReceiveHookPayload() {
        return GitHubUpdatePreReceiveHookPayloadBuilder.builder()
                .name("Check Commits")
                .script("scripts/commit_check.sh")
                .scriptRepository(Map.of("full_name", "DevIns/hooks-repo"))
                .environment(Map.of("id", 2))
                .enforcement("enabled")
                .allowDownstreamConfiguration(true)
                .build();
    }

    static GitHubUpdatePreReceiveHookEnforcementForRepoPayload updatePreReceiveHookEnforcementForRepoPayload() {
        return GitHubUpdatePreReceiveHookEnforcementForRepoPayloadBuilder.builder()
                .enforcement(GitHubUpdatePreReceiveHookEnforcementForRepoEnforcement.TESTING)
                .build();
    }

    static GitHubGlobalHookConfig globalHookConfig() {
        return GitHubGlobalHookConfigBuilder.builder()
                .url("url")
                .contentType("content_type")
                .insecureSsl("insecure_ssl")
                .secret("secret")
                .build();
    }

    static GitHubGlobalHook globalHook() {
        return GitHubGlobalHookBuilder.builder()
                .type("type")
                .id(1)
                .name("name")
                .active(true)
                .events(List.of("events"))
                .config(globalHookConfig())
                .updatedAt("updated_at")
                .createdAt("created_at")
                .url("url")
                .pingUrl("ping_url")
                .build();
    }

    static GitHubGlobalHookWithoutSecretConfig globalHookWithoutSecretConfig() {
        return GitHubGlobalHookWithoutSecretConfigBuilder.builder()
                .url("url")
                .contentType("content_type")
                .insecureSsl("insecure_ssl")
                .build();
    }

    static GitHubGlobalHookWithoutSecret globalHookWithoutSecret() {
        return GitHubGlobalHookWithoutSecretBuilder.builder()
                .type("type")
                .id(1)
                .name("name")
                .active(true)
                .events(List.of("events"))
                .config(globalHookWithoutSecretConfig())
                .updatedAt("updated_at")
                .createdAt("created_at")
                .url("url")
                .pingUrl("ping_url")
                .build();
    }

    static GitHubPublicKeyFull publicKeyFull() {
        return GitHubPublicKeyFullBuilder.builder()
                .id(1L)
                .key("key")
                .userId(1)
                .repositoryId(1L)
                .url("url")
                .title("title")
                .readOnly(true)
                .verified(true)
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .addedBy("added_by")
                .lastUsed(Instant.parse("2026-01-01T00:00:00Z"))
                .enabled(true)
                .build();
    }

    static GitHubCreateGlobalWebhookPayloadConfig createGlobalWebhookPayloadConfig() {
        return GitHubCreateGlobalWebhookPayloadConfigBuilder.builder()
                .url("url")
                .contentType("content_type")
                .secret("secret")
                .insecureSsl("insecure_ssl")
                .build();
    }

    static GitHubCreateGlobalWebhookPayload createGlobalWebhookPayload() {
        return GitHubCreateGlobalWebhookPayloadBuilder.builder()
                .name("name")
                .config(createGlobalWebhookPayloadConfig())
                .events(List.of("events"))
                .active(true)
                .build();
    }

    static GitHubUpdateGlobalWebhookPayloadConfig updateGlobalWebhookPayloadConfig() {
        return GitHubUpdateGlobalWebhookPayloadConfigBuilder.builder()
                .url("url")
                .contentType("content_type")
                .secret("secret")
                .insecureSsl("insecure_ssl")
                .build();
    }

    static GitHubUpdateGlobalWebhookPayload updateGlobalWebhookPayload() {
        return GitHubUpdateGlobalWebhookPayloadBuilder.builder()
                .config(updateGlobalWebhookPayloadConfig())
                .events(List.of("events"))
                .active(true)
                .build();
    }

    static GitHubCreateUserPayload createUserPayload() {
        return GitHubCreateUserPayloadBuilder.builder()
                .login("login")
                .email("email")
                .suspended(true)
                .build();
    }

    static GitHubUpdateUsernameForUserPayload updateUsernameForUserPayload() {
        return GitHubUpdateUsernameForUserPayloadBuilder.builder()
                .login("login")
                .build();
    }

    static GitHubUpdateUsernameForUserResponse updateUsernameForUserResponse() {
        return GitHubUpdateUsernameForUserResponseBuilder.builder()
                .message("message")
                .url("url")
                .build();
    }

    static GitHubCreateImpersonationOAuthTokenPayload createImpersonationOAuthTokenPayload() {
        return GitHubCreateImpersonationOAuthTokenPayloadBuilder.builder()
                .scopes(List.of("scopes"))
                .build();
    }

    static GitHubSuspendUserPayload suspendUserPayload() {
        return GitHubSuspendUserPayloadBuilder.builder().reason("reason").build();
    }

    static GitHubUnsuspendUserPayload unsuspendUserPayload() {
        return GitHubUnsuspendUserPayloadBuilder.builder().reason("reason").build();
    }

    static GitHubListPublicKeysFilter listPublicKeysFilter() {
        return GitHubListPublicKeysFilterBuilder.builder()
                .sort(GitHubListPublicKeysSort.CREATED)
                .direction(GitHubDirection.DESC)
                .since(Instant.parse("2026-01-01T00:00:00Z"))
                .build();
    }

    static GitHubLiveMigrationListMigrationRepository liveMigrationListMigrationRepository() {
        return GitHubLiveMigrationListMigrationRepositoryBuilder.builder()
                .sourceRepositoryUrl("source_repository_url")
                .targetRepositoryUrl("target_repository_url")
                .status("status")
                .build();
    }

    static GitHubLiveMigrationListMigration liveMigrationListMigration() {
        return GitHubLiveMigrationListMigrationBuilder.builder()
                .migrationId("migration_id")
                .status("status")
                .sourceOrganizationLogin("source_organization_login")
                .targetOrganizationLogin("target_organization_login")
                .sourceRepositoryName("source_repository_name")
                .targetRepositoryName("target_repository_name")
                .targetVisibility("target_visibility")
                .targetApiEndpoint("target_api_endpoint")
                .targetMigrationId(1L)
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .startedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .completedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .expiresAt(Instant.parse("2026-01-01T00:00:00Z"))
                .repositories(List.of(liveMigrationListMigrationRepository()))
                .build();
    }

    static GitHubLiveMigrationList liveMigrationList() {
        return GitHubLiveMigrationListBuilder.builder()
                .migrations(List.of(liveMigrationListMigration()))
                .totalCount(1)
                .nextCursor("next_cursor")
                .build();
    }

    static GitHubLiveMigrationCreatePayload liveMigrationCreatePayload() {
        return GitHubLiveMigrationCreatePayloadBuilder.builder()
                .sourceOrganizationLogin("source_organization_login")
                .sourceRepositoryName("source_repository_name")
                .targetOrganizationLogin("target_organization_login")
                .targetRepositoryName("target_repository_name")
                .targetApiEndpoint("https://api.github.com/target_api_endpoint")
                .patName("pat_name")
                .targetVisibility(GitHubLiveMigrationCreatePayloadTargetVisibility.PRIVATE)
                .build();
    }

    static GitHubLiveMigrationCreateResponse liveMigrationCreateResponse() {
        return GitHubLiveMigrationCreateResponseBuilder.builder()
                .migrationId("migration_id")
                .expiresAt(Instant.parse("2026-01-01T00:00:00Z"))
                .build();
    }

    static GitHubLiveMigrationDetailsMigrationRepository liveMigrationDetailsMigrationRepository() {
        return GitHubLiveMigrationDetailsMigrationRepositoryBuilder.builder()
                .sourceRepositoryUrl("source_repository_url")
                .targetRepositoryUrl("target_repository_url")
                .status("status")
                .build();
    }

    static GitHubLiveMigrationDetailsMigration liveMigrationDetailsMigration() {
        return GitHubLiveMigrationDetailsMigrationBuilder.builder()
                .migrationId("migration_id")
                .status("status")
                .sourceOrganizationLogin("source_organization_login")
                .targetOrganizationLogin("target_organization_login")
                .sourceRepositoryName("source_repository_name")
                .targetRepositoryName("target_repository_name")
                .targetVisibility("target_visibility")
                .targetApiEndpoint("target_api_endpoint")
                .targetMigrationId(1L)
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .startedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .completedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .expiresAt(Instant.parse("2026-01-01T00:00:00Z"))
                .repositories(List.of(liveMigrationDetailsMigrationRepository()))
                .build();
    }

    static GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdown
            liveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdown() {
        return GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdownBuilder.builder()
                .state("state")
                .kind("kind")
                .type("type")
                .count(1L)
                .build();
    }

    static GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfill
            liveMigrationDetailsTargetStateRepositorySummaryBackfill() {
        return GitHubLiveMigrationDetailsTargetStateRepositorySummaryBackfillBuilder.builder()
                .total(1L)
                .totalPending(1L)
                .totalProcessed(1L)
                .totalFailed(1L)
                .totalEligible(1L)
                .totalAcknowledged(1L)
                .breakdown(List.of(liveMigrationDetailsTargetStateRepositorySummaryBackfillBreakdown()))
                .build();
    }

    static GitHubLiveMigrationDetailsTargetStateRepositorySummaryLiveUpdateBreakdown
            liveMigrationDetailsTargetStateRepositorySummaryLiveUpdateBreakdown() {
        return GitHubLiveMigrationDetailsTargetStateRepositorySummaryLiveUpdateBreakdownBuilder.builder()
                .state("state")
                .kind("kind")
                .type("type")
                .count(1L)
                .build();
    }

    static GitHubLiveMigrationDetailsTargetStateRepositorySummaryLiveUpdate
            liveMigrationDetailsTargetStateRepositorySummaryLiveUpdate() {
        return GitHubLiveMigrationDetailsTargetStateRepositorySummaryLiveUpdateBuilder.builder()
                .total(1L)
                .totalPending(1L)
                .totalProcessed(1L)
                .totalFailed(1L)
                .totalEligible(1L)
                .totalAcknowledged(1L)
                .breakdown(List.of(liveMigrationDetailsTargetStateRepositorySummaryLiveUpdateBreakdown()))
                .build();
    }

    static GitHubLiveMigrationDetailsTargetStateRepositorySummary liveMigrationDetailsTargetStateRepositorySummary() {
        return GitHubLiveMigrationDetailsTargetStateRepositorySummaryBuilder.builder()
                .namespace("namespace")
                .repository("repository")
                .total(1L)
                .totalBackfill(1L)
                .totalLiveUpdate(1L)
                .totalProcessed(1L)
                .totalFailed(1L)
                .totalPending(1L)
                .totalEligible(1L)
                .totalAcknowledged(1L)
                .initialGitPushComplete(true)
                .backfill(liveMigrationDetailsTargetStateRepositorySummaryBackfill())
                .liveUpdate(liveMigrationDetailsTargetStateRepositorySummaryLiveUpdate())
                .build();
    }

    static GitHubLiveMigrationDetailsTargetStateRepositoryProgress liveMigrationDetailsTargetStateRepositoryProgress() {
        return GitHubLiveMigrationDetailsTargetStateRepositoryProgressBuilder.builder()
                .repositoryNwo("repository_nwo")
                .backfillResourcesAdded(1L)
                .backfillResourcesProcessed(1L)
                .backfillResourcesFailed(1L)
                .backfillResourcesEligible(1L)
                .backfillResourcesAcknowledged(1L)
                .liveUpdateResourcesAdded(1L)
                .liveUpdateResourcesProcessed(1L)
                .liveUpdateResourcesEligible(1L)
                .liveUpdateResourcesAcknowledged(1L)
                .liveUpdateResourcesFailed(1L)
                .allResourcesSent(true)
                .initialGitPushComplete(true)
                .repositoryLocked(true)
                .build();
    }

    static GitHubLiveMigrationDetailsTargetStateTerminalFailure liveMigrationDetailsTargetStateTerminalFailure() {
        return GitHubLiveMigrationDetailsTargetStateTerminalFailureBuilder.builder()
                .code("code")
                .summary("summary")
                .occurredAt(Instant.parse("2026-01-01T00:00:00Z"))
                .build();
    }

    static GitHubLiveMigrationDetailsTargetState liveMigrationDetailsTargetState() {
        return GitHubLiveMigrationDetailsTargetStateBuilder.builder()
                .status("status")
                .lastUpdated(Instant.parse("2026-01-01T00:00:00Z"))
                .targetUnavailable(true)
                .repositorySummaries(List.of(liveMigrationDetailsTargetStateRepositorySummary()))
                .repositoryProgress(List.of(liveMigrationDetailsTargetStateRepositoryProgress()))
                .terminalFailure(liveMigrationDetailsTargetStateTerminalFailure())
                .build();
    }

    static GitHubLiveMigrationDetailsCombinedStateRepository liveMigrationDetailsCombinedStateRepository() {
        return GitHubLiveMigrationDetailsCombinedStateRepositoryBuilder.builder()
                .repositoryNwo("repository_nwo")
                .phase("phase")
                .displayStatus("display_status")
                .build();
    }

    static GitHubLiveMigrationDetailsCombinedStateTerminalFailure liveMigrationDetailsCombinedStateTerminalFailure() {
        return GitHubLiveMigrationDetailsCombinedStateTerminalFailureBuilder.builder()
                .code("code")
                .summary("summary")
                .occurredAt(Instant.parse("2026-01-01T00:00:00Z"))
                .build();
    }

    static GitHubLiveMigrationDetailsCombinedState liveMigrationDetailsCombinedState() {
        return GitHubLiveMigrationDetailsCombinedStateBuilder.builder()
                .status("status")
                .displayMessage("display_message")
                .readyForCutover(true)
                .cutoverBlockers(List.of("cutover_blockers"))
                .repositories(List.of(liveMigrationDetailsCombinedStateRepository()))
                .terminalFailure(liveMigrationDetailsCombinedStateTerminalFailure())
                .build();
    }

    static GitHubLiveMigrationDetailsMessage liveMigrationDetailsMessage() {
        return GitHubLiveMigrationDetailsMessageBuilder.builder()
                .messageType("message_type")
                .message("message")
                .createdAt(Instant.parse("2026-01-01T00:00:00Z"))
                .build();
    }

    static GitHubLiveMigrationDetails liveMigrationDetails() {
        return GitHubLiveMigrationDetailsBuilder.builder()
                .migration(liveMigrationDetailsMigration())
                .targetState(liveMigrationDetailsTargetState())
                .combinedState(liveMigrationDetailsCombinedState())
                .messages(List.of(liveMigrationDetailsMessage()))
                .build();
    }

    static GitHubLiveMigrationCutoverPayload liveMigrationCutoverPayload() {
        return GitHubLiveMigrationCutoverPayloadBuilder.builder().force(true).build();
    }

    static GitHubLiveMigrationRevertCutoverResult liveMigrationRevertCutoverResult() {
        return GitHubLiveMigrationRevertCutoverResultBuilder.builder()
                .success(true)
                .unarchivedSourceRepository(true)
                .inProgressCutoverTerminated(true)
                .inProgressMigrationTerminated(true)
                .build();
    }

    static GitHubLiveMigrationListFilter liveMigrationListFilter() {
        return GitHubLiveMigrationListFilterBuilder.builder()
                .pageSize(1)
                .status(GitHubLiveMigrationListStatus.CREATED)
                .build();
    }

    static GitHubManageSshKey manageSshKey() {
        return GitHubManageSshKeyBuilder.builder()
                .key("key")
                .fingerprint("fingerprint")
                .build();
    }

    static GitHubManageSetSshPayload manageSetSshPayload() {
        return GitHubManageSetSshPayloadBuilder.builder().key("key").build();
    }

    static GitHubManageSetSshResult manageSetSshResult() {
        return GitHubManageSetSshResultBuilder.builder()
                .hostname("hostname")
                .uuid("uuid")
                .message("message")
                .error("error")
                .modified(true)
                .build();
    }

    static GitHubManageDeleteSshPayload manageDeleteSshPayload() {
        return GitHubManageDeleteSshPayloadBuilder.builder().key("key").build();
    }

    static GitHubManageDeleteSshResult manageDeleteSshResult() {
        return GitHubManageDeleteSshResultBuilder.builder()
                .hostname("hostname")
                .uuid("uuid")
                .message("message")
                .error("error")
                .build();
    }

    static GitHubManageSystemRequirementsNodeRolesStatu manageSystemRequirementsNodeRolesStatu() {
        return GitHubManageSystemRequirementsNodeRolesStatuBuilder.builder()
                .status(GitHubManageChecksResultIndicator.OK)
                .role("role")
                .build();
    }

    static GitHubManageSystemRequirementsNode manageSystemRequirementsNode() {
        return GitHubManageSystemRequirementsNodeBuilder.builder()
                .hostname("hostname")
                .status(GitHubManageChecksResultIndicator.OK)
                .rolesStatus(List.of(manageSystemRequirementsNodeRolesStatu()))
                .build();
    }

    static GitHubManageSystemRequirements manageSystemRequirements() {
        return GitHubManageSystemRequirementsBuilder.builder()
                .status(GitHubManageChecksResultIndicator.OK)
                .nodes(List.of(manageSystemRequirementsNode()))
                .build();
    }

    static GitHubManageClusterStatusNodeService manageClusterStatusNodeService() {
        return GitHubManageClusterStatusNodeServiceBuilder.builder()
                .status(GitHubManageClusterStatusIndicator.UNKNOWN)
                .name("name")
                .details("details")
                .build();
    }

    static GitHubManageClusterStatusNode manageClusterStatusNode() {
        return GitHubManageClusterStatusNodeBuilder.builder()
                .hostname("hostname")
                .status(GitHubManageClusterStatusIndicator.UNKNOWN)
                .services(List.of(manageClusterStatusNodeService()))
                .build();
    }

    static GitHubManageClusterStatus manageClusterStatus() {
        return GitHubManageClusterStatusBuilder.builder()
                .status(GitHubManageClusterStatusIndicator.UNKNOWN)
                .nodes(List.of(manageClusterStatusNode()))
                .build();
    }

    static GitHubManageReplicationStatusNodeService manageReplicationStatusNodeService() {
        return GitHubManageReplicationStatusNodeServiceBuilder.builder()
                .status(GitHubManageReplicationStatusIndicator.UNKNOWN)
                .name("name")
                .details("details")
                .build();
    }

    static GitHubManageReplicationStatusNode manageReplicationStatusNode() {
        return GitHubManageReplicationStatusNodeBuilder.builder()
                .hostname("hostname")
                .status(GitHubManageReplicationStatusIndicator.UNKNOWN)
                .services(List.of(manageReplicationStatusNodeService()))
                .build();
    }

    static GitHubManageReplicationStatus manageReplicationStatus() {
        return GitHubManageReplicationStatusBuilder.builder()
                .status(GitHubManageReplicationStatusIndicator.UNKNOWN)
                .nodes(List.of(manageReplicationStatusNode()))
                .build();
    }

    static GitHubManageConfigApplyStatusNode manageConfigApplyStatusNode() {
        return GitHubManageConfigApplyStatusNodeBuilder.builder()
                .runId("run_id")
                .hostname("hostname")
                .running(true)
                .successful(true)
                .build();
    }

    static GitHubManageConfigApplyStatus manageConfigApplyStatus() {
        return GitHubManageConfigApplyStatusBuilder.builder()
                .running(true)
                .successful(true)
                .nodes(List.of(manageConfigApplyStatusNode()))
                .build();
    }

    static GitHubManageRunConfigApplyPayload manageRunConfigApplyPayload() {
        return GitHubManageRunConfigApplyPayloadBuilder.builder()
                .runId("run_id")
                .build();
    }

    static GitHubManageRunConfigApplyResult manageRunConfigApplyResult() {
        return GitHubManageRunConfigApplyResultBuilder.builder().runId("run_id").build();
    }

    static GitHubManageConfigApplyEventsNodeEvent manageConfigApplyEventsNodeEvent() {
        return GitHubManageConfigApplyEventsNodeEventBuilder.builder()
                .timestamp("timestamp")
                .severityText("severity_text")
                .body("body")
                .eventName("event_name")
                .topology("topology")
                .hostname("hostname")
                .configRunId("config_run_id")
                .traceId("trace_id")
                .spanId("span_id")
                .spanParentId("span_parent_id")
                .spanDepth(1)
                .build();
    }

    static GitHubManageConfigApplyEventsNode manageConfigApplyEventsNode() {
        return GitHubManageConfigApplyEventsNodeBuilder.builder()
                .node("node")
                .lastRequestId("last_request_id")
                .events(List.of(manageConfigApplyEventsNodeEvent()))
                .build();
    }

    static GitHubManageConfigApplyEvents manageConfigApplyEvents() {
        return GitHubManageConfigApplyEventsBuilder.builder()
                .nodes(List.of(manageConfigApplyEventsNode()))
                .build();
    }

    static GitHubManageLicenseInfo manageLicenseInfo() {
        return GitHubManageLicenseInfoBuilder.builder()
                .advancedSecurityEnabled(true)
                .advancedSecuritySeats(1)
                .clusterSupport(true)
                .company("company")
                .croquetSupport(true)
                .customTerms(true)
                .evaluation(true)
                .expireAt(Instant.parse("2026-01-01T00:00:00Z"))
                .insightsEnabled(true)
                .insightsExpireAt(Instant.parse("2026-01-01T00:00:00Z"))
                .learningLabEvaluationExpires(Instant.parse("2026-01-01T00:00:00Z"))
                .learningLabSeats(1)
                .perpetual(true)
                .referenceNumber("referenceNumber")
                .seats(1)
                .sshAllowed(true)
                .supportKey("supportKey")
                .unlimitedSeating(true)
                .build();
    }

    static GitHubManageLicenseCheck manageLicenseCheck() {
        return GitHubManageLicenseCheckBuilder.builder()
                .status(GitHubManageLicenseCheckStatus.VALID)
                .build();
    }

    static GitHubManageConfigNodesNode manageConfigNodesNode() {
        return GitHubManageConfigNodesNodeBuilder.builder()
                .hostname("hostname")
                .uuid("uuid")
                .replica(true)
                .clusterRoles(List.of(GitHubManageClusterRole.BLANK))
                .build();
    }

    static GitHubManageConfigNodes manageConfigNodes() {
        return GitHubManageConfigNodesBuilder.builder()
                .topology(GitHubManageClusterTopology.SINGLENODE)
                .nodes(List.of(manageConfigNodesNode()))
                .build();
    }

    static GitHubManageSettingsAvatar manageSettingsAvatar() {
        return GitHubManageSettingsAvatarBuilder.builder()
                .enabled(true)
                .uri("uri")
                .build();
    }

    static GitHubManageSettingsCustomer manageSettingsCustomer() {
        return GitHubManageSettingsCustomerBuilder.builder()
                .name("name")
                .email("email")
                .uuid("uuid")
                .secretKeyData("secret_key_data")
                .publicKeyData("public_key_data")
                .build();
    }

    static GitHubManageSettingsLicense manageSettingsLicense() {
        return GitHubManageSettingsLicenseBuilder.builder()
                .seats(1)
                .evaluation(true)
                .perpetual(true)
                .unlimitedSeating(true)
                .supportKey("support_key")
                .sshAllowed(true)
                .clusterSupport(true)
                .expireAt("expire_at")
                .build();
    }

    static GitHubManageSettingsGithubSsl manageSettingsGithubSsl() {
        return GitHubManageSettingsGithubSslBuilder.builder()
                .enabled(true)
                .cert("cert")
                .key("key")
                .build();
    }

    static GitHubManageSettingsLdapReconciliation manageSettingsLdapReconciliation() {
        return GitHubManageSettingsLdapReconciliationBuilder.builder()
                .user("user")
                .org("org")
                .build();
    }

    static GitHubManageSettingsLdapProfile manageSettingsLdapProfile() {
        return GitHubManageSettingsLdapProfileBuilder.builder()
                .uid("uid")
                .name("name")
                .mail("mail")
                .key("key")
                .build();
    }

    static GitHubManageSettingsLdap manageSettingsLdap() {
        return GitHubManageSettingsLdapBuilder.builder()
                .host("host")
                .port(1)
                .base(List.of("base"))
                .uid("uid")
                .bindDn("bind_dn")
                .password("password")
                .method("method")
                .searchStrategy("search_strategy")
                .userGroups(List.of("user_groups"))
                .adminGroup("admin_group")
                .virtualAttributeEnabled(true)
                .recursiveGroupSearch(true)
                .posixSupport(true)
                .userSyncEmails(true)
                .userSyncKeys(true)
                .userSyncInterval(1)
                .teamSyncInterval(1)
                .syncEnabled(true)
                .reconciliation(manageSettingsLdapReconciliation())
                .profile(manageSettingsLdapProfile())
                .build();
    }

    static GitHubManageSettingsCas manageSettingsCas() {
        return GitHubManageSettingsCasBuilder.builder().url("url").build();
    }

    static GitHubManageSettingsSaml manageSettingsSaml() {
        return GitHubManageSettingsSamlBuilder.builder()
                .ssoUrl("sso_url")
                .certificate("certificate")
                .certificatePath("certificate_path")
                .issuer("issuer")
                .idpInitiatedSso(true)
                .disableAdminDemote(true)
                .build();
    }

    static GitHubManageSettingsGithubOauth manageSettingsGithubOauth() {
        return GitHubManageSettingsGithubOauthBuilder.builder()
                .clientId("client_id")
                .clientSecret("client_secret")
                .organizationName("organization_name")
                .organizationTeam("organization_team")
                .build();
    }

    static GitHubManageSettingsSmtp manageSettingsSmtp() {
        return GitHubManageSettingsSmtpBuilder.builder()
                .enabled(true)
                .address("address")
                .authentication("authentication")
                .port("port")
                .domain("domain")
                .username("username")
                .userName("user_name")
                .enableStarttlsAuto(true)
                .password("password")
                .discardToNoreplyAddress(true)
                .supportAddress("support_address")
                .supportAddressType("support_address_type")
                .noreplyAddress("noreply_address")
                .build();
    }

    static GitHubManageSettingsNtp manageSettingsNtp() {
        return GitHubManageSettingsNtpBuilder.builder()
                .primaryServer("primary_server")
                .secondaryServer("secondary_server")
                .build();
    }

    static GitHubManageSettingsSnmp manageSettingsSnmp() {
        return GitHubManageSettingsSnmpBuilder.builder()
                .enabled(true)
                .community("community")
                .build();
    }

    static GitHubManageSettingsSyslog manageSettingsSyslog() {
        return GitHubManageSettingsSyslogBuilder.builder()
                .enabled(true)
                .server("server")
                .protocolName("protocol_name")
                .build();
    }

    static GitHubManageSettingsPages manageSettingsPages() {
        return GitHubManageSettingsPagesBuilder.builder().enabled(true).build();
    }

    static GitHubManageSettingsCollectd manageSettingsCollectd() {
        return GitHubManageSettingsCollectdBuilder.builder()
                .enabled(true)
                .server("server")
                .port(1)
                .encryption("encryption")
                .username("username")
                .password("password")
                .build();
    }

    static GitHubManageSettingsMapping manageSettingsMapping() {
        return GitHubManageSettingsMappingBuilder.builder()
                .enabled(true)
                .tileserver("tileserver")
                .basemap("basemap")
                .token("token")
                .build();
    }

    static GitHubManageSettingsPrometheus manageSettingsPrometheus() {
        return GitHubManageSettingsPrometheusBuilder.builder()
                .enabled(true)
                .trustedIps("trusted_ips")
                .build();
    }

    static GitHubManageSettings manageSettings() {
        return GitHubManageSettingsBuilder.builder()
                .privateMode(true)
                .publicPages(true)
                .subdomainIsolation(true)
                .signupEnabled(true)
                .githubHostname("github_hostname")
                .identiconsHost("identicons_host")
                .httpProxy("http_proxy")
                .authMode("auth_mode")
                .expireSessions(true)
                .adminPassword("admin_password")
                .configurationId(1)
                .configurationRunCount(1)
                .avatar(manageSettingsAvatar())
                .customer(manageSettingsCustomer())
                .license(manageSettingsLicense())
                .githubSsl(manageSettingsGithubSsl())
                .ldap(manageSettingsLdap())
                .cas(manageSettingsCas())
                .saml(manageSettingsSaml())
                .githubOauth(manageSettingsGithubOauth())
                .smtp(manageSettingsSmtp())
                .ntp(manageSettingsNtp())
                .timezone("timezone")
                .snmp(manageSettingsSnmp())
                .syslog(manageSettingsSyslog())
                .assets("assets")
                .pages(manageSettingsPages())
                .collectd(manageSettingsCollectd())
                .mapping(manageSettingsMapping())
                .loadBalancer("load_balancer")
                .prometheus(manageSettingsPrometheus())
                .build();
    }

    static GitHubManageMaintenanceStatusConnectionService manageMaintenanceStatusConnectionService() {
        return GitHubManageMaintenanceStatusConnectionServiceBuilder.builder()
                .name("name")
                .number(1)
                .build();
    }

    static GitHubManageMaintenanceStatus manageMaintenanceStatus() {
        return GitHubManageMaintenanceStatusBuilder.builder()
                .hostname("hostname")
                .uuid("uuid")
                .status(GitHubManageMaintenanceStatusStatus.ON)
                .scheduledTime("scheduled_time")
                .connectionServices(List.of(manageMaintenanceStatusConnectionService()))
                .canUnsetMaintenance(true)
                .ipExceptionList(List.of("ip_exception_list"))
                .maintenanceModeMessage("maintenance_mode_message")
                .build();
    }

    static GitHubManageSetMaintenancePayload manageSetMaintenancePayload() {
        return GitHubManageSetMaintenancePayloadBuilder.builder()
                .enabled(true)
                .uuid("uuid")
                .when(Instant.parse("2026-01-01T00:00:00Z"))
                .ipExceptionList(List.of("ip_exception_list"))
                .maintenanceModeMessage("maintenance_mode_message")
                .build();
    }

    static GitHubManageSetMaintenanceResult manageSetMaintenanceResult() {
        return GitHubManageSetMaintenanceResultBuilder.builder()
                .hostname("hostname")
                .uuid("uuid")
                .message("message")
                .error("error")
                .build();
    }

    static GitHubManageApplyUpgradePayload manageApplyUpgradePayload() {
        return GitHubManageApplyUpgradePayloadBuilder.builder()
                .upgradePackageFile("upgrade_package_file")
                .version("version")
                .targetPartition("target_partition")
                .phase(GitHubManageApplyUpgradePayloadPhase.PRE_UPGRADE)
                .skipReboot(true)
                .uuid("uuid")
                .build();
    }

    static GitHubManageApplyUpgradeResult manageApplyUpgradeResult() {
        return GitHubManageApplyUpgradeResultBuilder.builder()
                .hostname("hostname")
                .uuid("uuid")
                .message("message")
                .error("error")
                .build();
    }

    static GitHubManageUpgradeDownloadStatusNode manageUpgradeDownloadStatusNode() {
        return GitHubManageUpgradeDownloadStatusNodeBuilder.builder()
                .hostname("hostname")
                .uuid("uuid")
                .downloadId("download_id")
                .packageName("package_name")
                .status(GitHubManageUpgradeDownloadStatusNodeStatus.NOT_STARTED)
                .progress("progress")
                .error("error")
                .build();
    }

    static GitHubManageUpgradeDownloadStatusOrchestration manageUpgradeDownloadStatusOrchestration() {
        return GitHubManageUpgradeDownloadStatusOrchestrationBuilder.builder()
                .phase(GitHubManageUpgradeDownloadStatusOrchestrationPhase.RUNNING)
                .errorMessage("error_message")
                .maxParallel(1)
                .queued(1)
                .inFlight(1)
                .completed(1)
                .failed(1)
                .total(1)
                .build();
    }

    static GitHubManageUpgradeDownloadStatus manageUpgradeDownloadStatus() {
        return GitHubManageUpgradeDownloadStatusBuilder.builder()
                .downloadId("download_id")
                .packageName("package_name")
                .status(GitHubManageUpgradeDownloadStatusStatus.NOT_STARTED)
                .errorMessage("error_message")
                .nodes(List.of(manageUpgradeDownloadStatusNode()))
                .orchestration(manageUpgradeDownloadStatusOrchestration())
                .build();
    }

    static GitHubManageUpgradeStatusStep manageUpgradeStatusStep() {
        return GitHubManageUpgradeStatusStepBuilder.builder()
                .phase("phase")
                .step("step")
                .status("status")
                .startedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .endedAt(Instant.parse("2026-01-01T00:00:00Z"))
                .error("error")
                .build();
    }

    static GitHubManageUpgradeStatus manageUpgradeStatus() {
        return GitHubManageUpgradeStatusBuilder.builder()
                .hostname("hostname")
                .uuid("uuid")
                .releaseVersion("release_version")
                .releaseSha("release_sha")
                .status(GitHubManageUpgradeStatusStatus.NOT_STARTED)
                .preUpgradeStatus(GitHubManageUpgradeStatusPreUpgradeStatus.NOT_STARTED)
                .upgradeLastCompletedStep("upgrade_last_completed_step")
                .isRunning(true)
                .failed(true)
                .steps(List.of(manageUpgradeStatusStep()))
                .messages(List.of("messages"))
                .error("error")
                .build();
    }

    static GitHubManageVersionVersion manageVersionVersion() {
        return GitHubManageVersionVersionBuilder.builder()
                .version("3.22.0")
                .platform(GitHubManageVersionVersionPlatform.AMI)
                .buildId("0123abcd")
                .buildDate("build_date")
                .build();
    }

    static GitHubManageVersion manageVersion() {
        return GitHubManageVersionBuilder.builder()
                .hostname("hostname")
                .version(manageVersionVersion())
                .build();
    }

    static GitHubGetConfigNodesFilter getConfigNodesFilter() {
        return GitHubGetConfigNodesFilterBuilder.builder()
                .uuid("uuid")
                .clusterRoles("cluster_roles")
                .build();
    }

    static GitHubGetManageMaintenanceFilter getManageMaintenanceFilter() {
        return GitHubGetManageMaintenanceFilterBuilder.builder()
                .uuid("uuid")
                .clusterRoles("cluster_roles")
                .build();
    }

    static GitHubGetReplicationStatusFilter getReplicationStatusFilter() {
        return GitHubGetReplicationStatusFilterBuilder.builder()
                .uuid("uuid")
                .clusterRoles("cluster_roles")
                .build();
    }

    static GitHubGetVersionFilter getVersionFilter() {
        return GitHubGetVersionFilterBuilder.builder()
                .uuid("uuid")
                .clusterRoles("cluster_roles")
                .build();
    }

    static GitHubGetManageConfigApplyStatusFilter getManageConfigApplyStatusFilter() {
        return GitHubGetManageConfigApplyStatusFilterBuilder.builder()
                .runId("run_id")
                .build();
    }

    static GitHubGetManageConfigApplyEventsFilter getManageConfigApplyEventsFilter() {
        return GitHubGetManageConfigApplyEventsFilterBuilder.builder()
                .lastRequestId("last_request_id")
                .build();
    }

    static GitHubGetManageUpgradeDownloadStatusFilter getManageUpgradeDownloadStatusFilter() {
        return GitHubGetManageUpgradeDownloadStatusFilterBuilder.builder()
                .uuid("uuid")
                .build();
    }

    static GitHubGetManageUpgradeStatusFilter getManageUpgradeStatusFilter() {
        return GitHubGetManageUpgradeStatusFilterBuilder.builder()
                .uuid("uuid")
                .isVerbose(true)
                .build();
    }

    static GitHubManageConfigInitPayload manageConfigInitPayload() {
        return GitHubManageConfigInitPayloadBuilder.builder()
                .license("license")
                .password("password")
                .build();
    }

    static GitHubManageUploadLicensePayload manageUploadLicensePayload() {
        return GitHubManageUploadLicensePayloadBuilder.builder()
                .license("license")
                .build();
    }

    static GitHubManageDownloadUpgradePayload manageDownloadUpgradePayload() {
        return GitHubManageDownloadUpgradePayloadBuilder.builder()
                .version("3.22.1")
                .force(true)
                .uuids(List.of("uuids"))
                .maxParallel(1)
                .nodeTimeout("1h")
                .maxAttempts(1)
                .build();
    }

    static GitHubManageDownloadUpgradeNodeResult manageDownloadUpgradeNodeResult() {
        return GitHubManageDownloadUpgradeNodeResultBuilder.builder()
                .hostname("hostname")
                .uuid("uuid")
                .downloadId("download_id")
                .message("message")
                .error("error")
                .build();
    }

    static GitHubManageDownloadUpgradeOrchestration manageDownloadUpgradeOrchestration() {
        return GitHubManageDownloadUpgradeOrchestrationBuilder.builder()
                .message("download orchestration started")
                .downloadId("7bba0103-31f4-48ea-9d4d-d7a2a700fe9f")
                .maxParallel(1)
                .totalNodes(1)
                .build();
    }

    static GitHubLicenseUploadFilter licenseUploadFilter() {
        return GitHubLicenseUploadFilterBuilder.builder().apply(true).build();
    }

    static GitHubLicenseInfo licenseInfo() {
        return GitHubLicenseInfoBuilder.builder()
                .seats("unlimited")
                .seatsUsed(1)
                .seatsAvailable("unlimited")
                .kind("kind")
                .daysUntilExpiration(1)
                .expireAt("expire_at")
                .build();
    }
}
