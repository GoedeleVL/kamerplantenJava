package be.vdab.kamerplanten.restcontrollers;

import be.vdab.kamerplanten.domain.Kamerplant;
import be.vdab.kamerplanten.exceptions.KamerplantNietGevondenException;
import be.vdab.kamerplanten.services.KamerplantService;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(exposedHeaders = "Location")
@ExposesResourceFor(Kamerplant.class)
@RequestMapping("planten")
class KamerplantController {
    private final KamerplantService kamerplantService;
    private final EntityLinks entityLinks;

    public KamerplantController(KamerplantService kamerplantService, EntityLinks entityLinks) {
        this.kamerplantService = kamerplantService;
        this.entityLinks = entityLinks;
    }

    @GetMapping
    List<Kamerplant> findAll() {
        return kamerplantService.findAll();
    }

    @GetMapping("dorstig")
    List<Kamerplant> findDorstigePlanten() {
        return kamerplantService.findDorstigePlanten();
    }

    @GetMapping("{id}")
    Kamerplant get(@PathVariable long id) {
        return kamerplantService.findById(id)
                .orElseThrow(KamerplantNietGevondenException::new);
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable long id) {
        kamerplantService.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    HttpHeaders create(@RequestBody @Valid Kamerplant kamerplant) {
        kamerplantService.create(kamerplant);
        var link = entityLinks.linkToItemResource(Kamerplant.class, kamerplant.getId());
        var headers = new HttpHeaders();
        headers.setLocation(link.toUri());
        return headers;
    }

    @PutMapping("{id}")
    void put(@RequestBody @Valid Kamerplant kamerplant) {
        kamerplantService.update(kamerplant);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    Map<String, String> verkeerdeData(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        FieldError::getDefaultMessage)
                );
    }

    @ExceptionHandler(KamerplantNietGevondenException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    void kamerplantNietGevonden() {
    }

}
