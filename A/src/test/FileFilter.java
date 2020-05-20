package test;

import java.io.File;

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
	
	public static File renameToIng(File file, String path) {
		String ingFileName = encFileName(file.getName());
		file.renameTo(new File(path + ingFileName));
		File newFile = new File(path + ingFileName);
		return newFile;
	}
//	public static void main(String[] args) {
//		
//		File f = renameToIng(new File("c:/myFolder/win64_11gR2_database_1of2.zip"), "c:/myFolder/");
//		System.out.println(f.getName()+" : "+f.exists());
//		
//	}
	
	public static File renameToOri(File file, String path) {
		String oriFileName = decFileName(file.getName());
		file.renameTo(new File(path + oriFileName));
		File oriFile = new File(path + oriFileName);
		return oriFile;
	}
}
