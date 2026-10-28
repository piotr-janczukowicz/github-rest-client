package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubApiInsightsRouteStatsSortTest {

    @ParameterizedTest
    @CsvSource({
        "last_rate_limited_timestamp,LAST_RATE_LIMITED_TIMESTAMP",
        "last_request_timestamp,LAST_REQUEST_TIMESTAMP",
        "rate_limited_request_count,RATE_LIMITED_REQUEST_COUNT",
        "http_method,HTTP_METHOD",
        "api_route,API_ROUTE",
        "total_request_count,TOTAL_REQUEST_COUNT"
    })
    void fromWire_shouldReturnSort_whenKnownValue(final String wire, final GitHubApiInsightsRouteStatsSort sort) {
        final GitHubApiInsightsRouteStatsSort decoded = GitHubApiInsightsRouteStatsSort.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(sort);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @ParameterizedTest
    @MethodSource("invalidWire")
    void fromWire_shouldThrow_whenInvalid(
            final String wire, final Class<? extends Throwable> type, final String message) {
        final Throwable thrown = catchThrowable(() -> GitHubApiInsightsRouteStatsSort.fromWire(wire));

        assertThat(thrown).isInstanceOf(type).hasMessageContaining(message);
    }

    private static Stream<Arguments> invalidWire() {
        return Stream.of(
                Arguments.of("subject_name", IllegalArgumentException.class, "subject_name"),
                Arguments.of(null, NullPointerException.class, "value"));
    }
}
