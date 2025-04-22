package ma.enset.reservation.web;


import lombok.AllArgsConstructor;
import ma.enset.reservation.DAO.entites.Entreprise;
import ma.enset.reservation.DAO.repository.EntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.reservation.DAO.entites.Entreprise;
import ma.enset.reservation.DAO.entites.Reservation;
import ma.enset.reservation.DAO.entites.TypeReservation;
import ma.enset.reservation.DAO.entites.EtatReservation;
import ma.enset.reservation.DAO.repository.ReservationRepository;
import ma.enset.reservation.DAO.repository.EntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EntrepriseRepository entrepriseRepository;

//    @GetMapping("/reservations")
//    public String index(Model model,
//                        @RequestParam(name = "page", defaultValue = "0") int p,
//                        @RequestParam(name = "size", defaultValue = "4") int s,
//                        @RequestParam(name = "keyword", defaultValue = "") String kw) {
//
//        // Recherche des réservations dont le nom de l'entreprise contient le mot-clé
//        Page<Reservation> pageRes = reservationRepository
//                .findByEntrepriseNomContains(kw, PageRequest.of(p, s));
//
//        model.addAttribute("reservationList", pageRes);
//        model.addAttribute("pages", new int[pageRes.getTotalPages()]);
//        model.addAttribute("currentPage", p);
//        model.addAttribute("keyword", kw);
//        return "reservations";
//    }

    @GetMapping("/reservations")
    public String index(
            Model model,
            @RequestParam(name = "page",    defaultValue = "0") int p,
            @RequestParam(name = "size",    defaultValue = "4") int s,
            @RequestParam(name = "date",    required = false)
            @org.springframework.format.annotation.DateTimeFormat(iso = org.springframework.format.annotation.DateTimeFormat.ISO.DATE)
            LocalDate date  // format attendu : yyyy‑MM‑dd
    ) {
        Page<Reservation> pageRes;
        if (date != null) {
            // on filtre par dateReservation
            pageRes = reservationRepository.findByDateReservation(date, PageRequest.of(p, s));
        } else {
            // sinon on récupère tout
            pageRes = reservationRepository.findAll(PageRequest.of(p, s));
        }

        model.addAttribute("reservationList", pageRes);
        model.addAttribute("pages",           new int[pageRes.getTotalPages()]);
        model.addAttribute("currentPage",     p);
        model.addAttribute("date",            date != null ? date : "");
        return "reservations";
    }

    @GetMapping("/formReservations")
    public String formReservations(Model model) {
        model.addAttribute("reservation", new Reservation());
        model.addAttribute("entreprises", entrepriseRepository.findAll());
        model.addAttribute("types", TypeReservation.values());
        model.addAttribute("etats", EtatReservation.values());
        return "formReservations";
    }

    @PostMapping("/saveReservations")
    public String save(Model model,
                       @Valid Reservation reservation,
                       BindingResult bindingResult,
                       @RequestParam(name = "page", defaultValue = "0") int page,
                       @RequestParam(name = "keyword", defaultValue = "") String keyword) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("entreprises", entrepriseRepository.findAll());
            model.addAttribute("types", TypeReservation.values());
            model.addAttribute("etats", EtatReservation.values());
            return "formReservations";
        }
        reservationRepository.save(reservation);
        return "redirect:reservations?page=" + page + "&keyword=" + keyword;
    }

    @GetMapping("/editReservation")
    public String editReservation(Model model,
                                  Long id,
                                  String keyword,
                                  int page) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));
        model.addAttribute("reservation", reservation);
        model.addAttribute("entreprises", entrepriseRepository.findAll());
        model.addAttribute("types", TypeReservation.values());
        model.addAttribute("etats", EtatReservation.values());
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "editReservation";
    }

    @GetMapping("/deleteReservation")
    public String deleteReservation(Long id,
                                    String keyword,
                                    int page) {
        reservationRepository.deleteById(id);
        return "redirect:reservations?page=" + page + "&keyword=" + keyword;
    }
}
