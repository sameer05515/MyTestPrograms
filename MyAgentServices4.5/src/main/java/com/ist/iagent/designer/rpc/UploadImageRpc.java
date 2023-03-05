package com.ist.iagent.designer.rpc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.ist.iagent.designer.util.PropertyUtil;

public class UploadImageRpc {
	private static final Logger log = Logger.getLogger(UploadImageRpc.class);
	private String uploadPath;
	private String docTitleImageFolderName = "docTitleImages";
	private String docClientImageFolderName = "docClientImages";
	private File f;
	private File documentationUpload;
	private File documentationClientUpload;

	public UploadImageRpc() {
		init();
	}

	private void init() {
		log.debug("Getting img upload url");
		uploadPath = PropertyUtil.getInstance().getValueForKey("imgUploadUrl");
		log.debug("Img upload path found :" + uploadPath);

		String docFolderName = PropertyUtil.getInstance().getValueForKey(
				"docTitleImageFolderName");
		if (docFolderName != null && docFolderName.trim().length() > 0) {
			docTitleImageFolderName = docFolderName;
			log.debug("documentation title image upload path found :"
					+ docTitleImageFolderName);
		} else {
			log.error("/'docTitleImageFolderName/' property not found to upload documentation title image");
			log.error("creating default folder to upload documentation title image "
					+ docTitleImageFolderName);
		}

		String docClientFolderName = PropertyUtil.getInstance().getValueForKey(
				"docClientImageFolderName");

		if (docClientFolderName != null
				&& docClientFolderName.trim().length() > 0) {
			docClientImageFolderName = docClientFolderName;
			log.debug("documentation client image upload path found :"
					+ docClientImageFolderName);
		} else {
			log.error("/'docClientImageFolderName/' property not found to upload documentation client image");
			log.error("creating default folder to upload documentation client image "
					+ docClientImageFolderName);
		}

		if (uploadPath == null) {
			return;
		}

		f = new File(uploadPath);
		if (!f.exists() || !f.isDirectory()) {
			log.debug("creating img upload folder :" + f.getAbsolutePath());
			f.mkdir();
		}

		documentationUpload = new File(f.getAbsolutePath() + "/"
				+ docTitleImageFolderName);
		if (!documentationUpload.exists() || !documentationUpload.isDirectory()) {
			log.debug("creating documentation title img upload folder :"
					+ documentationUpload.getAbsolutePath());
			documentationUpload.mkdir();
		}

		documentationClientUpload = new File(f.getAbsolutePath() + "/"
				+ docClientImageFolderName);

		if (!documentationClientUpload.exists()
				|| !documentationClientUpload.isDirectory()) {
			log.debug("creating documentation client img upload folder :"
					+ documentationClientUpload.getAbsolutePath());
			documentationClientUpload.mkdir();
		}
	}

	public File getImageUploadFolder(){
		return f;
	}
	
	public boolean uploadImage(String fileName, byte[] imgData) {

		if (f == null) {

			log.error("unable to upload img. /'imgUploadUrl/' property not found.");
			return false;
		}

		log.debug("uploading img file :" + fileName);
		return uploadImageFile(new File(f.getAbsolutePath() + "/" + fileName),
				imgData);
	}

	public boolean saveDocImage(String fileName, byte[] imgData) {

		if (documentationUpload == null) {

			log.error("unable to upload img. /'docTitleImageFolderName/' property not found.");
			return false;
		}

		log.debug("uploading img file :" + fileName);
		return uploadImageFile(new File(documentationUpload.getAbsolutePath()
				+ "/" + fileName), imgData);
	}

	public boolean saveClientImage(String fileName, byte[] imgData) {

		if (documentationClientUpload == null) {

			log.error("unable to upload img. /'docClientImageFolderName/' property not found.");
			return false;
		}

		log.debug("uploading img file :" + fileName);
		return uploadImageFile(
				new File(documentationClientUpload.getAbsolutePath() + "/"
						+ fileName), imgData);
	}

	private boolean uploadImageFile(File f, byte[] imgData) {

		FileOutputStream imgWriter = null;
		try {
			imgWriter = new FileOutputStream(f);
			imgWriter.write(imgData);
			imgWriter.flush();

			return true;
		} catch (Exception e) {
			log.error("Error in uploading img file :" + f.getAbsolutePath(), e);
			return false;
		} finally {
			try {

				if (imgWriter != null) {
					imgWriter.close();
				}
			} catch (Exception e) {
				log.error("Error in uploading img file handler :", e);
			}
		}

	}

	public byte[] getImage(String fileName) {

		if (f == null) {
			log.error("unable to getImage /'imgUploadUrl/' property not found.");
			return null;
		}
		log.debug("Getting img file :" + fileName);
		return getImageData(new File(f.getAbsolutePath() + "/" + fileName));

	}

	private byte[] getImageData(File f) {
		//File imgSave = null;
		FileInputStream imgReader = null;
		byte[] imgData = null;
		try {

			log.debug("reading img file :" + f.getAbsolutePath());
			if (f.exists() && f.isFile()) {

				imgReader = new FileInputStream(f);
				imgData = new byte[(int) f.length()];
				imgReader.read(imgData);

			}

			return imgData;
		} catch (Exception e) {
			log.error("Error in reading img file:" + f.getAbsolutePath(),
					e);
			return null;
		} finally {
			try {

				if (imgReader != null) {
					imgReader.close();
				}
			} catch (Exception e) {
				log.error("Error in reading img file handler :", e);
			}
		}
	}

