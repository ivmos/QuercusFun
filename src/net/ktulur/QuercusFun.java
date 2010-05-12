package net.ktulur;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.caucho.*;
import com.caucho.quercus.Quercus;
import com.caucho.quercus.env.Env;
import com.caucho.quercus.env.Value;
import com.caucho.quercus.lib.ApacheModule;
import com.caucho.quercus.lib.ApcModule;
import com.caucho.quercus.lib.ArrayModule;
import com.caucho.quercus.lib.BcmathModule;
import com.caucho.quercus.lib.ClassesModule;
import com.caucho.quercus.lib.CtypeModule;
import com.caucho.quercus.lib.ErrorModule;
import com.caucho.quercus.lib.ExifModule;
import com.caucho.quercus.lib.FunctionModule;
import com.caucho.quercus.lib.HashModule;
import com.caucho.quercus.lib.HtmlModule;
import com.caucho.quercus.lib.HttpModule;
import com.caucho.quercus.lib.ImageModule;
import com.caucho.quercus.lib.JavaModule;
import com.caucho.quercus.lib.MathModule;
import com.caucho.quercus.lib.MhashModule;
import com.caucho.quercus.lib.MiscModule;
import com.caucho.quercus.lib.NetworkModule;
import com.caucho.quercus.lib.OptionsModule;
import com.caucho.quercus.lib.OutputModule;
import com.caucho.quercus.lib.TokenModule;
import com.caucho.quercus.lib.UrlModule;
import com.caucho.quercus.lib.VariableModule;
import com.caucho.quercus.lib.regexp.RegexpModule;
import com.caucho.quercus.lib.string.StringModule;
import com.caucho.quercus.module.ModuleInfo;
import com.caucho.quercus.module.QuercusModule;
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
		
		quercus.addModule(new StringModule());
		quercus.addModule(new VariableModule() );
		quercus.addModule(new MiscModule() );
		quercus.addModule(new NetworkModule() );
		quercus.addModule(new OptionsModule() );
		quercus.addModule(new OutputModule() );
		quercus.addModule(new TokenModule() );
		quercus.addModule(new UrlModule() );
		quercus.addModule(new ExifModule() );
		quercus.addModule(new FunctionModule() );
		quercus.addModule(new HashModule() );
		quercus.addModule(new HtmlModule() );
		quercus.addModule(new HttpModule() );
		quercus.addModule(new ImageModule() );
		quercus.addModule(new JavaModule() );
		quercus.addModule(new MathModule() );
		quercus.addModule(new MhashModule() );
		quercus.addModule(new ApacheModule() );
		quercus.addModule(new ApcModule() );
		quercus.addModule(new ArrayModule() );
		quercus.addModule(new BcmathModule() );
		quercus.addModule(new ClassesModule() );
		quercus.addModule(new CtypeModule() );
		quercus.addModule(new ErrorModule() );
		quercus.addModule(new RegexpModule());
		/*
		Collection<ModuleInfo> lista = quercus.getModules();
		Iterator<ModuleInfo> ite = lista.iterator();
		while(ite.hasNext()) {
			System.out.println(ite.next().getName());
		}
		*/
	}
	
	public void processFile(String filename) {
		FilePath path = new FilePath(filename);
		QuercusPage qp;
		try {
			qp = quercus.parse(path);
			WriteStream out = new WriteStream(StdoutStream.create());
			Env env = quercus.createEnv(qp, out, null, null);
			env.setTimeLimit(-1);
			Value res = qp.executeTop(env);
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String process(String input) {
		try {
			

			QuercusPage qp = quercus.parse(StringStream.open(input));

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
