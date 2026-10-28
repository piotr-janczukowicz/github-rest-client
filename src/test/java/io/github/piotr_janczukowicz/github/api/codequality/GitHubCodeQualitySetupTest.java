package io.github.piotr_janczukowicz.github.api.codequality;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeQualitySetupTest {

    @Test
    void constructor_shouldLeaveLanguagesNull_whenNull() {
        final GitHubCodeQualitySetup built = GitHubCodeQualitySetupBuilder.builder(
                        CodeQualityCatalogFixtures.codeQualitySetup())
                .languages(null)
                .build();

        assertThat(built.languages()).isNull();
    }
}
