package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubMinimalRepositoryLicenseTest {

    @Test
    void builder_shouldCopyComponents_whenCopyingFromLicense() {
        final GitHubMinimalRepositoryLicense license = GitHubMinimalRepositoryLicenseBuilder.builder(
                        RepositoriesCatalogFixtures.minimalRepositoryLicense())
                .key("apache-2.0")
                .build();

        assertThat(license.key()).isEqualTo("apache-2.0");
        assertThat(license.name()).isEqualTo("MIT License");
    }
}
