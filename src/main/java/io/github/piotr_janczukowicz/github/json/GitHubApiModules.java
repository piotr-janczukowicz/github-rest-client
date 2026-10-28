package io.github.piotr_janczukowicz.github.json;

import io.github.piotr_janczukowicz.github.GitHubErrorBody;
import io.github.piotr_janczukowicz.github.api.actions.GitHubPendingDeploymentReviewer;
import io.github.piotr_janczukowicz.github.api.activity.GitHubEvent;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegrationInstallationRequestAccount;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegrationOwner;
import io.github.piotr_janczukowicz.github.api.apps.GitHubIntegrationPermissions;
import io.github.piotr_janczukowicz.github.api.branches.GitHubStatusCheckContextsPayload;
import io.github.piotr_janczukowicz.github.api.branches.GitHubTeamAccessRestrictionsPayload;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubCopilotContentExclusionRule;
import io.github.piotr_janczukowicz.github.api.copilot.GitHubCopilotEnterpriseContentExclusionRule;
import io.github.piotr_janczukowicz.github.api.copilotspaces.GitHubCopilotSpaceOwner;
import io.github.piotr_janczukowicz.github.api.deployments.GitHubDeploymentPayloadValue;
import io.github.piotr_janczukowicz.github.api.deployments.GitHubEnvironmentReviewer;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubCreateAuditLogStreamPayload;
import io.github.piotr_janczukowicz.github.api.enterpriseadmin.GitHubUpdateAuditLogStreamPayload;
import io.github.piotr_janczukowicz.github.api.issues.GitHubAddAssigneesAssignee;
import io.github.piotr_janczukowicz.github.api.issues.GitHubAddLabelsLabel;
import io.github.piotr_janczukowicz.github.api.issues.GitHubCreateIssueLabel;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueFieldValueWire;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueLabel;
import io.github.piotr_janczukowicz.github.api.issues.GitHubIssueSuggestionTargetValue;
import io.github.piotr_janczukowicz.github.api.issues.GitHubSetLabelsLabel;
import io.github.piotr_janczukowicz.github.api.issues.GitHubUpdateIssueAssignee;
import io.github.piotr_janczukowicz.github.api.issues.GitHubUpdateIssueLabel;
import io.github.piotr_janczukowicz.github.api.issues.GitHubUpdateIssueType;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubEnterprise;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubInstallationAccount;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrgRulesetConditions;
import io.github.piotr_janczukowicz.github.api.organizations.GitHubOrganizationSimple;
import io.github.piotr_janczukowicz.github.api.projects.GitHubAddFieldForUserPayload;
import io.github.piotr_janczukowicz.github.api.projects.GitHubAddProjectFieldPayload;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectItemContent;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectItemSimple;
import io.github.piotr_janczukowicz.github.api.projects.GitHubProjectViewSort;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubContentResult;
import io.github.piotr_janczukowicz.github.api.scim.GitHubScimOperationValue;
import io.github.piotr_janczukowicz.github.api.scim.GitHubUpdateAttributeForUserValue;
import io.github.piotr_janczukowicz.github.api.secretscanning.GitHubSecretScanningLocation;
import io.github.piotr_janczukowicz.github.api.teams.GitHubTeam;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserView;
import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.json.jackson.ArrayOrWrappedAdapters;
import io.github.piotr_janczukowicz.json.jackson.OptionalNullDeserializer;
import io.github.piotr_janczukowicz.json.jackson.OptionalNullMixin;
import io.github.piotr_janczukowicz.json.jackson.PolymorphicObjectAdapters;
import io.github.piotr_janczukowicz.json.jackson.StringOrObjectAdapters;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.module.SimpleModule;

/** Builds the Jackson module that registers every GitHub REST adapter used by the default mapper. */
final class GitHubApiModules {

    private static final TypeReference<Map<String, Object>> OBJECT_MAP = new TypeReference<>() {};
    private static final String LOGIN = "login";
    private static final String SLUG = "slug";
    private static final String HOOKS_URL = "hooks_url";

