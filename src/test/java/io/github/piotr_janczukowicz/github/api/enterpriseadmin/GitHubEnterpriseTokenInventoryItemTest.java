package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseTokenInventoryItemTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubEnterpriseTokenInventoryItem value = EnterpriseAdminCatalogFixtures.enterpriseTokenInventoryItem();

        assertThat(value.inventoryId()).isEqualTo("sample-inventory_id");
        assertThat(value.credentialId()).isEqualTo(1L);
        assertThat(value.hashedToken()).isEqualTo("sample-hashed_token");
        assertThat(value.fingerprint()).isEqualTo("sample-fingerprint");
        assertThat(value.itemType()).isEqualTo(GitHubEnterpriseTokenInventoryItemType.CREDENTIAL);
        assertThat(value.credentialType()).isEqualTo(GitHubEnterpriseTokenInventoryItemCredentialType.CLASSIC_PAT);
        assertThat(value.displayName()).isEqualTo("sample-display_name");
        assertThat(value.owner()).isNotNull();
        assertThat(value.ownerType()).isEqualTo(GitHubEnterpriseTokenInventoryItemOwnerType.USER);
        assertThat(value.application()).isNotNull();
        assertThat(value.credentialState()).isEqualTo(GitHubEnterpriseTokenInventoryItemCredentialState.ACTIVE);
        assertThat(value.authorizationState())
                .isEqualTo(GitHubEnterpriseTokenInventoryItemAuthorizationState.CURRENTLY_AUTHORIZED);
        assertThat(value.effectiveAccessState())
                .isEqualTo(GitHubEnterpriseTokenInventoryItemEffectiveAccessState.EFFECTIVE);
        assertThat(value.stateReason()).isEqualTo("sample-state_reason");
        assertThat(value.createdAt()).isNotNull();
        assertThat(value.lastUsedAt()).isNotNull();
        assertThat(value.expiresAt()).isNotNull();
        assertThat(value.nextExpiresAt()).isNotNull();
        assertThat(value.credentialInstanceCount()).isEqualTo(1);
        assertThat(value.enterpriseAuthorized()).isTrue();
        assertThat(value.authorizationCount()).isEqualTo(1);
        assertThat(value.authorizedOrganizations()).isNotEmpty();
        assertThat(value.ageDays()).isEqualTo(1);
        assertThat(value.neverExpires()).isTrue();
        assertThat(value.pastExpirationPolicy()).isTrue();
        assertThat(value.pastExpirationPolicyBasis())
                .isEqualTo(GitHubEnterpriseTokenInventoryItemPastExpirationPolicyBasis.ENFORCED_LIMIT);
        assertThat(value.expiryUnknown()).isTrue();
        assertThat(value.scopes()).isNotEmpty();
        assertThat(value.permissions()).isNotEmpty();
        assertThat(value.repositorySelection()).isEqualTo(GitHubEnterpriseTokenInventoryItemRepositorySelection.ALL);
    }

    @Test
    void constructor_shouldLeaveScopesNull_whenNull() {
        final GitHubEnterpriseTokenInventoryItem value = GitHubEnterpriseTokenInventoryItemBuilder.builder(
                        EnterpriseAdminCatalogFixtures.enterpriseTokenInventoryItem())
                .scopes(null)
                .build();

        assertThat(value.scopes()).isNull();
    }

    @Test
    void constructor_shouldLeavePermissionsNull_whenNull() {
        final GitHubEnterpriseTokenInventoryItem value = GitHubEnterpriseTokenInventoryItemBuilder.builder(
                        EnterpriseAdminCatalogFixtures.enterpriseTokenInventoryItem())
                .permissions(null)
                .build();

        assertThat(value.permissions()).isNull();
    }
}
