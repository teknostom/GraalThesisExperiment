javac Main.java
native-image Main -o uc1Main --pgo=../profiles/UC1.iprof
