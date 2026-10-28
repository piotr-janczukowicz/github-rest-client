package io.github.piotr_janczukowicz.github.api.organizations;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubConvertMemberToOutsideCollaboratorPayloadTest {

    @Test
    void constructor_shouldKeepNullAsync_whenAbsent() {
        final GitHubConvertMemberToOutsideCollaboratorPayload payload =
                GitHubConvertMemberToOutsideCollaboratorPayloadBuilder.builder().build();

        assertThat(payload.async()).isNull();
    }
}
