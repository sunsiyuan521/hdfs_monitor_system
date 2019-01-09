package cn.com.psbc.dao;

import static cn.com.psbc.utils.MD5Util.getMD5Str;

public class GetNameKey {
    public  static String getNameKey(String str) {
        StringBuffer buf = new StringBuffer();
        byte[] temp = getMD5Str(str);
        for (int i = 0; i < temp.length - 1; i++) {
            int i1 = Math.abs((temp[i]) ^ (temp[i + 1]));
            buf.append(i1);
        }
        return buf.toString();
    }
}
