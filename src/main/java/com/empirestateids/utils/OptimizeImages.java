package com.empirestateids.utils;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import net.ipowerlift.atlas.common.IConstants;

public class OptimizeImages {

	public static final String[] ALLOWED_EXT = { "gif", "GIF", "jpeg", "JPEG", "jpg", "JPG", "png", "PNG",
		"psd", "PSD", "bmp", "BMP", "tiff", "TIFF", "tif", "TIF", "swc", "SWC", "jpc", "JPC", "jp2", "JP2", "jpx", "JPX", "jb2", "JB2",
		"xbm", "XBM", "wbmp", "WBMP" };
	
	private static void dirlist(String fname, int deep) {
		File dir = new File(fname);
		String[] chld = dir.list();
		if (dir.isFile()) {
			System.out.println("" + dir.getName());
			return;

		} else if (dir.isDirectory()) {
			System.out.println(fname.substring(fname.lastIndexOf("\\")));
			for (int i = 0; i < chld.length; i++) {
				dirlist(fname + "\\" + chld[i], i);
			}
		}
	}

	public static String getThumbnailURL(String path){
    	if(path==null || path.trim().equals("")){
    		return path;
    	}
    	
    	StringBuffer urlBufferPath = null;
    	String urlPath = path;
    	
    	try{
    		urlBufferPath = new StringBuffer(path);
    		urlBufferPath.insert(urlBufferPath.lastIndexOf("\\")+1,IConstants.DIRECTORY_THUMBNAIL+"\\");
    		urlPath = urlBufferPath.toString();
    	} catch(Exception e){
    		urlPath = path;
    	}
    	
    	return urlPath;
    }
	
	/**
	 * @param args
	 */
	
	public static void main1(String[] args) {
		File dir = null;
		File imageFile = null;
		File thumbFile = null;
		//File optimizeFile = null;
		
		for(int i=41; i<=96; i++){
			//dir = new File("F:/profiles/10000"+i);
			if (dir.isDirectory()) {
				System.out.println("Dir: " + dir.getAbsolutePath());
				Iterator<File> it = org.apache.commons.io.FileUtils.iterateFiles(dir, ALLOWED_EXT, true);
				imageFile = null;
				//optimizeFile = null;
				thumbFile = null;
				
				while(it.hasNext()){
					try {
					imageFile = it.next();
					//System.out.println("**"+imageFile.getCanonicalPath());
					if(imageFile.getAbsolutePath().contains(IConstants.DIRECTORY_ORIGINAL_IMAGE)){
						System.out.println("**"+imageFile.getAbsolutePath());
						continue;
					}
					
		            	//.getName()
		            
		            if (!AtlasImageUtils.isImage(imageFile) || !AtlasImageUtils.checkImageFile(imageFile)){
		            	//ipnthatprofile
		            	System.out.println("##"+imageFile.getAbsolutePath());
		            	continue;
		            }
		            

	            	thumbFile = new File(getThumbnailURL(imageFile.getAbsolutePath()));
	            	//optimizeFile = imageFile;
		            
		            	//System.out.println("Origi file: " + imageFile.getAbsolutePath());
		            	//System.out.println("thumb file: " + thumbFile.getAbsolutePath());
		            	
						AtlasImageUtils.writeOptimizedThumbImage(imageFile, thumbFile, IConstants.DEFAULT_THUMB_MAX_WIDTH, IConstants.DEFAULT_THUMB_MAX_HEIGHT, IConstants.DEFAULT_THUMB_IMG_QUALITY, true);
						System.out.println(" > Converted thumb: " + thumbFile.getAbsolutePath());
						
						AtlasImageUtils.writeOptimizedImage(imageFile, imageFile, IConstants.DEFAULT_IMG_WIDTH, IConstants.DEFAULT_IMG_HEIGHT, IConstants.DEFAULT_IMG_QUALITY, true);
						System.out.println(" > Converted optimized: " + imageFile.getAbsolutePath());
		            } catch (Exception e) {
						System.err.println("Error creating resized image for : " + imageFile.getAbsolutePath() + "; Error Message: " + e.getMessage());
//						try {
//							FileUtils.copyFile(new File("C:/resources/images/ipnthatprofile.jpg"), imageFile);
//						} catch (IOException e1) {
//							System.err.println("Error copying files : " + imageFile.getAbsolutePath() + "; Error Message: " + e.getMessage());
//							
//						}
					}
				}
				
			}
		}
		
	}

	public static void main2(String[] args) {
		Iterator<File> it = org.apache.commons.io.FileUtils.iterateFiles(new File("F:/images/"), ALLOWED_EXT, true);
		int counter = 0;
		File imageFile = null;
		//File thumbFile = null;
		while(it.hasNext()){
			imageFile = it.next();
            //System.out.println(imageFile.getAbsoluteFile());	//.getName()
            
            if (!AtlasImageUtils.isImage(imageFile) || !AtlasImageUtils.checkImageFile(imageFile)) continue;
            try {
            	//thumbFile = new File(getThumbnailURL(imageFile.getAbsolutePath()));
            	imageFile.getCanonicalFile();
            	//System.out.println("Converted file: " + imageFile.getAbsolutePath());
            	
            	AtlasImageUtils.writeOptimizedImage(imageFile, imageFile, IConstants.DEFAULT_IMG_WIDTH, IConstants.DEFAULT_IMG_HEIGHT, IConstants.DEFAULT_THUMB_IMG_QUALITY, true);
				System.out.println(" > Converted optimized: " + imageFile.getAbsolutePath());
				
            } catch (IOException e) {
				System.err.println("Error creating resized image for : " + imageFile.getAbsolutePath() + "; Error Message: " + e.getMessage());
			}
            
            counter++;
        }
		
		System.out.println("no of files: " + counter);
	}

}
