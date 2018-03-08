all:
	rm -rf bin/
	cp -r src bin/
	find bin -name "*.java" -print | xargs javac
	find bin -type f -name '*.java' -delete

build: all

client:
	java -classpath bin/ chat.client.Application

server:
	java -classpath bin/ chat.server.Application

clean:
	find . -type f -name '*.class' -delete
