package objects;
import javafx.scene.image.Image;


/**
 * This class represents the different floor tiles of the game.
 * @author Maha Malik
 * @verson 1.8
 */
public class FloorCard extends Card{



    public static final int TILE_SIZE = 61;

    private int x, y;
    private FloorType type;
    private boolean isFixed = true;
    private FloorTileState state = FloorTileState.NORMAL;
    private int[] openings = new int[4];
    private int rotation;


    /**
     * The enum Floor type.
     */
    public enum FloorType {
        STRAIGHT, CORNER, T_SHAPED, GOAL;
    }


    /**
     * The enum Floor tile state.
     */
    public enum FloorTileState{
        FIRE, FROZEN, NORMAL;
    }

    /**
     * Instantiates a new Floor card.
     *
     * @param type - the floor tile type
     */
    public FloorCard (String type) {
        switch (type) {
            case "STRAIGHT" :
                type = FloorType.STRAIGHT;
                image straight = new image(StraightPath);
                break;
            case "CORNER" :
                type = FloorType.CORNER;
                image corner = new image(CornerPath);
                break;
            case "T_SHAPED" :
                type = FloorType.T_SHAPED;
                image tshaped = new image(TShapedPath);
                break;
            case "GOAL" :
                type = FloorType.GOAL;
                image goal = new image(GoalPath);
                break;
        }
    }

    /**
     * Instantiates a new Floor card.
     *
     * @param type     - the floor tile type
     * @param rotation - the rotation of the tile
     * @param isFixed  - the is fixed
     */
    public FloorCard (String type, int rotation, boolean isFixed) {
        this.isFixed = isFixed;
        FloorCard(type, rotation);
    }

    /**
     * State of the floor tile.
     *
     * @return the state of the floor tile
     */
    public FloorTileState state() {
        return state;
    }

    /**
     * Sets on fire.
     */
    public void setOnFire() {
        this.state = FloorTileState.FIRE;
    }

    /**
     * Sets on ice.
     */
    public void setOnIce() {
        this.state = FloorTileState.FROZEN;
    }

    /**
     * Sets no state.
     */
    public void setNoState() {
        this.state = FloorTileState.NORMAL;
    }

    /**
     * Use card.
     */
    public void useCard() {
    }

    /**
     * Check goal boolean to see if the goal have been reached.
     *
     * @return the boolean
     */
    public boolean checkGoal() {
        return type == FloorType.GOAL;
    }

    /**
     * Is fixed boolean to indicate if some floor tiles are fixed.
     *
     * @return the boolean
     */
    public boolean isFixed() {
        return isFixed;
    }

    /**
     * Sets fixed tiles in the board
     *
     * @param fixedTiles the fixed tiles
     */
    public void setFixed(boolean fixedTiles) {
        isFixed = fixedTiles;
    }


    /**
     * Gets rotation.
     *
     * @param rotation - the rotation of the tile
     * @return the rotation
     */
    public int getRotation (int rotation) {
        return rotation;
    }

    /**
     * Sets rotation.
     * @param rotation - the rotation of the tile
     */
    
    public void rotateShape (int rotation) {

        switch (rotation) {
            case STRAIGHT:
                if (rotation == 0 || rotation == 180) {
                    setOpenings(1,0,1,0);
                } else if (rotation == 90 || rotation == 270) {
                    setOpenings(0,1,0,1);
                }
                break;
            case CORNER:
                if (rotation == 0) {
                    setOpenings(0,1,1,0);
                } else if (rotation == 90) {
                    setOpenings(0,0,1,1);
                } else if (rotation == 180) {
                    setOpenings(1,0,0,1);
                } else (rotation == 270) {
                    setOpenings(1, 1, 0, 0);
                }
                break;
            case T_SHAPED:
                if (rotation == 0) {
                    setOpenings(1,0,1,1);
                } else if (rotation == 90) {
                    setOpenings(1,1,0,1);
                } else if (rotation == 180) {
                    setOpenings(1,1,1,0);
                } else (rotation == 270) {
                    setOpenings(0, 1, 1, 1);
                }
                break;
        }
    }

    /**
     * Next rotation of each tile.
     */
    public void nextRotation() {
        if (this.rotation == 0) {
            this.setRotation(90);
        } else if (this.rotation == 90) {
            this.setRotation(180);
        } else if (this.rotation == 180) {
            this.setRotation(270);
        } else (this.rotation == 270) {
            this.setRotation(0);
        }
    }

    /**
     * Get image image.
     * @return the image
     */
    public Image getImage(){
        return image;
    }

    /**
     * Get x int.
     * @return the int
     */
    public int getX(){
        return x;
    }

    /**
     * Get y int.
     * @return the int
     */
    public int getY(){
        return y;
    }

    /**
     * Sets openings from which the player can access the path for the game
     *
     * @param left - opening from the left
     * @param top -  opening from the top
     * @param right - opening from the right
     * @param bottom - opening from the bottom
     */
    public void setOpenings(int left, int top, int right, int bottom) {
        openings [0] = left;
        openings [1] = top;
        openings [2] = right;
        openings [3] = bottom;
    }
}