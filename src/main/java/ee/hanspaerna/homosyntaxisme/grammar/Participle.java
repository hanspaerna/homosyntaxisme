package ee.hanspaerna.homosyntaxisme.grammar;

import com.github.demidko.aot.morphology.MorphologyTag;
import ee.hanspaerna.homosyntaxisme.grammar.enums.*;

import java.util.List;

public class Participle extends Word {
    private GrammaticalNumber grammaticalNumber;
    private GrammaticalGender grammaticalGender;
    private GrammaticalTense grammaticalTense;
    private GrammaticalCase grammaticalCase;
    private GrammaticalTransitivity grammaticalTransitivity;

    public Participle(long lemmaId, String value, List<MorphologyTag> morphologyTags) {
        for (MorphologyTag tag : morphologyTags) {
            switch (tag) {
                // number
                case Singular -> grammaticalNumber = GrammaticalNumber.SINGULAR;
                case Plural -> grammaticalNumber = GrammaticalNumber.PLURAL;
                // gender
                case Female -> grammaticalGender = GrammaticalGender.FEMALE;
                case Male -> grammaticalGender = GrammaticalGender.MALE;
                // tense
                case PastTime -> grammaticalTense = GrammaticalTense.PAST;
                case FutureTime -> grammaticalTense = GrammaticalTense.FUTURE;
                case PresentTense -> grammaticalTense = GrammaticalTense.PRESENT;
                // case
                case Nominative -> grammaticalCase = GrammaticalCase.NOMINATIVE;
                case Genitive ->  grammaticalCase = GrammaticalCase.GENITIVE;
                case Accusative -> grammaticalCase = GrammaticalCase.ACCUSATIVE;
                case Dative -> grammaticalCase = GrammaticalCase.DATIVE;
                case Ablative -> grammaticalCase = GrammaticalCase.ABLATIVE;
                case Prepositional -> grammaticalCase = GrammaticalCase.PREPOSITIONAL;
                // transitivity
                case Transive -> grammaticalTransitivity = GrammaticalTransitivity.TRANSITIVE;
                case Intransitive -> grammaticalTransitivity = GrammaticalTransitivity.INTRANSITIVE;
            }
        }

        super(lemmaId, value);
    }

    @Override
    public Word findRandomGrammaticallySimilar(List<Token> searchArea) {
        List<Token> reducedSearchArea = searchArea.stream().filter((token) -> {
            Participle participle = (Participle) token;
            return (this.grammaticalGender == null || this.grammaticalGender.equals(participle.grammaticalGender))
                    && (this.grammaticalNumber == null || this.grammaticalNumber.equals(participle.grammaticalNumber))
                    && (this.grammaticalTense == null || this.grammaticalTense.equals(participle.grammaticalTense))
                    && (this.grammaticalCase == null || this.grammaticalCase.equals(participle.grammaticalCase))
                    && (this.grammaticalTransitivity == null || this.grammaticalTransitivity.equals(participle.grammaticalTransitivity));
        }).toList();

        return super.findRandomGrammaticallySimilar(reducedSearchArea);
    }
}
