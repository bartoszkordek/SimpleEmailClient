package io.github.email.client.imap;

public class ContentToken {
    private final int depth;
    private final String text;

    public ContentToken(int depth, String text) {
        this.depth = depth;
        this.text = text;
    }

    public int getDepth() {
        return depth;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "DepthText{" +
                "depth=" + depth +
                ", text='" + text + '\'' +
                '}';
    }
}