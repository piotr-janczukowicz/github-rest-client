package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

@UnitTest
class GitHubIssueFieldValueWireTest {

    @Test
    void text_shouldStoreValue_whenCalled() {
        final GitHubIssueFieldValueWire value = GitHubIssueFieldValueWire.text("open");

        assertThat(value.textValue()).isEqualTo("open");
    }

    @Test
    void text_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubIssueFieldValueWire.text(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void number_shouldStoreValue_whenCalled() {
        final GitHubIssueFieldValueWire value = GitHubIssueFieldValueWire.number(42);

        assertThat(value.numberValue()).isEqualTo(new BigDecimal("42"));
    }

    @Test
    void number_shouldKeepEveryDigit_whenBigDecimalExceedsDoublePrecision() {
        final BigDecimal huge = new BigDecimal("12345678901234567890.123456789");

        assertThat(GitHubIssueFieldValueWire.number(huge).numberValue()).isEqualTo(huge);
    }

    @Test
    void number_shouldKeepLongExactly_whenAboveDoublePrecision() {
        final long id = (1L << 53) + 1;

        assertThat(GitHubIssueFieldValueWire.number(id).numberValue()).isEqualTo(BigDecimal.valueOf(id));
    }

    @Test
    void number_shouldDropFraction_whenDoubleIsWhole() {
        assertThat(GitHubIssueFieldValueWire.number(42.0d).numberValue()).isEqualTo(new BigDecimal("42"));
    }

    @Test
    void number_shouldKeepFraction_whenDoubleIsNotWhole() {
        assertThat(GitHubIssueFieldValueWire.number(42.5d).numberValue()).isEqualTo(new BigDecimal("42.5"));
    }

    @ParameterizedTest
    @ValueSource(doubles = {Double.NaN, Double.POSITIVE_INFINITY, Double.NEGATIVE_INFINITY})
    void number_shouldThrow_whenDoubleIsNotFinite(final double value) {
        final Throwable thrown = catchThrowable(() -> GitHubIssueFieldValueWire.number(value));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("finite");
    }

    @Test
    void number_shouldKeepTheValue_whenDoubleHasNoFractionDigitsAndAnExponent() {
        final BigDecimal value = GitHubIssueFieldValueWire.number(1.0e10d).numberValue();

        assertThat(value).isEqualByComparingTo(new BigDecimal("10000000000"));
    }

    @Test
    void number_shouldThrow_whenBigDecimalNull() {
        final Throwable thrown = catchThrowable(() -> GitHubIssueFieldValueWire.number(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void texts_shouldCopyValues_whenCalled() {
        final List<String> values = new ArrayList<>(List.of("a", "b"));

        final GitHubIssueFieldValueWire value = GitHubIssueFieldValueWire.texts(values);
        values.add("c");

        assertThat(value.listValue()).containsExactly("a", "b");
    }

    @Test
    void texts_shouldThrow_whenValuesNull() {
        final Throwable thrown = catchThrowable(() -> GitHubIssueFieldValueWire.texts(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("values");
    }

    @Test
    void constructor_shouldThrow_whenNoBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssueFieldValueWire(null, null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of textValue, numberValue, or listValue is required");
    }

    @Test
    void constructor_shouldThrow_whenMultipleBranchesSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssueFieldValueWire("a", BigDecimal.ONE, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of textValue, numberValue, or listValue is required");
    }

    @Test
    void constructor_shouldThrow_whenTextAndListSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssueFieldValueWire("a", null, List.of("b")));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of textValue, numberValue, or listValue is required");
    }

    @Test
    void constructor_shouldThrow_whenNumberAndListSet() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubIssueFieldValueWire(null, BigDecimal.ONE, List.of("b")));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of textValue, numberValue, or listValue is required");
    }

    @Test
    void jsonValue_shouldReturnText_whenText() {
        final GitHubIssueFieldValueWire value = GitHubIssueFieldValueWire.text("open");

        final Object jsonValue = value.jsonValue();

        assertThat(jsonValue).isEqualTo("open");
    }

    @Test
    void jsonValue_shouldReturnNumber_whenNumber() {
        final GitHubIssueFieldValueWire value = GitHubIssueFieldValueWire.number(42);

        final Object jsonValue = value.jsonValue();

        assertThat(jsonValue).isEqualTo(new BigDecimal("42"));
    }

    @Test
    void jsonValue_shouldReturnList_whenTexts() {
        final GitHubIssueFieldValueWire value = GitHubIssueFieldValueWire.texts(List.of("a", "b"));

        final Object jsonValue = value.jsonValue();

        assertThat(jsonValue).isEqualTo(List.of("a", "b"));
    }
}
