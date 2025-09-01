# Simple Bug Report App

## What’s This All About?
Hey there! The **Simple Bug Report App** is a handy little Java tool designed to make logging software bugs a breeze. If you’re a tester, developer, or just someone who spots glitches in software, this app helps you jot down all the details in a neat, organized way. Built with Java’s Swing framework, it’s got a clean, user-friendly interface that lets you document bugs and save them as text files for easy sharing.

## Why Use It?
This app is all about saving you time and keeping your bug reports clear and consistent. Here’s what it does:
- Lets you describe the bug, steps to recreate it, what you expected, and what actually happened.
- Allows you to set a priority and severity to help teams know what’s urgent.
- Supports attaching files like screenshots or logs to give extra context.
- Saves everything in a tidy text file you can share with your team.

## Cool Features
- **Easy-to-Use Form**: A scrollable form with clear labels for all your bug details.
- **Must-Have Fields**: Makes sure you fill out the key stuff (like title, description, and steps) before saving.
- **Priority & Severity**: Pick from options like Low to Critical for priority and Minor to Blocker for severity.
- **File Attachments**: Add a screenshot, log, or other file (supports `.png`, `.jpg`, `.txt`, `.log`, `.pdf`, and more).
- **Save with Ease**: Saves your report as a text file with a timestamp, and you get to choose where it goes.
- **Clear It Out**: Hit "Clear All" to start fresh, with a quick confirmation to avoid accidents.
- **Looks Good**: Uses your system’s native style (Windows, macOS, or Linux) and includes tooltips to guide you.
- **Error Alerts**: Friendly pop-ups let you know if you missed something or if there’s a problem saving.

## Tech Stuff
- **Built With**: Java (works with JDK 8 or newer).
- **UI Framework**: Java Swing for a smooth, graphical experience.
- **Dependencies**: Just standard Java libraries—no extra downloads needed.
- **Output**: Saves reports as plain text files with a clear, structured format.
- **Styling**: Matches your system’s look and feel for a seamless experience.

## How to Use It
1. **Get It Running**:
   - Grab the code and make sure you have a Java Development Kit (JDK 8 or later) installed.
   - Compile and run the `SimpleBugReportApp.java` file.

2. **Fill in the Details**:
   - **Bug Title**: Give your bug a short, clear name.
   - **Description**: Explain what’s going wrong in detail.
   - **Steps to Reproduce**: List the exact steps to make the bug happen.
   - **Expected Result**: What should’ve happened?
   - **Actual Result**: What actually happened?
   - **Priority & Severity**: Choose how urgent or serious the bug is (e.g., High priority, Major severity).
   - **Version**: Note the software version (defaults to 1.0.0).
   - **Attachment**: Click "Choose File" to add a screenshot or log if needed.

3. **Save Your Report**:
   - Click "Save Report" and pick a spot to save your file (it’ll suggest a name like `bug_report_20250901_182400.txt`).
   - The report gets saved as a text file with all your details neatly formatted.

4. **Start Over**:
   - Hit "Clear All" to wipe the form clean and start a new report. You’ll get a quick “Are you sure?” prompt.
   - After saving, it’ll ask if you want to create another report.

5. **Check Your Report**:
   - Open the saved text file to see your bug report, complete with all the details you entered.

## Getting Started
1. Download or clone the source code.
2. Make sure you’ve got JDK 8 or later installed.
3. Compile the code:
   ```bash
   javac com/bugreportai/SimpleBugReportApp.java
   ```
4. Run the app:
   ```bash
   java com.bugreportai.SimpleBugReportApp
   ```

## Good to Know
- **Permissions**: Make sure you can write files to the folder you choose for saving reports.
- **File Types**: You can attach images (`.png`, `.jpg`, `.jpeg`, `.gif`), text files (`.txt`, `.log`), or PDFs.
- **Cross-Platform**: Works smoothly on Windows, macOS, or Linux, matching your system’s style.
- **Limitations**: Right now, it saves the file path for attachments but doesn’t include the actual file. Future updates might bundle them together.
