package org.lzz.chat.mapreduce;

public class RunHadoop {
    public static void main(String[] args){
        //创建目录 
        String folderName = "/test";
        Files.mkdirFolder(folderName);

//        WordCountRun.Run();
//        System.getProperty("hadoop.home.dir");

//        ScoreDistinctRun.Run();
//        UserScoreRun.Run();
//        SimilarityGoodsRun.Run();
//        UserGoodsRun.Run();
//        UserGoodsScoreSumRun.Run();
//        UserGoodsScoreSortRun.Run();
        Files.closeConnection();
    }
}