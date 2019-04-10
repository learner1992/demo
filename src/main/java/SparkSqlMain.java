import com.google.gson.Gson;
import com.maplecloudy.api.app.AppPod;
import com.maplecloudy.app.MAppRunner;
import com.maplecloudy.app.MAppTool;
import com.maplecloudy.app.annotation.Action;
import com.maplecloudy.app.utils.MAppUtils;
import config.FakeUnixGroupsMapping;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;


/**
 * @author fgzhong
 * @since 2019/3/31
 */
@Action
public class SparkSqlMain implements MAppTool {


    public int run(String[] args) throws Exception {
        AppPod appPod = MAppUtils.getAppPod();
        String useName = (String) appPod.getConfigMap().get("usename");
        String sql = (String) appPod.getConfigMap().get("sql");
        String tableName = (String) appPod.getConfigMap().get("table");
        String outPath = (String) appPod.getConfigMap().get("outPath");
        System.out.println(appPod.getConfigMap());
        MAppUtils.loadSparkConf();
        SparkConf scf = new SparkConf(true).setAppName("maplecloudy-spark-hive-app-" + MAppUtils.getMAppId());
        MAppUtils.appendHadoopConf2Spark(scf);
        MAppUtils.appendHiveConf2Spark(scf);
        System.out.println(new Gson().toJson(scf.getAll()));
        SparkSession spark = SparkSession.builder().config(scf)
                .enableHiveSupport().getOrCreate();
        Dataset<Row> table = spark.sql(sql);
        table.write().format("com.databricks.spark.avro").mode(SaveMode.Overwrite)
                .option("path",outPath)
                .saveAsTable(tableName);

        return 0;
    }

    public static void main(String[] args) throws Exception {
        System.exit(MAppRunner.run(new SparkSqlMain(), args));
    }
}
