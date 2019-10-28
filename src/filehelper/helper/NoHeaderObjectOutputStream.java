package filehelper.helper;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class NoHeaderObjectOutputStream extends ObjectOutputStream{

	public NoHeaderObjectOutputStream(OutputStream out) throws IOException {
		super(out);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void writeStreamHeader() throws IOException{
	// do not write a header
		return;
	}

}
