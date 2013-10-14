/*
 * 
 */
package redisui.connector.command;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Arrays;
import redisui.connector.Response;

/**
 *
 * @author marat
 */
public abstract class CommandBased {
    
    protected Socket s;
    
    public Socket getS() {
        return s;
    }
    
    public void setS(Socket s) {
        this.s = s;
    }
    
    public abstract Response exec(String[] params) throws Exception;
    
    protected Response interrupt(String in) throws Exception {
        InputStream is = s.getInputStream();
        OutputStream os = s.getOutputStream();
        os.write(in.getBytes("UTF-8"));
        os.flush();
        // читаем ответ
        byte buf[] = new byte[256 * 1024];
        int c = is.read(buf);        
        if (c < 1) {
            throw new Exception("zero response");
        }
        return new Response(new String(buf, 0, 1), Arrays.copyOfRange(buf, 1, c));
    }
}
