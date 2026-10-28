package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnabledOrganizationsTest {

    @Test
    void fromWire_shouldReturnAll_whenValueAll() {
        assertThat(GitHubEnabledOrganizations.fromWire("all")).isEqualTo(GitHubEnabledOrganizations.ALL);
    }

    @Test
    void fromWire_shouldReturnNone_whenValueNone() {
        assertThat(GitHubEnabledOrganizations.fromWire("none")).isEqualTo(GitHubEnabledOrganizations.NONE);
    }

    @Test
    void fromWire_shouldReturnSelected_whenValueSelected() {
        assertThat(GitHubEnabledOrganizations.fromWire("selected")).isEqualTo(GitHubEnabledOrganizations.SELECTED);
    }

    @Test
    void wireValue_shouldReturnWireString_whenConstant() {
        assertThat(GitHubEnabledOrganizations.SELECTED.wireValue()).isEqualTo("selected");
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenValueNull() {
        final Throwable thrown = catchThrowable(() -> GitHubEnabledOrganizations.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown = catchThrowable(() -> GitHubEnabledOrganizations.fromWire("unknown-value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
