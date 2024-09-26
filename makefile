test:
	./gradlew test

build-parser:
	bison parser.y -o app/src/main/java/org/projectD/interpreter/parser/Parser.java