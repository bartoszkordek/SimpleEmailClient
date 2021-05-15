package io.github.email.client.imap;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BodyStructureParser {
    private static final Set<String> SUBTYPES = Stream
            .of("MIXED", "MESSAGE", "DIGEST", "ALTERNATIVE", "RELATED", "REPORT", "SIGNED", "ENCRYPTED", "FORM DATA")
            .collect(Collectors.toCollection(HashSet<String>::new));
    private static final Pattern BODY_PATTERN = Pattern.compile(".*BODY\\w{0,9} (.*)\\)");
    private static final Pattern CONTENT_TYPE_PATTERN = Pattern.compile(
            "^\\s*\"(TEXT|APPLICATION|IMAGE|VIDEO|AUDIO)\"", Pattern.CASE_INSENSITIVE);
    private static final Pattern MULTIPART_SUBTYPE_PATTERN = Pattern.compile(
            String.format("^\\s*\"(%s)\"", String.join("|", SUBTYPES)), Pattern.CASE_INSENSITIVE);

    public List<MailContentPart> parseMailContent(String bodyStructure) {
        Matcher matcher = BODY_PATTERN.matcher(bodyStructure);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("BODYSTRUCTURE does not match expected pattern");
        }
        List<ContentToken> parts = new ArrayList<>();
        String body = matcher.group();
        for (ParsedPart part : parseParts(body)) {
            if (part.multipartSubtype.length() > 0) {
                parts.add(0, new ContentToken(part.depth - 1, part.multipartSubtype));
            }
            if (CONTENT_TYPE_PATTERN.matcher(part.text).find()) {
                parts.add(new ContentToken(part.depth, part.text));
            }
        }
        return parseMailContent(parts);
    }

    private List<ParsedPart> parseParts(String parts) {
        Deque<Integer> openParenPositions = new ArrayDeque<>();
        List<ParsedPart> parsedParts = new ArrayList<>();
        for (int i = 0; i < parts.length(); i++) {
            char c = parts.charAt(i);
            if (c == '(') {
                openParenPositions.push(i);
            } else if (c == ')') {
                int pos = openParenPositions.pop();
                // take chars between two parens
                String text = parts.substring(pos + 1, i);
                int depth = openParenPositions.size();
                Matcher matcher = MULTIPART_SUBTYPE_PATTERN.matcher(parts.substring(i + 1));
                String multipartSubtype;
                if (matcher.find()) {
                    multipartSubtype = matcher.group();
                } else {
                    multipartSubtype = "";
                }
                parsedParts.add(new ParsedPart(multipartSubtype, depth, text));
            }
        }
        return parsedParts;
    }

    private List<MailContentPart> parseMailContent(List<ContentToken> contentTokens) {
        Deque<String> counters = new ArrayDeque<>();
        Deque<Integer> depths = new ArrayDeque<>();
        List<MailContentPart> mailContentParts = new ArrayList<>();
        ContentToken firstToken = contentTokens.get(0);
        int prevDepth = firstToken.depth;
        for (int i = 1; i < contentTokens.size(); i++) {
            ContentToken currToken = contentTokens.get(i);
            int currDepth = currToken.depth;
            if (currDepth > prevDepth) {
                prevDepth = currDepth;
                if (counters.isEmpty()) {
                    counters.push("1");
                } else {
                    counters.push(counters.peek() + ".1");
                }
            } else if (currDepth == prevDepth) {
                counters.push(increment(counters.pop()));
                depths.pop();
            } else {
                while (!depths.isEmpty() && depths.peek() > currDepth) {
                    counters.pop();
                    depths.pop();
                }
                if (counters.isEmpty()) {
                    counters.push("1");
                } else {
                    counters.push(increment(counters.peek()));
                }
            }
            depths.push(currDepth);
            String[] parts = currToken.text.split(" ");
            assert parts.length >= 2;
            assert parts[1].length() >= 2;
            String type = parts[1].substring(1, parts[1].length()-1);
            mailContentParts.add(new MailContentPart(counters.peek(), type, currToken.text));
        }
        mailContentParts.forEach(System.out::println);
        return mailContentParts;
    }

    // eg. 2.3.1 -> 2.3.2
    private String increment(String counter) {
        String[] parts = counter.split("\\.");
        parts[parts.length-1] = String.valueOf(Integer.parseInt(parts[parts.length-1]) + 1);
        return String.join(".", parts);
    }

    private static class ParsedPart {
        private final String multipartSubtype;
        private final int depth;
        private final String text;

        private ParsedPart(String multipartSubtype, int depth, String text) {
            this.multipartSubtype = multipartSubtype;
            this.depth = depth;
            this.text = text;
        }
    }

    private static class ContentToken {
        private final int depth;
        private final String text;

        private ContentToken(int depth, String text) {
            this.depth = depth;
            this.text = text;
        }
    }
}
