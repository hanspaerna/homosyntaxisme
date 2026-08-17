package ee.hanspaerna.homosyntaxisme.controller;

import ee.hanspaerna.homosyntaxisme.grammar.Token;
import ee.hanspaerna.homosyntaxisme.service.MixerService;
import ee.hanspaerna.homosyntaxisme.service.TokenizerService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api")
@AllArgsConstructor
public class TransformationController {
    private MixerService mixerService;
    private TokenizerService tokenizerService;

    @PostMapping(value = "/generate", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public String generateText(
            @RequestParam("ghost") String ghostRaw,
            @RequestParam("textOne") String textOneRaw,
            @RequestParam("textTwo") String textTwoRaw
    ) {
        List<Token> ghost = tokenizerService.tokenize(ghostRaw, true);
        List<Token> textOne = tokenizerService.tokenize(textOneRaw, false);
        List<Token> textTwo = tokenizerService.tokenize(textTwoRaw, false);

        return mixerService.mix(ghost, textOne, textTwo);
    }
}
