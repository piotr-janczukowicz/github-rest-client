package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@UnitTest
class GitHubGetEnterpriseFusionModelPoliciesResponseModelEnterprisePolicyStateTest {

    @ParameterizedTest
    @CsvSource({
        "enabled,ENABLED",
        "disabled,DISABLED",
        "delegate_to_teams_and_apps,DELEGATE_TO_TEAMS_AND_APPS",
        "delegate_to_organizations,DELEGATE_TO_ORGANIZATIONS",
        "delegate_to_default,DELEGATE_TO_DEFAULT",
        "unconfigured,UNCONFIGURED"
    })
    void fromWire_shouldReturnConstant_whenKnownValue(
            final String wire, final GitHubGetEnterpriseFusionModelPoliciesResponseModelEnterprisePolicyState value) {
        final GitHubGetEnterpriseFusionModelPoliciesResponseModelEnterprisePolicyState decoded =
                GitHubGetEnterpriseFusionModelPoliciesResponseModelEnterprisePolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded).isEqualTo(value);
            softly.assertThat(decoded.wireValue()).isEqualTo(wire);
        });
    }

    @Test
    void fromWire_shouldThrow_whenValueNull() {
        final Throwable thrown = catchThrowable(
                () -> GitHubGetEnterpriseFusionModelPoliciesResponseModelEnterprisePolicyState.fromWire(null));

        assertThat(thrown).isInstanceOf(NullPointerException.class).hasMessageContaining("value");
    }

    @Test
    void fromWire_shouldReturnUnrecognized_whenValueNotKnown() {
        final String wire = "brand_new_value";

        final GitHubGetEnterpriseFusionModelPoliciesResponseModelEnterprisePolicyState decoded =
                GitHubGetEnterpriseFusionModelPoliciesResponseModelEnterprisePolicyState.fromWire(wire);

        assertSoftly(softly -> {
            softly.assertThat(decoded)
                    .isEqualTo(GitHubGetEnterpriseFusionModelPoliciesResponseModelEnterprisePolicyState.UNRECOGNIZED);
            softly.assertThat(decoded.wireValue()).isEqualTo("unrecognized");
        });
    }
}
