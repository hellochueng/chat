package org.lzz.chat.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

public class SortWordCount {
    public static void main(String[] args) throws Exception {
        SparkConf conf = new SparkConf().setAppName("SortWordCount").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);
        // 创建lines RDD
        JavaRDD<String> lines = sc.textFile("C:\\Users\\Administrator\\Desktop\\1.txt");
        lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator())
                .mapToPair(word -> new Tuple2<>(word,1))
                .reduceByKey((a,b) ->(a+b))
                .mapToPair(word -> new Tuple2<>(word._2,word._1))
                .sortByKey(false)
                .mapToPair(word -> new Tuple2<>(word._2,word._1))
                .foreach(s->System.out.println(s._1+" appears "+ s._2+" times."));
        sc.close();
    }

    public static String getString(){
        return "Hooray! It's snowing! It's time to make a snowman.James runs out. He makes a big pile of snow." +
                " He puts a big snowball on top. He adds a scarf and a hat. He adds an orange for the nose. He adds" +
                " coal for the eyes and buttons.In the evening, James opens the door. What does he see? The snowman" +
                " is moving! James invites him in. The snowman has never been inside a house. He says hello to the" +
                " cat. He plays with paper towels.A moment later, the snowman takes James's hand and goes out.They go" +
                " up, up, up into the air! They are flying! What a wonderful night!The next morning, " +
                "James jumps out of bed. He runs to the door.He wants to thank the snowman. But he's gone";
    }
    private static final String[] WORDS = new String[]{
            "To be, or not to be,--that is the question:--",
            "Whether 'tis nobler in the mind to suffer",
            "The slings and arrows of outrageous fortune",
            "Or to take arms against a sea of troubles,",
            "And by opposing end them?--To die,--to sleep,--",
            "No more; and by a sleep to say we end",
            "The heartache, and the thousand natural shocks",
            "That flesh is heir to,--'tis a consummation",
            "Devoutly to be wish'd. To die,--to sleep;--",
            "To sleep! perchance to dream:--ay, there's the rub;",
            "For in that sleep of death what dreams may come,",
            "When we have shuffled off this mortal coil,",
            "Must give us pause: there's the respect",
            "That makes calamity of so long life;",
            "For who would bear the whips and scorns of time,",
            "The oppressor's wrong, the proud man's contumely,",
            "The pangs of despis'd love, the law's delay,",
            "The insolence of office, and the spurns",
            "That patient merit of the unworthy takes,",
            "When he himself might his quietus make",
            "With a bare bodkin? who would these fardels bear,",
            "To grunt and sweat under a weary life,",
            "But that the dread of something after death,--",
            "The undiscover'd country, from whose bourn",
            "No traveller returns,--puzzles the will,",
            "And makes us rather bear those ills we have",
            "Than fly to others that we know not of?",
            "Thus conscience does make cowards of us all;",
            "And thus the native hue of resolution",
            "Is sicklied o'er with the pale cast of thought;",
            "And enterprises of great pith and moment,",
            "With this regard, their currents turn awry,",
            "And lose the name of action.--Soft you now!",
            "The fair Ophelia!--Nymph, in thy orisons",
            "Be all my sins remember'd."
    };
}