package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCustomRepositoryRoleUpdateBaseRoleTest {

    @Test
    void fromWire_shouldReturnRead_whenKnownValue() {
        assertThat(GitHubCustomRepositoryRoleUpdateBaseRole.fromWire("read"))
                .isEqualTo(GitHubCustomRepositoryRoleUpdateBaseRole.READ);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnRead() {
        assertThat(GitHubCustomRepositoryRoleUpdateBaseRole.READ.wireValue()).isEqualTo("read");
    }

    @Test
    void fromWire_shouldReturnTriage_whenKnownValue() {
        assertThat(GitHubCustomRepositoryRoleUpdateBaseRole.fromWire("triage"))
                .isEqualTo(GitHubCustomRepositoryRoleUpdateBaseRole.TRIAGE);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnTriage() {
        assertThat(GitHubCustomRepositoryRoleUpdateBaseRole.TRIAGE.wireValue()).isEqualTo("triage");
    }

    @Test
    void fromWire_shouldReturnWrite_whenKnownValue() {
        assertThat(GitHubCustomRepositoryRoleUpdateBaseRole.fromWire("write"))
                .isEqualTo(GitHubCustomRepositoryRoleUpdateBaseRole.WRITE);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnWrite() {
        assertThat(GitHubCustomRepositoryRoleUpdateBaseRole.WRITE.wireValue()).isEqualTo("write");
    }

    @Test
    void fromWire_shouldReturnMaintain_whenKnownValue() {
        assertThat(GitHubCustomRepositoryRoleUpdateBaseRole.fromWire("maintain"))
                .isEqualTo(GitHubCustomRepositoryRoleUpdateBaseRole.MAINTAIN);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnMaintain() {
        assertThat(GitHubCustomRepositoryRoleUpdateBaseRole.MAINTAIN.wireValue())
                .isEqualTo("maintain");
    }

    @Test
    void fromWire_shouldThrowIllegalArgumentException_whenValueUnknown() {
        final Throwable thrown =
                catchThrowable(() -> GitHubCustomRepositoryRoleUpdateBaseRole.fromWire("not-a-known-value"));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCustomRepositoryRoleUpdateBaseRole.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
