package test.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FileRenamer {

	private static List<String> extentions = new ArrayList<String>();
	private static final String[] str = { ".mp3" };
	static {
		getExtentions();
	}

	private static void getExtentions() {
		for (String dd : str) {
			if (dd != null && !dd.trim().equalsIgnoreCase("")) {
				extentions.add(dd);
			}
		}
	}

	public static void main(String[] args) {
		removeSpecialCharacter(new File(
				"C:/Users/VINU/Desktop/comics/YudhkeBeej.pdf"));
		// removeSpecialCharacter("C:/Users/VINU/Desktop/comics/#016-Maut ka Trishool.pdf");

	}

	private static void removeSpecialCharacter(File file) {
		if (!file.exists()) {
			return;
		}
		if (file.isFile()) {
			try {
				removeSpecialCharacter(file.getAbsolutePath());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		File[] list = file.listFiles();
		if (list != null && list.length > 0) {
			for (File sub : list) {
				if (sub.exists()) {
					try {
						removeSpecialCharacter(sub.getAbsolutePath());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	private static void removeSpecialCharacter(String fileName)
			throws Exception {
		File f = new File(fileName);
		// //////////////// --- VALIDATION SECTION-----------
		// ////////////////////
		if (!f.exists()) {
			return;
		}
		if (f.isDirectory()) {
			removeSpecialCharacter(f);
		}
		int index = f.getName().lastIndexOf('.');
		if (index <= 0) {
			return;
		}
		String extention = f.getName().substring(index);
		System.out.println(extention);
		boolean validExtn = false;
		for (String str : extentions) {
			if (extention != null && !extention.trim().equalsIgnoreCase("")) {
				if (str.equalsIgnoreCase(extention.trim())) {
					validExtn = true;
				}
			}
		}
		if (!validExtn) {
			System.out.println(extention
					+ " is not valid extention . Valid Extentions are : "
					+ extentions);
			return;
		}

		// /-------------------------------------------------------------------------////

		char oldName[] = f.getName().substring(0, f.getName().lastIndexOf('.'))
				.toCharArray();
		String newName = "";
		boolean hadSpecialCharacter = false;
		for (int i = 0; i < oldName.length; i++) {
			char ch = oldName[i];
			if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
				newName = newName + ch;
			} else {
				hadSpecialCharacter = true;
			}
		}
		if (hadSpecialCharacter) {// if the old name doen't contain any special
									// character then there is no nay need to
									// rename the file.
			File n = new File(f.getParentFile().getAbsolutePath()
					+ File.separator + newName
					+ f.getName().substring(f.getName().lastIndexOf('.')));
			int counter = 1;
			while (n.exists()) {
				newName = newName + (counter++);
				File nn = new File(f.getParentFile().getAbsolutePath()
						+ File.separator + newName
						+ f.getName().substring(f.getName().lastIndexOf('.')));
				n = nn;
			}

			System.out.println(f.getAbsolutePath() + "\t\t\t\t\t\t\t\t"
					+ n.getAbsolutePath());

			// f.renameTo(n);
		}

	}

}
