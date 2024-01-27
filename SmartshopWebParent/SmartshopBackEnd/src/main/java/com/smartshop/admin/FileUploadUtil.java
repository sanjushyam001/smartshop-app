package com.smartshop.admin;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtil {

	public static void saveFile(String uploadDir,String fileName, MultipartFile file) throws IOException {
		Path uploadPath=Paths.get(uploadDir);
		
		if(!Files.exists(uploadPath)) {
			Files.createDirectories(uploadPath);
		}
		try(InputStream inputStream=file.getInputStream()){
			Path filePath= uploadPath.resolve(fileName);
			Files.copy(inputStream, filePath,StandardCopyOption.REPLACE_EXISTING);
		}catch(IOException e) {
			throw new IOException("Cound not save file"+fileName,e);
		}
	}
	public static void cleanDir(String dirName) {
		Path dirPath = Paths.get(dirName);
		try {
			Files.list(dirPath).forEach(file->{
				if(!Files.isDirectory(file)) {
					try {
						Files.delete(file);
					} catch (IOException e) {
						System.out.println("Could not deleted the file :"+file);
						e.printStackTrace();
					}
				}
			});
			
		} catch (Exception e) {
			System.out.println("Could not list the file : "+dirPath);
		}
	}
}
