javac Main.java
native-image Main -o uc2Main --pgo=../profiles/UC2.iprof
