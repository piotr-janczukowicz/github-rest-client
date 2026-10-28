package io.github.piotr_janczukowicz.github.api.licenses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLicenseTest {

    @Test
    void constructor_shouldThrow_whenKeyNull() {
        final GitHubLicense valid = LicensesCatalogFixtures.license();

        final Throwable thrown = catchThrowable(() -> new GitHubLicense(
                null,
                valid.name(),
                valid.url(),
                valid.spdxId(),
                valid.nodeId(),
                valid.htmlUrl(),
                valid.description(),
                valid.implementation(),
                valid.permissions(),
                valid.conditions(),
                valid.limitations(),
                valid.body(),
                valid.featured()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("key");
    }

    @Test
    void constructor_shouldThrow_whenPermissionsNull() {
        final GitHubLicense valid = LicensesCatalogFixtures.license();

        final Throwable thrown = catchThrowable(() -> new GitHubLicense(
                valid.key(),
                valid.name(),
                valid.url(),
                valid.spdxId(),
                valid.nodeId(),
                valid.htmlUrl(),
                valid.description(),
                valid.implementation(),
                null,
                valid.conditions(),
                valid.limitations(),
                valid.body(),
                valid.featured()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("permissions");
    }
}
