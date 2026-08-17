package ee.hanspaerna.homosyntaxisme.grammar;

import java.util.List;

public class Interjection extends Word {
    public Interjection(long lemmaId, String value) {
        super(lemmaId, value);
    }

    @Override
    public Word findRandomGrammaticallySimilar(List<Token> searchArea) {
        return super.findRandomGrammaticallySimilar(searchArea);
    }
}
