import java.util.*;

public class Game
{
  private Grid grid;
  private int userRow;
  private int msElapsed;
  private int timesGet;
  private int timesAvoid;
  private String imageName = "dog.png";
  private String avoid = "poo.png";
  private String get = "bone.png";
  
  public Game()
  {
    grid = new Grid(5, 10);
    userRow = 0;
    msElapsed = 0;
    timesGet = 0;
    timesAvoid = 0;
    updateTitle();
    grid.setImage(new Location(userRow, 0), imageName);
  }
  
  public void play()
  {
    while (!isGameOver())
    {
      grid.pause(100);
      handleKeyPress();
      if (msElapsed % 300 == 0)
      {
        scrollLeft();
        populateRightEdge();
      }
      updateTitle();
      msElapsed += 100;
    } 
  }
  
  public void handleKeyPress()
  {
	  int key = grid.checkLastKeyPressed();
	  Location currentLocation = new Location(userRow,0);
	  if (key == 38) {
		  userRow-=1;
		  Location nextLocation = new Location(userRow, 0);
		  handleCollision(nextLocation);
		  grid.setImage(nextLocation, imageName);
		  grid.setImage(currentLocation, null);
	  }
	  if (key == 40) {
		  userRow+=1;
		  Location nextLocation = new Location(userRow, 0);
		  handleCollision(nextLocation);
		  grid.setImage(nextLocation, imageName);
		  grid.setImage(currentLocation, null);
	  }
  }
  public String chosePic() {
	  Random r = new Random();
	  int random = r.nextInt(2);
	  return (random==1?get:avoid);
  }
  public void populateRightEdge()
  {
	  Random r = new Random();
	  int random = r.nextInt(3);
	  int col = grid.getNumCols()-1;
	  if (random==1) {
		  int loc = (int) Math.random()*grid.getNumRows();
		  Location l = new Location(loc, col);
		  grid.setImage(l, chosePic());
	  }
	  if (random==2) {
		  int loc1 = r.nextInt(grid.getNumRows());
		  int loc2 = r.nextInt(grid.getNumRows());
		  Location l1 = new Location(loc1, col);
		  Location l2 = new Location(loc2, col);
		  grid.setImage(l1, chosePic());
		  grid.setImage(l2, chosePic());
	  }
	  if (random==3) {
		  int loc1 = r.nextInt(grid.getNumRows());
		  int loc2 = r.nextInt(grid.getNumRows());
		  int loc3 = r.nextInt(grid.getNumRows());
		  Location l1 = new Location(loc1, col);
		  Location l2 = new Location(loc2, col);
		  Location l3 = new Location(loc3, col);
		  grid.setImage(l1, chosePic());
		  grid.setImage(l2, chosePic());
		  grid.setImage(l3, chosePic());
	  }
  }
  
  public void scrollLeft()
  {
	  int row = grid.getNumRows();
	  int col = grid.getNumCols();
	  Location user = new Location(userRow, 0);
	  for (int i = 0; i<row; i++) {
		  for (int j = 0; j<col; j++) {
			  Location current = new Location(i, j);
			  if (!current.equals(user)) {
				  Location set = new Location(i, j-1);
				  if (j-1<0) {
					  grid.setImage(current, null);
				  } else if (!current.equals(new Location(userRow, 1))) {
					  grid.setImage(set, grid.getImage(current));
				  }
				  if (j==col-1) {
					  grid.setImage(current, null);
				  }
			  }
			  if (current.equals(new Location(userRow, 1))) {
				  handleCollision(current);
			  }
		  }
	  }
  }
  
  public void handleCollision(Location loc)
  {
	  try {
		  if (grid.getImage(loc)==get) {
			  timesGet++;
		  }
		  if (grid.getImage(loc)==avoid) {
			  timesAvoid++;
		  }
		  grid.setImage(loc, null);
	  } catch (RuntimeException ex) {
		  if (userRow<0||userRow>=grid.getNumRows()) {
			  System.out.println("OOPS! YOU DIED!");
		  }
	  }
  }
  
  public int getScore()
  {
    return timesGet*2-timesAvoid;
  }
  
  public void updateTitle()
  {
    grid.setTitle("Score:  " + getScore());
  }
  
  public boolean isGameOver()
  {
	  if (timesAvoid>2) {
		  System.out.println("You scored: " + getScore());
		  return true;
	  } else {
		  return false;
	  }
	  
  }
  
  public static void test()
  {
    Game game = new Game();
    game.play();
  }
  
  public static void main(String[] args)
  {
    Game.test();
  }
}
