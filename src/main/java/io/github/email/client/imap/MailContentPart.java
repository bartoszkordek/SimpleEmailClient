package io.github.email.client.imap;

public class MailContentPart {
    private final String partNum;
    private final String type;
    private final String tokenText;

    public MailContentPart(String partNum, String type, String tokenText) {
        this.partNum = partNum;
        this.type = type;
        this.tokenText = tokenText;
    }

    public String getPartNum() {
        return partNum;
    }

    public String getType() {
        return type;
    }

    public String getTokenText() {
        return tokenText;
    }

    @Override
    public String toString() {
        return "MailContent{" +
                "partNum='" + partNum + '\'' +
                ", type='" + type + '\'' +
                ", tokenText='" + tokenText + '\'' +
                '}';
    }
}