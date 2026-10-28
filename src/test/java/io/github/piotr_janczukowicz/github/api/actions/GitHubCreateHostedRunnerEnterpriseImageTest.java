package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateHostedRunnerEnterpriseImageTest {

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubCreateHostedRunnerEnterpriseImage value =
                ActionsCatalogFixtures.createHostedRunnerEnterpriseImage();

        assertThat(value.id()).isEqualTo("id-sample");
        assertThat(value.source()).isEqualTo(GitHubCreateHostedRunnerEnterpriseImageSource.GITHUB);
        assertThat(value.version()).isEqualTo("version-sample");
    }
}
