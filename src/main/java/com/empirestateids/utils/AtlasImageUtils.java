package com.empirestateids.utils;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import net.coobird.thumbnailator.Thumbnails;
import net.ipowerlift.atlas.common.IConstants;
import net.ipowerlift.atlas.utls.UtilityMethods;

/**
 * Utils to operate on images.
 */
public class AtlasImageUtils {

	static Logger logger = Logger.getLogger(AtlasImageUtils.class);
	
	/**
	 * allowed image extensions.
	 */
	public static final String[] ALLOWED_EXT = { "gif", "GIF", "jpeg", "JPEG", "jpg", "JPG", "png", "PNG",
		"psd", "PSD", "bmp", "BMP", "tiff", "TIFF", "tif", "TIF", "swc", "SWC", "jpc", "JPC", "jp2", "JP2", "jpx", "JPX", "jb2", "JB2",
		"xbm", "XBM", "wbmp", "WBMP" };
	
	public static final String[] ALLOWED_PROFILE_EXT = { "jpeg", "jpg", "png", "gif"};
		
	/**
	 * Uploads image and if the image size is larger than maximum allowed,
	 *  it resizes the image to specified height, width and quality
	 *
	 * @param sourceImage
	 *            orginal image file.
	 * @param destFile
	 *            file to write to
	 * @param width
	 *            requested width
	 * @param height
	 *            requested height
	 * @param quality
	 *            requested destenation file quality
	 * @param aspectRatio
	 *            retail aspect Ratio of image
	 * @throws IOException
	 *             when error occurs.
	 */
	public static void writeOptimizedImage(final File sourceImage, final File destFile, final int width, final int height, final float quality, final boolean aspectRatio) throws IOException{
		BufferedImage image = ImageIO.read(sourceImage);
		
		if (image == null) {
			throw new IOException("Wrong file");
		}
		
		Dimension dimension = new Dimension();
		
		if(image.getHeight() > height){
			dimension.height = height;
		} else {
			dimension.height = image.getHeight();
		}
		
		if(image.getWidth() > width){
			dimension.width = width;
		} else {
			dimension.width = image.getWidth();
		}
		
		resizeImage(image, dimension.width, dimension.height, quality, destFile, aspectRatio);
	}
	
	
	public static void writeOptimizedImageWithAspectRatio(final File sourceImage, final File destFile, final int width, final int height, final float quality, final boolean aspectRatio) throws IOException{
		BufferedImage image = ImageIO.read(sourceImage);
		
		if (image == null) {
			throw new IOException("Wrong file");
		}
		
		int original_width = image.getWidth();
	    int original_height = image.getHeight();
	    int bound_width = width;
	    int bound_height = height;
	    int new_width = original_width;
	    int new_height = original_height;
	    
	    // first check if we need to scale width
	    if (original_width > bound_width) {
	        //scale width to fit
	        new_width = bound_width;
	        //scale height to maintain aspect ratio
	        new_height = (new_width * original_height) / original_width;
	    }

	    // then check if we need to scale even with the new height
	    if (new_height > bound_height) {
	        //scale height to fit instead
	        new_height = bound_height;
	        //scale width to maintain aspect ratio
	        new_width = (new_height * original_width) / original_height;
	    }
	    
	    
		Dimension dimension = new Dimension();
		dimension.width = new_width;
		dimension.height = new_height;

		resizeImage(image, dimension.width, dimension.height, quality,
				destFile, aspectRatio);
	}
	/**
	 * Resizes and Uploads image to the specified height, width and quality
	 *
	 * @param sourceImage
	 *            orginal image file.
	 * @param destFile
	 *            file to write to
	 * @param width
	 *            requested width
	 * @param height
	 *            requested height
	 * @param quality
	 *            requested destenation file quality
	 * @param aspectRatio
	 *            retail aspect Ratio of image
	 * @throws IOException
	 *             when error occurs.
	 */
	public static void writeOptimizedThumbImage(final File sourceImage, final File destFile, final int width, final int height, final float quality, final boolean aspectRatio) throws IOException{
		BufferedImage image = ImageIO.read(sourceImage);
		
		if (image == null) {
			throw new IOException("Wrong file");
		}
		
		resizeImage(image, width, height, quality, destFile, aspectRatio);
	}
	
