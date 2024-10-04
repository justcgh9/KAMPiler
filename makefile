test:
	./gradlew clean test

test-info:
	./gradlew test --info


build-parser:
	bison parser.y -o app/src/main/java/org/projectD/interpreter/parser/Parser.java