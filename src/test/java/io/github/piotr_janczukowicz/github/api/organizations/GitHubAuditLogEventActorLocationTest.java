package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAuditLogEventActorLocationTest {

    @Test
    void constructor_shouldKeepCountryName_whenBuilt() {
        final GitHubAuditLogEventActorLocation sut = OrganizationsCatalogFixtures.auditLogEventActorLocation();

        assertThat(sut.countryName()).isEqualTo("United States");
    }
}
