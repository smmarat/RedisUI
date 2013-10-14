/*
 * 
 */
package redisui.connector.command;

import redisui.connector.Response;

/**
 *
 * @author marat
 */
public class Type extends CommandBased{

    @Override
    public Response exec(String[] params) throws Exception {
        String command = "type "+params[0]+"\r\n";
        Response res = interrupt(command);
        if (res.getType().equals(Response.TYPE_ERROR)) {
            throw new Exception(res.getLineOut());
        }
        return res;
    }
    
}
