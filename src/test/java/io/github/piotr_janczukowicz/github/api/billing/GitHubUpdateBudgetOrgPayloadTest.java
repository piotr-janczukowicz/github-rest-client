package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.json.OptionalNull;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateBudgetOrgPayloadTest {

    @Test
    void constructor_shouldKeepJsonNull_whenExpiresAtOfNull() {
        final GitHubUpdateBudgetOrgPayload payload = GitHubUpdateBudgetOrgPayloadBuilder.builder(
                        BillingCatalogFixtures.updateBudgetOrgPayload())
                .expiresAt(OptionalNull.ofNull())
                .build();

        assertThat(payload.expiresAt()).isNotNull();
        assertThat(payload.expiresAt().value()).isNull();
    }

    @Test
    void constructor_shouldLeaveExpiresAtAbsent_whenNull() {
        final GitHubUpdateBudgetOrgPayload payload = GitHubUpdateBudgetOrgPayloadBuilder.builder(
                        BillingCatalogFixtures.updateBudgetOrgPayload())
                .expiresAt(null)
                .build();

        assertThat(payload.expiresAt()).isNull();
    }
}
