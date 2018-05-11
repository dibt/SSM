package com.di;

import com.di.util.HbaseUtil;
import com.di.util.MD5Util;
import org.junit.Test;

/**
 * Created by bentengdi on 2018/5/11.
 */
public class HbaseUtilTest {
    @Test
    public void insertDataTest() throws Exception{
        String tablename="rec:feed_hide";
        String columnfamily="dislike";
        String column = "ACTION";
        String rowkey = "111";
        Integer action=33;
        HbaseUtil.insertData(tablename,columnfamily,column,rowkey,action);
    }

}
