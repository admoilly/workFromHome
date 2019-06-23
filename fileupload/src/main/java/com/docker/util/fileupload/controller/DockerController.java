package com.docker.util.fileupload.controller;

import com.docker.util.fileupload.dockerUtil.DockerImpl;
import com.docker.util.fileupload.model.DBFile;
import com.docker.util.fileupload.model.DownloadModel;
import com.docker.util.fileupload.payload.UploadFileResponse;
import com.github.dockerjava.api.DockerClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.print.Doc;
import java.io.IOException;

@RestController
public class DockerController {

    private static final Logger logger = LoggerFactory.getLogger(DockerController.class);
    @Autowired
    DockerClient dockerClient;

    @Autowired
    private com.docker.util.fileupload.service.DBFileStorageService DBFileStorageService;

    @PostMapping("/deploy")
    public String uploadFile(@RequestParam("file") MultipartFile jarFile, @RequestParam("property") MultipartFile propertyFile) throws IOException {
        UploadFileResponse jarUpload=_uploadFile(jarFile);
        UploadFileResponse propertyFileUpload=_uploadFile(propertyFile);
        try {
            new DockerImpl(dockerClient,new DownloadModel(jarUpload.getFileDownloadUri(),propertyFileUpload.getFileDownloadUri()));
        }catch (Exception e){
            e.printStackTrace();
            return "Failed";
        }
        return "Success";
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        return _downloadFile(fileId);
    }

    private UploadFileResponse _uploadFile(MultipartFile multipartFile) throws IOException {
        DBFile dbFile = DBFileStorageService.storeFile(multipartFile);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                multipartFile.getContentType(), multipartFile.getSize());
    }

    private ResponseEntity<Resource> _downloadFile(String fileId) {
        // Load file from database
        DBFile dbFile = DBFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

}
