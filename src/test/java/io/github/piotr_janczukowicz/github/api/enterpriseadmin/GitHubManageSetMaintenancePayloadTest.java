package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubManageSetMaintenancePayloadTest {

    @Test
    void constructor_shouldAcceptNull_whenIpExceptionListNull() {
        final GitHubManageSetMaintenancePayload value = GitHubManageSetMaintenancePayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.manageSetMaintenancePayload())
                .ipExceptionList(null)
                .build();

        assertThat(value.ipExceptionList()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenIpExceptionListGiven() {
        final List<String> source = new ArrayList<>(List.of("first"));
        final GitHubManageSetMaintenancePayload value = GitHubManageSetMaintenancePayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.manageSetMaintenancePayload())
                .ipExceptionList(source)
                .build();

        source.add("second");

        assertThat(value.ipExceptionList()).containsExactly("first");
    }
}
