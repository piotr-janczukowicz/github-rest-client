package io.github.piotr_janczukowicz.github.api.agentautomations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubListAutomationsFilterTest {

    @Test
    void empty_shouldReturnNoQueryParameters_whenCalled() {
        assertThat(GitHubListAutomationsFilter.empty().toQueryParameters()).isEmpty();
    }

    @Test
    void toQueryParameters_shouldEncodeField_whenSet() {
        final GitHubListAutomationsFilter filter = GitHubListAutomationsFilterBuilder.builder()
                .ownerType(GitHubListAutomationsOwnerType.USER)
                .build();

        assertThat(filter.toQueryParameters()).containsEntry("owner_type", "user");
    }
}
