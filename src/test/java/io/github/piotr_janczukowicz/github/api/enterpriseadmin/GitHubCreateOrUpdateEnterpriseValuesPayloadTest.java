package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateOrUpdateEnterpriseValuesPayloadTest {

    @Test
    void builder_shouldKeepEveryField_whenFixtureBuilt() {
        final GitHubCreateOrUpdateEnterpriseValuesPayload value =
                EnterpriseAdminCatalogFixtures.createOrUpdateEnterpriseValuesPayload();

        assertThat(GitHubCreateOrUpdateEnterpriseValuesPayloadBuilder.builder(value)
                        .build())
                .isEqualTo(value);
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenOrganizationLoginsEmpty() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateOrUpdateEnterpriseValuesPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.createOrUpdateEnterpriseValuesPayload())
                .organizationLogins(List.of())
                .build());

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void constructor_shouldThrowNullPointerException_whenPropertiesNull() {
        final Throwable thrown = catchThrowable(() -> GitHubCreateOrUpdateEnterpriseValuesPayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.createOrUpdateEnterpriseValuesPayload())
                .properties(null)
                .build());

        assertThat(thrown).isInstanceOf(NullPointerException.class);
    }
}
