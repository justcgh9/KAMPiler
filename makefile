test:
	./gradlew test

test-info:
	./gradlew test --info


build-parser:
	bison parser.y -o app/src/main/java/org/projectD/interpreter/parser/Parser.java

parse:
	@if [ -z "$(filepath)" ]; then \
		echo "Usage: make parse filepath=<your_argument>"; \
		exit 1; \
	fi
	@echo "Running Java program with argument: $(filepath)"
	java app/src/main/java/org/projectD/interpreter/main/Main.java $(filepath) 