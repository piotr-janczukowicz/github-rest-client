package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubGetConsumedLicensesTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubGetConsumedLicenses value = EnterpriseAdminCatalogFixtures.getConsumedLicenses();

        assertThat(value.totalSeatsConsumed()).isEqualTo(1);
        assertThat(value.totalSeatsPurchased()).isEqualTo(1);
        assertThat(value.users()).isNotEmpty();
    }

    @Test
    void constructor_shouldLeaveUsersNull_whenNull() {
        final GitHubGetConsumedLicenses value = GitHubGetConsumedLicensesBuilder.builder(
                        EnterpriseAdminCatalogFixtures.getConsumedLicenses())
                .users(null)
                .build();

        assertThat(value.users()).isNull();
    }
}
