package net.ktulur;



import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.caucho.*;
import com.caucho.quercus.Quercus;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.page.QuercusPage;
import com.caucho.quercus.program.QuercusProgram;
import com.caucho.vfs.FilePath;
import com.caucho.vfs.ReadStream;
import com.caucho.vfs.StdoutStream;
import com.caucho.vfs.StreamImpl;
import com.caucho.vfs.StreamImplOutputStream;
import com.caucho.vfs.StringReader;
import com.caucho.vfs.StringStream;
import com.caucho.vfs.WriteStream;

public class QuercusFun /*extends StreamImpl*/{
	private Quercus quercus;
	private ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
	public QuercusFun() {
		
		quercus = new Quercus();
		quercus.init();
		quercus.setCompile(true);
	}
	
	public String process(String filename) {
		try {
			//String code = "$arr = array_fill(0,10, \"quercus\"); var_dump($arr);";
			ReadStream rs = new ReadStream();
			FilePath path = new FilePath(filename);
			//rs.read(code.toCharArray(), code.length());
			
			QuercusPage qp = quercus.parse(path);	
//			
			
 			//WriteStream out = new WriteStream(StdoutStream.create());
			String salida = "";
			DirectStream directS = new DirectStream(salida);
			WriteStream out = new WriteStream(directS);
			Env env = quercus.createEnv(qp, out, null, null);
			env.setTimeLimit(-1);
			Value res = qp.executeTop(env);
			//return new String(out.getBuffer(), out.getBufferOffset());
			//return new String(out.getBuffer());
			
			out.flush();
			//out.seekEnd(out.getBufferSize());
			//System.out.println(directS.get_string());
			return directS.getInternalOut();
			
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Ha habido un error";
		
		
	}
	
	public static void main(String[] args) {
        
        
    
	}


	


}
