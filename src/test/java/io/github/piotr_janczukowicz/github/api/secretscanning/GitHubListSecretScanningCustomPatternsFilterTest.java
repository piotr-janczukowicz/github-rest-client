package io.github.piotr_janczukowicz.github.api.secretscanning;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@UnitTest
class GitHubListSecretScanningCustomPatternsFilterTest {

    @Test
    void empty_shouldReturnEmptyQuery_whenCalled() {
        final GitHubListSecretScanningCustomPatternsFilter filter =
                GitHubListSecretScanningCustomPatternsFilter.empty();

        assertSoftly(softly -> {
            softly.assertThat(filter.state()).isNull();
            softly.assertThat(filter.pushProtection()).isNull();
            softly.assertThat(filter.toQueryParameters()).isEmpty();
        });
    }

    @ParameterizedTest
    @MethodSource("partialFilters")
    void toQueryParameters_shouldOmitUnsetFields_whenCalled(
            final GitHubListSecretScanningCustomPatternsFilter filter, final Map<String, String> expected) {
        final Map<String, String> query = filter.toQueryParameters();

        assertThat(query).containsExactlyInAnyOrderEntriesOf(expected);
    }

    @Test
    void toQueryParameters_shouldIncludeAllSetFields_whenCalled() {
        final GitHubListSecretScanningCustomPatternsFilter filter =
                GitHubListSecretScanningCustomPatternsFilterBuilder.builder()
                        .state(GitHubSecretScanningCustomPatternState.PUBLISHED)
                        .pushProtection(GitHubSecretScanningCustomPatternPushProtection.ENABLED)
                        .sort(GitHubSecretScanningCustomPatternSort.UPDATED)
                        .direction(GitHubDirection.DESC)
                        .build();

        final Map<String, String> query = filter.toQueryParameters();

        assertSoftly(softly -> {
            softly.assertThat(query).containsEntry("state", "published");
            softly.assertThat(query).containsEntry("push_protection", "enabled");
            softly.assertThat(query).containsEntry("sort", "updated");
            softly.assertThat(query).containsEntry("direction", "desc");
        });
    }

    private static Stream<Arguments> partialFilters() {
        return Stream.of(
                Arguments.of(
                        GitHubListSecretScanningCustomPatternsFilterBuilder.builder()
                                .state(GitHubSecretScanningCustomPatternState.UNPUBLISHED)
                                .build(),
                        Map.of("state", "unpublished")),
                Arguments.of(
                        GitHubListSecretScanningCustomPatternsFilterBuilder.builder()
                                .pushProtection(GitHubSecretScanningCustomPatternPushProtection.DISABLED)
                                .build(),
                        Map.of("push_protection", "disabled")),
                Arguments.of(
                        GitHubListSecretScanningCustomPatternsFilterBuilder.builder()
                                .sort(GitHubSecretScanningCustomPatternSort.NAME)
                                .build(),
                        Map.of("sort", "name")));
    }
}
