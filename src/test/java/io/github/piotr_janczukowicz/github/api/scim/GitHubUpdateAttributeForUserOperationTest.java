package io.github.piotr_janczukowicz.github.api.scim;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateAttributeForUserOperationTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubUpdateAttributeForUserOperation operation = ScimCatalogFixtures.updateAttributeForUserOperation();

        assertThat(operation.op()).isEqualTo(GitHubUpdateAttributeForUserOperationOp.REPLACE);
        assertThat(operation.path()).isEqualTo("name");
        assertThat(operation.value().object()).isEqualTo(ScimCatalogFixtures.updateAttributeForUserValueObject());
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenOpNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubUpdateAttributeForUserOperation(null, null, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("op");
    }
}
