package board;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class BobTheBoardBuilder {
	public void saveString(String s,String location) throws IOException
	{
		File f = new File(location);
		FileWriter writer = new FileWriter(f);
		writer.write(s);
		
	}
	public String save(Board board)
	{
		
	}
	public Board build(String board)
	{
		
	}
}
