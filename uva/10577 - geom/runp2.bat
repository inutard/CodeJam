python c:\codejam\codejam\lib\replaceInFile.py main.cpp geom
make clean all  > out.txt 2>&1
cat out.txt
g++ -O2 p2.cpp -o p2.exe

gen_data.py > data.txt
cat data.txt | project > out.txt
cat data.txt | p2 > out2.txt
diff out.txt out2.txt