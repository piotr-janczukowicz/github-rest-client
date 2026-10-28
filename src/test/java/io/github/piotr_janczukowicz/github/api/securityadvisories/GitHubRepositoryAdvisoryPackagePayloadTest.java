package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryAdvisoryPackagePayloadTest {

    @Test
    void constructor_shouldAllowNullName_whenEcosystemPresent() {
        final GitHubRepositoryAdvisoryPackagePayload advisoryPackage =
                new GitHubRepositoryAdvisoryPackagePayload(GitHubSecurityAdvisoryEcosystem.NPM, null);

        assertSoftly(softly -> {
            softly.assertThat(advisoryPackage.ecosystem()).isEqualTo(GitHubSecurityAdvisoryEcosystem.NPM);
            softly.assertThat(advisoryPackage.name()).isNull();
        });
    }

    @Test
    void constructor_shouldThrow_whenEcosystemNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubRepositoryAdvisoryPackagePayload(null, "a-package"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ecosystem");
    }
}
