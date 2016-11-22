:paste
val seagul_source = scala.io.Source.fromFile("/home/alfred/Downloads/Jonathan_Livingston_Seagull.txt", "UTF-8")
val word_count_mutable = new scala.collection.mutable.HashMap[String, Int]
for (line <- seagul_source.getLines)
    for (word <- line.split("""\s+""")) 
        if (word_count_mutable.contains(word)) word_count_mutable(word) += 1 
        else  word_count_mutable(word) = 1
for ((k, v) <- word_count_mutable) if (v > 5) println(k, v)

:paste 
val seagul_source = scala.io.Source.fromFile("/home/alfred/Downloads/Jonathan_Livingston_Seagull.txt", "UTF-8")
var word_count_immutable = new scala.collection.immutable.HashMap[String, Int]
for (line <- seagul_source.getLines)
    for (word <- line.split("""\s+""")){
        val count = if (word_count_immutable.contains(word)) word_count_immutable(word)+1 else 1
        word_count_immutable += (word -> count)
        }
for ((k, v) <- word_count_immutable) if (v > 5) println(k, v)

:paste
var word_count_statistic = new collection.mutable.HashMap[Int, collection.mutable.ArrayBuffer[String]]
for ((k, v) <- word_count_immutable)
    if (word_count_statistic.contains(v)) word_count_statistic(v) += k
    else word_count_statistic(v) = collection.mutable.ArrayBuffer(k)

val sorted_map = collection.immutable.SortedMap(word_count_statistic.toSeq:_*)
for ((k,v) <- sorted_map) if (k>2) println(k,v) else ()

:paste
var word_count_statistic = collection.immutable.SortedMap.empty[Int, collection.mutable.ArrayBuffer[String]]
for ((k, v) <- word_count_immutable)
    if (word_count_statistic.contains(v)) word_count_statistic(v) += k
    else word_count_statistic +=  (v -> collection.mutable.ArrayBuffer(k))
for ((k,v) <- word_count_statistic) if (k>2) println(k,v) else ()

:paste
import scala.collection.JavaConverters.mapAsScalaMap
val java_map = new java.util.TreeMap[Int, collection.mutable.ArrayBuffer[String]]
val word_count_statistic_java: scala.collection.mutable.Map[Int, collection.mutable.ArrayBuffer[String]] =  mapAsScalaMap(java_map)
for ((k,v) <- word_count_mutable) 
    if (word_count_statistic_java.contains(v)) word_count_statistic_java(v) += k 
    else word_count_statistic_java(v) = collection.mutable.ArrayBuffer(k)
for ((k,v) <- word_count_statistic_java) if (k>2) println(k,v) else ()

:paste
import scala.collection.JavaConverters._
val java_map = new java.util.TreeMap[Int, collection.mutable.ArrayBuffer[String]]
val word_count_statistic_java: scala.collection.mutable.Map[Int, collection.mutable.ArrayBuffer[String]] = java_map.asScala
for ((k,v) <- word_count_mutable) 
    if (word_count_statistic_java.contains(v)) word_count_statistic_java(v) += k 
    else word_count_statistic_java(v) = collection.mutable.ArrayBuffer(k)
for ((k,v) <- word_count_statistic_java) if (k>2) println(k,v) else ()
