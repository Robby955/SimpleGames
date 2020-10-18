# Simple tic tac toe in python utilizing list comprehensions to check for winners
# To run the game, load the file and type ticTacToe() in the console.

import sys


# Method to switch the player.
def switchPlayer():
    global current_player
    if current_player=='X':
        current_player='O'
    else:
        current_player='X'
    return(current_player)


#Method that makes the actual move.
def makeMove(current_board):
    # Check for winners
    checkRows()
    checkColumns()
    checkLeft()
    checkRight()
    checkTies()
    
    # If the game is over and it isn't a tie
    if gameOver==True and isTie==False:
        switchPlayer()
        print(f'Congratulations, {current_player} wins!')
    
    # If the game is still going
    if gameOver==False:
        
        move=input("Enter your move: ")
        move=int(move)-1


        
        if isinstance(move,int) != True or move>dim or move<0:
            print("That is not a valid move, try againZz")
            switchPlayer()
        # Make sure it is a blank space, if not prompt user to try again
        if board[move]=='-':
            if current_player=='X':
                board[move]="X"
            elif current_player=='O':
                board[move]='O'
            printDisplay(k)
        elif board[move]!='-':
            print("That space is already taken, try again")
            switchPlayer()


# Main function, define key variables. While game is still going, do next move.
def ticTacToe():
    global current_player
    current_player="X"
    global gameOver
    gameOver=False
    global board
    global k
    global dim
    global isTie
    isTie=False
    
    
    while True:
        k=int(input("Please enter the dimension "))
        if k<=2:
            print("Sorry, the dimension must be greater than 2. Try again ")
            continue
        else:
            break

    dim=k**2
    board=printEmptyBoard(k)
    
    if gameOver==True:
        Print("Thanks for Playing")
        sys.exit()

    
    
    while gameOver==False:
        makeMove(board)
        switchPlayer()


# Makes an empty board of dimension K
def printEmptyBoard(k):
    dim=k**2
    global board
    board=['-']*dim
    getStop=[(n*k)-1 for n in range(1,100)]
    for i in range(dim):
       if i not in getStop:
            print(board[i]+'|',end='')
       else:
            print(board[i])
    return(board)
   # return(board)

# This Method updates the display, it must have a board already going, so it doesn't make a blank display.
def printDisplay(k):
    getStop=[(n*k)-1 for n in range(1,1000)]
    for i in range(dim):
       if i not in getStop:
            print(board[i]+'|',end='')
       else:
            print(board[i])
            

# Check for winners, if there is a winner, return it and set boolean of continueGame off

def checkRows():
    z=1
    #getEnd=[n*k for n in range(1,1000)]
    for z in range(k+1):
        listComp=[board[i]==board[i+1]!='-' for i in range((z-1)*k,z*k) if i!=z*k-1]
        if False not in listComp:
               #print(listComp)
               print(f"Winner on Row {z}")
               global gameOver
               gameOver=True
               break
               # Update boolean?
               #z=z+1
        else:
            continue
  
def checkColumns():
    for i in range(k):
        listComp=[board[i]==board[i+j*k]!='-' for j in range(k)]
        if False not in listComp:
            print(f'Winner on Column {i+1}')
            global gameOver
            gameOver=True
            break
        else:
            continue

def checkLeft():

    listComp=[board[i*(k+1)] for i in range(k)]
    if 'O' not in listComp and '-' not in listComp:
        print(f'Winner on left diagonal with symbol X')
        global gameOver
        gameOver=True
    elif 'X' not in listComp and '-' not in listComp:
        print(f'Winner on left diagonal with symbol O')
        gameOver=True

# Check the right diagonal
def checkRight():
        
    listComp=[board[i*(k-1)] for i in range(2,k+1)]
    if 'O' not in listComp and '-' not in listComp:
        print(f'Winner on right diagonal with symbol X')
        global gameOver
        gameOver=True
    elif 'X' not in listComp and '-' not in listComp:
        print(f'Winner on right diagonal with symbol O')
        gameOver=True

# Check for ties, if the board has no empty spaces and there is still no winner, it is a tie.
def checkTies():
    global gameOver
    global isTie
    if gameOver==False: # Game hasn't been one
        if '-' not in board: # But no empty spaces remain.
            print("It's a Tie!") # Thus it is a tie
            gameOver=True
            isTie=True
                

