/*
 * 
 */
package redisui.connector;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author marat
 */
public class Response {

    private String type;
    private byte[] out;
    public static final String TYPE_LINE = "+";
    public static final String TYPE_ERROR = "-";
    public static final String TYPE_INT = ":";
    public static final String TYPE_STRING = "$";
    public static final String TYPE_ARRAY = "*";

    public Response(String type, byte[] out) {
        this.type = type;
        this.out = out;
    }

    public String getLineOut() {
        return byte2str(out);
    }
    
    public int getIntOut() {
        return byte2int(out);
    }
    
    public String getStringOut() {
        return brows2str(out);
    }

    public String[] getArrayOut() {
        try {
            int[] bl = byte2begin_len(out);
            String[] result = new String[bl[1]];
            byte[] outbody = Arrays.copyOfRange(out, bl[0]+1, out.length);
            ArrayList<byte[]> parts = byteSplit(outbody, '$');
            for (int i=0;i<parts.size();i++) {
                byte[] part = parts.get(i);
                result[i] = brows2str(part);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        String[] rez = {};
        return rez;
    }

    public String getType() {
        return type;
    }
    
    private String byte2str(byte[] bb) {
        try {
            return new String(bb, "UTF-8").trim();
        } catch (UnsupportedEncodingException ex) {
            ex.printStackTrace(System.err);
        }
        return null;
    }
    
    private int byte2int(byte[] bb) {
        return Integer.parseInt(byte2str(bb));
    }
    
    private String brows2str(byte[] bb) {
        String ss = new String(bb); ///
        try {
            int[] bl = byte2begin_len(bb);
            byte[] strb = Arrays.copyOfRange(bb, bl[0], bl[0]+bl[1]);
        return byte2str(strb);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return "";
    }
    
    private int[] byte2begin_len(byte[] bb) throws Exception{
        int delim = charSearch(bb, '\r');
        if (delim<0) throw new Exception("not 'new line' in string "+new String(bb));
        byte[] num = Arrays.copyOf(bb, delim);
        int[] rez = {delim+2, byte2int(num)};
        return rez;
    }
    
    private ArrayList<byte[]> byteSplit(byte[] bb, char delimiter) {
        ArrayList<byte[]> rez = new ArrayList<byte[]>();
        int begin = 0;
        int pos = -1;
        do {
            pos = charSearch(bb, delimiter, begin, bb.length);
            byte[] spil = null;
            if (pos>0) {
                spil = Arrays.copyOfRange(bb, begin, pos);
            } else {
                spil = Arrays.copyOfRange(bb, begin, bb.length);
            }
            rez.add(spil);
            begin = pos+1;
        }
        while (pos>0);
        return rez;
    }
    
    private int charSearch(byte[] bb, char c) {
        return charSearch(bb, c, 0, bb.length);
    }
    
    private int charSearch(byte[] bb, char c, int begin, int end) {
        byte cc = (byte) c;
        for(int i=begin;i<end;i++) {
            byte b = bb[i];
            if(b==cc) {
                return i;
            }
        }
        return -1;
    }
}
