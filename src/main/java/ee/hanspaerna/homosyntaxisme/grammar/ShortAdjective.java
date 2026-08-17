package ee.hanspaerna.homosyntaxisme.grammar;

import com.github.demidko.aot.morphology.MorphologyTag;
import ee.hanspaerna.homosyntaxisme.grammar.enums.GrammaticalGender;
import ee.hanspaerna.homosyntaxisme.grammar.enums.GrammaticalNumber;

import java.util.List;

public class ShortAdjective extends Word {
    private GrammaticalNumber grammaticalNumber;
    private GrammaticalGender grammaticalGender;

    public ShortAdjective(long lemmaId, String value, List<MorphologyTag> morphologyTags) {
        for (MorphologyTag tag : morphologyTags) {
            switch (tag) {
                // gender
                case Female -> grammaticalGender = GrammaticalGender.FEMALE;
                case Male -> grammaticalGender = GrammaticalGender.MALE;
                // number
                case Singular -> grammaticalNumber = GrammaticalNumber.SINGULAR;
                case Plural -> grammaticalNumber = GrammaticalNumber.PLURAL;
            }
        }

        super(lemmaId, value);
    }

    @Override
    public Word findRandomGrammaticallySimilar(List<Token> searchArea) {
        List<Token> reducedSearchArea = searchArea.stream().filter((token) -> {
            ShortAdjective shortAdjective = (ShortAdjective) token;
            return (this.grammaticalGender == null || this.grammaticalGender.equals(shortAdjective.grammaticalGender))
                    && (this.grammaticalNumber == null || this.grammaticalNumber.equals(shortAdjective.grammaticalNumber));
        }).toList();

        return super.findRandomGrammaticallySimilar(reducedSearchArea);
    }
}
