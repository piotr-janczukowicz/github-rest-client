package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOidcCustomSubRepoTest {

    @Test
    void constructor_shouldAcceptNullIncludeClaimKeys_whenIncludeClaimKeysNull() {
        final GitHubOidcCustomSubRepo subRepo = GitHubOidcCustomSubRepoBuilder.builder(
                        ActionsCatalogFixtures.oidcCustomSubRepo())
                .includeClaimKeys(null)
                .build();

        assertThat(subRepo.includeClaimKeys()).isNull();
    }
}
