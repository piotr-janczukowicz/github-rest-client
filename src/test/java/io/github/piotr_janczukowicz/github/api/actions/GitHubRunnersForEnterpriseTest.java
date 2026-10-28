package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRunnersForEnterpriseTest {

    @Test
    void constructor_shouldKeepRunnersNull_whenRunnersOmitted() {
        final GitHubRunnersForEnterprise value = new GitHubRunnersForEnterprise(1, null);

        assertThat(value.runners()).isNull();
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubRunnersForEnterprise value = ActionsCatalogFixtures.runnersForEnterprise();

        assertThat(value.totalCount()).isEqualTo(1);
        assertThat(value.runners()).containsExactly(ActionsCatalogFixtures.runner());
    }
}
