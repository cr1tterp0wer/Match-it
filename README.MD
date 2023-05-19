Must be java8
$ sudo apt-get install openjdk-8-jdk
$ sudo update-alternatives --config java
$ sudo update-alternatives --config javac

$ export JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64

BUILD

From root dir

$ export MATCH_IT=/home/cr1tterp0wer/Projects/Match-it
$ javac -cp $MATCH_IT/lib/jars/lwjgl_applet.jar:$MATCH_IT/lib/jars/lwjgl.jar:$MATCH_IT/lib/jars/lwjgl_util.jar:$MATCH_IT/lib/jars/slick.jar $MATCH_IT/src/**/*.java -d ./bin/

RUN

$ java -Djava.library.path=$MATCH_IT/lib/res/linux -cp $MATCH_IT/lib/jars/lwjgl_applet.jar:$MATCH_IT/lib/jars/lwjgl.jar:$MATCH_IT/lib/jars/lwjgl_util.jar:$MATCH_IT/lib/jars/slick.jar:$MATCH_IT/bin MAIN.Match3

