package com.company.homework.bolt;

import com.company.homework.utils.MapSort;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseBasicBolt;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Map;

public class WordCountBolt extends BaseRichBolt {
    private Map<String, Integer> counters;
    private OutputCollector outputCollector;

    public void prepare(Map stormConf, TopologyContext context,
                        OutputCollector collector) {
        outputCollector = collector;
        counters = new HashMap<>();
    }

    public void execute(Tuple input) {
        String str = input.getString(0);

        if (!counters.containsKey(str)) {
            counters.put(str, 1);
        } else {
            Integer c = counters.get(str) + 1;
            counters.put(str, c);
        }

        //我们只取词频最大的前十个
        int length = 0;

        //使用工具类MapSort对map进行排序
        counters = MapSort.sortByValue(counters);

        length = counters.keySet().size();

        String word = null;

        int count = 0;
        for (String key : counters.keySet()) {
            if (count >= length) {
                break;
            }

            if (count == 0) {
                word = "[" + key + ":" + counters.get(key) + "]";
            } else {
                word = word + ", [" + key + ":" + counters.get(key) + "]";
            }
            count++;
        }

        outputCollector.emit(new Values(word));
    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("word"));
    }
}
