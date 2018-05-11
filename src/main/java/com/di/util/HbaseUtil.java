package com.di.util;


import com.di.pojo.HbaseResultKey;
import org.apache.commons.lang3.StringUtils;


import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by bentengdi on 2017/07/13.
 */
public class HbaseUtil {
    private final static Logger LOG = LoggerFactory.getLogger(HbaseUtil.class);

    private static Connection connection;

    static {
        getConnection();
    }

    private static Configuration getConnection() {//throws IOException{
        try {
            LOG.info("Initialize HBase Connection!");
            Configuration conf = HBaseConfiguration.create();
            conf.addResource(Thread.currentThread().getContextClassLoader().getResourceAsStream("hbase-site.xml"));
            conf.setInt("hbase.rpc.timeout", 20000);
            conf.setInt("hbase.client.operation.timeout", 30000);
            conf.setInt("hbase.client.scanner.timeout.period", 20000);
            conf.setInt("zookeeper.session.timeout", 20000);
            conf.setInt("hbase.client.retries.number",3);
            conf.setInt("zookeeper.recovery.retry", 3);
//            conf.setInt("hbase.hconnection.thread.max",512);
//            conf.setInt("hbase.hconnection.thread.core",512);
//            conf.setInt("hbase.hconnection.thread.keepalivetime",60);
            connection = ConnectionFactory.createConnection(conf);
            LOG.info("Finish initializing HBase Connection!");
            return conf;
        } catch (IOException e) {
            LOG.error("Initialize HBase Connection fail!",e);
            return null;
        }
    }

    private static boolean testConnection(Configuration conf) throws IOException {
        if (conf == null) return false;
        Admin admin = connection.getAdmin();
        TableName[] tableNames = admin.listTableNames();
        if (tableNames == null || tableNames.length == 0) return false;
        return true;
    }

    /**
     * 尝试重连Hbase
     * @return
     */
    private static boolean reconnection() {
        try {
            connection.close();
            Configuration conf = getConnection();
            return testConnection(conf);
        } catch (IOException e) {
            LOG.error("Reconnection Hbase fail!", e);
            return false;
        }
    }

    public static Map<HbaseResultKey, Map<String, String>> getKeyValuesByTimeRange(String table, String rowPrefix, String cloumnfamily,
                                                                                   long startTime, long endTime) throws IOException {
        Filter filter = new PrefixFilter(rowPrefix.getBytes());
        Scan scan = new Scan();
        String startRow = rowPrefix + "_";
        String stopRow = rowPrefix + "`";
        scan.setStartRow(startRow.getBytes());
        scan.setStopRow(stopRow.getBytes());
        scan.setFilter(filter);
        scan.setTimeRange(startTime, endTime);
        scan.setMaxVersions();
        scan.addFamily(cloumnfamily.getBytes());
        return getKeyValuesByScan(table, scan);
    }