    private GitHubApiModules() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Module with type adapters and mixins for GitHub wire shapes. The default codec registers it.
     *
     * @return configured module
     */
    public static SimpleModule create() {
        final SimpleModule module = new SimpleModule("GitHubApi");
        registerStringOrObject(module);
        registerPolymorphicOwners(module);
        registerArrayOrWrapped(module);
        registerRemaining(module);
        GitHubApiJacksonMixins.register(module);
        return module;
    }

    static Object unwrapSealed(final Object owner) {
        return switch (owner) {
            case GitHubIntegrationOwner.SimpleUser(final GitHubSimpleUser user) -> user;
            case GitHubIntegrationOwner.Enterprise(final GitHubEnterprise enterprise) -> enterprise;
            case GitHubInstallationAccount.SimpleUser(final GitHubSimpleUser user) -> user;
            case GitHubInstallationAccount.Enterprise(final GitHubEnterprise enterprise) -> enterprise;
            case GitHubIntegrationInstallationRequestAccount.SimpleUser(final GitHubSimpleUser user) -> user;
            case GitHubIntegrationInstallationRequestAccount.Enterprise(final GitHubEnterprise enterprise) ->
                enterprise;
            case GitHubEnvironmentReviewer.SimpleUser(final GitHubSimpleUser user) -> user;
            case GitHubEnvironmentReviewer.Team(final GitHubTeam team) -> team;
            case GitHubPendingDeploymentReviewer.SimpleUser(final GitHubSimpleUser user) -> user;
            case GitHubPendingDeploymentReviewer.Team(final GitHubTeam team) -> team;
            case GitHubCopilotSpaceOwner.SimpleUser(final GitHubSimpleUser user) -> user;
            case GitHubCopilotSpaceOwner.Organization(final GitHubOrganizationSimple organization) -> organization;
            default -> throw new IllegalArgumentException("Unknown GitHub sealed owner shape");
        };
    }

    private static void registerStringOrObject(final SimpleModule module) {
        StringOrObjectAdapters.register(
                module,
                GitHubCreateIssueLabel.class,
                GitHubCreateIssueLabel::labelName,
                GitHubCreateIssueLabel::labelDetails,
                GitHubCreateIssueLabel::ofName,
                GitHubCreateIssueLabel::ofDetails,
                GitHubCreateIssueLabel.Details.class);
        StringOrObjectAdapters.register(
                module,
                GitHubUpdateIssueLabel.class,
                GitHubUpdateIssueLabel::labelName,
                GitHubUpdateIssueLabel::labelDetails,
                GitHubUpdateIssueLabel::ofName,
                GitHubUpdateIssueLabel::ofDetails,
                GitHubUpdateIssueLabel.Details.class);
        StringOrObjectAdapters.register(
                module,
                GitHubAddLabelsLabel.class,
                GitHubAddLabelsLabel::labelName,
                GitHubAddLabelsLabel::labelDetails,
                GitHubAddLabelsLabel::ofName,
                GitHubAddLabelsLabel::ofDetails,
                GitHubAddLabelsLabel.Details.class);
        StringOrObjectAdapters.register(
                module,
                GitHubSetLabelsLabel.class,
                GitHubSetLabelsLabel::labelName,
                GitHubSetLabelsLabel::labelDetails,
                GitHubSetLabelsLabel::ofName,
                GitHubSetLabelsLabel::ofDetails,
                GitHubSetLabelsLabel.Details.class);
        StringOrObjectAdapters.register(
                module,
                GitHubAddAssigneesAssignee.class,
                GitHubAddAssigneesAssignee::assigneeLogin,
                GitHubAddAssigneesAssignee::assigneeDetails,
                GitHubAddAssigneesAssignee::ofLogin,
                GitHubAddAssigneesAssignee::ofDetails,
                GitHubAddAssigneesAssignee.Details.class);
        StringOrObjectAdapters.register(
                module,
                GitHubUpdateIssueAssignee.class,
                GitHubUpdateIssueAssignee::assigneeLogin,
                GitHubUpdateIssueAssignee::assigneeDetails,
                GitHubUpdateIssueAssignee::ofLogin,
                GitHubUpdateIssueAssignee::ofDetails,
                GitHubUpdateIssueAssignee.Details.class);
        StringOrObjectAdapters.register(
                module,
                GitHubUpdateIssueType.class,
                GitHubUpdateIssueType::typeName,
                GitHubUpdateIssueType::typeDetails,
                GitHubUpdateIssueType::ofName,
                GitHubUpdateIssueType::ofDetails,
                GitHubUpdateIssueType.Details.class);
        StringOrObjectAdapters.register(
                module,
                GitHubDeploymentPayloadValue.class,
                GitHubDeploymentPayloadValue::textValue,
                GitHubDeploymentPayloadValue::objectValue,
                GitHubDeploymentPayloadValue::ofText,
                GitHubDeploymentPayloadValue::ofObject,
                OBJECT_MAP);
    }

