import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task3 {
    public static void main(String[] args) throws Exception {

        long startTime = System.currentTimeMillis();
        String patternWord = "((?<=\\w\\s:)\\s)"; //для отделения строк с "INFO : "
        Pattern regexWord = Pattern.compile(patternWord);
        String patternTab = "(\\t)";
        Pattern regexTab = Pattern.compile(patternTab);
        String patternS = "(\\s)";
        Pattern regexS = Pattern.compile(patternS);
        Matcher matcherWord, matcherTab, matcherS, matcher4;
        String line, newLine, endLine, razd;
        int numTab, numWord;
        boolean boolWord, boolTab;
        razd = args[0];

        Check_File check_file = new Check_File();
        ArrayList arrayList = check_file.chek_file(args[1]);

        //создание новой папки
        String path2 = args[1] + "\\" + "Task3\\";
        File D = new File(path2);
        boolean D1 = D.mkdir();
        if(!D1){
            System.out.println("Такая дирректория уже существует");
        }
        //System.out.println(arrayList);
        //String path2 = "D:\\testri\\homework_java_2\\homework\\search1.txt";

        //путь для создания файла в новой папке, куда будет записан результат
        path2 = path2 + args[2] + ".csv";
        FileWriter writer = new FileWriter(path2);
        BufferedWriter bufferedWriter = new BufferedWriter(writer);

        //проходим по массиву подходящих файлов
        for (Object file : arrayList) {
            String filesName = String.valueOf(file);
            String path1 = args[1] + "\\" + filesName;
            FileInputStream fin = new FileInputStream(path1);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fin, "utf-8"));

            do {
                line = bufferedReader.readLine();
                if (line == null) {
                    break;
                }
                matcherWord = regexWord.matcher(line);
                matcherTab = regexTab.matcher(line);
                boolWord = matcherWord.find();
                boolTab = matcherTab.find();

                //если нет строчки с "INFO : " и есть \\t
                if (!boolWord && boolTab) {
                    numTab = matcherTab.start();
                    matcherS = regexS.matcher(line.substring(0, numTab));
                    newLine = matcherTab.replaceAll(razd);
                    endLine = matcherS.replaceAll(razd) + newLine.substring(numTab, newLine.length());
                    bufferedWriter.write(endLine + "\n");

                    //если нет \\t и есть "INFO : "
                } else if (!boolTab && boolWord) {
                    numWord = matcherWord.start();
                    matcherS = regexS.matcher(line.substring(0, numWord - 2));
                    newLine = matcherWord.replaceAll(razd);
                    endLine = matcherS.replaceAll(razd) + newLine.substring(numWord - 2, newLine.length());
                    bufferedWriter.write(endLine + "\n");

                    //если есть строчка строчки с "INFO : " и есть \\t
                } else if (boolWord && boolTab) {
                    numWord = matcherWord.start();
                    newLine = matcherWord.replaceAll(razd);
                    matcherS = regexS.matcher(line.substring(0, numWord - 2));
                    endLine = matcherS.replaceAll(razd) + newLine.substring(numWord - 2, newLine.length());
                    matcherTab = regexTab.matcher(endLine);
                    endLine = matcherTab.replaceAll(razd);
                    bufferedWriter.write(endLine + "\n");
                }
            } while (true);
        }
        bufferedWriter.close();
        long finishTime = System.currentTimeMillis();
        System.out.println("\nвремя работы=" + (finishTime - startTime) + "ms.");
    }
}
