package com.company.homework;

import com.company.homework.bolt.PrintBolt;
import com.company.homework.bolt.WordCountBolt;
import com.company.homework.bolt.WordNormalizerBolt;
import com.company.homework.spout.RandomSentenceSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

public class WordCountTopology {
    private static TopologyBuilder builder = new TopologyBuilder();
    
    public static void main(String[] args) {
        
        Config config = new Config();
    
        builder.setSpout("RandomSentence", new RandomSentenceSpout(), 2);
        builder.setBolt("WordNormalizer", new WordNormalizerBolt(), 2).shuffleGrouping(
                "RandomSentence");
        builder.setBolt("WordCount", new WordCountBolt(), 2).fieldsGrouping("WordNormalizer",
                new Fields("word"));
        builder.setBolt("Print", new PrintBolt(), 1).shuffleGrouping(
                "WordCount");
        
        config.setDebug(false);
        
        config.setMaxTaskParallelism(1);
        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("wordcount", config, builder.createTopology());
    }
}