    public static Map<HbaseResultKey, String > getValuesByVersion(String table, String rowPrefix, String cloumnfamily,
                                                             int maxVersion) {
        List<Filter> filters = new ArrayList<Filter>();
        Filter filter1 = new PrefixFilter(rowPrefix.getBytes());
        Filter filter2 = new PageFilter(500L);
        filters.add(filter1);
        filters.add(filter2);
        Scan scan = new Scan();
        try{
    //        String md5 = MD5Util.md5(rowPrefix);
            String startRow = rowPrefix + "_";
            String stopRow = rowPrefix + "`";
            Long endtime = System.currentTimeMillis();
            Long begintime = endtime-30*24*60*60*1000L;
            scan.setTimeRange(begintime, endtime);
            scan.setStartRow(startRow.getBytes());
            scan.setStopRow(stopRow.getBytes());

            FilterList filterList = new FilterList(filters);
            scan.setFilter(filterList);
            scan.addFamily(cloumnfamily.getBytes());
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return getValuesByScan(table, scan);
    }



    public static Map<HbaseResultKey, Map<String, String> > getKeyValuesByVersion(String table, String rowPrefix, String family,
                                                                             int maxVersion) {
//        String md5 = MD5Util.md5(rowPrefix);
        Filter filter = new PrefixFilter(rowPrefix.getBytes());
        Scan scan = new Scan();
        String startRow = rowPrefix + "_";
        String stopRow = rowPrefix + "`";
        scan.setStartRow(startRow.getBytes());
        scan.setStopRow(stopRow.getBytes());

        scan.setFilter(filter);
        scan.setMaxVersions(maxVersion);
        scan.addFamily(family.getBytes());
        return getKeyValuesByScan(table, scan);
    }


    public static Map<HbaseResultKey, Map<String, String>> getKeyValuesByScan(String table, Scan scan) {
        Map<HbaseResultKey, Map<String, String>> records = new TreeMap<HbaseResultKey, Map<String, String>>(new Comparator<HbaseResultKey>() {
            public int compare(HbaseResultKey o1, HbaseResultKey o2) {
                if (o1.equals(o2)) {
                    return 0;
                } else if (o1.timestamp < o2.timestamp) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        try {
            Table hTable = connection.getTable(TableName.valueOf(table));
            ResultScanner resultScanner = hTable.getScanner(scan);

            for (Result result : resultScanner) {
                for (Cell cell : result.rawCells()) {

                    String row = new String(CellUtil.cloneRow(cell), "UTF-8");

                    String key = row.substring(row.lastIndexOf('_') + 1);

                    Map<String, String> record = getRowByTimestamp(records, new HbaseResultKey(key, cell.getTimestamp()));

                    String column = new String(CellUtil.cloneQualifier(cell), "UTF-8");
                    String value = new String(CellUtil.cloneValue(cell), "UTF-8");
                    record.put(column, value);
                }
            }

            hTable.close();
            return records;
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return records;
        }
    }



    public static Map<HbaseResultKey, String > getValuesByScan(String table, Scan scan) {
        Map<HbaseResultKey, String > records = new TreeMap<HbaseResultKey, String>(new Comparator<HbaseResultKey>() {
            public int compare(HbaseResultKey o1, HbaseResultKey o2) {
                if(o1.equals(o2)) {
                    return 0;
                } else if(o1.timestamp < o2.timestamp) {
                    return 1;
                } else {
                    return -1;
                }
            }
        });

        try {
            Table hTable = connection.getTable(TableName.valueOf(table));
            ResultScanner resultScanner = hTable.getScanner(scan);

            for(Result result: resultScanner) {
                for(Cell cell: result.rawCells()) {
                    String row = new String(CellUtil.cloneRow(cell), "UTF-8");

                    String record = records.get(new HbaseResultKey(row, cell.getTimestamp()));
                    if(record == null) {
                        record = new String(CellUtil.cloneValue(cell), "UTF-8");
                        records.put(new HbaseResultKey(row, cell.getTimestamp()), record);
                    }
                }
            }

            hTable.close();
            return records;
        }catch(IOException e) {
            LOG.error(e.getMessage(), e);
            return records;
        }
    }




    public static String getResultByColumn(String tableName, String rowKey,
                                           String familyName, String columnName) {
        String value = new String();
        try {
            Table hTable = connection.getTable(TableName.valueOf(tableName));
            Get get = new Get(Bytes.toBytes(rowKey));
            get.addColumn(Bytes.toBytes(familyName), Bytes.toBytes(columnName)); // 获取指定列族和列修饰符对应的列
            Result result = hTable.get(get);

            for (Cell cell : result.rawCells()) {
                value = new String(CellUtil.cloneValue(cell), "UTF-8");
                break;
            }
            hTable.close();
            return value;
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
            return value;
        }
    }


    private static Map<String, String> getRowByTimestamp(Map<HbaseResultKey, Map<String, String>> rows, HbaseResultKey resultKey) {
        Map<String, String> row = rows.get(resultKey);
        if (row == null) {
            row = new HashMap<String, String>();
            //r.put("ts", String.valueOf(ts));
            rows.put(resultKey, row);
        }
        return row;
    }

    /**
     * Created by bentengdi on 2017/8/2.
     * 插入数据
     *
     * @param tablename  表名
     * @param rowkey     行健
     * @param action     用户行为
     * @param familyname 族名
     * @param columnname 列名
     */
    public static void insertData(String tablename, String familyname, String columnname, String rowkey, Integer action) {
        try {
            Table hTable = connection.getTable(TableName.valueOf(tablename));
            Put put = new Put(rowkey.getBytes());
            put.addColumn(familyname.getBytes(), columnname.getBytes(), action.toString().getBytes());
            hTable.put(put);
            hTable.close();
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * 现在的表结构为：MD5(Uid)_cid作为rowkey，Action作为一列
     * 根据Uid查询用户屏蔽或者看过的cid列表
     *
     * @param tablename    表名
     * @param prefixrowkey 行健rowkey前缀
     * @param number       限制返回结果数量
     * @param action       用户行为
     * @param begintime    开始时间
     * @param endtime      结束时间
     */
    public static List<Integer> scanRowkeyByUid(String tablename, String prefixrowkey, String familyname, String columnname, Integer number, Integer action, Long begintime, Long endtime) {
        List<Integer> cidlist = new ArrayList<Integer>();
        Integer defaultnumber = 2000;
        if (number == null) {//number为空就设置为默认值
            number = defaultnumber;
        }
        try {
            Table hTable = connection.getTable(TableName.valueOf(tablename));
            FilterList filterList = new FilterList(FilterList.Operator.MUST_PASS_ALL);
            //筛选出具有特定前缀的行键的数据
            Filter prefixFilter = new PrefixFilter(prefixrowkey.getBytes());
            filterList.addFilter(prefixFilter);
            Scan scan = new Scan();

            String startRow = prefixrowkey + "_";
            String stopRow = prefixrowkey + "`";
            scan.setStartRow(startRow.getBytes());
            scan.setStopRow(stopRow.getBytes());
            scan.setCaching(1000);
            if (action != null && action == 3) {//限制action
                Filter valueFilter = new ValueFilter(CompareFilter.CompareOp.EQUAL, new SubstringComparator(action.toString()));
                filterList.addFilter(valueFilter);
            }
            scan.setFilter(filterList);
            if (begintime != null && endtime != null) {//限制时间范围
                if (begintime > endtime) {
                    return cidlist;
                }
                scan.setTimeRange(begintime, endtime);
            }
            ResultScanner resultScanner = hTable.getScanner(scan);
            for (Result result : resultScanner) {//number限制查询返回记录的条数
                if (cidlist.size() < number) {
                    String[] stringarray = Bytes.toString(result.getRow()).split("_");
                    if (stringarray.length == 2) {
                        String string = StringUtils.join(stringarray[1]);
                        cidlist.add(Integer.parseInt(string));
                    } else if (stringarray.length == 3) {
                        String string = StringUtils.join(stringarray[2]);
                        if (cidlist.contains(Integer.parseInt(string))) {
                            continue;
                        }
                        cidlist.add(Integer.parseInt(string));
                    }
                } else {
                    break;
                }
            }
            hTable.close();
        } catch (Exception e) {
            LOG.error(e.getMessage(), e);
        }
        return cidlist;
    }

    /**
     * 现在的表结构为：MD5(Uid)_cid作为rowkey，value作为一列
     * 根据Uid查询根据tag计算好的cid列表
     *
     * @param tablename    表名
     * @param prefixrowkey 行健rowkey前缀
     * @param columnfamily 列族
     * @param columnname   列名
     * @param begintime    开始时间
     * @param endtime      结束时间
     */
    public static Map<Integer, Double> scanRowkeyAndCellByUid(String tablename, String prefixrowkey, String columnfamily,
                                                              String columnname, Long begintime, Long endtime,
                                                              Integer number) throws Exception {
        Map<Integer, Double> map = new HashMap<Integer, Double>();
        Table hTable = connection.getTable(TableName.valueOf(tablename));
        //筛选出具有特定前缀的行键的数据
        Filter prefixFilter = new PrefixFilter(prefixrowkey.getBytes());
        Scan scan = new Scan().setMaxVersions(1);//版本设置,取所有版本
        scan.setFilter(prefixFilter);
        scan.addColumn(Bytes.toBytes(columnfamily), Bytes.toBytes(columnname));
        String startRow = prefixrowkey + "_";
        String stopRow = prefixrowkey + "`";
        scan.setStartRow(startRow.getBytes());
        scan.setStopRow(stopRow.getBytes());
       // scan.setTimeRange(begintime, endtime);//限制时间范围
        if (begintime != null && endtime != null) {//限制时间范围
            if (begintime > endtime) {
                return map;
            }
            scan.setTimeRange(begintime, endtime);
        }
        ResultScanner resultScanner = hTable.getScanner(scan);
        for (Result result : resultScanner) {
            if (number != null) {
                if(map.size()>number){
                    return  map;
                }
            }
            for (Cell cell : result.rawCells()) {
                String stringarray[] = Bytes.toString(result.getRow()).split("_");
                if (stringarray == null || stringarray.length != 2) {
                    continue;
                }
                Integer key = Integer.parseInt(StringUtils.join(stringarray[1]));
                String string = Bytes.toString(CellUtil.cloneValue(cell));
                if (string != null) {
//                    if (map.containsKey(key)) {
//                        BigDecimal bigDecimal = new BigDecimal(String.valueOf(map.get(key)));
//                        Double value = bigDecimal.add(new BigDecimal(string)).doubleValue();
//                        map.put(key, value);
//                        continue;//                   }
                    map.put(key, Double.parseDouble(string));
                }
            }
        }
        resultScanner.close();
        hTable.close();
        return map;
    }
    /**
     *
     */
    public static String getValueByRowkey(String tablename, String rowkey, String cloumnfamily,
                                          String cloumnname) throws Exception {
        Table hTable = connection.getTable(TableName.valueOf(tablename));
        Get get=new Get(Bytes.toBytes(rowkey));
        get.addColumn(Bytes.toBytes(cloumnfamily),Bytes.toBytes(cloumnname));
        Result result=hTable.get(get);
        String string = Bytes.toString(result.getValue(Bytes.toBytes(cloumnfamily),Bytes.toBytes(cloumnname)));
        hTable.close();
        return  string;
    }
}

