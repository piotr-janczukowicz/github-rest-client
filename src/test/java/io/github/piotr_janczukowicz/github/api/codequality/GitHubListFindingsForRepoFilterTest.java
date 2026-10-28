package io.github.piotr_janczukowicz.github.api.codequality;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.api.GitHubDirection;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListFindingsForRepoFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters() {
        assertThat(GitHubListFindingsForRepoFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeEveryField() {
        final GitHubListFindingsForRepoFilter filter = GitHubListFindingsForRepoFilterBuilder.builder()
                .direction(GitHubDirection.DESC)
                .state(GitHubListFindingsForRepoState.OPEN)
                .build();

        assertThat(filter.toQueryParameters()).isEqualTo(Map.of("direction", "desc", "state", "open"));
    }
}
