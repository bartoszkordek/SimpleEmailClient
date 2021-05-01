package io.github.email.client.imap;

public class Attachment {
    private final byte[] content;
    private final String type;
    private final String fileName;

    public Attachment(byte[] content, String type, String fileName) {
        this.content = content;
        this.type = type;
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public String getType() {
        return type;
    }

    public String getFileName() {
        return fileName;
    }
}
