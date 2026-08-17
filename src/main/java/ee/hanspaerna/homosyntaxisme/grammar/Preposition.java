package ee.hanspaerna.homosyntaxisme.grammar;

import java.util.List;

public class Preposition extends Word {
    public Preposition(long lemmaId, String value) {
        super(lemmaId, value);
    }

    @Override
    public Word findRandomGrammaticallySimilar(List<Token> searchArea) {
        return super.findRandomGrammaticallySimilar(searchArea);
    }
}
