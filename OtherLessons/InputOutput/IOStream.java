/*
 * ... Вебинар https://www.youtube.com/watch?v=r9paa9AJ7Gk
 * ... Ввод-вывод в Java
 * ... Рассмотрим работу с файлами в виде потока байтов.
 */

package OtherLessons.InputOutput;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class IOStream {
    public static void main(String[] args) {
//        fileFirst("OtherLessons/InputOutput/fileForTestingIO");
//        demoReadWrite("OtherLessons/InputOutput/fileForTestingIO");
//        encodeDemo("OtherLessons/InputOutput/fileForTestingIO");
//        fileSystem("OtherLessons/InputOutput/fileForTestingIO");
        try {
            demoChannel("OtherLessons/InputOutput/fileForTestingIO");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void fileFirst(String path) {
        String outputPath = "OtherLessons/InputOutput/fileOutput.txt";
        try (InputStream is = new FileInputStream(path);
               /* В выходном потоке можно указать "дозапись"
                  ...new FileOutputStream(outputPath, true); */
             OutputStream os = new FileOutputStream(outputPath)) {
                /* Работа в побитовом режиме очень медленная, поэтому для работы
                   выгоднее применить методы с буферизацией. Для этого создаем
                   массив символов и используем его в качестве буфера.*/
            byte[] buffer = new byte[4096];
            int r = is.read(buffer);
            while (r != -1) {
                os.write(buffer, 0, r);
                r = is.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * Рассмотрим работу с текстовыми файлами в символьном потоке.
     */
    public static void demoReadWrite(String path) {
        String outputPath = "OtherLessons/InputOutput/fileOutput.txt";
        try (Reader r = new FileReader(path);
             Writer w = new FileWriter(outputPath)) {

            char[] buffer = new char[4096];
            int c = r.read(buffer);
            while (c != -1) {
                w.write(buffer, 0, c);
                c = r.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     * ... Существуют методы конвертации потока байтов в поток символов.
     * ... Это может быть необходимо, чтобы считать файлы и преобразовать в
     * соответствующую кодировку.
     */
    public static void encodeDemo(String path) {
        String outputPath = "OtherLessons/InputOutput/fileOutput.txt";
        try (FileInputStream fis = new FileInputStream(path);
             Reader r = new InputStreamReader(fis, "utf-8");
             BufferedReader br = new BufferedReader(r)) {

            String line = br.readLine();
            while (line != null){
                System.out.println(line);
                line = br.readLine();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
     *     Поток байт бывает интересен, когда необходимо "обмануть" какую-то
     * графическую подсистему или что-то подобное, что ждет как раз таки
     * поток байт.
     *     Это запись и чтение из памяти и в память: используется для специфических
     * задач, зубрить детально смысла нет. Но держать в голове "оно есть" надо.
     */
    public static void demoMemory(){
        ByteArrayInputStream bais = new ByteArrayInputStream(new byte[4096]);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
    }

    /*     Разберем способы работы с файловой системой: файлами и директориями.
     *
     */
    public static void fileSystem(String path){
        File file = new File(path);
        if ((file.exists())) {
            System.out.print("File is exist");
        } else {
            System.out.print("File not foud");
        }
//        file.mkdirs() // классный метод для создания вложенной структуры
//        file.list() // выводит состав директории (список файлов)

        /*
         *     Если скормить коду ниже путь в директорию, то вернется отфильтрованный
         * список файлов в этой директории.
         */
//        String[] files = file.list(new FilenameFilter() {
//            @Override
//            public boolean accept(File dir, String name) {
//                return name.startsWith("");
//            }
//        });
//
//        for (String g : files) {
//            System.out.println(g);
//        }
    }



    /* ------------------------ --- ------------------------ */
    /* ------------------------ NIO ------------------------ */
    /* ------------------------ --- ------------------------ */
    /*
     *     Не блокирующие потоки ввода-вывода. Достаточно запутаны для понимания.
     * Автор видео утверждает, что порог входа в эту тему достаточно высокий,
     * рассмотрим только базовые моменты.
     *     NIO строятся не на стримах, а на трех типах сущностей: канал, буфер, селектор.
     * Буфер заполняется из канала. Если буфер большого размера, то он просто
     * проглатывает порцию из канала и мы с ней впоследствии работаем.
     *     Копирование достаточно больших объемов происходит сильно производительнее,
     * чем даже копирование в стримах с буферами.
     *     Есть три важных понятия:
     *          position -
     *          limit -
     *          capacity -
     *     Каналы существуют только в рамках потоков. Поэтому сначала открываем поток.
     */
    public static void demoChannel(String path) throws Exception{
        FileInputStream fis = new FileInputStream(path);
        FileChannel inChannel = fis.getChannel();
        FileOutputStream fos = new FileOutputStream(path+"1.txt");
        FileChannel outChannel = fos.getChannel();
        ByteBuffer inBuffer = ByteBuffer.allocate(4096);
        ByteBuffer outBuffer = ByteBuffer.allocate(4096);

        int r = inChannel.read(inBuffer);
        while (r != -1){
            inBuffer.flip(); // переходим в режим чтения из буфера
            while(inBuffer.hasRemaining()){
                byte get = inBuffer.get();
                outBuffer.put(get);
            }
            outBuffer.flip();
            outChannel.write(outBuffer);
            inBuffer.clear();
            outBuffer.clear();
            r = inChannel.read(inBuffer);
        }
        fis.close();
        fos.close();
    }

    /*
     *     У каналов существует специальный метод "transferTo".
     * Можем использовать, если хотя бы один канал файловый (в или из).
     *     Секрет быстродействия в том, что при прямой записи на диск или чтении
     * скорость взаимодействия с диском очень высокая - вплоть до сотен МБ в секунду.
     *
     */

    public static void transferDemo(String path) throws Exception{
        FileInputStream fis = new FileInputStream(path);
        FileOutputStream fos = new FileOutputStream(path+"1.txt");
        FileChannel inChannel = fis.getChannel();
        FileChannel outChannel = fos.getChannel();

        // Копируем практически цельным куском: размер равен размеру буффера inChannel
        inChannel.transferTo(0,inChannel.size(),outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        fis.close();
        fos.close();
    }

    /*
     *     Существует еще два интересных класса в java.nio. : Path и Files.
     *     С обоими необходимо ознакомиться для работы с файловой системой.
     *
     *     Классы действительно полезные, например считать в поток строк и
     * вывести в консоль:
     *         Path p = Path.get("file.txt");
     *         Stream<String> lines = Files.lines(p);
     *         lines.forEach((s) -> System.out.println(s));
     */
}
