import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * @author Vincent Lee and Shamen Kumar
 */
public class App {
    static boolean readCheck = false;
    static boolean tenseCheck;
    static boolean verbCheck;
    static boolean pronounCheck;
    static MaoriSentence sentence = new MaoriSentence("", "", "");
    private static String verbsPast[] = { "went", "made", "saw", "wanted", "called", "asked", "read", "learned" };
    private static String verbs[] = { "go", "make", "see", "want", "call", "ask", "read",
            "learn" };
    private static String verbsPresent[] = { "going", "making", "seeing", "wanting", "calling",
            "asking", "reading", "learning" };

    /**
     * The main method handles the input of sentences in the form of strings. The
     * sentences are then split
     * into their components, which are analysed and translated to form a sentence
     * in Te Reo Maori. The method
     * calls the sentenceSplit() and sentencePutterTogetherer() methods to perform
     * this task.
     * 
     * @param args an array of strings that contains command-line arguments
     * @throws Exception if there is an error reading input from the Scanner
     */
    public static void main(String[] args) throws Exception {
        // Scanner sc = new Scanner(System.in);
        // while (sc.hasNextLine()) {
        // String stdin = sc.nextLine();
        // sentenceSplit(stdin);
        // sentencePutterTogetherer();
        // }
        // sc.close();

        try {
            Scanner sc = new Scanner(new File("test.txt"));

            while (sc.hasNextLine()) {
                String stdin = sc.nextLine();
                System.out.println(stdin);
                sentenceSplit(stdin);
                sentencePutterTogetherer();
            }
            sc.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * Checks if all required sentence
     * components have been set and
     * prints the translated sentence. If not all components are set, it prints
     * "INVALID".
     */
    public static void sentencePutterTogetherer() {
        // System.out.println("tense: " + tenseCheck + " verb: " + verbCheck + "
        // pronoun: " + pronounCheck);
        if (tenseCheck == true && verbCheck == true && pronounCheck == true) {
            System.out.println(sentence.tense + " " + sentence.verb + " " +
                    sentence.pronoun);
        } else {
            System.out.println(fail());
        }
    }

    /**
     * The fail method is used to print "INVALID" when the sentence is not properly
     * formatted.
     * 
     * @return a String containing "INVALID"
     */
    public static String fail() {

        return "INVALID";
    }

    /**
     * The sentenceSplit method takes a sentence as input, splits it into its
     * components and translates each component.
     * 
     * @param input a String containing the sentence to be translated
     */

    public static void sentenceSplit(String input) {
        tenseCheck = false;
        verbCheck = false;
        pronounCheck = false;
        readCheck = false;
        input = input.toLowerCase();
        String pronoun = "";
        String amount = "";
        String inclExcl = "";
        int amountInt = 0;
        String verb = "";

        if (input.contains("excl") || input.contains("incl")) {
            input = input.replaceAll("[\\(\\)]", "");
            String inputSplit[] = input.split("\\s");
            if (input.length() > 3) {
                pronoun = inputSplit[0];
                amount = inputSplit[1];
                inclExcl = inputSplit[2];
                verb = inputSplit[inputSplit.length - 1];

                if (verb.equals("read") && !inputSplit[inputSplit.length - 2].equals("will")) {
                    readCheck = true;
                }

                if (pronoun.equals("they") && inclExcl.equals("incl")) {
                    fail();
                } else {
                    verbTranslate(verb);

                    if (isNumeric(amount)) {
                        amountInt = Integer.parseInt(amount);
                    }
                    if (amountInt >= 3) {
                        threeOrMore(pronoun, inclExcl);

                    }
                    if (amountInt == 2) {
                        two(pronoun, inclExcl);

                    }
                    if (amountInt == 1) {
                        one(pronoun, inclExcl);

                    }
                }
            }
        } else {
            String inputSplitUp[] = input.split("\\s");
            one(inputSplitUp[0], inclExcl);
            if (inputSplitUp.length == 2 && inputSplitUp[inputSplitUp.length -
                    1].equals("read")) {
                readCheck = true;
            }
            verbTranslate(inputSplitUp[inputSplitUp.length - 1]);

        }
    }

    /**
     * 
     * This method checks if the input string matches any of the pronouns in the
     * given pronoun map and sets the appropriate value for the "pronoun" field of
     * the sentence for 3 or more people.
     * 
     * @param input    The input string to check for a pronoun match.
     * @param inclExcl Determines whether to include or exclude the listener in the
     *                 pronoun.
     */
    public static void threeOrMore(String input, String inclExcl) {
        HashMap<String, String[]> pronounsMap = new HashMap<>();
        if (inclExcl.equals("excl")) {
            pronounsMap.put("excludesListener", new String[] { "we", "us" });
        }
        if (inclExcl.equals("incl")) {
            pronounsMap.put("includesBoth", new String[] { "we", "us" });
        }

        pronounsMap.put("excludesSpeaker", new String[] { "you" });
        pronounsMap.put("neither", new String[] { "they", "them" });

        boolean foundMatch = false;
        for (Map.Entry<String, String[]> entry : pronounsMap.entrySet()) {
            String[] pronounArray = entry.getValue();
            for (String pronoun : pronounArray) {
                if (input.equals(pronoun)) {
                    foundMatch = true;
                    pronounCheck = true;
                    String key = entry.getKey();
                    if (key.equals("includesBoth")) {

                        sentence.pronoun = "t\u0101tou";

                    } else if (key.equals("excludesListener")) {

                        sentence.pronoun = "m\u0101tou";

                    } else if (key.equals("excludesSpeaker")) {

                        sentence.pronoun = "koutou";

                    } else if (key.equals("neither")) {

                        sentence.pronoun = "r\u0101tou";

                    }
                    break;
                }
            }
            if (foundMatch) {
                break;
            }
        }
    }

    /**
     * 
     * This method checks if the input string matches any of the pronouns in the
     * given pronoun map and sets the appropriate value for the "pronoun" field of
     * the sentence for 2 people.
     * 
     * @param input    The input string to check for a pronoun match.
     * @param inclExcl Determines whether to include or exclude the listener in the
     *                 pronoun.
     */

    public static void two(String input, String inclExcl) {
        HashMap<String, String[]> pronounsMap = new HashMap<>();
        if (inclExcl.equals("excl")) {
            pronounsMap.put("excludesListener", new String[] { "we", "us" });
        }
        if (inclExcl.equals("incl")) {
            pronounsMap.put("includesBoth", new String[] { "we", "us" });
        }
        pronounsMap.put("excludesSpeaker", new String[] { "you" });
        pronounsMap.put("neither", new String[] { "they", "them" });

        boolean foundMatch = false;
        for (Map.Entry<String, String[]> entry : pronounsMap.entrySet()) {
            String[] pronounArray = entry.getValue();
            for (String pronoun : pronounArray) {
                if (input.equals(pronoun)) {
                    foundMatch = true;
                    pronounCheck = true;
                    String key = entry.getKey();
                    if (key.equals("includesBoth")) {
                        sentence.pronoun = "t\u0101ua";
                    } else if (key.equals("excludesListener")) {
                        sentence.pronoun = "m\u0101ua";
                    } else if (key.equals("excludesSpeaker")) {
                        sentence.pronoun = "k\u014Drua";

                    } else if (key.equals("neither")) {
                        sentence.pronoun = "r\u0101ua";

                    }
                    break;
                }
            }
            if (foundMatch) {
                break;
            }
        }
    }

    /**
     * 
     * This method checks if the input string matches any of the pronouns in the
     * given pronoun map and sets the appropriate value for the "pronoun" field of
     * the sentence for one person.
     * 
     * @param input    The input string to check for a pronoun match.
     * @param inclExcl Determines whether to include or exclude the listener in the
     *                 pronoun.
     */
    public static void one(String input, String inclExcl) {
        HashMap<String, String[]> pronounsMap = new HashMap<>();
        if (inclExcl.equals("incl")) {
            pronounsMap.put("youInclude", new String[] { "you" });
        }
        pronounsMap.put("oneExL", new String[] { "i", "me" });
        pronounsMap.put("oneExBoth", new String[] { "he", "she", "him", "her" });

        boolean foundMatch = false;
        for (Map.Entry<String, String[]> entry : pronounsMap.entrySet()) {
            String[] pronounArray = entry.getValue();
            for (String pronoun : pronounArray) {
                if (input.equals(pronoun)) {
                    foundMatch = true;
                    pronounCheck = true;
                    String key = entry.getKey();
                    if (key.equals("oneExL")) {
                        sentence.pronoun = "au";
                    } else if (key.equals("youInclude")) {
                        readCheck = true;
                        sentence.pronoun = "koe";
                    } else if (key.equals("oneExBoth")) {
                        sentence.pronoun = "ia";
                    }

                    break;
                }
            }
            if (foundMatch) {
                break;
            }
        }
    }

    /**
     * This method checks if the input verb matches any of the verbs in the given
     * arrays and sets the verb and tense depending on which array it is found in.
     * 
     * @param input The input string to check for a verb match.
     */

    public static void verbTranslate(String input) {
        String[] maoriVerbs = { "haere", "hanga", "kite", "hiahia", "karanga", "p\u0101tai", "p\u0101nui", "ako" };
        for (int i = 0; i < verbs.length; i++) {
            if (verbs[i].equals(input)) {
                verbCheck = true;
                sentence.verb = maoriVerbs[i];
                tenseCheck = true;
                sentence.tense = "Ka";
            }
            if (verbsPresent[i].equals(input)) {
                verbCheck = true;
                sentence.verb = maoriVerbs[i];
                tenseCheck = true;
                sentence.tense = "Kei te";
            }
            if (verbsPast[i].equals(input)) {
                verbCheck = true;
                sentence.verb = maoriVerbs[i];
                tenseCheck = true;
                sentence.tense = "I";
            }
            if (readCheck == true) {
                sentence.tense = "I";
            }
        }

    }

    /**
     * Method to check whether a number is numeric or not.
     * Sources from:
     * 
     * @link https://www.baeldung.com/java-check-string-number#:~:text=Perhaps%20the%20easiest%20and%20the
     * @param strNum String to check if is a number or not.
     * @return boolean value indicating whether input number is numeric or not
     */
    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int m = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
