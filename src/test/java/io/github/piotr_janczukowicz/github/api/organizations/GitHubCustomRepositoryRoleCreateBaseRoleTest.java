package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCustomRepositoryRoleCreateBaseRoleTest {

    @Test
    void fromWire_shouldReturnRead_whenKnownValue() {
        assertThat(GitHubCustomRepositoryRoleCreateBaseRole.fromWire("read"))
                .isEqualTo(GitHubCustomRepositoryRoleCreateBaseRole.READ);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnRead() {
        assertThat(GitHubCustomRepositoryRoleCreateBaseRole.READ.wireValue()).isEqualTo("read");
    }

    @Test
    void fromWire_shouldReturnTriage_whenKnownValue() {
        assertThat(GitHubCustomRepositoryRoleCreateBaseRole.fromWire("triage"))
                .isEqualTo(GitHubCustomRepositoryRoleCreateBaseRole.TRIAGE);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnTriage() {
        assertThat(GitHubCustomRepositoryRoleCreateBaseRole.TRIAGE.wireValue()).isEqualTo("triage");
    }

    @Test
    void fromWire_shouldReturnWrite_whenKnownValue() {
        assertThat(GitHubCustomRepositoryRoleCreateBaseRole.fromWire("write"))
                .isEqualTo(GitHubCustomRepositoryRoleCreateBaseRole.WRITE);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnWrite() {
        assertThat(GitHubCustomRepositoryRoleCreateBaseRole.WRITE.wireValue()).isEqualTo("write");
    }

    @Test
    void fromWire_shouldReturnMaintain_whenKnownValue() {
        assertThat(GitHubCustomRepositoryRoleCreateBaseRole.fromWire("maintain"))
                .isEqualTo(GitHubCustomRepositoryRoleCreateBaseRole.MAINTAIN);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnMaintain() {
        assertThat(GitHubCustomRepositoryRoleCreateBaseRole.MAINTAIN.wireValue())
                .isEqualTo("maintain");
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCustomRepositoryRoleCreateBaseRole.fromWire("not-a-known-value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCustomRepositoryRoleCreateBaseRole.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
