package com.emc.util;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;

public class FileUtil {

	private static File file = null;
	static final Logger logger = Logger.getLogger(FileUtil.class);

	private FileUtil() {

	}

	public static synchronized boolean createFile(String fileName)
			throws IOException {
		boolean isFileCreated = false;
		file = new File(fileName);

		if (file.exists()) {
			logger.warn("El fichero ya existe");
		} else {
			try {
				isFileCreated = file.createNewFile();
				logger.info("File is created");
			} catch (IOException e) {
				logger.error(e.getMessage());
				throw e;
			}
		}

		return isFileCreated;
	}

	public static File getFile() {
		return file;
	}
	
	public static boolean deleteFile() {
		
		return file.delete();
	}

}
