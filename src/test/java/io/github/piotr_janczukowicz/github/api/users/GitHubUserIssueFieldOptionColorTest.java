package io.github.piotr_janczukowicz.github.api.users;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubUserIssueFieldOptionColorTest {

    @ParameterizedTest
    @CsvSource({
        "gray,GRAY",
        "blue,BLUE",
        "green,GREEN",
        "yellow,YELLOW",
        "orange,ORANGE",
        "red,RED",
        "pink,PINK",
        "purple,PURPLE"
    })
    void fromWire_shouldReturnConstant_whenKnownValue(final String wire, final GitHubUserIssueFieldOptionColor value) {
        final GitHubUserIssueFieldOptionColor decoded = GitHubUserIssueFieldOptionColor.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubUserIssueFieldOptionColor.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnrecognized_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubUserIssueFieldOptionColor decoded = GitHubUserIssueFieldOptionColor.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubUserIssueFieldOptionColor.UNRECOGNIZED);
            softly.assertThat(decoded.wireValue()).isEqualTo("unrecognized");
        });
    }
}
