package io.github.piotr_janczukowicz.github.api.codesecurity;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeSecurityConfigurationRepositoriesTest {

    @Test
    void accessors_shouldReturnValues_whenCreated() {
        final GitHubCodeSecurityConfigurationRepositories repositories =
                CodeSecurityCatalogFixtures.codeSecurityConfigurationRepositories();

        assertSoftly(softly -> {
            softly.assertThat(repositories.status()).isEqualTo(GitHubCodeSecurityAttachmentStatus.ATTACHED);
            softly.assertThat(repositories.repository())
                    .isEqualTo(RepositoriesCatalogFixtures.helloWorldSimpleRepository());
        });
    }
}
