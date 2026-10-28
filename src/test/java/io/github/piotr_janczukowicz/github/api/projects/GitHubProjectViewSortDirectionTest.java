package io.github.piotr_janczukowicz.github.api.projects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubProjectViewSortDirectionTest {

    @ParameterizedTest
    @CsvSource({"asc,ASC", "desc,DESC", "unknown,UNKNOWN"})
    void fromWire_shouldReturnDirection_whenKnownValue(final String wire, final GitHubProjectViewSortDirection value) {
        final GitHubProjectViewSortDirection decoded = GitHubProjectViewSortDirection.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueIsNotKnown() {
        final GitHubProjectViewSortDirection decoded = GitHubProjectViewSortDirection.fromWire("sideways");

        assertThat(decoded).isEqualTo(GitHubProjectViewSortDirection.UNKNOWN);
    }

    @Test
    void fromWire_shouldThrow_whenValueIsNull() {
        final Throwable thrown = catchThrowable(() -> GitHubProjectViewSortDirection.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }
}
