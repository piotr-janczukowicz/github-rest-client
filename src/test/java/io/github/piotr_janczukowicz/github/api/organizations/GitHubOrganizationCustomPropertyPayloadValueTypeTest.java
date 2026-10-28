package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationCustomPropertyPayloadValueTypeTest {

    @Test
    void fromWire_shouldReturnString_whenKnownValue() {
        assertThat(GitHubOrganizationCustomPropertyPayloadValueType.fromWire("string"))
                .isEqualTo(GitHubOrganizationCustomPropertyPayloadValueType.STRING);
    }

    @Test
    void fromWire_shouldReturnSingleSelect_whenKnownValue() {
        assertThat(GitHubOrganizationCustomPropertyPayloadValueType.fromWire("single_select"))
                .isEqualTo(GitHubOrganizationCustomPropertyPayloadValueType.SINGLE_SELECT);
    }

    @Test
    void fromWire_shouldReturnMultiSelect_whenKnownValue() {
        assertThat(GitHubOrganizationCustomPropertyPayloadValueType.fromWire("multi_select"))
                .isEqualTo(GitHubOrganizationCustomPropertyPayloadValueType.MULTI_SELECT);
    }

    @Test
    void fromWire_shouldReturnTrueFalse_whenKnownValue() {
        assertThat(GitHubOrganizationCustomPropertyPayloadValueType.fromWire("true_false"))
                .isEqualTo(GitHubOrganizationCustomPropertyPayloadValueType.TRUE_FALSE);
    }

    @Test
    void fromWire_shouldReturnUrl_whenKnownValue() {
        assertThat(GitHubOrganizationCustomPropertyPayloadValueType.fromWire("url"))
                .isEqualTo(GitHubOrganizationCustomPropertyPayloadValueType.URL);
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubOrganizationCustomPropertyPayloadValueType.fromWire("unknown"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("unknown");
    }
}
