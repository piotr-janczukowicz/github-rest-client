package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubEnterpriseTokenInventoryItemOwnerTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubEnterpriseTokenInventoryItemOwner value =
                EnterpriseAdminCatalogFixtures.enterpriseTokenInventoryItemOwner();

        assertThat(value.id()).isEqualTo(1L);
        assertThat(value.login()).isEqualTo("sample-login");
        assertThat(value.name()).isEqualTo("sample-name");
    }
}
