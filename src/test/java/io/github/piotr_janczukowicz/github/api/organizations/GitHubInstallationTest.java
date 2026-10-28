package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubInstallationTest {

    @Test
    void constructor_shouldCopySingleFilePaths_whenPresent() {
        final GitHubInstallation installation = installation(List.of("config.yml"));

        assertThat(installation.singleFilePaths()).containsExactly("config.yml");
    }

    @Test
    void constructor_shouldAllowNullSingleFilePaths_whenAbsent() {
        final GitHubInstallation installation = installation(null);

        assertThat(installation.singleFilePaths()).isNull();
    }

    private static GitHubInstallation installation(final List<String> singleFilePaths) {
        final Instant createdAt = Instant.parse("2017-05-16T15:47:09Z");
        return new GitHubInstallation(
                1L,
                null,
                GitHubInstallationRepositorySelection.ALL,
                "https://example.test/tokens",
                "https://example.test/repos",
                "https://example.test/html",
                1L,
                null,
                1L,
                "Organization",
                OrganizationsCatalogFixtures.appPermissions(),
                List.of("push"),
                createdAt,
                createdAt,
                null,
                null,
                singleFilePaths,
                "app",
                null,
                null,
                null);
    }
}
