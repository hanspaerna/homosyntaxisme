package ee.hanspaerna.homosyntaxisme.grammar;

import com.github.demidko.aot.morphology.MorphologyTag;
import ee.hanspaerna.homosyntaxisme.grammar.enums.GrammaticalCase;
import ee.hanspaerna.homosyntaxisme.grammar.enums.GrammaticalGender;
import ee.hanspaerna.homosyntaxisme.grammar.enums.GrammaticalNumber;
import ee.hanspaerna.homosyntaxisme.grammar.enums.GrammaticalPerson;

import java.util.List;

public class Pronoun extends Word {
    private GrammaticalNumber grammaticalNumber;
    private GrammaticalCase grammaticalCase;
    private GrammaticalPerson grammaticalPerson;

    public Pronoun(long lemmaId, String value, List<MorphologyTag> morphologyTags) {
        for (MorphologyTag tag : morphologyTags) {
            switch (tag) {
                // number
                case Singular -> grammaticalNumber = GrammaticalNumber.SINGULAR;
                case Plural -> grammaticalNumber = GrammaticalNumber.PLURAL;
                // case
                case Nominative -> grammaticalCase = GrammaticalCase.NOMINATIVE;
                case Genitive ->  grammaticalCase = GrammaticalCase.GENITIVE;
                case Accusative -> grammaticalCase = GrammaticalCase.ACCUSATIVE;
                case Dative -> grammaticalCase = GrammaticalCase.DATIVE;
                case Ablative -> grammaticalCase = GrammaticalCase.ABLATIVE;
                case Prepositional -> grammaticalCase = GrammaticalCase.PREPOSITIONAL;
                // person
                case FirstPerson -> grammaticalPerson = GrammaticalPerson.FIRST;
                case SecondPerson -> grammaticalPerson = GrammaticalPerson.SECOND;
                case ThirdPerson -> grammaticalPerson = GrammaticalPerson.THIRD;
            }
        }

        super(lemmaId, value);
    }

    @Override
    public Word findRandomGrammaticallySimilar(List<Token> searchArea) {
        List<Token> reducedSearchArea = searchArea.stream().filter((token) -> {
            Pronoun pronoun = (Pronoun) token;
            return (this.grammaticalNumber == null || this.grammaticalNumber.equals(pronoun.grammaticalNumber))
                    && (this.grammaticalCase == null || this.grammaticalCase.equals(pronoun.grammaticalCase))
                    && (this.grammaticalPerson == null || this.grammaticalPerson.equals(pronoun.grammaticalPerson));
        }).toList();

        return super.findRandomGrammaticallySimilar(reducedSearchArea);
    }
}
