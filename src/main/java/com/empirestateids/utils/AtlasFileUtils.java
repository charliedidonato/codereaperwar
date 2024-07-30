/**
 * 
 */
package com.empirestateids.utils;

import org.apache.commons.io.FilenameUtils;

/**
 * @author Syed
 *
 */
public class AtlasFileUtils {

	/**
	 * checks if fileName is image.
	 *
	 * @param fileName
	 *            fileName to check
	 * @return true if fileName is pdf.
	 */
	public static boolean isPDF(final String fileName) {
		String fileExt = null;
		if (fileName != null) {
			fileExt = FilenameUtils.getExtension(fileName.toLowerCase());
			return (fileExt != null) ? "pdf".equals(fileExt) : false;
		} else {
			return false; 
		}
	}

}
