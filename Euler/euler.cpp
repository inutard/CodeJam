#include <Windows.h>
#include <boost/dynamic_bitset.hpp>
#include <iostream>
#include <fstream>
#include <string>
#include <map>
#include <set>
#include <boost/assign/list_of.hpp>
#include <vector>
#include <algorithm>
#include <cassert>
#include <queue>
#include <functional>
#include <cmath>
using namespace std;
using namespace boost;

typedef unsigned long long ull;

template<typename T, typename InputIterator>  
void Print(std::ostream& ostr,   
           InputIterator itbegin,   
           InputIterator itend,   
           const std::string& delimiter)  
{  
    std::copy(itbegin,   
              itend,   
              std::ostream_iterator<T>(ostr, delimiter.c_str()));  
}  


ull getPentagonal(int n) 
{
	return 1ull * n * (3*n - 1) / 2;
}

bool isPentagonal(ull num)
{
	ull solveForN = 1 + 24ull * num;
	solveForN = (ull) sqrt(solveForN);

	solveForN = (1 + solveForN) / 6;

	//solveForN = floor(solveForN) + 0.5;

	int n = (int) solveForN;

	ull check_num = getPentagonal(n);

	return check_num == num;
}

void problem44() 
{
		int upperLimit = 30000;

		set<ull> pentNums;
		vector<ull> pentNumsList(upperLimit);

		for(int n = 1; n <= upperLimit; ++n)
		{
			ull pentNum = getPentagonal(n);
			pentNumsList[n-1] = pentNum;
			pentNums.insert(pentNum);
		}

		for(int j = 1; j <= upperLimit; ++j)
		{
			for(int k = 1; k < j; ++k)
			{
				ull pentJ = pentNumsList[j-1];
				ull pentK = pentNumsList[k-1];

				assert(pentJ > pentK);

				ull sum = pentJ+pentK;
				ull diff = pentJ - pentK;

				if (pentNums.find(sum) != pentNums.end() && pentNums.find(diff) != pentNums.end())
				{
					printf("Pair %llu %llu  Diff = %llu \n", pentJ, pentK, diff);
					return;
				}
			}
		}
	
}

void problem45() 
{
	ull hexIdx = 143+1;
	ull pentIdx = 165+1;
	ull triIdx = 285+1;

	ull hex = 3;
	ull pent = 2;
	ull tri = 1;

	while(true)
	{
		if (hex == pent && hex == tri) 
		{
			printf("Solution tri %llu pent %llu  hex %llu.  Num = %llu \n", triIdx, pentIdx, hexIdx, hex);
			return;
		}
		ull curMax = max( max(tri,pent), hex);

		if (tri < curMax) 
		{
			++triIdx;
			tri = triIdx*(triIdx+1) / 2;
		}

		if (pent < curMax) 
		{
			++pentIdx;
			pent = pentIdx * (3*pentIdx-1) /2;
		}

		if (hex < curMax) 
		{
			++hexIdx;
			hex = hexIdx * (2*hexIdx - 1);
		}
	}
}



void generatePrimes(int n, vector<unsigned int>& primes);

void problem46() 
{
	vector<unsigned int> primes;
	generatePrimes(1000000, primes);

	//cout << primes.size() << endl;

	for(unsigned int pUpperBoundIdx = 1; pUpperBoundIdx < primes.size(); ++pUpperBoundIdx)
	{
		//cout << "Prime upper bound  " << primes[pUpperBoundIdx] << endl;

		//Loop through all odd numbers between this prime and previous
		for(unsigned int odd = primes[pUpperBoundIdx-1] + 2; odd <= primes[pUpperBoundIdx] - 2; odd += 2)
		{
			//cout << "Odd number " << odd ;
			bool foundIt = true;

			//Go through all primes lower than pUpperbound
			for(int primeIdx = pUpperBoundIdx - 1; primeIdx >= 0; --primeIdx)
			{
				int prime = primes[primeIdx];

				int rest = odd  - prime;
				assert(rest > 0);

				int sq = 1;
				while( (2 * sq * sq) < rest)
				{
					++sq;
				}

				if ( (2 * sq * sq) == rest) 
				{
					//cout << " equals prime " << prime << " + 2 * " << sq << " squared " << endl;
					foundIt = false;
					break;
				}
			}
						
			if (foundIt) 
			{
				cout << "Found it " << odd << endl;
				return;
			}
			
		}
	}

}

typedef unsigned int uint; 

