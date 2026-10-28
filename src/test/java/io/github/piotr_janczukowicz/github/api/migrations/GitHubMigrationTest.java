package io.github.piotr_janczukowicz.github.api.migrations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.github.api.repositories.GitHubSimpleRepository;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubMigrationTest {

    private static final Instant TIMESTAMP = Instant.parse("2015-07-06T22:33:38Z");
    private static final GitHubSimpleRepository REPOSITORY = MigrationsCatalogFixtures.migrationFixtureHelloWorld();

    @Test
    void constructor_shouldThrow_whenNodeIdNull() {
        final Throwable thrown = catchThrowable(
                () -> migration(null, "guid", "state", List.of(REPOSITORY), url(), TIMESTAMP, TIMESTAMP));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("nodeId");
    }

    @Test
    void constructor_shouldThrow_whenGuidNull() {
        final Throwable thrown = catchThrowable(
                () -> migration("node", null, "state", List.of(REPOSITORY), url(), TIMESTAMP, TIMESTAMP));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("guid");
    }

    @Test
    void constructor_shouldThrow_whenStateNull() {
        final Throwable thrown =
                catchThrowable(() -> migration("node", "guid", null, List.of(REPOSITORY), url(), TIMESTAMP, TIMESTAMP));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("state");
    }

    @Test
    void constructor_shouldThrow_whenRepositoriesNull() {
        final Throwable thrown =
                catchThrowable(() -> migration("node", "guid", "state", null, url(), TIMESTAMP, TIMESTAMP));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("repositories");
    }

    @Test
    void constructor_shouldThrow_whenUrlNull() {
        final Throwable thrown = catchThrowable(
                () -> migration("node", "guid", "state", List.of(REPOSITORY), null, TIMESTAMP, TIMESTAMP));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("url");
    }

    @Test
    void constructor_shouldThrow_whenCreatedAtNull() {
        final Throwable thrown =
                catchThrowable(() -> migration("node", "guid", "state", List.of(REPOSITORY), url(), null, TIMESTAMP));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("createdAt");
    }

    @Test
    void constructor_shouldThrow_whenUpdatedAtNull() {
        final Throwable thrown =
                catchThrowable(() -> migration("node", "guid", "state", List.of(REPOSITORY), url(), TIMESTAMP, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("updatedAt");
    }

    private static GitHubMigration migration(
            final String nodeId,
            final String guid,
            final String state,
            final List<GitHubSimpleRepository> repositories,
            final String url,
            final Instant createdAt,
            final Instant updatedAt) {
        return new GitHubMigration(
                1L,
                nodeId,
                null,
                guid,
                state,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                repositories,
                url,
                createdAt,
                updatedAt,
                null,
                null);
    }

    private static String url() {
        return "https://api.github.com/orgs/github/migrations/1";
    }
}
