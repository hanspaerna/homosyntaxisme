package ee.hanspaerna.homosyntaxisme.grammar;

import java.util.List;
import java.util.Random;

public abstract class Word implements Token {
    public final long lemmaId;
    public final String value;

    public Word(long lemmaId, String value) {
        this.lemmaId = lemmaId;
        this.value = value;
    }

    public Word findRandomGrammaticallySimilar(List<Token> searchArea) {
        if (!searchArea.isEmpty()) {
            int randomIndex = (new Random().nextInt(searchArea.size()));
            return (Word) searchArea.get(randomIndex);
        }

        return null;
    }
}
