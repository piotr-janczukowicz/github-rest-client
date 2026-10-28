package io.github.piotr_janczukowicz.github.api.copilot;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.github.json.GitHubJsonCodecs;
import io.github.piotr_janczukowicz.json.JsonCodec;
import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.List;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubLongIdRoundTripTest {

    private static final JsonCodec CODEC = GitHubJsonCodecs.defaults();

    @Test
    void encode_shouldRoundTripSelectedRepositoryId_whenRepositoryIdAboveIntMax() {
        final GitHubSetCopilotCodingAgentSelectedRepositoriesForOrganizationPayload value =
                GitHubSetCopilotCodingAgentSelectedRepositoriesForOrganizationPayloadBuilder.builder(
                                CopilotCatalogFixtures
                                        .setCopilotCodingAgentSelectedRepositoriesForOrganizationPayload())
                        .selectedRepositoryIds(List.of(3_000_000_000L))
                        .build();

        final GitHubSetCopilotCodingAgentSelectedRepositoriesForOrganizationPayload decoded = CODEC.decode(
                CODEC.encode(value), GitHubSetCopilotCodingAgentSelectedRepositoriesForOrganizationPayload.class);

        assertThat(decoded).isEqualTo(value);
    }
}
