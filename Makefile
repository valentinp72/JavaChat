all:
	rm -rf bin/
	cp -r src bin/
	find bin -name "*.java" -print | xargs javac
	find bin -type f -name '*.java' -delete

build: all

clientStart:
	java -classpath bin/ chat.client.Application

serverStart:
	java -classpath bin/ chat.server.Application ${ARGS}

documentation:
	javadoc -sourcepath ./src -d ./doc -subpackages .

clean:
	find . -type f -name '*.class' -delete
