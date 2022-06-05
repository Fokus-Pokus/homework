import java.io.*;
import java.nio.charset.StandardCharsets;

public class Task1 {
    public static void main(String[] args) throws Exception {

        long startTime = System.currentTimeMillis();
        //String path1 = "D:\\testri\\homework_java_2\\homework\\12345.txt";
        //String path2 = "D:\\testri\\homework_java_2\\homework\\file";
        String path1 = args[0];
        String path2 = args[1];
        FileInputStream fin = new FileInputStream(path1);
        BufferedReader reader = new BufferedReader(new InputStreamReader(fin, "windows-1251"));
        int buffer = fin.available();
        int srez = buffer / 5;
        for (int k = 1; k < 6; k++) {
            FileOutputStream fos = new FileOutputStream(path2 + String.valueOf(k) + ".txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
            int amount = 0;
            do {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                byte [] bytes = line.getBytes();
                fos.write(bytes);
                fos.write(10);
                amount = amount + line.length();
            } while ( amount < srez);
        }
        long finishTime = System.currentTimeMillis();
        System.out.println("\nвремя работы=" + (finishTime - startTime) + "ms.");
    }
}