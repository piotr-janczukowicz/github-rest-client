package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryIdTest {

    @Test
    void constructor_shouldLeaveRepositoryIdsNull_whenRepositoryIdsNull() {
        final GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId repositoryId =
                new GitHubRepositoryRulesetConditionsRepositoryIdTargetRepositoryId(null);

        assertThat(repositoryId.repositoryIds()).isNull();
    }
}
