# Determine the operating system
ifeq ($(OS),Windows_NT)
    MV = move
    RM = del /Q
    RMDIR = rmdir /S /Q
    MKDIR = mkdir
    PATH_SEP = \\
else
    MV = mv
    RM = rm -f
    RMDIR = rm -rf
    MKDIR = mkdir -p
    PATH_SEP = /
endif

# Executable paths
JAVA_EXEC = java
JAVAC_EXEC = javac

# Source Directory
DIR_SRC = github$(PATH_SEP)erfan526z$(PATH_SEP)tictactoeagent

# Output Directory for class files
DIR_OUT = bin

# Output Directory for executable jar file
DIR_JAROUT = release

# Output .jar name
JAR_NAME = TicTacToeAgent.jar

# Main file name (without .java)
MAIN_FILE = github.erfan526z.tictactoeagent.TicTacToe

# Java flags
JAVA_FLAGS = -g

# List of all .java files
SOURCES = $(wildcard $(DIR_SRC)$(PATH_SEP)*.java)

# List of all .class files
CLASSES = $(SOURCES:$(DIR_SRC)$(PATH_SEP)%.java=$(DIR_OUT)$(PATH_SEP)%.class)

# Default target
all: $(DIR_OUT) $(JAR_NAME)

# Compile .java files to .class files
$(DIR_OUT)$(PATH_SEP)%.class: $(DIR_SRC)$(PATH_SEP)%.java
	$(JAVAC_EXEC) $(JAVA_FLAGS) -d $(DIR_OUT) $<

# Creating .jar file (mv is messy over there, will correct it later)
$(JAR_NAME) : $(CLASSES) $(DIR_JAROUT)
	jar cfe $(JAR_NAME) $(MAIN_FILE) -C $(DIR_OUT) .
	$(MV) $(JAR_NAME) $(DIR_JAROUT)$(PATH_SEP)$(JAR_NAME)

# Create output direcory
$(DIR_OUT):
	$(MKDIR) $(DIR_OUT)

# Create output directory for jar
$(DIR_JAROUT):
	$(MKDIR) $(DIR_JAROUT)

# Clean target
clean:
	$(RMDIR) $(DIR_OUT) $(DIR_JAROUT)
	
# Run target
run:
	$(JAVA_EXEC) -jar $(DIR_JAROUT)$(PATH_SEP)$(JAR_NAME)

# Cleans only temporary .class files and not the .jar
cleantemp:
	$(RMDIR) $(DIR_OUT)

.PHONY: all clean run cleantemp

