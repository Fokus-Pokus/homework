
import org.omg.CORBA.portable.IndirectionException;

import java.io.*;
public class Task_1_v2 {

    public static void main(String[] args) throws Exception {

        String path = args[0];
        String out_path = args[1];
        //чтение в первый раз "E:\\тестирование\\homework_java\\main.log.2014-11-17"

        FileInputStream fin = new FileInputStream(path);
        byte [] buffer = new byte[fin.available()];//создаем баффер длинной количества доступных байтов
        fin.read(buffer, 0, buffer.length);//читает количество байтов переданных от 0 до ....в баффер
        fin.close();

        //чтение во второй раз для покусочного чтения

        FileInputStream fin2 = new FileInputStream(path);
        byte [] buffer2 = new byte[fin2.available()];
        int start_reed = 0;
        int lenght = 14665465;
        int last_byte;
        int page = 0;

        /*цикл ду вайл для покусочного чтения, и создания несокльких файлов для записи
        первый ду отвечает за создание файлов и до тех пор пока не дойдем до конца изначального файла
        второй ду ловит ошибку выхода за границы быффер2 и делить ленгхт на 2 до тех пора пока не
        войдет в длинну оставшихся строк
         */
        boolean done = false;
        do {
            do {
                try {
                    fin2.read(buffer2, start_reed, lenght);
                    done = false;
                } catch (IndexOutOfBoundsException e) {
                    lenght = buffer.length - start_reed-50;
                    done = true;
                    System.out.println(lenght);
                }
            } while (done);

            //цикл для поиска \n

            boolean flag = true;
            int r=0;
            while (flag) {
                r++;
                int next_byte = fin2.read();
                String abc = new String(String.valueOf((char) next_byte));
                if (next_byte == 10) {
                    System.out.println("Мы дошли до \\n");
                    flag = false;

                    // создаем файлы для записи
                    //FileOutputStream fos = new FileOutputStream("E:\\тестирование\\homework_java\\part_" + String.valueOf(page) + ".txt");
                    FileOutputStream fos = new FileOutputStream(out_path + String.valueOf(page) + ".txt");

                    //в первый раз проходим по елсе, чтоб нормально попадать в границы строк, втророй раз по иф по той же причине

                    if(page>0) {
                        fos.write(buffer, start_reed + page - 1, lenght + r);
                    }
                    else {
                        fos.write(buffer, start_reed, lenght + r);
                    }
                    start_reed = start_reed + lenght + r;
                    page++;

                }
            }
        }
        while ((last_byte = fin2.read())!=-1);
    }
}