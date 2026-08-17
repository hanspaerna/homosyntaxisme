package ee.hanspaerna.homosyntaxisme.grammar;

import com.github.demidko.aot.morphology.MorphologyTag;
import ee.hanspaerna.homosyntaxisme.grammar.enums.GrammaticalCase;
import ee.hanspaerna.homosyntaxisme.grammar.enums.GrammaticalComparativity;
import ee.hanspaerna.homosyntaxisme.grammar.enums.GrammaticalGender;
import ee.hanspaerna.homosyntaxisme.grammar.enums.GrammaticalNumber;

import java.util.List;

public class Adjective extends Word {
    protected GrammaticalNumber grammaticalNumber;
    protected GrammaticalCase grammaticalCase;
    protected GrammaticalGender grammaticalGender;
    protected GrammaticalComparativity grammaticalComparativity;

    public Adjective(long lemmaId, String value, List<MorphologyTag> morphologyTags) {
        for (MorphologyTag tag : morphologyTags) {
            switch (tag) {
                // number
                case Singular -> grammaticalNumber = GrammaticalNumber.SINGULAR;
                case Plural -> grammaticalNumber = GrammaticalNumber.PLURAL;
                // gender
                case Female -> grammaticalGender = GrammaticalGender.FEMALE;
                case Male -> grammaticalGender = GrammaticalGender.MALE;
                // case
                case Nominative -> grammaticalCase = GrammaticalCase.NOMINATIVE;
                case Genitive ->  grammaticalCase = GrammaticalCase.GENITIVE;
                case Accusative -> grammaticalCase = GrammaticalCase.ACCUSATIVE;
                case Dative -> grammaticalCase = GrammaticalCase.DATIVE;
                case Ablative -> grammaticalCase = GrammaticalCase.ABLATIVE;
                case Prepositional -> grammaticalCase = GrammaticalCase.PREPOSITIONAL;
                // comparativity
                case Comparative -> grammaticalComparativity = GrammaticalComparativity.COMPARATIVE;
            }
        }

        super(lemmaId, value);
    }

    @Override
    public Word findRandomGrammaticallySimilar(List<Token> searchArea) {
        List<Token> reducedSearchArea = searchArea.stream().filter((token) -> {
            Adjective adjective = (Adjective) token;
            return (this.grammaticalGender == null || this.grammaticalGender.equals(adjective.grammaticalGender))
                    && (this.grammaticalNumber == null || this.grammaticalNumber.equals(adjective.grammaticalNumber))
                    && (this.grammaticalCase == null || this.grammaticalCase.equals(adjective.grammaticalCase))
                    && (this.grammaticalComparativity == null || this.grammaticalComparativity.equals(adjective.grammaticalComparativity));
        }).toList();

        return super.findRandomGrammaticallySimilar(reducedSearchArea);
    }
}
