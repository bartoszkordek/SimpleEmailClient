package io.github.email.client.service;

import io.github.email.client.imap.MailMetadata;

import java.util.List;
import java.util.Properties;

public interface ReceiveApi {
    List<MailMetadata> downloadEmails(Properties properties, int limit);
}
