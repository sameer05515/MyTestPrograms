package com.example.demo.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class SampleFileRunner {

	public static void run(String path, String... args) {
		if (path != null && new File(path).exists()) {
			String[] arr=new String[args.length+1];
			arr[0]=path;
			for(int i=1;i<arr.length;i++) {
				arr[i]=args[i-1];
			}
			
			ProcessBuilder processBuilder = new ProcessBuilder(arr);
			try {

				Process process = processBuilder.start();

				StringBuilder output = new StringBuilder();

				BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

				String line;
				while ((line = reader.readLine()) != null) {
					output.append(line + "\n");
				}

				int exitVal = process.waitFor();
				if (exitVal == 0) {
					System.out.println(output);
					System.exit(0);
				} else {
					// abnormal...
				}

			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
