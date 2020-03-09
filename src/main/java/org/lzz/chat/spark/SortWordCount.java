package org.lzz.chat.spark;

public class SortWordCount {
//    public static void main(String[] args) throws Exception {
//        SparkConf conf = new SparkConf().setAppName("SortWordCount").setMaster("local");
//        JavaSparkContext sc = new JavaSparkContext(conf);
//        // 创建lines RDD
//        JavaRDD<String> lines = sc.textFile("D:\\Users\\Administrator\\Desktop\\spark.txt");
//        JavaRDD<String> words = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());
//        JavaPairRDD<String,Integer> wordPair = words.mapToPair(word -> new Tuple2<>(word,1));
//        JavaPairRDD<String,Integer> wordCount = wordPair.reduceByKey((a,b) ->(a+b));
//        JavaPairRDD<Integer,String> countWord = wordCount.mapToPair(word -> new Tuple2<>(word._2,word._1));
//        JavaPairRDD<Integer,String> sortedCountWord = countWord.sortByKey(false);
//        JavaPairRDD<String,Integer> sortedWordCount = sortedCountWord.mapToPair(word -> new Tuple2<>(word._2,word._1));
//        sortedWordCount.foreach(s->System.out.println("word \""+s._1+"\" appears "+ s._2+" times."));
//        sc.close();
//    }
}