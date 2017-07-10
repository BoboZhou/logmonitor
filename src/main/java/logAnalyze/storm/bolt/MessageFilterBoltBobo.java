package logAnalyze.storm.bolt;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;
import com.google.gson.Gson;
import logAnalyze.storm.domain.LogMessage;
import logAnalyze.storm.utils.LogAnalyzeHandler;

import java.util.Map;

/**
 * Created by bobo on 2017/7/10.
 */
public class MessageFilterBoltBobo extends BaseRichBolt {
    private Map conf;
    private TopologyContext context;
    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.conf = stormConf;
        this.context = context;
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        //获取KafkaSpout发送出来的数据
        String line = input.getString(0);
        LogMessage logMessage = new Gson().fromJson(line, LogMessage.class);
        if (logMessage == null || !LogAnalyzeHandler.isValidType(logMessage.getType()))
            return;

        collector.emit(new Values(logMessage.getType(), logMessage));
        //定时更新规则信息
        LogAnalyzeHandler.scheduleLoad();
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("type", "message"));
    }
}
