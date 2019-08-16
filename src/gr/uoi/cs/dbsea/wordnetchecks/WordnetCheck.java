package gr.uoi.cs.dbsea.wordnetchecks;

import edu.smu.tspell.wordnet.Synset;
import edu.smu.tspell.wordnet.SynsetType;
import edu.smu.tspell.wordnet.WordNetDatabase;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import gr.uoi.cs.dbsea.generalchecks.TypeOfCases;
import gr.uoi.cs.dbsea.logger.Logger;

public class WordnetCheck {

	private WordNetDatabase database = null;
	private final String wordnetPath = "C:\\WordNet\\3.0\\dict";//Set wordnet to this path 
	private MaxentTagger tagger;

	public WordnetCheck() {

		initializeWordnet();

	}

	public void setUpStanfordMaxentTagger() {

		try {

			if (tagger == null) {
				String classpath = System.getProperty("java.class.path");
				tagger = new MaxentTagger(
						classpath.split(";")[0] + "\\..\\src\\resources\\english-left3words-distsim.tagger");
			}

		} catch (Exception e) {

			Logger.Log(e);

		}

	}

	public boolean isSingular(String word) {

		try {
			if (getFirstPosTag(word).equals("NNP") || getFirstPosTag(word).equals("NN")) {
				return true;
			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}
	}

	public boolean isPlural(String word) {

		try {
			if (getFirstPosTag(word).equals("NNS") || getFirstPosTag(word).equals("NNPS")) {

				return true;
			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}
	}

	private String getFirstPosTag(String word) {

		try {
			Sentence sent = new Sentence(word);
			return sent.posTag(0);
		} catch (Exception e) {

			Logger.Log(e);
			return null;
		}
	}

	public boolean containsSingular(String name, TypeOfCases typeOfCase) {

		try {

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
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}

	}

	public boolean containsVerb(String name, TypeOfCases typeOfCase) {

		try {

			String[] wordsInsideName = brokeWordToPieces(name, typeOfCase);
			for (String word : wordsInsideName) {
				Synset[] firstWordSynset = database.getSynsets(word.toLowerCase(), SynsetType.VERB);

				if (!areSynsetsOk(firstWordSynset)) {
					continue;
				}
				// Concatenate the command-line arguments
				return true;

			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}

	}

	public boolean containsAdjective(String name, TypeOfCases typeOfCase) {

		try {

			String[] wordsInsideName = brokeWordToPieces(name, typeOfCase);
			for (String word : wordsInsideName) {
				Synset[] firstWordSynset = database.getSynsets(word.toLowerCase(), SynsetType.ADJECTIVE);

				if (!areSynsetsOk(firstWordSynset)) {
					continue;
				}
				return true;

			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}

	}

	public boolean containsNoun(String name, TypeOfCases typeOfCase) {
		try {

			String[] wordsInsideName = brokeWordToPieces(name, typeOfCase);
			for (String word : wordsInsideName) {
				Synset[] firstWordSynset = database.getSynsets(word.toLowerCase(), SynsetType.NOUN);

				if (!areSynsetsOk(firstWordSynset)) {
					continue;
				}
				return true;

			}
			return false;
		} catch (Exception e) {

			Logger.Log(e);
			return false;
		}

	}

	//
	public int wordsInName(String name, TypeOfCases typeOfCase) {

		try {

			String[] wordsInsideName = brokeWordToPieces(name, typeOfCase);
			int numberOfWords = 0;
			for (String word : wordsInsideName) {
				Synset[] firstWordSynset = database.getSynsets(word.toLowerCase(), SynsetType.NOUN);// ts(word.toLowerCase(),
				if (areSynsetsOk(firstWordSynset)) {
					numberOfWords += 1;
				} else {
					firstWordSynset = database.getSynsets(word.toLowerCase(), SynsetType.ADJECTIVE);
					if (areSynsetsOk(firstWordSynset)) {
						numberOfWords += 1;
					} else {
						firstWordSynset = database.getSynsets(word.toLowerCase(), SynsetType.ADVERB);
						if (areSynsetsOk(firstWordSynset)) {
							numberOfWords += 1;
						}

					}

				}
			}
			return numberOfWords;

		} catch (Exception e) {

			Logger.Log(e);
			return -1;
		}

	}

	public int stringsInName(String name, TypeOfCases typeOfCase) {

		return brokeWordToPieces(name, typeOfCase).length;

	}

	public void initializeWordnet() {

		try {
			if (database != null) {
				return;
			}
			database = WordNetDatabase.getFileInstance();
			System.setProperty("wordnet.database.dir", wordnetPath);
		} catch (Exception e) {

			Logger.Log(e);

		}

	}

	public boolean containsPlural(String name, TypeOfCases typeOfCase) {

		try {

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
		} catch (Exception e) {

			Logger.Log(e);
			return false;

		}

	}

	boolean areSynsetsOk(Synset[] syn) {

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

	public String[] brokeWordToPieces(String word, TypeOfCases type) {

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
