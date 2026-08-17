package ee.hanspaerna.homosyntaxisme.grammar;

import com.github.demidko.aot.morphology.MorphologyTag;
import ee.hanspaerna.homosyntaxisme.grammar.enums.*;

import java.util.List;

public class ShortParticiple extends Word {
    private GrammaticalNumber grammaticalNumber;
    private GrammaticalGender grammaticalGender;
    private GrammaticalTense grammaticalTense;
    private GrammaticalTransitivity grammaticalTransitivity;
    private GrammaticalPassivity grammaticalPassivity;

    public ShortParticiple(long lemmaId, String value, List<MorphologyTag> morphologyTags) {
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
                // transitivity
                case Transive -> grammaticalTransitivity = GrammaticalTransitivity.TRANSITIVE;
                case Intransitive -> grammaticalTransitivity = GrammaticalTransitivity.INTRANSITIVE;
                // passivity
                case ActiveVoice -> grammaticalPassivity = GrammaticalPassivity.ACTIVE;
                case PassiveParticiple -> grammaticalPassivity = GrammaticalPassivity.PASSIVE;
            }
        }

        super(lemmaId, value);
    }

    @Override
    public Word findRandomGrammaticallySimilar(List<Token> searchArea) {
        List<Token> reducedSearchArea = searchArea.stream().filter((token) -> {
            ShortParticiple shortParticiple = (ShortParticiple) token;
            return (this.grammaticalGender == null || this.grammaticalGender.equals(shortParticiple.grammaticalGender))
                    && (this.grammaticalNumber == null || this.grammaticalNumber.equals(shortParticiple.grammaticalNumber))
                    && (this.grammaticalTense == null || this.grammaticalTense.equals(shortParticiple.grammaticalTense))
                    && (this.grammaticalTransitivity == null || this.grammaticalTransitivity.equals(shortParticiple.grammaticalTransitivity))
                    && (this.grammaticalPassivity == null || this.grammaticalPassivity.equals(shortParticiple.grammaticalPassivity));

        }).toList();

        return super.findRandomGrammaticallySimilar(reducedSearchArea);
    }
}
