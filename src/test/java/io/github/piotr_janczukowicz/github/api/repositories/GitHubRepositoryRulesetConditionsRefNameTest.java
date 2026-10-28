package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryRulesetConditionsRefNameTest {

    @Test
    void constructor_shouldLeaveIncludeAndExcludeNull_whenIncludeAndExcludeNull() {
        final GitHubRepositoryRulesetConditionsRefName refName =
                new GitHubRepositoryRulesetConditionsRefName(null, null);

        assertSoftly(softly -> {
            softly.assertThat(refName.include()).isNull();
            softly.assertThat(refName.exclude()).isNull();
        });
    }
}
