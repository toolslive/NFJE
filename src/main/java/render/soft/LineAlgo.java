package render.soft;

class LineAlgo {
  public LineAlgo() {}

  public void setXYZProcessor(XYZProcessor p) {
    _xyzProcessor = p;
  }

  private XYZProcessor _xyzProcessor;

  public void drawLine(int x0, int y0, float z0, int x1, int y1, float z1) {
    int deltax = x1 - x0, /* Change in x-coordinates */
        deltay = y1 - y0, /* Change in y-coordinates */
        majorIncrA = 0,
        majorIncrB = 0,
        minorIncrA = 0,
        minorIncrB = 0,
        twop_o = 0, /* Initial decision paramter */
        twopleft = 0, /* ... (p<0) addition to decision param. */
        twopright = 0; /* ... (p>=0) addition to decision param. */

    int current_x = x0, /* Initialize 1st endpoint to current endpt.*/ current_y = y0;
    float deltaz = z1 - z0;
    float current_z = z0;
    float zIncr = 0;

    /* Determine if absolute value of slope is less than 1 ... if so
    then deal with only four cases of lines...all of which have a
    slope whose absolute value is less than 1 -  abs(slope) < 1 */
    if (Math.abs(deltay) <= Math.abs(deltax)) {
      /* Determine if first point is to the left of second point
      ...if so then there are only two possible cases of this.
      the lines are in the 1st and 8th octants. */
      zIncr = deltaz / deltax;
      if (x0 < x1) {
        /* X will always be incremented for these two cases. */
        majorIncrA = 1;

        /* If first endpoint is below second endpoint...1st octant */
        if (y0 < y1) {
            /* CASE 1 */
          twop_o = 2 * deltay - deltax;
          twopleft = 2 * deltay;
          twopright = 2 * (deltay - deltax);
          minorIncrA = 0;
          minorIncrB = 1;
        } else {
            /* CASE 8 */
          zIncr = zIncr;
          twop_o = 2 * deltay + deltax;
          twopright = 2 * deltay;
          twopleft = 2 * (deltay + deltax);
          minorIncrA = -1;
          minorIncrB = 0;
        }
      } else {
          /* ...else if first point is to the right of second pt. */

        /* For these two cases, X will always be decrimented */
        majorIncrA = -1;

        if (y0 < y1) {
            /* CASE 4 */
          zIncr = -zIncr;
          twop_o = -2 * deltay - deltax;
          twopright = -2 * deltay;
          twopleft = 2 * (-deltay - deltax);
          minorIncrA = 1;
          minorIncrB = 0;
        } else {
            /* CASE 5 */
          zIncr = -zIncr;
          twop_o = -2 * deltay + deltax;
          twopleft = -2 * deltay;
          twopright = 2 * (-deltay + deltax);
          minorIncrA = 0;
          minorIncrB = -1;
        }
      }

      /* WHILE LOOP #1  - Draw the line of the 1,4,5 & 8th octants
      ... draw until current x coordinate is equal to 2nd pt's X value */
      while (current_x != x1) {
        /* Increment or Decrement X depending on what the value of
        majorIncrA was found previously */
        current_x += majorIncrA;
        current_z += zIncr;
        if (twop_o < 0) {
          twop_o += twopleft;
          current_y += minorIncrA;
        } else {
          twop_o += twopright;
          current_y += minorIncrB;
        }
        _xyzProcessor.process(current_x, current_y, current_z);
      }
    } else {
        /* If abs(slope) is between 1 and infinity */
      zIncr = deltaz / deltay;
      /* If point 1 is below point 2 ... i.e. only lines in the
      2nd and 3rd octants */
      if (y0 < y1) {
        /* Set increment/decrement parameter to +1 */
        majorIncrB = 1;

        /* If point 1 is to the left of point 2 ... i.e. 2nd octant */
        if (x0 < x1) {
            /* CASE 2 */
          twop_o = deltay - 2 * deltax;
          twopright = -2 * deltax;
          twopleft = 2 * (deltay - deltax);
          minorIncrA = 0;
          minorIncrB = 1;
        } else {
            /* CASE 3 */
          twop_o = -deltay - 2 * deltax;
          twopleft = -2 * deltax;
          twopright = 2 * (-deltay - deltax);
          minorIncrA = -1;
          minorIncrB = 0;
        }
      } else {
          /* ...else we are dealing with lines in 6th & 7th octants */

        /* Set increment/decrement operator to decrement by 1 */
        majorIncrB = -1;

        /* If 1st point is to the left of 2nd point...octant 7 */
        if (x0 < x1) {
            /* CASE 7 */
          zIncr = -zIncr;
          twop_o = deltay + 2 * deltax;
          twopleft = 2 * deltax;
          twopright = 2 * (deltay + deltax);
          minorIncrA = 1;
          minorIncrB = 0;
        } else {
            /* CASE 6 */
          zIncr = -zIncr;
          twop_o = -deltay + 2 * deltax;
          twopright = 2 * deltax;
          twopleft = 2 * (-deltay + deltax);
          minorIncrA = 0;
          minorIncrB = -1;
        }
      }

      /* WHILE LOOP #2  - Draw the line of the 2,3,6 & 7th octants
      ... draw until current y coordinate is equal to 2nd pt's Y value */
      while (current_y != y1) {
        /* Increment or Decrement current Y value */
        current_y += majorIncrB;
        current_z += zIncr;
        /* If decision parameter is less than zero */
        if (twop_o < 0) {
          twop_o += twopleft;
          current_x += minorIncrB;
        } else {
          twop_o += twopright;
          current_x += minorIncrA;
        }

        _xyzProcessor.process(current_x, current_y, current_z);
      }
    }
  }
}
