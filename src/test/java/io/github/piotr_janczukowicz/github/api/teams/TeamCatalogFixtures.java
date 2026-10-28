package io.github.piotr_janczukowicz.github.api.teams;

import io.github.piotr_janczukowicz.github.api.licenses.GitHubLicenseSimple;
import io.github.piotr_janczukowicz.github.api.repositories.GitHubRepositoryPermissions;
import io.github.piotr_janczukowicz.github.api.users.GitHubSimpleUser;
import io.github.piotr_janczukowicz.github.api.users.GitHubUserViewType;
import io.github.piotr_janczukowicz.github.api.users.UsersCatalogFixtures;
import java.time.Instant;
import java.util.List;

public final class TeamCatalogFixtures {

    private TeamCatalogFixtures() {}

    public static GitHubTeam team() {
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
                teamParentInTeamResponse());
    }

    public static GitHubEnterpriseTeam enterpriseTeam() {
        return new GitHubEnterpriseTeam(
                1L,
                "Justice League",
                "A great team.",
                "justice-league",
                "https://api.github.com/enterprises/octo-enterprise/teams/justice-league",
                "disabled",
                "disabled",
                "62ab9291-fae2-468e-974b-7e45096d5021",
                "Octo group",
                "https://github.com/enterprises/octo-enterprise/teams/justice-league",
                "https://api.github.com/enterprises/octo-enterprise/teams/justice-league/members{/member}",
                Instant.parse("2023-01-01T00:00:00Z"),
                Instant.parse("2023-01-01T12:00:00Z"),
                GitHubEnterpriseTeamNotificationSetting.NOTIFICATIONS_ENABLED);
    }

    static GitHubTeamSimple teamParentInTeamResponse() {
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

    static GitHubTeamFull teamFull() {
        return new GitHubTeamFull(
                42L,
                "MDQ6VGVhbTE=",
                "https://api.github.com/organizations/1/team/1",
                "https://github.com/orgs/rails/teams/core",
                "Developers",
                "justice-league",
                "A great team.",
                GitHubTeamPrivacy.CLOSED,
                GitHubTeamNotificationSetting.NOTIFICATIONS_ENABLED,
                "push",
                "https://api.github.com/organizations/1/team/1/members{/member}",
                "https://api.github.com/organizations/1/team/1/repos",
                TeamSimpleCatalogFixtures.sampleParentTeam(),
                3L,
                10L,
                Instant.parse("2017-07-14T16:53:42Z"),
                Instant.parse("2017-08-17T12:37:15Z"),
                TeamOrganizationCatalogFixtures.sampleOrganization(),
                "cn=developers,ou=groups,dc=github,dc=com",
                GitHubTeamOwnershipType.ORGANIZATION,
                1L,
                2L);
    }

    static GitHubTeamRepository teamRepository() {
        return new GitHubTeamRepository(
                42L,
                "MDEwOlJlcG9zaXRvcnkxMjk2MjY5",
                "Team Environment",
                "octocat/Hello-World",
                new GitHubLicenseSimple(
                        "mit",
                        "MIT License",
                        "https://api.github.com/licenses/mit",
                        "MIT",
                        "MDc6TGljZW5zZW1pdA==",
                        "https://choosealicense.com/licenses/mit/"),
                9L,
                new GitHubTeamRepositoryPermissions(true, true, false, true, false),
                "admin",
                UsersCatalogFixtures.octocat(),
                false,
                "https://github.com/octocat/Hello-World",
                "This your first repo!",
                false,
                "https://api.github.com/repos/octocat/Hello-World",
                "http://api.github.com/repos/octocat/Hello-World/{archive_format}{/ref}",
                "http://api.github.com/repos/octocat/Hello-World/assignees{/user}",
                "http://api.github.com/repos/octocat/Hello-World/git/blobs{/sha}",
                "http://api.github.com/repos/octocat/Hello-World/branches{/branch}",
                "http://api.github.com/repos/octocat/Hello-World/collaborators{/collaborator}",
                "http://api.github.com/repos/octocat/Hello-World/comments{/number}",
                "http://api.github.com/repos/octocat/Hello-World/commits{/sha}",
                "http://api.github.com/repos/octocat/Hello-World/compare/{base}...{head}",
                "http://api.github.com/repos/octocat/Hello-World/contents/{+path}",
                "http://api.github.com/repos/octocat/Hello-World/contributors",
                "http://api.github.com/repos/octocat/Hello-World/deployments",
                "http://api.github.com/repos/octocat/Hello-World/downloads",
                "http://api.github.com/repos/octocat/Hello-World/events",
                "http://api.github.com/repos/octocat/Hello-World/forks",
                "http://api.github.com/repos/octocat/Hello-World/git/commits{/sha}",
                "http://api.github.com/repos/octocat/Hello-World/git/refs{/sha}",
                "http://api.github.com/repos/octocat/Hello-World/git/tags{/sha}",
                "git:github.com/octocat/Hello-World.git",
                "http://api.github.com/repos/octocat/Hello-World/issues/comments{/number}",
                "http://api.github.com/repos/octocat/Hello-World/issues/events{/number}",
                "http://api.github.com/repos/octocat/Hello-World/issues{/number}",
                "http://api.github.com/repos/octocat/Hello-World/keys{/key_id}",
                "http://api.github.com/repos/octocat/Hello-World/labels{/name}",
                "http://api.github.com/repos/octocat/Hello-World/languages",
                "http://api.github.com/repos/octocat/Hello-World/merges",
                "http://api.github.com/repos/octocat/Hello-World/milestones{/number}",
                "http://api.github.com/repos/octocat/Hello-World/notifications{?since,all,participating}",
                "http://api.github.com/repos/octocat/Hello-World/pulls{/number}",
                "http://api.github.com/repos/octocat/Hello-World/releases{/id}",
                "git@github.com:octocat/Hello-World.git",
                "http://api.github.com/repos/octocat/Hello-World/stargazers",
                "http://api.github.com/repos/octocat/Hello-World/statuses/{sha}",
                "http://api.github.com/repos/octocat/Hello-World/subscribers",
                "http://api.github.com/repos/octocat/Hello-World/subscription",
                "http://api.github.com/repos/octocat/Hello-World/tags",
                "http://api.github.com/repos/octocat/Hello-World/teams",
                "http://api.github.com/repos/octocat/Hello-World/git/trees{/sha}",
                "https://github.com/octocat/Hello-World.git",
                "https://github.com/octocat/Hello-World-mirror.git",
                "http://api.github.com/repos/octocat/Hello-World/hooks",
                "https://svn.github.com/octocat/Hello-World",
                "https://github.com",
                "C",
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
                true,
                false,
                80L,
                0L,
                0L,
                80L,
                "master");
    }

    public static GitHubTeamMember teamMember() {
        final GitHubSimpleUser octocat = UsersCatalogFixtures.octocat();
        return new GitHubTeamMember(
                octocat.name(),
                octocat.email(),
                octocat.login(),
                octocat.id(),
                octocat.nodeId(),
                octocat.avatarUrl(),
                octocat.gravatarId(),
                octocat.url(),
                octocat.htmlUrl(),
                octocat.followersUrl(),
                octocat.followingUrl(),
                octocat.gistsUrl(),
                octocat.starredUrl(),
                octocat.subscriptionsUrl(),
                octocat.organizationsUrl(),
                octocat.reposUrl(),
                octocat.eventsUrl(),
                octocat.receivedEventsUrl(),
                octocat.type(),
                octocat.siteAdmin(),
                octocat.starredAt(),
                GitHubUserViewType.PUBLIC,
                GitHubTeamRole.MEMBER,
                false);
    }

    static GitHubTeamMembership teamMembership() {
        return new GitHubTeamMembership(
                "https://api.github.com/organizations/1/team/1/memberships/octocat",
                GitHubTeamRole.MEMBER,
                GitHubTeamMembershipState.ACTIVE);
    }

    static GitHubCreateTeamPayload createTeamPayload() {
        return GitHubCreateTeamPayloadBuilder.builder().name("Developers").build();
    }

    static GitHubUpdateTeamLegacyPayload updateTeamLegacyPayload() {
        return GitHubUpdateTeamLegacyPayloadBuilder.builder().name("Developers").build();
    }

    static GitHubUpdateTeamPayload updateTeamPayload() {
        return GitHubUpdateTeamPayloadBuilder.builder().name("Developers").build();
    }

    static GitHubUpsertTeamMembershipPayload upsertTeamMembershipPayload() {
        return GitHubUpsertTeamMembershipPayloadBuilder.builder()
                .role(GitHubTeamRole.MAINTAINER)
                .build();
    }

    static GitHubUpsertTeamRepositoryPermissionPayload upsertTeamRepositoryPermissionPayload() {
        return GitHubUpsertTeamRepositoryPermissionPayloadBuilder.builder()
                .permission("push")
                .build();
    }

    public static GitHubOrganizationInvitation organizationInvitation() {
        return new GitHubOrganizationInvitation(
                1L,
                "octocat",
                "octocat@github.com",
                "direct_member",
                "2016-05-30T19:18:10Z",
                "2016-05-31T10:00:00Z",
                "user_already_org_member",
                UsersCatalogFixtures.octocat(),
                1L,
                "MDIyOk9yZ2FuaXphdGlvbkludml0YXRpb24x",
                "https://api.github.com/organizations/16/invitations/1/teams",
                "member");
    }

    static GitHubExternalGroup externalGroup() {
        return new GitHubExternalGroup(
                1L,
                "group-azuread-test",
                "2021-01-03 22:27:15:000 -700",
                List.of(externalGroupTeam()),
                List.of(externalGroupMember()));
    }

    static GitHubExternalGroupTeam externalGroupTeam() {
        return new GitHubExternalGroupTeam(1L, "team-test");
    }

    static GitHubExternalGroupMember externalGroupMember() {
        return new GitHubExternalGroupMember(1L, "mona-lisa_eocsaxrs", "Mona Lisa", "mona_lisa@github.com");
    }

    static GitHubExternalGroups externalGroups() {
        return new GitHubExternalGroups(List.of(externalGroupsGroup()));
    }

    static GitHubExternalGroupsGroup externalGroupsGroup() {
        return new GitHubExternalGroupsGroup(1L, "group-azuread-test", "2021-01-03 22:27:15:000 -700");
    }

    static GitHubGroupMapping groupMapping() {
        return new GitHubGroupMapping(List.of(groupMappingGroup()));
    }

    static GitHubGroupMappingGroup groupMappingGroup() {
        return new GitHubGroupMappingGroup(
                "111a1a11-aaa1-1aaa-11a1-a1a1a1a1a1aa",
                "saml-azuread-test",
                "A group of Developers working on AzureAD SAML SSO",
                "synced",
                "2021-01-03T22:27:15Z");
    }

    static GitHubLinkExternalIdpGroupToTeamForOrgPayload linkExternalIdpGroupToTeamForOrgPayload() {
        return new GitHubLinkExternalIdpGroupToTeamForOrgPayload(123L);
    }

    static GitHubCreateOrUpdateIdpGroupConnectionsInOrgPayload createOrUpdateIdpGroupConnectionsInOrgPayload() {
        return new GitHubCreateOrUpdateIdpGroupConnectionsInOrgPayload(
                List.of(createOrUpdateIdpGroupConnectionsInOrgGroup()));
    }

    static GitHubCreateOrUpdateIdpGroupConnectionsInOrgGroup createOrUpdateIdpGroupConnectionsInOrgGroup() {
        return new GitHubCreateOrUpdateIdpGroupConnectionsInOrgGroup(
                "111a1a11-aaa1-1aaa-11a1-a1a1a1a1a1aa",
                "saml-azuread-test",
                "A group of Developers working on AzureAD SAML SSO");
    }

    static GitHubCreateOrUpdateIdpGroupConnectionsLegacyPayload createOrUpdateIdpGroupConnectionsLegacyPayload() {
        return new GitHubCreateOrUpdateIdpGroupConnectionsLegacyPayload(
                List.of(createOrUpdateIdpGroupConnectionsLegacyGroup()), "2021-01-03T22:27:15Z");
    }

    static GitHubCreateOrUpdateIdpGroupConnectionsLegacyGroup createOrUpdateIdpGroupConnectionsLegacyGroup() {
        return new GitHubCreateOrUpdateIdpGroupConnectionsLegacyGroup(
                "111a1a11-aaa1-1aaa-11a1-a1a1a1a1a1aa",
                "saml-azuread-test",
                "A group of Developers working on AzureAD SAML SSO",
                "caceab43fc9ffa20081c",
                "external-team-6c13e7288ef7",
                "moar cheese pleese");
    }

    static GitHubListExternalIdpGroupsForOrgFilter listExternalIdpGroupsForOrgFilter() {
        return GitHubListExternalIdpGroupsForOrgFilterBuilder.builder()
                .displayName("group-azuread-test")
                .build();
    }

    static GitHubListIdpGroupsForOrgFilter listIdpGroupsForOrgFilter() {
        return GitHubListIdpGroupsForOrgFilterBuilder.builder()
                .q("saml-azuread-test")
                .build();
    }
}
