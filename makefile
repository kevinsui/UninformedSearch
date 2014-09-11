JCC = javac

default: Main.class Search.class Node.class

agent: Main.class Search.class Node.class

Main.class: Main.java
	$(JCC) $(JFLAGS) Main.java

Search.class: Search.java
	$(JCC) $(JFLAGS) Search.java

Node.class: Node.java
	$(JCC) $(JFLAGS) Node.java

run: Main.class
	java Main

clean:
	$(RM) *.class