package test;


public class FileFilter {
	public FileFilter() {}
	
	public static String encFileName(String oriFileName) {
		int pos = oriFileName.lastIndexOf(".");
		String fileName = oriFileName.substring(0, pos);
		String ingFileName = fileName + "_ing";
		
		return ingFileName + getExtension(oriFileName);
	}
	
	public static String decFileName(String ingFileName) {
		int pos = ingFileName.lastIndexOf(".");
		String oriFileName = ingFileName.substring(0, pos - 4);
		return oriFileName + getExtension(ingFileName);
	}
	
	
	public static String getExtension(String fileName) {
		int post = fileName.lastIndexOf( "." );
		String extension =fileName.substring( post );
		
		return extension;
	}
}
