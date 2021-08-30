import javax.swing.*;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
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
        /*Manda arquivos para um endereco web*/
        String url = "http://localhost:8000";
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-type", "text/xml");
            connection.setDoOutput(true);

            DataOutputStream request = new DataOutputStream(connection.getOutputStream());
            request.writeBytes("test\r\n");
            request.flush();
            request.close();

            int responseCode = connection.getResponseCode();
            System.out.println(responseCode); // Should be 200
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
