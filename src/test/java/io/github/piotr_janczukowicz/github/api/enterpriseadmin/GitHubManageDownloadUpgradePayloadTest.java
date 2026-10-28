package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubManageDownloadUpgradePayloadTest {

    @Test
    void constructor_shouldAcceptNull_whenUuidsNull() {
        final GitHubManageDownloadUpgradePayload value = GitHubManageDownloadUpgradePayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.manageDownloadUpgradePayload())
                .uuids(null)
                .build();

        assertThat(value.uuids()).isNull();
    }

    @Test
    void constructor_shouldCopy_whenUuidsGiven() {
        final List<String> source = new ArrayList<>(List.of("first"));
        final GitHubManageDownloadUpgradePayload value = GitHubManageDownloadUpgradePayloadBuilder.builder(
                        EnterpriseAdminCatalogFixtures.manageDownloadUpgradePayload())
                .uuids(source)
                .build();

        source.add("second");

        assertThat(value.uuids()).containsExactly("first");
    }
}
