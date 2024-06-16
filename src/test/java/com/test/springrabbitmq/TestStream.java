package com.test.springrabbitmq;

public class TestStream {

    // RabbitMQ in 3.9.x version release new queue type called stream queue, stream queue mechanism is like this
    // it got log file system to store the message that received, each message will have it own offset, the message after consume by consumer
    // won't be deleted from queue, so it can repeat consume, similar to kafka

    // since this stream use case and feature is to compete with kafka, the ecosystem of stream queue not good compare to kafka
    // is for learning this new queue only, further information refer to official documentation

}
