First start multiplicator:

frascati compile src/main/java matrix
frascati run src/main/resources/operations -libpath matrix.jar

Then deploy the tiff processor:

frascati compile src/main/java operations
frascati run src/main/resources/operations -libpath operations.jar -s r -m run


