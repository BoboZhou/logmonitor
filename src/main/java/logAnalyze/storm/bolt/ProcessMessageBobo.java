package logAnalyze.storm.bolt;

import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import logAnalyze.storm.domain.LogMessage;
import logAnalyze.storm.utils.LogAnalyzeHandler;

/**
 * Created by bobo on 2017/7/10.
 */
public class ProcessMessageBobo extends BaseBasicBolt {
    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        LogMessage  logMessage = (LogMessage) input.getValue(1);
        LogAnalyzeHandler.process(logMessage);

    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
