package com.ssaffy.happyapi.File;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ServerProperties.Tomcat.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import springfox.documentation.spring.web.paths.Paths;

@RestController
@RequestMapping("/file")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FileController {
	@Autowired
	FileService service;
	
	@PostMapping(value = "/upload/{id}")
	public void upload(@Value("${file.paths}") String filePath, @PathVariable("id") String id, @RequestParam("file") MultipartFile mfile) throws Exception{
		System.out.println(filePath);
		File folder = new File(filePath);
		
		if (!folder.exists()) folder.mkdir();
		mfile.transferTo(new File(filePath, id+".png"));
	}
	
	@PostMapping(value = "/download")
	public String download(@Value("${file.paths}") String filePath, @RequestBody String id) {
		File file = new File(filePath + "\\" + id + ".png");
		if (file.exists() == true) {
			return "1";
		} else {
			return "0";
		}
	}

	@PostMapping(value="/articleFile/{num}")
	public void articleFile(@Value("${file.paths.article}") String filePath, @PathVariable("num") String num, @RequestParam("file") MultipartFile[] mfile) throws Exception{
		System.out.println(Arrays.toString(mfile));
		System.out.println(filePath + "\\" + num);
		File folder = new File(filePath + "\\" + num);
		if (folder.exists()) {
			File[] folder_list = folder.listFiles(); //파일리스트 얻어오기
					
			for (int j = 0; j < folder_list.length; j++) {
				folder_list[j].delete(); //파일 삭제 
				System.out.println("파일이 삭제되었습니다.");
						
			}
					
			if(folder_list.length == 0 && folder.isDirectory()){ 
				folder.delete(); //대상폴더 삭제
				System.out.println("폴더가 삭제되었습니다.");
			}
	    }
		folder.mkdir();
		int i = 1;
		for(MultipartFile m : mfile) {
			m.transferTo(new File(filePath + "\\" + num, i+".png"));
			i++;
		}
	}
	@PostMapping(value="/fileNum")
	public int fileNum(@Value("${file.paths.article}") String filePath, @RequestBody String articleNo) {
		File folder = new File(filePath + "\\" + articleNo);
		if (folder.exists() == true) {
			return folder.listFiles().length;
		} else {
			return 0;
		}
	}
	@PostMapping(value="/profileChk")
	public int profileChk(@Value("${file.paths}") String filePath, @RequestBody String id) {
		File file = new File(filePath + "\\" + id + ".png");
		if (file.exists() == true) {
			return 1;
		}
		return 0;
	}
	@PostMapping(value="/deleteFile")
	public void deleteFile(@Value("${file.paths}") String filePath, @RequestBody String id) {
		System.out.println(id);
		File file = new File(filePath + "\\" + id + ".png");
		if (file.exists() == true) {
			file.delete();
		}
	}
	@GetMapping(value="/folderChk/{no}")
	public int folderChk(@Value("${file.paths.article}") String filePath, @PathVariable("no") int no) {
//		System.out.println(no);
		File folder = new File(filePath + "\\" + no);
		if (folder.exists()) {
//			System.out.println(folder.listFiles().length);
			return folder.listFiles().length;
		} else {
			return 0;
		}
	}
	@GetMapping(value="/clearFolder/{no}")
	public void clearFolder(@Value("${file.paths.article}") String filePath, @PathVariable("no") int no) {
		File folder = new File(filePath + "\\" + no);
		if (folder.exists()) {
			File[] folder_list = folder.listFiles(); //파일리스트 얻어오기
					
			for (int j = 0; j < folder_list.length; j++) {
				folder_list[j].delete(); //파일 삭제 
				System.out.println("파일이 삭제되었습니다.");
						
			}
					
			if(folder_list.length == 0 && folder.isDirectory()){ 
				folder.delete(); //대상폴더 삭제
				System.out.println("폴더가 삭제되었습니다.");
			}
	    }
	}
}
