package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubRunnerTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenNameNull() {
        final int id = 1;
        final Long runnerGroupId = 1L;
        final String os = "os-sample";
        final String status = "status-sample";
        final boolean busy = true;
        final List<GitHubRunnerLabel> labels = List.of(ActionsCatalogFixtures.runnerLabel());
        final Boolean ephemeral = true;
        final String version = "version-sample";

        final Throwable thrown = catchThrowable(
                () -> new GitHubRunner(id, runnerGroupId, null, os, status, busy, labels, ephemeral, version));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenOsNull() {
        final int id = 1;
        final Long runnerGroupId = 1L;
        final String name = "name-sample";
        final String status = "status-sample";
        final boolean busy = true;
        final List<GitHubRunnerLabel> labels = List.of(ActionsCatalogFixtures.runnerLabel());
        final Boolean ephemeral = true;
        final String version = "version-sample";

        final Throwable thrown = catchThrowable(
                () -> new GitHubRunner(id, runnerGroupId, name, null, status, busy, labels, ephemeral, version));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("os");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenStatusNull() {
        final int id = 1;
        final Long runnerGroupId = 1L;
        final String name = "name-sample";
        final String os = "os-sample";
        final boolean busy = true;
        final List<GitHubRunnerLabel> labels = List.of(ActionsCatalogFixtures.runnerLabel());
        final Boolean ephemeral = true;
        final String version = "version-sample";

        final Throwable thrown = catchThrowable(
                () -> new GitHubRunner(id, runnerGroupId, name, os, null, busy, labels, ephemeral, version));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("status");
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenLabelsNull() {
        final int id = 1;
        final Long runnerGroupId = 1L;
        final String name = "name-sample";
        final String os = "os-sample";
        final String status = "status-sample";
        final boolean busy = true;
        final Boolean ephemeral = true;
        final String version = "version-sample";

        final Throwable thrown = catchThrowable(
                () -> new GitHubRunner(id, runnerGroupId, name, os, status, busy, null, ephemeral, version));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("labels");
    }
}
