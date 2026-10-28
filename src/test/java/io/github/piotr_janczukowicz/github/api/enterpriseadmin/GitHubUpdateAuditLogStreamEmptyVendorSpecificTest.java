package io.github.piotr_janczukowicz.github.api.enterpriseadmin;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import io.github.piotr_janczukowicz.testsupport.UnitTest;
import java.util.Map;
import org.junit.jupiter.api.Test;

@UnitTest
class GitHubUpdateAuditLogStreamEmptyVendorSpecificTest {

    @Test
    void constructor_shouldUseEmptyMap_whenNoArgument() {
        assertThat(new GitHubUpdateAuditLogStreamEmptyVendorSpecific().properties())
                .isEmpty();
    }

    @Test
    void constructor_shouldUseEmptyMap_whenNull() {
        assertThat(new GitHubUpdateAuditLogStreamEmptyVendorSpecific(null).properties())
                .isEmpty();
    }

    @Test
    void constructor_shouldAcceptEmptyMap_whenEmpty() {
        assertThat(new GitHubUpdateAuditLogStreamEmptyVendorSpecific(Map.of()).properties())
                .isEmpty();
    }

    @Test
    void constructor_shouldThrowIllegalArgumentException_whenNotEmpty() {
        final Throwable thrown =
                catchThrowable(() -> new GitHubUpdateAuditLogStreamEmptyVendorSpecific(Map.of("name", "octo")));

        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}
