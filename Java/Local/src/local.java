import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class local {
    public static void main(String[] args) {
        /*Escolhe uma pasta*/
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setCurrentDirectory(new File(
                System.getProperty("user.home") +
                        System.getProperty("file.separator") +
                        "Desktop"
        ));
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    chooser.getSelectedFile().getName());
        }
        /*Pega todos os xml na pasta e subpastas*/
        try {
            Files.walk(Path.of(chooser.getSelectedFile().getAbsolutePath()))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().endsWith("xml"))
                    .forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
