package io.github.piotr_janczukowicz.github.api.licenses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLicenseSimpleTest {

    @Test
    void constructor_shouldThrow_whenKeyNull() {
        final GitHubLicenseSimple valid = LicensesCatalogFixtures.licenseSimple();

        final Throwable thrown = catchThrowable(() -> new GitHubLicenseSimple(
                null, valid.name(), valid.url(), valid.spdxId(), valid.nodeId(), valid.htmlUrl()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("key");
    }

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final GitHubLicenseSimple valid = LicensesCatalogFixtures.licenseSimple();

        final Throwable thrown = catchThrowable(() ->
                new GitHubLicenseSimple(valid.key(), valid.name(), valid.url(), valid.spdxId(), null, valid.htmlUrl()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }
}
