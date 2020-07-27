package com.company;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {

        ExecutorService executor = Executors.newCachedThreadPool();  // используем для асинхронного запуска нескольких потоков

        ListMap lm = new ListMap();

        List<String> list = lm.reading_files();

        lm.Map(list);

        Map<String, List<String>> key_value = lm.map;

        long startTime = System.currentTimeMillis();

        // Перебираем key_value, передаём ключ со значением в конструктор и запускаем потоки

        key_value.forEach((key, value) -> {

            executor.submit(new RecordValues(key, value, "new document"));  // запускаю по очередно несколько потоков

        });

        executor.shutdown(); // завершаем работу потока

        try {

            executor.awaitTermination(10, TimeUnit.SECONDS);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        long finishFile = System.currentTimeMillis() - startTime;

        SimpleDateFormat sdf = new SimpleDateFormat("mm' минуты 'ss");

        VolumeDownload vd = new VolumeDownload();

        System.out.println("Завершено: 100%");

        System.out.printf("Загружено: " + key_value.size() + " файлов, %.1f MB\n", (vd.getVolume() / 1024));

        System.out.println("Время: " + sdf.format(finishFile) + " секунд");

        System.out.printf("Средняя скорость: %.1f kB/s\n", ((vd.getVolume() * 1024) / finishFile));
    }
}
