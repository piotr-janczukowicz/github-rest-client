package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateOrgActionsPolicyPayloadTest {

    @Test
    void constructor_shouldLeaveRulesNull_whenNull() {
        final GitHubUpdateOrgActionsPolicyPayload sut = GitHubUpdateOrgActionsPolicyPayloadBuilder.builder(
                        ActionsCatalogFixtures.updateOrgActionsPolicyPayload())
                .rules(null)
                .build();

        assertThat(sut.rules()).isNull();
    }
}
