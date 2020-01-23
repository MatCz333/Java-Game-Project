

/**
 * Represented the position of the Tile in the SparseTileArray
 */
public class Position extends Object implements Comparable<Position> {
	 
	 //Integer representing position on the x axis
	 private int x;
	 //Integer representing position on the y axis
	 private int y;
	 
	 /**
	  * Constructs the position of the Tile in (x,y) grid where x is the position
	  * on the x axis and y is the position on the y axis
	  *
	  * @param x-The position of the Tile on the x axis
	  * @param y-The position of the Tile on the y axis
	  */
	 public Position (int x, int y) {
		  this.x = x;
		  this.y = y;
	 }
	 
	 /**
	  * Gets the coordinate x
	  *
	  * @return The x coordinate
	  */
	 public int getX () {
		  return this.x;
	 }
	 
	 /**
	  * Gets the coordinate y
	  *
	  * @return The y coordinate
	  */
	 public int getY () {
		  return this.y;
	 }
	 
	 /**
	  * Overrides the equals method. Checks if another Object is "equal" to
	  * Position
	  *
	  * @param obj- Object we are comparing against
	  * @return true- If object is instance of Position and has same coordinates
	  * returned by getX() and getY();
	  */
	 @Override
	 public boolean equals (Object obj) {
		  if (obj == null) {
				return false;
		  }
		  if (obj == this) {
				return true;
		  }
		  if (!(obj instanceof Position)) {
				return false;
		  }
		  Position position = (Position) obj;
		  return (this.getX() == ((Position) obj).getX()) && (this.getY() == (
					 (Position) obj).getY());
	 }
	 
	 /**
	  * Creates a unique hash code for the Position class
	  *
	  * @return hash code
	  */
	 @Override
	 public int hashCode () {
		  int result = x;
		  result = 31 * result + y;
		  return result;
	 }
	 
	 /**
	  * Overrides comparator. Compares this position against other.
	  *
	  * @return -1 if getX() < other.getX() -1 if getX() == other.getX() and
	  * getY() < other.getY() 0 if getX() == other.getX() and getY() ==
	  * other.getY() 1 if getX() > other.getX() 1 if getX() == other.getX() and
	  * getY() > other.getY()
	  */
	 @Override
	 public int compareTo (Position other) {
		  if (this.getX() < other.getX()) {
				return -1;
		  } else if ((this.getX() == other.getX()) && (this.getY() < other
					 .getY())) {
				return -1;
		  } else if (this.getX() > other.getX()) {
				return 1;
		  } else if ((this.getX() == other.getX()) && (this.getY() > other
					 .getY())) {
				return 1;
		  }
		  return 0;
	 }
}
