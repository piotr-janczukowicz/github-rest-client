package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateRepoActionsPolicyPayloadTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateRepoActionsPolicyPayloadBuilder.builder(
                        ActionsCatalogFixtures.createRepoActionsPolicyPayload())
                .name(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenEnforcementNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateRepoActionsPolicyPayloadBuilder.builder(
                        ActionsCatalogFixtures.createRepoActionsPolicyPayload())
                .enforcement(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enforcement");
    }

    @Test
    void constructor_shouldLeaveRulesNull_whenNull() {
        final GitHubCreateRepoActionsPolicyPayload sut = GitHubCreateRepoActionsPolicyPayloadBuilder.builder(
                        ActionsCatalogFixtures.createRepoActionsPolicyPayload())
                .rules(null)
                .build();

        assertThat(sut.rules()).isNull();
    }
}
