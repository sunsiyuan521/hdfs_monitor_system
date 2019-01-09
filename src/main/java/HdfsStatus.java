
import cn.com.psbc.conf.ConfigurationManager;
import cn.com.psbc.constant.Constants;
import cn.com.psbc.dao.PBImageDelimitedTextWriter;
import cn.com.psbc.dao.FindFsimageFile;
import cn.com.psbc.utils.HdfsUtils;
import org.apache.hadoop.mapred.JobConf;
import java.io.*;
import static cn.com.psbc.utils.HdfsUtils.config;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class HdfsStatus {
    public static void main(String[] args) throws Exception  {
        String fsimage_tmp_path = ConfigurationManager.getProperty(Constants.FSIMAGE_TMP_PATH);
        String fsimage_output_path_file = ConfigurationManager.getProperty(Constants.FSIMAGE_OUTPUT_PATH_FILE);
        String fsimage_txt_hdfs_path = ConfigurationManager.getProperty(Constants.FSIMAGE_TXT_HDFS_PATH);
        FindFsimageFile findFsimageFile = new FindFsimageFile();
        File file = findFsimageFile.findFiles(fsimage_tmp_path);

        PrintStream out = new PrintStream(fsimage_output_path_file, "UTF-8");
        PBImageDelimitedTextWriter writer =
                new PBImageDelimitedTextWriter(out, ",", "");
        writer.visit(new RandomAccessFile(file, "r"));

    }

}
