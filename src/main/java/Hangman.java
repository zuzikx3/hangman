import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class Hangman {
    final private String word;
    private Status status;
    final Set<Character> charsOfWord;
    final private Set<Character> usedCharacters;

    public Hangman(String word) {
        this.word = word;
        status = Status.ZERO_MISTAKE;
        charsOfWord = word.chars()
                .mapToObj(e -> (char) e)
                .collect(Collectors.toSet());
        usedCharacters = new HashSet<>();
    }

    public String getWord() {
        return word;
    }

    public Status getStatus() {
        return status;
    }

    public void addMistake() throws ToManyMistakesException {
        if (status.equals(Status.SIXTH_MISTAKE)) {
            throw new ToManyMistakesException();
        }
        status = Status.values()[status.ordinal() + 1];
    }

    public int checkLetter(char c) throws ToManyMistakesException {
        if (usedCharacters.contains(c)) {
            return 0;
        }

        usedCharacters.add(c);

        if (!charsOfWord.contains(c)) {
            addMistake();
            return -1;
        }

        if(isWon()){
            status = Status.GUESSED;
        }
        return 1;
    }

    public String getGuessedLetters() {
        StringBuilder stringBuilder = new StringBuilder();

        for (char c : word.toCharArray()) {
            stringBuilder.append(usedCharacters.contains(c) ? c : "_");
        }
        return stringBuilder.toString();
    }

    private boolean isWon(){
        return usedCharacters.containsAll(charsOfWord);
    }

    public boolean isEnd(){
        return status.equals(Status.GUESSED) || status.equals(Status.SIXTH_MISTAKE);
    }

    public String buildHangman() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(" ------\n");
        stringBuilder.append(" | /   |\n");
        stringBuilder.append(" |/");

        stringBuilder.append(status.ordinal() > 0 ? "    o\n" : "\n");

        stringBuilder.append(" |");

        stringBuilder.append(status.ordinal() > 1 ? "     |\n" : "\n");

        stringBuilder.append(" |");

        if (status.ordinal() > 2) {
            stringBuilder.append("    /|");
        }

        stringBuilder.append(status.ordinal() > 3 ? "\\\n" : "\n");

        stringBuilder.append(" |");

        if (status.ordinal() > 4) {
            stringBuilder.append("    /");
        }

        stringBuilder.append(status.ordinal() > 5 ? " \\\n" : "\n");

        stringBuilder.append("---");

        return stringBuilder.toString();
    }

}
