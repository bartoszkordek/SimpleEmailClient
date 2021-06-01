package io.github.email.client.util;

import io.github.email.client.imap.ImapClient;
import io.github.email.client.imap.MailMetadata;
import io.github.email.client.service.ConfigService;
import io.github.email.client.service.ReceiveApi;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TableView;

import java.util.List;
import java.util.Properties;

public class DownloadEmails extends Thread {
    private final ConfigService configUtil;
    private final ProgressBar progressBar;
    private final TableView tableView;
    private final ReceiveApi receiveApi;
    private List<MailMetadata> metadataList;

    public DownloadEmails(ProgressBar progressBar, TableView tableView) {
        this.configUtil = new ConfigService();
        this.progressBar = progressBar;
        this.tableView = tableView;
        this.receiveApi = new ImapClient();
    }

    @Override
    public void run() {
        Properties configProperties = configUtil.getProperties();
        metadataList = receiveApi.downloadEmails(configProperties, 10, progressBar);

        for (int i = 0; i < metadataList.size(); i++) {
            MailMetadata metadata = metadataList.get(i);
            Email email = new Email(
                    metadata.getFrom(),
                    metadata.getTo(),
                    metadata.getCc(),
                    metadata.getBcc(),
                    metadata.getSubject(),
                    metadata.getDate()
            );
            tableView.getItems().add(email);
        }
    }
}
