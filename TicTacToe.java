import java.util.Arrays;
import java.lang.IllegalArgumentException;
import java.util.Scanner;
import java.util.Random;

public class TicTacToe {
  
  public static void main(String [] args) {
    
    play();
    
  }
  
  /* The method createBoard takes input an integer n, and returns a
   n by n array of characters. When created it is  empty,
   ie, the elements are initlized with ' '  */
  
  public static char [][] createBoard(int n) {
    
    char [][] array= new char [n][n];
    
    for(int i=0; i<n; i++){
      for(int j=0; j<n; j++){
        array[i][j]=' ';
      }
    }
    
    return array;
  }
  /* A method that takes a 2D char array and prints out the board. */
  
  public static void displayBoard(char[][] array){
    
    for(int i=0; i<2*array.length+1; i++) {
      if(i %2 ==0){
        for(int j=0; j<2*array.length+1;j++){
          if(j%2==0){
            System.out.print("+");
          }
          else{
            System.out.print("-");
          }
        }
      }
      else {
        for(int j=0; j<2*array.length+1;j++){
          if(j%2==0){
            System.out.print("|");
          }
          else{
            System.out.print((array[i/2][j/2])); 
          }
        }
      }
      System.out.println();
    }
  }
  /* A method that takes as input the board, the character to write and two integers
   x and y representing the position on the board the char should be written. 
   
   We cannot place a character anywhere except on a blank (' ' ) space on the board.
   This method thus must first verify that the inputs are valid!
   
   
   
   */
  
  
  public static void writeOnBoard(char[][] board, char input, int row, int col)
  {
    int n = board.length;
    
    if(row >= n || col >= n || row < 0 || col < 0)  // Verify input is not in a row or col that doesnt exist
    {
      throw new IllegalArgumentException("Incorrect parameters");
    }
    
    else if(board[row][col] == ' ')
    {
      board[row][col] = input;
      displayBoard(board);
    }
    
    
    else if(board[row][col] != ' ')
    {
      throw new IllegalArgumentException("Something exists there already"); //Verify we arent trying to place a move on an occupied space.
      
      
    }
    
  }
  
