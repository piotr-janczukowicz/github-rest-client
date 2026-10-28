package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCustomOrganizationRoleUpdateBaseRoleTest {

    @Test
    void fromWire_shouldReturnNone_whenKnownValue() {
        assertThat(GitHubCustomOrganizationRoleUpdateBaseRole.fromWire("none"))
                .isEqualTo(GitHubCustomOrganizationRoleUpdateBaseRole.NONE);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnNone() {
        assertThat(GitHubCustomOrganizationRoleUpdateBaseRole.NONE.wireValue()).isEqualTo("none");
    }

    @Test
    void fromWire_shouldReturnRead_whenKnownValue() {
        assertThat(GitHubCustomOrganizationRoleUpdateBaseRole.fromWire("read"))
                .isEqualTo(GitHubCustomOrganizationRoleUpdateBaseRole.READ);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnRead() {
        assertThat(GitHubCustomOrganizationRoleUpdateBaseRole.READ.wireValue()).isEqualTo("read");
    }

    @Test
    void fromWire_shouldReturnTriage_whenKnownValue() {
        assertThat(GitHubCustomOrganizationRoleUpdateBaseRole.fromWire("triage"))
                .isEqualTo(GitHubCustomOrganizationRoleUpdateBaseRole.TRIAGE);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnTriage() {
        assertThat(GitHubCustomOrganizationRoleUpdateBaseRole.TRIAGE.wireValue())
                .isEqualTo("triage");
    }

    @Test
    void fromWire_shouldReturnWrite_whenKnownValue() {
        assertThat(GitHubCustomOrganizationRoleUpdateBaseRole.fromWire("write"))
                .isEqualTo(GitHubCustomOrganizationRoleUpdateBaseRole.WRITE);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnWrite() {
        assertThat(GitHubCustomOrganizationRoleUpdateBaseRole.WRITE.wireValue()).isEqualTo("write");
    }

    @Test
    void fromWire_shouldReturnMaintain_whenKnownValue() {
        assertThat(GitHubCustomOrganizationRoleUpdateBaseRole.fromWire("maintain"))
                .isEqualTo(GitHubCustomOrganizationRoleUpdateBaseRole.MAINTAIN);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnMaintain() {
        assertThat(GitHubCustomOrganizationRoleUpdateBaseRole.MAINTAIN.wireValue())
                .isEqualTo("maintain");
    }

    @Test
    void fromWire_shouldReturnAdmin_whenKnownValue() {
        assertThat(GitHubCustomOrganizationRoleUpdateBaseRole.fromWire("admin"))
                .isEqualTo(GitHubCustomOrganizationRoleUpdateBaseRole.ADMIN);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnAdmin() {
        assertThat(GitHubCustomOrganizationRoleUpdateBaseRole.ADMIN.wireValue()).isEqualTo("admin");
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCustomOrganizationRoleUpdateBaseRole.fromWire("not-a-known-value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCustomOrganizationRoleUpdateBaseRole.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
