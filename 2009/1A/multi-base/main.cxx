#include <fstream>
#include <iostream>
#include <iomanip>
#include <vector>
#include <algorithm>
#include <limits>
#include <map>
#include <set>
#include <sstream>
#include <deque>

#include <stdio.h>
#include <time.h>
#include <assert.h>
#include <stdlib.h>

#include "util.h"
#include <cstring>

using namespace std;


void do_test_case(int, ifstream& input);

int main(int argc, char** args)
{
    
    LOG_OFF();
    
  if (argc < 2) {
    cerr << "Usage: <exe> input_file" << endl;
    return -1;
  }
  
  ifstream input;
  input.open(args[1]);
  
  int T;
  input >> T;

  string dummy;
  getline(input, dummy);
  SHOW_TIME_BEGIN(g) 
  	
  for (int test_case = 0; test_case < T; ++test_case) {
      
    do_test_case(test_case, input);   
  }
      
  SHOW_TIME_END(g)
}

typedef pair<int, int> IntPair;
typedef deque<int> BaseNum;

deque<int> baseConv(const int K, const int base) {
	deque<int> conNum;

	int k = K;

	do {
		conNum.push_front(k % base);
		k /= base;
	}
	while (k != 0);

	return conNum;
}

int squares[11] = { 0, 1, 4, 9, 16, 25, 36, 49, 64, 81, 100 };

int getSq(const deque<int>& convNum) {
    int r = 0;
    for(BaseNum::const_iterator it = convNum.begin();
        it != convNum.end();
        ++it) {
        r += squares[ (*it) ];
    }
    
    return r;
}

class Node {
    
public:
    bool happy ;
    bool init;
        
    Node() : happy(false), init(false) { 
           
    }
};

ostream& operator<<(ostream& os, Node* node) {
    os << (int) node;
    os << node->happy;
    return os;
}

template<typename T> ostream& operator<<(ostream& os, const vector<T>& vect)
{
  cout << "Size [" << vect.size() << "] " ;
  
  typename std::vector<T>::const_iterator it;
  
  for(it = vect.begin(); 
    it != vect.end(); ++it) {
    cout << *it << ", ";   
  }
  cout << endl;
  return os;
}

template std::ostream& operator<<(std::ostream& os, const std::vector<Node*>& vect);

typedef vector<Node*> VecNode;

VecNode nodes[9];

void do_test_case(const int test_case, ifstream& input)
{
    SHOW_TIME_BEGIN(tc);
    LOG_ON();
    LOG_OFF();
    string text;
    getline(input, text);

    LOG(text);
    stringstream iStr(text);

    vector<int> bases;

    while(iStr) {
        int b;
        iStr >> b;
        if (iStr) {
            bases.push_back(b);
            LOG(b);
        }
    }
   
    
    assert(bases.size() > 0);
    //cout << text << endl;

    int K = 2;

    while(true) {
        int allHappy = true;
        
        for(vector<int>::const_iterator it = bases.begin();
            it != bases.end();
            ++it) {
       // allHappy=true;
            int base = *it;
            //LOG_ON();
            //LOG(base);
            
            VecNode& bas = nodes[base - 2];
            
            int num = K;
    
            vector<int> visitedVec;
            
            while (num != 1) {
                
                deque<int> conNum = baseConv(num, base);
                LOG_OFF();
                //LOG_ON();
                LOG_STR("Starting inner loop");
                LOG(num);
                LOG(base);
                LOG(conNum);
                
                
                int newNum = getSq(conNum);
                
                visitedVec.push_back(newNum);
                
                //LOG(bas);
                //LOG_STR(num << " in base " << base << " = " << conNum << " square = " << newNum);
                while (bas.size() < newNum ) {
                    bas.push_back(new Node());
                    //LOG(bas);
                }
                                         
                if (bas[newNum-1]->happy) {                    
                    break;
                } else if (bas[newNum-1]->init) {
                    allHappy = false;                    
                    break;
                }
                
                bas[newNum-1]->init = true;
                                
                num = newNum;
                
            } // while
            
            if (!allHappy) {
                                
                break;
            } else {
                //mark all nodes as happy
                //LOG_ON();
                
                LOG_STR( K << " is happy in base " << base );
                for(vector<int>::const_iterator it = visitedVec.begin();
                    it != visitedVec.end();
                    ++it) {
                    bas[ (*it) - 1 ]->happy=true;
                }
            }
        }		
        
        if (allHappy ) {
            cout << "Case #" << (1+test_case) << ": " << K << endl;
            SHOW_TIME_END(tc);
            return;
        } else {
            allHappy = true;
        }
        ++K;
        LOG_ON();
        LOG_OFF();
        LOG(K);

    }
        
    
}
  
