Developed By
=

* Daniel Pardo Ligorred - <daniel@programacionj2ee.com>

YizzStomp - Stomp Client for Android
=

    YizzStomp is a own project library in Java to get in Android applications 
    the availability of the STOMP protocol, is the first library without 
    library dependencies of third, to guarantee the full compatibility with 
    Google Android SDK.

    This project is in ALPHA VERSION, has errors to be fix,  therefore I suggest 
    you don't use it in production applications.

    * You are welcome to contribute.
    * You are welcome to do any suggestion.
    * You are welcome to ask.

Quick start
=

Starting connection.
```java
StompClient stompClient = new StompClient(host, host_port, heartbeats_time);

stompClient.connect("username", "password", MessageHandler);

stompClient.subscribe("jms.topic.queue");
```

MessageHandler interface methods to implement.
```java
public void onConnected(ConnectMessage msg);

public void onMessage(SubscribeMessage msg);
```
License
=

    Copyright (C) 2014 Daniel Pardo ligorred

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.