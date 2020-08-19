package be.vdab.kamerplanten.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class KamerplantTest {

    private static Kamerplant PLANT =
            new Kamerplant("test", 1, "test",
                    LocalDate.of(2020, 1, 1));;

    @Test
    void deadlineIsWatergekregenPlusTijdinterval() {
        assertThat(PLANT.getDeadline())
                .isEqualTo(LocalDate.of(2020, 1, 2));
    }
}