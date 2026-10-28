package io.github.piotr_janczukowicz.github.api.issues;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubIssueSuggestionTargetValueTest {

    @Test
    void text_shouldStoreValue_whenCalled() {
        final GitHubIssueSuggestionTargetValue value = GitHubIssueSuggestionTargetValue.text("bug");

        assertThat(value.textValue()).isEqualTo("bug");
    }

    @Test
    void text_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubIssueSuggestionTargetValue.text(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void number_shouldDropFraction_whenDoubleIsWhole() {
        assertThat(GitHubIssueSuggestionTargetValue.number(42.0d).numberValue()).isEqualTo(new BigDecimal("42"));
    }

    @Test
    void number_shouldKeepFraction_whenDoubleIsNotWhole() {
        assertThat(GitHubIssueSuggestionTargetValue.number(42.5d).numberValue()).isEqualTo(new BigDecimal("42.5"));
    }

    @Test
    void number_shouldKeepLongExactly_whenAboveDoublePrecision() {
        final long id = (1L << 53) + 1;

        assertThat(GitHubIssueSuggestionTargetValue.number(id).numberValue()).isEqualTo(BigDecimal.valueOf(id));
    }

    @Test
    void number_shouldThrow_whenDoubleIsNotFinite() {
        final Throwable thrown = catchThrowable(() -> GitHubIssueSuggestionTargetValue.number(Double.NaN));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("finite");
    }

    @Test
    void number_shouldStoreValue_whenCalled() {
        final GitHubIssueSuggestionTargetValue value = GitHubIssueSuggestionTargetValue.number(7);

        assertThat(value.numberValue()).isEqualTo(new BigDecimal("7"));
    }

    @Test
    void flag_shouldStoreValue_whenCalled() {
        final GitHubIssueSuggestionTargetValue value = GitHubIssueSuggestionTargetValue.flag(true);

        assertThat(value.booleanValue()).isTrue();
    }

    @Test
    void texts_shouldThrow_whenValuesNull() {
        final Throwable thrown = catchThrowable(() -> GitHubIssueSuggestionTargetValue.texts(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("values");
    }

    @Test
    void constructor_shouldThrow_whenNoBranchSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssueSuggestionTargetValue(null, null, null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of textValue, numberValue, booleanValue, or listValue");
    }

    @Test
    void constructor_shouldThrow_whenMultipleBranchesSet() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubIssueSuggestionTargetValue("a", BigDecimal.ONE, null, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of textValue, numberValue, booleanValue, or listValue");
    }

    @Test
    void constructor_shouldThrow_whenBooleanAndTextSet() {
        final Throwable thrown = catchThrowable(() -> new GitHubIssueSuggestionTargetValue("a", null, true, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of textValue, numberValue, booleanValue, or listValue");
    }

    @Test
    void constructor_shouldThrow_whenBooleanAndNumberSet() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubIssueSuggestionTargetValue(null, BigDecimal.ONE, true, null));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of textValue, numberValue, booleanValue, or listValue");
    }

    @Test
    void constructor_shouldThrow_whenBooleanAndListSet() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubIssueSuggestionTargetValue(null, null, true, List.of("a")));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of textValue, numberValue, booleanValue, or listValue");
    }

    @Test
    void constructor_shouldThrow_whenTextAndListSet() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubIssueSuggestionTargetValue("a", null, null, List.of("b")));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of textValue, numberValue, booleanValue, or listValue");
    }

    @Test
    void constructor_shouldThrow_whenNumberAndListSet() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubIssueSuggestionTargetValue(null, BigDecimal.ONE, null, List.of("b")));

        assertThat(thrown)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("exactly one of textValue, numberValue, booleanValue, or listValue");
    }

    @Test
    void jsonValue_shouldReturnText_whenText() {
        final GitHubIssueSuggestionTargetValue value = GitHubIssueSuggestionTargetValue.text("bug");

        final Object jsonValue = value.jsonValue();

        assertThat(jsonValue).isEqualTo("bug");
    }

    @Test
    void jsonValue_shouldReturnNumber_whenNumber() {
        final GitHubIssueSuggestionTargetValue value = GitHubIssueSuggestionTargetValue.number(7);

        final Object jsonValue = value.jsonValue();

        assertThat(jsonValue).isEqualTo(new BigDecimal("7"));
    }

    @Test
    void jsonValue_shouldReturnBoolean_whenFlag() {
        final GitHubIssueSuggestionTargetValue value = GitHubIssueSuggestionTargetValue.flag(true);

        final Object jsonValue = value.jsonValue();

        assertThat(jsonValue).isEqualTo(true);
    }

    @Test
    void jsonValue_shouldReturnList_whenTexts() {
        final GitHubIssueSuggestionTargetValue value = GitHubIssueSuggestionTargetValue.texts(List.of("a"));

        final Object jsonValue = value.jsonValue();

        assertThat(jsonValue).isEqualTo(List.of("a"));
    }
}
