package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubScimUserOperationTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubScimUserOperation operation = ScimCatalogFixtures.scimUserOperation();

        assertThat(operation.op()).isEqualTo(GitHubScimUserOperationOp.REPLACE);
        assertThat(operation.path()).isEqualTo("active");
        assertThat(operation.value()).isEqualTo(GitHubScimOperationValue.text("false"));
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenOpNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubScimUserOperation(null, "active", null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("op");
    }
}
