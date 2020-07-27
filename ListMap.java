package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListMap {

    HashMap<String, List<String>> map = new HashMap<>(); //использую ассоциативный массив (ключ, значение)

    // метод считывает информацию с файла и присваивает значение в List
    public List<String> reading_files(){

        List<String> list = new ArrayList<>();

        try {

            File file = new File("D:\\Java_Zadanie\\textFile.txt");

            FileReader fr = new FileReader(file);

            BufferedReader reader = new BufferedReader(fr);

            String line = reader.readLine();

            while (line != null){

                list.add(line);

                line = reader.readLine();

            }

        } catch (IOException e) {

            e.printStackTrace();

        }

        return list;
    }

    // метод разбивает данные из листа на ключ=значения и присваивает их ассоциативному массиву
    public void Map(List<String> list){

        for (int i = 0; i < list.size(); i++) {

            String[] str = list.get(i).split(" "); // разделяем на будущее ключи и значения

            if (!map.containsKey(str[0])){              // проверим присутствует ли уже данный ключ

                map.put(str[0], new ArrayList<>());

            }

            map.get(str[0]).add(str[1]);  // присваиваем значение текущемц ключу

        }

    }

}
