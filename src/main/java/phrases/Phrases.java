package phrases;

public enum Phrases {
    ENTER_NAME("Введите имя: "),
    WELCOME("К нам подключился "),
    EXIT("-exit"),
    USER_DISCONNECT(" вышел из чата.");


    private String phrase;

    private Phrases(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }
}
