package io.github.piotr_janczukowicz.github.api.codesofconduct;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.repositories.RepositoriesCatalogFixtures;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCodeOfConductTest {

    @Test
    void constructor_shouldThrow_whenKeyNull() {
        final GitHubCodeOfConduct base = RepositoriesCatalogFixtures.contributorCovenantWithBody();

        final Throwable thrown = catchThrowable(
                () -> new GitHubCodeOfConduct(null, base.name(), base.url(), base.body(), base.htmlUrl()));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("key");
    }
}
