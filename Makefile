all: clean build run

build:
	@echo "Building..."
	@javac -d bin src/*.java

run:
	@echo "Running..."
	@java -cp bin App

clean:
	@echo "Cleaning..."
	@rm -rf bin/*