package ee.hanspaerna.homosyntaxisme.grammar;

import com.github.demidko.aot.morphology.MorphologyTag;

import java.util.List;

public class Adverb extends Word {
    public Adverb(long lemmaId, String value, List<MorphologyTag> morphologyTags) {
        super(lemmaId, value);
    }
}
