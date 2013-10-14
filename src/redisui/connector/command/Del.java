/*
 * 
 */
package redisui.connector.command;

import redisui.connector.Response;

/**
 *
 * @author marat
 */
public class Del extends CommandBased {

    @Override
    public Response exec(String[] params) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("del");
        for(String ss : params) {
            sb.append(" ").append(ss);
        }
        sb.append("\r\n");
        Response res = interrupt(sb.toString());
        if (res.getType().equals(Response.TYPE_ERROR)) {
            throw new Exception(res.getLineOut());
        }
        return null;
    }
    
}
