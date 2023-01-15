package fr.antyss77.passwordgenerator;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Random;

public class PasswordGenerator extends Application {

    private TextField passwordField = new TextField();
    private CheckBox includeUppercase = new CheckBox("Inclure des majuscules");
    private CheckBox includeNumbers = new CheckBox("Inclure des chiffres");
    private CheckBox includeSymbols = new CheckBox("Inclure des symboles");
    private int passwordLength = 8;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Générateur de mot de passe");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Générateur de mot de passe");
        titleLabel.setStyle("-fx-font-size:24; -fx-font-weight:bold;");

        Label lengthLabel = new Label("Longueur du mot de passe :");

        Spinner<Integer> lengthSpinner = new Spinner<>(8, 32, 16);
        lengthSpinner.setEditable(true);

        CheckBox includeLowercase = new CheckBox("Inclure des minuscules");
        includeLowercase.setSelected(true);

        CheckBox includeUppercase = new CheckBox("Inclure des majuscules");
        includeUppercase.setSelected(true);

        CheckBox includeDigits = new CheckBox("Inclure des chiffres");
        includeDigits.setSelected(true);

        CheckBox includeSpecial = new CheckBox("Inclure des caractères spéciaux");
        includeSpecial.setSelected(true);

        passwordField.setPromptText("Générer un mot de passe");
        passwordField.setEditable(false);

        Button generateButton = new Button("Générer");
        generateButton.setOnAction(event -> {
            int length = lengthSpinner.getValue();
            String password = generatePassword(length, includeLowercase.isSelected(), includeUppercase.isSelected(), includeDigits.isSelected(), includeSpecial.isSelected());
            passwordField.setText(password);
        });

        Button copyButton = new Button("Copier");
        copyButton.setOnAction(event -> {
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(passwordField.getText());
            clipboard.setContent(content);
        });

        root.getChildren().addAll(titleLabel, lengthLabel, lengthSpinner, includeLowercase, includeUppercase, includeDigits, includeSpecial, generateButton, passwordField, copyButton);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    private String generatePassword(int length, boolean includeLowercase, boolean includeUppercase, boolean includeDigits, boolean includeSpecial) {
        StringBuilder password = new StringBuilder();
        String validChars = "";

        if (includeLowercase) {
            validChars += "abcdefghijklmnopqrstuvwxyz";
        }
        if (includeUppercase) {
            validChars += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        if (includeDigits) {
            validChars += "0123456789";
        }
        if (includeSpecial) {
            validChars += "!@#$%^&*_=+-/";
        }

        Random random = new Random();
        for (int i = 0; i < length; i++) {
            password.append(validChars.charAt(random.nextInt(validChars.length())));
        }
        return password.toString();
    }

}

