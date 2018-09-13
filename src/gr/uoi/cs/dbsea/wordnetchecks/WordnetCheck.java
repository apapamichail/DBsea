package gr.uoi.cs.dbsea.wordnetchecks;

import java.util.ArrayList;

import java.util.List;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.simple.Token;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import gr.uoi.cs.dbsea.generalchecks.TypeOfCases;
/**
 * 
 * @author Papamichail Aggelos
 * 
 * The class responsible for recognizing a string as a word or string, the part of speech
 * of the word as well as other desirable functionalities.
 * 
 *
 */
public final class WordnetCheck {
	static private WordNetDatabase database = null;
	static private String wordnetPath;
	static private MaxentTagger tagger = null;

	static public void setUpStanfordMaxentTagger() {
		if (tagger == null) {
			String classpath = System.getProperty("java.class.path");
			tagger = new MaxentTagger(
					classpath.split(";")[0] + "\\..\\src\\resources\\english-left3words-distsim.tagger");
		}
	}

	static public boolean isSingular(String word) {
		if (getFirstPosTag(word).equals("NNP") || getFirstPosTag(word).equals("NN")) {
			return true;
		}
		return false;
	}

	static public boolean isPlural(String word) {

		if (getFirstPosTag(word).equals("NNS") || getFirstPosTag(word).equals("NNPS")) {

			return true;
		}
		return false;
	}

	static private String getFirstPosTag(String word) {
		Sentence sent = new Sentence(word);
		Token tok = new edu.stanford.nlp.simple.Token(sent, 0);
		List<String> nerTags = sent.nerTags();
		return sent.posTag(0);
	}

	static public boolean containsSingular(String name, TypeOfCases typeOfCase) {
		initializeWordnet();
		// πώς αντιδράει αν δε βρει;
		// συνδυασμος με cases και εγινε!
		String[] wordsInsideName = brokeWordToPieces(name, typeOfCase);
		for (String word : wordsInsideName) {
			Synset[] firstWordSynset = database.getSynsets(word.toLowerCase(), SynsetType.NOUN);

			if (!areSynsetsOk(firstWordSynset)) {
				continue;
			}
			// Concatenate the command-line arguments
			if (isSingular(word)) {
				return true;
			}
		}
		return false;

	}

	static public boolean containsVerb(String name, TypeOfCases typeOfCase) {
		initializeWordnet();
		// πώς αντιδράει αν δε βρει;
		// συνδυασμος με cases και εγινε!
		String[] wordsInsideName = brokeWordToPieces(name, typeOfCase);
		for (String word : wordsInsideName) {
			Synset[] firstWordSynset = database.getSynsets(word.toLowerCase(), SynsetType.VERB);

			if (!areSynsetsOk(firstWordSynset)) {
				continue;
			}

			return true;

		}
		return false;

	}

	static public boolean containsAdjective(String name, TypeOfCases typeOfCase) {
		initializeWordnet();

		String[] wordsInsideName = brokeWordToPieces(name, typeOfCase);
		for (String word : wordsInsideName) {
			Synset[] firstWordSynset = database.getSynsets(word.toLowerCase(), SynsetType.ADJECTIVE);

			if (!areSynsetsOk(firstWordSynset)) {
				continue;
			}

			return true;

		}
		return false;

	}

	static public boolean containsNoun(String name, TypeOfCases typeOfCase) {
		initializeWordnet();

		String[] wordsInsideName = brokeWordToPieces(name, typeOfCase);
		for (String word : wordsInsideName) {
			Synset[] firstWordSynset = database.getSynsets(word.toLowerCase(), SynsetType.NOUN);

			if (!areSynsetsOk(firstWordSynset)) {
				continue;
			}

			return true;

		}
		return false;

	}

	//
	static public int wordsInName(String name, TypeOfCases typeOfCase) {
		initializeWordnet();

		String[] wordsInsideName = brokeWordToPieces(name, typeOfCase);
		int numberOfWords = 0;
		for (String word : wordsInsideName) {
			Synset[] firstWordSynset = database.getSynsets(word.toLowerCase(),SynsetType.NOUN);// ts(word.toLowerCase(),
																				// SynsetType.ALL_TYPES);
			if (areSynsetsOk(firstWordSynset)) {
				numberOfWords += 1;
			}
			else {
				firstWordSynset = database.getSynsets(word.toLowerCase(),SynsetType.ADJECTIVE);
				if (areSynsetsOk(firstWordSynset)) {
					numberOfWords += 1;
				}
				else {
					firstWordSynset = database.getSynsets(word.toLowerCase(),SynsetType.ADVERB);
					if (areSynsetsOk(firstWordSynset)) {
						numberOfWords += 1;
					}

				}

			}
		}
		return numberOfWords;


	}

	static public int stringsInName(String name, TypeOfCases typeOfCase) {
		return brokeWordToPieces(name, typeOfCase).length;
	}

	static public void initializeWordnet() {

		if (database != null) {
			return;
		}
		database = WordNetDatabase.getFileInstance();
		wordnetPath = "C:\\WordNet\\3.0\\dict";
		System.setProperty("wordnet.database.dir", wordnetPath);

	}

	static public boolean containsPlural(String name, TypeOfCases typeOfCase) {
		initializeWordnet();

		String[] wordsInsideName = brokeWordToPieces(name, typeOfCase);

		for (String word : wordsInsideName) {

			Synset[] firstWordSynset = database.getSynsets(word.toLowerCase(), SynsetType.NOUN);

			if (!areSynsetsOk(firstWordSynset)) {
				continue;
			}
			if (isPlural(word)) {
				return true;
			}

		}
		return false;

	}

	static boolean areSynsetsOk(Synset[] syn) {
		if (syn == null || syn.length == 0)
			return false;
		else
			return true;
	}

	boolean variableNameNotNoun(Synset[] synsetWord, String classWord) {
		int i = 0;
		boolean foundAtSynset = false;
		while (i < synsetWord.length) {
			if (synsetWord[i].toString().contains(classWord)) {
				foundAtSynset = true;
				break;
			}
			i++;
		}
		return foundAtSynset;
	}

	static public String[] brokeWordToPieces(String word, TypeOfCases type) {

		if (type == TypeOfCases.LowerCase) {
			return new String[] { word };
		} else if (type == TypeOfCases.UpperCase) {
			return new String[] { word };
		} else if (type == TypeOfCases.CamelCase) {
			return word.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][a-z])");
		} else if (type == TypeOfCases.PascalCase) {
			return word.split("(?<!(^|[A-Z]))(?=[A-Z])|(?<!^)(?=[A-Z][A-Z])");
		} else if (type == TypeOfCases.LowerCaseWithUnderscore || type == TypeOfCases.UpperCaseWithUnderscore
				|| type == TypeOfCases.OtherCaseWithUnderscore) {
			return word.split("_");
		} else if (type == TypeOfCases.OtherCaseWithRandomCharacter) {
			return word.split("(?<!(^|[SPACE-@]))");
		}

		return null;

	}

}