  public static void getUserMove(char[][]board){
    
    int n=board.length;
    String s;
    int x;
    int y;
    
    Scanner read = new Scanner(System.in);
    
    System.out.println("Please enter your move:");
    
    
    x=read.nextInt();
    y=read.nextInt();
    
    
    while(x>= n || y >= n || x < 0 || y < 0) {
      
      System.out.println("invalid input, try again"); //Invalid input prompts us to try again
      x=read.nextInt();
      y=read.nextInt();
    }
    
    while(board[x][y] != ' '){
      System.out.println("Occupied space, try again");
      x=read.nextInt();
      y=read.nextInt();
    }
    
    while(board[x][y] == ' ') {  //When we have a valid input , we place our move there and display the board.
      board[x][y] = 'x';
      
      displayBoard(board);
    }
  }
  
  
  public static boolean checkForObviousMove(char[][]board){
    
    /* First of all, the highest priority move for the AI is a move that results in a win. 
     * This will occur when a row/col/diagonal has exactly n-1 'o' and exactly 1 ' ', in that case
     * the AI should place the 'o' in that empty space. If this is not the case then we should move
     on to the next best option, which is block the user from winning on their next turn*/
    
    int n=board.length;
    
    for(int i=0; i<n; i++) {
      if((numberOfChar(getCol(board,i),'o')==n-1 && containsSpace(getCol(board,i))==true)) { //For example if there is a col of n-1 'o' and a blank space, we should place the 'o' there.
        int index=indexOfSpace(getCol(board,i));
        writeOnBoard(board,'o',index,i);
        return true;
      }
      else if((numberOfChar(getRow(board,i),'o')==n-1 && containsSpace(getRow(board,i))==true)){
        int index=indexOfSpace(getRow(board,i));
        writeOnBoard(board,'o',i,index);
        return true;
      }
      else if(( numberOfChar(diagonalL(board),'o')==n-1 && containsSpace(diagonalL(board))==true)){
        int index=indexOfSpace(diagonalL(board));
        writeOnBoard(board,'o',index, index);
        return true;
      }
      
      else if(( numberOfChar(diagonalR(board),'o')==n-1 && containsSpace(diagonalR(board))==true)){
        int index=indexOfSpace(diagonalR(board));
        writeOnBoard(board,'o',index, n-index-1);
        return true;
      }
    }
    
    for(int j=0; j<n;j++){ // If AI has no direct move to win then he should look to block user.
      
     //For example if the User is one move away from being able to win on a col, we should block that space.
      if((numberOfChar(getCol(board,j),'x')==n-1 && containsSpace(getCol(board,j))==true)) {  
        int index=indexOfSpace(getCol(board,j));
        writeOnBoard(board,'o',index,j);
        return true;
      }
      else if((numberOfChar(getRow(board,j),'x')==n-1 && containsSpace(getRow(board,j))==true)){
        int index=indexOfSpace(getRow(board,j));
        writeOnBoard(board,'o',j,index);
        return true;
        
      }
      
      else if(( numberOfChar(diagonalL(board),'x')==n-1 && containsSpace(diagonalL(board))==true)){
        int index=indexOfSpace(diagonalL(board));
        writeOnBoard(board,'o',index, index);
        return true;
        
      }
      
      else if(( numberOfChar(diagonalR(board),'x')==n-1 && containsSpace(diagonalR(board))==true)){
        int index=indexOfSpace(diagonalR(board));
        writeOnBoard(board,'o',index, index);
        return true;
        
      }
    }
    
    
    return false;
    
    
    
    
  }
  
  
  
  public static void getAIMove(char[][]board) {
    int n=board.length;
    if(checkForObviousMove(board)==false){ //If there are no obvius moves, then we should place a move in a random valid spot.
      
      Random move=new Random();
      
      int random1=move.nextInt(n);
      int random2=move.nextInt(n);
      
      while(board[random1][random2]!=' '){
        random1=move.nextInt(n);
        random2=move.nextInt(n);
      }
      writeOnBoard(board,'o',random1,random2);
      
    }
    
    
  }
  
  
  //Checking for a winner. Here we are looking for a row/col/diagonal which has n of the same character, AND we want this to be the only such winning combo, that is, no other row/col/diagonal with n of that char.
  public static char checkForWinner(char[][]board) {
    int n=board.length;
    
    
    String ind="";
    int i=0;
    
    
    
    while(i<n){
      
      for(int j=0; j<n; j++){
        
        if((numberOfChar(getRow(board,j),'x')==n) && (!totalCharCol(board,'o'))&&(!totalCharRow(board,'o')) &&(!totalCharDiagonalL(board,'o')) && (!totalCharDiagonalR(board,'o')))   { 
          
          
          return 'x';
        }
        
        else if((numberOfChar(getCol(board,j),'x')==n)&& (!totalCharCol(board,'o'))&&(!totalCharRow(board,'o')) &&(!totalCharDiagonalL(board,'o')) && (!totalCharDiagonalR(board,'o'))) {
          
          return 'x';
        }
        else if((numberOfChar(diagonalL(board),'x')==n) && (!totalCharCol(board,'o'))&&(!totalCharRow(board,'o')) &&(!totalCharDiagonalL(board,'o')) && (!totalCharDiagonalR(board,'o'))) {
          return 'x';
        }
        else if((numberOfChar(diagonalR(board),'x')==n)&&(!totalCharCol(board,'o'))&&(!totalCharRow(board,'o')) &&(!totalCharDiagonalL(board,'o')) && (!totalCharDiagonalR(board,'o'))) {
          return 'x';
          
        }
        
        
        
        else if((numberOfChar(getRow(board,j),'o')==n) &&(!totalCharCol(board,'x'))&&(!totalCharRow(board,'x')) &&(!totalCharDiagonalL(board,'x')) && (!totalCharDiagonalR(board,'x'))) {
          return 'o';
        }
        
        else if((numberOfChar(getCol(board,j),'o')==n)&&(!totalCharCol(board,'x'))&&(!totalCharRow(board,'x')) &&(!totalCharDiagonalL(board,'x')) && (!totalCharDiagonalR(board,'x'))) {
          return 'o'; 
        }
        
        
        else if((numberOfChar(diagonalL(board),'o')==n)&&(!totalCharCol(board,'x'))&&(!totalCharRow(board,'x')) &&(!totalCharDiagonalL(board,'x')) && (!totalCharDiagonalR(board,'x'))){
          return 'o';
        }
        else if((numberOfChar(diagonalR(board),'o')==n)&&(!totalCharCol(board,'x'))&&(!totalCharRow(board,'x')) &&(!totalCharDiagonalL(board,'x')) && (!totalCharDiagonalR(board,'x'))) {
          
          return 'o';
          
        }
      }
      
      
      
      return ' ';
      
    }     
    return' ';
    
  }
  
  
  
