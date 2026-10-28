package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseSecurityProductEnablementTest {

    @Test
    void fromWire_shouldReturnEnableAll_whenKnownValue() {
        assertThat(GitHubEnterpriseSecurityProductEnablement.fromWire("enable_all"))
                .isEqualTo(GitHubEnterpriseSecurityProductEnablement.ENABLE_ALL);
    }

    @Test
    void fromWire_shouldReturnDisableAll_whenKnownValue() {
        assertThat(GitHubEnterpriseSecurityProductEnablement.fromWire("disable_all"))
                .isEqualTo(GitHubEnterpriseSecurityProductEnablement.DISABLE_ALL);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubEnterpriseSecurityProductEnablement.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