	public String getImageUploadFolderName() {
		if (f == null) {

			log.error("unable to get image upload folde name /'imgUploadUrl/' property not found.");
			return null;
		}

		log.debug("getting img file upload folder Name : " + f.getName());
		return f.getName();
	}

	public String getDocImageUploadFolderName() {
		if (documentationUpload == null) {

			log.error("unable to get image upload folde name /'docTitleImageFolderName/' property not found.");
			return null;
		}

		log.debug("getting img file upload folder Name : " + f.getName() + "/"
				+ documentationUpload.getName());
		return f.getName() + "/" + documentationUpload.getName();
	}

	public String getDocClientUploadFolderName() {
		if (documentationClientUpload == null) {

			log.error("unable to get image upload folde name /'docClientImageFolderName/' property not found.");
			return null;
		}

		log.debug("getting img file upload folder Name : " + f.getName() + "/"
				+ documentationClientUpload.getName());
		return f.getName() + "/" + documentationClientUpload.getName();
	}

	public boolean deleteImage(String fileName) {
		if (f == null) {
			log.error("unable to delete img. /'imgUploadUrl/' property not found.");
			return false;
		}

		log.debug("deleting img file :" + fileName);
		return deleteFile(new File(f.getAbsolutePath() + "/" + fileName));

	}

	public boolean deleteDocTitleImage(String fileName) {
		if (documentationUpload == null) {
			log.error("unable to delete img. /'docTitleImageFolderName/' property not found.");
			return false;
		}

		log.debug("deleting documentation title img file :" + fileName);
		return deleteFile(new File(documentationUpload.getAbsolutePath() + "/"
				+ fileName));

	}

	public boolean deleteDocClientImage(String fileName) {
		if (documentationClientUpload == null) {
			log.error("unable to delete img. /'docClientImageFolderName/' property not found.");
			return false;
		}

		log.debug("deleting documentation client img file :" + fileName);
		return deleteFile(new File(documentationClientUpload.getAbsolutePath()
				+ "/" + fileName));

	}

	private boolean deleteFile(File f) {

		try {
			if (f.exists() && f.isFile()) {
				log.debug("deleting img file :" + f.getAbsolutePath());
				f.delete();
			}
			return true;
		} catch (Exception e) {
			log.error("Error in deleting img file:" + f.getAbsolutePath(), e);
			return false;
		}
	}

	/**
	 * 
	 * check if upload file exists or not if file with same name already exists
	 * return true, else false
	 */

	public boolean isImgExists(String fileName) {

		if (f == null) {
			log.error("unable to check image is exists or not. /'imgUploadUrl/' property not found.");
			return false;
		}

		log.debug("checking upload img file :" + fileName);
		return isFileExists(new File(f.getAbsolutePath() + "/" + fileName));
	}

	public boolean isDocTitleImgExists(String fileName) {

		if (f == null) {
			log.error("unable to check image is exists or not. /'imgUploadUrl/' property not found.");
			return false;
		}
		if (documentationUpload == null) {
			log.error("unable to check image is exists or not. /'docTitleImageFolderName/' property not found.");
			return false;
		}

		log.debug("checking documentation image upload file :" + fileName);
		return isFileExists(new File(documentationUpload.getAbsolutePath()
				+ "/" + fileName));
	}

	public boolean isClientImgExists(String fileName) {

		if (f == null) {
			log.error("unable to check image is exists or not. /'imgUploadUrl/' property not found.");
			return false;
		}
		if (documentationClientUpload == null) {
			log.error("unable to check image is exists or not. /'docClientImageFolderName/' property not found.");
			return false;
		}

		log.debug("checking upload img file :" + fileName);
		return isFileExists(new File(
				documentationClientUpload.getAbsolutePath() + "/" + fileName));
	}

	private boolean isFileExists(File f) {
		if (f == null) {
			return false;
		}
		try {
			if (f.exists() && f.isFile()) {
				log.debug("Img file already exists :" + f.getAbsolutePath());
				return true;
			}
		} catch (Exception e) {
			log.debug("error in checking upload img file :" + f.getName());
			return false;
		}
		return false;
	}

	public List<String> imagesList() {

		if (f == null) {
			log.error("unable to get images list. /'imgUploadUrl/' property not found.");
			return null;
		}

		log.debug("Getting uploaded images");
		return getImagesNameList(f);
	}

	public List<String> getDocImagesList() {

		if (documentationUpload == null) {
			log.error("unable to get images list. /'docTitleImageFolderName/' property not found.");
			return null;
		}

		log.debug("Getting documentation uploaded images list");
		return getImagesNameList(documentationUpload);
	}

	public List<String> getDocClientImagesList() {

		if (documentationClientUpload == null) {
			log.error("unable to get images list. /'docClientImageFolderName/' property not found.");
			return null;
		}

		log.debug("Getting documentation client uploaded images list");
		return getImagesNameList(documentationClientUpload);
	}

	private List<String> getImagesNameList(File f) {
		List<String> imgList = new ArrayList<String>();
		File imgFiles[] = f.listFiles();

		for (int i = 0; i < imgFiles.length; i++) {
			String imgName = imgFiles[i].getName();
			if (imgName.endsWith(".jpg") || imgName.endsWith(".jpeg")
					|| imgName.endsWith(".png") || imgName.endsWith(".gif")) {
				log.trace("img file found :" + imgFiles[i].getName());
				imgList.add(imgFiles[i].getName());
			}
		}

		return imgList;
	}

}
