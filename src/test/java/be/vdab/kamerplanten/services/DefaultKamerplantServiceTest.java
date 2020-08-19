package be.vdab.kamerplanten.services;

import be.vdab.kamerplanten.domain.Kamerplant;
import be.vdab.kamerplanten.repositories.KamerplantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultKamerplantServiceTest {

    private DefaultKamerplantService service;
    @Mock
    private KamerplantRepository repository;

    private static List<Kamerplant> PLANTEN;
    private static Kamerplant DORSTIGE_PLANT;
    private static Kamerplant NIET_DORSTIGE_PLANT;


    @BeforeEach
    void beforeEach() {
        service = new DefaultKamerplantService(repository);
        var datumVerleden = LocalDate.of(2020, 1, 1);
        var datumHeden = LocalDate.now();
        DORSTIGE_PLANT = new Kamerplant("dorstig", 1, "test", datumVerleden);
        NIET_DORSTIGE_PLANT = new Kamerplant("nietDorstig", 100, "test", datumHeden);
        PLANTEN = Stream.of(DORSTIGE_PLANT, NIET_DORSTIGE_PLANT)
                .collect(Collectors.toList());

    }

    @Test
    void findDorstigePlanten() {
        when(repository.findAll()).thenReturn(PLANTEN);
        assertThat(service.findDorstigePlanten()).containsOnly(DORSTIGE_PLANT);
        verify(repository).findAll();
    }

}