	/**
	 * Resizes the image and writes it to the disk.
	 *
	 * @param sourceImage
	 *            orginal image file.
	 * @param width
	 *            requested width
	 * @param height
	 *            requested height
	 * @param quality
	 *            requested destenation file quality
	 * @param destFile
	 *            file to write to
	 * @param aspectRatio
	 *            retail aspect Ratio of image
	 * @throws IOException
	 *             when error occurs.
	 */
	private static void resizeImage(final BufferedImage sourceImage, final int width,
									final int height, final float quality,
									final File destFile, final boolean aspectRatio) throws IOException {
		try {
			Thumbnails.of(sourceImage).size(width, height).rotate(0).keepAspectRatio(aspectRatio)
			.outputQuality(quality).toFile(destFile);
			// for some special files outputQuality couses error:
			//IllegalStateException inner Thumbnailator jar. When exception is thrown
			// image is resized without quality
			// When http://code.google.com/p/thumbnailator/issues/detail?id=9 
			// will be fixed this try catch can be deleted. Only:
			//Thumbnails.of(sourceImage).size(width, height).keepAspectRatio(false)
			//	.outputQuality(quality).toFile(destFile);
			// should remain.
		} catch (IllegalStateException e) {
			Thumbnails.of(sourceImage).size(width, height).rotate(0).keepAspectRatio(aspectRatio)
						.toFile(destFile);
		}
	}
	
	/**
	 * checks if file is image.
	 *
	 * @param file
	 *            file to check
	 * @return true if file is image.
	 */
	public static boolean isImage(final File file) {
		List<String> list = Arrays.asList(ALLOWED_EXT);
		String fileExt = null;
		if (file != null) {
			fileExt = FilenameUtils.getExtension(file.getName().toLowerCase());
			return (fileExt != null) ? list.contains(fileExt) : false;
		} else {
			return false;
		}
	}

	/**
	 * checks if file is image.
	 *
	 * @param file
	 *            file to check
	 * @return true if file is image.
	 */
	public static boolean isProfileImage(final File file) {
		List<String> list = Arrays.asList(ALLOWED_PROFILE_EXT);
		String fileExt = null;
		if (file != null) {
			fileExt = FilenameUtils.getExtension(file.getName().toLowerCase());
			return (fileExt != null) ? list.contains(fileExt) : false;
		} else {
			return false;
		}
	}


	/**
	 * checks if fileName is image.
	 *
	 * @param fileName
	 *            fileName to check
	 * @return true if fileName is image.
	 */
	public static boolean isProfileImage(final String fileName) {
		List<String> list = Arrays.asList(ALLOWED_PROFILE_EXT);
		String fileExt = null;
		if (fileName != null) {
			fileExt = FilenameUtils.getExtension(fileName.toLowerCase());
			return (fileExt != null) ? list.contains(fileExt) : false;
		} else {
			return false;
		}
	}
	
	/**
	 * checks if image file is image.
	 *
	 * @param file
	 *            file to check
	 * @return true if file is image.
	 */
	public static boolean checkImageFile(final File file) {
		BufferedImage bi;
		FileInputStream fileIS = null;
		try {
			fileIS = new FileInputStream(file);
			bi = ImageIO.read(fileIS);
		} catch (IOException e) {
			return false;
		} finally {
			if (fileIS != null){
				try { fileIS.close(); } catch (Exception e) {}
			}
		}
		return (bi != null);
	}


	/**
	 * checks if image inputstream is image.
	 *
	 * @param fileIS
	 *            image file inputstream
	 * @return true if file inputstream is image.
	 */
	public static boolean checkImageFile( InputStream fileIS) {
		BufferedImage bi;
		try {
			bi = ImageIO.read(fileIS);
		} catch (IOException e) {
			logger.error("IOException:"+e.getMessage()+UtilityMethods.getStackTrace(e));
			return false;
		} finally {
			if (fileIS != null){
				try { fileIS.close(); } catch (Exception e) {}
			}
		}
		return (bi != null);
	}

	/**
	 * checks if image inputstream is image.
	 *
	 * @param fileIS
	 *            image file inputstream
	 * @return true if file inputstream is image.
	 */
	public static boolean rotateImageFile(File imgFile, int degreesRight) {
		BufferedImage bi;
		int width = IConstants.DEFAULT_IMG_WIDTH;
		int height = IConstants.DEFAULT_IMG_HEIGHT;
		float quality = IConstants.DEFAULT_IMG_QUALITY;
		try {
			bi = ImageIO.read(imgFile);
			Thumbnails.of(bi).outputQuality(quality).size(width, height).rotate(degreesRight).toFile(imgFile);
			bi = ImageIO.read(imgFile);
			
		} catch (IOException e) {
			logger.error("IOException:"+e.getMessage()+UtilityMethods.getStackTrace(e));
			return false;
		} 
		return (bi != null);
	}	
}
