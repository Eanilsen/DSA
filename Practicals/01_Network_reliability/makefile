JC = javac
JVM = java
JAVADOC = javadoc
MKBIN = mkdir -p bin

JC_FLAGS = -g -d 'bin/'
JC_CP = -cp

MAINSRC = src/main/java/
TESTSRC = src/test/java/
LIB = 'src/test/lib/*:src/main/java/dsa/*'

PKGSRC = dsa/
TESTPKG = dsa_test/
TARGET = bin

MAIN = dsa.Main

MAINTEST = dsa_test.MainTest

.SUFFIXES: .class .java

all:
	$(MKBIN)
	$(JC) $(JC_FLAGS) $(MAINSRC)$(PKGSRC)*

test:
	$(JC) $(JC_FLAGS) $(JC_CP) $(LIB) $(TESTSRC)$(TESTPKG)*

clean:
	rm -rf $(TARGET)

run:
	$(JVM) $(JC_CP) $(TARGET) $(MAIN)

run_test:
	$(JVM) $(JC_CP) $(TARGET) $(MAINTEST)

.PHONY: all test clean run run_test
