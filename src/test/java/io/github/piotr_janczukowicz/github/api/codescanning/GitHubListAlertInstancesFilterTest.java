package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAlertInstancesFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubListAlertInstancesFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubListAlertInstancesFilter filter = GitHubListAlertInstancesFilterBuilder.builder()
                .ref("refs/heads/main")
                .pr(123)
                .build();

        assertThat(filter.toQueryParameters())
                .isEqualTo(Map.of(
                        "ref", "refs/heads/main",
                        "pr", "123"));
    }
}
