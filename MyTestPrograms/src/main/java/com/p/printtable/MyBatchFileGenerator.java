package com.p.printtable;

import java.util.Arrays;
import java.util.function.Consumer;

public class MyBatchFileGenerator {

	private static String[] folders = { "MyTestPrograms", "smrt-bkp", "file-bckp", "link-mgmt-mongodb-services",
			"angular-npm-projects", "word-meaning-mongodb-services", "react-projects",
			"bce-rest-services", "db-files", "interview-mgmt-rest", "nodejs-express-mysql-rest", "bce-gui",
			"j-trac-code-r1373-trunk", "vocab-khajana", "jtrac", "FileService", "task-mgmt-service-mysql-db-branch",
			"MP3Player2.5", "db-backup", "task-mgmt-service" };

	public static void main(String[] args) {
		System.out.println("#!/bin/bash\n" + 
				"\n" + 
				"@echo off\n" + 
				"\n" + 
				"clear\n" + 
				"\n" + 
				"CURRENT_DIR=$(pwd)\n"+
				"GIT_PROJ_DIR=/home/premendra/git\n");
		
		/**
		 * Commenting iteration of collection using old syntax
		 * */
//		System.out.println("cd /home/premendra/git/MyTestPrograms");
//		for (String folder : folders) {
//			System.out.println("\n\n#=============== \necho \"" + folder + "\"");
//			System.out.println("cd $GIT_PROJ_DIR/"+folder +" && git remote -v");
//
//		}
		
		/**
		 * Using new syntax for iteration using Consumer , and forEach method
		 * */
		Consumer<String> folderBashGenerator=folder->{
			System.out.println("\n\n#=============== \necho \"" + folder + "\"");
			System.out.println("cd $GIT_PROJ_DIR/"+folder +" && git remote -v");
		};
		
		//Arrays.asList(folders).forEach(folderBashGenerator);
		
		Arrays.asList(folders).stream().forEach(folderBashGenerator);
		
		System.out.println("\n\ncd $CURRENT_DIR");

	}

}
