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
    private CheckBox includeUppercase = new CheckBox("Include Uppercase");
    private CheckBox includeNumbers = new CheckBox("Include Numbers");
    private CheckBox includeSymbols = new CheckBox("Include Symbols");
    private int passwordLength = 8;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Password Generator v1.0.0");

        VBox root = new VBox(10);
        root.setPadding(new Insets(10));
        root.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Password Generator");
        titleLabel.setStyle("-fx-font-size:24; -fx-font-weight:bold;");

        Label lengthLabel = new Label("Password Length:");

        Spinner<Integer> lengthSpinner = new Spinner<>(8, 32, 16);
        lengthSpinner.setEditable(true);

        CheckBox includeLowercase = new CheckBox("Include Lowercase");
        includeLowercase.setSelected(true);

        CheckBox includeUppercase = new CheckBox("Include Uppercase");
        includeUppercase.setSelected(true);

        CheckBox includeDigits = new CheckBox("Include Numbers");
        includeDigits.setSelected(true);

        CheckBox includeSpecial = new CheckBox("Include Special Characters");
        includeSpecial.setSelected(true);

        passwordField.setPromptText("Generate a Password");
        passwordField.setEditable(false);

        Button generateButton = new Button("Generate");
        generateButton.setOnAction(event -> {
            int length = lengthSpinner.getValue();
            String password = generatePassword(length, includeLowercase.isSelected(), includeUppercase.isSelected(), includeDigits.isSelected(), includeSpecial.isSelected());
            passwordField.setText(password);
        });

        Button copyButton = new Button("Copy");
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

        Random rnd = new Random();

        while (password.length() < length) {
            int index = (int) (rnd.nextFloat() * validChars.length());
            password.append(validChars.charAt(index));
        }

        return password.toString();
    }

}