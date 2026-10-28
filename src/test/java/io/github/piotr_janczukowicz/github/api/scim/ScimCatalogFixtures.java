package io.github.piotr_janczukowicz.github.api.scim;

import io.github.piotr_janczukowicz.json.OptionalNull;
import java.time.Instant;
import java.util.List;

final class ScimCatalogFixtures {

    private ScimCatalogFixtures() {}

    static GitHubProvisionAndInviteUserPayload provisionAndInviteUserPayload() {
        return GitHubProvisionAndInviteUserPayloadBuilder.builder()
                .userName("mona.octocat@okta.example.com")
                .displayName("Monalisa Octocat")
                .name(provisionAndInviteUserName())
                .emails(List.of(provisionAndInviteUserEmail()))
                .schemas(List.of("urn:ietf:params:scim:schemas:core:2.0:User"))
                .externalId("a7d0f98382")
                .groups(List.of("engineering"))
                .active(true)
                .build();
    }

    static GitHubProvisionAndInviteUserName provisionAndInviteUserName() {
        return GitHubProvisionAndInviteUserNameBuilder.builder()
                .givenName("Monalisa")
                .familyName("Octocat")
                .formatted("Monalisa Octocat")
                .build();
    }

    static GitHubProvisionAndInviteUserEmail provisionAndInviteUserEmail() {
        return GitHubProvisionAndInviteUserEmailBuilder.builder()
                .type("work")
                .value("mona.octocat@okta.example.com")
                .primary(true)
                .build();
    }

    static GitHubSetInformationForProvisionedUserPayload setInformationForProvisionedUserPayload() {
        return GitHubSetInformationForProvisionedUserPayloadBuilder.builder()
                .schemas(List.of("urn:ietf:params:scim:schemas:core:2.0:User"))
                .displayName("Monalisa Octocat")
                .externalId("a7d0f98382")
                .groups(List.of("engineering"))
                .active(true)
                .userName("mona.octocat@okta.example.com")
                .name(setInformationForProvisionedUserName())
                .emails(List.of(setInformationForProvisionedUserEmail()))
                .build();
    }

    static GitHubSetInformationForProvisionedUserName setInformationForProvisionedUserName() {
        return GitHubSetInformationForProvisionedUserNameBuilder.builder()
                .givenName("Monalisa")
                .familyName("Octocat")
                .formatted("Monalisa Octocat")
                .build();
    }

    static GitHubScimUser scimUser() {
        return GitHubScimUserBuilder.builder()
                .schemas(List.of("urn:ietf:params:scim:schemas:core:2.0:User"))
                .id("edefdfedf-050c-11e7-8d32")
                .externalId("a7d0f98382")
                .userName("mona.octocat@okta.example.com")
                .profileUrl("https://github.com/mona")
                .displayName("Monalisa Octocat")
                .name(scimUserName())
                .emails(List.of(scimUserEmail()))
                .active(true)
                .meta(scimUserMeta())
                .organizationId(1L)
                .operations(List.of(scimUserOperation()))
                .groups(List.of(scimUserGroup()))
                .roles(List.of(scimUserRole()))
                .build();
    }

    static GitHubScimUserName scimUserName() {
        return GitHubScimUserNameBuilder.builder()
                .givenName("Monalisa")
                .familyName("Octocat")
                .formatted("Monalisa Octocat")
                .build();
    }

    static GitHubScimUserEmail scimUserEmail() {
        return GitHubScimUserEmailBuilder.builder()
                .value("mona.octocat@okta.example.com")
                .primary(true)
                .type("work")
                .build();
    }

    static GitHubScimUserMeta scimUserMeta() {
        return GitHubScimUserMetaBuilder.builder()
                .resourceType("User")
                .created(Instant.parse("2017-03-09T21:11:13Z"))
                .lastModified(Instant.parse("2017-03-09T21:11:13Z"))
                .location("https://api.github.com/scim/v2/organizations/octo-org/Users/edefdfedf-050c-11e7-8d32")
                .build();
    }

    static GitHubScimUserOperation scimUserOperation() {
        return GitHubScimUserOperationBuilder.builder()
                .op(GitHubScimUserOperationOp.REPLACE)
                .path("active")
                .value(GitHubScimOperationValue.text("false"))
                .build();
    }

    static GitHubScimUserGroup scimUserGroup() {
        return GitHubScimUserGroupBuilder.builder()
                .value("engineering")
                .display("Engineering")
                .build();
    }

    static GitHubScimUserRole scimUserRole() {
        return GitHubScimUserRoleBuilder.builder()
                .value("admin")
                .primary(false)
                .type("member")
                .display("Member")
                .build();
    }

    static GitHubScimUserList scimUserList() {
        return GitHubScimUserListBuilder.builder()
                .schemas(List.of("urn:ietf:params:scim:schemas:core:2.0:ListResponse"))
                .totalResults(3)
                .itemsPerPage(1)
                .startIndex(1)
                .resources(List.of(scimUser()))
                .build();
    }

    static GitHubListProvisionedIdentitiesFilter listProvisionedIdentitiesFilter() {
        return GitHubListProvisionedIdentitiesFilterBuilder.builder()
                .filter("userName eq \"Octocat\"")
                .build();
    }

    static GitHubUpdateAttributeForUserValueObject updateAttributeForUserValueObject() {
        return GitHubUpdateAttributeForUserValueObjectBuilder.builder()
                .active(OptionalNull.of(false))
                .userName(OptionalNull.of("mona.octocat@okta.example.com"))
                .externalId(OptionalNull.of("a7d0f98382"))
                .givenName(OptionalNull.of("Monalisa"))
                .familyName(OptionalNull.of("Octocat"))
                .build();
    }

    static GitHubUpdateAttributeForUserValueEmail updateAttributeForUserValueEmail() {
        return GitHubUpdateAttributeForUserValueEmailBuilder.builder()
                .value("mona.octocat@okta.example.com")
                .primary(true)
                .build();
    }

    static GitHubUpdateAttributeForUserOperation updateAttributeForUserOperation() {
        return GitHubUpdateAttributeForUserOperationBuilder.builder()
                .op(GitHubUpdateAttributeForUserOperationOp.REPLACE)
                .path("name")
                .value(GitHubUpdateAttributeForUserValue.object(updateAttributeForUserValueObject()))
                .build();
    }

    static GitHubUpdateAttributeForUserPayload updateAttributeForUserPayload() {
        return GitHubUpdateAttributeForUserPayloadBuilder.builder()
                .schemas(List.of("urn:ietf:params:scim:schemas:core:2.0:PatchOp"))
                .operations(List.of(updateAttributeForUserOperation()))
                .build();
    }

    static GitHubSetInformationForProvisionedUserEmail setInformationForProvisionedUserEmail() {
        return GitHubSetInformationForProvisionedUserEmailBuilder.builder()
                .type("work")
                .value("mona.octocat@okta.example.com")
                .primary(true)
                .build();
    }
}
