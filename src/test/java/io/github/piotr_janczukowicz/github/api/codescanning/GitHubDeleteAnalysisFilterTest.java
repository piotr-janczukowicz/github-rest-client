package io.github.piotr_janczukowicz.github.api.codescanning;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubDeleteAnalysisFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubDeleteAnalysisFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldReturnEmpty_whenNull() {
        assertThat(GitHubDeleteAnalysisFilterBuilder.builder().build().toQueryParameters())
                .isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubDeleteAnalysisFilter filter = GitHubDeleteAnalysisFilterBuilder.builder()
                .confirmDelete("true")
                .build();

        assertThat(filter.toQueryParameters()).isEqualTo(Map.of("confirm_delete", "true"));
    }
}
