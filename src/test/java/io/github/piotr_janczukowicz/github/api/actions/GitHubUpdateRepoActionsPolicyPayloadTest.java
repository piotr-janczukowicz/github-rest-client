package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateRepoActionsPolicyPayloadTest {

    @Test
    void constructor_shouldLeaveRulesNull_whenNull() {
        final GitHubUpdateRepoActionsPolicyPayload sut = GitHubUpdateRepoActionsPolicyPayloadBuilder.builder(
                        ActionsCatalogFixtures.updateRepoActionsPolicyPayload())
                .rules(null)
                .build();

        assertThat(sut.rules()).isNull();
    }
}
