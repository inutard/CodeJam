#ifndef SIMPLEX_H
#define SIMPLEX_H

#include <vector>

using namespace std;

typedef vector<int> VecInt;
typedef vector<VecInt> MatrixInt;

typedef vector<double> VecDouble;
typedef vector<VecDouble> MatrixDouble;


class Simplex
{
protected:
  unsigned int num_non_basic_variables;
  unsigned int num_basic_variables; // slack variables
  
  bool finding_max;
  int rows;
  int cols;
  int cur_constraint;
  MatrixDouble data;

public:
  Simplex(int num_non_basic_variables, int num_basic_variables);
  void print();
  
  //z = z[0] * x_0 + z[1] * x_1 + ...
  void set_z(const VecDouble& z);
  
  void set_eq_to_minimize(const VecDouble& z);
  void set_eq_to_maximize(const VecDouble& z);
  
  //a[1][0]*x[0] + a[1][1]*x[1] + s[1] == b[1]  
  void add_constraint(unsigned int cons_num, const VecDouble& co_eff, double b);
  
  void add_constraint_lte(const VecDouble& co_eff, double b);
  void add_constraint_gte(const VecDouble& co_eff, double b);
  
  void print_current_solution();
  
  //s[i]
  double getBasicVar(int i);
  
  //x[i]
  double getVar(int i);
  
  bool solved();
  
  //returns true if optimal
  bool do_step();
  
};

class MinSimplex : public Simplex
{
public:
  //because of the transposition, invert the 2
  MinSimplex(int num_non_basic_variables, int num_basic_variables);
  
  void set_z(const VecDouble& z);
  
  void add_constraint(unsigned int cons_num, const VecDouble& co_eff, double b);
  
};

#endif