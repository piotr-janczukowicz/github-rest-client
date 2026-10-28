package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListRepoAutomationsFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        assertThat(GitHubListRepoAutomationsFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeField_whenSet() {
        final GitHubListRepoAutomationsFilter filter = GitHubListRepoAutomationsFilterBuilder.builder()
                .ownerType(GitHubListRepoAutomationsOwnerType.USER)
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("owner_type", "user");
    }
}