  public static void play(){
    
    String name;
    int dim;
    boolean playerTurn = false;
    
    Scanner read =new Scanner(System.in);
    
    System.out.println("Please enter your name:");
    
    name=read.nextLine();
    
    
    
    System.out.println("Welcome "+name+", Please enter the dimension of the board");
    
    while(!read.hasNextInt()){
      System.out.println("That is not a valid dimension: Try again");
      read.next();
      
    } 
    
    dim=read.nextInt();
    int totalMoves=0;
    char[][] board= createBoard(dim);
    
    int coin;
    
    coin=(int)(2*Math.random());
    String result="";
    
    if(coin==1){
      System.out.println("The result of the coin toss is 1");
      result="The AI has the first move";
      System.out.println(result);
      System.out.println("The AI made its move");
      getAIMove(board);
      totalMoves++;
      playerTurn = true;
    }
    
    if(coin==0){
      System.out.println("The result of the coin toss is 0");
      result=""+name+" has the first move";
      System.out.println(result);
      getUserMove(board);
      totalMoves++;
      playerTurn = false;
    }
    
    
    if(checkForWinner(board)==' ' && (totalMoves==dim*dim)){
      System.out.println("Its a tie!");
    }
    
    //In this case, the checkForWinner is not indicating someone has won yet, AND the board is not yet full, so we should continue to play.
    while(checkForWinner(board)==' ' && (totalMoves!=dim*dim)){ 
      
      if(playerTurn == true){
        getUserMove(board);
        playerTurn = false;
        totalMoves++;  
      }
      else{
        System.out.println("The AI made its move:");
        getAIMove(board);
        playerTurn = true;
        totalMoves++;
      }
    }
    if(checkForWinner(board)=='x'){
      System.out.println("You won!");
    }
    if(checkForWinner(board)=='o'){
      System.out.println("You lose!");
    }
    else if(checkForWinner(board)==' '){
      System.out.println("Tie");
    }
  }
  
  
  
  
  
  
  
  
  
// HELPER METHODS!
  
