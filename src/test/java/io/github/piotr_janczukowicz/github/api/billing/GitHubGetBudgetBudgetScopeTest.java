package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubGetBudgetBudgetScopeTest {

    @ParameterizedTest
    @CsvSource({
        "enterprise,ENTERPRISE,organization,ORGANIZATION,repository,REPOSITORY,cost_center,COST_CENTER,multi_user_customer,MULTI_USER_CUSTOMER,multi_user_cost_center,MULTI_USER_COST_CENTER,user,USER"
    })
    void fromWire_shouldReturnValue_whenKnownValue(final String wire, final GitHubGetBudgetBudgetScope value) {
        final GitHubGetBudgetBudgetScope decoded = GitHubGetBudgetBudgetScope.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenNull() {
        final Throwable thrown = catchThrowable(() -> GitHubGetBudgetBudgetScope.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnknown_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubGetBudgetBudgetScope decoded = GitHubGetBudgetBudgetScope.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(GitHubGetBudgetBudgetScope.UNKNOWN);
            softly.assertThat(decoded.wireValue()).isEqualTo("unknown");
        });
    }
}
