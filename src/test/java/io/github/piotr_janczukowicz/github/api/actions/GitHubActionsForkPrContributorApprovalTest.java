package io.github.piotr_janczukowicz.github.api.actions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubActionsForkPrContributorApprovalTest {

    @Test
    void constructor_shouldThrowNullPointerException_whenApprovalPolicyNull() {
        final Throwable thrown = catchThrowable(() -> new GitHubActionsForkPrContributorApproval(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("approvalPolicy");
    }
}
