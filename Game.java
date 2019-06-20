
public class Game
{
    private Player one, two;
    private int goal1 = 0; 
    private int goal2 = 7; 
    private boolean donePlaying; 
    private int currentPlayer; 
    private int numStones, stonesGoal1, stonesGoal2;
    private int[] board;
    private GameMenu menu;

    public Game(Player p1, Player p2)
    {
        board = new int[14];
        board[0] = 0;
        board[7] = 0;

        for (int i = 1; i < 7; i++)
            board[i] = 4;
        for (int i = 8; i < 14; i++)
            board[i] = 4;

        this.one = p1;
        this.two = p2;
        this.currentPlayer = 1; 
    }

    public void setMenu(GameMenu m)
    {
        menu = m;
    }

    public void processButton(int index)
    {
        System.out.println(index); 
        takeTurn(index);
        menu.updateButtons(currentPlayer, board);
    }

    //returns false when turn is over
    public boolean takeTurn(int firstPile)
    {   
        int notMyGoal, myGoal = -1; 
        int stonesInFirstPile = board[firstPile];

        if (currentPlayer == 1)
        {
            myGoal = goal1; 
            notMyGoal = goal2; 
        }
        else
        {
            myGoal = goal2; 
            notMyGoal = goal1; 
        }

        int lastSpace = 0; 
        for (int i = firstPile; i <= stonesInFirstPile; i++)
        {
            if (i != notMyGoal)
            {
                board[i + 1]++;
                //System.out.println(board[i+1]);
                lastSpace = i + 1; 
                stonesInFirstPile--;
            }
        } 

        if (lastSpace == myGoal) //if we ended up in myGoal
        {
            takeTurn(lastSpace);
        }

        if (board[lastSpace] == 1) //return false and change player
        {
            if (currentPlayer == 1)
            {
                goal1 += (1 + board[14-lastSpace]);
                currentPlayer = 2; 
            }
            else
            {
                goal2 += (1 + board[14-lastSpace]);
                currentPlayer = 1; 
            }
            return false; 
        }
        else 
        {
            if (currentPlayer == 1)
                menu.updateButtons(1, board);
            else
                menu.updateButtons(2, board);
        }
        return false; 
    }

    public void resetGame()
    {
        currentPlayer = 1; 
        //reset stones in each space
        for (int i = 0; i < board.length; i++)
        {
            if (i == goal1 || i == goal2)
            {
                board[i] = 0; 
            }
            else
            {
                board[i] = 4; 
            }
        }
    }

    public boolean gameIsOver()
    {
        int eachSpace, sumOfSpaces = 0;
        //adds total number of stones in all small spaces (not goals)
        for (int i = 0; i < board.length; i++)
        {
            if (!((i == goal1) || (i == goal2)))
            {
                eachSpace = board[i];
                sumOfSpaces += eachSpace;
            }
        }
        //game is over if there are no stones left on board not in goals
        if ((sumOfSpaces == 0) 
        || (stonesGoal1 > sumOfSpaces + stonesGoal2) //clear winner
        || (stonesGoal2 > sumOfSpaces + stonesGoal1)) //clear winner
            return true;

        return false;
    }

    public int determineWinner()
    {
        if (gameIsOver())
        {
            if (stonesGoal1 > stonesGoal2)
            { 
                System.out.println("Player 1 wins!");
                return 1;
            }

            else if (stonesGoal2> stonesGoal1)
            {
                System.out.println ("Player 2 wins!");
                return 2; 
            }
            else
                System.out.println("It's a tie!");
        }
        return 0;
    }
}