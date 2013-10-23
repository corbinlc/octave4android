## Author: Paul Kienzle <pkienzle@users.sf.net>
## This program is granted to the public domain.

## -*- texinfo -*-
## @deftypefn {Function File} normplot (@var{X})
##
## Produce a normal probability plot for each column of @var{X}.
##
## The line joing the 1st and 3rd quantile is drawn on the
## graph.  If the underlying distribution is normal, the
## points will cluster around this line.
##
## Note that this function sets the title, xlabel, ylabel,
## axis, grid, tics and hold properties of the graph.  These 
## need to be cleared before subsequent graphs using 'clf'.
## @end deftypefn

function normplot(X)
  if nargin!=1, print_usage; end
  if (rows(X) == 1), X=X(:); end

  # plot labels
  title "Normal Probability Plot"
  ylabel "% Probability"
  xlabel "Data"

  # plot grid
  t = [0.00001;0.0001;0.001;0.01;0.1;0.3;1;2;5;10;25;50;
      75;90;95;98;99;99.7;99.9;99.99;99.999;99.9999;99.99999];
  tics ('y',normal_inv(t/100),num2str(t));
  grid on

  # Transform data
  n = rows(X);
  if n<2, error("normplot requires a vector"); end
  q = normal_inv([1:n]'/(n+1));
  Y = sort(X);

  # Set view range with a bit of space around data
  miny = min(Y(:)); minq = min(q(1),normal_inv(0.05));
  maxy = max(Y(:)); maxq = max(q(end),normal_inv(0.95));
  yspace = (maxy-miny)*0.05; qspace = (q(end)-q(1))*0.05;
  axis ([miny-yspace, maxy+yspace, minq-qspace, maxq+qspace]); 

  # Find the line joining the first to the third quartile for each column
  q1 = ceil(n/4);
  q3 = n-q1+1;
  m = (q(q3)-q(q1))./(Y(q3,:)-Y(q1,:));
  p = [ m; q(q1)-m.*Y(q1,:) ];

  # Plot the lines one at a time.  Plot the lines before overlaying the
  # normals so that the default label is 'line n'.
  if columns(Y)==1,
    leg = "+;;";
  else
    leg = "%d+;Column %d;";
  endif

  for i=1:columns(Y)
    plot(Y(:,i),q,sprintf(leg,i,i)); hold on;

    # estimate the mean and standard deviation by linear regression
    # [v,dv] = wpolyfit(q,Y(:,i),1)
  end

  # Overlay the estimated normal lines.
  for i=1:columns(Y)
    # Use the end points and one point guaranteed to be in the view since
    # gnuplot skips any lines whose points are all outside the view.
    pts = [Y(1,i);Y(q1,i);Y(end,i)];
    plot(pts, polyval(p(:,i),pts), [num2str(i),";;"]);
  end
  hold off;
end

