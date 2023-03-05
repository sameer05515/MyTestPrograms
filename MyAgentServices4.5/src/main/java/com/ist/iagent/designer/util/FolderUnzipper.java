package com.ist.iagent.designer.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

public class FolderUnzipper {
	public static final int BUFFER_SIZE = 2048;

	private static Logger log = Logger.getLogger(FolderUnzipper.class);

	// This function converts the zip file into uncompressed files which are
	// placed in the
	// destination directory
	// destination directory should be created first
	public static boolean unzipFiles(String srcDirectory, String srcFile,
			String destDirectory) {
		try {
			/** first make sure that all the arguments are valid and not null */
			if (srcDirectory == null) {
				log.info("Error code 1 :- srcDirectory name is null. unable to process.");
				return false;
			}
			if (srcFile == null) {
				log.info("Error code 2 :- srcFile name is null. unable to process.");
				return false;
			}
			if (destDirectory == null) {
				log.info("Error code 3 :- destDirectory name is null. unable to process.");
				return false;
			}
			if (srcDirectory.equals("")) {
				log.info("Error code 4 :- srcDirectory name is empty. unable to process.");
				return false;
			}
			if (srcFile.equals("")) {
				log.info("Error code 5 :- srcFile name is empty. unable to process.");
				return false;
			}
			if (destDirectory.equals("")) {
				log.info("Error code 6 :- destDirectory name is empty. unable to process.");
				return false;
			}
			/** now make sure that these directories exist */
			File sourceDirectory = new File(srcDirectory);
			File sourceFile = new File(srcDirectory + File.separator + srcFile);
			File destinationDirectory = new File(destDirectory);

			if (!sourceDirectory.exists()) {
				log.info("Error code 7 :- sourceDirectory not exists. unable to process.");
				return false;
			}
			if (!sourceFile.exists()) {
				log.info("Error code 8 :- sourceFile not exists. unable to process.");
				return false;
			}
			if (!destinationDirectory.exists()) {
				log.info("Error code 9 :- destinationDirectory not exists. unable to process.");
				return false;
			}

			/** now start with unzip process */
			BufferedOutputStream dest = null;

			FileInputStream fis = new FileInputStream(sourceFile);
			ZipInputStream zis = new ZipInputStream(
					new BufferedInputStream(fis));

			ZipEntry entry = null;

			while ((entry = zis.getNextEntry()) != null) {
				String outputFilename = destDirectory + File.separator
						+ entry.getName();

				log.info("Extracting file: " + entry.getName());

				createDirIfNeeded(destDirectory, entry);

				int count;

				byte data[] = new byte[BUFFER_SIZE];

				/** write the file to the disk */
				FileOutputStream fos = new FileOutputStream(outputFilename);
				dest = new BufferedOutputStream(fos, BUFFER_SIZE);

				while ((count = zis.read(data, 0, BUFFER_SIZE)) != -1) {
					dest.write(data, 0, count);
				}

				/** close the output streams */
				dest.flush();
				dest.close();
				fos.flush();
				fos.close();
				
			}

			/**
			 * we are done with all the files close the zip file
			 */
			zis.close();
			fis.close();
			
		} catch (Throwable e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	private static void createDirIfNeeded(String destDirectory, ZipEntry entry) {
		String name = entry.getName();

		if (name.contains("/")) {
			log.info("directory will need to be created");

			int index = name.lastIndexOf("/");
			String dirSequence = name.substring(0, index);

			File newDirs = new File(destDirectory + File.separator
					+ dirSequence);

			// create the directory
			newDirs.mkdirs();
		}
	}

	public static void main(String[] args) {
		String srcDirectory = "C:/NovelVox/iagent-server/webapps/ROOT/xml/export-projects/";
		String srcFile = "iagent.zip";
		String destDirectory = "C:/NovelVox/iagent-server/webapps/ROOT/xml/";
		unzipFiles(srcDirectory, srcFile, destDirectory);
	}

}