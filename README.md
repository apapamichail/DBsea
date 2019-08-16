# DBsea
Database style extraction and assesment. The tool takes as input either a single version
of a particular schema, or the entire evolution history of the schema, which consists of 
a set of schema versions, from the birth of the schema to its last known version. It is 
also possible to conduct style analysis in the evolution history of multiple schemata, 
in this case Dbsea will parse the schemata in depth first fashion. 
The SQL style checking analysis is based on a set of rules introduced in two well-known 
sources of good coding practices, namely J. Celko's SQL programming style, and R. Martin's
book on clean code. Moreover, I consider rules derived from my own experience as developer.

To run DBsea please consider the following steps:

1. Download wordnet from here http://wordnetcode.princeton.edu/3.0/WordNet-3.0.tar.gz 
and set it in your machines path in this manner "C:\WordNet\3.0"

2. Set the external references consisting of the jars below: 
	*antlr-runtime-4.4.jar 
	*commons-math3-3.6.1.jar
	*jaws-bin.jar
	*stanford-corenlp-3.9.2.jar
	*stanford-corenlp-3.9.2-models.jar
	*org-netbeans-swing-outline.jar
	*protobuf-java-3.5.1.jar

And you are ready to conduct your analysis!
