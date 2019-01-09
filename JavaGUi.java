package com.demo.aksops;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Amit Sharma on 1/6/2019.
 */
public class JavaGUi {

    public static void main(String[] args) throws IOException {
        splitfile("D:\\Data\\","Self Attested PAN.JPG","TEST/");
        JoinFile("D:\\Data\\","Self Attested PAN.JPG","TEST/","TEST2.JPG");

    }
    private static void splitfile(String HomeDir,String FILE,String SplipDir){
        String FILE_NAME=HomeDir+FILE;
        String TestDir=HomeDir+SplipDir;
        File dir=new File(TestDir);
        dir.mkdir();
        int PART_SIZE = 204800;
        File inputFile = new File(FILE_NAME);
        FileInputStream inputStream;
        String newFileName;
        FileOutputStream filePart;
        int fileSize = (int) inputFile.length();
        int nChunks = 0, read = 0, readLength = PART_SIZE;
        byte[] byteChunkPart;
        try {
            inputStream = new FileInputStream(inputFile);
            while (fileSize > 0) {
                if (fileSize <= 5) {
                    readLength = fileSize;
                }
                byteChunkPart = new byte[readLength];
                read = inputStream.read(byteChunkPart, 0, readLength);
                fileSize -= read;
                assert (read == byteChunkPart.length);
                nChunks++;
                newFileName = TestDir+FILE+ ".part" + Integer.toString(nChunks - 1);
                filePart = new FileOutputStream(new File(newFileName));
                filePart.write(byteChunkPart);
                filePart.flush();
                filePart.close();
            }
            inputStream.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
    private static void JoinFile(String HomeDir,String FILE,String SplipDir,String outFile) throws IOException {
        String TestDir=HomeDir+SplipDir;
        String Destination=HomeDir+outFile;
        File out=new File(HomeDir+outFile);
        out.delete();
        Stream<Path> files = Files.list(Paths.get(HomeDir+SplipDir));
        long count = files.count();
        File ofile = new File(Destination);
        FileOutputStream fos;
        FileInputStream fis;
        byte[] fileBytes;
        int bytesRead;
        List<File> list = new ArrayList<>();
        for (int i = 0; i <count ; i++) {
            list.add(new File(TestDir+FILE+".part"+i));
        }
        try {
            fos = new FileOutputStream(ofile,true);
            for (File file : list) {
                fis = new FileInputStream(file);
                fileBytes = new byte[(int) file.length()];
                bytesRead = fis.read(fileBytes, 0,(int)  file.length());
                assert(bytesRead == fileBytes.length);
                assert(bytesRead == (int) file.length());
                fos.write(fileBytes);
                fos.flush();
                fis.close();
            }
            fos.close();
        }catch (Exception exception){
            exception.printStackTrace();
        }
        }
    }
