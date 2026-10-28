package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsPolicyTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubActionsPolicyBuilder.builder(ActionsCatalogFixtures.actionsPolicy())
                        .name(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenTargetNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubActionsPolicyBuilder.builder(ActionsCatalogFixtures.actionsPolicy())
                        .target(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("target");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenSourceTypeNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubActionsPolicyBuilder.builder(ActionsCatalogFixtures.actionsPolicy())
                        .sourceType(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("sourceType");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenSourceNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubActionsPolicyBuilder.builder(ActionsCatalogFixtures.actionsPolicy())
                        .source(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("source");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenEnforcementNull() {
        final Throwable thrown =
                catchThrowable(() -> GitHubActionsPolicyBuilder.builder(ActionsCatalogFixtures.actionsPolicy())
                        .enforcement(null)
                        .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("enforcement");
    }

    @Test
    void constructor_shouldLeaveRulesNull_whenNull() {
        final GitHubActionsPolicy sut = GitHubActionsPolicyBuilder.builder(ActionsCatalogFixtures.actionsPolicy())
                .rules(null)
                .build();

        assertThat(sut.rules()).isNull();
    }
}
