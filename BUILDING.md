# To build the project, you need to have the following tools installed:
- Native-image
- GraalVM

# To build the project follow the steps below:

1. Clone the repository
2. Go into the program directory
3. Run the following command:
```shell
native-image --pgo-instrument Main
```
4. Generate the profile-guided optimization data:
```shell
./Main -XX:ProfilesDumpFile=(The name of the file you want to save the data, i.e NO.iprof, UC1.iprof, etc)
```
During the runtime, you have to load the service with the right profiling data. To do this, you have to run the following command:
```shell
ab -n 1000 -c 1 http://127.0.0.1:8000/(UC1, or UC2)
```

Once you have generated the files, you can use them to build the project with the following command:
```shell
../compile.sh
```
This will automatically compile the configured version of the project with the profile-guided optimization data you have generated.
It uses the following files:
- NO.iprof
- UC1.iprof
- UC2.iprof
- Full.iprof
5. Run the compiled version of the project:
```shell
./Main
```
or run all the files in order and save the data using the following command:
```shell
../runTests.sh
```
When you run the project you can use ab again to test the performance of the project. You can use the following command:
```shell
ab -n 1000 -c 1 http://127.0.0.1:8000/(UC1, or UC2)
```
The program will output the times for each request to system.out, and you can pipe that result to a file if you want to save the data.
The sum.py script can be used to create a graph of the data. The script uses all .res files in the directory it is run in, so make sure to only have the files you want to use in the directory.
The script is not done yet, so we need to make it better. The script is run with the following command:
```shell
python3 ../sum.py
```
