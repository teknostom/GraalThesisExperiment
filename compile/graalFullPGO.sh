javac Main.java
native-image Main -o fullMain --pgo=../profiles/Full.iprof
