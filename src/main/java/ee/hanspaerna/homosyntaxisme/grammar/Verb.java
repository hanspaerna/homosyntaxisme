package ee.hanspaerna.homosyntaxisme.grammar;

import com.github.demidko.aot.morphology.MorphologyTag;
import ee.hanspaerna.homosyntaxisme.grammar.enums.*;

import java.util.List;

public class Verb extends Word
{
    private GrammaticalNumber grammaticalNumber;
    private GrammaticalGender grammaticalGender;
    private GrammaticalTense grammaticalTense;
    private GrammaticalPerson grammaticalPerson;
    private GrammaticalPerfective grammaticalPerfective;
    private GrammaticalTransitivity grammaticalTransitivity;

    public Verb(long lemmaId, String value, List<MorphologyTag> morphologyTags) {
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
                // person
                case FirstPerson -> grammaticalPerson = GrammaticalPerson.FIRST;
                case SecondPerson -> grammaticalPerson = GrammaticalPerson.SECOND;
                case ThirdPerson -> grammaticalPerson = GrammaticalPerson.THIRD;
                // perfective aspect
                case PerfectVerb -> grammaticalPerfective = GrammaticalPerfective.PERFECT;
                case Imperfect -> grammaticalPerfective = GrammaticalPerfective.IMPERFECT;
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
            Verb verb = (Verb) token;
            return (this.grammaticalGender == null || this.grammaticalGender.equals(verb.grammaticalGender))
                    && (this.grammaticalNumber == null || this.grammaticalNumber.equals(verb.grammaticalNumber))
                    && (this.grammaticalTense == null || this.grammaticalTense.equals(verb.grammaticalTense))
                    && (this.grammaticalPerson == null || this.grammaticalPerson.equals(verb.grammaticalPerson))
                    && (this.grammaticalPerfective == null || this.grammaticalPerfective.equals(verb.grammaticalPerfective))
                    && (this.grammaticalTransitivity == null || this.grammaticalTransitivity.equals(verb.grammaticalTransitivity));
        }).toList();

        return super.findRandomGrammaticallySimilar(reducedSearchArea);
    }
}
