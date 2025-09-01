package com.bugreportai;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class SimpleBugReportApp extends JFrame {
    private JTextField titleField;
    private JTextArea descriptionArea;
    private JTextArea stepsArea;
    private JTextArea expectedArea;
    private JTextArea actualArea;
    private JLabel attachmentLabel;
    private JComboBox<String> priorityCombo;
    private JComboBox<String> severityCombo;
    private JTextField versionField;
    private File attachmentFile;
    private final JFileChooser fileChooser;

    public SimpleBugReportApp() {
        fileChooser = new JFileChooser();
        setupFileChooser();
        setupWindow();
        createComponents();
        layoutComponents();
    }

    private void setupFileChooser() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Images, Text files, Logs (*.png, *.jpg, *.txt, *.log, *.pdf)",
                "png", "jpg", "jpeg", "gif", "txt", "log", "pdf");
        fileChooser.setFileFilter(filter);
    }

    private void setupWindow() {
        setTitle("Bug Report Creator");
        setSize(700, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(true);
    }

    private void createComponents() {
        // Text fields
        titleField = new JTextField(30);
        titleField.setToolTipText("Enter the bug title");
        descriptionArea = new JTextArea(4, 30);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setToolTipText("Describe the bug in detail");

        stepsArea = new JTextArea(4, 30);
        stepsArea.setLineWrap(true);
        stepsArea.setWrapStyleWord(true);
        stepsArea.setToolTipText("List steps to reproduce the bug");

        expectedArea = new JTextArea(3, 30);
        expectedArea.setLineWrap(true);
        expectedArea.setWrapStyleWord(true);
        expectedArea.setToolTipText("Describe the expected behavior");

        actualArea = new JTextArea(3, 30);
        actualArea.setLineWrap(true);
        actualArea.setWrapStyleWord(true);
        actualArea.setToolTipText("Describe the actual behavior");

        versionField = new JTextField("1.0.0", 15);
        versionField.setToolTipText("Enter the software version");

        // Combo boxes
        priorityCombo = new JComboBox<>(new String[]{"Low", "Medium", "High", "Critical"});
        priorityCombo.setSelectedIndex(1); // Medium by default
        priorityCombo.setToolTipText("Select the priority level");

        severityCombo = new JComboBox<>(new String[]{"Minor", "Major", "Blocker"});
        severityCombo.setToolTipText("Select the severity level");

        // File attachment label
        attachmentLabel = new JLabel("No file selected");
        attachmentLabel.setForeground(UIManager.getColor("Label.disabledForeground"));

        // Set font for all components
        Font defaultFont = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        titleField.setFont(defaultFont);
        descriptionArea.setFont(defaultFont);
        stepsArea.setFont(defaultFont);
        expectedArea.setFont(defaultFont);
        actualArea.setFont(defaultFont);
        versionField.setFont(defaultFont);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());

        // Main panel with form
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Title
        JLabel titleLabel = new JLabel("Create Bug Report", JLabel.CENTER);
        titleLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        titleLabel.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        // Form fields
        addFormField(mainPanel, "Bug Title (Required):", titleField);
        addFormField(mainPanel, "Description (Required):", createScrollPane(descriptionArea, 80));

        // Classification panel
        JPanel classificationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        classificationPanel.add(new JLabel("Priority:"));
        classificationPanel.add(priorityCombo);
        classificationPanel.add(Box.createHorizontalStrut(20));
        classificationPanel.add(new JLabel("Severity:"));
        classificationPanel.add(severityCombo);
        addFormSection(mainPanel, "Classification:", classificationPanel);

        addFormField(mainPanel, "Version:", versionField);
        addFormField(mainPanel, "Steps to Reproduce (Required):", createScrollPane(stepsArea, 80));
        addFormField(mainPanel, "Expected Result (Required):", createScrollPane(expectedArea, 60));
        addFormField(mainPanel, "Actual Result (Required):", createScrollPane(actualArea, 60));

        // Attachment panel
        JPanel attachPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton attachButton = new JButton("Choose File");
        attachButton.addActionListener(this::chooseFile);
        attachPanel.add(attachButton);
        attachPanel.add(attachmentLabel);
        addFormSection(mainPanel, "Attachment:", attachPanel);

        // Scrollable panel
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton clearButton = new JButton("Clear All");
        JButton saveButton = new JButton("Save Report");

        clearButton.addActionListener(e -> clearForm());
        saveButton.addActionListener(this::saveReport);

        // Make Save button more prominent
        saveButton.setBackground(UIManager.getColor("Button.select"));
        saveButton.setForeground(UIManager.getColor("Button.foreground"));
        saveButton.setPreferredSize(new Dimension(120, 30));

        clearButton.setPreferredSize(new Dimension(100, 30));

        buttonPanel.add(clearButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JScrollPane createScrollPane(JTextArea textArea, int height) {
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setMinimumSize(new Dimension(500, height));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    private void addFormField(JPanel parent, String labelText, JComponent field) {
        parent.add(Box.createVerticalStrut(10));

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        if (labelText.contains("Required")) {
            label.setForeground(UIManager.getColor("Label.errorForeground"));
        }
        parent.add(label);

        parent.add(Box.createVerticalStrut(5));
        field.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        parent.add(field);
    }

    private void addFormSection(JPanel parent, String labelText, JPanel section) {
        parent.add(Box.createVerticalStrut(10));

        JLabel label = new JLabel(labelText);
        label.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        parent.add(label);

        parent.add(Box.createVerticalStrut(5));
        section.setAlignmentX(JComponent.LEFT_ALIGNMENT);
        parent.add(section);
    }

    private void chooseFile(ActionEvent e) {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            attachmentFile = fileChooser.getSelectedFile();
            attachmentLabel.setText(attachmentFile.getName());
            attachmentLabel.setForeground(UIManager.getColor("Label.foreground"));
        }
    }

    private void clearForm() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Clear all fields?",
                "Confirm",
                JOptionPane.YES_NO_OPTION
        );

        if (result == JOptionPane.YES_OPTION) {
            titleField.setText("");
            descriptionArea.setText("");
            stepsArea.setText("");
            expectedArea.setText("");
            actualArea.setText("");
            versionField.setText("1.0.0");
            priorityCombo.setSelectedIndex(1);
            severityCombo.setSelectedIndex(0);
            attachmentFile = null;
            attachmentLabel.setText("No file selected");
            attachmentLabel.setForeground(UIManager.getColor("Label.disabledForeground"));
        }
    }

    private boolean validateForm() {
        if (isEmpty(titleField.getText())) {
            showError("Title is required!");
            titleField.requestFocus();
            return false;
        }
        if (isEmpty(descriptionArea.getText())) {
            showError("Description is required!");
            descriptionArea.requestFocus();
            return false;
        }
        if (isEmpty(stepsArea.getText())) {
            showError("Steps to Reproduce is required!");
            stepsArea.requestFocus();
            return false;
        }
        if (isEmpty(expectedArea.getText())) {
            showError("Expected Result is required!");
            expectedArea.requestFocus();
            return false;
        }
        if (isEmpty(actualArea.getText())) {
            showError("Actual Result is required!");
            actualArea.requestFocus();
            return false;
        }
        return true;
    }

    private boolean isEmpty(String text) {
        return text.trim().isEmpty();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void saveReport(ActionEvent e) {
        if (!validateForm()) {
            return;
        }

        JFileChooser saveChooser = new JFileChooser();
        saveChooser.setSelectedFile(new File("bug_report_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt"));
        if (saveChooser.showSaveDialog(this) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File outputFile = saveChooser.getSelectedFile();
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write("===============================\n");
            writer.write("       BUG REPORT\n");
            writer.write("===============================\n");
            writer.write("Created: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + "\n\n");

            writer.write("BASIC INFO:\n");
            writer.write("Title: " + titleField.getText().trim() + "\n");
            writer.write("Priority: " + priorityCombo.getSelectedItem() + "\n");
            writer.write("Severity: " + severityCombo.getSelectedItem() + "\n");
            writer.write("Version: " + versionField.getText().trim() + "\n\n");

            writer.write("DESCRIPTION:\n");
            writer.write(descriptionArea.getText().trim() + "\n\n");

            writer.write("STEPS TO REPRODUCE:\n");
            writer.write(stepsArea.getText().trim() + "\\-系列");

                    writer.write("EXPECTED RESULT:\n");
            writer.write(expectedArea.getText().trim() + "\n\n");

            writer.write("ACTUAL RESULT:\n");
            writer.write(actualArea.getText().trim() + "\n\n");

            if (attachmentFile != null) {
                writer.write("ATTACHMENT:\n");
                writer.write("File: " + attachmentFile.getAbsolutePath() + "\n\n");
            }

            writer.write("===============================\n");
            writer.write("       END OF REPORT\n");
            writer.write("===============================\n");

            JOptionPane.showMessageDialog(
                    this,
                    "Bug report saved successfully!\n\nFile: " + outputFile.getName(),
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

            // Ask to clear form
            int result = JOptionPane.showConfirmDialog(
                    this,
                    "Would you like to create another bug report?",
                    "Continue?",
                    JOptionPane.YES_NO_OPTION
            );

            if (result == JOptionPane.YES_OPTION) {
                clearForm();
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error saving file: " + ex.getMessage(),
                    "Save Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void main(String[] args) {
        // Set Look and Feel before creating GUI components
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set Look and Feel: " + e.getMessage());
        }

        SwingUtilities.invokeLater(() -> {
            new SimpleBugReportApp().setVisible(true);
        });
    }
}