package io.github.email.client.imap;

public class MailContentPart {
    private final String partNum;
    private final String type;
    private final String text;

    public MailContentPart(String partNum, String type, String text) {
        this.partNum = partNum;
        this.type = type;
        this.text = text;
    }

    public String getPartNum() {
        return partNum;
    }

    public String getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "MailContent{" +
                "access='" + partNum + '\'' +
                ", type='" + type + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}