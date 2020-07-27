package com.company;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.List;

// класс используется для скачивания файлов и их записи
public class RecordValues extends Thread {

    String keyurl;

    List<String> valuefiles;

    private String location;



    public RecordValues(String url, List<String> files, String location){

        this.keyurl = url;

        this.valuefiles = files;

        this.location = location + "/";

    }

    // запускается при создании объекта, скачивает исходные файлы и записывает их содержимое в указанные файлы
    public void run(){

        File folder = new File(location);

        if (!folder.exists()){

            folder.mkdirs();    // если файл не существует создадим его

        }

        long startTime = System.currentTimeMillis(); // запускаем таймер, чтобы понять время скачивания

        try {

            URL urlConnect = new URL(keyurl);

            byte[] buffer = new byte[1024];

            int count = 0;



            BufferedInputStream bis = new BufferedInputStream(urlConnect.openStream());

            FileOutputStream fos = new FileOutputStream(location + valuefiles.get(0));

            while ((count = bis.read(buffer, 0, 1024)) != -1) {

                fos.write(buffer, 0, count); // запись по байтно
            }

            fos.close();

            bis.close();



            if (valuefiles.size() != 0 || valuefiles.size() != 1)  // данное условие использую, когда нужно заполнить
            {                                                      // несколько файлов однотипной информацией

                for (int i = 1; i < valuefiles.size(); i++){

                    File source = new File(location + valuefiles.get(0));

                    File dest = new File(location + valuefiles.get(i));

                    Files.copy(source.toPath(), dest.toPath());

                }

            }

        } catch (IOException e) {

            e.printStackTrace();
        }


        long finishFile = System.currentTimeMillis() - startTime;  // останавливаем таймер

        SimpleDateFormat sdfFile = new SimpleDateFormat("mm");

        double size = 0;

        File file = new File(location + valuefiles.get(0));

        if (file.exists()){

            size = (double) ((file.length() / 1024) * valuefiles.size());

            if (size < 1024) {

                System.out.println("Загружается файл: " + valuefiles.get(0));

                System.out.println("Файл " + valuefiles.get(0) + " загружен: " + size + " kB" + " за " + sdfFile.format(finishFile) + " минуту");

                System.out.println();

            } else {

                System.out.println("Загружается файл: " + valuefiles.get(0));

                System.out.printf("Файл " + valuefiles.get(0) + " загружен: %.1f  MB" + " за " + sdfFile.format(finishFile) + " минуту\n", (size / 1024));

                System.out.println();
            }

            VolumeDownload.setVolume(size);
        }

        System.out.println();
    }
}
