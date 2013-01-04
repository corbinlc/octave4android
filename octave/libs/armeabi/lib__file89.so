// DO NOT EDIT!
// Generated automatically from DASSL-opts.in.

#if !defined (octave_DASSL_options_h)
#define octave_DASSL_options_h 1

#include <cfloat>
#include <cmath>

#include <DAE.h>


class
DASSL_options
{
public:

  DASSL_options (void)
    : x_absolute_tolerance (),
      x_relative_tolerance (),
      x_compute_consistent_initial_condition (),
      x_enforce_nonnegativity_constraints (),
      x_initial_step_size (),
      x_maximum_order (),
      x_maximum_step_size (),
      x_step_limit (),
      reset ()
    {
      init ();
    }

  DASSL_options (const DASSL_options& opt)
    : x_absolute_tolerance (opt.x_absolute_tolerance),
      x_relative_tolerance (opt.x_relative_tolerance),
      x_compute_consistent_initial_condition (opt.x_compute_consistent_initial_condition),
      x_enforce_nonnegativity_constraints (opt.x_enforce_nonnegativity_constraints),
      x_initial_step_size (opt.x_initial_step_size),
      x_maximum_order (opt.x_maximum_order),
      x_maximum_step_size (opt.x_maximum_step_size),
      x_step_limit (opt.x_step_limit),
      reset (opt.reset)
    { }

  DASSL_options& operator = (const DASSL_options& opt)
    {
      if (this != &opt)
        {
          x_absolute_tolerance = opt.x_absolute_tolerance;
          x_relative_tolerance = opt.x_relative_tolerance;
          x_compute_consistent_initial_condition = opt.x_compute_consistent_initial_condition;
          x_enforce_nonnegativity_constraints = opt.x_enforce_nonnegativity_constraints;
          x_initial_step_size = opt.x_initial_step_size;
          x_maximum_order = opt.x_maximum_order;
          x_maximum_step_size = opt.x_maximum_step_size;
          x_step_limit = opt.x_step_limit;
          reset = opt.reset;
        }

      return *this;
    }

  ~DASSL_options (void) { }

  void init (void)
    {
      x_absolute_tolerance.resize (dim_vector (1, 1));
      x_absolute_tolerance(0) = ::sqrt (DBL_EPSILON);
      x_relative_tolerance.resize (dim_vector (1, 1));
      x_relative_tolerance(0) = ::sqrt (DBL_EPSILON);
      x_initial_step_size = -1.0;
      x_maximum_order = -1;
      x_maximum_step_size = -1.0;
      x_step_limit = -1;
      reset = true;
    }

  void set_options (const DASSL_options& opt)
    {
      x_absolute_tolerance = opt.x_absolute_tolerance;
      x_relative_tolerance = opt.x_relative_tolerance;
      x_compute_consistent_initial_condition = opt.x_compute_consistent_initial_condition;
      x_enforce_nonnegativity_constraints = opt.x_enforce_nonnegativity_constraints;
      x_initial_step_size = opt.x_initial_step_size;
      x_maximum_order = opt.x_maximum_order;
      x_maximum_step_size = opt.x_maximum_step_size;
      x_step_limit = opt.x_step_limit;
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

  void set_enforce_nonnegativity_constraints (octave_idx_type val)
    { x_enforce_nonnegativity_constraints = val; reset = true; }

  void set_initial_step_size (double val)
    { x_initial_step_size = (val >= 0.0) ? val : -1.0; reset = true; }

  void set_maximum_order (octave_idx_type val)
    { x_maximum_order = val; reset = true; }

  void set_maximum_step_size (double val)
    { x_maximum_step_size = (val >= 0.0) ? val : -1.0; reset = true; }

  void set_step_limit (octave_idx_type val)
    { x_step_limit = (val >= 0) ? val : -1; reset = true; }
  Array<double> absolute_tolerance (void) const
    { return x_absolute_tolerance; }

  Array<double> relative_tolerance (void) const
    { return x_relative_tolerance; }

  octave_idx_type compute_consistent_initial_condition (void) const
    { return x_compute_consistent_initial_condition; }

  octave_idx_type enforce_nonnegativity_constraints (void) const
    { return x_enforce_nonnegativity_constraints; }

  double initial_step_size (void) const
    { return x_initial_step_size; }

  octave_idx_type maximum_order (void) const
    { return x_maximum_order; }

  double maximum_step_size (void) const
    { return x_maximum_step_size; }

  octave_idx_type step_limit (void) const
    { return x_step_limit; }

private:

  Array<double> x_absolute_tolerance;
  Array<double> x_relative_tolerance;
  octave_idx_type x_compute_consistent_initial_condition;
  octave_idx_type x_enforce_nonnegativity_constraints;
  double x_initial_step_size;
  octave_idx_type x_maximum_order;
  double x_maximum_step_size;
  octave_idx_type x_step_limit;

protected:

  bool reset;
};

#endif
