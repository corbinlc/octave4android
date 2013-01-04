// DO NOT EDIT!
// Generated automatically from DASPK-opts.in.

#if !defined (octave_DASPK_options_h)
#define octave_DASPK_options_h 1

#include <cfloat>
#include <cmath>

#include <DAE.h>


class
DASPK_options
{
public:

  DASPK_options (void)
    : x_absolute_tolerance (),
      x_relative_tolerance (),
      x_compute_consistent_initial_condition (),
      x_use_initial_condition_heuristics (),
      x_initial_condition_heuristics (),
      x_print_initial_condition_info (),
      x_exclude_algebraic_variables_from_error_test (),
      x_algebraic_variables (),
      x_enforce_inequality_constraints (),
      x_inequality_constraint_types (),
      x_initial_step_size (),
      x_maximum_order (),
      x_maximum_step_size (),
      reset ()
    {
      init ();
    }

  DASPK_options (const DASPK_options& opt)
    : x_absolute_tolerance (opt.x_absolute_tolerance),
      x_relative_tolerance (opt.x_relative_tolerance),
      x_compute_consistent_initial_condition (opt.x_compute_consistent_initial_condition),
      x_use_initial_condition_heuristics (opt.x_use_initial_condition_heuristics),
      x_initial_condition_heuristics (opt.x_initial_condition_heuristics),
      x_print_initial_condition_info (opt.x_print_initial_condition_info),
      x_exclude_algebraic_variables_from_error_test (opt.x_exclude_algebraic_variables_from_error_test),
      x_algebraic_variables (opt.x_algebraic_variables),
      x_enforce_inequality_constraints (opt.x_enforce_inequality_constraints),
      x_inequality_constraint_types (opt.x_inequality_constraint_types),
      x_initial_step_size (opt.x_initial_step_size),
      x_maximum_order (opt.x_maximum_order),
      x_maximum_step_size (opt.x_maximum_step_size),
      reset (opt.reset)
    { }

  DASPK_options& operator = (const DASPK_options& opt)
    {
      if (this != &opt)
        {
          x_absolute_tolerance = opt.x_absolute_tolerance;
          x_relative_tolerance = opt.x_relative_tolerance;
          x_compute_consistent_initial_condition = opt.x_compute_consistent_initial_condition;
          x_use_initial_condition_heuristics = opt.x_use_initial_condition_heuristics;
          x_initial_condition_heuristics = opt.x_initial_condition_heuristics;
          x_print_initial_condition_info = opt.x_print_initial_condition_info;
          x_exclude_algebraic_variables_from_error_test = opt.x_exclude_algebraic_variables_from_error_test;
          x_algebraic_variables = opt.x_algebraic_variables;
          x_enforce_inequality_constraints = opt.x_enforce_inequality_constraints;
          x_inequality_constraint_types = opt.x_inequality_constraint_types;
          x_initial_step_size = opt.x_initial_step_size;
          x_maximum_order = opt.x_maximum_order;
          x_maximum_step_size = opt.x_maximum_step_size;
          reset = opt.reset;
        }

      return *this;
    }

  ~DASPK_options (void) { }

  void init (void)
    {
      x_absolute_tolerance.resize (dim_vector (1, 1));
      x_absolute_tolerance(0) = ::sqrt (DBL_EPSILON);
      x_relative_tolerance.resize (dim_vector (1, 1));
      x_relative_tolerance(0) = ::sqrt (DBL_EPSILON);
      x_initial_condition_heuristics.resize (dim_vector (6, 1));
      x_initial_condition_heuristics(0) = 5.0;
      x_initial_condition_heuristics(1) = 6.0;
      x_initial_condition_heuristics(2) = 5.0;
      x_initial_condition_heuristics(3) = 0.0;
      x_initial_condition_heuristics(4) = ::pow (DBL_EPSILON, 2.0/3.0);
      x_initial_condition_heuristics(5) = 0.01;
      x_algebraic_variables.resize (dim_vector (1, 1));
      x_algebraic_variables(0) = 0;
      x_inequality_constraint_types.resize (dim_vector (1, 1));
      x_inequality_constraint_types(0) = 0;
      x_initial_step_size = -1.0;
      x_maximum_order = 5;
      x_maximum_step_size = -1.0;
      reset = true;
    }

  void set_options (const DASPK_options& opt)
    {
      x_absolute_tolerance = opt.x_absolute_tolerance;
      x_relative_tolerance = opt.x_relative_tolerance;
      x_compute_consistent_initial_condition = opt.x_compute_consistent_initial_condition;
      x_use_initial_condition_heuristics = opt.x_use_initial_condition_heuristics;
      x_initial_condition_heuristics = opt.x_initial_condition_heuristics;
      x_print_initial_condition_info = opt.x_print_initial_condition_info;
      x_exclude_algebraic_variables_from_error_test = opt.x_exclude_algebraic_variables_from_error_test;
      x_algebraic_variables = opt.x_algebraic_variables;
      x_enforce_inequality_constraints = opt.x_enforce_inequality_constraints;
      x_inequality_constraint_types = opt.x_inequality_constraint_types;
      x_initial_step_size = opt.x_initial_step_size;
      x_maximum_order = opt.x_maximum_order;
      x_maximum_step_size = opt.x_maximum_step_size;
      reset = opt.reset;
    }

  void set_default_options (void) { init (); }

  void set_absolute_tolerance (double val)
    {
      x_absolute_tolerance.resize (dim_vector (1, 1));
      x_absolute_tolerance(0) = (val > 0.0) ? val : ::sqrt (DBL_EPSILON);
      reset = true;
    }

  void set_absolute_tolerance (const Array<double>& val)
    { x_absolute_tolerance = val; reset = true; }

  void set_relative_tolerance (double val)
    {
      x_relative_tolerance.resize (dim_vector (1, 1));
      x_relative_tolerance(0) = (val > 0.0) ? val : ::sqrt (DBL_EPSILON);
      reset = true;
    }

  void set_relative_tolerance (const Array<double>& val)
    { x_relative_tolerance = val; reset = true; }

  void set_compute_consistent_initial_condition (octave_idx_type val)
    { x_compute_consistent_initial_condition = val; reset = true; }

  void set_use_initial_condition_heuristics (octave_idx_type val)
    { x_use_initial_condition_heuristics = val; reset = true; }

  void set_initial_condition_heuristics (const Array<double>& val)
    { x_initial_condition_heuristics = val; reset = true; }

  void set_print_initial_condition_info (octave_idx_type val)
    { x_print_initial_condition_info = val; reset = true; }

  void set_exclude_algebraic_variables_from_error_test (octave_idx_type val)
    { x_exclude_algebraic_variables_from_error_test = val; reset = true; }

  void set_algebraic_variables (int val)
    {
      x_algebraic_variables.resize (dim_vector (1, 1));
      x_algebraic_variables(0) = val;
      reset = true;
    }

  void set_algebraic_variables (const Array<octave_idx_type>& val)
    { x_algebraic_variables = val; reset = true; }

  void set_enforce_inequality_constraints (octave_idx_type val)
    { x_enforce_inequality_constraints = val; reset = true; }

  void set_inequality_constraint_types (octave_idx_type val)
    {
      x_inequality_constraint_types.resize (dim_vector (1, 1));
      x_inequality_constraint_types(0) = val;
      reset = true;
    }

  void set_inequality_constraint_types (const Array<octave_idx_type>& val)
    { x_inequality_constraint_types = val; reset = true; }

  void set_initial_step_size (double val)
    { x_initial_step_size = (val >= 0.0) ? val : -1.0; reset = true; }

  void set_maximum_order (octave_idx_type val)
    { x_maximum_order = val; reset = true; }

  void set_maximum_step_size (double val)
    { x_maximum_step_size = (val >= 0.0) ? val : -1.0; reset = true; }
  Array<double> absolute_tolerance (void) const
    { return x_absolute_tolerance; }

  Array<double> relative_tolerance (void) const
    { return x_relative_tolerance; }

  octave_idx_type compute_consistent_initial_condition (void) const
    { return x_compute_consistent_initial_condition; }

  octave_idx_type use_initial_condition_heuristics (void) const
    { return x_use_initial_condition_heuristics; }

  Array<double> initial_condition_heuristics (void) const
    { return x_initial_condition_heuristics; }

  octave_idx_type print_initial_condition_info (void) const
    { return x_print_initial_condition_info; }

  octave_idx_type exclude_algebraic_variables_from_error_test (void) const
    { return x_exclude_algebraic_variables_from_error_test; }

  Array<octave_idx_type> algebraic_variables (void) const
    { return x_algebraic_variables; }

  octave_idx_type enforce_inequality_constraints (void) const
    { return x_enforce_inequality_constraints; }

  Array<octave_idx_type> inequality_constraint_types (void) const
    { return x_inequality_constraint_types; }

  double initial_step_size (void) const
    { return x_initial_step_size; }

  octave_idx_type maximum_order (void) const
    { return x_maximum_order; }

  double maximum_step_size (void) const
    { return x_maximum_step_size; }

private:

  Array<double> x_absolute_tolerance;
  Array<double> x_relative_tolerance;
  octave_idx_type x_compute_consistent_initial_condition;
  octave_idx_type x_use_initial_condition_heuristics;
  Array<double> x_initial_condition_heuristics;
  octave_idx_type x_print_initial_condition_info;
  octave_idx_type x_exclude_algebraic_variables_from_error_test;
  Array<octave_idx_type> x_algebraic_variables;
  octave_idx_type x_enforce_inequality_constraints;
  Array<octave_idx_type> x_inequality_constraint_types;
  double x_initial_step_size;
  octave_idx_type x_maximum_order;
  double x_maximum_step_size;

protected:

  bool reset;
};

#endif
