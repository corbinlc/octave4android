// DO NOT EDIT!
// Generated automatically from DASRT-opts.in.

#if !defined (octave_DASRT_options_h)
#define octave_DASRT_options_h 1

#include <cfloat>
#include <cmath>

#include <DAERT.h>


class
DASRT_options
{
public:

  DASRT_options (void)
    : x_absolute_tolerance (),
      x_relative_tolerance (),
      x_initial_step_size (),
      x_maximum_order (),
      x_maximum_step_size (),
      x_step_limit (),
      reset ()
    {
      init ();
    }

  DASRT_options (const DASRT_options& opt)
    : x_absolute_tolerance (opt.x_absolute_tolerance),
      x_relative_tolerance (opt.x_relative_tolerance),
      x_initial_step_size (opt.x_initial_step_size),
      x_maximum_order (opt.x_maximum_order),
      x_maximum_step_size (opt.x_maximum_step_size),
      x_step_limit (opt.x_step_limit),
      reset (opt.reset)
    { }

  DASRT_options& operator = (const DASRT_options& opt)
    {
      if (this != &opt)
        {
          x_absolute_tolerance = opt.x_absolute_tolerance;
          x_relative_tolerance = opt.x_relative_tolerance;
          x_initial_step_size = opt.x_initial_step_size;
          x_maximum_order = opt.x_maximum_order;
          x_maximum_step_size = opt.x_maximum_step_size;
          x_step_limit = opt.x_step_limit;
          reset = opt.reset;
        }

      return *this;
    }

  ~DASRT_options (void) { }

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

  void set_options (const DASRT_options& opt)
    {
      x_absolute_tolerance = opt.x_absolute_tolerance;
      x_relative_tolerance = opt.x_relative_tolerance;
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
  double x_initial_step_size;
  octave_idx_type x_maximum_order;
  double x_maximum_step_size;
  octave_idx_type x_step_limit;

protected:

  bool reset;
};

#endif