void problem47()
{
	//Factor n
	int conseqNums = 4;

	const int upperLimit = 1250000;

	vector<uint> primes;
	generatePrimes( static_cast<uint>(sqrt(upperLimit)), primes);

	for(int n = 1; n <= upperLimit; ++n)
	{
		bool foundIt = true;

		for(int cIdx = 0; cIdx < conseqNums; ++cIdx)
		{
			int factors = 0;
			for(vector<uint>::const_iterator it = primes.begin(); it != primes.end(); ++it)
			{
				if ( (n+cIdx) % *it == 0)
				{
					++factors;
				}
			}

			if (factors != conseqNums)
			{
				foundIt = false;
				break;
			}
		}

		if (foundIt)
		{
			cout << "Found it " << n << endl;
			return;
		}
		
	}
}

uint getUsedDigits(uint num);

void problem49() 
{
	vector<uint> primes;
	generatePrimes(10000, primes);

	for(vector<uint>::const_reverse_iterator largest = primes.rbegin();
		largest != primes.rend() && *largest >= 1000; ++largest)
	{
		for(vector<uint>::const_reverse_iterator middle = 1 + largest;
			middle != primes.rend() && *middle >= 1000;
			++middle)
		{
			if (getUsedDigits(*middle) != getUsedDigits(*largest))
				continue;
			
			for(vector<uint>::const_reverse_iterator smallest = 1 + middle;
				smallest != primes.rend() && *smallest >= 1000; ++smallest)
			{
				if (getUsedDigits(*middle) != getUsedDigits(*smallest) || *largest - *middle != *middle - *smallest)
					continue;
			
				cout << *smallest << " " << *middle << " " << *largest << endl;
			}

		}		
	}
}


void problem50()
{
	const int upperLimit = 1000000;
	vector<uint> primes;
	generatePrimes(upperLimit, primes);

	set<uint> primeSet;
	primeSet.insert(primes.begin(), primes.end());

	uint currentMaxTerms = 0;

	for(vector<uint>::const_iterator pStart = primes.begin();
		pStart != primes.end();
		++pStart)
	{
		uint sum = *pStart;
		uint terms = 1;

		for(vector<uint>::const_iterator pStop = 1 + pStart;
		pStop != primes.end();
		++pStop) 
		{
			++terms;
			sum += *pStop;

			if (sum >= upperLimit)
				break;

			if (terms > currentMaxTerms && primeSet.find(sum) != primeSet.end())
			{
				currentMaxTerms = terms;
				cout << "New max "<< currentMaxTerms << " sum " << sum 
					<< " adding " << *pStart << " to " << *pStop << endl;
			}
		}
	}
	
}

/*
I solved it for non consecutive primes!
*/

void problem50_wrong()
{
	const int upperLimit = 1000000;
	vector<uint> primes;
	generatePrimes(upperLimit, primes);

	vector<int> maxTerms(upperLimit+1, -1);
	vector<int> prevTerm(upperLimit+1, -1);

	//Print<uint, vector<uint>::iterator>(cout, prevTerm.begin(), prevTerm.end(), ", ");
	//Print<int, vector<int>::iterator>(cout, prevTerm.begin(), prevTerm.end(), ", ");

	prevTerm[0] = -1;
	maxTerms[0] = 0;
	
	for(vector<uint>::const_iterator pIt = primes.begin(); pIt != primes.end(); ++pIt)
	{
		vector<int> nextTerms(maxTerms);

		uint prime = *pIt;

		cout << "Processing prime " << prime << endl;

		for(int prev = 0; prev+prime <= upperLimit; ++prev) 
		{
			if(maxTerms[prev] < 0)
				continue;

			int terms = 1 + maxTerms[prev];

			if (terms > maxTerms[prev + prime])
			{
				nextTerms[prev + prime] = terms;
				prevTerm[prev + prime] = prev;
			}
		}

		maxTerms = nextTerms;
		/*
		cout << "\n\nAfter prime " << prime << endl;
		cout << "Max " << endl;
		Print<int, vector<int>::iterator>(cout, maxTerms.begin(), maxTerms.end(), ", ");
		cout << "\n\nPrev " << endl;
		Print<int, vector<int>::iterator>(cout, prevTerm.begin(), prevTerm.end(), ", ");
		*/
	}

	int maxTermCount = 0;

	cout << endl << endl;
	for(vector<uint>::const_iterator pIt = primes.begin(); pIt != primes.end(); ++pIt)
	{
		uint prime = *pIt;
		if (maxTerms[prime] > maxTermCount)
		{
			maxTermCount = maxTerms[prime];

			cout << "New maximum with " << maxTermCount << " terms. " << prime << " = ";
			int next = prevTerm[prime];
			int prev = prime;
			while(next >= 0)
			{
				cout << prev - next;
				prev = next;
				next = prevTerm[next];
				if (next >= 0) {
					cout << " + ";
				}
			}

			cout << endl;
		}
	}
}

