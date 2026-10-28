package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCustomPropertyBaseSourceTypeTest {

    @Test
    void fromWire_shouldReturnOrganization_whenKnownValue() {
        assertThat(GitHubCustomPropertyBaseSourceType.fromWire("organization"))
                .isEqualTo(GitHubCustomPropertyBaseSourceType.ORGANIZATION);
    }

    @Test
    void fromWire_shouldReturnEnterprise_whenKnownValue() {
        assertThat(GitHubCustomPropertyBaseSourceType.fromWire("enterprise"))
                .isEqualTo(GitHubCustomPropertyBaseSourceType.ENTERPRISE);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubCustomPropertyBaseSourceType.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
