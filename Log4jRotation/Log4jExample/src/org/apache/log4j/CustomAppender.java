package org.apache.log4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.helpers.LogLog;

public class CustomAppender extends DailyRollingFileAppender {

	private int maxBackupIndex;

	public void setMaxBackupIndex(int maxBackupIndex) {
		this.maxBackupIndex = maxBackupIndex;
	}

	@Override
	public void rollOver() throws IOException {
		super.rollOver();
		File file = new File(fileName);
		List<String> files = new ArrayList<>();
		File[] dirFiles = new File(file.getAbsolutePath()).getParentFile().listFiles();
		if (dirFiles != null && dirFiles.length > 0) {
			Arrays.sort(dirFiles, (a, b) -> Long.compare(a.lastModified(), b.lastModified()));
			for (File allFile : dirFiles) {
				if (allFile.getName().contains(fileName)) {
					files.add(allFile.getAbsolutePath());
				}
			}
		}
		if (files.size() > maxBackupIndex+1) {
			File deleteFile = new File(files.get(0));
			LogLog.debug("delete result for"+deleteFile.getAbsolutePath()+" is "+deleteFile.delete());
			files.remove(0);
		}

	}
}
