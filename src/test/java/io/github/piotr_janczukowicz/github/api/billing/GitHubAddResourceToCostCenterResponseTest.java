package io.github.piotr_janczukowicz.github.api.billing;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubAddResourceToCostCenterResponseTest {

    @Test
    void fixture_shouldBuildRecord_whenFixtureUsed() {
        assertThat(BillingCatalogFixtures.addResourceToCostCenterResponse()).isNotNull();
    }

    @Test
    void constructor_shouldLeaveReassignedResourcesNull_whenNull() {
        final GitHubAddResourceToCostCenterResponse value = GitHubAddResourceToCostCenterResponseBuilder.builder(
                        BillingCatalogFixtures.addResourceToCostCenterResponse())
                .reassignedResources(null)
                .build();

        assertThat(value.reassignedResources()).isNull();
    }
}
