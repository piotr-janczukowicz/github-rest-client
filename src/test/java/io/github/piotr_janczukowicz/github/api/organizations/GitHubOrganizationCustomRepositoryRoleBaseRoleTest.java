package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationCustomRepositoryRoleBaseRoleTest {

    @Test
    void fromWire_shouldReturnRead_whenKnownValue() {
        assertThat(GitHubOrganizationCustomRepositoryRoleBaseRole.fromWire("read"))
                .isEqualTo(GitHubOrganizationCustomRepositoryRoleBaseRole.READ);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnRead() {
        assertThat(GitHubOrganizationCustomRepositoryRoleBaseRole.READ.wireValue())
                .isEqualTo("read");
    }

    @Test
    void fromWire_shouldReturnTriage_whenKnownValue() {
        assertThat(GitHubOrganizationCustomRepositoryRoleBaseRole.fromWire("triage"))
                .isEqualTo(GitHubOrganizationCustomRepositoryRoleBaseRole.TRIAGE);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnTriage() {
        assertThat(GitHubOrganizationCustomRepositoryRoleBaseRole.TRIAGE.wireValue())
                .isEqualTo("triage");
    }

    @Test
    void fromWire_shouldReturnWrite_whenKnownValue() {
        assertThat(GitHubOrganizationCustomRepositoryRoleBaseRole.fromWire("write"))
                .isEqualTo(GitHubOrganizationCustomRepositoryRoleBaseRole.WRITE);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnWrite() {
        assertThat(GitHubOrganizationCustomRepositoryRoleBaseRole.WRITE.wireValue())
                .isEqualTo("write");
    }

    @Test
    void fromWire_shouldReturnMaintain_whenKnownValue() {
        assertThat(GitHubOrganizationCustomRepositoryRoleBaseRole.fromWire("maintain"))
                .isEqualTo(GitHubOrganizationCustomRepositoryRoleBaseRole.MAINTAIN);
    }

    @Test
    void wireValue_shouldReturnWire_whenCalledOnMaintain() {
        assertThat(GitHubOrganizationCustomRepositoryRoleBaseRole.MAINTAIN.wireValue())
                .isEqualTo("maintain");
    }

    @Test
    void fromWire_shouldThrowNullPointerException_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubOrganizationCustomRepositoryRoleBaseRole.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubOrganizationCustomRepositoryRoleBaseRole decoded =
                GitHubOrganizationCustomRepositoryRoleBaseRole.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubOrganizationCustomRepositoryRoleBaseRole.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
