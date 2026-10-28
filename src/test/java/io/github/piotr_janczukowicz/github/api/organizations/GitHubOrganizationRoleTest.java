package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.time.Instant;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubOrganizationRoleTest {

    @Test
    void constructor_shouldThrow_whenNameNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationRole(
                1L,
                null,
                null,
                null,
                null,
                List.of("read_audit_logs"),
                null,
                Instant.parse("2022-07-04T22:19:11Z"),
                Instant.parse("2022-07-04T22:20:11Z")));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("name");
    }

    @Test
    void constructor_shouldThrow_whenPermissionsNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubOrganizationRole(
                1L,
                "Auditor",
                null,
                null,
                null,
                null,
                null,
                Instant.parse("2022-07-04T22:19:11Z"),
                Instant.parse("2022-07-04T22:20:11Z")));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("permissions");
    }
}
