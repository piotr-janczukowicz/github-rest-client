package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

@UnitTest
class GitHubProjectFieldSingleSelectColorTest {

    @ParameterizedTest
    @EnumSource(GitHubProjectFieldSingleSelectColor.class)
    void fromWire_shouldReturnColor_whenKnownValue(final GitHubProjectFieldSingleSelectColor value) {
        final GitHubProjectFieldSingleSelectColor decoded =
                GitHubProjectFieldSingleSelectColor.fromWire(value.wireValue());

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(value.name());
        });
    }

    @Test
    void fromWire_shouldReturnNull_whenValueIsNull() {
        final GitHubProjectFieldSingleSelectColor decoded = GitHubProjectFieldSingleSelectColor.fromWire(null);

        assertThat(decoded).isNull();
    }

    @Test
    void fromWire_shouldThrow_whenUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubProjectFieldSingleSelectColor.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
