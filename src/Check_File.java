import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Check_File {

    public ArrayList chek_file(String path) throws Exception{
        Date currentDate = new Date();
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        File file;
        Path filepath;
        BasicFileAttributes attr;
        ArrayList<String> arrayList = new ArrayList<>();
        String patternName = new String("((?<=)(\\w{0,99}\\d{1}.txt))");
        Pattern pattern = Pattern.compile(patternName);
        Matcher matcher;

        //String path = args[1]; //это для джарника
        //String path = "D:\\testri\\homework_java_2";
        File dir = new File(path);
        if (dir.isDirectory()) {
            // получаем все вложенные объекты в каталоге
            for (File item : dir.listFiles()) {
                //System.out.println(path + "\\" + item.getName());
                file = new File(path + "\\" + item.getName());
                String pathFile = (path + "\\" + item.getName());
                filepath = file.toPath();
                //получаем аттрибуты созданных файлов, чтоб потом сравнить даты создания
                attr = Files.readAttributes(filepath, BasicFileAttributes.class);
                String dateFile = String.valueOf(attr.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault())).substring(0, 10);
                Date date = formater.parse(dateFile);
                //System.out.println(dateFile);
                if (formater.format(currentDate).equals(formater.format(date))) {
                    matcher = pattern.matcher(pathFile);
                    if (matcher.find()) {
                        arrayList.add(matcher.group());
                        //System.out.println(arrayList);
                    }
                }
            }
        } else {
            System.out.println("вы подали не дирректорию");
        }
        //System.out.println(arrayList);
        if (arrayList.isEmpty()) {
            System.out.println("файлы пулов для поиска не нашлись");
            System.exit(1);
        }
        return arrayList;
    }
}
