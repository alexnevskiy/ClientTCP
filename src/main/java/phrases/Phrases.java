package phrases;

public enum Phrases {
    ENTER_NAME("Введите имя: "),
    WELCOME("К нам подключился "),
    SERVER_CLOSED("Сервер прекратил работу."),
    FILE_NOT_EXIST("Такого файла не существует, пожалуйста, введите корректный путь до файла."),
    SENT_FILE("Отправлен файл: "),
    CREATE_DIRECTORY("Директория для файлов успешно создана: "),
    FILE_RECEIVED("Файл успешно получен: "),
    FILE_OVERWRITTEN("Файл успешно получен и перезаписан: "),

    EXIT("-exit"),
    FILE("-file "),

    DESKTOP("Desktop");

    private String phrase;

    private Phrases(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }
}
