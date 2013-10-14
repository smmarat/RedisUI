/*
 * 
 */
package redisui.connector.command;

import java.net.Socket;
import redisui.connector.Response;

/**
 *
 * @author marat
 */
public class Auth extends CommandBased {

    @Override
    public Response exec(String[] params) throws Exception {
        String host = params[0];
        String port = params[1];
        String pass = params[2];
        s = new Socket(host, Integer.parseInt(port));
        String req = null;
        if (pass!=null && !pass.isEmpty()) {
            req = "auth "+pass+"\r\n";
        } else {
            req = "PING\r\n";
        }
        Response rez = interrupt(req);
        if (rez.getType().equals(Response.TYPE_ERROR)) throw new Exception(rez.getLineOut());
        return null;
    }
}
