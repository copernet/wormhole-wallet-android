package core.util;

import android.os.Environment;

import com.blankj.utilcode.util.FileIOUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.PathUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;


public class Files {
	public static final String path = "/whc_wallet1/";
	
	public static String getDefaultPath() throws IOException{
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED);//判断sd卡是否存在
        if(sdCardExist)
        {
            sdDir = Environment.getExternalStorageDirectory();//获取跟目录
        }
        return PathUtils.getInternalAppDataPath() + path;
//        String appPath = "file:///"+getDefaultRoot()+path;
//		FileConnection con = (FileConnection) Connector.open(appPath, Connector.READ_WRITE);
//		if(!con.isDirectory())
//			con.mkdir();
//		return appPath;
	}
	
	public static byte[] readBytes(RandomAccessFile randomAccessFile, int skip, int bytes) {
		byte[] buffer = new byte[bytes];
		try {
			randomAccessFile.seek(skip);
			randomAccessFile.read(buffer);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return buffer;
	}
	
	public static byte[] read(String path) throws IOException {
		byte[] fileContent = FileIOUtils.readFile2BytesByStream(path);
//			FileConnection fc = (FileConnection) Connector.open(getDefaultPath()+path, Connector.READ_WRITE);
//			if(!fc.exists()) {
//				fc.close();
//				throw new IOException("File does not exist on "+path);
//			}
//			byte[] fileContent = Util.readFromStream(fc.openDataInputStream());
//			fc.close();
			return fileContent;
	}

	public static String delete(String path) {

			File file = new File(path);
			file.delete();

		return null;
	}

	public static void write(Object data, String path) {
		FileIOUtils.writeFileFromBytesByStream(path, data.toString().getBytes());
//		try {
//			FileConnection con = (FileConnection) Connector.open(getDefaultPath()+path, Connector.READ_WRITE);
//			if(con.exists()) {
//				con.delete();
//			}
//			con.create();
//			DataOutputStream out = con.openDataOutputStream();
//			PrintStream output = new PrintStream(out);
//			output.print(data);
//			output.close();
//			out.close();
//			con.close();
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	public static void write(byte[] data, String path) {
		FileIOUtils.writeFileFromBytesByStream(path, data);
		
//		try {
//			FileConnection con = (FileConnection) Connector.open(getDefaultPath()+path, Connector.READ_WRITE);
//
//			if(con.exists()) {
//				con.delete();
//			}
//			con.create();
//			DataOutputStream out = con.openDataOutputStream();
//			out.write(data);
//			out.close();
//			con.close();
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	/*public static OutputStream write(String path) {
		try {
			FileConnection con = (FileConnection) Connector.open(getDefaultPath()+path, Connector.READ_WRITE);
			if(!con.exists())
				con.create();
			DataOutputStream out = con.openDataOutputStream();
			return out;
		}catch (IOException e) {
			e.printStackTrace();
		} 
		return null;
	}*/
	
	public static void download(InputStream inputStream, String file) throws IOException{
		FileIOUtils.writeFileFromIS(file, inputStream);
//		FileConnection con = (FileConnection) Connector.open(Files.getDefaultPath()+file, Connector.READ_WRITE);
//		if(!con.exists()) {
//			con.create();
//		}
//		DataOutputStream out = con.openDataOutputStream();
//		int length;
//		byte[] buffer = new byte[1024];
//		while ((length = inputStream.read(buffer)) != -1) {
//			out.write(buffer, 0, length);
//		}
//		out.close();
//		con.close();
//		inputStream.close();
	}
	
	public static boolean isExist(String path) {
		File file = new File(path);
		return file.exists();
//		boolean exist = false;
//		try {
//			FileConnection con = (FileConnection) Connector.open(getDefaultPath()+path, Connector.READ_WRITE);
//			exist = con.exists();
//			con.close();
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
//		return exist;
	}
	
	public static long filesize(String path) {
		File file = new File(path);
		return file.length();
//		long size = 0 ;
//		try {
//			FileConnection con = (FileConnection) Connector.open(getDefaultPath()+path, Connector.READ_WRITE);
//			if(con.exists()) {
//				size = con.fileSize();
//			}
//			con.close();
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
//		return size;
	}
	//TODO - Need some more testing
	public static void append(Object data, String path) {
		FileIOUtils.writeFileFromBytesByStream(path, data.toString().getBytes(), true);
//		try {
//			FileConnection con = (FileConnection) Connector.open(getDefaultPath()+path, Connector.READ_WRITE);
//			if(!con.exists())
//				con.create();
//			OutputStream out = con.openOutputStream(Long.MAX_VALUE);
//			PrintStream output = new PrintStream(out);
//			output.print(data);
//			output.close();
//			con.close();
//		}catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
//	public static String getDefaultRoot() throws IOException {
//		Enumeration roots = FileSystemRegistry.listRoots();
//		if(roots.hasMoreElements()) {
//			return roots.nextElement().toString();
//		}
//		else
//			throw new IOException("You don't have permission");
//	}
	
	public static boolean getOrCreateDir(String dir) throws IOException{
		File file = new File(dir);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
		return true;
	}
	public static List<File> listDir(String dir) throws IOException{
        List<File> files = FileUtils.listFilesInDir(dir);
        return files;
//		FileConnection con = (FileConnection) ColorSpace.Connector.open(getDefaultPath()+dir, ColorSpace.Connector.READ_WRITE);
//		if(con.isDirectory())
//			return con.list();
//		else
//			return null;
	}
//	public static Enumeration listFile(String dir) throws IOException{
//		FileConnection con = (FileConnection) Connector.open(getDefaultPath()+dir, Connector.READ_WRITE);
//		if(!con.isDirectory())
//			return con.list();
//		else
//			return null;
//	}
	public static Vector listFilterDir(String dir,String filterDir) throws IOException{
		List<File> dirList = listDir(dir);
		Vector vector = new Vector();
		if (null == dirList) {
		    return vector;
        }
		Iterator<File> iterable = dirList.iterator();
		while(iterable.hasNext()){
			String directory = iterable.next().getName();
			if(directory.startsWith(filterDir)) {
				vector.addElement(directory);
			}
		}
		return vector;
	}

	public static void rename(String old_path, String path) throws IOException {
		File file = new File(old_path);
		File newFile = new File(path);
		file.renameTo(newFile);
	}
}
