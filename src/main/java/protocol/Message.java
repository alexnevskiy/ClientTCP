package protocol;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

public class Message implements Serializable {
    private String time;
    private String name;
    private String text;
    private String file;
    private Integer fileSize;

    public Message(String time, String name, String text, String file, Integer fileSize) {
        this.time = time;
        this.name = name;
        this.text = text;
        this.file = file;
        this.fileSize = fileSize;
    }

    public Message() {
        super();
    }

    public String getTime() {
        return time;
    }

    @JsonSetter("time")
    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    @JsonSetter("text")
    public void setText(String text) {
        this.text = text;
    }

    public String getFile() {
        return file;
    }

    @JsonSetter("file")
    public void setFile(String file) {
        this.file = file;
    }

    public Integer getFileSize() {
        return fileSize;
    }

    @JsonSetter("fileSize")
    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }

    @Override
    public String toString() {
        return "Message{" +
                "time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", text='" + text + '\'' +
                ", file='" + file + '\'' +
                ", fileSize=" + fileSize +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return fileSize.equals(message.fileSize) && Objects.equals(time, message.time) &&
                Objects.equals(name, message.name) && Objects.equals(text, message.text) &&
                Objects.equals(file, message.file);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, name, text, file, fileSize);
    }

    public byte[] toBytes() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsBytes(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Message toMessage(byte[] bytes) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(bytes, Message.class);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
