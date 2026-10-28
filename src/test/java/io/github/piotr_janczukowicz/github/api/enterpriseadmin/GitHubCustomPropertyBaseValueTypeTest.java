package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCustomPropertyBaseValueTypeTest {

    @Test
    void fromWire_shouldReturnString_whenKnownValue() {
        assertThat(GitHubCustomPropertyBaseValueType.fromWire("string"))
                .isEqualTo(GitHubCustomPropertyBaseValueType.STRING);
    }

    @Test
    void fromWire_shouldReturnSingleSelect_whenKnownValue() {
        assertThat(GitHubCustomPropertyBaseValueType.fromWire("single_select"))
                .isEqualTo(GitHubCustomPropertyBaseValueType.SINGLE_SELECT);
    }

    @Test
    void fromWire_shouldReturnMultiSelect_whenKnownValue() {
        assertThat(GitHubCustomPropertyBaseValueType.fromWire("multi_select"))
                .isEqualTo(GitHubCustomPropertyBaseValueType.MULTI_SELECT);
    }

    @Test
    void fromWire_shouldReturnTrueFalse_whenKnownValue() {
        assertThat(GitHubCustomPropertyBaseValueType.fromWire("true_false"))
                .isEqualTo(GitHubCustomPropertyBaseValueType.TRUE_FALSE);
    }

    @Test
    void fromWire_shouldReturnUrl_whenKnownValue() {
        assertThat(GitHubCustomPropertyBaseValueType.fromWire("url")).isEqualTo(GitHubCustomPropertyBaseValueType.URL);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubCustomPropertyBaseValueType.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
