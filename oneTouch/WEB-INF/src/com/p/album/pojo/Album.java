package com.p.album.pojo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Album {

	private List<AlbumPart> albumPartList = new ArrayList<AlbumPart>();
	private static String newLineString="\n";

	public Album(String[][][] album,String newLineString ) {
		super();
		if (album != null) {
			for (String[][] a : album) {

				add(a[0][1], Integer.parseInt(a[1][1]), a[2]);
			}
		}

	}
	
	public Album(String[][][] album) {
		this(album,newLineString);

	}

	public Album add(String header, int allowedTimeToDisplay, String[] urls) {

		this.albumPartList
				.add(new AlbumPart(header, allowedTimeToDisplay, urls));

		return this;

	}

	public static class AlbumPart {
		private String header;
		private int allowedTimeToDisplay;
		private List<String> imageURLList = new ArrayList<String>();

		private AlbumPart(String header, int allowedTimeToDisplay, String[] urls) {
			this.header = header;
			this.allowedTimeToDisplay = allowedTimeToDisplay;
			if (urls != null && urls.length > 0) {
				for (String urlStr : urls) {
					addImage(urlStr);
				}
			}

		}

		public AlbumPart addImage(String url) {
			if (url == null) {
				System.out
						.println("Provided url is null : not added to Album Part : "
								+ header);
				return this;
			}
			if (!(new File(url).exists())) {
				System.out
						.println("Provided url is pointing non-existent image file : not added to Album Part : "
								+ header);
				return this;
			}
			this.imageURLList.add(url);
			return this;
		}

		public List<String> getImageURLList() {
			return imageURLList;
		}

		public void setImageURLList(List<String> imageURLList) {
			this.imageURLList = imageURLList;
		}

		public String getHeader() {
			return header;
		}

		public void setHeader(String header) {
			this.header = header;
		}

		public int getAllowedTimeToDisplay() {
			return allowedTimeToDisplay;
		}

		public void setAllowedTimeToDisplay(int allowedTimeToDisplay) {
			this.allowedTimeToDisplay = allowedTimeToDisplay;
		}
	}

	public List<AlbumPart> getAlbumPartList() {
		return albumPartList;
	}

	public void setAlbumPartList(List<AlbumPart> albumPartList) {
		this.albumPartList = albumPartList;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Album has total " + albumPartList.size() + " parts"+newLineString);
		int count = 0;
		for (AlbumPart ap : albumPartList) {
			sb.append("Album Part : " + count +newLineString);
			sb.append("Album Part Header : " + ap.getHeader() +newLineString);
			sb.append("Album Part AllowedTimeToDisplay : "
					+ ap.getAllowedTimeToDisplay() +newLineString);
			sb.append("Album Part images added : "
					+ ap.getImageURLList().size() +newLineString);
			int imgCount = 0;
			for (String url : ap.getImageURLList()) {
				sb.append("Album Part image added : " + imgCount + " : " + url
						+newLineString);
				imgCount++;
			}

		}

		return sb.toString();
	}

	public String getNewLineString() {
		return newLineString;
	}

	public void setNewLineString(String newLineString) {
		this.newLineString = newLineString;
	}

}
