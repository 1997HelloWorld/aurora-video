package com.aurora.video.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class FFMpeg {

    private String FFMPegEXE;

    public FFMpeg(String FFMPegEXE) {
        this.FFMPegEXE = FFMPegEXE;
    }

    /**
     * 视频格式转换
     * @param inputPath
     * @param outputPath
     * @throws IOException
     */
    public void convertor(String inputPath, String outputPath) throws IOException {
        //ffmpeg.exe -i fish.mp4  fish2.avi
        List<String> command = new ArrayList<>();
        command.add(FFMPegEXE);
        command.add("-i");
        command.add(inputPath);
        command.add(outputPath);

        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();

        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line=br.readLine())!=null){

        }
        if(br!=null){
            br.close();
        }
        if(inputStreamReader!=null){
            inputStreamReader.close();
        }
        if(errorStream!=null){
            errorStream.close();
        }

    }

    /**
     *
     * @param inputVideoPath 输入原视频
     * @param tempVideoPath 输出消音视频，给merger方法调用，合并BGM
     * @throws IOException
     */
    public void videoSilencing(String inputVideoPath, String tempVideoPath) throws IOException {
        //消音    ffmpeg.exe -i fish.mp4 -vcodec copy -an fish2.mp4

        List<String> command = new ArrayList<>();
        command.add(FFMPegEXE);
        command.add("-i");
        command.add(inputVideoPath);
        command.add("-vcodec");
        command.add("copy");
        command.add("-an");
        command.add(tempVideoPath);

        for (String s:command){
            System.out.print(s);
            System.out.print(" ");

        }

        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line=br.readLine())!=null){

        }
        if(br!=null){
            br.close();
        }
        if(inputStreamReader!=null){
            inputStreamReader.close();
        }
        if(errorStream!=null){
            errorStream.close();
        }

        System.out.println("消除当前视频音轨完成");
    }

    /**
     *
     * @param inputVideoPath 拿到消音后的视频
     * @param inputVideoDuration 视频时长
     * @param inputBgmPath  BGM地址
     * @param outputVideoPath   输出合并后的视频
     * @throws IOException
     */
    public void videoMerge(String inputVideoPath,String inputVideoDuration,
                           String inputBgmPath,String outputVideoPath) throws IOException {
        System.out.println("开始进行合并");
       // ffmpeg.exe -i F:\images\temp.avi -i F:\images\flowerSea -t 20 -y F:\images\video2.avi
        //合并    ffmpeg.exe -i fish2.mp4 -i flowerSea.mp3 -t 7 -y new3.mp4
        List<String> command = new ArrayList<>();
        command.add(FFMPegEXE);
        command.add("-i");
        command.add(inputVideoPath);
        command.add("-i");
        command.add(inputBgmPath);
        command.add("-t");
        command.add(String.valueOf(inputVideoDuration));
        command.add("-y");
        command.add(outputVideoPath);

        for (String s:command){
            System.out.print(s);
            System.out.print(" ");
        }

        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader br = new BufferedReader(inputStreamReader);
        String line = "";
        while ((line=br.readLine())!=null){

        }
        if(br!=null){
            br.close();
        }
        if(inputStreamReader!=null){
            inputStreamReader.close();
        }
        if(errorStream!=null){
            errorStream.close();
        }
        System.out.println("视频合并完成");
    }

    public static void main(String[] args) {
        FFMpeg ffMpeg = new FFMpeg("D:\\ffmpeg\\bin\\ffmpeg.exe");
        try {

            ffMpeg.videoSilencing("F:\\images\\video1.avi","F:\\images\\temp.avi");

            ffMpeg.videoMerge("F:\\images\\temp.avi",
                    "20",
                    "F:\\images\\flowerSea.mp3",
                    "F:\\images\\video2.avi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
