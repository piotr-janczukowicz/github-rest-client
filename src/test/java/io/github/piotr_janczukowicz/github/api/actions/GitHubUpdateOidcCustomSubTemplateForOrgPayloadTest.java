package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOidcCustomSubTemplateForOrgPayloadTest {

    @Test
    void constructor_shouldLeaveIncludeClaimKeysNull_whenNull() {
        final GitHubUpdateOidcCustomSubTemplateForOrgPayload payload =
                GitHubUpdateOidcCustomSubTemplateForOrgPayloadBuilder.builder(
                                ActionsCatalogFixtures.updateOidcCustomSubTemplateForOrgPayload())
                        .includeClaimKeys(null)
                        .build();

        assertThat(payload.includeClaimKeys()).isNull();
    }
}