    private static void registerPolymorphicOwners(final SimpleModule module) {
        PolymorphicObjectAdapters.register(
                module,
                GitHubIntegrationOwner.class,
                GitHubApiModules::unwrapSealed,
                "Unknown GitHub integration owner shape",
                List.of(
                        PolymorphicObjectAdapters.branch(
                                SLUG, GitHubEnterprise.class, GitHubIntegrationOwner.Enterprise::new),
                        PolymorphicObjectAdapters.branch(
                                LOGIN, GitHubSimpleUser.class, GitHubIntegrationOwner.SimpleUser::new)));
        PolymorphicObjectAdapters.register(
                module,
                GitHubInstallationAccount.class,
                GitHubApiModules::unwrapSealed,
                "Unknown GitHub installation account shape",
                List.of(
                        PolymorphicObjectAdapters.branch(
                                SLUG, GitHubEnterprise.class, GitHubInstallationAccount.Enterprise::new),
                        PolymorphicObjectAdapters.branch(
                                LOGIN, GitHubSimpleUser.class, GitHubInstallationAccount.SimpleUser::new)));
        PolymorphicObjectAdapters.register(
                module,
                GitHubIntegrationInstallationRequestAccount.class,
                GitHubApiModules::unwrapSealed,
                "Unknown GitHub integration installation request account shape",
                List.of(
                        PolymorphicObjectAdapters.branch(
                                SLUG,
                                GitHubEnterprise.class,
                                GitHubIntegrationInstallationRequestAccount.Enterprise::new),
                        PolymorphicObjectAdapters.branch(
                                LOGIN,
                                GitHubSimpleUser.class,
                                GitHubIntegrationInstallationRequestAccount.SimpleUser::new)));
        PolymorphicObjectAdapters.register(
                module,
                GitHubEnvironmentReviewer.class,
                GitHubApiModules::unwrapSealed,
                "Unknown GitHub environment reviewer shape",
                List.of(
                        PolymorphicObjectAdapters.branch(SLUG, GitHubTeam.class, GitHubEnvironmentReviewer.Team::new),
                        PolymorphicObjectAdapters.branch(
                                LOGIN, GitHubSimpleUser.class, GitHubEnvironmentReviewer.SimpleUser::new)));
        PolymorphicObjectAdapters.register(
                module,
                GitHubPendingDeploymentReviewer.class,
                GitHubApiModules::unwrapSealed,
                "Unknown GitHub pending deployment reviewer shape",
                List.of(
                        PolymorphicObjectAdapters.branch(
                                SLUG, GitHubTeam.class, GitHubPendingDeploymentReviewer.Team::new),
                        PolymorphicObjectAdapters.branch(
                                LOGIN, GitHubSimpleUser.class, GitHubPendingDeploymentReviewer.SimpleUser::new)));
        PolymorphicObjectAdapters.register(
                module,
                GitHubCopilotSpaceOwner.class,
                GitHubApiModules::unwrapSealed,
                "Unknown GitHub Copilot Space owner shape",
                List.of(
                        PolymorphicObjectAdapters.branch(
                                HOOKS_URL, GitHubOrganizationSimple.class, GitHubCopilotSpaceOwner.Organization::new),
                        PolymorphicObjectAdapters.branch(
                                LOGIN, GitHubSimpleUser.class, GitHubCopilotSpaceOwner.SimpleUser::new)));
    }

