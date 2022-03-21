package com.p.file.search;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class FileSearcher {

	/*public static void main(String[] args) {
		String res = new FileSearcher()
				.startSearch("C:/Users/796412/Desktop/readings/");

		System.out.println(res);

	}*/

	List<String> fileList = new ArrayList<String>();
	
	public List<String> extensionList=new ArrayList<String>();

	public String startSearch(String fileName, List<String> extnList) {
		extensionList=extnList;
		StringBuffer sb = new StringBuffer();
		if (fileName != null) {
			fileList = new ArrayList<String>();
			serarch(fileList, fileName);
			
			//System.out.println(fileName+"  "+fileList);
			for (int i = 0; i < fileList.size(); i++) {
				if (i == 0) {
					sb.append("[");
				}

				sb.append(fileList.get(i));

				if (i == fileList.size() - 1) {
					sb.append("]");
				} else {
					sb.append(" , \n");
				}
			}
		}

		return sb.toString();

	}

	private void serarch(List<String> fileList, String fileName) {

		File f = new File(fileName);
		File[] children = f.listFiles(new MyFileFilter(extensionList));
		if(children!=null && children.length>0){
			for (File child : children) {
			if (child.isDirectory()) {
				serarch(fileList, child.getAbsolutePath());
			}
			if (child.isFile()) {

				fileList.add("{\"name\":\"" + child.getName()
						+ "\" , \"filePath\":\""
						+ child.getAbsolutePath().trim().replace("\\", "/")
						+ "\" , \"parentFile\":\""
						+ fileName.trim().replace("\\", "/")
						+ "\"}");

				// try {
				// fileList.add("{\"name\":\""
				// + child.getName()
				// + "\" , \"filePath\":\""
				// + child.getCanonicalPath().trim()
				// + "\"}");
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
			}
		}
		}
		

	}

	private static class MyFileFilter implements FileFilter {

		List<String> allowedExtentions=new ArrayList<String>();
		
		public MyFileFilter(List<String> allowedExtentions){
			this.allowedExtentions=allowedExtentions;
		}
		@Override
		public boolean accept(File file) {

			if (file == null) {
				return false;
			}
			if (!file.exists()) {
				return false;
			}
			if (file.isDirectory()) {
				return true;
			}
			// System.out.println(file.getName());
			String fileNameee = (file.getName() != null) ? file.getName()
					.trim().toLowerCase() : null;
			//String[] allowedExtentions={".pdf",".mp3",".mp4",".exe",".zip",".png",".jpeg","jpg"};
			
			//System.out.println("fileNameee : "+fileNameee);
			
			for(String exts:allowedExtentions){
				
				if (fileNameee != null && fileNameee.endsWith(exts)) {
					return true;
				}				
			}
			
			
			/* if (fileNameee != null && fileNameee.endsWith(".pdf")) {
				return true;
			}
			
			if (fileNameee != null && fileNameee.endsWith(".mp3")) {
				return true;
			}
			
			if (fileNameee != null && fileNameee.endsWith(".mp4")) {
				return true;
			}
			
			if (fileNameee != null && fileNameee.endsWith(".exe")) {
				return true;
			} */

			return false;
		}

	}

}
