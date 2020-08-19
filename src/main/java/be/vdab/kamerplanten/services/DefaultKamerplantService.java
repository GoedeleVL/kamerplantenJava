package be.vdab.kamerplanten.services;

import be.vdab.kamerplanten.domain.Kamerplant;
import be.vdab.kamerplanten.exceptions.KamerplantNietGevondenException;
import be.vdab.kamerplanten.repositories.KamerplantRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class DefaultKamerplantService implements KamerplantService {
    private final KamerplantRepository kamerplantRepository;
    private static long MARGE = 2;

    public DefaultKamerplantService(KamerplantRepository kamerplantRepository) {
        this.kamerplantRepository = kamerplantRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Kamerplant> findById(long id) {
        return kamerplantRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Kamerplant> findAll() {
        return kamerplantRepository.findAll();
    }

    @Override
    public List<Kamerplant> findDorstigePlanten() {
        return kamerplantRepository.findAll()
                .stream()
                .filter(plant -> plant.getDeadline()
                        .minusDays(MARGE).isBefore(LocalDate.now()))
                .sorted(Comparator.comparing(Kamerplant::getDeadline))
                .collect(Collectors.toList());
    }

    @Override
    public void create(Kamerplant kamerplant) {
        kamerplantRepository.save(kamerplant);
    }

    @Override
    public void update(Kamerplant kamerplant) {
        kamerplantRepository.save(kamerplant);
    }

    @Override
    public void delete(long id) {
        try {
            kamerplantRepository.deleteById(id);
        } catch (EmptyResultDataAccessException ex) {
            throw new KamerplantNietGevondenException();
        }
    }
}
