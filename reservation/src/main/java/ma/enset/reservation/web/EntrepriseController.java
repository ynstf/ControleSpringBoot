package ma.enset.reservation.web;


import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.enset.reservation.DAO.entites.Entreprise;
import ma.enset.reservation.DAO.repository.EntrepriseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/")
@AllArgsConstructor
public class EntrepriseController {

    @Autowired
    private EntrepriseRepository entrepriseRepository;

    @GetMapping("/entreprises")
    public String index(Model model,
                        @RequestParam(name = "page",defaultValue = "0") int p,
                        @RequestParam(name = "size",defaultValue = "4") int s,
                        @RequestParam(name = "keyword",defaultValue = "") String kw){

        Page<Entreprise> pageEntreprise = entrepriseRepository.findByNomContains(kw, PageRequest.of(p,s));
        model.addAttribute("entrepriseList",pageEntreprise);
        model.addAttribute("pages",new int[pageEntreprise.getTotalPages()]);
        model.addAttribute("currentPage",p);
        model.addAttribute("keyword",kw);
        return "entreprise";
    }

    @GetMapping("/formEntreprises")
    public String formEntreprises(Model model){
        model.addAttribute("entreprise", new Entreprise());
        return "formEntreprises";
    }

    @PostMapping("/saveEntreprises")
    public String save(Model model, @Valid Entreprise entreprise, BindingResult bindingResult,
                       @RequestParam(name = "page",defaultValue = "0") int page,
                       @RequestParam(name = "keyword",defaultValue = "") String keyword
    ){
        if (bindingResult.hasErrors()) return "formEntreprises";
        entrepriseRepository.save(entreprise);
        return "redirect:entreprises?page="+page+"&keyword="+keyword;
    }

    @GetMapping("/editEntreprise")
    public String editPatient(Model model, Long id, String keyword, int page){
        Entreprise entreprise = entrepriseRepository.findById(id).orElse(null);
        if (entreprise == null) throw new RuntimeException("Entreprise not found");
        model.addAttribute("entreprise", entreprise);
        model.addAttribute("page", page);
        model.addAttribute("keyword", keyword);
        return "editEntreprise";
    }
    @GetMapping("/deleteEntreprise")
    public String delete(Long id, String keyword, int page){
        entrepriseRepository.deleteById(id);
        return "redirect:entreprises?page="+page+"&keyword="+keyword;
    }




}
