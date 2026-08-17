package ee.hanspaerna.homosyntaxisme.grammar;

import com.github.demidko.aot.morphology.MorphologyTag;

import java.util.List;

public class NumeralAdjective extends Adjective {
    public NumeralAdjective(long lemmaId, String value, List<MorphologyTag> morphologyTags) {
        super(lemmaId, value, morphologyTags);
    }

    @Override
    public Word findRandomGrammaticallySimilar(List<Token> searchArea) {
        List<Token> reducedSearchArea = searchArea.stream().filter((token) -> {
            NumeralAdjective numAdjective = (NumeralAdjective) token;
            return (this.grammaticalGender == null || this.grammaticalGender.equals(numAdjective.grammaticalGender))
                    && (this.grammaticalNumber == null || this.grammaticalNumber.equals(numAdjective.grammaticalNumber))
                    && (this.grammaticalCase == null || this.grammaticalCase.equals(numAdjective.grammaticalCase));
        }).toList();

        return super.findRandomGrammaticallySimilar(reducedSearchArea);
    }
}
