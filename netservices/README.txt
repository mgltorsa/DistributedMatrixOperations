frascati compile src/main/java broker
frascati run src/main/resources/broker -libpath broker.jar

to test
frascati compile src/main/java testbroker
frascati run src/main/resources/testbroker -libpath testbroker.jar -s r -m run
