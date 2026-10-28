package io.github.piotr_janczukowicz.github.api.codequality;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeQualitySetupUpdateTest {

    @Test
    void constructor_shouldLeaveLanguagesNull_whenNull() {
        final GitHubCodeQualitySetupUpdate built = GitHubCodeQualitySetupUpdateBuilder.builder(
                        CodeQualityCatalogFixtures.codeQualitySetupUpdate())
                .languages(null)
                .build();

        assertThat(built.languages()).isNull();
    }
}
