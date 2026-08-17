package ee.hanspaerna.homosyntaxisme.service;

import ee.hanspaerna.homosyntaxisme.grammar.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MixerService {
    public String mix(List<Token> ghost, List<Token> textOne, List<Token> textTwo) {
        Set<Token> mixedWordsSource = new HashSet<>();
        List<Token> tokenList = new ArrayList<>();
        mixedWordsSource.addAll(textOne);
        mixedWordsSource.addAll(textTwo);

        List<Token> verbs = mixedWordsSource.stream().filter(item -> item instanceof Verb).toList();
        List<Token> adjectives = mixedWordsSource.stream().filter(item -> item instanceof Adjective).toList();
        List<Token> shortAdjectives = mixedWordsSource.stream().filter(item -> item instanceof ShortAdjective).toList();
        List<Token> nouns = mixedWordsSource.stream().filter(item -> item instanceof Noun).toList();
        List<Token> particles = mixedWordsSource.stream().filter(item -> item instanceof Particle).toList();
        List<Token> adverbs = mixedWordsSource.stream().filter(item -> item instanceof Adverb).toList();
        List<Token> predicativeAdverbs = mixedWordsSource.stream().filter(item -> item instanceof PredicativeAdverb).toList();
        List<Token> interjections = mixedWordsSource.stream().filter(item -> item instanceof Interjection).toList();
        List<Token> numerals = mixedWordsSource.stream().filter(item -> item instanceof Numeral).toList();
        List<Token> numeralAdjectives = mixedWordsSource.stream().filter(item -> item instanceof NumeralAdjective).toList();
        List<Token> participles = mixedWordsSource.stream().filter(item -> item instanceof Participle).toList();
        List<Token> shortParticiples = mixedWordsSource.stream().filter(item -> item instanceof ShortParticiple).toList();
        List<Token> transgressiveParticiples = mixedWordsSource.stream()
                .filter(item -> item instanceof TransgressiveParticiple).toList();

        for (Token ghostItem : ghost) {
            if (ghostItem == null) {
                continue;
            }

            switch (ghostItem) {
                case PunctuationMark pm -> tokenList.add(pm);
                case Verb w -> tokenList.add(w.findRandomGrammaticallySimilar(verbs));
                case NumeralAdjective w -> tokenList.add(w.findRandomGrammaticallySimilar(numeralAdjectives));
                case PronounAdjective w -> tokenList.add(ghostItem);
                case Adjective w -> tokenList.add(w.findRandomGrammaticallySimilar(adjectives));
                case ShortAdjective w -> tokenList.add(w.findRandomGrammaticallySimilar(shortAdjectives));
                case Noun w -> tokenList.add(w.findRandomGrammaticallySimilar(nouns));
                case Particle w -> tokenList.add(w.findRandomGrammaticallySimilar(particles));
                case PredicativeAdverb w -> tokenList.add(w.findRandomGrammaticallySimilar(predicativeAdverbs));
                case Adverb w -> tokenList.add(w.findRandomGrammaticallySimilar(adverbs));
                case Numeral w -> tokenList.add(w.findRandomGrammaticallySimilar(numerals));
                case Interjection w -> tokenList.add(w.findRandomGrammaticallySimilar(interjections));
                case Participle w -> tokenList.add(w.findRandomGrammaticallySimilar(participles));
                case ShortParticiple w -> tokenList.add(w.findRandomGrammaticallySimilar(shortParticiples));
                case TransgressiveParticiple w -> tokenList.add(w.findRandomGrammaticallySimilar(transgressiveParticiples));
                case Union w -> tokenList.add(ghostItem);
                case Preposition w -> tokenList.add(ghostItem);
                case Pronoun w -> tokenList.add(ghostItem);
                case UnknownWord w -> tokenList.add(ghostItem);
                default -> throw new IllegalStateException("Unexpected value: " + ghostItem);
            }
        }

        return renderText(tokenList);
    }
    
    private String renderText(List<Token> tokenList) {
        StringBuilder resultText = new StringBuilder();

        int i = 0;
        for (Token token : tokenList) {
            String value;
            i++;

            if (token instanceof PunctuationMark) {
                resultText.append(((PunctuationMark) token).value());
            }

            if (token instanceof Word) {
                if (i < tokenList.size() && tokenList.get(i) instanceof PunctuationMark) {
                    value = ((Word) token).value;
                } else {
                    value = ((Word) token).value.concat(" ");
                }
                resultText.append(value);
            }
        }

        return resultText.toString();
    }
}
