package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrgActionsPolicyPayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateOrgActionsPolicyPayloadBuilder.builder(
                        ActionsCatalogFixtures.createOrgActionsPolicyPayload())
                .name(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenEnforcementNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateOrgActionsPolicyPayloadBuilder.builder(
                        ActionsCatalogFixtures.createOrgActionsPolicyPayload())
                .enforcement(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enforcement");
    }

    @Test
    void constructor_shouldLeaveRulesNull_whenNull() {
        final GitHubCreateOrgActionsPolicyPayload sut = GitHubCreateOrgActionsPolicyPayloadBuilder.builder(
                        ActionsCatalogFixtures.createOrgActionsPolicyPayload())
                .rules(null)
                .build();

        assertThat(sut.rules()).isNull();
    }
}
