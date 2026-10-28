package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsPoliciesForEnterpriseTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenPoliciesNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsPoliciesForEnterprise(1, null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("policies");
    }

    @Test
    void accessors_shouldReturnComponents_whenBuilt() {
        final GitHubActionsPoliciesForEnterprise value = ActionsCatalogFixtures.actionsPoliciesForEnterprise();

        assertThat(value.totalCount()).isEqualTo(1);
        assertThat(value.policies()).containsExactly(ActionsCatalogFixtures.actionsPolicy());
    }
}
