#include <stdarg.h>
#include <time.h>
#include "tri_logger.hpp"

#ifndef UTIL_H
#define UTIL_H

#ifndef SHOW_TIME
#define SHOW_TIME 0
#endif

//#undef assert
//#define assert(x) ((void)0)

#define LOG_STR_INFO(str) do{}while(false)
#define LOG_INFO(str) do{}while(false)
#define LOG_STR_DEBUG(str) do{}while(false)
#define LOG_DEBUG(str) do{}while(false)



#if SHOW_TIME
#define SHOW_TIME_BEGIN(A) clock_t begin_##A=clock();
#define SHOW_TIME_END(A) clock_t end_##A=clock(); cout << "Time elapsed: " #A << " " << double(diffclock(end_##A,begin_##A)) << " ms" << endl;
#else
#define SHOW_TIME_BEGIN(A) 
#define SHOW_TIME_END(A) 
#endif



#include <vector>
#include <ostream>
#include <set>

using namespace std;

unsigned int SetBit(unsigned int anInt, unsigned int pos);

double diffclock(clock_t clock1,clock_t clock2);

template<typename T> std::ostream& print_cont(std::ostream& os, const T& cont);

template<typename S, typename T> std::ostream& operator<<(std::ostream& os, const std::pair<S, T>& pair);
//template<typename S, typename T> std::ostream& operator<<(std::ostream& os, const std::pair<S, T>& pair);

template<typename T> std::ostream& operator<<(std::ostream& os, const std::vector<T>& vect);

  void error(const char* pMsg, ...);
  
  void info(const char* pMsg, ...);
  
  void debug(const char* pMsg, ...);
  
  void trace(const char* pMsg, ...);
  
  template<typename C, typename T> bool isMember(const C& aSet, const T& value)
{
  return aSet.find(value) != aSet.end();
};

template<typename C, typename T> bool remove(C& aSet, const T& value)
{
  typename C::iterator it = aSet.find(value);
  if (it == aSet.end()) {
    return false;
  }
  
  aSet.erase(it);
  return true;
}

template<typename T> bool removeAll(set<T>& aSet, const set<T>& aSetToRemove)
{
  typename set<T>::const_iterator it = aSetToRemove.begin();
  
  for( ; it != aSetToRemove.end(); ++it)
  {
    remove(aSet, *it);
  }
  
  return true;
}


#endif
