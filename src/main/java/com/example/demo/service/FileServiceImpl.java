package com.example.demo.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileServiceImpl implements FileService {



    @Value("${input.directory}")
    private String inputDirectory;

    @Value("${output.directory}")
    private String outputDirectory;

    @Value("${processed.directory}")
    private String processedDirectory;

    @Value("${error.directory}")
    private String errorDirectory;

    @Override
    public void processFiles() {
        File folder = new File(inputDirectory);
        File[] files = folder.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (file.isFile()) {
                try {
                    int sum = sumFileContents(file);
                    createOutputFile(file, sum);
                    moveFileToDirectory(file, processedDirectory);
                } catch (IOException e) {
                    // Handle IOException here
                    handleIOException(file);
                }
            }
        }
    }

    private void handleIOException(File file) {
        try {
            moveFileToDirectory(file, errorDirectory);
        } catch (IOException e) {
            // Log the error or perform any other appropriate action
            e.printStackTrace();
        }
    }

    private int sumFileContents(File file) throws IOException {
        int sum = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                sum += Integer.parseInt(line.trim());
            }
        }
        return sum;
    }

    private void createOutputFile(File inputFile, int sum) throws IOException {
        String outputFileName = inputFile.getName() + ".OUTPUT";
        File outputFile = new File(outputDirectory, outputFileName);
        try (FileWriter writer = new FileWriter(outputFile)) {
            writer.write(String.valueOf(sum));
        }
    }

    private void moveFileToDirectory(File file, String directory) throws IOException {
        Path sourcePath = file.toPath();
        Path destinationPath = new File(directory, file.getName()).toPath();
        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
    }
}
