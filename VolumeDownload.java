package com.company;

public class VolumeDownload {  // использую для хранения скаченного обЪема файлов

    private static double volume = 0;

    public double getVolume() {

        return volume;
    }

    public static void setVolume(double size) {

        volume += size;
    }
}