  //A method that returns true if there exists a row in a 2D array that contains a character n times( where n is the length of the board)
  public static boolean totalCharRow(char[][]board,char c){
    int n=board.length;
    for(int j=0; j<board.length;j++){
      
      if(numberOfChar(getRow(board,j),c)==n){
        return true;
      }
    }
    return false;
  }
  //A method that returns true if there exists a col in a 2D array that contains a character n times( where n is the length of the board)
  public static boolean totalCharCol(char[][]board,char c){
    int n=board.length;
    for(int j=0; j<board.length;j++){
      
      if(numberOfChar(getCol(board,j),c)==n){
        return true;
      }
    }
    return false;
  }
  //A method that returns true if there exists a left diagonal in a 2D array that contains a character n times( where n is the length of the board)
  public static boolean totalCharDiagonalL(char[][]board,char c){
    int n=board.length;
    for(int j=0; j<board.length;j++){
      
      if(numberOfChar(diagonalL(board),c)==n){
        return true;
      }
    }
    return false;
  }
  //A method that returns true if there exists a right diagonal in a 2D array that contains a character n times( where n is the length of the board)
  public static boolean totalCharDiagonalR(char[][]board,char c){
    int n=board.length;
    for(int j=0; j<board.length;j++){
      
      if(numberOfChar(diagonalR(board),c)==n){
        return true;
      }
    }
    return false;
  } 
  
  
  public static boolean isWinningRow(char[][]board){
    
    int n=board.length;
    for(int i=0; i<n; i++){
      // method checks all rows to see if any contain n 'o'
      if(numberOfChar(getRow(board,i),'o')==n){
        return true;
      }
    }
    return false;
  }
  // The isWinning methods checks if any row/col/diagonal already has n of the same characters.
  public static boolean isWinningCol(char[][]board){
    
    int n=board.length;
    for(int i=0; i<n; i++){
      // method checks all Col to see if any contain n 'o'
      if(numberOfChar(getCol(board,i),'o')==n){
        return true;
      }
    }
    return false;
  }
  
  public static boolean isWinningDiagonalL(char[][]board){
    
    int n=board.length;
    for(int i=0; i<n; i++){
      // method checks the diagonalL to see if any contain n 'o'
      if(numberOfChar(diagonalL(board),'o')==n){
        return true;
      }
    }
    return false;
  }
  
  public static boolean isWinningDiagonalR(char[][]board){
    
    int n=board.length;
    for(int i=0; i<n; i++){
      // method checks the diagonalR to see if any contain n 'o'
      if(numberOfChar(diagonalR(board),'o')==n){
        return true;
      }
    }
    return false;
  }
  
  
// A method that returns the ith col of 2D char array
  
  public static char[] getCol(char[][]board,int i){
    
    int n=board.length;
    
    char[] col= new char[n];
    
    for(int j=0; j<n; j++) {
      col[j]=board[j][i];
      
    }
    return col;
  }
// A method that returns the ith row of a 2D char array
  public static char[] getRow(char[][]board,int i){
    
    int n=board.length;
    
    char[] row= new char[n];
    
    for(int j=0; j<n; j++) {
      row[j]=board[i][j];
    }
    return row;
  }
  // A method that returns the number of a character c in a 1D char array.
  
  public static int numberOfChar(char[] array, char c) {
    
    int counter=0;
    int n=array.length;
    for(int i=0; i<n; i++) {
      if(array[i]==c){
        counter++;
      }
    }
    return counter;
  }
  // A method that returns true if a 1D char array contains a blank space
  public static boolean containsSpace(char[] array) {
    int n=array.length;
    for(int i=0; i<n;i++){
      if(array[i]==' '){
        return true;
      }
    }
    return false;
  }
  // A method that returns the first diagonal of a 1D char array
  public static char[] diagonalL(char[][]array) {
    int n=array.length;
    char[] diag= new char[n];
    for(int i=0; i<n; i++) {
      diag[i]=array[i][i];
    }
    return diag;
  }
  
  public static char[] diagonalR(char[][]array){
    int n=array.length;
    char[] diag= new char[n];
    diag[0]=array[0][n-1];
    for(int i=1;i<n;i++){
      diag[i]=array[i][n-1-i];
    }
    return diag;
  }
  
  //A method that returns the index (first instance) of the space character
  public static int indexOfSpace(char[]array) {
    
    
    if(containsSpace(array)==true){
      for(int i=0; i<array.length; i++){
        if(array[i]==' '){
          return i;
        }
      }
    }
    return 0 ;
    
  }
}