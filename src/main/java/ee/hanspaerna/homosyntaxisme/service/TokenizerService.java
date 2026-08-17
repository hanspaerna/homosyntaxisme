package ee.hanspaerna.homosyntaxisme.service;

import com.github.demidko.aot.WordformMeaning;
import com.github.demidko.aot.morphology.MorphologyTag;
import ee.hanspaerna.homosyntaxisme.grammar.*;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.github.demidko.aot.WordformMeaning.lookupForMeanings;

@Service
public class TokenizerService {
    public List<Token> tokenize(String text, boolean isGhost) {
        final String PUNCTUATION_REGEX = "[\\s+/,.!_?;:]+|\\B'|'\\B";

        Pattern meaningIndexPattern = Pattern.compile("\\[([0-9]+)\\]$");
        Pattern keepGhostWordPattern = Pattern.compile("#$");


        List<String> rawWords = new ArrayList<>(
                Arrays.asList(text.splitWithDelimiters(PUNCTUATION_REGEX, -1))
        );
        rawWords.removeIf(item -> item == null || item.equals(" ") || item.isEmpty());

        List<Token> tokenizedText = new ArrayList<>();

        for (String rawItem : rawWords) {
            if (rawItem.matches(PUNCTUATION_REGEX)) {
                tokenizedText.add(new PunctuationMark(rawItem));
                continue;
            }

            // protect a word ending with "#" from any future analysis and put it unchanged into the result
            Matcher keepGhostWordMatcher = keepGhostWordPattern.matcher(rawItem);
            if (keepGhostWordMatcher.find()) {
                tokenizedText.add(new UnknownWord(rawItem.substring(0, keepGhostWordMatcher.start())));
                continue;
            }

            String item;
            int customMeaningIndex = 0;
            Matcher meaningIndexMatcher = meaningIndexPattern.matcher(rawItem);

            // parse custom meaning index, if present
            if (meaningIndexMatcher.find()) {
                try {
                    customMeaningIndex = Integer.parseInt(meaningIndexMatcher.group(1));
                    item = rawItem.substring(0, meaningIndexMatcher.start());
                } catch (NumberFormatException e) {
                    item = "<INVALID_MEANING_INDEX>";
                }
            } else {
                item = rawItem;
            }

            List<WordformMeaning> meanings = lookupForMeanings(item);

            if (meanings.isEmpty()) {
                continue;
            }

            WordformMeaning meaning;

            // let's always use the first meaning for now
            try {
                meaning = meanings.get(customMeaningIndex);
            } catch (IndexOutOfBoundsException e) {
                tokenizedText.add(new PunctuationMark("BAD_MEANING_INDEX "));
                continue;
            }

            WordformMeaning lemma = meaning.getLemma();
            List<MorphologyTag> morphologyTags = meaning.getMorphology();
            long lemmaId = lemma.getId();

            if (!isGhost) {
                List<WordformMeaning> transformations = lemma.getTransformations();
                for (WordformMeaning transformation : transformations) {
                    tokenizedText.add(
                            createPartOfSpeechWord(transformation.getId(), transformation.toString(), transformation.getMorphology())
                    );
                }
            } else {
                tokenizedText.add(createPartOfSpeechWord(lemmaId, meaning.toString(), morphologyTags));
            }
        }

        if (!isGhost) {
            // duplicates make sense only in the ghost text, let's remove them
            Set<Token> tokenizedSet = new HashSet<>(tokenizedText);
            tokenizedText = tokenizedSet.stream().toList();
        }

        return tokenizedText.stream().filter(Objects::nonNull).toList();
    }

    private Word createPartOfSpeechWord(long lemmaId, String lemmaText, List<MorphologyTag> morphologyTags) {
        Word word = null;

        // define part of speech
        for (MorphologyTag morphologyTag : morphologyTags) {
            word = switch (morphologyTag) {
                case Interjection -> new Interjection(lemmaId, lemmaText);
                case Particle -> new Particle(lemmaId, lemmaText);
                case Union -> new Union(lemmaId, lemmaText);
                case Pretext -> new Preposition(lemmaId, lemmaText);
                case Noun -> new Noun(lemmaId, lemmaText, morphologyTags);
                case Verb -> new Verb(lemmaId, lemmaText, morphologyTags);
                case Adjective -> new Adjective(lemmaId, lemmaText, morphologyTags);
                case Adverb -> new Adverb(lemmaId, lemmaText, morphologyTags);
                case ShortAdjective -> new ShortAdjective(lemmaId, lemmaText, morphologyTags);
                case BriefCommunion -> new ShortParticiple(lemmaId, lemmaText, morphologyTags);
                case VerbalParticiple -> new TransgressiveParticiple(lemmaId, lemmaText, morphologyTags);
                case Numeral -> new Numeral(lemmaId, lemmaText, morphologyTags);
                case OrdinalNumber -> new NumeralAdjective(lemmaId, lemmaText, morphologyTags);
                case Participle -> new Participle(lemmaId, lemmaText, morphologyTags);
                case Pronoun -> new Pronoun(lemmaId, lemmaText, morphologyTags);
                case PronounAdjective -> new PronounAdjective(lemmaId, lemmaText, morphologyTags);
                case Predicative -> new PredicativeAdverb(lemmaId, lemmaText, morphologyTags);
                default -> null;
            };

            if (word != null) {
                break;
            }
        }

        return word;
    }
}
