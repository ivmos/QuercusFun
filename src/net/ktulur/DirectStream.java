package net.ktulur;

import java.io.IOException;

import com.caucho.vfs.Path;
import com.caucho.vfs.ReadStream;
import com.caucho.vfs.StreamImpl;
import com.caucho.vfs.StringPath;
import com.caucho.vfs.StringStream;

public class DirectStream extends StreamImpl {
	  private String _string;
	  private String internalOut;
	  public String getInternalOut() {
		return internalOut;
	}

	public String get_string() {
		return _string;
	}

	private int _length;
	  private int _index;

	  public DirectStream(String string)
	  {
	    // this.path = new NullPath("string");
	    _string = string;
	    _length = string.length();
	    _index = 0;
	  }

	  public static ReadStream open(String string)
	  {
	    DirectStream ss = new DirectStream(string);
	    return new ReadStream(ss);
	  }

	  public Path getPath() { return new StringPath(_string); }

	  public boolean canRead() { return true; }

	  // XXX: encoding issues
	  public int read(byte []buf, int offset, int length) throws IOException
	  {System.out.println("LALA");
	    int strlen = _length;

	    int start = offset;
	    int end = offset + length;

	    int index = _index;
	    for (; index < strlen && offset < end; index++) {
	      int ch = _string.charAt(index);

	      if (ch < 0x80)
	        buf[offset++] = (byte) ch;
	      else if (ch < 0x800 && offset + 1 < end) {
	        buf[offset++] = (byte) (0xc0 | (ch >> 6));
	        buf[offset++] = (byte) (0x80 | (ch & 0x3f));
	      }
	      else if (ch < 0x8000 && offset + 2 < end) {
	        buf[offset++] = (byte) (0xe0 | (ch >> 12));
	        buf[offset++] = (byte) (0x80 | ((ch >> 6) & 0x3f));
	        buf[offset++] = (byte) (0x80 | ((ch >> 6) & 0x3f));
	      }
	      else if (offset == start) {
	        throw new IllegalStateException("buffer length is not large enough to decode UTF-8 data");
	      }
	      else {
	        break;
	      }
	    }
	    
	    _index = index;

	    return start < offset ? offset - start : -1;
	  }
	  public void write(byte []buf, int offset, int length, boolean isEnd)
	    throws IOException
	  {
	    //System.out.write(buf, offset, length);
	    internalOut = new String(buf, offset, length);
	    //System.out.flush();
	  }


	  public int getAvailable() throws IOException
	  {
	    return _length - _index;
	  }
	}

