package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryPropertyTest {

    @Test
    void constructor_shouldLeaveIncludeAndExcludeNull_whenIncludeAndExcludeNull() {
        final GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty repositoryProperty =
                new GitHubRepositoryRulesetConditionsRepositoryPropertyTargetRepositoryProperty(null, null);

        assertSoftly(softly -> {
            softly.assertThat(repositoryProperty.include()).isNull();
            softly.assertThat(repositoryProperty.exclude()).isNull();
        });
    }
}
