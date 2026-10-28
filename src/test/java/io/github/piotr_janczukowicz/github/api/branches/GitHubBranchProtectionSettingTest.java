package io.github.piotr_janczukowicz.github.api.branches;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubBranchProtectionSettingTest {

    @Test
    void constructor_shouldStoreEnabled_whenCalled() {
        final GitHubBranchProtectionSetting setting = new GitHubBranchProtectionSetting(true);

        assertThat(setting.enabled()).isTrue();
    }
}
