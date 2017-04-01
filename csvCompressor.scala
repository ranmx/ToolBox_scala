:paste
val start_time = System.currentTimeMillis()
val bzip2File = spark.read
    .format("com.databricks.spark.csv")
    .option("header", "false")
    .option("inferSchema", "false")
    .option("codec", "bzip2")
    .load("/user/ranmx/test/shanghaiCombzip2/part-00000-28421be8-e32a-44b6-b1cc-84ee9551210f.csv.bz2")
bzip2File.repartition(1).write.mode("overwrite").parquet("/user/ranmx/test/shanghaiGPS/")
val end_time = System.currentTimeMillis() - start_time
printf("took %s ms", end_time)

:paste
csv_fullfile.repartition(1).write.format("com.databricks.spark.csv").mode("overwrite").option("compression", "bzip2").save("/user/ranmx/test/shanghaiCombzip2")

:paste
import org.apache.spark.sql._
import org.apache.spark.sql.types._
val csvRdd=sc.textFile("/user/fosundb/sftp/dsp/dsp/ruishi/ruishi_20170118_csv.gz.bak")
def transformRows(iter: Iterator[String]): Iterator[Row] = iter.map(i => Row.fromSeq(i.toString.split(',').toSeq))
val rowRdd = csvRdd.mapPartitions(transformRows)
val schema = StructType("abcdefghijklmn".map(charactor => StructField(charactor.toString, StringType, true)))
val gzDf = spark.createDataFrame(rowRdd, schema)
gzDf.repartition(1).write.mode("overwrite").parquet("/user/ranmx/test/ruishiTest")
