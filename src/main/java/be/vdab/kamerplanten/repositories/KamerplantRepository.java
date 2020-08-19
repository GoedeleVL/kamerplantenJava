package be.vdab.kamerplanten.repositories;

import be.vdab.kamerplanten.domain.Kamerplant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KamerplantRepository extends JpaRepository<Kamerplant, Long> {

}
