package be.vdab.kamerplanten.services;

import be.vdab.kamerplanten.domain.Kamerplant;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface KamerplantService {
    Optional<Kamerplant> findById(long id);
    List<Kamerplant> findAll();
    List<Kamerplant> findDorstigePlanten();
    void create(Kamerplant kamerplant);
    void update(Kamerplant kamerplant);
    void delete(long id);


}
