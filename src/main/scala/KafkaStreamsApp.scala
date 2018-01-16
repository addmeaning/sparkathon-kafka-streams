import java.util.Properties

import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.{Consumed, KafkaStreams, StreamsBuilder, Topology}
import org.apache.kafka.streams.Consumed.{`with` => ConsumedWith}
import org.apache.kafka.streams.kstream.{KStream, Printed, Produced}

object KafkaStreamsApp extends App {
  val buider = new StreamsBuilder
  private val consumed: Consumed[String, String] = ConsumedWith(Serdes.String(), Serdes.String())
  val input: KStream[String, String] = buider.stream("input", consumed)
  val printed: Printed[String, String] = Printed.toSysOut()
  input.print(printed)
  private val topology: Topology = buider.build()
  val props = new Properties()
  props.put("application.id", "whatever")
//  props.put("application.servers", "localhosst:9092")
  props.put("bootstrap.servers", "localhost:9092")
  val ks = new KafkaStreams(topology, props)

  val produced: Produced[String, String] = Produced.`with`(Serdes.String(), Serdes.String())

  //2: czytamy z topicu i piszemy do topicu
  input.to("output", produced)
  //3: mapValues
  //4:
  ks.start()
}
