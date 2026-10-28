package io.github.piotr_janczukowicz.github.api.search;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.organizations.GitHubIssueTypeColor;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubIssueSearchResultItemTest {

    @ParameterizedTest
    @CsvSource({
        "COLLABORATOR,COLLABORATOR",
        "CONTRIBUTOR,CONTRIBUTOR",
        "FIRST_TIMER,FIRST_TIMER",
        "FIRST_TIME_CONTRIBUTOR,FIRST_TIME_CONTRIBUTOR",
        "MANNEQUIN,MANNEQUIN",
        "MEMBER,MEMBER",
        "NONE,NONE",
        "OWNER,OWNER"
    })
    void fromWire_shouldReturnAuthorAssociation_whenKnownValue(final String wire, final GitHubAuthorAssociation value) {
        final GitHubAuthorAssociation decoded = GitHubAuthorAssociation.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @CsvSource({"text,TEXT", "single_select,SINGLE_SELECT", "multi_select,MULTI_SELECT", "number,NUMBER", "date,DATE"})
    void fromWire_shouldReturnFieldDataType_whenKnownValue(final String wire, final GitHubIssueFieldDataType value) {
        final GitHubIssueFieldDataType decoded = GitHubIssueFieldDataType.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

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
    void fromWire_shouldReturnIssueTypeColor_whenKnownValue(final String wire, final GitHubIssueTypeColor value) {
        final GitHubIssueTypeColor decoded = GitHubIssueTypeColor.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @CsvSource({
        "comments,COMMENTS",
        "reactions,REACTIONS",
        "reactions-+1,REACTIONS_PLUS_ONE",
        "reactions--1,REACTIONS_MINUS_ONE",
        "reactions-smile,REACTIONS_SMILE",
        "reactions-thinking_face,REACTIONS_THINKING_FACE",
        "reactions-heart,REACTIONS_HEART",
        "reactions-tada,REACTIONS_TADA",
        "interactions,INTERACTIONS",
        "created,CREATED",
        "updated,UPDATED"
    })
    void fromWire_shouldReturnSort_whenKnownValue(final String wire, final GitHubIssueSearchSort value) {
        final GitHubIssueSearchSort decoded = GitHubIssueSearchSort.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidSortWire")
    void fromWire_shouldThrow_whenInvalidSort(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubIssueSearchSort.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    @Test
    void fromWire_shouldThrow_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubAuthorAssociation.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalidFieldDataType(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubIssueFieldDataType.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalidIssueTypeColor(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubIssueTypeColor.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidSortWire() {
        return Stream.of(
                Arguments.of("unknown", IllegalArgumentException.class, "unknown"),
                Arguments.of(null, NullPointerException.class, "value"));
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("unknown", IllegalArgumentException.class, "unknown"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