uint replaceDigit(uint num, uint digitToReplace, uint newDigit)
{
	uint ret = 0;
	uint powTen = 1;

	while(num > 0)
	{
		uint digit = num % 10;
		
		digit = digit == digitToReplace ? newDigit : digit;

		ret += digit * powTen;

		powTen *= 10;
		num /= 10;
	}

	return ret;
}

void problem51() 
{
	const uint upperLimit = 1000000;
	const int replaceUpTo = 2;

	vector<uint> primes;
	generatePrimes(upperLimit, primes);

	set<uint> primeSet;
	primeSet.insert(primes.begin(), primes.end());

	uint currentMax = 0;

	uint maxCount = 0;

	for(vector<uint>::const_iterator pIt = primes.begin(); pIt != primes.end(); ++pIt)
	{
		const uint prime = *pIt;

		for(int digitToReplace = 0; digitToReplace <= replaceUpTo; ++digitToReplace)
		{
			uint count = 1;
			for(int newDigit = digitToReplace+1; newDigit <= 9; ++newDigit)
			{
				uint rep = replaceDigit(prime, digitToReplace, newDigit);

				if (rep == prime)
					break;

				if (primeSet.find(rep) != primeSet.end()) 
					++count;
			}

			if (count > maxCount) 
			{
				cout << "New max " << count << " with prime " << prime << " and digit " << digitToReplace << endl;
				maxCount = count;
			}
		}
	}
}

void problem52()
{
	uint multTarget = 6;

	for(uint num = 1; ; ++num)
	{
		uint digits = getUsedDigits(num);
		for(uint m = 2; m <= multTarget; ++m) 
		{
			uint digitsM = getUsedDigits(num * m);

			if (digitsM != digits) 
				break;

			if (m >= multTarget) 
			{
				cout << "num= " << num << " up to m " << m << endl;
				return;
			} 
		}
	}
}

void problem53()
{
	const int limit = 100;
	const uint goal = 1000000;

	uint combinations[limit+1][limit+1];
    for(int n = 0; n <= limit; ++n)
        for(int k = 0; k <= limit; ++k)
    {
        if (n<k)
            combinations[n][k] =0;
        else if (n==k || k==0 )
            combinations[n][k] = 1;
        else
            combinations[n][k] = (combinations[n-1][k] + combinations[n-1][k-1]);

		combinations[n][k] = min(goal+1, combinations[n][k]);
    }

	uint count = 0;

	for(int n = 1; n <= limit; ++n)
		for(int r = 0; r <= n; ++r)
		{
			uint cr = combinations[n][r];
			//printf("%d C %d = %d\n", n, r, cr);
			if (cr > goal)
				++count;
		}

	cout << "Count is " << count << endl;
}

uint concatNums(uint left, uint right);

bool miller_rabin_32(uint n);
bool isPrime(uint n);

void cutInt() {
    /*
    uint cutPoint = 10;

		while(*p > cutPoint)
		{
			uint left = *p / cutPoint;
			uint right = *p % cutPoint;

			//avoid leading zeros
			if (right < cutPoint / 10) {
				cutPoint *= 10;
				continue;
			}

			//% 10 >= 1
			//% 100 >= 10

			cutPoint *= 10;
			*/
}

void printK(const vector<set<uint>>& ki, int kSize)
{
	cout << "Printing k" << kSize << endl;
	for( auto kIt = ki.begin(); kIt != ki.end(); ++kIt)
	{
		cout << "Elems  ";
		for(auto kElemIt = kIt->begin(); kElemIt != kIt->end(); ++kElemIt)
		{
			cout << *kElemIt << " ";
		}
		cout << endl;
	}
}

