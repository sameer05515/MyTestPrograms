package com.p.socket;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class FileDelete {
    public static void main(String[] args) {
        String installedPath="C:\\Users\\premendra\\Desktop\\03-rendering-the-product-detail-view\\__MACOSX\\03-rendering-the-product-detail-view - Copy";
        try {
            Files.walk(Paths.get(installedPath))
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);

            Files.deleteIfExists(Paths.get(installedPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
