import java.io.*; 
import java.util.*; 

class FileSplit{
	public static void splitFile(File f) throws IOException{
		int partCounter=1;//name parts of chunks
		
		byte[] buffer=new byte[1024*512];
		String fileName=f.getName();
		
		try(FileInputStream fis=new FileInputStream(f);
		BufferedInputStream bis=new BufferedInputStream(fis)){
			
			int bytesAmount=0;
			while((bytesAmount=bis.read(buffer))>0){
				//write each chunk of data into seperate filename
				String filePartName = String.format("%s.%03d+.mp3", fileName, partCounter++);
                File newFile = new File(f.getParent(), filePartName);
                try (FileOutputStream out = new FileOutputStream(newFile)) {
                    out.write(buffer, 0, bytesAmount);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        splitFile(new File("Rush.mp3"));
    }
}