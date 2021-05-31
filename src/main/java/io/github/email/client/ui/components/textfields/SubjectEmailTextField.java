package io.github.email.client.ui.components.textfields;

public class SubjectEmailTextField extends CustomTextField {

    public SubjectEmailTextField() {
        super();
        this.setMessage("Email subject is too long.");
        this.setStyle("-fx-label-float:true;-fx-pref-width: 1000");
        this.setOnKeyReleased(event -> validateSubjectLength());
    }

    private void validateSubjectLength() {
        String subjectText = this.getText();
        int subjectCharLength = subjectText.length();
        if (subjectCharLength > 78) {
            this.setValid(false);
            this.setStyle("-fx-background-color: rgb(250, 160, 160)");
        } else {
            this.setValid(true);
            this.setStyle("-fx-background-color: white");
        }
        this.handleLabelChange();
    }
}
