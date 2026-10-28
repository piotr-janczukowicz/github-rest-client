package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCustomOrganizationRoleCreateBaseRoleTest {

    @Test
    void fromWire_shouldReturnRead_whenKnownValue() {
        assertThat(GitHubCustomOrganizationRoleCreateBaseRole.fromWire("read"))
                .isEqualTo(GitHubCustomOrganizationRoleCreateBaseRole.READ);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnRead() {
        assertThat(GitHubCustomOrganizationRoleCreateBaseRole.READ.wireValue()).isEqualTo("read");
    }

    @Test
    void fromWire_shouldReturnTriage_whenKnownValue() {
        assertThat(GitHubCustomOrganizationRoleCreateBaseRole.fromWire("triage"))
                .isEqualTo(GitHubCustomOrganizationRoleCreateBaseRole.TRIAGE);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnTriage() {
        assertThat(GitHubCustomOrganizationRoleCreateBaseRole.TRIAGE.wireValue())
                .isEqualTo("triage");
    }

    @Test
    void fromWire_shouldReturnWrite_whenKnownValue() {
        assertThat(GitHubCustomOrganizationRoleCreateBaseRole.fromWire("write"))
                .isEqualTo(GitHubCustomOrganizationRoleCreateBaseRole.WRITE);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnWrite() {
        assertThat(GitHubCustomOrganizationRoleCreateBaseRole.WRITE.wireValue()).isEqualTo("write");
    }

    @Test
    void fromWire_shouldReturnMaintain_whenKnownValue() {
        assertThat(GitHubCustomOrganizationRoleCreateBaseRole.fromWire("maintain"))
                .isEqualTo(GitHubCustomOrganizationRoleCreateBaseRole.MAINTAIN);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnMaintain() {
        assertThat(GitHubCustomOrganizationRoleCreateBaseRole.MAINTAIN.wireValue())
                .isEqualTo("maintain");
    }

    @Test
    void fromWire_shouldReturnAdmin_whenKnownValue() {
        assertThat(GitHubCustomOrganizationRoleCreateBaseRole.fromWire("admin"))
                .isEqualTo(GitHubCustomOrganizationRoleCreateBaseRole.ADMIN);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnAdmin() {
        assertThat(GitHubCustomOrganizationRoleCreateBaseRole.ADMIN.wireValue()).isEqualTo("admin");
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCustomOrganizationRoleCreateBaseRole.fromWire("not-a-known-value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCustomOrganizationRoleCreateBaseRole.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
