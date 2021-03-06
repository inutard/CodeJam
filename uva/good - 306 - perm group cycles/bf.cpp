#include "stdio.h"
#include <iostream> 
#include <vector>

using namespace std;

#define FOR(k,a,b) for(int k=(a); k <  (b); ++k)
 
typedef vector<int> vi;
typedef vector<vi> vvi;

const int MAX_KEYVAL_SIZE = 201;
int keyValues[201];
int N;

const bool debug = false;


string encodeStr( const string& str)
{
	//printf("Str len %d N %d\n", str.length(), N);
	
		
	string ret(str);
	//puts("th");
	
	for(int i = 0; i < str.length(); ++i)
	{
		//printf("%d = %d\n", keyValues[i] - 1, i);
		ret[ keyValues[i]  ] = str[i];
	}
	return ret;
}


int main()
{
	int t = 0;
	while(scanf("%d", &N) == 1 && N)
	{
		if (t++)
			puts("");
			
		for(int i = 0; i < N; ++i)
		{
			scanf("%d", &keyValues[i]);
			//convert to 0 index
			--keyValues[i];
		}
		
		
		int k;
		while(1 == scanf("%d", &k) && k)
		{
			string str;
			getline(cin, str);
			
			//Get rid of leading space
			str.erase(str.begin());
			//cout << "[" << str <<  "]" << endl;
			
			if (str.length() < N)
				str.append( N - str.length(), ' ');
			
			const string orig = str;
			
			
			
			for(int i = k-1; i >= 0; --i)
			{
				str = encodeStr(str);
				//if (str == orig)
				{
					//int cycleLen = k-i ;
					//printf("Found cycle len %d at i=%d\n", cycleLen, i);
					//i %= cycleLen; 
					//printf("i = %d\n", i);
					
				}
				//printf("k=%d str = [%s]\n", k, str.c_str());
			}
			
			cout << str << endl;
			//cout << "brute force [" << str <<  "]" << endl;
			
		}
		
		
	}

	return 0;
}