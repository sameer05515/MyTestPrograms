package com.p.pers.smbg;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;

public class WatchServiceTempTxtFile {

	private final WatchService watcher;
    private final Map<WatchKey, Path> keys;
    
    
    
	public WatchServiceTempTxtFile(Path dir) throws IOException {
		super();
		this.watcher = FileSystems.getDefault().newWatchService();
        this.keys = new HashMap<WatchKey, Path>();
        registerDirectory(dir);
	}
	
	/**
     * Register the given directory with the WatchService; This function will be called by FileVisitor
     */
    private void registerDirectory(Path dir) throws IOException 
    {
        WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        keys.put(key, dir);
    }
    
    /**
     * Process all events for keys queued to the watcher
     */
    void processEvents() {
        for (;;) {
 
            // wait for key to be signalled
            WatchKey key;
            try {
                key = watcher.take();
            } catch (InterruptedException x) {
                return;
            }
 
            Path dir = keys.get(key);
            if (dir == null) {
                System.err.println("WatchKey not recognized!!");
                continue;
            }
 
            for (WatchEvent<?> event : key.pollEvents()) {
                @SuppressWarnings("rawtypes")
                WatchEvent.Kind kind = event.kind();
 
                // Context for directory entry event is the file name of entry
                @SuppressWarnings("unchecked")
                Path name = ((WatchEvent<Path>)event).context();
                Path child = dir.resolve(name);
 
                // print out event
                System.out.format("%s: %s\n", event.kind().name(), child);
 
                // if directory is created, and watching recursively, then register it and its sub-directories
//                if (kind == ENTRY_CREATE) {
//                    try {
//                        if (Files.isDirectory(child)) {
//                            walkAndRegisterDirectories(child);
//                        }
//                    } catch (IOException x) {
//                        // do something useful
//                    }
//                }
                if (kind == ENTRY_MODIFY) {
                	//System.out.println("File");
                	TestJsonObjectTokenTraversal.main(new String[] {""});
                }
                
            }
 
            // reset key and remove from set if directory no longer accessible
            boolean valid = key.reset();
            if (!valid) {
                keys.remove(key);
 
                // all directories are inaccessible
                if (keys.isEmpty()) {
                    break;
                }
            }
        }
    }



	public static void main(String[] args) {
		try {
			Path dir = Paths.get("D:\\Prem\\GIT-PROJ\\file-bckp\\smbg\\verseDetails");
			new WatchServiceTempTxtFile(dir).processEvents();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}

}
