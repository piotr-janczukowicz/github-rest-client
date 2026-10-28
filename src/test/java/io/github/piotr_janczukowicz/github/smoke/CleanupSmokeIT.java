package io.github.piotr_janczukowicz.github.smoke;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("smoke")
class CleanupSmokeIT {

    private static final SmokeEnvironment ENV = SmokeEnvironment.fromEnv();

    @BeforeAll
    static void requireToken() {
        ENV.requireConfigured();
    }

    @Test
    void sweep_shouldBeIdempotent_whenRunTwice() {
        final Throwable first = catchThrowable(() -> SmokeCleanup.sweep(ENV));
        final Throwable second = catchThrowable(() -> SmokeCleanup.sweep(ENV));

        assertThat(first).isNull();
        assertThat(second).isNull();
    }
}
