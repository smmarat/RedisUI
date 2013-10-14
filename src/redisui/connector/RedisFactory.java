/*
 * 
 */
package redisui.connector;

import java.net.Socket;
import redisui.connector.command.CommandBased;

/**
 *
 * @author marat
 */
public class RedisFactory {
    
    private Socket s;
    public static final String COMMAND_AUTH = "redisui.connector.command.Auth";
    public static final String COMMAND_KEYS = "redisui.connector.command.Keys";
    public static final String COMMAND_GET = "redisui.connector.command.Get";
    public static final String COMMAND_TYPE = "redisui.connector.command.Type";
    public static final String COMMAND_HGETALL = "redisui.connector.command.Hgetall";
    public static final String COMMAND_DEL = "redisui.connector.command.Del";
    
    public void connect(String host, String port, String pass) throws Exception {
        String[] params = {host, port, pass};
        exec(COMMAND_AUTH, params);
    }
    
    public Response exec(String cmd, String[] params) throws Exception {
        Class<? extends CommandBased> command = Class.forName(cmd).asSubclass(CommandBased.class);
        CommandBased ci = command.newInstance();
        ci.setS(this.s);
        Response r = ci.exec(params);
        this.s = ci.getS();
        return r;
    }  
}