void buildNextK( const vector<set<uint>>& k, vector<set<uint>>& kNext, const uint kSize, const map<uint, set<uint>> & pairablePrimes)
{
	//Build K_n+1 by trying to combine 2 of K_n that share all but the last element
	for(auto kIt_a = k.begin(); kIt_a != k.end(); ++kIt_a)
	{
		for(auto kIt_b = kIt_a+1; kIt_b != k.end(); ++kIt_b)
		{
			//attempt to pair AB and AC with A < B and  A < C

			assert(kIt_a->size() == kIt_b->size());
			assert(kIt_a->size() == kSize);

			auto oneBeforeEnd = kIt_a->rbegin();
			oneBeforeEnd++;

			bool eq = equal(kIt_a->begin(), oneBeforeEnd.base(), kIt_b->begin());

			/*
			* We can break here because the k sets are sorted. 
			*/
			if (!eq)
				break;

			uint b = *kIt_a->rbegin();
			uint c = *kIt_b->rbegin();

			auto ppIt = pairablePrimes.find(b);

			//Are b and c connected
			if (ppIt->second.find(c) == ppIt->second.cend())
				continue;

			set<uint> kEntry(kIt_a->begin(), oneBeforeEnd.base());
			kEntry.insert(b);
			kEntry.insert(c);
			kNext.push_back(kEntry);

		}
	}
}

void problem60() 
{
	const int primeLimit = 10000;
	vector<unsigned int> primes;
	generatePrimes(primeLimit, primes);

	set<uint> primeSet(primes.begin(), primes.end());

	map<uint, set<uint>> pairablePrimes;

	vector<set<uint>> k2;	

	for(auto p = primes.begin(); p != primes.end(); ++p)
	{
		for(auto p2 = p + 1; p2 != primes.end(); ++p2)
		{

			uint left = *p;
			uint right = *p2;

			uint c1 = concatNums(right, left);
			uint c2 = concatNums(left, right);

			if (!isPrime(c1))
				continue;

			if (!isPrime(c2))
				continue;


			pairablePrimes[ left ].insert(right);
			pairablePrimes[ right ].insert(left);

			k2.push_back( set<uint>() );
			( *k2.rbegin() ).insert(left);
			( *k2.rbegin() ).insert(right);

		}
	}

	vector<set<uint>> k3, k4, k5;

	buildNextK(k2, k3, 2, pairablePrimes);
	buildNextK(k3, k4, 3, pairablePrimes);
	buildNextK(k4, k5, 4, pairablePrimes);

	printK(k5, 5);

	return;
}

#include "prettyprint.h"

vector<uint> genFigurateNumberSet( function< uint(uint)> genFunc )
{
	vector<uint> ret;

	uint n = 0;
	uint val = 0;

	while(val < 10000)
	{
		if (val >= 1000)
			ret.push_back(val);

		++n;
		val = genFunc(n);
	}

	return ret;

}

class node61
{
public:
	vector<uint> sequence;
	vector<uint> pOrigin;
	uint visited;

	node61() : visited(0) {}
};

void problem61() 
{
	vector<uint> pSet[] = {
		genFigurateNumberSet( [](uint n) { return n * (n+1) / 2; } ),
		genFigurateNumberSet( [](uint n) { return n * n; } ),
		genFigurateNumberSet( [](uint n) { return n * (3*n-1) / 2; } ),
		genFigurateNumberSet( [](uint n) { return n * (2*n-1); } ),
		genFigurateNumberSet( [](uint n) { return n * (5*n-3) / 2; } ),
		genFigurateNumberSet( [](uint n) { return n * (3*n-2) ; } )
	};

	for(int i = 0; i < 6; ++i) {
		cout << "P" << 3+i << pSet[i] << endl << endl;
	}

	//Initialize with p8 
	queue<node61> toVisit;

	for(uint p8 : pSet[5])
	{
		node61 node;
		node.visited |= 1 << 5;
		node.sequence.push_back(p8);
		node.pOrigin.push_back(8);
		toVisit.push(node);
	}

	while(!toVisit.empty())
	{
		node61 top = toVisit.front();

		toVisit.pop();
		
		for(int pSetIdx = 0; pSetIdx <= 5; ++pSetIdx)
		{
			//Already have a number from that set
			if ( (top.visited & (1 << pSetIdx)) != 0)
				continue;

			//Last 2 digits of previous num in sequence
			uint last2 = *top.sequence.rbegin() % 100;

			for(uint num : pSet[pSetIdx])
			{

				uint firstTwo = num / 100;

				if (last2 != firstTwo)
					continue;

				if (top.sequence.size() == 5) {
					//must also match beginning of sequence
					uint last2 = num % 100;
					uint first2Seq = top.sequence[0] / 100;

					if (last2 != first2Seq)
						continue;
				}

				node61 newNode(top);
				newNode.sequence.push_back(num);
				newNode.visited |= 1 << pSetIdx;
				newNode.pOrigin.push_back(3+pSetIdx);
				toVisit.push(newNode);

				if (newNode.sequence.size() == 6) {
					cout << "Found it ! " << newNode.sequence << endl;
					cout << newNode.pOrigin << endl << endl;

					uint sum_of_elems=0;
					std::for_each(newNode.sequence.begin(),newNode.sequence.end(),[&](int n){
						sum_of_elems += n;
					});

					cout << "Sum " << sum_of_elems << endl;
				}
			}
		}
	}
}

