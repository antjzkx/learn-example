package com.company.homework.spout;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;
import java.util.Random;

public class RandomSentenceSpout extends BaseRichSpout {
	private SpoutOutputCollector collector;
	private Random random;
	
	// 进行spout的一些初始化工作，包括参数传递
	public void open(Map conf, TopologyContext context,
	                 SpoutOutputCollector collector) {
		this.collector = collector;
		random = new Random();
	}
	
	// 进行Tuple处理的主要方法
	public void nextTuple() {
		Utils.sleep(2000);
		String[] sentences = new String[]{
				"Hello Tom",
				"Hello Dog",
				"Hello Pig",
				"Hello Jerry",
				"Hello Cat"};
		// 从sentences数组中，随机获取一条语句，作为这次spout发送的消息
		collector.emit(new Values(sentences[random.nextInt(sentences.length)]));
	}
	
	// 声明字段
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("word"));
	}
}
