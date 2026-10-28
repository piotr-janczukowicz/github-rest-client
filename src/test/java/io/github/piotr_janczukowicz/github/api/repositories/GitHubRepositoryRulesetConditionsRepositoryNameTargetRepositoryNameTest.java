package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryNameTest {

    @Test
    void constructor_shouldLeaveIncludeExcludeAndProtectedNull_whenAllNull() {
        final GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName repositoryName =
                new GitHubRepositoryRulesetConditionsRepositoryNameTargetRepositoryName(null, null, null);

        assertSoftly(softly -> {
            softly.assertThat(repositoryName.include()).isNull();
            softly.assertThat(repositoryName.exclude()).isNull();
            softly.assertThat(repositoryName.protectedRepos()).isNull();
        });
    }
}