typedef unsigned long long ull;

void problem62()
{
	map<string, vector<pair<uint,ull>>> digitsToNum;

	for(uint num = 1; num <= 400000; ++num)
	{
		ull cube = (ull)num*num*num;
		string digits = to_string(cube);

		sort(digits.begin(), digits.end());

		digitsToNum[digits].push_back(make_pair(num,cube));

		
	}
	
	uint minCub = 100000000;


	for (auto it = digitsToNum.begin(); it != digitsToNum.end(); ++it)
	{
		if (it->second.size() == 5 && it->second[0].first < minCub)
		{
			minCub = it->second[0].first;
			cout << "Perm groupe " << it->second << endl;
		}
	}

}

uint countDigits(ull num);

void problem63() 
{
	uint lb = 1;

	uint power = 1;
	uint count = 0;
	for(uint power = 1; power <= 19; ++power)
	{
		for(uint base = 1; base < 10; ++base)
		{
			ull num = base;
			for(uint p = 1; p < power; ++p)
			{
				num *= base;
			}
			uint dc = countDigits(num);

			if (dc > power)
				break;

			if (dc < power)
				continue;

			cout << "Found " << base << " ^ " << power << endl;
			++count;
		}
	}

	cout << "Count " << count << endl;
}




int main2() {
	ull start = GetTickCount64();
	problem63();
	ull end = GetTickCount64();

	cout << "Elapsed ms " << end-start << endl;
	return 0;
}

uint concatNums(uint left, uint right)
{
	//123 ; 456
	uint rightDigits = right;

	while(rightDigits > 0)
	{
		rightDigits /= 10;
		left *= 10;
	}
	return left+right;
}

uint countDigits(ull num)
{
	uint ret = 0;
	while(num > 0)
	{
		ret ++;
		num /= 10;
	}

	return ret;
}

uint getUsedDigits(uint num)
{
	uint ret = 0;
	while(num > 0)
	{
		uint digit = num % 10;
		ret |= 1 << digit;

		num /= 10;
	}

	return ret;
}

bool isPrime(uint n) {
	return miller_rabin_32(n);
}

int modular_exponent_32(int base, int power, int modulus) {
	unsigned long long result = 1;
	for (int i = 31; i >= 0; i--) {
		result = (result*result) % modulus;
		if ((power & (1 << i)) != 0) {
			result = (result*base) % modulus;
		}
	}
	return (int)result; // Will not truncate since modulus is an int
}




bool miller_rabin_pass_32(int a, int n) {
	int d = n - 1;
	int s = 0;

	while ((d % 2) == 0) {
		d /= 2;
		s++;
	}

	int a_to_power = modular_exponent_32(a, d, n);
	if (a_to_power == 1) return true;
	for (int i = 0; i < s-1; i++) {
		if (a_to_power == n-1) return true;
		a_to_power = modular_exponent_32(a_to_power, 2, n);
	}
	if (a_to_power == n-1) return true;
	return false;
}

bool miller_rabin_32(uint n) {
	if (n <= 1) return false;
	else if (n == 2) return true;
	else if (miller_rabin_pass_32( 2, n) &&
		(n <= 7  || miller_rabin_pass_32( 7, n)) &&
		(n <= 61 || miller_rabin_pass_32(61, n)))
		return true;
	else
		return false;
}

void generatePrimes(int n, vector<unsigned int>& primes) 
{
	dynamic_bitset<> isPrime(n+1, 0); 
	isPrime.flip();
	isPrime[0] = 0;
	isPrime[1] = 0;

	primes.clear();

	//Since we are eliminating via prime factors, a factor is at most sqrt(n)
	unsigned int upperLimit = static_cast<unsigned int>(sqrt(n));

	for(unsigned int i = 2; i <= upperLimit; ++i) {
		if (!isPrime[i]) {
			continue;
		}

		//Loop through all multiples of the prime factor i.  Start with i*i, because the rest
		//were already covered by previous factors.  Ex, i == 7, we start at 49 because 7*2 through 7*6 
		//we already covered by previous prime factors.
		for(int j = i * i; j <= n; j += i) {
			isPrime.set(j, false);
		}
	}

	for (size_t i = isPrime.find_first(); i != boost::dynamic_bitset<>::npos; i = isPrime.find_next(i)) {
		primes.push_back(static_cast<unsigned int>(i));
	}

}

