import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) throws IOException {
        final JFileChooser fc = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Pasta contendo XML", "xml");
        fc.setFileFilter(filter);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            System.out.println("You chose to open this file: " +
                    fc.getSelectedFile().getName());
        }

        try (Stream<Path> paths = Files.walk(Paths.get(fc.getSelectedFile().getAbsolutePath()))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(System.out::println);
        }
    }
}
