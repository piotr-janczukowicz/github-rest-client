package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubCreateEnterpriseTokenInventoryExportPayloadTest {

    @Test
    void accessors_shouldExposeEveryComponent() {
        final GitHubCreateEnterpriseTokenInventoryExportPayload value =
                EnterpriseAdminCatalogFixtures.createEnterpriseTokenInventoryExportPayload();

        assertThat(value.tokenTypes()).isNotEmpty();
        assertThat(value.authorizationState())
                .isEqualTo(GitHubCreateEnterpriseTokenInventoryExportAuthorizationState.CURRENTLY_AUTHORIZED);
        assertThat(value.owner()).isEqualTo("sample-owner");
        assertThat(value.organization()).isEqualTo("sample-organization");
        assertThat(value.application()).isEqualTo("sample-application");
    }

    @Test
    void constructor_shouldLeaveTokenTypesNull_whenNull() {
        final GitHubCreateEnterpriseTokenInventoryExportPayload value =
                GitHubCreateEnterpriseTokenInventoryExportPayloadBuilder.builder(
                                EnterpriseAdminCatalogFixtures.createEnterpriseTokenInventoryExportPayload())
                        .tokenTypes(null)
                        .build();

        assertThat(value.tokenTypes()).isNull();
    }
}
