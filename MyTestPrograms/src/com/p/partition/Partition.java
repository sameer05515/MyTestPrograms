package com.p.partition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import javax.swing.filechooser.FileSystemView;

public class Partition {
	
	private static PrintStream ps;

	public static void main(String[] args) throws FileNotFoundException {
		ps=new PrintStream(new File("C:/Users/Lenovo/Desktop/p.txt"));
		File[] paths;
		FileSystemView fsv = FileSystemView.getFileSystemView();

		// returns pathnames for files and directory
		paths = File.listRoots();

		// for each pathname in pathname array
		for(File path:paths)
		{
		    // prints file and directory paths
		    ps.println("\n#################\nDrive Name: "+path);
		    getFileNames(path);
//		    File[] f=path.listFiles();
//		    for(File ff:f){
//		    	ps.println(ff.getAbsolutePath());
//		    }
		    ps.println("Description: "+fsv.getSystemTypeDescription(path));
		}
		
		Runnable r = () -> System.out.println("Hello from thread");
		Thread t = new Thread(r);
		t.start();


	}
	
	// Get files from the user computer
	public static void getFileNames(File folder) {

	    // retrieve file listing
	    File[] fileList = folder.listFiles();

	    if (fileList == null) {
	        // throw an exception, return or do any other error handling here
	    	ps.println("No files found in "+folder);
	        return;
	    }

	    // path is correct
	    for (final File file : fileList ) {
	    	if(file.isDirectory())getFileNames(file);
	    	ps.println(file.getAbsolutePath());
	    }
	}

}
