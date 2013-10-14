/*
 * 
 */
package redisui.connector.command;

import redisui.connector.Response;

/**
 *
 * @author marat
 */
public class Keys extends CommandBased {

    @Override
    public Response exec(String[] params) throws Exception {
        String pattern = null;
        if (params.length == 0) {
            pattern = "*";
        } else {
            pattern = params[0];
        }
        Response rez = interrupt("KEYS " + pattern + "\r\n");
        if (rez.getType().equals(Response.TYPE_ERROR)) {
            throw new Exception(rez.getLineOut());
        }
        return rez;
    }

}
