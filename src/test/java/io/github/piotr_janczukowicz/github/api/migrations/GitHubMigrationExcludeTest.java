package io.github.piotr_janczukowicz.github.api.migrations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubMigrationExcludeTest {

    @Test
    void fromWire_shouldReturnConstant_whenKnownValue() {
        final GitHubMigrationExclude exclude = GitHubMigrationExclude.fromWire("repositories");

        assertSoftly(softly -> {
            softly.assertThat(exclude).isEqualTo(GitHubMigrationExclude.REPOSITORIES);
            softly.assertThat(exclude.wireValue()).isEqualTo("repositories");
        });
    }

    @Test
    void fromWire_shouldThrow_whenUnknownValue() {
        final Throwable thrown = catchThrowable(() -> GitHubMigrationExclude.fromWire("unknown"));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Unknown GitHub migration exclude item");
    }

    @Test
    void fromWire_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubMigrationExclude.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
