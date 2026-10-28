package io.github.piotr_janczukowicz.github.api.agenttasks;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLongIdRoundTripTest {

    @Test
    void toQueryParameters_shouldEncodeCreatorId_whenIdAboveIntMax() {
        final GitHubListTasksForRepoFilter filter = GitHubListTasksForRepoFilterBuilder.builder()
                .creatorId(List.of(3_000_000_000L))
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("creator_id", "3000000000");
    }
}
