JFLAGS = -g
JC = javac

CLASSES = \
		  TowersOfHanoi.java \
		  Main.java

.SUFFIXES: .java .class

.java.class:
	$(JC) $(JFLAGS) $*.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	rm -rf *.class
