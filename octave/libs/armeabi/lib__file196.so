// DO NOT EDIT!
// Generated automatically from LSODE-opts.in.

#if !defined (octave_LSODE_options_h)
#define octave_LSODE_options_h 1

#include <cfloat>
#include <cmath>

#include <ODE.h>


class
LSODE_options
{
public:

  LSODE_options (void)
    : x_absolute_tolerance (),
      x_relative_tolerance (),
      x_integration_method (),
      x_initial_step_size (),
      x_maximum_order (),
      x_maximum_step_size (),
      x_minimum_step_size (),
      x_step_limit (),
      reset ()
    {
      init ();
    }

  LSODE_options (const LSODE_options& opt)
    : x_absolute_tolerance (opt.x_absolute_tolerance),
      x_relative_tolerance (opt.x_relative_tolerance),
      x_integration_method (opt.x_integration_method),
      x_initial_step_size (opt.x_initial_step_size),
      x_maximum_order (opt.x_maximum_order),
      x_maximum_step_size (opt.x_maximum_step_size),
      x_minimum_step_size (opt.x_minimum_step_size),
      x_step_limit (opt.x_step_limit),
      reset (opt.reset)
    { }

  LSODE_options& operator = (const LSODE_options& opt)
    {
      if (this != &opt)
        {
          x_absolute_tolerance = opt.x_absolute_tolerance;
          x_relative_tolerance = opt.x_relative_tolerance;
          x_integration_method = opt.x_integration_method;
          x_initial_step_size = opt.x_initial_step_size;
          x_maximum_order = opt.x_maximum_order;
          x_maximum_step_size = opt.x_maximum_step_size;
          x_minimum_step_size = opt.x_minimum_step_size;
          x_step_limit = opt.x_step_limit;
          reset = opt.reset;
        }

      return *this;
    }

  ~LSODE_options (void) { }

  void init (void)
    {
      x_absolute_tolerance.resize (dim_vector (1, 1));
      x_absolute_tolerance(0) = ::sqrt (DBL_EPSILON);
      x_relative_tolerance = ::sqrt (DBL_EPSILON);
      x_integration_method = "stiff";
      x_initial_step_size = -1.0;
      x_maximum_order = -1;
      x_maximum_step_size = -1.0;
      x_minimum_step_size = 0.0;
      x_step_limit = 100000;
      reset = true;
    }

  void set_options (const LSODE_options& opt)
    {
      x_absolute_tolerance = opt.x_absolute_tolerance;
      x_relative_tolerance = opt.x_relative_tolerance;
      x_integration_method = opt.x_integration_method;
      x_initial_step_size = opt.x_initial_step_size;
      x_maximum_order = opt.x_maximum_order;
      x_maximum_step_size = opt.x_maximum_step_size;
      x_minimum_step_size = opt.x_minimum_step_size;
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
    { x_relative_tolerance = (val > 0.0) ? val : ::sqrt (DBL_EPSILON); reset = true; }

  void set_integration_method (const std::string& val)
    {
      if (val == "stiff" || val == "bdf")
        x_integration_method = "stiff";
      else if (val == "non-stiff" || val == "adams")
        x_integration_method = "non-stiff";
      else
        (*current_liboctave_error_handler)
          ("lsode_options: method must be \"stiff\", \"bdf\", \"non-stiff\", or \"adams\"");
      reset = true;
    }

  void set_initial_step_size (double val)
    { x_initial_step_size = (val >= 0.0) ? val : -1.0; reset = true; }

  void set_maximum_order (octave_idx_type val)
    { x_maximum_order = val; reset = true; }

  void set_maximum_step_size (double val)
    { x_maximum_step_size = (val >= 0.0) ? val : -1.0; reset = true; }

  void set_minimum_step_size (double val)
    { x_minimum_step_size = (val >= 0.0) ? val : 0.0; reset = true; }

  void set_step_limit (octave_idx_type val)
    { x_step_limit = val; reset = true; }
  Array<double> absolute_tolerance (void) const
    { return x_absolute_tolerance; }

  double relative_tolerance (void) const
    { return x_relative_tolerance; }

  std::string integration_method (void) const
    { return x_integration_method; }

  double initial_step_size (void) const
    { return x_initial_step_size; }

  octave_idx_type maximum_order (void) const
    { return x_maximum_order; }

  double maximum_step_size (void) const
    { return x_maximum_step_size; }

  double minimum_step_size (void) const
    { return x_minimum_step_size; }

  octave_idx_type step_limit (void) const
    { return x_step_limit; }

private:

  Array<double> x_absolute_tolerance;
  double x_relative_tolerance;
  std::string x_integration_method;
  double x_initial_step_size;
  octave_idx_type x_maximum_order;
  double x_maximum_step_size;
  double x_minimum_step_size;
  octave_idx_type x_step_limit;

protected:

  bool reset;
};

#endif
