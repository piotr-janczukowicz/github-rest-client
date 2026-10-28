package io.github.piotr_janczukowicz.github.api.securityadvisories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAdvisoryPackageTest {

    @Test
    void constructor_shouldThrow_whenEcosystemNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubAdvisoryPackage(null, "lodash"));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("ecosystem");
    }
}