    private static void registerArrayOrWrapped(final SimpleModule module) {
        ArrayOrWrappedAdapters.register(
                module,
                GitHubStatusCheckContextsPayload.class,
                "contexts",
                GitHubStatusCheckContextsPayload::contextList,
                GitHubStatusCheckContextsPayload::contexts,
                GitHubStatusCheckContextsPayload::withContextList,
                GitHubStatusCheckContextsPayload::withContexts);
        ArrayOrWrappedAdapters.register(
                module,
                GitHubTeamAccessRestrictionsPayload.class,
                "teams",
                GitHubTeamAccessRestrictionsPayload::teamSlugs,
                GitHubTeamAccessRestrictionsPayload::teams,
                GitHubTeamAccessRestrictionsPayload::withTeamSlugs,
                GitHubTeamAccessRestrictionsPayload::withTeams);
    }

    private static void registerRemaining(final SimpleModule module) {
        module.addDeserializer(Instant.class, new GitHubInstantDeserializer());
        module.addDeserializer(GitHubContentResult.class, new GitHubContentResultDeserializer());
        module.addDeserializer(GitHubErrorBody.class, new GitHubErrorBodyDeserializer());
        module.addDeserializer(GitHubEvent.class, new GitHubEventDeserializer());
        module.addDeserializer(GitHubUserView.class, new GitHubUserViewDeserializer());
        module.addDeserializer(GitHubScimOperationValue.class, new GitHubScimOperationValueDeserializer());
        module.addDeserializer(
                GitHubUpdateAttributeForUserValue.class, new GitHubUpdateAttributeForUserValueDeserializer());
        module.addDeserializer(GitHubOrgRulesetConditions.class, new GitHubOrgRulesetConditionsDeserializer());
        module.addSerializer(GitHubOrgRulesetConditions.class, new GitHubOrgRulesetConditionsSerializer());
        module.addDeserializer(GitHubSecretScanningLocation.class, new GitHubSecretScanningLocationDeserializer());
        module.addDeserializer(GitHubIssueFieldValueWire.class, new GitHubIssueFieldValueWireDeserializer());
        module.addSerializer(GitHubIssueFieldValueWire.class, new GitHubIssueFieldValueWireSerializer());
        module.addDeserializer(
                GitHubIssueSuggestionTargetValue.class, new GitHubIssueSuggestionTargetValueDeserializer());
        module.addSerializer(GitHubIssueSuggestionTargetValue.class, new GitHubIssueSuggestionTargetValueSerializer());
        module.addDeserializer(GitHubProjectViewSort.class, new GitHubProjectViewSortDeserializer());
        module.addSerializer(GitHubProjectItemContent.class, new GitHubProjectItemContentSerializer());
        module.addDeserializer(GitHubProjectItemSimple.class, new GitHubProjectItemSimpleDeserializer());
        module.addDeserializer(GitHubIssueLabel.class, new GitHubIssueLabelDeserializer());
        module.addDeserializer(GitHubIntegrationPermissions.class, new GitHubIntegrationPermissionsDeserializer());
        module.addDeserializer(GitHubAddProjectFieldPayload.class, new GitHubAddProjectFieldPayloadDeserializer());
        module.addDeserializer(GitHubAddFieldForUserPayload.class, new GitHubAddFieldForUserPayloadDeserializer());
        module.addDeserializer(
                GitHubCreateAuditLogStreamPayload.class, new GitHubCreateAuditLogStreamPayloadDeserializer());
        module.addDeserializer(
                GitHubUpdateAuditLogStreamPayload.class, new GitHubUpdateAuditLogStreamPayloadDeserializer());
        module.addDeserializer(
                GitHubCopilotContentExclusionRule.class, new GitHubCopilotContentExclusionRuleDeserializer());
        module.addSerializer(
                GitHubCopilotContentExclusionRule.class, new GitHubCopilotContentExclusionRuleSerializer());
        module.addDeserializer(
                GitHubCopilotEnterpriseContentExclusionRule.class,
                new GitHubCopilotEnterpriseContentExclusionRuleDeserializer());
        module.addSerializer(
                GitHubCopilotEnterpriseContentExclusionRule.class,
                new GitHubCopilotEnterpriseContentExclusionRuleSerializer());
        module.addDeserializer(OptionalNull.class, new OptionalNullDeserializer());
        module.setMixInAnnotation(OptionalNull.class, OptionalNullMixin.class);
    }
}
