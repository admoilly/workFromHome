package com.docker.util.fileupload.dockerUtil;


import com.docker.util.fileupload.controller.DockerController;
import com.docker.util.fileupload.model.DownloadModel;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.command.BuildImageResultCallback;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


public class DockerImpl {

    private static final Logger logger = LoggerFactory.getLogger(DockerImpl.class);
    public DockerImpl(DockerClient dockerClient, DownloadModel downloadModel) throws IOException {
        File DockerFile=new File(createDockerFile(downloadModel));
        String ImageId=dockerClient.buildImageCmd().withDockerfile(DockerFile).withPull(true)
                .withNoCache(false)
                .withTag("alpine:git")
                .exec(new BuildImageResultCallback())
                .awaitImageId();
        /*FileUtils.deleteQuietly(DockerFile);*/
        logger.info(ImageId);
    }

    private String createDockerFile(DownloadModel downloadModel) throws IOException {
        String dockerFileContent= String.format("FROM openjdk:latest\nADD %s /app/app.jar\nADD %s /app/db.properties", downloadModel.getJarDownloadLink(),downloadModel.getPropertyFileDownloadLink());
        File tmpFile = File.createTempFile("test", ".tmp");
        FileWriter writer = new FileWriter(tmpFile);
        writer.write(dockerFileContent);
        writer.close();
        logger.info(tmpFile.getAbsolutePath());
        return tmpFile.getAbsolutePath();
    }


}
