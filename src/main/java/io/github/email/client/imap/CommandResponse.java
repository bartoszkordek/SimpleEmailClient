package io.github.email.client.imap;

import java.util.List;

public class CommandResponse {
    private final List<String> lines;
    private final String confirmation;

    public CommandResponse(List<String> lines, String confirmation) {
        this.lines = lines;
        this.confirmation = confirmation;
    }

    public List<String> getLines() {
        return lines;
    }

    public String getConfirmation() {
        return confirmation;
    }
}