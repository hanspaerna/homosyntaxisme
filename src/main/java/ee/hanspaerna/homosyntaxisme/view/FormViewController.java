package ee.hanspaerna.homosyntaxisme.view;

import ee.hanspaerna.homosyntaxisme.controller.TransformationController;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class FormViewController {
    private TransformationController transformationController;

    @GetMapping("/")
    public String form(Model model) {
        model.addAttribute("textForm", new TextForm());
        return "form";
    }

    @PostMapping("/")
    public String submit(@ModelAttribute TextForm textForm, Model model) {
        textForm.setResult(transformationController.generateText(
                textForm.getGhost(), textForm.getTextOne(), textForm.getTextTwo()
        ));

        model.addAttribute("result", textForm);
        return "form";
    }

    @GetMapping("/help")
    public String help() {
        return "help";
    }
}
