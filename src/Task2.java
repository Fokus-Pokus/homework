import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task2 {
    public static void main(String[] args) throws Exception {

        long startTime = System.currentTimeMillis();

        Check_File check_file = new Check_File();
        ArrayList arrayList = check_file.chek_file(args[1]);

        //создание новой папки
        String path2 = args[1] + "\\" + "Task2\\";
        File D = new File(path2);
        boolean D1 = D.mkdir();
        if(!D1){
            System.out.println("Такая дирректория уже существует");
        }

        //задаем выражение для поиска
        String search = args[0];
        Pattern regex = Pattern.compile(search);

        //путь для создания файла в новой папке, куда будет записан результат
        path2 = path2 + args[2] + ".txt";
        FileWriter writer = new FileWriter(path2);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        //проходим по массиву подходящих файлов
        for (Object file : arrayList) {
            String filesName = String.valueOf(file);
            String path1 = args[1] + "\\" + filesName;
            FileInputStream fin = new FileInputStream(path1);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fin, "utf-8"));
            String text;
            Matcher matcher;

            do {
               text = bufferedReader.readLine();
               if (text == null) {
                   break;
               }
               matcher = regex.matcher(text);
               while (matcher.find()){
                   bufferedWriter.write(matcher.group() + "\n");
               }
            } while (true);
        }
        bufferedWriter.close();
        long finishTime = System.currentTimeMillis();
        System.out.println("\nвремя работы=" + (finishTime - startTime) + "ms.");
    }
}
