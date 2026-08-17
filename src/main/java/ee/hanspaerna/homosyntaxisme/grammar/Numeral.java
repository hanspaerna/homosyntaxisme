package ee.hanspaerna.homosyntaxisme.grammar;

import com.github.demidko.aot.morphology.MorphologyTag;
import ee.hanspaerna.homosyntaxisme.grammar.enums.GrammaticalCase;
import ee.hanspaerna.homosyntaxisme.grammar.enums.GrammaticalGender;
import ee.hanspaerna.homosyntaxisme.grammar.enums.GrammaticalNumber;

import java.util.List;

public class Numeral extends Word {
    private GrammaticalCase grammaticalCase;

    public Numeral(long lemmaId, String value, List<MorphologyTag> morphologyTags) {
        for (MorphologyTag tag : morphologyTags) {
            switch (tag) {
                // case
                case Nominative -> grammaticalCase = GrammaticalCase.NOMINATIVE;
                case Genitive ->  grammaticalCase = GrammaticalCase.GENITIVE;
                case Accusative -> grammaticalCase = GrammaticalCase.ACCUSATIVE;
                case Dative -> grammaticalCase = GrammaticalCase.DATIVE;
                case Ablative -> grammaticalCase = GrammaticalCase.ABLATIVE;
                case Prepositional -> grammaticalCase = GrammaticalCase.PREPOSITIONAL;
            }
        }

        super(lemmaId, value);
    }

    @Override
    public Word findRandomGrammaticallySimilar(List<Token> searchArea) {
        List<Token> reducedSearchArea = searchArea.stream().filter((token) -> {
            Numeral numeral = (Numeral) token;
            return this.grammaticalCase == null || this.grammaticalCase.equals(numeral.grammaticalCase);
        }).toList();

        return super.findRandomGrammaticallySimilar(reducedSearchArea);
    }
}
