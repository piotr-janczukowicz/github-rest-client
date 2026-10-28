package io.github.piotr_janczukowicz.github.api.licenses;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLicenseContentLinksTest {

    @Test
    void constructor_shouldThrow_whenSelfNull() {
        final GitHubLicenseContentLinks valid = LicensesCatalogFixtures.licenseContentLinks();

        final Throwable thrown = catchThrowable(() -> new GitHubLicenseContentLinks(valid.git(), valid.html(), null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("self");
    }
}
