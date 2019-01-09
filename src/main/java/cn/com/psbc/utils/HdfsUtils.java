package cn.com.psbc.utils;

import java.io.IOException;
import java.net.URI;

import cn.com.psbc.conf.ConfigurationManager;
import cn.com.psbc.constant.Constants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Hdfs;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hdfs.web.HsftpFileSystem;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.mapred.JobConf;

/**
 * HDFS工具类
 * 
 * 实现功能：
 * hadoop fs -ls / 
 * hadoop fs -mkdir /data 
 * hadoop fs -rmr /data/test.txt
 * hadoop fs -copyFromLocal /test/test.txt /data 
 * hadoop fs -cat /data/test.txt
 * hadoop fs -copyToLocal /data /test/test.txt 
 * 创建一个新文件，并写入内容  
 * 重命名
 * 
 * 需要导入以下路径的所有jar包： hadoop-2.7.2\share\hadoop\common
 * hadoop-2.7.2\share\hadoop\common\lib hadoop-2.7.2\share\hadoop\hdfs
 * hadoop-2.7.2\share\hadoop\hdfs\lib hadoop-2.7.2\share\hadoop\mapreduce
 * 
 *
 *
 */
public class HdfsUtils {

    // HDFS访问地址
    private static final String HDFS = ConfigurationManager.getProperty(Constants.HDFS_ADDRESS_PATH);
    // hdfs路径
    private String hdfsPath;
    // Hadoop系统配置
    private Configuration conf;

    public HdfsUtils(Configuration conf) {
        this(HDFS, conf);
    }

    public HdfsUtils(String hdfs, Configuration conf) {
        this.hdfsPath = hdfs;
        this.conf = conf;
    }

    // 加载Hadoop配置文件
    public static JobConf config() {
        JobConf conf = new JobConf(HdfsUtils.class);
        conf.setJobName("HdfsUtils");
        //System.setProperty("dfs.client.use.datanode.hostname","true");
        //conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs.DistributedFileSystem");
        //conf.set("fs.defaultFS", "hdfs://192.168.235.129:8020");
        System.setProperty("HADOOP_USER_NAME", "hdfs");
        //conf.addResource("classpath:/usr/hdp/2.6.4.0-91/hadoop/conf/core-site.xml");
        //conf.addResource("classpath:/usr/hdp/2.6.4.0-91/hadoop/conf/hdfs-site.xml");
        // conf.addResource("classpath:/hadoop/mapred-site.xml");
        return conf;
    }

    public void mkdirs(String folder) throws IOException {
        Path path = new Path(folder);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        if (!fs.exists(path)) {
            fs.mkdirs(path);
            System.out.println("Create: " + folder);
        }
        fs.close();
    }

    public void rmr(String folder) throws IOException {
        Path path = new Path(folder);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        fs.deleteOnExit(path);
        System.out.println("Delete: " + folder);
        fs.close();
    }

    public void ls(String folder) throws IOException {
        Path path = new Path(folder);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        FileStatus[] list = fs.listStatus(path);
        System.out.println("ls: " + folder);
        System.out.println("==========================================================");
        for (FileStatus f : list) {
            System.out.printf("name: %s, folder: %s, size: %d\n", f.getPath(), f.isDir(), f.getLen());
        }
        System.out.println("==========================================================");
        fs.close();
    }

    public void createFile(String file, String content) throws IOException {
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        byte[] buff = content.getBytes();
        FSDataOutputStream os = null;
        try {
            os = fs.create(new Path(file));
            os.write(buff, 0, buff.length);
            System.out.println("Create: " + file);
        } finally {
            if (os != null)
                os.close();
        }
        fs.close();
    }

    public void copyFileToHdfs(String local, String remote) throws IOException {
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        fs.copyFromLocalFile(new Path(local), new Path(remote));
        System.out.println("copy from: " + local + " to " + remote);
        fs.close();
    }

    public void download(String remote, String local) throws IOException {
        Path path = new Path(remote);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        fs.copyToLocalFile(path, new Path(local));
        System.out.println("download: from" + remote + " to " + local);
        fs.close();
    }

    public void renameFile(String oldFileName, String newFileName) throws IOException {
        boolean isSuccess = true;

        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        try {
            isSuccess = fs.rename(new Path(oldFileName), new Path(newFileName));
        } catch (IOException e) {
            isSuccess = false;
        }
        System.out.println(isSuccess ? "Rename success！ " + oldFileName + " to " + newFileName
                : "Rename failed！" + oldFileName + " to " + newFileName);
        fs.close();
    }

    /**
     * 查看某个文件在HDFS集群的位置
     * 
     * @throws IOException
     */

    public void findLocationOnHadoop(String filePath) throws IOException {
        // Path targetFile=new Path(rootPath+"user/hdfsupload/AA.txt");
        // FileStatus fileStaus=coreSys.getFileStatus(targetFile);
        Path targetFile = new Path(HDFS + filePath);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        FileStatus fileStaus = fs.getFileStatus(targetFile);
        BlockLocation[] bloLocations = fs.getFileBlockLocations(fileStaus, 0, fileStaus.getLen());
        for (int i = 0; i < bloLocations.length; i++) {
            System.out.println("block_" + i + "_location:" + bloLocations[i].getHosts()[0]);
        }
        fs.close();
    }

    public void cat(String remoteFile) throws IOException {
        Path path = new Path(remoteFile);
        FileSystem fs = FileSystem.get(URI.create(hdfsPath), conf);
        FSDataInputStream fsdis = null;
        System.out.println("cat: " + remoteFile);
        try {
            fsdis = fs.open(path);
            IOUtils.copyBytes(fsdis, System.out, 4096, false);
        } finally {
            IOUtils.closeStream(fsdis);
            fs.close();
        }
    }

}
