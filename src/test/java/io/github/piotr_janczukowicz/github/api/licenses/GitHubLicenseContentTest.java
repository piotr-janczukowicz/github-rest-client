package io.github.piotr_janczukowicz.github.api.licenses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLicenseContentTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final GitHubLicenseContent valid = LicensesCatalogFixtures.licenseContent();

        final Throwable thrown = catchThrowable(() -> new GitHubLicenseContent(
                valid.size(),
                null,
                valid.path(),
                valid.sha(),
                valid.url(),
                valid.type(),
                valid.content(),
                valid.encoding(),
                valid.htmlUrl(),
                valid.gitUrl(),
                valid.downloadUrl(),
                valid.links(),
                valid.license()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenLinksNull() {
        final GitHubLicenseContent valid = LicensesCatalogFixtures.licenseContent();

        final Throwable thrown = catchThrowable(() -> new GitHubLicenseContent(
                valid.size(),
                valid.name(),
                valid.path(),
                valid.sha(),
                valid.url(),
                valid.type(),
                valid.content(),
                valid.encoding(),
                valid.htmlUrl(),
                valid.gitUrl(),
                valid.downloadUrl(),
                null,
                valid.license()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("links");
    }
}
