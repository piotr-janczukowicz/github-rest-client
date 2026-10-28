package io.github.piotr_janczukowicz.github.api.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRepositoryTokenRedactionTest {

    @Test
    void toString_shouldOmitTempCloneToken_whenFullRepositoryDescribed() {
        final GitHubFullRepository repository = RepositoriesCatalogFixtures.fullRepository();

        assertThat(repository.toString()).startsWith("GitHubFullRepository[id=").doesNotContain("tempCloneToken");
    }

    @Test
    void toString_shouldOmitTempCloneToken_whenMinimalRepositoryHasToken() {
        final GitHubMinimalRepository repository = GitHubMinimalRepositoryBuilder.builder(
                        RepositoriesCatalogFixtures.helloWorldMinimalRepository())
                .tempCloneToken("clone-token-value")
                .build();

        assertThat(repository.toString()).doesNotContain("clone-token-value");
    }

    @Test
    void toString_shouldOmitTempCloneToken_whenRepositoryDescribed() {
        final GitHubRepository repository = RepositoriesCatalogFixtures.teamEnvironmentRepository();

        assertThat(repository.toString()).startsWith("GitHubRepository[id=").doesNotContain("tempCloneToken");
    }
}
