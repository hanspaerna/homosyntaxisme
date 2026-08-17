package ee.hanspaerna.homosyntaxisme.grammar;

import com.github.demidko.aot.morphology.MorphologyTag;
import ee.hanspaerna.homosyntaxisme.grammar.enums.*;

import java.util.List;

public class TransgressiveParticiple extends Word {
    private GrammaticalTense grammaticalTense;
    private GrammaticalPerfective grammaticalPerfective;

    public TransgressiveParticiple(long lemmaId, String value, List<MorphologyTag> morphologyTags) {
        for (MorphologyTag tag : morphologyTags) {
            switch (tag) {
                // tense
                case PastTime -> grammaticalTense = GrammaticalTense.PAST;
                case FutureTime -> grammaticalTense = GrammaticalTense.FUTURE;
                case PresentTense -> grammaticalTense = GrammaticalTense.PRESENT;
                // perfective aspect
                case PerfectVerb -> grammaticalPerfective = GrammaticalPerfective.PERFECT;
                case Imperfect -> grammaticalPerfective = GrammaticalPerfective.IMPERFECT;
            }
        }

        super(lemmaId, value);
    }

    @Override
    public Word findRandomGrammaticallySimilar(List<Token> searchArea) {
        List<Token> reducedSearchArea = searchArea.stream().filter((token) -> {
            TransgressiveParticiple transgressiveParticiple = (TransgressiveParticiple) token;
            return (this.grammaticalTense == null || this.grammaticalTense.equals(transgressiveParticiple.grammaticalTense))
                    && (this.grammaticalPerfective == null || this.grammaticalPerfective.equals(transgressiveParticiple.grammaticalPerfective));
        }).toList();

        return super.findRandomGrammaticallySimilar(reducedSearchArea);
    }
}
