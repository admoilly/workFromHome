package com.ubs.gcmm.devops.docker.Controllers;


import com.ubs.gcmm.devops.docker.Model.DBFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Controller
public class HomeController {

	@Autowired
	private com.ubs.gcmm.devops.docker.service.DBFileStorageService DBFileStorageService;

	@GetMapping("/")
	public String fileUploadForm(Model model) {
		return "fileUploadForm";
	}

	@PostMapping("/fileUpload")
	public ResponseEntity<Object> fileUpload(@RequestParam("file") MultipartFile file) {
		String out="Invalid file.";
		if (!file.getOriginalFilename().isEmpty()) {
			out=_uploadFile(file);
		}else{
			return new ResponseEntity<>(out, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(out,HttpStatus.OK);
	}

	private String _uploadFile(MultipartFile multipartFile) {
		DBFile dbFile = DBFileStorageService.storeFile(multipartFile);
		return ServletUriComponentsBuilder.fromCurrentContextPath()
				.path("/downloadFile/")
				.path(dbFile.getId())
				.toUriString();
	}
	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
		return _downloadFile(fileId);
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
